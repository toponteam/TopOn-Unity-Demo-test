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
#import "ATBannerAdWrapper.h"
#import "ATNativeAdWrapper.h"
#import "ATNativeBannerAdWrapper.h"
#import "ATInterstitialAdWrapper.h"
#import "ATRewardedVideoWrapper.h"

/*
 *class:
 *selector:
 *arguments:
 */
bool message_from_unity(const char *msg, void(*callback)(const char*, const char *)) {
    NSString *msgStr = [NSString stringWithUTF8String:msg];
    NSDictionary *msgDict = [NSJSONSerialization JSONObjectWithData:[msgStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    Class class = NSClassFromString(msgDict[@"class"]);

    bool ret = false;
    ret = [[[class sharedInstance] selWrapperClassWithDict:msgDict callback:callback != NULL ? callback : nil] boolValue];
    
    return ret;
}

int get_message_for_unity(const char *msg, void(*callback)(const char*, const char *)) {
    NSString *msgStr = [NSString stringWithUTF8String:msg];
    NSDictionary *msgDict = [NSJSONSerialization JSONObjectWithData:[msgStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    
    Class class = NSClassFromString(msgDict[@"class"]);
    
    int ret = 0;
    ret = [[[class sharedInstance] selWrapperClassWithDict:msgDict callback:callback != NULL ? callback : nil] intValue];
    
    return ret;
}

char * get_string_message_for_unity(const char *msg, void(*callback)(const char*, const char *)) {
    NSString *msgStr = [NSString stringWithUTF8String:msg];
    NSDictionary *msgDict = [NSJSONSerialization JSONObjectWithData:[msgStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];

    Class class = NSClassFromString(msgDict[@"class"]);
    
    NSString *ret = @"";
    ret = [[class sharedInstance] selWrapperClassWithDict:msgDict callback:callback != NULL ? callback : nil];
    
    if ([ret UTF8String] == NULL)
        return NULL;

    char* res = (char*)malloc(strlen([ret UTF8String]) + 1);
    strcpy(res, [ret UTF8String]);

    return res;
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

- (id)selWrapperClassWithDict:(NSDictionary *)dict callback:(void(*)(const char*))callback {
    NSString *selector = dict[@"selector"];
    NSArray<NSString*>* arguments = dict[@"arguments"];
    NSString *firstObject = @"";
    NSString *lastObject = @"";
    if (![ATUnityUtilities isEmpty:arguments]) {
        for (int i = 0; i < arguments.count; i++) {
            if (i == 0) { firstObject = arguments[i]; }
            else { lastObject = arguments[i]; }
        }
    }
    
    if ([selector isEqualToString:@"startSDKWithAppID:appKey:"]) {
        return [NSNumber numberWithBool:[self startSDKWithAppID:firstObject appKey:lastObject]];
    } else if ([selector isEqualToString:@"subjectToGDPR"]) {
        return [NSNumber numberWithBool:[self subjectToGDPR]];
    } else if ([selector isEqualToString:@"presentDataConsentDialog"]) {
        [self presentDataConsentDialog];
    } else if ([selector isEqualToString:@"getUserLocation:"]) {
        [self getUserLocation:callback];
    } else if ([selector isEqualToString:@"setPurchaseFlag"]) {
        [self setPurchaseFlag];
    } else if ([selector isEqualToString:@"clearPurchaseFlag"]) {
        [self clearPurchaseFlag];
    } else if ([selector isEqualToString:@"purchaseFlag"]) {
        return [NSNumber numberWithBool:[self purchaseFlag]];
    } else if ([selector isEqualToString:@"setChannel:"]) {
        [self setChannel:firstObject];
    } else if ([selector isEqualToString:@"setSubChannel:"]) {
        [self setSubChannel:firstObject];
    } else if ([selector isEqualToString:@"setCustomData:"]) {
        [self setCustomData:firstObject];
    } else if ([selector isEqualToString:@"setCustomData:forPlacementID:"]) {
        [self setCustomData:firstObject forPlacementID:lastObject];
    } else if ([selector isEqualToString:@"setDebugLog:"]) {
        [self setDebugLog:firstObject];
    } else if ([selector isEqualToString:@"getDataConsent"]) {
        return [NSNumber numberWithInt:[self getDataConsent]];
    } else if ([selector isEqualToString:@"setDataConsent:"]) {
        [self setDataConsent:[NSNumber numberWithInt:firstObject.intValue]];
    } else if ([selector isEqualToString:@"inDataProtectionArea"]) {
        return [NSNumber numberWithBool:[self inDataProtectionArea]];
    } else if ([selector isEqualToString:@"deniedUploadDeviceInfo:"]) {
        [self deniedUploadDeviceInfo:firstObject];
    } else if ([selector isEqualToString:@"setDataConsent:network:"]) {
        [self setDataConsent:firstObject network:[NSNumber numberWithInt:lastObject.intValue]];
    } else if ([selector isEqualToString:@"setExcludeBundleIdArray:"]) {
        [self setExcludeBundleIdArray:firstObject];
    } else if ([selector isEqualToString:@"setExludePlacementid:unitIDArray:"]) {
        [self setExludePlacementid:firstObject unitIDArray:lastObject];
    } else if ([selector isEqualToString:@"setSDKArea:"]) {
        [self setSDKArea:[NSNumber numberWithInt:firstObject.intValue]];
    } else if ([selector isEqualToString:@"getArea:"]) {
        [self getArea:callback];
    } else if ([selector isEqualToString:@"setWXStatus:"]) {
        [self setWXStatus:firstObject];
    } else if ([selector isEqualToString:@"setLocationLongitude:dimension:"]) {
        [self setLocationLongitude:[NSNumber numberWithDouble:firstObject.doubleValue] dimension:[NSNumber numberWithDouble:lastObject.doubleValue]];
    }
    return nil;
}

-(BOOL) startSDKWithAppID:(NSString*)appID appKey:(NSString*)appKey {
    [ATAPI setLogEnabled:YES];
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
    return NO;
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

-(void) deniedUploadDeviceInfo:(NSString *)deniedInfo {
    NSLog(@"ATUnityManager::deniedUploadDeviceInfo = %@", deniedInfo);
    if (![ATUnityUtilities isEmpty:deniedInfo]) {
        NSArray *deniedInfoArray = [NSJSONSerialization JSONObjectWithData:[deniedInfo dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        [[ATAPI sharedInstance] setDeniedUploadInfoArray:deniedInfoArray];
    }
}

/*
 *
 */
-(void) setDataConsent:(NSString*)consentJsonString network:(NSNumber*)network {
    NSLog(@"constenJsonString = %@, network = %@", consentJsonString, network);
    NSDictionary *networks = @{@1:kATNetworkNameFacebook, @2:kATNetworkNameAdmob, @3:kATNetworkNameInmobi, @4:kATNetworkNameFlurry, @5:kATNetworkNameApplovin, @6:kATNetworkNameMintegral, @7:kATNetworkNameMopub, @8:kATNetworkNameGDT, @9:kATNetworkNameChartboost, @10:kATNetworkNameTapjoy, @11:kATNetworkNameIronSource, @12:kATNetworkNameUnityAds, @13:kATNetworkNameVungle, @14:kATNetworkNameAdColony, @1:kATNetworkNameOneway, @18:kATNetworkNameMobPower, @20:kATNetworkNameYeahmobi, @21:kATNetworkNameAppnext, @22:kATNetworkNameBaidu};
    if ([networks containsObjectForKey:network]) {
        if (([consentJsonString isKindOfClass:[NSString class]] && [consentJsonString dataUsingEncoding:NSUTF8StringEncoding] != nil)) {
            NSDictionary *consentDict = [NSJSONSerialization JSONObjectWithData:[consentJsonString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
            _consentInfo[networks[network]] =  [consentDict containsObjectForKey:@"value"] ? consentDict[@"value"] : consentDict;
        } else {
            [_consentInfo removeObjectForKey:networks[network]];
        }
        NSLog(@"consentInfo = %@", _consentInfo);
        if ([_consentInfo[kATNetworkNameMintegral] isKindOfClass:[NSDictionary class]]) {
            NSMutableDictionary<NSNumber*, NSNumber*>* mintegralInfo = [NSMutableDictionary<NSNumber*, NSNumber*> dictionary];
            [_consentInfo[kATNetworkNameMintegral] enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, id  _Nonnull obj, BOOL * _Nonnull stop) {
                if ([key respondsToSelector:@selector(integerValue)] && [obj respondsToSelector:@selector(integerValue)]) mintegralInfo[@([key integerValue])] = @([obj integerValue]);
            }];
            NSLog(@"consentInfo = %@, %@", [((NSDictionary*)_consentInfo[kATNetworkNameMintegral]).allKeys[0] class], [((NSDictionary*)_consentInfo[kATNetworkNameMintegral]).allValues[0] class]);
            _consentInfo[kATNetworkNameMintegral] = mintegralInfo;
            NSLog(@"consentInfo = %@, %@", [((NSDictionary*)_consentInfo[kATNetworkNameMintegral]).allKeys[0] class], [((NSDictionary*)_consentInfo[kATNetworkNameMintegral]).allValues[0] class]);
        }
        [[ATAPI sharedInstance] setNetworkConsentInfo:_consentInfo];
    }
}

-(void) setExcludeBundleIdArray:(NSString*)bundleIds {
    NSLog(@"ATUnityManager::setExcludeBundleIdArray = %@", bundleIds);
    if (![ATUnityUtilities isEmpty:bundleIds]) {
        NSArray *bundleIdArray = [NSJSONSerialization JSONObjectWithData:[bundleIds dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        [[ATAPI sharedInstance] setExludeAppleIdArray:bundleIdArray];
    }
}

-(void) setExludePlacementid:(NSString*)placementID unitIDArray:(NSString*)adsourceIds {
    NSLog(@"ATUnityManager::setExludePlacementid=%@  adsourceIds= %@",placementID ,adsourceIds);
    if (![ATUnityUtilities isEmpty:adsourceIds]) {
        NSArray *adsourceIdArray = [NSJSONSerialization JSONObjectWithData:[adsourceIds dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        [[ATAdManager sharedManager] setExludePlacementid:placementID
                    unitIDArray:adsourceIdArray];
    }
}

-(void) setSDKArea:(NSNumber*)area {
    NSLog(@"ATUnityManager::setSDKArea=%@",area);
    [[ATAPI sharedInstance] setUserDataArea:[area intValue] == 0 ? ATAreaCodeGlobal : ATAreaCodeChinese_mainland];
}

-(void) getArea:(void(*)(const char *))callback {
    NSLog(@"ATUnityManager::getArea");
    NSMutableDictionary *resultDict = [NSMutableDictionary dictionary];
    [[ATAPI sharedInstance] getAreaSuccess:^(NSString *areaCodeStr) {
        NSLog(@"ATUnityManager::getArea:Success:%@",areaCodeStr);
        if (areaCodeStr != nil) {
            resultDict[@"areaCode"] = areaCodeStr;
            if (callback != NULL) { callback(resultDict.jsonString.UTF8String); }
        }
    } failure:^(NSError *error) {
        NSLog(@"ATUnityManager::getArea:failure:%@",error.domain);
        if (error.domain != nil) {
            resultDict[@"errorMsg"] = error.domain;
            if (callback != NULL) { callback(resultDict.jsonString.UTF8String); }
        }
    }];
}

-(void) setWXStatus:(NSString *)statusStr {
    NSLog(@"ATUnityManager::setWXStatus=%@",statusStr);
    [[ATAPI sharedInstance] setWXStatus:[statusStr boolValue]];
}

-(void) setLocationLongitude:(NSNumber*)longitude dimension:(NSNumber*)latitude {
    NSLog(@"ATUnityManager::setLocationLongitude=%@  dimension=%@",longitude,latitude);
    [[ATAPI sharedInstance] setLocationLongitude:longitude.doubleValue dimension:latitude.doubleValue];
}

@end

