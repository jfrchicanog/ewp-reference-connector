
package eu.erasmuswithoutpaper.iia.approval.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import eu.erasmuswithoutpaper.iia.entity.Iia;
import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Entity
@NamedQueries({
        @NamedQuery(name = IiaApproval.findAll, query = "SELECT i FROM IiaApproval i"),
        @NamedQuery(name = IiaApproval.findById, query = "SELECT i FROM IiaApproval i WHERE i.id = :id"),
        @NamedQuery(name = IiaApproval.findByIiaCode, query = "SELECT i FROM IiaApproval i WHERE i.iiaCode = :iiaCode"),
        @NamedQuery(name = IiaApproval.findByIiaId, query = "SELECT i FROM IiaApproval i JOIN i.iia ii WHERE ii.id = :iiaId"),
        @NamedQuery(name = IiaApproval.findByIiaIdAndHeiId, query = "SELECT i FROM IiaApproval i JOIN i.iia ii WHERE i.heiId = :heiId AND ii.id = :iiaId"),
})
public class IiaApproval implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval.";
    public static final String findAll = PREFIX + "all";
    public static final String findById = PREFIX + "byId";
    public static final String findByIiaCode = PREFIX + "byIiaCode";
    public static final String findByIiaId = PREFIX + "byIiaId";
    public static final String findByIiaIdAndHeiId = PREFIX + "byIiaIdAndHeiId";

    @Id
    @GeneratedValue(generator = "system-uuid")
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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "IIA_ID", referencedColumnName = "ID")
    private Iia iia;

    @Column(name = "HEI_ID", nullable = true)
    private String heiId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PDF", nullable = true)
    private byte[] pdf;

    public IiaApproval() {
    }

    public IiaApproval(String iiaCode) {
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

    public Iia getIia() {
        return iia;
    }

    public void setIia(Iia iia) {
        this.iia = iia;
    }

    public String getHeiId() {
        return heiId;
    }

    public void setHeiId(String heiId) {
        this.heiId = heiId;
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
