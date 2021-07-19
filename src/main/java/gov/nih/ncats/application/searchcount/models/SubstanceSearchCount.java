package gov.nih.ncats.application.searchcount.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.ncats.application.searchcount.*;
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
@IdClass(SearchCountCompositePrimaryKeyId.class)
@Table(name="SRSCID_SEARCH_COUNT_MV", schema = "srscid")
public class SubstanceSearchCount extends AbstractGsrsEntity {

    @Id
    public String substanceId;

    @Id
    public String provenance;

    @Column(name="UNII")
    public String unii;

    @Column(name="CODE")
    public String bdnum;

    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="APPCOUNT")
    public int appCount;

    @Column(name="PRODACTIVECOUNT")
    public int prodActiveCount;

    @Column(name="PRODINACTIVECOUNT")
    public int prodInactiveCount;

    @Column(name="CLINICALCOUNT")
    public int clinicalCount;

    @Column(name="CASECOUNT")
    public int adverseEventCount;

    @Column(name="CENTER")
    public String center;

    @Transient
    public String appCountConcat;

    public SubstanceSearchCount () {}

}

