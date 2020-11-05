/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Vector
 *  javax.mail.Message
 */
package com.sun.mail.imap;

import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.UIDSet;
import java.util.Vector;
import javax.mail.Message;

public final class Utility {
    private Utility() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static MessageSet[] toMessageSet(Message[] var0, Condition var1_1) {
        var2_2 = new Vector(1);
        var3_3 = 0;
        block0 : do {
            block13 : {
                block12 : {
                    block11 : {
                        if (var3_3 < var0.length) break block11;
                        if (var2_2.isEmpty()) {
                            return null;
                        }
                        break block12;
                    }
                    var4_4 = (IMAPMessage)var0[var3_3];
                    if (!var4_4.isExpunged()) {
                        var5_5 = var4_4.getSequenceNumber();
                        if (var1_1 == null || var1_1.test(var4_4)) {
                            var6_6 = new MessageSet();
                            var6_6.start = var5_5;
                            ++var3_3;
                            break;
                        }
                    }
                    break block13;
                }
                var9_9 = new MessageSet[var2_2.size()];
                var2_2.copyInto(var9_9);
                return var9_9;
            }
lbl23: // 2 sources:
            do {
                ++var3_3;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        while (var3_3 < var0.length) {
            block15 : {
                block14 : {
                    var7_7 = (IMAPMessage)var0[var3_3];
                    if (var7_7.isExpunged()) break block14;
                    var8_8 = var7_7.getSequenceNumber();
                    if (var1_1 != null && !var1_1.test(var7_7)) break block14;
                    if (var8_8 != var5_5 + 1) break block15;
                    var5_5 = var8_8;
                }
                ++var3_3;
                continue;
            }
            --var3_3;
            break;
        }
        var6_6.end = var5_5;
        var2_2.addElement((Object)var6_6);
        ** while (true)
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static UIDSet[] toUIDSet(Message[] var0) {
        var1_1 = new Vector(1);
        var2_2 = 0;
        block0 : do {
            block11 : {
                block10 : {
                    block9 : {
                        if (var2_2 < var0.length) break block9;
                        if (var1_1.isEmpty()) {
                            return null;
                        }
                        break block10;
                    }
                    var3_3 = (IMAPMessage)var0[var2_2];
                    if (!var3_3.isExpunged()) {
                        var4_4 = var3_3.getUID();
                        var6_5 = new UIDSet();
                        var6_5.start = var4_4;
                        ++var2_2;
                        break;
                    }
                    break block11;
                }
                var10_8 = new UIDSet[var1_1.size()];
                var1_1.copyInto(var10_8);
                return var10_8;
            }
lbl22: // 2 sources:
            do {
                ++var2_2;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        while (var2_2 < var0.length) {
            block13 : {
                block12 : {
                    var7_6 = (IMAPMessage)var0[var2_2];
                    if (var7_6.isExpunged()) break block12;
                    var8_7 = var7_6.getUID();
                    if (var8_7 != 1L + var4_4) break block13;
                    var4_4 = var8_7;
                }
                ++var2_2;
                continue;
            }
            --var2_2;
            break;
        }
        var6_5.end = var4_4;
        var1_1.addElement((Object)var6_5);
        ** while (true)
    }

    public static interface Condition {
        public boolean test(IMAPMessage var1);
    }

}

