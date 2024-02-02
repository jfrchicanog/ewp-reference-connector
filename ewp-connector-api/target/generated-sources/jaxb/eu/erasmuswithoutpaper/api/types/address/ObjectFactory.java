//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 04:54:23 PM CET 
//


package eu.erasmuswithoutpaper.api.types.address;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.types.address package. 
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

    private final static QName _StreetAddress_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1", "street-address");
    private final static QName _MailingAddress_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1", "mailing-address");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.types.address
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FlexibleAddress }
     * 
     */
    public FlexibleAddress createFlexibleAddress() {
        return new FlexibleAddress();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlexibleAddress }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FlexibleAddress }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1", name = "street-address")
    public JAXBElement<FlexibleAddress> createStreetAddress(FlexibleAddress value) {
        return new JAXBElement<FlexibleAddress>(_StreetAddress_QNAME, FlexibleAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlexibleAddress }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FlexibleAddress }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-address/tree/stable-v1", name = "mailing-address")
    public JAXBElement<FlexibleAddress> createMailingAddress(FlexibleAddress value) {
        return new JAXBElement<FlexibleAddress>(_MailingAddress_QNAME, FlexibleAddress.class, null, value);
    }

}
