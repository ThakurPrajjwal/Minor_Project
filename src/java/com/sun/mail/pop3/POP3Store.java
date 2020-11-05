/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.ClassNotFoundException
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Constructor
 *  java.util.Properties
 *  javax.mail.AuthenticationFailedException
 *  javax.mail.Folder
 *  javax.mail.MessagingException
 *  javax.mail.Session
 *  javax.mail.Store
 *  javax.mail.URLName
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.DefaultFolder;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.Protocol;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class POP3Store
extends Store {
    private int defaultPort;
    boolean disableTop;
    boolean forgetTopHeaders;
    private String host;
    private boolean isSSL;
    Constructor messageConstructor;
    private String name;
    private String passwd;
    private Protocol port;
    private int portNum;
    private POP3Folder portOwner;
    boolean rsetBeforeQuit;
    private String user;

    public POP3Store(Session session, URLName uRLName) {
        this(session, uRLName, "pop3", 110, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public POP3Store(Session session, URLName uRLName, String string2, int n, boolean bl) {
        block10 : {
            String string3;
            String string4;
            String string5;
            super(session, uRLName);
            this.name = "pop3";
            this.defaultPort = 110;
            this.isSSL = false;
            this.port = null;
            this.portOwner = null;
            this.host = null;
            this.portNum = -1;
            this.user = null;
            this.passwd = null;
            this.rsetBeforeQuit = false;
            this.disableTop = false;
            this.forgetTopHeaders = false;
            this.messageConstructor = null;
            if (uRLName != null) {
                string2 = uRLName.getProtocol();
            }
            this.name = string2;
            this.defaultPort = n;
            this.isSSL = bl;
            String string6 = session.getProperty("mail." + string2 + ".rsetbeforequit");
            if (string6 != null && string6.equalsIgnoreCase("true")) {
                this.rsetBeforeQuit = true;
            }
            if ((string5 = session.getProperty("mail." + string2 + ".disabletop")) != null && string5.equalsIgnoreCase("true")) {
                this.disableTop = true;
            }
            if ((string4 = session.getProperty("mail." + string2 + ".forgettopheaders")) != null && string4.equalsIgnoreCase("true")) {
                this.forgetTopHeaders = true;
            }
            if ((string3 = session.getProperty("mail." + string2 + ".message.class")) != null) {
                if (session.getDebug()) {
                    session.getDebugOut().println("DEBUG: POP3 message class: " + string3);
                }
                try {
                    Class class_;
                    ClassLoader classLoader = this.getClass().getClassLoader();
                    try {
                        Class class_2;
                        class_ = class_2 = classLoader.loadClass(string3);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        Class class_3;
                        class_ = class_3 = Class.forName((String)string3);
                    }
                    Class[] arrclass = new Class[]{Folder.class, Integer.TYPE};
                    this.messageConstructor = class_.getConstructor(arrclass);
                    return;
                }
                catch (Exception exception) {
                    if (!session.getDebug()) break block10;
                    session.getDebugOut().println("DEBUG: failed to load POP3 message class: " + (Object)((Object)exception));
                }
            }
        }
    }

    private void checkConnected() throws MessagingException {
        if (!super.isConnected()) {
            throw new MessagingException("Not connected");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() throws MessagingException {
        POP3Store pOP3Store = this;
        synchronized (pOP3Store) {
            block8 : {
                try {
                    if (this.port != null) {
                        this.port.quit();
                    }
                    break block8;
                }
                catch (IOException iOException) {}
                break block8;
                finally {
                    this.port = null;
                    super.close();
                }
            }
            return;
        }
    }

    void closePort(POP3Folder pOP3Folder) {
        POP3Store pOP3Store = this;
        synchronized (pOP3Store) {
            if (this.portOwner == pOP3Folder) {
                this.port = null;
                this.portOwner = null;
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.port != null) {
            this.close();
        }
    }

    public Folder getDefaultFolder() throws MessagingException {
        this.checkConnected();
        return new DefaultFolder(this);
    }

    public Folder getFolder(String string2) throws MessagingException {
        this.checkConnected();
        return new POP3Folder(this, string2);
    }

    public Folder getFolder(URLName uRLName) throws MessagingException {
        this.checkConnected();
        return new POP3Folder(this, uRLName.getFile());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    Protocol getPort(POP3Folder pOP3Folder) throws IOException {
        String string2;
        Protocol protocol;
        POP3Store pOP3Store = this;
        // MONITORENTER : pOP3Store
        if (this.port != null && this.portOwner == null) {
            this.portOwner = pOP3Folder;
            protocol = this.port;
            return protocol;
        } else {
            protocol = new Protocol(this.host, this.portNum, this.session.getDebug(), this.session.getDebugOut(), this.session.getProperties(), "mail." + this.name, this.isSSL);
            string2 = protocol.login(this.user, this.passwd);
            if (string2 != null) {
                protocol.quit();
            }
            if (this.port == null && pOP3Folder != null) {
                this.port = protocol;
                this.portOwner = pOP3Folder;
            }
            if (this.portOwner == null) {
                this.portOwner = pOP3Folder;
                return protocol;
            }
        }
        // MONITOREXIT : pOP3Store
        return protocol;
        catch (IOException iOException) {}
        finally {
            throw new EOFException(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnected() {
        POP3Store pOP3Store = this;
        synchronized (pOP3Store) {
            boolean bl = super.isConnected();
            boolean bl2 = false;
            if (!bl) return bl2;
            pOP3Store = this;
            synchronized (pOP3Store) {
                try {
                    if (this.port == null) {
                        this.port = this.getPort(null);
                    } else {
                        this.port.noop();
                    }
                    return true;
                }
                catch (IOException iOException) {
                    try {
                        super.close();
                    }
                    catch (MessagingException messagingException) {
                    }
                    catch (Throwable throwable) {}
                    return false;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean protocolConnect(String string2, int n, String string3, String string4) throws MessagingException {
        POP3Store pOP3Store = this;
        synchronized (pOP3Store) {
            String string5;
            if (string2 == null) return false;
            if (string4 == null) return false;
            if (string3 == null) {
                return false;
            }
            if (n == -1 && (string5 = this.session.getProperty("mail." + this.name + ".port")) != null) {
                n = Integer.parseInt((String)string5);
            }
            if (n == -1) {
                n = this.defaultPort;
            }
            this.host = string2;
            this.portNum = n;
            this.user = string3;
            this.passwd = string4;
            try {
                this.port = this.getPort(null);
                return true;
            }
            catch (EOFException eOFException) {
                throw new AuthenticationFailedException(eOFException.getMessage());
            }
            catch (IOException iOException) {
                throw new MessagingException("Connect failed", (Exception)((Object)iOException));
            }
        }
    }
}

