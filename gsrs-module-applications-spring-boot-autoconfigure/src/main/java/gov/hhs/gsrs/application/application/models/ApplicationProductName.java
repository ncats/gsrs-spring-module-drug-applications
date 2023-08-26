package gov.hhs.gsrs.application.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ix.core.SingleParent;
import ix.core.models.Indexable;

import ix.core.models.ParentReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@SingleParent
@Data
@Entity
@Table(name="SRSCID_PRODUCT_NAME_SRS")
public class ApplicationProductName extends ApplicationCommanData {

    @Id
    @SequenceGenerator(name="prodNameSrsSeq", sequenceName="SRSCID_SQ_PRODUCT_NAME_SRS_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prodNameSrsSeq")
    @Column(name="ID")
    public Long id;

    @Indexable(suggest = true, facet=true, name = "Product Name", sortable = true)
    @Column(name="PRODUCT_NAME", length=1000)
    public String productName;

    @Column(name="PRODUCT_NAME_TYPE")
    public String productNameType;

    @Column(name="PROVENANCE")
    public String provenance;

    @Indexable(facet=true, name = "Product Name Deprecated")
    @Column(name="DEPRECATED")
    public String deprecated;

    @Indexable(indexed=false)
    @ParentReference
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="PRODUCT_ID",referencedColumnName="PRODUCT_ID")
    public ApplicationProduct owner;

    public void setOwner(ApplicationProduct applicationProduct) {
        this.owner = applicationProduct;
    }
}
