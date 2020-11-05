/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Throwable
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BASE64EncoderStream
extends FilterOutputStream {
    private static byte[] newline = new byte[]{13, 10};
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private byte[] buffer = new byte[3];
    private int bufsize = 0;
    private int bytesPerLine;
    private int count = 0;
    private int lineLimit;
    private boolean noCRLF = false;
    private byte[] outbuf;

    public BASE64EncoderStream(OutputStream outputStream) {
        this(outputStream, 76);
    }

    public BASE64EncoderStream(OutputStream outputStream, int n) {
        int n2;
        super(outputStream);
        if (n == Integer.MAX_VALUE || n < 4) {
            this.noCRLF = true;
            n = 76;
        }
        this.bytesPerLine = n2 = 4 * (n / 4);
        this.lineLimit = 3 * (n2 / 4);
        if (this.noCRLF) {
            this.outbuf = new byte[n2];
            return;
        }
        this.outbuf = new byte[n2 + 2];
        this.outbuf[n2] = 13;
        this.outbuf[n2 + 1] = 10;
    }

    private void encode() throws IOException {
        int n = BASE64EncoderStream.encodedSize(this.bufsize);
        this.out.write(BASE64EncoderStream.encode(this.buffer, 0, this.bufsize, this.outbuf), 0, n);
        this.count = n + this.count;
        if (this.count >= this.bytesPerLine) {
            if (!this.noCRLF) {
                this.out.write(newline);
            }
            this.count = 0;
        }
    }

    public static byte[] encode(byte[] arrby) {
        if (arrby.length == 0) {
            return arrby;
        }
        return BASE64EncoderStream.encode(arrby, 0, arrby.length, null);
    }

    private static byte[] encode(byte[] arrby, int n, int n2, byte[] arrby2) {
        if (arrby2 == null) {
            arrby2 = new byte[BASE64EncoderStream.encodedSize(n2)];
        }
        int n3 = 0;
        int n4 = n;
        do {
            if (n2 < 3) {
                if (n2 != 1) break;
                n4 + 1;
                int n5 = (255 & arrby[n4]) << 4;
                arrby2[n3 + 3] = 61;
                arrby2[n3 + 2] = 61;
                arrby2[n3 + 1] = (byte)pem_array[n5 & 63];
                int n6 = n5 >> 6;
                arrby2[n3 + 0] = (byte)pem_array[n6 & 63];
                return arrby2;
            }
            int n7 = n4 + 1;
            int n8 = (255 & arrby[n4]) << 8;
            int n9 = n7 + 1;
            int n10 = (n8 | 255 & arrby[n7]) << 8;
            int n11 = n9 + 1;
            int n12 = n10 | 255 & arrby[n9];
            arrby2[n3 + 3] = (byte)pem_array[n12 & 63];
            int n13 = n12 >> 6;
            arrby2[n3 + 2] = (byte)pem_array[n13 & 63];
            int n14 = n13 >> 6;
            arrby2[n3 + 1] = (byte)pem_array[n14 & 63];
            int n15 = n14 >> 6;
            arrby2[n3 + 0] = (byte)pem_array[n15 & 63];
            n2 -= 3;
            n3 += 4;
            n4 = n11;
        } while (true);
        if (n2 == 2) {
            int n16 = n4 + 1;
            int n17 = (255 & arrby[n4]) << 8;
            n4 = n16 + 1;
            int n18 = (n17 | 255 & arrby[n16]) << 2;
            arrby2[n3 + 3] = 61;
            arrby2[n3 + 2] = (byte)pem_array[n18 & 63];
            int n19 = n18 >> 6;
            arrby2[n3 + 1] = (byte)pem_array[n19 & 63];
            int n20 = n19 >> 6;
            arrby2[n3 + 0] = (byte)pem_array[n20 & 63];
        }
        return arrby2;
    }

    private static int encodedSize(int n) {
        return 4 * ((n + 2) / 3);
    }

    public void close() throws IOException {
        BASE64EncoderStream bASE64EncoderStream = this;
        synchronized (bASE64EncoderStream) {
            this.flush();
            if (this.count > 0 && !this.noCRLF) {
                this.out.write(newline);
                this.out.flush();
            }
            this.out.close();
            return;
        }
    }

    public void flush() throws IOException {
        BASE64EncoderStream bASE64EncoderStream = this;
        synchronized (bASE64EncoderStream) {
            if (this.bufsize > 0) {
                this.encode();
                this.bufsize = 0;
            }
            this.out.flush();
            return;
        }
    }

    public void write(int n) throws IOException {
        BASE64EncoderStream bASE64EncoderStream = this;
        synchronized (bASE64EncoderStream) {
            byte[] arrby = this.buffer;
            int n2 = this.bufsize;
            this.bufsize = n2 + 1;
            arrby[n2] = (byte)n;
            if (this.bufsize == 3) {
                this.encode();
                this.bufsize = 0;
            }
            return;
        }
    }

    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        int n4;
        block14 : {
            int n5;
            block13 : {
                int n6;
                void var6_17;
                BASE64EncoderStream bASE64EncoderStream = this;
                // MONITORENTER : bASE64EncoderStream
                n4 = n + n2;
                n5 = n;
                do {
                    if (this.bufsize != 0 && n5 < n4) break block12;
                    n6 = 3 * ((this.bytesPerLine - this.count) / 4);
                    if (n5 + n6 >= n4) break block13;
                    int n7 = BASE64EncoderStream.encodedSize(n6);
                    if (!this.noCRLF) {
                        byte[] arrby2 = this.outbuf;
                        int n8 = n7 + 1;
                        arrby2[n7] = 13;
                        byte[] arrby3 = this.outbuf;
                        n7 = n8 + 1;
                        arrby3[n8] = 10;
                    }
                    this.out.write(BASE64EncoderStream.encode(arrby, n5, n6, this.outbuf), 0, n7);
                    break;
                } while (true);
                catch (Throwable throwable) {
                    // MONITOREXIT : bASE64EncoderStream
                    throw var6_17;
                }
                {
                    block12 : {
                        n3 = n5 + n6;
                        this.count = 0;
                        break block14;
                    }
                    int n9 = n5 + 1;
                    this.write(arrby[n5]);
                    n5 = n9;
                    continue;
                }
                catch (Throwable throwable) {
                    throw var6_17;
                }
            }
            n3 = n5;
        }
        do {
            if (n3 + this.lineLimit >= n4) {
                if (n3 + 3 >= n4) break;
                int n10 = 3 * ((n4 - n3) / 3);
                int n11 = BASE64EncoderStream.encodedSize(n10);
                this.out.write(BASE64EncoderStream.encode(arrby, n3, n10, this.outbuf), 0, n11);
                n3 += n10;
                this.count = n11 + this.count;
                break;
            }
            this.out.write(BASE64EncoderStream.encode(arrby, n3, this.lineLimit, this.outbuf));
            n3 += this.lineLimit;
        } while (true);
        do {
            if (n3 >= n4) {
                // MONITOREXIT : bASE64EncoderStream
                return;
            }
            this.write(arrby[n3]);
            ++n3;
        } while (true);
    }
}

