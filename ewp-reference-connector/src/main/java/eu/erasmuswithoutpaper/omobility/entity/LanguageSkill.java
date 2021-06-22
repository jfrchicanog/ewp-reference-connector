package eu.erasmuswithoutpaper.omobility.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity(name="LanguageSkill")
@NamedQuery(name = LanguageSkill.findAll, query = "SELECT ls FROM LanguageSkill ls")
public class LanguageSkill implements Serializable {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.LanguageSkill.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String language;
    private String cefrLevel;
    
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
