package gov.hhs.gsrs.application.applicationdarrts.models;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@IdClass(ApplicationDarrtsIngredCompositePrimaryKeyId.class)
@Table(name="SRSCID_APPLICATION_TYPE_MV")
public class ApplicationDarrtsIngredient extends AbstractGsrsEntity {

    @Id
    public String appType;

    @Id
    public String appNumber;

    @Id
    public String substanceKey;

    @Id
    public String productNo;

    @Column(name="SUBSTANCE_KEY_TYPE")
    public String substanceKeyType;

    @Column(name="ACTIVITY")
    public String activity;

    @Column(name="POTENCY")
    public String potency;

    @Column(name="PART_NO")
    public String partNo;

    public ApplicationDarrtsIngredient () {}
}
