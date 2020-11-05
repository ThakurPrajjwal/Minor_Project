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
 *  android.database.SQLException
 *  android.os.Bundle
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.CheckBox
 *  java.lang.Boolean
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
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import com.neoAntara.newrestaurant.AddItem;
import com.neoAntara.newrestaurant.ChangePassword;
import com.neoAntara.newrestaurant.CreateUser;
import com.neoAntara.newrestaurant.CreditTransaction;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.DayEnd;
import com.neoAntara.newrestaurant.ExportDB;
import com.neoAntara.newrestaurant.Help;
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UtilityHelper;

public class UserRights
extends Activity {
    private CheckBox chkAccessRights;
    private CheckBox chkAddItem;
    private CheckBox chkChangePaswd;
    private CheckBox chkDayEnd;
    private CheckBox chkDebitAmt;
    private CheckBox chkDeleteItem;
    private CheckBox chkEditItem;
    private CheckBox chkEditOrder;
    private CheckBox chkExportDB;
    private CheckBox chkNewOrder;
    private CheckBox chkReports;
    private CheckBox chkReprint;
    private CheckBox chkSettings;
    private DBHelper dbhelper = null;

    private int convertBoolToInt(Boolean bl) {
        return bl.compareTo(Boolean.valueOf((boolean)false));
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
                    UserRights.this.go();
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
                    UserRights.this.exportDb();
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

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            this.setContentView(2131296296);
            this.dbhelper = new DBHelper((Context)this, null);
            this.chkAddItem = (CheckBox)this.findViewById(2131165236);
            this.chkEditItem = (CheckBox)this.findViewById(2131165241);
            this.chkDeleteItem = (CheckBox)this.findViewById(2131165240);
            this.chkNewOrder = (CheckBox)this.findViewById(2131165244);
            this.chkEditOrder = (CheckBox)this.findViewById(2131165242);
            this.chkReprint = (CheckBox)this.findViewById(2131165246);
            this.chkReports = (CheckBox)this.findViewById(2131165245);
            this.chkDebitAmt = (CheckBox)this.findViewById(2131165239);
            this.chkChangePaswd = (CheckBox)this.findViewById(2131165237);
            this.chkDayEnd = (CheckBox)this.findViewById(2131165238);
            this.chkExportDB = (CheckBox)this.findViewById(2131165243);
            this.chkSettings = (CheckBox)this.findViewById(2131165247);
            this.dbhelper.getUserRoles("user1");
            CheckBox checkBox = this.chkAddItem;
            RolesModel.GetInstance();
            checkBox.setChecked(RolesModel.get_addItem());
            CheckBox checkBox2 = this.chkEditItem;
            RolesModel.GetInstance();
            checkBox2.setChecked(RolesModel.get_editItem());
            CheckBox checkBox3 = this.chkDeleteItem;
            RolesModel.GetInstance();
            checkBox3.setChecked(RolesModel.get_deleteItem());
            CheckBox checkBox4 = this.chkNewOrder;
            RolesModel.GetInstance();
            checkBox4.setChecked(RolesModel.get_newOrder());
            CheckBox checkBox5 = this.chkEditOrder;
            RolesModel.GetInstance();
            checkBox5.setChecked(RolesModel.get_editOrder());
            CheckBox checkBox6 = this.chkReprint;
            RolesModel.GetInstance();
            checkBox6.setChecked(RolesModel.get_reprintOrder());
            CheckBox checkBox7 = this.chkReports;
            RolesModel.GetInstance();
            checkBox7.setChecked(RolesModel.get_reports());
            CheckBox checkBox8 = this.chkDebitAmt;
            RolesModel.GetInstance();
            checkBox8.setChecked(RolesModel.get_debitAmount());
            CheckBox checkBox9 = this.chkChangePaswd;
            RolesModel.GetInstance();
            checkBox9.setChecked(RolesModel.get_changePassword());
            CheckBox checkBox10 = this.chkDayEnd;
            RolesModel.GetInstance();
            checkBox10.setChecked(RolesModel.get_dayEnd());
            CheckBox checkBox11 = this.chkExportDB;
            RolesModel.GetInstance();
            checkBox11.setChecked(RolesModel.get_exportDB());
            CheckBox checkBox12 = this.chkSettings;
            RolesModel.GetInstance();
            checkBox12.setChecked(RolesModel.get_updateSetting());
            this.dbhelper.getUserRoles("admin");
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

    public void saveUserRights(View view) {
        try {
            int n = this.convertBoolToInt(this.chkAddItem.isChecked());
            int n2 = this.convertBoolToInt(this.chkEditItem.isChecked());
            int n3 = this.convertBoolToInt(this.chkDeleteItem.isChecked());
            int n4 = this.convertBoolToInt(this.chkNewOrder.isChecked());
            int n5 = this.convertBoolToInt(this.chkEditOrder.isChecked());
            int n6 = this.convertBoolToInt(this.chkReprint.isChecked());
            int n7 = this.convertBoolToInt(this.chkReports.isChecked());
            int n8 = this.convertBoolToInt(this.chkDayEnd.isChecked());
            int n9 = this.convertBoolToInt(this.chkDebitAmt.isChecked());
            int n10 = this.convertBoolToInt(this.chkChangePaswd.isChecked());
            int n11 = this.convertBoolToInt(this.chkExportDB.isChecked());
            int n12 = this.convertBoolToInt(this.chkSettings.isChecked());
            this.dbhelper.updateUserRights(n, n2, n3, n4, n5, n6, n7, n8, n10, n9, n12, n11);
            UtilityHelper.showToastMessage(this.getApplicationContext(), "Rights saved successfuly");
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

}

