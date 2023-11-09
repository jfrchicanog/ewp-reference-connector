//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.09 um 08:55:56 PM CET 
//


package eu.erasmuswithoutpaper.api.iias.endpoints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für SubjectArea complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SubjectArea"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isced-f-code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isced-clarification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubjectArea", propOrder = {
    "iscedFCode",
    "iscedClarification"
})
public class SubjectArea
    implements Serializable
{

    @XmlElement(name = "isced-f-code", required = true)
    protected String iscedFCode;
    @XmlElement(name = "isced-clarification")
    protected String iscedClarification;

    /**
     * Ruft den Wert der iscedFCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIscedFCode() {
        return iscedFCode;
    }

    /**
     * Legt den Wert der iscedFCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIscedFCode(String value) {
        this.iscedFCode = value;
    }

    /**
     * Ruft den Wert der iscedClarification-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIscedClarification() {
        return iscedClarification;
    }

    /**
     * Legt den Wert der iscedClarification-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIscedClarification(String value) {
        this.iscedClarification = value;
    }

}
