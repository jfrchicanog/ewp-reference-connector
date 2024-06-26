
package eu.erasmuswithoutpaper.omobility.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = Mobility.findAll, query = "SELECT m FROM Mobility m"),
    @NamedQuery(name = Mobility.findBySendingInstitutionId, query = "SELECT m FROM Mobility m WHERE m.sendingInstitutionId=:sendingInstitutionId"),
    @NamedQuery(name = Mobility.findByReceivingInstitutionId, query = "SELECT m FROM Mobility m WHERE m.receivingInstitutionId=:receivingInstitutionId"),
})
public class Mobility implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.Mobility.";
    public static final String findAll = PREFIX + "all";
    public static final String findBySendingInstitutionId = PREFIX + "findBySendingInstitutionId";
    public static final String findByReceivingInstitutionId = PREFIX + "findByReceivingInstitutionId";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String iiaId;
    private String sendingInstitutionId;
    private String sendingOrganizationUnitId;
    private String receivingInstitutionId;
    private String receivingOrganizationUnitId;
    private String mobilityParticipantId;
    
    private String sendingAcademicTermEwpId;
    private String receivingAcademicYearId;
    private String nomineeIscedFCode;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "LANGSKILL_MOBILITY")
    List<LanguageSkill> nomineeLanguageSkill;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "ADDITIONAL_REQUIREMENTS_MOBILITY")
    List<AdditionalRequirements> additionalRequirements;
    
    private MobilityStatus status;
    private MobilityActivityType activityType;
    private MobilityActivityAttributes activityAttribute;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date plannedArrivalDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date plannedDepartureDate;

    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date actualArrivalDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date actualDepartureDate;
    
    private byte eqfLevelDeparture;
    private byte eqfLevelNomination;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIiaId() {
        return iiaId;
    }

    public void setIiaId(String iiaId) {
        this.iiaId = iiaId;
    }

    public String getSendingInstitutionId() {
        return sendingInstitutionId;
    }

    public void setSendingInstitutionId(String sendingInstitutionId) {
        this.sendingInstitutionId = sendingInstitutionId;
    }

    public String getReceivingInstitutionId() {
        return receivingInstitutionId;
    }

    public void setReceivingInstitutionId(String receivingInstitutionId) {
        this.receivingInstitutionId = receivingInstitutionId;
    }

    public String getSendingOrganizationUnitId() {
        return sendingOrganizationUnitId;
    }

    public void setSendingOrganizationUnitId(String sendingOrganizationUnitId) {
        this.sendingOrganizationUnitId = sendingOrganizationUnitId;
    }

    public String getReceivingOrganizationUnitId() {
        return receivingOrganizationUnitId;
    }

    public void setReceivingOrganizationUnitId(String receivingOrganizationUnitId) {
        this.receivingOrganizationUnitId = receivingOrganizationUnitId;
    }

    public String getMobilityParticipantId() {
        return mobilityParticipantId;
    }

    public void setMobilityParticipantId(String mobilityParticipantId) {
        this.mobilityParticipantId = mobilityParticipantId;
    }

    public MobilityStatus getStatus() {
        return status;
    }

    public void setStatus(MobilityStatus status) {
        this.status = status;
    }

    public Date getPlannedArrivalDate() {
        return plannedArrivalDate;
    }

    public void setPlannedArrivalDate(Date plannedArrivalDate) {
        this.plannedArrivalDate = plannedArrivalDate;
    }

    public Date getPlannedDepartureDate() {
        return plannedDepartureDate;
    }

    public void setPlannedDepartureDate(Date plannedDepartureDate) {
        this.plannedDepartureDate = plannedDepartureDate;
    }

    public Date getActualArrivalDate() {
        return actualArrivalDate;
    }

    public void setActualArrivalDate(Date actualArrivalDate) {
        this.actualArrivalDate = actualArrivalDate;
    }

    public Date getActualDepartureDate() {
        return actualDepartureDate;
    }

    public void setActualDepartureDate(Date actualDepartureDate) {
        this.actualDepartureDate = actualDepartureDate;
    }

    public byte getEqfLevelDeparture() {
		return eqfLevelDeparture;
	}

	public void setEqfLevelDeparture(byte eqfLevelDeparture) {
		this.eqfLevelDeparture = eqfLevelDeparture;
	}

	public byte getEqfLevelNomination() {
		return eqfLevelNomination;
	}

	public void setEqfLevelNomination(byte eqfLevelNomination) {
		this.eqfLevelNomination = eqfLevelNomination;
	}

	public String getSendingAcademicTermEwpId() {
		return sendingAcademicTermEwpId;
	}

	public void setSendingAcademicTermEwpId(String sendingAcademicTermEwpId) {
		this.sendingAcademicTermEwpId = sendingAcademicTermEwpId;
	}

	public String getReceivingAcademicYearId() {
		return receivingAcademicYearId;
	}

	public void setReceivingAcademicYearId(String receivingAcademicYearId) {
		this.receivingAcademicYearId = receivingAcademicYearId;
	}

	public String getNomineeIscedFCode() {
		return nomineeIscedFCode;
	}

	public void setNomineeIscedFCode(String nomineeIscedFCode) {
		this.nomineeIscedFCode = nomineeIscedFCode;
	}

	public List<LanguageSkill> getNomineeLanguageSkill() {
		return nomineeLanguageSkill;
	}

	public void setNomineeLanguageSkill(List<LanguageSkill> nomineeLanguageSkill) {
		this.nomineeLanguageSkill = nomineeLanguageSkill;
	}

	public MobilityActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(MobilityActivityType activityType) {
		this.activityType = activityType;
	}

	public MobilityActivityAttributes getActivityAttribute() {
		return activityAttribute;
	}

	public void setActivityAttribute(MobilityActivityAttributes activityAttribute) {
		this.activityAttribute = activityAttribute;
	}

	public List<AdditionalRequirements> getAdditionalRequirements() {
		return additionalRequirements;
	}

	public void setAdditionalRequirements(List<AdditionalRequirements> additionalRequirements) {
		this.additionalRequirements = additionalRequirements;
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
        final Mobility other = (Mobility) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
