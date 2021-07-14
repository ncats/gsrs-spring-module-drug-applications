package gov.nih.ncats.application.applicationdarrts.models;

import javax.persistence.Column;
import java.io.Serializable;

public class ApplicationDarrtsCompositePrimaryKeyId implements Serializable {

    @Column(name="APP_TYPE")
    private String appType;

    @Column(name="APP_NUMBER")
    public String appNumber;

    public ApplicationDarrtsCompositePrimaryKeyId() {};

    // default constructor
    public ApplicationDarrtsCompositePrimaryKeyId(String appType, String appNumber) {
        this.appType = appType;
        this.appNumber = appNumber;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationDarrtsCompositePrimaryKeyId that = (ApplicationDarrtsCompositePrimaryKeyId) o;

        if (!appType.equals(that.appType)) return false;
        return (appNumber.equals(that.appNumber));
    }

    @Override
    public int hashCode() {
        int result =  appType.hashCode();
        result = 31 * result + (appNumber != null ? appNumber.hashCode() : 0);
        return (int) result;
    }

}