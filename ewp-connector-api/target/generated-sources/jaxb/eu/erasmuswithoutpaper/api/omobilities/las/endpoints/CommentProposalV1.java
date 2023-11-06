//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.06 um 01:40:45 PM CET 
//


package eu.erasmuswithoutpaper.api.omobilities.las.endpoints;

import java.io.Serializable;
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
 *         &lt;element name="omobility-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
 *         &lt;element name="changes-proposal-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="signature" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}Signature"/&gt;
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
    "omobilityId",
    "changesProposalId",
    "comment",
    "signature"
})
@XmlRootElement(name = "comment-proposal-v1", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd")
public class CommentProposalV1
    implements Serializable
{

    @XmlElement(name = "omobility-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd", required = true)
    protected String omobilityId;
    @XmlElement(name = "changes-proposal-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd", required = true)
    protected String changesProposalId;
    @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd", required = true)
    protected String comment;
    @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd", required = true)
    protected Signature signature;

    /**
     * Ruft den Wert der omobilityId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmobilityId() {
        return omobilityId;
    }

    /**
     * Legt den Wert der omobilityId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmobilityId(String value) {
        this.omobilityId = value;
    }

    /**
     * Ruft den Wert der changesProposalId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangesProposalId() {
        return changesProposalId;
    }

    /**
     * Legt den Wert der changesProposalId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangesProposalId(String value) {
        this.changesProposalId = value;
    }

    /**
     * Ruft den Wert der comment-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Legt den Wert der comment-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Ruft den Wert der signature-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Legt den Wert der signature-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setSignature(Signature value) {
        this.signature = value;
    }

}
