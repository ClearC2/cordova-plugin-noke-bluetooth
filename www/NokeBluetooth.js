var exec = require('cordova/exec');

var PLUGIN_NAME = "NokeBluetooth";

function NokeBluetooth() {}

NokeBluetooth.prototype.initService = function() {
  exec(
    function() {},
    function() {},
    PLUGIN_NAME,
    "initService"
  )
}

module.exports = new NokeBluetooth();
