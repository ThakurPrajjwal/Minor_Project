/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedOutputStream
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.net.Socket
 *  java.util.Properties
 *  java.util.Vector
 */
package com.sun.mail.iap;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.LiteralException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.iap.ResponseInputStream;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Vector;

public class Protocol {
    private static final byte[] CRLF = new byte[]{13, 10};
    private boolean connected;
    protected boolean debug;
    private volatile Vector handlers;
    protected String host;
    private volatile ResponseInputStream input;
    protected PrintStream out;
    private volatile DataOutputStream output;
    protected String prefix;
    protected Properties props;
    protected boolean quote;
    private Socket socket;
    private int tagCounter;
    private volatile long timestamp;
    private TraceInputStream traceInput;
    private TraceOutputStream traceOutput;

    public Protocol(InputStream inputStream, OutputStream outputStream, boolean bl) throws IOException {
        this.connected = false;
        this.tagCounter = 0;
        this.handlers = null;
        this.host = "localhost";
        this.debug = bl;
        this.quote = false;
        this.out = System.out;
        this.traceInput = new TraceInputStream(inputStream, (OutputStream)System.out);
        this.traceInput.setTrace(bl);
        this.traceInput.setQuote(this.quote);
        this.input = new ResponseInputStream((InputStream)this.traceInput);
        this.traceOutput = new TraceOutputStream(outputStream, (OutputStream)System.out);
        this.traceOutput.setTrace(bl);
        this.traceOutput.setQuote(this.quote);
        this.output = new DataOutputStream((OutputStream)new BufferedOutputStream((OutputStream)this.traceOutput));
        this.timestamp = System.currentTimeMillis();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Protocol(String string2, int n, boolean bl, PrintStream printStream, Properties properties, String string3, boolean bl2) throws IOException, ProtocolException {
        block4 : {
            boolean bl3 = true;
            this.connected = false;
            this.tagCounter = 0;
            this.handlers = null;
            try {
                this.host = string2;
                this.debug = bl;
                this.out = printStream;
                this.props = properties;
                this.prefix = string3;
                this.socket = SocketFetcher.getSocket(string2, n, properties, string3, bl2);
                String string4 = properties.getProperty("mail.debug.quote");
                if (string4 == null || !string4.equalsIgnoreCase("true")) {
                    bl3 = false;
                }
                this.quote = bl3;
                this.initStreams(printStream);
                this.processGreeting(this.readResponse());
                this.timestamp = System.currentTimeMillis();
                this.connected = true;
                if (this.connected) break block4;
            }
            catch (Throwable throwable) {
                if (!this.connected) {
                    this.disconnect();
                }
                throw throwable;
            }
            this.disconnect();
        }
    }

    private void initStreams(PrintStream printStream) throws IOException {
        this.traceInput = new TraceInputStream(this.socket.getInputStream(), (OutputStream)printStream);
        this.traceInput.setTrace(this.debug);
        this.traceInput.setQuote(this.quote);
        this.input = new ResponseInputStream((InputStream)this.traceInput);
        this.traceOutput = new TraceOutputStream(this.socket.getOutputStream(), (OutputStream)printStream);
        this.traceOutput.setTrace(this.debug);
        this.traceOutput.setQuote(this.quote);
        this.output = new DataOutputStream((OutputStream)new BufferedOutputStream((OutputStream)this.traceOutput));
    }

    public void addResponseHandler(ResponseHandler responseHandler) {
        Protocol protocol = this;
        synchronized (protocol) {
            if (this.handlers == null) {
                this.handlers = new Vector();
            }
            this.handlers.addElement((Object)responseHandler);
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
    public Response[] command(String string2, Argument argument) {
        String string3;
        Protocol protocol = this;
        // MONITORENTER : protocol
        Vector vector = new Vector();
        boolean bl = false;
        try {
            String string4;
            string3 = string4 = this.writeCommand(string2, argument);
        }
        catch (LiteralException literalException) {
            vector.addElement((Object)literalException.getResponse());
            bl = true;
            string3 = null;
        }
        catch (Exception exception) {
            vector.addElement((Object)Response.byeResponse(exception));
            bl = true;
            string3 = null;
        }
        do {
            Response response;
            if (bl) {
                Object[] arrobject = new Response[vector.size()];
                vector.copyInto(arrobject);
                this.timestamp = System.currentTimeMillis();
                // MONITOREXIT : protocol
                return arrobject;
            }
            try {
                Response response2;
                response = response2 = this.readResponse();
            }
            catch (IOException iOException) {
                Response response3;
                response = response3 = Response.byeResponse((Exception)((Object)iOException));
            }
            vector.addElement((Object)response);
            if (response.isBYE()) {
                bl = true;
            }
            if (!response.isTagged() || !response.getTag().equals((Object)string3)) continue;
            bl = true;
            continue;
            catch (ProtocolException protocolException) {
                continue;
            }
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void disconnect() {
        Protocol protocol = this;
        synchronized (protocol) {
            Socket socket = this.socket;
            if (socket != null) {
                try {
                    this.socket.close();
                }
                catch (IOException iOException) {}
                this.socket = null;
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.disconnect();
    }

    protected ResponseInputStream getInputStream() {
        return this.input;
    }

    protected OutputStream getOutputStream() {
        return this.output;
    }

    protected ByteArray getResponseBuffer() {
        return null;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleResult(Response response) throws ProtocolException {
        block6 : {
            block5 : {
                if (response.isOK()) break block5;
                if (response.isNO()) {
                    throw new CommandFailedException(response);
                }
                if (response.isBAD()) {
                    throw new BadCommandException(response);
                }
                if (response.isBYE()) break block6;
            }
            return;
        }
        this.disconnect();
        throw new ConnectionException(this, response);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void notifyResponseHandlers(Response[] arrresponse) {
        if (this.handlers != null) {
            for (int i = 0; i < arrresponse.length; ++i) {
                Response response = arrresponse[i];
                if (response == null) continue;
                int n = this.handlers.size();
                if (n == 0) break;
                Object[] arrobject = new Object[n];
                this.handlers.copyInto(arrobject);
                for (int j = 0; j < n; ++j) {
                    ((ResponseHandler)arrobject[j]).handleResponse(response);
                }
            }
        }
    }

    protected void processGreeting(Response response) throws ProtocolException {
        if (response.isBYE()) {
            throw new ConnectionException(this, response);
        }
    }

    public Response readResponse() throws IOException, ProtocolException {
        return new Response(this);
    }

    public void removeResponseHandler(ResponseHandler responseHandler) {
        Protocol protocol = this;
        synchronized (protocol) {
            if (this.handlers != null) {
                this.handlers.removeElement((Object)responseHandler);
            }
            return;
        }
    }

    public void simpleCommand(String string2, Argument argument) throws ProtocolException {
        Response[] arrresponse = this.command(string2, argument);
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[-1 + arrresponse.length]);
    }

    public void startTLS(String string2) throws IOException, ProtocolException {
        Protocol protocol = this;
        synchronized (protocol) {
            this.simpleCommand(string2, null);
            this.socket = SocketFetcher.startTLS(this.socket, this.props, this.prefix);
            this.initStreams(this.out);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected boolean supportsNonSyncLiterals() {
        Protocol protocol = this;
        // MONITORENTER : protocol
        // MONITOREXIT : protocol
        return false;
    }

    public String writeCommand(String string2, Argument argument) throws IOException, ProtocolException {
        StringBuilder stringBuilder = new StringBuilder("A");
        int n = this.tagCounter;
        this.tagCounter = n + 1;
        String string3 = stringBuilder.append(Integer.toString((int)n, (int)10)).toString();
        this.output.writeBytes(String.valueOf((Object)string3) + " " + string2);
        if (argument != null) {
            this.output.write(32);
            argument.write(this);
        }
        this.output.write(CRLF);
        this.output.flush();
        return string3;
    }
}

