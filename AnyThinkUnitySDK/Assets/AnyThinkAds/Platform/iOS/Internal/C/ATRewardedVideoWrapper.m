//
//  ATRewardedVideoWrapper.m
//  UnityContainer
//
//  Created by Martin Lau on 08/08/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import "ATRewardedVideoWrapper.h"
#import "ATUnityUtilities.h"
#import <AnyThinkRewardedVideo/AnyThinkRewardedVideo.h>

NSString *const kLoadExtraUserIDKey = @"UserId";
NSString *const kLoadExtraMediaExtraKey = @"UserExtraData";
@interface ATRewardedVideoWrapper()<ATRewardedVideoDelegate>
@end
@implementation ATRewardedVideoWrapper
+(instancetype)sharedInstance {
    static ATRewardedVideoWrapper *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[ATRewardedVideoWrapper alloc] init];
    });
    return sharedInstance;
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
    
    if ([selector isEqualToString:@"loadRewardedVideoWithPlacementID:customDataJSONString:callback:"]) {
        [self loadRewardedVideoWithPlacementID:firstObject customDataJSONString:lastObject callback:callback];
    } else if ([selector isEqualToString:@"rewardedVideoReadyForPlacementID:"]) {
        return [NSNumber numberWithBool:[self rewardedVideoReadyForPlacementID:firstObject]];
    } else if ([selector isEqualToString:@"showRewardedVideoWithPlacementID:extraJsonString:"]) {
        [self showRewardedVideoWithPlacementID:firstObject extraJsonString:lastObject];
    } else if ([selector isEqualToString:@"checkAdStatus:"]) {
        return [self checkAdStatus:firstObject];
    } else if ([selector isEqualToString:@"clearCache"]) {
        [self clearCache];
    } else if ([selector isEqualToString:@"setExtra:"]) {
        [self setExtra:firstObject];
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
    }else if ([selector isEqualToString:@"autoLoadRewardedVideoReadyForPlacementID:"]){
        return [NSNumber numberWithBool:[self autoLoadRewardedVideoReadyForPlacementID:firstObject]];
    }else if ([selector isEqualToString:@"getAutoValidAdCaches:"]){
        return [self getAutoValidAdCaches:firstObject];
    }else if ([selector isEqualToString:@"setAutoLocalExtra:customDataJSONString:"]){
        [self setAutoLocalExtra:firstObject customDataJSONString:lastObject];
    }else if ([selector isEqualToString:@"entryAutoAdScenarioWithPlacementID:scenarioID:"]){
        [self entryAutoAdScenarioWithPlacementID:firstObject scenarioID:lastObject];
    }else if ([selector isEqualToString:@"showAutoRewardedVideoWithPlacementID:extraJsonString:"]){
        [self showAutoRewardedVideoWithPlacementID:firstObject extraJsonString:lastObject];
    }else if ([selector isEqualToString:@"checkAutoAdStatus:"]) {
        return [self checkAutoAdStatus:firstObject];
    }
    
    
    return nil;
}
#pragma mark - normal
-(void) loadRewardedVideoWithPlacementID:(NSString*)placementID customDataJSONString:(NSString*)customDataJSONString callback:(void(*)(const char*, const char*))callback {
    [self setCallBack:callback forKey:placementID];
    NSMutableDictionary *extra = [NSMutableDictionary dictionary];
    if ([customDataJSONString isKindOfClass:[NSString class]] && [customDataJSONString length] > 0) {
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithData:[customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        NSLog(@"extraDict = %@", extra);
        
        if (extraDict[kLoadExtraUserIDKey] != nil) { extra[kATAdLoadingExtraUserIDKey] = extraDict[kLoadExtraUserIDKey]; }
        if (extraDict[kLoadExtraMediaExtraKey] != nil) { extra[kATAdLoadingExtraMediaExtraKey] = extraDict[kLoadExtraMediaExtraKey]; }
    }
    
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:[extra isKindOfClass:[NSMutableDictionary class]] ? extra : nil delegate:self];
}

-(BOOL) rewardedVideoReadyForPlacementID:(NSString*)placementID {
    return [[ATAdManager sharedManager] rewardedVideoReadyForPlacementID:placementID];
}

-(NSString*) checkAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkRewardedVideoLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATRewardedVideoWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString;
}

-(NSString*) getValidAdCaches:(NSString *)placementID {
    NSArray *array = [[ATAdManager sharedManager] getRewardedVideoValidAdsForPlacementID:placementID];
    NSLog(@"ATNativeAdWrapper::array = %@", array);
    return array.jsonString;
}

-(void) showRewardedVideoWithPlacementID:(NSString*)placementID extraJsonString:(NSString*)extraJsonString {
    NSDictionary *extraDict = ([extraJsonString isKindOfClass:[NSString class]] && [extraJsonString dataUsingEncoding:NSUTF8StringEncoding] != nil) ? [NSJSONSerialization JSONObjectWithData:[extraJsonString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil] : nil;
    NSLog(@"ATRewardedVideoWrapper::showRewardedVideoWithPlacementID = %@ extraJsonString = %@", placementID,extraJsonString);
    NSLog(@"ATRewardedVideoWrapper::extraDict = %@", extraDict);
    [[ATAdManager sharedManager] showRewardedVideoWithPlacementID:placementID scene:extraDict[kATUnityUtilitiesAdShowingExtraScenarioKey] inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:self];
}

-(void) clearCache {
    [[ATAdManager sharedManager] clearCache];
}

-(void) setExtra:(NSString*)extra {
    if ([extra isKindOfClass:[NSString class]]) {
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithData:[extra dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        if ([extraDict isKindOfClass:[NSDictionary class]]) [[ATAdManager sharedManager] setExtra:extraDict];
    }
}

- (void)entryScenarioWithPlacementID:(NSString *)placementID scenarioID:(NSString *)scenarioID{
    
    [[ATAdManager sharedManager] entryRewardedVideoScenarioWithPlacementID:placementID scene:scenarioID];
}

-(NSString*) scriptWrapperClass {
    return @"ATRewardedVideoWrapper";
}

#pragma mark - auto
-(void) addAutoLoadAdPlacementID:(NSString*)placementID callback:(void(*)(const char*, const char*))callback {
    
    if (placementID == nil) {
        return;
    }
    
    [ATRewardedVideoAutoAdManager sharedInstance].delegate = self;
    
    
    NSArray *placementIDArray = [self jsonStrToArray:placementID];
    
    [placementIDArray enumerateObjectsUsingBlock:^(NSString * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        [self setCallBack:callback forKey:obj];
        NSLog(@" addAutoLoadAdPlacementID--%@",placementID);
    }];
    [[ATRewardedVideoAutoAdManager sharedInstance] addAutoLoadAdPlacementIDArray:placementIDArray];
    
}

-(void) removeAutoLoadAdPlacementID:(NSString*)placementID{
    NSLog(@" removeAutoLoadAdPlacementID--%@",placementID);
    
    if (placementID == nil) {
           return;
    }
    
    NSArray *placementIDArray = [self jsonStrToArray:placementID];
    
    [[ATRewardedVideoAutoAdManager sharedInstance] removeAutoLoadAdPlacementIDArray:placementIDArray];
}

-(BOOL) autoLoadRewardedVideoReadyForPlacementID:(NSString*)placementID {
    NSLog(@"Unity: autoLoadRewardedVideoReadyForPlacementID--%@--%d",placementID,[[ATRewardedVideoAutoAdManager sharedInstance] autoLoadRewardedVideoReadyForPlacementID:placementID]);
    return [[ATRewardedVideoAutoAdManager sharedInstance] autoLoadRewardedVideoReadyForPlacementID:placementID];
}

-(NSString*) getAutoValidAdCaches:(NSString *)placementID{
    NSArray *array = [[ATRewardedVideoAutoAdManager sharedInstance] checkValidAdCachesWithPlacementID:placementID];
    NSLog(@"Unity: getAutoValidAdCaches::array = %@", array);
    return array.jsonString;
}

-(NSString*) checkAutoAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATRewardedVideoAutoAdManager sharedInstance] checkRewardedVideoLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATRewardedVideoWrapper::checkAutoAdStatus statusDict = %@", statusDict);
    return statusDict.jsonString;
    
}

-(void) setAutoLocalExtra:(NSString*)placementID customDataJSONString:(NSString*)customDataJSONString{
    NSLog(@"Unity: setAutoLocalExtra::placementID = %@ customDataJSONString: %@", placementID,customDataJSONString);

    
    
    if ([customDataJSONString isKindOfClass:[NSString class]]) {
        
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithData:[customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        
        NSMutableDictionary *extra = [NSMutableDictionary dictionary];

        
        if ([extraDict isKindOfClass:[NSDictionary class]]) {
            
            if (extraDict[kLoadExtraUserIDKey] != nil) {
                extra[kATAdLoadingExtraUserIDKey] = extraDict[kLoadExtraUserIDKey];
            }
            if (extraDict[kLoadExtraMediaExtraKey] != nil) { extra[kATAdLoadingExtraMediaExtraKey] = extraDict[kLoadExtraMediaExtraKey];
            }
            
        };
        
        
        
        [[ATRewardedVideoAutoAdManager sharedInstance] setLocalExtra:extra placementID:placementID];
    }
}

-(void) entryAutoAdScenarioWithPlacementID:(NSString*)placementID scenarioID:(NSString*)scenarioID{
    NSLog(@"Unity: getAutoValidAdCaches::array = %@ scenarioID:%@", placementID,scenarioID);

    [[ATRewardedVideoAutoAdManager sharedInstance] entryAdScenarioWithPlacementID:placementID scenarioID:scenarioID];
}

-(void) showAutoRewardedVideoWithPlacementID:(NSString*)placementID extraJsonString:(NSString*)extraJsonString {
    
    NSDictionary *extraDict = ([extraJsonString isKindOfClass:[NSString class]] && [extraJsonString dataUsingEncoding:NSUTF8StringEncoding] != nil) ? [NSJSONSerialization JSONObjectWithData:[extraJsonString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil] : nil;
    
    NSLog(@"ATRewardedVideoWrapper::showAutoRewardedVideoWithPlacementID = %@ extraJsonString = %@", placementID,extraJsonString);
    
    NSLog(@"ATRewardedVideoWrapper::extraDict = %@", extraDict);
    
    [[ATRewardedVideoAutoAdManager sharedInstance] showAutoLoadRewardedVideoWithPlacementID:placementID scene:extraDict[kATUnityUtilitiesAdShowingExtraScenarioKey] inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:self];
}

#pragma mark - delegate
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
    [self invokeCallback:@"OnRewardedVideoLoaded" placementID:placementID error:nil extra:nil];
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error {
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to load ad", NSLocalizedFailureReasonErrorKey:@"AT has failed to load ad"}];
    [self invokeCallback:@"OnRewardedVideoLoadFailure" placementID:placementID error:error extra:nil];
}

-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnRewardedVideoPlayStart" placementID:placementID error:nil extra:extra];
    [[NSNotificationCenter defaultCenter] postNotificationName:kATUnityUtilitiesRewardedVideoImpressionNotification object:nil];
}

-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnRewardedVideoPlayEnd" placementID:placementID error:nil extra:extra];
}

-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to play video", NSLocalizedFailureReasonErrorKey:@"AT has failed to play video"}];
    [self invokeCallback:@"OnRewardedVideoPlayFailure" placementID:placementID error:error extra:extra];
}

-(void) rewardedVideoDidCloseForPlacementID:(NSString*)placementID rewarded:(BOOL)rewarded extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnRewardedVideoClose" placementID:placementID error:nil extra:@{@"rewarded":@(rewarded), @"extra":extra != nil ? extra : @{}}];
    [[NSNotificationCenter defaultCenter] postNotificationName:kATUnityUtilitiesRewardedVideoCloseNotification object:nil];
}

-(void) rewardedVideoDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnRewardedVideoClick" placementID:placementID error:nil extra:extra];
}

-(void) rewardedVideoDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra {
    [self invokeCallback:@"OnRewardedVideoReward" placementID:placementID error:nil extra:extra];
}

//again
// rewarded video again
-(void) rewardedVideoAgainDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra {
    [self invokeCallback:@"OnRewardedVideoAdAgainPlayStart" placementID:placementID error:nil extra:extra];
}

-(void) rewardedVideoAgainDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra {
    [self invokeCallback:@"OnRewardedVideoAdAgainPlayEnd" placementID:placementID error:nil extra:extra];
}

-(void) rewardedVideoAgainDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra {
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to play video", NSLocalizedFailureReasonErrorKey:@"AT has failed to play video"}];
    [self invokeCallback:@"OnRewardedVideoAdAgainPlayFailed" placementID:placementID error:error extra:extra];
}

-(void) rewardedVideoAgainDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra {
    [self invokeCallback:@"OnRewardedVideoAdAgainPlayClicked" placementID:placementID error:nil extra:extra];
}

-(void) rewardedVideoAgainDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra {
    [self invokeCallback:@"OnAgainReward" placementID:placementID error:nil extra:extra];
}

@end
