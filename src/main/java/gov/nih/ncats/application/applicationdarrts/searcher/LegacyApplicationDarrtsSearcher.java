package gov.nih.ncats.application.applicationdarrts.searcher;

import gov.nih.ncats.application.applicationdarrts.models.*;
import gov.nih.ncats.application.applicationdarrts.repositories.ApplicationDarrtsRepository;

import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.GsrsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyApplicationDarrtsSearcher extends LegacyGsrsSearchService<ApplicationDarrts> {

    @Autowired
    public LegacyApplicationDarrtsSearcher(ApplicationDarrtsRepository repository) {
        super(ApplicationDarrts.class, repository);
    }
}
