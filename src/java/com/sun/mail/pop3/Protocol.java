/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedInputStream
 *  java.io.BufferedWriter
 *  java.io.DataInputStream
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.PrintStream
 *  java.io.PrintWriter
 *  java.io.UnsupportedEncodingException
 *  java.io.Writer
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.net.Socket
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.util.Properties
 *  java.util.StringTokenizer
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.Response;
import com.sun.mail.pop3.SharedByteArrayOutputStream;
import com.sun.mail.pop3.Status;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.SocketFetcher;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.StringTokenizer;

class Protocol {
    private static final String CRLF = "\r\n";
    private static final int POP3_PORT = 110;
    private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private String apopChallenge = null;
    private boolean debug = false;
    private DataInputStream input;
    private PrintStream out;
    private PrintWriter output;
    private Socket socket;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    Protocol(String var1_1, int var2_2, boolean var3_3, PrintStream var4_4, Properties var5_5, String var6_6, boolean var7_7) throws IOException {
        super();
        this.debug = var3_3;
        this.out = var4_4;
        var8_8 = var5_5.getProperty(String.valueOf((Object)var6_6) + ".apop.enable");
        var9_9 = var8_8 != null && var8_8.equalsIgnoreCase("true") != false;
        if (var2_2 == -1) {
            var2_2 = 110;
        }
        if (!var3_3) ** GOTO lbl13
        try {
            var4_4.println("DEBUG POP3: connecting to host \"" + var1_1 + "\", port " + var2_2 + ", isSSL " + var7_7);
lbl13: // 2 sources:
            this.socket = SocketFetcher.getSocket(var1_1, var2_2, var5_5, var6_6, var7_7);
            this.input = new DataInputStream((InputStream)new BufferedInputStream(this.socket.getInputStream()));
            this.output = new PrintWriter((Writer)new BufferedWriter((Writer)new OutputStreamWriter(this.socket.getOutputStream(), "iso-8859-1")));
            var12_10 = this.simpleCommand(null);
            ** if (var12_10.ok) goto lbl20
        }
        catch (IOException var10_11) {
            this.socket.close();
lbl24: // 1 sources:
            if (var9_9 == false) return;
            var13_12 = var12_10.data.indexOf(60);
            var14_13 = var12_10.data.indexOf(62, var13_12);
            if (var13_12 != -1 && var14_13 != -1) {
                this.apopChallenge = var12_10.data.substring(var13_12, var14_13 + 1);
            }
            if (var3_3 == false) return;
            var4_4.println("DEBUG POP3: APOP challenge: " + this.apopChallenge);
            return;
        }
        finally {
            throw new IOException("Connect failed");
        }
lbl-1000: // 1 sources:
        {
            this.socket.close();
        }
lbl20: // 1 sources:
        ** GOTO lbl24
        {
            finally {
                throw var10_11;
            }
        }
    }

    private String getDigest(String string2) {
        byte[] arrby;
        String string3 = String.valueOf((Object)this.apopChallenge) + string2;
        try {
            arrby = MessageDigest.getInstance((String)"MD5").digest(string3.getBytes("iso-8859-1"));
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return null;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
        return Protocol.toHex(arrby);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Response multilineCommand(String string2, int n) throws IOException {
        Response response = this.simpleCommand(string2);
        if (!response.ok) {
            return response;
        }
        SharedByteArrayOutputStream sharedByteArrayOutputStream = new SharedByteArrayOutputStream(n);
        int n2 = 10;
        do {
            int n3;
            block10 : {
                block9 : {
                    if ((n3 = this.input.read()) < 0) break block9;
                    if (n2 != 10 || n3 != 46) break block10;
                    if (this.debug) {
                        this.out.write(n3);
                    }
                    if ((n3 = this.input.read()) != 13) break block10;
                    if (this.debug) {
                        this.out.write(n3);
                    }
                    n3 = this.input.read();
                    if (this.debug) {
                        this.out.write(n3);
                    }
                }
                if (n3 >= 0) break;
                throw new EOFException("EOF on socket");
            }
            sharedByteArrayOutputStream.write(n3);
            if (this.debug) {
                this.out.write(n3);
            }
            n2 = n3;
        } while (true);
        response.bytes = sharedByteArrayOutputStream.toStream();
        return response;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Response simpleCommand(String string2) throws IOException {
        int n;
        String string3;
        if (this.socket == null) {
            throw new IOException("Folder is closed");
        }
        if (string2 != null) {
            if (this.debug) {
                this.out.println("C: " + string2);
            }
            String string4 = String.valueOf((Object)string2) + CRLF;
            this.output.print(string4);
            this.output.flush();
        }
        if ((string3 = this.input.readLine()) == null) {
            if (this.debug) {
                this.out.println("S: EOF");
            }
            throw new EOFException("EOF on socket");
        }
        if (this.debug) {
            this.out.println("S: " + string3);
        }
        Response response = new Response();
        if (string3.startsWith("+OK")) {
            response.ok = true;
        } else {
            if (!string3.startsWith("-ERR")) {
                throw new IOException("Unexpected response: " + string3);
            }
            response.ok = false;
        }
        if ((n = string3.indexOf(32)) >= 0) {
            response.data = string3.substring(n + 1);
        }
        return response;
    }

    private static String toHex(byte[] arrby) {
        char[] arrc = new char[2 * arrby.length];
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            int n3 = 255 & arrby[n];
            int n4 = n2 + 1;
            arrc[n2] = digits[n3 >> 4];
            n2 = n4 + 1;
            arrc[n4] = digits[n3 & 15];
            ++n;
        }
        return new String(arrc);
    }

    boolean dele(int n) throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            boolean bl = this.simpleCommand((String)new StringBuilder((String)"DELE ").append((int)n).toString()).ok;
            return bl;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.socket != null) {
            this.quit();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int list(int n) throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            Response response = this.simpleCommand("LIST " + n);
            int n2 = -1;
            if (!response.ok) return n2;
            String string2 = response.data;
            if (string2 == null) return n2;
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(response.data);
                stringTokenizer.nextToken();
                int n3 = Integer.parseInt((String)stringTokenizer.nextToken());
                return n3;
            }
            catch (Exception exception) {}
            return n2;
        }
    }

    InputStream list() throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            InputStream inputStream = this.multilineCommand((String)"LIST", (int)128).bytes;
            return inputStream;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String login(String string2, String string3) throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            Response response;
            String string4 = this.apopChallenge;
            String string5 = null;
            if (string4 != null) {
                string5 = this.getDigest(string3);
            }
            if (this.apopChallenge != null && string5 != null) {
                response = this.simpleCommand("APOP " + string2 + " " + string5);
            } else {
                Response response2 = this.simpleCommand("USER " + string2);
                if (!response2.ok) {
                    if (response2.data == null) return "USER command failed";
                    return response2.data;
                }
                response = this.simpleCommand("PASS " + string3);
            }
            if (response.ok) return null;
            if (response.data == null) return "login failed";
            return response.data;
        }
    }

    boolean noop() throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            boolean bl = this.simpleCommand((String)"NOOP").ok;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean quit() throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            boolean bl;
            try {
                bl = this.simpleCommand((String)"QUIT").ok;
            }
            catch (Throwable throwable) {
                try {
                    this.socket.close();
                    throw throwable;
                }
                finally {
                    this.socket = null;
                    this.input = null;
                    this.output = null;
                }
            }
            try {
                this.socket.close();
                return bl;
            }
            finally {
                this.socket = null;
                this.input = null;
                this.output = null;
            }
        }
    }

    InputStream retr(int n, int n2) throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            InputStream inputStream = this.multilineCommand((String)new StringBuilder((String)"RETR ").append((int)n).toString(), (int)n2).bytes;
            return inputStream;
        }
    }

    boolean rset() throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            boolean bl = this.simpleCommand((String)"RSET").ok;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Status stat() throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            Status status;
            String string2;
            Response response = this.simpleCommand("STAT");
            status = new Status();
            if (response.ok && (string2 = response.data) != null) {
                try {
                    StringTokenizer stringTokenizer = new StringTokenizer(response.data);
                    status.total = Integer.parseInt((String)stringTokenizer.nextToken());
                    status.size = Integer.parseInt((String)stringTokenizer.nextToken());
                }
                catch (Exception exception) {}
            }
            return status;
        }
    }

    InputStream top(int n, int n2) throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            InputStream inputStream = this.multilineCommand((String)new StringBuilder((String)"TOP ").append((int)n).append((String)" ").append((int)n2).toString(), (int)0).bytes;
            return inputStream;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    String uidl(int n) throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            Response response;
            String string2;
            block5 : {
                response = this.simpleCommand("UIDL " + n);
                boolean bl = response.ok;
                string2 = null;
                if (bl) break block5;
                return string2;
            }
            int n2 = response.data.indexOf(32);
            string2 = null;
            if (n2 <= 0) return string2;
            String string3 = response.data.substring(n2 + 1);
            return string3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean uidl(String[] arrstring) throws IOException {
        Protocol protocol = this;
        synchronized (protocol) {
            Response response = this.multilineCommand("UIDL", 15 * arrstring.length);
            boolean bl = response.ok;
            boolean bl2 = false;
            if (!bl) return bl2;
            LineInputStream lineInputStream = new LineInputStream(response.bytes);
            String string2;
            while ((string2 = lineInputStream.readLine()) != null) {
                int n;
                int n2 = string2.indexOf(32);
                if (n2 < 1 || n2 >= string2.length() || (n = Integer.parseInt((String)string2.substring(0, n2))) <= 0 || n > arrstring.length) continue;
                arrstring[n - 1] = string2.substring(n2 + 1);
            }
            return true;
        }
    }
}

