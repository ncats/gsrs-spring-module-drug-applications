package gov.nih.ncats.application.models;

import gsrs.GsrsEntityProcessorListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.models.Indexable;
import ix.core.models.IxModel;
import ix.core.search.text.TextIndexerEntityListener;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@MappedSuperclass
public class ApplicationCommanData extends AbstractGsrsEntity {

  //  @Id
  //  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appSeq")
  //  public Long id;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

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

    @Version
    @Column(name = "INTERNAL_VERSION")
    public Long internalVersion;

    public ApplicationCommanData () {
    }

    @PrePersist
    public void prePersist() {
      //  Date currentDate = TimeUtil.getCurrentDate();
     /*
        Principal p1=UserFetcher.getActingUser();
        if (p1 != null) {
            this.createdBy = p1.username;
        }

        this.createDate = currentDate;
      */
        this.createdBy = "ADMIN";
    }


    @PreUpdate
    public void preUpdate() {
        /*
        Date currentDate = TimeUtil.getCurrentDate();
        Principal p1=UserFetcher.getActingUser();
        if (p1 != null) {
            this.modifiedBy = p1.username;
        }

        this.modifyDate = currentDate;
         */
        this.modifiedBy = "ADMIN";
    }

    /*
    public String getCreatedBy () {
        //Get from Database
        return this.createdBy;
    }

    /*
    public void setCreatedBy (String createdBy) {
        //Store to Database
        if (createdBy == null) {
            Principal p1 = UserFetcher.getActingUser();
            if (p1 != null) {
                this.createdBy = p1.username;
            }
        }
        else {
            this.createdBy = createdBy;
        }
    }
    */

    public Date getCreationDate() {
        //Get from Database
        return this.creationDate;
    }

    /*
    public void setCreateDate(Date createDate) {

        if (createDate == null) {
            this.createDate = TimeUtil.getCurrentDate();
        }
        else {
            this.createDate = createDate;
        }
    }
    */

    public String getModifiedBy () {
        return this.modifiedBy;
    }
    /*
    public void setModifiedBy (String modifiedBy) {
        //Store to Database
        Principal p1 = UserFetcher.getActingUser();
        if (p1 != null) {
            this.modifiedBy = p1.username;
        }
    }
    */

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    /*
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = TimeUtil.getCurrentDate();
    }
    */


    /*
    public Date getCreated() {
        return createDate;
    }

    public void setCreated(Date created) {
        this.createDate = created;
    }
    */

    public String convertDateToString(Date dtDate) {

        String strDate = null;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            if (dtDate != null) {
                strDate = df.format(dtDate);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return strDate;
    }

    public Date convertStringToDate(String strDate) {

        Date dtDate = null;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            if ((strDate != null) && (strDate.length() > 0)) {
                dtDate = df.parse(strDate);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return dtDate;
    }

}
