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
  // private JSONObject NokeDevices = new JSONObject();
  private NokeDevice currentLock = null;

  CallbackContext stopScanningCallback = null;
  CallbackContext onNokeInitCallback = null;
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
      mNokeService = ((NokeDeviceManagerService.LocalBinder) rawBinder).getService(0);
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
      mNokeService.stopScanning();
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
          info.put("name", noke.getName());
          info.put("serial", noke.getSerial());
          info.put("version", noke.getVersion());
          info.put("battery", noke.getBattery());
          info.put("trackingkey", noke.getTrackingKey());
          info.put("connectionstate", noke.getConnectionState());
        } catch (JSONException e) {
          e.printStackTrace();
        }
        PluginResult result = new PluginResult(PluginResult.Status.OK, info);
        result.setKeepCallback(true);
        onNokeConnectedCallback.sendPluginResult(result);
      }
      // try {
      // 	NokeDevices.put(noke.getMac(), noke);
      // } catch (JSONException e) {
      // 	e.printStackTrace();
      // }
      currentLock = noke;
    }

    @Override
    public void onNokeSyncing(NokeDevice noke) {
      if (onNokeSyncingCallback != null) {
        PluginResult result = new PluginResult(PluginResult.Status.OK);
        result.setKeepCallback(true);
        onNokeSyncingCallback.sendPluginResult(result);
      }
    }

    @Override
    public void onNokeUnlocked(NokeDevice noke) {
      if (onNokeUnlockedCallback != null) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, noke.getLockState());
        result.setKeepCallback(true);
        onNokeUnlockedCallback.sendPluginResult(result);
      }
    }

    @Override
    public void onNokeShutdown(NokeDevice noke, Boolean isLocked, Boolean didTimeout) {
      if (onNokeShutdownCallback != null) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, noke.getMac());
        result.setKeepCallback(true);
        onNokeShutdownCallback.sendPluginResult(result);
      }
    }

    @Override
    public void onNokeDisconnected(NokeDevice noke) {
      if (onNokeDisconnectedCallback != null) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, noke.getMac());
        result.setKeepCallback(true);
        onNokeDisconnectedCallback.sendPluginResult(result);
      }
      // NokeDevices.remove(noke.getMac());
      currentLock = null;
    }

    @Override
    public void onDataUploaded(int result, String message) {
        Log.d(TAG, message);
        if (onDataUploadedCallback != null) {
            PluginResult presult = new PluginResult(PluginResult.Status.OK, "ON DATA UPLOAD PAYLOAD");
            presult.setKeepCallback(true);
            onDataUploadedCallback.sendPluginResult(presult);
        }
    }

    @Override
    public void onBluetoothStatusChanged(int bluetoothStatus) {
      if (onBluetoothStatusChangedCallback != null) {
        PluginResult result = new PluginResult(PluginResult.Status.OK);
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
      if (mNokeService == null) {
        initService();
      }
      if (onNokeInitCallback != null) {
        PluginResult result = new PluginResult(PluginResult.Status.OK);
        result.setKeepCallback(true);
        onNokeInitCallback.sendPluginResult(result);
      }
      return true;
    }

    if (action.equals("sendCommands")) {
      String id = args.getString(0);
      String commands = args.getString(1);
      if (currentLock != null) {
        Log.d(TAG, "RECIEVED THIS COMMAND STRING FROM API - SENDING TO DEVICE: " + commands);
        currentLock.sendCommands(commands);
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, id));
      } else {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, id));
      }
      return true;
    }

    if (action.equals("resumeScanning")) {
      if (currentLock == null) {
        mNokeService.startScanningForNokeDevices();
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
      } else {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
      }
      return true;
    }

    if (action.equals("bindOnNokeInit")) {
      bindOnNokeInit(callbackContext);
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

  private void stopScanning(final CallbackContext callbackContext) {
    stopScanningCallback = callbackContext;
  };

  private void bindOnNokeInit(final CallbackContext callbackContext) {
    onNokeInitCallback = callbackContext;
  }

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
