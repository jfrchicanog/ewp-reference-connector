//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 12:28:50 PM CEST 
//


package eu.erasmuswithoutpaper.api.factsheet;

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
 *         &lt;element name="url" type="{https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd}HTTPS"/&gt;
 *         &lt;element name="max-hei-ids" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
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
    "url",
    "maxHeiIds"
})
@XmlRootElement(name = "factsheet", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/blob/stable-v1/manifest-entry.xsd")
public class Factsheet
    extends ManifestApiEntryBase
    implements Serializable
{

    @XmlElement(name = "http-security", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/blob/stable-v1/manifest-entry.xsd")
    protected HttpSecurityOptions httpSecurity;
    @XmlElement(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/blob/stable-v1/manifest-entry.xsd", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String url;
    @XmlElement(name = "max-hei-ids", namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-factsheet/blob/stable-v1/manifest-entry.xsd", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger maxHeiIds;

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
     * Ruft den Wert der maxHeiIds-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxHeiIds() {
        return maxHeiIds;
    }

    /**
     * Legt den Wert der maxHeiIds-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxHeiIds(BigInteger value) {
        this.maxHeiIds = value;
    }

}
