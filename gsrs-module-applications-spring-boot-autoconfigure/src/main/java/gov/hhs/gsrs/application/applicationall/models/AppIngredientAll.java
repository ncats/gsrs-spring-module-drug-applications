package gov.hhs.gsrs.application.applicationall.models;

import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="SRSCID_APPLICATION_TYPE_ALL_MV")
public class AppIngredientAll extends AbstractGsrsEntity {

    @Id
    @Column(name="APPLICATION_TYPE_ID")
    public String id;

    @Column(name="PRODUCT_ID")
    public String productId;

    @Column(name="APPLICANT_INGRED_NAME")
    public String applicantIngredName;

    @Indexable(facet=true, name= "Substance Key")
    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="INGREDIENT_TYPE")
    public String ingredientType;

    /*
    @ManyToOne(optional=true)
    @JoinColumn(name="BDNUM", insertable=false, updatable=false)
    public BdnumName bdnumName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="BDNUM")
    @Indexable(indexed = false)
    public SubstanceAll subAllAppType;
    */

    public AppIngredientAll () {}


}
