//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 11:48:25 AM CET 
//


package eu.erasmuswithoutpaper.api.iias.approval;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="approval" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
 *                   &lt;element name="conditions-hash" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex"/&gt;
 *                   &lt;element name="pdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "approval"
})
@XmlRootElement(name = "iias-approval-response", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias-approval/tree/stable-v1")
public class IiasApprovalResponse
    implements Serializable
{

    @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias-approval/tree/stable-v1")
    protected List<IiasApprovalResponse.Approval> approval;

    /**
     * Gets the value of the approval property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the approval property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApproval().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IiasApprovalResponse.Approval }
     * 
     * 
     */
    public List<IiasApprovalResponse.Approval> getApproval() {
        if (approval == null) {
            approval = new ArrayList<IiasApprovalResponse.Approval>();
        }
        return this.approval;
    }


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
     *         &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
     *         &lt;element name="conditions-hash" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex"/&gt;
     *         &lt;element name="pdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
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
        "iiaId",
        "conditionsHash",
        "pdf"
    })
    public static class Approval
        implements Serializable
    {

        @XmlElement(name = "iia-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias-approval/tree/stable-v1", required = true)
        protected String iiaId;
        @XmlElement(name = "conditions-hash", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias-approval/tree/stable-v1", required = true)
        protected String conditionsHash;
        @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iias-approval/tree/stable-v1")
        protected byte[] pdf;

        /**
         * Ruft den Wert der iiaId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIiaId() {
            return iiaId;
        }

        /**
         * Legt den Wert der iiaId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIiaId(String value) {
            this.iiaId = value;
        }

        /**
         * Ruft den Wert der conditionsHash-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConditionsHash() {
            return conditionsHash;
        }

        /**
         * Legt den Wert der conditionsHash-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConditionsHash(String value) {
            this.conditionsHash = value;
        }

        /**
         * Ruft den Wert der pdf-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getPdf() {
            return pdf;
        }

        /**
         * Legt den Wert der pdf-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setPdf(byte[] value) {
            this.pdf = value;
        }

    }

}
