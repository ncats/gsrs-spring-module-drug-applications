package gov.nih.ncats.application.applicationdarrts.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.ncats.application.application.models.ApplicationProduct;
import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.models.Indexable;
import ix.core.models.IxModel;
import ix.core.search.text.TextIndexerEntityListener;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;

import ix.ginas.models.v1.Name;
import ix.ginas.models.v1.Substance;
import ix.ginas.models.v1.SubstanceReference;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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
