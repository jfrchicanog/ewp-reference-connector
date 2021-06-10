package eu.erasmuswithoutpaper.factsheet.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = AdditionalRequirement.findAll, query = "SELECT r FROM AditionalRequirement r")
})
public class AdditionalRequirement implements Serializable {
    private static final String PREFIX = "eu.erasmuswithoutpaper.factsheet.entity.AditionalRequirement.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String name;
    private String details;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "CONTACT_URL")
    private List<LanguageItem> informationWebsite;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LanguageItem> getInformationWebsite() {
		return informationWebsite;
	}

	public void setInformationWebsite(List<LanguageItem> informationWebsite) {
		this.informationWebsite = informationWebsite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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
        final AdditionalRequirement other = (AdditionalRequirement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
