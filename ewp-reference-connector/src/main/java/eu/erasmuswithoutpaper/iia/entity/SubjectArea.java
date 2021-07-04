package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity(name="SubjectArea")
@NamedQuery(name = SubjectArea.findAll, query = "SELECT s FROM SubjectArea s")
public class SubjectArea implements Serializable {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.SubjectArea.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String iscedFCode;
    private String iscedClarification;
    
    public SubjectArea() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getIscedFCode() {
		return iscedFCode;
	}

	public void setIscedFCode(String iscedFCode) {
		this.iscedFCode = iscedFCode;
	}

	public String getIscedClarification() {
		return iscedClarification;
	}

	public void setIscedClarification(String iscedClarification) {
		this.iscedClarification = iscedClarification;
	}

	@Override
    public int hashCode() {
        int hash = 5;
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
        final SubjectArea other = (SubjectArea) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
