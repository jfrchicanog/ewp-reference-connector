//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 09:03:38 AM CEST 
//


package eu.erasmuswithoutpaper.api.imobilities.endpoints;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für NominationStatus.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="NominationStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="pending"/&gt;
 *     &lt;enumeration value="verified"/&gt;
 *     &lt;enumeration value="rejected"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "NominationStatus")
@XmlEnum
public enum NominationStatus {


    /**
     * 
     *                         The nomination proposal has not yet been accepted nor rejected by the
     *                         receiving HEI.
     *                     
     * 
     */
    @XmlEnumValue("pending")
    PENDING("pending"),

    /**
     * 
     *                         The nomination was recognized by the receiving HEI as formally correct and
     *                         complying with cooperation conditions. Now it's time for settling formalities,
     *                         sending
     *                         student's documents, creating the first Learning Agreement, etc.
     *                     
     * 
     */
    @XmlEnumValue("verified")
    VERIFIED("verified"),

    /**
     * 
     *                         The nomination has been rejected by the receiving HEI.
     *                     
     * 
     */
    @XmlEnumValue("rejected")
    REJECTED("rejected");
    private final String value;

    NominationStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NominationStatus fromValue(String v) {
        for (NominationStatus c: NominationStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
