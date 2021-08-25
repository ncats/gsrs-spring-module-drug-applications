package gov.hhs.gsrs.application.autoconfigure;

import gov.hhs.gsrs.application.SubstanceModuleService;
import gov.hhs.gsrs.application.searchcount.repositories.SearchCountRepository;
import gov.hhs.gsrs.application.searchcount.searcher.LegacySearchCountSearcher;
import gov.hhs.gsrs.application.searchcount.services.SearchCountEntityService;

import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableGsrsJpaEntities
@EnableGsrsApi
@Configuration
@Import({
        SubstanceModuleService.class,
        //searchcount
        SearchCountEntityService.class, LegacySearchCountSearcher.class}

)
public class GsrsApplicationsAutoConfiguration {
}
