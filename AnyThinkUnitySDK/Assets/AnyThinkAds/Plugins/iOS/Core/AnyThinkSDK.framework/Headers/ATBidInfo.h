//
//  ATBidInfo.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 2020/4/27.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ATModel.h"

#define kATBiddingInitiatingFailedCode -2
#define kATBiddingNetworkTimeoutCode -3
#define KATBiddingS2SConnectionErrorCode -4
#define kATBiddingBiddingFailedByCapCode -5
#define kATBiddingBiddingFailedByPacingCode -6
#define kATBiddingBiddingFailedIntervalCode -7
#define kATBiddingBiddingFailedByExcludedCode -8
#define kATBiddingBiddingFailedIntegrationErrorCode -9

@interface ATBidInfo : ATModel
@property(nonatomic, copy) NSDictionary *bidResult;
@property(nonatomic, readonly) NSDate *expireDate;
@property(nonatomic, readonly) NSDictionary *offerDataDict;
@property(nonatomic, readonly) NSString *bidId;
@property(nonatomic, readonly) NSString *tpBidId;
@property(nonatomic, readwrite) NSString *price;
@property(nonatomic, readonly) NSString *curRate;
@property(nonatomic, readonly) ATBiddingCurrencyType currencyType;
@property(nonatomic, readonly) id customObject;
@property(nonatomic, readonly) NSString *placementID;
@property(nonatomic, readonly) NSString *unitGroupUnitID;
@property(nonatomic, readonly) NSInteger networkFirmID;
@property(nonatomic, readonly) NSString *adapterClassString;
@property(nonatomic, readonly) NSString *lURL;
@property(nonatomic, readonly) NSString *nURL;
@property(nonatomic, readonly) NSString *bURL;
@property(nonatomic, readonly) NSString *bURLWin;
@property(nonatomic, readwrite) BOOL bidPriceSmallerlast;
@property(nonatomic, readwrite) BOOL bidResultIsFailed;
@property(nonatomic, readwrite) NSError *error;
@property(nonatomic, readonly, getter=isValid) BOOL valid;
@property(nonatomic, readonly, getter=isExpired) BOOL expired;
@property(nonatomic, readonly, getter=isSendNotif) BOOL sendNotif;
@property(nonatomic, readonly, getter=isNoPrice) BOOL noPrice;
+(instancetype) bidInfoWithPlacementID:(NSString*)placementID unitGroupUnitID:(NSString*)unitGroupUnitID adapterClassString:(NSString *)adapterClassString token:(NSString*)token price:(NSString*)price currencyType:(ATBiddingCurrencyType)currencyType expirationInterval:(NSTimeInterval)expirationInterval customObject:(id)customObject;
-(instancetype) initWithDictionary:(NSDictionary*)dictionary placementID:(NSString*)placementID unitGroupUnitID:(NSString*)unitGroupUnitID adapterClassString:(NSString *)adapterClassString currencyType:(ATBiddingCurrencyType)currencyType expirationInterval:(NSTimeInterval)expirationInterval;
-(void) invalidate;
-(void) hasBeenSendNotif;
-(NSDictionary *) serializationToDictionary;
@end
