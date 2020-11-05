/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.OutputStream
 */
package com.sun.mail.util;

import com.sun.mail.util.BASE64EncoderStream;
import java.io.OutputStream;

public class BEncoderStream
extends BASE64EncoderStream {
    public BEncoderStream(OutputStream outputStream) {
        super(outputStream, Integer.MAX_VALUE);
    }

    public static int encodedLength(byte[] arrby) {
        return 4 * ((2 + arrby.length) / 3);
    }
}

