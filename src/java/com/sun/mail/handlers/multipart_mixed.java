/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  javax.activation.ActivationDataFlavor
 *  javax.activation.DataContentHandler
 *  javax.activation.DataSource
 *  javax.mail.MessagingException
 *  javax.mail.internet.MimeMultipart
 */
package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import myjava.awt.datatransfer.DataFlavor;

public class multipart_mixed
implements DataContentHandler {
    private ActivationDataFlavor myDF = new ActivationDataFlavor(MimeMultipart.class, "multipart/mixed", "Multipart");

    public Object getContent(DataSource dataSource) throws IOException {
        try {
            MimeMultipart mimeMultipart = new MimeMultipart(dataSource);
            return mimeMultipart;
        }
        catch (MessagingException messagingException) {
            IOException iOException = new IOException("Exception while constructing MimeMultipart");
            iOException.initCause((Throwable)messagingException);
            throw iOException;
        }
    }

    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (this.myDF.equals(dataFlavor)) {
            return this.getContent(dataSource);
        }
        return null;
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] arrdataFlavor = new DataFlavor[]{this.myDF};
        return arrdataFlavor;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeTo(Object object, String string2, OutputStream outputStream) throws IOException {
        if (!(object instanceof MimeMultipart)) return;
        try {
            ((MimeMultipart)object).writeTo(outputStream);
            return;
        }
        catch (MessagingException messagingException) {
            throw new IOException(messagingException.toString());
        }
    }
}

