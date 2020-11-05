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
 *  android.os.Environment
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.AutoCompleteTextView
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.RadioButton
 *  android.widget.RadioGroup
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TableLayout
 *  android.widget.TableRow
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
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
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import com.neoAntara.newrestaurant.Models.SettingsModel;
import com.neoAntara.newrestaurant.Print.Bluetooth;
import com.neoAntara.newrestaurant.Print.PlainPrint;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;
import com.zj.btsdk.BluetoothService;
import java.util.ArrayList;

public class NewOrder
extends Activity
implements TextWatcher {
    private static int item_count;
    private static int srno;
    String Address = "";
    String Cust_name = "";
    String Discount = "";
    DBHelper ObjdbHelp;
    String OtherCharges = "";
    String Phoneno = "";
    int REQUEST_CONNECT_DEVICE = 6;
    int REQUEST_ENABLE_BT = 4;
    float Vat = 0.0f;
    public String[] addeditemcode = new String[50];
    public String[] addeditems = new String[50];
    public String[] addedquant = new String[50];
    public EditText address = null;
    String[] arr_addeditems;
    String[] arr_addedquant;
    public String[] arr_prize = new String[50];
    private Button btnConnectPrinter = null;
    private Button btnNext = null;
    private Button btnTotal = null;
    public String cgst;
    private TextView date = null;
    public EditText discount = null;
    float edit_prize = 0.0f;
    int effectivePrintWidth = 48;
    float f = 0.0f;
    float grand_total = 0.0f;
    GradientDrawable grd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{-11908534, -6737101});
    public TextView grnd_total = null;
    public String gstno;
    private AutoCompleteTextView item = null;
    BluetoothService mService = null;
    public EditText name = null;
    public EditText other_charges = null;
    public EditText phone_no = null;
    float prize;
    private EditText quantity = null;
    private RadioButton rbtCard = null;
    private RadioButton rbtCash = null;
    private RadioGroup rbtGroup = null;
    private RadioButton rbtOther = null;
    public String rest_address;
    public String restaurant_name;
    private TextView resto_name = null;
    private int rowid = 0;
    public TextView service_charge = null;
    SettingsModel settingsModel = null;
    public String sgst;
    float st = 0.0f;
    public String stphoneno;
    String str_Vat = "";
    String str_content = "";
    String str_st = "";
    String str_tax = "";
    private TableLayout tablelayout = null;
    private Spinner tableno = null;
    float tax = 0.0f;
    float total = 0.0f;
    private TextView total_prize = null;
    private TextView totalamt = null;
    float totalprize = 0.0f;
    private TableRow tr = null;
    private TextView txtViewPaymentType = null;
    private TextView txt_grandtotal = null;
    private TextView txt_orderno = null;
    private TextView txt_tax = null;
    private TextView txtst = null;
    private TextView txtv_SrNo = null;
    private TextView txtv_itemname = null;
    private TextView txtv_prize = null;
    private TextView txtv_quantity = null;
    public TextView vat = null;
    public String website;

    public NewOrder() {
        item_count = 0;
        srno = 1;
        this.settingsModel = new SettingsModel();
    }

    static /* synthetic */ int access$1010() {
        int n = srno;
        srno = n - 1;
        return n;
    }

    private String getItemPrize(String string2) {
        Cursor cursor = this.ObjdbHelp.getItemCode(string2);
        String string3 = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            string3 = cursor.getString(1).toString();
        }
        return string3;
    }

    private String getPaymentType() {
        if (this.rbtCash.isChecked()) {
            return "Cash";
        }
        if (this.rbtCard.isChecked()) {
            return "Card";
        }
        return "Other(Paytm, Mobikwik etc.)";
    }

    public void afterTextChanged(Editable editable) {
    }

    public void alert() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            if (NewOrder.this.tableno.getSelectedItem().toString().matches("Home Delivery")) {
                                NewOrder.this.alertforparcelorder("yes");
                                return;
                            }
                            NewOrder.this.alertfordiscount("yes");
                            return;
                        }
                        case -2: 
                    }
                    NewOrder.this.btnSubmit_Click("no");
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            this.txtViewPaymentType = new TextView((Context)this);
            this.txtViewPaymentType.setText((CharSequence)"Payment Mode : ");
            this.rbtGroup = new RadioGroup((Context)this);
            this.rbtGroup.setOrientation(0);
            this.rbtCash = new RadioButton((Context)this);
            this.rbtCash.setText((CharSequence)"Cash");
            this.rbtCard = new RadioButton((Context)this);
            this.rbtCard.setText((CharSequence)"Card");
            this.rbtOther = new RadioButton((Context)this);
            this.rbtOther.setText((CharSequence)"Other(Paytm etc.)");
            this.rbtGroup.addView((View)this.rbtCash);
            this.rbtGroup.addView((View)this.rbtCard);
            this.rbtGroup.addView((View)this.rbtOther);
            this.rbtCash.setChecked(true);
            LinearLayout linearLayout = new LinearLayout((Context)this);
            linearLayout.setOrientation(1);
            linearLayout.addView((View)this.txtViewPaymentType);
            linearLayout.addView((View)this.rbtGroup);
            builder.setView((View)linearLayout);
            builder.setMessage((CharSequence)"Do You Want to Print?");
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
    public void alertfordiscount(final String string2) {
        LinearLayout linearLayout;
        AlertDialog.Builder builder;
        DialogInterface.OnClickListener onClickListener;
        block6 : {
            block7 : {
                try {
                    onClickListener = new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            switch (n) {
                                default: {
                                    return;
                                }
                                case -1: {
                                    NewOrder.this.btnSubmit_Click(string2);
                                }
                                case -2: 
                            }
                            float f = Float.parseFloat((String)NewOrder.this.totalamt.getText().toString().split(" ")[2]);
                            TextView textView = NewOrder.this.txt_grandtotal;
                            StringBuilder stringBuilder = new StringBuilder().append("GrandTotal : ");
                            Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        }
                    };
                    builder = new AlertDialog.Builder((Context)this);
                    this.other_charges = new EditText((Context)this);
                    this.other_charges.setHint((CharSequence)"Other Charges");
                    this.discount = new EditText((Context)this);
                    this.discount.setHint((CharSequence)"Discount");
                    this.other_charges.setInputType(2);
                    this.discount.setInputType(2);
                    float f = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = this.total_prize = new TextView((Context)this);
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    this.total_prize.setTextSize(18.0f);
                    this.totalprize = Float.parseFloat((String)this.total_prize.getText().toString().split(" ")[2]);
                    this.txtst = new TextView((Context)this);
                    this.txt_tax = new TextView((Context)this);
                    this.grnd_total = new TextView((Context)this);
                    linearLayout = new LinearLayout((Context)this);
                    linearLayout.setOrientation(1);
                    linearLayout.addView((View)this.other_charges);
                    linearLayout.addView((View)this.discount);
                    linearLayout.addView((View)this.total_prize);
                    Cursor cursor = this.ObjdbHelp.getRestoDetails();
                    cursor.moveToFirst();
                    this.str_st = cursor.getString(4);
                    this.str_tax = cursor.getString(6);
                    this.str_Vat = cursor.getString(5);
                    cursor.close();
                    if (this.str_Vat.matches("") && this.str_st.matches("")) {
                        this.st = 0.0f;
                        this.Vat = 0.0f;
                        this.grand_total = this.totalprize;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txtst.setVisibility(8);
                        this.txt_tax.setVisibility(8);
                        break block6;
                    }
                    if (this.str_Vat.matches("") && this.str_st != "") {
                        this.st = Float.parseFloat((String)this.str_st);
                        this.Vat = 0.0f;
                        float f2 = this.totalprize * (this.st / 100.0f);
                        TextView textView2 = this.txtst;
                        StringBuilder stringBuilder2 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        this.grand_total = f2 + this.totalprize;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txt_tax.setVisibility(8);
                        break block6;
                    }
                    if (!this.str_st.matches("") || this.str_Vat == "") break block7;
                    this.st = 0.0f;
                    this.Vat = Float.parseFloat((String)this.str_Vat);
                    float f3 = this.totalprize * (this.Vat / 100.0f);
                    TextView textView3 = this.txt_tax;
                    StringBuilder stringBuilder3 = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                    Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                    textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                    this.grand_total = f3 + this.totalprize;
                    linearLayout.addView((View)this.txtst);
                    linearLayout.addView((View)this.txt_tax);
                    linearLayout.addView((View)this.grnd_total);
                    this.txtst.setVisibility(8);
                    break block6;
                }
                catch (Exception exception) {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
                    return;
                }
            }
            if (this.str_Vat != "" && this.str_st != "") {
                this.st = Float.parseFloat((String)this.str_st);
                this.Vat = Float.parseFloat((String)this.str_Vat);
                float f = this.totalprize * (this.Vat / 100.0f);
                float f4 = this.totalprize * (this.st / 100.0f);
                TextView textView = this.txt_tax;
                StringBuilder stringBuilder = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                TextView textView4 = this.txtst;
                StringBuilder stringBuilder4 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                this.grand_total = f4 + (f + this.totalprize);
                linearLayout.addView((View)this.txtst);
                linearLayout.addView((View)this.txt_tax);
                linearLayout.addView((View)this.grnd_total);
            }
        }
        TextView textView = this.grnd_total;
        StringBuilder stringBuilder = new StringBuilder().append("GrandTotal : ");
        Object[] arrobject = new Object[]{Float.valueOf((float)this.grand_total)};
        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
        this.grnd_total.setTextSize(18.0f);
        builder.setView((View)linearLayout);
        builder.setPositiveButton((CharSequence)"Ok", onClickListener);
        builder.setNegativeButton((CharSequence)"Cancel", onClickListener).show();
        this.other_charges.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!NewOrder.this.discount.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)NewOrder.this.discount.getText().toString());
                        TextView textView = NewOrder.this.total_prize;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (NewOrder.this.Vat / 100.0f);
                        float f3 = f * (NewOrder.this.st / 100.0f);
                        TextView textView2 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = NewOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        NewOrder.this.grand_total = f3 + (f + f2);
                        TextView textView4 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)NewOrder.this.grand_total)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f4 = f * (NewOrder.this.Vat / 100.0f);
                    float f5 = f * (NewOrder.this.st / 100.0f);
                    TextView textView5 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f4)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = NewOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f5)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    NewOrder.this.grand_total = f5 + (f + f4);
                    TextView textView7 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)NewOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)NewOrder.this.other_charges.getText().toString());
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f6 = f * (NewOrder.this.Vat / 100.0f);
                    float f7 = f * (NewOrder.this.st / 100.0f);
                    TextView textView8 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f6)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = NewOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f7)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f8 = f7 + (f + f6);
                    TextView textView10 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f8)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (NewOrder.this.discount.getText().toString().matches("")) return;
                    {
                        float f9 = Float.parseFloat((String)NewOrder.this.total_prize.getText().toString().split(" ")[2]) - Float.parseFloat((String)NewOrder.this.discount.getText().toString());
                        TextView textView11 = NewOrder.this.total_prize;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f9)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f10 = f9 * (NewOrder.this.Vat / 100.0f);
                        float f11 = f9 * (NewOrder.this.st / 100.0f);
                        TextView textView12 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f10)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = NewOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f11)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f12 = f11 + (f9 + f10);
                        TextView textView14 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder14 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject14 = new Object[]{Float.valueOf((float)f12)};
                        textView14.setText((CharSequence)stringBuilder14.append(String.format((String)"%.2f", (Object[])arrobject14)).toString());
                        return;
                    }
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.discount.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!NewOrder.this.other_charges.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)NewOrder.this.other_charges.getText().toString());
                        TextView textView = NewOrder.this.total_prize;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (NewOrder.this.Vat / 100.0f);
                        float f3 = f * (NewOrder.this.st / 100.0f);
                        TextView textView2 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = NewOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        float f4 = f3 + (f + f2);
                        TextView textView4 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f5 = f * (NewOrder.this.Vat / 100.0f);
                    float f6 = f * (NewOrder.this.st / 100.0f);
                    TextView textView5 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f5)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = NewOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f6)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    NewOrder.this.grand_total = f6 + (f + f5);
                    TextView textView7 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)NewOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)NewOrder.this.discount.getText().toString());
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f7 = f * (NewOrder.this.Vat / 100.0f);
                    float f8 = f * (NewOrder.this.st / 100.0f);
                    TextView textView8 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f7)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = NewOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f8)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f9 = f8 + (f + f7);
                    TextView textView10 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f9)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (NewOrder.this.other_charges.getText().toString().matches("")) return;
                    {
                        float f10 = Float.parseFloat((String)NewOrder.this.total_prize.getText().toString().split(" ")[2]) + Float.parseFloat((String)NewOrder.this.other_charges.getText().toString());
                        TextView textView11 = NewOrder.this.total_prize;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f10)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f11 = f10 * (NewOrder.this.Vat / 100.0f);
                        float f12 = f10 * (NewOrder.this.st / 100.0f);
                        TextView textView12 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f11)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = NewOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f12)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f13 = f12 + (f10 + f11);
                        TextView textView14 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder14 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject14 = new Object[]{Float.valueOf((float)f13)};
                        textView14.setText((CharSequence)stringBuilder14.append(String.format((String)"%.2f", (Object[])arrobject14)).toString());
                        return;
                    }
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
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
                            NewOrder.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            NewOrder.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    NewOrder.this.editorder("HD");
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void alertforparcelorder(final String string2) {
        LinearLayout linearLayout;
        AlertDialog.Builder builder;
        DialogInterface.OnClickListener onClickListener;
        block6 : {
            block7 : {
                try {
                    onClickListener = new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            switch (n) {
                                default: {
                                    return;
                                }
                                case -1: {
                                    NewOrder.this.btnSubmit_Click(string2);
                                }
                                case -2: 
                            }
                            float f = Float.parseFloat((String)NewOrder.this.totalamt.getText().toString().split(" ")[2]);
                            TextView textView = NewOrder.this.txt_grandtotal;
                            StringBuilder stringBuilder = new StringBuilder().append("GrandTotal : ");
                            Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        }
                    };
                    builder = new AlertDialog.Builder((Context)this);
                    this.name = new EditText((Context)this);
                    this.name.setHint((CharSequence)"Customer Name");
                    this.address = new EditText((Context)this);
                    this.address.setHint((CharSequence)"Address");
                    this.phone_no = new EditText((Context)this);
                    this.phone_no.setHint((CharSequence)"Phone No.");
                    this.other_charges = new EditText((Context)this);
                    this.other_charges.setHint((CharSequence)"Other Charges");
                    this.other_charges.setInputType(2);
                    this.discount = new EditText((Context)this);
                    this.discount.setHint((CharSequence)"Discount");
                    this.discount.setInputType(2);
                    this.phone_no.setInputType(2);
                    float f = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = this.total_prize = new TextView((Context)this);
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    this.total_prize.setTextSize(18.0f);
                    this.totalprize = Float.parseFloat((String)this.total_prize.getText().toString().split(" ")[2]);
                    this.txtst = new TextView((Context)this);
                    this.txt_tax = new TextView((Context)this);
                    this.grnd_total = new TextView((Context)this);
                    linearLayout = new LinearLayout((Context)this);
                    linearLayout.setOrientation(1);
                    linearLayout.addView((View)this.name);
                    linearLayout.addView((View)this.address);
                    linearLayout.addView((View)this.phone_no);
                    linearLayout.addView((View)this.other_charges);
                    linearLayout.addView((View)this.discount);
                    linearLayout.addView((View)this.total_prize);
                    Cursor cursor = this.ObjdbHelp.getRestoDetails();
                    cursor.moveToFirst();
                    this.str_st = cursor.getString(4);
                    this.str_tax = cursor.getString(6);
                    this.str_Vat = cursor.getString(5);
                    cursor.close();
                    if (this.str_Vat.matches("") && this.str_st.matches("")) {
                        this.st = 0.0f;
                        this.Vat = 0.0f;
                        this.grand_total = this.totalprize;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txtst.setVisibility(8);
                        this.txt_tax.setVisibility(8);
                        break block6;
                    }
                    if (this.str_Vat.matches("") && this.str_st != "") {
                        this.st = Float.parseFloat((String)this.str_st);
                        this.Vat = 0.0f;
                        float f2 = this.totalprize * (this.st / 100.0f);
                        TextView textView2 = this.txtst;
                        StringBuilder stringBuilder2 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        this.grand_total = f2 + this.totalprize;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txt_tax.setVisibility(8);
                        break block6;
                    }
                    if (!this.str_st.matches("") || this.str_Vat == "") break block7;
                    this.st = 0.0f;
                    this.Vat = Float.parseFloat((String)this.str_Vat);
                    float f3 = this.totalprize * (this.Vat / 100.0f);
                    TextView textView3 = this.txt_tax;
                    StringBuilder stringBuilder3 = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                    Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                    textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                    this.grand_total = f3 + this.totalprize;
                    linearLayout.addView((View)this.txtst);
                    linearLayout.addView((View)this.txt_tax);
                    linearLayout.addView((View)this.grnd_total);
                    this.txtst.setVisibility(8);
                    break block6;
                }
                catch (Exception exception) {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
                    return;
                }
            }
            if (this.str_Vat != "" && this.str_st != "") {
                this.st = Float.parseFloat((String)this.str_st);
                this.Vat = Float.parseFloat((String)this.str_Vat);
                float f = this.totalprize * (this.Vat / 100.0f);
                float f4 = this.totalprize * (this.st / 100.0f);
                TextView textView = this.txt_tax;
                StringBuilder stringBuilder = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                TextView textView4 = this.txtst;
                StringBuilder stringBuilder4 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                this.grand_total = f4 + (f + this.totalprize);
                linearLayout.addView((View)this.txtst);
                linearLayout.addView((View)this.txt_tax);
                linearLayout.addView((View)this.grnd_total);
            }
        }
        TextView textView = this.grnd_total;
        StringBuilder stringBuilder = new StringBuilder().append("GrandTotal : ");
        Object[] arrobject = new Object[]{Float.valueOf((float)this.grand_total)};
        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
        this.grnd_total.setTextSize(18.0f);
        builder.setView((View)linearLayout);
        builder.setPositiveButton((CharSequence)"Ok", onClickListener);
        builder.setNegativeButton((CharSequence)"Cancel", onClickListener).show();
        this.other_charges.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!NewOrder.this.discount.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)NewOrder.this.discount.getText().toString());
                        TextView textView = NewOrder.this.total_prize;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (NewOrder.this.Vat / 100.0f);
                        float f3 = f * (NewOrder.this.st / 100.0f);
                        TextView textView2 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = NewOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        NewOrder.this.grand_total = f3 + (f + f2);
                        TextView textView4 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)NewOrder.this.grand_total)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f4 = f * (NewOrder.this.Vat / 100.0f);
                    float f5 = f * (NewOrder.this.st / 100.0f);
                    TextView textView5 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f4)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = NewOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f5)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    NewOrder.this.grand_total = f5 + (f + f4);
                    TextView textView7 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)NewOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)NewOrder.this.other_charges.getText().toString());
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f6 = f * (NewOrder.this.Vat / 100.0f);
                    float f7 = f * (NewOrder.this.st / 100.0f);
                    TextView textView8 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f6)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = NewOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f7)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f8 = f7 + (f + f6);
                    TextView textView10 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f8)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (NewOrder.this.discount.getText().toString().matches("")) return;
                    {
                        float f9 = Float.parseFloat((String)NewOrder.this.total_prize.getText().toString().split(" ")[2]) - Float.parseFloat((String)NewOrder.this.discount.getText().toString());
                        TextView textView11 = NewOrder.this.total_prize;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f9)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f10 = f9 * (NewOrder.this.Vat / 100.0f);
                        float f11 = f9 * (NewOrder.this.st / 100.0f);
                        TextView textView12 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f10)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = NewOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f11)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f12 = f11 + (f9 + f10);
                        TextView textView14 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder14 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject14 = new Object[]{Float.valueOf((float)f12)};
                        textView14.setText((CharSequence)stringBuilder14.append(String.format((String)"%.2f", (Object[])arrobject14)).toString());
                        return;
                    }
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.discount.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!NewOrder.this.other_charges.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)NewOrder.this.other_charges.getText().toString());
                        TextView textView = NewOrder.this.total_prize;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (NewOrder.this.Vat / 100.0f);
                        float f3 = f * (NewOrder.this.st / 100.0f);
                        TextView textView2 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = NewOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        float f4 = f3 + (f + f2);
                        TextView textView4 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f5 = f * (NewOrder.this.Vat / 100.0f);
                    float f6 = f * (NewOrder.this.st / 100.0f);
                    TextView textView5 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f5)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = NewOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f6)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    NewOrder.this.grand_total = f6 + (f + f5);
                    TextView textView7 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)NewOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)NewOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)NewOrder.this.discount.getText().toString());
                    TextView textView = NewOrder.this.total_prize;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f7 = f * (NewOrder.this.Vat / 100.0f);
                    float f8 = f * (NewOrder.this.st / 100.0f);
                    TextView textView8 = NewOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f7)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = NewOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f8)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f9 = f8 + (f + f7);
                    TextView textView10 = NewOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f9)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (NewOrder.this.other_charges.getText().toString().matches("")) return;
                    {
                        float f10 = Float.parseFloat((String)NewOrder.this.total_prize.getText().toString().split(" ")[2]) + Float.parseFloat((String)NewOrder.this.other_charges.getText().toString());
                        TextView textView11 = NewOrder.this.total_prize;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f10)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f11 = f10 * (NewOrder.this.Vat / 100.0f);
                        float f12 = f10 * (NewOrder.this.st / 100.0f);
                        TextView textView12 = NewOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(NewOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f11)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = NewOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(NewOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f12)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f13 = f12 + (f10 + f11);
                        TextView textView14 = NewOrder.this.grnd_total;
                        StringBuilder stringBuilder14 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject14 = new Object[]{Float.valueOf((float)f13)};
                        textView14.setText((CharSequence)stringBuilder14.append(String.format((String)"%.2f", (Object[])arrobject14)).toString());
                        return;
                    }
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
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
                            NewOrder.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            NewOrder.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    NewOrder.this.reprintorder("HD");
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
                    NewOrder.this.go();
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
                            NewOrder.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            NewOrder.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    NewOrder.this.parcelorder("HD");
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

    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public void btnConnectPrinter_click(View view) {
        if (UtilityHelper.ConnectPrinter(this.getApplicationContext(), this, this.btnConnectPrinter)) {
            byte[] arrby = new byte[]{27, 33, 0};
            BluetoothService bluetoothService = Bluetooth.getServiceInstance();
            bluetoothService.write(arrby);
            bluetoothService.sendMessage("Test Test Test Test\n\n", "GBK");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void btnNextItem_click(View var1_1) {
        block16 : {
            block14 : {
                block17 : {
                    block15 : {
                        if (this.tableno.getSelectedItem().toString().matches("Table No")) {
                            UtilityHelper.showToastMessage(this.getApplicationContext(), "Select Table No");
                            return;
                        }
                        if (this.item.getText().toString().matches("") || this.item.getText().equals(null)) {
                            UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Item Name");
                            this.item.setText((CharSequence)"");
                            return;
                        }
                        if (this.quantity.getText().toString().matches("") || this.quantity.getText().equals(null)) {
                            UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Quantity");
                            this.quantity.setText((CharSequence)"");
                            return;
                        }
                        var4_4 = Integer.parseInt((String)this.quantity.getText().toString());
                        var5_5 = this.item.getText().toString();
                        var6_6 = this.ObjdbHelp.getItemCode(var5_5);
                        if (var6_6.getCount() <= 0) break block14;
                        var6_6.moveToFirst();
                        var9_7 = Integer.parseInt((String)var6_6.getString(0).toString());
                        this.prize = Float.parseFloat((String)var6_6.getString(1).toString());
                        var10_8 = this.totalamt.getText().toString().split(" ");
                        if (!"".matches("") || !"".matches("")) break block15;
                        this.totalprize = Float.parseFloat((String)var10_8[2]);
                        this.f = this.prize * (float)var4_4;
                        this.totalprize += this.f;
                        this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                        this.txt_tax.setText((CharSequence)"");
                        this.txtst.setText((CharSequence)"");
                        this.txt_tax.setVisibility(8);
                        this.txtst.setVisibility(8);
                        this.grand_total = this.totalprize;
                        var46_9 = this.txt_grandtotal;
                        var47_10 = new StringBuilder().append("Total : ");
                        var48_11 = new Object[]{Float.valueOf((float)this.grand_total)};
                        var46_9.setText((CharSequence)var47_10.append(String.format((String)"%.2f", (Object[])var48_11)).toString());
                    }
                    if (!"".matches("") || "" == "") ** GOTO lbl60
                    this.st = Float.parseFloat((String)"");
                    this.totalprize = Float.parseFloat((String)var10_8[2]);
                    this.f = this.prize * (float)var4_4;
                    this.totalprize += this.f;
                    this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                    this.totalprize * (this.Vat / 100.0f);
                    var12_12 = this.totalprize * (this.st / 100.0f);
                    this.txt_tax.setText((CharSequence)"");
                    var13_13 = this.txtst;
                    var14_14 = new StringBuilder().append("CGST(").append(this.st).append("%) : ");
                    var15_15 = new Object[]{Float.valueOf((float)var12_12)};
                    var13_13.setText((CharSequence)var14_14.append(String.format((String)"%.2f", (Object[])var15_15)).toString());
                    this.txt_tax.setVisibility(8);
                    this.txtst.setVisibility(8);
                    this.grand_total = this.totalprize;
                    var16_16 = this.txt_grandtotal;
                    var17_17 = new StringBuilder().append("Total : ");
                    var18_18 = new Object[]{Float.valueOf((float)this.grand_total)};
                    var16_16.setText((CharSequence)var17_17.append(String.format((String)"%.2f", (Object[])var18_18)).toString());
                    break block16;
lbl60: // 1 sources:
                    if (!"".matches("") || "" == "") break block17;
                    try {
                        this.Vat = Float.parseFloat((String)"");
                        this.totalprize = Float.parseFloat((String)var10_8[2]);
                        this.f = this.prize * (float)var4_4;
                        this.totalprize += this.f;
                        this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                        var28_19 = this.totalprize * (this.Vat / 100.0f);
                        var29_20 = this.txt_tax;
                        var30_21 = new StringBuilder().append("SGST(").append(var28_19).append("%) : ");
                        var31_22 = new Object[]{Float.valueOf((float)var28_19)};
                        var29_20.setText((CharSequence)var30_21.append(String.format((String)"%.2f", (Object[])var31_22)).toString());
                        this.txtst.setText((CharSequence)"");
                        this.txt_tax.setVisibility(8);
                        this.txtst.setVisibility(8);
                        this.grand_total = this.totalprize;
                        var32_23 = this.txt_grandtotal;
                        var33_24 = new StringBuilder().append("Total : ");
                        var34_25 = new Object[]{Float.valueOf((float)this.grand_total)};
                        var32_23.setText((CharSequence)var33_24.append(String.format((String)"%.2f", (Object[])var34_25)).toString());
                    }
                    catch (SQLException var3_2) {
                        UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var3_2);
                        return;
                    }
                    catch (Exception var2_3) {
                        UtilityHelper.GenericCatchBlock(this.getApplicationContext(), var2_3);
                        return;
                    }
                }
                if ("" != "" && "" != "") {
                    this.st = Float.parseFloat((String)"");
                    this.Vat = Float.parseFloat((String)"");
                    this.totalprize = Float.parseFloat((String)var10_8[2]);
                    this.f = this.prize * (float)var4_4;
                    this.totalprize += this.f;
                    this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                    var35_26 = this.totalprize * (this.Vat / 100.0f);
                    var36_27 = this.totalprize * (this.st / 100.0f);
                    var37_28 = this.txt_tax;
                    var38_29 = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                    var39_30 = new Object[]{Float.valueOf((float)var35_26)};
                    var37_28.setText((CharSequence)var38_29.append(String.format((String)"%.2f", (Object[])var39_30)).toString());
                    var40_31 = this.txtst;
                    var41_32 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                    var42_33 = new Object[]{Float.valueOf((float)var36_27)};
                    var40_31.setText((CharSequence)var41_32.append(String.format((String)"%.2f", (Object[])var42_33)).toString());
                    this.txt_tax.setVisibility(8);
                    this.txtst.setVisibility(8);
                    this.grand_total = this.totalprize;
                    var43_34 = this.txt_grandtotal;
                    var44_35 = new StringBuilder().append("Total : ");
                    var45_36 = new Object[]{Float.valueOf((float)this.grand_total)};
                    var43_34.setText((CharSequence)var44_35.append(String.format((String)"%.2f", (Object[])var45_36)).toString());
                }
                break block16;
            }
            var7_46 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Wrong Item", (int)1);
            var7_46.setGravity(17, 0, 0);
            var7_46.show();
            this.quantity.setText((CharSequence)"");
            this.item.setText((CharSequence)"");
            return;
        }
        this.addeditems[NewOrder.item_count] = this.item.getText().toString();
        this.addedquant[NewOrder.item_count] = this.quantity.getText().toString();
        this.addeditemcode[NewOrder.item_count] = var9_7 + "";
        this.arr_prize[NewOrder.item_count] = this.f + "";
        this.quantity.setText((CharSequence)"");
        this.item.setText((CharSequence)"");
        var6_6.close();
        var19_37 = 0;
        while (var19_37 < (var20_38 = this.addeditems.length)) {
            if (this.addeditems[var19_37] == null) return;
            this.tr = var21_39 = new TableRow((Context)this);
            this.txtv_SrNo = var22_40 = new TextView((Context)this);
            this.txtv_SrNo.setText((CharSequence)(NewOrder.srno + ""));
            this.txtv_SrNo.setTextColor(-16777216);
            this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_SrNo.setPadding(10, 10, 10, 10);
            this.txtv_SrNo.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_SrNo);
            this.txtv_itemname = var23_41 = new TextView((Context)this);
            this.txtv_itemname.setText((CharSequence)this.addeditems[var19_37]);
            this.txtv_itemname.setTextColor(-16777216);
            this.txtv_itemname.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_itemname.setPadding(10, 10, 10, 10);
            this.txtv_itemname.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_itemname);
            this.txtv_quantity = var24_42 = new TextView((Context)this);
            this.txtv_quantity.setText((CharSequence)this.addedquant[var19_37]);
            this.txtv_quantity.setTextColor(-16777216);
            this.txtv_quantity.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_quantity.setPadding(10, 10, 10, 10);
            this.txtv_quantity.setTextSize(16.0f);
            this.txtv_quantity.setTextAlignment(0);
            this.tr.addView((View)this.txtv_quantity);
            this.txtv_prize = var25_43 = new TextView((Context)this);
            this.txtv_prize.setText((CharSequence)this.arr_prize[var19_37]);
            this.txtv_prize.setTextColor(-16777216);
            this.txtv_prize.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_prize.setPadding(10, 10, 10, 10);
            this.txtv_prize.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_prize);
            this.tr.setBackgroundResource(2131099778);
            NewOrder.srno = 1 + NewOrder.srno;
            this.tablelayout.addView((View)this.tr);
            this.btnTotal.setEnabled(true);
            this.btnTotal.setTextColor(-1);
            var26_44 = this.tr;
            var27_45 = new View.OnClickListener(){

                public void onClick(View view) {
                    TableRow tableRow = (TableRow)view;
                    TextView textView = (TextView)tableRow.getChildAt(1);
                    TextView textView2 = (TextView)tableRow.getChildAt(2);
                    TextView textView3 = (TextView)tableRow.getChildAt(0);
                    TextView textView4 = (TextView)tableRow.getChildAt(3);
                    String string2 = textView3.getText().toString();
                    String string3 = textView4.getText().toString();
                    NewOrder.this.edit_prize = Float.parseFloat((String)string3);
                    NewOrder.this.item.setText(textView.getText());
                    NewOrder.this.quantity.setText(textView2.getText());
                    int n = NewOrder.this.tablelayout.getChildCount();
                    for (int i = 0; i < n; ++i) {
                        TableRow tableRow2 = (TableRow)NewOrder.this.tablelayout.getChildAt(i);
                        for (int j = 0; j < n; ++j) {
                            TableRow tableRow3;
                            if (j != 0 || !string2.equalsIgnoreCase(((TextView)tableRow2.getChildAt(j)).getText().toString())) continue;
                            for (int k = ++i; k < n && (tableRow3 = (TableRow)NewOrder.this.tablelayout.getChildAt(k)) != null; ++k) {
                                TextView textView5 = (TextView)tableRow3.getChildAt(j);
                                int n2 = -1 + Integer.parseInt((String)textView5.getText().toString());
                                textView5.setText((CharSequence)(n2 + ""));
                            }
                        }
                    }
                    NewOrder.this.tablelayout.removeView(view);
                    if (NewOrder.this.tablelayout.getChildCount() <= 1) {
                        NewOrder.this.btnTotal.setEnabled(false);
                        NewOrder.this.btnTotal.setTextColor(-7829368);
                    }
                    NewOrder.access$1010();
                    String[] arrstring = NewOrder.this.totalamt.getText().toString().split(" ");
                    NewOrder.this.totalprize = Float.parseFloat((String)arrstring[2]);
                    NewOrder.this.totalprize -= NewOrder.this.edit_prize;
                    NewOrder.this.grand_total = NewOrder.this.totalprize;
                    TextView textView6 = NewOrder.this.txt_grandtotal;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)NewOrder.this.grand_total)};
                    textView6.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    NewOrder.this.edit_prize = 0.0f;
                    NewOrder.this.totalamt.setText((CharSequence)("Amount : " + NewOrder.this.totalprize));
                }
            };
            var26_44.setOnClickListener(var27_45);
            ++var19_37;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void btnSubmit_Click(String var1_1) {
        block21 : {
            block20 : {
                this.str_content = "";
                var4_2 = this.tablelayout.getChildCount();
                if (var4_2 <= 0) {
                    UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Item");
                    return;
                }
                if (this.tableno.getSelectedItem().toString().matches("Table No")) {
                    UtilityHelper.showToastMessage(this.getApplicationContext(), "Select Table No");
                    return;
                }
                var5_3 = this.txt_orderno.getText().toString().split(" ")[2];
                var6_4 = this.tableno.getSelectedItem().toString();
                if (var1_1 != "yes") break block20;
                Environment.getExternalStorageDirectory().toString() + "/BluetoothPrint";
                var8_5 = new PlainPrint(this.getApplicationContext(), this.effectivePrintWidth, 4);
                var8_5.prepareTabularForm(4, 0, 3, true);
                this.mService = Bluetooth.getServiceInstance();
                var9_6 = new ArrayList();
                try {
                    UtilityHelper.printHeader(this.mService, var8_5, var5_3, (ArrayList<String>)var9_6, this.settingsModel, this.getPaymentType(), this.getApplicationContext());
                    if (var6_4 == "Home Delivery") {
                        this.Cust_name = this.name.getText().toString();
                        this.Address = this.address.getText().toString();
                        this.Phoneno = this.phone_no.getText().toString();
                    }
                    this.Discount = this.discount.getText().toString();
                    this.OtherCharges = this.other_charges.getText().toString();
                    break block21;
                }
                catch (SQLException var2_29) {}
                ** GOTO lbl-1000
                catch (Exception var3_33) {}
                ** GOTO lbl-1000
                catch (Exception var3_35) {
                    ** GOTO lbl-1000
                }
                catch (Exception var3_36) {}
lbl-1000: // 3 sources:
                {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var3_34);
                    return;
                }
                catch (SQLException var2_31) {
                    ** GOTO lbl-1000
                }
                catch (SQLException var2_32) {}
lbl-1000: // 3 sources:
                {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var2_30);
                    return;
                }
            }
            var8_5 = null;
            var9_6 = null;
        }
        var10_8 = 0;
        var11_9 = 0.0f;
        do {
            if (var10_8 < var4_2) {
                if (var10_8 > 0) {
                    var21_10 = (TableRow)this.tablelayout.getChildAt(var10_8);
                    var22_11 = (TextView)var21_10.getChildAt(1);
                    var23_12 = (TextView)var21_10.getChildAt(2);
                    (TextView)var21_10.getChildAt(0);
                    var25_13 = (TextView)var21_10.getChildAt(3);
                    var11_9 += Float.parseFloat((String)var25_13.getText().toString());
                    var26_14 = Integer.parseInt((String)var23_12.getText().toString());
                    this.ObjdbHelp.SubmitOrder(var6_4, var5_3, var22_11.getText().toString(), var25_13.getText().toString(), var26_14, this.Cust_name, this.Address, this.Phoneno, this.Discount, this.OtherCharges, var1_1, this.getPaymentType(), "0000", var10_8);
                    if (var1_1.matches("yes")) {
                        this.ObjdbHelp.PrintOrder(var6_4, var5_3, var22_11.getText().toString(), var25_13.getText().toString(), var26_14, this.Cust_name, this.Address, this.Phoneno, this.Discount, this.OtherCharges, this.getPaymentType(), "0000", var10_8);
                        var27_15 = this.getItemPrize(var22_11.getText().toString());
                        var9_6.clear();
                        var28_16 = this.mService;
                        var29_17 = var22_11.getText().toString();
                        var30_18 = var23_12.getText().toString();
                        var31_19 = var25_13.getText().toString();
                        var32_7 = this.getApplicationContext();
                        UtilityHelper.printTabularData(var28_16, var8_5, null, (ArrayList<String>)var9_6, var5_3, var29_17, var30_18, var27_15, var31_19, var32_7);
                    }
                }
            } else {
                if (var1_1.matches("yes")) {
                    var9_6.clear();
                    var13_20 = this.mService;
                    var14_21 = this.Cust_name;
                    var15_22 = this.Address;
                    var16_23 = this.Phoneno;
                    var17_24 = this.Discount;
                    var18_25 = this.OtherCharges;
                    var19_26 = this.settingsModel;
                    var20_27 = this.getApplicationContext();
                    UtilityHelper.printFooter(var13_20, var8_5, (ArrayList<String>)var9_6, var11_9, var6_4, var14_21, var15_22, var16_23, var17_24, var18_25, var19_26, var20_27);
                }
                this.tablelayout.removeAllViews();
                NewOrder.srno = 1;
                this.setup();
                this.totalamt.setText((CharSequence)"Amount : 0");
                this.txt_tax.setText((CharSequence)"");
                this.txt_grandtotal.setText((CharSequence)"Total : 0");
                this.txtst.setText((CharSequence)"");
                var12_28 = this.ObjdbHelp.getOrderDetails();
                this.txt_orderno.setText((CharSequence)("Order No: " + var12_28));
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Order Submitted Successfully");
                this.btnTotal.setEnabled(false);
                this.btnTotal.setTextColor(-7829368);
                return;
            }
            ++var10_8;
        } while (true);
    }

    public void btnTotal_click(View view) {
        this.alert();
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
                    NewOrder.this.exportDb();
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
                    NewOrder.this.importDb();
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
            builder.setMessage((CharSequence)"Are you sure to importDB?");
            builder.setPositiveButton((CharSequence)"Yes", onClickListener);
            builder.setNegativeButton((CharSequence)"No", onClickListener).show();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        UtilityHelper.onActivityResult_Generic(n, n2, intent, this.getApplicationContext(), this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            this.setContentView(2131296291);
            this.ObjdbHelp = new DBHelper((Context)this, null);
            String string2 = this.ObjdbHelp.getBusinessDate();
            this.date = (TextView)this.findViewById(2131165385);
            this.date.setText((CharSequence)string2);
            Cursor cursor = this.ObjdbHelp.getRestoDetails();
            cursor.moveToFirst();
            if (cursor.getCount() == 1) {
                this.settingsModel.set_cgst(cursor.getString(4));
                this.settingsModel.set_sgst(cursor.getString(5));
                this.settingsModel.set_resturantName(cursor.getString(0));
                this.settingsModel.set_restaurantAddress(cursor.getString(1));
                this.settingsModel.set_website(cursor.getString(3));
                this.settingsModel.set_gstno(cursor.getString(8));
                this.settingsModel.set_phoneNo(cursor.getString(2));
                this.resto_name = (TextView)this.findViewById(2131165338);
                this.resto_name.setTextColor(Color.rgb((int)153, (int)51, (int)51));
                this.resto_name.setBackgroundResource(2131099732);
                this.resto_name.setPadding(10, 5, 10, 5);
                this.resto_name.setText((CharSequence)this.settingsModel.get_resturantName());
            } else {
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Insert Restaurant details into setting file");
            }
            cursor.close();
            this.tableno = (Spinner)this.findViewById(2131165382);
            this.item = (AutoCompleteTextView)this.findViewById(2131165416);
            this.totalamt = (TextView)this.findViewById(2131165392);
            this.quantity = (EditText)this.findViewById(2131165428);
            this.tablelayout = (TableLayout)this.findViewById(2131165381);
            this.btnNext = (Button)this.findViewById(2131165221);
            this.txt_tax = (TextView)this.findViewById(2131165384);
            this.txt_grandtotal = (TextView)this.findViewById(2131165277);
            this.txt_orderno = (TextView)this.findViewById(2131165420);
            this.txtst = (TextView)this.findViewById(2131165440);
            this.btnTotal = (Button)this.findViewById(2131165226);
            this.btnTotal.setEnabled(false);
            this.btnTotal.setTextColor(-7829368);
            this.txt_tax.setTextSize(16.0f);
            this.totalamt.setTextSize(16.0f);
            this.txt_grandtotal.setTextSize(16.0f);
            this.txt_orderno.setTextColor(-16776961);
            String string3 = this.ObjdbHelp.getOrderDetails();
            this.txt_orderno.setText((CharSequence)("Order No: " + string3));
            this.setup();
            if (this.tablelayout.getChildCount() > 1) {
                this.btnTotal.setEnabled(true);
                this.btnTotal.setTextColor(-1);
            }
            this.item.addTextChangedListener(new TextWatcher(){

                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                }

                public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                    NewOrder.this.quantity.setText((CharSequence)"");
                }
            });
            this.btnConnectPrinter = (Button)this.findViewById(2131165218);
            UtilityHelper.ConnectPrinter(this.getApplicationContext(), this, this.btnConnectPrinter);
            this.btnConnectPrinter.setVisibility(8);
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

    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public void parcelorder(String string2) {
        Intent intent = new Intent((Context)this, NewOrder.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    public void reprint(View view) {
        this.alertforreprint();
    }

    public void reprintorder(String string2) {
        Intent intent = new Intent((Context)this, Reprint.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setup() {
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)"SrNo");
        this.txtv_SrNo.setTextColor(-1);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_SrNo.setPadding(10, 10, 10, 10);
        this.txtv_SrNo.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txtv_itemname = new TextView((Context)this);
        this.txtv_itemname.setText((CharSequence)"ItemName");
        this.txtv_itemname.setTextColor(-1);
        this.txtv_itemname.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_itemname.setPadding(10, 10, 10, 10);
        this.txtv_itemname.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_itemname);
        this.txtv_quantity = new TextView((Context)this);
        this.txtv_quantity.setText((CharSequence)"Qty");
        this.txtv_quantity.setTextColor(-1);
        this.txtv_quantity.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_quantity.setPadding(10, 10, 10, 10);
        this.txtv_quantity.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_quantity);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)"Prize(Rs.)");
        this.txtv_prize.setTextColor(-1);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_prize.setPadding(10, 10, 10, 10);
        this.txtv_prize.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_prize);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
        Cursor cursor = this.ObjdbHelp.getItemName();
        Object[] arrobject = new String[cursor.getCount()];
        String[] arrstring = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); ++i) {
            arrstring[i] = cursor.getString(0).toString();
            arrobject[i] = cursor.getString(1).toString();
            cursor.moveToNext();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367057, arrobject);
        this.item.setThreshold(1);
        this.item.setAdapter((ListAdapter)arrayAdapter);
        cursor.close();
        String string2 = this.getIntent().getStringExtra("ordertype");
        if (string2.matches("Parcel")) {
            ArrayAdapter arrayAdapter2 = new ArrayAdapter((Context)this, 17367049, (Object[])new String[]{"Parcel"});
            this.tableno.setAdapter((SpinnerAdapter)arrayAdapter2);
            return;
        } else {
            if (string2.matches("HD")) {
                ArrayAdapter arrayAdapter3 = new ArrayAdapter((Context)this, 17367049, (Object[])new String[]{"Home Delivery"});
                this.tableno.setAdapter((SpinnerAdapter)arrayAdapter3);
                return;
            }
            if (!string2.matches("Service")) return;
            {
                Cursor cursor2 = this.ObjdbHelp.getTableNo();
                Object[] arrobject2 = new String[1 + cursor2.getCount()];
                arrobject2[0] = "Table No";
                cursor2.moveToFirst();
                int n = 0;
                do {
                    if (n >= cursor2.getCount()) {
                        ArrayAdapter arrayAdapter4 = new ArrayAdapter((Context)this, 17367049, arrobject2);
                        this.tableno.setAdapter((SpinnerAdapter)arrayAdapter4);
                        cursor2.close();
                        return;
                    }
                    int n2 = n + 1;
                    arrobject2[n2] = cursor2.getString(0);
                    cursor2.moveToNext();
                    n = 1 + (n2 - 1);
                } while (true);
            }
        }
    }

}

