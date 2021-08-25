package gov.hhs.gsrs.application.application.controllers;

import gov.hhs.gsrs.application.SubstanceModuleService;
import gov.hhs.gsrs.application.application.models.Application;
import gov.hhs.gsrs.application.application.models.additional.ApplicationHistory;
import gov.hhs.gsrs.application.application.models.additional.ClinicalTrial;
import gov.hhs.gsrs.application.application.models.additional.ProductEffected;
import gov.hhs.gsrs.application.application.models.additional.ProductTechnicalEffect;
import gov.hhs.gsrs.application.application.searcher.LegacyApplicationSearcher;
import gov.hhs.gsrs.application.application.services.ApplicationEntityService;

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
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@ExposesResourceFor(Application.class)
@GsrsRestApiController(context = ApplicationEntityService.CONTEXT, idHelper = IdHelpers.NUMBER)
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
        Stream<Application> mstream = new EtagExportGenerator<Application>(entityManager, transactionManager).generateExportFrom(getEntityService().getContext(), etagObj.get()).get();

        //GSRS-699 REALLY filter out anything that isn't public unless we are looking at private data
//        if(publicOnly){
//            mstream = mstream.filter(s-> s.getAccess().isEmpty());
//        }

        Stream<Application> effectivelyFinalStream = filterStream(mstream, publicOnly, parameters);

        if(fileName!=null){
            emd.setDisplayFilename(fileName);
        }

        ExportProcess<Application> p = exportService.createExport(emd,
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

    private Exporter<Application> getExporterFor(String extension, OutputStream pos, boolean publicOnly, Map<String, String> parameters) throws IOException {
        ExporterFactory.Parameters params = this.createParamters(extension, publicOnly, parameters);
        ExporterFactory<Application> factory = this.gsrsExportConfiguration.getExporterFor(this.getEntityService().getContext(), params);
        if (factory == null) {
            throw new IllegalArgumentException("could not find suitable factory for " + params);
        } else {
            return factory.createNewExporter(pos, params);
        }
    }

    @GetGsrsRestApiMapping("/applicationhistory/{applicationId}")
    public ResponseEntity<String> findApplicationHistoryByApplicationId(@PathVariable("applicationId") String applicationId) throws Exception {
        List<ApplicationHistory> list = applicationEntityService.findApplicationHistoryByApplicationId(applicationId);
        if (applicationId == null) {
            throw new IllegalArgumentException("There is no Application Id provided");
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetGsrsRestApiMapping("/prodtechnicaleffect/{applicationId}")
    public ResponseEntity<String> findProductTechnicalEffectByApplicationId(@PathVariable("applicationId") String applicationId) throws Exception {
        List<ProductTechnicalEffect> list = applicationEntityService.findProductTechnicalEffectByApplicationId(applicationId);
        if (applicationId == null) {
            throw new IllegalArgumentException("There is no Application Id provided");
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetGsrsRestApiMapping("/prodeffected/{applicationId}")
    public ResponseEntity<String> findProductEffectedByApplicationId(@PathVariable("applicationId") String applicationId) throws Exception {
        List<ProductEffected> list = applicationEntityService.findProductEffectedByApplicationId(applicationId);
        if (applicationId == null) {
            throw new IllegalArgumentException("There is no Application Id provided");
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetGsrsRestApiMapping("/appclinicaltrial/{applicationId}")
    public ResponseEntity<String> findClinicalTrialByApplicationId(@PathVariable("applicationId") String applicationId) throws Exception {
        List<ClinicalTrial> list = applicationEntityService.findClinicalTrialByApplicationId(applicationId);
        if (applicationId == null) {
            throw new IllegalArgumentException("There is no Application Id provided");
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

    /*
    @GetGsrsRestApiMapping("/distcenter/{substanceKey}")
    public ResponseEntity<String> findCenterBySubstanceKey(@PathVariable("substanceKey") String substanceKey) throws Exception {
        List<String> provenanceList = applicationEntityService.findCenterBySubstanceKey(substanceKey);
        if (substanceKey == null) {
            throw new IllegalArgumentException("There is no Substance Key provided");
        }
        return new ResponseEntity(provenanceList, HttpStatus.OK);
    }
    */

    public JsonNode injectSubstanceBySubstanceKey(String substanceKey) {

        JsonNode actualObj = null;
        try {

            if (substanceKey != null) {

                ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(substanceKey);

                String jsonString = response.getBody();
                if (jsonString != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    actualObj = mapper.readTree(jsonString);

                 //   name = actualObj.path("_name").textValue();
                   // ingred._approvalID = actualObj.path("approvalID").textValue();
                   // ingred._name = actualObj.path("_name").textValue();
                }

                /*
                if (application.get().applicationProductList.size() > 0) {
                    for (int j = 0; j < application.get().applicationProductList.size(); j++) {
                        ApplicationProduct prod = application.get().applicationProductList.get(j);
                        if (prod != null) {

                            if (prod.applicationIngredientList.size() > 0) {
                                for (int i = 0; i < prod.applicationIngredientList.size(); i++) {
                                    ApplicationIngredient ingred = prod.applicationIngredientList.get(i);
                                    if (ingred != null) {
                                        if (ingred.substanceKey != null) {

                                            // ********* Get Substance Module/Details by Substance Code ***********
                                            // Using this for local Substance Module:  0017298AA
                                            // Use this for NCAT FDA URL API:   0126085AB
                                            ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(ingred.substanceKey);

                                            String jsonString = response.getBody();
                                            if (jsonString != null) {
                                                ObjectMapper mapper = new ObjectMapper();
                                                JsonNode actualObj = mapper.readTree(jsonString);

                                                ingred._substanceUuid = actualObj.path("uuid").textValue();
                                                ingred._approvalID = actualObj.path("approvalID").textValue();
                                                ingred._name = actualObj.path("_name").textValue();
                                            }
                                        }

                                        if (ingred.basisOfStrengthSubstanceKey != null) {

                                            // ********** Get Substance Module/Details by Basis of Strength by Substance Code **********
                                            // Optional<Substance> objSub = this.substanceModuleService.getSubstanceDetails("0017298AA");

                                            ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(ingred.basisOfStrengthSubstanceKey);

                                            String jsonString = response.getBody();
                                            if (jsonString != null) {
                                                ObjectMapper mapper = new ObjectMapper();
                                                JsonNode actualObj = mapper.readTree(jsonString);

                                                ingred._basisOfStrengthSubstanceUuid = actualObj.path("uuid").textValue();
                                                ingred._basisOfStrengthApprovalID = actualObj.path("approvalID").textValue();
                                                ingred._basisOfStrengthName = actualObj.path("_name").textValue();
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                 */
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return actualObj;
    }


    public Optional<Application> injectSubstanceDetails(Optional<Application> application) {

        try {
            if (application.isPresent()) {

                // Optional<Substance> objSub = this.substanceModuleService.getSubstanceDetails("0017298AA");

                /*
                if (application.get().applicationProductList.size() > 0) {
                    for (int j = 0; j < application.get().applicationProductList.size(); j++) {
                        ApplicationProduct prod = application.get().applicationProductList.get(j);
                        if (prod != null) {

                            if (prod.applicationIngredientList.size() > 0) {
                                for (int i = 0; i < prod.applicationIngredientList.size(); i++) {
                                    ApplicationIngredient ingred = prod.applicationIngredientList.get(i);
                                    if (ingred != null) {
                                        if (ingred.substanceKey != null) {

                                            // ********* Get Substance Module/Details by Substance Code ***********
                                            // Using this for local Substance Module:  0017298AA
                                            // Use this for NCAT FDA URL API:   0126085AB
                                            ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(ingred.substanceKey);

                                            String jsonString = response.getBody();
                                            if (jsonString != null) {
                                                ObjectMapper mapper = new ObjectMapper();
                                                JsonNode actualObj = mapper.readTree(jsonString);

                                                ingred._substanceUuid = actualObj.path("uuid").textValue();
                                                ingred._approvalID = actualObj.path("approvalID").textValue();
                                                ingred._name = actualObj.path("_name").textValue();
                                            }
                                        }

                                        if (ingred.basisOfStrengthSubstanceKey != null) {

                                            // ********** Get Substance Module/Details by Basis of Strength by Substance Code **********
                                            // Optional<Substance> objSub = this.substanceModuleService.getSubstanceDetails("0017298AA");

                                            ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(ingred.basisOfStrengthSubstanceKey);

                                            String jsonString = response.getBody();
                                            if (jsonString != null) {
                                                ObjectMapper mapper = new ObjectMapper();
                                                JsonNode actualObj = mapper.readTree(jsonString);

                                                ingred._basisOfStrengthSubstanceUuid = actualObj.path("uuid").textValue();
                                                ingred._basisOfStrengthApprovalID = actualObj.path("approvalID").textValue();
                                                ingred._basisOfStrengthName = actualObj.path("_name").textValue();
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                 */
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return application;
    }

}
