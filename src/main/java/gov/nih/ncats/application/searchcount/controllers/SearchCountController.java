package gov.nih.ncats.application.searchcount.controllers;

import gov.nih.ncats.application.searchcount.models.*;
import gov.nih.ncats.application.searchcount.services.*;
import gov.nih.ncats.application.searchcount.searcher.LegacySearchCountSearcher;

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
