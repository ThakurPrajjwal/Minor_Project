/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Character
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 */
package com.sun.mail.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASCIIUtility {
    private ASCIIUtility() {
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        if (inputStream instanceof ByteArrayInputStream) {
            int n = inputStream.available();
            byte[] arrby = new byte[n];
            inputStream.read(arrby, 0, n);
            return arrby;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[1024];
        int n;
        while ((n = inputStream.read(arrby, 0, 1024)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getBytes(String string2) {
        char[] arrc = string2.toCharArray();
        int n = arrc.length;
        byte[] arrby = new byte[n];
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2 + 1;
            arrby[n2] = (byte)arrc[n2];
            n2 = n3;
        }
        return arrby;
    }

    public static int parseInt(byte[] arrby, int n, int n2) throws NumberFormatException {
        return ASCIIUtility.parseInt(arrby, n, n2, 10);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int parseInt(byte[] arrby, int n, int n2, int n3) throws NumberFormatException {
        int n4;
        int n5;
        boolean bl;
        int n6;
        if (arrby == null) {
            throw new NumberFormatException("null");
        }
        int n7 = n;
        if (n2 <= n) {
            throw new NumberFormatException("illegal number");
        }
        if (arrby[n7] == 45) {
            bl = true;
            n4 = Integer.MIN_VALUE;
            ++n7;
        } else {
            n4 = -2147483647;
            bl = false;
        }
        int n8 = n4 / n3;
        if (n7 < n2) {
            n5 = n7 + 1;
            int n9 = Character.digit((char)((char)arrby[n7]), (int)n3);
            if (n9 < 0) {
                throw new NumberFormatException("illegal number: " + ASCIIUtility.toString(arrby, n, n2));
            }
            n6 = -n9;
        } else {
            n5 = n7;
            n6 = 0;
        }
        do {
            if (n5 >= n2) {
                if (!bl) {
                    return -n6;
                }
                if (n5 <= n + 1) break;
                return n6;
            }
            int n10 = n5 + 1;
            int n11 = Character.digit((char)((char)arrby[n5]), (int)n3);
            if (n11 < 0) {
                throw new NumberFormatException("illegal number");
            }
            if (n6 < n8) {
                throw new NumberFormatException("illegal number");
            }
            int n12 = n6 * n3;
            if (n12 < n4 + n11) {
                throw new NumberFormatException("illegal number");
            }
            n6 = n12 - n11;
            n5 = n10;
        } while (true);
        throw new NumberFormatException("illegal number");
    }

    public static long parseLong(byte[] arrby, int n, int n2) throws NumberFormatException {
        return ASCIIUtility.parseLong(arrby, n, n2, 10);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static long parseLong(byte[] arrby, int n, int n2, int n3) throws NumberFormatException {
        long l;
        int n4;
        boolean bl;
        if (arrby == null) {
            throw new NumberFormatException("null");
        }
        long l2 = 0L;
        int n5 = n;
        if (n2 <= n) {
            throw new NumberFormatException("illegal number");
        }
        if (arrby[n5] == 45) {
            bl = true;
            l = Long.MIN_VALUE;
            ++n5;
        } else {
            l = -9223372036854775807L;
            bl = false;
        }
        long l3 = l / (long)n3;
        if (n5 < n2) {
            n4 = n5 + 1;
            int n6 = Character.digit((char)((char)arrby[n5]), (int)n3);
            if (n6 < 0) {
                throw new NumberFormatException("illegal number: " + ASCIIUtility.toString(arrby, n, n2));
            }
            l2 = -n6;
        } else {
            n4 = n5;
        }
        do {
            if (n4 >= n2) {
                if (!bl) {
                    return -l2;
                }
                if (n4 <= n + 1) break;
                return l2;
            }
            int n7 = n4 + 1;
            int n8 = Character.digit((char)((char)arrby[n4]), (int)n3);
            if (n8 < 0) {
                throw new NumberFormatException("illegal number");
            }
            if (l2 < l3) {
                throw new NumberFormatException("illegal number");
            }
            long l4 = l2 * (long)n3;
            if (l4 < l + (long)n8) {
                throw new NumberFormatException("illegal number");
            }
            l2 = l4 - (long)n8;
            n4 = n7;
        } while (true);
        throw new NumberFormatException("illegal number");
    }

    public static String toString(ByteArrayInputStream byteArrayInputStream) {
        int n = byteArrayInputStream.available();
        char[] arrc = new char[n];
        byte[] arrby = new byte[n];
        byteArrayInputStream.read(arrby, 0, n);
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2 + 1;
            arrc[n2] = (char)(255 & arrby[n2]);
            n2 = n3;
        }
        return new String(arrc);
    }

    public static String toString(byte[] arrby, int n, int n2) {
        int n3 = n2 - n;
        char[] arrc = new char[n3];
        int n4 = n;
        int n5 = 0;
        while (n5 < n3) {
            int n6 = n5 + 1;
            int n7 = n4 + 1;
            arrc[n5] = (char)(255 & arrby[n4]);
            n4 = n7;
            n5 = n6;
        }
        return new String(arrc);
    }
}

