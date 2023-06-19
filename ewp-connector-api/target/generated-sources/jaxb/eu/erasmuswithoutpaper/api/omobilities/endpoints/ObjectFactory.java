//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.06.19 um 09:21:53 AM CEST 
//


package eu.erasmuswithoutpaper.api.omobilities.endpoints;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.omobilities.endpoints package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.omobilities.endpoints
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StudentMobility }
     * 
     */
    public StudentMobility createStudentMobility() {
        return new StudentMobility();
    }

    /**
     * Create an instance of {@link StudentMobility.Student }
     * 
     */
    public StudentMobility.Student createStudentMobilityStudent() {
        return new StudentMobility.Student();
    }

    /**
     * Create an instance of {@link OmobilitiesIndexResponse }
     * 
     */
    public OmobilitiesIndexResponse createOmobilitiesIndexResponse() {
        return new OmobilitiesIndexResponse();
    }

    /**
     * Create an instance of {@link OmobilitiesGetResponse }
     * 
     */
    public OmobilitiesGetResponse createOmobilitiesGetResponse() {
        return new OmobilitiesGetResponse();
    }

    /**
     * Create an instance of {@link StudentMobility.SendingHei }
     * 
     */
    public StudentMobility.SendingHei createStudentMobilitySendingHei() {
        return new StudentMobility.SendingHei();
    }

    /**
     * Create an instance of {@link StudentMobility.ReceivingHei }
     * 
     */
    public StudentMobility.ReceivingHei createStudentMobilityReceivingHei() {
        return new StudentMobility.ReceivingHei();
    }

    /**
     * Create an instance of {@link StudentMobility.NomineeLanguageSkill }
     * 
     */
    public StudentMobility.NomineeLanguageSkill createStudentMobilityNomineeLanguageSkill() {
        return new StudentMobility.NomineeLanguageSkill();
    }

    /**
     * Create an instance of {@link StudentMobility.AdditionalRequirement }
     * 
     */
    public StudentMobility.AdditionalRequirement createStudentMobilityAdditionalRequirement() {
        return new StudentMobility.AdditionalRequirement();
    }

    /**
     * Create an instance of {@link StudentMobility.Student.PhotoUrl }
     * 
     */
    public StudentMobility.Student.PhotoUrl createStudentMobilityStudentPhotoUrl() {
        return new StudentMobility.Student.PhotoUrl();
    }

}
