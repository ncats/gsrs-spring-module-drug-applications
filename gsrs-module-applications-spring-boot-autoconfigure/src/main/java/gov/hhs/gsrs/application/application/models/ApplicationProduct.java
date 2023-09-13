package gov.hhs.gsrs.application.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ix.core.SingleParent;
import ix.core.models.Indexable;
import ix.core.models.ParentReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SingleParent
@Data
@Entity
@Table(name="SRSCID_PRODUCT_SRS")
public class ApplicationProduct extends ApplicationCommanData {

    @Id
    @SequenceGenerator(name="prodsrsSeq", sequenceName="SRSCID_SQ_PRODUCT_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prodsrsSeq")
    @Column(name = "PRODUCT_ID")
    public Long id;

    @Indexable(facet = true, name = "Dosage Form")
    @Column(name="DOSAGE_FORM")
    public String dosageForm;

    @Column(name="ROUTE_OF_ADMINISTRATION")
    public String routeAdmin;

    @Column(name="UNIT_PRESENTATION")
    public String unitPresentation;

    @Column(name="AMOUNT")
    public Double amount;

    @Column(name="UNIT")
    public String unit;

    @Column(name="REVIEWED_BY")
    public String reviewedBy;

    @Column(name="REVIEW_DATE")
    public Date reviewDate;

    @Indexable(indexed=false)
    @ParentReference
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="APPLICATION_ID",referencedColumnName="APPLICATION_ID")
    public Application owner;

    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="owner")
    public List<ApplicationProductName> applicationProductNameList = new ArrayList<>();

    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="owner")
    public List<ApplicationIngredient> applicationIngredientList = new ArrayList<>();

    public void setApplicationProductNameList(List<ApplicationProductName> applicationProductNameList) {
        this.applicationProductNameList = applicationProductNameList;
        if(applicationProductNameList !=null) {
            for (ApplicationProductName appProdNme : applicationProductNameList)
            {
                appProdNme.setOwner(this);
            }
        }
    }

    public void setApplicationIngredientList(List<ApplicationIngredient> applicationIngredientList) {
        this.applicationIngredientList = applicationIngredientList;
        if(applicationIngredientList !=null) {
            for (ApplicationIngredient appProdIngred : applicationIngredientList)
            {
                appProdIngred.setOwner(this);
            }
        }
    }

    public void setOwner(Application application) {
        this.owner = application;
    }
}
