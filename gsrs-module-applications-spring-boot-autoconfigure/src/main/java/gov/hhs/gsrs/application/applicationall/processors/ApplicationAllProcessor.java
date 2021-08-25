package gov.hhs.gsrs.application.applicationall.processors;

import gov.hhs.gsrs.application.applicationall.controllers.ApplicationAllController;
import gov.hhs.gsrs.application.applicationall.models.ApplicationAll;

import ix.core.EntityProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ApplicationAllProcessor implements EntityProcessor<ApplicationAll> {

    @Autowired
    public ApplicationAllController applicationController;

    @Override
    public Class<ApplicationAll> getEntityClass() {
        return ApplicationAll.class;
    }

    @Override
    public void prePersist(final ApplicationAll obj) {
    }

    @Override
    public void preUpdate(ApplicationAll obj) {
    }

    @Override
    public void preRemove(ApplicationAll obj) {
    }

    @Override
    public void postLoad(ApplicationAll obj) throws FailProcessingException {
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

