/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 */
package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TraceInputStream
extends FilterInputStream {
    private boolean quote = false;
    private boolean trace = false;
    private OutputStream traceOut;

    public TraceInputStream(InputStream inputStream, OutputStream outputStream) {
        super(inputStream);
        this.traceOut = outputStream;
    }

    private final void writeByte(int n) throws IOException {
        int n2 = n & 255;
        if (n2 > 127) {
            this.traceOut.write(77);
            this.traceOut.write(45);
            n2 &= 127;
        }
        if (n2 == 13) {
            this.traceOut.write(92);
            this.traceOut.write(114);
            return;
        }
        if (n2 == 10) {
            this.traceOut.write(92);
            this.traceOut.write(110);
            this.traceOut.write(10);
            return;
        }
        if (n2 == 9) {
            this.traceOut.write(92);
            this.traceOut.write(116);
            return;
        }
        if (n2 < 32) {
            this.traceOut.write(94);
            this.traceOut.write(n2 + 64);
            return;
        }
        this.traceOut.write(n2);
    }

    public int read() throws IOException {
        int n;
        block3 : {
            block2 : {
                n = this.in.read();
                if (!this.trace || n == -1) break block2;
                if (!this.quote) break block3;
                this.writeByte(n);
            }
            return n;
        }
        this.traceOut.write(n);
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = this.in.read(arrby, n, n2);
        if (!this.trace || n3 == -1) return n3;
        if (this.quote) {
            int n4 = 0;
            do {
                if (n4 >= n3) {
                    return n3;
                }
                this.writeByte(arrby[n + n4]);
                ++n4;
            } while (true);
        }
        this.traceOut.write(arrby, n, n3);
        return n3;
    }

    public void setQuote(boolean bl) {
        this.quote = bl;
    }

    public void setTrace(boolean bl) {
        this.trace = bl;
    }
}

