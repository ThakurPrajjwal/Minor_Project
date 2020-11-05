/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.neoAntara.newrestaurant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.neoAntara.newrestaurant.MainActivity;
import java.util.HashMap;

public class SessionManager {
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    private static final String PREF_NAME = "AndroidHivePref";
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public SessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(this._context, MainActivity.class);
            intent.addFlags(67108864);
            intent.setFlags(268435456);
            this._context.startActivity(intent);
        }
    }

    public void createLoginSession(String string2, String string3) {
        this.editor.clear();
        this.editor.commit();
        this.editor.putBoolean(IS_LOGIN, true);
        this.editor.putString(KEY_NAME, string2);
        this.editor.putString(KEY_EMAIL, string3);
        this.editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap hashMap = new HashMap();
        hashMap.put((Object)KEY_NAME, (Object)this.pref.getString(KEY_NAME, null));
        hashMap.put((Object)KEY_EMAIL, (Object)this.pref.getString(KEY_EMAIL, null));
        return hashMap;
    }

    public boolean isLoggedIn() {
        return this.pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        this.editor.clear();
        this.editor.commit();
        Intent intent = new Intent(this._context, MainActivity.class);
        intent.addFlags(67108864);
        intent.setFlags(268435456);
        this._context.startActivity(intent);
    }
}

