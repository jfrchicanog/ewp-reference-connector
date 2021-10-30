
package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = OlearningAgreement.findAll, query = "SELECT la FROM OlearningAgreement la"),
    @NamedQuery(name = OlearningAgreement.findBySendingHeiId, query = "SELECT o FROM OlearningAgreement o WHERE o.sendingHei=:sendingHei")
})

public class OlearningAgreement implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement.";
    public static final String findAll = PREFIX + "all";
    public static final String findBySendingHeiId = PREFIX + "findBySendingHeiId";
    
    @Id
    @Column(updatable = false)
    String id;
    
    private String omobilityId;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "OMOBILITY_LAS_RECEIVING_HEI",referencedColumnName = "ID")
    MobilityInstitution receivingHei;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "OMOBILITY_LAS_SENDING_HEI",referencedColumnName = "ID")
    MobilityInstitution sendingHei;
    
    private String receivingAcademicTermEwpId;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "OMOBILITY_LAS_STUDENT",referencedColumnName = "ID")
    private eu.erasmuswithoutpaper.omobility.las.entity.Student student;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Convert(converter = YearMonthConverter.class)
    private YearMonth startYearMonth;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Convert(converter = YearMonthConverter.class)
    private YearMonth endYearMonth;
    
    private byte eqfLevelStudiedAtDeparture;
    private  String iscedFCode;
    private String iscedClarification;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "LANGSKILL_OMOBILITY_LAS",referencedColumnName = "ID")
    private OlasLanguageSkill studentLanguageSkill;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "FIRST_VERSION",referencedColumnName = "ID")
    private ListOfComponents firstVersion;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "APPROVED_VERSION",referencedColumnName = "ID")
    private ListOfComponents approvedChanges;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANGES_PROPOSAL",referencedColumnName = "ID")
    private ChangesProposal changesProposal;
    
    private String learningOutcomesUrl;
    private String provisionsUrl;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceivingAcademicTermEwpId() {
		return receivingAcademicTermEwpId;
	}

	public void setReceivingAcademicTermEwpId(String receivingAcademicTermEwpId) {
		this.receivingAcademicTermEwpId = receivingAcademicTermEwpId;
	}

	public OlasLanguageSkill getStudentLanguageSkill() {
		return studentLanguageSkill;
	}

	public void setStudentLanguageSkill(OlasLanguageSkill studentLanguageSkill) {
		this.studentLanguageSkill = studentLanguageSkill;
	}

    public MobilityInstitution getReceivingHei() {
		return receivingHei;
	}

	public void setReceivingHei(MobilityInstitution receivingHei) {
		this.receivingHei = receivingHei;
	}

	public MobilityInstitution getSendingHei() {
		return sendingHei;
	}

	public void setSendingHei(MobilityInstitution sendingHei) {
		this.sendingHei = sendingHei;
	}

	public String getOmobilityId() {
		return omobilityId;
	}

	public void setOmobilityId(String omobilityId) {
		this.omobilityId = omobilityId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public YearMonth getStartYearMonth() {
		return startYearMonth;
	}

	public void setStartYearMonth(YearMonth startYearMonth) {
		this.startYearMonth = startYearMonth;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public YearMonth getEndYearMonth() {
		return endYearMonth;
	}

	public void setEndYearMonth(YearMonth endYearMonth) {
		this.endYearMonth = endYearMonth;
	}

	public byte getEqfLevelStudiedAtDeparture() {
		return eqfLevelStudiedAtDeparture;
	}

	public void setEqfLevelStudiedAtDeparture(byte eqfLevelStudiedAtDeparture) {
		this.eqfLevelStudiedAtDeparture = eqfLevelStudiedAtDeparture;
	}

	public String getIscedFCode() {
		return iscedFCode;
	}

	public void setIscedFCode(String iscedFCode) {
		this.iscedFCode = iscedFCode;
	}

	public String getIscedClarification() {
		return iscedClarification;
	}

	public void setIscedClarification(String iscedClarification) {
		this.iscedClarification = iscedClarification;
	}

	public ListOfComponents getFirstVersion() {
		return firstVersion;
	}

	public void setFirstVersion(ListOfComponents firstVersion) {
		this.firstVersion = firstVersion;
	}

	public ListOfComponents getApprovedChanges() {
		return approvedChanges;
	}

	public void setApprovedChanges(ListOfComponents approvedChanges) {
		this.approvedChanges = approvedChanges;
	}

	public ChangesProposal getChangesProposal() {
		return changesProposal;
	}

	public void setChangesProposal(ChangesProposal changesProposal) {
		this.changesProposal = changesProposal;
	}

	public String getLearningOutcomesUrl() {
		return learningOutcomesUrl;
	}

	public void setLearningOutcomesUrl(String learningOutcomesUrl) {
		this.learningOutcomesUrl = learningOutcomesUrl;
	}

	public String getProvisionsUrl() {
		return provisionsUrl;
	}

	public void setProvisionsUrl(String provisionsUrl) {
		this.provisionsUrl = provisionsUrl;
	}

	public eu.erasmuswithoutpaper.omobility.las.entity.Student getStudent() {
		return student;
	}

	public void setStudent(eu.erasmuswithoutpaper.omobility.las.entity.Student student) {
		this.student = student;
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
        final OlearningAgreement other = (OlearningAgreement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
