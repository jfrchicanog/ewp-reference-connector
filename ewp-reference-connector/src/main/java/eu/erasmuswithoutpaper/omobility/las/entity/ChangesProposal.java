package eu.erasmuswithoutpaper.omobility.las.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = ChangesProposal.findAll, query = "SELECT c FROM ChangesProposal c"),
})
public class ChangesProposal extends ListOfComponents{
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal.";
    public static final String findAll = PREFIX + "all";
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANGE_PROPOSAL_STUDENT",referencedColumnName = "ID")
	private Student student;
    
    private  String id;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANGE_PROPOSAL_APPROVED_PROPOSAL",referencedColumnName = "ID")
    private ApprovedProposal approvedProposal;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANGE_PROPOSAL_COMMENT_PROPOSAL",referencedColumnName = "ID")
    private CommentProposal commentProposal;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "changesProposal")
    private OlearningAgreement olearningAgreement;
    
	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

    public ApprovedProposal getApprovedProposal() {
        return approvedProposal;
    }

    public void setApprovedProposal(ApprovedProposal approvedProposal) {
        this.approvedProposal = approvedProposal;
    }

    public CommentProposal getCommentProposal() {
        return commentProposal;
    }

    public void setCommentProposal(CommentProposal commentProposal) {
        this.commentProposal = commentProposal;
    }

    public OlearningAgreement getOlearningAgreement() {
        return olearningAgreement;
    }

    public void setOlearningAgreement(OlearningAgreement olearningAgreement) {
        this.olearningAgreement = olearningAgreement;
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
        final ChangesProposal other = (ChangesProposal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
