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
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;
import com.zj.btsdk.BluetoothService;
import java.util.ArrayList;

public class EditOrder
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
    private TextView txt_total = null;
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
                            if (EditOrder.this.tableno.getSelectedItem().toString().matches("Home Delivery")) {
                                EditOrder.this.alertforparcelorder("yes");
                                return;
                            }
                            EditOrder.this.alertfordiscount("yes");
                            return;
                        }
                        case -2: 
                    }
                    EditOrder.this.btnSubmit_Click("no");
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
        block6 : {
            block7 : {
                try {
                    onClickListener = new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            switch (n) {
                                default: {
                                    return;
                                }
                                case -1: 
                            }
                            EditOrder.this.btnSubmit_Click(string2);
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
                    this.str_st = cursor.getString(4);
                    this.str_tax = cursor.getString(6);
                    this.str_Vat = cursor.getString(5);
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
                        break block6;
                    }
                    if (this.str_Vat.matches("") && this.str_st != "") {
                        this.st = Float.parseFloat((String)this.str_st);
                        this.Vat = 0.0f;
                        float f = this.total_price * (this.st / 100.0f);
                        TextView textView = this.txtst;
                        StringBuilder stringBuilder = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        this.grand_total = f + this.total_price;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txt_tax.setVisibility(8);
                        break block6;
                    }
                    if (!this.str_st.matches("") || this.str_Vat == "") break block7;
                    this.st = 0.0f;
                    this.Vat = Float.parseFloat((String)this.str_Vat);
                    float f = this.total_price * (this.Vat / 100.0f);
                    TextView textView = this.txt_tax;
                    StringBuilder stringBuilder = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    this.grand_total = f + this.total_price;
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
                float f = this.total_price * (this.Vat / 100.0f);
                float f2 = this.total_price * (this.st / 100.0f);
                TextView textView = this.txt_tax;
                StringBuilder stringBuilder = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                TextView textView2 = this.txtst;
                StringBuilder stringBuilder2 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
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
        builder.setView((View)linearLayout);
        builder.setPositiveButton((CharSequence)"Ok", onClickListener);
        builder.setNegativeButton((CharSequence)"Cancel", onClickListener).show();
        this.other_charges.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!EditOrder.this.discount.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)EditOrder.this.discount.getText().toString());
                        TextView textView = EditOrder.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (EditOrder.this.Vat / 100.0f);
                        float f3 = f * (EditOrder.this.st / 100.0f);
                        TextView textView2 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = EditOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        EditOrder.this.grand_total = f3 + (f + f2);
                        TextView textView4 = EditOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f4 = f * (EditOrder.this.Vat / 100.0f);
                    float f5 = f * (EditOrder.this.st / 100.0f);
                    TextView textView5 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f4)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = EditOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f5)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    EditOrder.this.grand_total = f5 + (f + f4);
                    TextView textView7 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)EditOrder.this.other_charges.getText().toString());
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f6 = f * (EditOrder.this.Vat / 100.0f);
                    float f7 = f * (EditOrder.this.st / 100.0f);
                    TextView textView8 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f6)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = EditOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f7)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f8 = f7 + (f + f6);
                    TextView textView10 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f8)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (EditOrder.this.discount.getText().toString().matches("")) return;
                    {
                        float f9 = Float.parseFloat((String)EditOrder.this.total.getText().toString().split(" ")[2]) - Float.parseFloat((String)EditOrder.this.discount.getText().toString());
                        TextView textView11 = EditOrder.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f9)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f10 = f9 * (EditOrder.this.Vat / 100.0f);
                        float f11 = f9 * (EditOrder.this.st / 100.0f);
                        TextView textView12 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f10)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = EditOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f11)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f12 = f11 + (f9 + f10);
                        TextView textView14 = EditOrder.this.grnd_total;
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
                    if (!EditOrder.this.other_charges.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)EditOrder.this.other_charges.getText().toString());
                        TextView textView = EditOrder.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (EditOrder.this.Vat / 100.0f);
                        float f3 = f * (EditOrder.this.st / 100.0f);
                        TextView textView2 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = EditOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        float f4 = f3 + (f + f2);
                        TextView textView4 = EditOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f5 = f * (EditOrder.this.Vat / 100.0f);
                    float f6 = f * (EditOrder.this.st / 100.0f);
                    TextView textView5 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f5)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = EditOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f6)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    EditOrder.this.grand_total = f6 + (f + f5);
                    TextView textView7 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)EditOrder.this.discount.getText().toString());
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f7 = f * (EditOrder.this.Vat / 100.0f);
                    float f8 = f * (EditOrder.this.st / 100.0f);
                    TextView textView8 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f7)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = EditOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f8)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f9 = f8 + (f + f7);
                    TextView textView10 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f9)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (EditOrder.this.other_charges.getText().toString().matches("")) return;
                    {
                        float f10 = Float.parseFloat((String)EditOrder.this.total.getText().toString().split(" ")[2]) + Float.parseFloat((String)EditOrder.this.other_charges.getText().toString());
                        TextView textView11 = EditOrder.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f10)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f11 = f10 * (EditOrder.this.Vat / 100.0f);
                        float f12 = f10 * (EditOrder.this.st / 100.0f);
                        TextView textView12 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f11)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = EditOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f12)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f13 = f12 + (f10 + f11);
                        TextView textView14 = EditOrder.this.grnd_total;
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
                            EditOrder.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            EditOrder.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    EditOrder.this.editorder("HD");
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
        block6 : {
            block7 : {
                try {
                    onClickListener = new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            switch (n) {
                                default: {
                                    return;
                                }
                                case -1: 
                            }
                            EditOrder.this.btnSubmit_Click(string2);
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
                    this.str_st = cursor.getString(4);
                    this.str_tax = cursor.getString(6);
                    this.str_Vat = cursor.getString(5);
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
                        break block6;
                    }
                    if (this.str_Vat.matches("") && this.str_st != "") {
                        this.st = Float.parseFloat((String)this.str_st);
                        this.Vat = 0.0f;
                        float f = this.total_price * (this.st / 100.0f);
                        TextView textView = this.txtst;
                        StringBuilder stringBuilder = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        this.grand_total = f + this.total_price;
                        linearLayout.addView((View)this.txtst);
                        linearLayout.addView((View)this.txt_tax);
                        linearLayout.addView((View)this.grnd_total);
                        this.txt_tax.setVisibility(8);
                        break block6;
                    }
                    if (!this.str_st.matches("") || this.str_Vat == "") break block7;
                    this.st = 0.0f;
                    this.Vat = Float.parseFloat((String)this.str_Vat);
                    float f = this.total_price * (this.Vat / 100.0f);
                    TextView textView = this.txt_tax;
                    StringBuilder stringBuilder = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    this.grand_total = f + this.total_price;
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
                float f = this.total_price * (this.Vat / 100.0f);
                float f2 = this.total_price * (this.st / 100.0f);
                TextView textView = this.txt_tax;
                StringBuilder stringBuilder = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                TextView textView2 = this.txtst;
                StringBuilder stringBuilder2 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
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
        this.other_charges.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    if (!EditOrder.this.discount.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)EditOrder.this.discount.getText().toString());
                        TextView textView = EditOrder.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (EditOrder.this.Vat / 100.0f);
                        float f3 = f * (EditOrder.this.st / 100.0f);
                        TextView textView2 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = EditOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        EditOrder.this.grand_total = f3 + (f + f2);
                        TextView textView4 = EditOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f4 = f * (EditOrder.this.Vat / 100.0f);
                    float f5 = f * (EditOrder.this.st / 100.0f);
                    TextView textView5 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f4)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = EditOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f5)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    EditOrder.this.grand_total = f5 + (f + f4);
                    TextView textView7 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)EditOrder.this.other_charges.getText().toString());
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f6 = f * (EditOrder.this.Vat / 100.0f);
                    float f7 = f * (EditOrder.this.st / 100.0f);
                    TextView textView8 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f6)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = EditOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f7)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f8 = f7 + (f + f6);
                    TextView textView10 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f8)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (EditOrder.this.discount.getText().toString().matches("")) return;
                    {
                        float f9 = Float.parseFloat((String)EditOrder.this.total.getText().toString().split(" ")[2]) - Float.parseFloat((String)EditOrder.this.discount.getText().toString());
                        TextView textView11 = EditOrder.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f9)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f10 = f9 * (EditOrder.this.Vat / 100.0f);
                        float f11 = f9 * (EditOrder.this.st / 100.0f);
                        TextView textView12 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f10)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = EditOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f11)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f12 = f11 + (f9 + f10);
                        TextView textView14 = EditOrder.this.grnd_total;
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
                    if (!EditOrder.this.other_charges.getText().toString().matches("")) {
                        float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) + Float.parseFloat((String)EditOrder.this.other_charges.getText().toString());
                        TextView textView = EditOrder.this.total;
                        StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                        Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                        float f2 = f * (EditOrder.this.Vat / 100.0f);
                        float f3 = f * (EditOrder.this.st / 100.0f);
                        TextView textView2 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                        TextView textView3 = EditOrder.this.txtst;
                        StringBuilder stringBuilder3 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject3 = new Object[]{Float.valueOf((float)f3)};
                        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
                        float f4 = f3 + (f + f2);
                        TextView textView4 = EditOrder.this.grnd_total;
                        StringBuilder stringBuilder4 = new StringBuilder().append("GrandTotal : ");
                        Object[] arrobject4 = new Object[]{Float.valueOf((float)f4)};
                        textView4.setText((CharSequence)stringBuilder4.append(String.format((String)"%.2f", (Object[])arrobject4)).toString());
                        return;
                    }
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]);
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f5 = f * (EditOrder.this.Vat / 100.0f);
                    float f6 = f * (EditOrder.this.st / 100.0f);
                    TextView textView5 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder5 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject5 = new Object[]{Float.valueOf((float)f5)};
                    textView5.setText((CharSequence)stringBuilder5.append(String.format((String)"%.2f", (Object[])arrobject5)).toString());
                    TextView textView6 = EditOrder.this.txtst;
                    StringBuilder stringBuilder6 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject6 = new Object[]{Float.valueOf((float)f6)};
                    textView6.setText((CharSequence)stringBuilder6.append(String.format((String)"%.2f", (Object[])arrobject6)).toString());
                    EditOrder.this.grand_total = f6 + (f + f5);
                    TextView textView7 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder7 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject7 = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                    textView7.setText((CharSequence)stringBuilder7.append(String.format((String)"%.2f", (Object[])arrobject7)).toString());
                    return;
                } else {
                    float f = Float.parseFloat((String)EditOrder.this.txt_grandtotal.getText().toString().split(" ")[2]) - Float.parseFloat((String)EditOrder.this.discount.getText().toString());
                    TextView textView = EditOrder.this.total;
                    StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                    Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                    textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                    float f7 = f * (EditOrder.this.Vat / 100.0f);
                    float f8 = f * (EditOrder.this.st / 100.0f);
                    TextView textView8 = EditOrder.this.txt_tax;
                    StringBuilder stringBuilder8 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                    Object[] arrobject8 = new Object[]{Float.valueOf((float)f7)};
                    textView8.setText((CharSequence)stringBuilder8.append(String.format((String)"%.2f", (Object[])arrobject8)).toString());
                    TextView textView9 = EditOrder.this.txtst;
                    StringBuilder stringBuilder9 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                    Object[] arrobject9 = new Object[]{Float.valueOf((float)f8)};
                    textView9.setText((CharSequence)stringBuilder9.append(String.format((String)"%.2f", (Object[])arrobject9)).toString());
                    float f9 = f8 + (f + f7);
                    TextView textView10 = EditOrder.this.grnd_total;
                    StringBuilder stringBuilder10 = new StringBuilder().append("GrandTotal : ");
                    Object[] arrobject10 = new Object[]{Float.valueOf((float)f9)};
                    textView10.setText((CharSequence)stringBuilder10.append(String.format((String)"%.2f", (Object[])arrobject10)).toString());
                    if (EditOrder.this.other_charges.getText().toString().matches("")) return;
                    {
                        float f10 = Float.parseFloat((String)EditOrder.this.total.getText().toString().split(" ")[2]) + Float.parseFloat((String)EditOrder.this.other_charges.getText().toString());
                        TextView textView11 = EditOrder.this.total;
                        StringBuilder stringBuilder11 = new StringBuilder().append("Total : ");
                        Object[] arrobject11 = new Object[]{Float.valueOf((float)f10)};
                        textView11.setText((CharSequence)stringBuilder11.append(String.format((String)"%.2f", (Object[])arrobject11)).toString());
                        float f11 = f10 * (EditOrder.this.Vat / 100.0f);
                        float f12 = f10 * (EditOrder.this.st / 100.0f);
                        TextView textView12 = EditOrder.this.txt_tax;
                        StringBuilder stringBuilder12 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) :");
                        Object[] arrobject12 = new Object[]{Float.valueOf((float)f11)};
                        textView12.setText((CharSequence)stringBuilder12.append(String.format((String)"%.2f", (Object[])arrobject12)).toString());
                        TextView textView13 = EditOrder.this.txtst;
                        StringBuilder stringBuilder13 = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) :");
                        Object[] arrobject13 = new Object[]{Float.valueOf((float)f12)};
                        textView13.setText((CharSequence)stringBuilder13.append(String.format((String)"%.2f", (Object[])arrobject13)).toString());
                        float f13 = f12 + (f10 + f11);
                        TextView textView14 = EditOrder.this.grnd_total;
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
                            EditOrder.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            EditOrder.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    EditOrder.this.reprintorder("HD");
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
                    EditOrder.this.go();
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
                            EditOrder.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            EditOrder.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    EditOrder.this.parcelorder("HD");
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
        block19 : {
            block15 : {
                block17 : {
                    block18 : {
                        block16 : {
                            if (this.tableno.getSelectedItem().toString().matches("Table No")) {
                                var50_2 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Select Table No", (int)1);
                                var50_2.setGravity(17, 0, 0);
                                var50_2.show();
                                return;
                            }
                            if (this.item.getText().toString().matches("") || this.item.getText().equals(null)) {
                                var4_3 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Item Name", (int)1);
                                var4_3.setGravity(17, 0, 0);
                                var4_3.show();
                                this.item.setText((CharSequence)"");
                                return;
                            }
                            if (this.quantity.getText().toString().matches("") || this.quantity.getText().equals(null)) {
                                var5_5 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Enter Quantity", (int)1);
                                var5_5.setGravity(17, 0, 0);
                                var5_5.show();
                                this.quantity.setText((CharSequence)"");
                                return;
                            }
                            var6_7 = Integer.parseInt((String)this.quantity.getText().toString());
                            var7_8 = this.item.getText().toString();
                            var8_9 = this.ObjdbHelp.getItemCode(var7_8);
                            if (var8_9.getCount() <= 0) break block15;
                            var8_9.moveToFirst();
                            var11_10 = Integer.parseInt((String)var8_9.getString(0).toString());
                            this.prize = Float.parseFloat((String)var8_9.getString(1).toString());
                            var12_11 = this.totalamt.getText().toString().split(" ");
                            if (!"".matches("") || !"".matches("")) break block16;
                            this.stflag = 0;
                            this.vatflag = 0;
                            this.totalprize = Float.parseFloat((String)var12_11[2]);
                            this.f = this.prize * (float)var6_7;
                            this.totalprize += this.f;
                            this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                            this.txt_tax.setText((CharSequence)"");
                            this.txtst.setText((CharSequence)"");
                            this.grand_total = this.totalprize;
                            var47_12 = this.txt_grandtotal;
                            var48_13 = new StringBuilder().append("Total : ");
                            var49_14 = new Object[]{Float.valueOf((float)this.grand_total)};
                            var47_12.setText((CharSequence)var48_13.append(String.format((String)"%.2f", (Object[])var49_14)).toString());
                        }
                        if (!"".matches("") || "" == "") ** GOTO lbl65
                        this.stflag = 1;
                        this.vatflag = 0;
                        this.st = Float.parseFloat((String)"");
                        this.totalprize = Float.parseFloat((String)var12_11[2]);
                        this.f = this.prize * (float)var6_7;
                        this.totalprize += this.f;
                        this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                        var13_15 = this.totalprize * (this.st / 100.0f);
                        this.txt_tax.setText((CharSequence)"");
                        var14_16 = this.txtst;
                        var15_17 = new StringBuilder().append("CGST(").append(this.st).append("%) : ");
                        var16_18 = new Object[]{Float.valueOf((float)var13_15)};
                        var14_16.setText((CharSequence)var15_17.append(String.format((String)"%.2f", (Object[])var16_18)).toString());
                        this.grand_total = this.totalprize;
                        var17_19 = this.txt_grandtotal;
                        var18_20 = new StringBuilder().append("Total : ");
                        var19_21 = new Object[]{Float.valueOf((float)this.grand_total)};
                        var17_19.setText((CharSequence)var18_20.append(String.format((String)"%.2f", (Object[])var19_21)).toString());
                        break block17;
lbl65: // 1 sources:
                        if (!"".matches("") || "" == "") break block18;
                        try {
                            this.stflag = 0;
                            this.vatflag = 1;
                            this.Vat = Float.parseFloat((String)"");
                            this.totalprize = Float.parseFloat((String)var12_11[2]);
                            this.f = this.prize * (float)var6_7;
                            this.totalprize += this.f;
                            this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                            var29_22 = this.totalprize * (this.Vat / 100.0f);
                            var30_23 = this.txt_tax;
                            var31_24 = new StringBuilder().append("SGST(").append(var29_22).append("%) : ");
                            var32_25 = new Object[]{Float.valueOf((float)var29_22)};
                            var30_23.setText((CharSequence)var31_24.append(String.format((String)"%.2f", (Object[])var32_25)).toString());
                            this.txtst.setText((CharSequence)"");
                            this.grand_total = this.totalprize;
                            var33_26 = this.txt_grandtotal;
                            var34_27 = new StringBuilder().append("Total : ");
                            var35_28 = new Object[]{Float.valueOf((float)this.grand_total)};
                            var33_26.setText((CharSequence)var34_27.append(String.format((String)"%.2f", (Object[])var35_28)).toString());
                        }
                        catch (SQLException var3_4) {
                            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var3_4);
                            return;
                        }
                        catch (Exception var2_6) {
                            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), var2_6);
                            return;
                        }
                    }
                    if ("" != "" && "" != "") {
                        this.stflag = 1;
                        this.vatflag = 1;
                        this.st = Float.parseFloat((String)"");
                        this.Vat = Float.parseFloat((String)"");
                        this.totalprize = Float.parseFloat((String)var12_11[2]);
                        this.f = this.prize * (float)var6_7;
                        this.totalprize += this.f;
                        this.totalamt.setText((CharSequence)("Amount : " + this.totalprize));
                        var36_29 = this.totalprize * (this.Vat / 100.0f);
                        var37_30 = this.totalprize * (this.st / 100.0f);
                        var38_31 = this.txt_tax;
                        var39_32 = new StringBuilder().append("SGST(").append(this.Vat).append("%) :");
                        var40_33 = new Object[]{Float.valueOf((float)var36_29)};
                        var38_31.setText((CharSequence)var39_32.append(String.format((String)"%.2f", (Object[])var40_33)).toString());
                        var41_34 = this.txtst;
                        var42_35 = new StringBuilder().append("CGST(").append(this.st).append("%) :");
                        var43_36 = new Object[]{Float.valueOf((float)var37_30)};
                        var41_34.setText((CharSequence)var42_35.append(String.format((String)"%.2f", (Object[])var43_36)).toString());
                        this.grand_total = this.totalprize;
                        var44_37 = this.txt_grandtotal;
                        var45_38 = new StringBuilder().append("Total : ");
                        var46_39 = new Object[]{Float.valueOf((float)this.grand_total)};
                        var44_37.setText((CharSequence)var45_38.append(String.format((String)"%.2f", (Object[])var46_39)).toString());
                    }
                }
                this.totalprize += this.f;
                break block19;
            }
            var9_49 = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Invalid Item", (int)1);
            var9_49.setGravity(17, 0, 0);
            var9_49.show();
            this.quantity.setText((CharSequence)"");
            this.item.setText((CharSequence)"");
            return;
        }
        this.addeditems[EditOrder.item_count] = this.item.getText().toString();
        this.addedquant[EditOrder.item_count] = this.quantity.getText().toString();
        this.addeditemcode[EditOrder.item_count] = var11_10 + "";
        this.arr_prize[EditOrder.item_count] = this.f + "";
        this.quantity.setText((CharSequence)"");
        this.item.setText((CharSequence)"");
        var8_9.close();
        var20_40 = 0;
        while (var20_40 < (var21_41 = this.addeditems.length)) {
            if (this.addeditems[var20_40] == null) return;
            if (EditOrder.existing_srno == 5) {
                EditOrder.srno = -1 + EditOrder.srno;
            }
            this.tr = var22_42 = new TableRow((Context)this);
            this.txtv_SrNo = var23_43 = new TextView((Context)this);
            this.txtv_SrNo.setText((CharSequence)(EditOrder.srno + ""));
            this.txtv_SrNo.setTextColor(-16777216);
            this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_SrNo.setPadding(10, 10, 10, 10);
            this.txtv_SrNo.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_SrNo);
            EditOrder.existing_srno = 1;
            this.txtv_itemname = var24_44 = new TextView((Context)this);
            this.txtv_itemname.setText((CharSequence)this.addeditems[var20_40]);
            this.txtv_itemname.setTextColor(-16777216);
            this.txtv_itemname.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_itemname.setPadding(10, 10, 10, 10);
            this.txtv_itemname.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_itemname);
            this.txtv_quantity = var25_45 = new TextView((Context)this);
            this.txtv_quantity.setText((CharSequence)this.addedquant[var20_40]);
            this.txtv_quantity.setTextColor(-16777216);
            this.txtv_quantity.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_quantity.setPadding(10, 10, 10, 10);
            this.txtv_quantity.setTextSize(16.0f);
            this.txtv_quantity.setTextAlignment(0);
            this.tr.addView((View)this.txtv_quantity);
            this.txtv_prize = var26_46 = new TextView((Context)this);
            this.txtv_prize.setText((CharSequence)this.arr_prize[var20_40]);
            this.txtv_prize.setTextColor(-16777216);
            this.txtv_prize.setTypeface(Typeface.DEFAULT, 0);
            this.txtv_prize.setPadding(10, 10, 10, 10);
            this.txtv_prize.setTextSize(16.0f);
            this.tr.addView((View)this.txtv_prize);
            this.tr.setBackgroundResource(2131099778);
            EditOrder.srno = 1 + EditOrder.srno;
            this.tablelayout.addView((View)this.tr);
            this.btnTotal.setEnabled(true);
            this.btnTotal.setTextColor(-1);
            var27_47 = this.tr;
            var28_48 = new View.OnClickListener(){

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
                    EditOrder.this.edit_prize = Float.parseFloat((String)string3);
                    EditOrder.this.item.setText(textView.getText());
                    EditOrder.this.quantity.setText(textView2.getText());
                    int n = EditOrder.this.tablelayout.getChildCount();
                    int n2 = 0;
                    do {
                        TableRow tableRow2;
                        if (n2 < n) {
                            tableRow2 = (TableRow)EditOrder.this.tablelayout.getChildAt(n2);
                        } else {
                            if (EditOrder.this.tablelayout.getChildCount() <= 1) {
                                EditOrder.this.btnTotal.setEnabled(false);
                                EditOrder.this.btnTotal.setTextColor(-7829368);
                            }
                            EditOrder.this.tablelayout.removeView(view);
                            EditOrder.access$1410();
                            String[] arrstring = EditOrder.this.totalamt.getText().toString().split(" ");
                            EditOrder.this.totalprize = Float.parseFloat((String)arrstring[2]);
                            EditOrder.this.totalprize -= EditOrder.this.edit_prize;
                            if (EditOrder.this.stflag == 1 && EditOrder.this.vatflag == 1) {
                                float f = EditOrder.this.totalprize * (EditOrder.this.st / 100.0f);
                                TextView textView5 = EditOrder.this.txtst;
                                StringBuilder stringBuilder = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) : ");
                                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                                textView5.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                                float f2 = EditOrder.this.totalprize * (EditOrder.this.Vat / 100.0f);
                                TextView textView6 = EditOrder.this.txt_tax;
                                StringBuilder stringBuilder2 = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) : ");
                                Object[] arrobject2 = new Object[]{Float.valueOf((float)f2)};
                                textView6.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
                                EditOrder.this.grand_total = f2 + (f + EditOrder.this.totalprize) + EditOrder.this.OC_amt - EditOrder.this.Disc_amt;
                            } else if (EditOrder.this.stflag == 0 && EditOrder.this.vatflag == 1) {
                                float f = EditOrder.this.totalprize * (EditOrder.this.Vat / 100.0f);
                                TextView textView7 = EditOrder.this.txt_tax;
                                StringBuilder stringBuilder = new StringBuilder().append("SGST(").append(EditOrder.this.Vat).append("%) : ");
                                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                                textView7.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                                EditOrder.this.grand_total = f + EditOrder.this.totalprize + EditOrder.this.OC_amt - EditOrder.this.Disc_amt;
                            } else if (EditOrder.this.stflag == 1 && EditOrder.this.vatflag == 0) {
                                float f = EditOrder.this.totalprize * (EditOrder.this.st / 100.0f);
                                TextView textView8 = EditOrder.this.txtst;
                                StringBuilder stringBuilder = new StringBuilder().append("CGST(").append(EditOrder.this.st).append("%) : ");
                                Object[] arrobject = new Object[]{Float.valueOf((float)f)};
                                textView8.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                                EditOrder.this.grand_total = f + EditOrder.this.totalprize + EditOrder.this.OC_amt - EditOrder.this.Disc_amt;
                            } else if (EditOrder.this.stflag == 0 && EditOrder.this.vatflag == 0) {
                                EditOrder.this.txt_tax.setText((CharSequence)"");
                                EditOrder.this.txtst.setText((CharSequence)"");
                                EditOrder.this.grand_total = EditOrder.this.totalprize + EditOrder.this.OC_amt - EditOrder.this.Disc_amt;
                            }
                            TextView textView9 = EditOrder.this.txt_grandtotal;
                            StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                            Object[] arrobject = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                            textView9.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                            EditOrder.this.edit_prize = 0.0f;
                            EditOrder.this.totalamt.setText((CharSequence)("Amount : " + EditOrder.this.totalprize));
                            return;
                        }
                        for (int i = 0; i < n; ++i) {
                            TableRow tableRow3;
                            if (i != 0 || !string2.equalsIgnoreCase(((TextView)tableRow2.getChildAt(i)).getText().toString())) continue;
                            for (int j = ++n2; j < n && (tableRow3 = (TableRow)EditOrder.this.tablelayout.getChildAt(j)) != null; ++j) {
                                TextView textView10 = (TextView)tableRow3.getChildAt(i);
                                int n3 = -1 + Integer.parseInt((String)textView10.getText().toString());
                                textView10.setText((CharSequence)(n3 + ""));
                            }
                        }
                        ++n2;
                    } while (true);
                }
            };
            var27_47.setOnClickListener(var28_48);
            ++var20_40;
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
        block22 : {
            block21 : {
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
                var5_3 = this.tableno.getSelectedItem().toString();
                var6_4 = this.ext_orders.getSelectedItem().toString();
                if (var1_1 != "yes") break block21;
                var7_5 = new PlainPrint(this.getApplicationContext(), this.effectivePrintWidth, 4);
                var7_5.prepareTabularForm(4, 0, 3, true);
                this.mService = Bluetooth.getServiceInstance();
                var8_6 = new ArrayList();
                try {
                    UtilityHelper.printHeader(this.mService, var7_5, var6_4, (ArrayList<String>)var8_6, this.settingsModel, this.getPaymentType(), this.getApplicationContext());
                    if (var5_3 == "Home Delivery") {
                        this.Cust_name = this.name.getText().toString();
                        this.Address = this.address.getText().toString();
                        this.Phoneno = this.phone_no.getText().toString();
                    }
                    this.Discount = this.discount.getText().toString();
                    this.OtherCharges = this.other_charges.getText().toString();
                    break block22;
                }
                catch (SQLException var2_28) {}
                ** GOTO lbl-1000
                catch (Exception var3_32) {}
                ** GOTO lbl-1000
                catch (Exception var3_34) {
                    ** GOTO lbl-1000
                }
                catch (Exception var3_35) {}
lbl-1000: // 3 sources:
                {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var3_33);
                    return;
                }
                catch (SQLException var2_30) {
                    ** GOTO lbl-1000
                }
                catch (SQLException var2_31) {}
lbl-1000: // 3 sources:
                {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var2_29);
                    return;
                }
            }
            var7_5 = null;
            var8_6 = null;
        }
        var9_7 = 0;
        var10_8 = 0.0f;
        do {
            if (var9_7 < var4_2) {
                if (var9_7 > 0) {
                    var19_9 = (TableRow)this.tablelayout.getChildAt(var9_7);
                    var20_10 = (TextView)var19_9.getChildAt(1);
                    var21_11 = (TextView)var19_9.getChildAt(2);
                    (TextView)var19_9.getChildAt(0);
                    var23_12 = (TextView)var19_9.getChildAt(3);
                    var24_13 = Integer.parseInt((String)var21_11.getText().toString());
                    var10_8 += Float.parseFloat((String)var23_12.getText().toString());
                    if (var9_7 == 1) {
                        this.ObjdbHelp.deleteOrder(var6_4, var5_3);
                    }
                    this.ObjdbHelp.SubmitOrder(var5_3, var6_4, var20_10.getText().toString(), var23_12.getText().toString(), var24_13, this.Cust_name, this.Address, this.Phoneno, this.Discount, this.OtherCharges, var1_1, this.getPaymentType(), "0000", var9_7);
                    if (var1_1.matches("yes")) {
                        this.ObjdbHelp.PrintOrder(var5_3, var6_4, var20_10.getText().toString(), var23_12.getText().toString(), var24_13, this.Cust_name, this.Address, this.Phoneno, this.Discount, this.OtherCharges, this.getPaymentType(), "0000", var9_7);
                        var26_14 = this.getItemPrize(var20_10.getText().toString());
                        var8_6.clear();
                        var27_15 = this.mService;
                        var28_16 = var20_10.getText().toString();
                        var29_17 = var21_11.getText().toString();
                        var30_18 = var23_12.getText().toString();
                        var31_19 = this.getApplicationContext();
                        UtilityHelper.printTabularData(var27_15, var7_5, null, (ArrayList<String>)var8_6, var6_4, var28_16, var29_17, var26_14, var30_18, var31_19);
                    }
                }
            } else {
                if (var1_1.matches("yes")) {
                    var8_6.clear();
                    var11_20 = this.mService;
                    var12_21 = this.Cust_name;
                    var13_22 = this.Address;
                    var14_23 = this.Phoneno;
                    var15_24 = this.Discount;
                    var16_25 = this.OtherCharges;
                    var17_26 = this.settingsModel;
                    var18_27 = this.getApplicationContext();
                    UtilityHelper.printFooter(var11_20, var7_5, (ArrayList<String>)var8_6, var10_8, var5_3, var12_21, var13_22, var14_23, var15_24, var16_25, var17_26, var18_27);
                }
                this.tablelayout.removeAllViews();
                this.setup();
                this.totalamt.setText((CharSequence)"Amount : 0");
                this.txt_tax.setText((CharSequence)"");
                this.txt_grandtotal.setText((CharSequence)"Total : 0");
                this.txtst.setText((CharSequence)"");
                this.item.setText((CharSequence)"");
                this.quantity.setText((CharSequence)"");
                this.tableno.setSelection(0);
                this.ext_orders.setSelection(0);
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Order Submitted Successfully");
                this.btnTotal.setEnabled(false);
                this.btnTotal.setTextColor(-7829368);
                return;
            }
            ++var9_7;
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
                    EditOrder.this.exportDb();
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
                    EditOrder.this.importDb();
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
        this.tr.setBackgroundResource(2131099778);
        this.tablelayout.addView((View)this.tr);
        this.tr.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                TableRow tableRow = (TableRow)view;
                TextView textView = (TextView)tableRow.getChildAt(1);
                TextView textView2 = (TextView)tableRow.getChildAt(2);
                TextView textView3 = (TextView)tableRow.getChildAt(0);
                TextView textView4 = (TextView)tableRow.getChildAt(3);
                String string2 = textView3.getText().toString();
                String string3 = textView4.getText().toString();
                EditOrder.this.edit_prize = Float.parseFloat((String)string3);
                EditOrder.this.item.setText(textView.getText());
                EditOrder.this.quantity.setText(textView2.getText());
                int n = EditOrder.this.tablelayout.getChildCount();
                for (int i = 0; i < n; ++i) {
                    TableRow tableRow2 = (TableRow)EditOrder.this.tablelayout.getChildAt(i);
                    for (int j = 0; j < n; ++j) {
                        TableRow tableRow3;
                        if (j != 0 || !string2.equalsIgnoreCase(((TextView)tableRow2.getChildAt(j)).getText().toString())) continue;
                        for (int k = ++i; k < n && (tableRow3 = (TableRow)EditOrder.this.tablelayout.getChildAt(k)) != null; ++k) {
                            TextView textView5 = (TextView)tableRow3.getChildAt(j);
                            int n2 = -1 + Integer.parseInt((String)textView5.getText().toString());
                            textView5.setText((CharSequence)(n2 + ""));
                        }
                    }
                }
                existing_srno = 5;
                EditOrder.this.tablelayout.removeView(view);
                if (EditOrder.this.tablelayout.getChildCount() <= 1) {
                    EditOrder.this.btnTotal.setEnabled(false);
                    EditOrder.this.btnTotal.setTextColor(-7829368);
                }
                String[] arrstring = EditOrder.this.totalamt.getText().toString().split(" ");
                EditOrder.this.totalprize = Float.parseFloat((String)arrstring[2]);
                EditOrder.this.totalprize -= EditOrder.this.edit_prize;
                EditOrder.this.grand_total = EditOrder.this.totalprize;
                TextView textView6 = EditOrder.this.txt_grandtotal;
                StringBuilder stringBuilder = new StringBuilder().append("Total : ");
                Object[] arrobject = new Object[]{Float.valueOf((float)EditOrder.this.grand_total)};
                textView6.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
                EditOrder.this.edit_prize = 0.0f;
                EditOrder.this.totalamt.setText((CharSequence)("Amount : " + EditOrder.this.totalprize));
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
        this.txtv_SrNo.setPadding(0, 2, 0, 0);
        this.txtv_SrNo.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txtv_itemname = new TextView((Context)this);
        this.txtv_itemname.setText((CharSequence)"ItemName");
        this.txtv_itemname.setTextColor(-1);
        this.txtv_itemname.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_itemname.setPadding(0, 2, 2, 0);
        this.txtv_itemname.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_itemname);
        this.txtv_quantity = new TextView((Context)this);
        this.txtv_quantity.setText((CharSequence)"Qty");
        this.txtv_quantity.setTextColor(-1);
        this.txtv_quantity.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_quantity.setPadding(1, 2, 2, 0);
        this.txtv_quantity.setTextSize(16.0f);
        this.tr.addView((View)this.txtv_quantity);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)"Prize(Rs.)");
        this.txtv_prize.setTextColor(-1);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_prize.setPadding(1, 2, 2, 0);
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
    }

}

