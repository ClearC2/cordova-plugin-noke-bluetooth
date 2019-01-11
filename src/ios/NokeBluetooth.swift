func consolelog(_ message: String){
    NSLog("%@ - %@", "Noke", message)
}

@objc(NokeBluetooth) class NokeBluetooth : CDVPlugin {
    func initService(_ command: CDVInvokedUrlCommand) {
        consolelog("initService fired")
        let result: CDVPluginResult
        result = CDVPluginResult(status: CDVCommandStatus_ILLEGAL_ACCESS_EXCEPTION)
        commandDelegate!.send(result, callbackId: command.callbackId)
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
