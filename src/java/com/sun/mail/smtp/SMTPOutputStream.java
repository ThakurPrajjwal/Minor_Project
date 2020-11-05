/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 */
package com.sun.mail.smtp;

import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SMTPOutputStream
extends CRLFOutputStream {
    public SMTPOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void ensureAtBOL() throws IOException {
        if (!this.atBOL) {
            super.writeln();
        }
    }

    public void flush() {
    }

    @Override
    public void write(int n) throws IOException {
        if ((this.lastb == 10 || this.lastb == 13 || this.lastb == -1) && n == 46) {
            this.out.write(46);
        }
        super.write(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = this.lastb == -1 ? 10 : this.lastb;
        int n4 = n;
        int n5 = n2 + n;
        int n6 = n;
        do {
            if (n6 >= n5) {
                if (n5 - n4 > 0) {
                    super.write(arrby, n4, n5 - n4);
                }
                return;
            }
            if ((n3 == 10 || n3 == 13) && arrby[n6] == 46) {
                super.write(arrby, n4, n6 - n4);
                this.out.write(46);
                n4 = n6;
            }
            n3 = arrby[n6];
            ++n6;
        } while (true);
    }
}

