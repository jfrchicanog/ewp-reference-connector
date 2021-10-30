package eu.erasmuswithoutpaper.omobility.las.entity;

import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = TermId.findAll, query = "SELECT t FROM TermId t"),
})
public class TermId {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.TermId.";
    public static final String findAll = PREFIX + "all";
	
	@Id
    @Column(updatable = false)
    String id;
	
    private BigInteger termNumber;
    private BigInteger totalTerms;
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public BigInteger getTermNumber() {
		return termNumber;
	}
	
	public void setTermNumber(BigInteger termNumber) {
		this.termNumber = termNumber;
	}
	
	public BigInteger getTotalTerms() {
		return totalTerms;
	}
	
	public void setTotalTerms(BigInteger totalTerms) {
		this.totalTerms = totalTerms;
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
        final TermId other = (TermId) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
