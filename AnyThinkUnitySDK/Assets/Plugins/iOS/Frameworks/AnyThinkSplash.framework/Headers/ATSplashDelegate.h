//
//  ATSplashDelegate.h
//  AnyThinkSplash
//
//  Created by Martin Lau on 2018/12/20.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#ifndef ATSplashDelegate_h
#define ATSplashDelegate_h
#import <AnyThinkSDK/AnyThinkSDK.h>

extern NSString *const kATSplashDelegateExtraNetworkIDKey;
extern NSString *const kATSplashDelegateExtraAdSourceIDKey;
@protocol ATSplashDelegate<ATAdLoadingDelegate>
-(void)splashDidShowForPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void)splashDidClickForPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;
-(void)splashDidCloseForPlacementID:(NSString*)placementID DEPRECATED_ATTRIBUTE;

-(void)splashDidShowForPlacementID:(NSString*)placementID extra:(NSDictionary *) extra;
-(void)splashDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary *) extra;
-(void)splashDidCloseForPlacementID:(NSString*)placementID extra:(NSDictionary *) extra;
@end
#endif /* ATSplashDelegate_h */
