package gov.hhs.gsrs.application.searchcount.processors;

import gov.hhs.gsrs.application.searchcount.controllers.SearchCountController;
import gov.hhs.gsrs.application.searchcount.models.SubstanceSearchCount;

import ix.core.EntityProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SearchCountProcessor implements EntityProcessor<SubstanceSearchCount> {

    @Autowired
    public SearchCountController applicationController;

    @Override
    public Class<SubstanceSearchCount> getEntityClass() {
        return SubstanceSearchCount.class;
    }

    @Override
    public void prePersist(final SubstanceSearchCount obj) {
    }

    @Override
    public void preUpdate(SubstanceSearchCount obj) {
    }

    @Override
    public void preRemove(SubstanceSearchCount obj) {
    }

    @Override
    public void postLoad(SubstanceSearchCount obj) throws FailProcessingException {
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

