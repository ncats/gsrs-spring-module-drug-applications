package gov.hhs.gsrs.application.applicationall.searcher;

import gov.hhs.gsrs.application.applicationall.models.ApplicationAll;
import gov.hhs.gsrs.application.applicationall.repositories.ApplicationAllRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyApplicationAllSearcher extends LegacyGsrsSearchService<ApplicationAll> {

    @Autowired
    public LegacyApplicationAllSearcher(ApplicationAllRepository repository) {
        super(ApplicationAll.class, repository);
    }
}
