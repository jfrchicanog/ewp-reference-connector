
package eu.erasmuswithoutpaper.imobility.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;

@Entity
@NamedQueries({
    @NamedQuery(name = IMobility.findAll, query = "SELECT m FROM IMobility m"),
    @NamedQuery(name = IMobility.findByReceivingInstitutionId, query = "SELECT m FROM IMobility m WHERE m.receivingInstitutionId=:receivingInstitutionId"),
})
public class IMobility extends Mobility implements Serializable{

    private static final String PREFIX = "eu.erasmuswithoutpaper.mobility.entity.IMobility.";
    public static final String findAll = PREFIX + "all";
    public static final String findByReceivingInstitutionId = PREFIX + "findByReceivingInstitutionId";
    
    private IMobilityStatus istatus;
    private String comment;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date actualArrivalDate;
    
    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date actualDepartureDate;

    public IMobilityStatus getIstatus() {
		return istatus;
	}

	public void setIstatus(IMobilityStatus istatus) {
		this.istatus = istatus;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(getId());
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
        final IMobility other = (IMobility) obj;
        if (!Objects.equals(getId(), other.getId())) {
            return false;
        }
        return true;
    }

}
