package eu.erasmuswithoutpaper.iia.entity;

public class IiaForList {
    private String id;
    private String iiaCode;
    private String partnerId;

    public IiaForList() {
    }
    public IiaForList(String id, String iiaCode, String partnerId) {
        this.id = id;
        this.iiaCode = iiaCode;
        this.partnerId = partnerId;
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
    public String getPartnerId() {
        return partnerId;
    }
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    @Override
    public String toString() {
        return "IiaForList{" +
                "id='" + id + '\'' +
                ", iiaCode='" + iiaCode + '\'' +
                ", partnerId='" + partnerId + '\'' +
                '}';
    }
}
