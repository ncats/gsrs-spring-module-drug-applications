package gov.nih.ncats.application.validators;

import gov.nih.ncats.application.models.Application;

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

        if ((objnew.center == null) || (objnew.center.isEmpty())) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("Center is required"));
        }

        if (objnew.appType == null || objnew.appType.isEmpty()) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("Application Type is required."));
        }

        if (objnew.appNumber == null || objnew.appNumber.isEmpty()) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("Application Number is required."));
        }
    }
}
