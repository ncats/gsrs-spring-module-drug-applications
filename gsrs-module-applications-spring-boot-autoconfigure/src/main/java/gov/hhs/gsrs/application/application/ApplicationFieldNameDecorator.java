package gov.hhs.gsrs.application.application;

import ix.core.FieldNameDecorator;

import java.util.HashMap;
import java.util.Map;

public class ApplicationFieldNameDecorator implements FieldNameDecorator{

	private static final Map<String,String> displayNames;

	static{
			Map<String, String> m = new HashMap<>();

		    m.put("root_appType" , "Application Type");
			m.put("root_appNumber" , "Application Number");
		    m.put("root_center" , "Center");
		    m.put("root_provenance" , "Provenance");
		    m.put("root_title" , "Title");
		    m.put("root_sponsorName" , "Sponsor Name");
		    m.put("root_status" , "Application Status");
		    m.put("root_substanceKey", "Substance Key");
		    m.put("root_Ingredient Name" , "Ingredient Name");
			m.put("root_divisionClassDesc" , "Division Class Description");
			m.put("root_applicationIndicationList_indication" , "Indication");
			m.put("root_applicationProductList_applicationProductNameList_productName" , "Product Name");
			m.put("root_applicationProductList_applicationIngredientList_applicantIngredName", "Applicant Ingredient Name");
			m.put("root_applicationProductList_applicationIngredientList_unit" , "Unit");

			displayNames = m;
	}

	@Override
	public String getDisplayName(String field) {
		String fdisp = displayNames.get(field);
		if (fdisp == null) {
			return field;
		}
		return fdisp;
	}

}
