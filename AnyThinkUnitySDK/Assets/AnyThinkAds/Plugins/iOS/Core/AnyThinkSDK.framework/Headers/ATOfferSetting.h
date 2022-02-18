//
//  ATOfferSetting.h
//  AnyThinkSDK
//
//  Created by stephen on 26/8/2020.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ATModel.h"

extern NSString *const kATOfferTrackerExtraLifeCircleID;
extern NSString *const kATOfferTrackerExtraScene;

extern NSString *const kATOfferTrackerGDTClickID;
extern NSString *const kATOfferTrackerGDTWidth;
extern NSString *const kATOfferTrackerGDTHeight;
extern NSString *const kATOfferTrackerGDTDownX;
extern NSString *const kATOfferTrackerGDTDownY;
extern NSString *const kATOfferTrackerGDTUpX;
extern NSString *const kATOfferTrackerGDTUpY;
extern NSString *const kATOfferTrackerGDTRequestWidth;
extern NSString *const kATOfferTrackerGDTRequestHeight;

// For v5.7.7+ onlineApi. Some relative coordinates.
extern NSString *const kATOfferTrackerRelativeDownX;
extern NSString *const kATOfferTrackerRelativeDownY;
extern NSString *const kATOfferTrackerRelativeUpX;
extern NSString *const kATOfferTrackerRelativeUpY;
extern NSString *const kATOfferTrackerTimestamp;
extern NSString *const kATOfferTrackerMilliTimestamp;
extern NSString *const kATOfferTrackerEndTimestamp;
extern NSString *const kATOfferTrackerEndMilliTimestamp;
extern NSString *const kATOfferTrackerVideoTimePlayed;
extern NSString *const kATOfferTrackerVideoMilliTimePlayed;

// for v5.7.40+
extern NSString *const kATOfferTrackerKYAbsoluteCoord;
extern NSString *const kATOfferTrackerKYRelativeCoord;

// v5.7.42+
extern NSString *const kATOfferTrackerDeeplinkType;

extern NSString *const kATOfferTrackerOPDPType;

//typedef NS_ENUM(NSInteger, ATOfferCloseButtonSizeType) {
//    ATOfferCloseButtonSizeTypeNormal = 1,
//    ATOfferCloseButtonSizeType150Percent,
//    ATOfferCloseButtonSizeType75Percent,
//    ATOfferCloseButtonSizeType50Percent
//};
@interface ATOfferSetting : ATModel
-(instancetype) initWithDictionary:(NSDictionary *)dictionary;

@property(nonatomic, readwrite) NSString *placementID;
@property(nonatomic, readwrite) NSInteger format;
@property(nonatomic, readwrite) ATVideoClickable videoClickable;
@property(nonatomic, readwrite) NSTimeInterval resourceDownloadTimeout;
@property(nonatomic, readwrite) NSTimeInterval bannerAppearanceInterval;
@property(nonatomic, readwrite) ATEndCardClickable endCardClickable;
@property(nonatomic, readwrite) BOOL unMute;
@property(nonatomic, readwrite) NSTimeInterval closeButtonAppearanceInterval;
@property(nonatomic, readwrite) ATLoadStorekitTime storekitTime;
@property(nonatomic, readwrite) NSInteger lastOfferidsNum;

// The inherent attributes of the ad, such as the version number, etc.
//@property(nonatomic, readwrite) BOOL showAdAttrs;

//setting for banner
@property(nonatomic, readwrite) NSString *bannerSize;
@property(nonatomic, readwrite) BOOL showBannerCloseBtn;
@property(nonatomic, readwrite) NSInteger splashCountDownTime;
@property(nonatomic, readwrite) BOOL skipable;
@property(nonatomic, readwrite) NSInteger splashOrientation;

@property(nonatomic, readwrite) ATOfferLayoutType layoutType;
@property(nonatomic, readwrite) ATClickMode clickMode;
@property(nonatomic, readwrite) ATLoadType loadType;
@property(nonatomic, readwrite) ATUserAgentType impressionUAType;
@property(nonatomic, readwrite) ATUserAgentType clickUAType;
@property(nonatomic, readwrite) ATDeepLinkClickMode deeplinkClickMoment;
@property(nonatomic, readwrite) ATSplashType splashType;
@property(nonatomic, readwrite) ATSplashImageScaleType splashImageScaleType;

/// The minimum value of the close button delayed display
@property(nonatomic) NSInteger closeBtnDelayMinTime;
/// The maximum value of the close button delayed display
@property(nonatomic) NSInteger closeBtnDelayMaxTime;

@property(nonatomic) NSInteger closeBtnDelayRate;

// v5.7.56+
@property(nonatomic) CGFloat closeBtnSize;
@property(nonatomic) NSInteger onRewardTime;
@property(nonatomic) BOOL rewardWhenFailed;

// v5.7.71+
@property(nonatomic) NSInteger automatedClick;
@property(nonatomic) NSInteger automatedClickExtendTime;
@property(nonatomic) BOOL showEendCard;




@end


