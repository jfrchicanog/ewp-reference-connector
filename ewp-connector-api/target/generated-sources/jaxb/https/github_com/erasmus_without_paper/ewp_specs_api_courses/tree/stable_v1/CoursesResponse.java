//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.04 um 08:43:39 AM CEST 
//


package https.github_com.erasmus_without_paper.ewp_specs_api_courses.tree.stable_v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.erasmuswithoutpaper.api.architecture.HTTPWithOptionalLang;
import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.types.academicterm.AcademicTerm;


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
 *         &lt;element name="learningOpportunitySpecification" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="los-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LosID"/&gt;
 *                   &lt;element name="los-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                   &lt;element name="title" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="type" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;enumeration value="Degree Programme"/&gt;
 *                         &lt;enumeration value="Module"/&gt;
 *                         &lt;enumeration value="Course"/&gt;
 *                         &lt;enumeration value="Class"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="subjectArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="iscedCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="eqfLevelProvided" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}EqfLevel" minOccurs="0"/&gt;
 *                   &lt;element name="url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="specifies" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="learningOpportunityInstance" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="loi-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LoiID"/&gt;
 *                                       &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                                       &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                                       &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1}academic-term" minOccurs="0"/&gt;
 *                                       &lt;element name="gradingScheme" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="label" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                                                 &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="resultDistribution" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="category" maxOccurs="unbounded"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                                         &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="credit" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="level" minOccurs="0"&gt;
 *                                                   &lt;simpleType&gt;
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                                       &lt;enumeration value="Bachelor"/&gt;
 *                                                       &lt;enumeration value="Master"/&gt;
 *                                                       &lt;enumeration value="PhD"/&gt;
 *                                                     &lt;/restriction&gt;
 *                                                   &lt;/simpleType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="languageOfInstruction" type="{http://www.w3.org/2001/XMLSchema}language" minOccurs="0"/&gt;
 *                                       &lt;element name="engagementHours" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="contains" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="los-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LosID" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "learningOpportunitySpecification"
})
@XmlRootElement(name = "courses-response")
public class CoursesResponse
    implements Serializable
{

    protected List<CoursesResponse.LearningOpportunitySpecification> learningOpportunitySpecification;

    /**
     * Gets the value of the learningOpportunitySpecification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the learningOpportunitySpecification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLearningOpportunitySpecification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CoursesResponse.LearningOpportunitySpecification }
     * 
     * 
     */
    public List<CoursesResponse.LearningOpportunitySpecification> getLearningOpportunitySpecification() {
        if (learningOpportunitySpecification == null) {
            learningOpportunitySpecification = new ArrayList<CoursesResponse.LearningOpportunitySpecification>();
        }
        return this.learningOpportunitySpecification;
    }


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
     *         &lt;element name="los-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LosID"/&gt;
     *         &lt;element name="los-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
     *         &lt;element name="title" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
     *         &lt;element name="type" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;enumeration value="Degree Programme"/&gt;
     *               &lt;enumeration value="Module"/&gt;
     *               &lt;enumeration value="Course"/&gt;
     *               &lt;enumeration value="Class"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="subjectArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="iscedCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="eqfLevelProvided" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}EqfLevel" minOccurs="0"/&gt;
     *         &lt;element name="url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="specifies" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="learningOpportunityInstance" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="loi-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LoiID"/&gt;
     *                             &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                             &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                             &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1}academic-term" minOccurs="0"/&gt;
     *                             &lt;element name="gradingScheme" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="label" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
     *                                       &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="resultDistribution" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="category" maxOccurs="unbounded"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                                               &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="credit" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="level" minOccurs="0"&gt;
     *                                         &lt;simpleType&gt;
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                                             &lt;enumeration value="Bachelor"/&gt;
     *                                             &lt;enumeration value="Master"/&gt;
     *                                             &lt;enumeration value="PhD"/&gt;
     *                                           &lt;/restriction&gt;
     *                                         &lt;/simpleType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="languageOfInstruction" type="{http://www.w3.org/2001/XMLSchema}language" minOccurs="0"/&gt;
     *                             &lt;element name="engagementHours" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="contains" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="los-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LosID" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "losId",
        "losCode",
        "ounitId",
        "title",
        "type",
        "subjectArea",
        "iscedCode",
        "eqfLevelProvided",
        "url",
        "description",
        "specifies",
        "contains"
    })
    public static class LearningOpportunitySpecification
        implements Serializable
    {

        @XmlElement(name = "los-id", required = true)
        protected String losId;
        @XmlElement(name = "los-code")
        protected String losCode;
        @XmlElement(name = "ounit-id")
        protected String ounitId;
        @XmlElement(required = true)
        protected List<StringWithOptionalLang> title;
        protected String type;
        protected String subjectArea;
        protected String iscedCode;
        protected Byte eqfLevelProvided;
        protected List<HTTPWithOptionalLang> url;
        protected List<MultilineStringWithOptionalLang> description;
        protected CoursesResponse.LearningOpportunitySpecification.Specifies specifies;
        protected CoursesResponse.LearningOpportunitySpecification.Contains contains;

        /**
         * Ruft den Wert der losId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLosId() {
            return losId;
        }

        /**
         * Legt den Wert der losId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLosId(String value) {
            this.losId = value;
        }

        /**
         * Ruft den Wert der losCode-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLosCode() {
            return losCode;
        }

        /**
         * Legt den Wert der losCode-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLosCode(String value) {
            this.losCode = value;
        }

        /**
         * Ruft den Wert der ounitId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOunitId() {
            return ounitId;
        }

        /**
         * Legt den Wert der ounitId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOunitId(String value) {
            this.ounitId = value;
        }

        /**
         * Gets the value of the title property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the title property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTitle().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StringWithOptionalLang }
         * 
         * 
         */
        public List<StringWithOptionalLang> getTitle() {
            if (title == null) {
                title = new ArrayList<StringWithOptionalLang>();
            }
            return this.title;
        }

        /**
         * Ruft den Wert der type-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Legt den Wert der type-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Ruft den Wert der subjectArea-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubjectArea() {
            return subjectArea;
        }

        /**
         * Legt den Wert der subjectArea-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubjectArea(String value) {
            this.subjectArea = value;
        }

        /**
         * Ruft den Wert der iscedCode-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIscedCode() {
            return iscedCode;
        }

        /**
         * Legt den Wert der iscedCode-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIscedCode(String value) {
            this.iscedCode = value;
        }

        /**
         * Ruft den Wert der eqfLevelProvided-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getEqfLevelProvided() {
            return eqfLevelProvided;
        }

        /**
         * Legt den Wert der eqfLevelProvided-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setEqfLevelProvided(Byte value) {
            this.eqfLevelProvided = value;
        }

        /**
         * Gets the value of the url property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the url property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUrl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HTTPWithOptionalLang }
         * 
         * 
         */
        public List<HTTPWithOptionalLang> getUrl() {
            if (url == null) {
                url = new ArrayList<HTTPWithOptionalLang>();
            }
            return this.url;
        }

        /**
         * Gets the value of the description property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the description property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDescription().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultilineStringWithOptionalLang }
         * 
         * 
         */
        public List<MultilineStringWithOptionalLang> getDescription() {
            if (description == null) {
                description = new ArrayList<MultilineStringWithOptionalLang>();
            }
            return this.description;
        }

        /**
         * Ruft den Wert der specifies-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link CoursesResponse.LearningOpportunitySpecification.Specifies }
         *     
         */
        public CoursesResponse.LearningOpportunitySpecification.Specifies getSpecifies() {
            return specifies;
        }

        /**
         * Legt den Wert der specifies-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link CoursesResponse.LearningOpportunitySpecification.Specifies }
         *     
         */
        public void setSpecifies(CoursesResponse.LearningOpportunitySpecification.Specifies value) {
            this.specifies = value;
        }

        /**
         * Ruft den Wert der contains-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link CoursesResponse.LearningOpportunitySpecification.Contains }
         *     
         */
        public CoursesResponse.LearningOpportunitySpecification.Contains getContains() {
            return contains;
        }

        /**
         * Legt den Wert der contains-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link CoursesResponse.LearningOpportunitySpecification.Contains }
         *     
         */
        public void setContains(CoursesResponse.LearningOpportunitySpecification.Contains value) {
            this.contains = value;
        }


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
         *         &lt;element name="los-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LosID" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "losId"
        })
        public static class Contains
            implements Serializable
        {

            @XmlElement(name = "los-id")
            protected List<String> losId;

            /**
             * Gets the value of the losId property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the losId property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLosId().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getLosId() {
                if (losId == null) {
                    losId = new ArrayList<String>();
                }
                return this.losId;
            }

        }


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
         *         &lt;element name="learningOpportunityInstance" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="loi-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LoiID"/&gt;
         *                   &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *                   &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1}academic-term" minOccurs="0"/&gt;
         *                   &lt;element name="gradingScheme" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="label" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
         *                             &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="resultDistribution" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="category" maxOccurs="unbounded"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                                     &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                             &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="credit" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="level" minOccurs="0"&gt;
         *                               &lt;simpleType&gt;
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *                                   &lt;enumeration value="Bachelor"/&gt;
         *                                   &lt;enumeration value="Master"/&gt;
         *                                   &lt;enumeration value="PhD"/&gt;
         *                                 &lt;/restriction&gt;
         *                               &lt;/simpleType&gt;
         *                             &lt;/element&gt;
         *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="languageOfInstruction" type="{http://www.w3.org/2001/XMLSchema}language" minOccurs="0"/&gt;
         *                   &lt;element name="engagementHours" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
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
            "learningOpportunityInstance"
        })
        public static class Specifies
            implements Serializable
        {

            protected List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance> learningOpportunityInstance;

            /**
             * Gets the value of the learningOpportunityInstance property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the learningOpportunityInstance property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLearningOpportunityInstance().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance }
             * 
             * 
             */
            public List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance> getLearningOpportunityInstance() {
                if (learningOpportunityInstance == null) {
                    learningOpportunityInstance = new ArrayList<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance>();
                }
                return this.learningOpportunityInstance;
            }


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
             *         &lt;element name="loi-id" type="{https://github.com/erasmus-without-paper/ewp-specs-api-courses/tree/stable-v1}LoiID"/&gt;
             *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
             *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
             *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1}academic-term" minOccurs="0"/&gt;
             *         &lt;element name="gradingScheme" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="label" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
             *                   &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="resultDistribution" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="category" maxOccurs="unbounded"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *                           &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                   &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="credit" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="level" minOccurs="0"&gt;
             *                     &lt;simpleType&gt;
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
             *                         &lt;enumeration value="Bachelor"/&gt;
             *                         &lt;enumeration value="Master"/&gt;
             *                         &lt;enumeration value="PhD"/&gt;
             *                       &lt;/restriction&gt;
             *                     &lt;/simpleType&gt;
             *                   &lt;/element&gt;
             *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="languageOfInstruction" type="{http://www.w3.org/2001/XMLSchema}language" minOccurs="0"/&gt;
             *         &lt;element name="engagementHours" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
                "loiId",
                "start",
                "end",
                "academicTerm",
                "gradingScheme",
                "resultDistribution",
                "credit",
                "languageOfInstruction",
                "engagementHours"
            })
            public static class LearningOpportunityInstance
                implements Serializable
            {

                @XmlElement(name = "loi-id", required = true)
                protected String loiId;
                @XmlElement(required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar start;
                @XmlElement(required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar end;
                @XmlElement(name = "academic-term", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1")
                protected AcademicTerm academicTerm;
                protected CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme gradingScheme;
                protected CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution resultDistribution;
                protected List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit> credit;
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "language")
                protected String languageOfInstruction;
                protected BigDecimal engagementHours;

                /**
                 * Ruft den Wert der loiId-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLoiId() {
                    return loiId;
                }

                /**
                 * Legt den Wert der loiId-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLoiId(String value) {
                    this.loiId = value;
                }

                /**
                 * Ruft den Wert der start-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getStart() {
                    return start;
                }

                /**
                 * Legt den Wert der start-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setStart(XMLGregorianCalendar value) {
                    this.start = value;
                }

                /**
                 * Ruft den Wert der end-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getEnd() {
                    return end;
                }

                /**
                 * Legt den Wert der end-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setEnd(XMLGregorianCalendar value) {
                    this.end = value;
                }

                /**
                 * 
                 *                                                                 Describes the academic term in which the LOI took place.
                 * 
                 *                                                                 A note for server implementers: You SHOULD include this element for
                 *                                                                 "Course"-type learning opportunities ("Course" entities *always* span over a
                 *                                                                 single academic term). You SHOULD NOT include this element for "Degree
                 *                                                                 Programme"s, or any other learning opportunities which span over multiple
                 *                                                                 terms.
                 *                                                             
                 * 
                 * @return
                 *     possible object is
                 *     {@link AcademicTerm }
                 *     
                 */
                public AcademicTerm getAcademicTerm() {
                    return academicTerm;
                }

                /**
                 * Legt den Wert der academicTerm-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link AcademicTerm }
                 *     
                 */
                public void setAcademicTerm(AcademicTerm value) {
                    this.academicTerm = value;
                }

                /**
                 * Ruft den Wert der gradingScheme-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme }
                 *     
                 */
                public CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme getGradingScheme() {
                    return gradingScheme;
                }

                /**
                 * Legt den Wert der gradingScheme-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme }
                 *     
                 */
                public void setGradingScheme(CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.GradingScheme value) {
                    this.gradingScheme = value;
                }

                /**
                 * Ruft den Wert der resultDistribution-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution }
                 *     
                 */
                public CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution getResultDistribution() {
                    return resultDistribution;
                }

                /**
                 * Legt den Wert der resultDistribution-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution }
                 *     
                 */
                public void setResultDistribution(CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution value) {
                    this.resultDistribution = value;
                }

                /**
                 * Gets the value of the credit property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the credit property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCredit().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit }
                 * 
                 * 
                 */
                public List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit> getCredit() {
                    if (credit == null) {
                        credit = new ArrayList<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit>();
                    }
                    return this.credit;
                }

                /**
                 * Ruft den Wert der languageOfInstruction-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLanguageOfInstruction() {
                    return languageOfInstruction;
                }

                /**
                 * Legt den Wert der languageOfInstruction-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLanguageOfInstruction(String value) {
                    this.languageOfInstruction = value;
                }

                /**
                 * Ruft den Wert der engagementHours-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getEngagementHours() {
                    return engagementHours;
                }

                /**
                 * Legt den Wert der engagementHours-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setEngagementHours(BigDecimal value) {
                    this.engagementHours = value;
                }


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
                 *         &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="level" minOccurs="0"&gt;
                 *           &lt;simpleType&gt;
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
                 *               &lt;enumeration value="Bachelor"/&gt;
                 *               &lt;enumeration value="Master"/&gt;
                 *               &lt;enumeration value="PhD"/&gt;
                 *             &lt;/restriction&gt;
                 *           &lt;/simpleType&gt;
                 *         &lt;/element&gt;
                 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
                    "scheme",
                    "level",
                    "value"
                })
                public static class Credit
                    implements Serializable
                {

                    @XmlElement(required = true)
                    protected String scheme;
                    protected String level;
                    @XmlElement(required = true)
                    protected BigDecimal value;

                    /**
                     * Ruft den Wert der scheme-Eigenschaft ab.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getScheme() {
                        return scheme;
                    }

                    /**
                     * Legt den Wert der scheme-Eigenschaft fest.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setScheme(String value) {
                        this.scheme = value;
                    }

                    /**
                     * Ruft den Wert der level-Eigenschaft ab.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getLevel() {
                        return level;
                    }

                    /**
                     * Legt den Wert der level-Eigenschaft fest.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setLevel(String value) {
                        this.level = value;
                    }

                    /**
                     * Ruft den Wert der value-Eigenschaft ab.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getValue() {
                        return value;
                    }

                    /**
                     * Legt den Wert der value-Eigenschaft fest.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setValue(BigDecimal value) {
                        this.value = value;
                    }

                }


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
                 *         &lt;element name="label" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
                 *         &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                    "label",
                    "description"
                })
                public static class GradingScheme
                    implements Serializable
                {

                    @XmlElement(required = true)
                    protected List<StringWithOptionalLang> label;
                    protected List<MultilineStringWithOptionalLang> description;

                    /**
                     * Gets the value of the label property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the label property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getLabel().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link StringWithOptionalLang }
                     * 
                     * 
                     */
                    public List<StringWithOptionalLang> getLabel() {
                        if (label == null) {
                            label = new ArrayList<StringWithOptionalLang>();
                        }
                        return this.label;
                    }

                    /**
                     * Gets the value of the description property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the description property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDescription().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link MultilineStringWithOptionalLang }
                     * 
                     * 
                     */
                    public List<MultilineStringWithOptionalLang> getDescription() {
                        if (description == null) {
                            description = new ArrayList<MultilineStringWithOptionalLang>();
                        }
                        return this.description;
                    }

                }


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
                 *         &lt;element name="category" maxOccurs="unbounded"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
                 *                 &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *         &lt;element name="description" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}MultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                    "category",
                    "description"
                })
                public static class ResultDistribution
                    implements Serializable
                {

                    @XmlElement(required = true)
                    protected List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category> category;
                    protected List<MultilineStringWithOptionalLang> description;

                    /**
                     * Gets the value of the category property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the category property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getCategory().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category }
                     * 
                     * 
                     */
                    public List<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category> getCategory() {
                        if (category == null) {
                            category = new ArrayList<CoursesResponse.LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category>();
                        }
                        return this.category;
                    }

                    /**
                     * Gets the value of the description property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the description property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDescription().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link MultilineStringWithOptionalLang }
                     * 
                     * 
                     */
                    public List<MultilineStringWithOptionalLang> getDescription() {
                        if (description == null) {
                            description = new ArrayList<MultilineStringWithOptionalLang>();
                        }
                        return this.description;
                    }


                    /**
                     * <p>Java-Klasse für anonymous complex type.
                     * 
                     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
                     * 
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;complexContent&gt;
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *       &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
                     *       &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
                     *     &lt;/restriction&gt;
                     *   &lt;/complexContent&gt;
                     * &lt;/complexType&gt;
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "")
                    public static class Category
                        implements Serializable
                    {

                        @XmlAttribute(name = "label", required = true)
                        protected String label;
                        @XmlAttribute(name = "count", required = true)
                        @XmlSchemaType(name = "nonNegativeInteger")
                        protected BigInteger count;

                        /**
                         * Ruft den Wert der label-Eigenschaft ab.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getLabel() {
                            return label;
                        }

                        /**
                         * Legt den Wert der label-Eigenschaft fest.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setLabel(String value) {
                            this.label = value;
                        }

                        /**
                         * Ruft den Wert der count-Eigenschaft ab.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigInteger }
                         *     
                         */
                        public BigInteger getCount() {
                            return count;
                        }

                        /**
                         * Legt den Wert der count-Eigenschaft fest.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigInteger }
                         *     
                         */
                        public void setCount(BigInteger value) {
                            this.count = value;
                        }

                    }

                }

            }

        }

    }

}
