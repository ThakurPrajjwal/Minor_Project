/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.util.ASCIIUtility;
import java.io.IOException;
import java.util.Vector;

public class IMAPResponse
extends Response {
    private String key;
    private int number;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public IMAPResponse(Protocol protocol) throws IOException, ProtocolException {
        super(protocol);
        if (!this.isUnTagged() || this.isOK() || this.isNO() || this.isBAD() || this.isBYE()) return;
        this.key = this.readAtom();
        try {
            this.number = Integer.parseInt((String)this.key);
            this.key = this.readAtom();
            return;
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    public IMAPResponse(IMAPResponse iMAPResponse) {
        super(iMAPResponse);
        this.key = iMAPResponse.key;
        this.number = iMAPResponse.number;
    }

    public static IMAPResponse readResponse(Protocol protocol) throws IOException, ProtocolException {
        IMAPResponse iMAPResponse = new IMAPResponse(protocol);
        if (iMAPResponse.keyEquals("FETCH")) {
            iMAPResponse = new FetchResponse(iMAPResponse);
        }
        return iMAPResponse;
    }

    public String getKey() {
        return this.key;
    }

    public int getNumber() {
        return this.number;
    }

    public boolean keyEquals(String string2) {
        return this.key != null && this.key.equalsIgnoreCase(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String[] readSimpleList() {
        Vector vector;
        int n;
        block5 : {
            this.skipSpaces();
            if (this.buffer[this.index] == 40) {
                this.index = 1 + this.index;
                vector = new Vector();
                int n2 = this.index;
                do {
                    if (this.buffer[this.index] == 41) {
                        if (this.index > n2) {
                            vector.addElement((Object)ASCIIUtility.toString(this.buffer, n2, this.index));
                        }
                        this.index = 1 + this.index;
                        n = vector.size();
                        if (n <= 0) break;
                        break block5;
                    }
                    if (this.buffer[this.index] == 32) {
                        vector.addElement((Object)ASCIIUtility.toString(this.buffer, n2, this.index));
                        n2 = 1 + this.index;
                    }
                    this.index = 1 + this.index;
                } while (true);
            }
            return null;
        }
        Object[] arrobject = new String[n];
        vector.copyInto(arrobject);
        return arrobject;
    }
}

