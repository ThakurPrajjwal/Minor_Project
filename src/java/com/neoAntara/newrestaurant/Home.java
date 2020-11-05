/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.os.Environment
 *  android.support.v4.app.ActivityCompat
 *  android.support.v7.app.AlertDialog
 *  android.support.v7.app.AlertDialog$Builder
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.neoAntara.newrestaurant.AddItem;
import com.neoAntara.newrestaurant.ChangePassword;
import com.neoAntara.newrestaurant.CreateUser;
import com.neoAntara.newrestaurant.CreditTransaction;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.DayEnd;
import com.neoAntara.newrestaurant.EditOrder;
import com.neoAntara.newrestaurant.ExportDB;
import com.neoAntara.newrestaurant.Help;
import com.neoAntara.newrestaurant.ImportDB;
import com.neoAntara.newrestaurant.MainActivity;
import com.neoAntara.newrestaurant.Models.AppActivationModel;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.SessionManager;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;

public class Home
extends Activity {
    public static boolean IS_SDCARD_WRITABLE = false;
    static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    DBHelper ObjDBHelp;
    private TextView date = null;
    TextView resto_name = null;

    static {
        IS_SDCARD_WRITABLE = false;
    }

    private void checkPermissionForLogger() {
        try {
            if ("mounted".equals((Object)Environment.getExternalStorageState())) {
                IS_SDCARD_WRITABLE = true;
            }
            if (ActivityCompat.checkSelfPermission((Context)this, (String)"android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                Home.requestPermission((Context)this);
            }
            return;
        }
        catch (Exception exception) {
            IS_SDCARD_WRITABLE = false;
            return;
        }
    }

    private boolean isSettingFileUpdated() {
        Cursor cursor = this.ObjDBHelp.getRestoDetails();
        cursor.moveToFirst();
        if (cursor.getCount() < 1) {
            RolesModel.GetInstance();
            if (RolesModel.get_updateSetting()) {
                this.startActivityForResult(new Intent((Context)this, Setting.class), 0);
                UtilityHelper.showToastMessage(this.getApplicationContext(), "First enter restuarant details in Setting file.");
            }
            return false;
        }
        return true;
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

    public void additem(View view) {
        RolesModel.GetInstance();
        if (RolesModel.get_addItem()) {
            this.startActivityForResult(new Intent((Context)this, AddItem.class), 0);
            return;
        }
        UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
    }

    public void alertforedit() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            Home.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            Home.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Home.this.editorder("HD");
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Select type of Service");
            builder.setPositiveButton((CharSequence)"Parcel", onClickListener);
            builder.setNeutralButton((CharSequence)"HomeDelivery", onClickListener);
            builder.setNegativeButton((CharSequence)"Service", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public void alertforreprint() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            Home.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            Home.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Home.this.reprintorder("HD");
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Select type of Service");
            builder.setPositiveButton((CharSequence)"Parcel", onClickListener);
            builder.setNeutralButton((CharSequence)"HomeDelivery", onClickListener);
            builder.setNegativeButton((CharSequence)"Service", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public void alertmessage() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: 
                    }
                    Home.this.go();
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Are you sure to DayEnd?");
            builder.setPositiveButton((CharSequence)"Yes", onClickListener);
            builder.setNegativeButton((CharSequence)"No", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public void alertpopup() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            Home.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            Home.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Home.this.parcelorder("HD");
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Select type of Service");
            builder.setPositiveButton((CharSequence)"Parcel", onClickListener);
            builder.setNeutralButton((CharSequence)"HomeDelivery", onClickListener);
            builder.setNegativeButton((CharSequence)"Service", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public void createorder(View view) {
        RolesModel.GetInstance();
        if (RolesModel.get_newOrder()) {
            if (this.isSettingFileUpdated()) {
                this.alertpopup();
            }
            return;
        }
        UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
    }

    public void editorder(View view) {
        RolesModel.GetInstance();
        if (RolesModel.get_editOrder()) {
            if (this.isSettingFileUpdated()) {
                this.alertforedit();
            }
            return;
        }
        UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
    }

    public void editorder(String string2) {
        Intent intent = new Intent((Context)this, EditOrder.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    public void exportDb() {
        this.startActivityForResult(new Intent((Context)this, ExportDB.class), 0);
    }

    public void exportdb() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: 
                    }
                    Home.this.exportDb();
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Are you sure to exportDB?");
            builder.setPositiveButton((CharSequence)"Yes", onClickListener);
            builder.setNegativeButton((CharSequence)"No", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public void go() {
        this.startActivityForResult(new Intent((Context)this, DayEnd.class), 0);
    }

    public void importDb() {
        this.startActivityForResult(new Intent((Context)this, ImportDB.class), 0);
    }

    public void importdb() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: 
                    }
                    Home.this.importDb();
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Are you sure to importDB?");
            builder.setPositiveButton((CharSequence)"Yes", onClickListener);
            builder.setNegativeButton((CharSequence)"No", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131296289);
        this.ObjDBHelp = new DBHelper((Context)this, null);
        if (!UtilityHelper.isUserLoggedIn()) {
            AppActivationModel.GetInstance().get_SessionManager().logoutUser();
            StringBuilder stringBuilder = new StringBuilder().append("Home : Logged in User(If) : ");
            RolesModel.GetInstance();
            UtilityHelper.LogMessage(stringBuilder.append(RolesModel.get_loggedInUser()).toString());
            this.startActivityForResult(new Intent((Context)this, MainActivity.class), 0);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder().append("Home : Logged in User(else) : ");
        RolesModel.GetInstance();
        UtilityHelper.LogMessage(stringBuilder.append(RolesModel.get_loggedInUser()).toString());
        String string2 = this.ObjDBHelp.getBusinessDate();
        this.date = (TextView)this.findViewById(2131165439);
        this.date.setText((CharSequence)string2);
        this.date.setGravity(5);
        this.date.setTextColor(-16777216);
        Cursor cursor = this.ObjDBHelp.getRestoDetails();
        cursor.moveToFirst();
        if (cursor.getCount() == 1) {
            String string3 = cursor.getString(0);
            this.resto_name = (TextView)this.findViewById(2131165338);
            this.resto_name.setText((CharSequence)string3);
            this.resto_name.setTextColor(-16777216);
        } else {
            this.startActivityForResult(new Intent((Context)this, Setting.class), 0);
        }
        cursor.close();
        this.checkPermissionForLogger();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131361792, menu2);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean bl = true;
        switch (menuItem.getItemId()) {
            default: {
                bl = super.onOptionsItemSelected(menuItem);
            }
            case 2131165280: {
                return bl;
            }
            case 2131165252: {
                this.startActivityForResult(new Intent((Context)this, CreateUser.class), 0);
                this.finish();
                return bl;
            }
            case 2131165234: {
                RolesModel.GetInstance();
                if (RolesModel.get_changePassword()) {
                    this.startActivityForResult(new Intent((Context)this, ChangePassword.class), 0);
                    return bl;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return bl;
            }
            case 2131165205: {
                this.startActivityForResult(new Intent((Context)this, AddItem.class), 0);
                return bl;
            }
            case 2131165355: {
                this.startActivityForResult(new Intent((Context)this, SearchItem.class), 0);
                return bl;
            }
            case 2131165320: {
                this.alertpopup();
                return bl;
            }
            case 2131165270: {
                this.alertforedit();
                return bl;
            }
            case 2131165337: {
                this.alertforreprint();
                return bl;
            }
            case 2131165336: {
                this.startActivityForResult(new Intent((Context)this, Reports.class), 0);
                return bl;
            }
            case 2131165258: {
                RolesModel.GetInstance();
                if (RolesModel.get_dayEnd()) {
                    this.alertmessage();
                    return bl;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return bl;
            }
            case 2131165253: {
                RolesModel.GetInstance();
                if (RolesModel.get_debitAmount()) {
                    this.startActivityForResult(new Intent((Context)this, CreditTransaction.class), 0);
                    return bl;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return bl;
            }
            case 2131165359: {
                RolesModel.GetInstance();
                if (RolesModel.get_updateSetting()) {
                    this.startActivityForResult(new Intent((Context)this, Setting.class), 0);
                    return bl;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return bl;
            }
            case 2131165276: {
                RolesModel.GetInstance();
                if (RolesModel.get_exportDB()) {
                    this.exportdb();
                    return bl;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return bl;
            }
            case 2131165443: {
                RolesModel.GetInstance();
                if (RolesModel.get_accessRights()) {
                    this.startActivityForResult(new Intent((Context)this, UserRights.class), 0);
                    return bl;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return bl;
            }
            case 2131165310: {
                UtilityHelper.Logout();
                this.finish();
                return bl;
            }
            case 2131165278: 
        }
        this.startActivityForResult(new Intent((Context)this, Help.class), 0);
        return bl;
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
        Toast.makeText((Context)this.getBaseContext(), (CharSequence)"Permission denied but need to provide permission to log error.", (int)1).show();
        super.onRequestPermissionsResult(n, arrstring, arrn);
    }

    public void parcelorder(String string2) {
        Intent intent = new Intent((Context)this, NewOrder.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    public void report(View view) {
        RolesModel.GetInstance();
        if (RolesModel.get_reports()) {
            if (this.isSettingFileUpdated()) {
                this.startActivityForResult(new Intent((Context)this, Reports.class), 0);
            }
            return;
        }
        UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
    }

    public void reprint(View view) {
        RolesModel.GetInstance();
        if (RolesModel.get_reprintOrder()) {
            if (this.isSettingFileUpdated()) {
                this.alertforreprint();
            }
            return;
        }
        UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
    }

    public void reprintorder(String string2) {
        Intent intent = new Intent((Context)this, Reprint.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    public void searchitem(View view) {
        this.startActivityForResult(new Intent((Context)this, SearchItem.class), 0);
    }

}

