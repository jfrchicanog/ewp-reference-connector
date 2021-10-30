
package eu.erasmuswithoutpaper.imobility.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum IMobilityStatus {
    PENDING("pending"), VERIFIED("verified"), REJECTED("rejected");
    
    private final String value;

    IMobilityStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
    
    public static String[] names() {
        IMobilityStatus[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
