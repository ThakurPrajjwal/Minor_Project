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
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
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
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
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
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Print.Bluetooth;
import com.neoAntara.newrestaurant.Print.PlainPrint;
import com.neoAntara.newrestaurant.Reports;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;
import com.zj.btsdk.BluetoothService;
import java.io.PrintStream;
import java.util.ArrayList;

public class Reprint
extends Activity {
    private static int existing_srno;
    private static int item_count;
    private static int srno;
    String Address;
    String Cust_name;
    public float Disc_amt = 0.0f;
    String Discount;
    public float OC_amt = 0.0f;
    DBHelper ObjdbHelp;
    String OtherCharges;
    String Phoneno;
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
    private TextView date = null;
    public EditText discount = null;
    String dt = "";
    float edit_prize = 0.0f;
    int effectivePrintWidth = 48;
    private Spinner ext_orders = null;
    float f = 0.0f;
    float grand_total = 0.0f;
    public TextView grnd_total = null;
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
    SettingsModel settingsModel = new SettingsModel();
    float st = 0.0f;
    public int stflag = 0;
    public String stphoneno;
    String str_Vat = "";
    String str_content = "";
    String str_st = "";
    String str_tax = "";
    private TableLayout tablelayout = null;
    private Spinner tableno = null;
    float tax = 0.0f;
    public TextView total = null;
    float total_price = 0.0f;
    private TextView totalamt = null;
    float totalprize = 0.0f;
    private TableRow tr = null;
    private TextView txtViewPaymentType = null;
    private TextView txt_Discount = null;
    private TextView txt_OtherCharges = null;
    private TextView txt_grandtotal = null;
    private TextView txt_orderno = null;
    private TextView txt_tax = null;
    private TextView txtst = null;
    private TextView txtv_SrNo = null;
    private TextView txtv_itemname = null;
    private TextView txtv_prize = null;
    private TextView txtv_quantity = null;
    public int vatflag = 0;
    public String website;

    static /* synthetic */ int access$1408() {
        int n = srno;
        srno = n + 1;
        return n;
    }

    static /* synthetic */ int access$1410() {
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
        return "Other";
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
                            if (Reprint.this.tableno.getSelectedItem().toString().matches("Home Delivery")) {
                                Reprint.this.alertforparcelorder("yes");
                                return;
                            }
                            Reprint.this.alertfordiscount("yes");
                            return;
                        }
                        case -2: 
                    }
                    Reprint.this.btnSubmit_Click("no");
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
        AlertDialog.Builder builder;
        DialogInterface.OnClickListener onClickListener;
        LinearLayout linearLayout;
        block8 : {
            block9 : {
                try {
                    onClickListener = new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            switch (n) {
                                default: {
                                    return;
                                }
                                case -1: 
                            }
                            Reprint.this.btnSubmit_Click(string2);
                        }
                    };
                    builder = new AlertDialog.Builder((Context)this);
                    this.other_charges = new EditText((Context)this);
                    this.other_charges.setHint((CharSequence)"Other Charges");
                    this.discount = new EditText((Context)this);
                    this.discount.setHint((CharSequence)"Discount");
                    this.total = new TextView((Context)this);
                    this.total.setText(this.txt_grandtotal.getText());
                    this.total.setTextSize(18.0f);
                    this.txtst = new TextView((Context)this);
                    this.txt_tax = new TextView((Context)this);
                    this.grnd_total = new TextView((Context)this);
                    this.discount.setInputType(2);
                    this.other_charges.setInputType(2);
                    linearLayout = new LinearLayout((Context)this);
                    linearLayout.setOrientation(1);
                    linearLayout.addView((View)this.other_charges);
                    linearLayout.addView((View)this.discount);
                    linearLayout.addView((View)this.total);
                    this.total_price = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]);
                    Cursor cursor = this.ObjdbHelp.getRestoDetails();
                    cursor.moveToFirst();
                    this.str_tax = cursor.getString(5);
                    this.str_st = cursor.getString(4);
                    this.str_Vat = cursor.getString(6);
                    cursor.close();
                    if (this.str_Vat.matches("") && this.str_st.matches("")) {
                        this.st = 0.0f;
                        this.Vat = 0.0f;
                        this.grand_total = this.total_price;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txtst.setVisibility(8);
                        this.txt_tax.setVisibility(8);
                        break block8;
                    }
                    if (this.str_Vat.matches("") && this.str_st != "") {
                        this.st = Float.parseFloat((String)this.str_st);
                        this.Vat = 0.0f;
                        float f = this.total_price * (this.st / 100.0f);
                        TextView textView = this.txtst;
                        StringBuilder stringBuilder = new StringBuilder().append("ST(").append(this.st).append("%) :");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        this.grand_total = f + this.total_price;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txt_tax.setVisibility(8);
                        break block8;
                    }
                    if (!this.str_st.matches("") || this.str_Vat == "") break block9;
                    this.st = 0.0f;
                    this.Vat = Float.parseFloat((String)this.str_Vat);
                    float f = this.total_price * (this.Vat / 100.0f);
                    TextView textView = this.txt_tax;
                    StringBuilder stringBuilder = new StringBuilder().append("Vat(").append(this.Vat).append("%) :");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    this.grand_total = f + this.total_price;
                    linearLayout.addView((View)this.txtst);
                    linearLayout.addView((View)this.txt_tax);
                    linearLayout.addView((View)this.grnd_total);
                    this.txtst.setVisibility(8);
                    break block8;
                }
                catch (Exception exception) {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
                    return;
                }
            }
            if (this.str_Vat != "" && this.str_st != "") {
                this.st = Float.parseFloat((String)this.str_st);
                this.Vat = Float.parseFloat((String)this.str_Vat);
                float f = this.total_price * (this.Vat / 100.0f);
                float f2 = this.total_price * (this.st / 100.0f);
                TextView textView = this.txt_tax;
                StringBuilder stringBuilder = new StringBuilder().append("Vat(").append(this.Vat).append("%) :");
                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                TextView textView2 = this.txtst;
                StringBuilder stringBuilder2 = new StringBuilder().append("ST(").append(this.st).append("%) :");
                Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                this.grand_total = f2 + (f + this.total_price);
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
        this.other_charges.setText((CharSequence)this.OtherCharges);
        this.discount.setText((CharSequence)this.Discount);
        if (this.OtherCharges != "") {
            this.get1(this.st, this.Vat);
        }
        if (this.Discount != "") {
            this.get2(this.st, this.Vat);
        }
        builder.setView((View)linearLayout);
        builder.setPositiveButton((CharSequence)"Ok", onClickListener);
        builder.setNegativeButton((CharSequence)"Cancel", onClickListener).show();
        this.other_charges.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!Reprint.this.discount.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)Reprint.this.discount.getText().toString());
                        TextView textView = Reprint.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (Reprint.this.Vat / 100.0f);
                        float f3 = f * (Reprint.this.st / 100.0f);
                        TextView textView2 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = Reprint.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        Reprint.this.grand_total = f3 + (f + f2);
                        TextView textView4 = Reprint.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)Reprint.this.grand_total)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f4 = f * (Reprint.this.Vat / 100.0f);
                    float f5 = f * (Reprint.this.st / 100.0f);
                    TextView textView5 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f4)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = Reprint.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f5)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    Reprint.this.grand_total = f5 + (f + f4);
                    TextView textView7 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)Reprint.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)Reprint.this.other_charges.getText().toString());
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f6 = f * (Reprint.this.Vat / 100.0f);
                    float f7 = f * (Reprint.this.st / 100.0f);
                    TextView textView8 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f6)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = Reprint.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f7)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f8 = f7 + (f + f6);
                    TextView textView10 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f8)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (Reprint.this.discount.getText().toString().matches("")) return;
                    {
                        float f9 = Float.parseFloat((String)Reprint.this.total.getText().toString().split(" ")[2]) - Float.parseFloat((String)Reprint.this.discount.getText().toString());
                        TextView textView11 = Reprint.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f9)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f10 = f9 * (Reprint.this.Vat / 100.0f);
                        float f11 = f9 * (Reprint.this.st / 100.0f);
                        TextView textView12 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f10)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = Reprint.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f11)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f12 = f11 + (f9 + f10);
                        TextView textView14 = Reprint.this.grnd_total;
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
                    if (!Reprint.this.other_charges.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)Reprint.this.other_charges.getText().toString());
                        TextView textView = Reprint.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (Reprint.this.Vat / 100.0f);
                        float f3 = f * (Reprint.this.st / 100.0f);
                        TextView textView2 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = Reprint.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        float f4 = f3 + (f + f2);
                        TextView textView4 = Reprint.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f5 = f * (Reprint.this.Vat / 100.0f);
                    float f6 = f * (Reprint.this.st / 100.0f);
                    TextView textView5 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f5)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = Reprint.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f6)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    Reprint.this.grand_total = f6 + (f + f5);
                    TextView textView7 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)Reprint.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)Reprint.this.discount.getText().toString());
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f7 = f * (Reprint.this.Vat / 100.0f);
                    float f8 = f * (Reprint.this.st / 100.0f);
                    TextView textView8 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f7)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = Reprint.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f8)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f9 = f8 + (f + f7);
                    TextView textView10 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f9)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (Reprint.this.other_charges.getText().toString().matches("")) return;
                    {
                        float f10 = Float.parseFloat((String)Reprint.this.total.getText().toString().split(" ")[2]) + Float.parseFloat((String)Reprint.this.other_charges.getText().toString());
                        TextView textView11 = Reprint.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f10)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f11 = f10 * (Reprint.this.Vat / 100.0f);
                        float f12 = f10 * (Reprint.this.st / 100.0f);
                        TextView textView12 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f11)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = Reprint.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f12)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f13 = f12 + (f10 + f11);
                        TextView textView14 = Reprint.this.grnd_total;
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
                            Reprint.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            Reprint.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Reprint.this.editorder("HD");
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
        AlertDialog.Builder builder;
        DialogInterface.OnClickListener onClickListener;
        LinearLayout linearLayout;
        block8 : {
            block9 : {
                try {
                    onClickListener = new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            switch (n) {
                                default: {
                                    return;
                                }
                                case -1: 
                            }
                            Reprint.this.btnSubmit_Click(string2);
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
                    this.total = new TextView((Context)this);
                    this.total.setText(this.txt_grandtotal.getText());
                    this.total.setTextSize(18.0f);
                    this.txtst = new TextView((Context)this);
                    this.txt_tax = new TextView((Context)this);
                    this.grnd_total = new TextView((Context)this);
                    this.discount.setInputType(2);
                    this.phone_no.setInputType(2);
                    linearLayout = new LinearLayout((Context)this);
                    linearLayout.setOrientation(1);
                    linearLayout.addView((View)this.name);
                    linearLayout.addView((View)this.address);
                    linearLayout.addView((View)this.phone_no);
                    linearLayout.addView((View)this.other_charges);
                    linearLayout.addView((View)this.discount);
                    linearLayout.addView((View)this.total);
                    this.total_price = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]);
                    Cursor cursor = this.ObjdbHelp.getRestoDetails();
                    cursor.moveToFirst();
                    this.str_tax = cursor.getString(5);
                    this.str_st = cursor.getString(4);
                    this.str_Vat = cursor.getString(6);
                    cursor.close();
                    if (this.str_Vat.matches("") && this.str_st.matches("")) {
                        this.st = 0.0f;
                        this.Vat = 0.0f;
                        this.grand_total = this.total_price;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txtst.setVisibility(8);
                        this.txt_tax.setVisibility(8);
                        break block8;
                    }
                    if (this.str_Vat.matches("") && this.str_st != "") {
                        this.st = Float.parseFloat((String)this.str_st);
                        this.Vat = 0.0f;
                        float f = this.total_price * (this.st / 100.0f);
                        TextView textView = this.txtst;
                        StringBuilder stringBuilder = new StringBuilder().append("ST(").append(this.st).append("%) :");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        this.grand_total = f + this.total_price;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txt_tax.setVisibility(8);
                        break block8;
                    }
                    if (!this.str_st.matches("") || this.str_Vat == "") break block9;
                    this.st = 0.0f;
                    this.Vat = Float.parseFloat((String)this.str_Vat);
                    float f = this.total_price * (this.Vat / 100.0f);
                    TextView textView = this.txt_tax;
                    StringBuilder stringBuilder = new StringBuilder().append("Vat(").append(this.Vat).append("%) :");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    this.grand_total = f + this.total_price;
                    linearLayout.addView((View)this.txtst);
                    linearLayout.addView((View)this.txt_tax);
                    linearLayout.addView((View)this.grnd_total);
                    this.txtst.setVisibility(8);
                    break block8;
                }
                catch (Exception exception) {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
                    return;
                }
            }
            if (this.str_Vat != "" && this.str_st != "") {
                this.st = Float.parseFloat((String)this.str_st);
                this.Vat = Float.parseFloat((String)this.str_Vat);
                float f = this.total_price * (this.Vat / 100.0f);
                float f2 = this.total_price * (this.st / 100.0f);
                TextView textView = this.txt_tax;
                StringBuilder stringBuilder = new StringBuilder().append("Vat(").append(this.Vat).append("%) :");
                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                TextView textView2 = this.txtst;
                StringBuilder stringBuilder2 = new StringBuilder().append("ST(").append(this.st).append("%) :");
                Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                this.grand_total = f2 + (f + this.total_price);
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
        this.name.setText((CharSequence)this.Cust_name);
        this.address.setText((CharSequence)this.Address);
        this.phone_no.setText((CharSequence)this.Phoneno);
        this.other_charges.setText((CharSequence)this.OtherCharges);
        this.discount.setText((CharSequence)this.Discount);
        builder.setView((View)linearLayout);
        builder.setPositiveButton((CharSequence)"Ok", onClickListener);
        builder.setNegativeButton((CharSequence)"Cancel", onClickListener).show();
        if (this.OtherCharges != "") {
            this.get1(this.st, this.Vat);
        }
        if (this.Discount != "") {
            this.get2(this.st, this.Vat);
        }
        this.other_charges.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!Reprint.this.discount.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)Reprint.this.discount.getText().toString());
                        TextView textView = Reprint.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (Reprint.this.Vat / 100.0f);
                        float f3 = f * (Reprint.this.st / 100.0f);
                        TextView textView2 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = Reprint.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        Reprint.this.grand_total = f3 + (f + f2);
                        TextView textView4 = Reprint.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)Reprint.this.grand_total)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f4 = f * (Reprint.this.Vat / 100.0f);
                    float f5 = f * (Reprint.this.st / 100.0f);
                    TextView textView5 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f4)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = Reprint.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f5)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    Reprint.this.grand_total = f5 + (f + f4);
                    TextView textView7 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)Reprint.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)Reprint.this.other_charges.getText().toString());
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f6 = f * (Reprint.this.Vat / 100.0f);
                    float f7 = f * (Reprint.this.st / 100.0f);
                    TextView textView8 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f6)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = Reprint.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f7)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f8 = f7 + (f + f6);
                    TextView textView10 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f8)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (Reprint.this.discount.getText().toString().matches("")) return;
                    {
                        float f9 = Float.parseFloat((String)Reprint.this.total.getText().toString().split(" ")[2]) - Float.parseFloat((String)Reprint.this.discount.getText().toString());
                        TextView textView11 = Reprint.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f9)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f10 = f9 * (Reprint.this.Vat / 100.0f);
                        float f11 = f9 * (Reprint.this.st / 100.0f);
                        TextView textView12 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f10)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = Reprint.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f11)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f12 = f11 + (f9 + f10);
                        TextView textView14 = Reprint.this.grnd_total;
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
                    if (!Reprint.this.other_charges.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)Reprint.this.other_charges.getText().toString());
                        TextView textView = Reprint.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (Reprint.this.Vat / 100.0f);
                        float f3 = f * (Reprint.this.st / 100.0f);
                        TextView textView2 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = Reprint.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        float f4 = f3 + (f + f2);
                        TextView textView4 = Reprint.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f5 = f * (Reprint.this.Vat / 100.0f);
                    float f6 = f * (Reprint.this.st / 100.0f);
                    TextView textView5 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f5)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = Reprint.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f6)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    Reprint.this.grand_total = f6 + (f + f5);
                    TextView textView7 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)Reprint.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)Reprint.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)Reprint.this.discount.getText().toString());
                    TextView textView = Reprint.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f7 = f * (Reprint.this.Vat / 100.0f);
                    float f8 = f * (Reprint.this.st / 100.0f);
                    TextView textView8 = Reprint.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f7)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = Reprint.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f8)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f9 = f8 + (f + f7);
                    TextView textView10 = Reprint.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f9)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (Reprint.this.other_charges.getText().toString().matches("")) return;
                    {
                        float f10 = Float.parseFloat((String)Reprint.this.total.getText().toString().split(" ")[2]) + Float.parseFloat((String)Reprint.this.other_charges.getText().toString());
                        TextView textView11 = Reprint.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f10)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f11 = f10 * (Reprint.this.Vat / 100.0f);
                        float f12 = f10 * (Reprint.this.st / 100.0f);
                        TextView textView12 = Reprint.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("Vat(").append(Reprint.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f11)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = Reprint.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f12)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f13 = f12 + (f10 + f11);
                        TextView textView14 = Reprint.this.grnd_total;
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
                            Reprint.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            Reprint.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Reprint.this.reprintorder("HD");
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
                    Reprint.this.go();
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
                            Reprint.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            Reprint.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Reprint.this.parcelorder("HD");
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
        block17 : {
            block15 : {
                block18 : {
                    block16 : {
                        if (this.tableno.getSelectedItem().toString().matches("Table No")) {
                            var51_2 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Select Table No", (int)1);
                            var51_2.setGravity(17, 0, 0);
                            var51_2.show();
                            return;
                        }
                        if (this.item.getText().toString().matches("") || this.item.getText().equals(null)) {
                            var5_3 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Item Name", (int)1);
                            var5_3.setGravity(17, 0, 0);
                            var5_3.show();
                            this.item.setText((CharSequence)"");
                            return;
                        }
                        if (this.quantity.getText().toString().matches("") || this.quantity.getText().equals(null)) {
                            var6_5 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Quantity", (int)1);
                            var6_5.setGravity(17, 0, 0);
                            var6_5.show();
                            this.quantity.setText((CharSequence)"");
                            return;
                        }
                        var7_8 = Integer.parseInt((String)this.quantity.getText().toString());
                        var8_9 = this.item.getText().toString();
                        var9_10 = this.ObjdbHelp.getItemCode(var8_9);
                        if (var9_10.getCount() <= 0) break block15;
                        var9_10.moveToFirst();
                        var12_11 = Integer.parseInt((String)var9_10.getString(0).toString());
                        this.prize = Float.parseFloat((String)var9_10.getString(1).toString());
                        var13_12 = this.totalamt.getText().toString().split(" ");
                        if (!"".matches("") || !"".matches("")) break block16;
                        this.stflag = 0;
                        this.vatflag = 0;
                        this.totalprize = Float.parseFloat((String)var13_12[2]);
                        this.f = this.prize * (float)var7_8;
                        this.totalprize += this.f;
                        this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                        this.txt_tax.setText((CharSequence)"");
                        this.txtst.setText((CharSequence)"");
                        this.grand_total = this.totalprize;
                        var48_13 = this.txt_grandtotal;
                        var49_14 = new StringBuilder().append("Total : ");
                        var50_15 = new Object[]{Float.valueOf((float)this.grand_total)};
                        var48_13.setText((CharSequence)var49_14.append(String.format((String)"%.2f", (Object[])var50_15)).toString());
                    }
                    if (!"".matches("") || "" == "") ** GOTO lbl65
                    this.stflag = 1;
                    this.vatflag = 0;
                    this.st = Float.parseFloat((String)"");
                    this.totalprize = Float.parseFloat((String)var13_12[2]);
                    this.f = this.prize * (float)var7_8;
                    this.totalprize += this.f;
                    this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                    var14_16 = this.totalprize * (this.st / 100.0f);
                    this.txt_tax.setText((CharSequence)"");
                    var15_17 = this.txtst;
                    var16_18 = new StringBuilder().append("ST(").append(this.st).append("%) : ");
                    var17_19 = new Object[]{Float.valueOf((float)var14_16)};
                    var15_17.setText((CharSequence)var16_18.append(String.format((String)"%.2f", (Object[])var17_19)).toString());
                    this.grand_total = var14_16 + this.totalprize;
                    var18_20 = this.txt_grandtotal;
                    var19_21 = new StringBuilder().append("Total : ");
                    var20_22 = new Object[]{Float.valueOf((float)this.grand_total)};
                    var18_20.setText((CharSequence)var19_21.append(String.format((String)"%.2f", (Object[])var20_22)).toString());
                    break block17;
lbl65: // 1 sources:
                    if (!"".matches("") || "" == "") break block18;
                    try {
                        this.stflag = 0;
                        this.vatflag = 1;
                        this.Vat = Float.parseFloat((String)"");
                        this.totalprize = Float.parseFloat((String)var13_12[2]);
                        this.f = this.prize * (float)var7_8;
                        this.totalprize += this.f;
                        this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                        var30_23 = this.totalprize * (this.Vat / 100.0f);
                        var31_24 = this.txt_tax;
                        var32_25 = new StringBuilder().append("Vat(").append(var30_23).append("%) : ");
                        var33_26 = new Object[]{Float.valueOf((float)var30_23)};
                        var31_24.setText((CharSequence)var32_25.append(String.format((String)"%.2f", (Object[])var33_26)).toString());
                        this.txtst.setText((CharSequence)"");
                        this.grand_total = this.totalprize + this.Vat;
                        var34_27 = this.txt_grandtotal;
                        var35_28 = new StringBuilder().append("Total : ");
                        var36_29 = new Object[]{Float.valueOf((float)this.grand_total)};
                        var34_27.setText((CharSequence)var35_28.append(String.format((String)"%.2f", (Object[])var36_29)).toString());
                    }
                    catch (SQLException var4_4) {
                        UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var4_4);
                        return;
                    }
                    catch (Exception var2_6) {
                        UtilityHelper.GenericCatchBlock(this.getApplicationContext(), var2_6);
                        var3_7 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Wrong User id", (int)0);
                        var3_7.setGravity(17, 0, 0);
                        var3_7.show();
                        return;
                    }
                }
                if ("" != "" && "" != "") {
                    this.stflag = 1;
                    this.vatflag = 1;
                    this.st = Float.parseFloat((String)"");
                    this.Vat = Float.parseFloat((String)"");
                    this.totalprize = Float.parseFloat((String)var13_12[2]);
                    this.f = this.prize * (float)var7_8;
                    this.totalprize += this.f;
                    this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                    var37_30 = this.totalprize * (this.Vat / 100.0f);
                    var38_31 = this.totalprize * (this.st / 100.0f);
                    var39_32 = this.txt_tax;
                    var40_33 = new StringBuilder().append("Vat(").append(this.Vat).append("%) :");
                    var41_34 = new Object[]{Float.valueOf((float)var37_30)};
                    var39_32.setText((CharSequence)var40_33.append(String.format((String)"%.2f", (Object[])var41_34)).toString());
                    var42_35 = this.txtst;
                    var43_36 = new StringBuilder().append("ST(").append(this.st).append("%) :");
                    var44_37 = new Object[]{Float.valueOf((float)var38_31)};
                    var42_35.setText((CharSequence)var43_36.append(String.format((String)"%.2f", (Object[])var44_37)).toString());
                    this.grand_total = var37_30 + (var38_31 + this.totalprize);
                    var45_38 = this.txt_grandtotal;
                    var46_39 = new StringBuilder().append("Total : ");
                    var47_40 = new Object[]{Float.valueOf((float)this.grand_total)};
                    var45_38.setText((CharSequence)var46_39.append(String.format((String)"%.2f", (Object[])var47_40)).toString());
                }
                break block17;
            }
            var10_50 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Wrong Item", (int)1);
            var10_50.setGravity(17, 0, 0);
            var10_50.show();
            this.quantity.setText((CharSequence)"");
            this.item.setText((CharSequence)"");
            return;
        }
        this.addeditems[Reprint.item_count] = this.item.getText().toString();
        this.addedquant[Reprint.item_count] = this.quantity.getText().toString();
        this.addeditemcode[Reprint.item_count] = var12_11 + "";
        this.arr_prize[Reprint.item_count] = this.f + "";
        this.quantity.setText((CharSequence)"");
        this.item.setText((CharSequence)"");
        var9_10.close();
        var21_41 = 0;
        while (var21_41 < (var22_42 = this.addeditems.length)) {
            if (this.addeditems[var21_41] == null) return;
            if (Reprint.existing_srno == 5) {
                Reprint.srno = -1 + Reprint.srno;
            }
            this.tr = var23_43 = new TableRow((Context)this);
            this.txtv_SrNo = var24_44 = new TextView((Context)this);
            this.txtv_SrNo.setText((CharSequence)(Reprint.srno + ""));
            this.txtv_SrNo.setTextColor(-16777216);
            this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_SrNo.setPadding(10, 10, 10, 10);
            this.txtv_SrNo.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_SrNo);
            Reprint.existing_srno = 1;
            this.txtv_itemname = var25_45 = new TextView((Context)this);
            this.txtv_itemname.setText((CharSequence)this.addeditems[var21_41]);
            this.txtv_itemname.setTextColor(-16777216);
            this.txtv_itemname.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_itemname.setPadding(10, 10, 10, 10);
            this.txtv_itemname.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_itemname);
            this.txtv_quantity = var26_46 = new TextView((Context)this);
            this.txtv_quantity.setText((CharSequence)this.addedquant[var21_41]);
            this.txtv_quantity.setTextColor(-16777216);
            this.txtv_quantity.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_quantity.setPadding(10, 10, 10, 10);
            this.txtv_quantity.setTextSize(16.0f);
            this.txtv_quantity.setTextAlignment(0);
            this.tr.addView((View)this.txtv_quantity);
            this.txtv_prize = var27_47 = new TextView((Context)this);
            this.txtv_prize.setText((CharSequence)this.arr_prize[var21_41]);
            this.txtv_prize.setTextColor(-16777216);
            this.txtv_prize.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_prize.setPadding(10, 10, 10, 10);
            this.txtv_prize.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_prize);
            this.tr.setBackgroundResource(2131099778);
            Reprint.srno = 1 + Reprint.srno;
            this.tablelayout.addView((View)this.tr);
            var28_48 = this.tr;
            var29_49 = new View.OnClickListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void onClick(View view) {
                    TableRow tableRow = (TableRow)view;
                    TextView textView = (TextView)tableRow.getChildAt(1);
                    TextView textView2 = (TextView)tableRow.getChildAt(2);
                    TextView textView3 = (TextView)tableRow.getChildAt(0);
                    TextView textView4 = (TextView)tableRow.getChildAt(3);
                    String string2 = textView3.getText().toString();
                    String string3 = textView4.getText().toString();
                    Reprint.this.edit_prize = Float.parseFloat((String)string3);
                    Reprint.this.item.setText(textView.getText());
                    Reprint.this.quantity.setText(textView2.getText());
                    int n = Reprint.this.tablelayout.getChildCount();
                    int n2 = 0;
                    do {
                        TableRow tableRow2;
                        if (n2 < n) {
                            tableRow2 = (TableRow)Reprint.this.tablelayout.getChildAt(n2);
                        } else {
                            Reprint.this.tablelayout.removeView(view);
                            Reprint.access$1410();
                            String[] arrstring = Reprint.this.totalamt.getText().toString().split(" ");
                            Reprint.this.totalprize = Float.parseFloat((String)arrstring[2]);
                            Reprint.this.totalprize -= Reprint.this.edit_prize;
                            if (Reprint.this.stflag == 1 && Reprint.this.vatflag == 1) {
                                float f = Reprint.this.totalprize * (Reprint.this.st / 100.0f);
                                TextView textView5 = Reprint.this.txtst;
                                StringBuilder stringBuilder = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) : ");
                                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                                textView5.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                                float f2 = Reprint.this.totalprize * (Reprint.this.Vat / 100.0f);
                                TextView textView6 = Reprint.this.txt_tax;
                                StringBuilder stringBuilder2 = new StringBuilder().append("VAT(").append(Reprint.this.Vat).append("%) : ");
                                Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                                textView6.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                                Reprint.this.grand_total = f2 + (f + Reprint.this.totalprize);
                            } else if (Reprint.this.stflag == 0 && Reprint.this.vatflag == 1) {
                                float f = Reprint.this.totalprize * (Reprint.this.Vat / 100.0f);
                                TextView textView7 = Reprint.this.txt_tax;
                                StringBuilder stringBuilder = new StringBuilder().append("VAT(").append(Reprint.this.Vat).append("%) : ");
                                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                                textView7.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                                Reprint.this.grand_total = f + Reprint.this.totalprize;
                            } else if (Reprint.this.stflag == 1 && Reprint.this.vatflag == 0) {
                                float f = Reprint.this.totalprize * (Reprint.this.st / 100.0f);
                                TextView textView8 = Reprint.this.txtst;
                                StringBuilder stringBuilder = new StringBuilder().append("ST(").append(Reprint.this.st).append("%) : ");
                                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                                textView8.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                                Reprint.this.grand_total = f + Reprint.this.totalprize;
                            } else if (Reprint.this.stflag == 0 && Reprint.this.vatflag == 0) {
                                Reprint.this.txt_tax.setText((CharSequence)"");
                                Reprint.this.txtst.setText((CharSequence)"");
                                Reprint.this.grand_total = Reprint.this.totalprize;
                            }
                            TextView textView9 = Reprint.this.txt_grandtotal;
                            StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                            Object[] arrobject = new Object[]{Float.valueOf((float)Reprint.this.grand_total)};
                            textView9.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                            Reprint.this.edit_prize = 0.0f;
                            Reprint.this.totalamt.setText((CharSequence)("Amount : " + Reprint.this.totalprize));
                            return;
                        }
                        for (int i = 0; i < n; ++i) {
                            TableRow tableRow3;
                            if (i != 0 || !string2.equalsIgnoreCase(((TextView)tableRow2.getChildAt(i)).getText().toString())) continue;
                            for (int j = ++n2; j < n && (tableRow3 = (TableRow)Reprint.this.tablelayout.getChildAt(j)) != null; ++j) {
                                TextView textView10 = (TextView)tableRow3.getChildAt(i);
                                int n3 = -1 + Integer.parseInt((String)textView10.getText().toString());
                                textView10.setText((CharSequence)(n3 + ""));
                            }
                        }
                        ++n2;
                    } while (true);
                }
            };
            var28_48.setOnClickListener(var29_49);
            ++var21_41;
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
                var5_2 = this.tablelayout.getChildCount();
                if (var5_2 <= 0) {
                    UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Item");
                    return;
                }
                if (this.tableno.getSelectedItem().toString().matches("Table No")) {
                    UtilityHelper.showToastMessage(this.getApplicationContext(), "Select Table No");
                    return;
                }
                var6_3 = this.tableno.getSelectedItem().toString();
                var7_4 = this.ext_orders.getSelectedItem().toString();
                var8_5 = this.ObjdbHelp.getOrderDetails();
                if (var1_1 != "yes") break block20;
                var9_6 = new PlainPrint(this.getApplicationContext(), this.effectivePrintWidth, 4);
                var9_6.prepareTabularForm(4, 0, 3, true);
                this.mService = Bluetooth.getServiceInstance();
                var10_7 = new ArrayList();
                try {
                    UtilityHelper.printHeader(this.mService, var9_6, var7_4, (ArrayList<String>)var10_7, this.settingsModel, this.getPaymentType(), this.getApplicationContext());
                    if (var6_3 == "Home Delivery") {
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
                    var4_37 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Wrong User id", (int)0);
                    var4_37.setGravity(17, 0, 0);
                    var4_37.show();
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
            var9_6 = null;
            var10_7 = null;
        }
        var11_9 = 0;
        var12_10 = 0.0f;
        do {
            if (var11_9 < var5_2) {
                if (var11_9 > 0) {
                    var21_11 = (TableRow)this.tablelayout.getChildAt(var11_9);
                    var22_12 = (TextView)var21_11.getChildAt(1);
                    var23_13 = (TextView)var21_11.getChildAt(2);
                    (TextView)var21_11.getChildAt(0);
                    var25_14 = (TextView)var21_11.getChildAt(3);
                    var26_15 = Integer.parseInt((String)var23_13.getText().toString());
                    var12_10 += Float.parseFloat((String)var25_14.getText().toString());
                    this.ObjdbHelp.SubmitOrder(var6_3, var8_5, var22_12.getText().toString(), var25_14.getText().toString(), var26_15, this.Cust_name, this.Address, this.Phoneno, this.Discount, this.OtherCharges, var1_1, this.getPaymentType(), var7_4, var11_9);
                    if (var1_1.matches("yes")) {
                        this.ObjdbHelp.PrintOrder(var6_3, var8_5, var22_12.getText().toString(), var25_14.getText().toString(), var26_15, this.Cust_name, this.Address, this.Phoneno, this.Discount, this.OtherCharges, this.getPaymentType(), var7_4, var11_9);
                        var27_16 = this.getItemPrize(var22_12.getText().toString());
                        var10_7.clear();
                        var28_17 = this.mService;
                        var29_18 = var22_12.getText().toString();
                        var30_19 = var23_13.getText().toString();
                        var31_20 = var25_14.getText().toString();
                        var32_8 = this.getApplicationContext();
                        UtilityHelper.printTabularData(var28_17, var9_6, null, (ArrayList<String>)var10_7, var7_4, var29_18, var30_19, var27_16, var31_20, var32_8);
                    }
                }
            } else {
                if (var1_1.matches("yes")) {
                    var13_21 = this.mService;
                    var14_22 = this.Cust_name;
                    var15_23 = this.Address;
                    var16_24 = this.Phoneno;
                    var17_25 = this.Discount;
                    var18_26 = this.OtherCharges;
                    var19_27 = this.settingsModel;
                    var20_28 = this.getApplicationContext();
                    UtilityHelper.printFooter(var13_21, var9_6, (ArrayList<String>)var10_7, var12_10, var6_3, var14_22, var15_23, var16_24, var17_25, var18_26, var19_27, var20_28);
                }
                this.tablelayout.removeAllViews();
                this.setup();
                this.totalamt.setText((CharSequence)"Amount : 0");
                this.txt_tax.setText((CharSequence)"");
                this.txt_grandtotal.setText((CharSequence)"Total : 0");
                this.item.setText((CharSequence)"");
                this.quantity.setText((CharSequence)"");
                this.tableno.setSelection(0);
                this.ext_orders.setSelection(0);
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Order Submitted Successfully");
                return;
            }
            ++var11_9;
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
                    Reprint.this.exportDb();
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

    /*
     * Enabled aggressive block sorting
     */
    public void get1(float f, float f2) {
        if (this.other_charges.getText().toString().matches("")) {
            if (!this.discount.getText().toString().matches("")) {
                float f3 = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)this.discount.getText().toString());
                TextView textView = this.total;
                StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                Object[] arrobject = new Object[]{Float.valueOf((float)f3)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                float f4 = f3 * (f2 / 100.0f);
                float f5 = f3 * (f / 100.0f);
                TextView textView2 = this.txt_tax;
                StringBuilder stringBuilder2 = new StringBuilder().append("Vat(").append(f2).append("%) :");
                Object[] arrobject2 = new Object[]{Float.valueOf((float)f4)};
                textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                TextView textView3 = this.txtst;
                StringBuilder stringBuilder3 = new StringBuilder().append("ST(").append(f).append("%) :");
                Object[] arrobject3 = new Object[]{Float.valueOf((float)f5)};
                textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                this.grand_total = f5 + (f3 + f4);
                TextView textView4 = this.grnd_total;
                StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                Object[] arrobject4 = new Object[]{Float.valueOf((float)this.grand_total)};
                textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                return;
            }
            float f6 = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]);
            TextView textView = this.total;
            StringBuilder stringBuilder = new StringBuilder().append("Total : ");
            Object[] arrobject = new Object[]{Float.valueOf((float)f6)};
            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
            float f7 = f6 * (f2 / 100.0f);
            float f8 = f6 * (f / 100.0f);
            TextView textView5 = this.txt_tax;
            StringBuilder stringBuilder5 = new StringBuilder().append("Vat(").append(f2).append("%) :");
            Object[] arrobject5 = new Object[]{Float.valueOf((float)f7)};
            textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
            TextView textView6 = this.txtst;
            StringBuilder stringBuilder6 = new StringBuilder().append("ST(").append(f).append("%) :");
            Object[] arrobject6 = new Object[]{Float.valueOf((float)f8)};
            textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
            this.grand_total = f8 + (f6 + f7);
            TextView textView7 = this.grnd_total;
            StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
            Object[] arrobject7 = new Object[]{Float.valueOf((float)this.grand_total)};
            textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
            return;
        } else {
            float f9 = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)this.other_charges.getText().toString());
            TextView textView = this.total;
            StringBuilder stringBuilder = new StringBuilder().append("Total : ");
            Object[] arrobject = new Object[]{Float.valueOf((float)f9)};
            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
            float f10 = f9 * (f2 / 100.0f);
            float f11 = f9 * (f / 100.0f);
            TextView textView8 = this.txt_tax;
            StringBuilder stringBuilder8 = new StringBuilder().append("Vat(").append(f2).append("%) :");
            Object[] arrobject8 = new Object[]{Float.valueOf((float)f10)};
            textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
            TextView textView9 = this.txtst;
            StringBuilder stringBuilder9 = new StringBuilder().append("ST(").append(f).append("%) :");
            Object[] arrobject9 = new Object[]{Float.valueOf((float)f11)};
            textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
            float f12 = f11 + (f9 + f10);
            TextView textView10 = this.grnd_total;
            StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
            Object[] arrobject10 = new Object[]{Float.valueOf((float)f12)};
            textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
            if (this.discount.getText().toString().matches("")) return;
            {
                float f13 = Float.parseFloat((String)this.total.getText().toString().split(" ")[2]) - Float.parseFloat((String)this.discount.getText().toString());
                TextView textView11 = this.total;
                StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                Object[] arrobject11 = new Object[]{Float.valueOf((float)f13)};
                textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                float f14 = f13 * (f2 / 100.0f);
                float f15 = f13 * (f / 100.0f);
                TextView textView12 = this.txt_tax;
                StringBuilder stringBuilder12 = new StringBuilder().append("Vat(").append(f2).append("%) :");
                Object[] arrobject12 = new Object[]{Float.valueOf((float)f14)};
                textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                TextView textView13 = this.txtst;
                StringBuilder stringBuilder13 = new StringBuilder().append("ST(").append(f).append("%) :");
                Object[] arrobject13 = new Object[]{Float.valueOf((float)f15)};
                textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                float f16 = f15 + (f13 + f14);
                TextView textView14 = this.grnd_total;
                StringBuilder stringBuilder14 = new StringBuilder().append("GrandTotal : ");
                Object[] arrobject14 = new Object[]{Float.valueOf((float)f16)};
                textView14.setText((CharSequence)stringBuilder14.append(String.format((String)"%.2f", (Object[])arrobject14)).toString());
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void get2(float f, float f2) {
        if (this.discount.getText().toString().matches("")) {
            if (!this.other_charges.getText().toString().matches("")) {
                float f3 = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)this.other_charges.getText().toString());
                TextView textView = this.total;
                StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                Object[] arrobject = new Object[]{Float.valueOf((float)f3)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                float f4 = f3 * (f2 / 100.0f);
                float f5 = f3 * (f / 100.0f);
                TextView textView2 = this.txt_tax;
                StringBuilder stringBuilder2 = new StringBuilder().append("Vat(").append(f2).append("%) :");
                Object[] arrobject2 = new Object[]{Float.valueOf((float)f4)};
                textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                TextView textView3 = this.txtst;
                StringBuilder stringBuilder3 = new StringBuilder().append("ST(").append(f).append("%) :");
                Object[] arrobject3 = new Object[]{Float.valueOf((float)f5)};
                textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                float f6 = f5 + (f3 + f4);
                TextView textView4 = this.grnd_total;
                StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                Object[] arrobject4 = new Object[]{Float.valueOf((float)f6)};
                textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                return;
            }
            float f7 = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]);
            TextView textView = this.total;
            StringBuilder stringBuilder = new StringBuilder().append("Total : ");
            Object[] arrobject = new Object[]{Float.valueOf((float)f7)};
            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
            float f8 = f7 * (f2 / 100.0f);
            float f9 = f7 * (f / 100.0f);
            TextView textView5 = this.txt_tax;
            StringBuilder stringBuilder5 = new StringBuilder().append("Vat(").append(f2).append("%) :");
            Object[] arrobject5 = new Object[]{Float.valueOf((float)f8)};
            textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
            TextView textView6 = this.txtst;
            StringBuilder stringBuilder6 = new StringBuilder().append("ST(").append(f).append("%) :");
            Object[] arrobject6 = new Object[]{Float.valueOf((float)f9)};
            textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
            this.grand_total = f9 + (f7 + f8);
            TextView textView7 = this.grnd_total;
            StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
            Object[] arrobject7 = new Object[]{Float.valueOf((float)this.grand_total)};
            textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
            return;
        } else {
            float f10 = Float.parseFloat((String)this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)this.discount.getText().toString());
            TextView textView = this.total;
            StringBuilder stringBuilder = new StringBuilder().append("Total : ");
            Object[] arrobject = new Object[]{Float.valueOf((float)f10)};
            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
            float f11 = f10 * (f2 / 100.0f);
            float f12 = f10 * (f / 100.0f);
            TextView textView8 = this.txt_tax;
            StringBuilder stringBuilder8 = new StringBuilder().append("Vat(").append(f2).append("%) :");
            Object[] arrobject8 = new Object[]{Float.valueOf((float)f11)};
            textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
            TextView textView9 = this.txtst;
            StringBuilder stringBuilder9 = new StringBuilder().append("ST(").append(f).append("%) :");
            Object[] arrobject9 = new Object[]{Float.valueOf((float)f12)};
            textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
            float f13 = f12 + (f10 + f11);
            TextView textView10 = this.grnd_total;
            StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
            Object[] arrobject10 = new Object[]{Float.valueOf((float)f13)};
            textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
            if (this.other_charges.getText().toString().matches("")) return;
            {
                float f14 = Float.parseFloat((String)this.total.getText().toString().split(" ")[2]) + Float.parseFloat((String)this.other_charges.getText().toString());
                TextView textView11 = this.total;
                StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                Object[] arrobject11 = new Object[]{Float.valueOf((float)f14)};
                textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                float f15 = f14 * (f2 / 100.0f);
                float f16 = f14 * (f / 100.0f);
                TextView textView12 = this.txt_tax;
                StringBuilder stringBuilder12 = new StringBuilder().append("Vat(").append(f2).append("%) :");
                Object[] arrobject12 = new Object[]{Float.valueOf((float)f15)};
                textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                TextView textView13 = this.txtst;
                StringBuilder stringBuilder13 = new StringBuilder().append("ST(").append(f).append("%) :");
                Object[] arrobject13 = new Object[]{Float.valueOf((float)f16)};
                textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                float f17 = f16 + (f14 + f15);
                TextView textView14 = this.grnd_total;
                StringBuilder stringBuilder14 = new StringBuilder().append("GrandTotal : ");
                Object[] arrobject14 = new Object[]{Float.valueOf((float)f17)};
                textView14.setText((CharSequence)stringBuilder14.append(String.format((String)"%.2f", (Object[])arrobject14)).toString());
                return;
            }
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
                    Reprint.this.importDb();
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

    protected void onActivityResult(int n, int n2, Intent intent) {
        UtilityHelper.onActivityResult_Generic(n, n2, intent, this.getApplicationContext(), this);
    }

    /*
     * Exception decompiling
     */
    protected void onCreate(Bundle var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[TRYBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
        // java.lang.Thread.run(Thread.java:919)
        throw new IllegalStateException("Decompilation failed");
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

    public void setAdapter(String[] arrstring) {
        ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367043, (Object[])arrstring);
        this.ext_orders.setAdapter((SpinnerAdapter)arrayAdapter);
    }

    public void setTableLayout(String string2, String string3, String string4, int n) {
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)(n + ""));
        this.txtv_SrNo.setTextColor(-16777216);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 0);
        this.txtv_SrNo.setPadding(10, 10, 10, 10);
        this.txtv_SrNo.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txtv_itemname = new TextView((Context)this);
        this.txtv_itemname.setText((CharSequence)string2);
        this.txtv_itemname.setTextColor(-16777216);
        this.txtv_itemname.setTypeface(Typeface.DEFAULT, 0);
        this.txtv_itemname.setPadding(10, 10, 10, 10);
        this.txtv_itemname.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_itemname);
        this.txtv_quantity = new TextView((Context)this);
        this.txtv_quantity.setText((CharSequence)string3);
        this.txtv_quantity.setTextColor(-16777216);
        this.txtv_quantity.setTypeface(Typeface.DEFAULT, 0);
        this.txtv_quantity.setPadding(10, 10, 10, 10);
        this.txtv_quantity.setTextSize(16.0f);
        this.txtv_quantity.setTextAlignment(0);
        this.tr.addView((View)this.txtv_quantity);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)string4);
        this.txtv_prize.setTextColor(-16777216);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 0);
        this.txtv_prize.setPadding(10, 10, 10, 10);
        this.txtv_prize.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_prize);
        n + 1;
        this.tablelayout.addView((View)this.tr);
        this.tr.setBackgroundResource(2131099778);
        this.tr.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                TableRow tableRow = (TableRow)view;
                TextView textView = (TextView)tableRow.getChildAt(1);
                TextView textView2 = (TextView)tableRow.getChildAt(2);
                TextView textView3 = (TextView)tableRow.getChildAt(0);
                TextView textView4 = (TextView)tableRow.getChildAt(3);
                String string2 = textView3.getText().toString();
                String string3 = textView4.getText().toString();
                Reprint.this.edit_prize = Float.parseFloat((String)string3);
                Reprint.this.item.setText(textView.getText());
                Reprint.this.quantity.setText(textView2.getText());
                int n = Reprint.this.tablelayout.getChildCount();
                for (int i = 0; i < n; ++i) {
                    TableRow tableRow2 = (TableRow)Reprint.this.tablelayout.getChildAt(i);
                    for (int j = 0; j < n; ++j) {
                        TableRow tableRow3;
                        if (j != 0 || !string2.equalsIgnoreCase(((TextView)tableRow2.getChildAt(j)).getText().toString())) continue;
                        for (int k = ++i; k < n && (tableRow3 = (TableRow)Reprint.this.tablelayout.getChildAt(k)) != null; ++k) {
                            TextView textView5 = (TextView)tableRow3.getChildAt(j);
                            int n2 = -1 + Integer.parseInt((String)textView5.getText().toString());
                            textView5.setText((CharSequence)(n2 + ""));
                        }
                    }
                }
                existing_srno = 5;
                Reprint.this.tablelayout.removeView(view);
                String[] arrstring = Reprint.this.totalamt.getText().toString().split(" ");
                Reprint.this.totalprize = Float.parseFloat((String)arrstring[2]);
                Reprint.this.totalprize -= Reprint.this.edit_prize;
                TextView textView6 = Reprint.this.txt_grandtotal;
                StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                Object[] arrobject = new Object[]{Float.valueOf((float)Reprint.this.totalprize)};
                textView6.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                Reprint.this.edit_prize = 0.0f;
                Reprint.this.totalamt.setText((CharSequence)("Amount : " + Reprint.this.totalprize));
            }
        });
    }

    protected void setup() {
        this.tablelayout.removeAllViews();
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
        System.out.println("CCCCCC =" + cursor.getCount());
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
    }

}

