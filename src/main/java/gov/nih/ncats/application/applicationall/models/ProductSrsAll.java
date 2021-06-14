package gov.nih.ncats.application.applicationall.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name="SRSCID_PRODUCT_SRS_ALL_MV", schema = "srscid")
public class ProductSrsAll extends AbstractGsrsEntity {

    @Id
    @Column(name="PRODUCT_ID")
    public String id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    /*
    @Column(name="APP_TYPE")
    public String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="PRODUCT_NAME")
    public String productName;
    */

    @Column(name="DOSAGE_FORM")
    public String dosageForm;


    //  @JsonIgnore
  //  @ManyToOne
 //   @JoinColumn(name="APPLICATION_ID")
  //  public ApplicationAll applicationAll;

    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<AppIngredientAll> applicationIngredientList = new ArrayList<>();

    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductNameSrsAll> applicationProductNameList = new ArrayList<>();

    public ProductSrsAll () {}

}
