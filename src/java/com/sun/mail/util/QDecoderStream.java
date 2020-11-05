/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.NumberFormatException
 *  java.lang.String
 */
package com.sun.mail.util;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.QPDecoderStream;
import java.io.IOException;
import java.io.InputStream;

public class QDecoderStream
extends QPDecoderStream {
    public QDecoderStream(InputStream inputStream) {
        super(inputStream);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n == 95) {
            return 32;
        }
        if (n != 61) return n;
        this.ba[0] = (byte)this.in.read();
        this.ba[1] = (byte)this.in.read();
        try {
            return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw new IOException("Error in QP stream " + numberFormatException.getMessage());
        }
    }
}

