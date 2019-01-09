package com.clearc2.cordova.nokebluetooth;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NokeBluetooth extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            this.start(callbackContext);
            return true;
        }
        return false;
    }

    private void start(CallbackContext callbackContext) {
        if (true) {
            callbackContext.success("Start Success");
        } else {
            callbackContext.error("Start Failure");
        }
    }
}
