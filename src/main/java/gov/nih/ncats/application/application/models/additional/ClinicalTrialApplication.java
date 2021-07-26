package gov.nih.ncats.application.application.models.additional;

import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.IxModel;
import ix.core.search.text.TextIndexerEntityListener;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;

import ix.ginas.models.v1.Name;
import ix.ginas.models.v1.Substance;
import ix.ginas.models.v1.SubstanceReference;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Data
@Entity
@Table(name="SRSCID_CLINICAL_APPLICATION", schema = "srscid")
public class ClinicalTrialApplication extends AbstractGsrsEntity {

    @Id
    @Column(name="ID")
    public int id;

    @Column(name="APPLICATION_ID")
    public String applicationId;

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
