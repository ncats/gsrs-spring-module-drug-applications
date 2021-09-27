package gov.hhs.gsrs.application.searchcount.services;

import gov.hhs.gsrs.application.searchcount.models.SubstanceSearchCount;
import gov.hhs.gsrs.application.searchcount.repositories.SearchCountRepository;

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
public class SearchCountEntityService extends AbstractGsrsEntityService<SubstanceSearchCount, String> {
    public static final String  CONTEXT = "searchcounts";

    public SearchCountEntityService() {
        super(CONTEXT,  IdHelpers.STRING_NO_WHITESPACE, null, null, null);
    }

    @Autowired
    private SearchCountRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<SubstanceSearchCount> getEntityClass() {
        return SubstanceSearchCount.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected SubstanceSearchCount fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, SubstanceSearchCount.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected SubstanceSearchCount create(SubstanceSearchCount count) {
        try {
            return repository.saveAndFlush(count);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected SubstanceSearchCount update(SubstanceSearchCount count) {
        return repository.saveAndFlush(count);
    }

    @Override
    public void delete(String id) {
       // repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<SubstanceSearchCount> newUpdateEvent(SubstanceSearchCount updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<SubstanceSearchCount> newCreationEvent(SubstanceSearchCount createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(SubstanceSearchCount entity) {
        return entity.substanceId;
    }

    @Override
    protected List<SubstanceSearchCount> fromNewJsonList(JsonNode list) throws IOException {
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
    protected SubstanceSearchCount fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, SubstanceSearchCount.class);
    }

    @Override
    protected List<SubstanceSearchCount> fromUpdatedJsonList(JsonNode list) throws IOException {
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
    protected JsonNode toJson(SubstanceSearchCount application) throws IOException {
        return objectMapper.valueToTree(application);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<SubstanceSearchCount> get(String id) {
        SubstanceSearchCount searchCount = findSearchCountBySubstanceUuid(id);
        return Optional.ofNullable(searchCount);
       // return repository.findById(id);
    }

    @Override
    public Optional<SubstanceSearchCount> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        SubstanceSearchCount searchCount = findSearchCountBySubstanceUuid(someKindOfId);
        return Optional.ofNullable(searchCount);
       // return repository.findById(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<SubstanceSearchCount> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().substanceId);
        }
        return Optional.empty();
    }

    public SubstanceSearchCount findSearchCountBySubstanceUuid(String substanceUuid) {
        SubstanceSearchCount searchCount = new SubstanceSearchCount();
        List<SubstanceSearchCount> list = repository.findSearchCountBySubstanceUuid(substanceUuid);

        String appCountConcat = "";
        String provenance = "GSRS";

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                searchCount = list.get(i);
                if ((searchCount.provenance != null) && (searchCount.provenance.equalsIgnoreCase("Integrity"))) {
                    provenance = "Integrity";
                }
                if (searchCount.appCount > 0) {
                    if (appCountConcat != null && appCountConcat.length() > 0) {
                        appCountConcat = appCountConcat + "<br>";
                    }
                    appCountConcat = appCountConcat + searchCount.center + " " + provenance + ": "
                            + searchCount.appCount;
                } else {
                    appCountConcat = "0";
                }
            }

            if (list.size() > 0) {
                searchCount = list.get(0);
                searchCount.appCountConcat = appCountConcat;
            }
        }
        // If list size is 0, set count to "0"
        if (list.size() == 0) {
            searchCount.appCountConcat = "0";
        }

        return searchCount;
    }
}
