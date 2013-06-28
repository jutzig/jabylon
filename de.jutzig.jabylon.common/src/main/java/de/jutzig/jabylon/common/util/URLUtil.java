package de.jutzig.jabylon.common.util;

public class URLUtil {

    public static String escapeToIdAttribute(String value) {
        return value.replaceAll("\\W", "_");
    }

}
