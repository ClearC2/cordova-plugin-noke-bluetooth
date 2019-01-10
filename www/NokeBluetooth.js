var exec = require('cordova/exec');

var PLUGIN_NAME = "NokeBluetooth";

function NokeBluetooth() {}

var plugin = new NokeBluetooth()

NokeBluetooth.prototype.initService = function (listeners) {
  if (!listeners) listeners = {}
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
  plugin.bindOnNokeDiscovered(listeners.onNokeDiscovered)
  plugin.bindOnNokeConnecting(listeners.onNokeConnecting)
  plugin.bindOnNokeConnected(listeners.onNokeConnected)
  plugin.bindOnNokeSyncing(listeners.onNokeSyncing)
  plugin.bindOnNokeUnlocked(listeners.onNokeUnlocked)
  plugin.bindOnNokeShutdown(listeners.onNokeShutdown)
  plugin.bindOnNokeDisconnected(listeners.onNokeDisconnected)
  plugin.bindOnDataUploaded(listeners.onDataUploaded)
  plugin.bindOnBluetoothStatusChanged(listeners.onBluetoothStatusChanged)
  plugin.bindOnNokeError(listeners.onNokeError)
}

NokeBluetooth.prototype.bindOnNokeDiscovered = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeDiscovered')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeDiscovered')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeDiscovered")
}

NokeBluetooth.prototype.bindOnNokeConnecting = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeConnecting')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeConnecting')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeConnecting")
}

NokeBluetooth.prototype.bindOnNokeConnected = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeConnected')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeConnected')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeConnected")
}

NokeBluetooth.prototype.bindOnNokeSyncing = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeSyncing')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeSyncing')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeSyncing")
}

NokeBluetooth.prototype.bindOnNokeUnlocked = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeUnlocked')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeUnlocked')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeUnlocked")
}

NokeBluetooth.prototype.bindOnNokeShutdown = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeShutdown')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeShutdown')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeShutdown")
}

NokeBluetooth.prototype.bindOnNokeDisconnected = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeDisconnected')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeDisconnected')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeDisconnected")
}

NokeBluetooth.prototype.bindOnDataUploaded = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnDataUploaded')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnDataUploaded')}
  exec(fn, er, PLUGIN_NAME, "bindOnDataUploaded")
}

NokeBluetooth.prototype.bindOnBluetoothStatusChanged = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnBluetoothStatusChanged')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnBluetoothStatusChanged')}
  exec(fn, er, PLUGIN_NAME, "bindOnBluetoothStatusChanged")
}
NokeBluetooth.prototype.bindOnNokeError = function (fn, er) {
  if (typeof fn !== 'function') fn = function() {console.log('Noke bindOnNokeError')}
  if (typeof er !== 'function') er = function() {console.log('ERROR: bindOnNokeError')}
  exec(fn, er, PLUGIN_NAME, "bindOnNokeError")
}

module.exports = plugin;
