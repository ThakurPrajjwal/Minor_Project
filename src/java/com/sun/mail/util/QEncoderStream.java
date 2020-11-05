/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.String
 */
package com.sun.mail.util;

import com.sun.mail.util.QPEncoderStream;
import java.io.IOException;
import java.io.OutputStream;

public class QEncoderStream
extends QPEncoderStream {
    private static String TEXT_SPECIALS;
    private static String WORD_SPECIALS;
    private String specials;

    static {
        WORD_SPECIALS = "=_?\"#$%&'(),.:;<>@[\\]^`{|}~";
        TEXT_SPECIALS = "=_?";
    }

    /*
     * Enabled aggressive block sorting
     */
    public QEncoderStream(OutputStream outputStream, boolean bl) {
        super(outputStream, Integer.MAX_VALUE);
        String string2 = bl ? WORD_SPECIALS : TEXT_SPECIALS;
        this.specials = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int encodedLength(byte[] arrby, boolean bl) {
        int n = 0;
        String string2 = bl ? WORD_SPECIALS : TEXT_SPECIALS;
        int n2 = 0;
        while (n2 < arrby.length) {
            int n3 = 255 & arrby[n2];
            n = n3 < 32 || n3 >= 127 || string2.indexOf(n3) >= 0 ? (n += 3) : ++n;
            ++n2;
        }
        return n;
    }

    @Override
    public void write(int n) throws IOException {
        int n2 = n & 255;
        if (n2 == 32) {
            this.output(95, false);
            return;
        }
        if (n2 < 32 || n2 >= 127 || this.specials.indexOf(n2) >= 0) {
            this.output(n2, true);
            return;
        }
        this.output(n2, false);
    }
}

