//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.07.03 um 02:12:34 PM CEST 
//


package eu.erasmuswithoutpaper.api.institutions;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.institutions package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.institutions
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InstitutionsResponse }
     * 
     */
    public InstitutionsResponse createInstitutionsResponse() {
        return new InstitutionsResponse();
    }

    /**
     * Create an instance of {@link InstitutionsResponse.Hei }
     * 
     */
    public InstitutionsResponse.Hei createInstitutionsResponseHei() {
        return new InstitutionsResponse.Hei();
    }

    /**
     * Create an instance of {@link Institutions }
     * 
     */
    public Institutions createInstitutions() {
        return new Institutions();
    }

}
