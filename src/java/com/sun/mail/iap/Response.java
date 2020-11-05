/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.IOException
 *  java.lang.Character
 *  java.lang.Exception
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.ResponseInputStream;
import com.sun.mail.util.ASCIIUtility;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

public class Response {
    public static final int BAD = 12;
    public static final int BYE = 16;
    public static final int CONTINUATION = 1;
    public static final int NO = 8;
    public static final int OK = 4;
    public static final int SYNTHETIC = 32;
    public static final int TAGGED = 2;
    public static final int TAG_MASK = 3;
    public static final int TYPE_MASK = 28;
    public static final int UNTAGGED = 3;
    private static final int increment = 100;
    protected byte[] buffer = null;
    protected int index;
    protected int pindex;
    protected int size;
    protected String tag = null;
    protected int type = 0;

    public Response(Protocol protocol) throws IOException, ProtocolException {
        ByteArray byteArray = protocol.getResponseBuffer();
        ByteArray byteArray2 = protocol.getInputStream().readResponse(byteArray);
        this.buffer = byteArray2.getBytes();
        this.size = -2 + byteArray2.getCount();
        this.parse();
    }

    public Response(Response response) {
        this.index = response.index;
        this.size = response.size;
        this.buffer = response.buffer;
        this.type = response.type;
        this.tag = response.tag;
    }

    public Response(String string2) {
        this.buffer = ASCIIUtility.getBytes(string2);
        this.size = this.buffer.length;
        this.parse();
    }

    public static Response byeResponse(Exception exception) {
        Response response = new Response(("* BYE JavaMail Exception: " + exception.toString()).replace('\r', ' ').replace('\n', ' '));
        response.type = 32 | response.type;
        return response;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parse() {
        this.index = 0;
        if (this.buffer[this.index] == 43) {
            this.type = 1 | this.type;
            this.index = 1 + this.index;
            return;
        }
        if (this.buffer[this.index] == 42) {
            this.type = 3 | this.type;
            this.index = 1 + this.index;
        } else {
            this.type = 2 | this.type;
            this.tag = this.readAtom();
        }
        int n = this.index;
        String string2 = this.readAtom();
        if (string2 == null) {
            string2 = "";
        }
        if (string2.equalsIgnoreCase("OK")) {
            this.type = 4 | this.type;
        } else if (string2.equalsIgnoreCase("NO")) {
            this.type = 8 | this.type;
        } else if (string2.equalsIgnoreCase("BAD")) {
            this.type = 12 | this.type;
        } else if (string2.equalsIgnoreCase("BYE")) {
            this.type = 16 | this.type;
        } else {
            this.index = n;
        }
        this.pindex = this.index;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Object parseString(boolean bl, boolean bl2) {
        int n;
        this.skipSpaces();
        byte by = this.buffer[this.index];
        if (by != 34) {
            if (by != 123) {
                String string2;
                if (bl) {
                    int n2 = this.index;
                    string2 = this.readAtom();
                    if (bl2) return string2;
                    return new ByteArray(this.buffer, n2, this.index);
                }
                if (by != 78) {
                    string2 = null;
                    if (by != 110) return string2;
                }
                this.index = 3 + this.index;
                return null;
            }
        } else {
            int n3 = this.index = 1 + this.index;
            int n2 = this.index;
            do {
                byte by2;
                if ((by2 = this.buffer[this.index]) == 34) {
                    this.index = 1 + this.index;
                    if (!bl2) return new ByteArray(this.buffer, n3, n2 - n3);
                    return ASCIIUtility.toString(this.buffer, n3, n2);
                }
                if (by2 == 92) {
                    this.index = 1 + this.index;
                }
                if (this.index != n2) {
                    this.buffer[n2] = this.buffer[this.index];
                }
                ++n2;
                this.index = 1 + this.index;
            } while (true);
        }
        this.index = n = 1 + this.index;
        do {
            if (this.buffer[this.index] == 125) {
                int n3 = ASCIIUtility.parseInt(this.buffer, n, this.index);
                int n4 = 3 + this.index;
                this.index = n4 + n3;
                if (!bl2) return new ByteArray(this.buffer, n4, n3);
                return ASCIIUtility.toString(this.buffer, n4, n4 + n3);
            }
            this.index = 1 + this.index;
        } while (true);
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    public String getRest() {
        this.skipSpaces();
        return ASCIIUtility.toString(this.buffer, this.index, this.size);
    }

    public String getTag() {
        return this.tag;
    }

    public int getType() {
        return this.type;
    }

    public boolean isBAD() {
        return (28 & this.type) == 12;
    }

    public boolean isBYE() {
        return (28 & this.type) == 16;
    }

    public boolean isContinuation() {
        return (3 & this.type) == 1;
    }

    public boolean isNO() {
        return (28 & this.type) == 8;
    }

    public boolean isOK() {
        return (28 & this.type) == 4;
    }

    public boolean isSynthetic() {
        return (32 & this.type) == 32;
    }

    public boolean isTagged() {
        return (3 & this.type) == 2;
    }

    public boolean isUnTagged() {
        return (3 & this.type) == 3;
    }

    public byte peekByte() {
        if (this.index < this.size) {
            return this.buffer[this.index];
        }
        return 0;
    }

    public String readAtom() {
        return this.readAtom('\u0000');
    }

    public String readAtom(char c) {
        this.skipSpaces();
        if (this.index >= this.size) {
            return null;
        }
        int n = this.index;
        byte by;
        while (this.index < this.size && (by = this.buffer[this.index]) > 32 && by != 40 && by != 41 && by != 37 && by != 42 && by != 34 && by != 92 && by != 127 && (c == '\u0000' || by != c)) {
            this.index = 1 + this.index;
        }
        return ASCIIUtility.toString(this.buffer, n, this.index);
    }

    public String readAtomString() {
        return (String)this.parseString(true, true);
    }

    public byte readByte() {
        if (this.index < this.size) {
            byte[] arrby = this.buffer;
            int n = this.index;
            this.index = n + 1;
            return arrby[n];
        }
        return 0;
    }

    public ByteArray readByteArray() {
        if (this.isContinuation()) {
            this.skipSpaces();
            return new ByteArray(this.buffer, this.index, this.size - this.index);
        }
        return (ByteArray)this.parseString(false, false);
    }

    public ByteArrayInputStream readBytes() {
        ByteArray byteArray = this.readByteArray();
        if (byteArray != null) {
            return byteArray.toByteArrayInputStream();
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long readLong() {
        this.skipSpaces();
        int n = this.index;
        do {
            if (this.index >= this.size || !Character.isDigit((char)((char)this.buffer[this.index]))) {
                if (this.index <= n) return -1L;
                return ASCIIUtility.parseLong(this.buffer, n, this.index);
            }
            this.index = 1 + this.index;
        } while (true);
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return -1L;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int readNumber() {
        this.skipSpaces();
        int n = this.index;
        do {
            if (this.index >= this.size || !Character.isDigit((char)((char)this.buffer[this.index]))) {
                if (this.index <= n) return -1;
                return ASCIIUtility.parseInt(this.buffer, n, this.index);
            }
            this.index = 1 + this.index;
        } while (true);
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return -1;
    }

    public String readString() {
        return (String)this.parseString(false, true);
    }

    public String readString(char c) {
        this.skipSpaces();
        if (this.index >= this.size) {
            return null;
        }
        int n = this.index;
        while (this.index < this.size && this.buffer[this.index] != c) {
            this.index = 1 + this.index;
        }
        return ASCIIUtility.toString(this.buffer, n, this.index);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String[] readStringList() {
        Vector vector;
        int n;
        block4 : {
            block3 : {
                byte[] arrby;
                int n2;
                this.skipSpaces();
                if (this.buffer[this.index] != 40) break block3;
                this.index = 1 + this.index;
                vector = new Vector();
                do {
                    vector.addElement((Object)this.readString());
                    arrby = this.buffer;
                    n2 = this.index;
                    this.index = n2 + 1;
                } while (arrby[n2] != 41);
                n = vector.size();
                if (n > 0) break block4;
            }
            return null;
        }
        Object[] arrobject = new String[n];
        vector.copyInto(arrobject);
        return arrobject;
    }

    public void reset() {
        this.index = this.pindex;
    }

    public void skip(int n) {
        this.index = n + this.index;
    }

    public void skipSpaces() {
        while (this.index < this.size && this.buffer[this.index] == 32) {
            this.index = 1 + this.index;
        }
        return;
    }

    public void skipToken() {
        while (this.index < this.size && this.buffer[this.index] != 32) {
            this.index = 1 + this.index;
        }
        return;
    }

    public String toString() {
        return ASCIIUtility.toString(this.buffer, 0, this.size);
    }
}

