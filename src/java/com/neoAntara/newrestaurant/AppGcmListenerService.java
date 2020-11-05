/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.os.Bundle
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Log
 *  android.widget.Toast
 *  com.google.android.gms.gcm.GcmListenerService
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package com.neoAntara.newrestaurant;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.gcm.GcmListenerService;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.UtilityHelper;

public class AppGcmListenerService
extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";

    private void HandleMessage(String string2) {
        try {
            String string3 = Settings.Secure.getString((ContentResolver)this.getContentResolver(), (String)"android_id");
            DBHelper dBHelper = new DBHelper(this.getApplicationContext(), string3);
            if (string2.equals((Object)"0")) {
                if (dBHelper.ActivateDeActivateApp(string3, 2)) {
                    Log.d((String)"App Status", (String)"Deactivate");
                    UtilityHelper.Logout();
                    return;
                }
            } else if (string2.equals((Object)"1") && dBHelper.ActivateDeActivateApp(string3, 1)) {
                Log.d((String)"App Status", (String)"Activate");
                return;
            }
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
        }
    }

    private void sendNotification(String string2) {
        Toast.makeText((Context)this.getApplicationContext(), (CharSequence)string2, (int)1).show();
    }

    public void onMessageReceived(String string2, Bundle bundle) {
        String string3 = bundle.getString("message");
        Log.d((String)TAG, (String)("From: " + string2));
        Log.d((String)TAG, (String)("Message: " + string3));
        if (string2.startsWith("/topics/")) {
            // empty if block
        }
        this.HandleMessage(string3);
    }
}

