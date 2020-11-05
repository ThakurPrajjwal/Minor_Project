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

public class QPEncoderStream
extends FilterOutputStream {
    private static final char[] hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private int bytesPerLine;
    private int count = 0;
    private boolean gotCR = false;
    private boolean gotSpace = false;

    public QPEncoderStream(OutputStream outputStream) {
        this(outputStream, 76);
    }

    public QPEncoderStream(OutputStream outputStream, int n) {
        super(outputStream);
        this.bytesPerLine = n - 1;
    }

    private void outputCRLF() throws IOException {
        this.out.write(13);
        this.out.write(10);
        this.count = 0;
    }

    public void close() throws IOException {
        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    protected void output(int n, boolean bl) throws IOException {
        int n2;
        if (bl) {
            int n3;
            this.count = n3 = 3 + this.count;
            if (n3 > this.bytesPerLine) {
                this.out.write(61);
                this.out.write(13);
                this.out.write(10);
                this.count = 3;
            }
            this.out.write(61);
            this.out.write((int)hex[n >> 4]);
            this.out.write((int)hex[n & 15]);
            return;
        }
        this.count = n2 = 1 + this.count;
        if (n2 > this.bytesPerLine) {
            this.out.write(61);
            this.out.write(13);
            this.out.write(10);
            this.count = 1;
        }
        this.out.write(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(int n) throws IOException {
        int n2 = n & 255;
        if (this.gotSpace) {
            if (n2 == 13 || n2 == 10) {
                this.output(32, true);
            } else {
                this.output(32, false);
            }
            this.gotSpace = false;
        }
        if (n2 == 13) {
            this.gotCR = true;
            this.outputCRLF();
            return;
        }
        if (n2 == 10) {
            if (!this.gotCR) {
                this.outputCRLF();
            }
        } else if (n2 == 32) {
            this.gotSpace = true;
        } else if (n2 < 32 || n2 >= 127 || n2 == 61) {
            this.output(n2, true);
        } else {
            this.output(n2, false);
        }
        this.gotCR = false;
    }

    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = 0;
        while (n3 < n2) {
            this.write(arrby[n + n3]);
            ++n3;
        }
        return;
    }
}

