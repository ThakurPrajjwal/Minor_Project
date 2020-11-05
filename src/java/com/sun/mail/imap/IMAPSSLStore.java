/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  javax.mail.Session
 *  javax.mail.URLName
 */
package com.sun.mail.imap;

import com.sun.mail.imap.IMAPStore;
import javax.mail.Session;
import javax.mail.URLName;

public class IMAPSSLStore
extends IMAPStore {
    public IMAPSSLStore(Session session, URLName uRLName) {
        super(session, uRLName, "imaps", 993, true);
    }
}

