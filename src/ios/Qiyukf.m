#import "Qiyukf.h"

@implementation Qiyukf

- (NSString *)appKey {
    if (!_appKey) {
        _appKey = [[self.commandDelegate settings] objectForKey:@"app_key"];
    }
    return _appKey;
}

- (NSString *)appName {
    if (!_appName) {
        _appName = [[self.commandDelegate settings] objectForKey:@"app_key"];
    }
    return _appName;
}

- (QYSessionViewController *)sessionViewController {
    if (!_sessionViewController) {
        _sessionViewController = [[QYSDK sharedSDK] sessionViewController];
    }
    return _sessionViewController;
}

- (void)pluginInitialize
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(finishLaunching:) name:UIApplicationDidFinishLaunchingNotification object:nil];
    
}

- (void)finishLaunching:(NSNotification *)notification
{
    [[QYSDK sharedSDK] registerAppId:self.appKey appName:self.appName];
    QYSource *source = [[QYSource alloc] init];
    source.title = self.appName;
    source.urlString = @"https://8.163.com/";
    self.sessionViewController.sessionTitle = self.appName;
    self.sessionViewController.source = source;
    self.sessionViewController.navigationItem.leftBarButtonItem =
    [[UIBarButtonItem alloc] initWithTitle:@"返回" style:UIBarButtonItemStylePlain
                                    target:self action:@selector(onBack:)];
}

- (void)onBack:(id)sender
{
    [self.viewController dismissViewControllerAnimated:YES completion:nil];
}

- (void)open:(CDVInvokedUrlCommand *)command
{
    UINavigationController *nav =
    [[UINavigationController alloc] initWithRootViewController:self.sessionViewController];
    [self.viewController presentViewController:nav animated:YES completion:nil];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
