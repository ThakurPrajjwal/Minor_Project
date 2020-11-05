/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 *  javax.activation.DataSource
 *  javax.mail.BodyPart
 *  javax.mail.MessagingException
 *  javax.mail.Multipart
 *  javax.mail.internet.ContentType
 *  javax.mail.internet.InternetHeaders
 *  javax.mail.internet.MimeBodyPart
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimeMultipart
 */
package com.sun.mail.dsn;

import com.sun.mail.dsn.DeliveryStatus;
import com.sun.mail.dsn.MessageHeaders;
import java.io.IOException;
import java.util.Vector;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MultipartReport
extends MimeMultipart {
    protected boolean constructed;

    public MultipartReport() throws MessagingException {
        super("report");
        this.setBodyPart((BodyPart)new MimeBodyPart(), 0);
        this.setBodyPart((BodyPart)new MimeBodyPart(), 1);
        this.constructed = true;
    }

    public MultipartReport(String string2, DeliveryStatus deliveryStatus) throws MessagingException {
        super("report");
        ContentType contentType = new ContentType(this.contentType);
        contentType.setParameter("report-type", "delivery-status");
        this.contentType = contentType.toString();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(string2);
        this.setBodyPart((BodyPart)mimeBodyPart, 0);
        MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
        mimeBodyPart2.setContent((Object)deliveryStatus, "message/delivery-status");
        this.setBodyPart((BodyPart)mimeBodyPart2, 1);
        this.constructed = true;
    }

    public MultipartReport(String string2, DeliveryStatus deliveryStatus, InternetHeaders internetHeaders) throws MessagingException {
        this(string2, deliveryStatus);
        if (internetHeaders != null) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent((Object)new MessageHeaders(internetHeaders), "text/rfc822-headers");
            this.setBodyPart((BodyPart)mimeBodyPart, 2);
        }
    }

    public MultipartReport(String string2, DeliveryStatus deliveryStatus, MimeMessage mimeMessage) throws MessagingException {
        this(string2, deliveryStatus);
        if (mimeMessage != null) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent((Object)mimeMessage, "message/rfc822");
            this.setBodyPart((BodyPart)mimeBodyPart, 2);
        }
    }

    public MultipartReport(DataSource dataSource) throws MessagingException {
        super(dataSource);
        this.parse();
        this.constructed = true;
    }

    private void setBodyPart(BodyPart bodyPart, int n) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            if (this.parts == null) {
                this.parts = new Vector();
            }
            if (n < this.parts.size()) {
                super.removeBodyPart(n);
            }
            super.addBodyPart(bodyPart, n);
            return;
        }
    }

    public void addBodyPart(BodyPart bodyPart) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            if (!this.constructed) {
                super.addBodyPart(bodyPart);
                return;
            }
            throw new MessagingException("Can't add body parts to multipart/report 1");
        }
    }

    public void addBodyPart(BodyPart bodyPart, int n) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            throw new MessagingException("Can't add body parts to multipart/report 2");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DeliveryStatus getDeliveryStatus() throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            int n = this.getCount();
            DeliveryStatus deliveryStatus = null;
            if (n < 2) return deliveryStatus;
            BodyPart bodyPart = this.getBodyPart(1);
            boolean bl = bodyPart.isMimeType("message/delivery-status");
            deliveryStatus = null;
            if (!bl) return deliveryStatus;
            try {
                return (DeliveryStatus)bodyPart.getContent();
            }
            catch (IOException iOException) {
                throw new MessagingException("IOException getting DeliveryStatus", (Exception)((Object)iOException));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MimeMessage getReturnedMessage() throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            int n = this.getCount();
            MimeMessage mimeMessage = null;
            if (n < 3) return mimeMessage;
            BodyPart bodyPart = this.getBodyPart(2);
            if (!bodyPart.isMimeType("message/rfc822")) {
                boolean bl = bodyPart.isMimeType("text/rfc822-headers");
                mimeMessage = null;
                if (!bl) return mimeMessage;
            }
            try {
                return (MimeMessage)bodyPart.getContent();
            }
            catch (IOException iOException) {
                throw new MessagingException("IOException getting ReturnedMessage", (Exception)((Object)iOException));
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getText() throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            int n;
            Multipart multipart;
            BodyPart bodyPart;
            block8 : {
                bodyPart = this.getBodyPart(0);
                if (!bodyPart.isMimeType("text/plain")) break block8;
                return (String)bodyPart.getContent();
            }
            try {
                if (!bodyPart.isMimeType("multipart/alternative")) return null;
                multipart = (Multipart)bodyPart.getContent();
                n = 0;
            }
            catch (IOException iOException) {
                throw new MessagingException("Exception getting text content", (Exception)((Object)iOException));
            }
            do {
                block9 : {
                    if (n >= multipart.getCount()) return null;
                    BodyPart bodyPart2 = multipart.getBodyPart(n);
                    if (!bodyPart2.isMimeType("text/plain")) break block9;
                    return (String)bodyPart2.getContent();
                }
                ++n;
            } while (true);
        }
    }

    public MimeBodyPart getTextBodyPart() throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            MimeBodyPart mimeBodyPart = (MimeBodyPart)this.getBodyPart(0);
            return mimeBodyPart;
        }
    }

    public void removeBodyPart(int n) throws MessagingException {
        throw new MessagingException("Can't remove body parts from multipart/report");
    }

    public boolean removeBodyPart(BodyPart bodyPart) throws MessagingException {
        throw new MessagingException("Can't remove body parts from multipart/report");
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent((Object)deliveryStatus, "message/delivery-status");
            this.setBodyPart((BodyPart)mimeBodyPart, 2);
            ContentType contentType = new ContentType(this.contentType);
            contentType.setParameter("report-type", "delivery-status");
            this.contentType = contentType.toString();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setReturnedMessage(MimeMessage mimeMessage) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            if (mimeMessage == null) {
                (BodyPart)this.parts.elementAt(2);
                super.removeBodyPart(2);
            } else {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                if (mimeMessage instanceof MessageHeaders) {
                    mimeBodyPart.setContent((Object)mimeMessage, "text/rfc822-headers");
                } else {
                    mimeBodyPart.setContent((Object)mimeMessage, "message/rfc822");
                }
                this.setBodyPart((BodyPart)mimeBodyPart, 2);
            }
            return;
        }
    }

    public void setSubType(String string2) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            throw new MessagingException("Can't change subtype of MultipartReport");
        }
    }

    public void setText(String string2) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(string2);
            this.setBodyPart((BodyPart)mimeBodyPart, 0);
            return;
        }
    }

    public void setTextBodyPart(MimeBodyPart mimeBodyPart) throws MessagingException {
        MultipartReport multipartReport = this;
        synchronized (multipartReport) {
            this.setBodyPart((BodyPart)mimeBodyPart, 0);
            return;
        }
    }
}

