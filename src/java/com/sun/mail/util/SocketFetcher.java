/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.ClassNotFoundException
 *  java.lang.Exception
 *  java.lang.IllegalAccessException
 *  java.lang.Integer
 *  java.lang.NoSuchMethodException
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Method
 *  java.net.InetAddress
 *  java.net.InetSocketAddress
 *  java.net.Socket
 *  java.net.SocketAddress
 *  java.net.SocketTimeoutException
 *  java.security.AccessController
 *  java.security.PrivilegedAction
 *  java.util.ArrayList
 *  java.util.Properties
 *  java.util.StringTokenizer
 *  javax.net.SocketFactory
 *  javax.net.ssl.SSLSocket
 *  javax.net.ssl.SSLSocketFactory
 */
package com.sun.mail.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketFetcher {
    private SocketFetcher() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void configureSSLSocket(Socket socket, Properties properties, String string2) {
        SSLSocket sSLSocket;
        String string3;
        block6 : {
            block5 : {
                if (!(socket instanceof SSLSocket)) break block5;
                sSLSocket = (SSLSocket)socket;
                String string4 = properties.getProperty(String.valueOf((Object)string2) + ".ssl.protocols", null);
                if (string4 != null) {
                    sSLSocket.setEnabledProtocols(SocketFetcher.stringArray(string4));
                } else {
                    sSLSocket.setEnabledProtocols(new String[]{"TLSv1"});
                }
                if ((string3 = properties.getProperty(String.valueOf((Object)string2) + ".ssl.ciphersuites", null)) != null) break block6;
            }
            return;
        }
        sSLSocket.setEnabledCipherSuites(SocketFetcher.stringArray(string3));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Socket createSocket(InetAddress inetAddress, int n, String string2, int n2, int n3, SocketFactory socketFactory, boolean bl) throws IOException {
        Socket socket = socketFactory != null ? socketFactory.createSocket() : (bl ? SSLSocketFactory.getDefault().createSocket() : new Socket());
        if (inetAddress != null) {
            socket.bind((SocketAddress)new InetSocketAddress(inetAddress, n));
        }
        if (n3 >= 0) {
            socket.connect((SocketAddress)new InetSocketAddress(string2, n2), n3);
            return socket;
        }
        socket.connect((SocketAddress)new InetSocketAddress(string2, n2));
        return socket;
    }

    private static ClassLoader getContextClassLoader() {
        return (ClassLoader)AccessController.doPrivileged((PrivilegedAction)new PrivilegedAction(){

            public Object run() {
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    return classLoader;
                }
                catch (SecurityException securityException) {
                    return null;
                }
            }
        });
    }

    public static Socket getSocket(String string2, int n, Properties properties, String string3) throws IOException {
        return SocketFetcher.getSocket(string2, n, properties, string3, false);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Socket getSocket(String string2, int n, Properties properties, String string3, boolean bl) throws IOException {
        int n2;
        Socket socket;
        int n3;
        int n4;
        InetAddress inetAddress;
        String string4;
        block25 : {
            int n5;
            Socket socket2;
            SocketFactory socketFactory;
            block26 : {
                String string5;
                String string6;
                if (string3 == null) {
                    string3 = "socket";
                }
                if (properties == null) {
                    properties = new Properties();
                }
                String string7 = String.valueOf((Object)string3) + ".connectiontimeout";
                String string8 = properties.getProperty(string7, null);
                n3 = -1;
                if (string8 != null) {
                    try {
                        int n6;
                        n3 = n6 = Integer.parseInt((String)string8);
                    }
                    catch (NumberFormatException numberFormatException) {}
                }
                String string9 = String.valueOf((Object)string3) + ".timeout";
                string4 = properties.getProperty(string9, null);
                String string10 = String.valueOf((Object)string3) + ".localaddress";
                String string11 = properties.getProperty(string10, null);
                inetAddress = null;
                if (string11 != null) {
                    inetAddress = InetAddress.getByName((String)string11);
                }
                String string12 = String.valueOf((Object)string3) + ".localport";
                String string13 = properties.getProperty(string12, null);
                n4 = 0;
                if (string13 != null) {
                    try {
                        int n7;
                        n4 = n7 = Integer.parseInt((String)string13);
                    }
                    catch (NumberFormatException numberFormatException) {
                        n4 = 0;
                    }
                }
                boolean bl2 = (string5 = properties.getProperty(string6 = String.valueOf((Object)string3) + ".socketFactory.fallback", null)) == null || !string5.equalsIgnoreCase("false");
                String string14 = String.valueOf((Object)string3) + ".socketFactory.class";
                String string15 = properties.getProperty(string14, null);
                n5 = -1;
                try {
                    socketFactory = SocketFetcher.getSocketFactory(string15);
                    socket = null;
                    if (socketFactory == null) break block25;
                    String string16 = String.valueOf((Object)string3) + ".socketFactory.port";
                    String string17 = properties.getProperty(string16, null);
                    if (string17 != null) {
                        int n8;
                        n5 = n8 = Integer.parseInt((String)string17);
                    }
                    break block26;
                }
                catch (SocketTimeoutException socketTimeoutException) {
                    throw socketTimeoutException;
                }
                catch (Exception exception) {
                    socket = null;
                    if (!bl2) {
                        Exception exception2;
                        Throwable throwable;
                        if (exception instanceof InvocationTargetException && (throwable = ((InvocationTargetException)exception).getTargetException()) instanceof Exception) {
                            exception2 = (Exception)throwable;
                        }
                        if (exception2 instanceof IOException) {
                            throw (IOException)((Object)exception2);
                        }
                        IOException iOException = new IOException("Couldn't connect using \"" + string15 + "\" socket factory to host, port: " + string2 + ", " + n5 + "; Exception: " + (Object)((Object)exception2));
                        iOException.initCause((Throwable)exception2);
                        throw iOException;
                    }
                    break block25;
                }
                catch (NumberFormatException numberFormatException) {}
            }
            if (n5 == -1) {
                n5 = n;
            }
            socket = socket2 = SocketFetcher.createSocket(inetAddress, n4, string2, n5, n3, socketFactory, bl);
        }
        if (socket == null) {
            socket = SocketFetcher.createSocket(inetAddress, n4, string2, n, n3, null, bl);
        }
        n2 = -1;
        if (string4 != null) {
            try {
                int n9;
                n2 = n9 = Integer.parseInt((String)string4);
            }
            catch (NumberFormatException numberFormatException) {}
        }
        if (n2 >= 0) {
            socket.setSoTimeout(n2);
        }
        SocketFetcher.configureSSLSocket(socket, properties, string3);
        return socket;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static SocketFactory getSocketFactory(String string2) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (string2 == null || string2.length() == 0) {
            return null;
        }
        ClassLoader classLoader = SocketFetcher.getContextClassLoader();
        Class class_ = null;
        if (classLoader != null) {
            try {
                Class class_2;
                class_ = class_2 = classLoader.loadClass(string2);
            }
            catch (ClassNotFoundException classNotFoundException) {
                class_ = null;
            }
        }
        if (class_ == null) {
            class_ = Class.forName((String)string2);
        }
        return (SocketFactory)class_.getMethod("getDefault", new Class[0]).invoke(new Object(), new Object[0]);
    }

    public static Socket startTLS(Socket socket) throws IOException {
        return SocketFetcher.startTLS(socket, new Properties(), "socket");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Socket startTLS(Socket socket, Properties properties, String string2) throws IOException {
        String string3 = socket.getInetAddress().getHostName();
        int n = socket.getPort();
        try {
            SocketFactory socketFactory = SocketFetcher.getSocketFactory(properties.getProperty(String.valueOf((Object)string2) + ".socketFactory.class", null));
            SSLSocketFactory sSLSocketFactory = socketFactory != null && socketFactory instanceof SSLSocketFactory ? (SSLSocketFactory)socketFactory : (SSLSocketFactory)SSLSocketFactory.getDefault();
            Socket socket2 = sSLSocketFactory.createSocket(socket, string3, n, true);
            SocketFetcher.configureSSLSocket(socket2, properties, string2);
            return socket2;
        }
        catch (Exception exception) {
            Exception exception2;
            Throwable throwable;
            if (exception instanceof InvocationTargetException && (throwable = ((InvocationTargetException)exception).getTargetException()) instanceof Exception) {
                exception2 = (Exception)throwable;
            }
            if (exception2 instanceof IOException) {
                throw (IOException)((Object)exception2);
            }
            IOException iOException = new IOException("Exception in startTLS: host " + string3 + ", port " + n + "; Exception: " + (Object)((Object)exception2));
            iOException.initCause((Throwable)exception2);
            throw iOException;
        }
    }

    private static String[] stringArray(String string2) {
        StringTokenizer stringTokenizer = new StringTokenizer(string2);
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add((Object)stringTokenizer.nextToken());
        }
        return (String[])arrayList.toArray((Object[])new String[arrayList.size()]);
    }

}

