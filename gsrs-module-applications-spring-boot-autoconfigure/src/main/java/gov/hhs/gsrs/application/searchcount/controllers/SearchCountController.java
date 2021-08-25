package gov.hhs.gsrs.application.searchcount.controllers;

import gov.hhs.gsrs.application.searchcount.models.SubstanceSearchCount;
import gov.hhs.gsrs.application.searchcount.searcher.LegacySearchCountSearcher;
import gov.hhs.gsrs.application.searchcount.services.SearchCountEntityService;

import gsrs.autoconfigure.GsrsExportConfiguration;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.ETagRepository;
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
import java.util.Map;
import java.util.stream.Stream;

@ExposesResourceFor(SubstanceSearchCount.class)
@GsrsRestApiController(context = SearchCountEntityService.CONTEXT, idHelper = IdHelpers.STRING_NO_WHITESPACE)
public class SearchCountController extends EtagLegacySearchEntityController<SearchCountController, SubstanceSearchCount, String> {


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
    private SearchCountEntityService searchCountEntityService;

    @Autowired
    private LegacySearchCountSearcher legacySearchCountSearcher;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public GsrsEntityService<SubstanceSearchCount, String> getEntityService() {
        return searchCountEntityService;
    }

    @Override
    protected LegacyGsrsSearchService<SubstanceSearchCount> getlegacyGsrsSearchService() {
        return legacySearchCountSearcher;
    }

    @Override
    protected Stream<SubstanceSearchCount> filterStream(Stream<SubstanceSearchCount> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }

    @GetGsrsRestApiMapping("/searchcount/{substanceUuid}")
    public ResponseEntity<String> findSearchCountBySubstanceUuid(@PathVariable("substanceKey") String substanceUuid) throws Exception {
        SubstanceSearchCount searchCount = searchCountEntityService.findSearchCountBySubstanceUuid(substanceUuid);
        if (substanceUuid == null) {
            throw new IllegalArgumentException("There is no Substance Key provided");
        }
        return new ResponseEntity(searchCount, HttpStatus.OK);
    }
}
