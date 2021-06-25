package eu.erasmuswithoutpaper.omobility.las.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class SingleChange implements Serializable{
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.entity.SingleChange.";
    public static final String findAll = PREFIX + "all";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;

    private BigInteger index;
    private boolean delete;
    
    private String displayText;
    private String ewpReasonCode;
    
    /**
     * This value is necesary when the property 'delete' is false, it means that a new ComponentRecognized element is going to be added
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OMOBILITY_INSERT_COMPONENT_RECOGNIZED",referencedColumnName = "ID")
    private OmobilityComponentRecognized componentRecognized;
    
    /**
     * This value is necesary when the property 'delete' is false, it means that a new ComponentStudied element is going to be added
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OMOBILITY_INSERT_COMPONENT_STUDIED",referencedColumnName = "ID")
    private ComponentsStudied componentStudied;
    
    public SingleChange() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public BigInteger getIndex() {
		return index;
	}

	public void setIndex(BigInteger index) {
		this.index = index;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getEwpReasonCode() {
		return ewpReasonCode;
	}

	public void setEwpReasonCode(String ewpReasonCode) {
		this.ewpReasonCode = ewpReasonCode;
	}

	public OmobilityComponentRecognized getComponentRecognized() {
		return componentRecognized;
	}

	public void setComponentRecognized(OmobilityComponentRecognized componentRecognized) {
		this.componentRecognized = componentRecognized;
	}

	public ComponentsStudied getComponentStudied() {
		return componentStudied;
	}

	public void setComponentStudied(ComponentsStudied componentStudied) {
		this.componentStudied = componentStudied;
	}

	@Override
    public int hashCode() {
        int hash = 5;
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
        final OlasLanguageSkill other = (OlasLanguageSkill) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
