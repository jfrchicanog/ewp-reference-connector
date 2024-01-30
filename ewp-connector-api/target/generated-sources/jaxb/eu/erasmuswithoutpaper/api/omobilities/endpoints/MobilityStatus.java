//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.30 um 10:52:29 AM CET 
//


package eu.erasmuswithoutpaper.api.omobilities.endpoints;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MobilityStatus.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MobilityStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="nomination"/&gt;
 *     &lt;enumeration value="live"/&gt;
 *     &lt;enumeration value="recognized"/&gt;
 *     &lt;enumeration value="cancelled"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MobilityStatus")
@XmlEnum
public enum MobilityStatus {


    /**
     * 
     *                         The sending HEI has nominated the student for mobility. The proposal has not
     *                         yet been accepted nor rejected by the receiving HEI.
     * 
     *                         This is the default status with which mobility entities are first created.
     *                     
     * 
     */
    @XmlEnumValue("nomination")
    NOMINATION("nomination"),

    /**
     * 
     *                         The nomination has been accepted by the receiving HEI, and all initial
     *                         formalities have been settled (i.e. a first LA version has been signed). This
     *                         status doesn't usually change throughout the mobility.
     * 
     *                         While in this status, LA can still be modified (new revisions of it may be
     *                         created, and signed).
     *                     
     * 
     */
    @XmlEnumValue("live")
    LIVE("live"),

    /**
     * 
     *                         The student has returned from the mobility and all achievements have been
     *                         recognized as indicated on the `component-recognized` list.
     * 
     *                         At this point, the mobility SHOULD become read-only. The latest revision of it
     *                         SHOULD be approved by all parties (no subsequent draft revisions should exist).
     *                     
     * 
     */
    @XmlEnumValue("recognized")
    RECOGNIZED("recognized"),

    /**
     * 
     *                         The nomination has been cancelled (either by the student, or by one of the
     *                         partner HEIs).
     *                     
     * 
     */
    @XmlEnumValue("cancelled")
    CANCELLED("cancelled");
    private final String value;

    MobilityStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MobilityStatus fromValue(String v) {
        for (MobilityStatus c: MobilityStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
