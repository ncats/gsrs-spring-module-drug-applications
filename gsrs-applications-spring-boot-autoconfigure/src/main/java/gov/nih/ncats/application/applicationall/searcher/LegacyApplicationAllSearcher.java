package gov.nih.ncats.application.applicationall.searcher;

import gov.nih.ncats.application.applicationall.models.ApplicationAll;
import gov.nih.ncats.application.applicationall.repositories.ApplicationAllRepository;
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
