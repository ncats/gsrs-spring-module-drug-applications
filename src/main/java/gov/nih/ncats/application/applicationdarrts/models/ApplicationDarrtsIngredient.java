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
@IdClass(ApplicationDarrtsIngredCompositePrimaryKeyId.class)
@Table(name="SRSCID_APPLICATION_TYPE_MV", schema = "srscid")
public class ApplicationDarrtsIngredient extends AbstractGsrsEntity {

    @Id
    public String appType;

    @Id
    public String appNumber;

    @Id
    public String substanceKey;

    @Id
    public String productNo;

    @Column(name="SUBSTANCE_KEY_TYPE")
    public String substanceKeyType;

    @Column(name="ACTIVITY")
    public String activity;

    @Column(name="POTENCY")
    public String potency;

    @Column(name="PART_NO")
    public String partNo;

    /*
    @Transient
    public String substanceId;

    @Transient
    public String unii;

    @Transient
    public String name;

    @Transient
    public String parentBdnum;

    @Transient
    public String parentDisplayTerm;
    */

    /*
    @JoinColumn(name = "SUBSTANCE_KEY", referencedColumnName = "SUBSTANCE_KEY")
   // @OneToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = CascadeType.ALL)
    public List<SubstanceKeyParentConcept> substanceKeyParentConceptList = new ArrayList<>();
    */

    public ApplicationDarrtsIngredient () {}
}
