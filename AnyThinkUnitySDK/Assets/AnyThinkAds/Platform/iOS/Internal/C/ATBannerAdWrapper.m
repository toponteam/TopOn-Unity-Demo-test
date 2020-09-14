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

@interface ATBannerAdWrapper()<ATBannerDelegate>
@property(nonatomic, readonly) NSMutableDictionary<NSString*, ATBannerView*> *bannerViewStorage;
@property(nonatomic, readonly) BOOL interstitialOrRVBeingShown;
@end

static NSString *kATBannerSizeUsesPixelFlagKey = @"uses_pixel";
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
        if (extraDict[kATAdLoadingExtraAdmobBannerSizeKey] != nil) { extra[kATAdLoadingExtraAdmobBannerSizeKey] = extraDict[kATAdLoadingExtraAdmobBannerSizeKey]; }
        if (extraDict[kATAdLoadingExtraAdmobAnchoredAdaptiveKey] != nil) { extra[kATAdLoadingExtraAdmobAnchoredAdaptiveKey] = extraDict[kATAdLoadingExtraAdmobAnchoredAdaptiveKey]; }
    }
    if (extra[kATAdLoadingExtraBannerAdSizeKey] == nil) {
        extra[kATAdLoadingExtraBannerAdSizeKey] = [NSValue valueWithCGSize:CGSizeMake(320.0f, 50.0f)];
    }
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:extra delegate:self];
}

UIEdgeInsets SafeAreaInsets_ATUnityBanner() {
    return ([[UIApplication sharedApplication].keyWindow respondsToSelector:@selector(safeAreaInsets)] ? [UIApplication sharedApplication].keyWindow.safeAreaInsets : UIEdgeInsetsZero);
}

-(void) showBannerAdWithPlacementID:(NSString*)placementID rect:(NSString*)rect {
    dispatch_async(dispatch_get_main_queue(), ^{
        if ([rect isKindOfClass:[NSString class]] && [rect dataUsingEncoding:NSUTF8StringEncoding] != nil) {
            NSDictionary *rectDict = [NSJSONSerialization JSONObjectWithData:[rect dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
            NSLog(@"rectDict = %@", rectDict);
            CGFloat scale = [rectDict[kATBannerSizeUsesPixelFlagKey] boolValue] ? [UIScreen mainScreen].nativeScale : 1.0f;
            ATBannerView *bannerView = [[ATAdManager sharedManager] retrieveBannerViewForPlacementID:placementID];
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
            
            bannerCointainer.layer.borderColor = [UIColor redColor].CGColor;
            bannerCointainer.layer.borderWidth = .5f;
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
