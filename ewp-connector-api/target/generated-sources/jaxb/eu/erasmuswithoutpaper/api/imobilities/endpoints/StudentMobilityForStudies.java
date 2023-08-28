//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.08.28 um 09:44:23 AM CEST 
//


package eu.erasmuswithoutpaper.api.imobilities.endpoints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 *                     This describes the "incoming part" of a single Student Mobility for Studies.
 *                     "Incoming part" is the set of mobility's properties which the *receiving* HEI
 *                     is the master of.
 * 
 *                     In the future, it may become a "subclass" of a more generic Mobility parent class
 *                     (and some of the fields might be moved to the parent).
 *                 
 * 
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
 *         &lt;element name="status" type="{https://github.com/erasmus-without-paper/ewp-specs-api-imobilities/blob/stable-v1/endpoints/get-response.xsd}NominationStatus"/&gt;
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="actual-arrival-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="actual-departure-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
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
    "status",
    "comment",
    "actualArrivalDate",
    "actualDepartureDate"
})
@XmlRootElement(name = "student-mobility-for-studies")
public class StudentMobilityForStudies
    implements Serializable
{

    @XmlElement(name = "omobility-id", required = true)
    protected String omobilityId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected NominationStatus status;
    protected String comment;
    @XmlElement(name = "actual-arrival-date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar actualArrivalDate;
    @XmlElement(name = "actual-departure-date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar actualDepartureDate;

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
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link NominationStatus }
     *     
     */
    public NominationStatus getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link NominationStatus }
     *     
     */
    public void setStatus(NominationStatus value) {
        this.status = value;
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
     * Ruft den Wert der actualArrivalDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualArrivalDate() {
        return actualArrivalDate;
    }

    /**
     * Legt den Wert der actualArrivalDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualArrivalDate(XMLGregorianCalendar value) {
        this.actualArrivalDate = value;
    }

    /**
     * Ruft den Wert der actualDepartureDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualDepartureDate() {
        return actualDepartureDate;
    }

    /**
     * Legt den Wert der actualDepartureDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualDepartureDate(XMLGregorianCalendar value) {
        this.actualDepartureDate = value;
    }

}
