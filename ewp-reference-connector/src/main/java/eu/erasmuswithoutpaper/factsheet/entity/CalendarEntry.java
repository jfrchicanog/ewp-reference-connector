
package eu.erasmuswithoutpaper.factsheet.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = CalendarEntry.findAll, query = "SELECT c FROM CalendarEntry c"),
})
public class CalendarEntry implements Serializable{

    private static final String PREFIX = "eu.erasmuswithoutpaper.factsheet.entity.CalendarEntry.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String factsheetId;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date autumnTerm;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date springTerm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstitutionId() {
        return factsheetId;
    }

    public void setInstitutionId(String institutionId) {
        this.factsheetId = institutionId;
    }

    public String getFactsheetId() {
		return factsheetId;
	}

	public void setFactsheetId(String factsheetId) {
		this.factsheetId = factsheetId;
	}

	public Date getAutumnTerm() {
		return autumnTerm;
	}

	public void setAutumnTerm(Date autumnTerm) {
		this.autumnTerm = autumnTerm;
	}

	public Date getSpringTerm() {
		return springTerm;
	}

	public void setSpringTerm(Date springTerm) {
		this.springTerm = springTerm;
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final CalendarEntry other = (CalendarEntry) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
