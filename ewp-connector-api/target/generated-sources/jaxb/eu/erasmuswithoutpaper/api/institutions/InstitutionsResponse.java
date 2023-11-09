//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.09 um 08:55:56 PM CET 
//


package eu.erasmuswithoutpaper.api.institutions;

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
import eu.erasmuswithoutpaper.api.registry.OtherHeiId;
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
 *         &lt;element name="hei" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="other-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}OtherHeiId" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="abbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}street-address" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}mailing-address" minOccurs="0"/&gt;
 *                   &lt;element name="website-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="logo-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS" minOccurs="0"/&gt;
 *                   &lt;element name="mobility-factsheet-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="course-catalogue-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}contact" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="root-ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                   &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "hei"
})
@XmlRootElement(name = "institutions-response")
public class InstitutionsResponse
    implements Serializable
{

    protected List<InstitutionsResponse.Hei> hei;

    /**
     * Gets the value of the hei property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hei property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHei().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstitutionsResponse.Hei }
     * 
     * 
     */
    public List<InstitutionsResponse.Hei> getHei() {
        if (hei == null) {
            hei = new ArrayList<InstitutionsResponse.Hei>();
        }
        return this.hei;
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
     *         &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="other-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}OtherHeiId" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
     *         &lt;element name="abbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}street-address" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}mailing-address" minOccurs="0"/&gt;
     *         &lt;element name="website-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="logo-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS" minOccurs="0"/&gt;
     *         &lt;element name="mobility-factsheet-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="course-catalogue-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}contact" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="root-ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
     *         &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "heiId",
        "otherId",
        "name",
        "abbreviation",
        "streetAddress",
        "mailingAddress",
        "websiteUrl",
        "logoUrl",
        "mobilityFactsheetUrl",
        "courseCatalogueUrl",
        "contact",
        "rootOunitId",
        "ounitId"
    })
    public static class Hei
        implements Serializable
    {

        @XmlElement(name = "hei-id", required = true)
        protected String heiId;
        @XmlElement(name = "other-id")
        protected List<OtherHeiId> otherId;
        @XmlElement(required = true)
        protected List<StringWithOptionalLang> name;
        protected String abbreviation;
        @XmlElement(name = "street-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
        protected FlexibleAddress streetAddress;
        @XmlElement(name = "mailing-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
        protected FlexibleAddress mailingAddress;
        @XmlElement(name = "website-url")
        protected List<HTTPWithOptionalLang> websiteUrl;
        @XmlElement(name = "logo-url")
        @XmlSchemaType(name = "anyURI")
        protected String logoUrl;
        @XmlElement(name = "mobility-factsheet-url")
        protected List<HTTPWithOptionalLang> mobilityFactsheetUrl;
        @XmlElement(name = "course-catalogue-url")
        protected List<HTTPWithOptionalLang> courseCatalogueUrl;
        @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1")
        protected List<Contact> contact;
        @XmlElement(name = "root-ounit-id")
        protected String rootOunitId;
        @XmlElement(name = "ounit-id")
        protected List<String> ounitId;

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
         * Gets the value of the otherId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the otherId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOtherId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OtherHeiId }
         * 
         * 
         */
        public List<OtherHeiId> getOtherId() {
            if (otherId == null) {
                otherId = new ArrayList<OtherHeiId>();
            }
            return this.otherId;
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
         * 
         *                                         The street address of the institution.
         * 
         *                                         This is the address which should work when, for example, the user pastes it
         *                                         into Google Maps. If this HEI has many campuses, then this address should refer
         *                                         to a "main" campus. If this HEI doesn't have a "main" campus, then the address
         *                                         should simply contain the name of the institution, a city, and a country. In
         *                                         extreme cases, even a single country entry is better than nothing. Also see
         *                                         a related discussion here:
         * 
         *                                         https://github.com/erasmus-without-paper/ewp-specs-api-ounits/issues/2#issuecomment-266775582
         * 
         *                                         This is the primary address. Note, that more addresses may be provided by using
         *                                         the "contact" element.
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
         *                                         The postal address of the institution. It MAY be the same as street-address,
         *                                         but doesn't have to be (e.g. it can be a PO box).
         * 
         *                                         This is the primary address. Note, that more addresses may be provided by using
         *                                         the "contact" element.
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
         * Gets the value of the courseCatalogueUrl property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the courseCatalogueUrl property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCourseCatalogueUrl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HTTPWithOptionalLang }
         * 
         * 
         */
        public List<HTTPWithOptionalLang> getCourseCatalogueUrl() {
            if (courseCatalogueUrl == null) {
                courseCatalogueUrl = new ArrayList<HTTPWithOptionalLang>();
            }
            return this.courseCatalogueUrl;
        }

        /**
         * 
         *                                         A list of important contacts - phones, emails, addresses.
         * 
         *                                         In context of EWP, this will most often be mobility-related contacts, but it's
         *                                         not a rule. This means that server developers MAY choose to include all other
         *                                         kinds of contacts here (such as, for example, the phone number and address of
         *                                         the Institution's Library). Such contacts SHOULD however come AFTER the "more
         *                                         important" ones.
         * 
         *                                         See https://github.com/erasmus-without-paper/ewp-specs-types-contact for more
         *                                         information on the purpose and format of this element.
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

        /**
         * Ruft den Wert der rootOunitId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRootOunitId() {
            return rootOunitId;
        }

        /**
         * Legt den Wert der rootOunitId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRootOunitId(String value) {
            this.rootOunitId = value;
        }

        /**
         * Gets the value of the ounitId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ounitId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOunitId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getOunitId() {
            if (ounitId == null) {
                ounitId = new ArrayList<String>();
            }
            return this.ounitId;
        }

    }

}
