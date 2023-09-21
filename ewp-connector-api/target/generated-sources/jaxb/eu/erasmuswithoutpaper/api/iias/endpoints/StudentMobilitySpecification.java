//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 12:28:50 PM CEST 
//


package eu.erasmuswithoutpaper.api.iias.endpoints;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 A common parent class for all student mobility specifications.
 *             
 * 
 * <p>Java-Klasse für StudentMobilitySpecification complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="StudentMobilitySpecification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}MobilitySpecification"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="total-months-per-year"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal"&gt;
 *               &lt;minExclusive value="0"/&gt;
 *               &lt;fractionDigits value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="blended" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="eqf-level" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}EqfLevel" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentMobilitySpecification", propOrder = {
    "totalMonthsPerYear",
    "blended",
    "eqfLevel"
})
@XmlSeeAlso({
    StudentTraineeshipMobilitySpec.class,
    StudentStudiesMobilitySpec.class
})
public abstract class StudentMobilitySpecification
    extends MobilitySpecification
    implements Serializable
{

    @XmlElement(name = "total-months-per-year", required = true)
    protected BigDecimal totalMonthsPerYear;
    protected boolean blended;
    @XmlElement(name = "eqf-level", type = Byte.class)
    protected List<Byte> eqfLevel;

    /**
     * Ruft den Wert der totalMonthsPerYear-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalMonthsPerYear() {
        return totalMonthsPerYear;
    }

    /**
     * Legt den Wert der totalMonthsPerYear-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalMonthsPerYear(BigDecimal value) {
        this.totalMonthsPerYear = value;
    }

    /**
     * Ruft den Wert der blended-Eigenschaft ab.
     * 
     */
    public boolean isBlended() {
        return blended;
    }

    /**
     * Legt den Wert der blended-Eigenschaft fest.
     * 
     */
    public void setBlended(boolean value) {
        this.blended = value;
    }

    /**
     * Gets the value of the eqfLevel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eqfLevel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEqfLevel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Byte }
     * 
     * 
     */
    public List<Byte> getEqfLevel() {
        if (eqfLevel == null) {
            eqfLevel = new ArrayList<Byte>();
        }
        return this.eqfLevel;
    }

}
