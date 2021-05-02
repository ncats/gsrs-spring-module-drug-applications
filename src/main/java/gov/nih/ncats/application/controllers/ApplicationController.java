package gov.nih.ncats.application.controllers;

import gov.nih.ncats.application.models.*;
import gov.nih.ncats.application.services.*;
import gov.nih.ncats.application.LegacyApplicationSearcher;

import gov.nih.ncats.common.util.Unchecked;
import gsrs.autoconfigure.GsrsExportConfiguration;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.ETagRepository;
import gsrs.repository.EditRepository;
import gsrs.service.EtagExportGenerator;
import gsrs.service.ExportService;
import gsrs.service.GsrsEntityService;

import ix.core.models.ETag;
import ix.core.search.SearchResult;
import ix.ginas.exporters.ExportMetaData;
import ix.ginas.exporters.ExportProcess;
import ix.ginas.exporters.Exporter;
import ix.ginas.exporters.ExporterFactory;
import ix.ginas.models.v1.Substance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

@ExposesResourceFor(Application.class)
@GsrsRestApiController(context = ApplicationEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
public class ApplicationController extends EtagLegacySearchEntityController<ApplicationController, Application, Long> {


    @Autowired
    private ETagRepository eTagRepository;
    @Autowired
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
    private ApplicationEntityService applicationEntityService;

    @Autowired
    private SubstanceModuleService substanceModuleService;

    @Autowired
    private LegacyApplicationSearcher legacyApplicationSearcher;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public GsrsEntityService<Application, Long> getEntityService() {
        return applicationEntityService;
    }

    @Override
    protected LegacyGsrsSearchService<Application> getlegacyGsrsSearchService() {
        return legacyApplicationSearcher;
    }

    @Override
    protected Stream<Application> filterStream(Stream<Application> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }

    public ResponseEntity<Object> createExport(@PathVariable("etagId") String etagId, @PathVariable("format") String format, @RequestParam(value = "publicOnly",required = false) Boolean publicOnlyObj, @RequestParam(value = "filename",required = false) String fileName, Principal prof, @RequestParam Map<String, String> parameters) throws Exception {

        Optional<ETag> etagObj = this.eTagRepository.findByEtag(etagId);
        boolean publicOnly = publicOnlyObj == null ? true : publicOnlyObj;
        if (!etagObj.isPresent()) {
            return new ResponseEntity("could not find etag with Id " + etagId, this.gsrsControllerConfiguration.getHttpStatusFor(HttpStatus.BAD_REQUEST, parameters));
        } else {
            ExportMetaData emd = new ExportMetaData(etagId, ((ETag)etagObj.get()).uri, "admin", publicOnly, format);
            Stream<Application> mstream = (Stream)(new EtagExportGenerator(this.entityManager)).generateExportFrom("application", (ETag)etagObj.get()).get();
            Stream<Application> effectivelyFinalStream = this.filterStream(mstream, publicOnly, parameters);

            if (fileName != null) {
                emd.setDisplayFilename(fileName);
                System.out.println("FILE NAME: " + fileName);
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

    private Exporter<Application> getExporterFor(String extension, OutputStream pos, boolean publicOnly, Map<String, String> parameters) throws IOException {
        ExporterFactory.Parameters params = this.createParamters(extension, publicOnly, parameters);
        ExporterFactory<Application> factory = this.gsrsExportConfiguration.getExporterFor(this.getEntityService().getContext(), params);
        if (factory == null) {
            throw new IllegalArgumentException("could not find suitable factory for " + params);
        } else {
            return factory.createNewExporter(pos, params);
        }
    }

    public Optional<Application> injectSubstanceDetails(Optional<Application> application) {

        try {
            if (application.isPresent()) {

                if (application.get().applicationProductList.size() > 0) {
                    for (int j = 0; j < application.get().applicationProductList.size(); j++) {
                        ApplicationProduct prod = application.get().applicationProductList.get(j);
                        if (prod != null) {

                            if (prod.applicationIngredientList.size() > 0) {
                                for (int i = 0; i < prod.applicationIngredientList.size(); i++) {
                                    ApplicationIngredient ingred = prod.applicationIngredientList.get(i);
                                    if (ingred != null) {
                                        if (ingred.bdnum != null) {

                                            // ********* Get Substance Module/Details by Substance Code ***********
                                            Optional<Substance> objSub = this.substanceModuleService.getSubstanceDetails("0017298AA");

                                            if (objSub.isPresent()) {
                                                ingred._name = objSub.get().getName();
                                                ingred._approvalID = objSub.get().getApprovalID();
                                                ingred._substanceUuid = objSub.get().uuid.toString();
                                            }
                                        }

                                        if (ingred.basisOfStrengthBdnum != null) {

                                            // ********** Get Substance Module/Details by Basis of Strength by Substance Code **********
                                            Optional<Substance> objSub = this.substanceModuleService.getSubstanceDetails("0017298AA");

                                            if (objSub.isPresent()) {
                                                ingred._basisOfStrengthName = objSub.get().getName();
                                                ingred._basisOfStrengthApprovalID = objSub.get().getApprovalID();
                                                ingred._basisOfStrengthSubstanceUuid = objSub.get().uuid.toString();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return application;
    }

}
