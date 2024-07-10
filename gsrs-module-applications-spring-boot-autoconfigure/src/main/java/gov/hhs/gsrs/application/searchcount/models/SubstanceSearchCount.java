package gov.hhs.gsrs.application.searchcount.models;

import gsrs.model.AbstractGsrsEntity;

import ix.core.models.Backup;
import ix.core.models.IndexableRoot;
import lombok.Data;

import javax.persistence.*;

@IndexableRoot
@Backup
@Data
@Entity
@Table(name="SRSCID_SEARCH_COUNT_MV")
public class SubstanceSearchCount extends AbstractGsrsEntity {
    @Id
    @Column(name="ID")
    public String id;

    @Column(name="UUID")
    public String substanceId;

    @Column(name="UNII")
    public String unii;

    @Column(name="CODE")
    public String bdnum;

    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="APPCOUNT")
    public int appCount;

    @Column(name="CENTER")
    public String center;

    @Column(name="PROVENANCE")
    public String provenance;

    @Column(name="FROMTABLE")
    public String fromTable;

    @Column(name="PRODCOUNT")
    public int prodCount;

    @Column(name="PROD_PROVENANCE")
    public String prodProvenance;

    //@Column(name="PROD_INGREDIENT_TYPE")
    //public String prodIngredientType;

    @Column(name="CLINICALCOUNT")
    public int clinicalCount;

    @Column(name="CASECOUNT")
    public int adverseEventCount;

    @Transient
    public String appCountConcat;

    @Transient
    public String prodCountConcat;

    public SubstanceSearchCount () {}

}

