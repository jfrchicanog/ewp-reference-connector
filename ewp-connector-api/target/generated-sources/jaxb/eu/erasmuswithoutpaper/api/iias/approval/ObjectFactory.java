//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.07.03 um 10:20:55 AM CEST 
//


package eu.erasmuswithoutpaper.api.iias.approval;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.iias.approval package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.iias.approval
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IiasApprovalResponse }
     * 
     */
    public IiasApprovalResponse createIiasApprovalResponse() {
        return new IiasApprovalResponse();
    }

    /**
     * Create an instance of {@link IiasApprovalResponse.Approval }
     * 
     */
    public IiasApprovalResponse.Approval createIiasApprovalResponseApproval() {
        return new IiasApprovalResponse.Approval();
    }

    /**
     * Create an instance of {@link IiasApproval }
     * 
     */
    public IiasApproval createIiasApproval() {
        return new IiasApproval();
    }

}