package gov.hhs.gsrs.application.applicationall.services;

import gov.hhs.gsrs.application.applicationall.models.ApplicationAll;
import gov.hhs.gsrs.application.applicationall.repositories.ApplicationAllRepository;

import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationAllEntityService extends AbstractGsrsEntityService<ApplicationAll, String> {
    public static final String  CONTEXT = "applicationsall";

    public ApplicationAllEntityService() {
        super(CONTEXT,  IdHelpers.STRING_NO_WHITESPACE, null, null, null);
    }

    @Autowired
    private ApplicationAllRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<ApplicationAll> getEntityClass() {
        return ApplicationAll.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected ApplicationAll fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ApplicationAll.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected ApplicationAll create(ApplicationAll application) {
        try {
            return null;
         //   return repository.saveAndFlush(application);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected ApplicationAll update(ApplicationAll application) {
        return null;
        // return repository.saveAndFlush(application);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<ApplicationAll> newUpdateEvent(ApplicationAll updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<ApplicationAll> newCreationEvent(ApplicationAll createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(ApplicationAll entity) {
        return entity.getId();
    }

    @Override
    protected List<ApplicationAll> fromNewJsonList(JsonNode list) throws IOException {
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
    protected ApplicationAll fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, ApplicationAll.class);
    }

    @Override
    protected List<ApplicationAll> fromUpdatedJsonList(JsonNode list) throws IOException {
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
    protected JsonNode toJson(ApplicationAll application) throws IOException {
        return objectMapper.valueToTree(application);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<ApplicationAll> get(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ApplicationAll> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<ApplicationAll> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().id);
        }
        return Optional.empty();
    }

    public List<String> findCenterBySubstanceKey(String substanceKey) {
        List<String> centerList = new ArrayList<>();
        try {
            centerList = repository.findCenterBySubstanceKey(substanceKey);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        /*
        for (String cent : centerList) {
            if (cent != null) {
                myStr.indexOf("planet")a
                if (cent)
                    String replaceString=s1.replace('a','e');
                if (app.fromTable.equalsIgnoreCase("SRS")) {
                            app.fromTable = "GSRS";
                        } else if (app.fromTable.equalsIgnoreCase("Darrts")) {
                            app.fromTable = "Integrity";
                        }
                    } else {
                        app.fromTable = "GSRS";
                    }

                }
            }
        }

         */
        return centerList;
    }
}
