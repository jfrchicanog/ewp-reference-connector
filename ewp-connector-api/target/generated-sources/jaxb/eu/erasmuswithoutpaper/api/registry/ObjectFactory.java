//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.09.21 um 09:03:38 AM CEST 
//


package eu.erasmuswithoutpaper.api.registry;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.registry package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.registry
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Catalogue }
     * 
     */
    public Catalogue createCatalogue() {
        return new Catalogue();
    }

    /**
     * Create an instance of {@link Catalogue.Binaries }
     * 
     */
    public Catalogue.Binaries createCatalogueBinaries() {
        return new Catalogue.Binaries();
    }

    /**
     * Create an instance of {@link Catalogue.Host }
     * 
     */
    public Catalogue.Host createCatalogueHost() {
        return new Catalogue.Host();
    }

    /**
     * Create an instance of {@link Catalogue.Host.ServerCredentialsInUse }
     * 
     */
    public Catalogue.Host.ServerCredentialsInUse createCatalogueHostServerCredentialsInUse() {
        return new Catalogue.Host.ServerCredentialsInUse();
    }

    /**
     * Create an instance of {@link Catalogue.Host.ClientCredentialsInUse }
     * 
     */
    public Catalogue.Host.ClientCredentialsInUse createCatalogueHostClientCredentialsInUse() {
        return new Catalogue.Host.ClientCredentialsInUse();
    }

    /**
     * Create an instance of {@link ApisImplemented }
     * 
     */
    public ApisImplemented createApisImplemented() {
        return new ApisImplemented();
    }

    /**
     * Create an instance of {@link Catalogue.Institutions }
     * 
     */
    public Catalogue.Institutions createCatalogueInstitutions() {
        return new Catalogue.Institutions();
    }

    /**
     * Create an instance of {@link Hei }
     * 
     */
    public Hei createHei() {
        return new Hei();
    }

    /**
     * Create an instance of {@link OtherHeiId }
     * 
     */
    public OtherHeiId createOtherHeiId() {
        return new OtherHeiId();
    }

    /**
     * Create an instance of {@link Registry }
     * 
     */
    public Registry createRegistry() {
        return new Registry();
    }

    /**
     * Create an instance of {@link Catalogue.Binaries.RsaPublicKey }
     * 
     */
    public Catalogue.Binaries.RsaPublicKey createCatalogueBinariesRsaPublicKey() {
        return new Catalogue.Binaries.RsaPublicKey();
    }

    /**
     * Create an instance of {@link Catalogue.Host.InstitutionsCovered }
     * 
     */
    public Catalogue.Host.InstitutionsCovered createCatalogueHostInstitutionsCovered() {
        return new Catalogue.Host.InstitutionsCovered();
    }

    /**
     * Create an instance of {@link Catalogue.Host.ServerCredentialsInUse.RsaPublicKey }
     * 
     */
    public Catalogue.Host.ServerCredentialsInUse.RsaPublicKey createCatalogueHostServerCredentialsInUseRsaPublicKey() {
        return new Catalogue.Host.ServerCredentialsInUse.RsaPublicKey();
    }

    /**
     * Create an instance of {@link Catalogue.Host.ClientCredentialsInUse.Certificate }
     * 
     */
    public Catalogue.Host.ClientCredentialsInUse.Certificate createCatalogueHostClientCredentialsInUseCertificate() {
        return new Catalogue.Host.ClientCredentialsInUse.Certificate();
    }

    /**
     * Create an instance of {@link Catalogue.Host.ClientCredentialsInUse.RsaPublicKey }
     * 
     */
    public Catalogue.Host.ClientCredentialsInUse.RsaPublicKey createCatalogueHostClientCredentialsInUseRsaPublicKey() {
        return new Catalogue.Host.ClientCredentialsInUse.RsaPublicKey();
    }

}
