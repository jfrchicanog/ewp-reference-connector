
package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = Iia.findAll, query = "SELECT i FROM Iia i"),
    @NamedQuery(name = Iia.findById, query = "SELECT i FROM Iia i WHERE i.id = :id"),
    @NamedQuery(name = Iia.findByIiaCode, query = "SELECT i FROM Iia i WHERE i.iiaCode = :iiaCode")
})
public class Iia implements Serializable{
    
    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.Iia.";
    public static final String findAll = PREFIX + "all";
    public static final String findById = PREFIX + "byId";
    public static final String findByIiaCode = PREFIX + "byIiaCode";
    
    @Id
    @GeneratedValue(generator="system-uuid")
    String id;
    
    private String iiaCode;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date modifyDate;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "IIA_COOPERATION_CONDITION")
    List<CooperationCondition> cooperationConditions;
    
    @Column(name = "CONDITIONS_HASH", nullable = true)
    private String conditionsHash;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PDF", nullable = true)
    private byte[] pdf;
    
    public Iia(){
    }
    
    public Iia(String iiaCode){
        this.iiaCode = iiaCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIiaCode() {
        return iiaCode;
    }

    public void setIiaCode(String iiaCode) {
        this.iiaCode = iiaCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<CooperationCondition> getCooperationConditions() {
        return cooperationConditions;
    }

    public void setCooperationConditions(List<CooperationCondition> cooperationConditions) {
        this.cooperationConditions = cooperationConditions;
    }

    public String getConditionsHash() {
		return conditionsHash;
	}

	public void setConditionsHash(String conditionsHash) {
		this.conditionsHash = conditionsHash;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Iia other = (Iia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
