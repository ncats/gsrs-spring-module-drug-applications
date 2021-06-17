package gov.nih.ncats.application.applicationall.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.ncats.application.applicationall.models.*;
import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
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
@Table(name="SRSCID_APPLICATION_ALL_MV", schema = "srscid")
public class ApplicationAll extends AbstractGsrsEntity {

    @Id
    @Column(name="APPLICATION_ID")
    public String id;

    @Column(name="APP_TYPE")
    public String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="SPONSOR_NAME")
    public String sponsorName;

    @Column(name="STATUS")
    public String appStatus;

    @Column(name="DIVISION_CLASS_DESC")
    public String divisionClassDesc;

    @Column(name="APP_SUB_TYPE")
    public String appSubType;

    @Indexable(facet=true, name = "Center")
    @Column(name="CENTER")
    public String center;

    @Indexable(facet=true, name = "Source")
    @Column(name="FROMTABLE")
    public String fromTable;

    @Column(name="IN_DARRTS_DETAIL")
    public String inDarrtsDetail;

    @Indexable(facet=true, name = "Provenance")
    @Column(name="PROVENANCE")
    public String provenance;

    public String getId() {
        return id;
    }

    @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "APPLICATION_ID")
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProductSrsAll> applicationProductList = new ArrayList<>();

    @Indexable(indexed=false)
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="APPLICATION_ID", referencedColumnName="APPLICATION_ID")
    public List<AppIndicationAll> indicationList = new ArrayList<>();

    public ApplicationAll () {}

    @JsonIgnore
    @Indexable(facet=true, name="Deprecated")
    public String getDeprecated(){
        return "Not Deprecated";
    }

    /*
    @JsonIgnore
    @Indexable(facet=true, name= "Ingredient Type")
    public List<String> getIndexedIngredientTypes(){
    	if(this.ingredientType!=null){
    		return Arrays.asList(this.ingredientType.split("\\|"));
    	}
    	return new ArrayList<String>(); 
    }
    */
}
