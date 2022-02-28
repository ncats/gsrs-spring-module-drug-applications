package gov.hhs.gsrs.application.application.models.additional;

import gsrs.model.AbstractGsrsEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="SRSCID_CLINICAL_APPLICATION")
public class ClinicalTrialApplication extends AbstractGsrsEntity {

    @Id
    @Column(name="ID")
    public int id;

    @Column(name="APPLICATION_ID")
    public Long applicationId;

    @Column(name="NCT_NUMBER")
    public String nctNumber;

    @Column(name="APP_TYPE")
    public String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="CENTER")
    public String center;

    @Column(name="DECISION")
    public String decision;

    @Column(name="DECISION_DATE")
    public Date decisionDate;

    /*
    @Indexable(indexed =false)
    @ManyToOne(optional = true)
    @JoinColumn(name = "NCT_NUMBER", referencedColumnName = "NCTN")
    public ClinicalTrial clinicalTrialApp;

    public String getDecisionDate() {
        //Get value from Database
        //Convert Date to String
        return convertDateToString(this.decisionDate);
    }

    public ClinicalTrialApplication () {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClinicalTrialApplication)) return false;
        if (!super.equals(o)) return false;

        ClinicalTrialApplication that = (ClinicalTrialApplication) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
    */
}
