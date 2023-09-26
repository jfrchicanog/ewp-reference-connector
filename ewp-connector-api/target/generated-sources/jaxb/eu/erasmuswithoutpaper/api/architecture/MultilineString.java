//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.26 um 09:04:28 AM CEST 
//


package eu.erasmuswithoutpaper.api.architecture;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 *                 This is very similar to a regular xs:string, but whenever this type is used it
 *                 indicates that the content MAY contain basic whitespace formatting, such us
 *                 line breaks and double line breaks (for splitting paragraphs). The values still
 *                 MUST be in plaintext though (no HTML is allowed).
 * 
 *                 Clients which process data of this type SHOULD respect line breaks when they
 *                 display the data to the end user (e.g. replace CRs and LFs with <br>s
 *                 when rendering to HTML).
 *             
 * 
 * <p>Java-Klasse für MultilineString complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MultilineString"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultilineString", propOrder = {
    "value"
})
@XmlSeeAlso({
    MultilineStringWithOptionalLang.class
})
public class MultilineString
    implements Serializable
{

    @XmlValue
    protected String value;

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

}
