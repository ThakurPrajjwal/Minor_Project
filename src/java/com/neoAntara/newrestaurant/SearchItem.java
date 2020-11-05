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
 *  android.content.res.AssetManager
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
 *  android.widget.ListView
 *  android.widget.Spinner
 *  android.widget.TableLayout
 *  android.widget.TableRow
 *  android.widget.TableRow$LayoutParams
 *  android.widget.TextView
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
import android.content.res.AssetManager;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.ImportDB;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;

public class SearchItem
extends Activity {
    Typeface CustumFont;
    Button btn;
    private TextView date = null;
    EditText item;
    Spinner item_cat;
    String item_catgory;
    String itemname;
    String itemprize;
    View line;
    ListView lv;
    DBHelper objDbHelper;
    private TextView resto_name = null;
    int state = 0;
    TableLayout tb;
    TableRow tr;
    TextView txt_item_name;
    TextView txt_item_prize;

    private void alertDeleteItem(final View view, final TableLayout tableLayout) {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: 
                    }
                    SearchItem.this.deleteItem(view, tableLayout);
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Are you sure to delete this item?");
            builder.setPositiveButton((CharSequence)"Yes", onClickListener);
            builder.setNegativeButton((CharSequence)"No", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    private void deleteItem(View view, TableLayout tableLayout) {
        TextView textView = (TextView)((TableRow)view.getParent()).getChildAt(0);
        this.objDbHelper.deleteitem(textView.getText().toString());
        tableLayout.removeView((View)view.getParent());
        tableLayout.removeView(this.line);
        UtilityHelper.showToastMessage(this.getApplicationContext(), "Item deleted successfully");
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
                            SearchItem.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            SearchItem.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    SearchItem.this.editorder("HD");
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
                            SearchItem.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            SearchItem.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    SearchItem.this.reprintorder("HD");
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
                    SearchItem.this.go();
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
                            SearchItem.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            SearchItem.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    SearchItem.this.parcelorder("HD");
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
                    SearchItem.this.exportDb();
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
                    SearchItem.this.importDb();
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
        try {
            super.onCreate(bundle);
            this.setContentView(2131296294);
            this.item = (EditText)this.findViewById(2131165343);
            this.tb = (TableLayout)this.findViewById(2131165381);
            this.objDbHelper = new DBHelper((Context)this, null);
            String string2 = this.objDbHelper.getBusinessDate();
            this.date = (TextView)this.findViewById(2131165438);
            this.date.setText((CharSequence)string2);
            Cursor cursor = this.objDbHelper.getRestoDetails();
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
            Cursor cursor2 = this.objDbHelper.getItemDetails();
            cursor2.moveToFirst();
            new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{-11908534, -6737101});
            for (int i = 0; i < cursor2.getCount(); ++i) {
                this.tr = new TableRow((Context)this);
                this.tr.setClickable(true);
                this.tr.setBackgroundResource(2131099778);
                this.tr.setMinimumHeight(25);
                this.tr.setGravity(17);
                new TableRow.LayoutParams(-1, -2).setMargins(20, 0, 0, 0);
                this.CustumFont = Typeface.createFromAsset((AssetManager)this.getAssets(), (String)"fonts/Roboto-Light.ttf");
                this.itemname = cursor2.getString(1);
                this.txt_item_name = new TextView((Context)this);
                this.txt_item_name.setText((CharSequence)this.itemname);
                this.txt_item_name.setTypeface(this.CustumFont);
                this.txt_item_name.setTextSize(19.0f);
                this.txt_item_name.setTextColor(-16777216);
                this.txt_item_name.setGravity(16);
                this.tr.addView((View)this.txt_item_name);
                this.itemprize = cursor2.getString(2);
                this.txt_item_prize = new TextView((Context)this);
                this.txt_item_prize.setText((CharSequence)("Rs. " + this.itemprize));
                this.txt_item_prize.setTypeface(this.CustumFont);
                this.txt_item_prize.setTextSize(19.0f);
                this.txt_item_prize.setTextColor(-16777216);
                this.txt_item_prize.setGravity(16);
                this.tr.addView((View)this.txt_item_prize);
                this.item_catgory = cursor2.getString(4);
                this.item_cat = new Spinner((Context)this);
                this.btn = new Button((Context)this);
                this.btn.setId(i);
                this.btn.setBackgroundResource(2131099777);
                this.btn.setHighlightColor(17170443);
                this.btn.setClickable(true);
                this.btn.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(65, 65));
                this.tr.addView((View)this.btn);
                this.btn.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        RolesModel.GetInstance();
                        if (RolesModel.get_deleteItem()) {
                            SearchItem.this.alertDeleteItem(view, SearchItem.this.tb);
                            return;
                        }
                        UtilityHelper.showToastMessage(SearchItem.this.getApplicationContext(), "You dont have access to delete item");
                    }
                });
                this.tb.addView((View)this.tr);
                this.tr.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        RolesModel.GetInstance();
                        if (RolesModel.get_editItem()) {
                            TableRow tableRow = (TableRow)view;
                            TextView textView = (TextView)tableRow.getChildAt(0);
                            TextView textView2 = (TextView)tableRow.getChildAt(1);
                            String string2 = textView.getText().toString();
                            String string3 = textView2.getText().toString();
                            Intent intent = new Intent(view.getContext(), AddItem.class);
                            intent.putExtra("itemname", string2);
                            intent.putExtra("prize", string3);
                            SearchItem.this.startActivity(intent);
                            SearchItem.this.finish();
                            return;
                        }
                        UtilityHelper.showToastMessage(SearchItem.this.getApplicationContext(), "You dont have access to edit item");
                    }
                });
                cursor2.moveToNext();
            }
            cursor2.close();
            return;
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Wrong User id", (int)0);
            toast.setGravity(17, 0, 0);
            toast.show();
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void searchbtn_click(View view) {
        try {
            this.tb.removeAllViews();
            String string2 = this.item.getText().toString();
            Cursor cursor = this.objDbHelper.getItemDetails(string2);
            this.tb = (TableLayout)this.findViewById(2131165381);
            this.CustumFont = Typeface.createFromAsset((AssetManager)this.getAssets(), (String)"fonts/Roboto-Light.ttf");
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
            } else {
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Item Not found ", (int)1);
                toast.setGravity(17, 0, 0);
                toast.show();
                this.item.setText((CharSequence)"");
                return;
            }
            for (int i = 0; i < cursor.getCount(); ++i) {
                this.tr = new TableRow((Context)this);
                this.tr.setClickable(true);
                this.tr.setBackgroundResource(2131099778);
                this.tr.setGravity(17);
                new TableRow.LayoutParams(-1, -2).setMargins(20, 0, 0, 0);
                this.CustumFont = Typeface.createFromAsset((AssetManager)this.getAssets(), (String)"fonts/Roboto-Light.ttf");
                this.itemname = cursor.getString(1);
                this.txt_item_name = new TextView((Context)this);
                this.txt_item_name.setText((CharSequence)this.itemname);
                this.txt_item_name.setTypeface(this.CustumFont);
                this.txt_item_name.setTextSize(19.0f);
                this.txt_item_name.setHeight(65);
                this.txt_item_name.setTextColor(-16777216);
                this.txt_item_name.setPadding(10, 0, 0, 0);
                this.txt_item_name.setGravity(16);
                this.tr.addView((View)this.txt_item_name);
                this.itemprize = cursor.getString(2);
                this.txt_item_prize = new TextView((Context)this);
                this.txt_item_prize.setText((CharSequence)("Rs. " + this.itemprize));
                this.txt_item_prize.setTypeface(this.CustumFont);
                this.txt_item_prize.setTextSize(19.0f);
                this.txt_item_prize.setHeight(65);
                this.txt_item_prize.setTextColor(-16777216);
                this.txt_item_prize.setPadding(0, 0, 40, 0);
                this.txt_item_prize.setGravity(16);
                this.tr.addView((View)this.txt_item_prize);
                this.btn = new Button((Context)this);
                this.btn.setId(i);
                this.btn.setBackgroundResource(2131099777);
                this.btn.setHighlightColor(17170443);
                this.btn.setClickable(true);
                this.btn.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(30, 30));
                this.btn.setPadding(20, 0, 20, 0);
                this.tr.addView((View)this.btn);
                this.btn.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        TableRow tableRow = (TableRow)view.getParent();
                        TextView textView = (TextView)tableRow.getChildAt(0);
                        TextView textView2 = (TextView)tableRow.getChildAt(1);
                        String string2 = textView.getText().toString();
                        textView2.getText().toString();
                        SearchItem.this.objDbHelper.deleteitem(string2);
                        SearchItem.this.tb.removeView((View)view.getParent());
                        SearchItem.this.tb.removeView(SearchItem.this.line);
                        Toast toast = Toast.makeText((Context)SearchItem.this.getApplicationContext(), (CharSequence)"Item deleted successfully", (int)1);
                        toast.setGravity(17, 0, 0);
                        toast.show();
                    }
                });
                this.tb.addView((View)this.tr);
                this.tr.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        TableRow tableRow = (TableRow)view;
                        TextView textView = (TextView)tableRow.getChildAt(0);
                        TextView textView2 = (TextView)tableRow.getChildAt(1);
                        String string2 = textView.getText().toString();
                        String string3 = textView2.getText().toString();
                        Intent intent = new Intent(view.getContext(), AddItem.class);
                        intent.putExtra("itemname", string2);
                        intent.putExtra("prize", string3);
                        SearchItem.this.startActivity(intent);
                        SearchItem.this.finish();
                    }
                });
                cursor.moveToNext();
            }
            cursor.close();
            return;
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Wrong User id", (int)0);
            toast.setGravity(17, 0, 0);
            toast.show();
            return;
        }
    }

}

