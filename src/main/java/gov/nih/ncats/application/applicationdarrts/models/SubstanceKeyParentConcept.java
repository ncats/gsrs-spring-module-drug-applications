package ix.srs.models;

import java.util.*;
import play.db.ebean.*;
import javax.persistence.*;
import play.db.ebean.Model;


@Entity
@Table(name="SRSCID_BDNUM_NAME_CONCEPT_V")
public class BdnumNameConcept extends Model {

    @Id
    @Column(name="ID")
    public String id;

    @Column(name="UNII")
    public String unii;

    @Column(name="BDNUM")
    public String bdnum;

    @Column(name="NAME")
    public String name;

    @Column(name="DISPLAY_TERM")
    public String displayTerm;

    @Column(name="PARENT_BDNUM")
    public String parentBdnum;

    @Column(name="PARENT_DISPLAY_TERM")
    public String parentDisplayTerm;

    public BdnumNameConcept () {}
}
