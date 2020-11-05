/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.os.Bundle
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.text.Editable
 *  android.util.Log
 *  android.util.Patterns
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.ScrollView
 *  android.widget.TextView
 *  com.google.android.gms.common.GoogleApiAvailability
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.UUID
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package com.neoAntara.newrestaurant;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.neoAntara.newrestaurant.DBHelper;
import com.neoAntara.newrestaurant.Home;
import com.neoAntara.newrestaurant.Models.AppActivationModel;
import com.neoAntara.newrestaurant.RegistrationIntentService;
import com.neoAntara.newrestaurant.SessionManager;
import com.neoAntara.newrestaurant.UtilityHelper;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity
extends Activity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String DeviceID = null;
    DBHelper ObjDBHelp;
    private EditText activationKey = null;
    private EditText address = null;
    private Button btnActivate = null;
    private Button btnGoBack = null;
    private TextView date = null;
    private EditText emailId = null;
    private LinearLayout layoutActivateApp = null;
    private LinearLayout layoutLogin = null;
    private ScrollView loginWindow = null;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private EditText ownerMobileNo = null;
    private EditText ownerName = null;
    private EditText password = null;
    private EditText restaurantName = null;
    private EditText username = null;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int n = googleApiAvailability.isGooglePlayServicesAvailable((Context)this);
        if (n == 0) return true;
        if (googleApiAvailability.isUserResolvableError(n)) {
            googleApiAvailability.getErrorDialog((Activity)this, n, 9000).show();
            do {
                return false;
                break;
            } while (true);
        }
        Log.i((String)"ContentValues", (String)"This device is not supported.");
        this.finish();
        return false;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return this.getSharedPreferences(MainActivity.class.getSimpleName(), 0);
    }

    private boolean isValidEmailId(String string2) {
        return Patterns.EMAIL_ADDRESS.matcher((CharSequence)string2).matches();
    }

    public void ActivateApp(View view) {
        try {
            this.ObjDBHelp.sendEmail();
            String string2 = UUID.randomUUID().toString();
            this.ObjDBHelp.InsertIntoRegisterApp(this.DeviceID, string2);
            this.ObjDBHelp.ActivateApp(string2);
            this.date.setVisibility(0);
            this.layoutActivateApp.setVisibility(4);
            this.loginWindow.setVisibility(0);
            return;
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void GetActivation(View view) {
        try {
            if (this.restaurantName.getText().toString().matches("") || this.restaurantName.getText().equals(null)) {
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Restaurant Name");
                this.restaurantName.setText((CharSequence)"");
                return;
            }
            if (this.ownerName.getText().toString().matches("") || this.ownerName.getText().equals(null)) {
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Owner Name");
                this.ownerName.setText((CharSequence)"");
                return;
            }
            if (this.emailId.getText().toString().matches("") || this.emailId.getText().equals(null)) {
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Email Id");
                return;
            }
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
        if (this.address.getText().toString().matches("") || this.address.getText().equals(null)) {
            UtilityHelper.showToastMessage(this.getApplicationContext(), "Enter Address");
            this.address.setText((CharSequence)"");
            return;
        }
        if (!this.isValidEmailId(this.emailId.getText().toString())) {
            UtilityHelper.showToastMessage(this.getApplicationContext(), "Invalid Email Id");
            return;
        }
        AppActivationModel appActivationModel = UtilityHelper.InitializeAppActivationModel();
        appActivationModel.set_resturantName(this.restaurantName.getText().toString());
        appActivationModel.set_ownerName(this.ownerName.getText().toString());
        appActivationModel.set_mobileNo(this.ownerMobileNo.getText().toString());
        appActivationModel.set_address(this.address.getText().toString());
        appActivationModel.set_emailId(this.emailId.getText().toString());
        this.ObjDBHelp.getActivationKey(appActivationModel.get_resturantName(), appActivationModel.get_ownerName(), appActivationModel.get_mobileNo(), appActivationModel.get_address(), appActivationModel.get_emailId());
        UtilityHelper.showToastMessage(this.getApplicationContext(), "Activation KEY Sent...Check Mail");
    }

    public String GetToken() {
        return this.getApplicationContext().getSharedPreferences(MainActivity.class.getSimpleName(), 0).getString("Token", "");
    }

    public void GoBack(View view) {
        try {
            this.layoutActivateApp.setVisibility(0);
            this.loginWindow.setVisibility(4);
            this.date.setVisibility(4);
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public void exit(View view) {
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void login(View view) {
        try {
            Cursor cursor = this.ObjDBHelp.loginDetails(this.username.getText().toString(), this.password.getText().toString());
            if (cursor == null) {
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Your App is Deactivated...Contact vendor for Activation");
            }
            int n = cursor.getCount();
            cursor.moveToFirst();
            if (n > 0) {
                String string2 = cursor.getString(0);
                if (cursor.getString(1).equals((Object)"yes")) {
                    cursor.close();
                    UtilityHelper.showToastMessage(this.getApplicationContext(), "Loading..." + string2);
                    this.ObjDBHelp.getUserRoles(string2);
                    AppActivationModel.GetInstance().get_SessionManager().createLoginSession(string2, string2);
                    this.startActivityForResult(new Intent((Context)this, Home.class), 0);
                } else {
                    UtilityHelper.showToastMessage(this.getApplicationContext(), "User is not Active.");
                    this.password.setText((CharSequence)"");
                    this.username.setText((CharSequence)"");
                }
            } else {
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Invalid Username or Password");
                this.password.setText((CharSequence)"");
                this.username.setText((CharSequence)"");
            }
            cursor.close();
            return;
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131296290);
        if (this.checkPlayServices()) {
            this.startService(new Intent((Context)this, RegistrationIntentService.class));
        }
        try {
            UtilityHelper.InitializeAppActivationModel();
            UtilityHelper.InitializeRolesModel();
            AppActivationModel.GetInstance().set_SessionManager(this.getApplicationContext());
            this.DeviceID = Settings.Secure.getString((ContentResolver)this.getContentResolver(), (String)"android_id");
            this.ObjDBHelp = new DBHelper((Context)this, this.DeviceID);
            this.ObjDBHelp.InsertIntoTableMaster();
            String string2 = this.ObjDBHelp.getBusinessDate();
            this.date = (TextView)this.findViewById(2131165393);
            this.date.setText((CharSequence)string2);
            this.btnGoBack = (Button)this.findViewById(2131165217);
            this.btnGoBack.setBackgroundResource(0);
            this.btnGoBack.setPaintFlags(8 | this.btnGoBack.getPaintFlags());
            this.username = (EditText)this.findViewById(2131165267);
            this.password = (EditText)this.findViewById(2131165268);
            this.layoutActivateApp = (LinearLayout)this.findViewById(2131165203);
            this.loginWindow = (ScrollView)this.findViewById(2131165186);
            this.username.setTextColor(-16777216);
            this.password.setTextColor(-16777216);
            this.username.setHintTextColor(-7829368);
            this.password.setHintTextColor(-7829368);
            if (this.ObjDBHelp.CheckActivation(this.DeviceID) > 0) {
                if (AppActivationModel.GetInstance().get_SessionManager().isLoggedIn()) {
                    Intent intent = new Intent(this.getApplicationContext(), Home.class);
                    intent.addFlags(67108864);
                    intent.setFlags(268435456);
                    this.getApplicationContext().startActivity(intent);
                    return;
                }
                this.date.setVisibility(0);
                this.loginWindow.setVisibility(0);
                this.layoutActivateApp.setVisibility(4);
                return;
            }
            if (this.ObjDBHelp.isAppDeactive()) {
                this.date.setVisibility(0);
                this.loginWindow.setVisibility(0);
                this.layoutActivateApp.setVisibility(4);
                UtilityHelper.showToastMessage(this.getApplicationContext(), "Your App is Deactivated...Contact vendor for Activation");
                return;
            }
            this.date.setVisibility(4);
            this.loginWindow.setVisibility(4);
            this.layoutActivateApp.setVisibility(0);
            return;
        }
        catch (SQLException sQLException) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), (Exception)((Object)sQLException));
            return;
        }
        catch (Exception exception) {
            UtilityHelper.GenericCatchBlock(this.getApplicationContext(), exception);
            return;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem.getItemId();
        return super.onOptionsItemSelected(menuItem);
    }
}

