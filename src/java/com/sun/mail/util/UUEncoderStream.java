/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.String
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class UUEncoderStream
extends FilterOutputStream {
    private byte[] buffer;
    private int bufsize = 0;
    protected int mode;
    protected String name;
    private boolean wrotePrefix = false;

    public UUEncoderStream(OutputStream outputStream) {
        this(outputStream, "encoder.buf", 644);
    }

    public UUEncoderStream(OutputStream outputStream, String string2) {
        this(outputStream, string2, 644);
    }

    public UUEncoderStream(OutputStream outputStream, String string2, int n) {
        super(outputStream);
        this.name = string2;
        this.mode = n;
        this.buffer = new byte[45];
    }

    /*
     * Enabled aggressive block sorting
     */
    private void encode() throws IOException {
        int n = 0;
        this.out.write(32 + (63 & this.bufsize));
        do {
            byte by;
            byte by2;
            if (n >= this.bufsize) {
                this.out.write(10);
                return;
            }
            byte[] arrby = this.buffer;
            int n2 = n + 1;
            byte by3 = arrby[n];
            if (n2 < this.bufsize) {
                byte[] arrby2 = this.buffer;
                n = n2 + 1;
                by = arrby2[n2];
                if (n < this.bufsize) {
                    byte[] arrby3 = this.buffer;
                    int n3 = n + 1;
                    by2 = arrby3[n];
                    n = n3;
                } else {
                    by2 = 1;
                }
            } else {
                by = 1;
                by2 = 1;
                n = n2;
            }
            int n4 = 63 & by3 >>> 2;
            int n5 = 48 & by3 << 4 | 15 & by >>> 4;
            int n6 = 60 & by << 2 | 3 & by2 >>> 6;
            int n7 = by2 & 63;
            this.out.write(n4 + 32);
            this.out.write(n5 + 32);
            this.out.write(n6 + 32);
            this.out.write(n7 + 32);
        } while (true);
    }

    private void writePrefix() throws IOException {
        if (!this.wrotePrefix) {
            PrintStream printStream = new PrintStream(this.out);
            printStream.println("begin " + this.mode + " " + this.name);
            printStream.flush();
            this.wrotePrefix = true;
        }
    }

    private void writeSuffix() throws IOException {
        PrintStream printStream = new PrintStream(this.out);
        printStream.println(" \nend");
        printStream.flush();
    }

    public void close() throws IOException {
        this.flush();
        this.out.close();
    }

    public void flush() throws IOException {
        if (this.bufsize > 0) {
            this.writePrefix();
            this.encode();
        }
        this.writeSuffix();
        this.out.flush();
    }

    public void setNameMode(String string2, int n) {
        this.name = string2;
        this.mode = n;
    }

    public void write(int n) throws IOException {
        byte[] arrby = this.buffer;
        int n2 = this.bufsize;
        this.bufsize = n2 + 1;
        arrby[n2] = (byte)n;
        if (this.bufsize == 45) {
            this.writePrefix();
            this.encode();
            this.bufsize = 0;
        }
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

