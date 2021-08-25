package gov.hhs.gsrs.application.applicationdarrts;

import gov.hhs.gsrs.application.applicationdarrts.controllers.ApplicationDarrtsController;
import gov.hhs.gsrs.application.applicationdarrts.searcher.LegacyApplicationDarrtsSearcher;
import gov.hhs.gsrs.application.applicationdarrts.services.ApplicationDarrtsEntityService;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class DarrtsSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                ApplicationDarrtsEntityService.class.getName(),
                LegacyApplicationDarrtsSearcher.class.getName(),
                ApplicationDarrtsController.class.getName()
        };
    }
}
