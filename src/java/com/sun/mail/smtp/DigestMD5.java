/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.io.Reader
 *  java.io.StreamTokenizer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.security.SecureRandom
 *  java.util.Hashtable
 *  java.util.StringTokenizer
 */
package com.sun.mail.smtp;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class DigestMD5 {
    private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private String clientResponse;
    private PrintStream debugout;
    private MessageDigest md5;
    private String uri;

    public DigestMD5(PrintStream printStream) {
        this.debugout = printStream;
        if (printStream != null) {
            printStream.println("DEBUG DIGEST-MD5: Loaded");
        }
    }

    private static String toHex(byte[] arrby) {
        char[] arrc = new char[2 * arrby.length];
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            int n3 = 255 & arrby[n];
            int n4 = n2 + 1;
            arrc[n2] = digits[n3 >> 4];
            n2 = n4 + 1;
            arrc[n4] = digits[n3 & 15];
            ++n;
        }
        return new String(arrc);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Hashtable tokenize(String string2) throws IOException {
        Hashtable hashtable = new Hashtable();
        byte[] arrby = string2.getBytes();
        String string3 = null;
        StreamTokenizer streamTokenizer = new StreamTokenizer((Reader)new InputStreamReader((InputStream)new BASE64DecoderStream((InputStream)new ByteArrayInputStream(arrby, 4, -4 + arrby.length))));
        streamTokenizer.ordinaryChars(48, 57);
        streamTokenizer.wordChars(48, 57);
        int n;
        block4 : while ((n = streamTokenizer.nextToken()) != -1) {
            switch (n) {
                default: {
                    continue block4;
                }
                case -3: {
                    if (string3 != null) break;
                    string3 = streamTokenizer.sval;
                    continue block4;
                }
                case 34: 
            }
            if (this.debugout != null) {
                this.debugout.println("DEBUG DIGEST-MD5: Received => " + string3 + "='" + streamTokenizer.sval + "'");
            }
            if (hashtable.containsKey((Object)string3)) {
                hashtable.put((Object)string3, (Object)(hashtable.get((Object)string3) + "," + streamTokenizer.sval));
            } else {
                hashtable.put((Object)string3, (Object)streamTokenizer.sval);
            }
            string3 = null;
        }
        return hashtable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] authClient(String string2, String string3, String string4, String string5, String string6) throws IOException {
        SecureRandom secureRandom;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream((OutputStream)byteArrayOutputStream, Integer.MAX_VALUE);
        try {
            secureRandom = new SecureRandom();
            this.md5 = MessageDigest.getInstance((String)"MD5");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            if (this.debugout != null) {
                this.debugout.println("DEBUG DIGEST-MD5: " + (Object)((Object)noSuchAlgorithmException));
            }
            throw new IOException(noSuchAlgorithmException.toString());
        }
        StringBuffer stringBuffer = new StringBuffer();
        this.uri = "smtp/" + string2;
        byte[] arrby = new byte[32];
        if (this.debugout != null) {
            this.debugout.println("DEBUG DIGEST-MD5: Begin authentication ...");
        }
        Hashtable hashtable = this.tokenize(string6);
        if (string5 == null) {
            String string7 = (String)hashtable.get((Object)"realm");
            string5 = string7 != null ? new StringTokenizer(string7, ",").nextToken() : string2;
        }
        String string8 = (String)hashtable.get((Object)"nonce");
        secureRandom.nextBytes(arrby);
        bASE64EncoderStream.write(arrby);
        bASE64EncoderStream.flush();
        String string9 = byteArrayOutputStream.toString();
        byteArrayOutputStream.reset();
        this.md5.update(this.md5.digest(ASCIIUtility.getBytes(String.valueOf((Object)string3) + ":" + string5 + ":" + string4)));
        this.md5.update(ASCIIUtility.getBytes(":" + string8 + ":" + string9));
        this.clientResponse = String.valueOf((Object)DigestMD5.toHex(this.md5.digest())) + ":" + string8 + ":" + "00000001" + ":" + string9 + ":" + "auth" + ":";
        this.md5.update(ASCIIUtility.getBytes("AUTHENTICATE:" + this.uri));
        this.md5.update(ASCIIUtility.getBytes(String.valueOf((Object)this.clientResponse) + DigestMD5.toHex(this.md5.digest())));
        stringBuffer.append("username=\"" + string3 + "\"");
        stringBuffer.append(",realm=\"" + string5 + "\"");
        stringBuffer.append(",qop=" + "auth");
        stringBuffer.append(",nc=" + "00000001");
        stringBuffer.append(",nonce=\"" + string8 + "\"");
        stringBuffer.append(",cnonce=\"" + string9 + "\"");
        stringBuffer.append(",digest-uri=\"" + this.uri + "\"");
        stringBuffer.append(",response=" + DigestMD5.toHex(this.md5.digest()));
        if (this.debugout != null) {
            this.debugout.println("DEBUG DIGEST-MD5: Response => " + stringBuffer.toString());
        }
        bASE64EncoderStream.write(ASCIIUtility.getBytes(stringBuffer.toString()));
        bASE64EncoderStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public boolean authServer(String string2) throws IOException {
        Hashtable hashtable = this.tokenize(string2);
        this.md5.update(ASCIIUtility.getBytes(":" + this.uri));
        this.md5.update(ASCIIUtility.getBytes(String.valueOf((Object)this.clientResponse) + DigestMD5.toHex(this.md5.digest())));
        String string3 = DigestMD5.toHex(this.md5.digest());
        if (!string3.equals((Object)((String)hashtable.get((Object)"rspauth")))) {
            if (this.debugout != null) {
                this.debugout.println("DEBUG DIGEST-MD5: Expected => rspauth=" + string3);
            }
            return false;
        }
        return true;
    }
}

