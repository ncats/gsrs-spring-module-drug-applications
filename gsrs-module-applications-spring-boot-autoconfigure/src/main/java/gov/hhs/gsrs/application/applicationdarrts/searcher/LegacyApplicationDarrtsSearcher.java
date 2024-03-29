package gov.hhs.gsrs.application.applicationdarrts.searcher;

import gov.hhs.gsrs.application.applicationdarrts.models.ApplicationDarrts;
import gov.hhs.gsrs.application.applicationdarrts.repositories.ApplicationDarrtsRepository;

import gsrs.legacy.LegacyGsrsSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyApplicationDarrtsSearcher extends LegacyGsrsSearchService<ApplicationDarrts> {

    @Autowired
    public LegacyApplicationDarrtsSearcher(ApplicationDarrtsRepository repository) {
        super(ApplicationDarrts.class, repository);
    }
}
