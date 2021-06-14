package gov.nih.ncats.application.applicationall.services;

import gov.nih.ncats.application.applicationall.models.*;
import gov.nih.ncats.application.applicationall.repositories.ApplicationAllRepository;

import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
// import gsrs.module.substance.events.SubstanceCreatedEvent;
// import gsrs.module.substance.events.SubstanceUpdatedEvent;
// import gsrs.module.substance.repository.SubstanceRepository;
import gsrs.module.substance.SubstanceEntityService;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidationResponse;
import ix.core.validator.ValidationResponseBuilder;
import ix.core.validator.ValidatorCallback;
// import ix.ginas.models.v1.Substance;
// import ix.ginas.utils.GinasProcessingStrategy;
// import ix.ginas.utils.JsonSubstanceFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ix.utils.Util;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationAllEntityService extends AbstractGsrsEntityService<ApplicationAll, String> {
    public static final String  CONTEXT = "applicationall";

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
            return repository.saveAndFlush(application);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected ApplicationAll update(ApplicationAll application) {
        return repository.saveAndFlush(application);
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
        List<String> centerList = repository.findCenterBySubstanceKey(substanceKey);
        return centerList;
    }
}
