/********* NokeBluetooth.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface NokeBluetooth : CDVPlugin {
  // Member variables go here.
}

- (void)initService:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeDiscovered:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeConnecting:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeConnected:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeSyncing:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeUnlocked:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeShutdown:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeDisconnected:(CDVInvokedUrlCommand*)command;
- (void)bindOnDataUploaded:(CDVInvokedUrlCommand*)command;
- (void)bindOnBluetoothStatusChanged:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeError:(CDVInvokedUrlCommand*)command;
@end

@implementation NokeBluetooth

- (void)initService:(CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = nil;
    NSLog(@"Init Service Called.");
    // NSString* echo = [command.arguments objectAtIndex:0];
    //
    // if (echo != nil && [echo length] > 0) {
    //     pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    // } else {
    //     pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    // }
    //
    // [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)bindOnNokeDiscovered:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeDiscovered Called.");
}

- (void)bindOnNokeConnecting:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeConnecting Called.");
}

- (void)bindOnNokeConnected:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeConnected Called.");
}

- (void)bindOnNokeSyncing:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeSyncing Called.");
}

- (void)bindOnNokeUnlocked:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeUnlocked Called.");
}

- (void)bindOnNokeShutdown:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeShutdown Called.");
}

- (void)bindOnNokeDisconnected:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeDisconnected Called.");
}

- (void)bindOnDataUploaded:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnDataUploaded Called.");
}

- (void)bindOnBluetoothStatusChanged:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnBluetoothStatusChanged Called.");
}

- (void)bindOnNokeError:(CDVInvokedUrlCommand*)command {
  NSLog(@"bindOnNokeError Called.");
}

@end
