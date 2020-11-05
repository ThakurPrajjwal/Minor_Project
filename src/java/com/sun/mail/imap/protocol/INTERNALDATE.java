/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.text.FieldPosition
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.Locale
 *  javax.mail.internet.MailDateFormat
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.Item;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.mail.internet.MailDateFormat;

public class INTERNALDATE
implements Item {
    private static SimpleDateFormat df;
    private static MailDateFormat mailDateFormat;
    static final char[] name;
    protected Date date;
    public int msgno;

    static {
        name = new char[]{'I', 'N', 'T', 'E', 'R', 'N', 'A', 'L', 'D', 'A', 'T', 'E'};
        mailDateFormat = new MailDateFormat();
        df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss ", Locale.US);
    }

    public INTERNALDATE(FetchResponse fetchResponse) throws ParsingException {
        this.msgno = fetchResponse.getNumber();
        fetchResponse.skipSpaces();
        String string2 = fetchResponse.readString();
        if (string2 == null) {
            throw new ParsingException("INTERNALDATE is NIL");
        }
        try {
            this.date = mailDateFormat.parse(string2);
            return;
        }
        catch (ParseException parseException) {
            throw new ParsingException("INTERNALDATE parse error");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String format(Date date) {
        SimpleDateFormat simpleDateFormat;
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat2 = simpleDateFormat = df;
        synchronized (simpleDateFormat2) {
            df.format(date, stringBuffer, new FieldPosition(0));
        }
        int n = -date.getTimezoneOffset();
        if (n < 0) {
            stringBuffer.append('-');
            n = -n;
        } else {
            stringBuffer.append('+');
        }
        int n2 = n / 60;
        int n3 = n % 60;
        stringBuffer.append(Character.forDigit((int)(n2 / 10), (int)10));
        stringBuffer.append(Character.forDigit((int)(n2 % 10), (int)10));
        stringBuffer.append(Character.forDigit((int)(n3 / 10), (int)10));
        stringBuffer.append(Character.forDigit((int)(n3 % 10), (int)10));
        return stringBuffer.toString();
    }

    public Date getDate() {
        return this.date;
    }
}

