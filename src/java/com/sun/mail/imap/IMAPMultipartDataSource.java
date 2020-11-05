/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 *  javax.mail.BodyPart
 *  javax.mail.MessagingException
 *  javax.mail.MultipartDataSource
 *  javax.mail.internet.MimePart
 *  javax.mail.internet.MimePartDataSource
 */
package com.sun.mail.imap;

import com.sun.mail.imap.IMAPBodyPart;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import java.util.Vector;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;

public class IMAPMultipartDataSource
extends MimePartDataSource
implements MultipartDataSource {
    private Vector parts;

    /*
     * Enabled aggressive block sorting
     */
    protected IMAPMultipartDataSource(MimePart mimePart, BODYSTRUCTURE[] arrbODYSTRUCTURE, String string2, IMAPMessage iMAPMessage) {
        super(mimePart);
        this.parts = new Vector(arrbODYSTRUCTURE.length);
        int n = 0;
        while (n < arrbODYSTRUCTURE.length) {
            Vector vector = this.parts;
            BODYSTRUCTURE bODYSTRUCTURE = arrbODYSTRUCTURE[n];
            String string3 = string2 == null ? Integer.toString((int)(n + 1)) : String.valueOf((Object)string2) + "." + Integer.toString((int)(n + 1));
            vector.addElement((Object)new IMAPBodyPart(bODYSTRUCTURE, string3, iMAPMessage));
            ++n;
        }
        return;
    }

    public BodyPart getBodyPart(int n) throws MessagingException {
        return (BodyPart)this.parts.elementAt(n);
    }

    public int getCount() {
        return this.parts.size();
    }
}

