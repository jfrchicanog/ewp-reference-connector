//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 12:19:15 PM CET 
//


package eu.erasmuswithoutpaper.api.architecture;

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
 *         &lt;element name="developer-message" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineString"/&gt;
 *         &lt;element name="user-message" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "developerMessage",
    "userMessage"
})
@XmlRootElement(name = "error-response")
public class ErrorResponse
    implements Serializable
{

    @XmlElement(name = "developer-message", required = true)
    protected MultilineString developerMessage;
    @XmlElement(name = "user-message")
    protected List<MultilineStringWithOptionalLang> userMessage;

    /**
     * Ruft den Wert der developerMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultilineString }
     *     
     */
    public MultilineString getDeveloperMessage() {
        return developerMessage;
    }

    /**
     * Legt den Wert der developerMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilineString }
     *     
     */
    public void setDeveloperMessage(MultilineString value) {
        this.developerMessage = value;
    }

    /**
     * Gets the value of the userMessage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userMessage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultilineStringWithOptionalLang }
     * 
     * 
     */
    public List<MultilineStringWithOptionalLang> getUserMessage() {
        if (userMessage == null) {
            userMessage = new ArrayList<MultilineStringWithOptionalLang>();
        }
        return this.userMessage;
    }

}
