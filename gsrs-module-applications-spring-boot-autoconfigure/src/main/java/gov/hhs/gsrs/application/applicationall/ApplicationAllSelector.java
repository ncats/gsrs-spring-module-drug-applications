package gov.hhs.gsrs.application.applicationall;

import gov.hhs.gsrs.application.applicationall.controllers.ApplicationAllController;
import gov.hhs.gsrs.application.applicationall.searcher.LegacyApplicationAllSearcher;
import gov.hhs.gsrs.application.applicationall.services.ApplicationAllEntityService;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ApplicationAllSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                ApplicationAllEntityService.class.getName(),
                LegacyApplicationAllSearcher.class.getName(),
                ApplicationAllController.class.getName()
        };
    }
}
