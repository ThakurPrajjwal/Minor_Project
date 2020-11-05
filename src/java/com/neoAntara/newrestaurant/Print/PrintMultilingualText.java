/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.os.Handler
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.reflect.Field
 */
package com.neoAntara.newrestaurant.Print;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.neoAntara.newrestaurant.Print.Bluetooth;
import com.neoAntara.newrestaurant.Print.PrintImage;
import com.zj.btsdk.BluetoothService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class PrintMultilingualText {
    Activity act;
    Context con;
    int effectivePrintWidth = 48;
    String imageFilePath = "";
    boolean initilized = false;
    BluetoothService mService = null;

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    public PrintMultilingualText(Activity activity, Context context, BluetoothService bluetoothService, String string2, int n) {
        this.con = context;
        this.act = activity;
        this.effectivePrintWidth = n > 20 && n < 150 ? n : 48;
        if (string2 != null) {
            new File(string2).mkdirs();
            this.imageFilePath = string2 + "/temp.jpg";
        }
        if (PrintMultilingualText.getPlatformVersion() > 21) {
            WebView.enableSlowWholeDocumentDraw();
        }
        this.initilized = true;
    }

    public static int getPlatformVersion() {
        try {
            Field field = Class.forName((String)"android.os.Build$VERSION").getField("SDK_INT");
            int n = field.getInt((Object)field);
            return n;
        }
        catch (Exception exception) {
            return 1;
        }
    }

    private void myWebview(String string2, final File file) {
        View view = LayoutInflater.from((Context)this.con).inflate(2131296311, null);
        final WebView webView = (WebView)view.findViewById(2131165317);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.con);
        builder.setView(view);
        builder.setCancelable(true);
        webView.setWebViewClient(new WebViewClient(builder.create()){
            final /* synthetic */ AlertDialog val$alertDialog;
            {
                this.val$alertDialog = alertDialog;
            }

            public void onPageFinished(WebView webView2, String string2) {
                new Handler().postDelayed(new Runnable(){

                    public void run() {
                        if (webView.getWidth() > 0 && webView.getHeight() > 0) {
                            PrintMultilingualText.this.act.runOnUiThread(new Runnable(){

                                public void run() {
                                    PrintMultilingualText.this.createBitmap(webView, file, 1.this.val$alertDialog);
                                }
                            });
                        }
                    }

                }, 1500L);
            }

        });
        webView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(Math.round((float)(8 * this.effectivePrintWidth)), -2));
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadDataWithBaseURL(null, string2, "text/html", "utf-8", null);
    }

    public void createBitmap(WebView webView, File file, AlertDialog alertDialog) {
        int n;
        String string2 = file.getAbsolutePath();
        if (string2 != null && string2.length() > 0 && (n = string2.lastIndexOf("/")) != -1) {
            new File(string2.substring(0, n)).mkdirs();
        }
        float f = this.con.getResources().getDisplayMetrics().density;
        int n2 = webView.getWidth();
        int n3 = (int)(f * (float)webView.getContentHeight());
        if (n2 == 0 || n3 == 0) {
            alertDialog.dismiss();
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap((int)n2, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(-1);
        webView.draw(canvas);
        try {
            alertDialog.dismiss();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, (OutputStream)fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            this.printImage(file.getAbsolutePath());
            return;
        }
        catch (Exception exception) {
            alertDialog.dismiss();
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public Bitmap getResizedBitmap(Bitmap bitmap) {
        int n = Math.round((float)(8 * this.effectivePrintWidth));
        int n2 = bitmap.getWidth();
        int n3 = bitmap.getHeight();
        if (n2 == n) return bitmap;
        if (n2 < n && n2 > 16) {
            int n4 = n2 % 8;
            if (n4 == 0) return bitmap;
            {
                int n5 = n2 - n4;
                int n6 = n3 * (n2 - n4) / n2;
                float f = (float)n5 / (float)n2;
                float f2 = (float)n6 / (float)n3;
                Matrix matrix = new Matrix();
                matrix.postScale(f, f2);
                Bitmap bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)n2, (int)n3, (Matrix)matrix, (boolean)false);
                bitmap.recycle();
                return bitmap2;
            }
        }
        if (n2 <= 16) {
            return bitmap;
        }
        int n7 = n * n3 / n2;
        float f = (float)n / (float)n2;
        float f3 = (float)n7 / (float)n3;
        Matrix matrix = new Matrix();
        matrix.postScale(f, f3);
        Bitmap bitmap3 = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)n2, (int)n3, (Matrix)matrix, (boolean)false);
        bitmap.recycle();
        return bitmap3;
    }

    protected void printImage(String string2) {
        if (Bluetooth.isPrinterConnected(this.con, this.act)) {
            Bitmap bitmap;
            BluetoothService bluetoothService = Bluetooth.getServiceInstance();
            if (new File(string2).exists() && (bitmap = BitmapFactory.decodeFile((String)string2)) != null) {
                PrintImage printImage = new PrintImage(this.getResizedBitmap(bitmap));
                printImage.PrepareImage(PrintImage.dither.floyd_steinberg, 128);
                bluetoothService.write(printImage.getPrintImageData());
            }
        }
    }

    public void startPrinting(String string2) {
        if (this.initilized) {
            this.myWebview(string2, new File(this.imageFilePath));
        }
    }

}

