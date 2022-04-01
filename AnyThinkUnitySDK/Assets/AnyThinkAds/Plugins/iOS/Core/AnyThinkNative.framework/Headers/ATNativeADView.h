//
//  ATNativeADView.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 18/04/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ATNativeAd.h"
#import "ATNativeRendering.h"

typedef NS_ENUM(NSInteger, ATNativeAdRenderType) {
    ATNativeAdRenderSelfRender = 1,
    ATNativeAdRenderExpress = 2
};

typedef NS_ENUM(NSInteger, ATNativeAdType) {
    ATNativeAdTypeFeed = 1,
    ATNativeAdTypePaster = 2
};

typedef NS_ENUM(NSInteger, ATPlayerStatus) {
    ATPlayerStatusStartPlay,
    ATPlayerStatusPause,
    ATPlayerStatusFinish,
    ATPlayerStatusResume,
    ATPlayerStatusAbort
};


typedef NS_ENUM(NSInteger, ATStartAppNativeAdImageSize) {
    AT_SIZE_72X72      = 0,
    AT_SIZE_100X100    = 1,
    /// Default size
    AT_SIZE_150X150    = 2,
    AT_SIZE_340X340    = 3,
    /// Not supported by secondaryImageSize, default will be used instead
    AT_SIZE_1200X628   = 4,
};

/**
 * Subclasses are expected to call super when overriding willMoveToSuperview: because it it within this method the base class kick off the rendering process.
 */
@protocol ATNativeADDelegate;
@protocol ATNativeADRenderer;
@class ATNativeADConfiguration;
@interface ATNativeADView : UIView<ATNativeRendering>
/**
 * Subclass implementation has to call [super initSubviews] for the ad view to work properly. By the time this method's called, the ad view is not yet full fledged.
 */
-(void) initSubviews;

/**
 * Create constraints for subviews in this method if you are using autolayout.
 */
-(void) makeConstraintsForSubviews;

/**
 * During ad refreshing, the media view might be removed from it's superview and recreated and added; so the layout logic for media view might be called multiple times with different media views. You're recommended to use frame-based technique here.
 */
-(void) layoutMediaView;

/**
 * Whether the ad being shown is a video ad.
 */
-(BOOL) isVideoContents;

/*
 * ALWAYS call this method to retrieve the REAL rendered adview.
 */
-(ATNativeADView*)embededAdView;

/**
 * Returns an array containing views that are used to track clicks.
 */
-(NSArray<UIView*>*)clickableViews;

@property(nonatomic, weak) id<ATNativeADDelegate> delegate;
/**
 * The view that is used to play video or other media; it is set by the sdk; might be nil.
 */
@property(nonatomic, nullable) UIView *mediaView;
/**
 * The native ad that is being shown.
 */
@property(nonatomic, readonly) ATNativeAd *nativeAd;
/**
 * The networkFirm id of native ad.
 */
@property(nonatomic, readonly) NSInteger networkFirmID;

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
/**
 * The native ad type
 */
- (ATNativeAdType)getNativeAdType;
/**
 * The native ad render type
 */
- (ATNativeAdRenderType)getCurrentNativeAdRenderType;

- (void)recordCustomPlayerStatus:(ATPlayerStatus)status currentTime:(NSTimeInterval)time;

@end

//Defined for TT native
extern NSString const* kATExtraNativeImageSize228_150;
extern NSString const* kATExtraNativeImageSize690_388;
extern NSString *const kATExtraNativeImageSizeKey;
extern NSString const* kATExtraNativeImageSize1280_720;
extern NSString const* kATExtraNativeImageSize1200_628;
extern NSString const* kATExtraNativeImageSize640_640;
extern NSString *const kATExtraStartAPPNativeMainImageSizeKey;
extern NSString *const kATExtraNativeIconImageSizeKey;

@interface ATNativeADView(DrawVideo)
/*
 * Override this method to layout draw video assets.
 */
-(void) makeConstraintsDrawVideoAssets;
@property (nonatomic, strong, readonly, nullable) UIButton *dislikeButton;
@property (nonatomic, strong, readonly, nullable) UILabel *adLabel;
@property (nonatomic, strong, readonly, nullable) UIImageView *logoImageView;
@property (nonatomic, strong, readonly, nullable) UIImageView *logoADImageView;
@property (nonatomic, strong, readonly, nullable) UIView *videoAdView;
@end
