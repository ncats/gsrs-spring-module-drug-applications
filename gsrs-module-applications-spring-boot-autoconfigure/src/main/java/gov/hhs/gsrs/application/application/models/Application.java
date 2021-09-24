package gov.hhs.gsrs.application.application.models;

import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.IndexableRoot;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@IndexableRoot
@Backup
@Data
@Entity
@Table(name="SRSCID_APPLICATION_SRS")
public class Application extends ApplicationCommanData {

    public static final String HAS_INGREDIENT = "Has Ingredients";
    public static final String HAS_NO_INGREDIENT = "Has No Ingredient";
    public static final String HAS_INDICATION = "Has Indications";
    public static final String HAS_NO_INDICATION = "Has No Indication";
    public static final String HAS_NO_SUBMIT_DATE = "Has No Submit Date";
    public static final String HAS_NO_STATUS_DATE = "Has No Status Date";
    public static final String HAS_NO_PRODUCTS = "Has No product";
    public static final String HAS_PRODUCTS = "Has Products";
    public static final String HAS_CLINICALTRIAL = "Has Clinical Trials";
    public static final String HAS_NO_CLINICALTRIAL = "Has No Clinical Trial";
    public static final String HAS_NO_ACTIVE_MOIETY = "Has No Active Moiety";
    public static final String HAS_ACTIVE_MOIETY = "Has Active Moiety";
    public static final String TODAY = "Today";
    public static final String THIS_WEEK = "This Week";
    public static final String THIS_MONTH = "This Month";
    public static final String PAST_6_MONTHS = "Past 6 months";
    public static final String PAST_1_YEAR = "Past 1 year";
    public static final String PAST_2_YEARS = "Past 2 years";
    public static final String OLDER_THAN_2_YEARS = "Older than 2 years";

    static final ThreadLocal<DateFormat> YEAR_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }
    };
    //Today's Date
    static final LocalDate todayDate =  LocalDate.now();

    @Id
    @SequenceGenerator(name = "appSeq", sequenceName = "SRSCID_SQ_APPLICATION_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "appSeq")
    @Column(name = "APPLICATION_ID")
    public Long id;

    @Indexable(facet = true, name = "Center", sortable = true)
    @Column(name = "CENTER")
    public String center;

    @Indexable(facet = true, name = "Application Type")
    @Column(name = "APP_TYPE")
    public String appType;

    @Indexable(facet = true, name = "Application Number", sortable = true)
    @Column(name = "APP_NUMBER")
    public String appNumber;

    @Indexable(facet = true, name = "Title")
    @Column(name = "APPLICATION_TITLE")
    public String title;

    @Indexable(facet = true, name = "Sponsor Name")
    @Column(name = "SPONSOR_NAME")
    public String sponsorName;

    @Column(name = "NONPROPRIETARY_NAME")
    public String nonProprietaryName;

    @Indexable(facet = true, name = "Submit Date")
    @Column(name = "SUBMIT_DATE")
    public Date submitDate;

    @Indexable(facet = true, name = "Application Sub Type")
    @Column(name = "APP_SUB_TYPE")
    public String appSubType;

    @Indexable(facet = true, name = "Division Class Description")
    @Column(name = "DIVISION_CLASS_DESC")
    public String divisionClassDesc;

    @Indexable(facet = true, name = "Application Status")
    @Column(name = "STATUS")
    public String status;

    /*
    @Indexable(facet = true, name = "Source")
    @Column(name = "SOURCE")
    public String source;
    */

    @Indexable(facet = true, name = "Public Domain")
    @Column(name = "PUBLIC_DOMAIN")
    public String publicDomain;

    @Indexable(facet = true, name = "Provenance (GSRS)")
    @Column(name = "PROVENANCE")
    public String provenance;

    @Column(name = "EXTERNAL_TITLE")
    public String externalTitle;

    @Column(name = "STATUS_DATE")
    public Date statusDate;

    // @Column(name = "VERSION")
    // public int version = 0;

    /*
    @Version
    @Column(name = "INTERNAL_VERSION")
    public Long internalVersion;
    */

    @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "APPLICATION_ID")
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<ApplicationProduct> applicationProductList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "APPLICATION_ID_FK", referencedColumnName = "APPLICATION_ID")
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ApplicationIndication> applicationIndicationList = new ArrayList<>();

    public String getCenter() {
        return this.center;
    }

    public Long getId() {
        return id;
    }

    public String getSubmitDate() {
        //Convert Date to String
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

    /*
    @PrePersist
    public void prePersist() {
        Date currentDate = TimeUtil.getCurrentDate();
        Principal p1=UserFetcher.getActingUser();
        if (p1 != null) {
            this.createdBy = p1.username;
        }

        this.createDate = currentDate;
    }
    */

    @JsonIgnore
    @Indexable(facet=true, name="Has Products")
    public String getHasProducts(){
        String result = HAS_NO_PRODUCTS;

        if (!this.applicationProductList.isEmpty()) {
            result = HAS_PRODUCTS;
        }

        return result;
    }

    @JsonIgnore
    @Indexable(facet=true, name="Has Ingredients")
    public String getHasIngredient(){
        String result = HAS_NO_INGREDIENT;
        for(ApplicationProduct r: this.applicationProductList){
            for(ApplicationIngredient ing: r.applicationIngredientList){
                if (ing != null) {
                    if (ing.substanceKey != null) {
                        result = HAS_INGREDIENT;
                    }
                }
            }
        }
        return result;
    }

    @JsonIgnore
    @Indexable(facet=true, name="Has Indications")
    public String getHasIndication() {
        String result = HAS_NO_INDICATION;
        if (!this.applicationIndicationList.isEmpty()) {
            result = HAS_INDICATION;
        }
        return result;
    }

    @JsonIgnore
    @Indexable(facet=true, name="Submit Date Year")
    public String getSubmitDateByYear(){
        String result = HAS_NO_SUBMIT_DATE;
        if (this.submitDate != null) {
            result = YEAR_DATE_FORMAT.get().format(this.submitDate);
        }
        return result;
    }

    @JsonIgnore
    @Indexable(facet=true, name="Status Date Year")
    public String getStatusDateByYear(){
        String result = HAS_NO_STATUS_DATE;
        if (this.statusDate != null) {
            result = YEAR_DATE_FORMAT.get().format(this.statusDate);
        }
        return result;
    }

    /*
    @JsonIgnore
    @Indexable(facet=true, name="Ingredient Name", sortable = true)
    public String getIngredientName(){
        String result = HAS_NO_INGREDIENT;
        for(ApplicationProduct r: this.applicationProductList){
            for(ApplicationIngredient ing: r.applicationIngredientList){
                if (ing != null) {
                    if (ing._name != null) {
                        result =  ing._name;
                    }
                }
            }
        }
        return result;
    }
    */

    @JsonIgnore
    @Indexable(facet=true, name="Submit Date")
    public String getSubmitDateRange(){
        String result = HAS_NO_SUBMIT_DATE;
        if (this.submitDate != null) {
            LocalDate localDateSubmitDate = Instant.ofEpochMilli(this.submitDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            //Instant instant = Instant.ofEpochMilli(this.submitDate.getTime());
            //LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            // LocalDate localDateSubmitDate = localDateTime.toLocalDate();
            //System.out.println("Submit date : " + localDateSubmitDate.toString());
            // Period days = Period.between(localDateSubmitDate.withDayOfMonth(1), todayDate.withDayOfMonth(1));
            long month = ChronoUnit.MONTHS.between(localDateSubmitDate.withDayOfMonth(1), todayDate.withDayOfMonth(1));
            // int day = days.getDays();
            //int months = days.getMonths();
            // int years = days.getYears();
            //System.out.println("DAYS: " + days + " " + day + "  " + months + "   " + years);
//            System.out.println("MONTH: " + month);
            //if (day <= 7) {
            //      result = THIS_WEEK;
            //  }
            if (month > 24) {
                result = OLDER_THAN_2_YEARS;
            }
            if ((month >= 13) && (month <= 24)) {
                result = PAST_2_YEARS;
            }
            if ((month >= 7) && (month <= 12)) {
                result = PAST_1_YEAR;
            }
            if ((month >= 2) && (month <= 6)) {
                result = PAST_6_MONTHS;
            }
            if (month == 1) {
                result = THIS_MONTH;
            }
        }
        return result;
    }

    /*
    @JsonIgnore
    @Indexable(facet=true, name="Product Name Deprecated")
    public String getProductNameDeprecated(){
        String result = "";
        for(ApplicationProduct p: this.applicationProductList){
            for(ApplicationProductName pn: p.applicationProductNameList){
                if (pn != null) {
                    result = pn.deprecated;
                }
            }
        }
        return result;
    }
    */

    @JsonIgnore
    @Indexable(facet=true, name="Deprecated")
    public String getDeprecated(){
        return "Not Deprecated";
    }
}
