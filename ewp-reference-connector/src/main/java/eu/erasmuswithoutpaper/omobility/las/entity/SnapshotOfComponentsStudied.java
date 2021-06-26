package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = SnapshotOfComponentsStudied.findAll, query = "SELECT m FROM SnapshotOfComponentsStudied m")
})
public class SnapshotOfComponentsStudied implements Serializable{

	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.SnapshotOfComponentsStudied.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENT_STUDIED_SNAPSHOT")
    private List<ComponentsStudied> componentStudied;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "APPROVAL_STUDIED_SNAPSHOT")
    private List<Approval> approval;
   
    @ElementCollection
    @CollectionTable(name="SHOULD_BE_APPROVED_BY", joinColumns=@JoinColumn(name="ID"))
    private List<String> shouldNowBeApprovedBy;
    
    private Date inEffectSince;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ComponentsStudied> getComponentStudied() {
		return componentStudied;
	}

	public void setComponentStudied(List<ComponentsStudied> componentStudied) {
		this.componentStudied = componentStudied;
	}

	public List<Approval> getApproval() {
		return approval;
	}

	public void setApproval(List<Approval> approval) {
		this.approval = approval;
	}

	public List<String> getShouldNowBeApprovedBy() {
		return shouldNowBeApprovedBy;
	}

	public void setShouldNowBeApprovedBy(List<String> shouldNowBeApprovedBy) {
		this.shouldNowBeApprovedBy = shouldNowBeApprovedBy;
	}

	public Date getInEffectSince() {
		return inEffectSince;
	}

	public void setInEffectSince(Date inEffectSince) {
		this.inEffectSince = inEffectSince;
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
        final SnapshotOfComponentsStudied other = (SnapshotOfComponentsStudied) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
