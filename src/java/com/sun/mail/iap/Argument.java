/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Vector
 */
package com.sun.mail.iap;

import com.sun.mail.iap.AString;
import com.sun.mail.iap.Atom;
import com.sun.mail.iap.Literal;
import com.sun.mail.iap.LiteralException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.util.ASCIIUtility;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class Argument {
    protected Vector items = new Vector(1);

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void astring(byte[] arrby, Protocol protocol) throws IOException, ProtocolException {
        DataOutputStream dataOutputStream;
        boolean bl;
        block10 : {
            int n;
            block9 : {
                dataOutputStream = (DataOutputStream)protocol.getOutputStream();
                n = arrby.length;
                if (n > 1024) {
                    this.literal(arrby, protocol);
                    return;
                }
                bl = n == 0;
                boolean bl2 = false;
                int n2 = 0;
                do {
                    if (n2 >= n) {
                        if (bl) {
                            dataOutputStream.write(34);
                        }
                        if (!bl2) break;
                        break block9;
                    }
                    byte by = arrby[n2];
                    if (by == 0 || by == 13 || by == 10 || (by & 255) > 127) {
                        this.literal(arrby, protocol);
                        return;
                    }
                    if (by == 42 || by == 37 || by == 40 || by == 41 || by == 123 || by == 34 || by == 92 || (by & 255) <= 32) {
                        bl = true;
                        if (by == 34 || by == 92) {
                            bl2 = true;
                        }
                    }
                    ++n2;
                } while (true);
                dataOutputStream.write(arrby);
                break block10;
            }
            for (int i = 0; i < n; ++i) {
                byte by = arrby[i];
                if (by == 34 || by == 92) {
                    dataOutputStream.write(92);
                }
                dataOutputStream.write((int)by);
            }
        }
        if (!bl) return;
        dataOutputStream.write(34);
    }

    private void literal(Literal literal, Protocol protocol) throws IOException, ProtocolException {
        literal.writeTo(this.startLiteral(protocol, literal.size()));
    }

    private void literal(ByteArrayOutputStream byteArrayOutputStream, Protocol protocol) throws IOException, ProtocolException {
        byteArrayOutputStream.writeTo(this.startLiteral(protocol, byteArrayOutputStream.size()));
    }

    private void literal(byte[] arrby, Protocol protocol) throws IOException, ProtocolException {
        this.startLiteral(protocol, arrby.length).write(arrby);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private OutputStream startLiteral(Protocol protocol, int n) throws IOException, ProtocolException {
        Response response;
        DataOutputStream dataOutputStream = (DataOutputStream)protocol.getOutputStream();
        boolean bl = protocol.supportsNonSyncLiterals();
        dataOutputStream.write(123);
        dataOutputStream.writeBytes(Integer.toString((int)n));
        if (bl) {
            dataOutputStream.writeBytes("+}\r\n");
        } else {
            dataOutputStream.writeBytes("}\r\n");
        }
        dataOutputStream.flush();
        if (bl) return dataOutputStream;
        do {
            if (!(response = protocol.readResponse()).isContinuation()) continue;
            return dataOutputStream;
        } while (!response.isTagged());
        throw new LiteralException(response);
    }

    public void append(Argument argument) {
        this.items.ensureCapacity(this.items.size() + argument.items.size());
        int n = 0;
        while (n < argument.items.size()) {
            this.items.addElement(argument.items.elementAt(n));
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(Protocol protocol) throws IOException, ProtocolException {
        int n = this.items != null ? this.items.size() : 0;
        DataOutputStream dataOutputStream = (DataOutputStream)protocol.getOutputStream();
        int n2 = 0;
        while (n2 < n) {
            Object object;
            if (n2 > 0) {
                dataOutputStream.write(32);
            }
            if ((object = this.items.elementAt(n2)) instanceof Atom) {
                dataOutputStream.writeBytes(((Atom)object).string);
            } else if (object instanceof Number) {
                dataOutputStream.writeBytes(((Number)object).toString());
            } else if (object instanceof AString) {
                this.astring(((AString)object).bytes, protocol);
            } else if (object instanceof byte[]) {
                this.literal((byte[])object, protocol);
            } else if (object instanceof ByteArrayOutputStream) {
                this.literal((ByteArrayOutputStream)object, protocol);
            } else if (object instanceof Literal) {
                this.literal((Literal)object, protocol);
            } else if (object instanceof Argument) {
                dataOutputStream.write(40);
                ((Argument)object).write(protocol);
                dataOutputStream.write(41);
            }
            ++n2;
        }
        return;
    }

    public void writeArgument(Argument argument) {
        this.items.addElement((Object)argument);
    }

    public void writeAtom(String string2) {
        this.items.addElement((Object)new Atom(string2));
    }

    public void writeBytes(Literal literal) {
        this.items.addElement((Object)literal);
    }

    public void writeBytes(ByteArrayOutputStream byteArrayOutputStream) {
        this.items.addElement((Object)byteArrayOutputStream);
    }

    public void writeBytes(byte[] arrby) {
        this.items.addElement((Object)arrby);
    }

    public void writeNumber(int n) {
        this.items.addElement((Object)new Integer(n));
    }

    public void writeNumber(long l) {
        this.items.addElement((Object)new Long(l));
    }

    public void writeString(String string2) {
        this.items.addElement((Object)new AString(ASCIIUtility.getBytes(string2)));
    }

    public void writeString(String string2, String string3) throws UnsupportedEncodingException {
        if (string3 == null) {
            this.writeString(string2);
            return;
        }
        this.items.addElement((Object)new AString(string2.getBytes(string3)));
    }
}

