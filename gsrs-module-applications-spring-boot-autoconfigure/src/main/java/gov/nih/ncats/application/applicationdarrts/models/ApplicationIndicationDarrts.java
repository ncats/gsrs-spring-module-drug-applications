package gov.nih.ncats.application.applicationdarrts.models;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="SRSCID_APP_INDICATION_MV", schema = "srscid")
public class ApplicationIndicationDarrts extends AbstractGsrsEntity {

    @Id
    @Column(name="INDICATION_PK")
    public Integer indicationPk;

    @Column(name="APP_TYPE")
    public String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="INDICATION")
    public String indication;

    public ApplicationIndicationDarrts () {}
}
