//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.09 um 01:16:35 PM CET 
//


package eu.erasmuswithoutpaper.api.types.academicterm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.types.academicterm package. 
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

    private final static QName _AcademicTerm_QNAME = new QName("https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1", "academic-term");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.types.academicterm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AcademicTerm }
     * 
     */
    public AcademicTerm createAcademicTerm() {
        return new AcademicTerm();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcademicTerm }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AcademicTerm }{@code >}
     */
    @XmlElementDecl(namespace = "https://github.com/erasmus-without-paper/ewp-specs-types-academic-term/tree/stable-v1", name = "academic-term")
    public JAXBElement<AcademicTerm> createAcademicTerm(AcademicTerm value) {
        return new JAXBElement<AcademicTerm>(_AcademicTerm_QNAME, AcademicTerm.class, null, value);
    }

}
