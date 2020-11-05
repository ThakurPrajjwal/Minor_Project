/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 */
package com.neoAntara.newrestaurant.Models;

import android.content.Context;
import com.neoAntara.newrestaurant.SessionManager;

public class AppActivationModel {
    private static AppActivationModel Instance = null;
    private String _address;
    private String _emailId;
    private String _mobileNo;
    private String _ownerName;
    private String _resturantName;
    private SessionManager _sessionMgr;

    private AppActivationModel() {
    }

    public static AppActivationModel GetInstance() {
        if (Instance == null) {
            Instance = new AppActivationModel();
        }
        return Instance;
    }

    public SessionManager get_SessionManager() {
        return this._sessionMgr;
    }

    public String get_address() {
        return this._address;
    }

    public String get_emailId() {
        return this._emailId;
    }

    public String get_mobileNo() {
        return this._mobileNo;
    }

    public String get_ownerName() {
        return this._ownerName;
    }

    public String get_resturantName() {
        return this._resturantName;
    }

    public void set_SessionManager(Context context) {
        if (this._sessionMgr == null) {
            this._sessionMgr = new SessionManager(context);
        }
    }

    public void set_address(String string2) {
        this._address = string2;
    }

    public void set_emailId(String string2) {
        this._emailId = string2;
    }

    public void set_mobileNo(String string2) {
        this._mobileNo = string2;
    }

    public void set_ownerName(String string2) {
        this._ownerName = string2;
    }

    public void set_resturantName(String string2) {
        this._resturantName = string2;
    }
}

