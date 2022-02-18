//
//  ATInterstitialAdWrapper.m
//  UnityContainer
//
//  Created by Martin Lau on 2019/1/8.
//  Copyright © 2019 Martin Lau. All rights reserved.
//

#import "ATInterstitialAdWrapper.h"
#import "ATUnityUtilities.h"
#import <AnyThinkInterstitial/AnyThinkInterstitial.h>

NSString *const kLoadUseRVAsInterstitialKey = @"UseRewardedVideoAsInterstitial";
NSString *const kInterstitialExtraAdSizeKey = @"interstitial_ad_size";
static NSString *kATInterstitialSizeUsesPixelFlagKey = @"uses_pixel";

@interface ATInterstitialAdWrapper()<ATInterstitialDelegate>
@end
@implementation ATInterstitialAdWrapper
+(instancetype)sharedInstance {
    static ATInterstitialAdWrapper *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[ATInterstitialAdWrapper alloc] init];
    });
    return sharedInstance;
}

-(NSString*) scriptWrapperClass {
    return @"ATInterstitialAdWrapper";
}

- (id)selWrapperClassWithDict:(NSDictionary *)dict callback:(void(*)(const char*, const char*))callback {
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
    
    if ([selector isEqualToString:@"loadInterstitialAdWithPlacementID:customDataJSONString:callback:"]) {
        [self loadInterstitialAdWithPlacementID:firstObject customDataJSONString:lastObject callback:callback];
    } else if ([selector isEqualToString:@"interstitialAdReadyForPlacementID:"]) {
        return [NSNumber numberWithBool:[self interstitialAdReadyForPlacementID:firstObject]];
    } else if ([selector isEqualToString:@"showInterstitialAdWithPlacementID:extraJsonString:"]) {
        [self showInterstitialAdWithPlacementID:firstObject extraJsonString:lastObject];
    } else if ([selector isEqualToString:@"checkAdStatus:"]) {
        return [self checkAdStatus:firstObject];
    } else if ([selector isEqualToString:@"clearCache"]) {
        [self clearCache];
    } else if ([selector isEqualToString:@"getValidAdCaches:"]) {
        return [self getValidAdCaches:firstObject];
    }else if ([selector isEqualToString:@"entryScenarioWithPlacementID:scenarioID:"]) {
        [self entryScenarioWithPlacementID:firstObject scenarioID:lastObject];
    }
    // auto
    else if ([selector isEqualToString:@"addAutoLoadAdPlacementID:callback:"]){
        [self addAutoLoadAdPlacementID:firstObject callback:callback];
    }else if ([selector isEqualToString:@"removeAutoLoadAdPlacementID:"]){
        [self removeAutoLoadAdPlacementID:firstObject];
    }else if ([selector isEqualToString:@"autoLoadInterstitialAdReadyForPlacementID:"]){
        return [NSNumber numberWithBool:[self autoLoadInterstitialAdReadyForPlacementID:firstObject]];
    }else if ([selector isEqualToString:@"getAutoValidAdCaches:"]){
        return [self getAutoValidAdCaches:firstObject];
    }else if ([selector isEqualToString:@"setAutoLocalExtra:customDataJSONString:"]){
        [self setAutoLocalExtra:firstObject customDataJSONString:lastObject];
    }else if ([selector isEqualToString:@"entryAutoAdScenarioWithPlacementID:scenarioID:"]){
        [self entryAutoAdScenarioWithPlacementID:firstObject scenarioID:lastObject];
    }else if ([selector isEqualToString:@"showAutoInterstitialAd:extraJsonString:"]){
        [self showAutoInterstitialAd:firstObject extraJsonString:lastObject];
    }else if ([selector isEqualToString:@"checkAutoAdStatus:"]) {
        return [self checkAutoAdStatus:firstObject];
    }
    
    return nil;
}

-(void) loadInterstitialAdWithPlacementID:(NSString*)placementID customDataJSONString:(NSString*)customDataJSONString callback:(void(*)(const char*, const char*))callback {
    
    [self setCallBack:callback forKey:placementID];
    NSMutableDictionary *extra = [NSMutableDictionary dictionary];
    if ([customDataJSONString isKindOfClass:[NSString class]] && [customDataJSONString length] > 0) {
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithData:[customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        NSLog(@"extraDict = %@", extraDict);
        if (extraDict[kLoadUseRVAsInterstitialKey] != nil) {
            extra[kATInterstitialExtraUsesRewardedVideo] = @([extraDict[kLoadUseRVAsInterstitialKey] boolValue]);
        }
        
        CGFloat scale = [extraDict[kATInterstitialSizeUsesPixelFlagKey] boolValue] ? [UIScreen mainScreen].nativeScale : 1.0f;
        if ([extraDict[kInterstitialExtraAdSizeKey] isKindOfClass:[NSString class]] && [[extraDict[kInterstitialExtraAdSizeKey] componentsSeparatedByString:@"x"] count] == 2) {
            NSArray<NSString*>* com = [extraDict[kInterstitialExtraAdSizeKey] componentsSeparatedByString:@"x"];
            extra[kATInterstitialExtraAdSizeKey] = [NSValue valueWithCGSize:CGSizeMake([com[0] doubleValue] / scale, [com[1] doubleValue] / scale)];
        }
    }
    
    NSLog(@"ATInterstitialAdWrapper::extra = %@", extra);
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:extra != nil ? extra : nil delegate:self];
}

-(BOOL) interstitialAdReadyForPlacementID:(NSString*)placementID {
    return [[ATAdManager sharedManager] interstitialReadyForPlacementID:placementID];
}

-(NSString*) getValidAdCaches:(NSString *)placementID {
    NSArray *array = [[ATAdManager sharedManager] getInterstitialValidAdsForPlacementID:placementID];
    NSLog(@"ATNativeAdWrapper::array = %@", array);
    return array.jsonString;
}

-(void) showInterstitialAdWithPlacementID:(NSString*)placementID extraJsonString:(NSString*)extraJsonString {
    NSDictionary *extraDict = ([extraJsonString isKindOfClass:[NSString class]] && [extraJsonString dataUsingEncoding:NSUTF8StringEncoding] != nil) ? [NSJSONSerialization JSONObjectWithData:[extraJsonString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil] : nil;
    [[ATAdManager sharedManager] showInterstitialWithPlacementID:placementID scene:extraDict[kATUnityUtilitiesAdShowingExtraScenarioKey] inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:self];
}

-(NSString*) checkAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkInterstitialLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATInterstitialAdWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString;
}
- (void)entryScenarioWithPlacementID:(NSString *)placementID scenarioID:(NSString *)scenarioID{
    
    [[ATAdManager sharedManager] entryInterstitialScenarioWithPlacementID:placementID scene:scenarioID];
}
-(void) clearCache {
    [[ATAdManager sharedManager] clearCache];
}

#pragma mark - auto
-(void) addAutoLoadAdPlacementID:(NSString*)placementID callback:(void(*)(const char*, const char*))callback {

    if (placementID == nil) {
        return;
    }
    
    [ATInterstitialAutoAdManager sharedInstance].delegate = self;
    
    NSArray *placementIDArray = [self jsonStrToArray:placementID];

    [placementIDArray enumerateObjectsUsingBlock:^(NSString * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        [self setCallBack:callback forKey:obj];
        NSLog(@" addAutoLoadAdPlacementID--%@",placementID);
    }];
    
    
    [[ATInterstitialAutoAdManager sharedInstance] addAutoLoadAdPlacementIDArray:placementIDArray];
}

-(void) removeAutoLoadAdPlacementID:(NSString*)placementID{
    NSLog(@" removeAutoLoadAdPlacementID--%@",placementID);
    
    if (placementID == nil) {
        return;
    }
    
    NSArray *placementIDArray = [self jsonStrToArray:placementID];
    
    [[ATInterstitialAutoAdManager sharedInstance] removeAutoLoadAdPlacementIDArray:placementIDArray];
}

-(BOOL) autoLoadInterstitialAdReadyForPlacementID:(NSString*)placementID {
    
    NSLog(@"Unity: autoLoadInterstitialAdReadyForPlacementID--%@---%d",placementID,[[ATInterstitialAutoAdManager sharedInstance] autoLoadInterstitialReadyForPlacementID:placementID]);
    return [[ATInterstitialAutoAdManager sharedInstance] autoLoadInterstitialReadyForPlacementID:placementID];
}

-(NSString*) getAutoValidAdCaches:(NSString *)placementID{
    
    NSArray *array = [[ATInterstitialAutoAdManager sharedInstance] checkValidAdCachesWithPlacementID:placementID];
    
    NSLog(@"Unity: getAutoValidAdCaches::array = %@", array);
    
    return array.jsonString;
}

-(NSString*) checkAutoAdStatus:(NSString *)placementID {

    ATCheckLoadModel *checkLoadModel = [[ATInterstitialAutoAdManager sharedInstance] checkInterstitialLoadStatusForPlacementID:placementID];
    
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    
    NSLog(@":checkAutoAdStatus statusDict = %@", statusDict);
    return statusDict.jsonString;
}

-(void) setAutoLocalExtra:(NSString*)placementID customDataJSONString:(NSString*)customDataJSONString{
    
    if ([customDataJSONString isKindOfClass:[NSString class]]) {
        
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithData:[customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        
        NSMutableDictionary *extra = [NSMutableDictionary dictionary];

        if ([extraDict isKindOfClass:[NSDictionary class]]) {
            
            if (extraDict[kLoadUseRVAsInterstitialKey] != nil) {
                extra[kATInterstitialExtraUsesRewardedVideo] = @([extraDict[kLoadUseRVAsInterstitialKey] boolValue]);
            }
            
            CGFloat scale = [extraDict[kATInterstitialSizeUsesPixelFlagKey] boolValue] ? [UIScreen mainScreen].nativeScale : 1.0f;
            if ([extraDict[kInterstitialExtraAdSizeKey] isKindOfClass:[NSString class]] && [[extraDict[kInterstitialExtraAdSizeKey] componentsSeparatedByString:@"x"] count] == 2) {
                NSArray<NSString*>* com = [extraDict[kInterstitialExtraAdSizeKey] componentsSeparatedByString:@"x"];
                extra[kATInterstitialExtraAdSizeKey] = [NSValue valueWithCGSize:CGSizeMake([com[0] doubleValue] / scale, [com[1] doubleValue] / scale)];
            }
        }
        
        NSLog(@"ATInterstitialAdWrapper::setAutoLocalExtra statusDict = %@", extraDict);
        [[ATInterstitialAutoAdManager sharedInstance] setLocalExtra:extra placementID:placementID];
        
    
    }
}

-(void) entryAutoAdScenarioWithPlacementID:(NSString*)placementID scenarioID:(NSString*)scenarioID{
    NSLog(@"ATInterstitialAdWrapper::entryAutoAdScenarioWithPlacementID = %@ scenarioID = %@", placementID,scenarioID);

    [[ATInterstitialAutoAdManager sharedInstance] entryAdScenarioWithPlacementID:placementID scenarioID:scenarioID];
}

-(void) showAutoInterstitialAd:(NSString*)placementID extraJsonString:(NSString*)extraJsonString {
    
    NSDictionary *extraDict = ([extraJsonString isKindOfClass:[NSString class]] && [extraJsonString dataUsingEncoding:NSUTF8StringEncoding] != nil) ? [NSJSONSerialization JSONObjectWithData:[extraJsonString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil] : nil;
    
    NSLog(@"ATInterstitialAdWrapper::showAutoInterstitialAd = %@ extraJsonString = %@", placementID,extraJsonString);
    
    NSLog(@"ATInterstitialAdWrapper::extraDict = %@", extraDict);

    [[ATInterstitialAutoAdManager sharedInstance] showAutoLoadInterstitialWithPlacementID:placementID scene:extraDict[kATUnityUtilitiesAdShowingExtraScenarioKey] inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:self];
}

#pragma mark - delegate method(s)
// ad
- (void)didStartLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"startLoadingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFinishLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"finishLoadingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFailToLoadADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    [self invokeCallback:@"failToLoadADSource" placementID:placementID error:error extra:extra];
}

// bidding
- (void)didStartBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"startBiddingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFinishBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"finishBiddingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFailBiddingADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    [self invokeCallback:@"failBiddingADSource" placementID:placementID error:error extra:extra];
}



-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    [self invokeCallback:@"OnInterstitialAdLoaded" placementID:placementID error:nil extra:nil];
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error {
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to load ad", NSLocalizedFailureReasonErrorKey:@"AT has failed to load ad"}];
    [self invokeCallback:@"OnInterstitialAdLoadFailure" placementID:placementID error:error extra:nil];
}

-(void) interstitialDidShowForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnInterstitialAdShow" placementID:placementID error:nil extra:extra];
    [[NSNotificationCenter defaultCenter] postNotificationName:kATUnityUtilitiesInterstitialImpressionNotification object:nil];
}

-(void) interstitialFailedToShowForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to show ad", NSLocalizedFailureReasonErrorKey:@"AT has failed to show ad"}];
    [self invokeCallback:@"OnInterstitialAdFailedToShow" placementID:placementID error:error extra:nil];
}

-(void) interstitialDidStartPlayingVideoForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnInterstitialAdVideoPlayStart" placementID:placementID error:nil extra:extra];
}

-(void) interstitialDidEndPlayingVideoForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnInterstitialAdVideoPlayEnd" placementID:placementID error:nil extra:extra];
}

-(void) interstitialDidFailToPlayVideoForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnInterstitialAdVideoPlayFailure" placementID:placementID error:error extra:extra];
}

-(void) interstitialDidCloseForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnInterstitialAdClose" placementID:placementID error:nil extra:extra];
    [[NSNotificationCenter defaultCenter] postNotificationName:kATUnityUtilitiesInterstitialCloseNotification object:nil];
}

-(void) interstitialDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnInterstitialAdClick" placementID:placementID error:nil extra:extra];
}
@end
