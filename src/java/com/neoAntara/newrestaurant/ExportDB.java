/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.os.Environment
 *  android.support.v4.app.ActivityCompat
 *  android.support.v7.app.AlertDialog
 *  android.support.v7.app.AlertDialog$Builder
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.widget.Toast
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.Writer
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.text.SimpleDateFormat
 *  java.util.Calendar
 *  java.util.Date
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.UtilityHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExportDB
extends Activity {
    static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void exportDB() {
        try {
            boolean bl = "mounted".equals((Object)Environment.getExternalStorageState());
            boolean bl2 = false;
            if (bl) {
                bl2 = true;
            }
            if (ActivityCompat.checkSelfPermission((Context)this, (String)"android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                ExportDB.requestPermission((Context)this);
            }
            if (!bl2) return;
            File file = new File(Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_DOWNLOADS), "Restaurant/Database");
            boolean bl3 = !file.exists() ? file.mkdirs() : true;
            if (!bl3) return;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String string2 = simpleDateFormat.format(Calendar.getInstance().getTime());
            File file2 = new File(file, "dbbackup_" + string2 + ".txt");
            file2.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)fileOutputStream);
            outputStreamWriter.append((CharSequence)"Table No,Order No,Item,Prize,Quantity,User,Customer,Address,Phone No,Discount,Other Charges,Date\r\n");
            Cursor cursor = new DBHelper((Context)this, null).getPrintOrdersToExport();
            if (cursor == null) {
                Toast.makeText((Context)this.getBaseContext(), (CharSequence)"No data found", (int)1).show();
            } else {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); ++i) {
                    outputStreamWriter.append((CharSequence)(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + cursor.getString(6) + "," + cursor.getString(7) + "," + cursor.getString(8) + cursor.getString(9) + "," + cursor.getString(10) + "," + cursor.getString(12) + "\r\n"));
                    cursor.moveToNext();
                }
                cursor.close();
                Toast.makeText((Context)this.getBaseContext(), (CharSequence)("Database exported successfully and Saved to : " + file2.toString()), (int)1).show();
            }
            outputStreamWriter.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    private static void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)((Activity)context), (String)"android.permission.WRITE_EXTERNAL_STORAGE")) {
            new AlertDialog.Builder(context).setMessage((CharSequence)"Allow Access").setPositiveButton((CharSequence)"Yes", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    ActivityCompat.requestPermissions((Activity)((Activity)context), (String[])new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, (int)1);
                }
            }).show();
            return;
        }
        ActivityCompat.requestPermissions((Activity)((Activity)context), (String[])new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, (int)1);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.exportDB();
        this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        return super.onCreateOptionsMenu(menu2);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        switch (n) {
            default: {
                return;
            }
            case 1: 
        }
        if (arrn.length == 1 && arrn[0] == 0) {
            Toast.makeText((Context)this.getBaseContext(), (CharSequence)"Permisssion granted.", (int)1).show();
            return;
        }
        Toast.makeText((Context)this.getBaseContext(), (CharSequence)"Permission denied but need to provide permission to export database.", (int)1).show();
        super.onRequestPermissionsResult(n, arrstring, arrn);
    }

}

