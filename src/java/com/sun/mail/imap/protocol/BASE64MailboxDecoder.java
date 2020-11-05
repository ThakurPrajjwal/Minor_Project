/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.text.CharacterIterator
 *  java.text.StringCharacterIterator
 */
package com.sun.mail.imap.protocol;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class BASE64MailboxDecoder {
    static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', ','};
    private static final byte[] pem_convert_array = new byte[256];

    /*
     * Enabled aggressive block sorting
     */
    static {
        int n = 0;
        do {
            if (n >= 255) break;
            BASE64MailboxDecoder.pem_convert_array[n] = -1;
            ++n;
        } while (true);
        int n2 = 0;
        while (n2 < pem_array.length) {
            BASE64MailboxDecoder.pem_convert_array[BASE64MailboxDecoder.pem_array[n2]] = (byte)n2;
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static int base64decode(char[] arrc, int n, CharacterIterator characterIterator) {
        boolean bl = true;
        int n2 = -1;
        byte by;
        while ((by = (byte)characterIterator.next()) != -1) {
            if (by == 45) {
                if (!bl) return n;
                {
                    int n3 = n + 1;
                    arrc[n] = 38;
                    return n3;
                }
            }
            byte by2 = (byte)characterIterator.next();
            if (by2 == -1 || by2 == 45) return n;
            byte by3 = pem_convert_array[by & 255];
            byte by4 = pem_convert_array[by2 & 255];
            byte by5 = (byte)(252 & by3 << 2 | 3 & by4 >>> 4);
            if (n2 != -1) {
                int n4 = n + 1;
                arrc[n] = (char)(n2 << 8 | by5 & 255);
                n2 = -1;
                n = n4;
            } else {
                n2 = by5 & 255;
            }
            byte by6 = (byte)characterIterator.next();
            bl = false;
            if (by6 == 61) continue;
            if (by6 == -1 || by6 == 45) return n;
            byte by7 = pem_convert_array[by6 & 255];
            byte by8 = (byte)(240 & by4 << 4 | 15 & by7 >>> 2);
            if (n2 != -1) {
                int n5 = n + 1;
                arrc[n] = (char)(n2 << 8 | by8 & 255);
                n2 = -1;
                n = n5;
            } else {
                n2 = by8 & 255;
            }
            byte by9 = (byte)characterIterator.next();
            bl = false;
            if (by9 == 61) continue;
            if (by9 == -1 || by9 == 45) {
                return n;
            }
            byte by10 = pem_convert_array[by9 & 255];
            byte by11 = (byte)(192 & by7 << 6 | by10 & 63);
            if (n2 != -1) {
                (char)(n2 << 8 | by11 & 255);
                int n6 = n + 1;
                arrc[n] = (char)(n2 << 8 | by11 & 255);
                n2 = -1;
                n = n6;
                bl = false;
                continue;
            }
            n2 = by11 & 255;
            bl = false;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String decode(String string2) {
        if (string2 == null || string2.length() == 0) return string2;
        boolean bl = false;
        char[] arrc = new char[string2.length()];
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(string2);
        char c = stringCharacterIterator.first();
        int n = 0;
        do {
            int n2;
            if (c == '\uffff') {
                if (!bl) return string2;
                return new String(arrc, 0, n);
            }
            if (c == '&') {
                bl = true;
                n2 = BASE64MailboxDecoder.base64decode(arrc, n, (CharacterIterator)stringCharacterIterator);
            } else {
                n2 = n + 1;
                arrc[n] = c;
            }
            c = stringCharacterIterator.next();
            n = n2;
        } while (true);
    }
}

