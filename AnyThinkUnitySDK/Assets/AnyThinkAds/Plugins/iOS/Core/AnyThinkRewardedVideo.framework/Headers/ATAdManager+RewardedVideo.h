//
//  ATAdManager+RewardedVideo.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 05/07/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import <AnyThinkSDK/AnyThinkSDK.h>
#import "ATRewardedVideoDelegate.h"
/*
 * Third-party extra data
 */
extern NSString *const kATAdLoadingExtraKeywordKey;
extern NSString *const kATAdLoadingExtraUserDataKeywordKey;
extern NSString *const kATAdLoadingExtraUserIDKey;
extern NSString *const kATAdLoadingExtraLocationKey;
extern NSString *const kATAdLoadingExtraMediaExtraKey;
extern NSString *const kATAdLoadingExtraRewardNameKey;
extern NSString *const kATAdLoadingExtraRewardAmountKey;
extern NSString *const kATAdLoadingExtraNetworkPlacementIDKey;

extern NSString *const kATRewardedVideoCallbackExtraAdsourceIDKey;
extern NSString *const kATRewardedVideoCallbackExtraNetworkIDKey;

// Klevin SDK
extern NSString *const kATRewardedVideoKlevinRewardTimeKey;
extern NSString *const kATRewardedVideoKlevinRewardTriggerKey;


@interface ATAdManager (RewardedVideo)
-(BOOL) rewardedVideoReadyForPlacementID:(NSString*)placementID;
-(ATCheckLoadModel*) checkRewardedVideoLoadStatusForPlacementID:(NSString*)placementID;
- (NSArray<NSDictionary *> *)getRewardedVideoValidAdsForPlacementID:(NSString *)placementID;
-(void) showRewardedVideoWithPlacementID:(NSString*)placementID inViewController:(UIViewController*)viewController delegate:(id<ATRewardedVideoDelegate>)delegate;
-(void) showRewardedVideoWithPlacementID:(NSString*)placementID scene:(NSString*)scene inViewController:(UIViewController*)viewController delegate:(id<ATRewardedVideoDelegate>)delegate;

-(void)entryRewardedVideoScenarioWithPlacementID:(NSString *)placementID scene:(NSString*)scene;

@end
