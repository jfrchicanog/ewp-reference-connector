
package eu.erasmuswithoutpaper.factsheet.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	@NamedQuery(name = MobilityFactsheet.findAll, query = "SELECT mf FROM MobilityFactsheet mf"),
	@NamedQuery(name = MobilityFactsheet.findByHeid, query = "SELECT mf FROM MobilityFactsheet mf WHERE mf.heiId = :heiId")
})
public class MobilityFactsheet implements Serializable {
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet.";
    public static final String findAll = PREFIX + "all";
    public static final String findByHeid= PREFIX + "heid";

    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String heiId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTOM_TERM_ID", referencedColumnName = "ID")
    private CalendarEntry studentApplicationTerm;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SPRING_TERM_ID", referencedColumnName = "ID")
    private CalendarEntry studentNominationTerm;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "APPLICATION_INFO", referencedColumnName = "ID")
    private ContactInfo applicationInfo;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "HOUSING_INFO", referencedColumnName = "ID")
    private ContactInfo housingInfo;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "VISA_INFO", referencedColumnName = "ID")
    private ContactInfo visaInfo;
    
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "INSURANCE_INFO", referencedColumnName = "ID")
    private ContactInfo insuranceInfo;
    
    private BigInteger torWeeksLimit;
    private BigInteger decisionWeeksLimit;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "ADDITIONAL_REQUIREMENT")
    private List<AdditionalRequirement> additionalRequirements;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "ACCESSIBLITY")
    private List<Accessibility> accessibility;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "ADDITIONAL_INFO")
    private List<FactsheetAdditionalInfo> additionalInfo;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getHeiId() {
		return heiId;
	}

	public void setHeiId(String heiId) {
		this.heiId = heiId;
	}

	public CalendarEntry getStudentApplicationTerm() {
		return studentApplicationTerm;
	}

	public void setStudentApplicationTerm(CalendarEntry studentApplicationTerm) {
		this.studentApplicationTerm = studentApplicationTerm;
	}

	public CalendarEntry getStudentNominationTerm() {
		return studentNominationTerm;
	}

	public void setStudentNominationTerm(CalendarEntry studentNominationTerm) {
		this.studentNominationTerm = studentNominationTerm;
	}

	public ContactInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void setApplicationInfo(ContactInfo applicationInfo) {
		this.applicationInfo = applicationInfo;
	}

	public ContactInfo getHousingInfo() {
		return housingInfo;
	}

	public void setHousingInfo(ContactInfo housingInfo) {
		this.housingInfo = housingInfo;
	}

	public ContactInfo getVisaInfo() {
		return visaInfo;
	}

	public void setVisaInfo(ContactInfo visaInfo) {
		this.visaInfo = visaInfo;
	}

	public ContactInfo getInsuranceInfo() {
		return insuranceInfo;
	}

	public void setInsuranceInfo(ContactInfo insuranceInfo) {
		this.insuranceInfo = insuranceInfo;
	}

	public BigInteger getTorWeeksLimit() {
		return torWeeksLimit;
	}

	public void setTorWeeksLimit(BigInteger torWeeksLimit) {
		this.torWeeksLimit = torWeeksLimit;
	}

	public BigInteger getDecisionWeeksLimit() {
		return decisionWeeksLimit;
	}

	public void setDecisionWeeksLimit(BigInteger decisionWeeksLimit) {
		this.decisionWeeksLimit = decisionWeeksLimit;
	}

	public List<Accessibility> getAccessibility() {
		return accessibility;
	}

	public void setAccessibility(List<Accessibility> accessibility) {
		this.accessibility = accessibility;
	}

	public List<AdditionalRequirement> getAdditionalRequirements() {
		return additionalRequirements;
	}

	public void setAdditionalRequirements(List<AdditionalRequirement> additionalRequirements) {
		this.additionalRequirements = additionalRequirements;
	}

	public List<FactsheetAdditionalInfo> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(List<FactsheetAdditionalInfo> additionalInfo) {
		this.additionalInfo = additionalInfo;
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
        final MobilityFactsheet other = (MobilityFactsheet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
