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
    @NamedQuery(name = OmobilityLasUpdateRequest.findAll, query = "SELECT m FROM OmobilityLasUpdateRequest m")
})
public class OmobilityLasUpdateRequest implements Serializable{
	
	    private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.OmobilityLasUpdateRequest.";
	    public static final String findAll = PREFIX + "all";
	    
	    @Id
	    @GeneratedValue(generator="system-uuid")
	    String id;
	    
	    private String sendingHeiId;
	    
	    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	    @JoinColumn(name = "APPROVE_COMPONENT_STUDIE_DRAFT",referencedColumnName = "ID")
	    private ApproveComponentsStudiedDraft approveComponentsStudiedDraft;
	    
	    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	    @JoinColumn(name = "UPDATE_COMPONENT_STUDIED",referencedColumnName = "ID")
	    private UpdateComponentsStudied updateComponentsStudied;
	    
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getSendingHeiId() {
	        return sendingHeiId;
	    }

	    public void setSendingHeiId(String sendingHeiId) {
	        this.sendingHeiId = sendingHeiId;
	    }

	    public ApproveComponentsStudiedDraft getApproveComponentsStudiedDraft() {
			return approveComponentsStudiedDraft;
		}

		public void setApproveComponentsStudiedDraft(ApproveComponentsStudiedDraft approveComponentsStudiedDraft) {
			this.approveComponentsStudiedDraft = approveComponentsStudiedDraft;
		}

		public UpdateComponentsStudied getUpdateComponentsStudied() {
			return updateComponentsStudied;
		}

		public void setUpdateComponentsStudied(UpdateComponentsStudied updateComponentsStudied) {
			this.updateComponentsStudied = updateComponentsStudied;
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
	        final OmobilityLasUpdateRequest other = (OmobilityLasUpdateRequest) obj;
	        if (!Objects.equals(this.id, other.id)) {
	            return false;
	        }
	        return true;
	    }
}
