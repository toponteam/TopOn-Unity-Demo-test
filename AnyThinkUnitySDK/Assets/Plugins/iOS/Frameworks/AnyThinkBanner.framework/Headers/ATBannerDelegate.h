//
//  ATBannerDelegate.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 18/09/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#ifndef ATBannerDelegate_h
#define ATBannerDelegate_h
#import <AnyThinkSDK/AnyThinkSDK.h>
@class ATBannerView;

extern NSString *const kATBannerDelegateExtraNetworkIDKey;
extern NSString *const kATBannerDelegateExtraAdSourceIDKey;

@protocol ATBannerDelegate<ATAdLoadingDelegate>
-(void) bannerView:(ATBannerView*)bannerView didShowAdWithPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void) bannerView:(ATBannerView*)bannerView didClickWithPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void) bannerView:(ATBannerView*)bannerView didCloseWithPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void) bannerView:(ATBannerView*)bannerView didAutoRefreshWithPlacement:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void) bannerView:(ATBannerView*)bannerView failedToAutoRefreshWithPlacementID:(NSString*)placementID error:(NSError*)error;

-(void) bannerView:(ATBannerView*)bannerView didShowAdWithPlacementID:(NSString*)placementID extra:(NSDictionary *)extra;
-(void) bannerView:(ATBannerView*)bannerView didClickWithPlacementID:(NSString*)placementID extra:(NSDictionary *)extra;
-(void) bannerView:(ATBannerView*)bannerView didCloseWithPlacementID:(NSString*)placementID extra:(NSDictionary *)extra;
-(void) bannerView:(ATBannerView*)bannerView didAutoRefreshWithPlacement:(NSString*)placementID extra:(NSDictionary *)extra;

@end
#endif /* ATBannerDelegate_h */
