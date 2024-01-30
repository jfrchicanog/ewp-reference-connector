//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 09:10:20 AM CET 
//


package eu.erasmuswithoutpaper.api.omobilities.endpoints;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.types.address.FlexibleAddress;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;


/**
 * 
 *                     This describes a single Student Mobility.
 * 
 *                     Immutable elements
 *                     ------------------
 * 
 *                     Every mobility has its unique sending-hei, receiving-hei and nominee, which
 *                     MUST NOT change after the mobility is created. For example, if it turns out
 *                     that a different student will be nominated for this mobility, then a new
 *                     omobility-id MUST be created for such nomination. However, the properties of
 *                     these mobility-related entities still MAY change. E.g. the student's name may
 *                     get updated.
 *                 
 * 
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="omobility-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier"/&gt;
 *         &lt;element name="sending-hei"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                   &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="receiving-hei"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                   &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;choice&gt;
 *           &lt;element name="sending-academic-term-ewp-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1}EwpAcademicTermId"/&gt;
 *           &lt;element name="non-standard-mobility-period" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Empty"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="receiving-academic-year-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1}AcademicYearId"/&gt;
 *         &lt;element name="student"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="given-names" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="family-name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="global-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="birth-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                   &lt;element name="citizenship" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}CountryCode" minOccurs="0"/&gt;
 *                   &lt;element name="gender" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Gender" minOccurs="0"/&gt;
 *                   &lt;element name="email" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Email" minOccurs="0"/&gt;
 *                   &lt;element name="photo-url" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd&gt;HTTPS"&gt;
 *                           &lt;attribute name="size-px"&gt;
 *                             &lt;simpleType&gt;
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                 &lt;pattern value="[0-9]+x[0-9]+"/&gt;
 *                               &lt;/restriction&gt;
 *                             &lt;/simpleType&gt;
 *                           &lt;/attribute&gt;
 *                           &lt;attribute name="public" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *                           &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}street-address" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}mailing-address" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1}phone-number" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="status" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobilities/blob/stable-v2/endpoints/get-response.xsd}MobilityStatus"/&gt;
 *         &lt;element name="activity-type" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobilities/blob/stable-v2/endpoints/get-response.xsd}MobilityActivityType"/&gt;
 *         &lt;element name="activity-attributes" type="{https://github.com/erasmus-without-paper/ewp-specs-api-omobilities/blob/stable-v2/endpoints/get-response.xsd}MobilityActivityAttributes"/&gt;
 *         &lt;element name="planned-arrival-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="planned-departure-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="eqf-level-studied-at-nomination" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}EqfLevel" minOccurs="0"/&gt;
 *         &lt;element name="eqf-level-studied-at-departure" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}EqfLevel" minOccurs="0"/&gt;
 *         &lt;element name="nominee-isced-f-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nominee-language-skill" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}language"/&gt;
 *                   &lt;element name="cefr-level" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}CefrLevel" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="additional-requirement" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;choice&gt;
 *                     &lt;element name="file" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                     &lt;element name="url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTP"/&gt;
 *                   &lt;/choice&gt;
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
    "omobilityId",
    "sendingHei",
    "receivingHei",
    "sendingAcademicTermEwpId",
    "nonStandardMobilityPeriod",
    "receivingAcademicYearId",
    "student",
    "status",
    "activityType",
    "activityAttributes",
    "plannedArrivalDate",
    "plannedDepartureDate",
    "eqfLevelStudiedAtNomination",
    "eqfLevelStudiedAtDeparture",
    "nomineeIscedFCode",
    "nomineeLanguageSkill",
    "additionalRequirement"
})
@XmlRootElement(name = "student-mobility")
public class StudentMobility
    implements Serializable
{

    @XmlElement(name = "omobility-id", required = true)
    protected String omobilityId;
    @XmlElement(name = "sending-hei", required = true)
    protected StudentMobility.SendingHei sendingHei;
    @XmlElement(name = "receiving-hei", required = true)
    protected StudentMobility.ReceivingHei receivingHei;
    @XmlElement(name = "sending-academic-term-ewp-id")
    protected String sendingAcademicTermEwpId;
    @XmlElement(name = "non-standard-mobility-period")
    protected Empty nonStandardMobilityPeriod;
    @XmlElement(name = "receiving-academic-year-id", required = true)
    protected String receivingAcademicYearId;
    @XmlElement(required = true)
    protected StudentMobility.Student student;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected MobilityStatus status;
    @XmlElement(name = "activity-type", required = true)
    @XmlSchemaType(name = "string")
    protected MobilityActivityType activityType;
    @XmlElement(name = "activity-attributes", required = true)
    @XmlSchemaType(name = "string")
    protected MobilityActivityAttributes activityAttributes;
    @XmlElement(name = "planned-arrival-date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar plannedArrivalDate;
    @XmlElement(name = "planned-departure-date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar plannedDepartureDate;
    @XmlElement(name = "eqf-level-studied-at-nomination")
    protected Byte eqfLevelStudiedAtNomination;
    @XmlElement(name = "eqf-level-studied-at-departure")
    protected Byte eqfLevelStudiedAtDeparture;
    @XmlElement(name = "nominee-isced-f-code")
    protected String nomineeIscedFCode;
    @XmlElement(name = "nominee-language-skill")
    protected List<StudentMobility.NomineeLanguageSkill> nomineeLanguageSkill;
    @XmlElement(name = "additional-requirement")
    protected List<StudentMobility.AdditionalRequirement> additionalRequirement;

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
     *     {@link StudentMobility.SendingHei }
     *     
     */
    public StudentMobility.SendingHei getSendingHei() {
        return sendingHei;
    }

    /**
     * Legt den Wert der sendingHei-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentMobility.SendingHei }
     *     
     */
    public void setSendingHei(StudentMobility.SendingHei value) {
        this.sendingHei = value;
    }

    /**
     * Ruft den Wert der receivingHei-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link StudentMobility.ReceivingHei }
     *     
     */
    public StudentMobility.ReceivingHei getReceivingHei() {
        return receivingHei;
    }

    /**
     * Legt den Wert der receivingHei-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentMobility.ReceivingHei }
     *     
     */
    public void setReceivingHei(StudentMobility.ReceivingHei value) {
        this.receivingHei = value;
    }

    /**
     * Ruft den Wert der sendingAcademicTermEwpId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingAcademicTermEwpId() {
        return sendingAcademicTermEwpId;
    }

    /**
     * Legt den Wert der sendingAcademicTermEwpId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendingAcademicTermEwpId(String value) {
        this.sendingAcademicTermEwpId = value;
    }

    /**
     * Ruft den Wert der nonStandardMobilityPeriod-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Empty }
     *     
     */
    public Empty getNonStandardMobilityPeriod() {
        return nonStandardMobilityPeriod;
    }

    /**
     * Legt den Wert der nonStandardMobilityPeriod-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Empty }
     *     
     */
    public void setNonStandardMobilityPeriod(Empty value) {
        this.nonStandardMobilityPeriod = value;
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
     *     {@link StudentMobility.Student }
     *     
     */
    public StudentMobility.Student getStudent() {
        return student;
    }

    /**
     * Legt den Wert der student-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentMobility.Student }
     *     
     */
    public void setStudent(StudentMobility.Student value) {
        this.student = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MobilityStatus }
     *     
     */
    public MobilityStatus getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MobilityStatus }
     *     
     */
    public void setStatus(MobilityStatus value) {
        this.status = value;
    }

    /**
     * Ruft den Wert der activityType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MobilityActivityType }
     *     
     */
    public MobilityActivityType getActivityType() {
        return activityType;
    }

    /**
     * Legt den Wert der activityType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MobilityActivityType }
     *     
     */
    public void setActivityType(MobilityActivityType value) {
        this.activityType = value;
    }

    /**
     * Ruft den Wert der activityAttributes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MobilityActivityAttributes }
     *     
     */
    public MobilityActivityAttributes getActivityAttributes() {
        return activityAttributes;
    }

    /**
     * Legt den Wert der activityAttributes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MobilityActivityAttributes }
     *     
     */
    public void setActivityAttributes(MobilityActivityAttributes value) {
        this.activityAttributes = value;
    }

    /**
     * Ruft den Wert der plannedArrivalDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlannedArrivalDate() {
        return plannedArrivalDate;
    }

    /**
     * Legt den Wert der plannedArrivalDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlannedArrivalDate(XMLGregorianCalendar value) {
        this.plannedArrivalDate = value;
    }

    /**
     * Ruft den Wert der plannedDepartureDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlannedDepartureDate() {
        return plannedDepartureDate;
    }

    /**
     * Legt den Wert der plannedDepartureDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlannedDepartureDate(XMLGregorianCalendar value) {
        this.plannedDepartureDate = value;
    }

    /**
     * Ruft den Wert der eqfLevelStudiedAtNomination-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getEqfLevelStudiedAtNomination() {
        return eqfLevelStudiedAtNomination;
    }

    /**
     * Legt den Wert der eqfLevelStudiedAtNomination-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setEqfLevelStudiedAtNomination(Byte value) {
        this.eqfLevelStudiedAtNomination = value;
    }

    /**
     * Ruft den Wert der eqfLevelStudiedAtDeparture-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getEqfLevelStudiedAtDeparture() {
        return eqfLevelStudiedAtDeparture;
    }

    /**
     * Legt den Wert der eqfLevelStudiedAtDeparture-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setEqfLevelStudiedAtDeparture(Byte value) {
        this.eqfLevelStudiedAtDeparture = value;
    }

    /**
     * Ruft den Wert der nomineeIscedFCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomineeIscedFCode() {
        return nomineeIscedFCode;
    }

    /**
     * Legt den Wert der nomineeIscedFCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomineeIscedFCode(String value) {
        this.nomineeIscedFCode = value;
    }

    /**
     * Gets the value of the nomineeLanguageSkill property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nomineeLanguageSkill property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNomineeLanguageSkill().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudentMobility.NomineeLanguageSkill }
     * 
     * 
     */
    public List<StudentMobility.NomineeLanguageSkill> getNomineeLanguageSkill() {
        if (nomineeLanguageSkill == null) {
            nomineeLanguageSkill = new ArrayList<StudentMobility.NomineeLanguageSkill>();
        }
        return this.nomineeLanguageSkill;
    }

    /**
     * Gets the value of the additionalRequirement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalRequirement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalRequirement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudentMobility.AdditionalRequirement }
     * 
     * 
     */
    public List<StudentMobility.AdditionalRequirement> getAdditionalRequirement() {
        if (additionalRequirement == null) {
            additionalRequirement = new ArrayList<StudentMobility.AdditionalRequirement>();
        }
        return this.additionalRequirement;
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
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;choice&gt;
     *           &lt;element name="file" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *           &lt;element name="url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTP"/&gt;
     *         &lt;/choice&gt;
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
        "name",
        "file",
        "url"
    })
    public static class AdditionalRequirement
        implements Serializable
    {

        @XmlElement(required = true)
        protected String name;
        protected String file;
        @XmlSchemaType(name = "anyURI")
        protected String url;

        /**
         * Ruft den Wert der name-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Legt den Wert der name-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Ruft den Wert der file-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFile() {
            return file;
        }

        /**
         * Legt den Wert der file-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFile(String value) {
            this.file = value;
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
     *         &lt;element name="cefr-level" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}CefrLevel" minOccurs="0"/&gt;
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
    public static class NomineeLanguageSkill
        implements Serializable
    {

        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String language;
        @XmlElement(name = "cefr-level")
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
     *         &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
     *         &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
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
        "ounitId",
        "iiaId"
    })
    public static class ReceivingHei
        implements Serializable
    {

        @XmlElement(name = "hei-id", required = true)
        protected String heiId;
        @XmlElement(name = "ounit-id")
        protected String ounitId;
        @XmlElement(name = "iia-id")
        protected String iiaId;

        /**
         * Ruft den Wert der heiId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHeiId() {
            return heiId;
        }

        /**
         * Legt den Wert der heiId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHeiId(String value) {
            this.heiId = value;
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
         * Ruft den Wert der iiaId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIiaId() {
            return iiaId;
        }

        /**
         * Legt den Wert der iiaId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIiaId(String value) {
            this.iiaId = value;
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
     *         &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
     *         &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
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
        "ounitId",
        "iiaId"
    })
    public static class SendingHei
        implements Serializable
    {

        @XmlElement(name = "hei-id", required = true)
        protected String heiId;
        @XmlElement(name = "ounit-id")
        protected String ounitId;
        @XmlElement(name = "iia-id")
        protected String iiaId;

        /**
         * Ruft den Wert der heiId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHeiId() {
            return heiId;
        }

        /**
         * Legt den Wert der heiId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHeiId(String value) {
            this.heiId = value;
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
         * Ruft den Wert der iiaId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIiaId() {
            return iiaId;
        }

        /**
         * Legt den Wert der iiaId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIiaId(String value) {
            this.iiaId = value;
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
     *         &lt;element name="given-names" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
     *         &lt;element name="family-name" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}StringWithOptionalLang" maxOccurs="unbounded"/&gt;
     *         &lt;element name="global-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="birth-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *         &lt;element name="citizenship" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}CountryCode" minOccurs="0"/&gt;
     *         &lt;element name="gender" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Gender" minOccurs="0"/&gt;
     *         &lt;element name="email" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Email" minOccurs="0"/&gt;
     *         &lt;element name="photo-url" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd&gt;HTTPS"&gt;
     *                 &lt;attribute name="size-px"&gt;
     *                   &lt;simpleType&gt;
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                       &lt;pattern value="[0-9]+x[0-9]+"/&gt;
     *                     &lt;/restriction&gt;
     *                   &lt;/simpleType&gt;
     *                 &lt;/attribute&gt;
     *                 &lt;attribute name="public" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
     *                 &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}street-address" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}mailing-address" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1}phone-number" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "givenNames",
        "familyName",
        "globalId",
        "birthDate",
        "citizenship",
        "gender",
        "email",
        "photoUrl",
        "streetAddress",
        "mailingAddress",
        "phoneNumber"
    })
    public static class Student
        implements Serializable
    {

        @XmlElement(name = "given-names", required = true)
        protected List<StringWithOptionalLang> givenNames;
        @XmlElement(name = "family-name", required = true)
        protected List<StringWithOptionalLang> familyName;
        @XmlElement(name = "global-id", required = true)
        protected String globalId;
        @XmlElement(name = "birth-date")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar birthDate;
        protected String citizenship;
        protected BigInteger gender;
        protected String email;
        @XmlElement(name = "photo-url")
        protected List<StudentMobility.Student.PhotoUrl> photoUrl;
        @XmlElement(name = "street-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
        protected FlexibleAddress streetAddress;
        @XmlElement(name = "mailing-address", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1")
        protected FlexibleAddress mailingAddress;
        @XmlElement(name = "phone-number", namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1")
        protected List<PhoneNumber> phoneNumber;

        /**
         * Gets the value of the givenNames property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the givenNames property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGivenNames().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StringWithOptionalLang }
         * 
         * 
         */
        public List<StringWithOptionalLang> getGivenNames() {
            if (givenNames == null) {
                givenNames = new ArrayList<StringWithOptionalLang>();
            }
            return this.givenNames;
        }

        /**
         * Gets the value of the familyName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the familyName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFamilyName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StringWithOptionalLang }
         * 
         * 
         */
        public List<StringWithOptionalLang> getFamilyName() {
            if (familyName == null) {
                familyName = new ArrayList<StringWithOptionalLang>();
            }
            return this.familyName;
        }

        /**
         * Ruft den Wert der globalId-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGlobalId() {
            return globalId;
        }

        /**
         * Legt den Wert der globalId-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGlobalId(String value) {
            this.globalId = value;
        }

        /**
         * Ruft den Wert der birthDate-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getBirthDate() {
            return birthDate;
        }

        /**
         * Legt den Wert der birthDate-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setBirthDate(XMLGregorianCalendar value) {
            this.birthDate = value;
        }

        /**
         * Ruft den Wert der citizenship-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCitizenship() {
            return citizenship;
        }

        /**
         * Legt den Wert der citizenship-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCitizenship(String value) {
            this.citizenship = value;
        }

        /**
         * Ruft den Wert der gender-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getGender() {
            return gender;
        }

        /**
         * Legt den Wert der gender-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setGender(BigInteger value) {
            this.gender = value;
        }

        /**
         * Ruft den Wert der email-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEmail() {
            return email;
        }

        /**
         * Legt den Wert der email-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEmail(String value) {
            this.email = value;
        }

        /**
         * Gets the value of the photoUrl property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the photoUrl property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhotoUrl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StudentMobility.Student.PhotoUrl }
         * 
         * 
         */
        public List<StudentMobility.Student.PhotoUrl> getPhotoUrl() {
            if (photoUrl == null) {
                photoUrl = new ArrayList<StudentMobility.Student.PhotoUrl>();
            }
            return this.photoUrl;
        }

        /**
         * 
         *                                         Optional street address of the person's home.
         *                                     
         * 
         * @return
         *     possible object is
         *     {@link FlexibleAddress }
         *     
         */
        public FlexibleAddress getStreetAddress() {
            return streetAddress;
        }

        /**
         * Legt den Wert der streetAddress-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link FlexibleAddress }
         *     
         */
        public void setStreetAddress(FlexibleAddress value) {
            this.streetAddress = value;
        }

        /**
         * 
         *                                         The postal address of this person. (This is usually much more useful
         *                                         than street address.)
         *                                     
         * 
         * @return
         *     possible object is
         *     {@link FlexibleAddress }
         *     
         */
        public FlexibleAddress getMailingAddress() {
            return mailingAddress;
        }

        /**
         * Legt den Wert der mailingAddress-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link FlexibleAddress }
         *     
         */
        public void setMailingAddress(FlexibleAddress value) {
            this.mailingAddress = value;
        }

        /**
         * 
         *                                         A list of phone numbers at which this person can possibly be reached.
         *                                         The "best" numbers should be listed first.
         *                                     Gets the value of the phoneNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phoneNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoneNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhoneNumber }
         * 
         * 
         */
        public List<PhoneNumber> getPhoneNumber() {
            if (phoneNumber == null) {
                phoneNumber = new ArrayList<PhoneNumber>();
            }
            return this.phoneNumber;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd&gt;HTTPS"&gt;
         *       &lt;attribute name="size-px"&gt;
         *         &lt;simpleType&gt;
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *             &lt;pattern value="[0-9]+x[0-9]+"/&gt;
         *           &lt;/restriction&gt;
         *         &lt;/simpleType&gt;
         *       &lt;/attribute&gt;
         *       &lt;attribute name="public" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
         *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
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
        public static class PhotoUrl
            implements Serializable
        {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "size-px")
            protected String sizePx;
            @XmlAttribute(name = "public")
            protected Boolean _public;
            @XmlAttribute(name = "date")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;

            /**
             * 
             *                 Secure (HTTPS), absolute URL.
             *             
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
             * Ruft den Wert der sizePx-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSizePx() {
                return sizePx;
            }

            /**
             * Legt den Wert der sizePx-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSizePx(String value) {
                this.sizePx = value;
            }

            /**
             * Ruft den Wert der public-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public boolean isPublic() {
                if (_public == null) {
                    return false;
                } else {
                    return _public;
                }
            }

            /**
             * Legt den Wert der public-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setPublic(Boolean value) {
                this._public = value;
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

        }

    }

}
