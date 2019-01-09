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

	public static final String TAG = "Noke";
	private NokeDeviceManagerService mNokeService = null;
	private NokeDevice currentNoke;

	CallbackContext onServiceConnected;
	CallbackContext onServiceConnectFailure;
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

            mNokeService = ((NokeDeviceManagerService.LocalBinder) rawBinder).getService(2);
            mNokeService.registerNokeListener(mNokeServiceListener);
            mNokeService.startScanningForNokeDevices();
            mNokeService.setAllowAllDevices(true);

            if (!mNokeService.initialize()) {
                if (!onServiceConnected != null) {
            		onServiceConnected();
            	}
            } else {
            	if (!onServiceConnectFailure != null) {
            		onServiceConnectFailure();
            	}
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
        	if (!onServiceDisconnected != null) {
        		onServiceDisconnected();
        	}
            mNokeService = null;
            onServiceConnected = null;
            onServiceConnectFailure = null;
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
			if (!onNokeDiscovered != null) {
        		onNokeDiscovered(noke);
        	}
        }

        @Override
        public void onNokeConnecting(NokeDevice noke) {
			if (!onNokeConnecting != null) {
        		onNokeConnecting(noke);
        	}
        }

        @Override
        public void onNokeConnected(NokeDevice noke) {
			if (!onNokeConnected != null) {
        		onNokeConnected(noke);
        	}
        }

        @Override
        public void onNokeSyncing(NokeDevice noke) {
			if (!onNokeSyncing != null) {
        		onNokeSyncing(noke);
        	}
        }

        @Override
        public void onNokeUnlocked(NokeDevice noke) {
			if (!onNokeUnlocked != null) {
        		onNokeUnlocked(noke);
        	}
        }

        @Override
        public void onNokeShutdown(NokeDevice noke, Boolean isLocked, Boolean didTimeout) {
			if (!onNokeShutdown != null) {
        		onNokeShutdown(noke, isLocked, didTimeout);
        	}
        }

        @Override
        public void onNokeDisconnected(NokeDevice noke) {
			if (!onNokeDisconnected != null) {
        		onNokeDisconnected(noke);
        	}
        }

        @Override
        public void onDataUploaded(int result, String message) {
			if (!onDataUploaded != null) {
        		onDataUploaded(result, message);
        	}
        }

        @Override
        public void onBluetoothStatusChanged(int bluetoothStatus) {
			if (!onBluetoothStatusChanged != null) {
        		onBluetoothStatusChanged(bluetoothStatus);
        	}
        }

        @Override
        public void onError(NokeDevice noke, int error, String message) {
			if (!onError != null) {
        		onError(noke, error, message);
        	}
        }
    };

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("initService")) {
            initService(args);
            return true;
        }
        return false;
    };

    private void initService(JSONArray args) {
		initiateNokeService();
		onServiceConnected = args[0];
		onServiceConnectFailure = args[0];
		onServiceDisconnected = args[0];
		onNokeDiscovered = args[0];
		onNokeConnecting = args[0];
		onNokeConnected = args[0];
		onNokeSyncing = args[0];
		onNokeUnlocked = args[0];
		onNokeShutdown = args[0];
		onNokeDisconnected = args[0];
		onDataUploaded = args[0];
		onBluetoothStatusChanged = args[0];
		onError = args[0];
    };
}
