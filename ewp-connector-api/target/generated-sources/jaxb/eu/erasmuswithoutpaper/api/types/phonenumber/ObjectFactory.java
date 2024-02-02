//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 05:15:30 PM CET 
//


package eu.erasmuswithoutpaper.api.types.phonenumber;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.types.phonenumber package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PhoneNumber_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1", "phone-number");
    private final static QName _FaxNumber_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1", "fax-number");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.types.phonenumber
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PhoneNumber }
     * 
     */
    public PhoneNumber createPhoneNumber() {
        return new PhoneNumber();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneNumber }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PhoneNumber }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1", name = "phone-number")
    public JAXBElement<PhoneNumber> createPhoneNumber(PhoneNumber value) {
        return new JAXBElement<PhoneNumber>(_PhoneNumber_QNAME, PhoneNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneNumber }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PhoneNumber }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-phonenumber/tree/stable-v1", name = "fax-number")
    public JAXBElement<PhoneNumber> createFaxNumber(PhoneNumber value) {
        return new JAXBElement<PhoneNumber>(_FaxNumber_QNAME, PhoneNumber.class, null, value);
    }

}
