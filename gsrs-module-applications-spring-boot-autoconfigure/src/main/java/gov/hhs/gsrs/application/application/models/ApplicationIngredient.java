package gov.hhs.gsrs.application.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gsrs.model.AbstractGsrsEntity;
import gsrs.security.GsrsSecurityUtils;
import ix.core.SingleParent;
import ix.core.models.Indexable;
import ix.core.models.ParentReference;
import ix.core.models.Principal;
import ix.core.models.UserProfile;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@SingleParent
@Data
@Entity
@Table(name="SRSCID_APPLICATION_TYPE_SRS")
public class ApplicationIngredient extends AbstractGsrsEntity {

    @Id
    @SequenceGenerator(name="appingSeq", sequenceName="SRSCID_SQ_APPLICATION_TYPE_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "appingSeq")
    @Column(name="APPLICATION_TYPE_ID")
    public Long id;

    @Column(name="APPLICANT_INGRED_NAME")
    public String applicantIngredName;

    @Indexable(facet = true, name = "Substance Key")
    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="SUBSTANCE_KEY_TYPE")
    public String substanceKeyType;

    @Indexable(name = "Basis Of Strength Substance Key")
    @Column(name="BOS_SUBSTANCE_KEY")
    public String basisOfStrengthSubstanceKey ;

    @Column(name="BOS_SUBSTANCE_KEY_TYPE")
    public String basisOfStrengthSubstanceKeyType;

    @Column(name="AVERAGE")
    public Double average;

    @Column(name="LOW")
    public Double low;

    @Column(name="HIGH")
    public Double high;

    @Column(name="LOW_LIMIT")
    public Double lowLimit;

    @Column(name="HIGH_LIMIT")
    public Double highLimit;

    @Column(name="NON_NUMERIC_VALUE")
    public String nonNumericValue;

    @Indexable(facet = true, name = "Ingredient Type")
    @Column(name="INGREDIENT_TYPE")
    public String ingredientType;

    @Column(name="UNIT")
    public String unit;

    @Column(name="GRADE")
    public String grade;

    @Column(name="REVIEWED_BY")
    public String reviewedBy;

    @Column(name="REVIEW_DATE")
    public Date reviewDate;

    @Version
    @Column(name = "INTERNAL_VERSION")
    public Long internalVersion;

    @Indexable(facet = true, name = "Ingredient Created By")
    @Column(name = "CREATED_BY")
    public String createdBy;

    @Indexable(facet = true, name = "Ingredient Last Edited By")
    @Column(name = "MODIFIED_BY")
    public String modifiedBy;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @CreatedDate
    @Indexable( name = "Ingredient Create Date", sortable=true)
    @Column(name = "CREATE_DATE")
    private Date creationDate;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @LastModifiedDate
    @Indexable( name = "Ingredient Last Edited", sortable=true)
    @Column(name = "MODIFY_DATE")
    private Date lastModifiedDate;

    @Indexable(indexed=false)
    @ParentReference
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID")
    public ApplicationProduct owner;

    public void setOwner(ApplicationProduct applicationProduct) {
        this.owner = applicationProduct;
    }

    @PrePersist
    public void prePersist() {
        try {
            UserProfile profile = (UserProfile) GsrsSecurityUtils.getCurrentUser();
            if (profile != null) {
                Principal p = profile.user;
                if (p != null) {
                    this.createdBy = p.username;
                    this.modifiedBy = p.username;
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @PreUpdate
    public void preUpdate() {
        try {
            UserProfile profile = (UserProfile) GsrsSecurityUtils.getCurrentUser();
            if (profile != null) {
                Principal p = profile.user;
                if (p != null) {
                    this.modifiedBy = p.username;
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
