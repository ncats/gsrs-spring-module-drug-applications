package gov.nih.ncats.application.application.models.additional;

import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.IxModel;
import ix.core.search.text.TextIndexerEntityListener;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name="SRSCID_PRODUCT_EFFECTED", schema = "srscid")
public class ProductEffected extends AbstractGsrsEntity {

    @Id
    @SequenceGenerator(name="prodeffectedSeq", sequenceName="SRSCID_SQ_PRODUCT_EFFECTED_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodeffectedSeq")
    @Column(name="ID")
    public int id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    @Column(name="EFFECTED_PRODUCT")
    public String effectedProduct;

    @Column(name="FARM_PRODUCT_ID")
    public int farmProductId;

    @Column(name="SUBSTANCE_ID")
    public int substanceId;

    @Column(name="CREATED_BY")
    public String createdBy;

    @Column(name="CREATE_DATE")
    public Date createDate;

    @Version
    public Long internalVersion;

    public ProductEffected () {}
}
