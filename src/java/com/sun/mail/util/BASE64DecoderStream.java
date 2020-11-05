/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.FilterInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.System
 */
package com.sun.mail.util;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BASE64DecoderStream
extends FilterInputStream {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] pem_convert_array = new byte[256];
    private byte[] buffer;
    private int bufsize;
    private boolean ignoreErrors;
    private int index;
    private byte[] input_buffer;
    private int input_len;
    private int input_pos;

    /*
     * Enabled aggressive block sorting
     */
    static {
        int n = 0;
        do {
            if (n >= 255) break;
            BASE64DecoderStream.pem_convert_array[n] = -1;
            ++n;
        } while (true);
        int n2 = 0;
        while (n2 < pem_array.length) {
            BASE64DecoderStream.pem_convert_array[BASE64DecoderStream.pem_array[n2]] = (byte)n2;
            ++n2;
        }
        return;
    }

    public BASE64DecoderStream(InputStream inputStream) {
        boolean bl;
        block4 : {
            String string2;
            super(inputStream);
            this.buffer = new byte[3];
            this.bufsize = 0;
            this.index = 0;
            this.input_buffer = new byte[8190];
            this.input_pos = 0;
            this.input_len = 0;
            this.ignoreErrors = false;
            try {
                string2 = System.getProperty((String)"mail.mime.base64.ignoreerrors");
                bl = false;
                if (string2 == null) break block4;
            }
            catch (SecurityException securityException) {
                return;
            }
            boolean bl2 = string2.equalsIgnoreCase("false");
            bl = false;
            if (bl2) break block4;
            bl = true;
        }
        this.ignoreErrors = bl;
    }

    public BASE64DecoderStream(InputStream inputStream, boolean bl) {
        super(inputStream);
        this.buffer = new byte[3];
        this.bufsize = 0;
        this.index = 0;
        this.input_buffer = new byte[8190];
        this.input_pos = 0;
        this.input_len = 0;
        this.ignoreErrors = false;
        this.ignoreErrors = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int decode(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        int n4;
        int n5;
        boolean bl;
        int n6 = n;
        block0 : do {
            if (n2 < 3) {
                return n - n6;
            }
            n3 = 0;
            n5 = 0;
            do {
                if (n3 >= 4) {
                    arrby[n + 2] = (byte)(n5 & 255);
                    int n7 = n5 >> 8;
                    arrby[n + 1] = (byte)(n7 & 255);
                    arrby[n] = (byte)(255 & n7 >> 8);
                    n2 -= 3;
                    n += 3;
                    continue block0;
                }
                int n8 = this.getByte();
                if (n8 == -1 || n8 == -2) {
                    if (n8 == -1) {
                        if (n3 == 0) {
                            return n - n6;
                        }
                        if (!this.ignoreErrors) {
                            throw new IOException("Error in encoded stream: needed 4 valid base64 characters but only got " + n3 + " before EOF" + this.recentChars());
                        }
                        bl = true;
                    } else {
                        if (n3 < 2 && !this.ignoreErrors) {
                            throw new IOException("Error in encoded stream: needed at least 2 valid base64 characters, but only got " + n3 + " before padding character (=)" + this.recentChars());
                        }
                        if (n3 == 0) {
                            return n - n6;
                        }
                        bl = false;
                    }
                    if ((n4 = n3 - 1) == 0) {
                        n4 = 1;
                    }
                    break block0;
                }
                int n9 = n5 << 6;
                ++n3;
                n5 = n9 | n8;
            } while (true);
            break;
        } while (true);
        int n10 = n3 + 1;
        int n11 = n5 << 6;
        do {
            if (n10 >= 4) {
                int n12 = n11 >> 8;
                if (n4 == 2) {
                    arrby[n + 1] = (byte)(n12 & 255);
                }
                arrby[n] = (byte)(255 & n12 >> 8);
                return n + n4 - n6;
            }
            if (!bl) {
                int n13 = this.getByte();
                if (n13 == -1) {
                    if (!this.ignoreErrors) {
                        throw new IOException("Error in encoded stream: hit EOF while looking for padding characters (=)" + this.recentChars());
                    }
                } else if (n13 != -2 && !this.ignoreErrors) {
                    throw new IOException("Error in encoded stream: found valid base64 character after a padding character (=)" + this.recentChars());
                }
            }
            n11 <<= 6;
            ++n10;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] decode(byte[] arrby) {
        int n = 3 * (arrby.length / 4);
        if (n == 0) {
            return arrby;
        }
        if (arrby[-1 + arrby.length] == 61) {
            --n;
            if (arrby[-2 + arrby.length] == 61) {
                --n;
            }
        }
        byte[] arrby2 = new byte[n];
        int n2 = 0;
        int n3 = arrby.length;
        int n4 = 0;
        while (n3 > 0) {
            int n5;
            int n6 = 3;
            byte[] arrby3 = pem_convert_array;
            int n7 = n4 + 1;
            int n8 = arrby3[255 & arrby[n4]] << 6;
            byte[] arrby4 = pem_convert_array;
            int n9 = n7 + 1;
            int n10 = (n8 | arrby4[255 & arrby[n7]]) << 6;
            if (arrby[n9] != 61) {
                byte[] arrby5 = pem_convert_array;
                n5 = n9 + 1;
                n10 |= arrby5[255 & arrby[n9]];
            } else {
                --n6;
                n5 = n9;
            }
            int n11 = n10 << 6;
            if (arrby[n5] != 61) {
                byte[] arrby6 = pem_convert_array;
                int n12 = n5 + 1;
                n11 |= arrby6[255 & arrby[n5]];
                n5 = n12;
            } else {
                --n6;
            }
            if (n6 > 2) {
                arrby2[n2 + 2] = (byte)(n11 & 255);
            }
            int n13 = n11 >> 8;
            if (n6 > 1) {
                arrby2[n2 + 1] = (byte)(n13 & 255);
            }
            arrby2[n2] = (byte)(255 & n13 >> 8);
            n2 += n6;
            n3 -= 4;
            n4 = n5;
        }
        return arrby2;
    }

    private int getByte() throws IOException {
        byte by;
        int n;
        do {
            if (this.input_pos >= this.input_len) {
                try {
                    this.input_len = this.in.read(this.input_buffer);
                    if (this.input_len <= 0) {
                        return -1;
                    }
                }
                catch (EOFException eOFException) {
                    return -1;
                }
                this.input_pos = 0;
            }
            byte[] arrby = this.input_buffer;
            int n2 = this.input_pos;
            this.input_pos = n2 + 1;
            n = 255 & arrby[n2];
            if (n != 61) continue;
            return -2;
        } while ((by = pem_convert_array[n]) == -1);
        return by;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String recentChars() {
        int n = 10;
        String string2 = "";
        if (this.input_pos <= n) {
            n = this.input_pos;
        }
        if (n <= 0) return string2;
        String string3 = String.valueOf((Object)string2) + ", the " + n + " most recent characters were: \"";
        int n2 = this.input_pos - n;
        while (n2 < this.input_pos) {
            char c = (char)(255 & this.input_buffer[n2]);
            switch (c) {
                default: {
                    string3 = c >= ' ' && c < '' ? String.valueOf((Object)string3) + c : String.valueOf((Object)string3) + "\\" + c;
                }
                case '\r': {
                    string3 = String.valueOf((Object)string3) + "\\r";
                    break;
                }
                case '\n': {
                    string3 = String.valueOf((Object)string3) + "\\n";
                    break;
                }
                case '\t': {
                    string3 = String.valueOf((Object)string3) + "\\t";
                    break;
                }
            }
            ++n2;
        }
        return String.valueOf((Object)string3) + "\"";
    }

    public int available() throws IOException {
        return 3 * this.in.available() / 4 + (this.bufsize - this.index);
    }

    public boolean markSupported() {
        return false;
    }

    public int read() throws IOException {
        if (this.index >= this.bufsize) {
            this.bufsize = this.decode(this.buffer, 0, this.buffer.length);
            if (this.bufsize <= 0) {
                return -1;
            }
            this.index = 0;
        }
        byte[] arrby = this.buffer;
        int n = this.index;
        this.index = n + 1;
        return 255 & arrby[n];
    }

    /*
     * Enabled aggressive block sorting
     */
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n;
        do {
            if (this.index >= this.bufsize || n2 <= 0) {
                int n4;
                if (this.index >= this.bufsize) {
                    this.index = 0;
                    this.bufsize = 0;
                }
                if ((n4 = 3 * (n2 / 3)) <= 0) break;
                int n5 = this.decode(arrby, n, n4);
                n += n5;
                n2 -= n5;
                if (n5 == n4) break;
                if (n != n3) return n - n3;
                return -1;
            }
            int n6 = n + 1;
            byte[] arrby2 = this.buffer;
            int n7 = this.index;
            this.index = n7 + 1;
            arrby[n] = arrby2[n7];
            --n2;
            n = n6;
        } while (true);
        int n8 = n;
        while (n2 > 0) {
            int n9 = this.read();
            if (n9 == -1) break;
            int n10 = n8 + 1;
            arrby[n8] = (byte)n9;
            --n2;
            n8 = n10;
        }
        if (n8 != n3) return n8 - n3;
        return -1;
    }
}

