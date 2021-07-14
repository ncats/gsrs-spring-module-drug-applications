package gov.nih.ncats.application.application.models;

import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.SingleParent;
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

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@SingleParent
@Data
@Entity
@Table(name="SRSCID_PRODUCT_NAME_SRS", schema = "srscid")
public class ApplicationProductName extends ApplicationCommanData {

    @Id
    @SequenceGenerator(name="prodNameSrsSeq", sequenceName="SRSCID.SRSCID_SQ_PRODUCT_NAME_SRS_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodNameSrsSeq")
    @Column(name="ID")
    public Long id;

    @Indexable(facet=true, name = "Product Name", sortable = true)
    @Column(name="PRODUCT_NAME")
    public String productName;

    @Column(name="PRODUCT_NAME_TYPE")
    public String productNameType;

    @Column(name="PROVENANCE")
    public String provenance;

    @Indexable(facet=true, name = "Product Name Deprecated")
    @Column(name="DEPRECATED")
    public String deprecated;

    /*
    @Version
    @Column(name = "INTERNAL_VERSION")
    public Long internalVersion;

    @Column(name = "CREATED_BY")
    public String createdBy;

    @Column(name = "MODIFIED_BY")
    public String modifiedBy;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @CreatedDate
    @Indexable( name = "Create Date", sortable=true)
    @Column(name = "CREATE_DATE")
    private Date creationDate;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @LastModifiedDate
    @Indexable( name = "Last Modified Date", sortable=true)
    @Column(name = "MODIFY_DATE")
    private Date lastModifiedDate;
    */

    /*
    @Indexable(indexed=false)
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_ID")
    public ApplicationProduct productFromName;
     */

}