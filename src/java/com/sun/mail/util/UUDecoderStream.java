/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.String
 */
package com.sun.mail.util;

import com.sun.mail.util.LineInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UUDecoderStream
extends FilterInputStream {
    private byte[] buffer;
    private int bufsize = 0;
    private boolean gotEnd = false;
    private boolean gotPrefix = false;
    private int index = 0;
    private LineInputStream lin;
    private int mode;
    private String name;

    public UUDecoderStream(InputStream inputStream) {
        super(inputStream);
        this.lin = new LineInputStream(inputStream);
        this.buffer = new byte[45];
    }

    private boolean decode() throws IOException {
        String string2;
        if (this.gotEnd) {
            return false;
        }
        this.bufsize = 0;
        do {
            if ((string2 = this.lin.readLine()) == null) {
                throw new IOException("Missing End");
            }
            if (!string2.regionMatches(true, 0, "end", 0, 3)) continue;
            this.gotEnd = true;
            return false;
        } while (string2.length() == 0);
        char c = string2.charAt(0);
        if (c < ' ') {
            throw new IOException("Buffer format error");
        }
        int n = 63 & c - 32;
        if (n == 0) {
            String string3 = this.lin.readLine();
            if (string3 == null || !string3.regionMatches(true, 0, "end", 0, 3)) {
                throw new IOException("Missing End");
            }
            this.gotEnd = true;
            return false;
        }
        int n2 = (5 + n * 8) / 6;
        if (string2.length() < n2 + 1) {
            throw new IOException("Short buffer error");
        }
        int n3 = 1;
        while (this.bufsize < n) {
            int n4 = n3 + 1;
            byte by = (byte)(63 & -32 + string2.charAt(n3));
            n3 = n4 + 1;
            byte by2 = (byte)(63 & -32 + string2.charAt(n4));
            byte[] arrby = this.buffer;
            int n5 = this.bufsize;
            this.bufsize = n5 + 1;
            arrby[n5] = (byte)(252 & by << 2 | 3 & by2 >>> 4);
            if (this.bufsize < n) {
                byte by3 = by2;
                int n6 = n3 + 1;
                by2 = (byte)(63 & -32 + string2.charAt(n3));
                byte[] arrby2 = this.buffer;
                int n7 = this.bufsize;
                this.bufsize = n7 + 1;
                arrby2[n7] = (byte)(240 & by3 << 4 | 15 & by2 >>> 2);
                n3 = n6;
            }
            if (this.bufsize >= n) continue;
            byte by4 = by2;
            int n8 = n3 + 1;
            byte by5 = (byte)(63 & -32 + string2.charAt(n3));
            byte[] arrby3 = this.buffer;
            int n9 = this.bufsize;
            this.bufsize = n9 + 1;
            arrby3[n9] = (byte)(192 & by4 << 6 | by5 & 63);
            n3 = n8;
        }
        return true;
    }

    private void readPrefix() throws IOException {
        String string2;
        if (this.gotPrefix) {
            return;
        }
        do {
            if ((string2 = this.lin.readLine()) != null) continue;
            throw new IOException("UUDecoder error: No Begin");
        } while (!string2.regionMatches(true, 0, "begin", 0, 5));
        try {
            this.mode = Integer.parseInt((String)string2.substring(6, 9));
        }
        catch (NumberFormatException numberFormatException) {
            throw new IOException("UUDecoder error: " + numberFormatException.toString());
        }
        this.name = string2.substring(10);
        this.gotPrefix = true;
    }

    public int available() throws IOException {
        return 3 * this.in.available() / 4 + (this.bufsize - this.index);
    }

    public int getMode() throws IOException {
        this.readPrefix();
        return this.mode;
    }

    public String getName() throws IOException {
        this.readPrefix();
        return this.name;
    }

    public boolean markSupported() {
        return false;
    }

    public int read() throws IOException {
        if (this.index >= this.bufsize) {
            this.readPrefix();
            if (!this.decode()) {
                return -1;
            }
            this.index = 0;
        }
        byte[] arrby = this.buffer;
        int n = this.index;
        this.index = n + 1;
        return 255 & arrby[n];
    }

    /*
     * Enabled aggressive block sorting
     */
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = 0;
        do {
            int n4;
            block4 : {
                block5 : {
                    block3 : {
                        if (n3 >= n2) break block3;
                        n4 = this.read();
                        if (n4 != -1) break block4;
                        if (n3 == 0) break block5;
                    }
                    return n3;
                }
                return -1;
            }
            arrby[n + n3] = (byte)n4;
            ++n3;
        } while (true);
    }
}

