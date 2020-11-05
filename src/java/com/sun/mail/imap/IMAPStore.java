/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.lang.AssertionError
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.Map
 *  java.util.Properties
 *  java.util.StringTokenizer
 *  java.util.Vector
 *  javax.mail.AuthenticationFailedException
 *  javax.mail.Folder
 *  javax.mail.MessagingException
 *  javax.mail.Quota
 *  javax.mail.QuotaAwareStore
 *  javax.mail.Session
 *  javax.mail.Store
 *  javax.mail.StoreClosedException
 *  javax.mail.URLName
 */
package com.sun.mail.imap;

import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.imap.DefaultFolder;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.Namespaces;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.QuotaAwareStore;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.URLName;

public class IMAPStore
extends Store
implements QuotaAwareStore,
ResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int RESPONSE = 1000;
    private int appendBufferSize = -1;
    private String authorizationID;
    private int blksize = 16384;
    private volatile boolean connected = false;
    private int defaultPort = 143;
    private boolean disableAuthLogin = false;
    private boolean disableAuthPlain = false;
    private boolean enableImapEvents = false;
    private boolean enableSASL = false;
    private boolean enableStartTLS = false;
    private boolean forcePasswordRefresh = false;
    private String host;
    private boolean isSSL = false;
    private int minIdleTime = 10;
    private String name = "imap";
    private Namespaces namespaces;
    private PrintStream out;
    private String password;
    private ConnectionPool pool = new ConnectionPool();
    private int port = -1;
    private String proxyAuthUser;
    private String[] saslMechanisms;
    private String saslRealm;
    private int statusCacheTimeout = 1000;
    private String user;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !IMAPStore.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public IMAPStore(Session session, URLName uRLName) {
        this(session, uRLName, "imap", 143, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected IMAPStore(Session session, URLName uRLName, String string2, int n, boolean bl) {
        String string3;
        String string4;
        String string5;
        String string6;
        String string7;
        String string8;
        String string9;
        String string10;
        String string11;
        String string12;
        String string13;
        String string14;
        String string15;
        String string16;
        String string17;
        String string18;
        String string19;
        String string20;
        String string21;
        super(session, uRLName);
        if (uRLName != null) {
            string2 = uRLName.getProtocol();
        }
        this.name = string2;
        this.defaultPort = n;
        this.isSSL = bl;
        ConnectionPool.access$0(this.pool, System.currentTimeMillis());
        this.debug = session.getDebug();
        this.out = session.getDebugOut();
        if (this.out == null) {
            this.out = System.out;
        }
        if ((string3 = session.getProperty("mail." + string2 + ".connectionpool.debug")) != null && string3.equalsIgnoreCase("true")) {
            ConnectionPool.access$1(this.pool, true);
        }
        if ((string15 = session.getProperty("mail." + string2 + ".partialfetch")) != null && string15.equalsIgnoreCase("false")) {
            this.blksize = -1;
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.partialfetch: false");
            }
        } else {
            String string22 = session.getProperty("mail." + string2 + ".fetchsize");
            if (string22 != null) {
                this.blksize = Integer.parseInt((String)string22);
            }
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.fetchsize: " + this.blksize);
            }
        }
        if ((string6 = session.getProperty("mail." + string2 + ".statuscachetimeout")) != null) {
            this.statusCacheTimeout = Integer.parseInt((String)string6);
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.statuscachetimeout: " + this.statusCacheTimeout);
            }
        }
        if ((string19 = session.getProperty("mail." + string2 + ".appendbuffersize")) != null) {
            this.appendBufferSize = Integer.parseInt((String)string19);
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.appendbuffersize: " + this.appendBufferSize);
            }
        }
        if ((string16 = session.getProperty("mail." + string2 + ".minidletime")) != null) {
            this.minIdleTime = Integer.parseInt((String)string16);
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.minidletime: " + this.minIdleTime);
            }
        }
        if ((string8 = session.getProperty("mail." + string2 + ".connectionpoolsize")) != null) {
            try {
                int n2 = Integer.parseInt((String)string8);
                if (n2 > 0) {
                    ConnectionPool.access$2(this.pool, n2);
                }
            }
            catch (NumberFormatException numberFormatException) {}
            if (this.pool.debug) {
                this.out.println("DEBUG: mail.imap.connectionpoolsize: " + this.pool.poolSize);
            }
        }
        if ((string21 = session.getProperty("mail." + string2 + ".connectionpooltimeout")) != null) {
            try {
                int n3 = Integer.parseInt((String)string21);
                if (n3 > 0) {
                    ConnectionPool.access$5(this.pool, n3);
                }
            }
            catch (NumberFormatException numberFormatException) {}
            if (this.pool.debug) {
                this.out.println("DEBUG: mail.imap.connectionpooltimeout: " + this.pool.clientTimeoutInterval);
            }
        }
        if ((string9 = session.getProperty("mail." + string2 + ".servertimeout")) != null) {
            try {
                int n4 = Integer.parseInt((String)string9);
                if (n4 > 0) {
                    ConnectionPool.access$7(this.pool, n4);
                }
            }
            catch (NumberFormatException numberFormatException) {}
            if (this.pool.debug) {
                this.out.println("DEBUG: mail.imap.servertimeout: " + this.pool.serverTimeoutInterval);
            }
        }
        if ((string7 = session.getProperty("mail." + string2 + ".separatestoreconnection")) != null && string7.equalsIgnoreCase("true")) {
            if (this.pool.debug) {
                this.out.println("DEBUG: dedicate a store connection");
            }
            ConnectionPool.access$9(this.pool, true);
        }
        if ((string18 = session.getProperty("mail." + string2 + ".proxyauth.user")) != null) {
            this.proxyAuthUser = string18;
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.proxyauth.user: " + this.proxyAuthUser);
            }
        }
        if ((string11 = session.getProperty("mail." + string2 + ".auth.login.disable")) != null && string11.equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: disable AUTH=LOGIN");
            }
            this.disableAuthLogin = true;
        }
        if ((string4 = session.getProperty("mail." + string2 + ".auth.plain.disable")) != null && string4.equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: disable AUTH=PLAIN");
            }
            this.disableAuthPlain = true;
        }
        if ((string13 = session.getProperty("mail." + string2 + ".starttls.enable")) != null && string13.equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: enable STARTTLS");
            }
            this.enableStartTLS = true;
        }
        if ((string12 = session.getProperty("mail." + string2 + ".sasl.enable")) != null && string12.equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: enable SASL");
            }
            this.enableSASL = true;
        }
        if (this.enableSASL && (string17 = session.getProperty("mail." + string2 + ".sasl.mechanisms")) != null && string17.length() > 0) {
            if (this.debug) {
                this.out.println("DEBUG: SASL mechanisms allowed: " + string17);
            }
            Vector vector = new Vector(5);
            StringTokenizer stringTokenizer = new StringTokenizer(string17, " ,");
            do {
                if (!stringTokenizer.hasMoreTokens()) {
                    this.saslMechanisms = new String[vector.size()];
                    vector.copyInto((Object[])this.saslMechanisms);
                    break;
                }
                String string23 = stringTokenizer.nextToken();
                if (string23.length() <= 0) continue;
                vector.addElement((Object)string23);
            } while (true);
        }
        if ((string5 = session.getProperty("mail." + string2 + ".sasl.authorizationid")) != null) {
            this.authorizationID = string5;
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.sasl.authorizationid: " + this.authorizationID);
            }
        }
        if ((string14 = session.getProperty("mail." + string2 + ".sasl.realm")) != null) {
            this.saslRealm = string14;
            if (this.debug) {
                this.out.println("DEBUG: mail.imap.sasl.realm: " + this.saslRealm);
            }
        }
        if ((string10 = session.getProperty("mail." + string2 + ".forcepasswordrefresh")) != null && string10.equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: enable forcePasswordRefresh");
            }
            this.forcePasswordRefresh = true;
        }
        if ((string20 = session.getProperty("mail." + string2 + ".enableimapevents")) != null && string20.equalsIgnoreCase("true")) {
            if (this.debug) {
                this.out.println("DEBUG: enable IMAP events");
            }
            this.enableImapEvents = true;
        }
    }

    private void checkConnected() {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)this)) {
            throw new AssertionError();
        }
        if (!this.connected) {
            super.setConnected(false);
            throw new IllegalStateException("Not connected");
        }
    }

    private void cleanup() {
        this.cleanup(false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void cleanup(boolean var1_1) {
        if (this.debug) {
            this.out.println("DEBUG: IMAPStore cleanup, force " + var1_1);
        }
        var2_2 = null;
        block9 : do {
            var13_10 = var3_3 = this.pool;
            // MONITORENTER : var13_10
            if (ConnectionPool.access$13(this.pool) != null) {
                var5_4 = false;
                var2_2 = ConnectionPool.access$13(this.pool);
                ConnectionPool.access$14(this.pool, null);
            } else {
                var5_4 = true;
            }
            // MONITOREXIT : var13_10
            if (var5_4) {
                var14_12 = var11_11 = this.pool;
                // MONITORENTER : var14_12
                this.emptyConnectionPool(var1_1);
                // MONITOREXIT : var14_12
                this.connected = false;
                this.notifyConnectionListeners(3);
                if (this.debug == false) return;
                this.out.println("DEBUG: IMAPStore cleanup done");
                return;
            }
            var6_5 = 0;
            var7_6 = var2_2.size();
            do {
                block17 : {
                    if (var6_5 >= var7_6) continue block9;
                    var8_7 = (IMAPFolder)var2_2.elementAt(var6_5);
                    if (!var1_1) ** GOTO lbl35
                    try {
                        if (this.debug) {
                            this.out.println("DEBUG: force folder to close");
                        }
                        var8_7.forceClose();
                        break block17;
lbl35: // 1 sources:
                        if (this.debug) {
                            this.out.println("DEBUG: close folder");
                        }
                        var8_7.close(false);
                    }
                    catch (MessagingException var10_9) {
                    }
                    catch (IllegalStateException var9_8) {
                        // empty catch block
                    }
                }
                ++var6_5;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void emptyConnectionPool(boolean bl) {
        ConnectionPool connectionPool;
        ConnectionPool connectionPool2 = connectionPool = this.pool;
        synchronized (connectionPool2) {
            int n = -1 + this.pool.authenticatedConnections.size();
            do {
                if (n < 0) {
                    this.pool.authenticatedConnections.removeAllElements();
                    // MONITOREXIT [3, 5, 9] lbl7 : w: MONITOREXIT : var7_3
                    if (this.pool.debug) {
                        this.out.println("DEBUG: removed all authenticated connections");
                    }
                    return;
                }
                try {
                    IMAPProtocol iMAPProtocol = (IMAPProtocol)this.pool.authenticatedConnections.elementAt(n);
                    iMAPProtocol.removeResponseHandler(this);
                    if (bl) {
                        iMAPProtocol.disconnect();
                    } else {
                        iMAPProtocol.logout();
                    }
                }
                catch (ProtocolException protocolException) {
                    // empty catch block
                }
                --n;
            } while (true);
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
    private Namespaces getNamespaces() throws MessagingException {
        block13 : {
            IMAPStore iMAPStore = this;
            // MONITORENTER : iMAPStore
            this.checkConnected();
            IMAPProtocol iMAPProtocol = null;
            Namespaces namespaces = this.namespaces;
            if (namespaces == null) {
                try {
                    iMAPProtocol = this.getStoreProtocol();
                    this.namespaces = iMAPProtocol.namespace();
                    break block13;
                }
                catch (BadCommandException badCommandException) {}
                break block13;
                catch (ConnectionException connectionException) {
                    throw new StoreClosedException((Store)this, connectionException.getMessage());
                }
                catch (ProtocolException protocolException) {
                    throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                }
                finally {
                    this.releaseStoreProtocol(iMAPProtocol);
                    if (iMAPProtocol != null) break block13;
                    this.cleanup();
                }
            }
        }
        Namespaces namespaces = this.namespaces;
        // MONITOREXIT : iMAPStore
        return namespaces;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void login(IMAPProtocol iMAPProtocol, String string2, String string3) throws ProtocolException {
        block16 : {
            block15 : {
                if (this.enableStartTLS && iMAPProtocol.hasCapability("STARTTLS")) {
                    iMAPProtocol.startTLS();
                    iMAPProtocol.capability();
                }
                if (iMAPProtocol.isAuthenticated()) break block15;
                iMAPProtocol.getCapabilities().put((Object)"__PRELOGIN__", (Object)"");
                String string4 = this.authorizationID != null ? this.authorizationID : (this.proxyAuthUser != null ? this.proxyAuthUser : string2);
                if (this.enableSASL) {
                    iMAPProtocol.sasllogin(this.saslMechanisms, this.saslRealm, string4, string2, string3);
                }
                if (!iMAPProtocol.isAuthenticated()) {
                    if (iMAPProtocol.hasCapability("AUTH=PLAIN") && !this.disableAuthPlain) {
                        iMAPProtocol.authplain(string4, string2, string3);
                    } else if ((iMAPProtocol.hasCapability("AUTH-LOGIN") || iMAPProtocol.hasCapability("AUTH=LOGIN")) && !this.disableAuthLogin) {
                        iMAPProtocol.authlogin(string2, string3);
                    } else {
                        if (iMAPProtocol.hasCapability("LOGINDISABLED")) {
                            throw new ProtocolException("No login methods supported!");
                        }
                        iMAPProtocol.login(string2, string3);
                    }
                }
                if (this.proxyAuthUser != null) {
                    iMAPProtocol.proxyauth(this.proxyAuthUser);
                }
                if (iMAPProtocol.hasCapability("__PRELOGIN__")) break block16;
            }
            return;
        }
        try {
            iMAPProtocol.capability();
            return;
        }
        catch (ConnectionException connectionException) {
            throw connectionException;
        }
        catch (ProtocolException protocolException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Folder[] namespaceToFolders(Namespaces.Namespace[] arrnamespace, String string2) {
        Folder[] arrfolder = new Folder[arrnamespace.length];
        int n = 0;
        while (n < arrfolder.length) {
            String string3 = arrnamespace[n].prefix;
            if (string2 == null) {
                int n2 = string3.length();
                if (n2 > 0 && string3.charAt(n2 - 1) == arrnamespace[n].delimiter) {
                    string3 = string3.substring(0, n2 - 1);
                }
            } else {
                string3 = String.valueOf((Object)string3) + string2;
            }
            char c = arrnamespace[n].delimiter;
            boolean bl = string2 == null;
            arrfolder[n] = new IMAPFolder(string3, c, this, bl);
            ++n;
        }
        return arrfolder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void timeoutConnections() {
        ConnectionPool connectionPool;
        ConnectionPool connectionPool2 = connectionPool = this.pool;
        synchronized (connectionPool2) {
            if (System.currentTimeMillis() - this.pool.lastTimePruned > this.pool.pruningInterval && this.pool.authenticatedConnections.size() > 1) {
                if (this.pool.debug) {
                    this.out.println("DEBUG: checking for connections to prune: " + (System.currentTimeMillis() - this.pool.lastTimePruned));
                    this.out.println("DEBUG: clientTimeoutInterval: " + this.pool.clientTimeoutInterval);
                }
                int n = -1 + this.pool.authenticatedConnections.size();
                do {
                    if (n <= 0) {
                        ConnectionPool.access$0(this.pool, System.currentTimeMillis());
                        break;
                    }
                    IMAPProtocol iMAPProtocol = (IMAPProtocol)this.pool.authenticatedConnections.elementAt(n);
                    if (this.pool.debug) {
                        this.out.println("DEBUG: protocol last used: " + (System.currentTimeMillis() - iMAPProtocol.getTimestamp()));
                    }
                    if (System.currentTimeMillis() - iMAPProtocol.getTimestamp() > this.pool.clientTimeoutInterval) {
                        if (this.pool.debug) {
                            this.out.println("DEBUG: authenticated connection timed out");
                            this.out.println("DEBUG: logging out the connection");
                        }
                        iMAPProtocol.removeResponseHandler(this);
                        this.pool.authenticatedConnections.removeElementAt(n);
                        try {
                            iMAPProtocol.logout();
                        }
                        catch (ProtocolException protocolException) {}
                    }
                    --n;
                } while (true);
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void waitIfIdle() throws ProtocolException {
        if (IMAPStore.$assertionsDisabled || Thread.holdsLock((Object)this.pool)) ** GOTO lbl10
        throw new AssertionError();
lbl-1000: // 1 sources:
        {
            if (ConnectionPool.access$19(this.pool) == 1) {
                ConnectionPool.access$21(this.pool).idleAbort();
                ConnectionPool.access$20(this.pool, 2);
            }
            try {
                this.pool.wait();
                continue;
            }
            catch (InterruptedException var1_1) {}
lbl10: // 3 sources:
            ** while (ConnectionPool.access$19((ConnectionPool)this.pool) != 0)
        }
lbl11: // 1 sources:
    }

    boolean allowReadOnlySelect() {
        String string2 = this.session.getProperty("mail." + this.name + ".allowreadonlyselect");
        return string2 != null && string2.equalsIgnoreCase("true");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void close() throws MessagingException {
        ConnectionPool connectionPool2;
        ConnectionPool connectionPool;
        IMAPStore iMAPStore = this;
        // MONITORENTER : iMAPStore
        boolean bl = super.isConnected();
        if (!bl) {
            // MONITOREXIT : iMAPStore
            return;
        }
        IMAPProtocol iMAPProtocol = null;
        ConnectionPool connectionPool3 = connectionPool2 = this.pool;
        // MONITORENTER : connectionPool3
        boolean bl2 = this.pool.authenticatedConnections.isEmpty();
        // MONITOREXIT : connectionPool3
        if (bl2) {
            boolean bl3 = this.pool.debug;
            iMAPProtocol = null;
            if (bl3) {
                this.out.println("DEBUG: close() - no connections ");
            }
            this.cleanup();
            this.releaseStoreProtocol(null);
            return;
        }
        iMAPProtocol = this.getStoreProtocol();
        ConnectionPool connectionPool4 = connectionPool = this.pool;
        // MONITORENTER : connectionPool4
        this.pool.authenticatedConnections.removeElement((Object)iMAPProtocol);
        // MONITOREXIT : connectionPool4
        try {
            iMAPProtocol.logout();
        }
        catch (Throwable throwable) {
            this.releaseStoreProtocol(iMAPProtocol);
            throw throwable;
        }
        {
            catch (ProtocolException protocolException) {
                this.cleanup();
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        this.releaseStoreProtocol(iMAPProtocol);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }

    int getAppendBufferSize() {
        return this.appendBufferSize;
    }

    boolean getConnectionPoolDebug() {
        return this.pool.debug;
    }

    public Folder getDefaultFolder() throws MessagingException {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            this.checkConnected();
            DefaultFolder defaultFolder = new DefaultFolder(this);
            return defaultFolder;
        }
    }

    int getFetchBlockSize() {
        return this.blksize;
    }

    public Folder getFolder(String string2) throws MessagingException {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            this.checkConnected();
            IMAPFolder iMAPFolder = new IMAPFolder(string2, '\uffff', this);
            return iMAPFolder;
        }
    }

    public Folder getFolder(URLName uRLName) throws MessagingException {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            this.checkConnected();
            IMAPFolder iMAPFolder = new IMAPFolder(uRLName.getFile(), '\uffff', this);
            return iMAPFolder;
        }
    }

    int getMinIdleTime() {
        return this.minIdleTime;
    }

    public Folder[] getPersonalNamespaces() throws MessagingException {
        Namespaces namespaces = this.getNamespaces();
        if (namespaces == null || namespaces.personal == null) {
            return super.getPersonalNamespaces();
        }
        return this.namespaceToFolders(namespaces.personal, null);
    }

    /*
     * Exception decompiling
     */
    IMAPProtocol getProtocol(IMAPFolder var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 12[CATCHBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Quota[] getQuota(String string2) throws MessagingException {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            this.checkConnected();
            (Quota[])null;
            IMAPProtocol iMAPProtocol = null;
            try {
                iMAPProtocol = this.getStoreProtocol();
                Quota[] arrquota = iMAPProtocol.getQuotaRoot(string2);
                return arrquota;
            }
            catch (BadCommandException badCommandException) {
                throw new MessagingException("QUOTA not supported", (Exception)badCommandException);
            }
            catch (ConnectionException connectionException) {
                throw new StoreClosedException((Store)this, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
            finally {
                this.releaseStoreProtocol(iMAPProtocol);
                if (iMAPProtocol == null) {
                    this.cleanup();
                }
            }
        }
    }

    Session getSession() {
        return this.session;
    }

    public Folder[] getSharedNamespaces() throws MessagingException {
        Namespaces namespaces = this.getNamespaces();
        if (namespaces == null || namespaces.shared == null) {
            return super.getSharedNamespaces();
        }
        return this.namespaceToFolders(namespaces.shared, null);
    }

    int getStatusCacheTimeout() {
        return this.statusCacheTimeout;
    }

    /*
     * Exception decompiling
     */
    IMAPProtocol getStoreProtocol() throws ProtocolException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 10[CATCHBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
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

    public Folder[] getUserNamespaces(String string2) throws MessagingException {
        Namespaces namespaces = this.getNamespaces();
        if (namespaces == null || namespaces.otherUsers == null) {
            return super.getUserNamespaces(string2);
        }
        return this.namespaceToFolders(namespaces.otherUsers, string2);
    }

    @Override
    public void handleResponse(Response response) {
        if (response.isOK() || response.isNO() || response.isBAD() || response.isBYE()) {
            this.handleResponseCode(response);
        }
        if (response.isBYE()) {
            if (this.debug) {
                this.out.println("DEBUG: IMAPStore connection dead");
            }
            if (this.connected) {
                this.cleanup(response.isSynthetic());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void handleResponseCode(Response response) {
        String string2 = response.getRest();
        boolean bl = string2.startsWith("[");
        boolean bl2 = false;
        if (bl) {
            int n = string2.indexOf(93);
            bl2 = false;
            if (n > 0) {
                boolean bl3 = string2.substring(0, n + 1).equalsIgnoreCase("[ALERT]");
                bl2 = false;
                if (bl3) {
                    bl2 = true;
                }
            }
            string2 = string2.substring(n + 1).trim();
        }
        if (bl2) {
            this.notifyStoreListeners(1, string2);
            return;
        } else {
            if (!response.isUnTagged() || string2.length() <= 0) return;
            {
                this.notifyStoreListeners(2, string2);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasCapability(String string2) throws MessagingException {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            IMAPProtocol iMAPProtocol = null;
            try {
                iMAPProtocol = this.getStoreProtocol();
                boolean bl = iMAPProtocol.hasCapability(string2);
                return bl;
            }
            catch (ProtocolException protocolException) {
                if (iMAPProtocol == null) {
                    this.cleanup();
                }
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
            finally {
                this.releaseStoreProtocol(iMAPProtocol);
            }
        }
    }

    boolean hasSeparateStoreConnection() {
        return this.pool.separateStoreConnection;
    }

    /*
     * Exception decompiling
     */
    public void idle() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [37[UNCONDITIONALDOLOOP]], but top level block is 17[TRYBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnected() {
        boolean bl = false;
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            if (this.connected) {
                boolean bl2;
                IMAPProtocol iMAPProtocol = null;
                try {
                    iMAPProtocol = this.getStoreProtocol();
                    iMAPProtocol.noop();
                    bl2 = super.isConnected();
                    return bl2;
                }
                catch (ProtocolException protocolException) {
                    bl2 = super.isConnected();
                    if (iMAPProtocol != null) return bl2;
                    this.cleanup();
                    bl2 = super.isConnected();
                    return bl2;
                }
                finally {
                    this.releaseStoreProtocol(iMAPProtocol);
                }
                return bl2;
            }
            super.setConnected(false);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isConnectionPoolFull() {
        ConnectionPool connectionPool;
        ConnectionPool connectionPool2 = connectionPool = this.pool;
        synchronized (connectionPool2) {
            if (this.pool.debug) {
                this.out.println("DEBUG: current size: " + this.pool.authenticatedConnections.size() + "   pool size: " + this.pool.poolSize);
            }
            if (this.pool.authenticatedConnections.size() < this.pool.poolSize) return false;
            return true;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected boolean protocolConnect(String var1_1, int var2_2, String var3_3, String var4_4) throws MessagingException {
        var20_5 = this;
        // MONITORENTER : var20_5
        if (var1_1 == null || var4_4 == null || var3_3 == null) {
            if (this.debug) {
                var7_6 = this.out;
                var8_7 = new StringBuilder("DEBUG: protocolConnect returning false, host=").append(var1_1).append(", user=").append(var3_3).append(", password=");
                var9_8 = var4_4 != null ? "<non-null>" : "<null>";
                var7_6.println(var8_7.append(var9_8).toString());
            }
            var6_9 = false;
            // MONITOREXIT : var20_5
            return var6_9;
        }
        if (var2_2 != -1) {
            this.port = var2_2;
        } else {
            var19_16 = this.session.getProperty("mail." + this.name + ".port");
            if (var19_16 != null) {
                this.port = Integer.parseInt((String)var19_16);
            }
        }
        if (this.port == -1) {
            this.port = this.defaultPort;
        }
        var21_11 = var14_10 = this.pool;
        // MONITORENTER : var21_11
        var16_12 = ConnectionPool.access$10(this.pool).isEmpty();
        // MONITOREXIT : var21_11
        if (var16_12) {
            var13_13 = new IMAPProtocol(this.name, var1_1, this.port, this.session.getDebug(), this.session.getDebugOut(), this.session.getProperties(), this.isSSL);
            if (this.debug) {
                this.out.println("DEBUG: protocolConnect login, host=" + var1_1 + ", user=" + var3_3 + ", password=<non-null>");
            }
            this.login(var13_13, var3_3, var4_4);
            var13_13.addResponseHandler(this);
            this.host = var1_1;
            this.user = var3_3;
            this.password = var4_4;
            var22_15 = var17_14 = this.pool;
            // MONITORENTER : var22_15
            ConnectionPool.access$10(this.pool).addElement((Object)var13_13);
            // MONITOREXIT : var22_15
        }
        this.connected = true;
        return true;
        catch (CommandFailedException var12_18) {
            block34 : {
                var13_13 = null;
                break block34;
                {
                    catch (ProtocolException var11_21) {
                        throw new MessagingException(var11_22.getMessage(), (Exception)var11_22);
                    }
                }
                catch (IOException var10_26) {
                    throw new MessagingException(var10_24.getMessage(), (Exception)var10_24);
                }
            }
lbl51: // 2 sources:
            do {
                if (var13_13 == null) throw new AuthenticationFailedException(var12_19.getResponse().getRest());
                var13_13.disconnect();
                throw new AuthenticationFailedException(var12_19.getResponse().getRest());
                break;
            } while (true);
        }
        catch (CommandFailedException var12_20) {
            ** continue;
        }
        catch (IOException var10_25) {
            throw new MessagingException(var10_24.getMessage(), (Exception)var10_24);
        }
        catch (ProtocolException var11_23) {
            throw new MessagingException(var11_22.getMessage(), (Exception)var11_22);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void releaseProtocol(IMAPFolder iMAPFolder, IMAPProtocol iMAPProtocol) {
        ConnectionPool connectionPool;
        ConnectionPool connectionPool2 = connectionPool = this.pool;
        synchronized (connectionPool2) {
            if (iMAPProtocol != null) {
                if (!this.isConnectionPoolFull()) {
                    iMAPProtocol.addResponseHandler(this);
                    this.pool.authenticatedConnections.addElement((Object)iMAPProtocol);
                    if (this.debug) {
                        this.out.println("DEBUG: added an Authenticated connection -- size: " + this.pool.authenticatedConnections.size());
                    }
                } else {
                    if (this.debug) {
                        this.out.println("DEBUG: pool is full, not adding an Authenticated connection");
                    }
                    try {
                        iMAPProtocol.logout();
                    }
                    catch (ProtocolException protocolException) {}
                }
            }
            if (this.pool.folders != null) {
                this.pool.folders.removeElement((Object)iMAPFolder);
            }
            this.timeoutConnections();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void releaseStoreProtocol(IMAPProtocol iMAPProtocol) {
        ConnectionPool connectionPool;
        if (iMAPProtocol == null) {
            return;
        }
        ConnectionPool connectionPool2 = connectionPool = this.pool;
        synchronized (connectionPool2) {
            ConnectionPool.access$15(this.pool, false);
            this.pool.notifyAll();
            if (this.pool.debug) {
                this.out.println("DEBUG: releaseStoreProtocol()");
            }
            this.timeoutConnections();
            return;
        }
    }

    public void setPassword(String string2) {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            this.password = string2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setQuota(Quota quota) throws MessagingException {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            this.checkConnected();
            IMAPProtocol iMAPProtocol = null;
            try {
                iMAPProtocol = this.getStoreProtocol();
                iMAPProtocol.setQuota(quota);
                return;
            }
            catch (BadCommandException badCommandException) {
                throw new MessagingException("QUOTA not supported", (Exception)badCommandException);
            }
            catch (ConnectionException connectionException) {
                throw new StoreClosedException((Store)this, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
            finally {
                this.releaseStoreProtocol(iMAPProtocol);
                if (iMAPProtocol == null) {
                    this.cleanup();
                }
            }
        }
    }

    public void setUsername(String string2) {
        IMAPStore iMAPStore = this;
        synchronized (iMAPStore) {
            this.user = string2;
            return;
        }
    }

    static class ConnectionPool {
        private static final int ABORTING = 2;
        private static final int IDLE = 1;
        private static final int RUNNING;
        private Vector authenticatedConnections = new Vector();
        private long clientTimeoutInterval = 45000L;
        private boolean debug = false;
        private Vector folders;
        private IMAPProtocol idleProtocol;
        private int idleState = 0;
        private long lastTimePruned;
        private int poolSize = 1;
        private long pruningInterval = 60000L;
        private boolean separateStoreConnection = false;
        private long serverTimeoutInterval = 1800000L;
        private boolean storeConnectionInUse = false;

        ConnectionPool() {
        }

        static /* synthetic */ void access$0(ConnectionPool connectionPool, long l) {
            connectionPool.lastTimePruned = l;
        }

        static /* synthetic */ void access$1(ConnectionPool connectionPool, boolean bl) {
            connectionPool.debug = bl;
        }

        static /* synthetic */ boolean access$12(ConnectionPool connectionPool) {
            return connectionPool.storeConnectionInUse;
        }

        static /* synthetic */ void access$14(ConnectionPool connectionPool, Vector vector) {
            connectionPool.folders = vector;
        }

        static /* synthetic */ void access$15(ConnectionPool connectionPool, boolean bl) {
            connectionPool.storeConnectionInUse = bl;
        }

        static /* synthetic */ void access$18(ConnectionPool connectionPool, IMAPProtocol iMAPProtocol) {
            connectionPool.idleProtocol = iMAPProtocol;
        }

        static /* synthetic */ int access$19(ConnectionPool connectionPool) {
            return connectionPool.idleState;
        }

        static /* synthetic */ void access$2(ConnectionPool connectionPool, int n) {
            connectionPool.poolSize = n;
        }

        static /* synthetic */ void access$20(ConnectionPool connectionPool, int n) {
            connectionPool.idleState = n;
        }

        static /* synthetic */ IMAPProtocol access$21(ConnectionPool connectionPool) {
            return connectionPool.idleProtocol;
        }

        static /* synthetic */ void access$5(ConnectionPool connectionPool, long l) {
            connectionPool.clientTimeoutInterval = l;
        }

        static /* synthetic */ void access$7(ConnectionPool connectionPool, long l) {
            connectionPool.serverTimeoutInterval = l;
        }

        static /* synthetic */ void access$9(ConnectionPool connectionPool, boolean bl) {
            connectionPool.separateStoreConnection = bl;
        }
    }

}

