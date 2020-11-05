/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.InputStream
 *  java.lang.String
 *  javax.activation.DataHandler
 *  javax.mail.MessagingException
 *  javax.mail.Session
 *  javax.mail.internet.InternetHeaders
 *  javax.mail.internet.MimeMessage
 */
package com.sun.mail.dsn;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

public class MessageHeaders
extends MimeMessage {
    public MessageHeaders() throws MessagingException {
        super(null);
        this.content = new byte[0];
    }

    public MessageHeaders(InputStream inputStream) throws MessagingException {
        super(null, inputStream);
        this.content = new byte[0];
    }

    public MessageHeaders(InternetHeaders internetHeaders) throws MessagingException {
        super(null);
        this.headers = internetHeaders;
        this.content = new byte[0];
    }

    protected InputStream getContentStream() {
        return new ByteArrayInputStream(this.content);
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.content);
    }

    public int getSize() {
        return 0;
    }

    public void setDataHandler(DataHandler dataHandler) throws MessagingException {
        throw new MessagingException("Can't set content for MessageHeaders");
    }
}

