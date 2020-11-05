/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.Vector
 */
package com.sun.mail.imap.protocol;

import java.util.Vector;

public class MessageSet {
    public int end;
    public int start;

    public MessageSet() {
    }

    public MessageSet(int n, int n2) {
        this.start = n;
        this.end = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static MessageSet[] createMessageSets(int[] arrn) {
        Vector vector = new Vector();
        int n = 0;
        do {
            int n2;
            if (n >= arrn.length) {
                Object[] arrobject = new MessageSet[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            MessageSet messageSet = new MessageSet();
            messageSet.start = arrn[n];
            for (n2 = n + 1; n2 < arrn.length && arrn[n2] == 1 + arrn[n2 - 1]; ++n2) {
            }
            messageSet.end = arrn[n2 - 1];
            vector.addElement((Object)messageSet);
            n = 1 + (n2 - 1);
        } while (true);
    }

    public static int size(MessageSet[] arrmessageSet) {
        int n = 0;
        if (arrmessageSet == null) {
            return 0;
        }
        int n2 = 0;
        while (n2 < arrmessageSet.length) {
            n += arrmessageSet[n2].size();
            ++n2;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String toString(MessageSet[] arrmessageSet) {
        if (arrmessageSet == null || arrmessageSet.length == 0) {
            return null;
        }
        int n = 0;
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = arrmessageSet.length;
        do {
            int n3;
            int n4;
            if ((n3 = arrmessageSet[n].end) > (n4 = arrmessageSet[n].start)) {
                stringBuffer.append(n4).append(':').append(n3);
            } else {
                stringBuffer.append(n4);
            }
            if (++n >= n2) {
                return stringBuffer.toString();
            }
            stringBuffer.append(',');
        } while (true);
    }

    public int size() {
        return 1 + (this.end - this.start);
    }
}

