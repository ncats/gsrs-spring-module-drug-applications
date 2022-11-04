package gov.hhs.gsrs.application.application.indexers;

import gov.hhs.gsrs.application.ApplicationDataSourceConfig;
import gov.hhs.gsrs.application.application.models.Application;
import gov.hhs.gsrs.application.application.models.ApplicationIngredient;
import gov.hhs.gsrs.application.application.models.ApplicationProduct;

import gsrs.DefaultDataSourceConfig;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import ix.ginas.models.v1.Substance;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.function.Consumer;

public class ApplicationIngredientIndexValueMaker implements IndexValueMaker<Application> {

    @PersistenceContext(unitName = DefaultDataSourceConfig.NAME_ENTITY_MANAGER)
    public EntityManager entityManager;

    @Override
    public Class<Application> getIndexedEntityClass() {
        return Application.class;
    }

    @Override
    public void createIndexableValues(Application application, Consumer<IndexableValue> consumer) {
        try {
            String result = "HAS_NO_INGREDIENT";
            // Facet: Ingredient Name   (Note: This includes all the Ingredient Names)
            for (ApplicationProduct p : application.applicationProductList) {
                for (ApplicationIngredient ing : p.applicationIngredientList) {
                    if (ing != null) {
                        if (ing.substanceKey != null) {
                            String subKey = ing.substanceKey;
                            //Get Substance Object
                            Query query = entityManager.createQuery("SELECT s FROM Substance s JOIN s.codes c WHERE c.type = 'PRIMARY' and c.code=:subKey");
                            query.setParameter("subKey", subKey);
                            Substance s = (Substance) query.getSingleResult();

                            if (s != null) {
                                s.names.forEach(nameObj -> {
                                    if (nameObj.name != null) {
                                        consumer.accept(IndexableValue.simpleFacetStringValue("Ingredient Name", nameObj.name).suggestable().setSortable());
                                    }
                                });
                                // Facet: "Ingredient Name (Preferred)"
                                if (s.getName() != null) {
                                    consumer.accept(IndexableValue.simpleFacetStringValue("Ingredient Name (Preferred)", s.getName()).suggestable().setSortable());
                                }
                            }
                        }
                    }
                }
            }

            if ((application.appType != null) && (application.appNumber != null)) {
                // String padAppNumber =("000000" + application.appNumber).substring(("000000" + application.appNumber).length()-6);
                String padAppNumber = leftPadding(application.appNumber);
                // root_applicationType_<NDA/IND/MF>:<APPL_NUMBER>
                consumer.accept(IndexableValue.simpleFacetStringValue("root_applicationType_" + application.appType, padAppNumber));

                // root_applicationID:<NDA/IND/MF><APPL_NUMBER>
                consumer.accept(IndexableValue.simpleFacetStringValue("root_applicationID", application.appType + padAppNumber).suggestable());

                // root_applicationID:<NDA/IND/MF> <APPL_NUMBER>
                consumer.accept(IndexableValue.simpleFacetStringValue("root_applicationID", application.appType + " " + padAppNumber));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String leftPadding(String value) {
        if (value != null) {
            return String.format("%06d", Integer.parseInt(value));
        }
        return value;
    }
}
