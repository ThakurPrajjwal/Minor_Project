/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.NumberFormatException
 *  java.lang.Object
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.util.ASCIIUtility;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResponseInputStream {
    private static final int incrementSlop = 16;
    private static final int maxIncrement = 262144;
    private static final int minIncrement = 256;
    private BufferedInputStream bin;

    public ResponseInputStream(InputStream inputStream) {
        this.bin = new BufferedInputStream(inputStream, 2048);
    }

    public ByteArray readResponse() throws IOException {
        return this.readResponse(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ByteArray readResponse(ByteArray byteArray) throws IOException {
        if (byteArray == null) {
            byteArray = new ByteArray(new byte[128], 0, 128);
        }
        byte[] arrby = byteArray.getBytes();
        int n = 0;
        block5 : do {
            int n2;
            block13 : {
                int n3;
                block16 : {
                    block15 : {
                        block14 : {
                            int n4;
                            int n5 = 0;
                            boolean bl = false;
                            n2 = n;
                            do {
                                if (bl || (n5 = this.bin.read()) == -1) {
                                    if (n5 != -1) break;
                                    throw new IOException();
                                }
                                switch (n5) {
                                    default: {
                                        break;
                                    }
                                    case 10: {
                                        if (n2 <= 0 || arrby[n2 - 1] != 13) break;
                                        bl = true;
                                    }
                                }
                                if (n2 >= arrby.length) {
                                    int n6 = arrby.length;
                                    if (n6 > 262144) {
                                        n6 = 262144;
                                    }
                                    byteArray.grow(n6);
                                    arrby = byteArray.getBytes();
                                }
                                int n7 = n2 + 1;
                                arrby[n2] = (byte)n5;
                                n2 = n7;
                            } while (true);
                            if (n2 < 5 || arrby[n2 - 3] != 125) break block14;
                            for (n4 = n2 - 4; n4 >= 0 && arrby[n4] != 123; --n4) {
                            }
                            if (n4 < 0) break block14;
                            int n8 = n4 + 1;
                            int n9 = n2 - 3;
                            try {
                                int n10;
                                n3 = n10 = ASCIIUtility.parseInt(arrby, n8, n9);
                                if (n3 <= 0) break block13;
                            }
                            catch (NumberFormatException numberFormatException) {}
                            int n11 = arrby.length - n2;
                            if (n3 + 16 <= n11) break block15;
                            int n12 = 256 > n3 + 16 - n11 ? 256 : n3 + 16 - n11;
                            byteArray.grow(n12);
                            arrby = byteArray.getBytes();
                            n = n2;
                            break block16;
                        }
                        byteArray.setCount(n2);
                        return byteArray;
                    }
                    n = n2;
                }
                do {
                    if (n3 <= 0) continue block5;
                    int n13 = this.bin.read(arrby, n, n3);
                    n3 -= n13;
                    n += n13;
                } while (true);
            }
            n = n2;
        } while (true);
    }
}

