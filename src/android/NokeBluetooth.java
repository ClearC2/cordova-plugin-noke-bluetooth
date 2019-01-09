package com.clearc2.cordova.nokebluetooth;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NokeBluetooth extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            start(callbackContext);
            return true;
        }
        return false;
    }

    private void start(CallbackContext callbackContext) {
		JSONObject succ = new JSONObject();
		JSONObject fail = new JSONObject();
		succ.put("message", "success");
		fail.put("message", "fail");
    	PluginResult result = new PluginResult(PluginResult.Status.OK, succ);
		callbackContext.sendPluginResult(result);
        callbackContext.success(succ);
        callbackContext.error(fail);
    }
}
