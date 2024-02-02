//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 09:37:00 AM CET 
//


package eu.erasmuswithoutpaper.api.omobilities.las.endpoints;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2.TermId;


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
 *         &lt;element name="los-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LosID" minOccurs="0"/&gt;
 *         &lt;element name="los-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="loi-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LoiID" minOccurs="0"/&gt;
 *         &lt;element name="term-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v2}TermId" minOccurs="0"/&gt;
 *         &lt;element name="credit" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="recognition-conditions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="short-description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="status"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="inserted"/&gt;
 *             &lt;enumeration value="deleted"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="reason-code"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="not-available"/&gt;
 *             &lt;enumeration value="language-mismatch"/&gt;
 *             &lt;enumeration value="timetable-conflict"/&gt;
 *             &lt;enumeration value="substituting-deleted"/&gt;
 *             &lt;enumeration value="extending-mobility"/&gt;
 *             &lt;enumeration value="adding-virtual-component"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="reason-text" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "losId",
    "losCode",
    "title",
    "loiId",
    "termId",
    "credit",
    "recognitionConditions",
    "shortDescription"
})
@XmlRootElement(name = "component")
public class Component
    implements Serializable
{

    @XmlElement(name = "los-id")
    protected String losId;
    @XmlElement(name = "los-code")
    protected String losCode;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(name = "loi-id")
    protected String loiId;
    @XmlElement(name = "term-id")
    protected TermId termId;
    protected List<Component.Credit> credit;
    @XmlElement(name = "recognition-conditions")
    protected String recognitionConditions;
    @XmlElement(name = "short-description")
    protected String shortDescription;
    @XmlAttribute(name = "status")
    protected String status;
    @XmlAttribute(name = "reason-code")
    protected String reasonCode;
    @XmlAttribute(name = "reason-text")
    protected String reasonText;

    /**
     * Ruft den Wert der losId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLosId() {
        return losId;
    }

    /**
     * Legt den Wert der losId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLosId(String value) {
        this.losId = value;
    }

    /**
     * Ruft den Wert der losCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLosCode() {
        return losCode;
    }

    /**
     * Legt den Wert der losCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLosCode(String value) {
        this.losCode = value;
    }

    /**
     * Ruft den Wert der title-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Legt den Wert der title-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Ruft den Wert der loiId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoiId() {
        return loiId;
    }

    /**
     * Legt den Wert der loiId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoiId(String value) {
        this.loiId = value;
    }

    /**
     * Ruft den Wert der termId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TermId }
     *     
     */
    public TermId getTermId() {
        return termId;
    }

    /**
     * Legt den Wert der termId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TermId }
     *     
     */
    public void setTermId(TermId value) {
        this.termId = value;
    }

    /**
     * Gets the value of the credit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the credit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCredit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Component.Credit }
     * 
     * 
     */
    public List<Component.Credit> getCredit() {
        if (credit == null) {
            credit = new ArrayList<Component.Credit>();
        }
        return this.credit;
    }

    /**
     * Ruft den Wert der recognitionConditions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecognitionConditions() {
        return recognitionConditions;
    }

    /**
     * Legt den Wert der recognitionConditions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecognitionConditions(String value) {
        this.recognitionConditions = value;
    }

    /**
     * Ruft den Wert der shortDescription-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Legt den Wert der shortDescription-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Ruft den Wert der reasonCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Legt den Wert der reasonCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Ruft den Wert der reasonText-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonText() {
        return reasonText;
    }

    /**
     * Legt den Wert der reasonText-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonText(String value) {
        this.reasonText = value;
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
     *         &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
        "scheme",
        "value"
    })
    public static class Credit
        implements Serializable
    {

        @XmlElement(required = true)
        protected String scheme;
        @XmlElement(required = true)
        protected BigDecimal value;

        /**
         * Ruft den Wert der scheme-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getScheme() {
            return scheme;
        }

        /**
         * Legt den Wert der scheme-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setScheme(String value) {
            this.scheme = value;
        }

        /**
         * Ruft den Wert der value-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValue() {
            return value;
        }

        /**
         * Legt den Wert der value-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValue(BigDecimal value) {
            this.value = value;
        }

    }

}
