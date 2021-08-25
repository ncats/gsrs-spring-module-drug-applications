package gov.hhs.gsrs.application.application.searcher;

import gov.hhs.gsrs.application.application.models.Application;
import gov.hhs.gsrs.application.application.repositories.ApplicationRepository;

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
