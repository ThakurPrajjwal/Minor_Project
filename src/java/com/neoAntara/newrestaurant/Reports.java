/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.graphics.Typeface
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.GradientDrawable$Orientation
 *  android.os.Bundle
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.Button
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 *  android.widget.Spinner
 *  android.widget.TableLayout
 *  android.widget.TableRow
 *  android.widget.TableRow$LayoutParams
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.neoAntara.newrestaurant.Models.SettingsModel;
import com.neoAntara.newrestaurant.NewOrder;
import com.neoAntara.newrestaurant.Print.Bluetooth;
import com.neoAntara.newrestaurant.Print.PlainPrint;
import com.neoAntara.newrestaurant.Reprint;
import com.neoAntara.newrestaurant.SearchItem;
import com.neoAntara.newrestaurant.Setting;
import com.neoAntara.newrestaurant.UserRights;
import com.neoAntara.newrestaurant.UtilityHelper;
import com.zj.btsdk.BluetoothService;
import java.util.ArrayList;

public class Reports
extends Activity {
    static final int DATE_DIALOG_ID = 100;
    static final int DATE_DIALOG_ID_TO = 101;
    private static int srno;
    private TextView CardAmount = null;
    private TextView CashAmount = null;
    DBHelper ObjDBHelp;
    private TextView OtherAmount = null;
    int REQUEST_CONNECT_DEVICE = 6;
    int REQUEST_ENABLE_BT = 4;
    private TextView amount = null;
    public String between_date = "";
    Button btnFromdate = null;
    Button btnPrint = null;
    Button btnShow = null;
    Button btnToDate = null;
    String bus_date = "";
    private float cardTotalAmt = 0.0f;
    private float cashTotalAmt = 0.0f;
    private TextView date = null;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener(){

        public void onDateSet(DatePicker datePicker, int n, int n2, int n3) {
            Reports.this.yr = n;
            Reports.this.mon = n2;
            Reports.this.day = n3;
            Reports reports = Reports.this;
            reports.mon = 1 + reports.mon;
            Button button = Reports.this.btnFromdate;
            StringBuilder stringBuilder = new StringBuilder();
            Object[] arrobject = new Object[]{Reports.this.day};
            StringBuilder stringBuilder2 = stringBuilder.append(String.format((String)"%02d", (Object[])arrobject)).append("/");
            Object[] arrobject2 = new Object[]{Reports.this.mon};
            button.setText((CharSequence)stringBuilder2.append(String.format((String)"%02d", (Object[])arrobject2)).append("/").append(Reports.this.yr));
            Reports.this.dtpic1.init(Reports.this.yr, Reports.this.mon, Reports.this.day, null);
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener(){

        public void onDateSet(DatePicker datePicker, int n, int n2, int n3) {
            Reports.this.yr = n;
            Reports.this.mon = n2;
            Reports.this.day = n3;
            Reports reports = Reports.this;
            reports.mon = 1 + reports.mon;
            Button button = Reports.this.btnToDate;
            StringBuilder stringBuilder = new StringBuilder();
            Object[] arrobject = new Object[]{Reports.this.day};
            StringBuilder stringBuilder2 = stringBuilder.append(String.format((String)"%02d", (Object[])arrobject)).append("/");
            Object[] arrobject2 = new Object[]{Reports.this.mon};
            button.setText((CharSequence)stringBuilder2.append(String.format((String)"%02d", (Object[])arrobject2)).append("/").append(Reports.this.yr));
            Reports.this.dtpic2.init(Reports.this.yr, Reports.this.mon, Reports.this.day, null);
        }
    };
    int day;
    private TextView discount = null;
    DatePicker dtpic1;
    DatePicker dtpic2;
    int effectivePrintWidth = 48;
    GradientDrawable grd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{-11908534, -6737101});
    private AutoCompleteTextView item = null;
    View line;
    BluetoothService mService = null;
    int mon;
    String open_bal = "";
    private Spinner order_type = null;
    private float otherTotalAmt = 0.0f;
    private TextView other_charges = null;
    public String rest_address;
    public String restaurant_name;
    private TextView resto_name = null;
    public String rpt_Header = "";
    private TextView sales_tax = null;
    SettingsModel settingsModel = null;
    public String stphoneno;
    public String str_content = "";
    public String str_footer = "";
    public String str_s_tax = "";
    public String str_vat = "";
    private TableLayout tablelayout = null;
    private Spinner tableno = null;
    private TextView total_amt = null;
    private TableLayout total_table = null;
    private TableRow tr = null;
    private TableRow tr1 = null;
    private TextView txt_date = null;
    private TextView txttotalamt = null;
    private TextView txtv_CRamt = null;
    private TextView txtv_DRamt = null;
    private TextView txtv_SrNo = null;
    private TextView txtv_itemname = null;
    private TextView txtv_oldOrderNo = null;
    private TextView txtv_olddate = null;
    private TextView txtv_olditemname = null;
    private TextView txtv_oldprize = null;
    private TextView txtv_oldquantity = null;
    private TextView txtv_orderno = null;
    private TextView txtv_prize = null;
    private TextView txtv_quantity = null;
    private TextView txtv_tableno = null;
    private TextView vat = null;
    public String website;
    int yr;

    public Reports() {
        srno = 1;
        this.settingsModel = new SettingsModel();
    }

    private void calculateTotalCashCardAmt(String string2, float f) {
        if (string2.equalsIgnoreCase("cash")) {
            this.cashTotalAmt = f + this.cashTotalAmt;
            return;
        }
        if (string2.equalsIgnoreCase("card")) {
            this.cardTotalAmt = f + this.cardTotalAmt;
            return;
        }
        this.otherTotalAmt = f + this.otherTotalAmt;
    }

    private void clearViewVariables() {
        this.cardTotalAmt = 0.0f;
        this.cashTotalAmt = 0.0f;
        this.otherTotalAmt = 0.0f;
        this.CardAmount.setText((CharSequence)"");
        this.CashAmount.setText((CharSequence)"");
        this.OtherAmount.setText((CharSequence)"");
        this.amount.setText((CharSequence)"");
        this.sales_tax.setText((CharSequence)"");
        this.vat.setText((CharSequence)"");
        this.total_amt.setText((CharSequence)"");
        this.other_charges.setText((CharSequence)"");
        this.discount.setText((CharSequence)"");
        this.tablelayout.removeAllViews();
    }

    private void setTotalCashCardAmount() {
        TextView textView = this.CashAmount;
        StringBuilder stringBuilder = new StringBuilder().append("Total Cash Amount : ");
        Object[] arrobject = new Object[]{Float.valueOf((float)this.cashTotalAmt)};
        textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
        TextView textView2 = this.CardAmount;
        StringBuilder stringBuilder2 = new StringBuilder().append("Total Card Amount : ");
        Object[] arrobject2 = new Object[]{Float.valueOf((float)this.cardTotalAmt)};
        textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
        TextView textView3 = this.OtherAmount;
        StringBuilder stringBuilder3 = new StringBuilder().append("Total Other Amount(Paytm etc.) : ");
        Object[] arrobject3 = new Object[]{Float.valueOf((float)this.otherTotalAmt)};
        textView3.setText((CharSequence)stringBuilder3.append(String.format((String)"%.2f", (Object[])arrobject3)).toString());
    }

    public void ClosingReport() {
        try {
            this.amount.setText((CharSequence)("Opening Bal : " + this.open_bal));
            this.amount.setTextSize(16.0f);
            Cursor cursor = this.ObjDBHelp.getPrintReportTotalCashCardAmt(this.bus_date, this.bus_date, "Cash", "0000");
            cursor.moveToFirst();
            this.cashTotalAmt = Float.parseFloat((String)cursor.getString(0));
            Cursor cursor2 = this.ObjDBHelp.getPrintReportTotalCashCardAmt(this.bus_date, this.bus_date, "Card", "0000");
            cursor2.moveToFirst();
            this.cardTotalAmt = Float.parseFloat((String)cursor2.getString(0));
            Cursor cursor3 = this.ObjDBHelp.getPrintReportTotalCashCardAmt(this.bus_date, this.bus_date, "Other", "0000");
            cursor3.moveToFirst();
            this.otherTotalAmt = Float.parseFloat((String)cursor3.getString(0));
            this.setTotalCashCardAmount();
            Cursor cursor4 = this.ObjDBHelp.getPrintReportTotal(this.bus_date, this.bus_date, "0000");
            cursor4.moveToFirst();
            float f = Float.parseFloat((String)cursor4.getString(0));
            cursor4.close();
            if (this.str_s_tax.length() > 0 && this.str_vat.length() > 0) {
                float f2 = f * (Float.parseFloat((String)this.str_s_tax) / 100.0f);
                f = f * (Float.parseFloat((String)this.str_vat) / 100.0f) + (f + f2);
            }
            TextView textView = this.sales_tax;
            StringBuilder stringBuilder = new StringBuilder().append("Total Income : ");
            Object[] arrobject = new Object[]{Float.valueOf((float)f)};
            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
            this.sales_tax.setTextSize(16.0f);
            Cursor cursor5 = this.ObjDBHelp.getTotalExpense(this.bus_date, this.bus_date);
            cursor5.moveToFirst();
            float f3 = Float.parseFloat((String)cursor5.getString(0));
            TextView textView2 = this.vat;
            StringBuilder stringBuilder2 = new StringBuilder().append("Total Expenses : ");
            Object[] arrobject2 = new Object[]{Float.valueOf((float)f3)};
            textView2.setText((CharSequence)stringBuilder2.append(String.format((String)"%.2f", (Object[])arrobject2)).toString());
            this.vat.setTextSize(16.0f);
            cursor5.close();
            float f4 = f + (Float.parseFloat((String)this.open_bal) - f3);
            this.total_amt.setText((CharSequence)("Closing Bal : " + f4));
            this.total_amt.setTextSize(16.0f);
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public void SetExpenseReport(String string2, String string3) {
        this.line = new View((Context)this);
        this.line.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(-1, 1));
        this.line.setBackgroundColor(-7829368);
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)"SrNo");
        this.txtv_SrNo.setTextColor(-1);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_SrNo.setPadding(1, 2, 2, 0);
        this.txtv_SrNo.setGravity(17);
        this.txtv_SrNo.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txt_date = new TextView((Context)this);
        this.txt_date.setText((CharSequence)"Date");
        this.txt_date.setTextColor(-1);
        this.txt_date.setTypeface(Typeface.DEFAULT, 1);
        this.txt_date.setPadding(1, 2, 2, 0);
        this.txt_date.setGravity(17);
        this.txt_date.setTextSize(14.0f);
        this.tr.addView((View)this.txt_date);
        this.txtv_quantity = new TextView((Context)this);
        this.txtv_quantity.setText((CharSequence)"Description");
        this.txtv_quantity.setTextColor(-1);
        this.txtv_quantity.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_quantity.setPadding(1, 2, 8, 0);
        this.txtv_quantity.setGravity(17);
        this.txtv_quantity.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_quantity);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)"Amount(Rs.)");
        this.txtv_prize.setTextColor(-1);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_prize.setPadding(1, 2, 0, 0);
        this.txtv_prize.setGravity(17);
        this.txtv_prize.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_prize);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
        Cursor cursor = this.ObjDBHelp.getExpenseReport(string2, string3);
        int n = cursor.getCount();
        cursor.moveToFirst();
        if (n > 0) {
            srno = 1;
            for (int i = 0; i < n; ++i) {
                this.tr = new TableRow((Context)this);
                this.txtv_SrNo = new TextView((Context)this);
                this.txtv_SrNo.setText((CharSequence)(srno + ""));
                this.txtv_SrNo.setTextColor(-16777216);
                this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 0);
                this.txtv_SrNo.setPadding(1, 2, 2, 0);
                this.txtv_SrNo.setGravity(17);
                this.txtv_SrNo.setTextSize(16.0f);
                this.tr.addView((View)this.txtv_SrNo);
                String string4 = cursor.getString(1).substring(0, 5);
                this.txt_date = new TextView((Context)this);
                this.txt_date.setText((CharSequence)string4);
                this.txt_date.setTextColor(-16777216);
                this.txt_date.setTypeface(Typeface.DEFAULT, 0);
                this.txt_date.setPadding(1, 2, 2, 0);
                this.txt_date.setGravity(17);
                this.txt_date.setTextSize(16.0f);
                this.tr.addView((View)this.txt_date);
                this.txtv_quantity = new TextView((Context)this);
                this.txtv_quantity.setText((CharSequence)cursor.getString(3));
                this.txtv_quantity.setTextColor(-16777216);
                this.txtv_quantity.setTypeface(Typeface.DEFAULT, 0);
                this.txtv_quantity.setPadding(1, 2, 2, 0);
                this.txtv_quantity.setGravity(17);
                this.txtv_quantity.setTextSize(16.0f);
                this.txtv_quantity.setTextAlignment(0);
                this.tr.addView((View)this.txtv_quantity);
                this.txtv_prize = new TextView((Context)this);
                this.txtv_prize.setText((CharSequence)cursor.getString(2));
                this.txtv_prize.setTextColor(-16777216);
                this.txtv_prize.setTypeface(Typeface.DEFAULT, 0);
                this.txtv_prize.setPadding(1, 2, 2, 0);
                this.txtv_prize.setGravity(17);
                this.txtv_prize.setTextSize(16.0f);
                this.tr.addView((View)this.txtv_prize);
                srno = 1 + srno;
                this.tr.setBackgroundResource(2131099778);
                this.tablelayout.addView((View)this.tr);
                cursor.moveToNext();
            }
            Cursor cursor2 = this.ObjDBHelp.getTotalExpense(string2, string3);
            cursor2.moveToFirst();
            float f = Float.parseFloat((String)cursor2.getString(0));
            TextView textView = this.amount;
            StringBuilder stringBuilder = new StringBuilder().append("Total Amount : ");
            Object[] arrobject = new Object[]{Float.valueOf((float)f)};
            textView.setText((CharSequence)stringBuilder.append(String.format((String)"%.2f", (Object[])arrobject)).toString());
            cursor2.close();
            return;
        }
        this.clearViewVariables();
        this.setup_with_date();
        srno = 1;
        Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"No Data Found", (int)0);
        toast.setGravity(17, 0, 0);
        toast.show();
    }

    /*
     * Exception decompiling
     */
    public void SetReportDate(String var1_1, String var2_2, String var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 9[TRYBLOCK]
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

    public void alertforedit() {
        try {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    switch (n) {
                        default: {
                            return;
                        }
                        case -1: {
                            Reports.this.editorder("Parcel");
                            return;
                        }
                        case -2: {
                            Reports.this.editorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Reports.this.editorder("HD");
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
                            Reports.this.reprintorder("Parcel");
                            return;
                        }
                        case -2: {
                            Reports.this.reprintorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Reports.this.reprintorder("HD");
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
                    Reports.this.go();
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
                            Reports.this.parcelorder("Parcel");
                            return;
                        }
                        case -2: {
                            Reports.this.parcelorder("Service");
                            return;
                        }
                        case -3: 
                    }
                    Reports.this.parcelorder("HD");
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

    public void btnFromDate_click(View view) throws Exception {
        this.showDialog(100);
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void btnPrint_click(View var1_1) {
        block14 : {
            block15 : {
                try {
                    block17 : {
                        block16 : {
                            var2_2 = new ArrayList();
                            var3_3 = new PlainPrint(this.getApplicationContext(), this.effectivePrintWidth, 4);
                            this.mService = Bluetooth.getServiceInstance();
                            var6_4 = this.tablelayout.getChildCount();
                            if (!this.order_type.getSelectedItem().toString().matches("Cash Book")) break block16;
                            var3_3.prepareTabularForm(5, 2, 4, false);
                            UtilityHelper.printHeaderForCashBook(this.mService, var3_3, null, (ArrayList<String>)var2_2, this.settingsModel, this.getApplicationContext(), this.btnFromdate.getText().toString(), this.btnToDate.getText().toString());
                            var18_5 = 0.0f;
                            var19_6 = "";
                            var20_7 = 0;
                            var21_8 = 0.0f;
                            break block17;
                        }
                        if (this.order_type.getSelectedItem().toString().matches("Daily Report")) {
                            var3_3.prepareTabularForm(4, 1, 3, true);
                            UtilityHelper.printHeaderForDailyReport(this.mService, var3_3, null, (ArrayList<String>)var2_2, this.settingsModel, this.getApplicationContext(), "Daily Report");
                            var8_19 = 0;
                            var9_20 = 0.0f;
                            break block14;
                        }
                        if (!(this.order_type.getSelectedItem().toString().matches("Service") || this.order_type.getSelectedItem().toString().matches("Home Delivery") || (var7_30 = this.order_type.getSelectedItem().toString().matches("Parcel")))) {
                            // empty if block
                        }
                        ** GOTO lbl44
                    }
lbl26: // 2 sources:
                    do {
                        block20 : {
                            block19 : {
                                block18 : {
                                    if (var20_7 >= var6_4) break block18;
                                    if (var20_7 <= 0) break block15;
                                    var22_9 = (TableRow)this.tablelayout.getChildAt(var20_7);
                                    var23_10 = (TextView)var22_9.getChildAt(1);
                                    var24_11 = (TextView)var22_9.getChildAt(2);
                                    var25_12 = (TextView)var22_9.getChildAt(0);
                                    var26_13 = (TextView)var22_9.getChildAt(3);
                                    var27_14 = (TextView)var22_9.getChildAt(4);
                                    var28_15 = (TextView)var22_9.getChildAt(5);
                                    var29_16 = var21_8 + Float.parseFloat((String)var26_13.getText().toString());
                                    if (var28_15 == null || var28_15.getText().toString().matches("")) break block19;
                                    var31_18 = Float.parseFloat((String)var28_15.getText().toString());
                                    var30_17 = var18_5 + var31_18;
                                    break block20;
                                }
                                var2_2.clear();
                                UtilityHelper.printFooterForCashBook(this.mService, var3_3, (ArrayList<String>)var2_2, var21_8, var18_5, this.settingsModel, this.getApplicationContext());
lbl44: // 4 sources:
                                do {
                                    this.str_content = "";
                                    return;
                                    break;
                                } while (true);
                            }
                            var30_17 = var18_5;
                            var31_18 = 0.0f;
                        }
                        if (var27_14 != null && !var27_14.getText().toString().matches("")) {
                            var19_6 = var27_14.getText().toString();
                        }
                        var2_2.clear();
                        UtilityHelper.printCashBook(this.mService, var3_3, (ArrayList<String>)var2_2, var25_12.getText().toString(), var23_10.getText().toString(), var24_11.getText().toString(), Float.parseFloat((String)var26_13.getText().toString()), var19_6, var31_18, this.getApplicationContext());
                        var19_6 = "";
                        var18_5 = var30_17;
                        var21_8 = var29_16;
                        break;
                    } while (true);
                }
                catch (SQLException var5_29) {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)var5_29);
                    return;
                }
                catch (Exception var4_31) {
                    UtilityHelper.GenericCatchBlock(this.getApplicationContext(), var4_31);
                    UtilityHelper.showToastMessage(this.getApplicationContext(), "Wrong User id");
                    return;
                }
            }
            ++var20_7;
            ** while (true)
        }
        do {
            if (var8_19 < var6_4) {
                if (var8_19 > 0) {
                    var13_23 = (TableRow)this.tablelayout.getChildAt(var8_19);
                    var14_24 = (TextView)var13_23.getChildAt(0);
                    var15_25 = (TextView)var13_23.getChildAt(1);
                    var16_21 = (TextView)var13_23.getChildAt(2);
                    var17_22 = (TextView)var13_23.getChildAt(3);
                    var9_20 += Float.parseFloat((String)var17_22.getText().toString());
                    var2_2.clear();
                    UtilityHelper.printDailyReport(this.mService, var3_3, (ArrayList<String>)var2_2, var14_24.getText().toString(), var15_25.getText().toString(), var16_21.getText().toString(), var17_22.getText().toString(), this.getApplicationContext());
                }
            } else {
                var2_2.clear();
                var10_26 = this.mService;
                var11_27 = this.settingsModel;
                var12_28 = this.getApplicationContext();
                UtilityHelper.printFooter(var10_26, var3_3, (ArrayList<String>)var2_2, var9_20, "", "", "", "", "", "", var11_27, var12_28);
                ** continue;
            }
            ++var8_19;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    public void btnShow_click(View var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [15[FORLOOP]], but top level block is 2[TRYBLOCK]
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

    public void btnToDate_click(View view) throws Exception {
        this.showDialog(101);
        return;
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
                    Reports.this.exportDb();
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
                    Reports.this.importDb();
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
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[WHILELOOP]
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

    protected Dialog onCreateDialog(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 100: {
                return new DatePickerDialog((Context)this, this.datePickerListener, this.yr, this.mon, this.day);
            }
            case 101: 
        }
        return new DatePickerDialog((Context)this, this.datePickerListener1, this.yr, this.mon, this.day);
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

    public void parcelorder(String string2) {
        Intent intent = new Intent((Context)this, NewOrder.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    /*
     * Exception decompiling
     */
    public void print_content(int var1_1, String var2_2, String var3_3, String var4_4, String var5_5, String var6_6, String var7_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 8[FORLOOP]
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

    public void reprintorder(String string2) {
        Intent intent = new Intent((Context)this, Reprint.class);
        intent.putExtra("ordertype", string2);
        this.startActivityForResult(intent, 0);
    }

    /*
     * Exception decompiling
     */
    public void rpt_CashBook(String var1_1, String var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[TRYBLOCK]
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rpt_ReprintReport(String string2, String string3) {
        try {
            int n;
            Cursor cursor = this.ObjDBHelp.getReprintOrdersDetails(string2, string3, "0000");
            if (cursor != null) {
                n = cursor.getCount();
                cursor.moveToFirst();
                if (n <= 0) return;
                srno = 1;
            } else {
                this.clearViewVariables();
                this.setup_ReprintReport();
                srno = 1;
                Toast toast = Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"No Data Found", (int)0);
                toast.setGravity(17, 0, 0);
                toast.show();
                return;
            }
            for (int i = 0; i < n; ++i) {
                String string4 = cursor.getString(0);
                String string5 = cursor.getString(1);
                Cursor cursor2 = this.ObjDBHelp.getReprintReport(string4, string2, string3);
                int n2 = cursor2.getCount();
                cursor2.moveToFirst();
                if (n2 > 0) {
                    this.tr = new TableRow((Context)this);
                    this.txtv_SrNo = new TextView((Context)this);
                    this.txtv_SrNo.setText((CharSequence)(srno + ""));
                    this.txtv_SrNo.setTextColor(-16777216);
                    this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 0);
                    this.txtv_SrNo.setPadding(1, 2, 2, 0);
                    this.txtv_SrNo.setGravity(17);
                    this.txtv_SrNo.setTextSize(16.0f);
                    this.tr.addView((View)this.txtv_SrNo);
                    String string6 = cursor2.getString(1).substring(0, 5);
                    this.txt_date = new TextView((Context)this);
                    this.txt_date.setText((CharSequence)string6);
                    this.txt_date.setTextColor(-16777216);
                    this.txt_date.setTypeface(Typeface.DEFAULT, 0);
                    this.txt_date.setPadding(1, 2, 2, 0);
                    this.txt_date.setGravity(17);
                    this.txt_date.setTextSize(16.0f);
                    this.tr.addView((View)this.txt_date);
                    this.txtv_orderno = new TextView((Context)this);
                    this.txtv_orderno.setText((CharSequence)string4);
                    this.txtv_orderno.setTextColor(-16777216);
                    this.txtv_orderno.setTypeface(Typeface.DEFAULT, 0);
                    this.txtv_orderno.setPadding(1, 2, 2, 0);
                    this.txtv_orderno.setGravity(17);
                    this.txtv_orderno.setTextSize(16.0f);
                    this.tr.addView((View)this.txtv_orderno);
                    this.txtv_prize = new TextView((Context)this);
                    this.txtv_prize.setText((CharSequence)cursor2.getString(0));
                    this.txtv_prize.setTextColor(-16777216);
                    this.txtv_prize.setTypeface(Typeface.DEFAULT, 0);
                    this.txtv_prize.setPadding(1, 2, 2, 0);
                    this.txtv_prize.setGravity(17);
                    this.txtv_prize.setTextSize(16.0f);
                    this.tr.addView((View)this.txtv_prize);
                    this.txtv_oldOrderNo = new TextView((Context)this);
                    this.txtv_oldOrderNo.setText((CharSequence)string5);
                    this.txtv_oldOrderNo.setTextColor(-16777216);
                    this.txtv_oldOrderNo.setTypeface(Typeface.DEFAULT, 0);
                    this.txtv_oldOrderNo.setPadding(1, 2, 2, 0);
                    this.txtv_oldOrderNo.setGravity(17);
                    this.txtv_oldOrderNo.setTextSize(16.0f);
                    this.tr.addView((View)this.txtv_oldOrderNo);
                    Cursor cursor3 = this.ObjDBHelp.getReprintReport(string5, string2, string3);
                    int n3 = cursor3.getCount();
                    cursor3.moveToFirst();
                    if (n3 > 0) {
                        this.txtv_oldprize = new TextView((Context)this);
                        this.txtv_oldprize.setText((CharSequence)cursor3.getString(0));
                        this.txtv_oldprize.setTextColor(-16777216);
                        this.txtv_oldprize.setTypeface(Typeface.DEFAULT, 0);
                        this.txtv_oldprize.setPadding(1, 2, 2, 0);
                        this.txtv_oldprize.setGravity(17);
                        this.txtv_oldprize.setTextSize(16.0f);
                        this.tr.addView((View)this.txtv_oldprize);
                        String string7 = cursor3.getString(1).substring(0, 5);
                        this.txtv_olddate = new TextView((Context)this);
                        this.txtv_olddate.setText((CharSequence)string7);
                        this.txtv_olddate.setTextColor(-16777216);
                        this.txtv_olddate.setTypeface(Typeface.DEFAULT, 0);
                        this.txtv_olddate.setPadding(1, 2, 2, 0);
                        this.txtv_olddate.setGravity(17);
                        this.txtv_olddate.setTextSize(16.0f);
                        this.tr.addView((View)this.txtv_olddate);
                    }
                    srno = 1 + srno;
                    this.tr.setBackgroundResource(2131099778);
                    this.tablelayout.addView((View)this.tr);
                }
                cursor.moveToNext();
            }
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public void rpt_dailyReport(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [12[WHILELOOP]], but top level block is 3[TRYBLOCK]
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

    /*
     * Exception decompiling
     */
    public void rptforAllTables(String var1_1, String var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.b.a.a.j.b(Op04StructuredStatement.java:409)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:487)
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

    public void setup() {
        this.rpt_Header = "";
        this.line = new View((Context)this);
        this.line.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(-1, 1));
        this.line.setBackgroundColor(-7829368);
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)"SrNo");
        this.txtv_SrNo.setTextColor(-1);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_SrNo.setPadding(1, 2, 2, 0);
        this.txtv_SrNo.setGravity(17);
        this.txtv_SrNo.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txt_date = new TextView((Context)this);
        this.txt_date.setText((CharSequence)"Date");
        this.txt_date.setTextColor(-1);
        this.txt_date.setTypeface(Typeface.DEFAULT, 1);
        this.txt_date.setPadding(1, 2, 2, 0);
        this.txt_date.setGravity(17);
        this.txt_date.setTextSize(14.0f);
        this.tr.addView((View)this.txt_date);
        this.txtv_orderno = new TextView((Context)this);
        this.txtv_orderno.setText((CharSequence)"Order No");
        this.txtv_orderno.setTextColor(-1);
        this.txtv_orderno.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_orderno.setPadding(1, 2, 2, 0);
        this.txtv_orderno.setGravity(17);
        this.txtv_orderno.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_orderno);
        this.txtv_tableno = new TextView((Context)this);
        this.txtv_tableno.setText((CharSequence)"TableNo");
        this.txtv_tableno.setTextColor(-1);
        this.txtv_tableno.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_tableno.setPadding(1, 2, 2, 0);
        this.txtv_tableno.setGravity(17);
        this.txtv_tableno.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_tableno);
        this.txtv_itemname = new TextView((Context)this);
        this.txtv_itemname.setText((CharSequence)"ItemName");
        this.txtv_itemname.setTextColor(-1);
        this.txtv_itemname.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_itemname.setPadding(1, 2, 8, 0);
        this.txtv_itemname.setGravity(17);
        this.txtv_itemname.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_itemname);
        this.txtv_quantity = new TextView((Context)this);
        this.txtv_quantity.setText((CharSequence)"Qty");
        this.txtv_quantity.setTextColor(-1);
        this.txtv_quantity.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_quantity.setPadding(1, 2, 8, 0);
        this.txtv_quantity.setGravity(17);
        this.txtv_quantity.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_quantity);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)"Prize(Rs.)");
        this.txtv_prize.setTextColor(-1);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_prize.setPadding(1, 2, 0, 0);
        this.txtv_prize.setGravity(17);
        this.txtv_prize.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_prize);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
    }

    public void setup_CashBook() {
        this.line = new View((Context)this);
        this.line.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(-1, 1));
        this.line.setBackgroundColor(-7829368);
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)"SrNo");
        this.txtv_SrNo.setTextColor(-1);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_SrNo.setPadding(1, 2, 2, 0);
        this.txtv_SrNo.setGravity(17);
        this.txtv_SrNo.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txt_date = new TextView((Context)this);
        this.txt_date.setText((CharSequence)"Date");
        this.txt_date.setTextColor(-1);
        this.txt_date.setTypeface(Typeface.DEFAULT, 1);
        this.txt_date.setPadding(1, 2, 2, 0);
        this.txt_date.setGravity(17);
        this.txt_date.setTextSize(14.0f);
        this.tr.addView((View)this.txt_date);
        this.txtv_orderno = new TextView((Context)this);
        this.txtv_orderno.setText((CharSequence)"Order No.");
        this.txtv_orderno.setTextColor(-1);
        this.txtv_orderno.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_orderno.setPadding(1, 2, 2, 0);
        this.txtv_orderno.setGravity(17);
        this.txtv_orderno.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_orderno);
        this.txtv_CRamt = new TextView((Context)this);
        this.txtv_CRamt.setText((CharSequence)"Cr.Amt");
        this.txtv_CRamt.setTextColor(-1);
        this.txtv_CRamt.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_CRamt.setPadding(1, 2, 2, 0);
        this.txtv_CRamt.setGravity(17);
        this.txtv_CRamt.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_CRamt);
        this.txt_date = new TextView((Context)this);
        this.txt_date.setText((CharSequence)"Date");
        this.txt_date.setTextColor(-1);
        this.txt_date.setTypeface(Typeface.DEFAULT, 1);
        this.txt_date.setPadding(1, 2, 2, 0);
        this.txt_date.setGravity(17);
        this.txt_date.setTextSize(14.0f);
        this.tr.addView((View)this.txt_date);
        this.txtv_DRamt = new TextView((Context)this);
        this.txtv_DRamt.setText((CharSequence)"Dr.Amt");
        this.txtv_DRamt.setTextColor(-1);
        this.txtv_DRamt.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_DRamt.setPadding(1, 2, 2, 0);
        this.txtv_DRamt.setGravity(17);
        this.txtv_DRamt.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_DRamt);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
    }

    public void setup_DailyReport() {
        this.line = new View((Context)this);
        this.line.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(-1, 1));
        this.line.setBackgroundColor(-7829368);
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)"SrNo");
        this.txtv_SrNo.setTextColor(-1);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_SrNo.setPadding(1, 2, 2, 0);
        this.txtv_SrNo.setGravity(17);
        this.txtv_SrNo.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txtv_orderno = new TextView((Context)this);
        this.txtv_orderno.setText((CharSequence)"Order No.");
        this.txtv_orderno.setTextColor(-1);
        this.txtv_orderno.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_orderno.setPadding(1, 2, 2, 0);
        this.txtv_orderno.setGravity(17);
        this.txtv_orderno.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_orderno);
        this.txtv_tableno = new TextView((Context)this);
        this.txtv_tableno.setText((CharSequence)"Type");
        this.txtv_tableno.setTextColor(-1);
        this.txtv_tableno.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_tableno.setPadding(1, 2, 2, 0);
        this.txtv_tableno.setGravity(17);
        this.txtv_tableno.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_tableno);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)"Prize(Rs.)");
        this.txtv_prize.setTextColor(-1);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_prize.setPadding(1, 2, 0, 0);
        this.txtv_prize.setGravity(17);
        this.txtv_prize.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_prize);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
    }

    public void setup_ReprintReport() {
        this.line = new View((Context)this);
        this.line.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(-1, 1));
        this.line.setBackgroundColor(-7829368);
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)"SrNo");
        this.txtv_SrNo.setTextColor(-1);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_SrNo.setPadding(1, 2, 2, 0);
        this.txtv_SrNo.setGravity(17);
        this.txtv_SrNo.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txt_date = new TextView((Context)this);
        this.txt_date.setText((CharSequence)"Date");
        this.txt_date.setTextColor(-1);
        this.txt_date.setTypeface(Typeface.DEFAULT, 1);
        this.txt_date.setPadding(1, 2, 2, 0);
        this.txt_date.setGravity(17);
        this.txt_date.setTextSize(14.0f);
        this.tr.addView((View)this.txt_date);
        this.txtv_orderno = new TextView((Context)this);
        this.txtv_orderno.setText((CharSequence)"OrderNo");
        this.txtv_orderno.setTextColor(-1);
        this.txtv_orderno.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_orderno.setPadding(1, 2, 2, 0);
        this.txtv_orderno.setGravity(17);
        this.txtv_orderno.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_orderno);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)"Amount");
        this.txtv_prize.setTextColor(-1);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_prize.setPadding(1, 2, 0, 0);
        this.txtv_prize.setGravity(17);
        this.txtv_prize.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_prize);
        this.txtv_oldOrderNo = new TextView((Context)this);
        this.txtv_oldOrderNo.setText((CharSequence)"OldOrderNo");
        this.txtv_oldOrderNo.setTextColor(-1);
        this.txtv_oldOrderNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_oldOrderNo.setPadding(1, 2, 0, 0);
        this.txtv_oldOrderNo.setGravity(17);
        this.txtv_oldOrderNo.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_oldOrderNo);
        this.txtv_oldprize = new TextView((Context)this);
        this.txtv_oldprize.setText((CharSequence)"Amount");
        this.txtv_oldprize.setTextColor(-1);
        this.txtv_oldprize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_oldprize.setPadding(1, 2, 0, 0);
        this.txtv_oldprize.setGravity(17);
        this.txtv_oldprize.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_oldprize);
        this.txtv_olddate = new TextView((Context)this);
        this.txtv_olddate.setText((CharSequence)"Date");
        this.txtv_olddate.setTextColor(-1);
        this.txtv_olddate.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_olddate.setPadding(1, 2, 0, 0);
        this.txtv_olddate.setGravity(17);
        this.txtv_olddate.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_olddate);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
    }

    public void setup_with_date() {
        this.line = new View((Context)this);
        this.line.setLayoutParams((ViewGroup.LayoutParams)new TableRow.LayoutParams(-1, 1));
        this.line.setBackgroundColor(-7829368);
        this.tr = new TableRow((Context)this);
        this.txtv_SrNo = new TextView((Context)this);
        this.txtv_SrNo.setText((CharSequence)"SrNo");
        this.txtv_SrNo.setTextColor(-1);
        this.txtv_SrNo.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_SrNo.setPadding(1, 2, 2, 0);
        this.txtv_SrNo.setGravity(17);
        this.txtv_SrNo.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_SrNo);
        this.txt_date = new TextView((Context)this);
        this.txt_date.setText((CharSequence)"Date");
        this.txt_date.setTextColor(-1);
        this.txt_date.setTypeface(Typeface.DEFAULT, 1);
        this.txt_date.setPadding(1, 2, 2, 0);
        this.txt_date.setGravity(17);
        this.txt_date.setTextSize(14.0f);
        this.tr.addView((View)this.txt_date);
        this.txtv_orderno = new TextView((Context)this);
        this.txtv_orderno.setText((CharSequence)"Order No.");
        this.txtv_orderno.setTextColor(-1);
        this.txtv_orderno.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_orderno.setPadding(1, 2, 2, 0);
        this.txtv_orderno.setGravity(17);
        this.txtv_orderno.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_orderno);
        this.txtv_itemname = new TextView((Context)this);
        this.txtv_itemname.setText((CharSequence)"ItemName");
        this.txtv_itemname.setTextColor(-1);
        this.txtv_itemname.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_itemname.setPadding(1, 2, 8, 0);
        this.txtv_itemname.setGravity(17);
        this.txtv_itemname.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_itemname);
        this.txtv_quantity = new TextView((Context)this);
        this.txtv_quantity.setText((CharSequence)"Qty");
        this.txtv_quantity.setTextColor(-1);
        this.txtv_quantity.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_quantity.setPadding(1, 2, 8, 0);
        this.txtv_quantity.setGravity(17);
        this.txtv_quantity.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_quantity);
        this.txtv_prize = new TextView((Context)this);
        this.txtv_prize.setText((CharSequence)"Prize(Rs.)");
        this.txtv_prize.setTextColor(-1);
        this.txtv_prize.setTypeface(Typeface.DEFAULT, 1);
        this.txtv_prize.setPadding(1, 2, 0, 0);
        this.txtv_prize.setGravity(17);
        this.txtv_prize.setTextSize(14.0f);
        this.tr.addView((View)this.txtv_prize);
        this.tr.setBackgroundColor(-16777216);
        this.tablelayout.addView((View)this.tr);
    }

    /*
     * Exception decompiling
     */
    public void show(String var1_1, String var2_2, String var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 10[TRYBLOCK]
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

}

