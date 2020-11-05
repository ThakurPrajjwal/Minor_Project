/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.FileReader
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.io.StringReader
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.Hashtable
 *  java.util.StringTokenizer
 */
package com.sun.activation.registries;

import com.sun.activation.registries.LineTokenizer;
import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MimeTypeEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class MimeTypeFile {
    private String fname = null;
    private Hashtable type_hash = new Hashtable();

    public MimeTypeFile() {
    }

    public MimeTypeFile(InputStream inputStream) throws IOException {
        this.parse(new BufferedReader((Reader)new InputStreamReader(inputStream, "iso-8859-1")));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public MimeTypeFile(String string2) throws IOException {
        this.fname = string2;
        FileReader fileReader = new FileReader(new File(this.fname));
        try {
            this.parse(new BufferedReader((Reader)fileReader));
        }
        catch (Throwable throwable) {
            try {
                fileReader.close();
            }
            catch (IOException iOException) {
                throw throwable;
            }
            do {
                throw throwable;
                break;
            } while (true);
        }
        try {
            fileReader.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parse(BufferedReader bufferedReader) throws IOException {
        String string2 = null;
        do {
            String string3;
            if ((string3 = bufferedReader.readLine()) == null) {
                if (string2 != null) {
                    this.parseEntry(string2);
                }
                return;
            }
            String string4 = string2 == null ? string3 : String.valueOf(string2) + string3;
            int n = string4.length();
            if (string4.length() > 0 && string4.charAt(n - 1) == '\\') {
                string2 = string4.substring(0, n - 1);
                continue;
            }
            this.parseEntry(string4);
            string2 = null;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parseEntry(String string2) {
        String string3;
        StringTokenizer stringTokenizer;
        String string4 = null;
        String string5 = string2.trim();
        if (string5.length() == 0 || string5.charAt(0) == '#') return;
        if (string5.indexOf(61) <= 0) {
            stringTokenizer = new StringTokenizer(string5);
            if (stringTokenizer.countTokens() == 0) return;
            string3 = stringTokenizer.nextToken();
        } else {
            LineTokenizer lineTokenizer = new LineTokenizer(string5);
            while (lineTokenizer.hasMoreTokens()) {
                String string6 = lineTokenizer.nextToken();
                boolean bl = lineTokenizer.hasMoreTokens();
                String string7 = null;
                if (bl) {
                    boolean bl2 = lineTokenizer.nextToken().equals((Object)"=");
                    string7 = null;
                    if (bl2) {
                        boolean bl3 = lineTokenizer.hasMoreTokens();
                        string7 = null;
                        if (bl3) {
                            string7 = lineTokenizer.nextToken();
                        }
                    }
                }
                if (string7 == null) {
                    if (!LogSupport.isLoggable()) return;
                    {
                        LogSupport.log("Bad .mime.types entry: " + string5);
                        return;
                    }
                }
                if (string6.equals((Object)"type")) {
                    string4 = string7;
                    continue;
                }
                if (!string6.equals((Object)"exts")) continue;
                StringTokenizer stringTokenizer2 = new StringTokenizer(string7, ",");
                while (stringTokenizer2.hasMoreTokens()) {
                    String string8 = stringTokenizer2.nextToken();
                    MimeTypeEntry mimeTypeEntry = new MimeTypeEntry(string4, string8);
                    this.type_hash.put((Object)string8, (Object)mimeTypeEntry);
                    if (!LogSupport.isLoggable()) continue;
                    LogSupport.log("Added: " + mimeTypeEntry.toString());
                }
            }
            return;
        }
        while (stringTokenizer.hasMoreTokens()) {
            String string9 = stringTokenizer.nextToken();
            MimeTypeEntry mimeTypeEntry = new MimeTypeEntry(string3, string9);
            this.type_hash.put((Object)string9, (Object)mimeTypeEntry);
            if (!LogSupport.isLoggable()) continue;
            LogSupport.log("Added: " + mimeTypeEntry.toString());
        }
    }

    public void appendToRegistry(String string2) {
        try {
            this.parse(new BufferedReader((Reader)new StringReader(string2)));
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public String getMIMETypeString(String string2) {
        MimeTypeEntry mimeTypeEntry = this.getMimeTypeEntry(string2);
        if (mimeTypeEntry != null) {
            return mimeTypeEntry.getMIMEType();
        }
        return null;
    }

    public MimeTypeEntry getMimeTypeEntry(String string2) {
        return (MimeTypeEntry)this.type_hash.get((Object)string2);
    }
}

