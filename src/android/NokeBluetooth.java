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

	CallbackContext onNokeDiscoveredCallback = null;
	CallbackContext onNokeConnectingCallback = null;
	CallbackContext onNokeConnectedCallback = null;
	CallbackContext onNokeSyncingCallback = null;
	CallbackContext onNokeUnlockedCallback = null;
	CallbackContext onNokeShutdownCallback = null;
	CallbackContext onNokeDisconnectedCallback = null;
	CallbackContext onDataUploadedCallback = null;
	CallbackContext onBluetoothStatusChangedCallback = null;
	CallbackContext onErrorCallback = null;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
	}

  private void initiateNokeService() {
  	Context context = cordova.getActivity().getApplicationContext();
    Intent nokeServiceIntent = new Intent(context, NokeDeviceManagerService.class);
    context.bindService(nokeServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder rawBinder) {

      mNokeService = ((NokeDeviceManagerService.LocalBinder) rawBinder).getService(1);
      mNokeService.registerNokeListener(mNokeServiceListener);
      mNokeService.startScanningForNokeDevices();
      mNokeService.setAllowAllDevices(true);

      if (mNokeService.initialize()) {
        Log.d(TAG, "Noke Service Initialized.");
      } else {
      	Log.d(TAG, "Noke Service Initialization Failed.");
      }
    }

    public void onServiceDisconnected(ComponentName classname) {
    	Log.d(TAG, "Noke Service Disconnected.");
      mNokeService = null;
    }
	};

	private NokeServiceListener mNokeServiceListener = new NokeServiceListener() {

    @Override
    public void onNokeDiscovered(NokeDevice noke) {
			mNokeService.connectToNoke(noke);
			if (onNokeDiscoveredCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, true);
				result.setKeepCallback(true);
				onNokeDiscoveredCallback.sendPluginResult(result);
			}
    }

    @Override
    public void onNokeConnecting(NokeDevice noke) {
			if (onNokeConnectingCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, true);
				result.setKeepCallback(true);
				onNokeConnectingCallback.sendPluginResult(result);
    	}
    }

    @Override
    public void onNokeConnected(NokeDevice noke) {
			if (onNokeConnectingCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, false);
				result.setKeepCallback(true);
				onNokeConnectingCallback.sendPluginResult(result);
    	}
			if (onNokeConnectedCallback != null) {
				JSONObject info = new JSONObject();
				try {
					info.put("mac", noke.getMac());
					info.put("session", noke.getSession());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				PluginResult result = new PluginResult(PluginResult.Status.OK, info);
				result.setKeepCallback(true);
				onNokeConnectedCallback.sendPluginResult(result);
				noke.offlineUnlock();
    	}
    }

    @Override
    public void onNokeSyncing(NokeDevice noke) {
			if (onNokeSyncingCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, "ON SYNC PAYLOAD");
				result.setKeepCallback(true);
				onNokeSyncingCallback.sendPluginResult(result);
    	}
    }

    @Override
    public void onNokeUnlocked(NokeDevice noke) {
			if (onNokeUnlockedCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, "ON UNLOCK PAYLOAD");
				result.setKeepCallback(true);
				onNokeUnlockedCallback.sendPluginResult(result);
    	}
    }

    @Override
    public void onNokeShutdown(NokeDevice noke, Boolean isLocked, Boolean didTimeout) {
			if (onNokeShutdownCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, "ON SHUTDOWN PAYLOAD");
				result.setKeepCallback(true);
				onNokeShutdownCallback.sendPluginResult(result);
    	}
    }

    @Override
    public void onNokeDisconnected(NokeDevice noke) {
			if (onNokeDisconnectedCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, "ON DISCONNECT PAYLOAD");
				result.setKeepCallback(true);
				onNokeDisconnectedCallback.sendPluginResult(result);
    	}
    }

    @Override
    public void onDataUploaded(int result, String message) {
			if (onDataUploadedCallback != null) {
				PluginResult presult = new PluginResult(PluginResult.Status.OK, "ON DATA UPLOAD PAYLOAD");
				presult.setKeepCallback(true);
				onDataUploadedCallback.sendPluginResult(presult);
    	}
    }

    @Override
    public void onBluetoothStatusChanged(int bluetoothStatus) {
			if (onBluetoothStatusChangedCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK, "ON BLUETOOTH STATUS CHANGE PAYLOAD");
				result.setKeepCallback(true);
				onBluetoothStatusChangedCallback.sendPluginResult(result);
    	}
    }

    @Override
    public void onError(NokeDevice noke, int error, String message) {
			if (onErrorCallback != null) {
				JSONObject info = new JSONObject();
				try {
					info.put("code", error);
					info.put("message", message);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				PluginResult result = new PluginResult(PluginResult.Status.OK, info);
				result.setKeepCallback(true);
				onErrorCallback.sendPluginResult(result);
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
		onNokeDiscoveredCallback = callbackContext;
  };

  private void bindOnNokeConnecting(final CallbackContext callbackContext) {
		onNokeConnectingCallback = callbackContext;
  };

  private void bindOnNokeConnected(final CallbackContext callbackContext) {
		onNokeConnectedCallback = callbackContext;
  };

  private void bindOnNokeSyncing(final CallbackContext callbackContext) {
		onNokeSyncingCallback = callbackContext;
  };

  private void bindOnNokeUnlocked(final CallbackContext callbackContext) {
		onNokeUnlockedCallback = callbackContext;
  };

  private void bindOnNokeShutdown(final CallbackContext callbackContext) {
		onNokeShutdownCallback = callbackContext;
  };

  private void bindOnNokeDisconnected(final CallbackContext callbackContext) {
		onNokeDisconnectedCallback = callbackContext;
  };

  private void bindOnDataUploaded(final CallbackContext callbackContext) {
		onDataUploadedCallback = callbackContext;
  };

  private void bindOnBluetoothStatusChanged(final CallbackContext callbackContext) {
		onBluetoothStatusChangedCallback = callbackContext;
  };

  private void bindOnNokeError(final CallbackContext callbackContext) {
		onErrorCallback = callbackContext;
  };
}
