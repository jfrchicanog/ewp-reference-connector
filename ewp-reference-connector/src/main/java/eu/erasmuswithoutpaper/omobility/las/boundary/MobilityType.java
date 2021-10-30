package eu.erasmuswithoutpaper.omobility.las.boundary;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MobilityType {

		BLENDED("blended"), DOCTORAL("doctoral"), SEMESTRE("semestre");
	    
	    private final String value;

	    MobilityType(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }
	    
	    public static String[] names() {
	    	MobilityType[] appParties = values();
	        return Arrays.stream(appParties).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[appParties.length]);
	    }
}
