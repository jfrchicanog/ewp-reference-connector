
package eu.erasmuswithoutpaper.omobility.las.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ApprovingParty {
	STUDENT("student"), SENDING_HEI("sending_hei"), RECEIVING_HEI("receiving_hei");
    
    private final String value;

    ApprovingParty(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
    
    public static String[] names() {
        ApprovingParty[] appParties = values();
        return Arrays.stream(appParties).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[appParties.length]);
    }
}
