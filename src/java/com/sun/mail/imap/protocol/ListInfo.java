/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.BASE64MailboxDecoder;
import com.sun.mail.imap.protocol.IMAPResponse;
import java.util.Vector;

public class ListInfo {
    public static final int CHANGED = 1;
    public static final int INDETERMINATE = 3;
    public static final int UNCHANGED = 2;
    public String[] attrs;
    public boolean canOpen;
    public int changeState;
    public boolean hasInferiors;
    public String name;
    public char separator;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public ListInfo(IMAPResponse var1_1) throws ParsingException {
        block10 : {
            super();
            this.name = null;
            this.separator = (char)47;
            this.hasInferiors = true;
            this.canOpen = true;
            this.changeState = 3;
            var2_2 = var1_1.readSimpleList();
            var3_3 = new Vector();
            if (var2_2 == null) ** GOTO lbl13
            var5_4 = 0;
            do {
                block11 : {
                    if (var5_4 < var2_2.length) break block11;
lbl13: // 2 sources:
                    this.attrs = new String[var3_3.size()];
                    var3_3.copyInto((Object[])this.attrs);
                    var1_1.skipSpaces();
                    if (var1_1.readByte() != 34) break;
                    this.separator = var4_5 = (char)var1_1.readByte();
                    if (var4_5 == '\\') {
                        this.separator = (char)var1_1.readByte();
                    }
                    var1_1.skip(1);
                    break block10;
                }
                if (var2_2[var5_4].equalsIgnoreCase("\\Marked")) {
                    this.changeState = 1;
                } else if (var2_2[var5_4].equalsIgnoreCase("\\Unmarked")) {
                    this.changeState = 2;
                } else if (var2_2[var5_4].equalsIgnoreCase("\\Noselect")) {
                    this.canOpen = false;
                } else if (var2_2[var5_4].equalsIgnoreCase("\\Noinferiors")) {
                    this.hasInferiors = false;
                }
                var3_3.addElement((Object)var2_2[var5_4]);
                ++var5_4;
            } while (true);
            var1_1.skip(2);
        }
        var1_1.skipSpaces();
        this.name = var1_1.readAtomString();
        this.name = BASE64MailboxDecoder.decode(this.name);
    }
}

