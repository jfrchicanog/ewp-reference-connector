package eu.erasmuswithoutpaper.iia.control;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "iias")
@XmlAccessorType(XmlAccessType.FIELD)
public class HashClass {

    @XmlElement(name = "iia")
    private Iia iia;

    // Getters and setters
    public Iia getIia() {
        return iia;
    }

    public void setIia(Iia iia) {
        this.iia = iia;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Iia {

        private String iiaId;

        @XmlElement(name = "text-to-hash")
        private String textToHash;

        @XmlElement(name = "valid-for-approval")
        private Boolean validForApproval;

        // Getters and setters
        public String getIiaId() {
            return iiaId;
        }

        public void setIiaId(String iiaId) {
            this.iiaId = iiaId;
        }

        public String getTextToHash() {
            return textToHash;
        }

        public void setTextToHash(String textToHash) {
            this.textToHash = textToHash;
        }

        public Boolean getValidForApproval() {
            return validForApproval;
        }

        public void setValidForApproval(Boolean validForApproval) {
            this.validForApproval = validForApproval;
        }
    }
}