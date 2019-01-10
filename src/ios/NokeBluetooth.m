/********* NokeBluetooth.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface NokeBluetooth : CDVPlugin {
  // Member variables go here.
}

- (void)initService:(CDVInvokedUrlCommand*)command;
- (void)bindOnNokeDiscovered:(CDVInvokedUrlCommand*)command;
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

@end
