/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.InputStream
 *  java.lang.IllegalArgumentException
 *  java.lang.String
 *  java.lang.StringBuffer
 *  javax.mail.MessagingException
 *  javax.mail.Session
 *  javax.mail.internet.MimeMessage
 */
package com.sun.mail.smtp;

import java.io.InputStream;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class SMTPMessage
extends MimeMessage {
    public static final int NOTIFY_DELAY = 4;
    public static final int NOTIFY_FAILURE = 2;
    public static final int NOTIFY_NEVER = -1;
    public static final int NOTIFY_SUCCESS = 1;
    public static final int RETURN_FULL = 1;
    public static final int RETURN_HDRS = 2;
    private static final String[] returnOptionString;
    private boolean allow8bitMIME = false;
    private String envelopeFrom;
    private String extension = null;
    private int notifyOptions = 0;
    private int returnOption = 0;
    private boolean sendPartial = false;
    private String submitter = null;

    static {
        String[] arrstring = new String[3];
        arrstring[1] = "FULL";
        arrstring[2] = "HDRS";
        returnOptionString = arrstring;
    }

    public SMTPMessage(Session session) {
        super(session);
    }

    public SMTPMessage(Session session, InputStream inputStream) throws MessagingException {
        super(session, inputStream);
    }

    public SMTPMessage(MimeMessage mimeMessage) throws MessagingException {
        super(mimeMessage);
    }

    public boolean getAllow8bitMIME() {
        return this.allow8bitMIME;
    }

    String getDSNNotify() {
        if (this.notifyOptions == 0) {
            return null;
        }
        if (this.notifyOptions == -1) {
            return "NEVER";
        }
        StringBuffer stringBuffer = new StringBuffer();
        if ((1 & this.notifyOptions) != 0) {
            stringBuffer.append("SUCCESS");
        }
        if ((2 & this.notifyOptions) != 0) {
            if (stringBuffer.length() != 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append("FAILURE");
        }
        if ((4 & this.notifyOptions) != 0) {
            if (stringBuffer.length() != 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append("DELAY");
        }
        return stringBuffer.toString();
    }

    String getDSNRet() {
        return returnOptionString[this.returnOption];
    }

    public String getEnvelopeFrom() {
        return this.envelopeFrom;
    }

    public String getMailExtension() {
        return this.extension;
    }

    public int getNotifyOptions() {
        return this.notifyOptions;
    }

    public int getReturnOption() {
        return this.returnOption;
    }

    public boolean getSendPartial() {
        return this.sendPartial;
    }

    public String getSubmitter() {
        return this.submitter;
    }

    public void setAllow8bitMIME(boolean bl) {
        this.allow8bitMIME = bl;
    }

    public void setEnvelopeFrom(String string2) {
        this.envelopeFrom = string2;
    }

    public void setMailExtension(String string2) {
        this.extension = string2;
    }

    public void setNotifyOptions(int n) {
        if (n < -1 || n >= 8) {
            throw new IllegalArgumentException("Bad return option");
        }
        this.notifyOptions = n;
    }

    public void setReturnOption(int n) {
        if (n < 0 || n > 2) {
            throw new IllegalArgumentException("Bad return option");
        }
        this.returnOption = n;
    }

    public void setSendPartial(boolean bl) {
        this.sendPartial = bl;
    }

    public void setSubmitter(String string2) {
        this.submitter = string2;
    }
}

