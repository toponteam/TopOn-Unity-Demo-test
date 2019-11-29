//
//  ATRewardedVideoDelegate.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 05/07/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#ifndef ATRewardedVideoDelegate_h
#define ATRewardedVideoDelegate_h
#import <AnyThinkSDK/AnyThinkSDK.h>

extern NSString *const kATRewardedVideoCallbackExtraAdsourceIDKey;
extern NSString *const kATRewardedVideoCallbackExtraNetworkIDKey;
@protocol ATRewardedVideoDelegate<ATAdLoadingDelegate>
-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error DEPRECATED_ATTRIBUTE;
-(void) rewardedVideoDidCloseForPlacementID:(NSString*)placementID rewarded:(BOOL)rewarded DEPRECATED_ATTRIBUTE;
-(void) rewardedVideoDidClickForPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;

-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra;
-(void) rewardedVideoDidCloseForPlacementID:(NSString*)placementID rewarded:(BOOL)rewarded extra:(NSDictionary*)extra;
-(void) rewardedVideoDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra;
@end
#endif /* ATRewardedVideoDelegate_h */
