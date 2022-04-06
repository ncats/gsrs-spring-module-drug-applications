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
@IdClass(SearchCountCompositePrimaryKeyId.class)
@Table(name="SRSCID_SEARCH_COUNT_MV")
public class SubstanceSearchCount extends AbstractGsrsEntity {

    @Id
    public String substanceId;

    @Id
    public String provenance;

    @Id
    public String fromTable;

    @Column(name="UNII")
    public String unii;

    @Column(name="CODE")
    public String bdnum;

    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="APPCOUNT")
    public int appCount;

    @Column(name="PRODACTIVECOUNT")
    public int prodActiveCount;

    @Column(name="PRODINACTIVECOUNT")
    public int prodInactiveCount;

    @Column(name="CLINICALCOUNT")
    public int clinicalCount;

    @Column(name="CASECOUNT")
    public int adverseEventCount;

    @Column(name="CENTER")
    public String center;

    @Transient
    public String appCountConcat;

    public SubstanceSearchCount () {}

}

