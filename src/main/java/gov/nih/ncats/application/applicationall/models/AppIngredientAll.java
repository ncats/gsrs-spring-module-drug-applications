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
@Table(name="SRSCID_APPLICATION_TYPE_ALL_MV", schema = "srscid")
public class AppIngredientAll extends AbstractGsrsEntity {

    @Id
    @Column(name="APPLICATION_TYPE_ID")
    public String id;

    @Column(name="PRODUCT_ID")
    public String productId;

    @Column(name="APPLICANT_INGRED_NAME")
    public String applicantIngredName;

    @Indexable(facet=true, name= "Substance Key")
    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="INGREDIENT_TYPE")
    public String ingredientType;

    /*
    @ManyToOne(optional=true)
    @JoinColumn(name="BDNUM", insertable=false, updatable=false)
    public BdnumName bdnumName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="BDNUM")
    @Indexable(indexed = false)
    public SubstanceAll subAllAppType;
    */

    public AppIngredientAll () {}


}
