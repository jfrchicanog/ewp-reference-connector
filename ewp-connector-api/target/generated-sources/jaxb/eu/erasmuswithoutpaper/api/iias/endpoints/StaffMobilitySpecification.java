//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 09:03:38 AM CEST 
//


package eu.erasmuswithoutpaper.api.iias.endpoints;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 A common parent class for all staff mobility specifications.
 *             
 * 
 * <p>Java-Klasse für StaffMobilitySpecification complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="StaffMobilitySpecification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}MobilitySpecification"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="total-days-per-year"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *               &lt;minExclusive value="0"/&gt;
 *               &lt;fractionDigits value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StaffMobilitySpecification", propOrder = {
    "totalDaysPerYear"
})
@XmlSeeAlso({
    StaffTrainingMobilitySpec.class,
    StaffTeacherMobilitySpec.class
})
public abstract class StaffMobilitySpecification
    extends MobilitySpecification
    implements Serializable
{

    @XmlElement(name = "total-days-per-year", required = true)
    protected BigDecimal totalDaysPerYear;

    /**
     * Ruft den Wert der totalDaysPerYear-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalDaysPerYear() {
        return totalDaysPerYear;
    }

    /**
     * Legt den Wert der totalDaysPerYear-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalDaysPerYear(BigDecimal value) {
        this.totalDaysPerYear = value;
    }

}
