/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  javax.mail.Flags
 *  javax.mail.Flags$Flag
 *  javax.mail.Folder
 *  javax.mail.FolderClosedException
 *  javax.mail.MessagingException
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.MessagingException;

public class IMAPInputStream
extends InputStream {
    private static final int slop = 64;
    private int blksize;
    private byte[] buf;
    private int bufcount;
    private int bufpos;
    private int max;
    private IMAPMessage msg;
    private boolean peek;
    private int pos;
    private ByteArray readbuf;
    private String section;

    public IMAPInputStream(IMAPMessage iMAPMessage, String string2, int n, boolean bl) {
        this.msg = iMAPMessage;
        this.section = string2;
        this.max = n;
        this.peek = bl;
        this.pos = 0;
        this.blksize = iMAPMessage.getFetchBlockSize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void checkSeen() {
        if (this.peek) {
            return;
        }
        try {
            Folder folder = this.msg.getFolder();
            if (folder == null) return;
            if (folder.getMode() == 1) return;
            if (this.msg.isSet(Flags.Flag.SEEN)) return;
            this.msg.setFlag(Flags.Flag.SEEN, true);
            return;
        }
        catch (MessagingException messagingException) {
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void fill() throws IOException {
        ByteArray byteArray;
        block16 : {
            Object object;
            BODY bODY;
            if (this.max != -1 && this.pos >= this.max) {
                if (this.pos == 0) {
                    this.checkSeen();
                }
                this.readbuf = null;
                return;
            }
            if (this.readbuf == null) {
                this.readbuf = new ByteArray(64 + this.blksize);
            }
            Object object2 = object = this.msg.getMessageCacheLock();
            // MONITORENTER : object2
            try {
                BODY bODY2;
                IMAPProtocol iMAPProtocol = this.msg.getProtocol();
                if (this.msg.isExpunged()) {
                    throw new MessageRemovedIOException("No content for expunged message");
                }
                int n = this.msg.getSequenceNumber();
                int n2 = this.blksize;
                if (this.max != -1 && this.pos + this.blksize > this.max) {
                    n2 = this.max - this.pos;
                }
                if (this.peek) {
                    BODY bODY3;
                    bODY = bODY3 = iMAPProtocol.peekBody(n, this.section, this.pos, n2, this.readbuf);
                }
                bODY = bODY2 = iMAPProtocol.fetchBody(n, this.section, this.pos, n2, this.readbuf);
            }
            catch (ProtocolException protocolException) {
                this.forceCheckExpunged();
                throw new IOException(protocolException.getMessage());
            }
            if (bODY == null || (byteArray = bODY.getByteArray()) == null) {
                this.forceCheckExpunged();
                throw new IOException("No content");
            }
            break block16;
            catch (FolderClosedException folderClosedException) {
                throw new FolderClosedIOException(folderClosedException.getFolder(), folderClosedException.getMessage());
            }
        }
        // MONITOREXIT : object2
        if (this.pos == 0) {
            this.checkSeen();
        }
        this.buf = byteArray.getBytes();
        this.bufpos = byteArray.getStart();
        int n = byteArray.getCount();
        this.bufcount = n + this.bufpos;
        this.pos = n + this.pos;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void forceCheckExpunged() throws MessageRemovedIOException, FolderClosedIOException {
        Object object;
        Object object2 = object = this.msg.getMessageCacheLock();
        // MONITORENTER : object2
        try {
            this.msg.getProtocol().noop();
            // MONITOREXIT : object2
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedIOException(this.msg.getFolder(), connectionException.getMessage());
        }
        catch (FolderClosedException folderClosedException) {
            throw new FolderClosedIOException(folderClosedException.getFolder(), folderClosedException.getMessage());
        }
        catch (ProtocolException protocolException) {}
        if (!this.msg.isExpunged()) return;
        throw new MessageRemovedIOException();
    }

    public int available() throws IOException {
        IMAPInputStream iMAPInputStream = this;
        synchronized (iMAPInputStream) {
            int n = this.bufcount;
            int n2 = this.bufpos;
            int n3 = n - n2;
            return n3;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int read() throws IOException {
        IMAPInputStream iMAPInputStream = this;
        synchronized (iMAPInputStream) {
            block4 : {
                if (this.bufpos < this.bufcount) break block4;
                this.fill();
                int n = this.bufpos;
                int n2 = this.bufcount;
                if (n < n2) break block4;
                return -1;
            }
            byte[] arrby = this.buf;
            int n = this.bufpos;
            this.bufpos = n + 1;
            byte by = arrby[n];
            return by & 255;
        }
    }

    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int read(byte[] arrby, int n, int n2) throws IOException {
        IMAPInputStream iMAPInputStream = this;
        synchronized (iMAPInputStream) {
            int n3 = this.bufcount - this.bufpos;
            if (n3 <= 0) {
                this.fill();
                int n4 = this.bufcount;
                int n5 = this.bufpos;
                n3 = n4 - n5;
                if (n3 <= 0) {
                    return -1;
                }
            }
            int n6 = n3 < n2 ? n3 : n2;
            System.arraycopy((Object)this.buf, (int)this.bufpos, (Object)arrby, (int)n, (int)n6);
            this.bufpos = n6 + this.bufpos;
            return n6;
        }
    }
}

