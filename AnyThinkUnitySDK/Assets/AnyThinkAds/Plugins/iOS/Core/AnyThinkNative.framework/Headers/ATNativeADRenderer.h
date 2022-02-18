//
//  ATNativeADRenderer.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 20/04/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#ifndef ATNativeADRenderer_h
#define ATNativeADRenderer_h
#import <UIKit/UIKit.h>
#import "ATNativeADView.h"

@class ATNativeADConfiguration;
@class ATNativeADCache;
@protocol ATNativeADRenderer<NSObject>
/**
 Might return nil.
 */
-(__kindof UIView*)createMediaView;

/**
 Render the assets onto the the associated AD view. Adopter implements this method in a network-specific way.
 */
-(void) renderOffer:(ATNativeADCache*)offer;

/**
 * Whether the ad being rendered is video ad.
 */
-(BOOL)isVideoContents;

/**
 * The duration of the video ad playing, unit ms
 */
- (CGFloat)videoPlayTime;
/**
 * Video ad duration, unit ms
 */
- (CGFloat)videoDuration;
/**
 Play mute switch
 @param flag whether to mute
 */
- (void)muteEnable:(BOOL)flag;
/**
 * The video ad play
 */
- (void)videoPlay;
/**
 * The video ad pause
 */
- (void)videoPause;

- (ATNativeAdType)getNativeAdType;

- (ATNativeAdRenderType)getCurrentNativeAdRenderType;

/**
 This reference to the associated AD view should be kept as a weak one, for an AD view strongly keeps its render.
 This property is added so that the renderAssets: method can access it directly.
 */
@property(nonatomic, weak) ATNativeADView *ADView;

@end

#endif /* ATNativeADRenderer_h */
