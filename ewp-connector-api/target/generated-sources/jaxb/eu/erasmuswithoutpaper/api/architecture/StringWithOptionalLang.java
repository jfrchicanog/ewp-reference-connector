//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.09 um 08:55:56 PM CET 
//


package eu.erasmuswithoutpaper.api.architecture;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 *                 A string with an optional (but RECOMMENDED) xml:lang attribute. It is used in
 *                 places where a name of some entity can be provided in multiple languages.
 *             
 * 
 * <p>Java-Klasse für StringWithOptionalLang complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="StringWithOptionalLang"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StringWithOptionalLang", propOrder = {
    "value"
})
public class StringWithOptionalLang
    implements Serializable
{

    @XmlValue
    protected String value;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String lang;

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
     * 
     *                             A note for client developers: Keep in mind, that `xml:lang` is of the
     *                             `xs:language` type. This means that it MAY contain values such as `en`, but
     *                             also `en-US`, `ar-Cyrl-CO`, or `tlh-Kore-AQ-fonipa`. Most often, you will
     *                             need to parse only the first component, but in some cases other components
     *                             might also be pretty useful (like the script subtag - "Latn", "Cyrl", etc.).
     * 
     *                             The `xml:language` datatype is defined here:
     *                             https://www.ietf.org/rfc/bcp/bcp47.txt
     * 
     *                             You can also discuss this tag in context of EWP here:
     *                             https://github.com/erasmus-without-paper/ewp-specs-architecture/issues/11
     *                         
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Legt den Wert der lang-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

}
