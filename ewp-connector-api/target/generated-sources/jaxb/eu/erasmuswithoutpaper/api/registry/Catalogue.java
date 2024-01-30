//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 10:07:40 AM CET 
//


package eu.erasmuswithoutpaper.api.registry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import eu.erasmuswithoutpaper.api.architecture.MultilineString;


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
 *         &lt;element name="host" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-email" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-provider" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-notes" minOccurs="0"/&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}apis-implemented" minOccurs="0"/&gt;
 *                   &lt;element name="institutions-covered" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="client-credentials-in-use" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="certificate" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="server-credentials-in-use" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
 *         &lt;element name="institutions"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}hei" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="binaries" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;base64Binary"&gt;
 *                           &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
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
    "host",
    "institutions",
    "binaries"
})
@XmlRootElement(name = "catalogue")
public class Catalogue
    implements Serializable
{

    @XmlElement(required = true)
    protected List<Catalogue.Host> host;
    @XmlElement(required = true)
    protected Catalogue.Institutions institutions;
    protected Catalogue.Binaries binaries;

    /**
     * Gets the value of the host property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the host property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHost().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Catalogue.Host }
     * 
     * 
     */
    public List<Catalogue.Host> getHost() {
        if (host == null) {
            host = new ArrayList<Catalogue.Host>();
        }
        return this.host;
    }

    /**
     * Ruft den Wert der institutions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Catalogue.Institutions }
     *     
     */
    public Catalogue.Institutions getInstitutions() {
        return institutions;
    }

    /**
     * Legt den Wert der institutions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Catalogue.Institutions }
     *     
     */
    public void setInstitutions(Catalogue.Institutions value) {
        this.institutions = value;
    }

    /**
     * Ruft den Wert der binaries-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Catalogue.Binaries }
     *     
     */
    public Catalogue.Binaries getBinaries() {
        return binaries;
    }

    /**
     * Legt den Wert der binaries-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Catalogue.Binaries }
     *     
     */
    public void setBinaries(Catalogue.Binaries value) {
        this.binaries = value;
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
     *         &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;base64Binary"&gt;
     *                 &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
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
        "rsaPublicKey"
    })
    public static class Binaries
        implements Serializable
    {

        @XmlElement(name = "rsa-public-key")
        protected List<Catalogue.Binaries.RsaPublicKey> rsaPublicKey;

        /**
         * Gets the value of the rsaPublicKey property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rsaPublicKey property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRsaPublicKey().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Catalogue.Binaries.RsaPublicKey }
         * 
         * 
         */
        public List<Catalogue.Binaries.RsaPublicKey> getRsaPublicKey() {
            if (rsaPublicKey == null) {
                rsaPublicKey = new ArrayList<Catalogue.Binaries.RsaPublicKey>();
            }
            return this.rsaPublicKey;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;base64Binary"&gt;
         *       &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
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
        public static class RsaPublicKey
            implements Serializable
        {

            @XmlValue
            protected byte[] value;
            @XmlAttribute(name = "sha-256", required = true)
            protected String sha256;

            /**
             * Ruft den Wert der value-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     byte[]
             */
            public byte[] getValue() {
                return value;
            }

            /**
             * Legt den Wert der value-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     byte[]
             */
            public void setValue(byte[] value) {
                this.value = value;
            }

            /**
             * Ruft den Wert der sha256-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSha256() {
                return sha256;
            }

            /**
             * Legt den Wert der sha256-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSha256(String value) {
                this.sha256 = value;
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
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-email" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-provider" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-notes" minOccurs="0"/&gt;
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}apis-implemented" minOccurs="0"/&gt;
     *         &lt;element name="institutions-covered" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="client-credentials-in-use" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="certificate" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="server-credentials-in-use" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
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
        "adminEmail",
        "adminProvider",
        "adminNotes",
        "apisImplemented",
        "institutionsCovered",
        "clientCredentialsInUse",
        "serverCredentialsInUse"
    })
    public static class Host
        implements Serializable
    {

        @XmlElement(name = "admin-email", namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd")
        protected List<String> adminEmail;
        @XmlElement(name = "admin-provider", namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd")
        protected String adminProvider;
        @XmlElement(name = "admin-notes", namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd")
        protected MultilineString adminNotes;
        @XmlElement(name = "apis-implemented")
        protected ApisImplemented apisImplemented;
        @XmlElement(name = "institutions-covered")
        protected Catalogue.Host.InstitutionsCovered institutionsCovered;
        @XmlElement(name = "client-credentials-in-use")
        protected Catalogue.Host.ClientCredentialsInUse clientCredentialsInUse;
        @XmlElement(name = "server-credentials-in-use")
        protected Catalogue.Host.ServerCredentialsInUse serverCredentialsInUse;

        /**
         * Gets the value of the adminEmail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the adminEmail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdminEmail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAdminEmail() {
            if (adminEmail == null) {
                adminEmail = new ArrayList<String>();
            }
            return this.adminEmail;
        }

        /**
         * Ruft den Wert der adminProvider-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAdminProvider() {
            return adminProvider;
        }

        /**
         * Legt den Wert der adminProvider-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAdminProvider(String value) {
            this.adminProvider = value;
        }

        /**
         * Ruft den Wert der adminNotes-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link MultilineString }
         *     
         */
        public MultilineString getAdminNotes() {
            return adminNotes;
        }

        /**
         * Legt den Wert der adminNotes-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link MultilineString }
         *     
         */
        public void setAdminNotes(MultilineString value) {
            this.adminNotes = value;
        }

        /**
         * Ruft den Wert der apisImplemented-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link ApisImplemented }
         *     
         */
        public ApisImplemented getApisImplemented() {
            return apisImplemented;
        }

        /**
         * Legt den Wert der apisImplemented-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link ApisImplemented }
         *     
         */
        public void setApisImplemented(ApisImplemented value) {
            this.apisImplemented = value;
        }

        /**
         * Ruft den Wert der institutionsCovered-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Catalogue.Host.InstitutionsCovered }
         *     
         */
        public Catalogue.Host.InstitutionsCovered getInstitutionsCovered() {
            return institutionsCovered;
        }

        /**
         * Legt den Wert der institutionsCovered-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Catalogue.Host.InstitutionsCovered }
         *     
         */
        public void setInstitutionsCovered(Catalogue.Host.InstitutionsCovered value) {
            this.institutionsCovered = value;
        }

        /**
         * Ruft den Wert der clientCredentialsInUse-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Catalogue.Host.ClientCredentialsInUse }
         *     
         */
        public Catalogue.Host.ClientCredentialsInUse getClientCredentialsInUse() {
            return clientCredentialsInUse;
        }

        /**
         * Legt den Wert der clientCredentialsInUse-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Catalogue.Host.ClientCredentialsInUse }
         *     
         */
        public void setClientCredentialsInUse(Catalogue.Host.ClientCredentialsInUse value) {
            this.clientCredentialsInUse = value;
        }

        /**
         * Ruft den Wert der serverCredentialsInUse-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Catalogue.Host.ServerCredentialsInUse }
         *     
         */
        public Catalogue.Host.ServerCredentialsInUse getServerCredentialsInUse() {
            return serverCredentialsInUse;
        }

        /**
         * Legt den Wert der serverCredentialsInUse-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Catalogue.Host.ServerCredentialsInUse }
         *     
         */
        public void setServerCredentialsInUse(Catalogue.Host.ServerCredentialsInUse value) {
            this.serverCredentialsInUse = value;
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
         *         &lt;element name="certificate" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
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
            "certificate",
            "rsaPublicKey"
        })
        public static class ClientCredentialsInUse
            implements Serializable
        {

            protected List<Catalogue.Host.ClientCredentialsInUse.Certificate> certificate;
            @XmlElement(name = "rsa-public-key")
            protected List<Catalogue.Host.ClientCredentialsInUse.RsaPublicKey> rsaPublicKey;

            /**
             * Gets the value of the certificate property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the certificate property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCertificate().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Catalogue.Host.ClientCredentialsInUse.Certificate }
             * 
             * 
             */
            public List<Catalogue.Host.ClientCredentialsInUse.Certificate> getCertificate() {
                if (certificate == null) {
                    certificate = new ArrayList<Catalogue.Host.ClientCredentialsInUse.Certificate>();
                }
                return this.certificate;
            }

            /**
             * Gets the value of the rsaPublicKey property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the rsaPublicKey property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRsaPublicKey().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Catalogue.Host.ClientCredentialsInUse.RsaPublicKey }
             * 
             * 
             */
            public List<Catalogue.Host.ClientCredentialsInUse.RsaPublicKey> getRsaPublicKey() {
                if (rsaPublicKey == null) {
                    rsaPublicKey = new ArrayList<Catalogue.Host.ClientCredentialsInUse.RsaPublicKey>();
                }
                return this.rsaPublicKey;
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
             *       &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Certificate
                implements Serializable
            {

                @XmlAttribute(name = "sha-256", required = true)
                protected String sha256;

                /**
                 * Ruft den Wert der sha256-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSha256() {
                    return sha256;
                }

                /**
                 * Legt den Wert der sha256-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSha256(String value) {
                    this.sha256 = value;
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
             *       &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class RsaPublicKey
                implements Serializable
            {

                @XmlAttribute(name = "sha-256", required = true)
                protected String sha256;

                /**
                 * Ruft den Wert der sha256-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSha256() {
                    return sha256;
                }

                /**
                 * Legt den Wert der sha256-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSha256(String value) {
                    this.sha256 = value;
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
         *         &lt;element name="hei-id" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "heiId"
        })
        public static class InstitutionsCovered
            implements Serializable
        {

            @XmlElement(name = "hei-id")
            protected List<String> heiId;

            /**
             * Gets the value of the heiId property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the heiId property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getHeiId().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getHeiId() {
                if (heiId == null) {
                    heiId = new ArrayList<String>();
                }
                return this.heiId;
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
         *         &lt;element name="rsa-public-key" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
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
            "rsaPublicKey"
        })
        public static class ServerCredentialsInUse
            implements Serializable
        {

            @XmlElement(name = "rsa-public-key")
            protected List<Catalogue.Host.ServerCredentialsInUse.RsaPublicKey> rsaPublicKey;

            /**
             * Gets the value of the rsaPublicKey property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the rsaPublicKey property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRsaPublicKey().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Catalogue.Host.ServerCredentialsInUse.RsaPublicKey }
             * 
             * 
             */
            public List<Catalogue.Host.ServerCredentialsInUse.RsaPublicKey> getRsaPublicKey() {
                if (rsaPublicKey == null) {
                    rsaPublicKey = new ArrayList<Catalogue.Host.ServerCredentialsInUse.RsaPublicKey>();
                }
                return this.rsaPublicKey;
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
             *       &lt;attribute name="sha-256" use="required" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}Sha256Hex" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class RsaPublicKey
                implements Serializable
            {

                @XmlAttribute(name = "sha-256", required = true)
                protected String sha256;

                /**
                 * Ruft den Wert der sha256-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSha256() {
                    return sha256;
                }

                /**
                 * Legt den Wert der sha256-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSha256(String value) {
                    this.sha256 = value;
                }

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
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}hei" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "hei"
    })
    public static class Institutions
        implements Serializable
    {

        protected List<Hei> hei;

        /**
         * Gets the value of the hei property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hei property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHei().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Hei }
         * 
         * 
         */
        public List<Hei> getHei() {
            if (hei == null) {
                hei = new ArrayList<Hei>();
            }
            return this.hei;
        }

    }

}
