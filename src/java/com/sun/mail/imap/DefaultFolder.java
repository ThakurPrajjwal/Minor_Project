/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  javax.mail.Folder
 *  javax.mail.Message
 *  javax.mail.MessagingException
 *  javax.mail.MethodNotSupportedException
 *  javax.mail.Store
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.ListInfo;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;

public class DefaultFolder
extends IMAPFolder {
    protected DefaultFolder(IMAPStore iMAPStore) {
        super("", '\uffff', iMAPStore);
        this.exists = true;
        this.type = 2;
    }

    @Override
    public void appendMessages(Message[] arrmessage) throws MessagingException {
        throw new MethodNotSupportedException("Cannot append to Default Folder");
    }

    @Override
    public boolean delete(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("Cannot delete Default Folder");
    }

    @Override
    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("Cannot expunge Default Folder");
    }

    @Override
    public Folder getFolder(String string2) throws MessagingException {
        return new IMAPFolder(string2, '\uffff', (IMAPStore)this.store);
    }

    @Override
    public String getName() {
        return this.fullName;
    }

    @Override
    public Folder getParent() {
        return null;
    }

    @Override
    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Folder[] list(final String string2) throws MessagingException {
        (ListInfo[])null;
        ListInfo[] arrlistInfo = (ListInfo[])this.doCommand(new IMAPFolder.ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.list("", string2);
            }
        });
        if (arrlistInfo == null) {
            return new Folder[0];
        }
        Folder[] arrfolder = new IMAPFolder[arrlistInfo.length];
        int n = 0;
        while (n < arrfolder.length) {
            arrfolder[n] = new IMAPFolder(arrlistInfo[n], (IMAPStore)this.store);
            ++n;
        }
        return arrfolder;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Folder[] listSubscribed(final String string2) throws MessagingException {
        (ListInfo[])null;
        ListInfo[] arrlistInfo = (ListInfo[])this.doCommand(new IMAPFolder.ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.lsub("", string2);
            }
        });
        if (arrlistInfo == null) {
            return new Folder[0];
        }
        Folder[] arrfolder = new IMAPFolder[arrlistInfo.length];
        int n = 0;
        while (n < arrfolder.length) {
            arrfolder[n] = new IMAPFolder(arrlistInfo[n], (IMAPStore)this.store);
            ++n;
        }
        return arrfolder;
    }

    @Override
    public boolean renameTo(Folder folder) throws MessagingException {
        throw new MethodNotSupportedException("Cannot rename Default Folder");
    }

}

