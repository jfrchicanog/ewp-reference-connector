package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = ApprovedProposal.findAll, query = "SELECT m FROM ApprovedProposal m")
})
public class ApprovedProposal implements Serializable{
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.ApprovedProposal.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String omobilityId;
    
    private String changesProposalId;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "APPROVED_PROPOSAL_SIGNATURE",referencedColumnName = "ID")
    private Signature signature;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "approvedProposal")
    private ChangesProposal changesProposal;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOmobilityId() {
		return omobilityId;
	}

	public void setOmobilityId(String omobilityId) {
		this.omobilityId = omobilityId;
	}

	public String getChangesProposalId() {
		return changesProposalId;
	}

	public void setChangesProposalId(String changesProposalId) {
		this.changesProposalId = changesProposalId;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

    public ChangesProposal getChangesProposal() {
        return changesProposal;
    }

    public void setChangesProposal(ChangesProposal changesProposal) {
        this.changesProposal = changesProposal;
    }

	@Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final ApprovedProposal other = (ApprovedProposal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
