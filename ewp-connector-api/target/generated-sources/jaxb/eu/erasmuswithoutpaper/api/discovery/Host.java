//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.07 um 11:37:23 AM CET 
//


package eu.erasmuswithoutpaper.api.discovery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import eu.erasmuswithoutpaper.api.architecture.MultilineString;
import eu.erasmuswithoutpaper.api.registry.ApisImplemented;
import eu.erasmuswithoutpaper.api.registry.Hei;


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
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-email" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-provider"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}admin-notes" minOccurs="0"/&gt;
 *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}apis-implemented" minOccurs="0"/&gt;
 *         &lt;element name="institutions-covered" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}hei" minOccurs="0"/&gt;
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
 *                   &lt;element name="certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="rsa-public-key" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded" minOccurs="0"/&gt;
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
 *                   &lt;element name="rsa-public-key" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlRootElement(name = "host")
public class Host
    implements Serializable
{

    @XmlElement(name = "admin-email", namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", required = true)
    protected List<java.lang.String> adminEmail;
    @XmlElement(name = "admin-provider", namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", required = true)
    protected java.lang.String adminProvider;
    @XmlElement(name = "admin-notes", namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd")
    protected MultilineString adminNotes;
    @XmlElement(name = "apis-implemented", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1")
    protected ApisImplemented apisImplemented;
    @XmlElement(name = "institutions-covered")
    protected Host.InstitutionsCovered institutionsCovered;
    @XmlElement(name = "client-credentials-in-use")
    protected Host.ClientCredentialsInUse clientCredentialsInUse;
    @XmlElement(name = "server-credentials-in-use")
    protected Host.ServerCredentialsInUse serverCredentialsInUse;

    /**
     * 
     *                             REQUIRED element. Address of a developer or server administrator who may be
     *                             contacted in case of problems (e.g. invalid Manifest file, invalid certificates,
     *                             server errors, etc.). Multiple addresses may be provided.
     * 
     *                             Please note, that additional `admin-email` elements can also be included inside
     *                             specific APIs sections (this allows you to add extra admins to specific APIs).
     * 
     *                             Please also note, that this address MUST NOT be associated
     *                             with any person because of GDPR. You MUST use company aliases.
     *                         Gets the value of the adminEmail property.
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
     * {@link java.lang.String }
     * 
     * 
     */
    public List<java.lang.String> getAdminEmail() {
        if (adminEmail == null) {
            adminEmail = new ArrayList<java.lang.String>();
        }
        return this.adminEmail;
    }

    /**
     * 
     *                             REQUIRED element. Name of the EWP host provider and optionally name
     *                             of the system software in parentheses. Must be provided in English.
     * 
     *                             E.g. "MUCI (USOS)", "Gent University (OASIS)", "Masaryk University (ISOIS)".
     *                         
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getAdminProvider() {
        return adminProvider;
    }

    /**
     * Legt den Wert der adminProvider-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setAdminProvider(java.lang.String value) {
        this.adminProvider = value;
    }

    /**
     * 
     *                             Additional information provided by administrators and/or developers of this
     *                             host for Registry maintainers and client developers. Must be provided in English.
     * 
     *                             E.g. "This host is a DEMO server. We plan to keep it online for testing.".
     *                         
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
     *     {@link Host.InstitutionsCovered }
     *     
     */
    public Host.InstitutionsCovered getInstitutionsCovered() {
        return institutionsCovered;
    }

    /**
     * Legt den Wert der institutionsCovered-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Host.InstitutionsCovered }
     *     
     */
    public void setInstitutionsCovered(Host.InstitutionsCovered value) {
        this.institutionsCovered = value;
    }

    /**
     * Ruft den Wert der clientCredentialsInUse-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Host.ClientCredentialsInUse }
     *     
     */
    public Host.ClientCredentialsInUse getClientCredentialsInUse() {
        return clientCredentialsInUse;
    }

    /**
     * Legt den Wert der clientCredentialsInUse-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Host.ClientCredentialsInUse }
     *     
     */
    public void setClientCredentialsInUse(Host.ClientCredentialsInUse value) {
        this.clientCredentialsInUse = value;
    }

    /**
     * Ruft den Wert der serverCredentialsInUse-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Host.ServerCredentialsInUse }
     *     
     */
    public Host.ServerCredentialsInUse getServerCredentialsInUse() {
        return serverCredentialsInUse;
    }

    /**
     * Legt den Wert der serverCredentialsInUse-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Host.ServerCredentialsInUse }
     *     
     */
    public void setServerCredentialsInUse(Host.ServerCredentialsInUse value) {
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
     *         &lt;element name="certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="rsa-public-key" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded" minOccurs="0"/&gt;
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

        @XmlElement(type = java.lang.String.class)
        @XmlJavaTypeAdapter(Adapter2 .class)
        @XmlSchemaType(name = "base64Binary")
        protected List<String> certificate;
        @XmlElement(name = "rsa-public-key", type = java.lang.String.class)
        @XmlJavaTypeAdapter(Adapter3 .class)
        @XmlSchemaType(name = "base64Binary")
        protected List<String> rsaPublicKey;

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
         * {@link java.lang.String }
         * 
         * 
         */
        public List<String> getCertificate() {
            if (certificate == null) {
                certificate = new ArrayList<String>();
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
         * {@link java.lang.String }
         * 
         * 
         */
        public List<String> getRsaPublicKey() {
            if (rsaPublicKey == null) {
                rsaPublicKey = new ArrayList<String>();
            }
            return this.rsaPublicKey;
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
     *         &lt;element ref="{https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1}hei" minOccurs="0"/&gt;
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
    public static class InstitutionsCovered
        implements Serializable
    {

        @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-registry/tree/stable-v1")
        protected Hei hei;

        /**
         * Ruft den Wert der hei-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Hei }
         *     
         */
        public Hei getHei() {
            return hei;
        }

        /**
         * Legt den Wert der hei-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Hei }
         *     
         */
        public void setHei(Hei value) {
            this.hei = value;
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
     *         &lt;element name="rsa-public-key" type="{http://www.w3.org/2001/XMLSchema}base64Binary" maxOccurs="unbounded" minOccurs="0"/&gt;
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

        @XmlElement(name = "rsa-public-key", type = java.lang.String.class)
        @XmlJavaTypeAdapter(Adapter1 .class)
        @XmlSchemaType(name = "base64Binary")
        protected List<String> rsaPublicKey;

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
         * {@link java.lang.String }
         * 
         * 
         */
        public List<String> getRsaPublicKey() {
            if (rsaPublicKey == null) {
                rsaPublicKey = new ArrayList<String>();
            }
            return this.rsaPublicKey;
        }

    }

}
