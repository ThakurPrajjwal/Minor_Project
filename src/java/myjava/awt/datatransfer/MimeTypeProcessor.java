/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Cloneable
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Enumeration
 *  java.util.Hashtable
 */
package myjava.awt.datatransfer;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

final class MimeTypeProcessor {
    private static MimeTypeProcessor instance;

    private MimeTypeProcessor() {
    }

    static String assemble(MimeType mimeType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mimeType.getFullType());
        Enumeration enumeration = mimeType.parameters.keys();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            String string3 = (String)mimeType.parameters.get((Object)string2);
            stringBuilder.append("; ");
            stringBuilder.append(string2);
            stringBuilder.append("=\"");
            stringBuilder.append(string3);
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    private static int getNextMeaningfulIndex(String string2, int n) {
        while (n < string2.length() && !MimeTypeProcessor.isMeaningfulChar(string2.charAt(n))) {
            ++n;
        }
        return n;
    }

    private static boolean isMeaningfulChar(char c) {
        return c >= '!' && c <= '~';
    }

    private static boolean isTSpecialChar(char c) {
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '<' || c == '>' || c == '@' || c == ',' || c == ';' || c == ':' || c == '\\' || c == '\"' || c == '/' || c == '?' || c == '=';
    }

    static MimeType parse(String string2) {
        if (instance == null) {
            instance = new MimeTypeProcessor();
        }
        MimeType mimeType = new MimeType();
        if (string2 != null) {
            StringPosition stringPosition = new StringPosition();
            MimeTypeProcessor.retrieveType(string2, mimeType, stringPosition);
            MimeTypeProcessor.retrieveParams(string2, mimeType, stringPosition);
        }
        return mimeType;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void retrieveParam(String string2, MimeType mimeType, StringPosition stringPosition) {
        String string3 = MimeTypeProcessor.retrieveToken(string2, stringPosition).toLowerCase();
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length() || string2.charAt(stringPosition.i) != '=') {
            throw new IllegalArgumentException();
        }
        stringPosition.i = 1 + stringPosition.i;
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length()) {
            throw new IllegalArgumentException();
        }
        String string4 = string2.charAt(stringPosition.i) == '\"' ? MimeTypeProcessor.retrieveQuoted(string2, stringPosition) : MimeTypeProcessor.retrieveToken(string2, stringPosition);
        mimeType.parameters.put((Object)string3, (Object)string4);
    }

    private static void retrieveParams(String string2, MimeType mimeType, StringPosition stringPosition) {
        MimeType.access$3(mimeType, new Hashtable());
        MimeType.access$4(mimeType, new Hashtable());
        do {
            stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
            if (stringPosition.i >= string2.length()) {
                return;
            }
            if (string2.charAt(stringPosition.i) != ';') {
                throw new IllegalArgumentException();
            }
            stringPosition.i = 1 + stringPosition.i;
            MimeTypeProcessor.retrieveParam(string2, mimeType, stringPosition);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String retrieveQuoted(String string2, StringPosition stringPosition) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = true;
        stringPosition.i = 1 + stringPosition.i;
        do {
            if (string2.charAt(stringPosition.i) == '\"' && bl) {
                stringPosition.i = 1 + stringPosition.i;
                return stringBuilder.toString();
            }
            int n = stringPosition.i;
            stringPosition.i = n + 1;
            char c = string2.charAt(n);
            if (!bl) {
                bl = true;
            } else if (c == '\\') {
                bl = false;
            }
            if (!bl) continue;
            stringBuilder.append(c);
        } while (stringPosition.i != string2.length());
        throw new IllegalArgumentException();
    }

    private static String retrieveToken(String string2, StringPosition stringPosition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length() || MimeTypeProcessor.isTSpecialChar(string2.charAt(stringPosition.i))) {
            throw new IllegalArgumentException();
        }
        do {
            int n = stringPosition.i;
            stringPosition.i = n + 1;
            stringBuilder.append(string2.charAt(n));
        } while (stringPosition.i < string2.length() && MimeTypeProcessor.isMeaningfulChar(string2.charAt(stringPosition.i)) && !MimeTypeProcessor.isTSpecialChar(string2.charAt(stringPosition.i)));
        return stringBuilder.toString();
    }

    private static void retrieveType(String string2, MimeType mimeType, StringPosition stringPosition) {
        MimeType.access$1(mimeType, MimeTypeProcessor.retrieveToken(string2, stringPosition).toLowerCase());
        stringPosition.i = MimeTypeProcessor.getNextMeaningfulIndex(string2, stringPosition.i);
        if (stringPosition.i >= string2.length() || string2.charAt(stringPosition.i) != '/') {
            throw new IllegalArgumentException();
        }
        stringPosition.i = 1 + stringPosition.i;
        MimeType.access$2(mimeType, MimeTypeProcessor.retrieveToken(string2, stringPosition).toLowerCase());
    }

    static final class MimeType
    implements Cloneable,
    Serializable {
        private static final long serialVersionUID = -6693571907475992044L;
        private Hashtable<String, String> parameters;
        private String primaryType;
        private String subType;
        private Hashtable<String, Object> systemParameters;

        MimeType() {
            this.primaryType = null;
            this.subType = null;
            this.parameters = null;
            this.systemParameters = null;
        }

        MimeType(String string2, String string3) {
            this.primaryType = string2;
            this.subType = string3;
            this.parameters = new Hashtable();
            this.systemParameters = new Hashtable();
        }

        static /* synthetic */ void access$1(MimeType mimeType, String string2) {
            mimeType.primaryType = string2;
        }

        static /* synthetic */ void access$2(MimeType mimeType, String string2) {
            mimeType.subType = string2;
        }

        static /* synthetic */ void access$3(MimeType mimeType, Hashtable hashtable) {
            mimeType.parameters = hashtable;
        }

        static /* synthetic */ void access$4(MimeType mimeType, Hashtable hashtable) {
            mimeType.systemParameters = hashtable;
        }

        /*
         * Enabled aggressive block sorting
         */
        void addParameter(String string2, String string3) {
            block5 : {
                block4 : {
                    if (string3 == null) break block4;
                    if (string3.charAt(0) == '\"' && string3.charAt(-1 + string3.length()) == '\"') {
                        string3 = string3.substring(1, -2 + string3.length());
                    }
                    if (string3.length() != 0) break block5;
                }
                return;
            }
            this.parameters.put((Object)string2, (Object)string3);
        }

        void addSystemParameter(String string2, Object object) {
            this.systemParameters.put((Object)string2, object);
        }

        public Object clone() {
            MimeType mimeType = new MimeType(this.primaryType, this.subType);
            mimeType.parameters = (Hashtable)this.parameters.clone();
            mimeType.systemParameters = (Hashtable)this.systemParameters.clone();
            return mimeType;
        }

        boolean equals(MimeType mimeType) {
            if (mimeType == null) {
                return false;
            }
            return this.getFullType().equals((Object)mimeType.getFullType());
        }

        String getFullType() {
            return String.valueOf((Object)this.primaryType) + "/" + this.subType;
        }

        String getParameter(String string2) {
            return (String)this.parameters.get((Object)string2);
        }

        String getPrimaryType() {
            return this.primaryType;
        }

        String getSubType() {
            return this.subType;
        }

        Object getSystemParameter(String string2) {
            return this.systemParameters.get((Object)string2);
        }

        void removeParameter(String string2) {
            this.parameters.remove((Object)string2);
        }
    }

    private static final class StringPosition {
        int i = 0;

        private StringPosition() {
        }
    }

}

