//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.06.21 um 08:46:06 AM CEST 
//


package eu.erasmuswithoutpaper.api.iias;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.specs.sec.intro.HttpSecurityOptions;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}ManifestApiEntryBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="http-security" type="{https://github.com/erasmus-without-paper/ewp-specs-sec-intro/tree/stable-v2}HttpSecurityOptions" minOccurs="0"/&gt;
 *         &lt;element name="get-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS"/&gt;
 *         &lt;element name="max-iia-ids" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="max-iia-codes" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="index-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS"/&gt;
 *         &lt;element name="stats-url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "httpSecurity",
    "getUrl",
    "maxIiaIds",
    "maxIiaCodes",
    "indexUrl",
    "statsUrl"
})
@XmlRootElement(name = "iias")
public class Iias
    extends ManifestApiEntryBase
    implements Serializable
{

    @XmlElement(name = "http-security")
    protected HttpSecurityOptions httpSecurity;
    @XmlElement(name = "get-url", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String getUrl;
    @XmlElement(name = "max-iia-ids", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger maxIiaIds;
    @XmlElement(name = "max-iia-codes", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger maxIiaCodes;
    @XmlElement(name = "index-url", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String indexUrl;
    @XmlElement(name = "stats-url")
    @XmlSchemaType(name = "anyURI")
    protected String statsUrl;

    /**
     * Ruft den Wert der httpSecurity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link HttpSecurityOptions }
     *     
     */
    public HttpSecurityOptions getHttpSecurity() {
        return httpSecurity;
    }

    /**
     * Legt den Wert der httpSecurity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link HttpSecurityOptions }
     *     
     */
    public void setHttpSecurity(HttpSecurityOptions value) {
        this.httpSecurity = value;
    }

    /**
     * Ruft den Wert der getUrl-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetUrl() {
        return getUrl;
    }

    /**
     * Legt den Wert der getUrl-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetUrl(String value) {
        this.getUrl = value;
    }

    /**
     * Ruft den Wert der maxIiaIds-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxIiaIds() {
        return maxIiaIds;
    }

    /**
     * Legt den Wert der maxIiaIds-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxIiaIds(BigInteger value) {
        this.maxIiaIds = value;
    }

    /**
     * Ruft den Wert der maxIiaCodes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxIiaCodes() {
        return maxIiaCodes;
    }

    /**
     * Legt den Wert der maxIiaCodes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxIiaCodes(BigInteger value) {
        this.maxIiaCodes = value;
    }

    /**
     * Ruft den Wert der indexUrl-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexUrl() {
        return indexUrl;
    }

    /**
     * Legt den Wert der indexUrl-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexUrl(String value) {
        this.indexUrl = value;
    }

    /**
     * Ruft den Wert der statsUrl-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatsUrl() {
        return statsUrl;
    }

    /**
     * Legt den Wert der statsUrl-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatsUrl(String value) {
        this.statsUrl = value;
    }

}
