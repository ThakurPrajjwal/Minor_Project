/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.PushbackInputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class LineInputStream
extends FilterInputStream {
    private char[] lineBuffer = null;

    public LineInputStream(InputStream inputStream) {
        super(inputStream);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String readLine() throws IOException {
        InputStream inputStream = this.in;
        char[] arrc = this.lineBuffer;
        if (arrc == null) {
            this.lineBuffer = arrc = new char[128];
        }
        int n = arrc.length;
        int n2 = 0;
        do {
            int n3;
            block10 : {
                block9 : {
                    if ((n3 = inputStream.read()) == -1 || n3 == 10) break block9;
                    if (n3 != 13) break block10;
                    int n4 = inputStream.read();
                    if (n4 == 13) {
                        n4 = inputStream.read();
                    }
                    if (n4 != 10) {
                        if (!(inputStream instanceof PushbackInputStream)) {
                            PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
                            this.in = pushbackInputStream;
                            inputStream = pushbackInputStream;
                        }
                        ((PushbackInputStream)inputStream).unread(n4);
                    }
                }
                if (n3 != -1 || n2 != 0) break;
                return null;
            }
            if (--n < 0) {
                arrc = new char[n2 + 128];
                n = -1 + (arrc.length - n2);
                System.arraycopy((Object)this.lineBuffer, (int)0, (Object)arrc, (int)0, (int)n2);
                this.lineBuffer = arrc;
            }
            int n5 = n2 + 1;
            arrc[n2] = (char)n3;
            n2 = n5;
        } while (true);
        return String.copyValueOf((char[])arrc, (int)0, (int)n2);
    }
}

