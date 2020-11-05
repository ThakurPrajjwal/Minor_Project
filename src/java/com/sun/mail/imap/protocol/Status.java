/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;

public class Status {
    static final String[] standardItems = new String[]{"MESSAGES", "RECENT", "UNSEEN", "UIDNEXT", "UIDVALIDITY"};
    public String mbox = null;
    public int recent = -1;
    public int total = -1;
    public long uidnext = -1L;
    public long uidvalidity = -1L;
    public int unseen = -1;

    /*
     * Enabled aggressive block sorting
     */
    public Status(Response response) throws ParsingException {
        this.mbox = response.readAtomString();
        response.skipSpaces();
        if (response.readByte() != 40) {
            throw new ParsingException("parse error in STATUS");
        }
        do {
            String string2;
            if ((string2 = response.readAtom()).equalsIgnoreCase("MESSAGES")) {
                this.total = response.readNumber();
                continue;
            }
            if (string2.equalsIgnoreCase("RECENT")) {
                this.recent = response.readNumber();
                continue;
            }
            if (string2.equalsIgnoreCase("UIDNEXT")) {
                this.uidnext = response.readLong();
                continue;
            }
            if (string2.equalsIgnoreCase("UIDVALIDITY")) {
                this.uidvalidity = response.readLong();
                continue;
            }
            if (!string2.equalsIgnoreCase("UNSEEN")) continue;
            this.unseen = response.readNumber();
        } while (response.readByte() != 41);
    }

    public static void add(Status status, Status status2) {
        if (status2.total != -1) {
            status.total = status2.total;
        }
        if (status2.recent != -1) {
            status.recent = status2.recent;
        }
        if (status2.uidnext != -1L) {
            status.uidnext = status2.uidnext;
        }
        if (status2.uidvalidity != -1L) {
            status.uidvalidity = status2.uidvalidity;
        }
        if (status2.unseen != -1) {
            status.unseen = status2.unseen;
        }
    }
}

