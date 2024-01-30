//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 10:07:40 AM CET 
//


package eu.erasmuswithoutpaper.api.omobilities.las.endpoints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;


/**
 * <p>Java-Klasse für MobilityInstitution complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MobilityInstitution"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ounit-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="contact-person"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="given-names" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="family-name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="email" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Email"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1}phone-number" minOccurs="0"/&gt;
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
@XmlType(name = "MobilityInstitution", propOrder = {
    "heiId",
    "ounitName",
    "ounitId",
    "contactPerson"
})
public class MobilityInstitution
    implements Serializable
{

    @XmlElement(name = "hei-id", required = true)
    protected String heiId;
    @XmlElement(name = "ounit-name")
    protected String ounitName;
    @XmlElement(name = "ounit-id")
    protected String ounitId;
    @XmlElement(name = "contact-person", required = true)
    protected MobilityInstitution.ContactPerson contactPerson;

    /**
     * Ruft den Wert der heiId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeiId() {
        return heiId;
    }

    /**
     * Legt den Wert der heiId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeiId(String value) {
        this.heiId = value;
    }

    /**
     * Ruft den Wert der ounitName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOunitName() {
        return ounitName;
    }

    /**
     * Legt den Wert der ounitName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOunitName(String value) {
        this.ounitName = value;
    }

    /**
     * Ruft den Wert der ounitId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOunitId() {
        return ounitId;
    }

    /**
     * Legt den Wert der ounitId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOunitId(String value) {
        this.ounitId = value;
    }

    /**
     * Ruft den Wert der contactPerson-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MobilityInstitution.ContactPerson }
     *     
     */
    public MobilityInstitution.ContactPerson getContactPerson() {
        return contactPerson;
    }

    /**
     * Legt den Wert der contactPerson-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MobilityInstitution.ContactPerson }
     *     
     */
    public void setContactPerson(MobilityInstitution.ContactPerson value) {
        this.contactPerson = value;
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
     *         &lt;element name="given-names" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="family-name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="email" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Email"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1}phone-number" minOccurs="0"/&gt;
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
        "givenNames",
        "familyName",
        "email",
        "phoneNumber"
    })
    public static class ContactPerson
        implements Serializable
    {

        @XmlElement(name = "given-names", required = true)
        protected String givenNames;
        @XmlElement(name = "family-name", required = true)
        protected String familyName;
        @XmlElement(required = true)
        protected String email;
        @XmlElement(name = "phone-number", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1")
        protected PhoneNumber phoneNumber;

        /**
         * Ruft den Wert der givenNames-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGivenNames() {
            return givenNames;
        }

        /**
         * Legt den Wert der givenNames-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGivenNames(String value) {
            this.givenNames = value;
        }

        /**
         * Ruft den Wert der familyName-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFamilyName() {
            return familyName;
        }

        /**
         * Legt den Wert der familyName-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFamilyName(String value) {
            this.familyName = value;
        }

        /**
         * Ruft den Wert der email-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEmail() {
            return email;
        }

        /**
         * Legt den Wert der email-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEmail(String value) {
            this.email = value;
        }

        /**
         * Ruft den Wert der phoneNumber-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link PhoneNumber }
         *     
         */
        public PhoneNumber getPhoneNumber() {
            return phoneNumber;
        }

        /**
         * Legt den Wert der phoneNumber-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link PhoneNumber }
         *     
         */
        public void setPhoneNumber(PhoneNumber value) {
            this.phoneNumber = value;
        }

    }

}
