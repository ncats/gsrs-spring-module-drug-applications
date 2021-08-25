package gov.hhs.gsrs.application.application.exporters;

import com.fasterxml.jackson.databind.JsonNode;
import gov.hhs.gsrs.application.application.controllers.ApplicationController;
import gov.hhs.gsrs.application.application.models.*;
import ix.ginas.exporters.*;

import java.io.IOException;
import java.util.*;

enum AppDefaultColumns implements Column {
    ID,
    APP_TYPE,
    APP_NUMBER,
    CENTER,
    SPONSOR_NAME,
    APP_STATUS,
    APP_SUB_TYPE,
    DIVISION_CLASS_DESC,
    PRODUCT_NAME,
    PROVENANCE,
    SUBSTANCE_KEY,
    APPROVAL_ID,
    SUBSTANCE_NAME,
    ACTIVE_MOIETY,
    INGREDIENT_TYPE,
    INDICATION
}

public class ApplicationExporter implements Exporter<Application> {

    private final Spreadsheet spreadsheet;

    private int row=1;

    private final List<ColumnValueRecipe<Application>> recipeMap;

    private static ApplicationController applicationController;

    private static StringBuilder substanceApprovalIdSB;
    private static StringBuilder substanceActiveMoietySB;

    private ApplicationExporter(Builder builder, ApplicationController applicationController){

        this.applicationController = applicationController;
        substanceApprovalIdSB = new StringBuilder();
        substanceActiveMoietySB = new StringBuilder();

        this.spreadsheet = builder.spreadsheet;
        this.recipeMap = builder.columns;

        int j=0;
        Spreadsheet.SpreadsheetRow header = spreadsheet.getRow(0);
        for(ColumnValueRecipe<Application> col : recipeMap){
            j+= col.writeHeaderValues(header, j);
        }
    }

    @Override
    public void export(Application s) throws IOException {
        Spreadsheet.SpreadsheetRow row = spreadsheet.getRow( this.row++);

        int j=0;
        for(ColumnValueRecipe<Application> recipe : recipeMap){
            j+= recipe.writeValuesFor(row, j, s);
        }
    }

    @Override
    public void close() throws IOException {
        spreadsheet.close();
    }

    private static Map<Column, ColumnValueRecipe<Application>> DEFAULT_RECIPE_MAP;

    static{

        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();

        /*
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create( AppDefaultColumns.SUBSTANCE_NAME ,(s, cell) ->{
        	StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.SUBSTANCE_NAME);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.UNII, SingleColumnValueRecipe.create( AppDefaultColumns.UNII ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.UNII);
            cell.writeString(sb.toString());
        }));
        */

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create(AppDefaultColumns.SUBSTANCE_NAME ,(s, cell) ->{
            StringBuilder sb = getIngredientName(s);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APPROVAL_ID, SingleColumnValueRecipe.create(AppDefaultColumns.APPROVAL_ID ,(s, cell) ->{
         cell.writeString(substanceApprovalIdSB.toString());
       }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create( AppDefaultColumns.SUBSTANCE_KEY ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.SUBSTANCE_KEY);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.INGREDIENT_TYPE, SingleColumnValueRecipe.create( AppDefaultColumns.INGREDIENT_TYPE ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.INGREDIENT_TYPE);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.CENTER, SingleColumnValueRecipe.create( AppDefaultColumns.CENTER ,(s, cell) -> cell.writeString(s.center)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_TYPE, SingleColumnValueRecipe.create( AppDefaultColumns.APP_TYPE ,(s, cell) -> cell.writeString(s.appType)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_NUMBER, SingleColumnValueRecipe.create( AppDefaultColumns.APP_NUMBER ,(s, cell) -> cell.writeString(s.appNumber)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.PRODUCT_NAME, SingleColumnValueRecipe.create( AppDefaultColumns.PRODUCT_NAME ,(s, cell) ->{
            StringBuilder sb = getProductDetails(s, AppDefaultColumns.PRODUCT_NAME);
            cell.writeString(sb.toString());
        }));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SPONSOR_NAME, SingleColumnValueRecipe.create( AppDefaultColumns.SPONSOR_NAME ,(s, cell) -> cell.writeString(s.sponsorName)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_STATUS, SingleColumnValueRecipe.create( AppDefaultColumns.APP_STATUS ,(s, cell) -> cell.writeString(s.status)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_SUB_TYPE, SingleColumnValueRecipe.create( AppDefaultColumns.APP_SUB_TYPE ,(s, cell) -> cell.writeString(s.appSubType)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.DIVISION_CLASS_DESC, SingleColumnValueRecipe.create( AppDefaultColumns.DIVISION_CLASS_DESC ,(s, cell) -> cell.writeString(s.divisionClassDesc)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.PROVENANCE, SingleColumnValueRecipe.create( AppDefaultColumns.PROVENANCE ,(s, cell) -> cell.writeString(s.provenance)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.INDICATION, SingleColumnValueRecipe.create( AppDefaultColumns.INDICATION ,(s, cell) ->{
            StringBuilder sb = getIndicationDetails(s);
            cell.writeString(sb.toString());
        }));
    }

    private static StringBuilder getProductDetails(Application s, AppDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();

        if(s.applicationProductList.size() > 0){
            List<ApplicationProduct> prodList = s.applicationProductList;

            for(ApplicationProduct prod : prodList){

                for (ApplicationProductName prodName : prod.applicationProductNameList) {
                    if(sb.length()!=0){
                        sb.append("|");
                    }
                    switch (fieldName) {
                        case PRODUCT_NAME:
                            sb.append((prodName.productName != null) ? prodName.productName : "(No Product Name)");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return sb;
    }

    private static StringBuilder getIngredientName(Application s) {
        StringBuilder sb = new StringBuilder();
        substanceApprovalIdSB.setLength(0);
        String substanceName = null;
        String approvalId = null;

        try {
            if (s.applicationProductList.size() > 0) {
                List<ApplicationProduct> prodList = s.applicationProductList;

                for (ApplicationProduct prod : prodList) {

                    for (ApplicationIngredient ingred : prod.applicationIngredientList) {
                        if (sb.length() != 0) {
                            sb.append("|");
                        }

                        if (substanceApprovalIdSB.length() != 0) {
                            substanceApprovalIdSB.append("|");
                        }
                        if (substanceActiveMoietySB.length() != 0) {
                            substanceActiveMoietySB.append("|");
                        }


                        // Get Substance Details
                        if (ingred.substanceKey != null) {
                            if (applicationController != null) {
                                JsonNode subJson = applicationController.injectSubstanceBySubstanceKey(ingred.substanceKey);

                                if (subJson != null) {
                                    substanceName = subJson.path("_name").textValue();
                                    approvalId = subJson.path("approvalID").textValue();

                                    // Get Substance Name
                                    sb.append((substanceName != null) ? substanceName : "(No Ingredient Name)");

                                    // Storing in static variable so do not have to call the same Substance API twice just to get
                                    // approval Id.
                                    substanceApprovalIdSB.append((approvalId != null) ? approvalId : "(No Approval ID)");
                                }
                            }
                        } else {
                            sb.append("(No Ingredient Name)");
                            substanceApprovalIdSB.append("(No Approval ID)");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb;
    }

    private static StringBuilder getIngredientDetails(Application s, AppDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();

        try {
            if (s.applicationProductList.size() > 0) {
                List<ApplicationProduct> prodList = s.applicationProductList;

                for (ApplicationProduct prod : prodList) {

                    for (ApplicationIngredient ingred : prod.applicationIngredientList) {
                        if (sb.length() != 0) {
                            sb.append("|");
                        }

                        try {
                            switch (fieldName) {
                                case SUBSTANCE_KEY:
                                    sb.append((ingred.substanceKey != null) ? ingred.substanceKey : "(No Substance Key)");
                                    break;
                                case INGREDIENT_TYPE:
                                    sb.append((ingred.ingredientType != null) ? ingred.ingredientType : "(No Ingredient Type)");
                                    break;
                                default:
                                    break;
                            }

                        } catch (Exception ex) {
                            System.out.println("*** Error Occured in ApplicationExporter.java for Substance Code : " + ingred.substanceKey);
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb;
    }

    private static StringBuilder getIndicationDetails(Application s) {
        StringBuilder sb = new StringBuilder();

        if (s.applicationIndicationList.size() > 0){
            List<ApplicationIndication> indList = s.applicationIndicationList;
            for(ApplicationIndication ind : indList){
                if(sb.length()!=0){
                    sb.append("|");
                }
                sb.append((ind.indication != null) ? ind.indication : "");
            }
        }
        return sb;
    }

    /**
     * Builder class that makes a SpreadsheetExporter.  By default, the default columns are used
     * but these may be modified using the add/remove column methods.
     *
     */
    public static class Builder{
        private final List<ColumnValueRecipe<Application>> columns = new ArrayList<>();
        private final Spreadsheet spreadsheet;

        private boolean publicOnly = false;

        /**
         * Create a new Builder that uses the given Spreadsheet to write to.
         * @param spreadSheet the {@link Spreadsheet} object that will be written to by this exporter. can not be null.
         *
         * @throws NullPointerException if spreadsheet is null.
         */
        public Builder(Spreadsheet spreadSheet){
            Objects.requireNonNull(spreadSheet);
            this.spreadsheet = spreadSheet;

            for(Map.Entry<Column, ColumnValueRecipe<Application>> entry : DEFAULT_RECIPE_MAP.entrySet()){
            	columns.add(entry.getValue());
            }
        }

        public Builder addColumn(Column column, ColumnValueRecipe<Application> recipe){
        	return addColumn(column.name(), recipe);
        }

        public Builder addColumn(String columnName, ColumnValueRecipe<Application> recipe){
        	Objects.requireNonNull(columnName);
            Objects.requireNonNull(recipe);
            columns.add(recipe);

            return this;
        }

        public Builder renameColumn(Column oldColumn, String newName){
            return renameColumn(oldColumn.name(), newName);
        }
        
        public Builder renameColumn(String oldName, String newName){
            //use iterator to preserve order
            ListIterator<ColumnValueRecipe<Application>> iter = columns.listIterator();
            while(iter.hasNext()){

                ColumnValueRecipe<Application> oldValue = iter.next();
                ColumnValueRecipe<Application> newValue = oldValue.replaceColumnName(oldName, newName);
                if(oldValue != newValue){
                   iter.set(newValue);
                }
            }
            return this;
        }

        public ApplicationExporter build(ApplicationController applicationController){
            return new ApplicationExporter(this, applicationController);
        }

        public Builder includePublicDataOnly(boolean publicOnly){
            this.publicOnly = publicOnly;
            return this;
        }

    }
}
