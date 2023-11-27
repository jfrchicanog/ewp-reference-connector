//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.27 um 01:24:38 PM CET 
//


package eu.erasmuswithoutpaper.api.factsheet;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.architecture.HTTPWithOptionalLang;


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
 *         &lt;element name="factsheet" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="calendar"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="student-nominations" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}CalendarEntry"/&gt;
 *                             &lt;element name="student-applications" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}CalendarEntry"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="application-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
 *                   &lt;element name="additional-requirement" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="information-website" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="decision-weeks-limit" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="tor-weeks-limit" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                   &lt;element name="accessibility" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="type"&gt;
 *                               &lt;simpleType&gt;
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                   &lt;enumeration value="infrastructure"/&gt;
 *                                   &lt;enumeration value="service"/&gt;
 *                                 &lt;/restriction&gt;
 *                               &lt;/simpleType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="information" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="housing-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
 *                   &lt;element name="visa-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
 *                   &lt;element name="insurance-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
 *                   &lt;element name="additional-info" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
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
    "factsheet"
})
@XmlRootElement(name = "factsheet-response")
public class FactsheetResponse
    implements Serializable
{

    protected List<FactsheetResponse.Factsheet> factsheet;

    /**
     * Gets the value of the factsheet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the factsheet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFactsheet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FactsheetResponse.Factsheet }
     * 
     * 
     */
    public List<FactsheetResponse.Factsheet> getFactsheet() {
        if (factsheet == null) {
            factsheet = new ArrayList<FactsheetResponse.Factsheet>();
        }
        return this.factsheet;
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
     *         &lt;element name="calendar"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="student-nominations" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}CalendarEntry"/&gt;
     *                   &lt;element name="student-applications" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}CalendarEntry"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="application-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
     *         &lt;element name="additional-requirement" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="information-website" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="decision-weeks-limit" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="tor-weeks-limit" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *         &lt;element name="accessibility" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="type"&gt;
     *                     &lt;simpleType&gt;
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *                         &lt;enumeration value="infrastructure"/&gt;
     *                         &lt;enumeration value="service"/&gt;
     *                       &lt;/restriction&gt;
     *                     &lt;/simpleType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="information" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="housing-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
     *         &lt;element name="visa-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
     *         &lt;element name="insurance-info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
     *         &lt;element name="additional-info" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
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
        "heiId",
        "calendar",
        "applicationInfo",
        "additionalRequirement",
        "decisionWeeksLimit",
        "torWeeksLimit",
        "accessibility",
        "housingInfo",
        "visaInfo",
        "insuranceInfo",
        "additionalInfo"
    })
    public static class Factsheet
        implements Serializable
    {

        @XmlElement(name = "hei-id", required = true)
        protected String heiId;
        @XmlElement(required = true)
        protected FactsheetResponse.Factsheet.Calendar calendar;
        @XmlElement(name = "application-info", required = true)
        protected InformationEntry applicationInfo;
        @XmlElement(name = "additional-requirement")
        protected List<FactsheetResponse.Factsheet.AdditionalRequirement> additionalRequirement;
        @XmlElement(name = "decision-weeks-limit", required = true)
        protected BigInteger decisionWeeksLimit;
        @XmlElement(name = "tor-weeks-limit", required = true)
        protected BigInteger torWeeksLimit;
        protected List<FactsheetResponse.Factsheet.Accessibility> accessibility;
        @XmlElement(name = "housing-info", required = true)
        protected InformationEntry housingInfo;
        @XmlElement(name = "visa-info", required = true)
        protected InformationEntry visaInfo;
        @XmlElement(name = "insurance-info", required = true)
        protected InformationEntry insuranceInfo;
        @XmlElement(name = "additional-info")
        protected List<FactsheetResponse.Factsheet.AdditionalInfo> additionalInfo;

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
         * Ruft den Wert der calendar-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link FactsheetResponse.Factsheet.Calendar }
         *     
         */
        public FactsheetResponse.Factsheet.Calendar getCalendar() {
            return calendar;
        }

        /**
         * Legt den Wert der calendar-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link FactsheetResponse.Factsheet.Calendar }
         *     
         */
        public void setCalendar(FactsheetResponse.Factsheet.Calendar value) {
            this.calendar = value;
        }

        /**
         * Ruft den Wert der applicationInfo-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link InformationEntry }
         *     
         */
        public InformationEntry getApplicationInfo() {
            return applicationInfo;
        }

        /**
         * Legt den Wert der applicationInfo-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link InformationEntry }
         *     
         */
        public void setApplicationInfo(InformationEntry value) {
            this.applicationInfo = value;
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
         * {@link FactsheetResponse.Factsheet.AdditionalRequirement }
         * 
         * 
         */
        public List<FactsheetResponse.Factsheet.AdditionalRequirement> getAdditionalRequirement() {
            if (additionalRequirement == null) {
                additionalRequirement = new ArrayList<FactsheetResponse.Factsheet.AdditionalRequirement>();
            }
            return this.additionalRequirement;
        }

        /**
         * Ruft den Wert der decisionWeeksLimit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDecisionWeeksLimit() {
            return decisionWeeksLimit;
        }

        /**
         * Legt den Wert der decisionWeeksLimit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDecisionWeeksLimit(BigInteger value) {
            this.decisionWeeksLimit = value;
        }

        /**
         * Ruft den Wert der torWeeksLimit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTorWeeksLimit() {
            return torWeeksLimit;
        }

        /**
         * Legt den Wert der torWeeksLimit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTorWeeksLimit(BigInteger value) {
            this.torWeeksLimit = value;
        }

        /**
         * Gets the value of the accessibility property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accessibility property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccessibility().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FactsheetResponse.Factsheet.Accessibility }
         * 
         * 
         */
        public List<FactsheetResponse.Factsheet.Accessibility> getAccessibility() {
            if (accessibility == null) {
                accessibility = new ArrayList<FactsheetResponse.Factsheet.Accessibility>();
            }
            return this.accessibility;
        }

        /**
         * Ruft den Wert der housingInfo-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link InformationEntry }
         *     
         */
        public InformationEntry getHousingInfo() {
            return housingInfo;
        }

        /**
         * Legt den Wert der housingInfo-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link InformationEntry }
         *     
         */
        public void setHousingInfo(InformationEntry value) {
            this.housingInfo = value;
        }

        /**
         * Ruft den Wert der visaInfo-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link InformationEntry }
         *     
         */
        public InformationEntry getVisaInfo() {
            return visaInfo;
        }

        /**
         * Legt den Wert der visaInfo-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link InformationEntry }
         *     
         */
        public void setVisaInfo(InformationEntry value) {
            this.visaInfo = value;
        }

        /**
         * Ruft den Wert der insuranceInfo-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link InformationEntry }
         *     
         */
        public InformationEntry getInsuranceInfo() {
            return insuranceInfo;
        }

        /**
         * Legt den Wert der insuranceInfo-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link InformationEntry }
         *     
         */
        public void setInsuranceInfo(InformationEntry value) {
            this.insuranceInfo = value;
        }

        /**
         * Gets the value of the additionalInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionalInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionalInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FactsheetResponse.Factsheet.AdditionalInfo }
         * 
         * 
         */
        public List<FactsheetResponse.Factsheet.AdditionalInfo> getAdditionalInfo() {
            if (additionalInfo == null) {
                additionalInfo = new ArrayList<FactsheetResponse.Factsheet.AdditionalInfo>();
            }
            return this.additionalInfo;
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
         *         &lt;element name="type"&gt;
         *           &lt;simpleType&gt;
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
         *               &lt;enumeration value="infrastructure"/&gt;
         *               &lt;enumeration value="service"/&gt;
         *             &lt;/restriction&gt;
         *           &lt;/simpleType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="information" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
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
            "name",
            "description",
            "information"
        })
        public static class Accessibility
            implements Serializable
        {

            @XmlElement(required = true)
            protected String type;
            @XmlElement(required = true)
            protected String name;
            protected String description;
            @XmlElement(required = true)
            protected InformationEntry information;

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
             * Ruft den Wert der description-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescription() {
                return description;
            }

            /**
             * Legt den Wert der description-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescription(String value) {
                this.description = value;
            }

            /**
             * Ruft den Wert der information-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link InformationEntry }
             *     
             */
            public InformationEntry getInformation() {
                return information;
            }

            /**
             * Legt den Wert der information-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link InformationEntry }
             *     
             */
            public void setInformation(InformationEntry value) {
                this.information = value;
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
         *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="info" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}InformationEntry"/&gt;
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
            "info"
        })
        public static class AdditionalInfo
            implements Serializable
        {

            @XmlElement(required = true)
            protected String type;
            @XmlElement(required = true)
            protected InformationEntry info;

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
             * Ruft den Wert der info-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link InformationEntry }
             *     
             */
            public InformationEntry getInfo() {
                return info;
            }

            /**
             * Legt den Wert der info-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link InformationEntry }
             *     
             */
            public void setInfo(InformationEntry value) {
                this.info = value;
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
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="information-website" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPWithOptionalLang" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "details",
            "informationWebsite"
        })
        public static class AdditionalRequirement
            implements Serializable
        {

            @XmlElement(required = true)
            protected String name;
            @XmlElement(required = true)
            protected String details;
            @XmlElement(name = "information-website")
            protected List<HTTPWithOptionalLang> informationWebsite;

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
             * Ruft den Wert der details-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDetails() {
                return details;
            }

            /**
             * Legt den Wert der details-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDetails(String value) {
                this.details = value;
            }

            /**
             * Gets the value of the informationWebsite property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the informationWebsite property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getInformationWebsite().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link HTTPWithOptionalLang }
             * 
             * 
             */
            public List<HTTPWithOptionalLang> getInformationWebsite() {
                if (informationWebsite == null) {
                    informationWebsite = new ArrayList<HTTPWithOptionalLang>();
                }
                return this.informationWebsite;
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
         *         &lt;element name="student-nominations" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}CalendarEntry"/&gt;
         *         &lt;element name="student-applications" type="{https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/tree/stable-v1}CalendarEntry"/&gt;
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
            "studentNominations",
            "studentApplications"
        })
        public static class Calendar
            implements Serializable
        {

            @XmlElement(name = "student-nominations", required = true)
            protected CalendarEntry studentNominations;
            @XmlElement(name = "student-applications", required = true)
            protected CalendarEntry studentApplications;

            /**
             * Ruft den Wert der studentNominations-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link CalendarEntry }
             *     
             */
            public CalendarEntry getStudentNominations() {
                return studentNominations;
            }

            /**
             * Legt den Wert der studentNominations-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link CalendarEntry }
             *     
             */
            public void setStudentNominations(CalendarEntry value) {
                this.studentNominations = value;
            }

            /**
             * Ruft den Wert der studentApplications-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link CalendarEntry }
             *     
             */
            public CalendarEntry getStudentApplications() {
                return studentApplications;
            }

            /**
             * Legt den Wert der studentApplications-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link CalendarEntry }
             *     
             */
            public void setStudentApplications(CalendarEntry value) {
                this.studentApplications = value;
            }

        }

    }

}
