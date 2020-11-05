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
 */
package com.sun.mail.dsn;

import com.sun.mail.dsn.MultipartReport;
import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;

public class multipart_report
implements DataContentHandler {
    private ActivationDataFlavor myDF = new ActivationDataFlavor(MultipartReport.class, "multipart/report", "Multipart Report");

    public Object getContent(DataSource dataSource) throws IOException {
        try {
            MultipartReport multipartReport = new MultipartReport(dataSource);
            return multipartReport;
        }
        catch (MessagingException messagingException) {
            IOException iOException = new IOException("Exception while constructing MultipartReport");
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
        if (!(object instanceof MultipartReport)) return;
        try {
            ((MultipartReport)((Object)object)).writeTo(outputStream);
            return;
        }
        catch (MessagingException messagingException) {
            throw new IOException(messagingException.toString());
        }
    }
}

