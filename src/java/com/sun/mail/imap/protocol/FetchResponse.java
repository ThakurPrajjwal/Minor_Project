/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.FLAGS;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.INTERNALDATE;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.RFC822DATA;
import com.sun.mail.imap.protocol.RFC822SIZE;
import com.sun.mail.imap.protocol.UID;
import java.io.IOException;
import java.util.Vector;

public class FetchResponse
extends IMAPResponse {
    private static final char[] HEADER = new char[]{'.', 'H', 'E', 'A', 'D', 'E', 'R'};
    private static final char[] TEXT = new char[]{'.', 'T', 'E', 'X', 'T'};
    private Item[] items;

    public FetchResponse(Protocol protocol) throws IOException, ProtocolException {
        super(protocol);
        this.parse();
    }

    public FetchResponse(IMAPResponse iMAPResponse) throws IOException, ProtocolException {
        super(iMAPResponse);
        this.parse();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static Item getItem(Response[] var0, int var1_1, Class var2_2) {
        if (var0 == null) {
            return null;
        }
        var3_3 = 0;
        block0 : do {
            if (var3_3 >= var0.length) {
                return null;
            }
            if (var0[var3_3] == null || !(var0[var3_3] instanceof FetchResponse) || ((FetchResponse)var0[var3_3]).getNumber() != var1_1) ** GOTO lbl12
            var4_4 = (FetchResponse)var0[var3_3];
            var5_5 = 0;
            do {
                block8 : {
                    if (var5_5 < var4_4.items.length) break block8;
lbl12: // 2 sources:
                    ++var3_3;
                    continue block0;
                }
                if (var2_2.isInstance((Object)var4_4.items[var5_5])) {
                    return var4_4.items[var5_5];
                }
                ++var5_5;
            } while (true);
            break;
        } while (true);
    }

    private boolean match(char[] arrc) {
        int n = arrc.length;
        int n2 = this.index;
        int n3 = 0;
        while (n3 < n) {
            byte[] arrby = this.buffer;
            int n4 = n2 + 1;
            char c = Character.toUpperCase((char)((char)arrby[n2]));
            int n5 = n3 + 1;
            if (c != arrc[n3]) {
                return false;
            }
            n2 = n4;
            n3 = n5;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parse() throws ParsingException {
        this.skipSpaces();
        if (this.buffer[this.index] != 40) {
            throw new ParsingException("error in FETCH parsing, missing '(' at index " + this.index);
        }
        Vector vector = new Vector();
        Object var2_2 = null;
        do {
            void var2_3;
            this.index = 1 + this.index;
            if (this.index >= this.size) {
                throw new ParsingException("error in FETCH parsing, ran off end of buffer, size " + this.size);
            }
            switch (this.buffer[this.index]) {
                case 69: {
                    if (!this.match(ENVELOPE.name)) break;
                    this.index += ENVELOPE.name.length;
                    ENVELOPE eNVELOPE = new ENVELOPE(this);
                    break;
                }
                case 70: {
                    if (!this.match(FLAGS.name)) break;
                    this.index += FLAGS.name.length;
                    FLAGS fLAGS = new FLAGS(this);
                    break;
                }
                case 73: {
                    if (!this.match(INTERNALDATE.name)) break;
                    this.index += INTERNALDATE.name.length;
                    INTERNALDATE iNTERNALDATE = new INTERNALDATE(this);
                    break;
                }
                case 66: {
                    if (!this.match(BODY.name)) break;
                    if (this.buffer[4 + this.index] == 91) {
                        this.index += BODY.name.length;
                        BODY bODY = new BODY(this);
                        break;
                    }
                    this.index = this.match(BODYSTRUCTURE.name) ? (this.index += BODYSTRUCTURE.name.length) : (this.index += BODY.name.length);
                    BODYSTRUCTURE bODYSTRUCTURE = new BODYSTRUCTURE(this);
                    break;
                }
                case 82: {
                    if (this.match(RFC822SIZE.name)) {
                        this.index += RFC822SIZE.name.length;
                        RFC822SIZE rFC822SIZE = new RFC822SIZE(this);
                        break;
                    }
                    if (!this.match(RFC822DATA.name)) break;
                    this.index += RFC822DATA.name.length;
                    if (this.match(HEADER)) {
                        this.index += HEADER.length;
                    } else if (this.match(TEXT)) {
                        this.index += TEXT.length;
                    }
                    RFC822DATA rFC822DATA = new RFC822DATA(this);
                    break;
                }
                case 85: {
                    if (!this.match(UID.name)) break;
                    this.index += UID.name.length;
                    UID uID = new UID(this);
                    break;
                }
            }
            if (var2_3 == null) continue;
            vector.addElement((Object)var2_3);
        } while (this.buffer[this.index] != 41);
        this.index = 1 + this.index;
        this.items = new Item[vector.size()];
        vector.copyInto((Object[])this.items);
    }

    public Item getItem(int n) {
        return this.items[n];
    }

    public Item getItem(Class class_) {
        int n = 0;
        while (n < this.items.length) {
            if (class_.isInstance((Object)this.items[n])) {
                return this.items[n];
            }
            ++n;
        }
        return null;
    }

    public int getItemCount() {
        return this.items.length;
    }
}

