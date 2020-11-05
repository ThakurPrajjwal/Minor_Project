/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.bluetooth.BluetoothDevice
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Set
 */
package com.neoAntara.newrestaurant.Print;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.zj.btsdk.BluetoothService;
import java.util.Set;

public class DeviceListActivity
extends Activity {
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener(){

        public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
            DeviceListActivity.this.mService.cancelDiscovery();
            String string2 = ((TextView)view).getText().toString();
            String string3 = string2.substring(-17 + string2.length());
            Intent intent = new Intent();
            intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, string3);
            Log.d((String)"\ufffd\ufffd\ufffd\u04f5\ufffd\u05b7", (String)string3);
            DeviceListActivity.this.setResult(-1, intent);
            DeviceListActivity.this.finish();
        }
    };
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context context, Intent intent) {
            String string2 = intent.getAction();
            if ("android.bluetooth.device.action.FOUND".equals((Object)string2)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice.getBondState() == 12) return;
                {
                    DeviceListActivity.this.mNewDevicesArrayAdapter.add((Object)(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress()));
                    return;
                }
            } else {
                if (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals((Object)string2)) return;
                {
                    DeviceListActivity.this.setProgressBarIndeterminateVisibility(false);
                    DeviceListActivity.this.setTitle((CharSequence)"Select Device");
                    if (DeviceListActivity.this.mNewDevicesArrayAdapter.getCount() != 0) return;
                    {
                        DeviceListActivity.this.mNewDevicesArrayAdapter.add((Object)"None found");
                        return;
                    }
                }
            }
        }
    };
    BluetoothService mService = null;

    private void doDiscovery() {
        this.setProgressBarIndeterminateVisibility(true);
        this.setTitle((CharSequence)"Scanning");
        this.findViewById(2131165400).setVisibility(0);
        if (this.mService.isDiscovering()) {
            this.mService.cancelDiscovery();
        }
        this.mService.startDiscovery();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(5);
        this.setContentView(2131296297);
        this.setResult(0);
        ((Button)this.findViewById(2131165229)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                DeviceListActivity.this.doDiscovery();
                view.setVisibility(8);
            }
        });
        this.mPairedDevicesArrayAdapter = new ArrayAdapter((Context)this, 2131296298);
        this.mNewDevicesArrayAdapter = new ArrayAdapter((Context)this, 2131296298);
        ListView listView = (ListView)this.findViewById(2131165327);
        listView.setAdapter(this.mPairedDevicesArrayAdapter);
        listView.setOnItemClickListener(this.mDeviceClickListener);
        ListView listView2 = (ListView)this.findViewById(2131165319);
        listView2.setAdapter(this.mNewDevicesArrayAdapter);
        listView2.setOnItemClickListener(this.mDeviceClickListener);
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
        this.registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.registerReceiver(this.mReceiver, intentFilter2);
        this.mService = new BluetoothService((Context)this, null);
        Set<BluetoothDevice> set = this.mService.getPairedDev();
        if (set.size() > 0) {
            this.findViewById(2131165401).setVisibility(0);
            for (BluetoothDevice bluetoothDevice : set) {
                this.mPairedDevicesArrayAdapter.add((Object)(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress()));
            }
        } else {
            this.mPairedDevicesArrayAdapter.add((Object)"None paired");
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mService != null) {
            this.mService.cancelDiscovery();
        }
        this.mService = null;
        this.unregisterReceiver(this.mReceiver);
    }

}

