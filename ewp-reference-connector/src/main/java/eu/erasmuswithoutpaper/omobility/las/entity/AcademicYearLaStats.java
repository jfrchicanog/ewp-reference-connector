package eu.erasmuswithoutpaper.omobility.las.entity;

import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = AcademicYearLaStats.findAll, query = "SELECT e FROM AcademicYearLaStats e"),
})
public class AcademicYearLaStats {
	
     private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.AcademicYearLaStats.";
     public static final String findAll = PREFIX + "all";
	
	@Id
    @GeneratedValue(generator="system-uuid")
    String id;
	
     protected String receivingAcademicYearId;
     protected BigInteger laOutgoingTotal;
     protected BigInteger laOutgoingNotModifiedAfterApproval;
     protected BigInteger laOutgoingModifiedAfterApproval;
     protected BigInteger laOutgoingLatestVersionApproved;
     protected BigInteger laOutgoingLatestVersionRejected;
     protected BigInteger laOutgoingLatestVersionAwaiting;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceivingAcademicYearId() {
		return receivingAcademicYearId;
	}
	public void setReceivingAcademicYearId(String receivingAcademicYearId) {
		this.receivingAcademicYearId = receivingAcademicYearId;
	}
	public BigInteger getLaOutgoingTotal() {
		return laOutgoingTotal;
	}
	public void setLaOutgoingTotal(BigInteger laOutgoingTotal) {
		this.laOutgoingTotal = laOutgoingTotal;
	}
	public BigInteger getLaOutgoingNotModifiedAfterApproval() {
		return laOutgoingNotModifiedAfterApproval;
	}
	public void setLaOutgoingNotModifiedAfterApproval(BigInteger laOutgoingNotModifiedAfterApproval) {
		this.laOutgoingNotModifiedAfterApproval = laOutgoingNotModifiedAfterApproval;
	}
	public BigInteger getLaOutgoingModifiedAfterApproval() {
		return laOutgoingModifiedAfterApproval;
	}
	public void setLaOutgoingModifiedAfterApproval(BigInteger laOutgoingModifiedAfterApproval) {
		this.laOutgoingModifiedAfterApproval = laOutgoingModifiedAfterApproval;
	}
	public BigInteger getLaOutgoingLatestVersionApproved() {
		return laOutgoingLatestVersionApproved;
	}
	public void setLaOutgoingLatestVersionApproved(BigInteger laOutgoingLatestVersionApproved) {
		this.laOutgoingLatestVersionApproved = laOutgoingLatestVersionApproved;
	}
	public BigInteger getLaOutgoingLatestVersionRejected() {
		return laOutgoingLatestVersionRejected;
	}
	public void setLaOutgoingLatestVersionRejected(BigInteger laOutgoingLatestVersionRejected) {
		this.laOutgoingLatestVersionRejected = laOutgoingLatestVersionRejected;
	}
	public BigInteger getLaOutgoingLatestVersionAwaiting() {
		return laOutgoingLatestVersionAwaiting;
	}
	public void setLaOutgoingLatestVersionAwaiting(BigInteger laOutgoingLatestVersionAwaiting) {
		this.laOutgoingLatestVersionAwaiting = laOutgoingLatestVersionAwaiting;
	}


	@Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AcademicYearLaStats other = (AcademicYearLaStats) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
