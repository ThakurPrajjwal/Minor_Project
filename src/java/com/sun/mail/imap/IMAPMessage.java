/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.Date
 *  java.util.Enumeration
 *  java.util.Hashtable
 *  java.util.Locale
 *  javax.activation.DataHandler
 *  javax.activation.DataSource
 *  javax.mail.Address
 *  javax.mail.FetchProfile
 *  javax.mail.FetchProfile$Item
 *  javax.mail.Flags
 *  javax.mail.Flags$Flag
 *  javax.mail.Folder
 *  javax.mail.FolderClosedException
 *  javax.mail.IllegalWriteException
 *  javax.mail.Message
 *  javax.mail.Message$RecipientType
 *  javax.mail.MessageRemovedException
 *  javax.mail.MessagingException
 *  javax.mail.Session
 *  javax.mail.Store
 *  javax.mail.UIDFolder
 *  javax.mail.UIDFolder$FetchProfileItem
 *  javax.mail.internet.ContentType
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.InternetHeaders
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimePart
 *  javax.mail.internet.MimeUtility
 *  javax.mail.internet.ParameterList
 */
package com.sun.mail.imap;

import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPInputStream;
import com.sun.mail.imap.IMAPMultipartDataSource;
import com.sun.mail.imap.IMAPNestedMessage;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.Utility;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.INTERNALDATE;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.RFC822DATA;
import com.sun.mail.imap.protocol.RFC822SIZE;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParameterList;

public class IMAPMessage
extends MimeMessage {
    private static String EnvelopeCmd = "ENVELOPE INTERNALDATE RFC822.SIZE";
    protected BODYSTRUCTURE bs;
    private String description;
    protected ENVELOPE envelope;
    private boolean headersLoaded = false;
    private Hashtable loadedHeaders;
    private boolean peek;
    private Date receivedDate;
    protected String sectionId;
    private int seqnum;
    private int size = -1;
    private String subject;
    private String type;
    private long uid = -1L;

    protected IMAPMessage(IMAPFolder iMAPFolder, int n, int n2) {
        super((Folder)iMAPFolder, n);
        this.seqnum = n2;
        this.flags = null;
    }

    protected IMAPMessage(Session session) {
        super(session);
    }

    private BODYSTRUCTURE _getBodyStructure() {
        return this.bs;
    }

    private ENVELOPE _getEnvelope() {
        return this.envelope;
    }

    private Flags _getFlags() {
        return this.flags;
    }

    private InternetAddress[] aaclone(InternetAddress[] arrinternetAddress) {
        if (arrinternetAddress == null) {
            return null;
        }
        return (InternetAddress[])arrinternetAddress.clone();
    }

    private boolean areHeadersLoaded() {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            boolean bl = this.headersLoaded;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String craftHeaderCmd(IMAPProtocol iMAPProtocol, String[] arrstring) {
        StringBuffer stringBuffer = iMAPProtocol.isREV1() ? new StringBuffer("BODY.PEEK[HEADER.FIELDS (") : new StringBuffer("RFC822.HEADER.LINES (");
        int n = 0;
        do {
            if (n >= arrstring.length) {
                if (!iMAPProtocol.isREV1()) break;
                stringBuffer.append(")]");
                return stringBuffer.toString();
            }
            if (n > 0) {
                stringBuffer.append(" ");
            }
            stringBuffer.append(arrstring[n]);
            ++n;
        } while (true);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    /*
     * Exception decompiling
     */
    static void fetch(IMAPFolder var0, Message[] var1_1, FetchProfile var2_2) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: CONTINUE without a while class org.benf.cfr.reader.b.a.b.e.e
        // org.benf.cfr.reader.b.a.b.e.p.k(GotoStatement.java:87)
        // org.benf.cfr.reader.b.a.b.e.p.f(GotoStatement.java:101)
        // org.benf.cfr.reader.b.a.a.i.z(Op03SimpleStatement.java:503)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:598)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
        // java.lang.Thread.run(Thread.java:919)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isHeaderLoaded(String string2) {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            block4 : {
                boolean bl = this.headersLoaded;
                if (!bl) break block4;
                return true;
            }
            if (this.loadedHeaders == null) return false;
            boolean bl = this.loadedHeaders.containsKey((Object)string2.toUpperCase(Locale.ENGLISH));
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void loadBODYSTRUCTURE() throws MessagingException {
        Object object;
        IMAPMessage iMAPMessage = this;
        // MONITORENTER : iMAPMessage
        BODYSTRUCTURE bODYSTRUCTURE = this.bs;
        if (bODYSTRUCTURE != null) {
            // MONITOREXIT : iMAPMessage
            return;
        }
        Object object2 = object = this.getMessageCacheLock();
        // MONITORENTER : object2
        try {
            IMAPProtocol iMAPProtocol = this.getProtocol();
            this.checkExpunged();
            this.bs = iMAPProtocol.fetchBodyStructure(this.getSequenceNumber());
            if (this.bs != null) return;
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException(this.folder, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            this.forceCheckExpunged();
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
        {
            this.forceCheckExpunged();
            throw new MessagingException("Unable to load BODYSTRUCTURE");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadEnvelope() throws MessagingException {
        var16_1 = this;
        synchronized (var16_1) {
            var2_2 = this.envelope;
            if (var2_2 != null) return;
            (Response[])null;
            var17_4 = var4_3 = this.getMessageCacheLock();
            synchronized (var17_4) {
                block22 : {
                    block21 : {
                        try {
                            var8_5 = this.getProtocol();
                            this.checkExpunged();
                            var9_6 = this.getSequenceNumber();
                            var10_7 = var8_5.fetch(var9_6, IMAPMessage.EnvelopeCmd);
                            var11_8 = 0;
lbl14: // 2 sources:
                            do {
                                if (var11_8 >= var10_7.length) {
                                    var8_5.notifyResponseHandlers(var10_7);
                                    var8_5.handleResult(var10_7[-1 + var10_7.length]);
                                    // MONITOREXIT [2, 20, 9, 10, 13] lbl18 : w: MONITOREXIT : var17_4
                                    if (this.envelope != null) return;
                                    throw new MessagingException("Failed to load IMAP envelope");
                                }
                                if (var10_7[var11_8] == null || !(var10_7[var11_8] instanceof FetchResponse) || ((FetchResponse)var10_7[var11_8]).getNumber() != var9_6) break;
                                var12_9 = (FetchResponse)var10_7[var11_8];
                                var13_10 = var12_9.getItemCount();
                                var14_11 = 0;
lbl26: // 2 sources:
                                do {
                                    if (var14_11 >= var13_10) break block21;
                                    var15_12 = var12_9.getItem(var14_11);
                                    if (var15_12 instanceof ENVELOPE) {
                                        this.envelope = (ENVELOPE)var15_12;
                                    } else if (var15_12 instanceof INTERNALDATE) {
                                        this.receivedDate = ((INTERNALDATE)var15_12).getDate();
                                    } else if (var15_12 instanceof RFC822SIZE) {
                                        this.size = ((RFC822SIZE)var15_12).size;
                                    }
                                    break block22;
                                    break;
                                } while (true);
                                break;
                            } while (true);
                        }
                        catch (ConnectionException var7_13) {
                            throw new FolderClosedException(this.folder, var7_13.getMessage());
                        }
                        catch (ProtocolException var5_14) {
                            this.forceCheckExpunged();
                            throw new MessagingException(var5_14.getMessage(), (Exception)var5_14);
                        }
                    }
                    ++var11_8;
                    ** continue;
                }
                ++var14_11;
                ** continue;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void loadFlags() throws MessagingException {
        Object object;
        IMAPMessage iMAPMessage = this;
        // MONITORENTER : iMAPMessage
        Flags flags = this.flags;
        if (flags != null) {
            // MONITOREXIT : iMAPMessage
            return;
        }
        Object object2 = object = this.getMessageCacheLock();
        // MONITORENTER : object2
        try {
            IMAPProtocol iMAPProtocol = this.getProtocol();
            this.checkExpunged();
            this.flags = iMAPProtocol.fetchFlags(this.getSequenceNumber());
            // MONITOREXIT : object2
            return;
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException(this.folder, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            this.forceCheckExpunged();
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void loadHeaders() throws MessagingException {
        ByteArrayInputStream byteArrayInputStream;
        block18 : {
            Object object;
            IMAPMessage iMAPMessage = this;
            // MONITORENTER : iMAPMessage
            boolean bl = this.headersLoaded;
            if (bl) {
                // MONITOREXIT : iMAPMessage
                return;
            }
            Object object2 = object = this.getMessageCacheLock();
            // MONITORENTER : object2
            try {
                ByteArrayInputStream byteArrayInputStream2;
                IMAPProtocol iMAPProtocol = this.getProtocol();
                this.checkExpunged();
                if (iMAPProtocol.isREV1()) {
                    BODY bODY = iMAPProtocol.peekBody(this.getSequenceNumber(), this.toSection("HEADER"));
                    byteArrayInputStream = null;
                    if (bODY != null) {
                        ByteArrayInputStream byteArrayInputStream3;
                        byteArrayInputStream = byteArrayInputStream3 = bODY.getByteArrayInputStream();
                    }
                    break block18;
                }
                RFC822DATA rFC822DATA = iMAPProtocol.fetchRFC822(this.getSequenceNumber(), "HEADER");
                byteArrayInputStream = null;
                if (rFC822DATA == null) break block18;
                byteArrayInputStream = byteArrayInputStream2 = rFC822DATA.getByteArrayInputStream();
            }
            catch (ConnectionException connectionException) {
                throw new FolderClosedException(this.folder, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                this.forceCheckExpunged();
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        // MONITOREXIT : object2
        if (byteArrayInputStream == null) {
            throw new MessagingException("Cannot load header");
        }
        this.headers = new InternetHeaders((InputStream)byteArrayInputStream);
        this.headersLoaded = true;
    }

    private void setHeaderLoaded(String string2) {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            if (this.loadedHeaders == null) {
                this.loadedHeaders = new Hashtable(1);
            }
            this.loadedHeaders.put((Object)string2.toUpperCase(Locale.ENGLISH), (Object)string2);
            return;
        }
    }

    private void setHeadersLoaded(boolean bl) {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            this.headersLoaded = bl;
            return;
        }
    }

    private String toSection(String string2) {
        if (this.sectionId == null) {
            return string2;
        }
        return String.valueOf((Object)this.sectionId) + "." + string2;
    }

    Session _getSession() {
        return this.session;
    }

    void _setFlags(Flags flags) {
        this.flags = flags;
    }

    public void addFrom(Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void addHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void addHeaderLine(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void addRecipients(Message.RecipientType recipientType, Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void checkExpunged() throws MessageRemovedException {
        if (this.expunged) {
            throw new MessageRemovedException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void forceCheckExpunged() throws MessageRemovedException, FolderClosedException {
        Object object;
        Object object2 = object = this.getMessageCacheLock();
        // MONITORENTER : object2
        try {
            this.getProtocol().noop();
            // MONITOREXIT : object2
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException(this.folder, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {}
        if (!this.expunged) return;
        throw new MessageRemovedException();
    }

    public Enumeration getAllHeaderLines() throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getAllHeaderLines();
    }

    public Enumeration getAllHeaders() throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getAllHeaders();
    }

    public String getContentID() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.id;
    }

    public String[] getContentLanguage() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        if (this.bs.language != null) {
            return (String[])this.bs.language.clone();
        }
        return null;
    }

    public String getContentMD5() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
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
        block12 : {
            Object object;
            int n = -1;
            boolean bl = this.getPeek();
            Object object2 = object = this.getMessageCacheLock();
            // MONITORENTER : object2
            try {
                ByteArrayInputStream byteArrayInputStream2;
                IMAPProtocol iMAPProtocol = this.getProtocol();
                this.checkExpunged();
                if (iMAPProtocol.isREV1() && this.getFetchBlockSize() != n) {
                    String string2 = this.toSection("TEXT");
                    if (this.bs != null) {
                        n = this.bs.size;
                    }
                    IMAPInputStream iMAPInputStream = new IMAPInputStream(this, string2, n, bl);
                    // MONITOREXIT : object2
                    return iMAPInputStream;
                }
                if (iMAPProtocol.isREV1()) {
                    BODY bODY = bl ? iMAPProtocol.peekBody(this.getSequenceNumber(), this.toSection("TEXT")) : iMAPProtocol.fetchBody(this.getSequenceNumber(), this.toSection("TEXT"));
                    byteArrayInputStream = null;
                    if (bODY != null) {
                        ByteArrayInputStream byteArrayInputStream3;
                        byteArrayInputStream = byteArrayInputStream3 = bODY.getByteArrayInputStream();
                    }
                    break block12;
                }
                RFC822DATA rFC822DATA = iMAPProtocol.fetchRFC822(this.getSequenceNumber(), "TEXT");
                byteArrayInputStream = null;
                if (rFC822DATA == null) break block12;
                byteArrayInputStream = byteArrayInputStream2 = rFC822DATA.getByteArrayInputStream();
            }
            catch (ConnectionException connectionException) {
                throw new FolderClosedException(this.folder, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                this.forceCheckExpunged();
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        // MONITOREXIT : object2
        if (byteArrayInputStream != null) return byteArrayInputStream;
        throw new MessagingException("No content");
    }

    public String getContentType() throws MessagingException {
        this.checkExpunged();
        if (this.type == null) {
            this.loadBODYSTRUCTURE();
            this.type = new ContentType(this.bs.type, this.bs.subtype, this.bs.cParams).toString();
        }
        return this.type;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DataHandler getDataHandler() throws MessagingException {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            this.checkExpunged();
            if (this.dh != null) return super.getDataHandler();
            this.loadBODYSTRUCTURE();
            if (this.type == null) {
                this.type = new ContentType(this.bs.type, this.bs.subtype, this.bs.cParams).toString();
            }
            if (this.bs.isMulti()) {
                this.dh = new DataHandler((DataSource)new IMAPMultipartDataSource((MimePart)this, this.bs.bodies, this.sectionId, this));
                return super.getDataHandler();
            } else {
                String string2;
                if (!this.bs.isNested()) return super.getDataHandler();
                if (!this.isREV1()) return super.getDataHandler();
                BODYSTRUCTURE bODYSTRUCTURE = this.bs.bodies[0];
                ENVELOPE eNVELOPE = this.bs.envelope;
                String string3 = this.sectionId == null ? "1" : (string2 = String.valueOf((Object)this.sectionId) + ".1");
                this.dh = new DataHandler((Object)new IMAPNestedMessage(this, bODYSTRUCTURE, eNVELOPE, string3), this.type);
            }
            return super.getDataHandler();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getDescription() throws MessagingException {
        this.checkExpunged();
        if (this.description != null) {
            return this.description;
        }
        this.loadBODYSTRUCTURE();
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
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.disposition;
    }

    public String getEncoding() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.encoding;
    }

    protected int getFetchBlockSize() {
        return ((IMAPStore)this.folder.getStore()).getFetchBlockSize();
    }

    public String getFileName() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
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

    public Flags getFlags() throws MessagingException {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            this.checkExpunged();
            this.loadFlags();
            Flags flags = super.getFlags();
            return flags;
        }
    }

    public Address[] getFrom() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.aaclone(this.envelope.from);
    }

    public String getHeader(String string2, String string3) throws MessagingException {
        this.checkExpunged();
        if (this.getHeader(string2) == null) {
            return null;
        }
        return this.headers.getHeader(string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public String[] getHeader(String string2) throws MessagingException {
        ByteArrayInputStream byteArrayInputStream;
        block12 : {
            Object object;
            this.checkExpunged();
            if (this.isHeaderLoaded(string2)) {
                return this.headers.getHeader(string2);
            }
            Object object2 = object = this.getMessageCacheLock();
            // MONITORENTER : object2
            try {
                ByteArrayInputStream byteArrayInputStream2;
                IMAPProtocol iMAPProtocol = this.getProtocol();
                this.checkExpunged();
                if (iMAPProtocol.isREV1()) {
                    BODY bODY = iMAPProtocol.peekBody(this.getSequenceNumber(), this.toSection("HEADER.FIELDS (" + string2 + ")"));
                    byteArrayInputStream = null;
                    if (bODY != null) {
                        ByteArrayInputStream byteArrayInputStream3;
                        byteArrayInputStream = byteArrayInputStream3 = bODY.getByteArrayInputStream();
                    }
                    break block12;
                }
                RFC822DATA rFC822DATA = iMAPProtocol.fetchRFC822(this.getSequenceNumber(), "HEADER.LINES (" + string2 + ")");
                byteArrayInputStream = null;
                if (rFC822DATA == null) break block12;
                byteArrayInputStream = byteArrayInputStream2 = rFC822DATA.getByteArrayInputStream();
            }
            catch (ConnectionException connectionException) {
                throw new FolderClosedException(this.folder, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                this.forceCheckExpunged();
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        // MONITOREXIT : object2
        if (byteArrayInputStream == null) {
            return null;
        }
        if (this.headers == null) {
            this.headers = new InternetHeaders();
        }
        this.headers.load((InputStream)byteArrayInputStream);
        this.setHeaderLoaded(string2);
        return this.headers.getHeader(string2);
    }

    public String getInReplyTo() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.envelope.inReplyTo;
    }

    public int getLineCount() throws MessagingException {
        this.checkExpunged();
        this.loadBODYSTRUCTURE();
        return this.bs.lines;
    }

    public Enumeration getMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getMatchingHeaderLines(arrstring);
    }

    public Enumeration getMatchingHeaders(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getMatchingHeaders(arrstring);
    }

    protected Object getMessageCacheLock() {
        return ((IMAPFolder)this.folder).messageCacheLock;
    }

    public String getMessageID() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.envelope.messageId;
    }

    public Enumeration getNonMatchingHeaderLines(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getNonMatchingHeaderLines(arrstring);
    }

    public Enumeration getNonMatchingHeaders(String[] arrstring) throws MessagingException {
        this.checkExpunged();
        this.loadHeaders();
        return super.getNonMatchingHeaders(arrstring);
    }

    public boolean getPeek() {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            boolean bl = this.peek;
            return bl;
        }
    }

    protected IMAPProtocol getProtocol() throws ProtocolException, FolderClosedException {
        ((IMAPFolder)this.folder).waitIfIdle();
        IMAPProtocol iMAPProtocol = ((IMAPFolder)this.folder).protocol;
        if (iMAPProtocol == null) {
            throw new FolderClosedException(this.folder);
        }
        return iMAPProtocol;
    }

    public Date getReceivedDate() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (this.receivedDate == null) {
            return null;
        }
        return new Date(this.receivedDate.getTime());
    }

    public Address[] getRecipients(Message.RecipientType recipientType) throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (recipientType == Message.RecipientType.TO) {
            return this.aaclone(this.envelope.to);
        }
        if (recipientType == Message.RecipientType.CC) {
            return this.aaclone(this.envelope.cc);
        }
        if (recipientType == Message.RecipientType.BCC) {
            return this.aaclone(this.envelope.bcc);
        }
        return super.getRecipients(recipientType);
    }

    public Address[] getReplyTo() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        return this.aaclone(this.envelope.replyTo);
    }

    public Address getSender() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (this.envelope.sender != null) {
            return this.envelope.sender[0];
        }
        return null;
    }

    public Date getSentDate() throws MessagingException {
        this.checkExpunged();
        this.loadEnvelope();
        if (this.envelope.date == null) {
            return null;
        }
        return new Date(this.envelope.date.getTime());
    }

    protected int getSequenceNumber() {
        return this.seqnum;
    }

    public int getSize() throws MessagingException {
        this.checkExpunged();
        if (this.size == -1) {
            this.loadEnvelope();
        }
        return this.size;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getSubject() throws MessagingException {
        this.checkExpunged();
        if (this.subject != null) {
            return this.subject;
        }
        this.loadEnvelope();
        if (this.envelope.subject == null) {
            return null;
        }
        try {
            this.subject = MimeUtility.decodeText((String)this.envelope.subject);
            do {
                return this.subject;
                break;
            } while (true);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.subject = this.envelope.subject;
            return this.subject;
        }
    }

    protected long getUID() {
        return this.uid;
    }

    public void invalidateHeaders() {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            this.headersLoaded = false;
            this.loadedHeaders = null;
            this.envelope = null;
            this.bs = null;
            this.receivedDate = null;
            this.size = -1;
            this.type = null;
            this.subject = null;
            this.description = null;
            return;
        }
    }

    protected boolean isREV1() throws FolderClosedException {
        IMAPProtocol iMAPProtocol = ((IMAPFolder)this.folder).protocol;
        if (iMAPProtocol == null) {
            throw new FolderClosedException(this.folder);
        }
        return iMAPProtocol.isREV1();
    }

    public boolean isSet(Flags.Flag flag) throws MessagingException {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            this.checkExpunged();
            this.loadFlags();
            boolean bl = super.isSet(flag);
            return bl;
        }
    }

    public void removeHeader(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setContentID(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setContentLanguage(String[] arrstring) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setContentMD5(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setDataHandler(DataHandler dataHandler) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setDescription(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setDisposition(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setExpunged(boolean bl) {
        super.setExpunged(bl);
        this.seqnum = -1;
    }

    public void setFileName(String string2) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setFlags(Flags flags, boolean bl) throws MessagingException {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            Object object;
            Object object2 = object = this.getMessageCacheLock();
            synchronized (object2) {
                try {
                    IMAPProtocol iMAPProtocol = this.getProtocol();
                    this.checkExpunged();
                    iMAPProtocol.storeFlags(this.getSequenceNumber(), flags, bl);
                    return;
                }
                catch (ConnectionException connectionException) {
                    throw new FolderClosedException(this.folder, connectionException.getMessage());
                }
                catch (ProtocolException protocolException) {
                    throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                }
            }
        }
    }

    public void setFrom(Address address) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setHeader(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setMessageNumber(int n) {
        super.setMessageNumber(n);
    }

    public void setPeek(boolean bl) {
        IMAPMessage iMAPMessage = this;
        synchronized (iMAPMessage) {
            this.peek = bl;
            return;
        }
    }

    public void setRecipients(Message.RecipientType recipientType, Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setReplyTo(Address[] arraddress) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setSender(Address address) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setSentDate(Date date) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setSequenceNumber(int n) {
        this.seqnum = n;
    }

    public void setSubject(String string2, String string3) throws MessagingException {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setUID(long l) {
        this.uid = l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void writeTo(OutputStream outputStream) throws IOException, MessagingException {
        ByteArrayInputStream byteArrayInputStream;
        block13 : {
            Object object;
            boolean bl = this.getPeek();
            Object object2 = object = this.getMessageCacheLock();
            // MONITORENTER : object2
            try {
                ByteArrayInputStream byteArrayInputStream2;
                IMAPProtocol iMAPProtocol = this.getProtocol();
                this.checkExpunged();
                if (iMAPProtocol.isREV1()) {
                    BODY bODY = bl ? iMAPProtocol.peekBody(this.getSequenceNumber(), this.sectionId) : iMAPProtocol.fetchBody(this.getSequenceNumber(), this.sectionId);
                    byteArrayInputStream = null;
                    if (bODY != null) {
                        ByteArrayInputStream byteArrayInputStream3;
                        byteArrayInputStream = byteArrayInputStream3 = bODY.getByteArrayInputStream();
                    }
                    break block13;
                }
                RFC822DATA rFC822DATA = iMAPProtocol.fetchRFC822(this.getSequenceNumber(), null);
                byteArrayInputStream = null;
                if (rFC822DATA == null) break block13;
                byteArrayInputStream = byteArrayInputStream2 = rFC822DATA.getByteArrayInputStream();
            }
            catch (ConnectionException connectionException) {
                throw new FolderClosedException(this.folder, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                this.forceCheckExpunged();
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        // MONITOREXIT : object2
        if (byteArrayInputStream == null) {
            throw new MessagingException("No content");
        }
        byte[] arrby = new byte[1024];
        int n;
        while ((n = byteArrayInputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n);
        }
        return;
    }

    class 1FetchProfileCondition
    implements Utility.Condition {
        private String[] hdrs = null;
        private boolean needBodyStructure = false;
        private boolean needEnvelope = false;
        private boolean needFlags = false;
        private boolean needHeaders = false;
        private boolean needSize = false;
        private boolean needUID = false;

        public 1FetchProfileCondition(FetchProfile fetchProfile) {
            if (fetchProfile.contains(FetchProfile.Item.ENVELOPE)) {
                this.needEnvelope = true;
            }
            if (fetchProfile.contains(FetchProfile.Item.FLAGS)) {
                this.needFlags = true;
            }
            if (fetchProfile.contains(FetchProfile.Item.CONTENT_INFO)) {
                this.needBodyStructure = true;
            }
            if (fetchProfile.contains((FetchProfile.Item)UIDFolder.FetchProfileItem.UID)) {
                this.needUID = true;
            }
            if (fetchProfile.contains((FetchProfile.Item)IMAPFolder.FetchProfileItem.HEADERS)) {
                this.needHeaders = true;
            }
            if (fetchProfile.contains((FetchProfile.Item)IMAPFolder.FetchProfileItem.SIZE)) {
                this.needSize = true;
            }
            this.hdrs = fetchProfile.getHeaderNames();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean test(IMAPMessage iMAPMessage) {
            if (!(this.needEnvelope && iMAPMessage._getEnvelope() == null || this.needFlags && iMAPMessage._getFlags() == null || this.needBodyStructure && iMAPMessage._getBodyStructure() == null || this.needUID && iMAPMessage.getUID() == -1L || this.needHeaders && !iMAPMessage.areHeadersLoaded() || this.needSize && iMAPMessage.size == -1)) {
                int n = 0;
                do {
                    if (n >= this.hdrs.length) {
                        return false;
                    }
                    if (!iMAPMessage.isHeaderLoaded(this.hdrs[n])) break;
                    ++n;
                } while (true);
            }
            return true;
        }
    }

}

