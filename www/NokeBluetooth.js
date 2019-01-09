var exec = require('cordova/exec');

var PLUGIN_NAME = "NokeBluetooth";

function NokeBluetooth() {}

NokeBluetooth.prototype.initService = function (listeners) {
  if (!listeners) listeners = {}
  if (typeof listeners.onServiceConnectSuccess !== 'function') listeners.onServiceConnectSuccess = function() {console.log('Noke onServiceConnectSuccess')}
  if (typeof listeners.onServiceConnectFailure !== 'function') listeners.onServiceConnectFailure = function() {console.log('Noke onServiceConnectFailure')}
  if (typeof listeners.onServiceDisconnected !== 'function') listeners.onServiceDisconnected = function() {console.log('Noke onServiceDisconnected')}
  if (typeof listeners.onNokeDiscovered !== 'function') listeners.onNokeDiscovered = function() {console.log('Noke onNokeDiscovered')}
  if (typeof listeners.onNokeConnecting !== 'function') listeners.onNokeConnecting = function() {console.log('Noke onNokeConnecting')}
  if (typeof listeners.onNokeConnected !== 'function') listeners.onNokeConnected = function() {console.log('Noke onNokeConnected')}
  if (typeof listeners.onNokeSyncing !== 'function') listeners.onNokeSyncing = function() {console.log('Noke onNokeSyncing')}
  if (typeof listeners.onNokeUnlocked !== 'function') listeners.onNokeUnlocked = function() {console.log('Noke onNokeUnlocked')}
  if (typeof listeners.onNokeShutdown !== 'function') listeners.onNokeShutdown = function() {console.log('Noke onNokeShutdown')}
  if (typeof listeners.onNokeDisconnected !== 'function') listeners.onNokeDisconnected = function() {console.log('Noke onNokeDisconnected')}
  if (typeof listeners.onDataUploaded !== 'function') listeners.onDataUploaded = function() {console.log('Noke onDataUploaded')}
  if (typeof listeners.onBluetoothStatusChanged !== 'function') listeners.onBluetoothStatusChanged = function() {console.log('Noke onBluetoothStatusChanged')}
  if (typeof listeners.onNokeError !== 'function') listeners.onNokeError = function() {console.log('Noke onNokeError')}
  exec(function() {}, function() {}, PLUGIN_NAME, "initService")
}

module.exports = new NokeBluetooth();
