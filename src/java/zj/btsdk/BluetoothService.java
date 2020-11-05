/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothServerSocket
 *  android.bluetooth.BluetoothSocket
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.Iterator
 *  java.util.Set
 *  java.util.UUID
 */
package com.zj.btsdk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class BluetoothService {
    private static final boolean D = true;
    public static final int MESSAGE_CONNECTION_LOST = 5;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_UNABLE_CONNECT = 6;
    public static final int MESSAGE_WRITE = 3;
    private static final UUID MY_UUID = UUID.fromString((String)"00001101-0000-1000-8000-00805F9B34FB");
    private static final String NAME = "BTPrinter";
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    private static final String TAG = "BluetoothService";
    private AcceptThread mAcceptThread;
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private final Handler mHandler;
    private int mState = 0;

    public BluetoothService(Context context, Handler handler) {
        this.mHandler = handler;
    }

    static /* synthetic */ void access$4(BluetoothService bluetoothService, ConnectThread connectThread) {
        bluetoothService.mConnectThread = connectThread;
    }

    private void connectionFailed() {
        this.setState(1);
        Message message = this.mHandler.obtainMessage(6);
        this.mHandler.sendMessage(message);
    }

    private void connectionLost() {
        Message message = this.mHandler.obtainMessage(5);
        this.mHandler.sendMessage(message);
    }

    private void setState(int n) {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            this.mState = n;
            this.mHandler.obtainMessage(1, n, -1).sendToTarget();
            return;
        }
    }

    public boolean cancelDiscovery() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            boolean bl = this.mAdapter.cancelDiscovery();
            return bl;
        }
    }

    public void connect(BluetoothDevice bluetoothDevice) {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            Log.d((String)TAG, (String)("connect to: " + (Object)bluetoothDevice));
            if (this.mState == 2 && this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            this.mConnectThread = new ConnectThread(bluetoothDevice);
            this.mConnectThread.start();
            this.setState(2);
            return;
        }
    }

    public void connected(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice) {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            Log.d((String)TAG, (String)"connected");
            if (this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            if (this.mAcceptThread != null) {
                this.mAcceptThread.cancel();
                this.mAcceptThread = null;
            }
            this.mConnectedThread = new ConnectedThread(bluetoothSocket);
            this.mConnectedThread.start();
            Message message = this.mHandler.obtainMessage(4);
            this.mHandler.sendMessage(message);
            this.setState(3);
            return;
        }
    }

    public BluetoothDevice getDevByMac(String string2) {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            BluetoothDevice bluetoothDevice = this.mAdapter.getRemoteDevice(string2);
            return bluetoothDevice;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public BluetoothDevice getDevByName(String string2) {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            BluetoothDevice bluetoothDevice;
            int n;
            Set<BluetoothDevice> set = this.getPairedDev();
            int n2 = set.size();
            BluetoothDevice bluetoothDevice2 = null;
            if (n2 <= 0) return bluetoothDevice2;
            Iterator iterator = set.iterator();
            do {
                boolean bl = iterator.hasNext();
                bluetoothDevice2 = null;
                if (!bl) return bluetoothDevice2;
            } while ((n = (bluetoothDevice = (BluetoothDevice)iterator.next()).getName().indexOf(string2)) == -1);
            return bluetoothDevice;
        }
    }

    public Set<BluetoothDevice> getPairedDev() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            Set set = this.mAdapter.getBondedDevices();
            return set;
        }
    }

    public int getState() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            int n = this.mState;
            return n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isAvailable() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            BluetoothAdapter bluetoothAdapter = this.mAdapter;
            if (bluetoothAdapter != null) return true;
            return false;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isBTopen() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            boolean bl = this.mAdapter.isEnabled();
            if (bl) return true;
            return false;
        }
    }

    public boolean isDiscovering() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            boolean bl = this.mAdapter.isDiscovering();
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendMessage(String string2, String string3) {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            int n = string2.length();
            if (n > 0) {
                byte[] arrby;
                try {
                    byte[] arrby2;
                    arrby = arrby2 = string2.getBytes(string3);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    byte[] arrby3 = string2.getBytes();
                    arrby = arrby3;
                }
                this.write(arrby);
                byte[] arrby4 = new byte[3];
                arrby4[0] = 10;
                arrby4[1] = 13;
                this.write(arrby4);
            }
            return;
        }
    }

    public void start() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            Log.d((String)TAG, (String)"start");
            if (this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            if (this.mAcceptThread == null) {
                this.mAcceptThread = new AcceptThread();
                this.mAcceptThread.start();
            }
            this.setState(1);
            return;
        }
    }

    public boolean startDiscovery() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            boolean bl = this.mAdapter.startDiscovery();
            return bl;
        }
    }

    public void stop() {
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            Log.d((String)TAG, (String)"stop");
            this.setState(0);
            if (this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            if (this.mAcceptThread != null) {
                this.mAcceptThread.cancel();
                this.mAcceptThread = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(byte[] arrby) {
        ConnectedThread connectedThread;
        BluetoothService bluetoothService = this;
        synchronized (bluetoothService) {
            if (this.mState != 3) {
                return;
            }
            connectedThread = this.mConnectedThread;
        }
        connectedThread.write(arrby);
    }

    private class AcceptThread
    extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public AcceptThread() {
            BluetoothServerSocket bluetoothServerSocket;
            try {
                BluetoothServerSocket bluetoothServerSocket2;
                bluetoothServerSocket = bluetoothServerSocket2 = BluetoothService.this.mAdapter.listenUsingRfcommWithServiceRecord(BluetoothService.NAME, MY_UUID);
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"listen() failed", (Throwable)iOException);
                bluetoothServerSocket = null;
            }
            this.mmServerSocket = bluetoothServerSocket;
        }

        public void cancel() {
            Log.d((String)BluetoothService.TAG, (String)("cancel " + (Object)((Object)this)));
            try {
                this.mmServerSocket.close();
                return;
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"close() of server failed", (Throwable)iOException);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            Log.d((String)BluetoothService.TAG, (String)("BEGIN mAcceptThread" + (Object)((Object)this)));
            this.setName("AcceptThread");
            while (BluetoothService.this.mState != 3) {
                BluetoothSocket bluetoothSocket;
                BluetoothService bluetoothService;
                Log.d((String)"AcceptThread\u7ebf\u7a0b\u8fd0\u884c", (String)"\u6b63\u5728\u8fd0\u884c......");
                try {
                    BluetoothService bluetoothService2;
                    bluetoothSocket = this.mmServerSocket.accept();
                    if (bluetoothSocket == null) continue;
                    bluetoothService = bluetoothService2 = BluetoothService.this;
                }
                catch (IOException iOException) {
                    Log.e((String)BluetoothService.TAG, (String)"accept() failed", (Throwable)iOException);
                    break;
                }
                synchronized (bluetoothService) {
                    switch (BluetoothService.this.mState) {
                        case 1: 
                        case 2: {
                            BluetoothService.this.connected(bluetoothSocket, bluetoothSocket.getRemoteDevice());
                            break;
                        }
                        case 0: 
                        case 3: {
                            try {
                                bluetoothSocket.close();
                                break;
                            }
                            catch (IOException iOException) {
                                Log.e((String)BluetoothService.TAG, (String)"Could not close unwanted socket", (Throwable)iOException);
                                break;
                            }
                        }
                    }
                }
            }
            Log.i((String)BluetoothService.TAG, (String)"END mAcceptThread");
        }
    }

    private class ConnectThread
    extends Thread {
        private final BluetoothDevice mmDevice;
        private final BluetoothSocket mmSocket;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ConnectThread(BluetoothDevice bluetoothDevice) {
            BluetoothSocket bluetoothSocket;
            this.mmDevice = bluetoothDevice;
            try {
                BluetoothSocket bluetoothSocket2;
                bluetoothSocket = bluetoothSocket2 = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"create() failed", (Throwable)iOException);
                bluetoothSocket = null;
            }
            this.mmSocket = bluetoothSocket;
        }

        public void cancel() {
            try {
                this.mmSocket.close();
                return;
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"close() of connect socket failed", (Throwable)iOException);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            BluetoothService bluetoothService;
            Log.i((String)BluetoothService.TAG, (String)"BEGIN mConnectThread");
            this.setName("ConnectThread");
            BluetoothService.this.mAdapter.cancelDiscovery();
            try {
                BluetoothService bluetoothService2;
                this.mmSocket.connect();
                bluetoothService = bluetoothService2 = BluetoothService.this;
            }
            catch (IOException iOException) {
                BluetoothService.this.connectionFailed();
                try {
                    this.mmSocket.close();
                }
                catch (IOException iOException2) {
                    Log.e((String)BluetoothService.TAG, (String)"unable to close() socket during connection failure", (Throwable)iOException2);
                }
                BluetoothService.this.start();
                return;
            }
            synchronized (bluetoothService) {
                BluetoothService.access$4(BluetoothService.this, null);
            }
            BluetoothService.this.connected(this.mmSocket, this.mmDevice);
        }
    }

    private class ConnectedThread
    extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ConnectedThread(BluetoothSocket bluetoothSocket) {
            OutputStream outputStream;
            Log.d((String)BluetoothService.TAG, (String)"create ConnectedThread");
            this.mmSocket = bluetoothSocket;
            InputStream inputStream = null;
            try {
                OutputStream outputStream2;
                inputStream = bluetoothSocket.getInputStream();
                outputStream = outputStream2 = bluetoothSocket.getOutputStream();
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"temp sockets not created", (Throwable)iOException);
                outputStream = null;
            }
            this.mmInStream = inputStream;
            this.mmOutStream = outputStream;
        }

        public void cancel() {
            try {
                this.mmSocket.close();
                return;
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"close() of connect socket failed", (Throwable)iOException);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            Log.d((String)"ConnectedThread\u7ebf\u7a0b\u8fd0\u884c", (String)"\u6b63\u5728\u8fd0\u884c......");
            Log.i((String)BluetoothService.TAG, (String)"BEGIN mConnectedThread");
            try {
                byte[] arrby;
                int n;
                while ((n = this.mmInStream.read(arrby = new byte[256])) > 0) {
                    BluetoothService.this.mHandler.obtainMessage(2, n, -1, (Object)arrby).sendToTarget();
                }
                Log.e((String)BluetoothService.TAG, (String)"disconnected");
                BluetoothService.this.connectionLost();
                if (BluetoothService.this.mState == 0) return;
                {
                    Log.e((String)BluetoothService.TAG, (String)"disconnected");
                    BluetoothService.this.start();
                    return;
                }
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"disconnected", (Throwable)iOException);
                BluetoothService.this.connectionLost();
                if (BluetoothService.this.mState == 0) return;
                {
                    BluetoothService.this.start();
                }
                return;
            }
        }

        public void write(byte[] arrby) {
            try {
                this.mmOutStream.write(arrby);
                BluetoothService.this.mHandler.obtainMessage(3, -1, -1, (Object)arrby).sendToTarget();
                return;
            }
            catch (IOException iOException) {
                Log.e((String)BluetoothService.TAG, (String)"Exception during write", (Throwable)iOException);
                return;
            }
        }
    }

}

