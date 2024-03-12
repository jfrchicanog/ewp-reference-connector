package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

//import lombok.*;

/*@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@NamedQueries({
    @NamedQuery(name = Iia_NEW.findAll, query = "SELECT i FROM Iia_NEW i"),
    @NamedQuery(name = Iia_NEW.findById, query = "SELECT i FROM Iia_NEW i WHERE i.id = :id"),
})*/
public class Iia_NEW implements Serializable {
/*
    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.Iia.";
    public static final String findAll = PREFIX + "all";
    public static final String findById = PREFIX + "byId";

    @Id
    @GeneratedValue(generator = "system-uuid")
    String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "iia")
    private List<IiaPartner_NEW> partner;
    private boolean inEfect;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COOPERATIONCONDITION_ID", referencedColumnName = "ID")
    private CooperationCondition_NEW cooperationConditions;
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
        final Iia_NEW other = (Iia_NEW) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
*/
}
