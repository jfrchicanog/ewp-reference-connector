package eu.erasmuswithoutpaper.factsheet.entity;

public enum AccessibilityType {
	INFRASTRUCTURE, SERVICE;

    public static String displayName(AccessibilityType accType) {
        String displayName = accType.toString();
        
        displayName = displayName.charAt(0) + displayName.substring(1).toLowerCase();
        return displayName;
    }
}
