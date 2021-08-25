package gov.hhs.gsrs.application.searchcount.searcher;

import gov.hhs.gsrs.application.searchcount.models.SubstanceSearchCount;
import gov.hhs.gsrs.application.searchcount.repositories.SearchCountRepository;

import gsrs.legacy.LegacyGsrsSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacySearchCountSearcher extends LegacyGsrsSearchService<SubstanceSearchCount> {

    @Autowired
    public LegacySearchCountSearcher(SearchCountRepository repository) {
        super(SubstanceSearchCount.class, repository);
    }
}
