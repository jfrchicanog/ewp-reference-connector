//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 09:25:12 AM CET 
//


package eu.erasmuswithoutpaper.api.imobilities.endpoints;

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
 *         &lt;group ref="{https://github.com/erasmus-without-paper/ewp-specs-api-imobilities/blob/stable-v1/endpoints/get-response.xsd}SequenceOfIncomingMobilities"/&gt;
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
    "singleIncomingMobilityObject"
})
@XmlRootElement(name = "imobilities-get-response")
public class ImobilitiesGetResponse
    implements Serializable
{

    @XmlElement(name = "student-mobility-for-studies")
    protected List<StudentMobilityForStudies> singleIncomingMobilityObject;

    /**
     * 
     *                         A list of matching mobilities.
     * 
     *                         Currently there's only type of mobility exposed by this API. More types MAY
     *                         come in the future.
     *                     Gets the value of the singleIncomingMobilityObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the singleIncomingMobilityObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSingleIncomingMobilityObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudentMobilityForStudies }
     * 
     * 
     */
    public List<StudentMobilityForStudies> getSingleIncomingMobilityObject() {
        if (singleIncomingMobilityObject == null) {
            singleIncomingMobilityObject = new ArrayList<StudentMobilityForStudies>();
        }
        return this.singleIncomingMobilityObject;
    }

}
