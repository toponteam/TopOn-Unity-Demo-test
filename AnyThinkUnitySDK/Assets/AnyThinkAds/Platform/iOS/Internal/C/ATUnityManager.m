//
//  ATUnityManager.m
//  UnityContainer
//
//  Created by Martin Lau on 08/08/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import "ATUnityManager.h"
#import <AnyThinkSDK/AnyThinkSDK.h>
#import "ATUnityUtilities.h"
#import <CoreTelephony/CTTelephonyNetworkInfo.h>
#import <CoreTelephony/CTCarrier.h>

NSInvocation* build_invocation(Class class, SEL sel, NSArray<NSString*> *arguments, void(*callback)(const char*, const char *));

/*
 *class:
 *selector:
 *arguments:
 */
bool message_from_unity(const char *msg, void(*callback)(const char*, const char *)) {
    NSString *msgStr = [NSString stringWithUTF8String:msg];
    NSDictionary *msgDict = [NSJSONSerialization JSONObjectWithData:[msgStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    
    Class class = NSClassFromString(msgDict[@"class"]);
    SEL sel = NSSelectorFromString(msgDict[@"selector"]);
    NSArray<NSString*>* arguments = msgDict[@"arguments"];
    
    NSInvocation *invocation = build_invocation(class, sel, arguments, callback);
    [invocation invoke];
    bool ret = false;
    
    if (strcmp(@encode(void), invocation.methodSignature.methodReturnType)) [invocation getReturnValue:&ret]; //Handle return value
    
    return ret;
}

int get_message_for_unity(const char *msg, void(*callback)(const char*, const char *)) {
    NSString *msgStr = [NSString stringWithUTF8String:msg];
    NSDictionary *msgDict = [NSJSONSerialization JSONObjectWithData:[msgStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    
    Class class = NSClassFromString(msgDict[@"class"]);
    SEL sel = NSSelectorFromString(msgDict[@"selector"]);
    NSArray<NSString*>* arguments = msgDict[@"arguments"];
    
    NSInvocation *invocation = build_invocation(class, sel, arguments, callback);
    [invocation invoke];
    int ret = 0;
    
    if (strcmp(@encode(void), invocation.methodSignature.methodReturnType)) [invocation getReturnValue:&ret]; //Handle return value
    
    return ret;
}

NSInvocation* build_invocation(Class class, SEL sel, NSArray<NSString*> *arguments, void(*callback)(const char*, const char *)) {
    NSMethodSignature *signature = [class instanceMethodSignatureForSelector:sel];
    NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:signature];
    invocation.target = [class sharedInstance];
    invocation.selector = sel;
    if ([arguments isKindOfClass:[NSArray class]]) [arguments enumerateObjectsUsingBlock:^(NSString * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) { [invocation setArgument:&obj atIndex:idx + 2]; }];
    if (callback != NULL) [invocation setArgument:&callback atIndex:signature.numberOfArguments - 1];//callback
    
    return invocation;
}

@interface ATUnityManager()
@property(nonatomic, readonly) NSMutableDictionary *consentInfo;
@end
@implementation ATUnityManager
+(instancetype)sharedInstance {
    static ATUnityManager *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[ATUnityManager alloc] init];
    });
    return sharedInstance;
}

-(instancetype) init {
    self = [super init];
    if (self != nil) {
        _consentInfo = [NSMutableDictionary dictionary];
    }
    return self;
}

-(BOOL) startSDKWithAppID:(NSString*)appID appKey:(NSString*)appKey {
    [ATAPI setLogEnabled:YES];
//    if ([self subjectToGDPR]) {
//        [self presentDataConsentDialog];
//    }
    if ([[ATAPI sharedInstance] inDataProtectionArea])
    {
        
        ATDataConsentSet status = [ATAPI sharedInstance].dataConsentSet;
        if (status == ATDataConsentSetUnknown){
            [self presentDataConsentDialog];
        }
    }

    return [[ATAPI sharedInstance] startWithAppID:appID appKey:appKey error:nil];
}
- (BOOL) subjectToGDPR {
    return [@[@"AT", @"BE", @"BG", @"HR", @"CY", @"CZ", @"DK", @"EE", @"FI", @"FR", @"DE", @"GR", @"HU", @"IS", @"IE", @"IT", @"LV", @"LI", @"LT", @"LU", @"MT", @"NL", @"NO", @"PL", @"PT", @"RO", @"SK", @"SI", @"ES", @"SE", @"GB", @"UK"] containsObject:[[CTTelephonyNetworkInfo new].subscriberCellularProvider.isoCountryCode length] > 0 ? [[CTTelephonyNetworkInfo new].subscriberCellularProvider.isoCountryCode uppercaseString] : @""];
}

-(void) presentDataConsentDialog {
    [[ATAPI sharedInstance] presentDataConsentDialogInViewController:[UIApplication sharedApplication].delegate.window.rootViewController dismissalCallback:^{
        
    }];
}

-(void) getUserLocation:(void(*)(const char*))callback {
    [[ATAPI sharedInstance] getUserLocationWithCallback:^(ATUserLocation location) {
        if (callback != NULL) { callback(@(location).stringValue.UTF8String); }
    }];
}

-(void) setPurchaseFlag {
    
}

-(void) clearPurchaseFlag {
    
}

-(BOOL) purchaseFlag {
    
}

-(void) setChannel:(NSString*)channel {
    [[ATAPI sharedInstance] setChannel:channel];
}

-(void) setSubChannel:(NSString*)subChannel {
    [[ATAPI sharedInstance] setSubchannel:subChannel];
}

-(void) setCustomData:(NSString*)customDataStr {
    if ([customDataStr isKindOfClass:[NSString class]] && [customDataStr length] > 0) {
        NSDictionary *customData = [NSJSONSerialization JSONObjectWithData:[customDataStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        [[ATAPI sharedInstance] setCustomData:customData];
    }
}

-(void) setCustomData:(NSString*)customDataStr forPlacementID:(NSString*)placementID {
    if ([customDataStr isKindOfClass:[NSString class]] && [customDataStr length] > 0) {
        NSDictionary *customData = [NSJSONSerialization JSONObjectWithData:[customDataStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        [[ATAPI sharedInstance] setCustomData:customData forPlacementID:placementID];
    }
}

-(void) setDebugLog:(NSString*)flagStr {
    [ATAPI setLogEnabled:[flagStr boolValue]];
}

-(int) getDataConsent {
    return [@{@(ATDataConsentSetPersonalized):@0, @(ATDataConsentSetNonpersonalized):@1, @(ATDataConsentSetUnknown):@2}[@([ATAPI sharedInstance].dataConsentSet)] intValue];
}

-(void) setDataConsent:(NSNumber*)dataConsent {
    [[ATAPI sharedInstance] setDataConsentSet:[@{@0:@(ATDataConsentSetPersonalized), @1:@(ATDataConsentSetNonpersonalized), @2:@(ATDataConsentSetUnknown)}[dataConsent] integerValue] consentString:nil];
}

-(BOOL) inDataProtectionArea {
    return [[ATAPI sharedInstance] inDataProtectionArea];
}


/*
 *
 */
-(void) setDataConsent:(NSString*)consentJsonString network:(NSNumber*)network {
    NSLog(@"constenJsonString = %@, network = %@", consentJsonString, network);
    NSDictionary *networks = @{@1:kNetworkNameFacebook, @2:kNetworkNameAdmob, @3:kNetworkNameInmobi, @4:kNetworkNameFlurry, @5:kNetworkNameApplovin, @6:kNetworkNameMintegral, @7:kNetworkNameMopub, @8:kNetworkNameGDT, @9:kNetworkNameChartboost, @10:kNetworkNameTapjoy, @11:kNetworkNameIronSource, @12:kNetworkNameUnityAds, @13:kNetworkNameVungle, @14:kNetworkNameAdColony, @17:kNetworkNameOneway, @18:kNetworkNameMobPower, @20:kNetworkNameYeahmobi, @21:kNetworkNameAppnext, @22:kNetworkNameBaidu};
    if ([networks containsObjectForKey:network]) {
        if (([consentJsonString isKindOfClass:[NSString class]] && [consentJsonString dataUsingEncoding:NSUTF8StringEncoding] != nil)) {
            NSDictionary *consentDict = [NSJSONSerialization JSONObjectWithData:[consentJsonString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
            _consentInfo[networks[network]] =  [consentDict containsObjectForKey:@"value"] ? consentDict[@"value"] : consentDict;
        } else {
            [_consentInfo removeObjectForKey:networks[network]];
        }
        NSLog(@"consentInfo = %@", _consentInfo);
        if ([_consentInfo[kNetworkNameMintegral] isKindOfClass:[NSDictionary class]]) {
            NSMutableDictionary<NSNumber*, NSNumber*>* mintegralInfo = [NSMutableDictionary<NSNumber*, NSNumber*> dictionary];
            [_consentInfo[kNetworkNameMintegral] enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, id  _Nonnull obj, BOOL * _Nonnull stop) {
                if ([key respondsToSelector:@selector(integerValue)] && [obj respondsToSelector:@selector(integerValue)]) mintegralInfo[@([key integerValue])] = @([obj integerValue]);
            }];
            NSLog(@"consentInfo = %@, %@", [((NSDictionary*)_consentInfo[kNetworkNameMintegral]).allKeys[0] class], [((NSDictionary*)_consentInfo[kNetworkNameMintegral]).allValues[0] class]);
            _consentInfo[kNetworkNameMintegral] = mintegralInfo;
            NSLog(@"consentInfo = %@, %@", [((NSDictionary*)_consentInfo[kNetworkNameMintegral]).allKeys[0] class], [((NSDictionary*)_consentInfo[kNetworkNameMintegral]).allValues[0] class]);
        }
        [[ATAPI sharedInstance] setNetworkConsentInfo:_consentInfo];
    }
}
@end

