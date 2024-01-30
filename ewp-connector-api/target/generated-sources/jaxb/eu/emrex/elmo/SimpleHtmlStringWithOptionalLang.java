//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 10:01:59 AM CET 
//


package eu.emrex.elmo;

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
 * "Simple" (but still dangerous) HTML string. This type is very different from
 *                 PlaintextMultilineStringWithOptionalLang, because it contains HTML, not
 *                 plain-text.
 * 
 * 
 *                 Notes for SERVER implementers
 *                 =============================
 * 
 *                 There's the word "Simple" in the name of this type. This is because servers are
 *                 RECOMMENDED to use only the basic HTML tags for formatting (paragraphs,
 *                 italics, emphasis, lists, etc.). You SHOULD avoid inserting more complex
 *                 content (such as tables, images or h1..h6 headers), because clients might not
 *                 be able to display these.
 * 
 *                 Also note, that this type is usually used in contexts where the content is also
 *                 provided in non-HTML form (such as the `descriptionHtml` and `description`
 *                 pair). Therefore, many clients will simply ignore the HTML values, and use the
 *                 plain-text counterparts instead.
 * 
 * 
 *                 Notes for CLIENT implementers
 *                 =============================
 * 
 *                 DO NOT TRUST THAT THIS MARKUP IS SAFE. If you decide to display this HTML
 *                 content, then you SHOULD sanitize it. Otherwise you will leave your system
 *                 vulnerable to various attacks, including XSS. If you're not sure how
 *                 sanitization works, then you SHOULD NOT display this content in your
 *                 application. Use the plaintext counterparts instead (if available).
 * 
 *                 Also note, that you SHOULD NOT apply `nl2br`-like funtions on this input (the
 *                 ones you do apply in the PlaintextMultilineStringWithOptionalLang format above).
 *                 It's up to the server to make sure that its HTML is valid (properly split into
 *                 paragraphs). You may however attempt to detect and fix HTML errors, if this
 *                 HTML seems broken.
 *             
 * 
 * <p>Java-Klasse für SimpleHtmlStringWithOptionalLang complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SimpleHtmlStringWithOptionalLang"&gt;
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
@XmlType(name = "SimpleHtmlStringWithOptionalLang", propOrder = {
    "value"
})
public class SimpleHtmlStringWithOptionalLang
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
     * Ruft den Wert der lang-Eigenschaft ab.
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
