/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  javax.mail.Session
 *  javax.mail.URLName
 */
package com.sun.mail.smtp;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Session;
import javax.mail.URLName;

public class SMTPSSLTransport
extends SMTPTransport {
    public SMTPSSLTransport(Session session, URLName uRLName) {
        super(session, uRLName, "smtps", 465, true);
    }
}

