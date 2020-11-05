/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.BASE64MailboxDecoder;
import java.util.Vector;

public class Namespaces {
    public Namespace[] otherUsers;
    public Namespace[] personal;
    public Namespace[] shared;

    public Namespaces(Response response) throws ProtocolException {
        this.personal = this.getNamespaces(response);
        this.otherUsers = this.getNamespaces(response);
        this.shared = this.getNamespaces(response);
    }

    private Namespace[] getNamespaces(Response response) throws ProtocolException {
        response.skipSpaces();
        if (response.peekByte() == 40) {
            Vector vector = new Vector();
            response.readByte();
            do {
                vector.addElement((Object)new Namespace(response));
            } while (response.peekByte() != 41);
            response.readByte();
            Object[] arrobject = new Namespace[vector.size()];
            vector.copyInto(arrobject);
            return arrobject;
        }
        String string2 = response.readAtom();
        if (string2 == null) {
            throw new ProtocolException("Expected NIL, got null");
        }
        if (!string2.equalsIgnoreCase("NIL")) {
            throw new ProtocolException("Expected NIL, got " + string2);
        }
        return null;
    }

    public static class Namespace {
        public char delimiter;
        public String prefix;

        public Namespace(Response response) throws ProtocolException {
            if (response.readByte() != 40) {
                throw new ProtocolException("Missing '(' at start of Namespace");
            }
            this.prefix = BASE64MailboxDecoder.decode(response.readString());
            response.skipSpaces();
            if (response.peekByte() == 34) {
                response.readByte();
                this.delimiter = (char)response.readByte();
                if (this.delimiter == '\\') {
                    this.delimiter = (char)response.readByte();
                }
                if (response.readByte() != 34) {
                    throw new ProtocolException("Missing '\"' at end of QUOTED_CHAR");
                }
            } else {
                String string2 = response.readAtom();
                if (string2 == null) {
                    throw new ProtocolException("Expected NIL, got null");
                }
                if (!string2.equalsIgnoreCase("NIL")) {
                    throw new ProtocolException("Expected NIL, got " + string2);
                }
                this.delimiter = '\u0000';
            }
            if (response.peekByte() != 41) {
                response.skipSpaces();
                response.readString();
                response.skipSpaces();
                response.readStringList();
            }
            if (response.readByte() != 41) {
                throw new ProtocolException("Missing ')' at end of Namespace");
            }
        }
    }

}

