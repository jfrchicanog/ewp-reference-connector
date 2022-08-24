
package eu.erasmuswithoutpaper.omobility.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MobilityActivityAttributes {
	LONG_TERM("long-term"), SHORT_TERM_BLENDED("short-term-blended"), SHORT_TERM_DOCTORAL("short-term-doctoral");
    
    private final String value;

    MobilityActivityAttributes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
    
    public static String[] names() {
        MobilityActivityAttributes[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
