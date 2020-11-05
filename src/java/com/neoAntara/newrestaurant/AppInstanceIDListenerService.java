/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  com.google.android.gms.iid.InstanceIDListenerService
 *  java.lang.Class
 *  java.lang.String
 */
package com.neoAntara.newrestaurant;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.neoAntara.newrestaurant.RegistrationIntentService;

public class AppInstanceIDListenerService
extends InstanceIDListenerService {
    private static final String TAG = "MyInstanceIDLS";

    public void onTokenRefresh() {
        this.startService(new Intent((Context)this, RegistrationIntentService.class));
    }
}

