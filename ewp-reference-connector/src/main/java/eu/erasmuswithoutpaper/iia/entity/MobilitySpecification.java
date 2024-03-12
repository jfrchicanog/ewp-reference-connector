package eu.erasmuswithoutpaper.iia.entity;

//import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)*/
public class MobilitySpecification implements Serializable {
/*
    @Id
    @GeneratedValue
    String id;

    private String sendingHeiId;
    private String sendingOunitId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "MOBILITY_SPECIFICATION_SENDING_CONTACT", joinColumns = @JoinColumn(name = "MOBILITY_SPECIFICATION_ID"), inverseJoinColumns = @JoinColumn(name = "IIA_CONTACT_ID"))
    private List<IiaContact_NEW> sendingContact;
    private String receivingHeiId;
    private String receivingOunitId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "MOBILITY_SPECIFICATION_RECEIVING_CONTACT", joinColumns = @JoinColumn(name = "MOBILITY_SPECIFICATION_ID"), inverseJoinColumns = @JoinColumn(name = "IIA_CONTACT_ID"))
    private List<IiaContact_NEW> receivingContact;
    private String receivingFirstAcademicYearId;
    private String receivingLastAcademicYearId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "MOBILITIESPERYEAR_ID", referencedColumnName = "ID")
    private MobilitiesPerYear mobilitiesPerYear;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "mobilitySpecification")
    private List<RecommendedLanguageSkill> recommendedLanguageSkill;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "mobilitySpecification")
    private List<SubjectArea> subjectArea;
    private String otherInfoTerms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MobilitySpecification that = (MobilitySpecification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/
}
