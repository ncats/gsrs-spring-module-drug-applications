package gov.nih.ncats.application.application.models;

import ix.core.SingleParent;
import ix.core.models.Indexable;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SingleParent
@Data
@Entity
@Table(name="SRSCID_PRODUCT_SRS")
public class ApplicationProduct extends ApplicationCommanData {

    @Id
    @SequenceGenerator(name="prodsrsSeq", sequenceName="SRSCID_SQ_PRODUCT_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prodsrsSeq")
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
