package gov.nih.ncats.application.entityProcessor;

import gov.nih.ncats.application.model.Application;
import ix.core.EntityProcessor;
import org.springframework.stereotype.Component;

//@Component
public class MyEntityProcessor implements EntityProcessor<Application> {
    @Override
    public Class<Application> getEntityClass() {
        return Application.class;
    }

    @Override
    public void postLoad(Application obj) throws FailProcessingException {
        System.out.println("post Load Application " + obj);
    }
}
