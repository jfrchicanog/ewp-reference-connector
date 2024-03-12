package eu.erasmuswithoutpaper.iia.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MobilitySpecification {

    @Id
    @GeneratedValue
    String id;

    private String sendingHeiId;
    private String sendingOunitId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<IiaContact> sendingContact;
    private String receivingHeiId;
    private String receivingOunitId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<IiaContact> receivingContact;
    private String receivingFirstAcademicYearId;
    private String receivingLastAcademicYearId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MobilitiesPerYear mobilitiesPerYear;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RecommendedLanguageSkill> recommendedLanguageSkill;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    }
}
