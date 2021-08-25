package gov.hhs.gsrs.application.application.models;

import ix.core.SingleParent;
import ix.core.models.Indexable;

import lombok.Data;

import javax.persistence.*;

@SingleParent
@Data
@Entity
@Table(name="SRSCID_PRODUCT_NAME_SRS")
public class ApplicationProductName extends ApplicationCommanData {

    @Id
    @SequenceGenerator(name="prodNameSrsSeq", sequenceName="SRSCID.SRSCID_SQ_PRODUCT_NAME_SRS_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prodNameSrsSeq")
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
