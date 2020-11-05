/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 *  javax.mail.Message
 *  javax.mail.MessagingException
 */
package com.sun.mail.imap;

import com.sun.mail.iap.Literal;
import com.sun.mail.imap.LengthCounter;
import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.mail.Message;
import javax.mail.MessagingException;

class MessageLiteral
implements Literal {
    private byte[] buf;
    private Message msg;
    private int msgSize = -1;

    public MessageLiteral(Message message, int n) throws MessagingException, IOException {
        this.msg = message;
        LengthCounter lengthCounter = new LengthCounter(n);
        CRLFOutputStream cRLFOutputStream = new CRLFOutputStream(lengthCounter);
        message.writeTo((OutputStream)cRLFOutputStream);
        cRLFOutputStream.flush();
        this.msgSize = lengthCounter.getSize();
        this.buf = lengthCounter.getBytes();
    }

    @Override
    public int size() {
        return this.msgSize;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        CRLFOutputStream cRLFOutputStream;
        void var2_4;
        try {
            if (this.buf != null) {
                outputStream.write(this.buf, 0, this.msgSize);
                return;
            }
            cRLFOutputStream = new CRLFOutputStream(outputStream);
        }
        catch (MessagingException messagingException) {
            do {
                throw new IOException("MessagingException while appending message: " + var2_4);
                break;
            } while (true);
        }
        try {
            this.msg.writeTo((OutputStream)cRLFOutputStream);
            return;
        }
        catch (MessagingException messagingException) {
            throw new IOException("MessagingException while appending message: " + var2_4);
        }
    }
}

