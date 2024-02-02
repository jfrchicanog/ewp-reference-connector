//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 06:35:56 PM CET 
//


package eu.erasmuswithoutpaper.api.factsheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.architecture.HTTPWithOptionalLang;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;


/**
 * <p>Java-Klasse für InformationEntry complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="InformationEntry"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="email" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Email"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1}phone-number"/&gt;
 *         &lt;element name="website-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformationEntry", propOrder = {
    "email",
    "phoneNumber",
    "websiteUrl"
})
public class InformationEntry
    implements Serializable
{

    @XmlElement(required = true)
    protected String email;
    @XmlElement(name = "phone-number", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1", required = true)
    protected PhoneNumber phoneNumber;
    @XmlElement(name = "website-url", required = true)
    protected List<HTTPWithOptionalLang> websiteUrl;

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

}
