
package eu.erasmuswithoutpaper.course.entity;

import eu.erasmuswithoutpaper.omobility.entity.ResultDistribution;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="loi")
@NamedQuery(name = LearningOpportunityInstance.findAll, query = "SELECT l FROM loi l")
public class LearningOpportunityInstance implements Serializable {
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.course.entity.LearningOpportunityInstance.";
    public static final String findAll = PREFIX + "all";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ACADEMIC_TERM_ID", referencedColumnName = "ID")
    private AcademicTerm academicTerm;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "LOI_CREDITS")
    private List<StudiedCredit> credits;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "GRADING_SCHEME_ID", referencedColumnName = "ID")
    private GradingScheme gradingScheme;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "RESULT_DISTRIBUTION", referencedColumnName = "ID")
    private ResultDistribution resultDistribution;

    private String languageOfInstruction;
    private BigDecimal engagementHours;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AcademicTerm getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(AcademicTerm academicTerm) {
        this.academicTerm = academicTerm;
    }

    public List<StudiedCredit> getCredits() {
        return credits;
    }

    public void setCredits(List<StudiedCredit> credits) {
        this.credits = credits;
    }

    public GradingScheme getGradingScheme() {
        return gradingScheme;
    }

    public void setGradingScheme(GradingScheme gradingScheme) {
        this.gradingScheme = gradingScheme;
    }

    public String getLanguageOfInstruction() {
        return languageOfInstruction;
    }

    public void setLanguageOfInstruction(String languageOfInstruction) {
        this.languageOfInstruction = languageOfInstruction;
    }

    public ResultDistribution getResultDistribution() {
        return resultDistribution;
    }

    public void setResultDistribution(ResultDistribution resultDistribution) {
        this.resultDistribution = resultDistribution;
    }

    public BigDecimal getEngagementHours() {
        return engagementHours;
    }

    public void setEngagementHours(BigDecimal engagementHours) {
        this.engagementHours = engagementHours;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final LearningOpportunityInstance other = (LearningOpportunityInstance) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
