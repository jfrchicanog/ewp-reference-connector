//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.09 um 11:57:55 PM CET 
//


package eu.erasmuswithoutpaper.api.echo;

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
 *         &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="echo" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "echo"
})
@XmlRootElement(name = "response", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-echo/tree/stable-v2")
public class Response
    implements Serializable
{

    @XmlElement(name = "hei-id", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-echo/tree/stable-v2")
    protected List<String> heiId;
    @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-echo/tree/stable-v2")
    protected List<String> echo;

    /**
     * Gets the value of the heiId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the heiId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeiId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getHeiId() {
        if (heiId == null) {
            heiId = new ArrayList<String>();
        }
        return this.heiId;
    }

    /**
     * Gets the value of the echo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the echo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEcho().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEcho() {
        if (echo == null) {
            echo = new ArrayList<String>();
        }
        return this.echo;
    }

}
