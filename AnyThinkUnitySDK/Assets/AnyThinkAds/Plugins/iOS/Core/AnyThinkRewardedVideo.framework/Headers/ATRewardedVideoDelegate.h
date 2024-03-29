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
extern NSString *const kATRewardedVideoCallbackExtraIsHeaderBidding DEPRECATED_MSG_ATTRIBUTE("The kATRewardedVideoCallbackExtraIsHeaderBidding class will be obsolete, please use kATRewardedVideoDelegateExtraAdSourceIsHeaderBidding");
extern NSString *const kATRewardedVideoDelegateExtraAdSourceIsHeaderBidding;
extern NSString *const kATRewardedVideoCallbackExtraPrice;
extern NSString *const kATRewardedVideoCallbackExtraPriority;

extern NSString *const kATRewardedVideoAgainFlag;

@protocol ATRewardedVideoDelegate<ATAdLoadingDelegate>

-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra;
-(void) rewardedVideoDidCloseForPlacementID:(NSString*)placementID rewarded:(BOOL)rewarded extra:(NSDictionary*)extra;
-(void) rewardedVideoDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoDidDeepLinkOrJumpForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra result:(BOOL)success;

// rewarded video again
-(void) rewardedVideoAgainDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoAgainDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoAgainDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra;
-(void) rewardedVideoAgainDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra;
-(void) rewardedVideoAgainDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra;
@end
#endif /* ATRewardedVideoDelegate_h */
