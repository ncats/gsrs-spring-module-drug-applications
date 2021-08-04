package gov.nih.ncats.application.application.processors;

import gov.nih.ncats.application.application.controllers.ApplicationController;
import gov.nih.ncats.application.application.models.Application;
import ix.core.EntityProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ApplicationProcessor implements EntityProcessor<Application> {

    @Autowired
    public ApplicationController applicationController;

    @Override
    public Class<Application> getEntityClass() {
        return Application.class;
    }

    @Override
    public void prePersist(final Application obj) {
    }

    @Override
    public void preUpdate(Application obj) {
    }

    @Override
    public void preRemove(Application obj) {
    }

    @Override
    public void postLoad(Application obj) throws FailProcessingException {
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

