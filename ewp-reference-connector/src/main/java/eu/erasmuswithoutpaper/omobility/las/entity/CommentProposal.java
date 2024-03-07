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
    @NamedQuery(name = CommentProposal.findAll, query = "SELECT la FROM CommentProposal la"),
})

public class CommentProposal implements Serializable{
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.CommentProposal.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String omobilityId;
    
    private String comment;
    
    private String changesProposalId;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_PROPOSAL_SIGNATURE",referencedColumnName = "ID")
    private Signature signature;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "commentProposal")
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
        final CommentProposal other = (CommentProposal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
