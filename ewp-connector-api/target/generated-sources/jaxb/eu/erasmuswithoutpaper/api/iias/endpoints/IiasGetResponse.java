//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 10:07:40 AM CET 
//


package eu.erasmuswithoutpaper.api.iias.endpoints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.erasmuswithoutpaper.api.types.contact.Contact;


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
 *         &lt;element name="iia" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="partner" maxOccurs="2" minOccurs="2"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                             &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
 *                             &lt;element name="iia-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="signing-contact" type="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}Contact" minOccurs="0"/&gt;
 *                             &lt;element name="signing-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                             &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}contact" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="in-effect" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                   &lt;element name="cooperation-conditions"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}student-studies-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                             &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}student-traineeship-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                             &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}staff-teacher-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                             &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}staff-training-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="conditions-hash" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" minOccurs="0"/&gt;
 *                   &lt;element name="pdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
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
    "iia"
})
@XmlRootElement(name = "iias-get-response")
public class IiasGetResponse
    implements Serializable
{

    protected List<IiasGetResponse.Iia> iia;

    /**
     * Gets the value of the iia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IiasGetResponse.Iia }
     * 
     * 
     */
    public List<IiasGetResponse.Iia> getIia() {
        if (iia == null) {
            iia = new ArrayList<IiasGetResponse.Iia>();
        }
        return this.iia;
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
     *         &lt;element name="partner" maxOccurs="2" minOccurs="2"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ounit-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
     *                   &lt;element name="iia-id" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}AsciiPrintableIdentifier" minOccurs="0"/&gt;
     *                   &lt;element name="iia-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="signing-contact" type="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}Contact" minOccurs="0"/&gt;
     *                   &lt;element name="signing-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}contact" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="in-effect" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *         &lt;element name="cooperation-conditions"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}student-studies-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}student-traineeship-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}staff-teacher-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}staff-training-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="conditions-hash" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" minOccurs="0"/&gt;
     *         &lt;element name="pdf" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
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
        "partner",
        "inEffect",
        "cooperationConditions",
        "conditionsHash",
        "pdf"
    })
    public static class Iia
        implements Serializable
    {

        @XmlElement(required = true)
        protected List<IiasGetResponse.Iia.Partner> partner;
        @XmlElement(name = "in-effect")
        protected boolean inEffect;
        @XmlElement(name = "cooperation-conditions", required = true)
        protected IiasGetResponse.Iia.CooperationConditions cooperationConditions;
        @XmlElement(name = "conditions-hash")
        protected String conditionsHash;
        protected byte[] pdf;

        /**
         * Gets the value of the partner property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the partner property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPartner().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IiasGetResponse.Iia.Partner }
         * 
         * 
         */
        public List<IiasGetResponse.Iia.Partner> getPartner() {
            if (partner == null) {
                partner = new ArrayList<IiasGetResponse.Iia.Partner>();
            }
            return this.partner;
        }

        /**
         * Ruft den Wert der inEffect-Eigenschaft ab.
         * 
         */
        public boolean isInEffect() {
            return inEffect;
        }

        /**
         * Legt den Wert der inEffect-Eigenschaft fest.
         * 
         */
        public void setInEffect(boolean value) {
            this.inEffect = value;
        }

        /**
         * Ruft den Wert der cooperationConditions-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link IiasGetResponse.Iia.CooperationConditions }
         *     
         */
        public IiasGetResponse.Iia.CooperationConditions getCooperationConditions() {
            return cooperationConditions;
        }

        /**
         * Legt den Wert der cooperationConditions-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link IiasGetResponse.Iia.CooperationConditions }
         *     
         */
        public void setCooperationConditions(IiasGetResponse.Iia.CooperationConditions value) {
            this.cooperationConditions = value;
        }

        /**
         * Ruft den Wert der conditionsHash-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConditionsHash() {
            return conditionsHash;
        }

        /**
         * Legt den Wert der conditionsHash-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConditionsHash(String value) {
            this.conditionsHash = value;
        }

        /**
         * Ruft den Wert der pdf-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getPdf() {
            return pdf;
        }

        /**
         * Legt den Wert der pdf-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setPdf(byte[] value) {
            this.pdf = value;
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
         *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}student-studies-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
         *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}student-traineeship-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
         *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}staff-teacher-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
         *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-iias/blob/stable-v6/endpoints/get-response.xsd}staff-training-mobility-spec" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "studentStudiesMobilitySpec",
            "studentTraineeshipMobilitySpec",
            "staffTeacherMobilitySpec",
            "staffTrainingMobilitySpec"
        })
        public static class CooperationConditions
            implements Serializable
        {

            @XmlElement(name = "student-studies-mobility-spec")
            protected List<StudentStudiesMobilitySpec> studentStudiesMobilitySpec;
            @XmlElement(name = "student-traineeship-mobility-spec")
            protected List<StudentTraineeshipMobilitySpec> studentTraineeshipMobilitySpec;
            @XmlElement(name = "staff-teacher-mobility-spec")
            protected List<StaffTeacherMobilitySpec> staffTeacherMobilitySpec;
            @XmlElement(name = "staff-training-mobility-spec")
            protected List<StaffTrainingMobilitySpec> staffTrainingMobilitySpec;

            /**
             * 
             *                                                     A list of descriptions of student mobilities *for studies* (not to be
             *                                                     confused with student mobility *for traineeships*).
             *                                                 Gets the value of the studentStudiesMobilitySpec property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the studentStudiesMobilitySpec property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStudentStudiesMobilitySpec().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link StudentStudiesMobilitySpec }
             * 
             * 
             */
            public List<StudentStudiesMobilitySpec> getStudentStudiesMobilitySpec() {
                if (studentStudiesMobilitySpec == null) {
                    studentStudiesMobilitySpec = new ArrayList<StudentStudiesMobilitySpec>();
                }
                return this.studentStudiesMobilitySpec;
            }

            /**
             * 
             *                                                     A list of descriptions of student mobility *for traineeships*.
             *                                                 Gets the value of the studentTraineeshipMobilitySpec property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the studentTraineeshipMobilitySpec property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStudentTraineeshipMobilitySpec().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link StudentTraineeshipMobilitySpec }
             * 
             * 
             */
            public List<StudentTraineeshipMobilitySpec> getStudentTraineeshipMobilitySpec() {
                if (studentTraineeshipMobilitySpec == null) {
                    studentTraineeshipMobilitySpec = new ArrayList<StudentTraineeshipMobilitySpec>();
                }
                return this.studentTraineeshipMobilitySpec;
            }

            /**
             * 
             *                                                     A list of descriptions of staff mobility *for teaching*.
             *                                                 Gets the value of the staffTeacherMobilitySpec property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the staffTeacherMobilitySpec property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStaffTeacherMobilitySpec().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link StaffTeacherMobilitySpec }
             * 
             * 
             */
            public List<StaffTeacherMobilitySpec> getStaffTeacherMobilitySpec() {
                if (staffTeacherMobilitySpec == null) {
                    staffTeacherMobilitySpec = new ArrayList<StaffTeacherMobilitySpec>();
                }
                return this.staffTeacherMobilitySpec;
            }

            /**
             * 
             *                                                     A list of descriptions of staff mobility *for training*.
             *                                                 Gets the value of the staffTrainingMobilitySpec property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the staffTrainingMobilitySpec property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStaffTrainingMobilitySpec().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link StaffTrainingMobilitySpec }
             * 
             * 
             */
            public List<StaffTrainingMobilitySpec> getStaffTrainingMobilitySpec() {
                if (staffTrainingMobilitySpec == null) {
                    staffTrainingMobilitySpec = new ArrayList<StaffTrainingMobilitySpec>();
                }
                return this.staffTrainingMobilitySpec;
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
         *         &lt;element name="iia-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="signing-contact" type="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}Contact" minOccurs="0"/&gt;
         *         &lt;element name="signing-date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
         *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1}contact" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "iiaId",
            "iiaCode",
            "signingContact",
            "signingDate",
            "contact"
        })
        public static class Partner
            implements Serializable
        {

            @XmlElement(name = "hei-id", required = true)
            protected String heiId;
            @XmlElement(name = "ounit-id")
            protected String ounitId;
            @XmlElement(name = "iia-id")
            protected String iiaId;
            @XmlElement(name = "iia-code")
            protected String iiaCode;
            @XmlElement(name = "signing-contact")
            protected Contact signingContact;
            @XmlElement(name = "signing-date")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar signingDate;
            @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-contact/tree/stable-v1")
            protected List<Contact> contact;

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

            /**
             * Ruft den Wert der iiaCode-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIiaCode() {
                return iiaCode;
            }

            /**
             * Legt den Wert der iiaCode-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIiaCode(String value) {
                this.iiaCode = value;
            }

            /**
             * Ruft den Wert der signingContact-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Contact }
             *     
             */
            public Contact getSigningContact() {
                return signingContact;
            }

            /**
             * Legt den Wert der signingContact-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Contact }
             *     
             */
            public void setSigningContact(Contact value) {
                this.signingContact = value;
            }

            /**
             * Ruft den Wert der signingDate-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getSigningDate() {
                return signingDate;
            }

            /**
             * Legt den Wert der signingDate-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setSigningDate(XMLGregorianCalendar value) {
                this.signingDate = value;
            }

            /**
             * 
             *                                                     A list of other partner contacts related to this IIA (or to mobilities related
             *                                                     to this IIA).
             * 
             *                                                     Only some servers provide these, even for the *local* partner. Many HEIs
             *                                                     (especially the smaller ones) don't granulate their contacts in such a detailed
             *                                                     way. Instead, they have a fixed set of contacts described in their Institutions
             *                                                     API.
             * 
             *                                                     These contacts take precedence over contacts defined in the Institutions API
             *                                                     and Organization Units API for the partner HEI/unit. Clients are advised to
             *                                                     display these contacts in a separate section above other contacts - so that the
             *                                                     users will notice them first (before scrolling down to other, more generic
             *                                                     contacts).
             *                                                 Gets the value of the contact property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the contact property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getContact().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Contact }
             * 
             * 
             */
            public List<Contact> getContact() {
                if (contact == null) {
                    contact = new ArrayList<Contact>();
                }
                return this.contact;
            }

        }

    }

}
