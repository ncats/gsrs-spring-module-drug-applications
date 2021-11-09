package gov.hhs.gsrs.application.applicationall.exporters;

import gov.hhs.gsrs.application.applicationall.controllers.ApplicationAllController;
import gov.hhs.gsrs.application.applicationall.models.*;

import ix.core.EntityFetcher;
import ix.ginas.exporters.*;

import com.fasterxml.jackson.databind.JsonNode;
import ix.ginas.models.v1.Substance;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.*;

enum AppAllDefaultColumns implements Column {
    ID,
    APP_TYPE,
    APP_NUMBER,
    CENTER,
    SPONSOR_NAME,
    APP_STATUS,
    APP_SUB_TYPE,
    DIVISION_CLASS_DESC,
    INDICATION,
    PRODUCT_NAME,
    PROVENANCE,
    SUBSTANCE_KEY,
    APPROVAL_ID,
    SUBSTANCE_NAME,
    ACTIVE_MOIETY,
    INGREDIENT_TYPE,
    FROM_TABLE
}

public class ApplicationAllExporter implements Exporter<ApplicationAll> {

    private final Spreadsheet spreadsheet;

    private int row=1;

    private final List<ColumnValueRecipe<ApplicationAll>> recipeMap;

    private static ApplicationAllController applicationController;
    private static EntityManager entityManager;

    private static StringBuilder substanceApprovalIdSB;
    private static StringBuilder substanceActiveMoietySB;

    private ApplicationAllExporter(Builder builder, ApplicationAllController applicationController, EntityManager entityManager){

        this.applicationController = applicationController;
        this.entityManager = entityManager;

        substanceApprovalIdSB = new StringBuilder();
        substanceActiveMoietySB = new StringBuilder();

        this.spreadsheet = builder.spreadsheet;
        this.recipeMap = builder.columns;

        int j=0;
        Spreadsheet.SpreadsheetRow header = spreadsheet.getRow(0);
        for(ColumnValueRecipe<ApplicationAll> col : recipeMap){
            j+= col.writeHeaderValues(header, j);
        }
    }

    @Override
    public void export(ApplicationAll s) throws IOException {
        Spreadsheet.SpreadsheetRow row = spreadsheet.getRow( this.row++);

        int j=0;
        for(ColumnValueRecipe<ApplicationAll> recipe : recipeMap){
            j+= recipe.writeValuesFor(row, j, s);
        }
    }

    @Override
    public void close() throws IOException {
        spreadsheet.close();
    }

    private static Map<Column, ColumnValueRecipe<ApplicationAll>> DEFAULT_RECIPE_MAP;

    static{

        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create(AppAllDefaultColumns.SUBSTANCE_NAME ,(s, cell) ->{
            StringBuilder sb = getIngredientName(s);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APPROVAL_ID, SingleColumnValueRecipe.create(AppAllDefaultColumns.APPROVAL_ID ,(s, cell) ->{
            cell.writeString(substanceApprovalIdSB.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create( AppAllDefaultColumns.SUBSTANCE_KEY ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppAllDefaultColumns.SUBSTANCE_KEY);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.INGREDIENT_TYPE, SingleColumnValueRecipe.create( AppAllDefaultColumns.INGREDIENT_TYPE ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppAllDefaultColumns.INGREDIENT_TYPE);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.CENTER, SingleColumnValueRecipe.create( AppAllDefaultColumns.CENTER ,(s, cell) -> cell.writeString(s.center)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_TYPE, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_TYPE ,(s, cell) -> cell.writeString(s.appType)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_NUMBER, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_NUMBER ,(s, cell) -> cell.writeString(s.appNumber)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.PRODUCT_NAME, SingleColumnValueRecipe.create( AppAllDefaultColumns.PRODUCT_NAME ,(s, cell) ->{
            StringBuilder sb = getProductDetails(s, AppAllDefaultColumns.PRODUCT_NAME);
            cell.writeString(sb.toString());
        }));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.SPONSOR_NAME, SingleColumnValueRecipe.create( AppAllDefaultColumns.SPONSOR_NAME ,(s, cell) -> cell.writeString(s.sponsorName)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_STATUS, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_STATUS ,(s, cell) -> cell.writeString(s.appStatus)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_SUB_TYPE, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_SUB_TYPE ,(s, cell) -> cell.writeString(s.appSubType)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.DIVISION_CLASS_DESC, SingleColumnValueRecipe.create( AppAllDefaultColumns.DIVISION_CLASS_DESC ,(s, cell) -> cell.writeString(s.divisionClassDesc)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.PROVENANCE, SingleColumnValueRecipe.create( AppAllDefaultColumns.PROVENANCE ,(s, cell) -> cell.writeString(s.provenance)));


        /*
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create( AppAllDefaultColumns.SUBSTANCE_NAME ,(s, cell) ->{
        	StringBuilder sb = getIngredientDetails(s, AppAllDefaultColumns.SUBSTANCE_NAME);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.UNII, SingleColumnValueRecipe.create( AppAllDefaultColumns.UNII ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppAllDefaultColumns.UNII);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.BDNUM, SingleColumnValueRecipe.create( AppAllDefaultColumns.BDNUM ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppAllDefaultColumns.BDNUM);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.INGREDIENT_TYPE, SingleColumnValueRecipe.create( AppAllDefaultColumns.INGREDIENT_TYPE ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppAllDefaultColumns.INGREDIENT_TYPE);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.CENTER, SingleColumnValueRecipe.create( AppAllDefaultColumns.CENTER ,(s, cell) -> cell.writeString(s.center)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_TYPE, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_TYPE ,(s, cell) -> cell.writeString(s.appType)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_NUMBER, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_NUMBER ,(s, cell) -> cell.writeString(s.appNumber)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.PRODUCT_NAME, SingleColumnValueRecipe.create( AppAllDefaultColumns.PRODUCT_NAME ,(s, cell) ->{
            StringBuilder sb = getProductDetails(s, AppAllDefaultColumns.PRODUCT_NAME);
            cell.writeString(sb.toString());
        }));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.SPONSOR_NAME, SingleColumnValueRecipe.create( AppAllDefaultColumns.SPONSOR_NAME ,(s, cell) -> cell.writeString(s.sponsorName)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_STATUS, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_STATUS ,(s, cell) -> cell.writeString(s.appStatus)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.APP_SUB_TYPE, SingleColumnValueRecipe.create( AppAllDefaultColumns.APP_SUB_TYPE ,(s, cell) -> cell.writeString(s.appSubType)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.DIVISION_CLASS_DESC, SingleColumnValueRecipe.create( AppAllDefaultColumns.DIVISION_CLASS_DESC ,(s, cell) -> cell.writeString(s.divisionClassDesc)));
        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.PROVENANCE, SingleColumnValueRecipe.create( AppAllDefaultColumns.PROVENANCE ,(s, cell) -> cell.writeString(s.provenance)));
        */

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.FROM_TABLE, SingleColumnValueRecipe.create( AppAllDefaultColumns.FROM_TABLE ,(s, cell) -> {
            String fromTable = null;
            if (s.fromTable != null) {
                if (s.fromTable.equalsIgnoreCase("SRS")) {
                    fromTable = "GSRS";
                } else if (s.fromTable.equalsIgnoreCase("Darrts")) {
                    fromTable = "Integrity/DARRTS";
                }
            }else {
                fromTable = "GSRS";
            }
            cell.writeString(fromTable);
        }));

        DEFAULT_RECIPE_MAP.put(AppAllDefaultColumns.INDICATION, SingleColumnValueRecipe.create( AppAllDefaultColumns.INDICATION ,(s, cell) ->{
            StringBuilder sb = getIndicationDetails(s);
            cell.writeString(sb.toString());
        }));

    }

    private static StringBuilder getProductDetails(ApplicationAll s, AppAllDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();


        if(s.applicationProductList.size() > 0){
            List<ProductSrsAll> prodList = s.applicationProductList;

            for(ProductSrsAll prod : prodList){

                for (ProductNameSrsAll prodName : prod.applicationProductNameList) {
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

    private static StringBuilder getIngredientName(ApplicationAll s) {
        StringBuilder sb = new StringBuilder();
        substanceApprovalIdSB.setLength(0);
        String substanceName = null;
        String approvalId = null;

        try {
            if (s.applicationProductList.size() > 0) {
                List<ProductSrsAll> prodList = s.applicationProductList;

                for (ProductSrsAll prod : prodList) {

                    for (AppIngredientAll ingred : prod.applicationIngredientList) {
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

                           // if (applicationController != null) {
                           //     JsonNode subJson = applicationController.injectSubstanceBySubstanceKey(ingred.substanceKey);

                          //      if (subJson != null) {
                          //          substanceName = subJson.path("_name").textValue();
                          //          approvalId = subJson.path("approvalID").textValue();

                            //TODO: replace with SubstanceKeyResolver for this later
                            //Get Substance Object by Substance Key
                            Query query = entityManager.createQuery("SELECT s FROM Substance s JOIN s.codes c WHERE c.type = 'PRIMARY' and c.code=:subKey");
                            query.setParameter("subKey", ingred.substanceKey);
                            Substance sub = (Substance) query.getSingleResult();

                            if (sub != null) {
                                // if (applicationController != null) {
                                //     JsonNode subJson = applicationController.injectSubstanceBySubstanceKey(ingred.substanceKey);

                                //     if (subJson != null) {
                                //        substanceName = subJson.path("_name").textValue();
                                //         approvalId = subJson.path("approvalID").textValue();

                                substanceName = ((Substance) EntityFetcher.of(sub.fetchKey()).call()).getName();
                                approvalId = sub.approvalID;

                                    // Get Substance Name
                                    sb.append((substanceName != null) ? substanceName : "(No Ingredient Name)");

                                    // Storing in static variable so do not have to call the same Substance API twice just to get
                                    // approval Id.
                                    substanceApprovalIdSB.append((approvalId != null) ? approvalId : "(No Approval ID)");
                                }
                          //  }
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

    private static StringBuilder getIngredientDetails(ApplicationAll s, AppAllDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();

        try {
            if (s.applicationProductList.size() > 0) {
                List<ProductSrsAll> prodList = s.applicationProductList;

                for (ProductSrsAll prod : prodList) {

                    for (AppIngredientAll ingred : prod.applicationIngredientList) {
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

    /*
    private static StringBuilder getIngredientDetails(ApplicationAll s, AppAllDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();
        substanceApprovalIdSB.setLength(0);
        String substanceName = null;
        String approvalId = null;

        try {
            if (s.applicationProductList.size() > 0) {
                List<ProductSrsAll> prodList = s.applicationProductList;

                for (ProductSrsAll prod : prodList) {

                    for (AppIngredientAll ingred : prod.applicationIngredientList) {
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

                    } // for
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb;
    }
    */

    private static StringBuilder getIndicationDetails(ApplicationAll s) {
        StringBuilder sb = new StringBuilder();

        if(s.indicationList.size() > 0){
            List<AppIndicationAll> indList = s.indicationList;

            for(AppIndicationAll ind : indList){
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
        private final List<ColumnValueRecipe<ApplicationAll>> columns = new ArrayList<>();
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

            for(Map.Entry<Column, ColumnValueRecipe<ApplicationAll>> entry : DEFAULT_RECIPE_MAP.entrySet()){
            	columns.add(entry.getValue());
            }
        }

        public Builder addColumn(Column column, ColumnValueRecipe<ApplicationAll> recipe){
        	return addColumn(column.name(), recipe);
        }

        public Builder addColumn(String columnName, ColumnValueRecipe<ApplicationAll> recipe){
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
            ListIterator<ColumnValueRecipe<ApplicationAll>> iter = columns.listIterator();
            while(iter.hasNext()){

                ColumnValueRecipe<ApplicationAll> oldValue = iter.next();
                ColumnValueRecipe<ApplicationAll> newValue = oldValue.replaceColumnName(oldName, newName);
                if(oldValue != newValue){
                   iter.set(newValue);
                }
            }
            return this;
        }

        public ApplicationAllExporter build(ApplicationAllController applicationController, EntityManager entityManager){
            return new ApplicationAllExporter(this, applicationController, entityManager);
        }

        public Builder includePublicDataOnly(boolean publicOnly){
            this.publicOnly = publicOnly;
            return this;
        }

    }
}
