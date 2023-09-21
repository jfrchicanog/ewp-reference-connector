//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 09:14:25 AM CEST 
//


package eu.erasmuswithoutpaper.api.architecture;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.architecture package. 
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

    private final static QName _AdminEmail_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", "admin-email");
    private final static QName _AdminProvider_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", "admin-provider");
    private final static QName _AdminNotes_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", "admin-notes");
    private final static QName _SuccessUserMessage_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", "success-user-message");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.architecture
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MultilineString }
     * 
     */
    public MultilineString createMultilineString() {
        return new MultilineString();
    }

    /**
     * Create an instance of {@link ErrorResponse }
     * 
     */
    public ErrorResponse createErrorResponse() {
        return new ErrorResponse();
    }

    /**
     * Create an instance of {@link MultilineStringWithOptionalLang }
     * 
     */
    public MultilineStringWithOptionalLang createMultilineStringWithOptionalLang() {
        return new MultilineStringWithOptionalLang();
    }

    /**
     * Create an instance of {@link StringWithOptionalLang }
     * 
     */
    public StringWithOptionalLang createStringWithOptionalLang() {
        return new StringWithOptionalLang();
    }

    /**
     * Create an instance of {@link Empty }
     * 
     */
    public Empty createEmpty() {
        return new Empty();
    }

    /**
     * Create an instance of {@link HTTPWithOptionalLang }
     * 
     */
    public HTTPWithOptionalLang createHTTPWithOptionalLang() {
        return new HTTPWithOptionalLang();
    }

    /**
     * Create an instance of {@link ManifestApiEntryBase }
     * 
     */
    public ManifestApiEntryBase createManifestApiEntryBase() {
        return new ManifestApiEntryBase();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", name = "admin-email")
    public JAXBElement<String> createAdminEmail(String value) {
        return new JAXBElement<String>(_AdminEmail_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", name = "admin-provider")
    public JAXBElement<String> createAdminProvider(String value) {
        return new JAXBElement<String>(_AdminProvider_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultilineString }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MultilineString }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", name = "admin-notes")
    public JAXBElement<MultilineString> createAdminNotes(MultilineString value) {
        return new JAXBElement<MultilineString>(_AdminNotes_QNAME, MultilineString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultilineStringWithOptionalLang }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MultilineStringWithOptionalLang }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-architecture/blob/stable-v1/common-types.xsd", name = "success-user-message")
    public JAXBElement<MultilineStringWithOptionalLang> createSuccessUserMessage(MultilineStringWithOptionalLang value) {
        return new JAXBElement<MultilineStringWithOptionalLang>(_SuccessUserMessage_QNAME, MultilineStringWithOptionalLang.class, null, value);
    }

}
