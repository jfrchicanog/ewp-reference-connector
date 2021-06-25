
package eu.erasmuswithoutpaper.omobility.las.entity;

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
    @NamedQuery(name = ComponentsStudied.findAll, query = "SELECT c FROM ComponentsStudied c"),
})
public class ComponentsStudied implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.mobility.las.entity.ComponentsStudied.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String losId;
    private String losCode;
    private String title;
    private String loiId;
    private String academicTermDisplayName;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENT_STUDIED_CREDIT")
    private List<Credit> credit;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getLosId() {
		return losId;
	}

	public void setLosId(String losId) {
		this.losId = losId;
	}

	public String getLosCode() {
		return losCode;
	}

	public void setLosCode(String losCode) {
		this.losCode = losCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLoiId() {
		return loiId;
	}

	public void setLoiId(String loiId) {
		this.loiId = loiId;
	}

	public String getAcademicTermDisplayName() {
		return academicTermDisplayName;
	}

	public void setAcademicTermDisplayName(String academicTermDisplayName) {
		this.academicTermDisplayName = academicTermDisplayName;
	}

	public List<Credit> getCredit() {
		return credit;
	}

	public void setCredit(List<Credit> credit) {
		this.credit = credit;
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
        final ComponentsStudied other = (ComponentsStudied) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
