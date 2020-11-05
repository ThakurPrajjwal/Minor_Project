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
 *  javax.activation.ActivationDataFlavor
 *  javax.activation.DataContentHandler
 *  javax.activation.DataSource
 *  javax.mail.MessagingException
 */
package com.sun.mail.dsn;

import com.sun.mail.dsn.DeliveryStatus;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;

public class message_deliverystatus
implements DataContentHandler {
    ActivationDataFlavor ourDataFlavor = new ActivationDataFlavor(DeliveryStatus.class, "message/delivery-status", "Delivery Status");

    public Object getContent(DataSource dataSource) throws IOException {
        try {
            DeliveryStatus deliveryStatus = new DeliveryStatus(dataSource.getInputStream());
            return deliveryStatus;
        }
        catch (MessagingException messagingException) {
            throw new IOException("Exception creating DeliveryStatus in message/devliery-status DataContentHandler: " + messagingException.toString());
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
        if (object instanceof DeliveryStatus) {
            DeliveryStatus deliveryStatus = (DeliveryStatus)object;
            try {
                deliveryStatus.writeTo(outputStream);
                return;
            }
            catch (MessagingException messagingException) {
                throw new IOException(messagingException.toString());
            }
        }
        throw new IOException("unsupported object");
    }
}

