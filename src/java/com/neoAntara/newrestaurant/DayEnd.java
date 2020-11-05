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
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.neoAntara.newrestaurant.AddItem;
import com.neoAntara.newrestaurant.ChangePassword;
import com.neoAntara.newrestaurant.CreateUser;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.EditOrder;
import com.neoAntara.newrestaurant.ExportDB;
import com.neoAntara.newrestaurant.Help;
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.ImportDB;
import com.neoAntara.newrestaurant.MainActivity;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;

public class DayEnd
extends Activity {
    DBHelper ObjDBHelp;
    float open_bal = 0.0f;
    float openingbal = 0.0f;
    float st = 0.0f;
    String str_opening_bal;
    String str_st;
    String str_vat;
    float total_item_price = 0.0f;
    float vat = 0.0f;

    public void alertforedit() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            DayEnd.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            DayEnd.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    DayEnd.this.editorder("HD");
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
                            DayEnd.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            DayEnd.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    DayEnd.this.reprintorder("HD");
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
                    DayEnd.this.go();
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
                            DayEnd.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            DayEnd.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    DayEnd.this.parcelorder("HD");
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
                    DayEnd.this.exportDb();
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
                    DayEnd.this.importDb();
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
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle bundle) {
        String string2;
        Cursor cursor;
        String string3;
        float f;
        block14 : {
            String string4;
            block13 : {
                try {
                    DBHelper dBHelper;
                    super.onCreate(bundle);
                    this.setContentView(2131296286);
                    this.ObjDBHelp = dBHelper = new DBHelper((Context)this, null);
                    string3 = UtilityHelper.getCurrentDate();
                    Cursor cursor2 = this.ObjDBHelp.getRestoDetails();
                    cursor2.moveToFirst();
                    if (cursor2.getCount() > 0) {
                        this.str_st = cursor2.getString(4);
                        this.str_vat = cursor2.getString(6);
                        this.str_opening_bal = cursor2.getString(7);
                        this.openingbal = this.str_opening_bal.isEmpty() ? 0.0f : Float.parseFloat((String)this.str_opening_bal);
                        cursor2.close();
                    } else {
                        Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Insert Restaurant details into setting file", (int)1);
                        toast.setGravity(17, 0, 0);
                        toast.show();
                        this.startActivityForResult(new Intent((Context)this, MainActivity.class), 0);
                        this.finish();
                    }
                    cursor = this.ObjDBHelp.getItemPriceTotal();
                    cursor.moveToFirst();
                    String string5 = cursor.getString(0);
                    string4 = cursor.getString(1);
                    string2 = cursor.getString(2);
                    f = Float.parseFloat((String)string5);
                    cursor.close();
                    if (string4 != null && !string4.matches("")) break block13;
                    break block14;
                }
                catch (Exception exception) {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
                    this.startActivityForResult(new Intent((Context)this, MainActivity.class), 0);
                    this.finish();
                    return;
                }
            }
            f -= Float.parseFloat((String)string4);
        }
        if (string2 != null && !string2.matches("")) {
            f += Float.parseFloat((String)string2);
        }
        String string6 = this.ObjDBHelp.getBusinessDate();
        Cursor cursor3 = this.ObjDBHelp.getTotalCrAmount(string6);
        cursor3.moveToFirst();
        float f2 = Float.parseFloat((String)cursor3.getString(0));
        cursor.close();
        if (this.str_vat.matches("") && this.str_st.matches("")) {
            this.open_bal = f + this.openingbal;
        } else if (this.str_st.matches("") && this.str_vat != "") {
            this.vat = Float.parseFloat((String)this.str_vat);
            this.total_item_price = f + f * (this.vat / 100.0f);
            this.open_bal = this.total_item_price + this.openingbal;
        } else if (this.str_vat.matches("") && this.str_st != "") {
            this.st = Float.parseFloat((String)this.str_st);
            this.total_item_price = f + f * (this.st / 100.0f);
            this.open_bal = this.total_item_price + this.openingbal;
        } else if (this.str_vat != "" && this.str_st != "") {
            this.st = Float.parseFloat((String)this.str_st);
            this.vat = Float.parseFloat((String)this.str_vat);
            float f3 = f * (this.vat / 100.0f);
            this.total_item_price = f * (this.st / 100.0f) + (f + f3);
            this.open_bal = this.total_item_price + this.openingbal;
        }
        this.open_bal -= f2;
        String string7 = this.open_bal + "";
        this.ObjDBHelp.UpdateOpeningBal(string7);
        this.ObjDBHelp.updatebusinessdate(string3);
        this.ObjDBHelp.transferorderstohistory(string3);
        Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"DayEnd done successfully.", (int)1);
        toast.setGravity(17, 0, 0);
        toast.show();
        this.startActivityForResult(new Intent((Context)this, MainActivity.class), 0);
        this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131361792, menu2);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131165280: {
                this.startActivityForResult(new Intent((Context)this, Home.class), 0);
                return true;
            }
            case 2131165252: {
                this.startActivityForResult(new Intent((Context)this, CreateUser.class), 0);
                this.finish();
                return true;
            }
            case 2131165234: {
                this.startActivityForResult(new Intent((Context)this, ChangePassword.class), 0);
                return true;
            }
            case 2131165443: {
                RolesModel.GetInstance();
                if (RolesModel.get_accessRights()) {
                    this.startActivityForResult(new Intent((Context)this, UserRights.class), 0);
                    return true;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return true;
            }
            case 2131165278: {
                this.startActivityForResult(new Intent((Context)this, Help.class), 0);
                return true;
            }
            case 2131165205: {
                this.startActivityForResult(new Intent((Context)this, AddItem.class), 0);
                return true;
            }
            case 2131165355: {
                this.startActivityForResult(new Intent((Context)this, SearchItem.class), 0);
                return true;
            }
            case 2131165320: {
                this.alertpopup();
                return true;
            }
            case 2131165270: {
                this.alertforedit();
                return true;
            }
            case 2131165337: {
                this.alertforreprint();
                return true;
            }
            case 2131165336: {
                this.startActivityForResult(new Intent((Context)this, Reports.class), 0);
                return true;
            }
            case 2131165258: {
                this.alertmessage();
                return true;
            }
            case 2131165359: {
                this.startActivityForResult(new Intent((Context)this, Setting.class), 0);
                return true;
            }
            case 2131165310: 
        }
        UtilityHelper.Logout();
        this.finish();
        return true;
    }

    public void parcelorder(String string2) {
        Intent intent = new Intent((Context)this, NewOrder.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    public void reprintorder(String string2) {
        Intent intent = new Intent((Context)this, Reprint.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

}

