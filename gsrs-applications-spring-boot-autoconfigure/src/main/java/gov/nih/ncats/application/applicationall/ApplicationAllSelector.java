package gov.nih.ncats.application.applicationall;

import gov.nih.ncats.application.applicationall.controllers.ApplicationAllController;
import gov.nih.ncats.application.applicationall.searcher.LegacyApplicationAllSearcher;
import gov.nih.ncats.application.applicationall.services.ApplicationAllEntityService;
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
