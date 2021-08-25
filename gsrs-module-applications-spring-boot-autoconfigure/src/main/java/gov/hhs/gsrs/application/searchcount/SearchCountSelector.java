package gov.hhs.gsrs.application.searchcount;

import gov.hhs.gsrs.application.searchcount.controllers.SearchCountController;
import gov.hhs.gsrs.application.searchcount.searcher.LegacySearchCountSearcher;
import gov.hhs.gsrs.application.searchcount.services.SearchCountEntityService;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class SearchCountSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                SearchCountEntityService.class.getName(),
                LegacySearchCountSearcher.class.getName(),
                SearchCountController.class.getName()
        };
    }
}
