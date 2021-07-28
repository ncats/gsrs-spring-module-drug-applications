package gov.nih.ncats.application.autoconfigure;

import gov.nih.ncats.application.SubstanceModuleService;
import gov.nih.ncats.application.searchcount.repositories.SearchCountRepository;
import gov.nih.ncats.application.searchcount.searcher.LegacySearchCountSearcher;
import gov.nih.ncats.application.searchcount.services.SearchCountEntityService;
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
        SearchCountEntityService.class, LegacySearchCountSearcher.class, SearchCountRepository.class}

)
public class GsrsApplicationsAutoConfiguration {
}
