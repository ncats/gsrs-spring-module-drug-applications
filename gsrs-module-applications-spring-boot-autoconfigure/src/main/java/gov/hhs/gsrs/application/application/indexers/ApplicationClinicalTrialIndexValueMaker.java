package gov.hhs.gsrs.application.application.indexers;

import gov.hhs.gsrs.application.ApplicationDataSourceConfig;
import gov.hhs.gsrs.application.application.models.Application;
import gov.hhs.gsrs.application.application.models.ApplicationIngredient;
import gov.hhs.gsrs.application.application.models.ApplicationProduct;

import gov.hhs.gsrs.application.application.models.additional.ClinicalTrialApplication;
import gsrs.DefaultDataSourceConfig;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import ix.ginas.models.v1.Substance;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;
import java.util.function.Consumer;

public class ApplicationClinicalTrialIndexValueMaker implements IndexValueMaker<Application> {

    public static final String HAS_CLINICALTRIAL = "Has Clinical Trials";
    public static final String HAS_NO_CLINICALTRIAL = "Has No Clinical Trial";

    @PersistenceContext(unitName = ApplicationDataSourceConfig.NAME_ENTITY_MANAGER)
    public EntityManager entityManager;

    @Override
    public Class<Application> getIndexedEntityClass() {
        return Application.class;
    }

    @Override
    public void createIndexableValues(Application application, Consumer<IndexableValue> consumer) {
        try {
            if (application.id != null) {
                //Get Clinical Trial Application Object
                Query query = entityManager.createQuery("SELECT c FROM ClinicalTrialApplication c WHERE c.applicationId =:applicationId");
                query.setParameter("applicationId", application.id);
                List<ClinicalTrialApplication> clinicalList = query.getResultList();

                if (clinicalList.size() > 0) {
                    consumer.accept(IndexableValue.simpleFacetStringValue("Has Clinical Trials", HAS_CLINICALTRIAL));

                    clinicalList.forEach(clinicalObj -> {
                        if (clinicalObj != null) {
                            consumer.accept(IndexableValue.simpleFacetStringValue("NCT Number", clinicalObj.nctNumber).suggestable().setSortable());
                        }
                    });
                }
                else {
                    consumer.accept(IndexableValue.simpleFacetStringValue("Has Clinical Trials", HAS_NO_CLINICALTRIAL));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
