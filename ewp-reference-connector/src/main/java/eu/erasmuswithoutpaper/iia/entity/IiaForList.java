package eu.erasmuswithoutpaper.iia.entity;

public class IiaForList {
    private String id;
    private String iiaCode;
    private String partner_id;

    public IiaForList() {
    }
    public IiaForList(String id, String iiaCode, String partner_id) {
        this.id = id;
        this.iiaCode = iiaCode;
        this.partner_id = partner_id;
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
        return partner_id;
    }
    public void setPartnerId(String partner_id) {
        this.partner_id = partner_id;
    }
    @Override
    public String toString() {
        return "IiaForList{" +
                "id='" + id + '\'' +
                ", iiaCode='" + iiaCode + '\'' +
                ", partner_id='" + partner_id + '\'' +
                '}';
    }
}
