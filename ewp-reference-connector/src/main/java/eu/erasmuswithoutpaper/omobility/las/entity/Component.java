package eu.erasmuswithoutpaper.omobility.las.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = Component.findAll, query = "SELECT c FROM Component c"),
})
public class Component {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.Component.";
    public static final String findAll = PREFIX + "all";
	
	@Id
    @Column(updatable = false)
    String id;

    protected String losId;
    protected String losCode;
    protected String title;
    protected String loiId;
    
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPONENT_TERM_ID",referencedColumnName = "ID")
    protected TermId termId;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENT_CREDIT")
    protected List<Credit> credit;
    
    protected String recognitionConditions;
    protected String shortDescription;
    protected String status;
    protected String reasonCode;
    protected String reasonText;
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLosId() {
		return losId;
	}
	
	public void setLosId(String losId) {
		this.losId = losId;
	}
	
	public String getLosCode() {
		return losCode;
	}
	
	public void setLosCode(String losCode) {
		this.losCode = losCode;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLoiId() {
		return loiId;
	}
	
	public void setLoiId(String loiId) {
		
		this.loiId = loiId;
	}
	public TermId getTermId() {
		return termId;
	}
	
	public void setTermId(TermId termId) {
		this.termId = termId;
	}
	
	public List<Credit> getCredit() {
		return credit;
	}
	
	public void setCredit(List<Credit> credit) {
		this.credit = credit;
	}
	public String getRecognitionConditions() {
		return recognitionConditions;
	}
	
	public void setRecognitionConditions(String recognitionConditions) {
		this.recognitionConditions = recognitionConditions;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}
	
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	public String getReasonText() {
		return reasonText;
	}
	
	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
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
        final Component other = (Component) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
