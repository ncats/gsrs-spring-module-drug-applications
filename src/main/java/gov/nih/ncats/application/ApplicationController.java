package gov.nih.ncats.application;

import gsrs.controller.*;
import gov.nih.ncats.application.model.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

@GsrsRestApiController(context = "application", idHelper = IdHelpers.NUMBER)
public class ApplicationController extends EtagLegacySearchEntityController<Application, Long> {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LegacyApplicationSearcher legacyApplicationSearcher;
    private Object Application;

    public ApplicationController() {
        super("application", IdHelpers.NUMBER);
    }

    @Override
    protected Application fromNewJson(JsonNode json) throws IOException {
        //remove id?
        return objectMapper.convertValue(json, Application.class);
    }

    @Override
    protected List<Application> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected Application fromUpdatedJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Application.class);
    }

    @Override
    protected List<Application> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(Application application) throws IOException {
        return objectMapper.valueToTree(application);
    }

    @Override
    protected Application create(Application application) {
        return applicationRepository.saveAndFlush(application);
    }

    @Override
    protected long count() {
        return applicationRepository.count();
    }

    @Override
    protected Optional<Application> get(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    protected Long parseIdFromString(String idAsString) {
        return Long.parseLong(idAsString);
    }

    @Override
    protected Optional<Application> flexLookup(String someKindOfId) {
        return applicationRepository.findByTitle(someKindOfId);
    }

    @Override
    protected Class<Application> getEntityClass() {
        return Application.class;
    }

    @Override
    protected Page page(long offset, long numOfRecords, Sort sort) {
        return applicationRepository.findAll(new OffsetBasedPageRequest(offset, numOfRecords, sort));
    }

    @Override
    protected void delete(Long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    protected Long getIdFrom(Application entity) {
        return entity.getId();
    }

    @Override
    protected Application update(Application application) {
       return applicationRepository.saveAndFlush(application);
    }

    @Override
    protected LegacyApplicationSearcher getlegacyGsrsSearchService() {
        return legacyApplicationSearcher;
    }

}
