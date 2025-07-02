package eu.erasmuswithoutpaper.iia.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import org.apache.johnzon.mapper.JohnzonConverter;

import eu.erasmuswithoutpaper.internal.StandardDateConverter;

@Entity
@NamedQueries({
        @NamedQuery(name = Iia.findAll, query = "SELECT i FROM Iia i"),
        @NamedQuery(name = Iia.findById, query = "SELECT i FROM Iia i WHERE i.id = :id"),
        @NamedQuery(name = Iia.findByIiaCode, query = "SELECT i FROM Iia i WHERE i.iiaCode = :iiaCode"),
        @NamedQuery(name = Iia.findByPartnerId, query = "SELECT i FROM Iia i JOIN i.cooperationConditions cc JOIN cc.sendingPartner sp JOIN cc.receivingPartner rp WHERE sp.iiaId = :iiaId or rp.iiaId = :iiaId"),
        @NamedQuery(name = Iia.findByPartnerAndId, query = "SELECT DISTINCT i FROM Iia i " +
                "JOIN i.cooperationConditions cc " +
                "JOIN cc.sendingPartner sp " +
                "JOIN cc.receivingPartner rp " +
                " WHERE ((sp.institutionId = :heiId AND sp.iiaId = :iiaId) OR (rp.institutionId = :heiId AND rp.iiaId = :iiaId)) " +
                "AND i.original is null"),
        @NamedQuery(name = Iia.findByPartner, query = "SELECT DISTINCT i FROM Iia i " +
                "JOIN i.cooperationConditions cc " +
                "JOIN cc.sendingPartner sp " +
                "JOIN cc.receivingPartner rp " +
                " WHERE (sp.institutionId = :heiId OR rp.institutionId = :heiId) " +
                "AND i.original is null"),
        @NamedQuery(name = Iia.findByOriginalIiaId, query = "SELECT i FROM Iia i JOIN i.original oi WHERE oi.id = :iiaId"),
        @NamedQuery(name = Iia.findByDateRange, query = "SELECT i FROM Iia i WHERE i.modifyDate <= :endDate AND i.modifyDate >= :statrDate")
})
public class Iia implements Serializable {

    private static final String PREFIX = "eu.erasmuswithoutpaper.iia.entity.Iia.";
    public static final String findAll = PREFIX + "all";
    public static final String findById = PREFIX + "byId";
    public static final String findByIiaCode = PREFIX + "byIiaCode";
    public static final String findByPartnerId = PREFIX + "byPartnerId";
    public static final String findByPartnerAndId = PREFIX + "byPartnerAndId";
    public static final String findByPartner = PREFIX + "byPartner";

    public static final String findByOriginalIiaId = PREFIX + "byOriginalId";
    public static final String findByDateRange = PREFIX + "byDateRange";

    @Id
    @GeneratedValue(generator = "system-uuid")
    String id;

    private String iiaCode;
    private boolean inEfect;

    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date modifyDate;

    @JohnzonConverter(StandardDateConverter.class)
    @Temporal(TemporalType.DATE)
    private Date signingDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "IIA_COOPERATION_CONDITION")
    List<CooperationCondition> cooperationConditions;

    @Column(name = "CONDITIONS_HASH", nullable = true)
    private String conditionsHash;

    @Column(name = "ID_PARTNER", nullable = true)
    private String idPartner;

    @Column(name = "HASH_PARTNER", nullable = true)
    private String hashPartner;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PDF", nullable = true)
    private byte[] pdf;

    @Column(name = "CONDITIONS_TERMINATED_AS_A_WHOLE", nullable = true)
    private Boolean conditionsTerminatedAsAWhole;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORIGINAL_IIA")
    private Iia original;

    public Iia() {
    }

    public Iia(String iiaCode) {
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

    public boolean isInEfect() {
        return inEfect;
    }

    public void setInEfect(boolean inEfect) {
        this.inEfect = inEfect;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public String getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(String idPartner) {
        this.idPartner = idPartner;
    }

    public String getHashPartner() {
        return hashPartner;
    }

    public void setHashPartner(String hashPartner) {
        this.hashPartner = hashPartner;
    }

    public Boolean getConditionsTerminatedAsAWhole() {
        return conditionsTerminatedAsAWhole;
    }

    public void setConditionsTerminatedAsAWhole(Boolean conditionsTerminatedAsAWhole) {
        this.conditionsTerminatedAsAWhole = conditionsTerminatedAsAWhole;
    }

    public Iia getOriginal() {
        return original;
    }

    public void setOriginal(Iia original) {
        this.original = original;
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
