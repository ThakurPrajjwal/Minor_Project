/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.reflect.Constructor
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Enumeration
 *  java.util.HashMap
 *  java.util.Hashtable
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Properties
 *  java.util.Vector
 *  javax.mail.Flags
 *  javax.mail.Flags$Flag
 *  javax.mail.Quota
 *  javax.mail.Quota$Resource
 *  javax.mail.internet.MimeUtility
 *  javax.mail.search.SearchException
 *  javax.mail.search.SearchTerm
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.Literal;
import com.sun.mail.iap.LiteralException;
import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.ACL;
import com.sun.mail.imap.AppendUID;
import com.sun.mail.imap.Rights;
import com.sun.mail.imap.protocol.BASE64MailboxEncoder;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.INTERNALDATE;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.ListInfo;
import com.sun.mail.imap.protocol.MailboxInfo;
import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.Namespaces;
import com.sun.mail.imap.protocol.RFC822DATA;
import com.sun.mail.imap.protocol.SaslAuthenticator;
import com.sun.mail.imap.protocol.SearchSequence;
import com.sun.mail.imap.protocol.Status;
import com.sun.mail.imap.protocol.UID;
import com.sun.mail.imap.protocol.UIDSet;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.mail.Flags;
import javax.mail.Quota;
import javax.mail.internet.MimeUtility;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;

public class IMAPProtocol
extends Protocol {
    private static final byte[] CRLF = new byte[]{13, 10};
    private static final byte[] DONE = new byte[]{68, 79, 78, 69, 13, 10};
    private boolean authenticated;
    private List authmechs = null;
    private ByteArray ba;
    private Map capabilities = null;
    private boolean connected = false;
    private String idleTag;
    private String name;
    private boolean rev1 = false;
    private SaslAuthenticator saslAuthenticator;
    private String[] searchCharsets;

    public IMAPProtocol(String string2, String string3, int n, boolean bl, PrintStream printStream, Properties properties, boolean bl2) throws IOException, ProtocolException {
        super(string3, n, bl, printStream, properties, "mail." + string2, bl2);
        try {
            this.name = string2;
            if (this.capabilities == null) {
                this.capability();
            }
            if (this.hasCapability("IMAP4rev1")) {
                this.rev1 = true;
            }
            this.searchCharsets = new String[2];
            this.searchCharsets[0] = "UTF-8";
            this.searchCharsets[1] = MimeUtility.mimeCharset((String)MimeUtility.getDefaultJavaCharset());
            this.connected = true;
            return;
        }
        finally {
            if (!this.connected) {
                this.disconnect();
            }
        }
    }

    private void copy(String string2, String string3) throws ProtocolException {
        String string4 = BASE64MailboxEncoder.encode(string3);
        Argument argument = new Argument();
        argument.writeAtom(string2);
        argument.writeString(string4);
        this.simpleCommand("COPY", argument);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String createFlagList(Flags flags) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("(");
        Flags.Flag[] arrflag = flags.getSystemFlags();
        boolean bl = true;
        int n = 0;
        do {
            block16 : {
                String string2;
                block11 : {
                    Flags.Flag flag;
                    block15 : {
                        block14 : {
                            block13 : {
                                block12 : {
                                    block10 : {
                                        if (n >= arrflag.length) break;
                                        flag = arrflag[n];
                                        if (flag != Flags.Flag.ANSWERED) break block10;
                                        string2 = "\\Answered";
                                        break block11;
                                    }
                                    if (flag != Flags.Flag.DELETED) break block12;
                                    string2 = "\\Deleted";
                                    break block11;
                                }
                                if (flag != Flags.Flag.DRAFT) break block13;
                                string2 = "\\Draft";
                                break block11;
                            }
                            if (flag != Flags.Flag.FLAGGED) break block14;
                            string2 = "\\Flagged";
                            break block11;
                        }
                        if (flag != Flags.Flag.RECENT) break block15;
                        string2 = "\\Recent";
                        break block11;
                    }
                    if (flag != Flags.Flag.SEEN) break block16;
                    string2 = "\\Seen";
                }
                if (bl) {
                    bl = false;
                } else {
                    stringBuffer.append(' ');
                }
                stringBuffer.append(string2);
            }
            ++n;
        } while (true);
        String[] arrstring = flags.getUserFlags();
        int n2 = 0;
        do {
            if (n2 >= arrstring.length) {
                stringBuffer.append(")");
                return stringBuffer.toString();
            }
            if (bl) {
                bl = false;
            } else {
                stringBuffer.append(' ');
            }
            stringBuffer.append(arrstring[n2]);
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private ListInfo[] doList(String string2, String string3, String string4) throws ProtocolException {
        String string5 = BASE64MailboxEncoder.encode(string3);
        String string6 = BASE64MailboxEncoder.encode(string4);
        Argument argument = new Argument();
        argument.writeString(string5);
        argument.writeString(string6);
        Response[] arrresponse = this.command(string2, argument);
        Object[] arrobject = null;
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            Vector vector = new Vector(1);
            int n = 0;
            int n2 = arrresponse.length;
            do {
                IMAPResponse iMAPResponse;
                if (n >= n2) {
                    if (vector.size() <= 0) break;
                    arrobject = new ListInfo[vector.size()];
                    vector.copyInto(arrobject);
                    break;
                }
                if (arrresponse[n] instanceof IMAPResponse && (iMAPResponse = (IMAPResponse)arrresponse[n]).keyEquals(string2)) {
                    vector.addElement((Object)new ListInfo(iMAPResponse));
                    arrresponse[n] = null;
                }
                ++n;
            } while (true);
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        return arrobject;
    }

    private Response[] fetch(String string2, String string3, boolean bl) throws ProtocolException {
        if (bl) {
            return this.command("UID FETCH " + string2 + " (" + string3 + ")", null);
        }
        return this.command("FETCH " + string2 + " (" + string3 + ")", null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private AppendUID getAppendUID(Response response) {
        block4 : {
            block3 : {
                byte by;
                if (!response.isOK()) break block3;
                while ((by = response.readByte()) > 0 && by != 91) {
                }
                if (by != 0 && response.readAtom().equalsIgnoreCase("APPENDUID")) break block4;
            }
            return null;
        }
        return new AppendUID(response.readLong(), response.readLong());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int[] issueSearch(String var1_1, SearchTerm var2_2, String var3_3) throws ProtocolException, SearchException, IOException {
        var4_4 = var3_3 == null ? null : MimeUtility.javaCharset((String)var3_3);
        var5_5 = SearchSequence.generateSequence(var2_2, var4_4);
        var5_5.writeAtom(var1_1);
        var6_6 = var3_3 == null ? this.command("SEARCH", var5_5) : this.command("SEARCH CHARSET " + var3_3, var5_5);
        var7_7 = var6_6[-1 + var6_6.length];
        var8_8 = null;
        if (!var7_7.isOK()) ** GOTO lbl22
        var9_9 = new Vector();
        var10_10 = 0;
        var11_11 = var6_6.length;
        block0 : do {
            block11 : {
                block10 : {
                    if (var10_10 >= var11_11) break block10;
                    if (var6_6[var10_10] instanceof IMAPResponse && (var12_12 = (IMAPResponse)var6_6[var10_10]).keyEquals("SEARCH")) {
                        break;
                    }
                    break block11;
                }
                var14_14 = var9_9.size();
                var8_8 = new int[var14_14];
                var15_15 = 0;
                do {
                    block12 : {
                        if (var15_15 < var14_14) break block12;
lbl22: // 2 sources:
                        this.notifyResponseHandlers(var6_6);
                        this.handleResult(var7_7);
                        return var8_8;
                    }
                    var8_8[var15_15] = (Integer)var9_9.elementAt(var15_15);
                    ++var15_15;
                } while (true);
            }
lbl30: // 2 sources:
            do {
                ++var10_10;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        do {
            if ((var13_13 = var12_12.readNumber()) == -1) {
                var6_6[var10_10] = null;
                ** continue;
            }
            var9_9.addElement((Object)new Integer(var13_13));
        } while (true);
    }

    private Quota parseQuota(Response response) throws ParsingException {
        Quota quota = new Quota(response.readAtomString());
        response.skipSpaces();
        if (response.readByte() != 40) {
            throw new ParsingException("parse error in QUOTA");
        }
        Vector vector = new Vector();
        do {
            if (response.peekByte() == 41) {
                response.readByte();
                quota.resources = new Quota.Resource[vector.size()];
                vector.copyInto((Object[])quota.resources);
                return quota;
            }
            String string2 = response.readAtom();
            if (string2 == null) continue;
            vector.addElement((Object)new Quota.Resource(string2, response.readLong(), response.readLong()));
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int[] search(String string2, SearchTerm searchTerm) throws ProtocolException, SearchException {
        if (SearchSequence.isAscii(searchTerm)) {
            try {
                return this.issueSearch(string2, searchTerm, null);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        int n = 0;
        do {
            if (n >= this.searchCharsets.length) {
                throw new SearchException("Search failed");
            }
            if (this.searchCharsets[n] != null) {
                try {
                    return this.issueSearch(string2, searchTerm, this.searchCharsets[n]);
                }
                catch (CommandFailedException commandFailedException) {
                    this.searchCharsets[n] = null;
                }
                catch (IOException iOException) {}
            }
            ++n;
        } while (true);
        catch (ProtocolException protocolException) {
            throw protocolException;
        }
        catch (SearchException searchException) {
            throw searchException;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void storeFlags(String string2, Flags flags, boolean bl) throws ProtocolException {
        Response[] arrresponse = bl ? this.command("STORE " + string2 + " +FLAGS " + this.createFlagList(flags), null) : this.command("STORE " + string2 + " -FLAGS " + this.createFlagList(flags), null);
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[-1 + arrresponse.length]);
    }

    public void append(String string2, Flags flags, Date date, Literal literal) throws ProtocolException {
        this.appenduid(string2, flags, date, literal, false);
    }

    public AppendUID appenduid(String string2, Flags flags, Date date, Literal literal) throws ProtocolException {
        return this.appenduid(string2, flags, date, literal, true);
    }

    public AppendUID appenduid(String string2, Flags flags, Date date, Literal literal, boolean bl) throws ProtocolException {
        String string3 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string3);
        if (flags != null) {
            if (flags.contains(Flags.Flag.RECENT)) {
                Flags flags2 = new Flags(flags);
                flags2.remove(Flags.Flag.RECENT);
                flags = flags2;
            }
            argument.writeAtom(this.createFlagList(flags));
        }
        if (date != null) {
            argument.writeString(INTERNALDATE.format(date));
        }
        argument.writeBytes(literal);
        Response[] arrresponse = this.command("APPEND", argument);
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[-1 + arrresponse.length]);
        if (bl) {
            return this.getAppendUID(arrresponse[-1 + arrresponse.length]);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void authlogin(String string2, String string3) throws ProtocolException {
        IMAPProtocol iMAPProtocol = this;
        synchronized (iMAPProtocol) {
            String string4;
            Vector vector = new Vector();
            Response response = null;
            boolean bl = false;
            try {
                String string5;
                string4 = string5 = this.writeCommand("AUTHENTICATE LOGIN", null);
            }
            catch (Exception exception) {
                Response response2;
                response = response2 = Response.byeResponse(exception);
                bl = true;
                string4 = null;
            }
            OutputStream outputStream = this.getOutputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream((OutputStream)byteArrayOutputStream, Integer.MAX_VALUE);
            boolean bl2 = true;
            do {
                block14 : {
                    if (bl) {
                        Object[] arrobject = new Response[vector.size()];
                        vector.copyInto(arrobject);
                        this.notifyResponseHandlers((Response[])arrobject);
                        this.handleResult(response);
                        this.setCapabilities(response);
                        this.authenticated = true;
                        return;
                    }
                    try {
                        response = this.readResponse();
                        if (response.isContinuation()) {
                            String string6;
                            if (bl2) {
                                string6 = string2;
                                bl2 = false;
                            } else {
                                string6 = string3;
                            }
                            bASE64EncoderStream.write(ASCIIUtility.getBytes(string6));
                            bASE64EncoderStream.flush();
                            byteArrayOutputStream.write(CRLF);
                            outputStream.write(byteArrayOutputStream.toByteArray());
                            outputStream.flush();
                            byteArrayOutputStream.reset();
                        }
                        if (response.isTagged() && response.getTag().equals((Object)string4)) {
                            bl = true;
                            continue;
                        }
                        break block14;
                    }
                    catch (Exception exception) {
                        Response response3;
                        response = response3 = Response.byeResponse(exception);
                        bl = true;
                    }
                    continue;
                }
                if (response.isBYE()) {
                    bl = true;
                    continue;
                }
                vector.addElement((Object)response);
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void authplain(String string2, String string3, String string4) throws ProtocolException {
        IMAPProtocol iMAPProtocol = this;
        synchronized (iMAPProtocol) {
            String string5;
            Vector vector = new Vector();
            Response response = null;
            boolean bl = false;
            try {
                String string6;
                string5 = string6 = this.writeCommand("AUTHENTICATE PLAIN", null);
            }
            catch (Exception exception) {
                Response response2;
                response = response2 = Response.byeResponse(exception);
                bl = true;
                string5 = null;
            }
            OutputStream outputStream = this.getOutputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream((OutputStream)byteArrayOutputStream, Integer.MAX_VALUE);
            do {
                block12 : {
                    if (bl) {
                        Object[] arrobject = new Response[vector.size()];
                        vector.copyInto(arrobject);
                        this.notifyResponseHandlers((Response[])arrobject);
                        this.handleResult(response);
                        this.setCapabilities(response);
                        this.authenticated = true;
                        return;
                    }
                    try {
                        response = this.readResponse();
                        if (response.isContinuation()) {
                            bASE64EncoderStream.write(ASCIIUtility.getBytes(String.valueOf((Object)string2) + "\u0000" + string3 + "\u0000" + string4));
                            bASE64EncoderStream.flush();
                            byteArrayOutputStream.write(CRLF);
                            outputStream.write(byteArrayOutputStream.toByteArray());
                            outputStream.flush();
                            byteArrayOutputStream.reset();
                        }
                        if (response.isTagged() && response.getTag().equals((Object)string5)) {
                            bl = true;
                            continue;
                        }
                        break block12;
                    }
                    catch (Exception exception) {
                        Response response3;
                        response = response3 = Response.byeResponse(exception);
                        bl = true;
                    }
                    continue;
                }
                if (response.isBYE()) {
                    bl = true;
                    continue;
                }
                vector.addElement((Object)response);
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void capability() throws ProtocolException {
        Response[] arrresponse = this.command("CAPABILITY", null);
        if (!arrresponse[-1 + arrresponse.length].isOK()) {
            throw new ProtocolException(arrresponse[-1 + arrresponse.length].toString());
        }
        this.capabilities = new HashMap(10);
        this.authmechs = new ArrayList(5);
        int n = 0;
        int n2 = arrresponse.length;
        while (n < n2) {
            IMAPResponse iMAPResponse;
            if (arrresponse[n] instanceof IMAPResponse && (iMAPResponse = (IMAPResponse)arrresponse[n]).keyEquals("CAPABILITY")) {
                this.parseCapabilities(iMAPResponse);
            }
            ++n;
        }
        return;
    }

    public void check() throws ProtocolException {
        this.simpleCommand("CHECK", null);
    }

    public void close() throws ProtocolException {
        this.simpleCommand("CLOSE", null);
    }

    public void copy(int n, int n2, String string2) throws ProtocolException {
        this.copy(String.valueOf((Object)String.valueOf((int)n)) + ":" + String.valueOf((int)n2), string2);
    }

    public void copy(MessageSet[] arrmessageSet, String string2) throws ProtocolException {
        this.copy(MessageSet.toString(arrmessageSet), string2);
    }

    public void create(String string2) throws ProtocolException {
        String string3 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string3);
        this.simpleCommand("CREATE", argument);
    }

    public void delete(String string2) throws ProtocolException {
        String string3 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string3);
        this.simpleCommand("DELETE", argument);
    }

    public void deleteACL(String string2, String string3) throws ProtocolException {
        if (!this.hasCapability("ACL")) {
            throw new BadCommandException("ACL not supported");
        }
        String string4 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string4);
        argument.writeString(string3);
        Response[] arrresponse = this.command("DELETEACL", argument);
        Response response = arrresponse[-1 + arrresponse.length];
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
    }

    @Override
    public void disconnect() {
        super.disconnect();
        this.authenticated = false;
    }

    public MailboxInfo examine(String string2) throws ProtocolException {
        String string3 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string3);
        Response[] arrresponse = this.command("EXAMINE", argument);
        MailboxInfo mailboxInfo = new MailboxInfo(arrresponse);
        mailboxInfo.mode = 1;
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[-1 + arrresponse.length]);
        return mailboxInfo;
    }

    public void expunge() throws ProtocolException {
        this.simpleCommand("EXPUNGE", null);
    }

    public Response[] fetch(int n, int n2, String string2) throws ProtocolException {
        return this.fetch(String.valueOf((Object)String.valueOf((int)n)) + ":" + String.valueOf((int)n2), string2, false);
    }

    public Response[] fetch(int n, String string2) throws ProtocolException {
        return this.fetch(String.valueOf((int)n), string2, false);
    }

    public Response[] fetch(MessageSet[] arrmessageSet, String string2) throws ProtocolException {
        return this.fetch(MessageSet.toString(arrmessageSet), string2, false);
    }

    public BODY fetchBody(int n, String string2) throws ProtocolException {
        return this.fetchBody(n, string2, false);
    }

    public BODY fetchBody(int n, String string2, int n2, int n3) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, false, null);
    }

    public BODY fetchBody(int n, String string2, int n2, int n3, ByteArray byteArray) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, false, byteArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BODY fetchBody(int n, String string2, int n2, int n3, boolean bl, ByteArray byteArray) throws ProtocolException {
        this.ba = byteArray;
        String string3 = bl ? "BODY.PEEK[" : "BODY[";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf((Object)string3));
        String string4 = string2 == null ? "]<" : String.valueOf((Object)string2) + "]<";
        Response[] arrresponse = this.fetch(n, stringBuilder.append(string4).append(String.valueOf((int)n2)).append(".").append(String.valueOf((int)n3)).append(">").toString());
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            return (BODY)FetchResponse.getItem(arrresponse, n, BODY.class);
        }
        if (response.isNO()) {
            return null;
        }
        this.handleResult(response);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BODY fetchBody(int n, String string2, boolean bl) throws ProtocolException {
        Response[] arrresponse;
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder("BODY.PEEK[");
            String string3 = string2 == null ? "]" : String.valueOf((Object)string2) + "]";
            arrresponse = this.fetch(n, stringBuilder.append(string3).toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder("BODY[");
            String string4 = string2 == null ? "]" : String.valueOf((Object)string2) + "]";
            arrresponse = this.fetch(n, stringBuilder.append(string4).toString());
        }
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            return (BODY)FetchResponse.getItem(arrresponse, n, BODY.class);
        }
        if (response.isNO()) {
            return null;
        }
        this.handleResult(response);
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public BODYSTRUCTURE fetchBodyStructure(int n) throws ProtocolException {
        Response[] arrresponse = this.fetch(n, "BODYSTRUCTURE");
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            return (BODYSTRUCTURE)FetchResponse.getItem(arrresponse, n, BODYSTRUCTURE.class);
        }
        boolean bl = response.isNO();
        BODYSTRUCTURE bODYSTRUCTURE = null;
        if (bl) return bODYSTRUCTURE;
        this.handleResult(response);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Flags fetchFlags(int n) throws ProtocolException {
        Flags flags = null;
        Response[] arrresponse = this.fetch(n, "FLAGS");
        int n2 = arrresponse.length;
        for (int i = 0; i < n2; ++i) {
            if (arrresponse[i] == null || !(arrresponse[i] instanceof FetchResponse) || ((FetchResponse)arrresponse[i]).getNumber() != n || (flags = (Flags)((FetchResponse)arrresponse[i]).getItem(Flags.class)) == null) continue;
            arrresponse[i] = null;
            break;
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[-1 + arrresponse.length]);
        return flags;
    }

    /*
     * Enabled aggressive block sorting
     */
    public RFC822DATA fetchRFC822(int n, String string2) throws ProtocolException {
        String string3 = string2 == null ? "RFC822" : "RFC822." + string2;
        Response[] arrresponse = this.fetch(n, string3);
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            return (RFC822DATA)FetchResponse.getItem(arrresponse, n, RFC822DATA.class);
        }
        if (response.isNO()) {
            return null;
        }
        this.handleResult(response);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public UID fetchSequenceNumber(long l) throws ProtocolException {
        UID uID = null;
        Response[] arrresponse = this.fetch(String.valueOf((long)l), "UID", true);
        int n = 0;
        int n2 = arrresponse.length;
        do {
            block4 : {
                block5 : {
                    block3 : {
                        if (n >= n2) break block3;
                        if (arrresponse[n] == null || !(arrresponse[n] instanceof FetchResponse) || (uID = (UID)((FetchResponse)arrresponse[n]).getItem(UID.class)) == null) break block4;
                        if (uID.uid != l) break block5;
                    }
                    this.notifyResponseHandlers(arrresponse);
                    this.handleResult(arrresponse[-1 + arrresponse.length]);
                    return uID;
                }
                uID = null;
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public UID[] fetchSequenceNumbers(long l, long l2) throws ProtocolException {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf((Object)String.valueOf((long)l))).append(":");
        String string2 = l2 == -1L ? "*" : String.valueOf((long)l2);
        Response[] arrresponse = this.fetch(stringBuilder.append(string2).toString(), "UID", true);
        Vector vector = new Vector();
        int n = 0;
        int n2 = arrresponse.length;
        do {
            UID uID;
            if (n >= n2) {
                this.notifyResponseHandlers(arrresponse);
                this.handleResult(arrresponse[-1 + arrresponse.length]);
                Object[] arrobject = new UID[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            if (arrresponse[n] != null && arrresponse[n] instanceof FetchResponse && (uID = (UID)((FetchResponse)arrresponse[n]).getItem(UID.class)) != null) {
                vector.addElement((Object)uID);
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public UID[] fetchSequenceNumbers(long[] arrl) throws ProtocolException {
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        do {
            if (n >= arrl.length) break;
            if (n > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append(String.valueOf((long)arrl[n]));
            ++n;
        } while (true);
        Response[] arrresponse = this.fetch(stringBuffer.toString(), "UID", true);
        Vector vector = new Vector();
        int n2 = 0;
        int n3 = arrresponse.length;
        do {
            UID uID;
            if (n2 >= n3) {
                this.notifyResponseHandlers(arrresponse);
                this.handleResult(arrresponse[-1 + arrresponse.length]);
                Object[] arrobject = new UID[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            if (arrresponse[n2] != null && arrresponse[n2] instanceof FetchResponse && (uID = (UID)((FetchResponse)arrresponse[n2]).getItem(UID.class)) != null) {
                vector.addElement((Object)uID);
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public UID fetchUID(int n) throws ProtocolException {
        Response[] arrresponse = this.fetch(n, "UID");
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            return (UID)FetchResponse.getItem(arrresponse, n, UID.class);
        }
        boolean bl = response.isNO();
        UID uID = null;
        if (bl) return uID;
        this.handleResult(response);
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public ACL[] getACL(String var1_1) throws ProtocolException {
        if (!this.hasCapability("ACL")) {
            throw new BadCommandException("ACL not supported");
        }
        var2_2 = BASE64MailboxEncoder.encode(var1_1);
        var3_3 = new Argument();
        var3_3.writeString(var2_2);
        var4_4 = this.command("GETACL", var3_3);
        var5_5 = var4_4[-1 + var4_4.length];
        var6_6 = new Vector();
        if (!var5_5.isOK()) ** GOTO lbl14
        var8_7 = 0;
        var9_8 = var4_4.length;
        block0 : do {
            block8 : {
                if (var8_7 < var9_8) break block8;
lbl14: // 2 sources:
                this.notifyResponseHandlers(var4_4);
                this.handleResult(var5_5);
                var7_12 = new ACL[var6_6.size()];
                var6_6.copyInto(var7_12);
                return var7_12;
            }
            if (var4_4[var8_7] instanceof IMAPResponse && (var10_9 = (IMAPResponse)var4_4[var8_7]).keyEquals("ACL")) {
                var10_9.readAtomString();
                break;
            }
lbl23: // 3 sources:
            do {
                ++var8_7;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        while ((var12_10 = var10_9.readAtomString()) != null && (var13_11 = var10_9.readAtomString()) != null) {
            var6_6.addElement((Object)new ACL(var12_10, new Rights(var13_11)));
        }
        var4_4[var8_7] = null;
        ** while (true)
    }

    public Map getCapabilities() {
        return this.capabilities;
    }

    OutputStream getIMAPOutputStream() {
        return this.getOutputStream();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Quota[] getQuota(String string2) throws ProtocolException {
        if (!this.hasCapability("QUOTA")) {
            throw new BadCommandException("QUOTA not supported");
        }
        Argument argument = new Argument();
        argument.writeString(string2);
        Response[] arrresponse = this.command("GETQUOTA", argument);
        Vector vector = new Vector();
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            int n = arrresponse.length;
            for (int i = 0; i < n; ++i) {
                IMAPResponse iMAPResponse;
                if (!(arrresponse[i] instanceof IMAPResponse) || !(iMAPResponse = (IMAPResponse)arrresponse[i]).keyEquals("QUOTA")) continue;
                vector.addElement((Object)this.parseQuota(iMAPResponse));
                arrresponse[i] = null;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        Object[] arrobject = new Quota[vector.size()];
        vector.copyInto(arrobject);
        return arrobject;
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Quota[] getQuotaRoot(String var1_1) throws ProtocolException {
        block8 : {
            block10 : {
                if (!this.hasCapability("QUOTA")) {
                    throw new BadCommandException("GETQUOTAROOT not supported");
                }
                var2_2 = BASE64MailboxEncoder.encode(var1_1);
                var3_3 = new Argument();
                var3_3.writeString(var2_2);
                var4_4 = this.command("GETQUOTAROOT", var3_3);
                var5_5 = var4_4[-1 + var4_4.length];
                var6_6 = new Hashtable();
                if (!var5_5.isOK()) ** GOTO lbl14
                var10_7 = 0;
                var11_8 = var4_4.length;
                block0 : do {
                    block9 : {
                        if (var10_7 < var11_8) break block9;
lbl14: // 2 sources:
                        this.notifyResponseHandlers(var4_4);
                        this.handleResult(var5_5);
                        var7_13 = new Quota[var6_6.size()];
                        var8_14 = var6_6.elements();
                        var9_15 = 0;
lbl19: // 2 sources:
                        do {
                            if (!var8_14.hasMoreElements()) {
                                return var7_13;
                            }
                            break block8;
                            break;
                        } while (true);
                    }
                    if (var4_4[var10_7] instanceof IMAPResponse) break;
lbl25: // 4 sources:
                    do {
                        ++var10_7;
                        continue block0;
                        break;
                    } while (true);
                    break;
                } while (true);
                var12_9 = (IMAPResponse)var4_4[var10_7];
                if (!var12_9.keyEquals("QUOTAROOT")) break block10;
                var12_9.readAtomString();
                do {
                    block11 : {
                        if ((var18_12 = var12_9.readAtomString()) != null) break block11;
                        var4_4[var10_7] = null;
                        ** GOTO lbl25
                    }
                    var6_6.put((Object)var18_12, (Object)new Quota(var18_12));
                } while (true);
            }
            if (!var12_9.keyEquals("QUOTA")) ** GOTO lbl25
            var13_10 = this.parseQuota(var12_9);
            var14_11 = (Quota)var6_6.get((Object)var13_10.quotaRoot);
            if (var14_11 != null) {
                // empty if block
            }
            var6_6.put((Object)var13_10.quotaRoot, (Object)var13_10);
            var4_4[var10_7] = null;
            ** while (true)
        }
        var7_13[var9_15] = (Quota)var8_14.nextElement();
        ++var9_15;
        ** while (true)
    }

    @Override
    protected ByteArray getResponseBuffer() {
        ByteArray byteArray = this.ba;
        this.ba = null;
        return byteArray;
    }

    public boolean hasCapability(String string2) {
        return this.capabilities.containsKey((Object)string2.toUpperCase(Locale.ENGLISH));
    }

    public void idleAbort() throws ProtocolException {
        OutputStream outputStream = this.getOutputStream();
        try {
            outputStream.write(DONE);
            outputStream.flush();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void idleStart() throws ProtocolException {
        IMAPProtocol iMAPProtocol = this;
        synchronized (iMAPProtocol) {
            Response response;
            if (!this.hasCapability("IDLE")) {
                throw new BadCommandException("IDLE not supported");
            }
            try {
                Response response2;
                this.idleTag = this.writeCommand("IDLE", null);
                response = response2 = this.readResponse();
            }
            catch (LiteralException literalException) {
                response = literalException.getResponse();
            }
            catch (Exception exception) {
                Response response3;
                response = response3 = Response.byeResponse(exception);
            }
            if (!response.isContinuation()) {
                this.handleResult(response);
            }
            return;
        }
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public boolean isREV1() {
        return this.rev1;
    }

    public ListInfo[] list(String string2, String string3) throws ProtocolException {
        return this.doList("LIST", string2, string3);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Rights[] listRights(String var1_1, String var2_2) throws ProtocolException {
        if (!this.hasCapability("ACL")) {
            throw new BadCommandException("ACL not supported");
        }
        var3_3 = BASE64MailboxEncoder.encode(var1_1);
        var4_4 = new Argument();
        var4_4.writeString(var3_3);
        var4_4.writeString(var2_2);
        var5_5 = this.command("LISTRIGHTS", var4_4);
        var6_6 = var5_5[-1 + var5_5.length];
        var7_7 = new Vector();
        if (!var6_6.isOK()) ** GOTO lbl15
        var9_8 = 0;
        var10_9 = var5_5.length;
        block0 : do {
            block8 : {
                if (var9_8 < var10_9) break block8;
lbl15: // 2 sources:
                this.notifyResponseHandlers(var5_5);
                this.handleResult(var6_6);
                var8_12 = new Rights[var7_7.size()];
                var7_7.copyInto(var8_12);
                return var8_12;
            }
            if (var5_5[var9_8] instanceof IMAPResponse && (var11_10 = (IMAPResponse)var5_5[var9_8]).keyEquals("LISTRIGHTS")) break;
lbl22: // 2 sources:
            do {
                ++var9_8;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        var11_10.readAtomString();
        var11_10.readAtomString();
        do {
            if ((var14_11 = var11_10.readAtomString()) == null) {
                var5_5[var9_8] = null;
                ** continue;
            }
            var7_7.addElement((Object)new Rights(var14_11));
        } while (true);
    }

    public void login(String string2, String string3) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString(string2);
        argument.writeString(string3);
        Response[] arrresponse = this.command("LOGIN", argument);
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[-1 + arrresponse.length]);
        this.setCapabilities(arrresponse[-1 + arrresponse.length]);
        this.authenticated = true;
    }

    public void logout() throws ProtocolException {
        Response[] arrresponse = this.command("LOGOUT", null);
        this.authenticated = false;
        this.notifyResponseHandlers(arrresponse);
        this.disconnect();
    }

    public ListInfo[] lsub(String string2, String string3) throws ProtocolException {
        return this.doList("LSUB", string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Rights myRights(String string2) throws ProtocolException {
        if (!this.hasCapability("ACL")) {
            throw new BadCommandException("ACL not supported");
        }
        String string3 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string3);
        Response[] arrresponse = this.command("MYRIGHTS", argument);
        Response response = arrresponse[-1 + arrresponse.length];
        boolean bl = response.isOK();
        Rights rights = null;
        if (bl) {
            int n = arrresponse.length;
            for (int i = 0; i < n; ++i) {
                IMAPResponse iMAPResponse;
                if (!(arrresponse[i] instanceof IMAPResponse) || !(iMAPResponse = (IMAPResponse)arrresponse[i]).keyEquals("MYRIGHTS")) continue;
                iMAPResponse.readAtomString();
                String string4 = iMAPResponse.readAtomString();
                if (rights == null) {
                    rights = new Rights(string4);
                }
                arrresponse[i] = null;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        return rights;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Namespaces namespace() throws ProtocolException {
        if (!this.hasCapability("NAMESPACE")) {
            throw new BadCommandException("NAMESPACE not supported");
        }
        Response[] arrresponse = this.command("NAMESPACE", null);
        Response response = arrresponse[-1 + arrresponse.length];
        boolean bl = response.isOK();
        Namespaces namespaces = null;
        if (bl) {
            int n = arrresponse.length;
            for (int i = 0; i < n; ++i) {
                IMAPResponse iMAPResponse;
                if (!(arrresponse[i] instanceof IMAPResponse) || !(iMAPResponse = (IMAPResponse)arrresponse[i]).keyEquals("NAMESPACE")) continue;
                if (namespaces == null) {
                    namespaces = new Namespaces(iMAPResponse);
                }
                arrresponse[i] = null;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        return namespaces;
    }

    public void noop() throws ProtocolException {
        if (this.debug) {
            this.out.println("IMAP DEBUG: IMAPProtocol noop");
        }
        this.simpleCommand("NOOP", null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void parseCapabilities(Response response) {
        do {
            String string2;
            block4 : {
                block5 : {
                    block3 : {
                        if ((string2 = response.readAtom(']')) == null) break block3;
                        if (string2.length() != 0) break block4;
                        if (response.peekByte() != 93) break block5;
                    }
                    return;
                }
                response.skipToken();
                continue;
            }
            this.capabilities.put((Object)string2.toUpperCase(Locale.ENGLISH), (Object)string2);
            if (!string2.regionMatches(true, 0, "AUTH=", 0, 5)) continue;
            this.authmechs.add((Object)string2.substring(5));
            if (!this.debug) continue;
            this.out.println("IMAP DEBUG: AUTH: " + string2.substring(5));
        } while (true);
    }

    public BODY peekBody(int n, String string2) throws ProtocolException {
        return this.fetchBody(n, string2, true);
    }

    public BODY peekBody(int n, String string2, int n2, int n3) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, true, null);
    }

    public BODY peekBody(int n, String string2, int n2, int n3, ByteArray byteArray) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, true, byteArray);
    }

    @Override
    protected void processGreeting(Response response) throws ProtocolException {
        super.processGreeting(response);
        if (response.isOK()) {
            this.setCapabilities(response);
            return;
        }
        if (((IMAPResponse)response).keyEquals("PREAUTH")) {
            this.authenticated = true;
            this.setCapabilities(response);
            return;
        }
        throw new ConnectionException(this, response);
    }

    public boolean processIdleResponse(Response response) throws ProtocolException {
        this.notifyResponseHandlers(new Response[]{response});
        boolean bl = response.isBYE();
        boolean bl2 = false;
        if (bl) {
            bl2 = true;
        }
        if (response.isTagged() && response.getTag().equals((Object)this.idleTag)) {
            bl2 = true;
        }
        if (bl2) {
            this.idleTag = null;
        }
        this.handleResult(response);
        return !bl2;
    }

    public void proxyauth(String string2) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString(string2);
        this.simpleCommand("PROXYAUTH", argument);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Response readIdleResponse() {
        IMAPProtocol iMAPProtocol = this;
        synchronized (iMAPProtocol) {
            String string2 = this.idleTag;
            if (string2 == null) {
                return null;
            }
            try {
                Response response = this.readResponse();
                return response;
            }
            catch (IOException iOException) {
                return Response.byeResponse((Exception)((Object)iOException));
            }
            catch (ProtocolException protocolException) {
                Response response = Response.byeResponse(protocolException);
                return response;
            }
        }
    }

    @Override
    public Response readResponse() throws IOException, ProtocolException {
        return IMAPResponse.readResponse(this);
    }

    public void rename(String string2, String string3) throws ProtocolException {
        String string4 = BASE64MailboxEncoder.encode(string2);
        String string5 = BASE64MailboxEncoder.encode(string3);
        Argument argument = new Argument();
        argument.writeString(string4);
        argument.writeString(string5);
        this.simpleCommand("RENAME", argument);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void sasllogin(String[] arrstring, String string2, String string3, String string4, String string5) throws ProtocolException {
        String[] arrstring2;
        List list;
        block7 : {
            block6 : {
                block5 : {
                    if (this.saslAuthenticator == null) {
                        Class class_ = Class.forName((String)"com.sun.mail.imap.protocol.IMAPSaslAuthenticator");
                        Class[] arrclass = new Class[]{IMAPProtocol.class, String.class, Properties.class, Boolean.TYPE, PrintStream.class, String.class};
                        Constructor constructor = class_.getConstructor(arrclass);
                        Object[] arrobject = new Object[6];
                        arrobject[0] = this;
                        arrobject[1] = this.name;
                        arrobject[2] = this.props;
                        Boolean bl = this.debug ? Boolean.TRUE : Boolean.FALSE;
                        arrobject[3] = bl;
                        arrobject[4] = this.out;
                        arrobject[5] = this.host;
                        this.saslAuthenticator = (SaslAuthenticator)constructor.newInstance(arrobject);
                    }
                    if (arrstring == null || arrstring.length <= 0) break block5;
                    list = new ArrayList(arrstring.length);
                    break block6;
                    catch (Exception exception) {
                        if (!this.debug) return;
                        this.out.println("IMAP DEBUG: Can't load SASL authenticator: " + (Object)((Object)exception));
                        return;
                    }
                }
                list = this.authmechs;
                break block7;
            }
            for (int i = 0; i < arrstring.length; ++i) {
                if (!this.authmechs.contains((Object)arrstring[i])) continue;
                list.add((Object)arrstring[i]);
            }
        }
        if (!this.saslAuthenticator.authenticate(arrstring2 = (String[])list.toArray((Object[])new String[list.size()]), string2, string3, string4, string5)) return;
        this.authenticated = true;
    }

    public int[] search(SearchTerm searchTerm) throws ProtocolException, SearchException {
        return this.search("ALL", searchTerm);
    }

    public int[] search(MessageSet[] arrmessageSet, SearchTerm searchTerm) throws ProtocolException, SearchException {
        return this.search(MessageSet.toString(arrmessageSet), searchTerm);
    }

    /*
     * Enabled aggressive block sorting
     */
    public MailboxInfo select(String string2) throws ProtocolException {
        String string3 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string3);
        Response[] arrresponse = this.command("SELECT", argument);
        MailboxInfo mailboxInfo = new MailboxInfo(arrresponse);
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[-1 + arrresponse.length];
        if (response.isOK()) {
            mailboxInfo.mode = response.toString().indexOf("READ-ONLY") != -1 ? 1 : 2;
        }
        this.handleResult(response);
        return mailboxInfo;
    }

    public void setACL(String string2, char c, ACL aCL) throws ProtocolException {
        if (!this.hasCapability("ACL")) {
            throw new BadCommandException("ACL not supported");
        }
        String string3 = BASE64MailboxEncoder.encode(string2);
        Argument argument = new Argument();
        argument.writeString(string3);
        argument.writeString(aCL.getName());
        String string4 = aCL.getRights().toString();
        if (c == '+' || c == '-') {
            string4 = String.valueOf((char)c) + string4;
        }
        argument.writeString(string4);
        Response[] arrresponse = this.command("SETACL", argument);
        Response response = arrresponse[-1 + arrresponse.length];
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setCapabilities(Response response) {
        byte by;
        while ((by = response.readByte()) > 0 && by != 91) {
        }
        if (by == 0 || !response.readAtom().equalsIgnoreCase("CAPABILITY")) {
            return;
        }
        this.capabilities = new HashMap(10);
        this.authmechs = new ArrayList(5);
        this.parseCapabilities(response);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setQuota(Quota quota) throws ProtocolException {
        if (!this.hasCapability("QUOTA")) {
            throw new BadCommandException("QUOTA not supported");
        }
        Argument argument = new Argument();
        argument.writeString(quota.quotaRoot);
        Argument argument2 = new Argument();
        if (quota.resources != null) {
            for (int i = 0; i < quota.resources.length; ++i) {
                argument2.writeAtom(quota.resources[i].name);
                argument2.writeNumber(quota.resources[i].limit);
            }
        }
        argument.writeArgument(argument2);
        Response[] arrresponse = this.command("SETQUOTA", argument);
        Response response = arrresponse[-1 + arrresponse.length];
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
    }

    public void startTLS() throws ProtocolException {
        try {
            super.startTLS("STARTTLS");
            return;
        }
        catch (ProtocolException protocolException) {
            throw protocolException;
        }
        catch (Exception exception) {
            Response[] arrresponse = new Response[]{Response.byeResponse(exception)};
            this.notifyResponseHandlers(arrresponse);
            this.disconnect();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public Status status(String string2, String[] arrstring) throws ProtocolException {
        Response response;
        Status status;
        Response[] arrresponse;
        block8 : {
            int n;
            if (!this.isREV1() && !this.hasCapability("IMAP4SUNVERSION")) {
                throw new BadCommandException("STATUS not supported");
            }
            String string3 = BASE64MailboxEncoder.encode(string2);
            Argument argument = new Argument();
            argument.writeString(string3);
            Argument argument2 = new Argument();
            if (arrstring == null) {
                arrstring = Status.standardItems;
            }
            int n2 = 0;
            int n3 = arrstring.length;
            do {
                if (n2 >= n3) {
                    argument.writeArgument(argument2);
                    arrresponse = this.command("STATUS", argument);
                    response = arrresponse[-1 + arrresponse.length];
                    boolean bl = response.isOK();
                    status = null;
                    if (bl) {
                        n = arrresponse.length;
                        break;
                    }
                    break block8;
                }
                argument2.writeAtom(arrstring[n2]);
                ++n2;
            } while (true);
            for (int i = 0; i < n; ++i) {
                IMAPResponse iMAPResponse;
                if (!(arrresponse[i] instanceof IMAPResponse) || !(iMAPResponse = (IMAPResponse)arrresponse[i]).keyEquals("STATUS")) continue;
                if (status == null) {
                    status = new Status(iMAPResponse);
                } else {
                    Status.add(status, new Status(iMAPResponse));
                }
                arrresponse[i] = null;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        return status;
    }

    public void storeFlags(int n, int n2, Flags flags, boolean bl) throws ProtocolException {
        this.storeFlags(String.valueOf((Object)String.valueOf((int)n)) + ":" + String.valueOf((int)n2), flags, bl);
    }

    public void storeFlags(int n, Flags flags, boolean bl) throws ProtocolException {
        this.storeFlags(String.valueOf((int)n), flags, bl);
    }

    public void storeFlags(MessageSet[] arrmessageSet, Flags flags, boolean bl) throws ProtocolException {
        this.storeFlags(MessageSet.toString(arrmessageSet), flags, bl);
    }

    public void subscribe(String string2) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString(BASE64MailboxEncoder.encode(string2));
        this.simpleCommand("SUBSCRIBE", argument);
    }

    @Override
    protected boolean supportsNonSyncLiterals() {
        return this.hasCapability("LITERAL+");
    }

    public void uidexpunge(UIDSet[] arruIDSet) throws ProtocolException {
        if (!this.hasCapability("UIDPLUS")) {
            throw new BadCommandException("UID EXPUNGE not supported");
        }
        this.simpleCommand("UID EXPUNGE " + UIDSet.toString(arruIDSet), null);
    }

    public void unsubscribe(String string2) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString(BASE64MailboxEncoder.encode(string2));
        this.simpleCommand("UNSUBSCRIBE", argument);
    }
}

