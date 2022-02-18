//
//  ATNativeAdOffer.h
//  AnyThinkNative
//
//  Created by Topon on 10/27/21.
//  Copyright © 2021 AnyThink. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ATNativeAd.h"

NS_ASSUME_NONNULL_BEGIN

@class ATNativeADView;
@class ATNativeADConfiguration;
@interface ATNativeAdOffer : NSObject
/**
 * The native ad that is being shown.
 */
@property(nonatomic, readonly) ATNativeAd *nativeAd;
/**
 * The adOffer info of native ad.
 */
@property(nonatomic, readonly) NSDictionary *adOfferInfo;
/**
 * The ATNativeADView info of native ad.
 */
@property(nonatomic, readonly) ATNativeADView *adView;

-(__kindof UIView *) rendererWithConfiguration:(ATNativeADConfiguration*)configuration;

@end

NS_ASSUME_NONNULL_END
