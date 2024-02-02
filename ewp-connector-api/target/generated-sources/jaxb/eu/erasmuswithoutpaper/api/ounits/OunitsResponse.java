//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 05:21:36 PM CET 
//


package eu.erasmuswithoutpaper.api.ounits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.architecture.HTTPWithOptionalLang;
import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.types.address.FlexibleAddress;
import eu.erasmuswithoutpaper.api.types.contact.Contact;


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
 *         &lt;element name="ounit" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
 *                   &lt;element name="ounit-code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="abbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="parent-ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}street-address" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}mailing-address" minOccurs="0"/&gt;
 *                   &lt;element name="website-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="logo-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS" minOccurs="0"/&gt;
 *                   &lt;element name="mobility-factsheet-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}contact" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "ounit"
})
@XmlRootElement(name = "ounits-response", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2")
public class OunitsResponse
    implements Serializable
{

    @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2")
    protected List<OunitsResponse.Ounit> ounit;

    /**
     * Gets the value of the ounit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ounit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOunit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OunitsResponse.Ounit }
     * 
     * 
     */
    public List<OunitsResponse.Ounit> getOunit() {
        if (ounit == null) {
            ounit = new ArrayList<OunitsResponse.Ounit>();
        }
        return this.ounit;
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
     *         &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
     *         &lt;element name="ounit-code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
     *         &lt;element name="abbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="parent-ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}street-address" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}mailing-address" minOccurs="0"/&gt;
     *         &lt;element name="website-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="logo-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS" minOccurs="0"/&gt;
     *         &lt;element name="mobility-factsheet-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}contact" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "ounitId",
        "ounitCode",
        "name",
        "abbreviation",
        "parentOunitId",
        "streetAddress",
        "mailingAddress",
        "websiteUrl",
        "logoUrl",
        "mobilityFactsheetUrl",
        "contact"
    })
    public static class Ounit
        implements Serializable
    {

        @XmlElement(name = "ounit-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2", required = true)
        protected String ounitId;
        @XmlElement(name = "ounit-code", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2", required = true)
        protected String ounitCode;
        @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2", required = true)
        protected List<StringWithOptionalLang> name;
        @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2")
        protected String abbreviation;
        @XmlElement(name = "parent-ounit-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2")
        protected String parentOunitId;
        @XmlElement(name = "street-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
        protected FlexibleAddress streetAddress;
        @XmlElement(name = "mailing-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
        protected FlexibleAddress mailingAddress;
        @XmlElement(name = "website-url", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2")
        protected List<HTTPWithOptionalLang> websiteUrl;
        @XmlElement(name = "logo-url", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2")
        @XmlSchemaType(name = "anyURI")
        protected String logoUrl;
        @XmlElement(name = "mobility-factsheet-url", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-ounits/tree/stable-v2")
        protected List<HTTPWithOptionalLang> mobilityFactsheetUrl;
        @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1")
        protected List<Contact> contact;

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
         * Ruft den Wert der ounitCode-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOunitCode() {
            return ounitCode;
        }

        /**
         * Legt den Wert der ounitCode-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOunitCode(String value) {
            this.ounitCode = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the name property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StringWithOptionalLang }
         * 
         * 
         */
        public List<StringWithOptionalLang> getName() {
            if (name == null) {
                name = new ArrayList<StringWithOptionalLang>();
            }
            return this.name;
        }

        /**
         * Ruft den Wert der abbreviation-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAbbreviation() {
            return abbreviation;
        }

        /**
         * Legt den Wert der abbreviation-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAbbreviation(String value) {
            this.abbreviation = value;
        }

        /**
         * Ruft den Wert der parentOunitId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParentOunitId() {
            return parentOunitId;
        }

        /**
         * Legt den Wert der parentOunitId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParentOunitId(String value) {
            this.parentOunitId = value;
        }

        /**
         * 
         *                                         Street address of this organizational unit.
         * 
         *                                         This is the address which should work when, for example, the user pastes it
         *                                         into Google Maps. If this organizational unit is spread between multiple
         *                                         addresses, then this address should refer to a "primary" building. If it
         *                                         doesn't have a "primary" building, then the address should be skipped
         *                                         (multiple addresses can still be provided via separate "contact" elements).
         *                                     
         * 
         * @return
         *     possible object is
         *     {@link FlexibleAddress }
         *     
         */
        public FlexibleAddress getStreetAddress() {
            return streetAddress;
        }

        /**
         * Legt den Wert der streetAddress-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link FlexibleAddress }
         *     
         */
        public void setStreetAddress(FlexibleAddress value) {
            this.streetAddress = value;
        }

        /**
         * 
         *                                         The postal address of this organizational unit. It MAY be the same as
         *                                         street-address, but doesn't have to be (e.g. it can be a PO box).
         * 
         *                                         This is the primary address. Note, that more addresses may be provided by using
         *                                         the "contact" element, if necessary.
         *                                     
         * 
         * @return
         *     possible object is
         *     {@link FlexibleAddress }
         *     
         */
        public FlexibleAddress getMailingAddress() {
            return mailingAddress;
        }

        /**
         * Legt den Wert der mailingAddress-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link FlexibleAddress }
         *     
         */
        public void setMailingAddress(FlexibleAddress value) {
            this.mailingAddress = value;
        }

        /**
         * Gets the value of the websiteUrl property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the websiteUrl property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWebsiteUrl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HTTPWithOptionalLang }
         * 
         * 
         */
        public List<HTTPWithOptionalLang> getWebsiteUrl() {
            if (websiteUrl == null) {
                websiteUrl = new ArrayList<HTTPWithOptionalLang>();
            }
            return this.websiteUrl;
        }

        /**
         * Ruft den Wert der logoUrl-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLogoUrl() {
            return logoUrl;
        }

        /**
         * Legt den Wert der logoUrl-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLogoUrl(String value) {
            this.logoUrl = value;
        }

        /**
         * Gets the value of the mobilityFactsheetUrl property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mobilityFactsheetUrl property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMobilityFactsheetUrl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HTTPWithOptionalLang }
         * 
         * 
         */
        public List<HTTPWithOptionalLang> getMobilityFactsheetUrl() {
            if (mobilityFactsheetUrl == null) {
                mobilityFactsheetUrl = new ArrayList<HTTPWithOptionalLang>();
            }
            return this.mobilityFactsheetUrl;
        }

        /**
         * 
         *                                         A list of important contacts - phones, emails, addresses.
         * 
         *                                         In context of EWP, this will most often be mobility-related contacts, but it's
         *                                         not a rule.
         *                                     Gets the value of the contact property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contact property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContact().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Contact }
         * 
         * 
         */
        public List<Contact> getContact() {
            if (contact == null) {
                contact = new ArrayList<Contact>();
            }
            return this.contact;
        }

    }

}
