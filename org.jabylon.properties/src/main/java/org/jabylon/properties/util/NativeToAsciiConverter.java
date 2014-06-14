/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

//Taken from org.eclipse.babel.core
//org.eclipse.babel.core.message.resource.ser.PropertiesSerializer
//org.eclipse.babel.core.message.resource.ser.PropertiesDeserializer
public class NativeToAsciiConverter {

    /** A table of hex digits */
    private static final char[] HEX_DIGITS = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };

    /**
     * Converts encoded &#92;uxxxx to unicode chars
     * and changes special saved chars to their original forms
     * @param str the string to convert
     * @return converted string
     * @see java.util.Properties
     */
    public static String convertEncodedToUnicode(String str) {
        char aChar;
        int len = str.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len;) {
            aChar = str.charAt(x++);
            if (aChar == '\\' && x + 1 <= len) {
                aChar = str.charAt(x++);
                if (aChar == 'u' && x + 4 <= len) {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = str.charAt(x++);
                        switch (aChar) {
                        case '0': case '1': case '2': case '3': case '4':
                        case '5': case '6': case '7': case '8': case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a': case 'b': case 'c':
                        case 'd': case 'e': case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A': case 'B': case 'C':
                        case 'D': case 'E': case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            value = aChar;
                            System.err.println(
                                    "PropertiesDeserializer: " //$NON-NLS-1$
                                  + "bad character " //$NON-NLS-1$
                                  + "encoding for string:" //$NON-NLS-1$
                                  + str);
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    } else if (aChar == 'u') {
                        outBuffer.append("\\"); //$NON-NLS-1$
                    } else {
                    	// might be an escaped special char, we mustn't omit it
                    	//see https://github.com/jutzig/jabylon/issues/212
                    	outBuffer.append("\\"); //$NON-NLS-1$
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }



    /**
     * Converts unicodes to encoded &#92;uxxxx.
     * @param str string to convert
     * @return converted string
     * @see java.util.Properties
     */
    public static String convertUnicodeToEncoded(String str, boolean lowerCase) {
        int len = str.length();
        StringBuffer outBuffer = new StringBuffer(len * 2);

        for (int x = 0; x < len; x++) {
            char aChar = str.charAt(x);
            if(aChar=='\n' || aChar=='\r') //leave the newlines alone
                outBuffer.append(aChar);
            else if ((aChar < 0x0020) || (aChar > 0x007e)) {
                outBuffer.append('\\');
                outBuffer.append('u');
                outBuffer.append(toHex((aChar >> 12) & 0xF, lowerCase));
                outBuffer.append(toHex((aChar >> 8) & 0xF, lowerCase));
                outBuffer.append(toHex((aChar >> 4) & 0xF, lowerCase));
                outBuffer.append(toHex(aChar & 0xF, lowerCase));
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * Converts a nibble to a hex character
     * @param nibble  the nibble to convert.
     * @return a converted character
     */
    private static char toHex(int nibble, boolean lowerCase) {
        char hexChar = HEX_DIGITS[(nibble & 0xF)];
        if (lowerCase) {
            return Character.toLowerCase(hexChar);
        }
        return hexChar;
    }

}
