package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity(name="CooperationCondLanguageSkill")
@NamedQuery(name = LanguageSkill.findAll, query = "SELECT ls FROM CooperationCondLanguageSkill ls")
public class LanguageSkill implements Serializable {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.LanguageSkill.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String language;
    private String cefrLevel;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBJECT_AREA")
    private SubjectArea subjectArea;
    
    public LanguageSkill() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LanguageSkill(String cefrLevel, String language) {
        this.cefrLevel = cefrLevel;
        this.language = language;
    }

    public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCefrLevel() {
		return cefrLevel;
	}

	public void setCefrLevel(String cefrLevel) {
		this.cefrLevel = cefrLevel;
	}

	public SubjectArea getSubjectArea() {
		return subjectArea;
	}

	public void setSubjectArea(SubjectArea subjectArea) {
		this.subjectArea = subjectArea;
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
        final LanguageSkill other = (LanguageSkill) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
