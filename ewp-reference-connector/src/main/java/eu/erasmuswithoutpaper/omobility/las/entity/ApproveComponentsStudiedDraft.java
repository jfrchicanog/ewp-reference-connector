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
    @NamedQuery(name = ApproveComponentsStudiedDraft.findAll, query = "SELECT m FROM ApproveComponentsStudiedDraft m")
})
public class ApproveComponentsStudiedDraft implements Serializable{
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.ApproveComponentsStudiedDraft.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String omobilityId;
    
    private String approvingParty;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENT_LATEST_DRAFT_SNAPSHOT",referencedColumnName = "ID")
    private SnapshotOfComponentsStudied currentLatestDraftSnapshot;

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

	public String getApprovingParty() {
		return approvingParty;
	}

	public void setApprovingParty(String approvingParty) {
		this.approvingParty = approvingParty;
	}

	public SnapshotOfComponentsStudied getCurrentLatestDraftSnapshot() {
		return currentLatestDraftSnapshot;
	}

	public void setCurrentLatestDraftSnapshot(SnapshotOfComponentsStudied currentLatestDraftSnapshot) {
		this.currentLatestDraftSnapshot = currentLatestDraftSnapshot;
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
        final ApproveComponentsStudiedDraft other = (ApproveComponentsStudiedDraft) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
