
package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.organization.entity.Contact;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/*@Entity
@NamedQueries({
    @NamedQuery(name = IiaPartner_OLD.findAll, query = "SELECT i FROM IiaPartner i"),
})*/
public class IiaPartner_OLD implements Serializable{
/*
    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.IiaPartner.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String institutionId;
    private String organizationUnitId;
    
    private String iiaId;
    private String iiaCode;
    
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "IIA_PARTNER_CONTACTS")
    private List<Contact> contacts;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SINGING_CONTACT_PARTNER")
    private Contact signingContact;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Contact getSigningContact() {
		return signingContact;
	}

	public void setSigningContact(Contact signingContact) {
		this.signingContact = signingContact;
	}

	public String getIiaId() {
		return iiaId;
	}

	public void setIiaId(String iiaId) {
		this.iiaId = iiaId;
	}

	public String getIiaCode() {
		return iiaCode;
	}

	public void setIiaCode(String iiaCode) {
		this.iiaCode = iiaCode;
	}

	@Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final IiaPartner_OLD other = (IiaPartner_OLD) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }*/
}
