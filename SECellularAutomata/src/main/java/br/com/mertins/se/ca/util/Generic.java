package br.com.mertins.se.ca.util;

/**
 *
 * @author mertins
 */
public class Generic {

    public static String removeExtensionFile(String name) {
        int pos = name == null ? -1 : name.indexOf(".");
        return pos < 0 ? name : name.substring(0, pos);
    }
    
}
