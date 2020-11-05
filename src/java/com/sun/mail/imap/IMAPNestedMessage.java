/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  javax.mail.Flags
 *  javax.mail.FolderClosedException
 *  javax.mail.MessageRemovedException
 *  javax.mail.MessagingException
 *  javax.mail.MethodNotSupportedException
 *  javax.mail.Session
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.IMAPProtocol;
import javax.mail.Flags;
import javax.mail.FolderClosedException;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Session;

public class IMAPNestedMessage
extends IMAPMessage {
    private IMAPMessage msg;

    IMAPNestedMessage(IMAPMessage iMAPMessage, BODYSTRUCTURE bODYSTRUCTURE, ENVELOPE eNVELOPE, String string2) {
        super(iMAPMessage._getSession());
        this.msg = iMAPMessage;
        this.bs = bODYSTRUCTURE;
        this.envelope = eNVELOPE;
        this.sectionId = string2;
    }

    @Override
    protected void checkExpunged() throws MessageRemovedException {
        this.msg.checkExpunged();
    }

    @Override
    protected int getFetchBlockSize() {
        return this.msg.getFetchBlockSize();
    }

    @Override
    protected Object getMessageCacheLock() {
        return this.msg.getMessageCacheLock();
    }

    @Override
    protected IMAPProtocol getProtocol() throws ProtocolException, FolderClosedException {
        return this.msg.getProtocol();
    }

    @Override
    protected int getSequenceNumber() {
        return this.msg.getSequenceNumber();
    }

    @Override
    public int getSize() throws MessagingException {
        return this.bs.size;
    }

    public boolean isExpunged() {
        return this.msg.isExpunged();
    }

    @Override
    protected boolean isREV1() throws FolderClosedException {
        return this.msg.isREV1();
    }

    @Override
    public void setFlags(Flags flags, boolean bl) throws MessagingException {
        IMAPNestedMessage iMAPNestedMessage = this;
        synchronized (iMAPNestedMessage) {
            throw new MethodNotSupportedException("Cannot set flags on this nested message");
        }
    }
}

