package gov.nih.ncats.application.searchcount.searcher;

import gov.nih.ncats.application.applicationdarrts.models.ApplicationDarrts;
import gov.nih.ncats.application.searchcount.models.*;
import gov.nih.ncats.application.searchcount.repositories.SearchCountRepository;

import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.GsrsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacySearchCountSearcher extends LegacyGsrsSearchService<SubstanceSearchCount> {

    @Autowired
    public LegacySearchCountSearcher(SearchCountRepository repository) {
        super(SubstanceSearchCount.class, repository);
    }
}
