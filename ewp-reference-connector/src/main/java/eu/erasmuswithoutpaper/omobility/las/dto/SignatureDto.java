package eu.erasmuswithoutpaper.omobility.las.dto;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signature", propOrder = {
        "signerName",
        "signerPosition",
        "signerEmail",
        "timestamp",
        "signerApp"
})
public class SignatureDto implements Serializable {

    @XmlElement(name = "signer-name")
    private String signerName;

    @XmlElement(name = "signer-position")
    private String signerPosition;

    @XmlElement(name = "signer-email")
    private String signerEmail;

    @XmlElement(required = true)
    private XMLGregorianCalendar timestamp;

    @XmlElement(name = "signer-app")
    private String signerApp;

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public String getSignerPosition() {
        return signerPosition;
    }

    public void setSignerPosition(String signerPosition) {
        this.signerPosition = signerPosition;
    }

    public String getSignerEmail() {
        return signerEmail;
    }

    public void setSignerEmail(String signerEmail) {
        this.signerEmail = signerEmail;
    }

    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(XMLGregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignerApp() {
        return signerApp;
    }

    public void setSignerApp(String signerApp) {
        this.signerApp = signerApp;
    }
}
