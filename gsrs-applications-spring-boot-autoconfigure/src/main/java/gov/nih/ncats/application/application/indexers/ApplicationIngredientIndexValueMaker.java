package gov.nih.ncats.application.application.indexers;

import gov.nih.ncats.application.application.models.Application;
import gov.nih.ncats.application.application.models.ApplicationIngredient;
import gov.nih.ncats.application.application.models.ApplicationProduct;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import ix.ginas.models.v1.Substance;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.function.Consumer;

public class ApplicationIngredientIndexValueMaker implements IndexValueMaker<Application> {

	@Autowired
	public EntityManager entityManager;

	@Override
	public Class<Application> getIndexedEntityClass() {
		return Application.class;
	}

    @Override
    public void createIndexableValues(Application application, Consumer<IndexableValue> consumer) {
		try {
			String result = "HAS_NO_INGREDIENT";
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
								result = s.getName();
							}
						}
					}
					consumer.accept(IndexableValue.simpleFacetStringValue("Ingredient Name", result));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
