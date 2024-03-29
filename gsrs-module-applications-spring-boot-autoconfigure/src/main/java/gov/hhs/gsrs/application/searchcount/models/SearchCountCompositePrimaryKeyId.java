package gov.hhs.gsrs.application.searchcount.models;

import javax.persistence.Column;
import java.io.Serializable;

public class SearchCountCompositePrimaryKeyId implements Serializable {

    @Column(name="UUID")
    public String substanceId;

    @Column(name="PROVENANCE")
    public String provenance;

    @Column(name="FROMTABLE")
    public String fromTable;

    public SearchCountCompositePrimaryKeyId() {};

    // default constructor
    public SearchCountCompositePrimaryKeyId(String substanceId, String provenance, String fromTable) {
        this.substanceId = substanceId;
        this.provenance = provenance;
        this.fromTable = fromTable;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchCountCompositePrimaryKeyId that = (SearchCountCompositePrimaryKeyId) o;

        if (!substanceId.equals(that.substanceId)) return false;
        if (!fromTable.equals(that.fromTable)) return false;
        return (provenance.equals(that.provenance));
    }

    @Override
    public int hashCode() {
        int result =  substanceId.hashCode();
        result = 31 * result + (provenance != null ? provenance.hashCode() : 0);
        result = 31 * result + (fromTable != null ? fromTable.hashCode() : 0);
        return (int) result;
    }

}
