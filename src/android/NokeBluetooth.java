package com.clearc2.cordova.nokebluetooth;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.noke.nokemobilelibrary.NokeDefines;
import com.noke.nokemobilelibrary.NokeDeviceManagerService;
import com.noke.nokemobilelibrary.NokeDevice;
import com.noke.nokemobilelibrary.NokeMobileError;
import com.noke.nokemobilelibrary.NokeServiceListener;

public class NokeBluetooth extends CordovaPlugin {

	public static final String TAG = "NOKE";
	private NokeDeviceManagerService mNokeService = null;
	private NokeDevice currentNoke;

	CallbackContext onServiceConnected;
	CallbackContext onServiceDisconnected;
	CallbackContext onNokeDiscovered;
	CallbackContext onNokeConnecting;
	CallbackContext onNokeConnected;
	CallbackContext onNokeSyncing;
	CallbackContext onNokeUnlocked;
	CallbackContext onNokeShutdown;
	CallbackContext onNokeDisconnected;
	CallbackContext onDataUploaded;
	CallbackContext onBluetoothStatusChanged;
	CallbackContext onError;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		// initiateNokeService();
	}

    private void initiateNokeService() {
    	Context context = cordova.getActivity().getApplicationContext();
	    Intent nokeServiceIntent = new Intent(context, NokeDeviceManagerService.class);
	    context.bindService(nokeServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder rawBinder) {
			Log.w(TAG, "ON SERVICE CONNECTED");
            //Store reference to service
            mNokeService = ((NokeDeviceManagerService.LocalBinder) rawBinder).getService(2);

            //Register callback listener
            mNokeService.registerNokeListener(mNokeServiceListener);

            //Start bluetooth scanning
            mNokeService.startScanningForNokeDevices();
            mNokeService.setAllowAllDevices(true);

            if (!mNokeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
            mNokeService = null;
            onServiceConnected = null;
			onServiceDisconnected = null;
			onNokeDiscovered = null;
			onNokeConnecting = null;
			onNokeConnected = null;
			onNokeSyncing = null;
			onNokeUnlocked = null;
			onNokeShutdown = null;
			onNokeDisconnected = null;
			onDataUploaded = null;
			onBluetoothStatusChanged = null;
			onError = null;
        }
	};

	private NokeServiceListener mNokeServiceListener = new NokeServiceListener() {
        @Override
        public void onNokeDiscovered(NokeDevice noke) {

        }

        @Override
        public void onNokeConnecting(NokeDevice noke) {

        }

        @Override
        public void onNokeConnected(NokeDevice noke) {

        }

        @Override
        public void onNokeSyncing(NokeDevice noke) {

        }

        @Override
        public void onNokeUnlocked(NokeDevice noke) {

        }

        @Override
        public void onNokeShutdown(NokeDevice noke, Boolean isLocked, Boolean didTimeout) {

        }

        @Override
        public void onNokeDisconnected(NokeDevice noke) {

        }

        @Override
        public void onDataUploaded(int result, String message) {

        }        

        @Override
        public void onBluetoothStatusChanged(int bluetoothStatus) {

        }

        @Override
        public void onError(NokeDevice noke, int error, String message) {
            Log.e(TAG, "NOKE SERVICE ERROR " + error + ": " + message);
        }
    };

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("initService")) {
            initService();
            return true;
        }
        return false;
    };

    private void initService() {
		initiateNokeService();
    };
}
