/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.lang.Object
 *  java.lang.String
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.Item;
import java.io.ByteArrayInputStream;

public class BODY
implements Item {
    static final char[] name = new char[]{'B', 'O', 'D', 'Y'};
    public ByteArray data;
    public int msgno;
    public int origin = 0;
    public String section;

    public BODY(FetchResponse fetchResponse) throws ParsingException {
        byte by;
        this.msgno = fetchResponse.getNumber();
        fetchResponse.skipSpaces();
        do {
            if ((by = fetchResponse.readByte()) != 93) continue;
            if (fetchResponse.readByte() == 60) {
                this.origin = fetchResponse.readNumber();
                fetchResponse.skip(1);
            }
            this.data = fetchResponse.readByteArray();
            return;
        } while (by != 0);
        throw new ParsingException("BODY parse error: missing ``]'' at section end");
    }

    public ByteArray getByteArray() {
        return this.data;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
        if (this.data != null) {
            return this.data.toByteArrayInputStream();
        }
        return null;
    }
}

