/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Enumeration
 *  javax.mail.Flags
 *  javax.mail.Folder
 *  javax.mail.FolderClosedException
 *  javax.mail.IllegalWriteException
 *  javax.mail.Message
 *  javax.mail.MessageRemovedException
 *  javax.mail.MessagingException
 *  javax.mail.Store
 *  javax.mail.internet.InternetHeaders
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.SharedInputStream
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Store;
import com.sun.mail.pop3.Protocol;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.SharedInputStream;

public class POP3Message
extends MimeMessage {
    static final String UNKNOWN = "UNKNOWN";
    private POP3Folder folder;
    private int hdrSize = -1;
    private int msgSize = -1;
    String uid = "UNKNOWN";

    public POP3Message(Folder folder, int n) throws MessagingException {
        super(folder, n);
        this.folder = (POP3Folder)folder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void loadHeaders() throws MessagingException {
        InputStream inputStream;
        block8 : {
            try {
                POP3Message pOP3Message = this;
                // MONITORENTER : pOP3Message
                if (this.headers == null) break block8;
            }
            catch (EOFException eOFException) {
                this.folder.close(false);
                throw new FolderClosedException((Folder)this.folder, eOFException.toString());
            }
            catch (IOException iOException) {
                throw new MessagingException("error loading POP3 headers", (Exception)((Object)iOException));
            }
            // MONITOREXIT : pOP3Message
            return;
        }
        if (!((POP3Store)this.folder.getStore()).disableTop && (inputStream = this.folder.getProtocol().top(this.msgnum, 0)) != null) {
            this.hdrSize = inputStream.available();
            this.headers = new InternetHeaders(inputStream);
            return;
        }
        this.getContentStream().close();
        // MONITOREXIT : pOP3Message
        return;
    }

    public void addHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public void addHeaderLine(String string2) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public Enumeration getAllHeaderLines() throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getAllHeaderLines();
    }

    public Enumeration getAllHeaders() throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getAllHeaders();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected InputStream getContentStream() throws MessagingException {
        block19 : {
            block20 : {
                try {
                    var10_1 = this;
                    // MONITORENTER : var10_1
                    if (this.contentStream != null) ** GOTO lbl24
                }
                catch (EOFException var2_6) {
                    this.folder.close(false);
                    throw new FolderClosedException((Folder)this.folder, var2_6.toString());
                }
                var4_2 = this.folder.getProtocol();
                var7_5 = var4_2.retr(var5_3 = this.msgnum, var6_4 = this.msgSize > 0 ? this.msgSize + this.hdrSize : 0);
                if (var7_5 == null) {
                    this.expunged = true;
                    throw new MessageRemovedException();
                }
                if (this.headers != null && !((POP3Store)this.folder.getStore()).forgetTopHeaders) break block19;
                this.headers = new InternetHeaders(var7_5);
                this.hdrSize = (int)((SharedInputStream)var7_5).getPosition();
                break block20;
                catch (IOException var1_9) {
                    throw new MessagingException("error fetching POP3 content", (Exception)var1_9);
                }
            }
lbl22: // 2 sources:
            do {
                this.contentStream = ((SharedInputStream)var7_5).newStream((long)this.hdrSize, -1L);
lbl24: // 2 sources:
                // MONITOREXIT : var10_1
                return super.getContentStream();
                break;
            } while (true);
        }
        block9 : do {
            var8_7 = 0;
            do {
                block22 : {
                    block21 : {
                        if ((var9_8 = var7_5.read()) < 0 || var9_8 == 10) break block21;
                        if (var9_8 != 13) break block22;
                        if (var7_5.available() > 0) {
                            var7_5.mark(1);
                            if (var7_5.read() != 10) {
                                var7_5.reset();
                            }
                        }
                    }
                    if (var7_5.available() != 0) continue block9;
                    break block9;
                }
                ++var8_7;
            } while (true);
        } while (var8_7 != 0);
        this.hdrSize = (int)((SharedInputStream)var7_5).getPosition();
        ** while (true)
    }

    public String getHeader(String string2, String string3) throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getHeader(string2, string3);
    }

    public String[] getHeader(String string2) throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getHeader(string2);
    }

    public Enumeration getMatchingHeaderLines(String[] arrstring) throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getMatchingHeaderLines(arrstring);
    }

    public Enumeration getMatchingHeaders(String[] arrstring) throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getMatchingHeaders(arrstring);
    }

    public Enumeration getNonMatchingHeaderLines(String[] arrstring) throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getNonMatchingHeaderLines(arrstring);
    }

    public Enumeration getNonMatchingHeaders(String[] arrstring) throws MessagingException {
        if (this.headers == null) {
            this.loadHeaders();
        }
        return this.headers.getNonMatchingHeaders(arrstring);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int getSize() throws MessagingException {
        block9 : {
            int n;
            try {
                POP3Message pOP3Message = this;
                // MONITORENTER : pOP3Message
                if (this.msgSize < 0) break block9;
                n = this.msgSize;
            }
            catch (EOFException eOFException) {
                this.folder.close(false);
                throw new FolderClosedException((Folder)this.folder, eOFException.toString());
            }
            catch (IOException iOException) {
                throw new MessagingException("error getting size", (Exception)((Object)iOException));
            }
            // MONITOREXIT : pOP3Message
            return n;
        }
        if (this.msgSize < 0) {
            if (this.headers == null) {
                this.loadHeaders();
            }
            this.msgSize = this.contentStream != null ? this.contentStream.available() : this.folder.getProtocol().list(this.msgnum) - this.hdrSize;
        }
        int n = this.msgSize;
        // MONITOREXIT : pOP3Message
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void invalidate(boolean bl) {
        POP3Message pOP3Message = this;
        synchronized (pOP3Message) {
            this.content = null;
            this.contentStream = null;
            this.msgSize = -1;
            if (bl) {
                this.headers = null;
                this.hdrSize = -1;
            }
            return;
        }
    }

    public void removeHeader(String string2) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public void saveChanges() throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    public void setFlags(Flags flags, boolean bl) throws MessagingException {
        Flags flags2 = (Flags)this.flags.clone();
        super.setFlags(flags, bl);
        if (!this.flags.equals((Object)flags2)) {
            this.folder.notifyMessageChangedListeners(1, (Message)this);
        }
    }

    public void setHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public InputStream top(int n) throws MessagingException {
        try {
            POP3Message pOP3Message = this;
            // MONITORENTER : pOP3Message
        }
        catch (EOFException eOFException) {
            this.folder.close(false);
            throw new FolderClosedException((Folder)this.folder, eOFException.toString());
        }
        catch (IOException iOException) {
            throw new MessagingException("error getting size", (Exception)((Object)iOException));
        }
        InputStream inputStream = this.folder.getProtocol().top(this.msgnum, n);
        // MONITOREXIT : pOP3Message
        return inputStream;
    }
}

