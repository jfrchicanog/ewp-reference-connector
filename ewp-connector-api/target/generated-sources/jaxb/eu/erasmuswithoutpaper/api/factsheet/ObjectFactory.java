//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.01.26 um 01:04:23 PM CET 
//


package eu.erasmuswithoutpaper.api.factsheet;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.erasmuswithoutpaper.api.factsheet package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.erasmuswithoutpaper.api.factsheet
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FactsheetResponse }
     * 
     */
    public FactsheetResponse createFactsheetResponse() {
        return new FactsheetResponse();
    }

    /**
     * Create an instance of {@link FactsheetResponse.Factsheet }
     * 
     */
    public FactsheetResponse.Factsheet createFactsheetResponseFactsheet() {
        return new FactsheetResponse.Factsheet();
    }

    /**
     * Create an instance of {@link InformationEntry }
     * 
     */
    public InformationEntry createInformationEntry() {
        return new InformationEntry();
    }

    /**
     * Create an instance of {@link CalendarEntry }
     * 
     */
    public CalendarEntry createCalendarEntry() {
        return new CalendarEntry();
    }

    /**
     * Create an instance of {@link eu.erasmuswithoutpaper.api.factsheet.Factsheet }
     * 
     */
    public eu.erasmuswithoutpaper.api.factsheet.Factsheet createFactsheet() {
        return new eu.erasmuswithoutpaper.api.factsheet.Factsheet();
    }

    /**
     * Create an instance of {@link FactsheetResponse.Factsheet.Calendar }
     * 
     */
    public FactsheetResponse.Factsheet.Calendar createFactsheetResponseFactsheetCalendar() {
        return new FactsheetResponse.Factsheet.Calendar();
    }

    /**
     * Create an instance of {@link FactsheetResponse.Factsheet.AdditionalRequirement }
     * 
     */
    public FactsheetResponse.Factsheet.AdditionalRequirement createFactsheetResponseFactsheetAdditionalRequirement() {
        return new FactsheetResponse.Factsheet.AdditionalRequirement();
    }

    /**
     * Create an instance of {@link FactsheetResponse.Factsheet.Accessibility }
     * 
     */
    public FactsheetResponse.Factsheet.Accessibility createFactsheetResponseFactsheetAccessibility() {
        return new FactsheetResponse.Factsheet.Accessibility();
    }

    /**
     * Create an instance of {@link FactsheetResponse.Factsheet.AdditionalInfo }
     * 
     */
    public FactsheetResponse.Factsheet.AdditionalInfo createFactsheetResponseFactsheetAdditionalInfo() {
        return new FactsheetResponse.Factsheet.AdditionalInfo();
    }

}
