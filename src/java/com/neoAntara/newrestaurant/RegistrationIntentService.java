/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.IntentService
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Log
 *  com.google.android.gms.gcm.GcmPubSub
 *  com.google.android.gms.iid.InstanceID
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.neoAntara.newrestaurant;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.iid.InstanceID;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.UtilityHelper;
import java.io.IOException;

public class RegistrationIntentService
extends IntentService {
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = new String[]{"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    private void sendRegistrationToServer(String string2) {
        String string3 = Settings.Secure.getString((ContentResolver)this.getContentResolver(), (String)"android_id");
        new DBHelper(this.getApplicationContext(), string3).InsertToken(string2);
    }

    private void subscribeTopics(String string2) throws IOException {
        GcmPubSub gcmPubSub = GcmPubSub.getInstance((Context)this);
        for (String string3 : TOPICS) {
            gcmPubSub.subscribe(string2, "/topics/" + string3, null);
        }
    }

    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this);
        try {
            String string2 = InstanceID.getInstance((Context)this).getToken(this.getString(2131427387), "GCM", null);
            Log.i((String)TAG, (String)("GCM Registration Token: " + string2));
            this.sendRegistrationToServer(string2);
            this.subscribeTopics(string2);
            sharedPreferences.edit().putBoolean("sentTokenToServer", true).apply();
            return;
        }
        catch (Exception exception) {
            Log.d((String)TAG, (String)"Failed to complete token refresh", (Throwable)exception);
            sharedPreferences.edit().putBoolean("sentTokenToServer", false).apply();
            UtilityHelper.LogException(exception);
            return;
        }
    }
}

