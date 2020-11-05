/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Properties
 *  javax.activation.ActivationDataFlavor
 *  javax.activation.DataContentHandler
 *  javax.activation.DataSource
 *  javax.mail.Message
 *  javax.mail.MessageAware
 *  javax.mail.MessageContext
 *  javax.mail.MessagingException
 *  javax.mail.Session
 *  javax.mail.internet.MimeMessage
 */
package com.sun.mail.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import myjava.awt.datatransfer.DataFlavor;

public class message_rfc822
implements DataContentHandler {
    ActivationDataFlavor ourDataFlavor = new ActivationDataFlavor(Message.class, "message/rfc822", "Message");

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object getContent(DataSource dataSource) throws IOException {
        try {
            Session session;
            Session session2;
            if (dataSource instanceof MessageAware) {
                session = ((MessageAware)dataSource).getMessageContext().getSession();
                do {
                    return new MimeMessage(session, dataSource.getInputStream());
                    break;
                } while (true);
            }
            session = session2 = Session.getDefaultInstance((Properties)new Properties(), null);
            return new MimeMessage(session, dataSource.getInputStream());
        }
        catch (MessagingException messagingException) {
            throw new IOException("Exception creating MimeMessage in message/rfc822 DataContentHandler: " + messagingException.toString());
        }
    }

    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (this.ourDataFlavor.equals(dataFlavor)) {
            return this.getContent(dataSource);
        }
        return null;
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] arrdataFlavor = new DataFlavor[]{this.ourDataFlavor};
        return arrdataFlavor;
    }

    public void writeTo(Object object, String string2, OutputStream outputStream) throws IOException {
        if (object instanceof Message) {
            Message message = (Message)object;
            try {
                message.writeTo(outputStream);
                return;
            }
            catch (MessagingException messagingException) {
                throw new IOException(messagingException.toString());
            }
        }
        throw new IOException("unsupported object");
    }
}

