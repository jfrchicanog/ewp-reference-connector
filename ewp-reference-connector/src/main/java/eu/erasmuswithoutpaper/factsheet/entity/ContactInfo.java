package eu.erasmuswithoutpaper.factsheet.entity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = ContactInfo.findAll, query = "SELECT c FROM ContactInfo c")
})
public class ContactInfo implements Serializable {
    private static final String PREFIX = "eu.erasmuswithoutpaper.factsheet.entity.ContactInfo.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String email;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "FACTSHEET_CONTACT_URL")
    private List<LanguageItem> url;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PHONE_NUMBER_FACTSHEET_CONTACT")
    private eu.erasmuswithoutpaper.organization.entity.PhoneNumber phoneNumber;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LanguageItem> getUrl() {
        return url;
    }

    public void setUrl(List<LanguageItem> url) {
        this.url = url;
    }

    public eu.erasmuswithoutpaper.organization.entity.PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(eu.erasmuswithoutpaper.organization.entity.PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
    public int hashCode() {
        int hash = 7;
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
        final ContactInfo other = (ContactInfo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
