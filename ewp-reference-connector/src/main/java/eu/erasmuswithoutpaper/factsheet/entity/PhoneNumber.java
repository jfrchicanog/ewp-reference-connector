
package eu.erasmuswithoutpaper.factsheet.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Represetation of phone numbers and fax numbers.
 */

@Entity(name="FactsheePhoneNumber")
@NamedQuery(name = PhoneNumber.findAll, query = "SELECT fp FROM FactsheePhoneNumber fp")
public class PhoneNumber implements Serializable {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.factsheet.entity.PhoneNumber.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String e164;
    private String extensionNumber;
    private String otherFormat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getE164() {
        return e164;
    }

    public void setE164(String e164) {
        this.e164 = e164;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getOtherFormat() {
        return otherFormat;
    }

    public void setOtherFormat(String otherFormat) {
        this.otherFormat = otherFormat;
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
        final PhoneNumber other = (PhoneNumber) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
