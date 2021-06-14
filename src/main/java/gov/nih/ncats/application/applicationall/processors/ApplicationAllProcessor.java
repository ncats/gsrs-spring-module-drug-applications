package gov.nih.ncats.application.applicationall.processors;

import gov.nih.ncats.application.applicationall.models.*;
import gov.nih.ncats.application.applicationall.controllers.ApplicationAllController;

import gsrs.springUtils.AutowireHelper;
import ix.core.EntityProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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

