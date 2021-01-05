package gov.nih.ncats.application.validators;

import gov.nih.ncats.application.model.Application;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;

public class RequiredFieldNonNullValidator implements ValidatorPlugin<Application> {

    @Override
    public boolean supports(Application newValue, Application oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return true;
    }

    @Override
    public void validate(Application objnew, Application objold, ValidatorCallback callback) {

        if(objnew.getCenter() == null){
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("Center is required"));
        }
        /*
        if(objnew.getAppType() == null){
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("null Application Type"));
        }else if(objnew.getNumber().trim().isEmpty()){
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE("blank Application Number"));
        }
         */
    }
}
