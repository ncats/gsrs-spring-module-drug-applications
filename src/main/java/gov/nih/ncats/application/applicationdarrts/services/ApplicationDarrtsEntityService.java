package gov.nih.ncats.application.applicationdarrts.services;

import gov.nih.ncats.application.applicationdarrts.models.*;
import gov.nih.ncats.application.applicationdarrts.repositories.*;

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
public class ApplicationDarrtsEntityService extends AbstractGsrsEntityService<ApplicationDarrts, String> {
    public static final String  CONTEXT = "applicationdarrts";

    public ApplicationDarrtsEntityService() {
        super(CONTEXT,  IdHelpers.STRING_NO_WHITESPACE, null, null, null);
    }

    @Autowired
    private ApplicationDarrtsRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

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
    protected ApplicationDarrts fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ApplicationDarrts.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected ApplicationDarrts create(ApplicationDarrts application) {
        try {
            return repository.saveAndFlush(application);
        }catch(Throwable t){
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
        repository.deleteById(id);
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
    protected List<ApplicationDarrts> fromNewJsonList(JsonNode list) throws IOException {
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
    protected ApplicationDarrts fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, ApplicationDarrts.class);
    }

    @Override
    protected List<ApplicationDarrts> fromUpdatedJsonList(JsonNode list) throws IOException {
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
    protected JsonNode toJson(ApplicationDarrts application) throws IOException {
        return objectMapper.valueToTree(application);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<ApplicationDarrts> get(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ApplicationDarrts> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<ApplicationDarrts> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getAppType());
        }
        return Optional.empty();
    }

}
