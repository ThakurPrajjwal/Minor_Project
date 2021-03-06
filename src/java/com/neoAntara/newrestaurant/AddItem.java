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
 *  android.graphics.Color
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
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
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
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
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;

public class AddItem
extends Activity {
    DBHelper ObjDBHelp;
    private Button btnAddItem;
    private TextView date = null;
    String food_catgory = null;
    private Spinner foodcatgory = null;
    String is_active = null;
    private Spinner isactive = null;
    String item_code = null;
    private EditText itemname = null;
    private EditText prize = null;
    private TextView resto_name = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void additem_click(View view) {
        try {
            if (this.btnAddItem.getText().toString().matches("Update")) {
                if (this.itemname.getText().toString().matches("") || this.itemname.getText().equals(null)) {
                    Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Item Name", (int)0);
                    toast.setGravity(17, 0, 0);
                    toast.show();
                    this.itemname.setText((CharSequence)"");
                    return;
                }
                if (this.prize.getText().toString().matches("") || this.prize.getText().equals(null)) {
                    Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Item Prize", (int)0);
                    toast.setGravity(17, 0, 0);
                    toast.show();
                    this.prize.setText((CharSequence)"");
                    return;
                }
                int n = Integer.parseInt((String)this.item_code);
                this.ObjDBHelp.UpdateItemInItemMaster(this.itemname.getText().toString(), this.prize.getText().toString(), this.isactive.getSelectedItem().toString(), n);
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Item Updated Successfully", (int)1);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.prize.setText((CharSequence)"");
                this.itemname.setText((CharSequence)"");
                this.finish();
                this.startActivityForResult(new Intent((Context)this, SearchItem.class), 0);
                return;
            }
            if (this.itemname.getText().toString().matches("") || this.itemname.getText().equals(null)) {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Item Name", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.itemname.setText((CharSequence)"");
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
        if (this.prize.getText().toString().matches("") || this.prize.getText().equals(null)) {
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Item Prize", (int)0);
            toast.setGravity(17, 0, 0);
            toast.show();
            this.prize.setText((CharSequence)"");
            return;
        }
        int n = this.ObjDBHelp.InsertIntoItemMaster(this.itemname.getText().toString(), this.prize.getText().toString(), this.isactive.getSelectedItem().toString());
        if (n > 0) {
            UtilityHelper.showToastMessage(this.getApplicationContext(), "Item already exist.");
        } else if (n == 0) {
            UtilityHelper.showToastMessage(this.getApplicationContext(), "Item Added Successfully.");
        } else {
            UtilityHelper.showToastMessage(this.getApplicationContext(), "Error occurred while adding an Item.");
        }
        this.prize.setText((CharSequence)"");
        this.itemname.setText((CharSequence)"");
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
                            AddItem.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            AddItem.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    AddItem.this.editorder("HD");
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
                            AddItem.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            AddItem.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    AddItem.this.reprintorder("HD");
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
                    AddItem.this.go();
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
                            AddItem.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            AddItem.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    AddItem.this.parcelorder("HD");
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
                    AddItem.this.exportDb();
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
                    AddItem.this.importDb();
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
        super.onCreate(bundle);
        this.setContentView(2131296281);
        try {
            DBHelper dBHelper;
            this.ObjDBHelp = dBHelper = new DBHelper((Context)this, null);
            String string2 = this.ObjDBHelp.getBusinessDate();
            this.date = (TextView)this.findViewById(2131165257);
            this.date.setText((CharSequence)string2);
            Cursor cursor = this.ObjDBHelp.getRestoDetails();
            cursor.moveToFirst();
            if (cursor.getCount() == 1) {
                String string3 = cursor.getString(0);
                this.resto_name = (TextView)this.findViewById(2131165338);
                this.resto_name.setTextColor(Color.rgb((int)153, (int)51, (int)51));
                this.resto_name.setBackgroundResource(2131099732);
                this.resto_name.setPadding(10, 5, 10, 5);
                this.resto_name.setText((CharSequence)string3);
            } else {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Insert Restaurant details into setting file", (int)1);
                toast.setGravity(17, 0, 0);
                toast.show();
            }
            cursor.close();
            this.itemname = (EditText)this.findViewById(2131165416);
            this.prize = (EditText)this.findViewById(2131165427);
            this.btnAddItem = (Button)this.findViewById(2131165227);
            this.itemname.setHintTextColor(-7829368);
            this.prize.setHintTextColor(-7829368);
            this.isactive = (Spinner)this.findViewById(2131165364);
            ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])new String[]{"yes", "no"});
            arrayAdapter.setDropDownViewResource(17367050);
            this.isactive.setAdapter((SpinnerAdapter)arrayAdapter);
            String string4 = "";
            Intent intent = this.getIntent();
            if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey("itemname") && intent.getExtras().containsKey("prize")) {
                string4 = intent.getExtras().getString("itemname");
                String[] arrstring = intent.getExtras().getString("prize").split(" ");
                this.itemname.setText((CharSequence)string4);
                this.prize.setText((CharSequence)arrstring[1]);
                this.itemname.setTextColor(-16777216);
                this.prize.setTextColor(-16777216);
            }
            if (string4.matches("")) return;
            if (string4.equals(null)) {
                return;
            }
            this.btnAddItem.setText((CharSequence)"Update");
            Cursor cursor2 = this.ObjDBHelp.getItem_code(string4);
            cursor2.moveToFirst();
            this.item_code = cursor2.getString(0);
            this.is_active = cursor2.getString(1);
            if (!this.is_active.matches("no")) return;
            ArrayAdapter arrayAdapter2 = new ArrayAdapter((Context)this, 17367048, (Object[])new String[]{"no", "yes"});
            arrayAdapter2.setDropDownViewResource(17367050);
            this.isactive.setAdapter((SpinnerAdapter)arrayAdapter2);
            return;
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
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
            case 2131165443: {
                RolesModel.GetInstance();
                if (RolesModel.get_accessRights()) {
                    this.startActivityForResult(new Intent((Context)this, UserRights.class), 0);
                    return true;
                }
                UtilityHelper.showToastMessage(this.getApplicationContext(), "You dont have access to this page");
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

    public void reprintorder(String string2) {
        Intent intent = new Intent((Context)this, Reprint.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

}

