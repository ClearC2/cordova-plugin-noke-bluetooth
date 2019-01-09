var exec = require('cordova/exec');

var PLUGIN_NAME = "NokeBluetooth";

function NokeBluetooth() {}

NokeBluetooth.prototype.start = function(successCallback, errorCallback) {
  exec(
    successCallback || function() {},
    errorCallback || function() {},
    PLUGIN_NAME,
    "start"
  )
}

module.exports = new NokeBluetooth();
