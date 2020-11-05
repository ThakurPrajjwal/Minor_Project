/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Date
 *  java.util.Vector
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.MailDateFormat
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPAddress;
import com.sun.mail.imap.protocol.Item;
import java.util.Date;
import java.util.Vector;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MailDateFormat;

public class ENVELOPE
implements Item {
    private static MailDateFormat mailDateFormat;
    static final char[] name;
    public InternetAddress[] bcc;
    public InternetAddress[] cc;
    public Date date = null;
    public InternetAddress[] from;
    public String inReplyTo;
    public String messageId;
    public int msgno;
    public InternetAddress[] replyTo;
    public InternetAddress[] sender;
    public String subject;
    public InternetAddress[] to;

    static {
        name = new char[]{'E', 'N', 'V', 'E', 'L', 'O', 'P', 'E'};
        mailDateFormat = new MailDateFormat();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ENVELOPE(FetchResponse fetchResponse) throws ParsingException {
        this.msgno = fetchResponse.getNumber();
        fetchResponse.skipSpaces();
        if (fetchResponse.readByte() != 40) {
            throw new ParsingException("ENVELOPE parse error");
        }
        String string2 = fetchResponse.readString();
        if (string2 != null) {
            try {
                this.date = mailDateFormat.parse(string2);
            }
            catch (Exception exception) {}
        }
        this.subject = fetchResponse.readString();
        this.from = this.parseAddressList(fetchResponse);
        this.sender = this.parseAddressList(fetchResponse);
        this.replyTo = this.parseAddressList(fetchResponse);
        this.to = this.parseAddressList(fetchResponse);
        this.cc = this.parseAddressList(fetchResponse);
        this.bcc = this.parseAddressList(fetchResponse);
        this.inReplyTo = fetchResponse.readString();
        this.messageId = fetchResponse.readString();
        if (fetchResponse.readByte() != 41) {
            throw new ParsingException("ENVELOPE parse error");
        }
    }

    private InternetAddress[] parseAddressList(Response response) throws ParsingException {
        response.skipSpaces();
        byte by = response.readByte();
        if (by == 40) {
            Vector vector = new Vector();
            do {
                IMAPAddress iMAPAddress;
                if ((iMAPAddress = new IMAPAddress(response)).isEndOfGroup()) continue;
                vector.addElement((Object)iMAPAddress);
            } while (response.peekByte() != 41);
            response.skip(1);
            Object[] arrobject = new InternetAddress[vector.size()];
            vector.copyInto(arrobject);
            return arrobject;
        }
        if (by == 78 || by == 110) {
            response.skip(2);
            return null;
        }
        throw new ParsingException("ADDRESS parse error");
    }
}

