package gov.nih.ncats.application.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name="SRSCID_APPLICATION_TYPE_SRS", schema = "srscid")
public class ApplicationIngredient extends AbstractGsrsEntity {

    @Id
    @SequenceGenerator(name="appingSeq", sequenceName="SRSCID.SRSCID_SQ_APPLICATION_TYPE_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appingSeq")
    @Column(name="APPLICATION_TYPE_ID")
    public Long id;

    @Column(name="PRODUCT_ID")
    public String productId;

    @Column(name="APPLICANT_INGRED_NAME")
    public String applicantIngredName;
    /*
    @Indexable(facet = true, name = "Bdnum")
    @Column(name="BDNUM")
    public String bdnum;

    @Column(name="BASIS_OF_STRENGTH")
    public String basisOfStrengthBdnum;
    */

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

    @Indexable(facet = true, name = "Ingredient Last Modified By")
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
    @Indexable( name = "Ingredient Last Modified Date", sortable=true)
    @Column(name = "MODIFY_DATE")
    private Date lastModifiedDate;

    /*
    @Transient
    @JsonProperty("_substanceUuid")
    public String _substanceUuid;

    @Transient
    @JsonProperty("_approvalID")
    public String _approvalID;

    @Transient
    @JsonProperty("_name")
    public String _name;

    @Transient
    @JsonProperty("_basisOfStrengthSubstanceUuid")
    public String _basisOfStrengthSubstanceUuid;

    @Transient
    @JsonProperty("_basisOfStrengthApprovalID")
    public String _basisOfStrengthApprovalID;

    @Transient
    @JsonProperty("_basisOfStrengthName")
    public String _basisOfStrengthName;
    */
}
