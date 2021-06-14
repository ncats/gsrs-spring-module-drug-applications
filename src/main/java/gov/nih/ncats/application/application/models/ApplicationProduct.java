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
@Table(name="SRSCID_PRODUCT_SRS", schema = "srscid")
public class ApplicationProduct extends ApplicationCommanData {

    @Id
    @SequenceGenerator(name="prodsrsSeq", sequenceName="SRSCID.SRSCID_SQ_PRODUCT_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodsrsSeq")
    @Column(name = "PRODUCT_ID")
    public Long id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    @Indexable(facet = true, name = "Dosage Form")
    @Column(name="DOSAGE_FORM")
    public String dosageForm;

    @Column(name="ROUTE_OF_ADMINISTRATION")
    public String routeAdmin;

    @Column(name="UNIT_PRESENTATION")
    public String unitPresentation;

    @Column(name="AMOUNT")
    public Double amount;

    @Column(name="UNIT")
    public String unit;

    @Column(name="REVIEWED_BY")
    public String reviewedBy;

    @Column(name="REVIEW_DATE")
    public Date reviewDate;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName="APPLICATION_ID")
    public Application application;
    */
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ApplicationProductName> applicationProductNameList = new ArrayList<>();

    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ApplicationIngredient> applicationIngredientList = new ArrayList<>();

}
