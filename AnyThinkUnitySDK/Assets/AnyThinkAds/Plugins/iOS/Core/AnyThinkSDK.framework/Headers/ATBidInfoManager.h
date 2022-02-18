//
//  ATBidInfoManager.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 2020/4/28.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ATBidInfo.h"
@class ATUnitGroupModel;
@class ATPlacementModel;
@class ATBidWaterFallModel;
@class ATBidNotifSendModel;
@interface ATBidInfoManager : NSObject
+(instancetype) sharedManager;
/**
 Used for renew bidinfo
 */
-(void) saveRequestID:(NSString*)requestID forPlacementID:(NSString*)placementID;
-(NSString*)requestForPlacementID:(NSString*)placementID;
-(void) renewBidInfoForPlacementID:(NSString*)placementID fromRequestID:(NSString*)requestID toRequestID:(NSString*)newRequestID unitGroups:(NSArray<ATUnitGroupModel*>*)unitGroups;

/**
 Used for send hb loss notification
 */
-(void) saveWithBidNotifSendModel:(ATBidNotifSendModel*)bidNotifSendModel forRequestID:(NSString*)requestID;
-(ATBidNotifSendModel*)getBidNotifSendModelForRequestID:(NSString*)requestID;

-(void) saveNoPriceCacheWitBidWaterFallModel:(ATBidWaterFallModel*)bidWaterfallModel;
-(void) removeNoPriceCacheWithTpBidId:(NSString*)tpBidId unitId:(NSString*)unitId;
-(ATBidWaterFallModel *)getBidWaterFallModelWithTpBidId:(NSString*)tpBidId unitId:(NSString*)unitId;


/**
 send hb win、loss、display notification
 */
-(void)sendHBWinnerNotificationForBidInfo:(ATBidInfo *)bidInfo nextUnitGroupPrice:(NSString *)nextPrice;
-(void)sendHBLossNotificationForPlacementID:(NSString*)placementID requestID:(NSString*)requestID unitGroups:(NSArray<ATUnitGroupModel*>*)unitGroups headerBiddingUnitGroups:(NSArray<ATUnitGroupModel*>*)headerBiddingUnitGroups;
-(void)sendNotifyDisplayForPlacementID:(NSString*)placementID unitGroup:(ATUnitGroupModel*)unitGroup winner:(BOOL)isWinner headerBidding:(BOOL)headerBidding price:(NSString *)price;

-(void)sendHBLossNotificationForBidInfo:(ATBidInfo *)bidInfo price:(NSString*)price headerBidding:(BOOL)headerBidding winnerNetworkFirmID:(NSInteger)winnerNetworkFirmID requestID:(NSString*)requestID;

-(void) saveBidInfo:(ATBidInfo*)bidInfo forRequestID:(NSString*)requestID;
-(void) removeDiskBidInfo:(ATBidInfo*)bidInfo;
-(void) hasBeenSendNotifBidInfoForPlacementID:(NSString*)placementID unitGroupModel:(ATUnitGroupModel*)unitGroupModel requestID:(NSString*)requestID;
-(void) invalidateBidInfoForPlacementID:(NSString*)placementID unitGroupModel:(ATUnitGroupModel*)unitGroupModel requestID:(NSString*)requestID;
-(ATBidInfo*) bidInfoForPlacementID:(NSString*)placementID unitGroupModel:(ATUnitGroupModel*)unitGroupModel requestID:(NSString*)requestID;

-(NSArray<ATUnitGroupModel*>*) unitGroupWithHistoryBidInfoAvailableForPlacementID:(NSString*)placementID unitGroups:(NSArray<ATUnitGroupModel*>*)unitGroupsToInspect inhouseUnitGroups:(NSArray<ATUnitGroupModel*>*)inhouseUnitGroupsToInspect s2sUnitGroups:(NSArray<ATUnitGroupModel*>*)s2sUnitGroupsToInspect bksUnitGroups:(NSArray<ATUnitGroupModel*>*)bksUnitGroupsToInspect  directUnitGroups:(NSArray<ATUnitGroupModel*>*)directUnitGroups newRequestID:(NSString*)newRequestID;

+(NSString *) priceForUnitGroup:(ATUnitGroupModel*)unitGroupModel;
+(NSString *) priceForUnitGroup:(ATUnitGroupModel*)unitGroupModel placementID:(NSString*)placementID requestID:(NSString*)requestID;
+(NSString *) getPriceToSendHBNotifiForUnitGroup:(ATUnitGroupModel*)unitGroupModel;

-(BOOL) checkAdxBidInfoExpireForUnitGroupModel:(ATUnitGroupModel*)unitGroupModel;
-(ATBidInfo*) getBidInfoCachedForPlacementID:(NSString*)placementID unitGroup:(ATUnitGroupModel*)unitGroup;
-(void) invalidateBidInfoForPlacementID:(NSString*)placementID unitGroupModel:(ATUnitGroupModel*)unitGroupModel;
@end
