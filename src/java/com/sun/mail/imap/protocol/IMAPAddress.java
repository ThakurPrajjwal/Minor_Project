/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.Vector
 *  javax.mail.internet.AddressException
 *  javax.mail.internet.InternetAddress
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.util.Vector;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

class IMAPAddress
extends InternetAddress {
    private static final long serialVersionUID = -3835822029483122232L;
    private boolean group = false;
    private InternetAddress[] grouplist;
    private String groupname;

    /*
     * Enabled aggressive block sorting
     */
    IMAPAddress(Response response) throws ParsingException {
        response.skipSpaces();
        if (response.readByte() != 40) {
            throw new ParsingException("ADDRESS parse error");
        }
        this.encodedPersonal = response.readString();
        response.readString();
        String string2 = response.readString();
        String string3 = response.readString();
        if (response.readByte() != 41) {
            throw new ParsingException("ADDRESS parse error");
        }
        if (string3 == null) {
            this.group = true;
            this.groupname = string2;
            if (this.groupname == null) {
                return;
            }
        } else {
            if (string2 == null || string2.length() == 0) {
                this.address = string3;
                return;
            }
            if (string3.length() == 0) {
                this.address = string2;
                return;
            }
            this.address = String.valueOf((Object)string2) + "@" + string3;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.groupname).append(':');
        Vector vector = new Vector();
        do {
            IMAPAddress iMAPAddress;
            if (response.peekByte() == 41 || (iMAPAddress = new IMAPAddress(response)).isEndOfGroup()) {
                stringBuffer.append(';');
                this.address = stringBuffer.toString();
                this.grouplist = new IMAPAddress[vector.size()];
                vector.copyInto((Object[])this.grouplist);
                return;
            }
            if (vector.size() != 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(iMAPAddress.toString());
            vector.addElement((Object)iMAPAddress);
        } while (true);
    }

    public InternetAddress[] getGroup(boolean bl) throws AddressException {
        if (this.grouplist == null) {
            return null;
        }
        return (InternetAddress[])this.grouplist.clone();
    }

    boolean isEndOfGroup() {
        return this.group && this.groupname == null;
    }

    public boolean isGroup() {
        return this.group;
    }
}

