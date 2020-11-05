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
 *  android.widget.LinearLayout
 *  android.widget.TextView
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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neoAntara.newrestaurant.AddItem;
import com.neoAntara.newrestaurant.ChangePassword;
import com.neoAntara.newrestaurant.CreateUser;
import com.neoAntara.newrestaurant.CreditTransaction;
import com.neoAntara.newrestaurant.DayEnd;
import com.neoAntara.newrestaurant.EditOrder;
import com.neoAntara.newrestaurant.ExportDB;
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.ImportDB;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;

public class Help
extends Activity {
    private LinearLayout LPrimary = null;
    private LinearLayout LSecondary = null;
    private TextView txtLoginDetails = null;
    private TextView txtUsername = null;

    public void alertforedit() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            Help.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            Help.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Help.this.editorder("HD");
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
                            Help.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            Help.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Help.this.reprintorder("HD");
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
                    Help.this.go();
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
                            Help.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            Help.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Help.this.parcelorder("HD");
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
                    Help.this.exportDb();
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

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            this.setContentView(2131296288);
            this.LPrimary = (LinearLayout)this.findViewById(2131165294);
            this.LSecondary = (LinearLayout)this.findViewById(2131165295);
            this.LPrimary.setVisibility(8);
            this.LSecondary.setVisibility(8);
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
            case 2131165310: {
                UtilityHelper.Logout();
                this.finish();
                return true;
            }
            case 2131165278: 
        }
        this.startActivityForResult(new Intent((Context)this, Help.class), 0);
        return true;
    }

    public void parcelorder(String string2) {
        Intent intent = new Intent((Context)this, NewOrder.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    public void primaryDetails_click(View view) {
        RolesModel.GetInstance();
        if (RolesModel.get_loggedInUser().equalsIgnoreCase("admin")) {
            if (this.LPrimary.getVisibility() == 0) {
                this.LPrimary.setVisibility(8);
                return;
            }
            this.LPrimary.setVisibility(0);
            return;
        }
        UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have right to access this section");
    }

    public void reprintorder(String string2) {
        Intent intent = new Intent((Context)this, Reprint.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    public void secondaryDetails_click(View view) {
        if (this.LSecondary.getVisibility() == 0) {
            this.LSecondary.setVisibility(8);
            return;
        }
        this.LSecondary.setVisibility(0);
    }

}

