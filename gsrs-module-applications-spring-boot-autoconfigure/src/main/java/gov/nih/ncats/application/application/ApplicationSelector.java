package gov.nih.ncats.application.application;

import gov.nih.ncats.application.application.controllers.ApplicationController;
import gov.nih.ncats.application.application.searcher.LegacyApplicationSearcher;
import gov.nih.ncats.application.application.services.ApplicationEntityService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ApplicationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                ApplicationEntityService.class.getName(),
                LegacyApplicationSearcher.class.getName(),
                ApplicationController.class.getName()
        };
    }
}
