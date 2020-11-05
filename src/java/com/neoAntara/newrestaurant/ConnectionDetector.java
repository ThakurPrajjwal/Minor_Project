/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.NetworkInfo$State
 *  java.lang.Object
 *  java.lang.String
 */
package com.neoAntara.newrestaurant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        NetworkInfo[] arrnetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager)this._context.getSystemService("connectivity");
        if (connectivityManager != null && (arrnetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
            for (int i = 0; i < arrnetworkInfo.length; ++i) {
                if (arrnetworkInfo[i].getState() != NetworkInfo.State.CONNECTED) continue;
                return true;
            }
        }
        return false;
    }
}

