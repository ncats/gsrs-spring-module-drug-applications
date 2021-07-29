package gov.nih.ncats.application.searchcount;

import gov.nih.ncats.application.searchcount.controllers.SearchCountController;
import gov.nih.ncats.application.searchcount.searcher.LegacySearchCountSearcher;
import gov.nih.ncats.application.searchcount.services.SearchCountEntityService;

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
