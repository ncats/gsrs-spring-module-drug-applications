package gov.hhs.gsrs.application.applicationall.models;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="SRSCID_PRODUCT_SRS_ALL_MV", schema = "srscid")
public class ProductSrsAll extends AbstractGsrsEntity {

    @Id
    @Column(name="PRODUCT_ID")
    public String id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    @Column(name="DOSAGE_FORM")
    public String dosageForm;

    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(cascade = CascadeType.ALL)
    public List<AppIngredientAll> applicationIngredientList = new ArrayList<>();

    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductNameSrsAll> applicationProductNameList = new ArrayList<>();

    public ProductSrsAll () {}

}
