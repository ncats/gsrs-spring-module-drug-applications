package gov.nih.ncats.application.model;

import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
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

import javax.persistence.EntityListeners;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name="SRSCID_APPLICATION_SRS")
public class Application extends AbstractGsrsEntity {

    @Id
    @SequenceGenerator(name = "appSeq", sequenceName = "SRSCID_SQ_APPLICATION_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appSeq")
    @Column(name = "APPLICATION_ID")
    public Long id;

    @Indexable(facet = true, name = "Application Type")
    @Column(name = "APP_TYPE")
    public String appType;

    @Indexable(facet = true, name = "Application Number")
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

    @Column(name = "DIVISION_CLASS_DESC")
    public String divisionClassDesc;

    @Indexable(facet = true, name = "Application Status")
    @Column(name = "STATUS")
    public String status;

    @Indexable(facet = true, name = "Center")
    @Column(name = "CENTER")
    public String center;

    @Indexable(facet = true, name = "Source")
    @Column(name = "SOURCE")
    public String source;

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

    @Column(name = "VERSION")
    public int version = 0;

    @Version
    public Long internalVersion;

   // @Transient
   // public boolean isDisabled;

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

    @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "APPLICATION_ID")
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<ApplicationProduct> applicationProductList = new ArrayList<>();

    public String getCenter() {
        return this.center;
    }
}
