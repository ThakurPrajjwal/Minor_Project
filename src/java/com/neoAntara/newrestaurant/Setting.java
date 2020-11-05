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
 *  android.database.SQLException
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
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
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.ImportDB;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;

public class Setting
extends Activity {
    EditText address = null;
    Button btn = null;
    EditText cgst = null;
    Cursor cur;
    EditText gstn_number = null;
    DBHelper objdbhelper;
    EditText opening_bal = null;
    EditText phone_no = null;
    EditText resto_name = null;
    EditText service_charge = null;
    EditText sgst = null;
    EditText website = null;

    public void alertforedit() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            Setting.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            Setting.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Setting.this.editorder("HD");
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
                            Setting.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            Setting.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Setting.this.reprintorder("HD");
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
                    Setting.this.go();
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
                            Setting.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            Setting.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Setting.this.parcelorder("HD");
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
                    Setting.this.exportDb();
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
                    Setting.this.importDb();
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
    public void newresto(View view) {
        try {
            if (this.btn.getText().toString().matches("Update")) {
                if (this.resto_name.getText().toString().matches("") || this.resto_name.getText().equals(null)) {
                    Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Restaurant Name", (int)0);
                    toast.setGravity(17, 0, 0);
                    toast.show();
                    this.resto_name.setText((CharSequence)"");
                    return;
                }
                if (this.address.getText().toString().matches("") || this.address.getText().equals(null)) {
                    Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Address", (int)0);
                    toast.setGravity(17, 0, 0);
                    toast.show();
                    this.address.setText((CharSequence)"");
                    return;
                }
                this.objdbhelper.UpdateResto(this.resto_name.getText().toString(), this.address.getText().toString(), this.phone_no.getText().toString(), this.website.getText().toString(), this.cgst.getText().toString(), this.sgst.getText().toString(), this.service_charge.getText().toString(), this.opening_bal.getText().toString(), this.gstn_number.getText().toString());
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Restaurant Updated Successfully", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.startActivityForResult(new Intent((Context)this, Home.class), 0);
                return;
            }
            if (this.resto_name.getText().toString().matches("") || this.resto_name.getText().equals(null)) {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Restaurant Name", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.resto_name.setText((CharSequence)"");
                return;
            }
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
        if (this.address.getText().toString().matches("") || this.address.getText().equals(null)) {
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Address", (int)0);
            toast.setGravity(17, 0, 0);
            toast.show();
            this.address.setText((CharSequence)"");
            return;
        }
        this.objdbhelper.CreateResto(this.resto_name.getText().toString(), this.address.getText().toString(), this.phone_no.getText().toString(), this.website.getText().toString(), this.cgst.getText().toString(), this.sgst.getText().toString(), this.service_charge.getText().toString(), this.opening_bal.getText().toString(), this.gstn_number.getText().toString());
        Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Restaurant Created Successfully", (int)0);
        toast.setGravity(17, 0, 0);
        toast.show();
        if (this.btn.getText().equals((Object)"Save")) {
            this.objdbhelper.sendEmail_Settings(this.resto_name.getText().toString(), this.phone_no.getText().toString(), this.address.getText().toString(), this.website.getText().toString(), this.gstn_number.getText().toString());
        }
        this.btn.setText((CharSequence)"Update");
        this.startActivityForResult(new Intent((Context)this, Home.class), 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            this.setContentView(2131296295);
            this.objdbhelper = new DBHelper((Context)this, null);
            this.resto_name = (EditText)this.findViewById(2131165430);
            this.address = (EditText)this.findViewById(2131165408);
            this.phone_no = (EditText)this.findViewById(2131165423);
            this.website = (EditText)this.findViewById(2131165436);
            this.cgst = (EditText)this.findViewById(2131165411);
            this.sgst = (EditText)this.findViewById(2131165431);
            this.service_charge = (EditText)this.findViewById(2131165434);
            this.gstn_number = (EditText)this.findViewById(2131165414);
            this.opening_bal = (EditText)this.findViewById(2131165419);
            this.resto_name.setTextColor(-16777216);
            this.address.setTextColor(-16777216);
            this.phone_no.setTextColor(-16777216);
            this.website.setTextColor(-16777216);
            this.cgst.setTextColor(-16777216);
            this.sgst.setTextColor(-16777216);
            this.service_charge.setTextColor(-16777216);
            this.gstn_number.setTextColor(-16777216);
            this.opening_bal.setTextColor(-16777216);
            this.resto_name.setHintTextColor(-7829368);
            this.address.setHintTextColor(-7829368);
            this.phone_no.setHintTextColor(-7829368);
            this.website.setHintTextColor(-7829368);
            this.cgst.setHintTextColor(-7829368);
            this.sgst.setHintTextColor(-7829368);
            this.service_charge.setHintTextColor(-7829368);
            this.gstn_number.setHintTextColor(-7829368);
            this.opening_bal.setHintTextColor(-7829368);
            this.btn = (Button)this.findViewById(2131165375);
            this.cur = this.objdbhelper.getRestoDetails();
            this.cur.moveToFirst();
            if (this.cur.getCount() == 1) {
                this.resto_name.setText((CharSequence)this.cur.getString(0));
                this.address.setText((CharSequence)this.cur.getString(1));
                this.phone_no.setText((CharSequence)this.cur.getString(2));
                this.website.setText((CharSequence)this.cur.getString(3));
                this.cgst.setText((CharSequence)this.cur.getString(4));
                this.sgst.setText((CharSequence)this.cur.getString(5));
                this.service_charge.setText((CharSequence)this.cur.getString(6));
                this.opening_bal.setText((CharSequence)this.cur.getString(7));
                this.gstn_number.setText((CharSequence)this.cur.getString(8));
                this.btn.setText((CharSequence)"Update");
            } else {
                this.resto_name.setText((CharSequence)"");
                this.address.setText((CharSequence)"");
                this.phone_no.setText((CharSequence)"");
                this.website.setText((CharSequence)"");
                this.cgst.setText((CharSequence)"");
                this.service_charge.setText((CharSequence)"");
                this.gstn_number.setText((CharSequence)"");
                this.sgst.setText((CharSequence)"");
                this.opening_bal.setText((CharSequence)"");
                this.btn.setText((CharSequence)"Save");
            }
            this.service_charge.setVisibility(8);
            return;
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
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
            case 2131165278: {
                this.startActivityForResult(new Intent((Context)this, Help.class), 0);
                return true;
            }
            case 2131165234: {
                RolesModel.GetInstance();
                if (RolesModel.get_changePassword()) {
                    this.startActivityForResult(new Intent((Context)this, ChangePassword.class), 0);
                    return true;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
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
                RolesModel.GetInstance();
                if (RolesModel.get_dayEnd()) {
                    this.alertmessage();
                    return true;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return true;
            }
            case 2131165253: {
                RolesModel.GetInstance();
                if (RolesModel.get_debitAmount()) {
                    this.startActivityForResult(new Intent((Context)this, CreditTransaction.class), 0);
                    return true;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return true;
            }
            case 2131165359: {
                RolesModel.GetInstance();
                if (RolesModel.get_updateSetting()) {
                    this.startActivityForResult(new Intent((Context)this, Setting.class), 0);
                    return true;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
                return true;
            }
            case 2131165276: {
                RolesModel.GetInstance();
                if (RolesModel.get_exportDB()) {
                    this.exportdb();
                    return true;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
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

