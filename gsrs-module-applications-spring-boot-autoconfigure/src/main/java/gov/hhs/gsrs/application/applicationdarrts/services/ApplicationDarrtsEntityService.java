package gov.hhs.gsrs.application.applicationdarrts.services;

import gov.hhs.gsrs.application.applicationdarrts.models.ApplicationDarrts;
import gov.hhs.gsrs.application.applicationdarrts.models.SubstanceKeyParentConcept;
import gov.hhs.gsrs.application.applicationdarrts.repositories.ApplicationDarrtsRepository;

import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationDarrtsEntityService extends AbstractGsrsEntityService<ApplicationDarrts, String> {
    public static final String CONTEXT = "applicationsdarrts";

    public ApplicationDarrtsEntityService() {
        super(CONTEXT, IdHelpers.STRING_NO_WHITESPACE, null, null, null);
    }

    @Autowired
    private ApplicationDarrtsRepository repository;

    @Autowired
    private JsonMapper mapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<ApplicationDarrts> getEntityClass() {
        return ApplicationDarrts.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected ApplicationDarrts fromNewJson(JsonNode json) {
        return mapper.convertValue(json, ApplicationDarrts.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected ApplicationDarrts create(ApplicationDarrts application) {
        try {
            return repository.saveAndFlush(application);
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected ApplicationDarrts update(ApplicationDarrts application) {
        return repository.saveAndFlush(application);
    }

    @Override
    public void delete(String id) {
        // repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<ApplicationDarrts> newUpdateEvent(ApplicationDarrts updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<ApplicationDarrts> newCreationEvent(ApplicationDarrts createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(ApplicationDarrts entity) {
        return entity.getAppType();
    }

    @Override
    protected List<ApplicationDarrts> fromNewJsonList(JsonNode list)  {
        return null;
    }

    /*
    @Override
    protected Application fixUpdatedIfNeeded(Application oldEntity, Application updatedEntity) {
        //force the "owner" on all the updated fields to point to the old version so the uuids are correct
        return updatedEntity;
    }
    */

    @Override
    protected ApplicationDarrts fromUpdatedJson(JsonNode json)  {
        //TODO should we make any edits to remove fields?
        return mapper.convertValue(json, ApplicationDarrts.class);
    }

    @Override
    protected List<ApplicationDarrts> fromUpdatedJsonList(JsonNode list)  {
        return null;
        /*
        List<Application> substances = new ArrayList<>(list.size());
        for(JsonNode n : list){
            substances.add(fromUpdatedJson(n));
        }
        return substances;
         */
    }

    @Override
    protected JsonNode toJson(ApplicationDarrts application)  {
        return mapper.valueToTree(application);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<ApplicationDarrts> get(String id) {
        try {
            return getIdByApptypeAndAppnumber(id);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<ApplicationDarrts> flexLookup(String someKindOfId) {
        if (someKindOfId == null) {
            return Optional.empty();
        }
        //   return repository.findById(someKindOfId);
        try {
            return getIdByApptypeAndAppnumber(someKindOfId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<ApplicationDarrts> found = flexLookup(someKindOfId);
        if (found.isPresent()) {
            return Optional.of(found.get().getAppType());
        }
        return Optional.empty();
    }

    public  Optional<ApplicationDarrts> getIdByApptypeAndAppnumber(String appTypeNumber) throws Exception {
        String appType = "";
        String appNumber = "";
        String newAppNumber = "";

        if (appTypeNumber != null) {
            for (int i = 0; i < appTypeNumber.length(); i++) {
                // Check for non-digit character, check if it is a character
                if (!Character.isDigit(appTypeNumber.charAt(i))) {

                    appType = appType + appTypeNumber.charAt(i);
                } else { // check for digits
                    appNumber = appNumber + appTypeNumber.charAt(i);
                }
            } // for

            if (appNumber != null) {
                if (appNumber.length() < 6) {
                    newAppNumber = StringUtils.leftPad(appNumber, 6, "0");
                    appNumber = newAppNumber;
                }
            }

        } // appNumber is not null

        if (appType == null) {
            throw new IllegalArgumentException("No Application Type found " + appTypeNumber);
        }
        if (appNumber == null) {
            throw new IllegalArgumentException("No Application Number found " + appTypeNumber);
        }

        return getApplicationByApptypeAndAppnumber(appType, appNumber);
    }

    public Optional<ApplicationDarrts> getApplicationByApptypeAndAppnumber(String appType, String appNumber)  {
        ApplicationDarrts appFirst = null;
        StringBuilder routeOfAdmin = new StringBuilder();

        List<ApplicationDarrts> applicationList = repository.getApplicationByApptypeAndAppnumber(appType, appNumber);

        if (applicationList.size() > 0) {

            for (int i = 0; i < applicationList.size(); i++) {
                ApplicationDarrts app = applicationList.get(i);
                if (routeOfAdmin.length() != 0) {
                    routeOfAdmin.append("|");
                }
                routeOfAdmin.append((app.routeOfAdmin != null) ? app.routeOfAdmin : "");
            }
            // Store all the combined data of each row in the first row.
            appFirst = applicationList.get(0);
            appFirst.routeOfAdmin = routeOfAdmin.toString();
            applicationList.set(0, appFirst);
        }
        return Optional.ofNullable(appFirst);
    }

    public Optional<SubstanceKeyParentConcept> getSubstanceKeyParentConcept(String substanceKey) {
        SubstanceKeyParentConcept appFirst = null;
        //StringBuilder routeOfAdmin = new StringBuilder();

        List<SubstanceKeyParentConcept> list = repository.getSubstanceKeyParentConcept(substanceKey);
        if (list.size() > 0) {
            /*
            for (int i = 0; i < list.size(); i++) {
                SubstanceKeyParentConcept app = list.get(i);
                if (routeOfAdmin.length() != 0) {
                    routeOfAdmin.append("|");
                }
              //  routeOfAdmin.append((app.routeOfAdmin != null) ? app.routeOfAdmin : "");
            }
             */
            // Store all the combined data of each row in the first row.
            appFirst = list.get(0);
         //   appFirst.routeOfAdmin = routeOfAdmin.toString();
         //   applicationList.set(0, appFirst);
        }
        return Optional.ofNullable(appFirst);
    }

}
