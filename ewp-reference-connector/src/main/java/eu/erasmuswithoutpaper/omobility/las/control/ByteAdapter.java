package eu.erasmuswithoutpaper.omobility.las.control;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ByteAdapter extends XmlAdapter<String, Byte> {

    @Override
    public Byte unmarshal(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) return null;
        return Byte.parseByte(value); // Convert String to byte
    }

    @Override
    public String marshal(Byte value) throws Exception {
        if (value == null) return null;
        return Byte.toString(value); // Convert byte to String
    }
}
