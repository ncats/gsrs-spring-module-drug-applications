package gov.hhs.gsrs.application.application;

import gov.hhs.gsrs.application.application.controllers.ApplicationController;
import gov.hhs.gsrs.application.application.searcher.LegacyApplicationSearcher;
import gov.hhs.gsrs.application.application.services.ApplicationEntityService;

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
