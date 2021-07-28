package gov.nih.ncats.application.application.searcher;

import gov.nih.ncats.application.application.models.Application;
import gov.nih.ncats.application.application.repositories.ApplicationRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyApplicationSearcher extends LegacyGsrsSearchService<Application> {

    @Autowired
    public LegacyApplicationSearcher(ApplicationRepository repository) {
        super(Application.class, repository);
    }
}
