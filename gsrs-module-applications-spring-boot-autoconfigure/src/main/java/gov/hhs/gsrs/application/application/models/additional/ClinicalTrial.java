package gov.hhs.gsrs.application.application.models.additional;

import com.fasterxml.jackson.annotation.JsonProperty;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="ctrial_us")
//@Table(name="ct_clinical_trial")
public class ClinicalTrial extends AbstractGsrsEntity {

    @Id
    @Column(name = "TRIAL_NUMBER", length=255)
    @JsonProperty("nctNumber")
    public String nctn;

    @Lob
    @Indexable(sortable = true)
    @Column(name = "TITLE")
    public String title;

    @Column(name = "URL", length=2000)
    public String url;

    @Lob
    @Column(name = "RECRUITMENT", length=4000)
    public String recruitment;

    @Lob
    @Column(name = "RESULTS_FIRST_RECEIVED", length=4000)
    public String resultsFirstRecieved;

    @Lob
    @Indexable(sortable = true)
    @Column(name = "CONDITIONS", length=4000)
    public String conditions;

    @Lob
    @Column(name = "INTERVENTION", length=4000)
    public String intervention;

    @Lob
    @Indexable(sortable = true)
    @Column(name = "SPONSOR", length=4000)
    public String sponsor;

    @Lob
    @Column(name = "PHASES", length=2000)
    public String phases;

    @Lob
    @Column(name = "FUNDED_BYS", length=100)
    public String fundedBys;

    @Lob
    @Indexable(facet = true, name = "Study Types")
    @Column(name = "STUDY_TYPES", length=2000)
    public String studyTypes;

    @Lob
    @Column(name = "STUDY_DESIGNS", length=2000)
    public String studyDesigns;

    @Lob
    @Column(name = "STUDY_RESULTS", length=4000)
    @Indexable(facet = true, name = "Study Results")
    public String studyResults;

    @Column(name = "AGE_GROUPS", length=50)
    public String ageGroups;


    @Column(name = "GENDER", length=50)
    @Indexable(facet= true, name = "Gender")
    public String gender;

    @Lob
    @Column(name = "ENROLLMENT", length=2000)
    public String enrollment;

    @Lob
    @Column(name = "OTHER_IDS", length=500)
    public String otherIds;

    @Lob
    @Column(name = "ACRONYM", length=4000)
    public String acronym;

    @Column(name = "START_DATE")
    @Indexable(name = "Start Date at Source", sortable=true)

    public Date startDate;

    @Column(name = "LAST_VERIFIED")
    @Indexable(name = "Last Verified Date at Source", sortable=true)
    public Date lastVerified;

    @Column(name = "COMPLETION_DATE")
    @Indexable(name = "Completion Date at Source", sortable=true)
    public Date completionDate;

    @Column(name = "PRIMARY_COMPLETION_DATE")
    @Indexable(name = "Primary Completion Date at Source", sortable=true)
    public Date primaryCompletionDate;

    @Column(name = "FIRST_RECEIVED")
    @Indexable(name = "First Received Date at Source", sortable=true)
    public Date firstReceived;

    @Column(name = "LAST_UPDATED")
    @Indexable(name = "Last Updated at Source", sortable=true)
    public Date lastUpdated;

    @Lob
    @Column(name = "OUTCOME_MEASURES", length=4000)
    public String outcomeMeasures;

    /*
    @Lob
    @Column(name = "LOCATIONS", length=4000)
    public String locations;
    */

}
