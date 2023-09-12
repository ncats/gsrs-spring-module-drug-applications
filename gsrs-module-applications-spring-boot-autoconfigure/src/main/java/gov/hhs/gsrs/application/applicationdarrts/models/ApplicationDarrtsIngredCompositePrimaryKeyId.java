package gov.hhs.gsrs.application.applicationdarrts.models;

import javax.persistence.Column;
import java.io.Serializable;

public class ApplicationDarrtsIngredCompositePrimaryKeyId implements Serializable {

    @Column(name="APP_TYPE")
    private String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Column(name="PRODUCT_NO")
    public String productNo;

    public ApplicationDarrtsIngredCompositePrimaryKeyId() {};

    // default constructor
    public ApplicationDarrtsIngredCompositePrimaryKeyId(String appType, String appNumber, String substanceKey, String productNo) {
        this.appType = appType;
        this.appNumber = appNumber;
        this.substanceKey = substanceKey;
        this.productNo = productNo;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationDarrtsIngredCompositePrimaryKeyId that = (ApplicationDarrtsIngredCompositePrimaryKeyId) o;

        if (!appType.equals(that.appType)) return false;
        if (!appNumber.equals(that.appNumber)) return false;
        if (!substanceKey.equals(that.substanceKey)) return false;
        return (productNo.equals(that.productNo));
    }

    @Override
    public int hashCode() {
        int result =  appType.hashCode();
        result = 31 * result + (appNumber != null ? appNumber.hashCode() : 0);
        result = 31 * result + (substanceKey != null ? substanceKey.hashCode() : 0);
        result = 31 * result + (productNo != null ? productNo.hashCode() : 0);
        return (int) result;
    }

}