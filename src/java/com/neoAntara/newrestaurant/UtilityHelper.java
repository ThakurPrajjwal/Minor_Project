/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 *  android.widget.Button
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Calendar
 *  java.util.Date
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.neoAntara.newrestaurant.Logger;
import com.neoAntara.newrestaurant.Models.AppActivationModel;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.Models.SettingsModel;
import com.neoAntara.newrestaurant.Print.Bluetooth;
import com.neoAntara.newrestaurant.Print.DeviceListActivity;
import com.neoAntara.newrestaurant.Print.PlainPrint;
import com.neoAntara.newrestaurant.Print.PrintMultilingualText;
import com.neoAntara.newrestaurant.SessionManager;
import com.zj.btsdk.BluetoothService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UtilityHelper {
    static final int REQUEST_CONNECT_DEVICE = 6;
    static final int REQUEST_ENABLE_BT = 4;

    public static boolean ConnectPrinter(Context context, Activity activity, Button button) {
        block5 : {
            try {
                if (!Bluetooth.isPrinterConnected(context, activity)) {
                    Bluetooth.connectPrinter(context, activity);
                }
                if (button == null) break block5;
            }
            catch (Exception exception) {
                exception.printStackTrace();
                UtilityHelper.showToastMessage(context, "Error occurred while connecting to printer...Try again.");
                if (button != null) {
                    button.setBackgroundColor(-65536);
                }
                return false;
            }
            button.setBackgroundColor(-16711936);
        }
        return true;
    }

    public static void GenericCatchBlock(Context context, Exception exception) {
        UtilityHelper.LogException(exception);
        UtilityHelper.showToastMessage(context, "Error occurred...Try again");
    }

    public static AppActivationModel InitializeAppActivationModel() {
        return AppActivationModel.GetInstance();
    }

    public static RolesModel InitializeRolesModel() {
        return RolesModel.GetInstance();
    }

    public static void LogException(Exception exception) {
        Logger.GetInstance().LogException(exception);
    }

    public static void LogMessage(String string2) {
        Logger.GetInstance().LogDebugMessage(string2);
    }

    public static void Logout() {
        if (AppActivationModel.GetInstance().get_SessionManager().isLoggedIn()) {
            AppActivationModel.GetInstance().get_SessionManager().logoutUser();
        }
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static boolean isUserLoggedIn() {
        RolesModel.GetInstance();
        return RolesModel.get_loggedInUser() != null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void onActivityResult_Generic(int n, int n2, Intent intent, Context context, Activity activity) {
        if (n == 4 && n2 == -1) {
            Bluetooth.pairPrinter(context, activity);
            return;
        } else {
            if (n != 6 || n2 != -1) return;
            {
                Bluetooth.pairedPrinterAddress(context, activity, intent.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS));
                return;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printCashBook(BluetoothService var0, PlainPrint var1_1, ArrayList<String> var2_2, String var3_3, String var4_4, String var5_5, float var6_6, String var7_7, float var8_8, Context var9_9) {
        if (var0 != null) ** GOTO lbl5
        UtilityHelper.showToastMessage(var9_9, "Error connecting to printer...Try again.");
        return;
lbl5: // 1 sources:
        var10_10 = new byte[]{27, 33, 0};
        var2_2.add((Object)var5_5);
        var2_2.add((Object)var4_4);
        var14_11 = new Object[]{Float.valueOf((float)var6_6)};
        var2_2.add((Object)String.format((String)"%.2f", (Object[])var14_11));
        var2_2.add((Object)var7_7);
        if (!(var8_8 > 0.0f)) ** GOTO lbl17
        try {
            var17_12 = new Object[]{Float.valueOf((float)var8_8)};
            var2_2.add((Object)String.format((String)"%.2f", (Object[])var17_12));
            ** break block4
lbl17: // 2 sources:
            var2_2.add((Object)"");
        }
        catch (Exception var11_13) {
            var11_13.printStackTrace();
            UtilityHelper.showToastMessage(var9_9, "Print Error.");
            return;
        }
lbl-1000: // 2 sources:
        {
            
            var1_1.startAddingContent4printFields();
            var1_1.addItemContent(var2_2);
            var0.write(var10_10);
            var0.sendMessage(var1_1.getContent4PrintFields(), "GBK");
            var2_2.clear();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printDailyReport(BluetoothService var0, PlainPrint var1_1, ArrayList<String> var2_2, String var3_3, String var4_4, String var5_5, String var6_6, Context var7_7) {
        if (var0 != null) ** GOTO lbl5
        try {
            UtilityHelper.showToastMessage(var7_7, "Error connecting to printer...Try again.");
            return;
lbl5: // 1 sources:
            var8_8 = new byte[]{27, 33, 0};
            var2_2.add((Object)var3_3);
            var2_2.add((Object)var4_4);
            var2_2.add((Object)var5_5);
            var13_9 = new Object[]{Float.valueOf((float)Float.parseFloat((String)var6_6))};
            var2_2.add((Object)String.format((String)"%.2f", (Object[])var13_9));
            var1_1.startAddingContent4printFields();
            var1_1.addItemContent(var2_2);
            var0.write(var8_8);
            var0.sendMessage(var1_1.getContent4PrintFields(), "GBK");
            var2_2.clear();
            return;
        }
        catch (Exception var9_10) {
            var9_10.printStackTrace();
            UtilityHelper.showToastMessage(var7_7, "Print Error.");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printFooter(BluetoothService var0, PlainPrint var1_1, ArrayList<String> var2_2, float var3_3, String var4_4, String var5_5, String var6_6, String var7_7, String var8_8, String var9_9, SettingsModel var10_10, Context var11_11) {
        block12 : {
            var12_12 = var3_3;
            if (var0 != null) ** GOTO lbl6
            UtilityHelper.showToastMessage(var11_11, "Error connecting to printer...Try again.");
            return;
lbl6: // 1 sources:
            var13_13 = new byte[]{27, 33, 0};
            var14_14 = new byte[]{27, 33, 0};
            var15_15 = new byte[]{27, 97, 0};
            v0 = new byte[]{27, 97, 1};
            var17_16 = new byte[]{27, 97, 2};
            var14_14[2] = (byte)(8 | var13_13[2]);
            var0.write(var14_14);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var0.write(var17_16);
            var19_17 = new StringBuilder().append("Total Amount: ");
            var20_18 = new Object[]{Float.valueOf((float)var3_3)};
            var0.sendMessage(var19_17.append(String.format((String)"%.2f", (Object[])var20_18)).toString(), "GBK");
            var0.write(var14_14);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var21_19 = var10_10.get_cgst().matches("");
            var22_20 = 0.0f;
            if (!var21_19) {
                var23_21 = var10_10.get_cgst().equals(null);
                var22_20 = 0.0f;
                if (!var23_21) {
                    var22_20 = var3_3 * (Float.parseFloat((String)var10_10.get_cgst()) / 100.0f);
                    var0.write(var17_16);
                    var24_22 = new StringBuilder().append("CGST(").append(var10_10.get_cgst()).append("%): ");
                    var25_23 = new Object[]{Float.valueOf((float)var22_20)};
                    var0.sendMessage(var24_22.append(String.format((String)"%.2f", (Object[])var25_23)).toString(), "GBK");
                    var12_12 += var22_20;
                }
            }
            if (var10_10.get_sgst().matches("") || var10_10.get_sgst().equals(null)) break block12;
            var32_24 = var3_3 * (Float.parseFloat((String)var10_10.get_sgst()) / 100.0f);
            var0.write(var17_16);
            var33_25 = new StringBuilder().append("SGST(").append(var10_10.get_sgst()).append("%): ");
            var34_26 = new Object[]{Float.valueOf((float)var32_24)};
            var0.sendMessage(var33_25.append(String.format((String)"%.2f", (Object[])var34_26)).toString(), "GBK");
            var0.write(var17_16);
            var35_27 = new StringBuilder().append("Total GST: ");
            var36_28 = new Object[]{Float.valueOf((float)(var32_24 + var22_20))};
            var0.sendMessage(var35_27.append(String.format((String)"%.2f", (Object[])var36_28)).toString(), "GBK");
            var12_12 += var32_24;
        }
        try {
            if (!var8_8.matches("") && !var8_8.equals(null)) {
                var0.write(var17_16);
                var30_29 = new StringBuilder().append("Discount: ");
                var31_30 = new Object[]{Float.valueOf((float)Float.parseFloat((String)var8_8))};
                var0.sendMessage(var30_29.append(String.format((String)"%.2f", (Object[])var31_30)).toString(), "GBK");
                var12_12 -= Float.parseFloat((String)var8_8);
            }
            if (!var9_9.matches("") && !var9_9.equals(null)) {
                var0.write(var17_16);
                var28_31 = new StringBuilder().append("Other Charges: ");
                var29_32 = new Object[]{Float.valueOf((float)Float.parseFloat((String)var9_9))};
                var0.sendMessage(var28_31.append(String.format((String)"%.2f", (Object[])var29_32)).append("\n").toString(), "GBK");
                var12_12 += Float.parseFloat((String)var9_9);
            }
            var0.write(var14_14);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var0.write(var14_14);
            var0.write(var17_16);
            var26_33 = new StringBuilder().append("Grand Total: ");
            var27_34 = new Object[]{Float.valueOf((float)var12_12)};
            var0.sendMessage(var26_33.append(String.format((String)"%.2f", (Object[])var27_34)).toString(), "GBK");
            if (var4_4 != "Home Delivery") ** GOTO lbl81
        }
        catch (Exception var18_35) {
            var18_35.printStackTrace();
            UtilityHelper.showToastMessage(var11_11, "Print error while setting up footer.");
            return;
        }
        var0.write(var14_14);
        var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
        if (!var5_5.matches("") && !var5_5.equals(null)) {
            var0.write(var15_15);
            var0.sendMessage("Customer Name: " + var5_5, "GBK");
        }
        if (!var6_6.matches("") && !var6_6.equals(null)) {
            var0.write(var15_15);
            var0.sendMessage("Address: " + var6_6, "GBK");
        }
        if (!var7_7.matches("") && !var7_7.equals(null)) {
            var0.write(var15_15);
            var0.sendMessage("Mobile No: " + var7_7, "GBK");
        }
lbl81: // 4 sources:
        var0.write(var14_14);
        var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
        var0.write(var15_15);
        var0.sendMessage("THANK YOU...VISIT AGAIN!!!\n", "GBK");
        var0.write(var13_13);
        var0.sendMessage(".\n", "GBK");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printFooterForCashBook(BluetoothService var0, PlainPrint var1_1, ArrayList<String> var2_2, float var3_3, float var4_4, SettingsModel var5_5, Context var6_6) {
        block6 : {
            var7_7 = var3_3;
            if (var0 != null) ** GOTO lbl6
            UtilityHelper.showToastMessage(var6_6, "Error connecting to printer...Try again.");
            return;
lbl6: // 1 sources:
            var8_8 = new byte[]{27, 33, 0};
            var9_9 = new byte[]{27, 33, 0};
            v0 = new byte[]{27, 97, 0};
            v1 = new byte[]{27, 97, 1};
            var12_10 = new byte[]{27, 97, 2};
            var9_9[2] = (byte)(8 | var8_8[2]);
            var2_2.clear();
            var0.write(var9_9);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var0.write(var12_10);
            var14_11 = new StringBuilder().append("Total Cr. Amount: ");
            var15_12 = new Object[]{Float.valueOf((float)var3_3)};
            var0.sendMessage(var14_11.append(String.format((String)"%.2f", (Object[])var15_12)).toString(), "GBK");
            var0.write(var9_9);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var16_13 = var5_5.get_cgst().matches("");
            var17_14 = 0.0f;
            if (!var16_13) {
                var18_15 = var5_5.get_cgst().equals(null);
                var17_14 = 0.0f;
                if (!var18_15) {
                    var17_14 = var3_3 * (Float.parseFloat((String)var5_5.get_cgst()) / 100.0f);
                    var0.write(var12_10);
                    var19_16 = new StringBuilder().append("CGST(").append(var5_5.get_cgst()).append("%): ");
                    var20_17 = new Object[]{Float.valueOf((float)var17_14)};
                    var0.sendMessage(var19_16.append(String.format((String)"%.2f", (Object[])var20_17)).toString(), "GBK");
                    var7_7 += var17_14;
                }
            }
            if (var5_5.get_sgst().matches("") || var5_5.get_sgst().equals(null)) break block6;
            var25_18 = var3_3 * (Float.parseFloat((String)var5_5.get_sgst()) / 100.0f);
            var0.write(var12_10);
            var26_19 = new StringBuilder().append("SGST(").append(var5_5.get_sgst()).append("%): ");
            var27_20 = new Object[]{Float.valueOf((float)var25_18)};
            var0.sendMessage(var26_19.append(String.format((String)"%.2f", (Object[])var27_20)).toString(), "GBK");
            var0.write(var12_10);
            var28_21 = new StringBuilder().append("Total GST: ");
            var29_22 = new Object[]{Float.valueOf((float)(var25_18 + var17_14))};
            var0.sendMessage(var28_21.append(String.format((String)"%.2f", (Object[])var29_22)).toString(), "GBK");
            var7_7 += var25_18;
        }
        try {
            var0.write(var9_9);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var0.write(var9_9);
            var0.write(var12_10);
            var21_23 = new StringBuilder().append("Grand Total: ");
            var22_24 = new Object[]{Float.valueOf((float)var7_7)};
            var0.sendMessage(var21_23.append(String.format((String)"%.2f", (Object[])var22_24)).toString(), "GBK");
            var0.write(var9_9);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var0.write(var12_10);
            var23_25 = new StringBuilder().append("Total Dr. Amount: ");
            var24_26 = new Object[]{Float.valueOf((float)var4_4)};
            var0.sendMessage(var23_25.append(String.format((String)"%.2f", (Object[])var24_26)).toString(), "GBK");
            var0.write(var9_9);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var0.write(var8_8);
            var0.sendMessage(".\n", "GBK");
            return;
        }
        catch (Exception var13_27) {
            var13_27.printStackTrace();
            UtilityHelper.showToastMessage(var6_6, "Print error while setting up footer.");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printHeader(BluetoothService var0, PlainPrint var1_1, String var2_2, ArrayList<String> var3_3, SettingsModel var4_4, String var5_5, Context var6_6) {
        if (var0 != null) ** GOTO lbl5
        try {
            UtilityHelper.showToastMessage(var6_6, "Error connecting to printer...Try again.");
            return;
lbl5: // 1 sources:
            var7_7 = new byte[]{27, 33, 0};
            var8_8 = new byte[]{27, 33, 0};
            var9_9 = new byte[]{27, 97, 0};
            var10_10 = new byte[]{27, 97, 1};
            v0 = new byte[]{27, 97, 2};
            var8_8[2] = (byte)(8 | var7_7[2]);
            var13_11 = new byte[]{27, 33, 0};
            var13_11[2] = (byte)(16 | var7_7[2]);
            var0.write(var13_11);
            var0.write(var10_10);
            var0.sendMessage(var4_4.get_resturantName(), "GBK");
            var0.write(var7_7);
            var0.write(var10_10);
            var0.sendMessage(var4_4.get_restaurantAddress(), "GBK");
            if (!var4_4.get_phoneNo().matches("") && !var4_4.get_phoneNo().equals(null)) {
                var0.write(var10_10);
                var0.sendMessage("Phone No: " + var4_4.get_phoneNo(), "GBK");
            }
            if (!var4_4.get_website().matches("") && !var4_4.get_website().equals(null)) {
                var0.write(var10_10);
                var0.sendMessage("Website: " + var4_4.get_website(), "GBK");
            }
            if (!var4_4.get_gstno().matches("") && !var4_4.get_gstno().equals(null)) {
                var0.write(var10_10);
                var0.sendMessage("GSTN No : " + var4_4.get_gstno(), "GBK");
            }
            var0.write(var9_9);
            var0.sendMessage("\nOrder No: " + var2_2, "GBK");
            var0.write(var9_9);
            var0.sendMessage("Date: " + UtilityHelper.getCurrentDateTime(), "GBK");
            var0.write(var9_9);
            var0.sendMessage("Payment: " + var5_5, "GBK");
            var0.write(var8_8);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var3_3.add((Object)"Item");
            var3_3.add((Object)"Qty");
            var3_3.add((Object)"Rate");
            var3_3.add((Object)"Amt");
            Log.v((String)"mainact", (String)"bbbeb");
            var1_1.startAddingContent4printFields();
            var1_1.addItemContent(var3_3);
            Log.v((String)"mainact", (String)var1_1.getContent4PrintFields());
            var0.write(var8_8);
            var0.sendMessage(var1_1.getContent4PrintFields(), "GBK");
            var0.write(var8_8);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var3_3.clear();
            return;
        }
        catch (Exception var12_12) {
            var12_12.printStackTrace();
            UtilityHelper.showToastMessage(var6_6, "Print error while setting up header.");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printHeaderForCashBook(BluetoothService var0, PlainPrint var1_1, String var2_2, ArrayList<String> var3_3, SettingsModel var4_4, Context var5_5, String var6_6, String var7_7) {
        if (var0 != null) ** GOTO lbl5
        try {
            UtilityHelper.showToastMessage(var5_5, "Error connecting to printer...Try again.");
            return;
lbl5: // 1 sources:
            var8_8 = new byte[]{27, 33, 0};
            var9_9 = new byte[]{27, 33, 0};
            var10_10 = new byte[]{27, 97, 0};
            var11_11 = new byte[]{27, 97, 1};
            v0 = new byte[]{27, 97, 2};
            var9_9[2] = (byte)(8 | var8_8[2]);
            var14_12 = new byte[]{27, 33, 0};
            var14_12[2] = (byte)(16 | var8_8[2]);
            var0.write(var14_12);
            var0.write(var11_11);
            var0.sendMessage(var4_4.get_resturantName(), "GBK");
            var0.write(var8_8);
            var0.write(var11_11);
            var0.sendMessage(var4_4.get_restaurantAddress(), "GBK");
            var0.write(var10_10);
            var0.sendMessage("Date: " + UtilityHelper.getCurrentDateTime(), "GBK");
            var0.write(var10_10);
            var0.sendMessage("Report Type: Cash Boook", "GBK");
            var0.write(var10_10);
            var0.sendMessage("From : " + var6_6 + " To: " + var7_7, "GBK");
            var0.write(var9_9);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var3_3.add((Object)"OrderNo");
            var3_3.add((Object)"Date");
            var3_3.add((Object)"Cr.Amt");
            var3_3.add((Object)"Date");
            var3_3.add((Object)"Dr.Amt");
            Log.v((String)"mainact", (String)"bbbeb");
            var1_1.startAddingContent4printFields();
            var1_1.addItemContent(var3_3);
            Log.v((String)"mainact", (String)var1_1.getContent4PrintFields());
            var0.write(var9_9);
            var0.sendMessage(var1_1.getContent4PrintFields(), "GBK");
            var0.write(var9_9);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var3_3.clear();
            return;
        }
        catch (Exception var13_13) {
            var13_13.printStackTrace();
            UtilityHelper.showToastMessage(var5_5, "Print error while setting up header.");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printHeaderForDailyReport(BluetoothService var0, PlainPrint var1_1, String var2_2, ArrayList<String> var3_3, SettingsModel var4_4, Context var5_5, String var6_6) {
        if (var0 != null) ** GOTO lbl5
        try {
            UtilityHelper.showToastMessage(var5_5, "Error connecting to printer...Try again.");
            return;
lbl5: // 1 sources:
            var7_7 = new byte[]{27, 33, 0};
            var8_8 = new byte[]{27, 33, 0};
            var9_9 = new byte[]{27, 97, 0};
            var10_10 = new byte[]{27, 97, 1};
            v0 = new byte[]{27, 97, 2};
            var8_8[2] = (byte)(8 | var7_7[2]);
            var13_11 = new byte[]{27, 33, 0};
            var13_11[2] = (byte)(16 | var7_7[2]);
            var0.write(var13_11);
            var0.write(var10_10);
            var0.sendMessage(var4_4.get_resturantName(), "GBK");
            var0.write(var7_7);
            var0.write(var10_10);
            var0.sendMessage(var4_4.get_restaurantAddress(), "GBK");
            var0.write(var9_9);
            var0.sendMessage("Date: " + UtilityHelper.getCurrentDateTime(), "GBK");
            var0.write(var9_9);
            var0.sendMessage("Report Type: Daily Report", "GBK");
            var0.write(var8_8);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var3_3.add((Object)"Sr");
            var3_3.add((Object)"OrderNo");
            var3_3.add((Object)"OrderType");
            var3_3.add((Object)"Amt");
            Log.v((String)"mainact", (String)"bbbeb");
            var1_1.startAddingContent4printFields();
            var1_1.addItemContent(var3_3);
            Log.v((String)"mainact", (String)var1_1.getContent4PrintFields());
            var0.write(var8_8);
            var0.sendMessage(var1_1.getContent4PrintFields(), "GBK");
            var0.write(var8_8);
            var0.sendMessage(var1_1.getDashesFullLine(), "GBK");
            var3_3.clear();
            return;
        }
        catch (Exception var12_12) {
            var12_12.printStackTrace();
            UtilityHelper.showToastMessage(var5_5, "Print error while setting up header.");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void printTabularData(BluetoothService var0, PlainPrint var1_1, PrintMultilingualText var2_2, ArrayList<String> var3_3, String var4_4, String var5_5, String var6_6, String var7_7, String var8_8, Context var9_9) {
        if (var0 != null) ** GOTO lbl5
        try {
            UtilityHelper.showToastMessage(var9_9, "Error connecting to printer...Try again.");
            return;
lbl5: // 1 sources:
            var10_10 = new byte[]{27, 33, 0};
            var3_3.add((Object)var5_5);
            var3_3.add((Object)var6_6);
            var3_3.add((Object)var7_7);
            var15_11 = new Object[]{Float.valueOf((float)Float.parseFloat((String)var8_8))};
            var3_3.add((Object)String.format((String)"%.2f", (Object[])var15_11));
            var1_1.startAddingContent4printFields();
            var1_1.addItemContent(var3_3);
            var0.write(var10_10);
            var0.sendMessage(var1_1.getContent4PrintFields(), "GBK");
            var3_3.clear();
            return;
        }
        catch (Exception var11_12) {
            var11_12.printStackTrace();
            UtilityHelper.showToastMessage(var9_9, "Print Error.");
            return;
        }
    }

    public static void showToastMessage(Context context, String string2) {
        Toast toast = Toast.makeText((Context)context, (CharSequence)string2, (int)1);
        toast.setGravity(17, 0, 0);
        toast.show();
    }
}

