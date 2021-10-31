package gov.hhs.gsrs.application.applicationall.indexers;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gov.hhs.gsrs.application.applicationall.models.AppIngredientAll;
import gov.hhs.gsrs.application.applicationall.models.ApplicationAll;
import gov.hhs.gsrs.application.applicationall.models.ProductSrsAll;
import gsrs.DefaultDataSourceConfig;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import ix.ginas.models.v1.Substance;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationSubstanceIndexValueMaker implements IndexValueMaker<ApplicationAll> {

	@PersistenceContext(unitName =  DefaultDataSourceConfig.NAME_ENTITY_MANAGER)
	public EntityManager entityManager;

	@Override
	public Class<ApplicationAll> getIndexedEntityClass() {
		return ApplicationAll.class;
	}

    @Override
    public void createIndexableValues(ApplicationAll application, Consumer<IndexableValue> consumer) {
		try {
			String result = "HAS_NO_INGREDIENT";
			for (ProductSrsAll p : application.applicationProductList) {
				for (AppIngredientAll ing : p.applicationIngredientList) {
					if (ing != null) {
						if (ing.substanceKey != null) {
							String subKey = ing.substanceKey;

							//Get Substance Object by Substance Key
							Query query = entityManager.createQuery("SELECT s FROM Substance s JOIN s.codes c WHERE c.type = 'PRIMARY' and c.code=:subKey");
							query.setParameter("subKey", subKey);
							Substance s = (Substance) query.getSingleResult();

							if (s != null) {
								if (s.uuid != null) {
									result = s.uuid.toString();
									// consumer.accept(IndexableValue.simpleFacetStringValue("entity_link_substances", result));
								    consumer.accept(IndexableValue.simpleStringValue("entity_link_substances", result));
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
		    log.warn("Error indexing application:" + application.fetchKey(), e);
//			e.printStackTrace();
		}
	}
}
