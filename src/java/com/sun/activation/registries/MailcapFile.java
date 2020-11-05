/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedReader
 *  java.io.FileReader
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.io.StringReader
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringIndexOutOfBoundsException
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 */
package com.sun.activation.registries;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MailcapParseException;
import com.sun.activation.registries.MailcapTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MailcapFile {
    private static boolean addReverse = false;
    private Map fallback_hash = new HashMap();
    private Map native_commands = new HashMap();
    private Map type_hash = new HashMap();

    static {
        try {
            addReverse = Boolean.getBoolean((String)"javax.activation.addreverse");
        }
        catch (Throwable throwable) {}
    }

    public MailcapFile() {
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: default");
        }
    }

    public MailcapFile(InputStream inputStream) throws IOException {
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: InputStream");
        }
        this.parse((Reader)new BufferedReader((Reader)new InputStreamReader(inputStream, "iso-8859-1")));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public MailcapFile(String var1_1) throws IOException {
        super();
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: file " + var1_1);
        }
        var2_2 = null;
        try {
            var3_3 = new FileReader(var1_1);
        }
        catch (Throwable var4_4) {}
        this.parse((Reader)new BufferedReader((Reader)var3_3));
        if (var3_3 == null) return;
        try {
            var3_3.close();
            return;
        }
        catch (IOException var6_8) {
            return;
        }
        ** GOTO lbl-1000
        catch (Throwable var4_6) {
            var2_2 = var3_3;
        }
lbl-1000: // 2 sources:
        {
            if (var2_2 == null) throw var4_5;
            try {
                var2_2.close();
            }
            catch (IOException var5_7) {
                throw var4_5;
            }
            throw var4_5;
        }
    }

    private Map mergeResults(Map map, Map map2) {
        Iterator iterator = map2.keySet().iterator();
        HashMap hashMap = new HashMap(map);
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            List list = (List)hashMap.get((Object)string2);
            if (list == null) {
                hashMap.put((Object)string2, map2.get((Object)string2));
                continue;
            }
            List list2 = (List)map2.get((Object)string2);
            ArrayList arrayList = new ArrayList((Collection)list);
            arrayList.addAll((Collection)list2);
            hashMap.put((Object)string2, (Object)arrayList);
        }
        return hashMap;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void parse(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String string2 = null;
        String string3;
        while ((string3 = bufferedReader.readLine()) != null) {
            String string4 = string3.trim();
            try {
                if (string4.charAt(0) == '#') continue;
                if (string4.charAt(-1 + string4.length()) == '\\') {
                    if (string2 != null) {
                        string2 = String.valueOf(string2) + string4.substring(0, -1 + string4.length());
                        continue;
                    }
                    string2 = string4.substring(0, -1 + string4.length());
                    continue;
                }
                if (string2 != null) {
                    String string5;
                    string2 = string5 = String.valueOf(string2) + string4;
                    this.parseLine(string2);
                } else {
                    try {
                        this.parseLine(string4);
                    }
                    catch (MailcapParseException mailcapParseException) {}
                    continue;
                    catch (MailcapParseException mailcapParseException) {}
                }
                string2 = null;
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            }
        }
        return;
    }

    protected static void reportParseError(int n, int n2, int n3, int n4, String string2) throws MailcapParseException {
        if (LogSupport.isLoggable()) {
            LogSupport.log("PARSE ERROR: Encountered a " + MailcapTokenizer.nameForToken(n4) + " token (" + string2 + ") while expecting a " + MailcapTokenizer.nameForToken(n) + ", a " + MailcapTokenizer.nameForToken(n2) + ", or a " + MailcapTokenizer.nameForToken(n3) + " token.");
        }
        throw new MailcapParseException("Encountered a " + MailcapTokenizer.nameForToken(n4) + " token (" + string2 + ") while expecting a " + MailcapTokenizer.nameForToken(n) + ", a " + MailcapTokenizer.nameForToken(n2) + ", or a " + MailcapTokenizer.nameForToken(n3) + " token.");
    }

    protected static void reportParseError(int n, int n2, int n3, String string2) throws MailcapParseException {
        throw new MailcapParseException("Encountered a " + MailcapTokenizer.nameForToken(n3) + " token (" + string2 + ") while expecting a " + MailcapTokenizer.nameForToken(n) + " or a " + MailcapTokenizer.nameForToken(n2) + " token.");
    }

    protected static void reportParseError(int n, int n2, String string2) throws MailcapParseException {
        throw new MailcapParseException("Encountered a " + MailcapTokenizer.nameForToken(n2) + " token (" + string2 + ") while expecting a " + MailcapTokenizer.nameForToken(n) + " token.");
    }

    public void appendToMailcap(String string2) {
        if (LogSupport.isLoggable()) {
            LogSupport.log("appendToMailcap: " + string2);
        }
        try {
            this.parse((Reader)new StringReader(string2));
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public Map getMailcapFallbackList(String string2) {
        Map map;
        block3 : {
            Map map2;
            block2 : {
                String string3;
                map2 = (Map)this.fallback_hash.get((Object)string2);
                int n = string2.indexOf(47);
                if (string2.substring(n + 1).equals((Object)"*") || (map = (Map)this.fallback_hash.get((Object)(string3 = String.valueOf((Object)string2.substring(0, n + 1)) + "*"))) == null) break block2;
                if (map2 == null) break block3;
                map2 = this.mergeResults(map2, map);
            }
            return map2;
        }
        return map;
    }

    public Map getMailcapList(String string2) {
        Map map;
        block3 : {
            Map map2;
            block2 : {
                String string3;
                map2 = (Map)this.type_hash.get((Object)string2);
                int n = string2.indexOf(47);
                if (string2.substring(n + 1).equals((Object)"*") || (map = (Map)this.type_hash.get((Object)(string3 = String.valueOf((Object)string2.substring(0, n + 1)) + "*"))) == null) break block2;
                if (map2 == null) break block3;
                map2 = this.mergeResults(map2, map);
            }
            return map2;
        }
        return map;
    }

    public String[] getMimeTypes() {
        HashSet hashSet = new HashSet((Collection)this.type_hash.keySet());
        hashSet.addAll((Collection)this.fallback_hash.keySet());
        hashSet.addAll((Collection)this.native_commands.keySet());
        return (String[])hashSet.toArray((Object[])new String[hashSet.size()]);
    }

    public String[] getNativeCommands(String string2) {
        String[] arrstring = null;
        List list = (List)this.native_commands.get((Object)string2.toLowerCase(Locale.ENGLISH));
        if (list != null) {
            arrstring = (String[])list.toArray((Object[])new String[list.size()]);
        }
        return arrstring;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void parseLine(String var1_1) throws MailcapParseException, IOException {
        var2_2 = new MailcapTokenizer(var1_1);
        var2_2.setIsAutoquoting(false);
        if (LogSupport.isLoggable()) {
            LogSupport.log("parse: " + var1_1);
        }
        if ((var3_3 = var2_2.nextToken()) != 2) {
            MailcapFile.reportParseError(2, var3_3, var2_2.getCurrentTokenValue());
        }
        var4_4 = var2_2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
        var5_5 = "*";
        var6_6 = var2_2.nextToken();
        if (var6_6 != 47 && var6_6 != 59) {
            MailcapFile.reportParseError(47, 59, var6_6, var2_2.getCurrentTokenValue());
        }
        if (var6_6 == 47) {
            var38_7 = var2_2.nextToken();
            if (var38_7 != 2) {
                MailcapFile.reportParseError(2, var38_7, var2_2.getCurrentTokenValue());
            }
            var5_5 = var2_2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            var6_6 = var2_2.nextToken();
        }
        var7_8 = String.valueOf((Object)var4_4) + "/" + var5_5;
        if (LogSupport.isLoggable()) {
            LogSupport.log("  Type: " + var7_8);
        }
        var8_9 = new LinkedHashMap();
        if (var6_6 != 59) {
            MailcapFile.reportParseError(59, var6_6, var2_2.getCurrentTokenValue());
        }
        var2_2.setIsAutoquoting(true);
        var9_10 = var2_2.nextToken();
        var2_2.setIsAutoquoting(false);
        if (var9_10 != 2 && var9_10 != 59) {
            MailcapFile.reportParseError(2, 59, var9_10, var2_2.getCurrentTokenValue());
        }
        if (var9_10 == 2) {
            var33_11 = (List)this.native_commands.get((Object)var7_8);
            if (var33_11 == null) {
                var34_12 = new ArrayList();
                var34_12.add((Object)var1_1);
                this.native_commands.put((Object)var7_8, (Object)var34_12);
            } else {
                var33_11.add((Object)var1_1);
            }
        }
        if (var9_10 != 59) {
            var9_10 = var2_2.nextToken();
        }
        if (var9_10 != 59) {
            if (var9_10 == 5) return;
            MailcapFile.reportParseError(5, 59, var9_10, var2_2.getCurrentTokenValue());
            return;
        }
        var10_13 = false;
        do {
            if ((var11_14 = var2_2.nextToken()) != 2) {
                MailcapFile.reportParseError(2, var11_14, var2_2.getCurrentTokenValue());
            }
            var12_15 = var2_2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            var13_16 = var2_2.nextToken();
            if (var13_16 != 61 && var13_16 != 59 && var13_16 != 5) {
                MailcapFile.reportParseError(61, 59, 5, var13_16, var2_2.getCurrentTokenValue());
            }
            if (var13_16 != 61) continue;
            var2_2.setIsAutoquoting(true);
            var27_17 = var2_2.nextToken();
            var2_2.setIsAutoquoting(false);
            if (var27_17 != 2) {
                MailcapFile.reportParseError(2, var27_17, var2_2.getCurrentTokenValue());
            }
            var28_18 = var2_2.getCurrentTokenValue();
            if (var12_15.startsWith("x-java-")) {
                var29_19 = var12_15.substring(7);
                if (var29_19.equals((Object)"fallback-entry") && var28_18.equalsIgnoreCase("true")) {
                    var10_13 = true;
                } else {
                    if (LogSupport.isLoggable()) {
                        LogSupport.log("    Command: " + var29_19 + ", Class: " + var28_18);
                    }
                    if ((var30_20 = (List)var8_9.get((Object)var29_19)) == null) {
                        var30_20 = new ArrayList();
                        var8_9.put((Object)var29_19, (Object)var30_20);
                    }
                    if (MailcapFile.addReverse) {
                        var30_20.add(0, (Object)var28_18);
                    } else {
                        var30_20.add((Object)var28_18);
                    }
                }
            }
            var13_16 = var2_2.nextToken();
        } while (var13_16 == 59);
        var14_21 = var10_13 != false ? this.fallback_hash : this.type_hash;
        var15_22 = (Map)var14_21.get((Object)var7_8);
        if (var15_22 == null) {
            var14_21.put((Object)var7_8, (Object)var8_9);
            return;
        }
        if (LogSupport.isLoggable()) {
            LogSupport.log("Merging commands for type " + var7_8);
        }
        var16_23 = var15_22.keySet().iterator();
        block1 : do lbl-1000: // 3 sources:
        {
            if (!var16_23.hasNext()) {
                var23_29 = var8_9.keySet().iterator();
                while (var23_29.hasNext() != false) {
                    var24_30 = (String)var23_29.next();
                    if (var15_22.containsKey((Object)var24_30)) continue;
                    var15_22.put((Object)var24_30, (Object)((List)var8_9.get((Object)var24_30)));
                }
                return;
            }
            var17_24 = (String)var16_23.next();
            var18_25 = (List)var15_22.get((Object)var17_24);
            var19_26 = (List)var8_9.get((Object)var17_24);
            if (var19_26 == null) ** GOTO lbl-1000
            var20_27 = var19_26.iterator();
            do {
                if (!var20_27.hasNext()) continue block1;
                var21_28 = (String)var20_27.next();
                if (var18_25.contains((Object)var21_28)) continue;
                if (MailcapFile.addReverse) {
                    var18_25.add(0, (Object)var21_28);
                    continue;
                }
                var18_25.add((Object)var21_28);
            } while (true);
            break;
        } while (true);
    }
}

