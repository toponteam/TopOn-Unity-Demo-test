//
//  ATNativeAdWrapper.m
//  UnityContainer
//
//  Created by Martin Lau on 27/07/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import "ATNativeAdWrapper.h"
#import "ATUnityUtilities.h"
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <AnyThinkNative/ATAdManager+Native.h>
#import <AnyThinkNative/ATNativeAdConfiguration.h>
#import <AnyThinkNative/ATNativeADView.h>
#import "MTAutolayoutCategories.h"
#import "ATUnityManager.h"

static NSString *const kParsedPropertiesFrameKey = @"frame";
static NSString *const kParsedPropertiesBackgroundColorKey = @"background_color";
static NSString *const kParsedPropertiesTextColorKey = @"text_color";
static NSString *const kParsedPropertiesTextSizeKey = @"text_size";

static NSString *const kNativeAssetAdvertiser = @"advertiser_label";
static NSString *const kNativeAssetText = @"text";
static NSString *const kNativeAssetTitle = @"title";
static NSString *const kNativeAssetCta = @"cta";
static NSString *const kNativeAssetRating = @"rating";
static NSString *const kNativeAssetIcon = @"icon";
static NSString *const kNativeAssetMainImage = @"main_image";
static NSString *const kNativeAssetSponsorImage = @"sponsor_image";
static NSString *const kNativeAssetMedia = @"media";

NSDictionary* parseUnityProperties(NSDictionary *properties) {
    NSMutableDictionary *result = NSMutableDictionary.dictionary;
    CGFloat scale = [properties[@"usesPixel"] boolValue] ? [UIScreen mainScreen].scale : 1.0f;
    result[kParsedPropertiesFrameKey] = [NSString stringWithFormat:@"{{%@, %@}, {%@, %@}}", @([properties[@"x"] doubleValue] / scale), @([properties[@"y"] doubleValue] / scale), @([properties[@"width"] doubleValue] / scale), @([properties[@"height"] doubleValue] / scale)];
    result[kParsedPropertiesBackgroundColorKey] = properties[@"backgroundColor"];
    result[kParsedPropertiesTextColorKey] = properties[@"textColor"];
    result[kParsedPropertiesTextSizeKey] = properties[@"textSize"];
    
    return result;
}

NSDictionary* parseUnityMetrics(NSDictionary* metrics) {
    NSMutableDictionary *result = NSMutableDictionary.dictionary;
    NSDictionary *keysMap = @{@"appIcon":kNativeAssetIcon, @"mainImage":kNativeAssetMainImage, @"title":kNativeAssetTitle, @"desc":kNativeAssetText, @"adLogo":kNativeAssetSponsorImage, @"cta":kNativeAssetCta};
    [keysMap enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, id  _Nonnull obj, BOOL * _Nonnull stop) { result[keysMap[key]] = parseUnityProperties(metrics[key]); }];
    return result;
}

@interface ATUnityNativeAdView:ATNativeADView
@property(nonatomic, readonly) UILabel *advertiserLabel;
@property(nonatomic, readonly) UILabel *textLabel;
@property(nonatomic, readonly) UILabel *titleLabel;
@property(nonatomic, readonly) UILabel *ctaLabel;
@property(nonatomic, readonly) UILabel *ratingLabel;
@property(nonatomic, readonly) UIImageView *iconImageView;
@property(nonatomic, readonly) UIImageView *mainImageView;
@property(nonatomic, readonly) UIImageView *sponsorImageView;
-(void) configureMetrics:(NSDictionary<NSString*, NSString*>*)metrics;
@end

@implementation ATUnityNativeAdView
-(void) initSubviews {
    [super initSubviews];
    _advertiserLabel = [UILabel autolayoutLabelFont:[UIFont boldSystemFontOfSize:15.0f] textColor:[UIColor blackColor] textAlignment:NSTextAlignmentLeft];
    [self addSubview:_advertiserLabel];
    
    _titleLabel = [UILabel autolayoutLabelFont:[UIFont boldSystemFontOfSize:18.0f] textColor:[UIColor blackColor] textAlignment:NSTextAlignmentLeft];
    [self addSubview:_titleLabel];
    
    _textLabel = [UILabel autolayoutLabelFont:[UIFont systemFontOfSize:12.0f] textColor:[UIColor blackColor]];
    _textLabel.numberOfLines = 2;
    [self addSubview:_textLabel];
    
    _ctaLabel = [UILabel autolayoutLabelFont:[UIFont systemFontOfSize:15.0f] textColor:[UIColor blackColor]];
    _ctaLabel.textAlignment = NSTextAlignmentCenter;
    [self addSubview:_ctaLabel];
    
    _ratingLabel = [UILabel autolayoutLabelFont:[UIFont systemFontOfSize:15.0f] textColor:[UIColor blackColor]];
    [self addSubview:_ratingLabel];
    
    _iconImageView = [UIImageView autolayoutView];
    _iconImageView.layer.cornerRadius = 4.0f;
    _iconImageView.layer.masksToBounds = YES;
    _iconImageView.contentMode = UIViewContentModeScaleAspectFit;
    [self addSubview:_iconImageView];
    
    _mainImageView = [UIImageView autolayoutView];
    _mainImageView.contentMode = UIViewContentModeScaleAspectFit;
    [self addSubview:_mainImageView];
    
    _sponsorImageView = [UIImageView autolayoutView];
    _sponsorImageView.contentMode = UIViewContentModeScaleAspectFit;
    [self addSubview:_sponsorImageView];
}

-(NSArray<UIView*>*)clickableViews {
    NSMutableArray *clickableViews = [NSMutableArray arrayWithObjects:_iconImageView, _ctaLabel, nil];
    if (self.mediaView != nil) { [clickableViews addObject:self.mediaView]; }
    return clickableViews;
}

-(void) configureMetrics:(NSDictionary *)metrics {
    NSDictionary<NSString*, UIView*> *views = @{kNativeAssetTitle:_titleLabel, kNativeAssetText:_textLabel, kNativeAssetCta:_ctaLabel, kNativeAssetRating:_ratingLabel, kNativeAssetAdvertiser:_advertiserLabel, kNativeAssetIcon:_iconImageView, kNativeAssetMainImage:_mainImageView, kNativeAssetSponsorImage:_sponsorImageView};
    [views enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, id  _Nonnull obj, BOOL * _Nonnull stop) {
        CGRect frame = CGRectFromString(metrics[key][kParsedPropertiesFrameKey]);
        [self addConstraintsWithVisualFormat:[NSString stringWithFormat:@"|-x-[%@(w)]", key] options:0 metrics:@{@"x":@(frame.origin.x), @"w":@(frame.size.width)} views:views];
        [self addConstraintsWithVisualFormat:[NSString stringWithFormat:@"V:|-y-[%@(h)]", key] options:0 metrics:@{@"y":@(frame.origin.y), @"h":@(frame.size.height)} views:views];
        if ([obj respondsToSelector:@selector(setBackgroundColor:)] && [metrics[key] containsObjectForKey:@"background_color"]) [obj setBackgroundColor:[metrics[key][@"background_color"] hasPrefix:@"#"] ? [UIColor colorWithHexString:metrics[key][@"background_color"]] : [UIColor clearColor]];
        if ([obj respondsToSelector:@selector(setTextColor:)] && [metrics[key] containsObjectForKey:@"text_color"]) [obj setTextColor:[UIColor colorWithHexString:metrics[key][@"text_color"]]];
        if ([obj respondsToSelector:@selector(setFont:)] && [metrics[key] containsObjectForKey:@"text_size"] && [metrics[key][@"text_size"] respondsToSelector:@selector(doubleValue)]) [obj setFont:[UIFont systemFontOfSize:[metrics[key][@"text_size"] doubleValue]]];
    }];
    if ([metrics containsObjectForKey:kNativeAssetMedia]) self.mediaView.frame = CGRectFromString(metrics[kNativeAssetMedia][kParsedPropertiesFrameKey]);
    else self.mediaView.frame = CGRectFromString(metrics[kNativeAssetMainImage][kParsedPropertiesFrameKey]);
}
@end

#define CS_ATNativeAdWrapper "ATNativeAdWrapper"
@interface ATNativeAdWrapper()
@property(nonatomic, readonly) NSMutableDictionary<NSString*, UIView*> *viewsStorage;
@end
@implementation ATNativeAdWrapper
+(instancetype)sharedInstance {
    static ATNativeAdWrapper *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[ATNativeAdWrapper alloc] init];
    });
    return sharedInstance;
}

-(instancetype) init {
    self = [super init];
    if (self != nil) _viewsStorage = [NSMutableDictionary<NSString*, UIView*> dictionary];
    return self;
}

-(void) loadNativeAdWithPlacementID:(NSString*)placementID customDataJSONString:(NSString*)customDataJSONString callback:(void(*)(const char*, const char *))callback {
    [self setCallBack:callback forKey:placementID];
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:@{kExtraInfoNativeAdTypeKey:@(ATGDTNativeAdTypeSelfRendering), kATExtraNativeImageSizeKey:kATExtraNativeImageSize690_388} customData:([customDataJSONString isKindOfClass:[NSString class]] && [customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] != nil) ? [NSJSONSerialization JSONObjectWithData:[customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil] : nil delegate:self];
}

-(BOOL) isNativeAdReadyForPlacementID:(NSString*)placementID {
    return [[ATAdManager sharedManager] nativeAdReadyForPlacementID:placementID];
}

-(void) showNativeAdWithPlacementID:(NSString*)placementID metricsJSONString:(NSString*)metricsJSONString {
    if ([self isNativeAdReadyForPlacementID:placementID]) {
        NSDictionary *metrics = [NSJSONSerialization JSONObjectWithData:[metricsJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        NSDictionary *parsedMetrics = parseUnityMetrics(metrics);
        
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button addTarget:self action:@selector(noop) forControlEvents:UIControlEventTouchUpInside];
        button.frame = CGRectFromString(parseUnityProperties(metrics[@"parent"])[kParsedPropertiesFrameKey]);
        _viewsStorage[placementID] = button;
        
        ATNativeADConfiguration *configuration = [ATNativeADConfiguration new];
        configuration.ADFrame = button.bounds;
        configuration.renderingViewClass = [ATUnityNativeAdView class];
        configuration.delegate = self;
        configuration.rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
        ATUnityNativeAdView *adview = [[ATAdManager sharedManager] retriveAdViewWithPlacementID:placementID configuration:configuration];
        adview.ctaLabel.hidden = [adview.nativeAd.ctaText length] == 0;
        if (adview != nil) {
            if ([adview respondsToSelector:@selector(configureMetrics:)]) {
                [adview configureMetrics:parsedMetrics];
            } else {
                [adview.subviews enumerateObjectsUsingBlock:^(__kindof UIView * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
                    if ([obj isKindOfClass:[ATUnityNativeAdView class]]) {
                        [(ATUnityNativeAdView*)obj configureMetrics:parsedMetrics];
                        *stop = YES;
                    }
                }];
            }
            [button addSubview:adview];
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:button];
        }
    }
}

-(void) noop {
    
}

-(void) removeNativeAdViewWithPlacementID:(NSString*)placementID {
    
    [_viewsStorage[placementID] removeFromSuperview];
}

-(void) clearCache {
    [[ATAdManager sharedManager] clearCache];
}

-(NSString*) scriptWrapperClass {
    return @"ATNativeAdWrapper";
}
#pragma mark - delegate
-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    [self invokeCallback:@"OnNativeAdLoaded" placementID:placementID error:nil extra:nil];
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error {
    [self invokeCallback:@"OnNativeAdLoadingFailure" placementID:placementID error:error extra:nil];
}

-(void) didShowNativeAdInAdView:(ATNativeADView*)adView placementID:(NSString*)placementID {
    [self invokeCallback:@"OnNaitveAdShow" placementID:placementID error:nil extra:nil];
}
    
-(void) didClickNativeAdInAdView:(ATNativeADView*)adView placementID:(NSString*)placementID {
    //Drop ad view
    [self invokeCallback:@"OnNativeAdClick" placementID:placementID error:nil extra:nil];
}
    
-(void) didStartPlayingVideoInAdView:(ATNativeADView*)adView placementID:(NSString*)placementID {
    //Drop ad view
    [self invokeCallback:@"OnNativeAdVideoStart" placementID:placementID error:nil extra:nil];
}
    
-(void) didEndPlayingVideoInAdView:(ATNativeADView*)adView placementID:(NSString*)placementID {
    //Drop ad view
    [self invokeCallback:@"OnNativeAdVideoEnd" placementID:placementID error:nil extra:nil];
}
@end
