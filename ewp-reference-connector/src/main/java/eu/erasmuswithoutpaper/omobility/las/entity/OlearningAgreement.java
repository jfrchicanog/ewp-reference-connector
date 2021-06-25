
package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    
    private String receivingAcademicTermEwpId;
    private String studentIscedFCode;
    
    private byte eqfLevel;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "LANGSKILL_OMOBILITY_LAS",referencedColumnName = "ID")
    OlasLanguageSkill studentLanguageSkill;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    Date plannedMobilityStart;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    Date plannedMobilityEnd;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "OMOBILITY_LAS_RECEIVING_HEI",referencedColumnName = "ID")
    MobilityInstitution receivingHei;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "OMOBILITY_LAS_SENDING_HEI",referencedColumnName = "ID")
    MobilityInstitution sendingHei;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "OMOBILITY_LAS_STUDENT",referencedColumnName = "ID")
    Student student;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_COMPONENT_RECOGNIZED_BEFORE_MOBILITY")
    List<SingleChange> cmpRecognizedBeforeMobilityChanges;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_COMPONENT_RECOGNIZED_LATEST_APPOVED")
    List<SingleChange> cmpRecognizedLatestApprovedChanges;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_COMPONENT_RECOGNIZED_LATEST_DRAFT")
    List<SingleChange> cmpRecognizedLatestDraftChanges;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_COMPONENT_RECOGNIZED_BEFORE_MOBILITY_SNAPSHOT")
    List<OmobilityComponentRecognized> cmpRecognizedBeforeMobilitySnapshot;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_COMPONENT_RECOGNIZED_LATEST_APPROVED_SNAPSHOT")
    List<OmobilityComponentRecognized> cmpRecognizedLatestApprovedSnapshot;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_COMPONENT_RECOGNIZED_LATEST_DRAFT_SNAPSHOT")
    List<OmobilityComponentRecognized> cmpRecognizedLatestDraftSnapshot;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_STUDIED_BEFORE_MOBILITY_CHANGES")
    List<SingleChange> studiedbeforeMobilityChanges;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_STUDIED_LATEST_APPROVED_CHANGES")
    List<SingleChange> studiedlatestApprovedChanges;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_STUDIED_LATEST_DRAFT_CHANGES")
    List<SingleChange> studiedLatestDraftChanges;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_STUDIED_BEFORE_MOBILITY_SANPSHOT")
    List<OmobilityComponentRecognized> studiedBeforeMobilitySnapshot;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_STUDIED_LATEST_APPROVED_SANPSHOT")
    List<OmobilityComponentRecognized> studiedLatestApprovedSnapshot;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "OMOBILITY_LAS_STUDIED_LATEST_DRAFT_SANPSHOT")
    List<OmobilityComponentRecognized> studiedLatestDraftSnapshot;
    
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

	public String getStudentIscedFCode() {
		return studentIscedFCode;
	}

	public void setStudentIscedFCode(String studentIscedFCode) {
		this.studentIscedFCode = studentIscedFCode;
	}

	public OlasLanguageSkill getStudentLanguageSkill() {
		return studentLanguageSkill;
	}

	public void setStudentLanguageSkill(OlasLanguageSkill studentLanguageSkill) {
		this.studentLanguageSkill = studentLanguageSkill;
	}

	public Date getPlannedMobilityEnd() {
		return plannedMobilityEnd;
	}

	public void setPlannedMobilityEnd(Date plannedMobilityEnd) {
		this.plannedMobilityEnd = plannedMobilityEnd;
	}

	public Date getPlannedMobilityStart() {
		return plannedMobilityStart;
	}

	public void setPlannedMobilityStart(Date plannedMobilityStart) {
		this.plannedMobilityStart = plannedMobilityStart;
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

	public byte getEqfLevel() {
        return eqfLevel;
    }

    public void setEqfLevel(byte eqfLevel) {
        this.eqfLevel = eqfLevel;
    }

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<SingleChange> getCmpRecognizedBeforeMobilityChanges() {
		return cmpRecognizedBeforeMobilityChanges;
	}

	public void setCmpRecognizedBeforeMobilityChanges(List<SingleChange> cmpRecognizedBeforeMobilityChanges) {
		this.cmpRecognizedBeforeMobilityChanges = cmpRecognizedBeforeMobilityChanges;
	}

	public List<SingleChange> getCmpRecognizedLatestApprovedChanges() {
		return cmpRecognizedLatestApprovedChanges;
	}

	public void setCmpRecognizedLatestApprovedChanges(List<SingleChange> cmpRecognizedLatestApprovedChanges) {
		this.cmpRecognizedLatestApprovedChanges = cmpRecognizedLatestApprovedChanges;
	}

	public List<SingleChange> getCmpRecognizedLatestDraftChanges() {
		return cmpRecognizedLatestDraftChanges;
	}

	public void setCmpRecognizedLatestDraftChanges(List<SingleChange> cmpRecognizedLatestDraftChanges) {
		this.cmpRecognizedLatestDraftChanges = cmpRecognizedLatestDraftChanges;
	}

	public List<SingleChange> getStudiedbeforeMobilityChanges() {
		return studiedbeforeMobilityChanges;
	}

	public void setStudiedbeforeMobilityChanges(List<SingleChange> studiedbeforeMobilityChanges) {
		this.studiedbeforeMobilityChanges = studiedbeforeMobilityChanges;
	}

	public List<SingleChange> getStudiedlatestApprovedChanges() {
		return studiedlatestApprovedChanges;
	}

	public void setStudiedlatestApprovedChanges(List<SingleChange> studiedlatestApprovedChanges) {
		this.studiedlatestApprovedChanges = studiedlatestApprovedChanges;
	}

	public List<SingleChange> getStudiedLatestDraftChanges() {
		return studiedLatestDraftChanges;
	}

	public void setStudiedLatestDraftChanges(List<SingleChange> studiedLatestDraftChanges) {
		this.studiedLatestDraftChanges = studiedLatestDraftChanges;
	}

	public List<OmobilityComponentRecognized> getCmpRecognizedBeforeMobilitySnapshot() {
		return cmpRecognizedBeforeMobilitySnapshot;
	}

	public void setCmpRecognizedBeforeMobilitySnapshot(
			List<OmobilityComponentRecognized> cmpRecognizedBeforeMobilitySnapshot) {
		this.cmpRecognizedBeforeMobilitySnapshot = cmpRecognizedBeforeMobilitySnapshot;
	}

	public List<OmobilityComponentRecognized> getCmpRecognizedLatestApprovedSnapshot() {
		return cmpRecognizedLatestApprovedSnapshot;
	}

	public void setCmpRecognizedLatestApprovedSnapshot(
			List<OmobilityComponentRecognized> cmpRecognizedLatestApprovedSnapshot) {
		this.cmpRecognizedLatestApprovedSnapshot = cmpRecognizedLatestApprovedSnapshot;
	}

	public List<OmobilityComponentRecognized> getCmpRecognizedLatestDraftSnapshot() {
		return cmpRecognizedLatestDraftSnapshot;
	}

	public void setCmpRecognizedLatestDraftSnapshot(List<OmobilityComponentRecognized> cmpRecognizedLatestDraftSnapshot) {
		this.cmpRecognizedLatestDraftSnapshot = cmpRecognizedLatestDraftSnapshot;
	}

	public List<OmobilityComponentRecognized> getStudiedBeforeMobilitySnapshot() {
		return studiedBeforeMobilitySnapshot;
	}

	public void setStudiedBeforeMobilitySnapshot(List<OmobilityComponentRecognized> studiedBeforeMobilitySnapshot) {
		this.studiedBeforeMobilitySnapshot = studiedBeforeMobilitySnapshot;
	}

	public List<OmobilityComponentRecognized> getStudiedLatestApprovedSnapshot() {
		return studiedLatestApprovedSnapshot;
	}

	public void setStudiedLatestApprovedSnapshot(List<OmobilityComponentRecognized> studiedLatestApprovedSnapshot) {
		this.studiedLatestApprovedSnapshot = studiedLatestApprovedSnapshot;
	}

	public List<OmobilityComponentRecognized> getStudiedLatestDraftSnapshot() {
		return studiedLatestDraftSnapshot;
	}

	public void setStudiedLatestDraftSnapshot(List<OmobilityComponentRecognized> studiedLatestDraftSnapshot) {
		this.studiedLatestDraftSnapshot = studiedLatestDraftSnapshot;
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
