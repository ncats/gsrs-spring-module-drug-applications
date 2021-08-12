package gov.nih.ncats.application.application.models.additional;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="SRSCID_PRODUCT_TECH_EFFECT")
public class ProductTechnicalEffect extends AbstractGsrsEntity {

    @Id
    @SequenceGenerator(name="prodtecheffectSeq", sequenceName="SRSCID_SQ_PROD_TECH_EFFECT_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodtecheffectSeq")
    @Column(name="ID")
    public int id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    // @Indexable(facet=true, name="Technical Effect")
    @Column(name="TECHNICAL_EFFECT")
    public String technicalEffect;

    @Column(name="FARM_TECH_EFFECT_ID")
    public int farmTechEffectId;

    @Column(name="SUBSTANCE_ID")
    public int substanceId;

    @Column(name="CREATED_BY")
    public String createdBy;

    @Column(name="CREATE_DATE")
    public Date createDate;

    @Version
    public Long internalVersion;

    public ProductTechnicalEffect () {}
}
