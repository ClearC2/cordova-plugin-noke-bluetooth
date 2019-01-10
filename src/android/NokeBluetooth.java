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

	CallbackContext onNokeDiscovered = null;
	CallbackContext onNokeConnecting = null;
	CallbackContext onNokeConnected = null;
	CallbackContext onNokeSyncing = null;
	CallbackContext onNokeUnlocked = null;
	CallbackContext onNokeShutdown = null;
	CallbackContext onNokeDisconnected = null;
	CallbackContext onDataUploaded = null;
	CallbackContext onBluetoothStatusChanged = null;
	CallbackContext onError = null;

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
                Log.d(TAG, 'Noke Service Initialized.')
            } else {
            	Log.d(TAG, 'Noke Service Initialization Failed.')
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
        	if (serviceDisconnected != null) {
        		Log.d(TAG, 'Noke Service Disconnected.')
        	}
            mNokeService = null;
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
			if (onNokeDiscovered != null) {
        		onNokeDiscovered(noke);
        	}
        }

        @Override
        public void onNokeConnecting(NokeDevice noke) {
			if (onNokeConnecting != null) {
        		onNokeConnecting(noke);
        	}
        }

        @Override
        public void onNokeConnected(NokeDevice noke) {
			if (onNokeConnected != null) {
        		onNokeConnected(noke);
        	}
        }

        @Override
        public void onNokeSyncing(NokeDevice noke) {
			if (onNokeSyncing != null) {
        		onNokeSyncing(noke);
        	}
        }

        @Override
        public void onNokeUnlocked(NokeDevice noke) {
			if (onNokeUnlocked != null) {
        		onNokeUnlocked(noke);
        	}
        }

        @Override
        public void onNokeShutdown(NokeDevice noke, Boolean isLocked, Boolean didTimeout) {
			if (onNokeShutdown != null) {
        		onNokeShutdown(noke, isLocked, didTimeout);
        	}
        }

        @Override
        public void onNokeDisconnected(NokeDevice noke) {
			if (onNokeDisconnected != null) {
        		onNokeDisconnected(noke);
        	}
        }

        @Override
        public void onDataUploaded(int result, String message) {
			if (onDataUploaded != null) {
        		onDataUploaded(result, message);
        	}
        }

        @Override
        public void onBluetoothStatusChanged(int bluetoothStatus) {
			if (onBluetoothStatusChanged != null) {
        		onBluetoothStatusChanged(bluetoothStatus);
        	}
        }

        @Override
        public void onError(NokeDevice noke, int error, String message) {
			if (onError != null) {
        		onError(noke, error, message);
        	}
        }
    };

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("initService")) {
            initService();
            return true;
        }

        if (action.equals("bindOnNokeDiscovered")) {
            bindOnNokeDiscovered(callbackContext);
            return true;
        }

        if (action.equals("bindOnNokeConnecting")) {
            bindOnNokeConnecting(callbackContext);
            return true;
        }

        if (action.equals("bindOnNokeConnected")) {
            bindOnNokeConnected(callbackContext);
            return true;
        }

        if (action.equals("bindOnNokeSyncing")) {
            bindOnNokeSyncing(callbackContext);
            return true;
        }

        if (action.equals("bindOnNokeUnlocked")) {
            bindOnNokeUnlocked(callbackContext);
            return true;
        }

        if (action.equals("bindOnNokeShutdown")) {
            bindOnNokeShutdown(callbackContext);
            return true;
        }

        if (action.equals("bindOnNokeDisconnected")) {
            bindOnNokeDisconnected(callbackContext);
            return true;
        }

        if (action.equals("bindOnDataUploaded")) {
            bindOnDataUploaded(callbackContext);
            return true;
        }

        if (action.equals("bindOnBluetoothStatusChanged")) {
            bindOnBluetoothStatusChanged(callbackContext);
            return true;
        }

        if (action.equals("bindOnNokeError")) {
            bindOnNokeError(callbackContext);
            return true;
        }

        return false;
    };

    private void initService() {
		initiateNokeService();
    };

    private void bindOnNokeDiscovered(final CallbackContext callbackContext) {
		onNokeDiscovered = callbackContext;
    };

    private void bindOnNokeConnecting(final CallbackContext callbackContext) {
		onNokeConnecting = callbackContext;
    };

    private void bindOnNokeConnected(final CallbackContext callbackContext) {
		onNokeConnected = callbackContext;
    };

    private void bindOnNokeSyncing(final CallbackContext callbackContext) {
		onNokeSyncing = callbackContext;
    };

    private void bindOnNokeUnlocked(final CallbackContext callbackContext) {
		onNokeUnlocked = callbackContext;
    };

    private void bindOnNokeShutdown(final CallbackContext callbackContext) {
		onNokeShutdown = callbackContext;
    };

    private void bindOnNokeDisconnected(final CallbackContext callbackContext) {
		onNokeDisconnected = callbackContext;
    };

    private void bindOnDataUploaded(final CallbackContext callbackContext) {
		onDataUploaded = callbackContext;
    };

    private void bindOnBluetoothStatusChanged(final CallbackContext callbackContext) {
		onBluetoothStatusChanged = callbackContext;
    };

    private void bindOnNokeError(final CallbackContext callbackContext) {
		onError = callbackContext;
    };
}
