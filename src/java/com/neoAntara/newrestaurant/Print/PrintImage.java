/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Color
 *  android.graphics.Matrix
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.lang.Enum
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 */
package com.neoAntara.newrestaurant.Print;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PrintImage {
    private int bm_height;
    private Bitmap bm_print;
    private Bitmap bm_source;
    private int bm_width;
    private int[] pixels;

    /*
     * Enabled aggressive block sorting
     */
    public PrintImage(Bitmap bitmap) {
        this.bm_source = bitmap;
        int n = this.bm_source.getWidth();
        int n2 = this.bm_source.getHeight();
        if (n > 576) {
            int n3 = (int)Math.floor((double)((float)576 / (float)n * (float)n2));
            this.bm_source = this.getResizedBitmap(this.bm_source, 576, n3);
        } else if (Math.floor((double)(n / 8)) != (double)n / 8.0) {
            int n4 = (int)Math.floor((double)(n / 8));
            int n5 = (int)Math.floor((double)((float)n4 / (float)n * (float)n2));
            this.bm_source = this.getResizedBitmap(this.bm_source, n4, n5);
        }
        this.bm_width = this.bm_source.getWidth();
        this.bm_height = this.bm_source.getHeight();
        this.pixels = new int[n * n2];
    }

    /*
     * Enabled aggressive block sorting
     */
    public void Dither_Floyd_Steinberg(int n) {
        int n2 = this.bm_width;
        int n3 = this.bm_height;
        int n4 = n - 128;
        if (this.bm_print != null) {
            this.bm_print.recycle();
        }
        this.bm_print = this.bm_source.copy(this.bm_source.getConfig(), true);
        this.bm_print.getPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
        int n5 = n2 + 1;
        int n6 = n3 + 1;
        int[] arrn = new int[n5 * n6];
        int n7 = 0;
        do {
            int n8;
            int n9;
            if (n7 >= (n8 = n6 - 1)) break;
            for (int i = 0; i < (n9 = n5 - 1); ++i) {
                int n10;
                int n11;
                int n12 = n5 - 1;
                if (i == n12 || n7 == (n10 = n6 - 1)) {
                    arrn[i + n5 * n7] = 0;
                    continue;
                }
                int n13 = this.pixels[i + n7 * this.bm_width];
                arrn[i + n5 * n7] = n11 = n4 + (76 * Color.red((int)n13) + 151 * Color.blue((int)n13) + 29 * Color.green((int)n13)) / 256;
            }
            ++n7;
        } while (true);
        int n14 = 0;
        do {
            int n15;
            int n16;
            if (n14 >= (n16 = n6 - 2)) break;
            for (int i = 0; i < (n15 = n5 - 2); ++i) {
                int n17 = i + n14 * n5;
                int n18 = arrn[n17];
                int n19 = n18 < 128 ? 0 : 255;
                int n20 = n18 - n19;
                arrn[n17] = n19;
                arrn[n17 + 1] = arrn[n17 + 1] + n20 * 7 / 16;
                arrn[n5 + (n17 - 1)] = arrn[n5 + (n17 - 1)] + n20 * 3 / 16;
                arrn[n17 + n5] = arrn[n17 + n5] + n20 * 5 / 16;
                arrn[n5 + (n17 + 1)] = arrn[n5 + (n17 + 1)] + n20 / 16;
            }
            ++n14;
        } while (true);
        int n21 = 0;
        do {
            int n22;
            int n23;
            if (n21 >= (n22 = n6 - 1)) {
                this.bm_print.setPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
                return;
            }
            for (int i = 0; i < (n23 = n5 - 1); ++i) {
                int n24 = arrn[i + n5 * n21] == 0 ? -16777216 : -1;
                this.pixels[i + n21 * this.bm_width] = n24;
            }
            ++n21;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void Dither_Matrix_2x2(int n) {
        int[] arrn = new int[]{32, 160, 222, 96};
        int n2 = n - 128;
        if (this.bm_print != null) {
            this.bm_print.recycle();
        }
        this.bm_print = this.bm_source.copy(this.bm_source.getConfig(), true);
        this.bm_print.getPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
        int n3 = 0;
        do {
            if (n3 >= this.bm_height) {
                this.bm_print.setPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
                return;
            }
            for (int i = 0; i < this.bm_width; ++i) {
                int n4 = this.pixels[i + n3 * this.bm_width];
                int n5 = n2 + (76 * Color.red((int)n4) + 150 * Color.blue((int)n4) + 30 * Color.green((int)n4)) / 256 < arrn[i % 2 + 2 * (n3 % 2)] ? -16777216 : -1;
                this.pixels[i + n3 * this.bm_width] = n5;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void Dither_Matrix_4x4(int n) {
        int[] arrn = new int[]{15, 143, 47, 175, 207, 79, 239, 111, 63, 191, 31, 159, 255, 127, 223, 95};
        int n2 = n - 128;
        if (this.bm_print != null) {
            this.bm_print.recycle();
        }
        this.bm_print = this.bm_source.copy(this.bm_source.getConfig(), true);
        this.bm_print.getPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
        int n3 = 0;
        do {
            if (n3 >= this.bm_height) {
                this.bm_print.setPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
                return;
            }
            for (int i = 0; i < this.bm_width; ++i) {
                int n4 = this.pixels[i + n3 * this.bm_width];
                int n5 = n2 + (76 * Color.red((int)n4) + 150 * Color.blue((int)n4) + 30 * Color.green((int)n4)) / 256 < arrn[i % 4 + 4 * (n3 % 4)] ? -16777216 : -1;
                this.pixels[i + n3 * this.bm_width] = n5;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void Dither_Threshold(int n) {
        int n2 = n - 128;
        if (this.bm_print != null) {
            this.bm_print.recycle();
        }
        this.bm_print = this.bm_source.copy(this.bm_source.getConfig(), true);
        this.bm_print.getPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
        int n3 = 0;
        do {
            if (n3 >= this.bm_height) {
                this.bm_print.setPixels(this.pixels, 0, this.bm_width, 0, 0, this.bm_width, this.bm_height);
                return;
            }
            for (int i = 0; i < this.bm_width; ++i) {
                int n4 = this.pixels[i + n3 * this.bm_width];
                int n5 = n2 + (76 * Color.red((int)n4) + 150 * Color.blue((int)n4) + 30 * Color.green((int)n4)) / 256 < 128 ? -16777216 : -1;
                this.pixels[i + n3 * this.bm_width] = n5;
            }
            ++n3;
        } while (true);
    }

    public void PrepareImage(dither dither2, int n) {
        switch (1.$SwitchMap$com$neoAntara$newrestaurant$Print$PrintImage$dither[dither2.ordinal()]) {
            default: {
                return;
            }
            case 1: {
                this.Dither_Floyd_Steinberg(n);
                return;
            }
            case 2: {
                this.Dither_Matrix_2x2(n);
                return;
            }
            case 3: {
                this.Dither_Matrix_4x4(n);
                return;
            }
            case 4: 
        }
        this.Dither_Threshold(n);
    }

    public int getHeight() {
        return this.bm_height;
    }

    public Bitmap getPrintImage() {
        return this.bm_print;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] getPrintImageData() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte by = (byte)(this.bm_width / 8 / 256);
        byte by2 = (byte)(this.bm_width / 8 % 256);
        byte by3 = (byte)(this.bm_height / 256);
        byte by4 = (byte)(this.bm_height % 256);
        try {
            byteArrayOutputStream.write(new byte[]{29, 118, 48, 0, by2, by, by4, by3});
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        int n = 0;
        while (n < this.bm_height) {
            for (int i = 0; i < this.bm_width / 8; ++i) {
                int n2 = 0;
                for (int j = 0; j < 8; ++j) {
                    n2 <<= 1;
                    if (this.pixels[j + i * 8 + n * this.bm_width] != -16777216) continue;
                    n2 |= 1;
                }
                byteArrayOutputStream.write((int)((byte)n2));
            }
            ++n;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int n, int n2) {
        int n3 = bitmap.getWidth();
        int n4 = bitmap.getHeight();
        float f = (float)n / (float)n3;
        float f2 = (float)n2 / (float)n4;
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        Bitmap bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)n3, (int)n4, (Matrix)matrix, (boolean)false);
        bitmap.recycle();
        return bitmap2;
    }

    public Bitmap getSourceImage() {
        return this.bm_source;
    }

    public int getWidth() {
        return this.bm_width;
    }

    public static final class dither
    extends Enum<dither> {
        private static final /* synthetic */ dither[] $VALUES;
        public static final /* enum */ dither floyd_steinberg = new dither();
        public static final /* enum */ dither matrix_2x2 = new dither();
        public static final /* enum */ dither matrix_4x4 = new dither();
        public static final /* enum */ dither threshold = new dither();

        static {
            dither[] arrdither = new dither[]{floyd_steinberg, matrix_2x2, matrix_4x4, threshold};
            $VALUES = arrdither;
        }

        public static dither valueOf(String string2) {
            return (dither)Enum.valueOf(dither.class, (String)string2);
        }

        public static dither[] values() {
            return (dither[])$VALUES.clone();
        }
    }

}

