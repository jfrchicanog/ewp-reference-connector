package eu.erasmuswithoutpaper.omobility.las.entity;

import java.sql.Date;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;

public class YearMonthConverter implements AttributeConverter<YearMonth, java.sql.Date>{

	@Override
	public Date convertToDatabaseColumn(YearMonth value) {
		if (value != null) {
            return java.sql.Date.valueOf(value.atDay(1));
        }
		
        return null;
	}

	@Override
	public YearMonth convertToEntityAttribute(Date dbValue) {
		if (dbValue != null) {
            return YearMonth.from(Instant.ofEpochMilli(dbValue.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
        }
		
        return null;
	}

	
}
