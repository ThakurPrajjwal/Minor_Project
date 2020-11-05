/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.PushbackInputStream
 *  java.lang.NumberFormatException
 */
package com.sun.mail.util;

import com.sun.mail.util.ASCIIUtility;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class QPDecoderStream
extends FilterInputStream {
    protected byte[] ba = new byte[2];
    protected int spaces = 0;

    public QPDecoderStream(InputStream inputStream) {
        super((InputStream)new PushbackInputStream(inputStream, 2));
    }

    public int available() throws IOException {
        return this.in.available();
    }

    public boolean markSupported() {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int read() throws IOException {
        if (this.spaces > 0) {
            this.spaces = -1 + this.spaces;
            return 32;
        }
        int n = this.in.read();
        if (n == 32) {
            int n2;
            do {
                if ((n2 = this.in.read()) != 32) {
                    if (n2 != 13 && n2 != 10 && n2 != -1) break;
                    this.spaces = 0;
                    return n2;
                }
                this.spaces = 1 + this.spaces;
            } while (true);
            ((PushbackInputStream)this.in).unread(n2);
            return 32;
        }
        if (n != 61) return n;
        int n3 = this.in.read();
        if (n3 == 10) {
            return this.read();
        }
        if (n3 == 13) {
            int n4 = this.in.read();
            if (n4 == 10) return this.read();
            ((PushbackInputStream)this.in).unread(n4);
            return this.read();
        }
        if (n3 == -1) {
            return -1;
        }
        this.ba[0] = (byte)n3;
        this.ba[1] = (byte)this.in.read();
        try {
            return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
        }
        catch (NumberFormatException numberFormatException) {
            ((PushbackInputStream)this.in).unread(this.ba);
            return n;
        }
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

