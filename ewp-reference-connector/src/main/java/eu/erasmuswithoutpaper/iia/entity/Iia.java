package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import lombok.*;
import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@NamedQueries({
    @NamedQuery(name = Iia.findAll, query = "SELECT i FROM Iia i"),
    @NamedQuery(name = Iia.findById, query = "SELECT i FROM Iia i WHERE i.id = :id"),
})
public class Iia implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.Iia.";
    public static final String findAll = PREFIX + "all";
    public static final String findById = PREFIX + "byId";

    @Id
    @GeneratedValue(generator = "system-uuid")
    String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<IiaPartner> partner;
    private boolean inEfect;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CooperationCondition cooperationConditions;
    private String iiaHash;
    private String pdfFile;

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Iia other = (Iia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
