//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.08.28 um 10:30:34 AM CEST 
//


package eu.erasmuswithoutpaper.api.omobilities.las.endpoints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;


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
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}success-user-message" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "successUserMessage"
})
@XmlRootElement(name = "omobility-las-update-response", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/update-response.xsd")
public class OmobilityLasUpdateResponse
    implements Serializable
{

    @XmlElement(name = "success-user-message", namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd")
    protected List<MultilineStringWithOptionalLang> successUserMessage;

    /**
     * 
     *                             There are several possible update types covered by this API, but this is what
     *                             all of them have in common - for each of these requests, the server returns an
     *                             optional message to be displayed for the user, who initiated this request.
     *                         Gets the value of the successUserMessage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the successUserMessage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSuccessUserMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultilineStringWithOptionalLang }
     * 
     * 
     */
    public List<MultilineStringWithOptionalLang> getSuccessUserMessage() {
        if (successUserMessage == null) {
            successUserMessage = new ArrayList<MultilineStringWithOptionalLang>();
        }
        return this.successUserMessage;
    }

}
