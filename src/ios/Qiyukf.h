#import <Cordova/CDVPlugin.h>
#import <QYSDK.h>

@interface Qiyukf : CDVPlugin

@property (nonatomic, strong) NSString *appKey;
@property (nonatomic, strong) NSString *appName;
@property (nonatomic, strong) QYSessionViewController *sessionViewController;

- (void)open:(CDVInvokedUrlCommand *)command;

@end
