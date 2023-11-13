//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2023.11.13 um 09:38:07 AM CET 
//


package eu.erasmuswithoutpaper.api.discovery;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<java.lang.String, String>
{


    public String unmarshal(java.lang.String value) {
        return new String(value);
    }

    public java.lang.String marshal(String value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
