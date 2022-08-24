
package eu.erasmuswithoutpaper.omobility.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MobilityActivityType {
	STUDENT_STUDIES("student-studies"), STUDENT_TRAINEESHIPS("student-traineeships");
    
    private final String value;

    MobilityActivityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
    
    public static String[] names() {
        MobilityActivityType[] statuses = values();
        return Arrays.stream(statuses).map(s -> s.name()).collect(Collectors.toList()).toArray(new String[statuses.length]);
    }
}
