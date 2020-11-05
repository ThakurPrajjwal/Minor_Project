/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package com.zj.btsdk;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SuppressLint(value={"SdCardPath"})
public class PrintPic {
    public byte[] bitbuf = null;
    public Bitmap bm = null;
    public Canvas canvas = null;
    public float length = 0.0f;
    public Paint paint = null;
    public int width;

    public void drawImage(float f, float f2, String string2) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile((String)string2);
            this.canvas.drawBitmap(bitmap, f, f2, null);
            if (this.length < f2 + (float)bitmap.getHeight()) {
                this.length = f2 + (float)bitmap.getHeight();
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public int getLength() {
        return 20 + (int)this.length;
    }

    public void initCanvas(int n) {
        this.bm = Bitmap.createBitmap((int)n, (int)(n * 10), (Bitmap.Config)Bitmap.Config.ARGB_4444);
        this.canvas = new Canvas(this.bm);
        this.canvas.drawColor(-1);
        this.width = n;
        this.bitbuf = new byte[this.width / 8];
    }

    public void initPaint() {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(-16777216);
        this.paint.setStyle(Paint.Style.STROKE);
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] printDraw() {
        Bitmap bitmap = Bitmap.createBitmap((Bitmap)this.bm, (int)0, (int)0, (int)this.width, (int)this.getLength());
        byte[] arrby = new byte[8 + this.width / 8 * this.getLength()];
        arrby[0] = 29;
        arrby[1] = 118;
        arrby[2] = 48;
        arrby[3] = 0;
        arrby[4] = (byte)(this.width / 8);
        arrby[5] = 0;
        arrby[6] = (byte)(this.getLength() % 256);
        arrby[7] = (byte)(this.getLength() / 256);
        int n = 7;
        int n2 = 0;
        block0 : while (n2 < this.getLength()) {
            int n3 = 0;
            do {
                if (n3 >= this.width / 8) break;
                int n4 = bitmap.getPixel(0 + n3 * 8, n2) == -1 ? 0 : 1;
                int n5 = bitmap.getPixel(1 + n3 * 8, n2) == -1 ? 0 : 1;
                int n6 = bitmap.getPixel(2 + n3 * 8, n2) == -1 ? 0 : 1;
                int n7 = bitmap.getPixel(3 + n3 * 8, n2) == -1 ? 0 : 1;
                int n8 = bitmap.getPixel(4 + n3 * 8, n2) == -1 ? 0 : 1;
                int n9 = bitmap.getPixel(5 + n3 * 8, n2) == -1 ? 0 : 1;
                int n10 = bitmap.getPixel(6 + n3 * 8, n2) == -1 ? 0 : 1;
                int n11 = bitmap.getPixel(7 + n3 * 8, n2) == -1 ? 0 : 1;
                int n12 = n11 + (n4 * 128 + n5 * 64 + n6 * 32 + n7 * 16 + n8 * 8 + n9 * 4 + n10 * 2);
                this.bitbuf[n3] = (byte)n12;
                ++n3;
            } while (true);
            int n13 = 0;
            do {
                int n14;
                if (n13 >= (n14 = this.width / 8)) {
                    ++n2;
                    continue block0;
                }
                arrby[++n] = this.bitbuf[n13];
                ++n13;
            } while (true);
            break;
        }
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void printPng() {
        FileOutputStream fileOutputStream;
        File file = new File("/mnt/sdcard/0.png");
        Bitmap bitmap = Bitmap.createBitmap((Bitmap)this.bm, (int)0, (int)0, (int)this.width, (int)this.getLength());
        try {
            fileOutputStream = new FileOutputStream(file);
        }
        catch (FileNotFoundException fileNotFoundException) {}
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, (OutputStream)fileOutputStream);
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {}
        {
            void var4_5;
            var4_5.printStackTrace();
            return;
        }
    }
}

