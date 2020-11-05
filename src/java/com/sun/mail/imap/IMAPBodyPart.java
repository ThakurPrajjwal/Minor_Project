/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.InputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Enumeration
 *  javax.activation.DataHandler
 *  javax.activation.DataSource
 *  javax.mail.Folder
 *  javax.mail.FolderClosedException
 *  javax.mail.IllegalWriteException
 *  javax.mail.MessagingException
 *  javax.mail.Multipart
 *  javax.mail.internet.ContentType
 *  javax.mail.internet.InternetHeaders
 *  javax.mail.internet.MimeBodyPart
 *  javax.mail.internet.MimePart
 *  javax.mail.internet.MimeUtility
 *  javax.mail.internet.ParameterList
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPInputStream;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPMultipartDataSource;
import com.sun.mail.imap.IMAPNestedMessage;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.IMAPProtocol;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParameterList;

public class IMAPBodyPart
extends MimeBodyPart {
    private BODYSTRUCTURE bs;
    private String description;
    private boolean headersLoaded = false;
    private IMAPMessage message;
    private String sectionId;
    private String type;

    protected IMAPBodyPart(BODYSTRUCTURE bODYSTRUCTURE, String string2, IMAPMessage iMAPMessage) {
        this.bs = bODYSTRUCTURE;
        this.sectionId = string2;
        this.message = iMAPMessage;
        this.type = new ContentType(bODYSTRUCTURE.type, bODYSTRUCTURE.subtype, bODYSTRUCTURE.cParams).toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadHeaders() throws MessagingException {
        IMAPBodyPart iMAPBodyPart = this;
        synchronized (iMAPBodyPart) {
            boolean bl = this.headersLoaded;
            if (!bl) {
                Object object;
                if (this.headers == null) {
                    this.headers = new InternetHeaders();
                }
                Object object2 = object = this.message.getMessageCacheLock();
                synchronized (object2) {
                    block21 : {
                        IMAPProtocol iMAPProtocol = this.message.getProtocol();
                        this.message.checkExpunged();
                        if (!iMAPProtocol.isREV1()) break block21;
                        BODY bODY = iMAPProtocol.peekBody(this.message.getSequenceNumber(), String.valueOf((Object)this.sectionId) + ".MIME");
                        if (bODY == null) {
                            throw new MessagingException("Failed to fetch headers");
                        }
                        ByteArrayInputStream byteArrayInputStream = bODY.getByteArrayInputStream();
                        if (byteArrayInputStream == null) {
                            throw new MessagingException("Failed to fetch headers");
                        }
                        this.headers.load((InputStream)byteArrayInputStream);
                    }
                    try {
                        this.headers.addHeader("Content-Type", this.type);
                        this.headers.addHeader("Content-Transfer-Encoding", this.bs.encoding);
                        if (this.bs.description != null) {
                            this.headers.addHeader("Content-Description", this.bs.description);
                        }
                        if (this.bs.id != null) {
                            this.headers.addHeader("Content-ID", this.bs.id);
                        }
                        if (this.bs.md5 != null) {
                            this.headers.addHeader("Content-MD5", this.bs.md5);
                        }
                    }
                    catch (ConnectionException connectionException) {
                        throw new FolderClosedException(this.message.getFolder(), connectionException.getMessage());
                    }
                    catch (ProtocolException protocolException) {
                        throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                    }
                }
                this.headersLoaded = true;
            }
            return;
        }
    }

    public void addHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void addHeaderLine(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public Enumeration getAllHeaderLines() throws MessagingException {
        this.loadHeaders();
        return super.getAllHeaderLines();
    }

    public Enumeration getAllHeaders() throws MessagingException {
        this.loadHeaders();
        return super.getAllHeaders();
    }

    public String getContentID() throws MessagingException {
        return this.bs.id;
    }

    public String getContentMD5() throws MessagingException {
        return this.bs.md5;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected InputStream getContentStream() throws MessagingException {
        ByteArrayInputStream byteArrayInputStream;
        block9 : {
            Object object;
            boolean bl = this.message.getPeek();
            Object object2 = object = this.message.getMessageCacheLock();
            // MONITORENTER : object2
            try {
                ByteArrayInputStream byteArrayInputStream2;
                BODY bODY;
                IMAPProtocol iMAPProtocol = this.message.getProtocol();
                this.message.checkExpunged();
                if (iMAPProtocol.isREV1() && this.message.getFetchBlockSize() != -1) {
                    IMAPInputStream iMAPInputStream = new IMAPInputStream(this.message, this.sectionId, this.bs.size, bl);
                    // MONITOREXIT : object2
                    return iMAPInputStream;
                }
                int n = this.message.getSequenceNumber();
                BODY bODY2 = bl ? iMAPProtocol.peekBody(n, this.sectionId) : (bODY = iMAPProtocol.fetchBody(n, this.sectionId));
                byteArrayInputStream = null;
                if (bODY2 == null) break block9;
                byteArrayInputStream = byteArrayInputStream2 = bODY2.getByteArrayInputStream();
            }
            catch (ConnectionException connectionException) {
                throw new FolderClosedException(this.message.getFolder(), connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        // MONITOREXIT : object2
        if (byteArrayInputStream != null) return byteArrayInputStream;
        throw new MessagingException("No content");
    }

    public String getContentType() throws MessagingException {
        return this.type;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public DataHandler getDataHandler() throws MessagingException {
        IMAPBodyPart iMAPBodyPart = this;
        synchronized (iMAPBodyPart) {
            block5 : {
                if (this.dh != null) return super.getDataHandler();
                if (!this.bs.isMulti()) break block5;
                this.dh = new DataHandler((DataSource)new IMAPMultipartDataSource((MimePart)this, this.bs.bodies, this.sectionId, this.message));
                do {
                    return super.getDataHandler();
                    break;
                } while (true);
            }
            if (!this.bs.isNested()) return super.getDataHandler();
            if (!this.message.isREV1()) return super.getDataHandler();
            this.dh = new DataHandler((Object)new IMAPNestedMessage(this.message, this.bs.bodies[0], this.bs.envelope, this.sectionId), this.type);
            return super.getDataHandler();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getDescription() throws MessagingException {
        if (this.description != null) {
            return this.description;
        }
        if (this.bs.description == null) {
            return null;
        }
        try {
            this.description = MimeUtility.decodeText((String)this.bs.description);
            do {
                return this.description;
                break;
            } while (true);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.description = this.bs.description;
            return this.description;
        }
    }

    public String getDisposition() throws MessagingException {
        return this.bs.disposition;
    }

    public String getEncoding() throws MessagingException {
        return this.bs.encoding;
    }

    public String getFileName() throws MessagingException {
        ParameterList parameterList = this.bs.dParams;
        String string2 = null;
        if (parameterList != null) {
            string2 = this.bs.dParams.get("filename");
        }
        if (string2 == null && this.bs.cParams != null) {
            string2 = this.bs.cParams.get("name");
        }
        return string2;
    }

    public String[] getHeader(String string2) throws MessagingException {
        this.loadHeaders();
        return super.getHeader(string2);
    }

    public int getLineCount() throws MessagingException {
        return this.bs.lines;
    }

    public Enumeration getMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getMatchingHeaderLines(arrstring);
    }

    public Enumeration getMatchingHeaders(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getMatchingHeaders(arrstring);
    }

    public Enumeration getNonMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getNonMatchingHeaderLines(arrstring);
    }

    public Enumeration getNonMatchingHeaders(String[] arrstring) throws MessagingException {
        this.loadHeaders();
        return super.getNonMatchingHeaders(arrstring);
    }

    public int getSize() throws MessagingException {
        return this.bs.size;
    }

    public void removeHeader(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContent(Object object, String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContent(Multipart multipart) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContentMD5(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setDataHandler(DataHandler dataHandler) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setDescription(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setDisposition(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setFileName(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    protected void updateHeaders() {
    }
}

