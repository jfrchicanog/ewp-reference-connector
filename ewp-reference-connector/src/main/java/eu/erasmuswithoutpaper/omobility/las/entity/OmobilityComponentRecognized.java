package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity(name="OmibilityComponentRecognized")
@NamedQuery(name = OmobilityComponentRecognized.findAll, query = "SELECT o FROM OmibilityComponentRecognized o")
public class OmobilityComponentRecognized implements Serializable {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.OmibilityComponentRecognized.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String losId;
    private String loiId;;
    
    public OmobilityComponentRecognized() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OmobilityComponentRecognized(String loiId, String losId) {
        this.losId = losId;
        this.loiId = loiId;
    }

	public String getLosId() {
		return losId;
	}

	public void setLosId(String losId) {
		this.losId = losId;
	}

	public String getLoiId() {
		return loiId;
	}

	public void setLoiId(String loiId) {
		this.loiId = loiId;
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
        final OmobilityComponentRecognized other = (OmobilityComponentRecognized) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
