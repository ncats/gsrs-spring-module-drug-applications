package gov.nih.ncats.application.validators;

import gov.nih.ncats.application.model.Application;
import gov.nih.ncats.application.ApplicationRepository;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DuplicateTitleValidator implements ValidatorPlugin<Application> {
    @Autowired
    private ApplicationRepository repository;

    @Override
    public boolean supports(Application newValue, Application oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return methodType != ValidatorConfig.METHOD_TYPE.BATCH;
    }

    @Override
    public void validate(Application objnew, Application objold, ValidatorCallback callback) {
        /*
        Optional<Application> found = repository.findByTitle(objnew.getTitle());
        if(found.isPresent()){
            if(objold == null || !objnew.getId().equals(found.get().getId())){
                //this is a new book or a book with different book id: can't have duplicate titles
                //TODO what about second editions?
                //TODO what about same title different authors?
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("duplicate title '" + objnew.getTitle() +"'"));
            }
        }
        */
    }
}
