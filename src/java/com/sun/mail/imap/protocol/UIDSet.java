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

public class UIDSet {
    public long end;
    public long start;

    public UIDSet() {
    }

    public UIDSet(long l, long l2) {
        this.start = l;
        this.end = l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static UIDSet[] createUIDSets(long[] arrl) {
        Vector vector = new Vector();
        int n = 0;
        do {
            int n2;
            if (n >= arrl.length) {
                Object[] arrobject = new UIDSet[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            UIDSet uIDSet = new UIDSet();
            uIDSet.start = arrl[n];
            for (n2 = n + 1; n2 < arrl.length && arrl[n2] == 1L + arrl[n2 - 1]; ++n2) {
            }
            uIDSet.end = arrl[n2 - 1];
            vector.addElement((Object)uIDSet);
            n = 1 + (n2 - 1);
        } while (true);
    }

    public static long size(UIDSet[] arruIDSet) {
        long l = 0L;
        if (arruIDSet == null) {
            return 0L;
        }
        int n = 0;
        while (n < arruIDSet.length) {
            l += arruIDSet[n].size();
            ++n;
        }
        return l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String toString(UIDSet[] arruIDSet) {
        if (arruIDSet == null || arruIDSet.length == 0) {
            return null;
        }
        int n = 0;
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = arruIDSet.length;
        do {
            long l;
            long l2;
            if ((l = arruIDSet[n].end) > (l2 = arruIDSet[n].start)) {
                stringBuffer.append(l2).append(':').append(l);
            } else {
                stringBuffer.append(l2);
            }
            if (++n >= n2) {
                return stringBuffer.toString();
            }
            stringBuffer.append(',');
        } while (true);
    }

    public long size() {
        return 1L + (this.end - this.start);
    }
}

