//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 11:48:25 AM CET 
//


package eu.erasmuswithoutpaper.api.iias.endpoints;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="iia-fetchable" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="iia-local-unapproved-partner-approved" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="iia-local-approved-partner-unapproved" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="iia-both-approved" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "iiaFetchable",
    "iiaLocalUnapprovedPartnerApproved",
    "iiaLocalApprovedPartnerUnapproved",
    "iiaBothApproved"
})
@XmlRootElement(name = "iias-stats-response", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/stats-response.xsd")
public class IiasStatsResponse
    implements Serializable
{

    @XmlElement(name = "iia-fetchable", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/stats-response.xsd", required = true)
    protected BigInteger iiaFetchable;
    @XmlElement(name = "iia-local-unapproved-partner-approved", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/stats-response.xsd", required = true)
    protected BigInteger iiaLocalUnapprovedPartnerApproved;
    @XmlElement(name = "iia-local-approved-partner-unapproved", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/stats-response.xsd", required = true)
    protected BigInteger iiaLocalApprovedPartnerUnapproved;
    @XmlElement(name = "iia-both-approved", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/stats-response.xsd", required = true)
    protected BigInteger iiaBothApproved;

    /**
     * Ruft den Wert der iiaFetchable-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIiaFetchable() {
        return iiaFetchable;
    }

    /**
     * Legt den Wert der iiaFetchable-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIiaFetchable(BigInteger value) {
        this.iiaFetchable = value;
    }

    /**
     * Ruft den Wert der iiaLocalUnapprovedPartnerApproved-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIiaLocalUnapprovedPartnerApproved() {
        return iiaLocalUnapprovedPartnerApproved;
    }

    /**
     * Legt den Wert der iiaLocalUnapprovedPartnerApproved-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIiaLocalUnapprovedPartnerApproved(BigInteger value) {
        this.iiaLocalUnapprovedPartnerApproved = value;
    }

    /**
     * Ruft den Wert der iiaLocalApprovedPartnerUnapproved-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIiaLocalApprovedPartnerUnapproved() {
        return iiaLocalApprovedPartnerUnapproved;
    }

    /**
     * Legt den Wert der iiaLocalApprovedPartnerUnapproved-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIiaLocalApprovedPartnerUnapproved(BigInteger value) {
        this.iiaLocalApprovedPartnerUnapproved = value;
    }

    /**
     * Ruft den Wert der iiaBothApproved-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIiaBothApproved() {
        return iiaBothApproved;
    }

    /**
     * Legt den Wert der iiaBothApproved-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIiaBothApproved(BigInteger value) {
        this.iiaBothApproved = value;
    }

}
