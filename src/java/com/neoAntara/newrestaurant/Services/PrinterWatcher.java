/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.IBinder
 */
package com.neoAntara.newrestaurant.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PrinterWatcher
extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        return 1;
    }

    public void onTaskRemoved(Intent intent) {
    }
}

