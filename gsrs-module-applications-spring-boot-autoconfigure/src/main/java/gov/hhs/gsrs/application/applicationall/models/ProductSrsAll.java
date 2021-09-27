package gov.hhs.gsrs.application.applicationall.models;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="SRSCID_PRODUCT_SRS_ALL_MV")
public class ProductSrsAll extends AbstractGsrsEntity {

    @Id
    @Column(name="PRODUCT_ID")
    public String id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    @Column(name="DOSAGE_FORM")
    public String dosageForm;

    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ProductNameSrsAll> applicationProductNameList = new ArrayList<>();

    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<AppIngredientAll> applicationIngredientList = new ArrayList<>();

    public ProductSrsAll () {}

}
