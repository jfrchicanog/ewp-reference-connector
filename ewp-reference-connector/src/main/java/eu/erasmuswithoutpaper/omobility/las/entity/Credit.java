package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = Credit.findAll, query = "SELECT c FROM Credit c"),
})
public class Credit implements Serializable{
	 private static final String PREFIX = "eu.erasmuswithoutpaper.mobility.las.entity.Credit.";
	    public static final String findAll = PREFIX + "all";
	    
	    @Id
	    @GeneratedValue(generator="system-uuid")
	    String id;
	    
        private String scheme;
        private BigDecimal value;
	    
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

		public String getScheme() {
			return scheme;
		}

		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		public BigDecimal getValue() {
			return value;
		}

		public void setValue(BigDecimal value) {
			this.value = value;
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
