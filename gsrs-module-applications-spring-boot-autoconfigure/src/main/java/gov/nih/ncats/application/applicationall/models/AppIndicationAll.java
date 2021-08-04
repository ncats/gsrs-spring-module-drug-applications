package gov.nih.ncats.application.applicationall.models;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="SRSCID_APP_INDICATION_ALL_MV", schema = "srscid")
public class AppIndicationAll extends AbstractGsrsEntity {

    @Id
    @Column(name="ID")
    public String id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

    @Column(name="APP_TYPE")
    public String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="INDICATION")
    public String indication;

    public AppIndicationAll () {}

}
