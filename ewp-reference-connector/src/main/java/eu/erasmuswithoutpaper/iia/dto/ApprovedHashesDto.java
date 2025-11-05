package eu.erasmuswithoutpaper.iia.dto;

public class ApprovedHashesDto {

    private String iiaId;
    private String ourApprovedHash;
    private String partnerApprovedHash;
    private String ourIiaHash;
    private String partnerIiaHash;

    public String getIiaId() {
        return iiaId;
    }

    public void setIiaId(String iiaId) {
        this.iiaId = iiaId;
    }

    public String getOurApprovedHash() {
        return ourApprovedHash;
    }

    public void setOurApprovedHash(String ourApprovedHash) {
        this.ourApprovedHash = ourApprovedHash;
    }

    public String getPartnerApprovedHash() {
        return partnerApprovedHash;
    }

    public void setPartnerApprovedHash(String partnerApprovedHash) {
        this.partnerApprovedHash = partnerApprovedHash;
    }

    public String getOurIiaHash() {
        return ourIiaHash;
    }

    public void setOurIiaHash(String ourIiaHash) {
        this.ourIiaHash = ourIiaHash;
    }

    public String getPartnerIiaHash() {
        return partnerIiaHash;
    }

    public void setPartnerIiaHash(String partnerIiaHash) {
        this.partnerIiaHash = partnerIiaHash;
    }
}
