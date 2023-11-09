//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.09 um 08:55:56 PM CET 
//


package eu.erasmuswithoutpaper.api.discovery;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.discovery package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.discovery
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Host }
     * 
     */
    public Host createHost() {
        return new Host();
    }

    /**
     * Create an instance of {@link Manifest }
     * 
     */
    public Manifest createManifest() {
        return new Manifest();
    }

    /**
     * Create an instance of {@link Host.InstitutionsCovered }
     * 
     */
    public Host.InstitutionsCovered createHostInstitutionsCovered() {
        return new Host.InstitutionsCovered();
    }

    /**
     * Create an instance of {@link Host.ClientCredentialsInUse }
     * 
     */
    public Host.ClientCredentialsInUse createHostClientCredentialsInUse() {
        return new Host.ClientCredentialsInUse();
    }

    /**
     * Create an instance of {@link Host.ServerCredentialsInUse }
     * 
     */
    public Host.ServerCredentialsInUse createHostServerCredentialsInUse() {
        return new Host.ServerCredentialsInUse();
    }

    /**
     * Create an instance of {@link Discovery }
     * 
     */
    public Discovery createDiscovery() {
        return new Discovery();
    }

}
