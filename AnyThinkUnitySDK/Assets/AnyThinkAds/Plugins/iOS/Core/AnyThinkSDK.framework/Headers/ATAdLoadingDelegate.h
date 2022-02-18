//
//  ATAdLoadingDelegate.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 04/07/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#ifndef ATAdLoadingDelegate_h
#define ATAdLoadingDelegate_h
@protocol ATAdLoadingDelegate<NSObject>


-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID;
-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error;


//v 5.7.73
-(void) didFinishLoadingSplashADWithPlacementID:(NSString *)placementID isTimeout:(BOOL)isTimeout;
-(void) didTimeoutLoadingSplashADWithPlacementID:(NSString *)placementID;

@optional
- (void)didStartLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra;
- (void)didFinishLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra;

- (void)didFailToLoadADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error;

// bidding
- (void)didStartBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra;
- (void)didFinishBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra;

- (void)didFailBiddingADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error;

@end
#endif /* ATAdLoadingDelegate_h */
