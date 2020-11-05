/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.UnsupportedEncodingException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  javax.activation.ActivationDataFlavor
 *  javax.activation.DataContentHandler
 *  javax.activation.DataSource
 *  javax.mail.MessagingException
 *  javax.mail.internet.ContentType
 *  javax.mail.internet.MimeUtility
 */
package com.sun.mail.dsn;

import com.sun.mail.dsn.MessageHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import myjava.awt.datatransfer.DataFlavor;

public class text_rfc822headers
implements DataContentHandler {
    private static ActivationDataFlavor myDF = new ActivationDataFlavor(MessageHeaders.class, "text/rfc822-headers", "RFC822 headers");
    private static ActivationDataFlavor myDFs = new ActivationDataFlavor(String.class, "text/rfc822-headers", "RFC822 headers");

    private String getCharset(String string2) {
        String string3;
        block3 : {
            try {
                string3 = new ContentType(string2).getParameter("charset");
                if (string3 != null) break block3;
                string3 = "us-ascii";
            }
            catch (Exception exception) {
                return null;
            }
        }
        String string4 = MimeUtility.javaCharset((String)string3);
        return string4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object getStringContent(DataSource dataSource) throws IOException {
        String string2 = null;
        try {}
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException(string2);
        }
        string2 = this.getCharset(dataSource.getContentType());
        InputStreamReader inputStreamReader = new InputStreamReader(dataSource.getInputStream(), string2);
        int n = 0;
        char[] arrc = new char[1024];
        int n2;
        while ((n2 = inputStreamReader.read(arrc, n, arrc.length - n)) != -1) {
            if ((n += n2) < arrc.length) continue;
            int n3 = arrc.length;
            int n4 = n3 < 262144 ? n3 + n3 : n3 + 262144;
            char[] arrc2 = new char[n4];
            System.arraycopy((Object)arrc, (int)0, (Object)arrc2, (int)0, (int)n);
            arrc = arrc2;
        }
        return new String(arrc, 0, n);
    }

    public Object getContent(DataSource dataSource) throws IOException {
        try {
            MessageHeaders messageHeaders = new MessageHeaders(dataSource.getInputStream());
            return messageHeaders;
        }
        catch (MessagingException messagingException) {
            throw new IOException("Exception creating MessageHeaders: " + (Object)((Object)messagingException));
        }
    }

    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (myDF.equals(dataFlavor)) {
            return this.getContent(dataSource);
        }
        if (myDFs.equals(dataFlavor)) {
            return this.getStringContent(dataSource);
        }
        return null;
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] arrdataFlavor = new DataFlavor[]{myDF, myDFs};
        return arrdataFlavor;
    }

    public void writeTo(Object object, String string2, OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter;
        if (object instanceof MessageHeaders) {
            MessageHeaders messageHeaders = (MessageHeaders)((Object)object);
            try {
                messageHeaders.writeTo(outputStream);
                return;
            }
            catch (MessagingException messagingException) {
                Exception exception = messagingException.getNextException();
                if (exception instanceof IOException) {
                    throw (IOException)((Object)exception);
                }
                throw new IOException("Exception writing headers: " + (Object)((Object)messagingException));
            }
        }
        if (!(object instanceof String)) {
            throw new IOException("\"" + myDFs.getMimeType() + "\" DataContentHandler requires String object, " + "was given object of type " + object.getClass().toString());
        }
        String string3 = null;
        try {
            string3 = this.getCharset(string2);
            outputStreamWriter = new OutputStreamWriter(outputStream, string3);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException(string3);
        }
        String string4 = (String)object;
        outputStreamWriter.write(string4, 0, string4.length());
        outputStreamWriter.flush();
    }
}

