package gov.nih.ncats.application.applicationdarrts.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.nih.ncats.application.application.models.ApplicationProduct;
import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.models.Indexable;
import ix.core.models.IxModel;
import ix.core.search.text.TextIndexerEntityListener;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;

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
