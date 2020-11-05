/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.System
 *  java.util.Enumeration
 *  java.util.Vector
 *  javax.mail.MessagingException
 *  javax.mail.internet.InternetHeaders
 */
package com.sun.mail.dsn;

import com.sun.mail.util.LineOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;

public class DeliveryStatus {
    private static boolean debug;
    protected InternetHeaders messageDSN;
    protected InternetHeaders[] recipientDSN;

    static {
        boolean bl;
        block4 : {
            String string2;
            debug = false;
            try {
                string2 = System.getProperty((String)"mail.dsn.debug");
                bl = false;
                if (string2 == null) break block4;
            }
            catch (SecurityException securityException) {}
            boolean bl2 = string2.equalsIgnoreCase("false");
            bl = false;
            if (bl2) break block4;
            bl = true;
        }
        debug = bl;
    }

    public DeliveryStatus() throws MessagingException {
        this.messageDSN = new InternetHeaders();
        this.recipientDSN = new InternetHeaders[0];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DeliveryStatus(InputStream inputStream) throws MessagingException, IOException {
        Vector vector;
        block7 : {
            this.messageDSN = new InternetHeaders(inputStream);
            if (debug) {
                System.out.println("DSN: got messageDSN");
            }
            vector = new Vector();
            try {
                int n;
                while ((n = inputStream.available()) > 0) {
                    InternetHeaders internetHeaders = new InternetHeaders(inputStream);
                    if (debug) {
                        System.out.println("DSN: got recipientDSN");
                    }
                    vector.addElement((Object)internetHeaders);
                }
            }
            catch (EOFException eOFException) {
                if (!debug) break block7;
                System.out.println("DSN: got EOFException");
            }
        }
        if (debug) {
            System.out.println("DSN: recipientDSN size " + vector.size());
        }
        this.recipientDSN = new InternetHeaders[vector.size()];
        vector.copyInto((Object[])this.recipientDSN);
    }

    private static void writeInternetHeaders(InternetHeaders internetHeaders, LineOutputStream lineOutputStream) throws IOException {
        Enumeration enumeration = internetHeaders.getAllHeaderLines();
        try {
            do {
                if (!enumeration.hasMoreElements()) {
                    return;
                }
                lineOutputStream.writeln((String)enumeration.nextElement());
            } while (true);
        }
        catch (MessagingException messagingException) {
            Exception exception = messagingException.getNextException();
            if (exception instanceof IOException) {
                throw (IOException)((Object)exception);
            }
            throw new IOException("Exception writing headers: " + (Object)((Object)messagingException));
        }
    }

    public void addRecipientDSN(InternetHeaders internetHeaders) {
        InternetHeaders[] arrinternetHeaders = new InternetHeaders[1 + this.recipientDSN.length];
        System.arraycopy((Object)this.recipientDSN, (int)0, (Object)arrinternetHeaders, (int)0, (int)this.recipientDSN.length);
        this.recipientDSN = arrinternetHeaders;
        this.recipientDSN[-1 + this.recipientDSN.length] = internetHeaders;
    }

    public InternetHeaders getMessageDSN() {
        return this.messageDSN;
    }

    public InternetHeaders getRecipientDSN(int n) {
        return this.recipientDSN[n];
    }

    public int getRecipientDSNCount() {
        return this.recipientDSN.length;
    }

    public void setMessageDSN(InternetHeaders internetHeaders) {
        this.messageDSN = internetHeaders;
    }

    public String toString() {
        return "DeliveryStatus: Reporting-MTA=" + this.messageDSN.getHeader("Reporting-MTA", null) + ", #Recipients=" + this.recipientDSN.length;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeTo(OutputStream outputStream) throws IOException, MessagingException {
        LineOutputStream lineOutputStream = outputStream instanceof LineOutputStream ? (LineOutputStream)outputStream : new LineOutputStream(outputStream);
        DeliveryStatus.writeInternetHeaders(this.messageDSN, lineOutputStream);
        lineOutputStream.writeln();
        int n = 0;
        while (n < this.recipientDSN.length) {
            DeliveryStatus.writeInternetHeaders(this.recipientDSN[n], lineOutputStream);
            lineOutputStream.writeln();
            ++n;
        }
        return;
    }
}

