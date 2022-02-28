package gov.hhs.gsrs.application.application.services;

import gov.hhs.gsrs.application.application.models.Application;
import gov.hhs.gsrs.application.application.models.additional.ApplicationHistory;
import gov.hhs.gsrs.application.application.models.additional.ClinicalTrial;
import gov.hhs.gsrs.application.application.models.additional.ProductEffected;
import gov.hhs.gsrs.application.application.models.additional.ProductTechnicalEffect;
import gov.hhs.gsrs.application.application.repositories.ApplicationRepository;

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
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationEntityService extends AbstractGsrsEntityService<Application, Long> {
    public static final String  CONTEXT = "applications";

    public ApplicationEntityService() {
        super(CONTEXT,  IdHelpers.NUMBER, null, null, null);
    }

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<Application> getEntityClass() {
        return Application.class;
    }

    @Override
    public Long parseIdFromString(String idAsString) {
        return Long.parseLong(idAsString);
    }

    @Override
    protected Application fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Application.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected Application create(Application application) {
        try {
            return repository.saveAndFlush(application);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected Application update(Application application) {
        return repository.saveAndFlush(application);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<Application> newUpdateEvent(Application updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<Application> newCreationEvent(Application createdEntity) {
        return null;
    }

    @Override
    public Long getIdFrom(Application entity) {
        return entity.getId();
    }

    @Override
    protected List<Application> fromNewJsonList(JsonNode list) throws IOException {
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
    protected Application fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, Application.class);
    }

    @Override
    protected List<Application> fromUpdatedJsonList(JsonNode list) throws IOException {
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
    protected JsonNode toJson(Application application) throws IOException {
        return objectMapper.valueToTree(application);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Application> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Application> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(Long.parseLong(someKindOfId));
    }

    @Override
    protected Optional<Long> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<Application> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().id);
        }
        return Optional.empty();
    }

    public List<ApplicationHistory> findApplicationHistoryByApplicationId(String applicationId) {
        List<ApplicationHistory> list = repository.findApplicationHistoryByApplicationId(applicationId);
        return list;
    }

    public List<ProductTechnicalEffect> findProductTechnicalEffectByApplicationId(String applicationId) {
        List<ProductTechnicalEffect> list = repository.findProductTechnicalEffectByApplicationId(applicationId);
        return list;
    }

    public List<ProductEffected> findProductEffectedByApplicationId(String applicationId) {
        List<ProductEffected> list = repository.findProductEffectedByApplicationId(applicationId);
        return list;
    }

    public List<ClinicalTrial> findClinicalTrialByApplicationId(String applicationId) {
        List<ClinicalTrial> list = null;
        if (applicationId != null) {
            list = repository.findClinicalTrialByApplicationId(Long.parseLong(applicationId));
        }
        return list;
    }

}
