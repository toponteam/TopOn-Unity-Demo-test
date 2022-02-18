//
//  ATOfferModel.h
//  AnyThinkSDK
//
//  Created by stephen on 21/8/2020.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATModel.h"
#import <UIKit/UIKit.h>

extern NSString *const kATOfferBannerSize320_50;
extern NSString *const kATOfferBannerSize320_90;
extern NSString *const kATOfferBannerSize300_250;
extern NSString *const kATOfferBannerSize728_90;

@interface ATVideoPlayingTKItem : NSObject

@property (nonatomic, copy) NSArray<NSString *> *urls;
@property (nonatomic) NSInteger triggerTime;
@property (nonatomic) BOOL sent;

- (instancetype)initWithDict:(NSDictionary *)dict;

@end

// v5.7.24
typedef NS_ENUM(NSInteger, ATOfferInterActableArea) {
    ATOfferInterActableAreaAll,
    ATOfferInterActableAreaVisibleItems,
    ATOfferInterActableAreaCTA
};

@interface ATOfferModel : ATModel

@property(nonatomic) ATOfferInterActableArea interActableArea;
@property(nonatomic, readwrite) NSString *resourceID;
@property(nonatomic, readwrite) NSString *offerID;
@property(nonatomic, readwrite) NSString *pkgName;
@property(nonatomic, readwrite) NSString *title;
@property(nonatomic, readwrite) NSString *text;
@property(nonatomic, readwrite) NSInteger rating;
@property(nonatomic, readwrite) NSString *iconURL;
@property(nonatomic, readwrite) NSString *fullScreenImageURL;
@property(nonatomic, readwrite) ATInterstitialType interstitialType;//check the offer for video or image
@property(nonatomic, readwrite) NSString *logoURL;//adv_u
@property(nonatomic, readwrite) NSString *CTA;
@property(nonatomic, readwrite) NSString *videoURL;
@property(nonatomic, readwrite) NSString *storeURL;
@property(nonatomic, readwrite) ATLinkType linkType;
@property(nonatomic, readwrite) NSString *clickURL;
@property(nonatomic, readwrite) NSString *deeplinkUrl; 
@property(nonatomic, readwrite) NSString *localResourceID;
@property(nonatomic, assign) ATOfferModelType offerModelType;
@property(nonatomic, assign) ATOfferCrtType crtType;

@property(nonatomic, copy) NSString *jumpUrl;
@property(nonatomic) NSInteger offerFirmID;


@property(nonatomic, assign) BOOL offerIMCapSw;
@property(nonatomic, assign) BOOL offerCLCapSw;

@property(nonatomic, assign) BOOL resourceIMCapSw;
@property(nonatomic, assign) BOOL resourceCLCapSw;



// ad attributes
@property(nonatomic, readwrite) NSString *adPublisher;
@property(nonatomic, readwrite) NSString *adVersion;
@property(nonatomic, readwrite) NSString *adPrivacy;
@property(nonatomic, readwrite) NSString *adPermission;

//banner(myoffer:5.6.6)
@property(nonatomic, readwrite) NSString *bannerImageUrl;
@property(nonatomic, readwrite) NSString *bannerBigImageUrl;
@property(nonatomic, readwrite) NSString *rectangleImageUrl;
@property(nonatomic, readwrite) NSString *homeImageUrl;

@property(nonatomic, readwrite) NSArray<NSString*>* resourceURLs;

@property(nonatomic) NSInteger displayDuration;

@property(nonatomic, readwrite) NSArray<NSString*>* clickTKUrl;
@property(nonatomic, readwrite) NSArray<NSString*>* openSchemeFailedTKUrl;

//to do
@property(nonatomic) NSInteger videoCurrentTime;
@property(nonatomic) NSInteger videoResumeTime;

@property(nonatomic, copy) NSDictionary *tapInfoDict;

// If it is YES, it means that the feedback has been done.
@property(nonatomic) BOOL feedback;

// v5.7.47 +
@property(nonatomic, readwrite) NSArray<NSString*>* deeplinkSuccessUrl;
@property(nonatomic, readwrite) NSDictionary* at_deeplinkSuccessUrl;

// v5.7.56+
@property(nonatomic, readwrite) ATSplashImageScaleType splashImageScaleType;


- (BOOL)showAdAttributes;
@end


