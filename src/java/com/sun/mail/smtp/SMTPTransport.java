/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedInputStream
 *  java.io.BufferedOutputStream
 *  java.io.BufferedReader
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.io.Reader
 *  java.io.StringReader
 *  java.lang.AssertionError
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringIndexOutOfBoundsException
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.net.InetAddress
 *  java.net.Socket
 *  java.net.UnknownHostException
 *  java.util.Hashtable
 *  java.util.Locale
 *  java.util.Properties
 *  java.util.StringTokenizer
 *  java.util.Vector
 *  javax.mail.Address
 *  javax.mail.BodyPart
 *  javax.mail.Message
 *  javax.mail.MessagingException
 *  javax.mail.SendFailedException
 *  javax.mail.Session
 *  javax.mail.Transport
 *  javax.mail.URLName
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimeMultipart
 *  javax.mail.internet.MimePart
 */
package com.sun.mail.smtp;

import com.sun.mail.smtp.DigestMD5;
import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.smtp.SMTPOutputStream;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64EncoderStream;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

public class SMTPTransport
extends Transport {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] CRLF;
    private static final String UNKNOWN = "UNKNOWN";
    private static char[] hexchar;
    private static final String[] ignoreList;
    private Address[] addresses;
    private SMTPOutputStream dataStream;
    private int defaultPort;
    private MessagingException exception;
    private Hashtable extMap;
    private Address[] invalidAddr;
    private boolean isSSL;
    private int lastReturnCode;
    private String lastServerResponse;
    private LineInputStream lineInputStream;
    private String localHostName;
    private DigestMD5 md5support;
    private MimeMessage message;
    private String name;
    private PrintStream out;
    private boolean quitWait;
    private boolean reportSuccess;
    private String saslRealm;
    private boolean sendPartiallyFailed;
    private BufferedInputStream serverInput;
    private OutputStream serverOutput;
    private Socket serverSocket;
    private boolean useRset;
    private boolean useStartTLS;
    private Address[] validSentAddr;
    private Address[] validUnsentAddr;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !SMTPTransport.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        ignoreList = new String[]{"Bcc", "Content-Length"};
        CRLF = new byte[]{13, 10};
        hexchar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public SMTPTransport(Session session, URLName uRLName) {
        this(session, uRLName, "smtp", 25, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SMTPTransport(Session session, URLName uRLName, String string2, int n, boolean bl) {
        boolean bl2 = true;
        super(session, uRLName);
        this.name = "smtp";
        this.defaultPort = 25;
        this.isSSL = false;
        this.sendPartiallyFailed = false;
        this.quitWait = false;
        this.saslRealm = UNKNOWN;
        if (uRLName != null) {
            string2 = uRLName.getProtocol();
        }
        this.name = string2;
        this.defaultPort = n;
        this.isSSL = bl;
        this.out = session.getDebugOut();
        String string3 = session.getProperty("mail." + string2 + ".quitwait");
        boolean bl3 = string3 != null && !string3.equalsIgnoreCase("true") ? false : bl2;
        this.quitWait = bl3;
        String string4 = session.getProperty("mail." + string2 + ".reportsuccess");
        boolean bl4 = string4 != null && string4.equalsIgnoreCase("true") ? bl2 : false;
        this.reportSuccess = bl4;
        String string5 = session.getProperty("mail." + string2 + ".starttls.enable");
        boolean bl5 = string5 != null && string5.equalsIgnoreCase("true") ? bl2 : false;
        this.useStartTLS = bl5;
        String string6 = session.getProperty("mail." + string2 + ".userset");
        if (string6 == null || !string6.equalsIgnoreCase("true")) {
            bl2 = false;
        }
        this.useRset = bl2;
    }

    private void closeConnection() throws MessagingException {
        try {
            if (this.serverSocket != null) {
                this.serverSocket.close();
            }
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("Server Close Failed", (Exception)((Object)iOException));
        }
        finally {
            this.serverSocket = null;
            this.serverOutput = null;
            this.serverInput = null;
            this.lineInputStream = null;
            if (super.isConnected()) {
                super.close();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean convertTo8Bit(MimePart mimePart) {
        boolean bl;
        block11 : {
            block12 : {
                bl = false;
                boolean bl2 = mimePart.isMimeType("text/*");
                bl = false;
                if (!bl2) break block11;
                String string2 = mimePart.getEncoding();
                bl = false;
                if (string2 == null) return bl;
                if (string2.equalsIgnoreCase("quoted-printable")) break block12;
                boolean bl3 = string2.equalsIgnoreCase("base64");
                bl = false;
                if (!bl3) return bl;
            }
            boolean bl4 = this.is8Bit(mimePart.getInputStream());
            bl = false;
            if (!bl4) return bl;
            mimePart.setContent(mimePart.getContent(), mimePart.getContentType());
            mimePart.setHeader("Content-Transfer-Encoding", "8bit");
            return true;
        }
        boolean bl5 = mimePart.isMimeType("multipart/*");
        bl = false;
        if (!bl5) return bl;
        MimeMultipart mimeMultipart = (MimeMultipart)mimePart.getContent();
        int n = mimeMultipart.getCount();
        for (int i = 0; i < n; ++i) {
            try {
                boolean bl6 = this.convertTo8Bit((MimePart)mimeMultipart.getBodyPart(i));
                if (!bl6) continue;
                bl = true;
            }
            catch (MessagingException messagingException) {
                return bl;
            }
            catch (IOException iOException) {
                // empty catch block
                return bl;
            }
            continue;
        }
        return bl;
    }

    /*
     * Exception decompiling
     */
    private void expandGroups() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[UNCONDITIONALDOLOOP]], but top level block is 0[TRYBLOCK]
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
    private DigestMD5 getMD5() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            if (this.md5support != null) return this.md5support;
            PrintStream printStream = this.debug ? this.out : null;
            this.md5support = new DigestMD5(printStream);
            return this.md5support;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initStreams() throws IOException {
        Properties properties = this.session.getProperties();
        PrintStream printStream = this.session.getDebugOut();
        boolean bl = this.session.getDebug();
        String string2 = properties.getProperty("mail.debug.quote");
        boolean bl2 = string2 != null && string2.equalsIgnoreCase("true");
        TraceInputStream traceInputStream = new TraceInputStream(this.serverSocket.getInputStream(), (OutputStream)printStream);
        traceInputStream.setTrace(bl);
        traceInputStream.setQuote(bl2);
        TraceOutputStream traceOutputStream = new TraceOutputStream(this.serverSocket.getOutputStream(), (OutputStream)printStream);
        traceOutputStream.setTrace(bl);
        traceOutputStream.setQuote(bl2);
        this.serverOutput = new BufferedOutputStream((OutputStream)traceOutputStream);
        this.serverInput = new BufferedInputStream((InputStream)traceInputStream);
        this.lineInputStream = new LineInputStream((InputStream)this.serverInput);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean is8Bit(InputStream var1_1) {
        var2_2 = 0;
        var3_3 = false;
        do lbl-1000: // 3 sources:
        {
            var5_4 = var1_1.read();
            if (var5_4 >= 0) break block7;
            if (this.debug == false) return var3_3;
            if (var3_3 == false) return var3_3;
            break;
        } while (true);
        catch (IOException var4_6) {
            return false;
        }
        {
            block7 : {
                this.out.println("DEBUG SMTP: found an 8bit part");
                return var3_3;
            }
            var6_5 = var5_4 & 255;
            if (var6_5 == 13 || var6_5 == 10) {
                var2_2 = 0;
            } else {
                if (var6_5 == 0) {
                    return false;
                }
                if (++var2_2 > 998) {
                    return false;
                }
            }
            if (var6_5 <= 127) continue;
            var3_3 = true;
            ** while (true)
        }
    }

    private boolean isNotLastLine(String string2) {
        return string2 != null && string2.length() >= 4 && string2.charAt(3) == '-';
    }

    /*
     * Enabled aggressive block sorting
     */
    private void issueSendCommand(String string2, int n) throws MessagingException {
        this.sendCommand(string2);
        int n2 = this.readServerResponse();
        if (n2 == n) {
            return;
        }
        int n3 = this.validSentAddr == null ? 0 : this.validSentAddr.length;
        int n4 = this.validUnsentAddr == null ? 0 : this.validUnsentAddr.length;
        Address[] arraddress = new Address[n3 + n4];
        if (n3 > 0) {
            System.arraycopy((Object)this.validSentAddr, (int)0, (Object)arraddress, (int)0, (int)n3);
        }
        if (n4 > 0) {
            System.arraycopy((Object)this.validUnsentAddr, (int)0, (Object)arraddress, (int)n3, (int)n4);
        }
        this.validSentAddr = null;
        this.validUnsentAddr = arraddress;
        if (this.debug) {
            this.out.println("DEBUG SMTP: got response code " + n2 + ", with response: " + this.lastServerResponse);
        }
        String string3 = this.lastServerResponse;
        int n5 = this.lastReturnCode;
        if (this.serverSocket != null) {
            this.issueCommand("RSET", 250);
        }
        this.lastServerResponse = string3;
        this.lastReturnCode = n5;
        throw new SMTPSendFailedException(string2, n2, this.lastServerResponse, (Exception)((Object)this.exception), this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
    }

    private String normalizeAddress(String string2) {
        if (!string2.startsWith("<") && !string2.endsWith(">")) {
            string2 = "<" + string2 + ">";
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void openServer() throws MessagingException {
        int n = -1;
        String string2 = UNKNOWN;
        try {
            n = this.serverSocket.getPort();
            string2 = this.serverSocket.getInetAddress().getHostName();
            if (this.debug) {
                this.out.println("DEBUG SMTP: starting protocol to host \"" + string2 + "\", port " + n);
            }
            this.initStreams();
            int n2 = this.readServerResponse();
            if (n2 != 220) {
                this.serverSocket.close();
                this.serverSocket = null;
                this.serverOutput = null;
                this.serverInput = null;
                this.lineInputStream = null;
                if (this.debug) {
                    this.out.println("DEBUG SMTP: got bad greeting from host \"" + string2 + "\", port: " + n + ", response: " + n2 + "\n");
                }
                throw new MessagingException("Got bad greeting from SMTP host: " + string2 + ", port: " + n + ", response: " + n2);
            }
            if (this.debug) {
                this.out.println("DEBUG SMTP: protocol started to host \"" + string2 + "\", port: " + n + "\n");
            }
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("Could not start protocol to SMTP host: " + string2 + ", port: " + n, (Exception)((Object)iOException));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void openServer(String string2, int n) throws MessagingException {
        if (this.debug) {
            this.out.println("DEBUG SMTP: trying to connect to host \"" + string2 + "\", port " + n + ", isSSL " + this.isSSL);
        }
        try {
            this.serverSocket = SocketFetcher.getSocket(string2, n, this.session.getProperties(), "mail." + this.name, this.isSSL);
            n = this.serverSocket.getPort();
            this.initStreams();
            int n2 = this.readServerResponse();
            if (n2 != 220) {
                this.serverSocket.close();
                this.serverSocket = null;
                this.serverOutput = null;
                this.serverInput = null;
                this.lineInputStream = null;
                if (this.debug) {
                    this.out.println("DEBUG SMTP: could not connect to host \"" + string2 + "\", port: " + n + ", response: " + n2 + "\n");
                }
                throw new MessagingException("Could not connect to SMTP host: " + string2 + ", port: " + n + ", response: " + n2);
            }
            if (this.debug) {
                this.out.println("DEBUG SMTP: connected to host \"" + string2 + "\", port: " + n + "\n");
            }
            return;
        }
        catch (UnknownHostException unknownHostException) {
            throw new MessagingException("Unknown SMTP host: " + string2, (Exception)((Object)unknownHostException));
        }
        catch (IOException iOException) {
            throw new MessagingException("Could not connect to SMTP host: " + string2 + ", port: " + n, (Exception)((Object)iOException));
        }
    }

    private void sendCommand(byte[] arrby) throws MessagingException {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)((Object)this))) {
            throw new AssertionError();
        }
        try {
            this.serverOutput.write(arrby);
            this.serverOutput.write(CRLF);
            this.serverOutput.flush();
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("Can't send command to SMTP host", (Exception)((Object)iOException));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static String xtext(String string2) {
        StringBuffer stringBuffer = null;
        int n = 0;
        do {
            if (n >= string2.length()) {
                if (stringBuffer == null) return string2;
                return stringBuffer.toString();
            }
            char c = string2.charAt(n);
            if (c >= '') {
                throw new IllegalArgumentException("Non-ASCII character in SMTP submitter: " + string2);
            }
            if (c < '!' || c > '~' || c == '+' || c == '=') {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(4 + string2.length());
                    stringBuffer.append(string2.substring(0, n));
                }
                stringBuffer.append('+');
                stringBuffer.append(hexchar[(c & 240) >> 4]);
                stringBuffer.append(hexchar[c & 15]);
            } else if (stringBuffer != null) {
                stringBuffer.append(c);
            }
            ++n;
        } while (true);
    }

    protected void checkConnected() {
        if (!super.isConnected()) {
            throw new IllegalStateException("Not connected");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() throws MessagingException {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            boolean bl = super.isConnected();
            if (bl) {
                try {
                    if (this.serverSocket != null) {
                        int n;
                        this.sendCommand("QUIT");
                        if (this.quitWait && (n = this.readServerResponse()) != 221 && n != -1) {
                            this.out.println("DEBUG SMTP: QUIT failed with " + n);
                        }
                    }
                }
                finally {
                    this.closeConnection();
                }
            }
            return;
        }
    }

    public void connect(Socket socket) throws MessagingException {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.serverSocket = socket;
            super.connect();
            return;
        }
    }

    protected OutputStream data() throws MessagingException {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)((Object)this))) {
            throw new AssertionError();
        }
        this.issueSendCommand("DATA", 354);
        this.dataStream = new SMTPOutputStream(this.serverOutput);
        return this.dataStream;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected boolean ehlo(String var1_1) throws MessagingException {
        block6 : {
            var2_2 = var1_1 != null ? "EHLO " + var1_1 : "EHLO";
            {
                this.sendCommand(var2_2);
                var3_3 = this.readServerResponse();
                if (var3_3 != 250) break block6;
                var4_4 = new BufferedReader((Reader)new StringReader(this.lastServerResponse));
                this.extMap = new Hashtable();
                var5_5 = true;
                ** try [egrp 0[TRYBLOCK] [0 : 73->246)] { 
lbl9: // 4 sources:
                while ((var8_6 = var4_4.readLine()) != null) {
                    if (var5_5) {
                        var5_5 = false;
                        continue;
                    }
                    if (var8_6.length() < 5) continue;
                    var9_7 = var8_6.substring(4);
                    var10_8 = var9_7.indexOf(32);
                    var11_9 = "";
                    if (var10_8 > 0) {
                        var11_9 = var9_7.substring(var10_8 + 1);
                        var9_7 = var9_7.substring(0, var10_8);
                    }
                    if (this.debug) {
                        this.out.println("DEBUG SMTP: Found extension \"" + var9_7 + "\", arg \"" + var11_9 + "\"");
                    }
                    this.extMap.put((Object)var9_7.toUpperCase(Locale.ENGLISH), (Object)var11_9);
                }
                break block6;
            }
lbl25: // 1 sources:
            catch (IOException var6_11) {}
        }
        var7_10 = false;
        if (var3_3 != 250) return var7_10;
        return true;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        try {
            this.closeConnection();
            return;
        }
        catch (MessagingException messagingException) {
            return;
        }
    }

    protected void finishData() throws IOException, MessagingException {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)((Object)this))) {
            throw new AssertionError();
        }
        this.dataStream.ensureAtBOL();
        this.issueSendCommand(".", 250);
    }

    public String getExtensionParameter(String string2) {
        if (this.extMap == null) {
            return null;
        }
        return (String)this.extMap.get((Object)string2.toUpperCase(Locale.ENGLISH));
    }

    public int getLastReturnCode() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            int n = this.lastReturnCode;
            return n;
        }
    }

    public String getLastServerResponse() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            String string2 = this.lastServerResponse;
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getLocalHost() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            try {
                if (this.localHostName == null || this.localHostName.length() <= 0) {
                    this.localHostName = this.session.getProperty("mail." + this.name + ".localhost");
                }
                if (this.localHostName == null || this.localHostName.length() <= 0) {
                    this.localHostName = this.session.getProperty("mail." + this.name + ".localaddress");
                }
                if (this.localHostName != null) {
                    if (this.localHostName.length() > 0) return this.localHostName;
                }
                InetAddress inetAddress = InetAddress.getLocalHost();
                this.localHostName = inetAddress.getHostName();
                if (this.localHostName != null) return this.localHostName;
                this.localHostName = "[" + inetAddress.getHostAddress() + "]";
                return this.localHostName;
            }
            catch (UnknownHostException unknownHostException) {}
            return this.localHostName;
        }
    }

    public boolean getReportSuccess() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            boolean bl = this.reportSuccess;
            return bl;
        }
    }

    public String getSASLRealm() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            if (this.saslRealm == UNKNOWN) {
                this.saslRealm = this.session.getProperty("mail." + this.name + ".sasl.realm");
                if (this.saslRealm == null) {
                    this.saslRealm = this.session.getProperty("mail." + this.name + ".saslrealm");
                }
            }
            String string2 = this.saslRealm;
            return string2;
        }
    }

    public boolean getStartTLS() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            boolean bl = this.useStartTLS;
            return bl;
        }
    }

    public boolean getUseRset() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            boolean bl = this.useRset;
            return bl;
        }
    }

    protected void helo(String string2) throws MessagingException {
        if (string2 != null) {
            this.issueCommand("HELO " + string2, 250);
            return;
        }
        this.issueCommand("HELO", 250);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnected() {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            boolean bl = super.isConnected();
            boolean bl2 = false;
            if (!bl) return bl2;
            try {
                int n;
                if (this.useRset) {
                    this.sendCommand("RSET");
                } else {
                    this.sendCommand("NOOP");
                }
                if ((n = this.readServerResponse()) >= 0 && n != 421) {
                    return true;
                }
                this.closeConnection();
                return false;
            }
            catch (Exception exception) {
                try {
                    this.closeConnection();
                    return false;
                }
                catch (MessagingException messagingException) {
                    return false;
                }
            }
        }
    }

    public void issueCommand(String string2, int n) throws MessagingException {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.sendCommand(string2);
            if (this.readServerResponse() != n) {
                throw new MessagingException(this.lastServerResponse);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void mailFrom() throws MessagingException {
        String string2;
        block17 : {
            boolean bl = this.message instanceof SMTPMessage;
            String string3 = null;
            if (bl) {
                string3 = ((SMTPMessage)this.message).getEnvelopeFrom();
            }
            if (string3 == null || string3.length() <= 0) {
                string3 = this.session.getProperty("mail." + this.name + ".from");
            }
            if (string3 == null || string3.length() <= 0) {
                Address[] arraddress;
                Object object = this.message != null && (arraddress = this.message.getFrom()) != null && arraddress.length > 0 ? arraddress[0] : InternetAddress.getLocalAddress((Session)this.session);
                if (object == null) {
                    throw new MessagingException("can't determine local email address");
                }
                string3 = object.getAddress();
            }
            string2 = "MAIL FROM:" + this.normalizeAddress(string3);
            if (this.supportsExtension("DSN")) {
                boolean bl2 = this.message instanceof SMTPMessage;
                String string4 = null;
                if (bl2) {
                    string4 = ((SMTPMessage)this.message).getDSNRet();
                }
                if (string4 == null) {
                    string4 = this.session.getProperty("mail." + this.name + ".dsn.ret");
                }
                if (string4 != null) {
                    string2 = String.valueOf((Object)string2) + " RET=" + string4;
                }
            }
            if (this.supportsExtension("AUTH")) {
                boolean bl3 = this.message instanceof SMTPMessage;
                String string5 = null;
                if (bl3) {
                    string5 = ((SMTPMessage)this.message).getSubmitter();
                }
                if (string5 == null) {
                    string5 = this.session.getProperty("mail." + this.name + ".submitter");
                }
                if (string5 != null) {
                    try {
                        String string6;
                        String string7 = SMTPTransport.xtext(string5);
                        string2 = string6 = String.valueOf((Object)string2) + " AUTH=" + string7;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        if (!this.debug) break block17;
                        this.out.println("DEBUG SMTP: ignoring invalid submitter: " + string5 + ", Exception: " + (Object)((Object)illegalArgumentException));
                    }
                }
            }
        }
        boolean bl = this.message instanceof SMTPMessage;
        String string8 = null;
        if (bl) {
            string8 = ((SMTPMessage)this.message).getMailExtension();
        }
        if (string8 == null) {
            string8 = this.session.getProperty("mail." + this.name + ".mailextension");
        }
        if (string8 != null && string8.length() > 0) {
            string2 = String.valueOf((Object)string2) + " " + string8;
        }
        this.issueSendCommand(string2, 250);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean protocolConnect(String string2, int n, String string3, String string4) throws MessagingException {
        String string5 = this.session.getProperty("mail." + this.name + ".ehlo");
        boolean bl = string5 == null || !string5.equalsIgnoreCase("false");
        String string6 = this.session.getProperty("mail." + this.name + ".auth");
        boolean bl2 = string6 != null && string6.equalsIgnoreCase("true");
        if (this.debug) {
            this.out.println("DEBUG SMTP: useEhlo " + bl + ", useAuth " + bl2);
        }
        if (bl2) {
            if (string3 == null) return false;
            if (string4 == null) {
                return false;
            }
        }
        if (n == -1) {
            String string7 = this.session.getProperty("mail." + this.name + ".port");
            n = string7 != null ? Integer.parseInt((String)string7) : this.defaultPort;
        }
        if (string2 == null || string2.length() == 0) {
            string2 = "localhost";
        }
        if (this.serverSocket != null) {
            this.openServer();
        } else {
            this.openServer(string2, n);
        }
        boolean bl3 = false;
        if (bl) {
            bl3 = this.ehlo(this.getLocalHost());
        }
        if (!bl3) {
            this.helo(this.getLocalHost());
        }
        if (this.useStartTLS && this.supportsExtension("STARTTLS")) {
            this.startTLS();
            this.ehlo(this.getLocalHost());
        }
        if (!bl2) {
            if (string3 == null) return true;
            if (string4 == null) return true;
        }
        if (!this.supportsExtension("AUTH")) {
            if (!this.supportsExtension("AUTH=LOGIN")) return true;
        }
        if (this.debug) {
            this.out.println("DEBUG SMTP: Attempt to authenticate");
            if (!this.supportsAuthentication("LOGIN") && this.supportsExtension("AUTH=LOGIN")) {
                this.out.println("DEBUG SMTP: use AUTH=LOGIN hack");
            }
        }
        if (this.supportsAuthentication("LOGIN") || this.supportsExtension("AUTH=LOGIN")) {
            int n2 = this.simpleCommand("AUTH LOGIN");
            if (n2 == 530) {
                this.startTLS();
                n2 = this.simpleCommand("AUTH LOGIN");
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream((OutputStream)byteArrayOutputStream, Integer.MAX_VALUE);
                if (n2 == 334) {
                    bASE64EncoderStream.write(ASCIIUtility.getBytes(string3));
                    bASE64EncoderStream.flush();
                    n2 = this.simpleCommand(byteArrayOutputStream.toByteArray());
                    byteArrayOutputStream.reset();
                }
                if (n2 != 334) return true;
                bASE64EncoderStream.write(ASCIIUtility.getBytes(string4));
                bASE64EncoderStream.flush();
                n2 = this.simpleCommand(byteArrayOutputStream.toByteArray());
                byteArrayOutputStream.reset();
                return true;
            }
            catch (IOException iOException) {
                if (n2 == 235) return true;
                this.closeConnection();
                return false;
            }
            finally {
                if (n2 != 235) {
                    this.closeConnection();
                    return false;
                }
            }
        }
        if (this.supportsAuthentication("PLAIN")) {
            int n3 = this.simpleCommand("AUTH PLAIN");
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream((OutputStream)byteArrayOutputStream, Integer.MAX_VALUE);
                if (n3 == 334) {
                    int n4;
                    bASE64EncoderStream.write(0);
                    bASE64EncoderStream.write(ASCIIUtility.getBytes(string3));
                    bASE64EncoderStream.write(0);
                    bASE64EncoderStream.write(ASCIIUtility.getBytes(string4));
                    bASE64EncoderStream.flush();
                    n3 = n4 = this.simpleCommand(byteArrayOutputStream.toByteArray());
                }
                if (n3 == 235) return true;
            }
            catch (IOException iOException) {
                if (n3 == 235) return true;
                this.closeConnection();
                return false;
            }
            catch (Throwable throwable) {
                if (n3 == 235) throw throwable;
                this.closeConnection();
                return false;
            }
            this.closeConnection();
            return false;
        }
        if (!this.supportsAuthentication("DIGEST-MD5")) return true;
        DigestMD5 digestMD5 = this.getMD5();
        if (digestMD5 == null) return true;
        int n5 = this.simpleCommand("AUTH DIGEST-MD5");
        if (n5 != 334) return true;
        try {
            int n6;
            String string8 = this.getSASLRealm();
            String string9 = this.lastServerResponse;
            n5 = this.simpleCommand(digestMD5.authClient(string2, string3, string4, string8, string9));
            if (n5 != 334) return true;
            boolean bl4 = digestMD5.authServer(this.lastServerResponse);
            if (!bl4) {
                return true;
            }
            n5 = n6 = this.simpleCommand(new byte[0]);
            return true;
        }
        catch (Exception exception) {
            if (this.debug) {
                this.out.println("DEBUG SMTP: DIGEST-MD5: " + (Object)((Object)exception));
            }
            if (n5 == 235) return true;
            this.closeConnection();
            return false;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void rcptTo() throws MessagingException {
        block50 : {
            var1_1 = new Vector();
            var2_2 = new Vector();
            var3_3 = new Vector();
            var4_4 /* !! */  = null;
            var5_5 = false;
            this.invalidAddr = null;
            this.validUnsentAddr = null;
            this.validSentAddr = null;
            var6_6 = this.message instanceof SMTPMessage;
            var7_7 = false;
            if (var6_6) {
                var7_7 = ((SMTPMessage)this.message).getSendPartial();
            }
            if (!var7_7) {
                var49_8 = this.session.getProperty("mail." + this.name + ".sendpartial");
                var7_7 = var49_8 != null && var49_8.equalsIgnoreCase("true") != false;
            }
            if (this.debug && var7_7) {
                this.out.println("DEBUG SMTP: sendPartial set");
            }
            var8_9 = this.supportsExtension("DSN");
            var9_10 = false;
            var10_11 = null;
            if (var8_9) {
                var48_12 = this.message instanceof SMTPMessage;
                var10_11 = null;
                if (var48_12) {
                    var10_11 = ((SMTPMessage)this.message).getDSNNotify();
                }
                if (var10_11 == null) {
                    var10_11 = this.session.getProperty("mail." + this.name + ".dsn.notify");
                }
                var9_10 = false;
                if (var10_11 != null) {
                    var9_10 = true;
                }
            }
            var11_13 = 0;
            do {
                block52 : {
                    block54 : {
                        block53 : {
                            block51 : {
                                if (var11_13 >= this.addresses.length) {
                                    if (var7_7 && var1_1.size() == 0) {
                                        var5_5 = true;
                                    }
                                    if (!var5_5) break;
                                    break block50;
                                }
                                var12_14 = (InternetAddress)this.addresses[var11_13];
                                var13_15 = "RCPT TO:" + this.normalizeAddress(var12_14.getAddress());
                                if (var9_10) {
                                    var13_15 = String.valueOf((Object)var13_15) + " NOTIFY=" + var10_11;
                                }
                                this.sendCommand(var13_15);
                                var14_16 = this.readServerResponse();
                                switch (var14_16) {
                                    default: {
                                        if (var14_16 < 400 || var14_16 > 499) break;
                                        var2_2.addElement((Object)var12_14);
                                        break block51;
                                    }
                                    case 250: 
                                    case 251: {
                                        var1_1.addElement((Object)var12_14);
                                        if (this.reportSuccess) {
                                            var19_19 = new SMTPAddressSucceededException(var12_14, var13_15, var14_16, this.lastServerResponse);
                                            if (var4_4 /* !! */  == null) {
                                                var4_4 /* !! */  = var19_19;
                                            } else {
                                                var4_4 /* !! */ .setNextException((Exception)var19_19);
                                            }
                                        }
                                        break block52;
                                    }
                                    case 501: 
                                    case 503: 
                                    case 550: 
                                    case 551: 
                                    case 553: {
                                        if (!var7_7) {
                                            var5_5 = true;
                                        }
                                        var3_3.addElement((Object)var12_14);
                                        var17_18 = new SMTPAddressFailedException(var12_14, var13_15, var14_16, this.lastServerResponse);
                                        if (var4_4 /* !! */  == null) {
                                            var4_4 /* !! */  = var17_18;
                                        } else {
                                            var4_4 /* !! */ .setNextException((Exception)var17_18);
                                        }
                                        break block52;
                                    }
                                    case 450: 
                                    case 451: 
                                    case 452: 
                                    case 552: {
                                        if (!var7_7) {
                                            var5_5 = true;
                                        }
                                        var2_2.addElement((Object)var12_14);
                                        var15_17 = new SMTPAddressFailedException(var12_14, var13_15, var14_16, this.lastServerResponse);
                                        if (var4_4 /* !! */  == null) {
                                            var4_4 /* !! */  = var15_17;
                                        } else {
                                            var4_4 /* !! */ .setNextException((Exception)var15_17);
                                        }
                                        break block52;
                                    }
                                }
                                if (var14_16 < 500 || var14_16 > 599) break block53;
                                var3_3.addElement((Object)var12_14);
                            }
                            if (!var7_7) {
                                var5_5 = true;
                            }
                            var23_20 = new SMTPAddressFailedException(var12_14, var13_15, var14_16, this.lastServerResponse);
                            if (var4_4 /* !! */  != null) break block54;
                            var4_4 /* !! */  = var23_20;
                            break block52;
                        }
                        if (this.debug) {
                            this.out.println("DEBUG SMTP: got response code " + var14_16 + ", with response: " + this.lastServerResponse);
                        }
                        var21_41 = this.lastServerResponse;
                        var22_42 = this.lastReturnCode;
                        if (this.serverSocket != null) {
                            this.issueCommand("RSET", 250);
                        }
                        this.lastServerResponse = var21_41;
                        this.lastReturnCode = var22_42;
                        throw new SMTPAddressFailedException(var12_14, var13_15, var14_16, var21_41);
                    }
                    var4_4 /* !! */ .setNextException((Exception)var23_20);
                }
                ++var11_13;
            } while (true);
            if (this.reportSuccess || var7_7 && (var3_3.size() > 0 || var2_2.size() > 0)) {
                this.sendPartiallyFailed = true;
                this.exception = var4_4 /* !! */ ;
                this.invalidAddr = new Address[var3_3.size()];
                var3_3.copyInto((Object[])this.invalidAddr);
                this.validUnsentAddr = new Address[var2_2.size()];
                var2_2.copyInto((Object[])this.validUnsentAddr);
                this.validSentAddr = new Address[var1_1.size()];
                var1_1.copyInto((Object[])this.validSentAddr);
            } else {
                this.validSentAddr = this.addresses;
            }
            ** GOTO lbl131
        }
        this.invalidAddr = new Address[var3_3.size()];
        var3_3.copyInto((Object[])this.invalidAddr);
        this.validUnsentAddr = new Address[var1_1.size() + var2_2.size()];
        var39_21 = 0;
        var40_22 = 0;
        do {
            if (var40_22 >= (var41_23 = var1_1.size())) break;
            var42_24 = this.validUnsentAddr;
            var43_25 = var39_21 + 1;
            var42_24[var39_21] = (Address)var1_1.elementAt(var40_22);
            ++var40_22;
            var39_21 = var43_25;
        } while (true);
        var44_26 = 0;
        do {
            block55 : {
                if (var44_26 < (var45_27 = var2_2.size())) break block55;
lbl131: // 3 sources:
                if (!this.debug) ** GOTO lbl168
                if (this.validSentAddr != null && this.validSentAddr.length > 0) {
                    break;
                }
                ** GOTO lbl146
            }
            var46_28 = this.validUnsentAddr;
            var47_29 = var39_21 + 1;
            var46_28[var39_21] = (Address)var2_2.elementAt(var44_26);
            ++var44_26;
            var39_21 = var47_29;
        } while (true);
        this.out.println("DEBUG SMTP: Verified Addresses");
        var37_30 = 0;
        do {
            block56 : {
                if (var37_30 < (var38_31 = this.validSentAddr.length)) break block56;
lbl146: // 2 sources:
                if (this.validUnsentAddr != null && this.validUnsentAddr.length > 0) {
                    break;
                }
                ** GOTO lbl157
            }
            this.out.println("DEBUG SMTP:   " + (Object)this.validSentAddr[var37_30]);
            ++var37_30;
        } while (true);
        this.out.println("DEBUG SMTP: Valid Unsent Addresses");
        var35_32 = 0;
        do {
            block57 : {
                if (var35_32 < (var36_33 = this.validUnsentAddr.length)) break block57;
lbl157: // 2 sources:
                if (this.invalidAddr != null && this.invalidAddr.length > 0) {
                    break;
                }
                ** GOTO lbl168
            }
            this.out.println("DEBUG SMTP:   " + (Object)this.validUnsentAddr[var35_32]);
            ++var35_32;
        } while (true);
        this.out.println("DEBUG SMTP: Invalid Addresses");
        var33_34 = 0;
        do {
            block58 : {
                if (var33_34 < (var34_35 = this.invalidAddr.length)) break block58;
lbl168: // 3 sources:
                if (var5_5 == false) return;
                if (this.debug) {
                    this.out.println("DEBUG SMTP: Sending failed because of invalid destination addresses");
                }
                this.notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, (Message)this.message);
                var25_36 = this.lastServerResponse;
                var26_37 = this.lastReturnCode;
                if (this.serverSocket != null) {
                    this.issueCommand("RSET", 250);
                }
            }
            this.out.println("DEBUG SMTP:   " + (Object)this.invalidAddr[var33_34]);
            ++var33_34;
        } while (true);
        catch (MessagingException var28_43) {
            this.close();
        }
lbl184: // 3 sources:
        do {
            var30_38 = this.validSentAddr;
            var31_39 = this.validUnsentAddr;
            var32_40 = this.invalidAddr;
            throw new SendFailedException("Invalid Addresses", (Exception)var4_4 /* !! */ , var30_38, var31_39, var32_40);
            break;
        } while (true);
        finally {
            this.lastServerResponse = var25_36;
            this.lastReturnCode = var26_37;
            ** continue;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected int readServerResponse() throws MessagingException {
        int n;
        String string2;
        block19 : {
            block18 : {
                if (!$assertionsDisabled && !Thread.holdsLock((Object)((Object)this))) {
                    throw new AssertionError();
                }
                StringBuffer stringBuffer = new StringBuffer(100);
                try {
                    String string3;
                    do {
                        if ((string3 = this.lineInputStream.readLine()) == null) {
                            String string4 = stringBuffer.toString();
                            if (string4.length() == 0) {
                                string4 = "[EOF]";
                            }
                            this.lastServerResponse = string4;
                            this.lastReturnCode = -1;
                            if (!this.debug) return -1;
                            this.out.println("DEBUG SMTP: EOF: " + string4);
                            return -1;
                        }
                        stringBuffer.append(string3);
                        stringBuffer.append("\n");
                    } while (this.isNotLastLine(string3));
                }
                catch (IOException iOException) {
                    if (this.debug) {
                        this.out.println("DEBUG SMTP: exception reading response: " + (Object)((Object)iOException));
                    }
                    this.lastServerResponse = "";
                    this.lastReturnCode = 0;
                    throw new MessagingException("Exception reading response", (Exception)((Object)iOException));
                }
                string2 = stringBuffer.toString();
                if (string2 != null && string2.length() >= 3) {
                    int n2;
                    n = n2 = Integer.parseInt((String)string2.substring(0, 3));
                }
                break block18;
                catch (NumberFormatException numberFormatException) {
                    block16 : {
                        try {
                            this.close();
                        }
                        catch (MessagingException messagingException) {
                            if (!this.debug) break block16;
                            messagingException.printStackTrace(this.out);
                        }
                    }
                    n = -1;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    block17 : {
                        try {
                            this.close();
                        }
                        catch (MessagingException messagingException) {
                            if (!this.debug) break block17;
                            messagingException.printStackTrace(this.out);
                        }
                    }
                    n = -1;
                }
                break block19;
            }
            n = -1;
        }
        if (n == -1 && this.debug) {
            this.out.println("DEBUG SMTP: bad server response: " + string2);
        }
        this.lastServerResponse = string2;
        this.lastReturnCode = n;
        return n;
    }

    protected void sendCommand(String string2) throws MessagingException {
        this.sendCommand(ASCIIUtility.getBytes(string2));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void sendMessage(Message message, Address[] arraddress) throws MessagingException, SendFailedException {
        block29 : {
            SMTPTransport sMTPTransport = this;
            // MONITORENTER : sMTPTransport
            this.checkConnected();
            if (!(message instanceof MimeMessage)) {
                if (!this.debug) throw new MessagingException("SMTP can only send RFC822 messages");
                this.out.println("DEBUG SMTP: Can only send RFC822 msgs");
                throw new MessagingException("SMTP can only send RFC822 messages");
            }
            int n = 0;
            do {
                if (n >= arraddress.length) {
                    boolean bl;
                    this.message = (MimeMessage)message;
                    this.addresses = arraddress;
                    this.validUnsentAddr = arraddress;
                    this.expandGroups();
                    boolean bl2 = message instanceof SMTPMessage;
                    boolean bl3 = false;
                    if (bl2) {
                        bl3 = ((SMTPMessage)message).getAllow8bitMIME();
                    }
                    if (!bl3) {
                        String string2 = this.session.getProperty("mail." + this.name + ".allow8bitmime");
                        bl3 = string2 != null && string2.equalsIgnoreCase("true");
                    }
                    if (this.debug) {
                        this.out.println("DEBUG SMTP: use8bit " + bl3);
                    }
                    if (bl3 && this.supportsExtension("8BITMIME") && (bl = this.convertTo8Bit((MimePart)this.message))) {
                        this.message.saveChanges();
                    }
                    break block29;
                }
                if (!(arraddress[n] instanceof InternetAddress)) {
                    throw new MessagingException((Object)arraddress[n] + " is not an InternetAddress");
                }
                ++n;
            } while (true);
            catch (MessagingException messagingException) {}
        }
        try {
            this.mailFrom();
            this.rcptTo();
            this.message.writeTo(this.data(), ignoreList);
            this.finishData();
            if (this.sendPartiallyFailed) {
                if (this.debug) {
                    this.out.println("DEBUG SMTP: Sending partially failed because of invalid destination addresses");
                }
                this.notifyTransportListeners(3, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, (Message)this.message);
                throw new SMTPSendFailedException(".", this.lastReturnCode, this.lastServerResponse, (Exception)((Object)this.exception), this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
            }
        }
        catch (MessagingException messagingException) {
            if (this.debug) {
                messagingException.printStackTrace(this.out);
            }
            this.notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, (Message)this.message);
            throw messagingException;
        }
        this.notifyTransportListeners(1, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, (Message)this.message);
        return;
        catch (IOException iOException) {
            block30 : {
                if (this.debug) {
                    iOException.printStackTrace(this.out);
                }
                try {
                    this.closeConnection();
                }
                catch (MessagingException messagingException) {}
                break block30;
                finally {
                    this.invalidAddr = null;
                    this.validUnsentAddr = null;
                    this.validSentAddr = null;
                    this.addresses = null;
                    this.message = null;
                    this.exception = null;
                    this.sendPartiallyFailed = false;
                }
            }
            this.notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, (Message)this.message);
            throw new MessagingException("IOException while sending message", (Exception)((Object)iOException));
        }
    }

    public void setLocalHost(String string2) {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.localHostName = string2;
            return;
        }
    }

    public void setReportSuccess(boolean bl) {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.reportSuccess = bl;
            return;
        }
    }

    public void setSASLRealm(String string2) {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.saslRealm = string2;
            return;
        }
    }

    public void setStartTLS(boolean bl) {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.useStartTLS = bl;
            return;
        }
    }

    public void setUseRset(boolean bl) {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.useRset = bl;
            return;
        }
    }

    public int simpleCommand(String string2) throws MessagingException {
        SMTPTransport sMTPTransport = this;
        synchronized (sMTPTransport) {
            this.sendCommand(string2);
            int n = this.readServerResponse();
            return n;
        }
    }

    protected int simpleCommand(byte[] arrby) throws MessagingException {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)((Object)this))) {
            throw new AssertionError();
        }
        this.sendCommand(arrby);
        return this.readServerResponse();
    }

    protected void startTLS() throws MessagingException {
        this.issueCommand("STARTTLS", 220);
        try {
            this.serverSocket = SocketFetcher.startTLS(this.serverSocket, this.session.getProperties(), "mail." + this.name);
            this.initStreams();
            return;
        }
        catch (IOException iOException) {
            this.closeConnection();
            throw new MessagingException("Could not convert socket to TLS", (Exception)((Object)iOException));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected boolean supportsAuthentication(String string2) {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)((Object)this))) {
            throw new AssertionError();
        }
        if (this.extMap == null) {
            return false;
        }
        String string3 = (String)this.extMap.get((Object)"AUTH");
        if (string3 == null) return false;
        StringTokenizer stringTokenizer = new StringTokenizer(string3);
        do {
            if (!stringTokenizer.hasMoreTokens()) return false;
        } while (!stringTokenizer.nextToken().equalsIgnoreCase(string2));
        return true;
    }

    public boolean supportsExtension(String string2) {
        return this.extMap != null && this.extMap.get((Object)string2.toUpperCase(Locale.ENGLISH)) != null;
    }
}

