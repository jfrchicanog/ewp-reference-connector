//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.07.10 um 12:00:19 PM CEST 
//


package eu.erasmuswithoutpaper.api.omobilities.las.endpoints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für LearningAgreement complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="LearningAgreement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="omobility-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
 *         &lt;element name="sending-hei" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}MobilityInstitution"/&gt;
 *         &lt;element name="receiving-hei" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}MobilityInstitution"/&gt;
 *         &lt;element name="receiving-academic-year-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v2}AcademicYearId"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}student"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="start-date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="start-year-month" type="{http://www.w3.org/2001/XMLSchema}gYearMonth"/&gt;
 *         &lt;/choice&gt;
 *         &lt;choice&gt;
 *           &lt;element name="end-date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *           &lt;element name="end-year-month" type="{http://www.w3.org/2001/XMLSchema}gYearMonth"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="eqf-level-studied-at-departure" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}EqfLevel"/&gt;
 *         &lt;element name="isced-f-code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isced-clarification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="student-language-skill"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}language"/&gt;
 *                   &lt;element name="cefr-level" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}CefrLevel"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="first-version" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ListOf_Components" minOccurs="0"/&gt;
 *         &lt;element name="approved-changes" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ListOf_Components" minOccurs="0"/&gt;
 *         &lt;element name="changes-proposal" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ListOf_Components"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}student" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="learning-outcomes-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTP" minOccurs="0"/&gt;
 *         &lt;element name="provisions-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTP" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LearningAgreement", propOrder = {
    "omobilityId",
    "sendingHei",
    "receivingHei",
    "receivingAcademicYearId",
    "student",
    "startDate",
    "startYearMonth",
    "endDate",
    "endYearMonth",
    "eqfLevelStudiedAtDeparture",
    "iscedFCode",
    "iscedClarification",
    "studentLanguageSkill",
    "firstVersion",
    "approvedChanges",
    "changesProposal",
    "learningOutcomesUrl",
    "provisionsUrl"
})
public class LearningAgreement
    implements Serializable
{

    @XmlElement(name = "omobility-id", required = true)
    protected String omobilityId;
    @XmlElement(name = "sending-hei", required = true)
    protected MobilityInstitution sendingHei;
    @XmlElement(name = "receiving-hei", required = true)
    protected MobilityInstitution receivingHei;
    @XmlElement(name = "receiving-academic-year-id", required = true)
    protected String receivingAcademicYearId;
    @XmlElement(required = true)
    protected Student student;
    @XmlElement(name = "start-date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "start-year-month")
    @XmlSchemaType(name = "gYearMonth")
    protected XMLGregorianCalendar startYearMonth;
    @XmlElement(name = "end-date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "end-year-month")
    @XmlSchemaType(name = "gYearMonth")
    protected XMLGregorianCalendar endYearMonth;
    @XmlElement(name = "eqf-level-studied-at-departure")
    protected byte eqfLevelStudiedAtDeparture;
    @XmlElement(name = "isced-f-code", required = true)
    protected String iscedFCode;
    @XmlElement(name = "isced-clarification")
    protected String iscedClarification;
    @XmlElement(name = "student-language-skill", required = true)
    protected LearningAgreement.StudentLanguageSkill studentLanguageSkill;
    @XmlElement(name = "first-version")
    protected ListOfComponents firstVersion;
    @XmlElement(name = "approved-changes")
    protected ListOfComponents approvedChanges;
    @XmlElement(name = "changes-proposal")
    protected LearningAgreement.ChangesProposal changesProposal;
    @XmlElement(name = "learning-outcomes-url")
    @XmlSchemaType(name = "anyURI")
    protected String learningOutcomesUrl;
    @XmlElement(name = "provisions-url")
    @XmlSchemaType(name = "anyURI")
    protected String provisionsUrl;

    /**
     * Ruft den Wert der omobilityId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmobilityId() {
        return omobilityId;
    }

    /**
     * Legt den Wert der omobilityId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmobilityId(String value) {
        this.omobilityId = value;
    }

    /**
     * Ruft den Wert der sendingHei-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MobilityInstitution }
     *     
     */
    public MobilityInstitution getSendingHei() {
        return sendingHei;
    }

    /**
     * Legt den Wert der sendingHei-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MobilityInstitution }
     *     
     */
    public void setSendingHei(MobilityInstitution value) {
        this.sendingHei = value;
    }

    /**
     * Ruft den Wert der receivingHei-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MobilityInstitution }
     *     
     */
    public MobilityInstitution getReceivingHei() {
        return receivingHei;
    }

    /**
     * Legt den Wert der receivingHei-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MobilityInstitution }
     *     
     */
    public void setReceivingHei(MobilityInstitution value) {
        this.receivingHei = value;
    }

    /**
     * Ruft den Wert der receivingAcademicYearId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivingAcademicYearId() {
        return receivingAcademicYearId;
    }

    /**
     * Legt den Wert der receivingAcademicYearId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivingAcademicYearId(String value) {
        this.receivingAcademicYearId = value;
    }

    /**
     * Ruft den Wert der student-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Student }
     *     
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Legt den Wert der student-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Student }
     *     
     */
    public void setStudent(Student value) {
        this.student = value;
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
     * Ruft den Wert der startYearMonth-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartYearMonth() {
        return startYearMonth;
    }

    /**
     * Legt den Wert der startYearMonth-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartYearMonth(XMLGregorianCalendar value) {
        this.startYearMonth = value;
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

    /**
     * Ruft den Wert der endYearMonth-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndYearMonth() {
        return endYearMonth;
    }

    /**
     * Legt den Wert der endYearMonth-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndYearMonth(XMLGregorianCalendar value) {
        this.endYearMonth = value;
    }

    /**
     * Ruft den Wert der eqfLevelStudiedAtDeparture-Eigenschaft ab.
     * 
     */
    public byte getEqfLevelStudiedAtDeparture() {
        return eqfLevelStudiedAtDeparture;
    }

    /**
     * Legt den Wert der eqfLevelStudiedAtDeparture-Eigenschaft fest.
     * 
     */
    public void setEqfLevelStudiedAtDeparture(byte value) {
        this.eqfLevelStudiedAtDeparture = value;
    }

    /**
     * Ruft den Wert der iscedFCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIscedFCode() {
        return iscedFCode;
    }

    /**
     * Legt den Wert der iscedFCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIscedFCode(String value) {
        this.iscedFCode = value;
    }

    /**
     * Ruft den Wert der iscedClarification-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIscedClarification() {
        return iscedClarification;
    }

    /**
     * Legt den Wert der iscedClarification-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIscedClarification(String value) {
        this.iscedClarification = value;
    }

    /**
     * Ruft den Wert der studentLanguageSkill-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LearningAgreement.StudentLanguageSkill }
     *     
     */
    public LearningAgreement.StudentLanguageSkill getStudentLanguageSkill() {
        return studentLanguageSkill;
    }

    /**
     * Legt den Wert der studentLanguageSkill-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LearningAgreement.StudentLanguageSkill }
     *     
     */
    public void setStudentLanguageSkill(LearningAgreement.StudentLanguageSkill value) {
        this.studentLanguageSkill = value;
    }

    /**
     * Ruft den Wert der firstVersion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ListOfComponents }
     *     
     */
    public ListOfComponents getFirstVersion() {
        return firstVersion;
    }

    /**
     * Legt den Wert der firstVersion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfComponents }
     *     
     */
    public void setFirstVersion(ListOfComponents value) {
        this.firstVersion = value;
    }

    /**
     * Ruft den Wert der approvedChanges-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ListOfComponents }
     *     
     */
    public ListOfComponents getApprovedChanges() {
        return approvedChanges;
    }

    /**
     * Legt den Wert der approvedChanges-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfComponents }
     *     
     */
    public void setApprovedChanges(ListOfComponents value) {
        this.approvedChanges = value;
    }

    /**
     * Ruft den Wert der changesProposal-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LearningAgreement.ChangesProposal }
     *     
     */
    public LearningAgreement.ChangesProposal getChangesProposal() {
        return changesProposal;
    }

    /**
     * Legt den Wert der changesProposal-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LearningAgreement.ChangesProposal }
     *     
     */
    public void setChangesProposal(LearningAgreement.ChangesProposal value) {
        this.changesProposal = value;
    }

    /**
     * Ruft den Wert der learningOutcomesUrl-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLearningOutcomesUrl() {
        return learningOutcomesUrl;
    }

    /**
     * Legt den Wert der learningOutcomesUrl-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLearningOutcomesUrl(String value) {
        this.learningOutcomesUrl = value;
    }

    /**
     * Ruft den Wert der provisionsUrl-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvisionsUrl() {
        return provisionsUrl;
    }

    /**
     * Legt den Wert der provisionsUrl-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvisionsUrl(String value) {
        this.provisionsUrl = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}ListOf_Components"&gt;
     *       &lt;sequence&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-omobility-las/blob/stable-v1/endpoints/get-response.xsd}student" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "student"
    })
    public static class ChangesProposal
        extends ListOfComponents
        implements Serializable
    {

        protected Student student;
        @XmlAttribute(name = "id", required = true)
        protected String id;

        /**
         * 
         *                                             This element represents the student's personal data that has changed after
         *                                             the last receiving HEI's approval.
         * 
         *                                             Note: Only those fields that have changed should be present here.
         *                                             Changes to the following fields are considered as important and should be
         *                                             mentioned here: `given-names`, `family-name`, `global-id`, `birth-date`.
         *                                             Other fields may change without the need to issue a proposal.
         *                                         
         * 
         * @return
         *     possible object is
         *     {@link Student }
         *     
         */
        public Student getStudent() {
            return student;
        }

        /**
         * Legt den Wert der student-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Student }
         *     
         */
        public void setStudent(Student value) {
            this.student = value;
        }

        /**
         * Ruft den Wert der id-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Legt den Wert der id-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
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
     *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}language"/&gt;
     *         &lt;element name="cefr-level" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}CefrLevel"/&gt;
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
        "language",
        "cefrLevel"
    })
    public static class StudentLanguageSkill
        implements Serializable
    {

        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String language;
        @XmlElement(name = "cefr-level", required = true)
        protected String cefrLevel;

        /**
         * Ruft den Wert der language-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLanguage() {
            return language;
        }

        /**
         * Legt den Wert der language-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLanguage(String value) {
            this.language = value;
        }

        /**
         * Ruft den Wert der cefrLevel-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCefrLevel() {
            return cefrLevel;
        }

        /**
         * Legt den Wert der cefrLevel-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCefrLevel(String value) {
            this.cefrLevel = value;
        }

    }

}
