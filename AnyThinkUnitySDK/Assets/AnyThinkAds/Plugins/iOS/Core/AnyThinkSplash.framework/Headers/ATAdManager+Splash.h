//
//  ATAdManager+Splash.h
//  AnyThinkSplash
//
//  Created by Martin Lau on 2018/12/20.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import <AnyThinkSDK/AnyThinkSDK.h>
extern NSString *const kATSplashExtraCountdownKey;
extern NSString *const kATSplashExtraTolerateTimeoutKey;
extern NSString *const kATSplashExtraHideSkipButtonFlagKey;
extern NSString *const kATSplashExtraBackgroundImageKey;
extern NSString *const kATSplashExtraBackgroundColorKey;
extern NSString *const kATSplashExtraSkipButtonCenterKey;
extern NSString *const kATSplashExtraCustomSkipButtonKey;
extern NSString *const kATSplashExtraCanClickFlagKey;
extern NSString *const kATSplashExtraShowDirectionKey;//Supported by KS Splash, defaults to Vertical, 1 to Horizontal
// 5.7.61+
extern NSString *const kATSplashExtraCountdownIntervalKey;

extern NSString *const kATSplashExtraPlacementIDKey;
extern NSString *const kATSplashExtraNetworkFirmID;
extern NSString *const kATSplashExtraAdSourceIDKey;
//#pragma mark - Mintegral
//extern NSString *const kATSplashExtraMintegralAppKey;
//extern NSString *const kATSplashExtraMintegralAppID;
//extern NSString *const kATSplashExtraMintegralPlacementID;
//extern NSString *const kATSplashExtraMintegralUnitID;
//#pragma mark - GDT
//extern NSString *const kATSplashExtraGDTAppID;
//extern NSString *const kATSplashExtraGDTUnitID;
//#pragma mark - TT
//extern NSString *const kATSplashExtraAppID;
//extern NSString *const kATSplashExtraSlotID;
//extern NSString *const kATSplashExtraPersonalizedTemplateFlag;
//extern NSString *const kATSplashExtraZoomOutKey;
//#pragma mark - Baidu
//extern NSString *const kATSplashExtraBaiduAppID;
//extern NSString *const kATSplashExtraBaiduAdPlaceID;
//#pragma mark - Sigmob
//extern NSString *const kATSplashExtraSigmobAppKey;
//extern NSString *const kATSplashExtraSigmobAppID;
//extern NSString *const kATSplashExtraSigmobPlacementID;
//#pragma mark - Admob
//extern NSString *const kATSplashExtraAdmobAppID;
//extern NSString *const kATSplashExtraAdmobUnitID;
//extern NSString *const kATSplashExtraAdmobOrientation;
//#pragma mark - KuaiShou
//extern NSString *const kATSplashExtraKSAppID ;
//extern NSString *const kATSplashExtraKSPosID;
//
//extern NSString *const kATAdLoadingExtraSplashAdSizeKey;
//extern NSString *const kATSplashExtraRootViewControllerKey;
//
//#pragma mark - Klevin
//extern NSString *const kATSplashExtraKlevinPosID;
//extern NSString *const kATSplashExtraKlevinAppID;

//extern NSString *const kATSplashExtraConfigKey;

@protocol ATSplashDelegate;
@interface ATAdManager (Splash)
- (void)loadADWithPlacementID:(NSString *)placementID extra:(NSDictionary *)extra delegate:(id<ATSplashDelegate>)delegate containerView:(UIView *)containerView;
- (void)loadADWithPlacementID:(NSString *)placementID extra:(NSDictionary *)extra delegate:(id<ATSplashDelegate>)delegate containerView:(UIView *)containerView defaultAdSourceConfig:(NSString *)defaultAdSourceConfig;

- (void)checkAdSourceList:(NSString*)placementID;
- (void)showSplashWithPlacementID:(NSString*)placementID window:(UIWindow*)window delegate:(id<ATSplashDelegate>)delegate;

// v5.7.61+
- (void)showSplashWithPlacementID:(NSString*)placementID window:(UIWindow*)window extra:(NSDictionary *)extra delegate:(id<ATSplashDelegate>)delegate;

- (void)showSplashWithPlacementID:(NSString*)placementID window:(UIWindow*)window windowScene:(UIWindowScene *)windowScene delegate:(id<ATSplashDelegate>)delegate API_AVAILABLE(ios(13.0));
- (BOOL)splashReadyForPlacementID:(NSString *)placementID;
- (BOOL)splashReadyForPlacementID:(NSString *)placementID sendTK:(BOOL)send;

- (ATCheckLoadModel*)checkSplashLoadStatusForPlacementID:(NSString *)placementID;
- (NSArray<NSDictionary *> *)getSplashValidAdsForPlacementID:(NSString *)placementID;

- (void)entrySplashScenarioWithPlacementID:(NSString *)placementID scene:(NSString *)scene;

@end
