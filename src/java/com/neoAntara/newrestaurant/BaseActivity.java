/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.support.annotation.Nullable
 *  java.lang.Class
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.neoAntara.newrestaurant.MainActivity;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.UtilityHelper;

public class BaseActivity
extends Activity {
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (!UtilityHelper.isUserLoggedIn()) {
            StringBuilder stringBuilder = new StringBuilder().append("Activity : Logged in User(If) : ");
            RolesModel.GetInstance();
            UtilityHelper.LogMessage(stringBuilder.append(RolesModel.get_loggedInUser()).toString());
            this.startActivityForResult(new Intent((Context)this, MainActivity.class), 0);
            this.finish();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder().append("Activity : Logged in User(else) : ");
        RolesModel.GetInstance();
        UtilityHelper.LogMessage(stringBuilder.append(RolesModel.get_loggedInUser()).toString());
    }
}

