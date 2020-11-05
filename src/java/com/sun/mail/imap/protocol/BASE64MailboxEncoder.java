/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.CharArrayWriter
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.Object
 *  java.lang.String
 */
package com.sun.mail.imap.protocol;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

public class BASE64MailboxEncoder {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', ','};
    protected byte[] buffer = new byte[4];
    protected int bufsize = 0;
    protected Writer out = null;
    protected boolean started = false;

    public BASE64MailboxEncoder(Writer writer) {
        this.out = writer;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String encode(String string2) {
        BASE64MailboxEncoder bASE64MailboxEncoder = null;
        char[] arrc = string2.toCharArray();
        int n = arrc.length;
        boolean bl = false;
        CharArrayWriter charArrayWriter = new CharArrayWriter(n);
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (bASE64MailboxEncoder != null) {
                    bASE64MailboxEncoder.flush();
                }
                if (!bl) return string2;
                return charArrayWriter.toString();
            }
            char c = arrc[n2];
            if (c >= ' ' && c <= '~') {
                if (bASE64MailboxEncoder != null) {
                    bASE64MailboxEncoder.flush();
                }
                if (c == '&') {
                    bl = true;
                    charArrayWriter.write(38);
                    charArrayWriter.write(45);
                } else {
                    charArrayWriter.write((int)c);
                }
            } else {
                if (bASE64MailboxEncoder == null) {
                    bASE64MailboxEncoder = new BASE64MailboxEncoder((Writer)charArrayWriter);
                    bl = true;
                }
                bASE64MailboxEncoder.write(c);
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void encode() throws IOException {
        if (this.bufsize == 1) {
            byte by = this.buffer[0];
            this.out.write((int)pem_array[63 & by >>> 2]);
            this.out.write((int)pem_array[0 + (48 & by << 4)]);
            return;
        } else {
            if (this.bufsize == 2) {
                byte by = this.buffer[0];
                byte by2 = this.buffer[1];
                this.out.write((int)pem_array[63 & by >>> 2]);
                this.out.write((int)pem_array[(48 & by << 4) + (15 & by2 >>> 4)]);
                this.out.write((int)pem_array[0 + (60 & by2 << 2)]);
                return;
            }
            byte by = this.buffer[0];
            byte by3 = this.buffer[1];
            byte by4 = this.buffer[2];
            this.out.write((int)pem_array[63 & by >>> 2]);
            this.out.write((int)pem_array[(48 & by << 4) + (15 & by3 >>> 4)]);
            this.out.write((int)pem_array[(60 & by3 << 2) + (3 & by4 >>> 6)]);
            this.out.write((int)pem_array[by4 & 63]);
            if (this.bufsize != 4) return;
            {
                this.buffer[0] = this.buffer[3];
                return;
            }
        }
    }

    public void flush() {
        try {
            if (this.bufsize > 0) {
                this.encode();
                this.bufsize = 0;
            }
            if (this.started) {
                this.out.write(45);
                this.started = false;
            }
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public void write(int n) {
        try {
            if (!this.started) {
                this.started = true;
                this.out.write(38);
            }
            byte[] arrby = this.buffer;
            int n2 = this.bufsize;
            this.bufsize = n2 + 1;
            arrby[n2] = (byte)(n >> 8);
            byte[] arrby2 = this.buffer;
            int n3 = this.bufsize;
            this.bufsize = n3 + 1;
            arrby2[n3] = (byte)(n & 255);
            if (this.bufsize >= 3) {
                this.encode();
                this.bufsize = -3 + this.bufsize;
            }
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }
}

