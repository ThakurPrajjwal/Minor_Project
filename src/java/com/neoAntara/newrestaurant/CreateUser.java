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
 *  android.widget.ArrayAdapter
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.neoAntara.newrestaurant.AddItem;
import com.neoAntara.newrestaurant.ChangePassword;
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

public class CreateUser
extends Activity {
    EditText address = null;
    EditText address_proof = null;
    EditText birthdate = null;
    EditText city = null;
    EditText id_proof = null;
    Spinner isactive = null;
    EditText mobile_no = null;
    DBHelper objdbhelper;
    EditText password = null;
    EditText phone_no = null;
    EditText pincode = null;
    EditText reffered = null;
    EditText state = null;
    EditText user_name = null;

    public void alertforedit() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            CreateUser.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            CreateUser.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    CreateUser.this.editorder("HD");
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
                            CreateUser.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            CreateUser.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    CreateUser.this.reprintorder("HD");
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
                    CreateUser.this.go();
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
                            CreateUser.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            CreateUser.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    CreateUser.this.parcelorder("HD");
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
                    CreateUser.this.exportDb();
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
                    CreateUser.this.importDb();
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
    public void newuser_click(View view) {
        try {
            if (this.user_name.getText().toString().matches("") || this.user_name.getText().equals(null)) {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter User Name", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.user_name.setText((CharSequence)"");
                return;
            }
            if (this.password.getText().toString().matches("") || this.password.getText().equals(null)) {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Password", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.password.setText((CharSequence)"");
                return;
            }
            if (this.address.getText().toString().matches("") || this.address.getText().equals(null)) {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Address", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.address.setText((CharSequence)"");
                return;
            }
            if (this.mobile_no.getText().toString().matches("") || this.mobile_no.getText().equals(null)) {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Mobile Number", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.mobile_no.setText((CharSequence)"");
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
        if (!this.pincode.getText().toString().matches("") && !this.pincode.getText().equals(null)) {
            this.objdbhelper.CreateUser(this.user_name.getText().toString(), this.password.getText().toString(), this.address.getText().toString(), this.city.getText().toString(), this.state.getText().toString(), this.pincode.getText().toString(), this.phone_no.getText().toString(), this.mobile_no.getText().toString(), this.birthdate.getText().toString(), this.id_proof.getText().toString(), this.address_proof.getText().toString(), this.reffered.getText().toString(), this.isactive.getSelectedItem().toString());
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"User Created Successfully", (int)0);
            toast.setGravity(17, 0, 0);
            toast.show();
            this.user_name.setText((CharSequence)"");
            this.password.setText((CharSequence)"");
            this.address.setText((CharSequence)"");
            this.city.setText((CharSequence)"");
            this.state.setText((CharSequence)"");
            this.pincode.setText((CharSequence)"");
            this.phone_no.setText((CharSequence)"");
            this.mobile_no.setText((CharSequence)"");
            this.birthdate.setText((CharSequence)"");
            this.id_proof.setText((CharSequence)"");
            this.address_proof.setText((CharSequence)"");
            this.reffered.setText((CharSequence)"");
            return;
        }
        Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Pincode Number", (int)0);
        toast.setGravity(17, 0, 0);
        toast.show();
        this.pincode.setText((CharSequence)"");
    }

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            this.setContentView(2131296284);
            this.objdbhelper = new DBHelper((Context)this, null);
            this.user_name = (EditText)this.findViewById(2131165418);
            this.password = (EditText)this.findViewById(2131165422);
            this.address = (EditText)this.findViewById(2131165408);
            this.city = (EditText)this.findViewById(2131165412);
            this.state = (EditText)this.findViewById(2131165435);
            this.pincode = (EditText)this.findViewById(2131165424);
            this.phone_no = (EditText)this.findViewById(2131165423);
            this.mobile_no = (EditText)this.findViewById(2131165417);
            this.birthdate = (EditText)this.findViewById(2131165410);
            this.id_proof = (EditText)this.findViewById(2131165415);
            this.address_proof = (EditText)this.findViewById(2131165409);
            this.reffered = (EditText)this.findViewById(2131165429);
            this.isactive = (Spinner)this.findViewById(2131165364);
            ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])new String[]{"yes", "no"});
            arrayAdapter.setDropDownViewResource(17367050);
            this.isactive.setAdapter((SpinnerAdapter)arrayAdapter);
            this.user_name.setHintTextColor(-7829368);
            this.password.setHintTextColor(-7829368);
            this.address.setHintTextColor(-7829368);
            this.city.setHintTextColor(-7829368);
            this.state.setHintTextColor(-7829368);
            this.pincode.setHintTextColor(-7829368);
            this.phone_no.setHintTextColor(-7829368);
            this.mobile_no.setHintTextColor(-7829368);
            this.birthdate.setHintTextColor(-7829368);
            this.id_proof.setHintTextColor(-7829368);
            this.address_proof.setHintTextColor(-7829368);
            this.reffered.setHintTextColor(-7829368);
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
            case 2131165234: {
                this.startActivityForResult(new Intent((Context)this, ChangePassword.class), 0);
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
            case 2131165253: {
                this.startActivityForResult(new Intent((Context)this, CreditTransaction.class), 0);
                return true;
            }
            case 2131165359: {
                this.startActivityForResult(new Intent((Context)this, Setting.class), 0);
                return true;
            }
            case 2131165276: {
                this.exportdb();
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

