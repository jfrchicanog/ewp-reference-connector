package eu.erasmuswithoutpaper.iia.entity;

//import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity*/
public class RecommendedLanguageSkill implements Serializable {
/*
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String language;
    private String cefrLevel;
    @OneToOne(cascade = javax.persistence.CascadeType.ALL, fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "SUBJECTAREA_ID", referencedColumnName = "ID")
    private SubjectArea subjectArea;
    private Boolean notYetDefined;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private MobilitySpecification mobilitySpecification;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendedLanguageSkill that = (RecommendedLanguageSkill) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/
}
