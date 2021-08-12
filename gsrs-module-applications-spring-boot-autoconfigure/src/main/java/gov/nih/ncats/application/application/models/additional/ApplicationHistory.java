package gov.nih.ncats.application.application.models.additional;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="SRSCID_APPLICATION_SRS_HISTORY")
public class ApplicationHistory extends AbstractGsrsEntity {

    @Id
    public int id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    @Column(name="PRODUCT_NAME")
    public String productName;

    @Column(name="SPONSOR_NAME")
    public String sponsorName;

    @Column(name="STATUS")
    public String status;

    @Column(name="STATUS_DATE")
    public Date statusDate;

    @Column(name="CREATE_DATE")
    public Date createDate;

    public ApplicationHistory() {}

}
