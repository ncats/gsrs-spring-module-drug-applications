package gov.hhs.gsrs.application.applicationdarrts.controllers;

import gov.hhs.gsrs.application.ApplicationDataSourceConfig;
import gov.hhs.gsrs.application.applicationdarrts.models.ApplicationDarrts;
import gov.hhs.gsrs.application.applicationdarrts.models.SubstanceKeyParentConcept;
import gov.hhs.gsrs.application.applicationdarrts.searcher.LegacyApplicationDarrtsSearcher;
import gov.hhs.gsrs.application.applicationdarrts.services.ApplicationDarrtsEntityService;

import gsrs.autoconfigure.GsrsExportConfiguration;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.service.ExportService;
import gsrs.service.GsrsEntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PathVariable;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@ExposesResourceFor(ApplicationDarrts.class)
@GsrsRestApiController(context = ApplicationDarrtsEntityService.CONTEXT, idHelper = IdHelpers.STRING_NO_WHITESPACE)
public class ApplicationDarrtsController extends EtagLegacySearchEntityController<ApplicationDarrtsController, ApplicationDarrts, String> {

 //   @Autowired
 //   private ETagRepository eTagRepository;

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
    private ApplicationDarrtsEntityService applicationDarrtsEntityService;

    @Autowired
    private LegacyApplicationDarrtsSearcher legacyApplicationDarrtsSearcher;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public GsrsEntityService<ApplicationDarrts, String> getEntityService() {
        return applicationDarrtsEntityService;
    }

    @Override
    protected LegacyGsrsSearchService<ApplicationDarrts> getlegacyGsrsSearchService() {
        return legacyApplicationDarrtsSearcher;
    }

    @Override
    protected Stream<ApplicationDarrts> filterStream(Stream<ApplicationDarrts> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }

    @GetGsrsRestApiMapping("/substanceparentconcept/{substanceKey}")
    public ResponseEntity<String> getSubstanceKeyParentConcept(@PathVariable("substanceKey") String substanceKey) throws Exception {
        Optional<SubstanceKeyParentConcept> concept = applicationDarrtsEntityService.getSubstanceKeyParentConcept(substanceKey);
        if (concept == null) {
            throw new IllegalArgumentException("There is no SubstanceKey provided");
        }
        return new ResponseEntity(concept.get(), HttpStatus.OK);
    }

    public Optional<ApplicationDarrts> injectSubstanceDetails(Optional<ApplicationDarrts> application) {

        try {
            if (application.isPresent()) {

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
