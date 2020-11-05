/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.lang.Object
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.Item;
import java.io.ByteArrayInputStream;

public class RFC822DATA
implements Item {
    static final char[] name = new char[]{'R', 'F', 'C', '8', '2', '2'};
    public ByteArray data;
    public int msgno;

    public RFC822DATA(FetchResponse fetchResponse) throws ParsingException {
        this.msgno = fetchResponse.getNumber();
        fetchResponse.skipSpaces();
        this.data = fetchResponse.readByteArray();
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

