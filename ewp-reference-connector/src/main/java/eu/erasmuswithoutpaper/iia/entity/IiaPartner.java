
package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.api.types.contact.Contact;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
/*@NamedQueries({
    @NamedQuery(name = IiaPartner.findAll, query = "SELECT i FROM IiaPartner i"),
})*/
public class IiaPartner implements Serializable {
    
    /*private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.IiaPartner.";
    public static final String findAll = PREFIX + "all";*/

    @Id
    @GeneratedValue(generator = "system-uuid")
    String id;

    private String heiId;
    private String ounitId;
    private String iiaId;
    private String iiaCode;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private IiaContact signingContact;
    @Temporal(TemporalType.TIMESTAMP)
    private Date signingDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<IiaContact> contact;

    @Override
    public int hashCode() {
        int hash = 5;
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
        final IiaPartner other = (IiaPartner) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
