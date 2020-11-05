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

public class CRLFOutputStream
extends FilterOutputStream {
    private static final byte[] newline = new byte[]{13, 10};
    protected boolean atBOL = true;
    protected int lastb = -1;

    public CRLFOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(int n) throws IOException {
        if (n == 13) {
            this.writeln();
        } else if (n == 10) {
            if (this.lastb != 13) {
                this.writeln();
            }
        } else {
            this.out.write(n);
            this.atBOL = false;
        }
        this.lastb = n;
    }

    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n;
        int n4 = n2 + n;
        int n5 = n3;
        do {
            if (n5 >= n4) {
                if (n4 - n3 > 0) {
                    this.out.write(arrby, n3, n4 - n3);
                    this.atBOL = false;
                }
                return;
            }
            if (arrby[n5] == 13) {
                this.out.write(arrby, n3, n5 - n3);
                this.writeln();
                n3 = n5 + 1;
            } else if (arrby[n5] == 10) {
                if (this.lastb != 13) {
                    this.out.write(arrby, n3, n5 - n3);
                    this.writeln();
                }
                n3 = n5 + 1;
            }
            this.lastb = arrby[n5];
            ++n5;
        } while (true);
    }

    public void writeln() throws IOException {
        this.out.write(newline);
        this.atBOL = true;
    }
}

