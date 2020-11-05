/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  javax.mail.Flags
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.FLAGS;
import com.sun.mail.imap.protocol.IMAPResponse;
import javax.mail.Flags;

public class MailboxInfo {
    public Flags availableFlags = null;
    public int first = -1;
    public int mode;
    public Flags permanentFlags = null;
    public int recent = -1;
    public int total = -1;
    public long uidnext = -1L;
    public long uidvalidity = -1L;

    /*
     * Enabled aggressive block sorting
     */
    public MailboxInfo(Response[] arrresponse) throws ParsingException {
        int n = 0;
        do {
            if (n >= arrresponse.length) {
                if (this.permanentFlags == null) {
                    if (this.availableFlags == null) {
                        this.permanentFlags = new Flags();
                        return;
                    }
                    this.permanentFlags = new Flags(this.availableFlags);
                }
                return;
            }
            if (arrresponse[n] != null && arrresponse[n] instanceof IMAPResponse) {
                IMAPResponse iMAPResponse = (IMAPResponse)arrresponse[n];
                if (iMAPResponse.keyEquals("EXISTS")) {
                    this.total = iMAPResponse.getNumber();
                    arrresponse[n] = null;
                } else if (iMAPResponse.keyEquals("RECENT")) {
                    this.recent = iMAPResponse.getNumber();
                    arrresponse[n] = null;
                } else if (iMAPResponse.keyEquals("FLAGS")) {
                    this.availableFlags = new FLAGS(iMAPResponse);
                    arrresponse[n] = null;
                } else if (iMAPResponse.isUnTagged() && iMAPResponse.isOK()) {
                    iMAPResponse.skipSpaces();
                    if (iMAPResponse.readByte() != 91) {
                        iMAPResponse.reset();
                    } else {
                        boolean bl = true;
                        String string2 = iMAPResponse.readAtom();
                        if (string2.equalsIgnoreCase("UNSEEN")) {
                            this.first = iMAPResponse.readNumber();
                        } else if (string2.equalsIgnoreCase("UIDVALIDITY")) {
                            this.uidvalidity = iMAPResponse.readLong();
                        } else if (string2.equalsIgnoreCase("PERMANENTFLAGS")) {
                            this.permanentFlags = new FLAGS(iMAPResponse);
                        } else if (string2.equalsIgnoreCase("UIDNEXT")) {
                            this.uidnext = iMAPResponse.readLong();
                        } else {
                            bl = false;
                        }
                        if (bl) {
                            arrresponse[n] = null;
                        } else {
                            iMAPResponse.reset();
                        }
                    }
                }
            }
            ++n;
        } while (true);
    }
}

