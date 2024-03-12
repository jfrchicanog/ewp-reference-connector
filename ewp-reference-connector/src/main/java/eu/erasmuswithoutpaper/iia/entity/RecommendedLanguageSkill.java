package eu.erasmuswithoutpaper.iia.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class RecommendedLanguageSkill {

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private String language;
    private String cefrLevel;
    @OneToOne(cascade = javax.persistence.CascadeType.ALL, fetch = javax.persistence.FetchType.EAGER)
    private SubjectArea subjectArea;
    private Boolean notYetDefined;

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
    }
}
