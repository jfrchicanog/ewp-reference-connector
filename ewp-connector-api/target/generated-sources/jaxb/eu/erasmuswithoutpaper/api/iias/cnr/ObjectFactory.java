//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.02.02 um 05:51:21 PM CET 
//


package eu.erasmuswithoutpaper.api.iias.cnr;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import eu.erasmuswithoutpaper.api.architecture.Empty;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.iias.cnr package. 
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

    private final static QName _IiaCnrResponse_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-api-iia-cnr/tree/stable-v2", "iia-cnr-response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.iias.cnr
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IiaCnr }
     * 
     */
    public IiaCnr createIiaCnr() {
        return new IiaCnr();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Empty }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Empty }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-iia-cnr/tree/stable-v2", name = "iia-cnr-response")
    public JAXBElement<Empty> createIiaCnrResponse(Empty value) {
        return new JAXBElement<Empty>(_IiaCnrResponse_QNAME, Empty.class, null, value);
    }

}
