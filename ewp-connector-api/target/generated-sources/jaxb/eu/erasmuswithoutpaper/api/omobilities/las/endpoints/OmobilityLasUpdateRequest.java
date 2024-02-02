//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 11:52:32 AM CET 
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
 *         &lt;element name="sending-hei-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
 *         &lt;choice&gt;
 *           &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd}approve-proposal-v1"/&gt;
 *           &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd}comment-proposal-v1"/&gt;
 *         &lt;/choice&gt;
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
    "sendingHeiId",
    "approveProposalV1",
    "commentProposalV1"
})
@XmlRootElement(name = "omobility-las-update-request", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd")
public class OmobilityLasUpdateRequest
    implements Serializable
{

    @XmlElement(name = "sending-hei-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd", required = true)
    protected String sendingHeiId;
    @XmlElement(name = "approve-proposal-v1", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd")
    protected ApproveProposalV1 approveProposalV1;
    @XmlElement(name = "comment-proposal-v1", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-request.xsd")
    protected CommentProposalV1 commentProposalV1;

    /**
     * Ruft den Wert der sendingHeiId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingHeiId() {
        return sendingHeiId;
    }

    /**
     * Legt den Wert der sendingHeiId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendingHeiId(String value) {
        this.sendingHeiId = value;
    }

    /**
     * Ruft den Wert der approveProposalV1-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ApproveProposalV1 }
     *     
     */
    public ApproveProposalV1 getApproveProposalV1() {
        return approveProposalV1;
    }

    /**
     * Legt den Wert der approveProposalV1-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ApproveProposalV1 }
     *     
     */
    public void setApproveProposalV1(ApproveProposalV1 value) {
        this.approveProposalV1 = value;
    }

    /**
     * Ruft den Wert der commentProposalV1-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CommentProposalV1 }
     *     
     */
    public CommentProposalV1 getCommentProposalV1() {
        return commentProposalV1;
    }

    /**
     * Legt den Wert der commentProposalV1-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CommentProposalV1 }
     *     
     */
    public void setCommentProposalV1(CommentProposalV1 value) {
        this.commentProposalV1 = value;
    }

}
