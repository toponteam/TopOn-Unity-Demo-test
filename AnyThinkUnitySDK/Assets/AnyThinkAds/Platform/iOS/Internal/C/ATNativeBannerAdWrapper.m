//
//  ATNativeBannerAdWrapper.m
//  UnityContainer
//
//  Created by Martin Lau on 2019/4/23.
//  Copyright © 2019 Martin Lau. All rights reserved.
//

#import "ATNativeBannerAdWrapper.h"
#import <AnyThinkNative/ATNativeBannerWrapper.h>
#import "ATUnityUtilities.h"
#import <objc/runtime.h>
@interface UIViewController(ATUtilities)
-(void)AT_presentViewController:(UIViewController *)viewControllerToPresent animated:(BOOL)flag completion:(void (^)(void))completion;
-(void) AT_dismissViewControllerAnimated:(BOOL)flag completion:(void (^)(void))completion;
@end

@interface ATNativeBannerAdWrapper()<ATNativeBannerDelegate>
@property(nonatomic, readonly) NSMutableDictionary<NSString*, UIView*> *nativeBannerAdViews;
@end

static NSString *const kATSharedCallbackKey = @"placement_id_placement_holder";
@implementation ATNativeBannerAdWrapper
+(void) swizzleMethodWithSelector:(SEL)originalSel swizzledMethodSelector:(SEL)swizzledMethodSel inClass:(Class)inClass {
    if (originalSel != NULL && swizzledMethodSel != NULL && inClass != nil) {
        Method originalMethod = class_getInstanceMethod(inClass, originalSel);
        Method swizzledMethod = class_getInstanceMethod(inClass, swizzledMethodSel);
        
        BOOL didAddMethod = class_addMethod(inClass, originalSel, method_getImplementation(swizzledMethod), method_getTypeEncoding(swizzledMethod));
        
        if (didAddMethod) {
            class_replaceMethod(inClass, swizzledMethodSel, method_getImplementation(originalMethod), method_getTypeEncoding(originalMethod));
        } else {
            method_exchangeImplementations(originalMethod, swizzledMethod);
        }
    }
}

+(instancetype)sharedInstance {
    static ATNativeBannerAdWrapper *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[ATNativeBannerAdWrapper alloc] init];
    });
    return sharedInstance;
}

-(instancetype) init {
    self = [super init];
    if (self != nil) {
        _nativeBannerAdViews = [NSMutableDictionary dictionary];
        [ATNativeBannerAdWrapper swizzleMethodWithSelector:@selector(presentViewController:animated:completion:) swizzledMethodSelector:@selector(AT_presentViewController:animated:completion:) inClass:[UIViewController class]];
        [ATNativeBannerAdWrapper swizzleMethodWithSelector:@selector(dismissViewControllerAnimated:completion:) swizzledMethodSelector:@selector(AT_dismissViewControllerAnimated:completion:) inClass:[UIViewController class]];
    }
    return self;
}

-(NSString*) scriptWrapperClass {
    return @"ATNativeBannerAdWrapper";
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
    
    if ([selector isEqualToString:@"loadNativeBannerAdWithPlacementID:customDataJSONString:callback:"]) {
        [self loadNativeBannerAdWithPlacementID:firstObject customDataJSONString:secondObject callback:callback];
    } else if ([selector isEqualToString:@"isNativeBannerAdReadyForPlacementID:"]) {
        return [NSNumber numberWithBool:[self isNativeBannerAdReadyForPlacementID:firstObject]];
    } else if ([selector isEqualToString:@"showNativeBannerAdWithPlacementID:rect:extra:"]) {
        [self showNativeBannerAdWithPlacementID:firstObject rect:secondObject extra:lastObject];
    } else if ([selector isEqualToString:@"removeNativeBannerAdWithPlacementID:"]) {
        [self removeNativeBannerAdWithPlacementID:firstObject];
    } else if ([selector isEqualToString:@"showNativeBannerAdWithPlacementID:"]) {
        [self showNativeBannerAdWithPlacementID:firstObject];
    } else if ([selector isEqualToString:@"hideNativeBannerAdWithPlacementID:"]) {
        [self hideNativeBannerAdWithPlacementID:firstObject];
    }
    return nil;
}

-(void) noop {
    
}

-(void) showNativeBannerAdWithPlacementID:(NSString*)placementID rect:(NSString*)rect extra:(NSString*)extraStr {
    dispatch_async(dispatch_get_main_queue(), ^{
        if ([rect isKindOfClass:[NSString class]] && [rect dataUsingEncoding:NSUTF8StringEncoding] != nil) {
            NSDictionary *rectDict = [NSJSONSerialization JSONObjectWithData:[rect dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
            NSMutableDictionary *extra = [NSMutableDictionary dictionaryWithDictionary:@{kATExtraInfoNativeAdTypeKey:@(ATGDTNativeAdTypeSelfRendering), kATNativeBannerAdShowingExtraAdSizeKey:[NSValue valueWithCGSize:CGSizeMake([rectDict[@"width"] doubleValue], [rectDict[@"height"] doubleValue])], kATExtraNativeImageSizeKey:kATExtraNativeImageSize690_388, kATNativeBannerAdShowingExtraBackgroundColorKey:[UIColor whiteColor]}];
            if ([extraStr isKindOfClass:[NSString class]] && [extraStr dataUsingEncoding:NSUTF8StringEncoding] != nil) {
                NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithData:[extraStr dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
                if ([extraDict[kATNativeBannerAdShowingExtraBackgroundColorKey] isKindOfClass:[NSString class]]) {
                    extra[kATNativeBannerAdShowingExtraBackgroundColorKey] = [UIColor colorWithHexString:extraDict[kATNativeBannerAdShowingExtraBackgroundColorKey]];
                }
                if ([extraDict[kATNativeBannerAdShowingExtraAutorefreshIntervalKey] respondsToSelector:@selector(doubleValue)]) {
                    extra[kATNativeBannerAdShowingExtraAutorefreshIntervalKey] = @([extraDict[kATNativeBannerAdShowingExtraAutorefreshIntervalKey] doubleValue]);
                }
                if ([extraDict[kATNativeBannerAdShowingExtraHideCloseButtonFlagKey] respondsToSelector:@selector(boolValue)]) {
                    extra[kATNativeBannerAdShowingExtraHideCloseButtonFlagKey] = @([extraDict[kATNativeBannerAdShowingExtraHideCloseButtonFlagKey] boolValue]);
                }
                if ([extraDict[kATNativeBannerAdShowingExtraCTAButtonBackgroundColorKey] isKindOfClass:[NSString class]]) {
                    extra[kATNativeBannerAdShowingExtraCTAButtonBackgroundColorKey] = [UIColor colorWithHexString:extraDict[kATNativeBannerAdShowingExtraCTAButtonBackgroundColorKey]];
                }
                if ([extraDict[kATNativeBannerAdShowingExtraCTAButtonTitleFontKey] respondsToSelector:@selector(doubleValue)]) {
                    extra[kATNativeBannerAdShowingExtraCTAButtonTitleFontKey] = @([extraDict[kATNativeBannerAdShowingExtraCTAButtonTitleFontKey] doubleValue]);
                }
                if ([extraDict[kATNativeBannerAdShowingExtraCTAButtonTitleColorKey] isKindOfClass:[NSString class]]) {
                    extra[kATNativeBannerAdShowingExtraCTAButtonTitleColorKey] = [UIColor colorWithHexString:extraDict[kATNativeBannerAdShowingExtraCTAButtonTitleColorKey]];
                }
                if ([extraDict[kATNativeBannerAdShowingExtraTitleFontKey] respondsToSelector:@selector(doubleValue)]) {
                    extra[kATNativeBannerAdShowingExtraTitleFontKey] = @([extraDict[kATNativeBannerAdShowingExtraTitleFontKey] doubleValue]);
                }
                if ([extraDict[kATNativeBannerAdShowingExtraTitleColorKey] isKindOfClass:[NSString class]]) {
                    extra[kATNativeBannerAdShowingExtraTitleColorKey] = [UIColor colorWithHexString:extraDict[kATNativeBannerAdShowingExtraTitleColorKey]];
                }
                if ([extraDict[kATNativeBannerAdShowingExtraTextFontKey] respondsToSelector:@selector(doubleValue)]) {
                    extra[kATNativeBannerAdShowingExtraTextFontKey] = @([extraDict[kATNativeBannerAdShowingExtraTextFontKey] doubleValue]);
                }
                if ([extraDict[kATNativeBannerAdShowingExtraTextColorKey] isKindOfClass:[NSString class]]) {
                    extra[kATNativeBannerAdShowingExtraTextColorKey] = [UIColor colorWithHexString:extraDict[kATNativeBannerAdShowingExtraTextColorKey]];
                }
                if ([extraDict[kATNativeBannerAdShowingExtraAdvertiserTextFontKey] respondsToSelector:@selector(doubleValue)]) {
                    extra[kATNativeBannerAdShowingExtraAdvertiserTextFontKey] = @([extraDict[kATNativeBannerAdShowingExtraAdvertiserTextFontKey] doubleValue]);
                }
                if ([extraDict[kATNativeBannerAdShowingExtraAdvertiserTextColorKey] isKindOfClass:[NSString class]]) {
                    extra[kATNativeBannerAdShowingExtraAdvertiserTextColorKey] = [UIColor colorWithHexString:extraDict[kATNativeBannerAdShowingExtraAdvertiserTextColorKey]];
                }
            }
            ATNativeBannerView *bannerView = [ATNativeBannerWrapper retrieveNativeBannerAdViewWithPlacementID:placementID extra:extra delegate:self];
            UIButton *bannerCointainer = [UIButton buttonWithType:UIButtonTypeCustom];
            [bannerCointainer addTarget:self action:@selector(noop) forControlEvents:UIControlEventTouchUpInside];
            bannerCointainer.frame = CGRectMake([rectDict[@"x"] doubleValue], [rectDict[@"y"] doubleValue], [rectDict[@"width"] doubleValue], [rectDict[@"height"] doubleValue]);
            bannerView.frame = bannerCointainer.bounds;
            [bannerCointainer addSubview:bannerView];
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:bannerCointainer];
            self->_nativeBannerAdViews[placementID] = bannerCointainer;
        }
    });
}

-(void) removeNativeBannerAdWithPlacementID:(NSString*)placementID {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self->_nativeBannerAdViews[placementID] removeFromSuperview];
        [self->_nativeBannerAdViews removeObjectForKey:placementID];
    });
}

-(void) showNativeBannerAdWithPlacementID:(NSString*)placementID {
    dispatch_async(dispatch_get_main_queue(), ^{
        self->_nativeBannerAdViews[placementID].hidden = NO;
    });
}

-(void) hideNativeBannerAdWithPlacementID:(NSString*)placementID {
    dispatch_async(dispatch_get_main_queue(), ^{
        self->_nativeBannerAdViews[placementID].hidden = YES;
    });
}

-(void) loadNativeBannerAdWithPlacementID:(NSString*)placementID customDataJSONString:(NSString*)customDataJSONString callback:(void(*)(const char*, const char*))callback {
    [self setCallBack:callback forKey:kATSharedCallbackKey];
    [self setCallBack:callback forKey:placementID];
    [ATNativeBannerWrapper loadNativeBannerAdWithPlacementID:placementID extra:nil customData:([customDataJSONString isKindOfClass:[NSString class]] && [customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] != nil) ? [NSJSONSerialization JSONObjectWithData:[customDataJSONString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil] : nil delegate:self];
}

-(BOOL) isNativeBannerAdReadyForPlacementID:(NSString*)placementID {
    return [ATNativeBannerWrapper nativeBannerAdReadyForPlacementID:placementID];
}
#pragma mark - delegate(s)
-(void) didFinishLoadingNativeBannerAdWithPlacementID:(NSString *)placementID {
    NSLog(@"ATNativeBannerAdWrapper::didFinishLoadingNativeBannerAdWithPlacementID:%@", placementID);
    [self invokeCallback:@"OnNativeBannerAdLoaded" placementID:placementID error:nil extra:nil];
}

-(void) didFailToLoadNativeBannerAdWithPlacementID:(NSString*)placementID error:(NSError*)error {
    NSLog(@"ATNativeBannerAdWrapper::didFailToLoadNativeBannerAdWithPlacementID:%@ error:%@", placementID, error);
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to load native banner ad", NSLocalizedFailureReasonErrorKey:@"AT has failed to load native banner ad"}];
    [self invokeCallback:@"OnNativeBannerAdLoadingFailure" placementID:placementID error:error extra:nil];
}

-(void) didShowNativeBannerAdInView:(ATNativeBannerView*)bannerView placementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeBannerAdWrapper::didShowNativeBannerAdInView:placementID:%@", placementID);
    [self invokeCallback:@"OnNaitveBannerAdShow" placementID:placementID error:nil extra:extra];
}

-(void) didClickNativeBannerAdInView:(ATNativeBannerView*)bannerView placementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeBannerAdWrapper::didClickNativeBannerAdInView:placementID:%@", placementID);
    [self invokeCallback:@"OnNativeBannerAdClick" placementID:placementID error:nil extra:extra];
}

-(void) didClickCloseButtonInNativeBannerAdView:(ATNativeBannerView*)bannerView placementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeBannerAdWrapper::didClickCloseButtonInNativeBannerAdView:placementID:%@", placementID);
    [self invokeCallback:@"OnNativeBannerAdCloseButtonClicked" placementID:placementID error:nil extra:extra];
}

-(void) didAutorefreshNativeBannerAdInView:(ATNativeBannerView*)bannerView placementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeBannerAdWrapper::didFailToAutorefreshNativeBannerAdInView:placementID:%@", placementID);
    [self invokeCallback:@"OnNativeBannerAdAutorefresh" placementID:placementID error:nil extra:extra];
    
}

-(void) didFailToAutorefreshNativeBannerAdInView:(ATNativeBannerView*)bannerView placementID:(NSString*)placementID error:(NSError*)error {
    NSLog(@"ATNativeBannerAdWrapper::didFailToAutorefreshNativeBannerAdInView:placementID:%@ error:%@", placementID, error);
    error = error != nil ? error : [NSError errorWithDomain:@"com.anythink.Unity3DPackage" code:100001 userInfo:@{NSLocalizedDescriptionKey:@"AT has failed to refresh native banner ad", NSLocalizedFailureReasonErrorKey:@"AT has failed to refresh native banner ad"}];
    [self invokeCallback:@"OnNativeBannerAdAutorefreshFailed" placementID:placementID error:error extra:nil];
}
@end

#pragma mark - vc swizzling
@implementation UIViewController (ATUtilities)

-(void)AT_presentViewController:(UIViewController *)viewControllerToPresent animated:(BOOL)flag completion:(void (^)(void))completion {
    [self AT_presentViewController:viewControllerToPresent animated:flag completion:completion];
    [[ATNativeBannerAdWrapper sharedInstance] invokeCallback:@"PauseAudio" placementID:kATSharedCallbackKey error:nil extra:nil];
    NSLog(@"oc : AT_presentViewController, callback to PauseAudio");
}

-(void) AT_dismissViewControllerAnimated:(BOOL)flag completion:(void (^)(void))completion {
    [self AT_dismissViewControllerAnimated:flag completion:completion];
    [[ATNativeBannerAdWrapper sharedInstance] invokeCallback:@"ResumeAudio" placementID:kATSharedCallbackKey error:nil extra:nil];
    NSLog(@"oc : AT_dismissViewControllerAnimated, callback to ResumeAudio");
}
@end
