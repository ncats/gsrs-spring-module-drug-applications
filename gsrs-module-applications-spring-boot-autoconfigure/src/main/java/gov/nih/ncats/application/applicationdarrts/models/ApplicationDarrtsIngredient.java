package gov.nih.ncats.application.applicationdarrts.models;

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

    /*
    @Transient
    public String substanceId;

    @Transient
    public String unii;

    @Transient
    public String name;

    @Transient
    public String parentBdnum;

    @Transient
    public String parentDisplayTerm;
    */

    /*
    @JoinColumn(name = "SUBSTANCE_KEY", referencedColumnName = "SUBSTANCE_KEY")
   // @OneToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = CascadeType.ALL)
    public List<SubstanceKeyParentConcept> substanceKeyParentConceptList = new ArrayList<>();
    */

    public ApplicationDarrtsIngredient () {}
}
