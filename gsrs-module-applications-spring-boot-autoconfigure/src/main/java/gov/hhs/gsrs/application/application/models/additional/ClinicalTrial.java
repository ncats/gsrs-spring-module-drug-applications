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
    @Column(name = "NCTN")
    @JsonProperty("nctNumber")
    public String nctn;

    @Column(name = "TITLE")
    public String title;

    @Column(name = "RECRUITMENT")
    public String recruitment;

    @Column(name = "RESULTS_FIRST_RECEIVED")
    public String resultsFirstRecieved;

    @Column(name = "CONDITIONS")
    public String conditions;

    @Column(name = "INTERVENTION")
    public String intervention;

    @Column(name = "SPONSOR")
    public String sponsor;

    @Column(name = "PHASES")
    public String phases;

    @Column(name = "FUNDED_BYS")
    public String fundedBys;

    @Indexable(facet = true, name = "Study Types")
    @Column(name = "STUDY_TYPES")
    public String studyTypes;

    @Column(name = "STUDY_DESIGNS")
    public String studyDesigns;

    @Indexable(facet = true, name = "Study Results")
    @Column(name = "STUDY_RESULTS")
    public String studyResults;

    @Column(name = "AGE_GROUPS")
    public String ageGroups;

    @Column(name = "GENDER")
    public String gender;

    @Column(name = "ENROLLMENT")
    public String enrollment;

    @Column(name = "OTHER_IDS")
    public String otherIds;

    @Column(name = "ACRONYM")
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

    @Column(name = "OUTCOME_MEASURES")
    public String outcomeMeasures;

    @Column(name = "URL")
    public String url;

    /*@Column(name = "LOCATIONS", length=4000)
    public String locations;*/

}
