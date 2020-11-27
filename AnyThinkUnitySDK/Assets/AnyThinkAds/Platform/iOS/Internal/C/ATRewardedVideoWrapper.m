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
    }
    return nil;
}

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

-(NSString*) scriptWrapperClass {
    return @"ATRewardedVideoWrapper";
}

#pragma mark - delegate
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
@end
