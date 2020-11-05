/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.System
 */
package com.sun.mail.imap;

import java.io.IOException;
import java.io.OutputStream;

class LengthCounter
extends OutputStream {
    private byte[] buf = new byte[8192];
    private int maxsize;
    private int size = 0;

    public LengthCounter(int n) {
        this.maxsize = n;
    }

    public byte[] getBytes() {
        return this.buf;
    }

    public int getSize() {
        return this.size;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(int n) {
        int n2 = 1 + this.size;
        if (this.buf != null) {
            if (n2 > this.maxsize && this.maxsize >= 0) {
                this.buf = null;
            } else if (n2 > this.buf.length) {
                byte[] arrby = new byte[Math.max((int)(this.buf.length << 1), (int)n2)];
                System.arraycopy((Object)this.buf, (int)0, (Object)arrby, (int)0, (int)this.size);
                this.buf = arrby;
                this.buf[this.size] = (byte)n;
            } else {
                this.buf[this.size] = (byte)n;
            }
        }
        this.size = n2;
    }

    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(byte[] arrby, int n, int n2) {
        if (n < 0 || n > arrby.length || n2 < 0 || n + n2 > arrby.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        int n3 = n2 + this.size;
        if (this.buf != null) {
            if (n3 > this.maxsize && this.maxsize >= 0) {
                this.buf = null;
            } else if (n3 > this.buf.length) {
                byte[] arrby2 = new byte[Math.max((int)(this.buf.length << 1), (int)n3)];
                System.arraycopy((Object)this.buf, (int)0, (Object)arrby2, (int)0, (int)this.size);
                this.buf = arrby2;
                System.arraycopy((Object)arrby, (int)n, (Object)this.buf, (int)this.size, (int)n2);
            } else {
                System.arraycopy((Object)arrby, (int)n, (Object)this.buf, (int)this.size, (int)n2);
            }
        }
        this.size = n3;
    }
}

