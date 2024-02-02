//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 01:55:25 PM CET 
//


package eu.emrex.elmo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * This describes a single "branch" of the "LOS tree": One "parent" node along
 *                 with all its descendants (via the hasPart elements).
 * 
 *                 Each report element contains a set of such trees (a forest). For example, a
 *                 report may contain a set of degree programmes, and each of these programme may
 *                 contain a set of courses. But it is also valid for the report to contain only
 *                 the courses (without the degree programmes), or a mixture of some programmes
 *                 alongside some seperate courses (unbound to any degree programme).
 * 
 *                 In theory the depth of such tree is unlimited, but in practice no more than
 *                 4 levels are usually reached. Each level has an OPTIONAL type, and these types
 *                 (if given) SHOULD follow a logical structure - in order of their depth, these
 *                 would be: "Degree programme", "Module", "Course" and "Class". That is, it is
 *                 valid to include a "Course" with a "Degree programme" parent, but it would be
 *                 invalid to include them the other way around.
 * 
 * 
 *                 Notes for server implementers
 *                 =============================
 * 
 *                 Depending on the scenario, EMREX ELMO file will contain only a subset of the
 *                 student's degree programmes and courses. (E.g. in case of EMREX EMP server,
 *                 students are allowed to select any subset of courses are to be exported, but
 *                 only from among the passed/completed courses. This might however be different
 *                 in other contexts.)
 * 
 * 
 *                 Notes of client implemeneters
 *                 ===================================
 * 
 *                 We cannot guarantee the *all* servers will follow the "max of 4 levels deep"
 *                 structure, nor that all of them will fill in the "type" elements for all the
 *                 nodes.
 * 
 *                 If you're trying to import the data into your system, and the "possibly
 *                 infinite tree" representation seems to be incompatible with your model, then
 *                 you will need to dynamically analyze the types of all the the nodes included in
 *                 the report and decide if the structure can be automatically imported.
 *             
 * 
 * <p>Java-Klasse für LearningOpportunitySpecification complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="LearningOpportunitySpecification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identifier" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
 *         &lt;element name="type" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *               &lt;enumeration value="Degree Programme"/&gt;
 *               &lt;enumeration value="Module"/&gt;
 *               &lt;enumeration value="Course"/&gt;
 *               &lt;enumeration value="Class"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="subjectArea" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *         &lt;element name="iscedCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="descriptionHtml" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}SimpleHtmlStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="specifies"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="learningOpportunityInstance"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="identifier" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;simpleContent&gt;
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
 *                                     &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *                                   &lt;/extension&gt;
 *                                 &lt;/simpleContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                             &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                             &lt;element name="academicTerm" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                                       &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                                       &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="status" minOccurs="0"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *                                   &lt;enumeration value="passed"/&gt;
 *                                   &lt;enumeration value="failed"/&gt;
 *                                   &lt;enumeration value="in-progress"/&gt;
 *                                 &lt;/restriction&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="gradingSchemeLocalId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *                             &lt;element name="resultLabel" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *                             &lt;element name="shortenedGrading" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="percentageLower" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                                       &lt;element name="percentageEqual" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                                       &lt;element name="percentageHigher" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
 *                                               &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *                                               &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
 *                                       &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
 *                                       &lt;element name="level" minOccurs="0"&gt;
 *                                         &lt;simpleType&gt;
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *                                             &lt;enumeration value="Bachelor"/&gt;
 *                                             &lt;enumeration value="Master"/&gt;
 *                                             &lt;enumeration value="PhD"/&gt;
 *                                           &lt;/restriction&gt;
 *                                         &lt;/simpleType&gt;
 *                                       &lt;/element&gt;
 *                                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="level" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
 *                                       &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" minOccurs="0"/&gt;
 *                                       &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="languageOfInstruction" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *                             &lt;element name="engagementHours" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                             &lt;element name="attachments" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="ref" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="grouping" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;simpleContent&gt;
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                                     &lt;attribute name="typeref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="idref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                   &lt;/extension&gt;
 *                                 &lt;/simpleContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
 *                             &lt;element name="diplomaSupplement" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}DiplomaSupplement" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="hasPart" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="learningOpportunitySpecification" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}LearningOpportunitySpecification"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LearningOpportunitySpecification", propOrder = {
    "identifier",
    "title",
    "type",
    "subjectArea",
    "iscedCode",
    "url",
    "description",
    "descriptionHtml",
    "specifies",
    "hasPart",
    "extension"
})
public class LearningOpportunitySpecification
    implements Serializable
{

    protected List<LearningOpportunitySpecification.Identifier> identifier;
    @XmlElement(required = true)
    protected List<TokenWithOptionalLang> title;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String subjectArea;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String iscedCode;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String url;
    protected List<PlaintextMultilineStringWithOptionalLang> description;
    protected List<SimpleHtmlStringWithOptionalLang> descriptionHtml;
    @XmlElement(required = true)
    protected LearningOpportunitySpecification.Specifies specifies;
    protected List<LearningOpportunitySpecification.HasPart> hasPart;
    protected CustomExtensionsContainer extension;

    /**
     * Gets the value of the identifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LearningOpportunitySpecification.Identifier }
     * 
     * 
     */
    public List<LearningOpportunitySpecification.Identifier> getIdentifier() {
        if (identifier == null) {
            identifier = new ArrayList<LearningOpportunitySpecification.Identifier>();
        }
        return this.identifier;
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
     * {@link TokenWithOptionalLang }
     * 
     * 
     */
    public List<TokenWithOptionalLang> getTitle() {
        if (title == null) {
            title = new ArrayList<TokenWithOptionalLang>();
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
     * Ruft den Wert der url-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Legt den Wert der url-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
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
     * {@link PlaintextMultilineStringWithOptionalLang }
     * 
     * 
     */
    public List<PlaintextMultilineStringWithOptionalLang> getDescription() {
        if (description == null) {
            description = new ArrayList<PlaintextMultilineStringWithOptionalLang>();
        }
        return this.description;
    }

    /**
     * Gets the value of the descriptionHtml property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descriptionHtml property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescriptionHtml().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SimpleHtmlStringWithOptionalLang }
     * 
     * 
     */
    public List<SimpleHtmlStringWithOptionalLang> getDescriptionHtml() {
        if (descriptionHtml == null) {
            descriptionHtml = new ArrayList<SimpleHtmlStringWithOptionalLang>();
        }
        return this.descriptionHtml;
    }

    /**
     * Ruft den Wert der specifies-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LearningOpportunitySpecification.Specifies }
     *     
     */
    public LearningOpportunitySpecification.Specifies getSpecifies() {
        return specifies;
    }

    /**
     * Legt den Wert der specifies-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LearningOpportunitySpecification.Specifies }
     *     
     */
    public void setSpecifies(LearningOpportunitySpecification.Specifies value) {
        this.specifies = value;
    }

    /**
     * Gets the value of the hasPart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hasPart property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHasPart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LearningOpportunitySpecification.HasPart }
     * 
     * 
     */
    public List<LearningOpportunitySpecification.HasPart> getHasPart() {
        if (hasPart == null) {
            hasPart = new ArrayList<LearningOpportunitySpecification.HasPart>();
        }
        return this.hasPart;
    }

    /**
     * Ruft den Wert der extension-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CustomExtensionsContainer }
     *     
     */
    public CustomExtensionsContainer getExtension() {
        return extension;
    }

    /**
     * Legt den Wert der extension-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomExtensionsContainer }
     *     
     */
    public void setExtension(CustomExtensionsContainer value) {
        this.extension = value;
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
     *         &lt;element name="learningOpportunitySpecification" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}LearningOpportunitySpecification"/&gt;
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
    public static class HasPart
        implements Serializable
    {

        @XmlElement(required = true)
        protected LearningOpportunitySpecification learningOpportunitySpecification;

        /**
         * Ruft den Wert der learningOpportunitySpecification-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link LearningOpportunitySpecification }
         *     
         */
        public LearningOpportunitySpecification getLearningOpportunitySpecification() {
            return learningOpportunitySpecification;
        }

        /**
         * Legt den Wert der learningOpportunitySpecification-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link LearningOpportunitySpecification }
         *     
         */
        public void setLearningOpportunitySpecification(LearningOpportunitySpecification value) {
            this.learningOpportunitySpecification = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Identifier
        implements Serializable
    {

        @XmlValue
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String value;
        @XmlAttribute(name = "type", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String type;

        /**
         * Ruft den Wert der value-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Legt den Wert der value-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
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
     *         &lt;element name="learningOpportunityInstance"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="identifier" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;simpleContent&gt;
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
     *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *                         &lt;/extension&gt;
     *                       &lt;/simpleContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *                   &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *                   &lt;element name="academicTerm" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
     *                             &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                             &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="status" minOccurs="0"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
     *                         &lt;enumeration value="passed"/&gt;
     *                         &lt;enumeration value="failed"/&gt;
     *                         &lt;enumeration value="in-progress"/&gt;
     *                       &lt;/restriction&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="gradingSchemeLocalId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
     *                   &lt;element name="resultLabel" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
     *                   &lt;element name="shortenedGrading" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="percentageLower" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                             &lt;element name="percentageEqual" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                             &lt;element name="percentageHigher" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
     *                                     &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *                                     &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
     *                             &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
     *                             &lt;element name="level" minOccurs="0"&gt;
     *                               &lt;simpleType&gt;
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
     *                                   &lt;enumeration value="Bachelor"/&gt;
     *                                   &lt;enumeration value="Master"/&gt;
     *                                   &lt;enumeration value="PhD"/&gt;
     *                                 &lt;/restriction&gt;
     *                               &lt;/simpleType&gt;
     *                             &lt;/element&gt;
     *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="level" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
     *                             &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" minOccurs="0"/&gt;
     *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="languageOfInstruction" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
     *                   &lt;element name="engagementHours" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *                   &lt;element name="attachments" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="ref" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="grouping" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;simpleContent&gt;
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                           &lt;attribute name="typeref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                           &lt;attribute name="idref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                         &lt;/extension&gt;
     *                       &lt;/simpleContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
     *                   &lt;element name="diplomaSupplement" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}DiplomaSupplement" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
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
        "learningOpportunityInstance",
        "extension"
    })
    public static class Specifies
        implements Serializable
    {

        @XmlElement(required = true)
        protected LearningOpportunitySpecification.Specifies.LearningOpportunityInstance learningOpportunityInstance;
        protected CustomExtensionsContainer extension;

        /**
         * Ruft den Wert der learningOpportunityInstance-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance }
         *     
         */
        public LearningOpportunitySpecification.Specifies.LearningOpportunityInstance getLearningOpportunityInstance() {
            return learningOpportunityInstance;
        }

        /**
         * Legt den Wert der learningOpportunityInstance-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance }
         *     
         */
        public void setLearningOpportunityInstance(LearningOpportunitySpecification.Specifies.LearningOpportunityInstance value) {
            this.learningOpportunityInstance = value;
        }

        /**
         * Ruft den Wert der extension-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link CustomExtensionsContainer }
         *     
         */
        public CustomExtensionsContainer getExtension() {
            return extension;
        }

        /**
         * Legt den Wert der extension-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link CustomExtensionsContainer }
         *     
         */
        public void setExtension(CustomExtensionsContainer value) {
            this.extension = value;
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
         *         &lt;element name="identifier" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;simpleContent&gt;
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
         *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
         *               &lt;/extension&gt;
         *             &lt;/simpleContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
         *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
         *         &lt;element name="academicTerm" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
         *                   &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *                   &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="status" minOccurs="0"&gt;
         *           &lt;simpleType&gt;
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
         *               &lt;enumeration value="passed"/&gt;
         *               &lt;enumeration value="failed"/&gt;
         *               &lt;enumeration value="in-progress"/&gt;
         *             &lt;/restriction&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="gradingSchemeLocalId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
         *         &lt;element name="resultLabel" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
         *         &lt;element name="shortenedGrading" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="percentageLower" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *                   &lt;element name="percentageEqual" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *                   &lt;element name="percentageHigher" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
         *                           &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
         *                           &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
         *                   &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
         *                   &lt;element name="level" minOccurs="0"&gt;
         *                     &lt;simpleType&gt;
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
         *                         &lt;enumeration value="Bachelor"/&gt;
         *                         &lt;enumeration value="Master"/&gt;
         *                         &lt;enumeration value="PhD"/&gt;
         *                       &lt;/restriction&gt;
         *                     &lt;/simpleType&gt;
         *                   &lt;/element&gt;
         *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="level" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
         *                   &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" minOccurs="0"/&gt;
         *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="languageOfInstruction" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
         *         &lt;element name="engagementHours" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
         *         &lt;element name="attachments" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="ref" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="grouping" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;simpleContent&gt;
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *                 &lt;attribute name="typeref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                 &lt;attribute name="idref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *               &lt;/extension&gt;
         *             &lt;/simpleContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
         *         &lt;element name="diplomaSupplement" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}DiplomaSupplement" minOccurs="0"/&gt;
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
            "identifier",
            "start",
            "date",
            "academicTerm",
            "status",
            "gradingSchemeLocalId",
            "resultLabel",
            "shortenedGrading",
            "resultDistribution",
            "credit",
            "level",
            "languageOfInstruction",
            "engagementHours",
            "attachments",
            "grouping",
            "extension",
            "diplomaSupplement"
        })
        public static class LearningOpportunityInstance
            implements Serializable
        {

            protected List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Identifier> identifier;
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar start;
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;
            protected LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.AcademicTerm academicTerm;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String status;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String gradingSchemeLocalId;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String resultLabel;
            protected LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ShortenedGrading shortenedGrading;
            protected LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution resultDistribution;
            protected List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit> credit;
            protected List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Level> level;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String languageOfInstruction;
            protected BigDecimal engagementHours;
            protected LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Attachments attachments;
            protected LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Grouping grouping;
            protected CustomExtensionsContainer extension;
            protected DiplomaSupplement diplomaSupplement;

            /**
             * Gets the value of the identifier property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the identifier property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getIdentifier().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Identifier }
             * 
             * 
             */
            public List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Identifier> getIdentifier() {
                if (identifier == null) {
                    identifier = new ArrayList<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Identifier>();
                }
                return this.identifier;
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
             * Ruft den Wert der date-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDate() {
                return date;
            }

            /**
             * Legt den Wert der date-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDate(XMLGregorianCalendar value) {
                this.date = value;
            }

            /**
             * Ruft den Wert der academicTerm-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.AcademicTerm }
             *     
             */
            public LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.AcademicTerm getAcademicTerm() {
                return academicTerm;
            }

            /**
             * Legt den Wert der academicTerm-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.AcademicTerm }
             *     
             */
            public void setAcademicTerm(LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.AcademicTerm value) {
                this.academicTerm = value;
            }

            /**
             * Ruft den Wert der status-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatus() {
                return status;
            }

            /**
             * Legt den Wert der status-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatus(String value) {
                this.status = value;
            }

            /**
             * Ruft den Wert der gradingSchemeLocalId-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGradingSchemeLocalId() {
                return gradingSchemeLocalId;
            }

            /**
             * Legt den Wert der gradingSchemeLocalId-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGradingSchemeLocalId(String value) {
                this.gradingSchemeLocalId = value;
            }

            /**
             * Ruft den Wert der resultLabel-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResultLabel() {
                return resultLabel;
            }

            /**
             * Legt den Wert der resultLabel-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResultLabel(String value) {
                this.resultLabel = value;
            }

            /**
             * Ruft den Wert der shortenedGrading-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ShortenedGrading }
             *     
             */
            public LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ShortenedGrading getShortenedGrading() {
                return shortenedGrading;
            }

            /**
             * Legt den Wert der shortenedGrading-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ShortenedGrading }
             *     
             */
            public void setShortenedGrading(LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ShortenedGrading value) {
                this.shortenedGrading = value;
            }

            /**
             * Ruft den Wert der resultDistribution-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution }
             *     
             */
            public LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution getResultDistribution() {
                return resultDistribution;
            }

            /**
             * Legt den Wert der resultDistribution-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution }
             *     
             */
            public void setResultDistribution(LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution value) {
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
             * {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit }
             * 
             * 
             */
            public List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit> getCredit() {
                if (credit == null) {
                    credit = new ArrayList<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Credit>();
                }
                return this.credit;
            }

            /**
             * Gets the value of the level property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the level property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLevel().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Level }
             * 
             * 
             */
            public List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Level> getLevel() {
                if (level == null) {
                    level = new ArrayList<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Level>();
                }
                return this.level;
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
             * Ruft den Wert der attachments-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Attachments }
             *     
             */
            public LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Attachments getAttachments() {
                return attachments;
            }

            /**
             * Legt den Wert der attachments-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Attachments }
             *     
             */
            public void setAttachments(LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Attachments value) {
                this.attachments = value;
            }

            /**
             * Ruft den Wert der grouping-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Grouping }
             *     
             */
            public LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Grouping getGrouping() {
                return grouping;
            }

            /**
             * Legt den Wert der grouping-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Grouping }
             *     
             */
            public void setGrouping(LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.Grouping value) {
                this.grouping = value;
            }

            /**
             * Ruft den Wert der extension-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link CustomExtensionsContainer }
             *     
             */
            public CustomExtensionsContainer getExtension() {
                return extension;
            }

            /**
             * Legt den Wert der extension-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link CustomExtensionsContainer }
             *     
             */
            public void setExtension(CustomExtensionsContainer value) {
                this.extension = value;
            }

            /**
             * Ruft den Wert der diplomaSupplement-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link DiplomaSupplement }
             *     
             */
            public DiplomaSupplement getDiplomaSupplement() {
                return diplomaSupplement;
            }

            /**
             * Legt den Wert der diplomaSupplement-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link DiplomaSupplement }
             *     
             */
            public void setDiplomaSupplement(DiplomaSupplement value) {
                this.diplomaSupplement = value;
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
             *         &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
             *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
             *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
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
                "title",
                "start",
                "end"
            })
            public static class AcademicTerm
                implements Serializable
            {

                @XmlElement(required = true)
                protected List<TokenWithOptionalLang> title;
                @XmlElement(required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar start;
                @XmlElement(required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar end;

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
                 * {@link TokenWithOptionalLang }
                 * 
                 * 
                 */
                public List<TokenWithOptionalLang> getTitle() {
                    if (title == null) {
                        title = new ArrayList<TokenWithOptionalLang>();
                    }
                    return this.title;
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
             *         &lt;element name="ref" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "ref"
            })
            public static class Attachments
                implements Serializable
            {

                protected List<String> ref;

                /**
                 * Gets the value of the ref property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the ref property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getRef().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getRef() {
                    if (ref == null) {
                        ref = new ArrayList<String>();
                    }
                    return this.ref;
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
             *         &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
             *         &lt;element name="level" minOccurs="0"&gt;
             *           &lt;simpleType&gt;
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
             *               &lt;enumeration value="Bachelor"/&gt;
             *               &lt;enumeration value="Master"/&gt;
             *               &lt;enumeration value="PhD"/&gt;
             *             &lt;/restriction&gt;
             *           &lt;/simpleType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "token")
                protected String scheme;
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String level;
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
             *   &lt;simpleContent&gt;
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
             *       &lt;attribute name="typeref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *       &lt;attribute name="idref" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *     &lt;/extension&gt;
             *   &lt;/simpleContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Grouping
                implements Serializable
            {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "typeref")
                protected String typeref;
                @XmlAttribute(name = "idref")
                protected String idref;

                /**
                 * Ruft den Wert der value-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Legt den Wert der value-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Ruft den Wert der typeref-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTyperef() {
                    return typeref;
                }

                /**
                 * Legt den Wert der typeref-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTyperef(String value) {
                    this.typeref = value;
                }

                /**
                 * Ruft den Wert der idref-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getIdref() {
                    return idref;
                }

                /**
                 * Legt den Wert der idref-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setIdref(String value) {
                    this.idref = value;
                }

            }


            /**
             * <p>Java-Klasse für anonymous complex type.
             * 
             * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;simpleContent&gt;
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
             *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
             *     &lt;/extension&gt;
             *   &lt;/simpleContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Identifier
                implements Serializable
            {

                @XmlValue
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "token")
                protected String value;
                @XmlAttribute(name = "type", required = true)
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "token")
                protected String type;

                /**
                 * Ruft den Wert der value-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Legt den Wert der value-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
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
             *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
             *         &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" minOccurs="0"/&gt;
             *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
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
                "type",
                "description",
                "value"
            })
            public static class Level
                implements Serializable
            {

                @XmlElement(required = true)
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "token")
                protected String type;
                protected PlaintextMultilineStringWithOptionalLang description;
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                @XmlSchemaType(name = "token")
                protected String value;

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
                 * Ruft den Wert der description-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link PlaintextMultilineStringWithOptionalLang }
                 *     
                 */
                public PlaintextMultilineStringWithOptionalLang getDescription() {
                    return description;
                }

                /**
                 * Legt den Wert der description-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link PlaintextMultilineStringWithOptionalLang }
                 *     
                 */
                public void setDescription(PlaintextMultilineStringWithOptionalLang value) {
                    this.description = value;
                }

                /**
                 * Ruft den Wert der value-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Legt den Wert der value-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
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
             *         &lt;element name="category" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
             *                 &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *         &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                protected List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category> category;
                protected List<PlaintextMultilineStringWithOptionalLang> description;

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
                 * {@link LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category }
                 * 
                 * 
                 */
                public List<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category> getCategory() {
                    if (category == null) {
                        category = new ArrayList<LearningOpportunitySpecification.Specifies.LearningOpportunityInstance.ResultDistribution.Category>();
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
                 * {@link PlaintextMultilineStringWithOptionalLang }
                 * 
                 * 
                 */
                public List<PlaintextMultilineStringWithOptionalLang> getDescription() {
                    if (description == null) {
                        description = new ArrayList<PlaintextMultilineStringWithOptionalLang>();
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
                 *       &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
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
                    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                    @XmlSchemaType(name = "token")
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
             *         &lt;element name="percentageLower" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
             *         &lt;element name="percentageEqual" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
             *         &lt;element name="percentageHigher" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
                "percentageLower",
                "percentageEqual",
                "percentageHigher"
            })
            public static class ShortenedGrading
                implements Serializable
            {

                @XmlElement(required = true)
                protected BigDecimal percentageLower;
                @XmlElement(required = true)
                protected BigDecimal percentageEqual;
                @XmlElement(required = true)
                protected BigDecimal percentageHigher;

                /**
                 * Ruft den Wert der percentageLower-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getPercentageLower() {
                    return percentageLower;
                }

                /**
                 * Legt den Wert der percentageLower-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setPercentageLower(BigDecimal value) {
                    this.percentageLower = value;
                }

                /**
                 * Ruft den Wert der percentageEqual-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getPercentageEqual() {
                    return percentageEqual;
                }

                /**
                 * Legt den Wert der percentageEqual-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setPercentageEqual(BigDecimal value) {
                    this.percentageEqual = value;
                }

                /**
                 * Ruft den Wert der percentageHigher-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getPercentageHigher() {
                    return percentageHigher;
                }

                /**
                 * Legt den Wert der percentageHigher-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setPercentageHigher(BigDecimal value) {
                    this.percentageHigher = value;
                }

            }

        }

    }

}
