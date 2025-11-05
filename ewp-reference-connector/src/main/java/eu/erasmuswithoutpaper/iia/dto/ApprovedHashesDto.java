package eu.erasmuswithoutpaper.iia.dto;

public class ApprovedHashesDto {

    private String iiaId;
    private String ourApprovedHash;
    private String partnerApprovedHash;
    private String iiaHash;

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

    public String getIiaHash() {
        return iiaHash;
    }

    public void setIiaHash(String iiaHash) {
        this.iiaHash = iiaHash;
    }
}
