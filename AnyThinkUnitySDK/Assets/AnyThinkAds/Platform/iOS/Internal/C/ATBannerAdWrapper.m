//
//  ATBannerAdWrapper.m
//  UnityContainer
//
//  Created by Martin Lau on 2019/1/8.
//  Copyright © 2019 Martin Lau. All rights reserved.
//

#import "ATBannerAdWrapper.h"
#import <AnyThinkBanner/AnyThinkBanner.h>
#import "ATUnityUtilities.h"
//5.6.6版本以上支持 admob 自适应banner （用到时再import该头文件）
//#import <GoogleMobileAds/GoogleMobileAds.h>

@interface ATBannerAdWrapper()<ATBannerDelegate>
@property(nonatomic, readonly) NSMutableDictionary<NSString*, ATBannerView*> *bannerViewStorage;
@property(nonatomic, readonly) BOOL interstitialOrRVBeingShown;
@end

static NSString *kATBannerSizeUsesPixelFlagKey = @"uses_pixel";
static NSString *kATBannerAdLoadingExtraInlineAdaptiveWidthKey = @"inline_adaptive_width";
static NSString *kATBannerAdLoadingExtraInlineAdaptiveOrientationKey = @"inline_adaptive_orientation";

@implementation ATBannerAdWrapper
+(instancetype)sharedInstance {
    static ATBannerAdWrapper *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[ATBannerAdWrapper alloc] init];
    });
    return sharedInstance;
}

-(instancetype) init {
    self = [super init];
    if (self != nil) {
        _bannerViewStorage = [NSMutableDictionary<NSString*, ATBannerView*> dictionary];
    }
    return self;
}

-(NSString*) scriptWrapperClass {
    return @"ATBannerAdWrapper";
}

- (id)selWrapperClassWithDict:(NSDictionary *)dict callback:(void(*)(const char*, const char*))callback {
    NSString *selector = dict[@"selector"];
    NSArray<NSString*>* arguments = dict[@"arguments"];
    NSString *firstObject = @"";
    NSString *secondObject = @"";
    NSString *lastObject = @"";
    if (![ATUnityUtilities isEmpty:arguments]) {
        for (int i = 0; i < arguments.count; i++) {
            if (i == 0) { firstObject = arguments[i]; }
            else if (i == 1) { secondObject = arguments[i]; }
            else { lastObject = arguments[i]; }
        }
    }
    
    if ([selector isEqualToString:@"loadBannerAdWithPlacementID:customDataJSONString:callback:"]) {
        [self loadBannerAdWithPlacementID:firstObject customDataJSONString:secondObject callback:callback];
    } else if ([selector isEqualToString:@"showBannerAdWithPlacementID:rect:extraJsonString:"]) {
        [self showBannerAdWithPlacementID:firstObject rect:secondObject extraJsonString:lastObject];
    } else if ([selector isEqualToString:@"removeBannerAdWithPlacementID:"]) {
        [self removeBannerAdWithPlacementID:firstObject];
    } else if ([selector isEqualToString:@"showBannerAdWithPlacementID:"]) {
        [self showBannerAdWithPlacementID:firstObject];
    } else if ([selector isEqualToString:@"hideBannerAdWithPlacementID:"]) {
        [self hideBannerAdWithPlacementID:firstObject];
    } else if ([selector isEqualToString:@"checkAdStatus:"]) {
        return [self checkAdStatus:firstObject];
    }   else if ([selector isEqualToString:@"clearCache"]) {
        [self clearCache];
    }   else if ([selector isEqualToString:@"getValidAdCaches:"]) {
        return [self getValidAdCaches:firstObject];
    }
    return nil;
}

-(void) loadBannerAdWithPlacementID:(NSString*)placementID customDataJSONString:(NSString*)customDataJSONString callback:(void(*)(const char*, const char*))callback {
    [self setCallBack:callback forKey:placementID];
    NSMutableDictionary *extra = [NSMutableDictionary dictionary];
    if ([customDataJSONString isKindOfClass:[NSString class]] && [customDataJSONString length] > 0) {
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithData:[customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
        NSLog(@"extraDict = %@", extraDict);
        CGFloat scale = [extraDict[kATBannerSizeUsesPixelFlagKey] boolValue] ? [UIScreen mainScreen].nativeScale : 1.0f;
        if ([extraDict[kATAdLoadingExtraBannerAdSizeKey] isKindOfClass:[NSString class]] && [[extraDict[kATAdLoadingExtraBannerAdSizeKey] componentsSeparatedByString:@"x"] count] == 2) {
            NSArray<NSString*>* com = [extraDict[kATAdLoadingExtraBannerAdSizeKey] componentsSeparatedByString:@"x"];

            extra[kATAdLoadingExtraBannerAdSizeKey] = [NSValue valueWithCGSize:CGSizeMake([com[0] doubleValue] / scale, [com[1] doubleValue] / scale)];
        }
        
//        // admob 自适应banner，5.6.6版本以上支持
//        if (extraDict[kATBannerAdLoadingExtraInlineAdaptiveWidthKey] != nil && extraDict[kATBannerAdLoadingExtraInlineAdaptiveOrientationKey] != nil) {
//            //GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth 自适应
//            //GADPortraitAnchoredAdaptiveBannerAdSizeWithWidth 竖屏
//            //GADLandscapeAnchoredAdaptiveBannerAdSizeWithWidth 横屏
//            CGFloat admobBannerWidth = [extraDict[kATBannerAdLoadingExtraInlineAdaptiveWidthKey] doubleValue];
//            GADAdSize admobSize;
//            if ([extraDict[kATBannerAdLoadingExtraInlineAdaptiveOrientationKey] integerValue] == 1) {
//                admobSize = GADPortraitAnchoredAdaptiveBannerAdSizeWithWidth(admobBannerWidth);
//            } else if ([extraDict[kATBannerAdLoadingExtraInlineAdaptiveOrientationKey] integerValue] == 2) {
//                admobSize = GADLandscapeAnchoredAdaptiveBannerAdSizeWithWidth(admobBannerWidth);
//            } else {
//                admobSize = GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(admobBannerWidth);
//            }
//
//            extra[kATAdLoadingExtraAdmobBannerSizeKey] = [NSValue valueWithCGSize:admobSize.size];
//            extra[kATAdLoadingExtraAdmobAdSizeFlagsKey] = @(admobSize.flags);
//        }
    }
    if (extra[kATAdLoadingExtraBannerAdSizeKey] == nil) {
        extra[kATAdLoadingExtraBannerAdSizeKey] = [NSValue valueWithCGSize:CGSizeMake(320.0f, 50.0f)];
    }
    NSLog(@"extra = %@", extra);
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:extra delegate:self];
}

-(NSString*) checkAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkBannerLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATBannerAdWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString;
}

-(NSString*) getValidAdCaches:(NSString *)placementID {
    NSArray *array = [[ATAdManager sharedManager] getBannerValidAdsForPlacementID:placementID];
    NSLog(@"ATNativeAdWrapper::array = %@", array);
    return array.jsonString;
}

UIEdgeInsets SafeAreaInsets_ATUnityBanner() {
    return ([[UIApplication sharedApplication].keyWindow respondsToSelector:@selector(safeAreaInsets)] ? [UIApplication sharedApplication].keyWindow.safeAreaInsets : UIEdgeInsetsZero);
}

-(void) showBannerAdWithPlacementID:(NSString*)placementID rect:(NSString*)rect extraJsonString:(NSString*)extraJsonString {
    dispatch_async(dispatch_get_main_queue(), ^{
        if ([rect isKindOfClass:[NSString class]] && [rect dataUsingEncoding:NSUTF8StringEncoding] != nil) {
            NSDictionary *extraDict = ([extraJsonString isKindOfClass:[NSString class]] && [extraJsonString dataUsingEncoding:NSUTF8StringEncoding] != nil) ? [NSJSONSerialization JSONObjectWithData:[extraJsonString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil] : nil;
            
            NSDictionary *rectDict = [NSJSONSerialization JSONObjectWithData:[rect dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
            NSLog(@"rectDict = %@", rectDict);
            CGFloat scale = [rectDict[kATBannerSizeUsesPixelFlagKey] boolValue] ? [UIScreen mainScreen].nativeScale : 1.0f;
            ATBannerView *bannerView = [[ATAdManager sharedManager] retrieveBannerViewForPlacementID:placementID scene:extraDict[kATUnityUtilitiesAdShowingExtraScenarioKey]];
            bannerView.delegate = self;
            UIButton *bannerCointainer = [UIButton buttonWithType:UIButtonTypeCustom];
            [bannerCointainer addTarget:self action:@selector(noop) forControlEvents:UIControlEventTouchUpInside];
            
            NSString *position = rectDict[@"position"];
            CGSize totalSize = [UIApplication sharedApplication].keyWindow.rootViewController.view.bounds.size;
            UIEdgeInsets safeAreaInsets = SafeAreaInsets_ATUnityBanner();
            if ([@"top" isEqualToString:position]) {
                bannerCointainer.frame = CGRectMake((totalSize.width - CGRectGetWidth(bannerView.bounds)) / 2.0f, safeAreaInsets.top , CGRectGetWidth(bannerView.bounds), CGRectGetHeight(bannerView.bounds));
            } else if ([@"bottom" isEqualToString:position]) {
                bannerCointainer.frame = CGRectMake((totalSize.width - CGRectGetWidth(bannerView.bounds)) / 2.0f, totalSize.height - safeAreaInsets.bottom - CGRectGetHeight(bannerView.bounds) , CGRectGetWidth(bannerView.bounds), CGRectGetHeight(bannerView.bounds));
            } else {
                bannerCointainer.frame = CGRectMake([rectDict[@"x"] doubleValue] / scale, [rectDict[@"y"] doubleValue] / scale, [rectDict[@"width"] doubleValue] / scale, [rectDict[@"height"] doubleValue] / scale);
            }
            
            bannerView.frame = bannerCointainer.bounds;
            [bannerCointainer addSubview:bannerView];
            
//            bannerCointainer.layer.borderColor = [UIColor redColor].CGColor;
//            bannerCointainer.layer.borderWidth = .5f;
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:bannerCointainer];
            self->_bannerViewStorage[placementID] = bannerCointainer;
        }
    });
}

-(void) noop {
    
}

-(void) removeBannerAdWithPlacementID:(NSString*)placementID {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self->_bannerViewStorage[placementID] removeFromSuperview];
        [self->_bannerViewStorage removeObjectForKey:placementID];
    });
}

-(void) showBannerAdWithPlacementID:(NSString*)placementID {
    dispatch_async(dispatch_get_main_queue(), ^{
        ATBannerView *bannerView = self->_bannerViewStorage[placementID];
        if (bannerView.superview != nil && !_interstitialOrRVBeingShown) { bannerView.hidden = NO; }
    });
}

-(void) hideBannerAdWithPlacementID:(NSString*)placementID {
    dispatch_async(dispatch_get_main_queue(), ^{
        ATBannerView *bannerView = self->_bannerViewStorage[placementID];
        if (bannerView.superview != nil) { bannerView.hidden = YES; }
    });
}

-(void) clearCache {
    [[ATAdManager sharedManager] clearCache];
}

#pragma mark - banner delegate method(s)
-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    [self invokeCallback:@"OnBannerAdLoad" placementID:placementID error:nil extra:nil];
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error {
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to load ad", NSLocalizedFailureReasonErrorKey:@"AT has failed to load ad"}];
    [self invokeCallback:@"OnBannerAdLoadFail" placementID:placementID error:error extra:nil];
}
// ad
- (void)didStartLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"startLoadingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFinishLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"finishLoadingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFailToLoadADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    [self invokeCallback:@"failToLoadADSource" placementID:placementID error:error extra:extra];
}

// bidding
- (void)didStartBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"startBiddingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFinishBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    [self invokeCallback:@"finishBiddingADSource" placementID:placementID error:nil extra:extra];
}

- (void)didFailBiddingADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    [self invokeCallback:@"failBiddingADSource" placementID:placementID error:error extra:extra];
}

-(void) bannerView:(ATBannerView *)bannerView didShowAdWithPlacementID:(NSString *)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnBannerAdImpress" placementID:placementID error:nil extra:extra];
}

-(void) bannerView:(ATBannerView*)bannerView didClickWithPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnBannerAdClick" placementID:placementID error:nil extra:extra];
}

-(void) bannerView:(ATBannerView *)bannerView didTapCloseButtonWithPlacementID:(NSString *)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnBannerAdCloseButtonTapped" placementID:placementID error:nil extra:extra];
}

-(void) bannerView:(ATBannerView*)bannerView didCloseWithPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnBannerAdClose" placementID:placementID error:nil extra:extra];
}

-(void) bannerView:(ATBannerView *)bannerView didAutoRefreshWithPlacement:(NSString *)placementID extra:(NSDictionary *)extra {
    [self invokeCallback:@"OnBannerAdAutoRefresh" placementID:placementID error:nil extra:extra];
}

-(void) bannerView:(ATBannerView *)bannerView failedToAutoRefreshWithPlacementID:(NSString *)placementID error:(NSError *)error {
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to refresh ad", NSLocalizedFailureReasonErrorKey:@"AT has failed to refresh ad"}];
    [self invokeCallback:@"OnBannerAdAutoRefreshFail" placementID:placementID error:error extra:nil];
}

@end
