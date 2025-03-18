package eu.erasmuswithoutpaper.omobility.las.control;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class XMLGregorianCalendarAdapter extends XmlAdapter<String, XMLGregorianCalendar> {
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    private static final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public XMLGregorianCalendar unmarshal(String text) throws Exception {
        if (text == null || text.isEmpty()) return null;

        Date date;
        try {
            // Try parsing as full timestamp (with time and timezone)
            date = DATE_TIME_FORMAT.parse(text);
        } catch (ParseException e) {
            // If that fails, try parsing as a date-only format
            date = DATE_ONLY_FORMAT.parse(text);
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

    @Override
    public String marshal(XMLGregorianCalendar xmlCal) {
        if (xmlCal == null) return null;

        // Check if it has time components
        if (xmlCal.getHour() != 0 || xmlCal.getMinute() != 0 || xmlCal.getSecond() != 0) {
            return DATE_TIME_FORMAT.format(xmlCal.toGregorianCalendar().getTime());
        } else {
            return DATE_ONLY_FORMAT.format(xmlCal.toGregorianCalendar().getTime());
        }
    }
}
