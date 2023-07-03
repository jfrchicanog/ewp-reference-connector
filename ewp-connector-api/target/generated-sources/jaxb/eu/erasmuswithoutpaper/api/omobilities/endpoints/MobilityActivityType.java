//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.07.03 um 02:04:03 PM CEST 
//


package eu.erasmuswithoutpaper.api.omobilities.endpoints;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MobilityActivityType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MobilityActivityType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="student-studies"/&gt;
 *     &lt;enumeration value="student-traineeships"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MobilityActivityType")
@XmlEnum
public enum MobilityActivityType {


    /**
     * 
     *                         Student Mobility for Studies (SMS).
     *                     
     * 
     */
    @XmlEnumValue("student-studies")
    STUDENT_STUDIES("student-studies"),

    /**
     * 
     *                         Student Mobility for Traineeships (SMT).
     *                     
     * 
     */
    @XmlEnumValue("student-traineeships")
    STUDENT_TRAINEESHIPS("student-traineeships");
    private final String value;

    MobilityActivityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MobilityActivityType fromValue(String v) {
        for (MobilityActivityType c: MobilityActivityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
