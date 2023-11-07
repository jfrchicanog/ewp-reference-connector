//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.07 um 11:32:52 AM CET 
//


package eu.erasmuswithoutpaper.api.specs.sec.intro;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.specs.sec.intro package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.specs.sec.intro
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HttpSecurityOptions }
     * 
     */
    public HttpSecurityOptions createHttpSecurityOptions() {
        return new HttpSecurityOptions();
    }

    /**
     * Create an instance of {@link HttpSecurityOptions.ClientAuthMethods }
     * 
     */
    public HttpSecurityOptions.ClientAuthMethods createHttpSecurityOptionsClientAuthMethods() {
        return new HttpSecurityOptions.ClientAuthMethods();
    }

    /**
     * Create an instance of {@link HttpSecurityOptions.ServerAuthMethods }
     * 
     */
    public HttpSecurityOptions.ServerAuthMethods createHttpSecurityOptionsServerAuthMethods() {
        return new HttpSecurityOptions.ServerAuthMethods();
    }

    /**
     * Create an instance of {@link HttpSecurityOptions.RequestEncryptionMethods }
     * 
     */
    public HttpSecurityOptions.RequestEncryptionMethods createHttpSecurityOptionsRequestEncryptionMethods() {
        return new HttpSecurityOptions.RequestEncryptionMethods();
    }

    /**
     * Create an instance of {@link HttpSecurityOptions.ResponseEncryptionMethods }
     * 
     */
    public HttpSecurityOptions.ResponseEncryptionMethods createHttpSecurityOptionsResponseEncryptionMethods() {
        return new HttpSecurityOptions.ResponseEncryptionMethods();
    }

}
