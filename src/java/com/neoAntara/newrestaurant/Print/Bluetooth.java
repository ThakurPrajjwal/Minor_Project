/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.bluetooth.BluetoothDevice
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.String
 */
package com.neoAntara.newrestaurant.Print;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.neoAntara.newrestaurant.Print.DeviceListActivity;
import com.zj.btsdk.BluetoothService;

public class Bluetooth
extends Application {
    private static final int REQUEST_CONNECT_DEVICE = 6;
    private static final int REQUEST_ENABLE_BT = 4;
    static Activity act = null;
    static String bluetootMacAddress;
    static boolean btOnOffConnect;
    static BluetoothDevice con_dev;
    static Context context;
    private static final Handler mHandler;
    static BluetoothService mService;
    private static SharedPreferences pref;
    static boolean printerConnected;
    static boolean printerConnecting;

    static {
        context = null;
        bluetootMacAddress = null;
        printerConnected = false;
        btOnOffConnect = false;
        printerConnecting = false;
        mService = null;
        con_dev = null;
        pref = null;
        mHandler = new Handler(){

            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        return;
                    }
                    case 1: {
                        switch (message.arg1) {
                            default: {
                                return;
                            }
                            case 0: 
                            case 1: {
                                Bluetooth.printerConnected = false;
                                Bluetooth.printerConnecting = false;
                                return;
                            }
                            case 3: {
                                Bluetooth.printerConnected = true;
                                Bluetooth.printerConnecting = false;
                                return;
                            }
                            case 2: 
                        }
                        Bluetooth.printerConnected = false;
                        Bluetooth.printerConnecting = true;
                        return;
                    }
                    case 5: {
                        Bluetooth.printerConnected = false;
                        Bluetooth.disconnect();
                        return;
                    }
                    case 6: 
                }
                Bluetooth.printerConnected = false;
                Toast.makeText((Context)Bluetooth.context, (CharSequence)"Cannot connect printer", (int)0).show();
                Bluetooth.disconnect();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void connectPrinter(Context context, Activity activity) {
        Log.v((String)"HyperMan", (String)"connect printer");
        Bluetooth.context = context;
        act = activity;
        if (printerConnecting) {
            Toast.makeText((Context)Bluetooth.context, (CharSequence)"Trying to connect printer!!", (int)1).show();
            return;
        } else {
            mService = new BluetoothService(Bluetooth.context, mHandler);
            if (!mService.isAvailable()) {
                Toast.makeText((Context)Bluetooth.context, (CharSequence)"Bluetooth not available", (int)1).show();
                return;
            }
            if (mService.isBTopen()) {
                if (bluetootMacAddress == null) {
                    Bluetooth.pairPrinter(context, activity);
                    return;
                }
                con_dev = mService.getDevByMac(bluetootMacAddress);
                mService.connect(con_dev);
                return;
            }
            if (mService.isBTopen()) return;
            {
                Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                act.startActivityForResult(intent, 4);
                return;
            }
        }
    }

    public static void disconnect() {
        if (mService != null && mService.isAvailable()) {
            mService.stop();
        }
        mService = null;
        bluetootMacAddress = null;
        con_dev = null;
        btOnOffConnect = false;
        printerConnected = false;
    }

    public static BluetoothService getServiceInstance() {
        return mService;
    }

    public static boolean isPrinterConnected() {
        return printerConnected;
    }

    public static boolean isPrinterConnected(Context context, Activity activity) {
        Bluetooth.context = context;
        act = activity;
        return printerConnected;
    }

    public static void pairPrinter(Context context, Activity activity) {
        Bluetooth.context = context;
        act = activity;
        Intent intent = new Intent(Bluetooth.context, DeviceListActivity.class);
        act.startActivityForResult(intent, 6);
    }

    public static void pairedPrinterAddress(Context context, Activity activity, String string2) {
        Bluetooth.context = context;
        act = activity;
        bluetootMacAddress = string2;
        con_dev = mService.getDevByMac(string2);
        mService.connect(con_dev);
    }

    protected void onDestroy() {
        if (mService != null) {
            mService.stop();
        }
        mService = null;
    }

}

