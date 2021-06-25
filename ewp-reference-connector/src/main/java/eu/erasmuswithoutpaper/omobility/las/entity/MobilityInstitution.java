
package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = MobilityInstitution.findAll, query = "SELECT m FROM MobilityInstitution m"),
    @NamedQuery(name = MobilityInstitution.findByHeiId, query = "SELECT m FROM MobilityInstitution m WHERE m.heiId=:heiId"),
    @NamedQuery(name = MobilityInstitution.findByOrganizationalUnitId, query = "SELECT m FROM MobilityInstitution m WHERE m.ounitId=:ounitId"),
})

public class MobilityInstitution implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution.";
    public static final String findAll = PREFIX + "all";
    public static final String findByHeiId = PREFIX + "findByHeiId";
    public static final String findByOrganizationalUnitId = PREFIX + "findByOrganizationalUnitId";
    
    private String heiId;
    private String ounitId;
    
    private String ounitName;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "OMOBILITY_LAS_CONTACT_PERSON",referencedColumnName = "ID")
    ContactPerson contactPerson;

	public String getHeiId() {
		return heiId;
	}

	public void setHeiId(String heiId) {
		this.heiId = heiId;
	}

	public String getOunitName() {
		return ounitName;
	}

	public void setOunitName(String ounitName) {
		this.ounitName = ounitName;
	}

	public String getOunitId() {
		return ounitId;
	}

	public void setOunitId(String ounitId) {
		this.ounitId = ounitId;
	}

	public ContactPerson getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(ContactPerson contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.heiId);
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
        final MobilityInstitution other = (MobilityInstitution) obj;
        if (!Objects.equals(this.heiId, other.heiId) && !Objects.equals(this.ounitId, other.ounitId)) {
            return false;
        }
        return true;
    }

}
