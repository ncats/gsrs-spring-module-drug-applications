package gov.nih.ncats.application;

import gov.nih.ncats.application.model.Application;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.GsrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyApplicationSearcher extends LegacyGsrsSearchService<Application> {

    @Autowired
    public LegacyApplicationSearcher(ApplicationRepository repository) {
        super(Application.class, repository);
    }
}
