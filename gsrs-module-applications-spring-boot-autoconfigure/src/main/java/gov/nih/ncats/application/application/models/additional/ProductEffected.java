package gov.nih.ncats.application.application.models.additional;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="SRSCID_PRODUCT_EFFECTED")
public class ProductEffected extends AbstractGsrsEntity {

    @Id
    @SequenceGenerator(name="prodeffectedSeq", sequenceName="SRSCID_SQ_PRODUCT_EFFECTED_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prodeffectedSeq")
    @Column(name="ID")
    public int id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    @Column(name="EFFECTED_PRODUCT")
    public String effectedProduct;

    @Column(name="FARM_PRODUCT_ID")
    public int farmProductId;

    @Column(name="SUBSTANCE_ID")
    public int substanceId;

    @Column(name="CREATED_BY")
    public String createdBy;

    @Column(name="CREATE_DATE")
    public Date createDate;

    @Version
    public Long internalVersion;

    public ProductEffected () {}
}
