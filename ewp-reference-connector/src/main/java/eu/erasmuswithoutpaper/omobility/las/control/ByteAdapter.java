package eu.erasmuswithoutpaper.omobility.las.control;

import org.apache.johnzon.mapper.Adapter;
import org.apache.johnzon.mapper.Converter;

public class ByteAdapter implements Adapter<Byte, String> {

    @Override
    public Byte to(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        return Byte.parseByte(value); // Convert String to byte
    }

    @Override
    public String from(Byte value) {
        if (value == null) return null;
        return Byte.toString(value); // Convert byte to String
    }
}
