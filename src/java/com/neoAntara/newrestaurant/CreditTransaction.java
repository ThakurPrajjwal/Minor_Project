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
 *  android.graphics.Typeface
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.GradientDrawable$Orientation
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.TableLayout
 *  android.widget.TableRow
 *  android.widget.TableRow$LayoutParams
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Float
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
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.neoAntara.newrestaurant.AddItem;
import com.neoAntara.newrestaurant.ChangePassword;
import com.neoAntara.newrestaurant.CreateUser;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.DayEnd;
import com.neoAntara.newrestaurant.EditOrder;
import com.neoAntara.newrestaurant.ExportDB;
import com.neoAntara.newrestaurant.Help;
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.ImportDB;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.MyColorConstants;
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;

public class CreditTransaction
extends Activity {
    DBHelper ObjdbHelp;
    Button btn = null;
    EditText credit_bal = null;
    private TextView date = null;
    EditText descripation = null;
    String dt = "";
    GradientDrawable grd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{-11908534, -6737101});
    String main_bal = null;
    private TextView opening_bal = null;
    private TextView resto_name = null;
    TableLayout tablelayout = null;
    private TableRow tr = null;
    private TextView txtv_SrNo = null;
    private TextView txtv_cramt = null;
    private TextView txtv_descr = null;
    private TextView txtv_sno = null;

    public void addCreditBal(View view) {
        try {
            if (this.credit_bal.getText().toString().matches("")) {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Credit Balance", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                return;
            }
            float f = Float.parseFloat((String)this.main_bal) - Float.parseFloat((String)this.credit_bal.getText().toString());
            this.ObjdbHelp.UpdateOpeningBalFromCreditTable(f + "", this.credit_bal.getText().toString(), this.descripation.getText().toString(), this.dt);
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Amount Credited Successfully", (int)0);
            toast.setGravity(17, 0, 0);
            toast.show();
            this.credit_bal.setText((CharSequence)"");
            this.descripation.setText((CharSequence)"");
            this.setTableContent();
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

    public void alertforedit() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            CreditTransaction.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            CreditTransaction.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    CreditTransaction.this.editorder("HD");
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
                            CreditTransaction.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            CreditTransaction.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    CreditTransaction.this.reprintorder("HD");
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
                    CreditTransaction.this.go();
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
                            CreditTransaction.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            CreditTransaction.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    CreditTransaction.this.parcelorder("HD");
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
                    CreditTransaction.this.exportDb();
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
                    CreditTransaction.this.importDb();
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

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            this.setContentView(2131296285);
            this.ObjdbHelp = new DBHelper((Context)this, null);
            this.tablelayout = (TableLayout)this.findViewById(2131165381);
            this.dt = this.ObjdbHelp.getBusinessDate();
            this.date = (TextView)this.findViewById(2131165257);
            this.date.setText((CharSequence)this.dt);
            Cursor cursor = this.ObjdbHelp.getRestoDetails();
            cursor.moveToFirst();
            if (cursor.getCount() == 1) {
                String string2 = cursor.getString(0);
                this.resto_name = (TextView)this.findViewById(2131165338);
                this.resto_name.setTextColor(Color.rgb((int)153, (int)51, (int)51));
                this.resto_name.setBackgroundResource(2131099732);
                this.resto_name.setPadding(10, 5, 10, 5);
                this.resto_name.setText((CharSequence)string2);
                this.main_bal = cursor.getString(7);
                this.opening_bal = (TextView)this.findViewById(2131165311);
                this.opening_bal.setText((CharSequence)("Opening Balance(Rs.) : " + this.main_bal));
                this.opening_bal.setTextColor(-16777216);
                this.opening_bal.setTextSize(18.0f);
                cursor.close();
                this.credit_bal = (EditText)this.findViewById(2131165251);
                this.descripation = (EditText)this.findViewById(2131165261);
                this.credit_bal.setHintTextColor(-7829368);
                this.descripation.setHintTextColor(-7829368);
                this.setTableContent();
                return;
            }
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Insert Restaurant details into Setting file", (int)1);
            toast.setGravity(17, 0, 0);
            toast.show();
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setTableContent() {
        block7 : {
            this.tablelayout.removeAllViews();
            this.setup();
            int n = 1;
            Cursor cursor = this.ObjdbHelp.getCrAmtDetails(this.dt);
            cursor.moveToFirst();
            if (cursor.getCount() <= 0) break block7;
            int n2 = 0;
            do {
                if (n2 >= cursor.getCount()) return;
                this.tr = new TableRow((Context)this);
                this.txtv_cramt = new TextView((Context)this);
                this.txtv_cramt.setText((CharSequence)cursor.getString(2));
                this.txtv_cramt.setTextColor(-16777216);
                this.txtv_cramt.setTypeface(Typeface.DEFAULT, 0);
                this.txtv_cramt.setPadding(10, 10, 10, 10);
                this.txtv_cramt.setTextSize(16.0f);
                this.tr.addView((View)this.txtv_cramt);
                this.txtv_descr = new TextView((Context)this);
                this.txtv_descr.setText((CharSequence)cursor.getString(3));
                this.txtv_descr.setTextColor(-16777216);
                this.txtv_descr.setTypeface(Typeface.DEFAULT, 0);
                this.txtv_descr.setPadding(10, 10, 10, 10);
                this.txtv_descr.setTextSize(16.0f);
                this.tr.addView((View)this.txtv_descr);
                this.txtv_sno = new TextView((Context)this);
                this.txtv_sno.setText((CharSequence)cursor.getString(0));
                this.txtv_sno.setTextColor(-16777216);
                this.tr.addView((View)this.txtv_sno);
                this.txtv_sno.setVisibility(4);
                this.txtv_sno.setPadding(10, 10, 10, 10);
                this.btn = new Button((Context)this);
                this.btn.setId(n2);
                this.btn.setBackgroundResource(2131099777);
                this.btn.setHighlightColor(17170443);
                this.btn.setClickable(true);
                this.btn.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(30, 30));
                this.btn.setPadding(20, 0, 20, 0);
                this.tr.addView((View)this.btn);
                this.btn.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        int n = Integer.parseInt((String)((TextView)((TableRow)view.getParent()).getChildAt(2)).getText().toString());
                        CreditTransaction.this.ObjdbHelp.deletefromCrAmount(n);
                        CreditTransaction.this.tablelayout.removeView((View)view.getParent());
                        Toast toast = Toast.makeText((Context)CreditTransaction.this.getApplicationContext(), (CharSequence)"Record deleted successfully", (int)0);
                        toast.setGravity(17, 0, 0);
                        toast.show();
                    }
                });
                this.tr.setBackgroundColor(MyColorConstants.LIST_BACK_COLOR);
                this.tablelayout.addView((View)this.tr);
                ++n;
                cursor.moveToNext();
                ++n2;
            } while (true);
        }
        try {
            this.tablelayout.removeAllViews();
            this.setup();
            this.credit_bal.setText((CharSequence)"");
            this.descripation.setText((CharSequence)"");
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    protected void setup() {
        this.tablelayout.removeAllViews();
        this.tr = new TableRow((Context)this);
        this.txtv_cramt = new TextView((Context)this);
        this.txtv_cramt.setText((CharSequence)"Debit Amount");
        this.txtv_cramt.setTextColor(-1);
        this.txtv_cramt.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_cramt.setPadding(0, 2, 2, 0);
        this.txtv_cramt.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_cramt);
        this.txtv_descr = new TextView((Context)this);
        this.txtv_descr.setText((CharSequence)"Description");
        this.txtv_descr.setTextColor(-1);
        this.txtv_descr.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_descr.setPadding(1, 2, 2, 0);
        this.txtv_descr.setTextSize(16.0f);
        this.txtv_sno = new TextView((Context)this);
        this.txtv_sno.setText((CharSequence)"");
        this.txtv_sno.setVisibility(4);
        this.txtv_sno.setPadding(1, 2, 2, 0);
        this.txtv_sno.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_descr);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
    }

}

