//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.08.28 um 10:24:58 AM CEST 
//


package eu.erasmuswithoutpaper.api.types.contact;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.types.address.FlexibleAddress;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;


/**
 * 
 *                 An abstract, non-identifiable contact data type.
 * 
 *                 Please read https://github.com/erasmus-without-paper/ewp-specs-types-contact
 *                 for introduction on this data type.
 *             
 * 
 * <p>Java-Klasse für Contact complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Contact"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="contact-name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *         &lt;element name="person-given-names" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="person-family-name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="person-gender" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Gender" minOccurs="0"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1}phone-number" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1}fax-number" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Email" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}street-address" minOccurs="0"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}mailing-address" minOccurs="0"/&gt;
 *         &lt;element name="role-description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contact", propOrder = {
    "contactName",
    "personGivenNames",
    "personFamilyName",
    "personGender",
    "phoneNumber",
    "faxNumber",
    "email",
    "streetAddress",
    "mailingAddress",
    "roleDescription"
})
public class Contact
    implements Serializable
{

    @XmlElement(name = "contact-name", required = true)
    protected List<StringWithOptionalLang> contactName;
    @XmlElement(name = "person-given-names")
    protected List<StringWithOptionalLang> personGivenNames;
    @XmlElement(name = "person-family-name")
    protected List<StringWithOptionalLang> personFamilyName;
    @XmlElement(name = "person-gender")
    protected BigInteger personGender;
    @XmlElement(name = "phone-number", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1")
    protected List<PhoneNumber> phoneNumber;
    @XmlElement(name = "fax-number", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1")
    protected List<PhoneNumber> faxNumber;
    protected List<String> email;
    @XmlElement(name = "street-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
    protected FlexibleAddress streetAddress;
    @XmlElement(name = "mailing-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
    protected FlexibleAddress mailingAddress;
    @XmlElement(name = "role-description")
    protected List<MultilineStringWithOptionalLang> roleDescription;

    /**
     * Gets the value of the contactName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringWithOptionalLang }
     * 
     * 
     */
    public List<StringWithOptionalLang> getContactName() {
        if (contactName == null) {
            contactName = new ArrayList<StringWithOptionalLang>();
        }
        return this.contactName;
    }

    /**
     * Gets the value of the personGivenNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personGivenNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonGivenNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringWithOptionalLang }
     * 
     * 
     */
    public List<StringWithOptionalLang> getPersonGivenNames() {
        if (personGivenNames == null) {
            personGivenNames = new ArrayList<StringWithOptionalLang>();
        }
        return this.personGivenNames;
    }

    /**
     * Gets the value of the personFamilyName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personFamilyName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonFamilyName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringWithOptionalLang }
     * 
     * 
     */
    public List<StringWithOptionalLang> getPersonFamilyName() {
        if (personFamilyName == null) {
            personFamilyName = new ArrayList<StringWithOptionalLang>();
        }
        return this.personFamilyName;
    }

    /**
     * Ruft den Wert der personGender-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPersonGender() {
        return personGender;
    }

    /**
     * Legt den Wert der personGender-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPersonGender(BigInteger value) {
        this.personGender = value;
    }

    /**
     * 
     *                         A list of phone numbers at which this contact can be reached.
     *                     Gets the value of the phoneNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phoneNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhoneNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PhoneNumber }
     * 
     * 
     */
    public List<PhoneNumber> getPhoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new ArrayList<PhoneNumber>();
        }
        return this.phoneNumber;
    }

    /**
     * 
     *                         A list of fax numbers at which this contact can be reached.
     *                     Gets the value of the faxNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the faxNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFaxNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PhoneNumber }
     * 
     * 
     */
    public List<PhoneNumber> getFaxNumber() {
        if (faxNumber == null) {
            faxNumber = new ArrayList<PhoneNumber>();
        }
        return this.faxNumber;
    }

    /**
     * Gets the value of the email property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the email property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEmail() {
        if (email == null) {
            email = new ArrayList<String>();
        }
        return this.email;
    }

    /**
     * 
     *                         Street address of the place where the contact can be found (room number, floor,
     *                         etc.)
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
     *                         A postal address at which people should send paper documents for this contact.
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
     * Gets the value of the roleDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultilineStringWithOptionalLang }
     * 
     * 
     */
    public List<MultilineStringWithOptionalLang> getRoleDescription() {
        if (roleDescription == null) {
            roleDescription = new ArrayList<MultilineStringWithOptionalLang>();
        }
        return this.roleDescription;
    }

}
