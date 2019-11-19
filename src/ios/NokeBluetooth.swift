import NokeMobileLibrary

func consolelog(_ message: String){
  NSLog("%@ - %@", "Noke", message)
}

@objc(NokeBluetooth) class NokeBluetooth : CDVPlugin, NokeDeviceManagerDelegate {

  var currentNoke : NokeDevice?
  var onNokeInitCallback : String?
  var onNokeDiscoveredCallback : String?
  var onNokeConnectingCallback : String?
  var onNokeConnectedCallback : String?
  var onNokeSyncingCallback : String?
  var onNokeUnlockedCallback : String?
  var onNokeShutdownCallback : String?
  var onNokeDisconnectedCallback : String?
  var onDataUploadedCallback : String?
  var onBluetoothStatusChangedCallback : String?
  var onErrorCallback : String?

  func initService(_ command: CDVInvokedUrlCommand) {
    // NokeDeviceManager.shared().setAPIKey("eyJhbGciOiJOT0tFX1BSSVZBVEVfU0FOREJPWCIsInR5cCI6IkpXVCJ9.eyJhbGciOiJOT0tFX1BSSVZBVEVfU0FOREJPWCIsImNvbXBhbnlfdXVpZCI6IjdhZWJiOTgyLWU2YTgtNGMyNi04NzUyLWNhODY2ZjMzYzc2YSIsImlzcyI6Im5va2UuY29tIn0.786e0e298707cd68d91fd85f0da3e803d0cb3cd6") //PRODUCTION
    // NokeDeviceManager.shared().setLibraryMode(NokeLibraryMode.PRODUCTION)
    NokeDeviceManager.shared().setAPIKey("eyJhbGciOiJOT0tFX01PQklMRV9TQU5EQk9YIiwidHlwIjoiSldUIn0.eyJhbGciOiJOT0tFX01PQklMRV9TQU5EQk9YIiwiY29tcGFueV91dWlkIjoiN2FlYmI5ODItZTZhOC00YzI2LTg3NTItY2E4NjZmMzNjNzZhIiwiaXNzIjoibm9rZS5jb20ifQ.5eb9b38d6c294e238882703fc7b684e0bba4d28c") //SANDBOX
    NokeDeviceManager.shared().setLibraryMode(NokeLibraryMode.SANDBOX)
    NokeDeviceManager.shared().setAllowAllNokeDevices(true)
    NokeDeviceManager.shared().delegate = self
    if (onNokeInitCallback != nil) {
      let result: CDVPluginResult
      result = CDVPluginResult(status: CDVCommandStatus_OK)
      result.setKeepCallbackAs(true)
      commandDelegate!.send(result, callbackId: onNokeInitCallback)
    }
  }

  func nokeDeviceDidUpdateState(to state: NokeDeviceConnectionState, noke: NokeDevice) {
    switch state {
    case .nokeDeviceConnectionStateDiscovered:
      if (onNokeDiscoveredCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeDiscoveredCallback)
      }
      NokeDeviceManager.shared().stopScan()
      if (onNokeConnectingCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: true)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeConnectingCallback)
      }
      NokeDeviceManager.shared().connectToNokeDevice(noke)
      break
    case .nokeDeviceConnectionStateConnected:
      if (onNokeConnectingCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: false)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeConnectingCallback)
      }
      if (onNokeConnectedCallback != nil) {
        let result: CDVPluginResult
        let info: [String: Any] = [
          "mac": noke.mac,
          "session": noke.session!,
          "name": noke.name,
          "serial": noke.serial,
          "version": noke.version,
          "battery": noke.battery,
          "trackingkey": noke.trackingKey
        ]
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: info)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeConnectedCallback)
      }
      currentNoke = noke
      break
    case .nokeDeviceConnectionStateSyncing:
      if (onNokeSyncingCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeSyncingCallback)
      }
      break
    case .nokeDeviceConnectionStateUnlocked:
      if (onNokeUnlockedCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: noke.lockState.rawValue)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeUnlockedCallback)
      }
      NokeDeviceManager.shared().startScanForNokeDevices()
      break
    case .nokeDeviceConnectionStateDisconnected:
      if (onNokeDisconnectedCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: noke.mac)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeDisconnectedCallback)
      }
      NokeDeviceManager.shared().cacheUploadQueue()
      NokeDeviceManager.shared().startScanForNokeDevices()
      currentNoke = nil
      break
    default:
      if (onNokeConnectingCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: true)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onNokeConnectingCallback)
      }
      consolelog("UNRECOGNIZED NOKE DEVICE STATE")
      break
    }
  }

  func bluetoothManagerDidUpdateState(state: NokeManagerBluetoothState) {
    switch (state) {
    case NokeManagerBluetoothState.poweredOn:
      if (onBluetoothStatusChangedCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: true)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onBluetoothStatusChangedCallback)
      }
      NokeDeviceManager.shared().startScanForNokeDevices()
      break
    case NokeManagerBluetoothState.poweredOff:
      if (onBluetoothStatusChangedCallback != nil) {
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: false)
        result.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: onBluetoothStatusChangedCallback)
      }
      break
    default:
      consolelog("UNRECOGNIZED BLUETOOTH MANAGER STATE")
      break
    }
  }

  func nokeDeviceDidShutdown(noke: NokeDevice, isLocked: Bool, didTimeout: Bool) {
    if (onNokeShutdownCallback != nil) {
      let result: CDVPluginResult
      result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: noke.mac)
      result.setKeepCallbackAs(true)
      commandDelegate!.send(result, callbackId: onNokeShutdownCallback)
    }
    currentNoke = nil
  }

  func didUploadData(result: Int, message: String) {
    if (onDataUploadedCallback != nil) {
      let result: CDVPluginResult
      result = CDVPluginResult(status: CDVCommandStatus_OK)
      result.setKeepCallbackAs(true)
      commandDelegate!.send(result, callbackId: onDataUploadedCallback)
    }
  }

  func nokeErrorDidOccur(error: NokeDeviceManagerError, message: String, noke: NokeDevice?) {
    if (onErrorCallback != nil) {
      let result: CDVPluginResult
      let info: [String: Any] = [
          "code": 0,
          "message": message
      ]
      result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: info)
      result.setKeepCallbackAs(true)
      commandDelegate!.send(result, callbackId: onErrorCallback)
    }
  }

  func sendCommands (_ command: CDVInvokedUrlCommand) {
    let commands = command.arguments[1] as? String ?? ""
    let id = command.arguments[0] as? String ?? ""
    let result: CDVPluginResult
    if (currentNoke != nil) {
      currentNoke?.sendCommands(commands)
      result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: id)
    } else {
      result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: id)
    }
    commandDelegate!.send(result, callbackId: command.callbackId)
  }

  func bindOnNokeInit(_ command: CDVInvokedUrlCommand) {
    onNokeInitCallback = command.callbackId
  }

  func bindOnNokeDiscovered(_ command: CDVInvokedUrlCommand) {
    onNokeDiscoveredCallback = command.callbackId
  }

  func bindOnNokeConnecting(_ command: CDVInvokedUrlCommand) {
    onNokeConnectingCallback = command.callbackId
  }

  func bindOnNokeConnected(_ command: CDVInvokedUrlCommand) {
    onNokeConnectedCallback = command.callbackId
  }

  func bindOnNokeSyncing(_ command: CDVInvokedUrlCommand) {
    onNokeSyncingCallback = command.callbackId
  }

  func bindOnNokeUnlocked(_ command: CDVInvokedUrlCommand) {
    onNokeUnlockedCallback = command.callbackId
  }

  func bindOnNokeShutdown(_ command: CDVInvokedUrlCommand) {
    onNokeShutdownCallback = command.callbackId
  }

  func bindOnNokeDisconnected(_ command: CDVInvokedUrlCommand) {
    onNokeDiscoveredCallback = command.callbackId
  }

  func bindOnDataUploaded(_ command: CDVInvokedUrlCommand) {
    onDataUploadedCallback = command.callbackId
  }

  func bindOnBluetoothStatusChanged(_ command: CDVInvokedUrlCommand) {
    onBluetoothStatusChangedCallback = command.callbackId
  }

  func bindOnNokeError(_ command: CDVInvokedUrlCommand) {
    onErrorCallback = command.callbackId
  }
}
