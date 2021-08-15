package eu.erasmuswithoutpaper.omobility.las.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;

public class PhotoURL {

	 @XmlValue
     protected String value;
     @XmlAttribute(name = "size-px")
     protected String sizePx;
     @XmlAttribute(name = "public")
     protected Boolean _public;
     @XmlAttribute(name = "date")
     @XmlSchemaType(name = "date")
     protected XMLGregorianCalendar date;
}
