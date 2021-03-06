/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TraceOutputStream
extends FilterOutputStream {
    private boolean quote = false;
    private boolean trace = false;
    private OutputStream traceOut;

    public TraceOutputStream(OutputStream outputStream, OutputStream outputStream2) {
        super(outputStream);
        this.traceOut = outputStream2;
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

    public void setQuote(boolean bl) {
        this.quote = bl;
    }

    public void setTrace(boolean bl) {
        this.trace = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(int n) throws IOException {
        if (this.trace) {
            if (this.quote) {
                this.writeByte(n);
            } else {
                this.traceOut.write(n);
            }
        }
        this.out.write(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this.trace) {
            if (this.quote) {
                for (int i = 0; i < n2; ++i) {
                    this.writeByte(arrby[n + i]);
                }
            } else {
                this.traceOut.write(arrby, n, n2);
            }
        }
        this.out.write(arrby, n, n2);
    }
}

