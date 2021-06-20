package eu.erasmuswithoutpaper.factsheet.entity;

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
    @NamedQuery(name = Accessibility.findAll, query = "SELECT a FROM Accessibility a")
})
public class Accessibility implements Serializable {
    private static final String PREFIX = "eu.erasmuswithoutpaper.factsheet.entity.Accessibility.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String name;
    private String description;
    private AccessibilityType type;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "INFO", referencedColumnName = "ID")
    private ContactInfo information;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccessibilityType getType() {
		return type;
	}

	public void setType(AccessibilityType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ContactInfo getInformation() {
		return information;
	}

	public void setInformation(ContactInfo information) {
		this.information = information;
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
        final Accessibility other = (Accessibility) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
