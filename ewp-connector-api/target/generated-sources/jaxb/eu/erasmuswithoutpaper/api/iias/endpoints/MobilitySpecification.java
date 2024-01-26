//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.26 um 01:15:12 PM CET 
//


package eu.erasmuswithoutpaper.api.iias.endpoints;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import eu.erasmuswithoutpaper.api.types.contact.Contact;


/**
 * 
 *                 A common parent class for all mobility specifications.
 * 
 *                 Note that "mobility specification" is a name we (EWP designers) made up. We
 *                 needed to have a descriptive name for this particular entity, and we couldn't
 *                 find such name in existing Erasmus+ templates. In other words, this is an
 *                 EWP-specific term only, and you should probably avoid using it in other
 *                 contexts.
 * 
 *                 Mobility specification may also be considered as a subclass of "cooperation
 *                 condition", but - since currently all nonabstract subclasses inherit from
 *                 MobilitySpecification - no CooperationCondition class is introduced. See here:
 *                 https://github.com/erasmus-without-paper/ewp-wp3-data-model/issues/10
 * 
 *                 Each specification has a sending and receiving partner. Each specification
 *                 represents an agreement that, for each of the academic years listed within, the
 *                 sending partner will send a particular number of people to the receiving
 *                 partner, for a particular average duration each (e.g. for 8 weeks). This
 *                 describes an unidirectional flow - if people are sent in both directions, two
 *                 separate mobility specifications need to be defined (one for each direction).
 * 
 *                 More requirements (e.g. *who* is being sent to do *what*) are defined in
 *                 specific `*-mobility-spec` subclasses.
 * 
 *                 This type, nor any of its subclasses, SHOULD NOT be referenced outside of the
 *                 EWP IIAs API. It is likely to be modified, or to be removed, in the future.
 *             
 * 
 * <p>Java-Klasse für MobilitySpecification complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MobilitySpecification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sending-hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sending-ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="sending-contact" type="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}Contact" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="receiving-hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="receiving-ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="receiving-contact" type="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}Contact" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="receiving-academic-year-id" type="{https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1}AcademicYearId" maxOccurs="unbounded"/&gt;
 *         &lt;element name="mobilities-per-year" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="recommended-language-skill" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}language"/&gt;
 *                   &lt;element name="cefr-level" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;pattern value="[ABC][12]"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="subject-area" type="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}SubjectArea" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="subject-area" type="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}SubjectArea" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="other-info-terms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MobilitySpecification", propOrder = {
    "sendingHeiId",
    "sendingOunitId",
    "sendingContact",
    "receivingHeiId",
    "receivingOunitId",
    "receivingContact",
    "receivingAcademicYearId",
    "mobilitiesPerYear",
    "recommendedLanguageSkill",
    "subjectArea",
    "otherInfoTerms"
})
@XmlSeeAlso({
    StaffMobilitySpecification.class,
    StudentMobilitySpecification.class
})
public abstract class MobilitySpecification
    implements Serializable
{

    @XmlElement(name = "sending-hei-id", required = true)
    protected String sendingHeiId;
    @XmlElement(name = "sending-ounit-id")
    protected String sendingOunitId;
    @XmlElement(name = "sending-contact")
    protected List<Contact> sendingContact;
    @XmlElement(name = "receiving-hei-id", required = true)
    protected String receivingHeiId;
    @XmlElement(name = "receiving-ounit-id")
    protected String receivingOunitId;
    @XmlElement(name = "receiving-contact")
    protected List<Contact> receivingContact;
    @XmlElement(name = "receiving-academic-year-id", required = true)
    protected List<String> receivingAcademicYearId;
    @XmlElement(name = "mobilities-per-year")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger mobilitiesPerYear;
    @XmlElement(name = "recommended-language-skill")
    protected List<MobilitySpecification.RecommendedLanguageSkill> recommendedLanguageSkill;
    @XmlElement(name = "subject-area")
    protected List<SubjectArea> subjectArea;
    @XmlElement(name = "other-info-terms")
    protected String otherInfoTerms;

    /**
     * Ruft den Wert der sendingHeiId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingHeiId() {
        return sendingHeiId;
    }

    /**
     * Legt den Wert der sendingHeiId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendingHeiId(String value) {
        this.sendingHeiId = value;
    }

    /**
     * Ruft den Wert der sendingOunitId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingOunitId() {
        return sendingOunitId;
    }

    /**
     * Legt den Wert der sendingOunitId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendingOunitId(String value) {
        this.sendingOunitId = value;
    }

    /**
     * Gets the value of the sendingContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sendingContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSendingContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getSendingContact() {
        if (sendingContact == null) {
            sendingContact = new ArrayList<Contact>();
        }
        return this.sendingContact;
    }

    /**
     * Ruft den Wert der receivingHeiId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivingHeiId() {
        return receivingHeiId;
    }

    /**
     * Legt den Wert der receivingHeiId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivingHeiId(String value) {
        this.receivingHeiId = value;
    }

    /**
     * Ruft den Wert der receivingOunitId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivingOunitId() {
        return receivingOunitId;
    }

    /**
     * Legt den Wert der receivingOunitId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivingOunitId(String value) {
        this.receivingOunitId = value;
    }

    /**
     * Gets the value of the receivingContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receivingContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceivingContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contact }
     * 
     * 
     */
    public List<Contact> getReceivingContact() {
        if (receivingContact == null) {
            receivingContact = new ArrayList<Contact>();
        }
        return this.receivingContact;
    }

    /**
     * Gets the value of the receivingAcademicYearId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receivingAcademicYearId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceivingAcademicYearId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReceivingAcademicYearId() {
        if (receivingAcademicYearId == null) {
            receivingAcademicYearId = new ArrayList<String>();
        }
        return this.receivingAcademicYearId;
    }

    /**
     * Ruft den Wert der mobilitiesPerYear-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMobilitiesPerYear() {
        return mobilitiesPerYear;
    }

    /**
     * Legt den Wert der mobilitiesPerYear-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMobilitiesPerYear(BigInteger value) {
        this.mobilitiesPerYear = value;
    }

    /**
     * Gets the value of the recommendedLanguageSkill property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recommendedLanguageSkill property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecommendedLanguageSkill().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MobilitySpecification.RecommendedLanguageSkill }
     * 
     * 
     */
    public List<MobilitySpecification.RecommendedLanguageSkill> getRecommendedLanguageSkill() {
        if (recommendedLanguageSkill == null) {
            recommendedLanguageSkill = new ArrayList<MobilitySpecification.RecommendedLanguageSkill>();
        }
        return this.recommendedLanguageSkill;
    }

    /**
     * Gets the value of the subjectArea property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectArea property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectArea().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubjectArea }
     * 
     * 
     */
    public List<SubjectArea> getSubjectArea() {
        if (subjectArea == null) {
            subjectArea = new ArrayList<SubjectArea>();
        }
        return this.subjectArea;
    }

    /**
     * Ruft den Wert der otherInfoTerms-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherInfoTerms() {
        return otherInfoTerms;
    }

    /**
     * Legt den Wert der otherInfoTerms-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherInfoTerms(String value) {
        this.otherInfoTerms = value;
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
     *         &lt;element name="cefr-level" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;pattern value="[ABC][12]"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="subject-area" type="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}SubjectArea" minOccurs="0"/&gt;
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
        "cefrLevel",
        "subjectArea"
    })
    public static class RecommendedLanguageSkill
        implements Serializable
    {

        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String language;
        @XmlElement(name = "cefr-level")
        protected String cefrLevel;
        @XmlElement(name = "subject-area")
        protected SubjectArea subjectArea;

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

        /**
         * Ruft den Wert der subjectArea-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link SubjectArea }
         *     
         */
        public SubjectArea getSubjectArea() {
            return subjectArea;
        }

        /**
         * Legt den Wert der subjectArea-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link SubjectArea }
         *     
         */
        public void setSubjectArea(SubjectArea value) {
            this.subjectArea = value;
        }

    }

}
