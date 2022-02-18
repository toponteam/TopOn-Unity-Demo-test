//
//  ATUnitGroupModel.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 11/04/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import "ATModel.h"
#import <UIKit/UIKit.h>
#import "ATMyOfferOfferModel.h"
#import "ATBidInfo.h"


typedef NS_ENUM(NSInteger, ATShowAutoLoadType) {
    ATShowAutoOneAdSupportLoadType = 1,
    ATShowAutoOneAdNotSupportLoadType,
    ATShowAutoOneNetWorkNotSupportLoadType,
};

@interface ATUnitGroupModel : ATModel
-(instancetype) initWithDictionary:(NSDictionary *)dictionary;
@property(nonatomic, readonly, weak) Class adapterClass;
@property(nonatomic, readonly) NSString *adapterClassString;
@property(nonatomic, readonly) NSInteger capByDay;
@property(nonatomic, readonly) NSInteger capByHour;
@property(nonatomic, readonly) NSTimeInterval networkCacheTime;
@property(nonatomic, readonly) NSInteger networkFirmID;
@property(nonatomic, readonly) NSString *networkName;
@property(nonatomic, readonly) NSInteger networkRequestNum;
@property(nonatomic, readonly) NSTimeInterval networkDataTimeout; //  5.1.0 双回调数据超时
@property(nonatomic, readonly) NSTimeInterval networkTimeout;
@property(nonatomic, readonly) NSTimeInterval skipIntervalAfterLastLoadingFailure;
@property(nonatomic, readonly) NSTimeInterval skipIntervalAfterLastBiddingFailure;
@property(nonatomic, readonly) NSString *unitGroupID;
@property(nonatomic, readonly) NSString *unitID;
@property(nonatomic, readonly) NSDictionary *content;
@property(nonatomic, readonly) NSTimeInterval showingInterval;//minimum interval between previous request & last impression
@property(nonatomic, readonly) CGSize adSize;

@property(nonatomic, readonly) BOOL splashZoomOut;
@property(nonatomic, readonly) NSString *price;
@property(nonatomic, readonly) NSString *ecpmByCurrency;
@property(nonatomic, readonly) NSInteger ecpmLevel;
@property(nonatomic, readonly) NSTimeInterval headerBiddingRequestTimeout;
@property(nonatomic, readwrite) NSString *bidPrice;
@property(nonatomic, readwrite) NSString *bidToken;
@property(nonatomic, readonly) BOOL headerBidding;
@property(nonatomic, readonly) ATUnitGroupType unitGroupType;
@property(nonatomic, readonly) NSTimeInterval bidTokenTime;
@property(nonatomic, readonly) NSTimeInterval statusTime;
@property(nonatomic, readonly) NSString *clickTkAddress;
@property(nonatomic, readonly) NSTimeInterval clickTkDelayMin;
@property(nonatomic, readonly) NSTimeInterval clickTkDelayMax;
@property(nonatomic, readonly) BOOL postsNotificationOnShow;
@property(nonatomic, readonly) BOOL postsNotificationOnClick;
@property(nonatomic, readonly) NSString *precision;
@property(nonatomic, readonly) BOOL canAutoReady;
@property(nonatomic, readonly) BOOL waterfallTimeOutSW;
@property(nonatomic, readonly) BOOL reportBidPriceSW;
@property(nonatomic, readonly) BOOL bidRealTimeLoadSW;
@property(nonatomic, readonly) BOOL returnHBEcpmSW;
@property(nonatomic, readwrite) ATBidInfo *bidInfo;

@property(nonatomic, readonly, getter=isBidInfoNoPrice) BOOL bidInfoNoPrice;
@property(nonatomic, readonly) BOOL unitGroupS2SSW;

@property(nonatomic) BOOL s2sRewardEnabled;

// v5.7.61+
@property(nonatomic, readonly) NSInteger weight;

@property(nonatomic, copy) NSString *directOfferOid;



@property(nonatomic, assign) ATShowAutoLoadType showAutoLoadType;


@property(nonatomic) NSInteger priority; // when ad is filterd 

@end
