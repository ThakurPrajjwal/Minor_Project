/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  javax.mail.Flags
 *  javax.mail.Folder
 *  javax.mail.Message
 *  javax.mail.MessagingException
 *  javax.mail.MethodNotSupportedException
 *  javax.mail.Store
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.POP3Store;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;

public class DefaultFolder
extends Folder {
    DefaultFolder(POP3Store pOP3Store) {
        super((Store)pOP3Store);
    }

    public void appendMessages(Message[] arrmessage) throws MessagingException {
        throw new MethodNotSupportedException("Append not supported");
    }

    public void close(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("close");
    }

    public boolean create(int n) throws MessagingException {
        return false;
    }

    public boolean delete(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("delete");
    }

    public boolean exists() {
        return true;
    }

    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("expunge");
    }

    public Folder getFolder(String string2) throws MessagingException {
        if (!string2.equalsIgnoreCase("INBOX")) {
            throw new MessagingException("only INBOX supported");
        }
        return this.getInbox();
    }

    public String getFullName() {
        return "";
    }

    protected Folder getInbox() throws MessagingException {
        return this.getStore().getFolder("INBOX");
    }

    public Message getMessage(int n) throws MessagingException {
        throw new MethodNotSupportedException("getMessage");
    }

    public int getMessageCount() throws MessagingException {
        return 0;
    }

    public String getName() {
        return "";
    }

    public Folder getParent() {
        return null;
    }

    public Flags getPermanentFlags() {
        return new Flags();
    }

    public char getSeparator() {
        return '/';
    }

    public int getType() {
        return 2;
    }

    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    public boolean isOpen() {
        return false;
    }

    public Folder[] list(String string2) throws MessagingException {
        Folder[] arrfolder = new Folder[]{this.getInbox()};
        return arrfolder;
    }

    public void open(int n) throws MessagingException {
        throw new MethodNotSupportedException("open");
    }

    public boolean renameTo(Folder folder) throws MessagingException {
        throw new MethodNotSupportedException("renameTo");
    }
}

