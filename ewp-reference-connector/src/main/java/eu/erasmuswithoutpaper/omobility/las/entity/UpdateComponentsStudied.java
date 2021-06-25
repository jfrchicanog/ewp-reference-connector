package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = UpdateComponentsStudied.findAll, query = "SELECT la FROM UpdateComponentsStudied la"),
})

public class UpdateComponentsStudied implements Serializable{
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.UpdateComponentsStudied.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String omobilityId;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENTL_LATEST_DRAFT_SNAPSHOT",referencedColumnName = "ID")
    private SnapshotOfComponentsStudied currentLatestDraftSnapshot;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "SUGGESTED_CHANGES",referencedColumnName = "ID")
    protected OmobilityComponentRecognized suggestedChanges;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "SNAPSHOT_WITH_CHANGES_APPLIED",referencedColumnName = "ID")
    private SnapshotOfComponentsStudied snapshotWithChangesApplied;

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

	public SnapshotOfComponentsStudied getCurrentLatestDraftSnapshot() {
		return currentLatestDraftSnapshot;
	}

	public void setCurrentLatestDraftSnapshot(SnapshotOfComponentsStudied currentLatestDraftSnapshot) {
		this.currentLatestDraftSnapshot = currentLatestDraftSnapshot;
	}

	public OmobilityComponentRecognized getSuggestedChanges() {
		return suggestedChanges;
	}

	public void setSuggestedChanges(OmobilityComponentRecognized suggestedChanges) {
		this.suggestedChanges = suggestedChanges;
	}

	public SnapshotOfComponentsStudied getSnapshotWithChangesApplied() {
		return snapshotWithChangesApplied;
	}

	public void setSnapshotWithChangesApplied(SnapshotOfComponentsStudied snapshotWithChangesApplied) {
		this.snapshotWithChangesApplied = snapshotWithChangesApplied;
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
        final UpdateComponentsStudied other = (UpdateComponentsStudied) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
