
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import eu.erasmuswithoutpaper.api.iias.endpoints.StudentTraineeshipMobilitySpec;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
/*@NamedQueries({
    @NamedQuery(name = CooperationCondition.findAll, query = "SELECT c FROM CooperationCondition c"),
})*/
public class CooperationCondition implements Serializable{
    
    /*private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.CooperationCondition.";
    public static final String findAll = PREFIX + "all";*/
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudentMobilitySpecification> studentStudiesMobilitySpec;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudentMobilitySpecification> studentTraineeshipMobilitySpec;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StaffMobilitySpecification> staffTeacherMobilitySpec;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StaffMobilitySpecification> staffTrainingMobilitySpec;
    private Boolean terminatedAsAWhole;

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final CooperationCondition other = (CooperationCondition) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
