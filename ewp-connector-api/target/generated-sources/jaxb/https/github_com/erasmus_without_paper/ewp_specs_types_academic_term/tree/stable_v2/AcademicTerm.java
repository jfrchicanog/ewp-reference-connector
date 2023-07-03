//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.07.03 um 01:32:45 PM CEST 
//


package https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;


/**
 * <p>Java-Klasse für AcademicTerm complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="AcademicTerm"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="academic-year-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v2}AcademicYearId"/&gt;
 *         &lt;element name="term-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v2}TermId"/&gt;
 *         &lt;element name="display-name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *         &lt;element name="start-date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="end-date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcademicTerm", propOrder = {
    "academicYearId",
    "termId",
    "displayName",
    "startDate",
    "endDate"
})
public class AcademicTerm
    implements Serializable
{

    @XmlElement(name = "academic-year-id", required = true)
    protected String academicYearId;
    @XmlElement(name = "term-id", required = true)
    protected TermId termId;
    @XmlElement(name = "display-name", required = true)
    protected List<StringWithOptionalLang> displayName;
    @XmlElement(name = "start-date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "end-date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;

    /**
     * Ruft den Wert der academicYearId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcademicYearId() {
        return academicYearId;
    }

    /**
     * Legt den Wert der academicYearId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcademicYearId(String value) {
        this.academicYearId = value;
    }

    /**
     * Ruft den Wert der termId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TermId }
     *     
     */
    public TermId getTermId() {
        return termId;
    }

    /**
     * Legt den Wert der termId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TermId }
     *     
     */
    public void setTermId(TermId value) {
        this.termId = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringWithOptionalLang }
     * 
     * 
     */
    public List<StringWithOptionalLang> getDisplayName() {
        if (displayName == null) {
            displayName = new ArrayList<StringWithOptionalLang>();
        }
        return this.displayName;
    }

    /**
     * Ruft den Wert der startDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Legt den Wert der startDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Ruft den Wert der endDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Legt den Wert der endDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

}
