/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Environment
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.widget.Toast
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.channels.FileChannel
 *  java.nio.channels.ReadableByteChannel
 *  java.text.SimpleDateFormat
 *  java.util.Calendar
 *  java.util.Date
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.neoAntara.newrestaurant.MainActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ImportDB
extends Activity {
    private void importDB() {
        try {
            File file = Environment.getExternalStorageDirectory();
            File file2 = Environment.getDataDirectory();
            if (file.canWrite()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                simpleDateFormat.format(Calendar.getInstance().getTime());
                File file3 = new File((Object)Environment.getExternalStorageDirectory() + "/DB Backup");
                if (!file3.exists()) {
                    Toast.makeText((Context)this, (CharSequence)"No database found", (int)1).show();
                    return;
                }
                String string2 = (Object)file3 + "//MyDB";
                File file4 = new File(file2, "//data//com.example.Restaurant//databases//MyDB");
                new File(file, string2);
                FileChannel fileChannel = new FileInputStream(string2).getChannel();
                FileChannel fileChannel2 = new FileOutputStream(file4).getChannel();
                fileChannel2.transferFrom((ReadableByteChannel)fileChannel, 0L, fileChannel.size());
                fileChannel.close();
                fileChannel2.close();
                Toast.makeText((Context)this.getBaseContext(), (CharSequence)file4.toString(), (int)1).show();
                this.finish();
                Intent intent = new Intent((Context)this, MainActivity.class);
                this.startActivityForResult(intent, 0);
                return;
            }
        }
        catch (Exception exception) {
            Toast.makeText((Context)this.getBaseContext(), (CharSequence)exception.toString(), (int)1).show();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.importDB();
        this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131361792, menu2);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }
}

