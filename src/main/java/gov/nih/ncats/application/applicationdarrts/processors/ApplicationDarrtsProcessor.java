package gov.nih.ncats.application.applicationdarrts.processors;

import gov.nih.ncats.application.applicationdarrts.models.*;
import gov.nih.ncats.application.applicationdarrts.controllers.ApplicationDarrtsController;

import gsrs.springUtils.AutowireHelper;
import ix.core.EntityProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class ApplicationDarrtsProcessor implements EntityProcessor<ApplicationDarrts> {

    @Autowired
    public ApplicationDarrtsController applicationDarrtsController;

    @Override
    public Class<ApplicationDarrts> getEntityClass() {
        return ApplicationDarrts.class;
    }

    @Override
    public void prePersist(final ApplicationDarrts obj) {
    }

    @Override
    public void preUpdate(ApplicationDarrts obj) {
    }

    @Override
    public void preRemove(ApplicationDarrts obj) {
    }

    @Override
    public void postLoad(ApplicationDarrts obj) throws FailProcessingException {
        /*
        if(applicationController==null) {
            AutowireHelper.getInstance().autowire(this);
        }

        if (applicationController != null) {
            Optional<Application> objInjectSubstance = applicationController.injectSubstanceDetails(Optional.of(obj));
        }
         */
    }

}

