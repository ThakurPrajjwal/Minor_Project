/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.CloneNotSupportedException
 *  java.lang.Cloneable
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 *  java.util.Vector
 */
package com.sun.mail.imap;

import java.util.Vector;

public class Rights
implements Cloneable {
    private boolean[] rights = new boolean[128];

    public Rights() {
    }

    public Rights(Right right) {
        this.rights[right.right] = true;
    }

    public Rights(Rights rights) {
        System.arraycopy((Object)rights.rights, (int)0, (Object)this.rights, (int)0, (int)this.rights.length);
    }

    public Rights(String string2) {
        int n = 0;
        while (n < string2.length()) {
            this.add(Right.getInstance(string2.charAt(n)));
            ++n;
        }
        return;
    }

    public void add(Right right) {
        this.rights[right.right] = true;
    }

    public void add(Rights rights) {
        int n = 0;
        while (n < rights.rights.length) {
            if (rights.rights[n]) {
                this.rights[n] = true;
            }
            ++n;
        }
        return;
    }

    public Object clone() {
        Rights rights = null;
        try {
            rights = (Rights)super.clone();
            rights.rights = new boolean[128];
            System.arraycopy((Object)this.rights, (int)0, (Object)rights.rights, (int)0, (int)this.rights.length);
            return rights;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return rights;
        }
    }

    public boolean contains(Right right) {
        return this.rights[right.right];
    }

    public boolean contains(Rights rights) {
        int n = 0;
        while (n < rights.rights.length) {
            if (rights.rights[n] && !this.rights[n]) {
                return false;
            }
            ++n;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (object instanceof Rights) {
            Rights rights = (Rights)object;
            int n = 0;
            do {
                if (n >= rights.rights.length) {
                    return true;
                }
                if (rights.rights[n] != this.rights[n]) break;
                ++n;
            } while (true);
        }
        return false;
    }

    public Right[] getRights() {
        Vector vector = new Vector();
        int n = 0;
        do {
            if (n >= this.rights.length) {
                Object[] arrobject = new Right[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            if (this.rights[n]) {
                vector.addElement((Object)Right.getInstance((char)n));
            }
            ++n;
        } while (true);
    }

    public int hashCode() {
        int n = 0;
        int n2 = 0;
        while (n2 < this.rights.length) {
            if (this.rights[n2]) {
                ++n;
            }
            ++n2;
        }
        return n;
    }

    public void remove(Right right) {
        this.rights[right.right] = false;
    }

    public void remove(Rights rights) {
        int n = 0;
        while (n < rights.rights.length) {
            if (rights.rights[n]) {
                this.rights[n] = false;
            }
            ++n;
        }
        return;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        while (n < this.rights.length) {
            if (this.rights[n]) {
                stringBuffer.append((char)n);
            }
            ++n;
        }
        return stringBuffer.toString();
    }

    public static final class Right {
        public static final Right ADMINISTER;
        public static final Right CREATE;
        public static final Right DELETE;
        public static final Right INSERT;
        public static final Right KEEP_SEEN;
        public static final Right LOOKUP;
        public static final Right POST;
        public static final Right READ;
        public static final Right WRITE;
        private static Right[] cache;
        char right;

        static {
            cache = new Right[128];
            LOOKUP = Right.getInstance('l');
            READ = Right.getInstance('r');
            KEEP_SEEN = Right.getInstance('s');
            WRITE = Right.getInstance('w');
            INSERT = Right.getInstance('i');
            POST = Right.getInstance('p');
            CREATE = Right.getInstance('c');
            DELETE = Right.getInstance('d');
            ADMINISTER = Right.getInstance('a');
        }

        private Right(char c) {
            if (c >= '') {
                throw new IllegalArgumentException("Right must be ASCII");
            }
            this.right = c;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static Right getInstance(char c) {
            Class<Right> class_ = Right.class;
            synchronized (Right.class) {
                if (c >= '') {
                    throw new IllegalArgumentException("Right must be ASCII");
                }
                if (cache[c] != null) return cache[c];
                Right.cache[c] = new Right(c);
                return cache[c];
            }
        }

        public String toString() {
            return String.valueOf((char)this.right);
        }
    }

}

