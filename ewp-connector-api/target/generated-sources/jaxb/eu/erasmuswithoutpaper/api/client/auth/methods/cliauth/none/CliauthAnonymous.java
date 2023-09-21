//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 10:00:51 AM CEST 
//


package eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.none;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 This element uniquely identifies the anonymous client authentication method, as
 *                 described here:
 * 
 *                 https://github.com/erasmus-without-paper/ewp-specs-sec-cliauth-none
 * 
 *                 It can be used in various contexts, whenever someone needs to identify this
 *                 particular method of *client authentication*. In particular, it is often seen
 *                 together with `HttpSecurityOptions` data type described here:
 * 
 *                 https://github.com/erasmus-without-paper/ewp-specs-sec-intro/blob/stable-v2/schema.xsd
 *             
 * 
 * <p>Java-Klasse für anonymous element declaration.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;element name="anonymous"&gt;
 *   &lt;complexType&gt;
 *     &lt;complexContent&gt;
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *         &lt;sequence&gt;
 *         &lt;/sequence&gt;
 *       &lt;/restriction&gt;
 *     &lt;/complexContent&gt;
 *   &lt;/complexType&gt;
 * &lt;/element&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "anonymous")
public class CliauthAnonymous
    implements Serializable
{


}
