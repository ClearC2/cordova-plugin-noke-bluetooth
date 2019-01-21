import NokeMobileLibrary

func consolelog(_ message: String){
    NSLog("%@ - %@", "Noke", message)
}

@objc(NokeBluetooth) class NokeBluetooth : CDVPlugin {
    func initService(_ command: CDVInvokedUrlCommand) {
        consolelog("initService fired")
        // NokeDeviceManager.shared().setAPIKey("eyJhbGciOiJOT0tFX1BSSVZBVEVfU0FOREJPWCIsInR5cCI6IkpXVCJ9.eyJhbGciOiJOT0tFX1BSSVZBVEVfU0FOREJPWCIsImNvbXBhbnlfdXVpZCI6IjdhZWJiOTgyLWU2YTgtNGMyNi04NzUyLWNhODY2ZjMzYzc2YSIsImlzcyI6Im5va2UuY29tIn0.786e0e298707cd68d91fd85f0da3e803d0cb3cd6") //PRODUCTION
        // NokeDeviceManager.shared().setLibraryMode(NokeLibraryMode.PRODUCTION)
        NokeDeviceManager.shared().setAPIKey("eyJhbGciOiJOT0tFX01PQklMRV9TQU5EQk9YIiwidHlwIjoiSldUIn0.eyJhbGciOiJOT0tFX01PQklMRV9TQU5EQk9YIiwiY29tcGFueV91dWlkIjoiN2FlYmI5ODItZTZhOC00YzI2LTg3NTItY2E4NjZmMzNjNzZhIiwiaXNzIjoibm9rZS5jb20ifQ.5eb9b38d6c294e238882703fc7b684e0bba4d28c") //SANDBOX
        NokeDeviceManager.shared().setLibraryMode(NokeLibraryMode.SANDBOX)
        NokeDeviceManager.shared().setAllowAllNokeDevices(true)
        NokeDeviceManager.shared().delegate = self as? NokeDeviceManagerDelegate
    }

    func nokeDeviceDidUpdateState(to state: NokeDeviceConnectionState, noke: NokeDevice) {

    switch state {
        case .nokeDeviceConnectionStateDiscovered:
            consolelog("Nokē Discovered")
            break
        case .nokeDeviceConnectionStateConnected:
            consolelog("Nokē Connected")
            break
        case .nokeDeviceConnectionStateSyncing:
            consolelog("Nokē Syncing")
            break
        case .nokeDeviceConnectionStateUnlocked:
            consolelog("Nokē Unlocked")
            break
        case .nokeDeviceConnectionStateDisconnected:
            consolelog("Nokē Disconnected")
            break
        default:
            consolelog("Unrecognized State")
            break
        }
    }

    func nokeErrorDidOccur(error: NokeDeviceManagerError, message: String, noke: NokeDevice?) {
      consolelog("nokeErrorDidOccur")
    }

    func bindOnNokeDiscovered(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeDiscovered fired")
    }

    func bindOnNokeConnecting(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeConnecting fired")
    }

    func bindOnNokeConnected(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeConnected fired")
    }

    func bindOnNokeSyncing(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeSyncing fired")
    }

    func bindOnNokeUnlocked(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeUnlocked fired")
    }

    func bindOnNokeShutdown(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeShutdown fired")
    }

    func bindOnNokeDisconnected(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeDisconnected fired")
    }

    func bindOnDataUploaded(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnDataUploaded fired")
    }

    func bindOnBluetoothStatusChanged(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnBluetoothStatusChanged fired")
    }

    func bindOnNokeError(_ command: CDVInvokedUrlCommand) {
        consolelog("bindOnNokeError fired")
    }
}
