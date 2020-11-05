/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 */
package com.sun.activation.registries;

public class MailcapTokenizer {
    public static final int EOI_TOKEN = 5;
    public static final int EQUALS_TOKEN = 61;
    public static final int SEMICOLON_TOKEN = 59;
    public static final int SLASH_TOKEN = 47;
    public static final int START_TOKEN = 1;
    public static final int STRING_TOKEN = 2;
    public static final int UNKNOWN_TOKEN;
    private char autoquoteChar;
    private int currentToken;
    private String currentTokenValue;
    private String data;
    private int dataIndex;
    private int dataLength;
    private boolean isAutoquoting;

    public MailcapTokenizer(String string2) {
        this.data = string2;
        this.dataIndex = 0;
        this.dataLength = string2.length();
        this.currentToken = 1;
        this.currentTokenValue = "";
        this.isAutoquoting = false;
        this.autoquoteChar = (char)59;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String fixEscapeSequences(String string2) {
        int n = string2.length();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity(n);
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c != '\\') {
                stringBuffer.append(c);
            } else if (n2 < n - 1) {
                stringBuffer.append(string2.charAt(n2 + 1));
                ++n2;
            } else {
                stringBuffer.append(c);
            }
            ++n2;
        }
        return stringBuffer.toString();
    }

    private static boolean isControlChar(char c) {
        return Character.isISOControl((char)c);
    }

    private static boolean isSpecialChar(char c) {
        switch (c) {
            default: {
                return false;
            }
            case '\"': 
            case '(': 
            case ')': 
            case ',': 
            case '/': 
            case ':': 
            case ';': 
            case '<': 
            case '=': 
            case '>': 
            case '?': 
            case '@': 
            case '[': 
            case '\\': 
            case ']': 
        }
        return true;
    }

    private static boolean isStringTokenChar(char c) {
        return !MailcapTokenizer.isSpecialChar(c) && !MailcapTokenizer.isControlChar(c) && !MailcapTokenizer.isWhiteSpaceChar(c);
    }

    private static boolean isWhiteSpaceChar(char c) {
        return Character.isWhitespace((char)c);
    }

    public static String nameForToken(int n) {
        switch (n) {
            default: {
                return "really unknown";
            }
            case 0: {
                return "unknown";
            }
            case 1: {
                return "start";
            }
            case 2: {
                return "string";
            }
            case 5: {
                return "EOI";
            }
            case 47: {
                return "'/'";
            }
            case 59: {
                return "';'";
            }
            case 61: 
        }
        return "'='";
    }

    private void processAutoquoteToken() {
        int n = this.dataIndex;
        boolean bl = false;
        do {
            if (this.dataIndex >= this.dataLength || bl) {
                this.currentToken = 2;
                this.currentTokenValue = MailcapTokenizer.fixEscapeSequences(this.data.substring(n, this.dataIndex));
                return;
            }
            if (this.data.charAt(this.dataIndex) != this.autoquoteChar) {
                this.dataIndex = 1 + this.dataIndex;
                continue;
            }
            bl = true;
        } while (true);
    }

    private void processStringToken() {
        int n = this.dataIndex;
        do {
            if (this.dataIndex >= this.dataLength || !MailcapTokenizer.isStringTokenChar(this.data.charAt(this.dataIndex))) {
                this.currentToken = 2;
                this.currentTokenValue = this.data.substring(n, this.dataIndex);
                return;
            }
            this.dataIndex = 1 + this.dataIndex;
        } while (true);
    }

    public int getCurrentToken() {
        return this.currentToken;
    }

    public String getCurrentTokenValue() {
        return this.currentTokenValue;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int nextToken() {
        if (this.dataIndex < this.dataLength) {
            block7 : {
                char c;
                block8 : {
                    do {
                        if (this.dataIndex >= this.dataLength || !MailcapTokenizer.isWhiteSpaceChar(this.data.charAt(this.dataIndex))) {
                            if (this.dataIndex >= this.dataLength) break block7;
                            c = this.data.charAt(this.dataIndex);
                            if (this.isAutoquoting) {
                                if (c != ';' && c != '=') break;
                                this.currentToken = c;
                                this.currentTokenValue = new Character(c).toString();
                                this.dataIndex = 1 + this.dataIndex;
                                do {
                                    return this.currentToken;
                                    break;
                                } while (true);
                            }
                            break block8;
                        }
                        this.dataIndex = 1 + this.dataIndex;
                    } while (true);
                    this.processAutoquoteToken();
                    return this.currentToken;
                }
                if (MailcapTokenizer.isStringTokenChar(c)) {
                    this.processStringToken();
                    return this.currentToken;
                }
                if (c == '/' || c == ';' || c == '=') {
                    this.currentToken = c;
                    this.currentTokenValue = new Character(c).toString();
                    this.dataIndex = 1 + this.dataIndex;
                    return this.currentToken;
                }
                this.currentToken = 0;
                this.currentTokenValue = new Character(c).toString();
                this.dataIndex = 1 + this.dataIndex;
                return this.currentToken;
            }
            this.currentToken = 5;
            this.currentTokenValue = null;
            return this.currentToken;
        }
        this.currentToken = 5;
        this.currentTokenValue = null;
        return this.currentToken;
    }

    public void setIsAutoquoting(boolean bl) {
        this.isAutoquoting = bl;
    }
}

