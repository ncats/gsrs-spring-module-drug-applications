package gov.hhs.gsrs.application.application.models.additional;

import com.fasterxml.jackson.annotation.JsonProperty;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="ct_clinical_trial")
public class ClinicalTrial extends AbstractGsrsEntity {

    @Id
    @Column(name = "NCTN", length=22)
    @JsonProperty("nctNumber")
    public String nctn;

    @Column(name = "TITLE", length=500)
    public String title;

    @Column(name = "RECRUITMENT", length=200)
    public String recruitment;

    @Column(name = "RESULTS_FIRST_RECEIVED", length=100)
    public String resultsFirstRecieved;

    @Column(name = "CONDITIONS", length=4000)
    public String conditions;

    @Column(name = "INTERVENTION", length=1800)
    public String intervention;

    @Column(name = "SPONSOR", length=4000)
    public String sponsor;

    @Column(name = "PHASES", length=50)
    public String phases;

    @Column(name = "FUNDED_BYS", length=100)
    public String fundedBys;

    @Indexable(facet = true, name = "Study Types")
    @Column(name = "STUDY_TYPES", length=150)
    public String studyTypes;

    @Column(name = "STUDY_DESIGNS", length=255)
    public String studyDesigns;

    @Indexable(facet = true, name = "Study Results")
    @Column(name = "STUDY_RESULTS", length=100)
    public String studyResults;

    @Column(name = "AGE_GROUPS", length=100)
    public String ageGroups;

    @Column(name = "GENDER", length=50)
    public String gender;

    @Column(name = "ENROLLMENT", length=100)
    public String enrollment;

    @Column(name = "OTHER_IDS", length=550)
    public String otherIds;

    @Column(name = "ACRONYM", length=100)
    public String acronym;

    @Column(name = "START_DATE")
    public Date startDate;

    @Column(name = "LAST_VERIFIED")
    public Date lastVerified;

    @Column(name = "COMPLETION_DATE")
    public Date completionDate;

    @Column(name = "PRIMARY_COMPLETION_DATE")
    public Date primaryCompletionDate;

    @Column(name = "FIRST_RECEIVED")
    public Date firstReceived;

    @Column(name = "LAST_UPDATED")
    public Date lastUpdated;

    @Column(name = "OUTCOME_MEASURES", length=4000)
    public String outcomeMeasures;

    @Column(name = "URL", length=100)
    public String url;

    /*@Column(name = "LOCATIONS", length=4000)
    public String locations;*/

}
