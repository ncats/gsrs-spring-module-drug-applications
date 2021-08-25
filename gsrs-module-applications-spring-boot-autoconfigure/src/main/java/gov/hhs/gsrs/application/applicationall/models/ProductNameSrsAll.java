package gov.hhs.gsrs.application.applicationall.models;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="SRSCID_PRODUCT_NAME_SRS_ALL_MV")
public class ProductNameSrsAll extends AbstractGsrsEntity {

    @Id
    @Column(name="ID")
    public String id;

    @Column(name="PRODUCT_NAME")
    public String productName;

    @Column(name="FROMTABLE")
    public String fromTable;

    public ProductNameSrsAll () {}

}
