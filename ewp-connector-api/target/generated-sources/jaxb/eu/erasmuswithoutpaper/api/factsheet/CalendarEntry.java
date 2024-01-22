//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.22 um 10:33:29 AM CET 
//


package eu.erasmuswithoutpaper.api.factsheet;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für CalendarEntry complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CalendarEntry"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="autumn-term" type="{http://www.w3.org/2001/XMLSchema}gMonthDay"/&gt;
 *         &lt;element name="spring-term" type="{http://www.w3.org/2001/XMLSchema}gMonthDay"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalendarEntry", propOrder = {
    "autumnTerm",
    "springTerm"
})
public class CalendarEntry
    implements Serializable
{

    @XmlElement(name = "autumn-term", required = true)
    @XmlSchemaType(name = "gMonthDay")
    protected XMLGregorianCalendar autumnTerm;
    @XmlElement(name = "spring-term", required = true)
    @XmlSchemaType(name = "gMonthDay")
    protected XMLGregorianCalendar springTerm;

    /**
     * Ruft den Wert der autumnTerm-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAutumnTerm() {
        return autumnTerm;
    }

    /**
     * Legt den Wert der autumnTerm-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAutumnTerm(XMLGregorianCalendar value) {
        this.autumnTerm = value;
    }

    /**
     * Ruft den Wert der springTerm-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSpringTerm() {
        return springTerm;
    }

    /**
     * Legt den Wert der springTerm-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSpringTerm(XMLGregorianCalendar value) {
        this.springTerm = value;
    }

}
