//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 01:00:17 PM CEST 
//


package eu.erasmuswithoutpaper.api.omobilities.endpoints;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MobilityActivityAttributes.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MobilityActivityAttributes"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="long-term"/&gt;
 *     &lt;enumeration value="short-term-blended"/&gt;
 *     &lt;enumeration value="short-term-doctoral"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MobilityActivityAttributes")
@XmlEnum
public enum MobilityActivityAttributes {


    /**
     * 
     *                         Long-term physical mobility (with an optional virtual component).
     *                     
     * 
     */
    @XmlEnumValue("long-term")
    LONG_TERM("long-term"),

    /**
     * 
     *                         Short term blended mobility (with a compulsory virtual component).
     *                     
     * 
     */
    @XmlEnumValue("short-term-blended")
    SHORT_TERM_BLENDED("short-term-blended"),

    /**
     * 
     *                         Short term doctoral mobility (with an optional virtual component).
     *                     
     * 
     */
    @XmlEnumValue("short-term-doctoral")
    SHORT_TERM_DOCTORAL("short-term-doctoral");
    private final String value;

    MobilityActivityAttributes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MobilityActivityAttributes fromValue(String v) {
        for (MobilityActivityAttributes c: MobilityActivityAttributes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
