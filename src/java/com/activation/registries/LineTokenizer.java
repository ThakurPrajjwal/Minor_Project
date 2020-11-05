/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.NoSuchElementException
 *  java.util.Vector
 */
package com.sun.activation.registries;

import java.util.NoSuchElementException;
import java.util.Vector;

class LineTokenizer {
    private static final String singles = "=";
    private int currentPosition = 0;
    private int maxPosition;
    private Vector stack = new Vector();
    private String str;

    public LineTokenizer(String string2) {
        this.str = string2;
        this.maxPosition = string2.length();
    }

    private void skipWhiteSpace() {
        while (this.currentPosition < this.maxPosition && Character.isWhitespace((char)this.str.charAt(this.currentPosition))) {
            this.currentPosition = 1 + this.currentPosition;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasMoreTokens() {
        block3 : {
            block2 : {
                if (this.stack.size() > 0) break block2;
                this.skipWhiteSpace();
                if (this.currentPosition >= this.maxPosition) break block3;
            }
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String nextToken() {
        block11 : {
            var1_1 = this.stack.size();
            if (var1_1 > 0) {
                var13_2 = (String)this.stack.elementAt(var1_1 - 1);
                this.stack.removeElementAt(var1_1 - 1);
                return var13_2;
            }
            this.skipWhiteSpace();
            if (this.currentPosition >= this.maxPosition) {
                throw new NoSuchElementException();
            }
            var2_3 = this.currentPosition;
            var3_4 = this.str.charAt(var2_3);
            if (var3_4 == '\"') break block11;
            if ("=".indexOf((int)var3_4) >= 0) {
                this.currentPosition = 1 + this.currentPosition;
                return this.str.substring(var2_3, this.currentPosition);
            }
            ** GOTO lbl45
        }
        this.currentPosition = 1 + this.currentPosition;
        var4_5 = false;
        do {
            if (this.currentPosition >= this.maxPosition) {
                return this.str.substring(var2_3, this.currentPosition);
            }
            var5_6 = this.str;
            var6_7 = this.currentPosition;
            this.currentPosition = var6_7 + 1;
            var7_8 = var5_6.charAt(var6_7);
            if (var7_8 == '\\') {
                this.currentPosition = 1 + this.currentPosition;
                var4_5 = true;
                continue;
            }
            if (var7_8 == '\"') break;
        } while (true);
        if (!var4_5) {
            return this.str.substring(var2_3 + 1, -1 + this.currentPosition);
        }
        var8_9 = new StringBuffer();
        var9_10 = var2_3 + 1;
        do {
            if (var9_10 >= -1 + this.currentPosition) {
                return var8_9.toString();
            }
            var10_11 = this.str.charAt(var9_10);
            if (var10_11 != '\\') {
                var8_9.append(var10_11);
            }
            ++var9_10;
        } while (true);
lbl-1000: // 1 sources:
        {
            this.currentPosition = 1 + this.currentPosition;
lbl45: // 2 sources:
            if (this.currentPosition >= this.maxPosition) return this.str.substring(var2_3, this.currentPosition);
            if ("=".indexOf((int)this.str.charAt(this.currentPosition)) >= 0) return this.str.substring(var2_3, this.currentPosition);
            ** while (!Character.isWhitespace((char)this.str.charAt((int)this.currentPosition)))
        }
lbl48: // 1 sources:
        return this.str.substring(var2_3, this.currentPosition);
    }

    public void pushToken(String string2) {
        this.stack.addElement((Object)string2);
    }
}

