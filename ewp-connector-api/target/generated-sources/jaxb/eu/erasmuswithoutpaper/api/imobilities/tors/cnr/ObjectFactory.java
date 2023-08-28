//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.08.28 um 09:13:54 AM CEST 
//


package eu.erasmuswithoutpaper.api.imobilities.tors.cnr;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import eu.erasmuswithoutpaper.api.architecture.Empty;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.imobilities.tors.cnr package. 
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

    private final static QName _ImobilityTorCnrResponse_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-api-imobility-tor-cnr/tree/stable-v1", "imobility-tor-cnr-response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.imobilities.tors.cnr
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImobilityTorCnr }
     * 
     */
    public ImobilityTorCnr createImobilityTorCnr() {
        return new ImobilityTorCnr();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Empty }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Empty }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-api-imobility-tor-cnr/tree/stable-v1", name = "imobility-tor-cnr-response")
    public JAXBElement<Empty> createImobilityTorCnrResponse(Empty value) {
        return new JAXBElement<Empty>(_ImobilityTorCnrResponse_QNAME, Empty.class, null, value);
    }

}
