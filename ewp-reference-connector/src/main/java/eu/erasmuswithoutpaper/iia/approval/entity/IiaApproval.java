
package eu.erasmuswithoutpaper.iia.approval.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Entity
@NamedQueries({
    @NamedQuery(name = IiaApproval.findAll, query = "SELECT i FROM IiaApproval i"),
    @NamedQuery(name = IiaApproval.findById, query = "SELECT i FROM IiaApproval i WHERE i.id = :id"),
    @NamedQuery(name = IiaApproval.findByIiaCode, query = "SELECT i FROM IiaApproval i WHERE i.iiaCode = :iiaCode")
})
public class IiaApproval implements Serializable{
    
    private static final long serialVersionUID = 1L;
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval.";
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
        
    @Column(name = "CONDITIONS_HASH", nullable = true)
    private String conditionsHash;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PDF", nullable = true)
    private byte[] pdf;
    
    public IiaApproval(){
    }
    
    public IiaApproval(String iiaCode){
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
        final IiaApproval other = (IiaApproval) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
