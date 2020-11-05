/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterOutputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.String
 *  javax.mail.MessagingException
 */
package com.sun.mail.util;

import com.sun.mail.util.ASCIIUtility;
import java.io.FilterOutputStream;
import java.io.OutputStream;
import javax.mail.MessagingException;

public class LineOutputStream
extends FilterOutputStream {
    private static byte[] newline = new byte[2];

    static {
        LineOutputStream.newline[0] = 13;
        LineOutputStream.newline[1] = 10;
    }

    public LineOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void writeln() throws MessagingException {
        try {
            this.out.write(newline);
            return;
        }
        catch (Exception exception) {
            throw new MessagingException("IOException", exception);
        }
    }

    public void writeln(String string2) throws MessagingException {
        try {
            byte[] arrby = ASCIIUtility.getBytes(string2);
            this.out.write(arrby);
            this.out.write(newline);
            return;
        }
        catch (Exception exception) {
            throw new MessagingException("IOException", exception);
        }
    }
}

