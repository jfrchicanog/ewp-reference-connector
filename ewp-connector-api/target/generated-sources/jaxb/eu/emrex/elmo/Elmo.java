//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 05:42:32 PM CET 
//


package eu.emrex.elmo;

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
import eu.erasmuswithoutpaper.api.types.address.FlexibleAddress;
import eu.europa.cedefop.europass.europass.v2.CountryCode;
import org.w3.xmldsig.SignatureType;


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
 *         &lt;element name="generatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="learner"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="citizenship" type="{http://europass.cedefop.europa.eu/Europass/V2.0}countryCode" minOccurs="0"/&gt;
 *                   &lt;element name="identifier" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
 *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="givenNames" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
 *                   &lt;element name="familyName" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
 *                   &lt;element name="bday" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                   &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *                   &lt;element name="birthName" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
 *                   &lt;element name="currentAddress" type="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}FlexibleAddress" minOccurs="0"/&gt;
 *                   &lt;element name="gender" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;enumeration value="0"/&gt;
 *                         &lt;enumeration value="1"/&gt;
 *                         &lt;enumeration value="2"/&gt;
 *                         &lt;enumeration value="9"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="report" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="issuer"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="country" type="{http://europass.cedefop.europa.eu/Europass/V2.0}countryCode" minOccurs="0"/&gt;
 *                             &lt;element name="identifier" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;simpleContent&gt;
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
 *                                     &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *                                   &lt;/extension&gt;
 *                                 &lt;/simpleContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
 *                             &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="learningOpportunitySpecification" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}LearningOpportunitySpecification" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="issueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *                   &lt;element name="gradingScheme" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="localId" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="attachment" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}Attachment" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="attachment" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}Attachment" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="groups" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}Groups" minOccurs="0"/&gt;
 *         &lt;element name="extension" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}CustomExtensionsContainer" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/&gt;
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
    "generatedDate",
    "learner",
    "report",
    "attachment",
    "groups",
    "extension",
    "signature"
})
@XmlRootElement(name = "elmo")
public class Elmo
    implements Serializable
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generatedDate;
    @XmlElement(required = true)
    protected Elmo.Learner learner;
    @XmlElement(required = true)
    protected List<Elmo.Report> report;
    protected List<Attachment> attachment;
    protected Groups groups;
    protected CustomExtensionsContainer extension;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Ruft den Wert der generatedDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGeneratedDate() {
        return generatedDate;
    }

    /**
     * Legt den Wert der generatedDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGeneratedDate(XMLGregorianCalendar value) {
        this.generatedDate = value;
    }

    /**
     * Ruft den Wert der learner-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Elmo.Learner }
     *     
     */
    public Elmo.Learner getLearner() {
        return learner;
    }

    /**
     * Legt den Wert der learner-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Elmo.Learner }
     *     
     */
    public void setLearner(Elmo.Learner value) {
        this.learner = value;
    }

    /**
     * Gets the value of the report property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the report property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Elmo.Report }
     * 
     * 
     */
    public List<Elmo.Report> getReport() {
        if (report == null) {
            report = new ArrayList<Elmo.Report>();
        }
        return this.report;
    }

    /**
     * Gets the value of the attachment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attachment }
     * 
     * 
     */
    public List<Attachment> getAttachment() {
        if (attachment == null) {
            attachment = new ArrayList<Attachment>();
        }
        return this.attachment;
    }

    /**
     * Ruft den Wert der groups-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Groups }
     *     
     */
    public Groups getGroups() {
        return groups;
    }

    /**
     * Legt den Wert der groups-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Groups }
     *     
     */
    public void setGroups(Groups value) {
        this.groups = value;
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
     * Every EMREX ELMO element MUST be signed with xmldsig-core2 enveloped signature. The
     *                             ds:SignedInfo element MUST contain a single ds:Reference with an empty URI. The
     *                             key used by the server for signing must often match some external criteria, which
     *                             are NOT documented here. (E.g. If you're implementing EMREX EMP, then the certificate
     *                             used must match the one previously published in EMREG for your EMP.) The ds:Signature
     *                             element SHOULD contain the copy of the its certficate in the ds:KeyInfo element.
     *                         
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Legt den Wert der signature-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
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
     *         &lt;element name="citizenship" type="{http://europass.cedefop.europa.eu/Europass/V2.0}countryCode" minOccurs="0"/&gt;
     *         &lt;element name="identifier" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
     *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="givenNames" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
     *         &lt;element name="familyName" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
     *         &lt;element name="bday" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *         &lt;element name="placeOfBirth" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
     *         &lt;element name="birthName" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/&gt;
     *         &lt;element name="currentAddress" type="{https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1}FlexibleAddress" minOccurs="0"/&gt;
     *         &lt;element name="gender" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;enumeration value="0"/&gt;
     *               &lt;enumeration value="1"/&gt;
     *               &lt;enumeration value="2"/&gt;
     *               &lt;enumeration value="9"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
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
        "citizenship",
        "identifier",
        "givenNames",
        "familyName",
        "bday",
        "placeOfBirth",
        "birthName",
        "currentAddress",
        "gender"
    })
    public static class Learner
        implements Serializable
    {

        @XmlSchemaType(name = "string")
        protected CountryCode citizenship;
        protected List<Elmo.Learner.Identifier> identifier;
        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String givenNames;
        @XmlElement(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String familyName;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar bday;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String placeOfBirth;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String birthName;
        protected FlexibleAddress currentAddress;
        protected BigInteger gender;

        /**
         * Ruft den Wert der citizenship-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link CountryCode }
         *     
         */
        public CountryCode getCitizenship() {
            return citizenship;
        }

        /**
         * Legt den Wert der citizenship-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link CountryCode }
         *     
         */
        public void setCitizenship(CountryCode value) {
            this.citizenship = value;
        }

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
         * {@link Elmo.Learner.Identifier }
         * 
         * 
         */
        public List<Elmo.Learner.Identifier> getIdentifier() {
            if (identifier == null) {
                identifier = new ArrayList<Elmo.Learner.Identifier>();
            }
            return this.identifier;
        }

        /**
         * Ruft den Wert der givenNames-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGivenNames() {
            return givenNames;
        }

        /**
         * Legt den Wert der givenNames-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGivenNames(String value) {
            this.givenNames = value;
        }

        /**
         * Ruft den Wert der familyName-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFamilyName() {
            return familyName;
        }

        /**
         * Legt den Wert der familyName-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFamilyName(String value) {
            this.familyName = value;
        }

        /**
         * Ruft den Wert der bday-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getBday() {
            return bday;
        }

        /**
         * Legt den Wert der bday-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setBday(XMLGregorianCalendar value) {
            this.bday = value;
        }

        /**
         * Ruft den Wert der placeOfBirth-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPlaceOfBirth() {
            return placeOfBirth;
        }

        /**
         * Legt den Wert der placeOfBirth-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPlaceOfBirth(String value) {
            this.placeOfBirth = value;
        }

        /**
         * Ruft den Wert der birthName-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBirthName() {
            return birthName;
        }

        /**
         * Legt den Wert der birthName-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBirthName(String value) {
            this.birthName = value;
        }

        /**
         * Ruft den Wert der currentAddress-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link FlexibleAddress }
         *     
         */
        public FlexibleAddress getCurrentAddress() {
            return currentAddress;
        }

        /**
         * Legt den Wert der currentAddress-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link FlexibleAddress }
         *     
         */
        public void setCurrentAddress(FlexibleAddress value) {
            this.currentAddress = value;
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

    }


    /**
     * This describes an institution (issuer) and a *subset* of courses *completed*
     *                                 by the student within this institution.
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
     *         &lt;element name="issuer"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="country" type="{http://europass.cedefop.europa.eu/Europass/V2.0}countryCode" minOccurs="0"/&gt;
     *                   &lt;element name="identifier" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;simpleContent&gt;
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
     *                           &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *                         &lt;/extension&gt;
     *                       &lt;/simpleContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
     *                   &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="learningOpportunitySpecification" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}LearningOpportunitySpecification" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="issueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
     *         &lt;element name="gradingScheme" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="localId" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="attachment" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}Attachment" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "issuer",
        "learningOpportunitySpecification",
        "issueDate",
        "gradingScheme",
        "attachment"
    })
    public static class Report
        implements Serializable
    {

        @XmlElement(required = true)
        protected Elmo.Report.Issuer issuer;
        protected List<LearningOpportunitySpecification> learningOpportunitySpecification;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar issueDate;
        protected List<Elmo.Report.GradingScheme> gradingScheme;
        protected List<Attachment> attachment;

        /**
         * Ruft den Wert der issuer-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Elmo.Report.Issuer }
         *     
         */
        public Elmo.Report.Issuer getIssuer() {
            return issuer;
        }

        /**
         * Legt den Wert der issuer-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Elmo.Report.Issuer }
         *     
         */
        public void setIssuer(Elmo.Report.Issuer value) {
            this.issuer = value;
        }

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
         * {@link LearningOpportunitySpecification }
         * 
         * 
         */
        public List<LearningOpportunitySpecification> getLearningOpportunitySpecification() {
            if (learningOpportunitySpecification == null) {
                learningOpportunitySpecification = new ArrayList<LearningOpportunitySpecification>();
            }
            return this.learningOpportunitySpecification;
        }

        /**
         * Ruft den Wert der issueDate-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getIssueDate() {
            return issueDate;
        }

        /**
         * Legt den Wert der issueDate-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setIssueDate(XMLGregorianCalendar value) {
            this.issueDate = value;
        }

        /**
         * Gets the value of the gradingScheme property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the gradingScheme property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGradingScheme().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Elmo.Report.GradingScheme }
         * 
         * 
         */
        public List<Elmo.Report.GradingScheme> getGradingScheme() {
            if (gradingScheme == null) {
                gradingScheme = new ArrayList<Elmo.Report.GradingScheme>();
            }
            return this.gradingScheme;
        }

        /**
         * Gets the value of the attachment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attachment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttachment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Attachment }
         * 
         * 
         */
        public List<Attachment> getAttachment() {
            if (attachment == null) {
                attachment = new ArrayList<Attachment>();
            }
            return this.attachment;
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
         *         &lt;element name="description" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}PlaintextMultilineStringWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="localId" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "description"
        })
        public static class GradingScheme
            implements Serializable
        {

            protected List<PlaintextMultilineStringWithOptionalLang> description;
            @XmlAttribute(name = "localId", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String localId;

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
             * Ruft den Wert der localId-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLocalId() {
                return localId;
            }

            /**
             * Legt den Wert der localId-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLocalId(String value) {
                this.localId = value;
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
         *         &lt;element name="country" type="{http://europass.cedefop.europa.eu/Europass/V2.0}countryCode" minOccurs="0"/&gt;
         *         &lt;element name="identifier" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;simpleContent&gt;
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;token"&gt;
         *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
         *               &lt;/extension&gt;
         *             &lt;/simpleContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="title" type="{https://github.com/emrex-eu/elmo-schemas/tree/v1}TokenWithOptionalLang" maxOccurs="unbounded"/&gt;
         *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}token"/&gt;
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
            "country",
            "identifier",
            "title",
            "url"
        })
        public static class Issuer
            implements Serializable
        {

            @XmlSchemaType(name = "string")
            protected CountryCode country;
            @XmlElement(required = true)
            protected List<Elmo.Report.Issuer.Identifier> identifier;
            @XmlElement(required = true)
            protected List<TokenWithOptionalLang> title;
            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String url;

            /**
             * Ruft den Wert der country-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link CountryCode }
             *     
             */
            public CountryCode getCountry() {
                return country;
            }

            /**
             * Legt den Wert der country-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link CountryCode }
             *     
             */
            public void setCountry(CountryCode value) {
                this.country = value;
            }

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
             * {@link Elmo.Report.Issuer.Identifier }
             * 
             * 
             */
            public List<Elmo.Report.Issuer.Identifier> getIdentifier() {
                if (identifier == null) {
                    identifier = new ArrayList<Elmo.Report.Issuer.Identifier>();
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

        }

    }

}
