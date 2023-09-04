//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.04 um 08:43:39 AM CEST 
//


package https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 An identifier of the academic term.
 * 
 *                 This identifier does not relate to a year, so to identify an academic term that is fixed in time you
 *                 need to also specify the academic year (see `AcademicTerm` type).
 *             
 * 
 * <p>Java-Klasse für TermId complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TermId"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="term-number" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="total-terms" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermId", propOrder = {
    "termNumber",
    "totalTerms"
})
public class TermId
    implements Serializable
{

    @XmlElement(name = "term-number", required = true)
    protected BigInteger termNumber;
    @XmlElement(name = "total-terms", required = true)
    protected BigInteger totalTerms;

    /**
     * Ruft den Wert der termNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTermNumber() {
        return termNumber;
    }

    /**
     * Legt den Wert der termNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTermNumber(BigInteger value) {
        this.termNumber = value;
    }

    /**
     * Ruft den Wert der totalTerms-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalTerms() {
        return totalTerms;
    }

    /**
     * Legt den Wert der totalTerms-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalTerms(BigInteger value) {
        this.totalTerms = value;
    }

}
