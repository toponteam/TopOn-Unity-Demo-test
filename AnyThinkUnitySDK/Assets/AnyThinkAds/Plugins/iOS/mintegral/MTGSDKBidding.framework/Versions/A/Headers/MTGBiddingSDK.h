//
//  MTGBiddingSDK.h
//  MTGSDKBidding
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>



#define MTGBiddingSDKVersion @"6.6.1"



@interface MTGBiddingSDK : NSObject

/* BuyerUID is required when you decide to request a bid response on your own server. */
+ (NSString *)buyerUID;

@end

