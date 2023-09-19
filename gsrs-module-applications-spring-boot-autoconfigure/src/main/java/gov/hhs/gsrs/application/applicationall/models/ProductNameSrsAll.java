package gov.hhs.gsrs.application.applicationall.models;

import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="SRSCID_PRODUCT_NAME_SRS_ALL_MV")
public class ProductNameSrsAll extends AbstractGsrsEntity {

    @Id
    @Column(name="ID")
    public String id;

    @Indexable(sortable = true)
    @Column(name="PRODUCT_NAME", length=1000)
    public String productName;

    @Column(name="FROMTABLE")
    public String fromTable;

    public ProductNameSrsAll () {}

}
