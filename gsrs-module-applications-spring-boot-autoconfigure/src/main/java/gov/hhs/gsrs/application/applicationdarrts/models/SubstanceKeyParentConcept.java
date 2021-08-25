package gov.hhs.gsrs.application.applicationdarrts.models;

import gsrs.model.AbstractGsrsEntity;

import javax.persistence.*;

@Entity
@Table(name="SRSCID_SUBSTKEY_NAME_CONCEPT_V", schema = "srscid")
public class SubstanceKeyParentConcept extends AbstractGsrsEntity {

    @Id
    @Column(name="ID")
    public String id;

    @Column(name="UNII")
    public String unii;

    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="NAME")
    public String name;

    @Column(name="DISPLAY_TERM")
    public String displayTerm;

    @Column(name="PARENT_SUBSTANCE_KEY")
    public String parentSubstanceKey;

    @Column(name="PARENT_DISPLAY_TERM")
    public String parentDisplayTerm;

    public SubstanceKeyParentConcept () {}
}
