/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Environment
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.PrintWriter
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.String
 *  java.text.SimpleDateFormat
 *  java.util.Calendar
 *  java.util.Date
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.UtilityHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger
extends Activity {
    private static Logger Instance = null;
    private File logFile = null;

    private Logger() {
    }

    private long GetFileSizeInKB(File file) {
        return file.length() / 1024L;
    }

    public static Logger GetInstance() {
        if (Instance == null) {
            Instance = new Logger();
        }
        return Instance;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private File createLogFile() {
        File file;
        File file2;
        block7 : {
            File file3;
            file2 = null;
            try {
                boolean bl = Home.IS_SDCARD_WRITABLE;
                file2 = null;
                if (!bl) return file2;
                file3 = new File(Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_DOWNLOADS), "Restaurant/Logs");
                boolean bl2 = file3.exists();
                file2 = null;
                boolean bl3 = !bl2 ? file3.mkdirs() : true;
                file2 = null;
                if (!bl3) return file2;
                file = new File(file3, "Log.txt");
            }
            catch (Exception exception) {}
            if (!file.exists()) break block7;
            if (this.GetFileSizeInKB(file) <= 3072L) return file;
            String string2 = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            file.renameTo(new File(file3, "Log_" + string2 + ".txt"));
            file2 = new File(file3, "Log.txt");
            file2.createNewFile();
            return file2;
        }
        try {
            file.createNewFile();
        }
        catch (Exception exception) {
            file2 = file;
        }
        return file;
        {
            UtilityHelper.showToastMessage(this.getApplicationContext(), "Error occured");
            return file2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void LogDebugMessage(String string2) {
        this.logFile = this.createLogFile();
        if (this.logFile == null) return;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.logFile, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)fileOutputStream);
            outputStreamWriter.append((CharSequence)("Date : " + UtilityHelper.getCurrentDateTime() + "\r\n"));
            outputStreamWriter.append((CharSequence)"Debug Message : \r\n");
            StringWriter stringWriter = new StringWriter();
            new PrintWriter((Writer)stringWriter).append((CharSequence)string2);
            outputStreamWriter.append((CharSequence)stringWriter.toString());
            outputStreamWriter.append((CharSequence)"\n\r--------------------------------------------------------------------------------\r\n");
            outputStreamWriter.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void LogException(Exception exception) {
        this.logFile = this.createLogFile();
        if (this.logFile == null) return;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.logFile, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)fileOutputStream);
            outputStreamWriter.append((CharSequence)("Date : " + UtilityHelper.getCurrentDateTime() + "\r\n"));
            outputStreamWriter.append((CharSequence)"Stack Trace : \r\n");
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter((Writer)stringWriter));
            outputStreamWriter.append((CharSequence)stringWriter.toString());
            outputStreamWriter.append((CharSequence)"\n\r--------------------------------------------------------------------------------\r\n");
            outputStreamWriter.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return;
        }
        catch (Exception exception2) {
            exception2.printStackTrace();
            return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }
}

