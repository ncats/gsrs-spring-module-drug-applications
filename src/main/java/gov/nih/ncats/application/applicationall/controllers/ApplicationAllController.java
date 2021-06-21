package gov.nih.ncats.application.applicationall.controllers;

import gov.nih.ncats.application.applicationall.models.*;
import gov.nih.ncats.application.applicationall.services.*;
import gov.nih.ncats.application.applicationall.searcher.LegacyApplicationAllSearcher;

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

import com.fasterxml.jackson.databind.JsonNode;
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

@ExposesResourceFor(ApplicationAll.class)
@GsrsRestApiController(context = ApplicationAllEntityService.CONTEXT, idHelper = IdHelpers.STRING_NO_WHITESPACE)
public class ApplicationAllController extends EtagLegacySearchEntityController<ApplicationAllController, ApplicationAll, String> {


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
    private ApplicationAllEntityService applicationEntityService;

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

    @GetGsrsRestApiMapping("/distcenter/{substanceKey}")
    public ResponseEntity<String> findCenterBySubstanceKey(@PathVariable("substanceKey") String substanceKey) throws Exception {
        List<String> provenanceList = applicationEntityService.findCenterBySubstanceKey(substanceKey);
        if (substanceKey == null) {
            throw new IllegalArgumentException("There is no Substance Key provided");
        }
        return new ResponseEntity(provenanceList, HttpStatus.OK);
    }

    public Optional<ApplicationAll> injectSubstanceDetails(Optional<ApplicationAll> application) {

        try {
         /*
            if (application.isPresent()) {

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


                                      //  if (ingred.basisOfStrengthSubstanceKey != null) {

                                            // ********** Get Substance Module/Details by Basis of Strength by Substance Code **********
                                            // Optional<Substance> objSub = this.substanceModuleService.getSubstanceDetails("0017298AA");

                                        //    ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(ingred.basisOfStrengthSubstanceKey);

                                            String jsonString = response.getBody();
                                            if (jsonString != null) {
                                                ObjectMapper mapper = new ObjectMapper();
                                                JsonNode actualObj = mapper.readTree(jsonString);

                                          //      ingred._basisOfStrengthSubstanceUuid = actualObj.path("uuid").textValue();
                                           //     ingred._basisOfStrengthApprovalID = actualObj.path("approvalID").textValue();
                                          //      ingred._basisOfStrengthName = actualObj.path("_name").textValue();
                                            }
                                        }



                                    }
                                }
                            }
                        }
                    }
                }
            }
            */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return application;
    }

}
