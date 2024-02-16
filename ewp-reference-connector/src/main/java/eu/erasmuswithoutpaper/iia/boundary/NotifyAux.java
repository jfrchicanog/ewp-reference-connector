package eu.erasmuswithoutpaper.iia.boundary;

public class NotifyAux {
    private String heiId;
    private String url;

    public NotifyAux(String heiId, String url) {
        this.heiId = heiId;
        this.url = url;
    }

    public String getHeiId() {
        return heiId;
    }

    public void setHeiId(String heiId) {
        this.heiId = heiId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!NotifyAux.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final NotifyAux other = (NotifyAux) obj;
        if ((this.heiId == null) ? (other.heiId != null) : !this.heiId.equals(other.heiId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.heiId != null ? this.heiId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "NotifyAux{" + "heiId=" + heiId + ", url=" + url + '}';
    }
}
