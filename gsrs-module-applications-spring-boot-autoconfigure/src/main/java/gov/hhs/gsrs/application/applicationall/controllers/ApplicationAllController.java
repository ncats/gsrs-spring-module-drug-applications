package gov.hhs.gsrs.application.applicationall.controllers;

import gov.hhs.gsrs.application.ApplicationDataSourceConfig;
import gov.hhs.gsrs.application.SubstanceModuleService;
import gov.hhs.gsrs.application.applicationall.models.ApplicationAll;
import gov.hhs.gsrs.application.applicationall.searcher.LegacyApplicationAllSearcher;
import gov.hhs.gsrs.application.applicationall.services.ApplicationAllEntityService;

import gov.nih.ncats.common.util.Unchecked;
import gsrs.autoconfigure.GsrsExportConfiguration;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.ETagRepository;
import gsrs.service.EtagExportGenerator;
import gsrs.service.ExportService;
import gsrs.service.GsrsEntityService;
import ix.core.models.ETag;
import ix.ginas.exporters.ExportMetaData;
import ix.ginas.exporters.ExportProcess;
import ix.ginas.exporters.Exporter;
import ix.ginas.exporters.ExporterFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@ExposesResourceFor(ApplicationAll.class)
@GsrsRestApiController(context = ApplicationAllEntityService.CONTEXT, idHelper = IdHelpers.STRING_NO_WHITESPACE)
public class ApplicationAllController extends EtagLegacySearchEntityController<ApplicationAllController, ApplicationAll, String> {


    @Autowired
    private ETagRepository eTagRepository;

    @PersistenceContext(unitName =  ApplicationDataSourceConfig.NAME_ENTITY_MANAGER)
    private EntityManager entityManager;
    @Autowired
    private GsrsControllerConfiguration gsrsControllerConfiguration;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private ExportService exportService;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private GsrsExportConfiguration gsrsExportConfiguration;


    @Autowired
    private ApplicationAllEntityService applicationEntityService;

    @Autowired
    private SubstanceModuleService substanceModuleService;

    @Autowired
    private LegacyApplicationAllSearcher legacyApplicationSearcher;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public GsrsEntityService<ApplicationAll, String> getEntityService() {
        return applicationEntityService;
    }

    @Override
    protected LegacyGsrsSearchService<ApplicationAll> getlegacyGsrsSearchService() {
        return legacyApplicationSearcher;
    }

    @Override
    protected Stream<ApplicationAll> filterStream(Stream<ApplicationAll> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }

    @PreAuthorize("isAuthenticated()")
    @GetGsrsRestApiMapping("/export/{etagId}/{format}")
    public ResponseEntity<Object> createExport(@PathVariable("etagId") String etagId, @PathVariable("format") String format,
                                               @RequestParam(value = "publicOnly", required = false) Boolean publicOnlyObj, @RequestParam(value ="filename", required= false) String fileName,
                                               Principal prof,
                                               @RequestParam Map<String, String> parameters) throws Exception {
        Optional<ETag> etagObj = eTagRepository.findByEtag(etagId);

        boolean publicOnly = publicOnlyObj==null? true: publicOnlyObj;

        if (!etagObj.isPresent()) {
            return new ResponseEntity<>("could not find etag with Id " + etagId,gsrsControllerConfiguration.getHttpStatusFor(HttpStatus.BAD_REQUEST, parameters));
        }

        ExportMetaData emd=new ExportMetaData(etagId, etagObj.get().uri, prof.getName(), publicOnly, format);

        //Not ideal, but gets around user problem
        Stream<ApplicationAll> mstream = new EtagExportGenerator<ApplicationAll>(entityManager, transactionManager).generateExportFrom(getEntityService().getContext(), etagObj.get()).get();

        //GSRS-699 REALLY filter out anything that isn't public unless we are looking at private data
//        if(publicOnly){
//            mstream = mstream.filter(s-> s.getAccess().isEmpty());
//        }

        Stream<ApplicationAll> effectivelyFinalStream = filterStream(mstream, publicOnly, parameters);

        if(fileName!=null){
            emd.setDisplayFilename(fileName);
        }

        ExportProcess<ApplicationAll> p = exportService.createExport(emd,
                () -> effectivelyFinalStream);

        p.run(taskExecutor, out -> Unchecked.uncheck(() -> getExporterFor(format, out, publicOnly, parameters)));

        return new ResponseEntity<>(GsrsControllerUtil.enhanceWithView(p.getMetaData(), parameters), HttpStatus.OK);
    }
    /*
    public ResponseEntity<Object> createExport(@PathVariable("etagId") String etagId, @PathVariable("format") String format, @RequestParam(value = "publicOnly", required = false) Boolean publicOnlyObj, @RequestParam(value = "filename", required = false) String fileName, Principal prof, @RequestParam Map<String, String> parameters) throws Exception {

        Optional<ETag> etagObj = this.eTagRepository.findByEtag(etagId);
        boolean publicOnly = publicOnlyObj == null ? true : publicOnlyObj;
        if (!etagObj.isPresent()) {
            return new ResponseEntity("could not find etag with Id " + etagId, this.gsrsControllerConfiguration.getHttpStatusFor(HttpStatus.BAD_REQUEST, parameters));
        } else {
            ExportMetaData emd = new ExportMetaData(etagId, ((ETag) etagObj.get()).uri, "admin", publicOnly, format);
            Stream<Application> mstream = (Stream)(new EtagExportGenerator(this.entityManager, this.transactionManager)).generateExportFrom(this.getEntityService().getContext(), (ETag)etagObj.get()).get();
            Stream<Application> effectivelyFinalStream = this.filterStream(mstream, publicOnly, parameters);

            if (fileName != null) {
                emd.setDisplayFilename(fileName);
            }

            ExportProcess<Application> p = this.exportService.createExport(emd, () -> {
                return effectivelyFinalStream;
            });
            p.run(this.taskExecutor, (out) -> {
                return (Exporter) Unchecked.uncheck(() -> {

                    return this.getExporterFor(format, out, publicOnly, parameters);
                });
            });
            return new ResponseEntity(p.getMetaData(), HttpStatus.OK);
        }
    }
    */

    private Exporter<ApplicationAll> getExporterFor(String extension, OutputStream pos, boolean publicOnly, Map<String, String> parameters) throws IOException {
        ExporterFactory.Parameters params = this.createParamters(extension, publicOnly, parameters);
        ExporterFactory<ApplicationAll> factory = this.gsrsExportConfiguration.getExporterFor(this.getEntityService().getContext(), params);
        if (factory == null) {
            throw new IllegalArgumentException("could not find suitable factory for " + params);
        } else {
            return factory.createNewExporter(pos, params);
        }
    }

    @GetGsrsRestApiMapping("/distcenter/{substanceKey}")
    public ResponseEntity<String> findCenterBySubstanceKey(@PathVariable("substanceKey") String substanceKey) throws Exception {
        List<String> provenanceList = applicationEntityService.findCenterBySubstanceKey(substanceKey);
        if (substanceKey == null) {
            throw new IllegalArgumentException("There is no Substance Key provided");
        }
        return new ResponseEntity(provenanceList, HttpStatus.OK);
    }

    public JsonNode injectSubstanceBySubstanceKey(String substanceKey) {

        JsonNode actualObj = null;
        try {
            if (substanceKey != null) {

                ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(substanceKey);

                String jsonString = response.getBody();
                if (jsonString != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    actualObj = mapper.readTree(jsonString);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return actualObj;
    }

}
