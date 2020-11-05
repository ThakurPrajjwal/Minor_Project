/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.preference.PreferenceManager
 *  android.util.Log
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 */
package com.neoAntara.newrestaurant.Print;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import java.util.ArrayList;

public class PlainPrint {
    private static SharedPreferences pref = null;
    ArrayList<String> columnEntries = new ArrayList();
    int columnsCount = 0;
    String content = "";
    Context context;
    int doubleColRank = 0;
    boolean doubleColumnPresent = false;
    int equalCharacters = 10;
    String fullContent = "";
    int itemColumnCharacters = 15;
    int itemColumnRank = 0;
    boolean itemColumnsSet = true;
    boolean lastColRightAligned = false;
    int leftHalfMaxChars = 25;
    int maxChars = 50;
    int maxColumnsPerRow = 2;
    int minCharsPerFieldEntry = 8;
    boolean multiRowEntries = false;
    int multiRowEntriesCount = 2;
    boolean oddColumnsEntries = false;
    int pageWidth = 53;
    int qtyColumnCharacters = 10;
    int qtyCoulmnRank = 0;
    int rateCoulmnRank = 0;
    int rightHalfMaxChars = 25;
    int serialColumnCharacters = 15;
    int serialColumnRank = 0;
    boolean showSerialNo = false;
    char space = (char)32;
    int tttequalCharactersNoItem = 10;

    /*
     * Enabled aggressive block sorting
     */
    public PlainPrint(Context context, int n, int n2) {
        pref = PreferenceManager.getDefaultSharedPreferences((Context)context);
        this.context = context;
        this.content = "";
        this.fullContent = "";
        this.minCharsPerFieldEntry = n2;
        this.multiRowEntries = false;
        this.maxChars = (int)(0.67 * (double)n);
        this.maxChars = -1 + this.maxChars;
        if (this.maxChars > 0) {
            if (this.maxChars % 2 == 0) {
                this.leftHalfMaxChars = this.maxChars / 2;
                this.rightHalfMaxChars = this.maxChars / 2;
            } else {
                this.leftHalfMaxChars = (-1 + this.maxChars) / 2;
                this.rightHalfMaxChars = this.maxChars - this.leftHalfMaxChars;
            }
        }
        Log.v((String)"plaintext maxChars ", (String)(this.maxChars + ""));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addItemContentSingleColumn(ArrayList<String> arrayList) {
        Log.v((String)"plainText itcn columnsCount", (String)(this.columnsCount + " itemsList size " + arrayList.size()));
        ArrayList arrayList2 = new ArrayList();
        String string2 = "";
        for (int i = 0; i < arrayList.size(); ++i) {
            string2 = string2 + (String)arrayList.get(i);
        }
        Log.v((String)"pt occ", (String)(string2.trim().length() + ""));
        int n = 0;
        arrayList2.clear();
        boolean bl = this.itemColumnsSet;
        boolean bl2 = this.occurenceCount(string2, this.space) != string2.length();
        if (bl2 & bl) {
            String string3 = "";
            boolean bl3 = false;
            Log.v((String)"plaintext addcontent", (String)(" : " + this.itemColumnRank + " columnsCount " + this.columnsCount + " itemColumnCharacters " + this.itemColumnCharacters + " serialcolchars " + this.serialColumnCharacters));
            for (int i = 0; i < arrayList.size(); ++i) {
                String string4 = (String)arrayList.get(i);
                if (i > 1 && i != this.itemColumnRank && this.equalCharacters < 6) {
                    string4 = string4.replaceAll("\\.00", "");
                }
                Log.v((String)"pt i", (String)(i + " equalCharacters " + this.equalCharacters));
                Log.v((String)"pt colcm", (String)(" wowad item " + string4));
                boolean bl4 = false;
                if (i != this.itemColumnRank) {
                    if (i == this.serialColumnRank) {
                        bl4 = false;
                    } else if (i == this.qtyCoulmnRank) {
                        bl4 = false;
                    } else {
                        int n2 = this.rateCoulmnRank;
                        bl4 = false;
                        if (i == n2) {
                            bl4 = true;
                        }
                    }
                }
                int n3 = this.doubleColumnPresent && i == this.itemColumnRank ? this.itemColumnCharacters : this.equalCharacters;
                if (string4.length() <= n3) {
                    Log.v((String)"pt mycol", (String)"item column1");
                    String string5 = this.getStringWhiteSpacesSingleLine(string4, n3, bl4, true);
                    string3 = string3 + string5;
                    String string6 = "";
                    for (int j = 0; j < n3; ++j) {
                        string6 = string6 + this.space;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    int n4 = n + 1;
                    Log.v((String)"pt rk", (String)stringBuilder.append(n).append(" ad").toString());
                    arrayList2.add((Object)string6);
                    n = n4;
                    continue;
                }
                Log.v((String)"pt mycol", (String)"item column2");
                bl3 = true;
                String string7 = string4.substring(0, n3);
                String string8 = string4.substring(n3);
                if (string7.substring(-1 + string7.length()).equals((Object)" ")) {
                    string3 = string3 + string7;
                    arrayList2.add((Object)string8);
                    continue;
                }
                int n5 = string7.lastIndexOf(" ");
                if (n5 != -1) {
                    String string9 = string7.substring(0, n5);
                    Log.v((String)"newstr", (String)(string9 + " len " + string9.length()));
                    string3 = string3 + string9;
                    for (int j = 0; j < n3 - string9.length(); ++j) {
                        string3 = string3 + this.space;
                    }
                    String string10 = string7.substring(n5 + 1) + string8;
                    StringBuilder stringBuilder = new StringBuilder();
                    int n6 = n + 1;
                    Log.v((String)"pt rk", (String)stringBuilder.append(n).append(" ad2").toString());
                    arrayList2.add((Object)string10);
                    n = n6;
                    continue;
                }
                string3 = string3 + string7;
                arrayList2.add((Object)string8);
            }
            if (!string3.equals((Object)"")) {
                this.addNewLine();
                Log.v((String)"plain text", (String)(string3 + " new line"));
                this.addText(string3);
            }
            Log.v((String)"plainText nextItemsList size", (String)((Object)arrayList2 + " " + n));
            if (bl3) {
                this.addItemContentSingleColumn((ArrayList<String>)arrayList2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addItemTitlesSingleColumn(ArrayList<String> arrayList) {
        String string2 = "";
        this.itemColumnsSet = true;
        for (int i = 0; i < arrayList.size(); ++i) {
            Log.v((String)"plainText columns", (String)((String)arrayList.get(i)));
            boolean bl = false;
            if (i != this.serialColumnRank) {
                if (i == this.itemColumnRank) {
                    bl = false;
                } else if (i == this.qtyCoulmnRank) {
                    bl = false;
                } else {
                    int n = this.rateCoulmnRank;
                    bl = false;
                    if (i == n) {
                        bl = true;
                    }
                }
            }
            Log.v((String)"pl itcon", (String)(this.doubleColumnPresent + " i:" + i + " doubleColRank:" + this.doubleColRank + " itemColumnCharacters:" + this.itemColumnCharacters));
            int n = this.doubleColumnPresent && i == this.itemColumnRank ? this.itemColumnCharacters : this.equalCharacters;
            Log.v((String)"pl itcon1", (String)("myCharsCount: " + n));
            boolean bl2 = true;
            if (!bl && i == -1 + arrayList.size()) {
                bl2 = false;
            }
            String string3 = this.getStringWhiteSpacesSingleLine((String)arrayList.get(i), n, bl, bl2);
            Log.v((String)"pl itemTitles", (String)(string3.length() + " len"));
            string2 = string2 + string3;
        }
        if (!string2.equals((Object)"")) {
            this.addNewLine();
            this.content = this.content + string2;
            this.fullContent = this.fullContent + string2;
        }
    }

    private void addWhiteSpaces(int n) {
        for (int i = 0; i < n; ++i) {
            this.content = this.content + this.space;
            this.fullContent = this.fullContent + this.space;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private String getStringWhiteSpacesSingleLine(String string2, int n, boolean bl, boolean bl2) {
        String string3;
        Log.v((String)"plaintext setStringWhiteSpacesSingleLine", (String)string2);
        String string4 = "";
        int n2 = string2.length();
        if (n2 == n) {
            return string2;
        }
        if (n2 >= n) {
            return string2.substring(0, n);
        }
        int n3 = n - n2;
        if (bl) {
            for (int i = 0; i < n3; ++i) {
                string4 = string4 + this.space;
            }
            string3 = string4 + string2;
        } else {
            string3 = string2;
            if (bl2) {
                for (int i = 0; i < n3; ++i) {
                    string3 = string3 + this.space;
                }
            }
        }
        Log.v((String)"plaintext setStringWhiteSpacesSingleLine len", (String)(n2 + " remLen " + n3));
        return string3;
    }

    public void addDashesFullLine() {
        this.addNewLine();
        for (int i = 0; i < this.maxChars; ++i) {
            this.content = this.content + "-";
            this.fullContent = this.fullContent + "-";
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addItemContent(ArrayList<String> arrayList) {
        block22 : {
            block21 : {
                if (this.columnsCount == 0) break block21;
                if (!this.multiRowEntries) {
                    this.addItemContentSingleColumn(arrayList);
                    return;
                }
                if (this.multiRowEntriesCount == 2) {
                    int n = -1 + this.maxColumnsPerRow;
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    for (int i = 0; i < n; ++i) {
                        arrayList2.add(arrayList.get(i));
                    }
                    for (int i = n; i < this.columnsCount; ++i) {
                        arrayList3.add(arrayList.get(i));
                    }
                    this.addItemTitlesSingleColumn((ArrayList<String>)arrayList2);
                    Log.v((String)"plainText myList size ", (String)(arrayList2.size() + ""));
                    int n2 = this.equalCharacters;
                    int n3 = this.itemColumnRank;
                    int n4 = this.serialColumnRank;
                    int n5 = this.qtyCoulmnRank;
                    int n6 = this.rateCoulmnRank;
                    this.itemColumnRank = 100;
                    this.serialColumnRank = 150;
                    this.qtyCoulmnRank = 140;
                    this.rateCoulmnRank = 140;
                    int n7 = 0;
                    do {
                        if (n7 >= 7) {
                            this.addItemTitlesSingleColumn((ArrayList<String>)arrayList3);
                            this.itemColumnRank = n3;
                            this.serialColumnRank = n4;
                            this.qtyCoulmnRank = n5;
                            this.rateCoulmnRank = n6;
                            this.equalCharacters = n2;
                            return;
                        }
                        if (arrayList3.size() * (1 + this.equalCharacters) <= this.maxChars) {
                            this.equalCharacters = 1 + this.equalCharacters;
                        }
                        ++n7;
                    } while (true);
                }
                if (this.multiRowEntriesCount == 3) break block22;
            }
            return;
        }
        int n = -1 + this.maxColumnsPerRow;
        int n8 = n + this.maxColumnsPerRow;
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        for (int i = 0; i < n; ++i) {
            arrayList4.add(arrayList.get(i));
        }
        for (int i = n; i < n8; ++i) {
            arrayList5.add(arrayList.get(i));
        }
        for (int i = n8; i < this.columnsCount; ++i) {
            arrayList6.add(arrayList.get(i));
        }
        this.addItemTitlesSingleColumn((ArrayList<String>)arrayList4);
        Log.v((String)"plainText myList size ", (String)(arrayList4.size() + ""));
        int n9 = this.equalCharacters;
        int n10 = this.itemColumnRank;
        int n11 = this.serialColumnRank;
        int n12 = this.qtyCoulmnRank;
        int n13 = this.rateCoulmnRank;
        this.itemColumnRank = 100;
        this.serialColumnRank = 150;
        this.qtyCoulmnRank = 150;
        this.rateCoulmnRank = 150;
        if (arrayList5.size() != this.maxColumnsPerRow) {
            if (arrayList5.size() * (1 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 1 + this.equalCharacters;
            }
            if (arrayList5.size() * (2 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 2 + this.equalCharacters;
            }
            if (arrayList5.size() * (3 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 3 + this.equalCharacters;
            }
        }
        this.addItemTitlesSingleColumn((ArrayList<String>)arrayList5);
        this.equalCharacters = n9;
        if (arrayList6.size() != this.maxColumnsPerRow) {
            if (arrayList6.size() * (1 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 1 + this.equalCharacters;
            }
            if (arrayList6.size() * (2 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 2 + this.equalCharacters;
            }
            if (arrayList6.size() * (3 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 3 + this.equalCharacters;
            }
        }
        this.addItemTitlesSingleColumn((ArrayList<String>)arrayList6);
        this.itemColumnRank = n10;
        this.serialColumnRank = n11;
        this.qtyCoulmnRank = n12;
        this.rateCoulmnRank = n13;
        this.equalCharacters = n9;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addItemTiles(ArrayList<String> arrayList) {
        block22 : {
            block21 : {
                Log.v((String)"PlainPrint addItemTiles", (String)(" multiRowEntries " + this.multiRowEntries + " multiRowEntriesCount " + this.multiRowEntriesCount));
                if (this.columnsCount == 0) break block21;
                if (!this.multiRowEntries) {
                    this.addItemTitlesSingleColumn(arrayList);
                    return;
                }
                if (this.multiRowEntriesCount == 2) {
                    Log.v((String)"PlainPrint itemTitles", (String)("equalCharacters " + this.equalCharacters + " maxColumnsPerRow " + this.maxColumnsPerRow + " itemColumnCharacters " + this.itemColumnCharacters));
                    int n = -1 + this.maxColumnsPerRow;
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    for (int i = 0; i < n; ++i) {
                        arrayList2.add(arrayList.get(i));
                    }
                    for (int i = n; i < this.columnsCount; ++i) {
                        arrayList3.add(arrayList.get(i));
                    }
                    this.addItemTitlesSingleColumn((ArrayList<String>)arrayList2);
                    Log.v((String)"plainText myList size ", (String)(arrayList2.size() + ""));
                    int n2 = this.equalCharacters;
                    int n3 = this.itemColumnRank;
                    int n4 = this.serialColumnRank;
                    int n5 = this.qtyCoulmnRank;
                    int n6 = this.rateCoulmnRank;
                    this.itemColumnRank = 100;
                    this.serialColumnRank = 150;
                    this.qtyCoulmnRank = 140;
                    this.rateCoulmnRank = 140;
                    int n7 = 0;
                    do {
                        if (n7 >= 7) {
                            Log.v((String)"plainText equalCharacters ", (String)(this.equalCharacters + ""));
                            this.addItemTitlesSingleColumn((ArrayList<String>)arrayList3);
                            this.itemColumnRank = n3;
                            this.serialColumnRank = n4;
                            this.qtyCoulmnRank = n5;
                            this.rateCoulmnRank = n6;
                            this.equalCharacters = n2;
                            return;
                        }
                        if (arrayList3.size() * (1 + this.equalCharacters) <= this.maxChars) {
                            this.equalCharacters = 1 + this.equalCharacters;
                        }
                        ++n7;
                    } while (true);
                }
                if (this.multiRowEntriesCount == 3) break block22;
            }
            return;
        }
        Log.v((String)"PlainPrint itemTitles", (String)("equalCharacters " + this.equalCharacters + " maxColumnsPerRow " + this.maxColumnsPerRow + " itemColumnCharacters " + this.itemColumnCharacters));
        int n = -1 + this.maxColumnsPerRow;
        int n8 = n + this.maxColumnsPerRow;
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        for (int i = 0; i < n; ++i) {
            arrayList4.add(arrayList.get(i));
        }
        for (int i = n; i < n8; ++i) {
            arrayList5.add(arrayList.get(i));
        }
        for (int i = n8; i < this.columnsCount; ++i) {
            arrayList6.add(arrayList.get(i));
        }
        this.addItemTitlesSingleColumn((ArrayList<String>)arrayList4);
        Log.v((String)"plainText myList size ", (String)(arrayList4.size() + ""));
        int n9 = this.equalCharacters;
        int n10 = this.itemColumnRank;
        int n11 = this.serialColumnRank;
        int n12 = this.qtyCoulmnRank;
        int n13 = this.rateCoulmnRank;
        this.itemColumnRank = 100;
        this.serialColumnRank = 150;
        this.qtyCoulmnRank = 150;
        this.rateCoulmnRank = 150;
        if (arrayList5.size() != this.maxColumnsPerRow) {
            if (arrayList5.size() * (1 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 1 + this.equalCharacters;
            }
            if (arrayList5.size() * (2 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 2 + this.equalCharacters;
            }
            if (arrayList5.size() * (3 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 3 + this.equalCharacters;
            }
        }
        this.addItemTitlesSingleColumn((ArrayList<String>)arrayList5);
        this.equalCharacters = n9;
        if (arrayList6.size() != this.maxColumnsPerRow) {
            if (arrayList6.size() * (1 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 1 + this.equalCharacters;
            }
            if (arrayList6.size() * (2 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 2 + this.equalCharacters;
            }
            if (arrayList6.size() * (3 + this.equalCharacters) <= this.maxChars) {
                this.equalCharacters = 3 + this.equalCharacters;
            }
        }
        this.addItemTitlesSingleColumn((ArrayList<String>)arrayList6);
        this.itemColumnRank = n10;
        this.serialColumnRank = n11;
        this.qtyCoulmnRank = n12;
        this.rateCoulmnRank = n13;
        this.equalCharacters = n9;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addLeftRightText(String string2, String string3, boolean bl) {
        String string4 = string2.replaceAll("\n", " ");
        String string5 = string3.replaceAll("\n", " ");
        int n = string4.length();
        int n2 = string5.length();
        if (n <= 0 && n2 <= 0) return;
        if (bl) {
            this.addNewLine();
        }
        if (string4.contains((CharSequence)"\n") || string5.contains((CharSequence)"\n")) return;
        if (n <= this.leftHalfMaxChars && n2 <= this.rightHalfMaxChars) {
            int n3 = this.leftHalfMaxChars - n;
            this.addText(string4);
            this.addWhiteSpaces(n3);
            this.addText(string5);
            return;
        }
        if (n > this.leftHalfMaxChars && n2 <= this.rightHalfMaxChars) {
            if (n2 == 0) {
                this.addText(string4);
                return;
            }
            String string6 = string4.substring(0, this.leftHalfMaxChars);
            String string7 = string4.substring(this.leftHalfMaxChars);
            this.addText(string6 + string5);
            if (string7.trim().length() == 0) return;
            {
                this.addLeftRightText(string7, "", true);
                return;
            }
        }
        if (n <= this.leftHalfMaxChars && n2 > this.rightHalfMaxChars) {
            int n4 = this.leftHalfMaxChars - n;
            String string8 = string5.substring(0, this.rightHalfMaxChars);
            String string9 = string5.substring(this.rightHalfMaxChars);
            this.addText(string4);
            this.addWhiteSpaces(n4);
            this.addText(string8);
            if (string9.trim().length() == 0) return;
            {
                this.addLeftRightText("", string9, true);
                return;
            }
        }
        if (n <= this.leftHalfMaxChars || n2 <= this.rightHalfMaxChars) return;
        String string10 = string4.substring(0, this.leftHalfMaxChars);
        String string11 = string4.substring(this.leftHalfMaxChars);
        String string12 = string5.substring(0, this.rightHalfMaxChars);
        String string13 = string5.substring(this.rightHalfMaxChars);
        this.addText(string10 + string12);
        if (string11.trim().length() == 0 && string13.trim().length() == 0) {
            return;
        }
        this.addLeftRightText(string11, string13, true);
    }

    public void addLeftRightTextJustified(String string2, String string3, boolean bl) {
        int n;
        String string4;
        int n2;
        String string5;
        block6 : {
            block5 : {
                string4 = string2.replaceAll("\n", " ");
                string5 = string3.replaceAll("\n", " ");
                n2 = string4.length();
                n = string5.length();
                if (n2 <= 0 && n <= 0) break block5;
                if (bl) {
                    this.addNewLine();
                }
                if (!string4.contains((CharSequence)"\n") && !string5.contains((CharSequence)"\n")) break block6;
            }
            return;
        }
        if (n2 + n <= this.maxChars) {
            int n3 = this.maxChars - (n2 + n);
            this.addText(string4);
            this.addWhiteSpaces(n3);
            this.addText(string5);
            return;
        }
        this.addText(string5);
    }

    public void addNewLine() {
        this.content = this.content + "\n";
        this.fullContent = this.fullContent + "\n";
    }

    public void addText(String string2) {
        this.content = this.content + string2;
        this.fullContent = this.fullContent + string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addTextCenterAlign(String string2, boolean bl) {
        int n;
        if (string2 == null) {
            string2 = "";
        }
        if ((n = string2.length()) <= 0) return;
        {
            if (bl) {
                this.addNewLine();
            }
            if (!string2.contains((CharSequence)"\n")) {
                if (n == this.maxChars) {
                    this.addText(string2);
                    return;
                } else {
                    if (n < this.maxChars) {
                        int n2 = this.maxChars - n;
                        if (n2 == 1) {
                            this.addText(string2);
                            return;
                        }
                        int n3 = n2 % 2 == 0 ? n2 / 2 : -1 + n2 / 2;
                        this.addWhiteSpaces(n3);
                        this.addText(string2);
                        return;
                    }
                    if (n <= this.maxChars) return;
                    {
                        String string3 = string2.substring(0, this.maxChars);
                        String string4 = string2.substring(this.maxChars);
                        this.addText(string3);
                        this.addTextCenterAlign(string4, true);
                        return;
                    }
                }
            } else {
                String[] arrstring = string2.split("\n");
                for (int i = 0; i < arrstring.length; ++i) {
                    if (arrstring[i].length() == 0) {
                        this.addNewLine();
                        continue;
                    }
                    if (i != 0) {
                        this.addNewLine();
                    }
                    this.addTextCenterAlign(arrstring[i], false);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addTextDoubleWidthCenterAlign(String string2, boolean bl) {
        int n;
        if (string2 == null) {
            string2 = "";
        }
        if ((n = 2 * string2.length()) <= 0) return;
        {
            if (bl) {
                this.addNewLine();
            }
            if (!string2.contains((CharSequence)"\n")) {
                if (n == this.maxChars) {
                    this.addText(string2);
                    return;
                } else {
                    if (n < this.maxChars) {
                        int n2 = this.maxChars - n;
                        if (n2 == 1) {
                            this.addText(string2);
                            return;
                        }
                        int n3 = n2 % 2 == 0 ? n2 / 2 : (n2 - 1) / 2;
                        this.addWhiteSpaces(n3 / 2);
                        this.addText(string2);
                        Log.v((String)"PlainPrint", (String)("length " + n + " remainingEqualChars " + n3));
                        return;
                    }
                    if (n <= this.maxChars) return;
                    {
                        int n4 = this.maxChars % 2 == 0 ? this.maxChars / 2 : (-1 + this.maxChars) / 2;
                        String string3 = string2.substring(0, n4);
                        String string4 = string2.substring(n4);
                        if (string3.substring(-1 + string3.length()).equals((Object)" ")) {
                            this.addText(string3);
                            this.addTextDoubleWidthCenterAlign(string4, true);
                            return;
                        }
                        int n5 = string3.lastIndexOf(" ");
                        if (n5 != -1) {
                            String string5 = string3.substring(0, n5);
                            String string6 = string3.substring(n5 + 1) + string4;
                            this.addTextDoubleWidthCenterAlign(string5 + "\n" + string6, false);
                            return;
                        }
                        this.addText(string3);
                        this.addTextDoubleWidthCenterAlign(string4, true);
                        return;
                    }
                }
            } else {
                String[] arrstring = string2.split("\n");
                for (int i = 0; i < arrstring.length; ++i) {
                    if (arrstring[i].length() == 0) {
                        this.addNewLine();
                        continue;
                    }
                    if (i != 0) {
                        this.addNewLine();
                    }
                    this.addTextDoubleWidthCenterAlign(arrstring[i], false);
                }
            }
        }
    }

    public void addTextLeftAlign(String string2) {
        if (string2 == null) {
            string2 = "";
        }
        if (string2.length() > 0) {
            this.addNewLine();
            this.content = this.content + string2;
            this.fullContent = this.fullContent + string2;
        }
    }

    public String getContent4PrintFields() {
        return this.content.replaceFirst("\n", "");
    }

    public String getDashesFullLine() {
        String string2 = "";
        for (int i = 0; i < this.maxChars; ++i) {
            string2 = string2 + "-";
        }
        return string2;
    }

    public String getFinalFullText() {
        return this.fullContent;
    }

    public String getStarsFullLine() {
        String string2 = "";
        for (int i = 0; i < this.maxChars; ++i) {
            string2 = string2 + "*";
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void getTextRightAlign(String string2, boolean bl) {
        int n;
        if (string2 == null) {
            string2 = "";
        }
        if ((n = string2.length()) <= 0) return;
        {
            if (bl) {
                this.addNewLine();
            }
            if (!string2.contains((CharSequence)"\n")) {
                if (n == this.maxChars) {
                    this.addText(string2);
                    return;
                } else {
                    if (n < this.maxChars) {
                        int n2 = this.maxChars - n;
                        if (n2 == 1) {
                            this.addWhiteSpaces(1);
                            this.addText(string2);
                            return;
                        }
                        this.addWhiteSpaces(n2);
                        this.addText(string2);
                        return;
                    }
                    if (n <= this.maxChars) return;
                    {
                        String string3 = string2.substring(0, this.maxChars);
                        String string4 = string2.substring(this.maxChars);
                        this.addText(string3);
                        this.getTextRightAlign(string4, true);
                        return;
                    }
                }
            } else {
                String[] arrstring = string2.split("\n");
                for (int i = 0; i < arrstring.length; ++i) {
                    if (arrstring[i].length() == 0) {
                        this.addNewLine();
                        continue;
                    }
                    if (i != 0) {
                        this.addNewLine();
                    }
                    this.getTextRightAlign(arrstring[i], false);
                }
            }
        }
    }

    public int occurenceCount(String string2, char c) {
        int n = 0;
        for (int i = 0; i < string2.length(); ++i) {
            if (string2.charAt(i) != c) continue;
            ++n;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void prepareTabularForm(int n, int n2, int n3, boolean bl) {
        int n4;
        int n5;
        this.columnsCount = n;
        this.itemColumnRank = n2;
        this.qtyCoulmnRank = 1000;
        this.rateCoulmnRank = n3;
        this.maxColumnsPerRow = 2;
        if (!bl) {
            this.equalCharacters = this.maxChars / this.columnsCount;
        } else {
            this.equalCharacters = this.maxChars / (1 + this.columnsCount);
            this.doubleColumnPresent = true;
            this.doubleColRank = this.itemColumnRank;
        }
        this.itemColumnCharacters = this.maxChars - this.equalCharacters * (-1 + this.columnsCount);
        if (this.equalCharacters < this.minCharsPerFieldEntry) {
            this.multiRowEntries = true;
        }
        Log.v((String)"PlainPrint", (String)("multiRowEntries " + this.multiRowEntries));
        if (!this.multiRowEntries) {
            this.maxColumnsPerRow = this.columnsCount;
            return;
        }
        this.oddColumnsEntries = false;
        if (this.columnsCount % 2 != 0) {
            this.oddColumnsEntries = true;
        }
        if ((n4 = this.maxChars / (n5 = this.oddColumnsEntries ? (1 + this.columnsCount) / 2 : (2 + this.columnsCount) / 2)) >= this.minCharsPerFieldEntry) {
            this.multiRowEntriesCount = 2;
            this.equalCharacters = n4;
            this.maxColumnsPerRow = n5;
            this.itemColumnCharacters = this.maxChars - this.equalCharacters * (n5 - 2);
            Log.v((String)"PlainPrint", (String)("equalCharacters " + this.equalCharacters + " maxColumnsPerRow " + this.maxColumnsPerRow + " itemColumnCharacters " + this.itemColumnCharacters));
            return;
        }
        this.multiRowEntriesCount = 3;
        int n6 = this.columnsCount;
        if (this.columnsCount % 3 == 0) {
            n6 = 3 + this.columnsCount;
        } else if (this.columnsCount % 3 == 1) {
            n6 = 2 + this.columnsCount;
        }
        if (this.columnsCount % 3 == 2) {
            n6 = 1 + this.columnsCount;
        }
        this.maxColumnsPerRow = n6 / 3;
        this.equalCharacters = this.maxChars / this.maxColumnsPerRow;
        this.itemColumnCharacters = this.maxChars - this.equalCharacters * (-2 + this.maxColumnsPerRow);
    }

    public void setLastColRightAlign() {
        this.lastColRightAligned = true;
    }

    public void startAddingContent4printFields() {
        this.content = "";
    }
}

