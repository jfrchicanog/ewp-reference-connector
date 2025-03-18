package eu.erasmuswithoutpaper.omobility.las.control;

import org.apache.johnzon.mapper.Converter;

public class ByteConverter implements Converter<Byte> {

    @Override
    public String toString(Byte value) {
        if (value == null) return null;
        return Byte.toString(value); // Convert byte to String
    }

    @Override
    public Byte fromString(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        return Byte.parseByte(value); // Convert String to byte
    }
}
