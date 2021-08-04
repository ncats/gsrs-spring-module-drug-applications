package gov.nih.ncats.application.applicationdarrts.models;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@IdClass(ApplicationDarrtsCompositePrimaryKeyId.class)
@Table(name="SRSCID_APPLICATION_MV", schema = "srscid")
public class ApplicationDarrts extends AbstractGsrsEntity {

    @Id
   // @Column(name="APP_TYPE")
    public String appType;

    @Id
   // @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="APP_SUB_TYPE")
    public String appSubType;

    @Column(name="APP_SUB_TYPE_DESC")
    public String appSubTypeDesc;

    @Column(name="PRODUCT_NAME")
    public String productName;

    @Column(name="DOSAGE_FORM_DESC")
    public String dosageFormDesc;

    @Column(name="SPONSOR_NAME")
    public String sponsorName;

    @Column(name="DIVISION_CLASS")
    public String divisionClass;

    @Column(name="DIVISION_CLASS_DESC")
    public String divisionClassDesc;

    @Column(name="APP_STATUS")
    public String status;

    @Column(name="APP_STATUS_DATE")
    public String statusDate;

    @Column(name="ROUTE_OF_ADMIN")
    public String routeOfAdmin;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="APP_TYPE", referencedColumnName = "APP_TYPE", insertable = false, updatable = false ),
            @JoinColumn(name="APP_NUMBER", referencedColumnName = "APP_NUMBER", insertable = false, updatable = false)
    })
    public List<ApplicationDarrtsIngredient> ingredientList = new ArrayList<ApplicationDarrtsIngredient>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="APP_TYPE", referencedColumnName = "APP_TYPE", insertable = false, updatable = false ),
            @JoinColumn(name="APP_NUMBER", referencedColumnName = "APP_NUMBER", insertable = false, updatable = false)
    })
    public List<ApplicationIndicationDarrts> indicationList = new ArrayList<ApplicationIndicationDarrts>();

    //  public List<String> indicationList = new ArrayList<>();
    //  public List<ApplicationDarrtsIngredient> ingredientList = new ArrayList<>();

    public ApplicationDarrts () {}

    public String getAppType() {
        return appType;
    }

}
