package gov.nih.ncats.application.application.models;

import ix.core.SingleParent;
import ix.core.models.Indexable;
import lombok.Data;

import javax.persistence.*;

@SingleParent
@Data
@Entity
@Table(name="SRSCID_APP_INDICATION_SRS")
public class ApplicationIndication extends ApplicationCommanData {

    @Id
    @SequenceGenerator(name="appIndSeq", sequenceName="SRSCID_SQ_APP_INDICATION_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "appIndSeq")
    @Column(name="APP_INDICATION_ID")
    public Long id;

    @Indexable(facet = true, name = "Indication")
    @Column(name="INDICATION")
    public String indication;

    /*
    @Column(name = "CREATED_BY")
    public String createdBy;

    @Column(name = "MODIFIED_BY")
    public String modifiedBy;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @CreatedDate
    @Indexable( name = "Create Date", sortable=true)
    @Column(name = "CREATE_DATE")
    private Date creationDate;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @LastModifiedDate
    @Indexable( name = "Last Modified Date", sortable=true)
    @Column(name = "MODIFY_DATE")
    private Date lastModifiedDate;
    */

    public ApplicationIndication () {}
}
