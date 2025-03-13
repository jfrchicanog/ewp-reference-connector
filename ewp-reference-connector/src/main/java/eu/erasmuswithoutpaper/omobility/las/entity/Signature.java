package eu.erasmuswithoutpaper.omobility.las.entity;

import java.sql.Timestamp;
import java.time.Instant;
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
    @NamedQuery(name = Signature.findAll, query = "SELECT s FROM Signature s")
})
public class Signature {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.Signature.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String signerName;
    private String signerPosition;
    private String signerEmail;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private String timeZone;
    
    private String signerApp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignerName() {
		return signerName;
	}
	
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}
	
	public String getSignerPosition() {
		return signerPosition;
	}
	
	public void setSignerPosition(String signerPosition) {
		this.signerPosition = signerPosition;
	}
	
	public String getSignerEmail() {
		return signerEmail;
	}
	
	public void setSignerEmail(String signerEmail) {
		this.signerEmail = signerEmail;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getSignerApp() {
		return signerApp;
	}
	
	public void setSignerApp(String signerApp) {
		this.signerApp = signerApp;
	}

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Signature other = (Signature) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Signature{" + "id=" + id + ", signerName=" + signerName + ", signerPosition=" + signerPosition + ", signerEmail=" + signerEmail + ", timestamp=" + timestamp + ", signerApp=" + signerApp + '}';
    }
}
