/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.Item;

public class UID
implements Item {
    static final char[] name = new char[]{'U', 'I', 'D'};
    public int seqnum;
    public long uid;

    public UID(FetchResponse fetchResponse) throws ParsingException {
        this.seqnum = fetchResponse.getNumber();
        fetchResponse.skipSpaces();
        this.uid = fetchResponse.readLong();
    }
}

