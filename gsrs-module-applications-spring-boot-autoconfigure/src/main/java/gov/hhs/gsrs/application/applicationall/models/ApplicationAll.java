package gov.hhs.gsrs.application.applicationall.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.IndexableRoot;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@IndexableRoot
@Backup
@Data
@Entity
@Table(name="SRSCID_APPLICATION_ALL_MV")
public class ApplicationAll extends AbstractGsrsEntity {

    @Id
    @Column(name="APPLICATION_ID")
    public String id;

    @Column(name="APP_TYPE")
    public String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="APPLICATION_TITLE")
    public String title;

    @Column(name="SPONSOR_NAME")
    public String sponsorName;

    @Column(name="STATUS")
    public String appStatus;

    @Column(name = "SUBMIT_DATE")
    public Date submitDate;

    @Column(name = "STATUS_DATE")
    public Date statusDate;

    @Column(name="DIVISION_CLASS_DESC")
    public String divisionClassDesc;

    @Column(name="APP_SUB_TYPE")
    public String appSubType;

    @Column(name="NONPROPRIETARY_NAME")
    public String nonProprietaryName;

    @Indexable(facet=true, suggest=true, name = "Center")
    @Column(name="CENTER")
    public String center;

    @Indexable(facet=true, name = "Source")
    @Column(name="FROMTABLE")
    public String fromTable;

    @Column(name="IN_DARRTS_DETAIL")
    public String inDarrtsDetail;

    @Indexable(facet=true, name = "Provenance")
    @Column(name="PROVENANCE")
    public String provenance;

    public String getId() {
        return id;
    }

    @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "APPLICATION_ID")
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<ProductSrsAll> applicationProductList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @Indexable(indexed=false)
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="APPLICATION_ID", referencedColumnName="APPLICATION_ID")
    public List<AppIndicationAll> indicationList = new ArrayList<>();

    public ApplicationAll () {}

    @JsonIgnore
    @Indexable(facet=true, name="Deprecated")
    public String getDeprecated(){
        return "Not Deprecated";
    }

    public String getSubmitDate() {
        //Convert Date to String, get value from database
        return convertDateToString(this.submitDate);
    }

    public void setSubmitDate(String submitDate) {
        //Convert String to Date
        this.submitDate = convertStringToDate(submitDate);
    }

    public String getStatusDate() {
        //Get value from Database
        //Convert Date to String
        return convertDateToString(this.statusDate);
    }

    public void setStatusDate(String statusDate) {
        //Set or Store value into Database
        //Convert String to Date
        this.statusDate = convertStringToDate(statusDate);
    }

    public String convertDateToString(Date dtDate) {

        String strDate = null;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            if (dtDate != null) {
                strDate = df.format(dtDate);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return strDate;
    }

    public Date convertStringToDate(String strDate) {

        Date dtDate = null;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            if ((strDate != null) && (strDate.length() > 0)) {
                dtDate = df.parse(strDate);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return dtDate;
    }

    /*
    @JsonIgnore
    @Indexable(facet=true, name= "Ingredient Type")
    public List<String> getIndexedIngredientTypes(){
    	if(this.ingredientType!=null){
    		return Arrays.asList(this.ingredientType.split("\\|"));
    	}
    	return new ArrayList<String>(); 
    }
    */
}
