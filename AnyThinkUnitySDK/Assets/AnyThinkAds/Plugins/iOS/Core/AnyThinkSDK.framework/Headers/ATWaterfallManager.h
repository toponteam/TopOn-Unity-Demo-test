//
//  ATWaterfallManager.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 2020/4/28.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import <Foundation/Foundation.h>
@class ATUnitGroupModel;
@class ATPlacementModel;
typedef NS_ENUM(NSInteger, ATWaterfallType) {
    ATWaterfallTypeWaterfall = 0,
    ATWaterfallTypeHeaderBidding = 1,
    ATWaterfallTypeFinal = 2
};

typedef NS_ENUM(NSInteger, ATUnitGroupFinishType) {
    ATUnitGroupFinishTypeFinished = 0,
    ATUnitGroupFinishTypeTimeout = 1,
    ATUnitGroupFinishTypeFailed = 2
};

@interface ATWaterfallWrapper:NSObject
-(void) finish;
-(void) fill;
//-(void) callback;
-(ATUnitGroupModel*) filledUnitGroupWithMaximumPrice;
- (ATUnitGroupModel *)requestingUnitGroupMaxPriceWithFilteredUnitID:(NSString *)unitID;
@property(nonatomic) NSInteger numberOfCachedOffers;
@property(nonatomic, readonly, getter=isFilled) BOOL filled;
//@property(nonatomic, readonly, getter=isCallbacked) BOOL callbacked;
@property(nonatomic) BOOL headerBiddingFired;
@property(nonatomic) BOOL headerBiddingFailed;
@property(nonatomic, readonly) dispatch_queue_t access_queue;

@end

@interface ATWaterfall:NSObject
-(instancetype) initWithUnitGroups:(NSArray<ATUnitGroupModel*>*)unitGroups placementID:(NSString*)placementID requestID:(NSString*)requestID;
-(void) requestUnitGroup:(ATUnitGroupModel*)unitGroup;
-(void) finishUnitGroup:(ATUnitGroupModel*)unitGroup withType:(ATUnitGroupFinishType)type;
-(void) addUnitGroup:(ATUnitGroupModel*)unitGroup;
-(void) insertUnitGroup:(ATUnitGroupModel*)unitGroup price:(NSString *)price;
-(void) insertUnitGroup:(ATUnitGroupModel*)unitGroup price:(NSString *)price filtered:(BOOL)filtered;

-(ATUnitGroupModel*) firstPendingNonHBUnitGroupWithNetworkFirmID:(NSInteger)nwFirmID;
-(ATUnitGroupModel*) unitGroupWithUnitID:(NSString*)unitID;
-(ATUnitGroupModel*) unitGroupWithMaximumPrice;
-(ATUnitGroupModel*) unitGroupWithMinimumPrice;
-(BOOL)canContinueLoading:(BOOL)waitForSentRequests;
-(void) enumerateTimeoutUnitGroupWithBlock:(void(^)(ATUnitGroupModel*unitGroup))block;
@property(nonatomic, strong) NSMutableArray<ATUnitGroupModel*>* unitGroups;
@property(nonatomic, readonly) NSUInteger numberOfTimeoutRequests;
@property(nonatomic, readonly) ATWaterfallType type;
@property(nonatomic, readonly, getter=isLoading) BOOL loading;

- (NSMutableArray<ATUnitGroupModel *> *)getWaterfallUnitGroups;
@end

@interface ATWaterfallManager : NSObject
+(instancetype) sharedManager;

-(void) removeWaterfallWrappers:(NSString *)placementID;

-(BOOL) loadingAdForPlacementID:(NSString*)placementID;

// just for api: check ad loading status
-(BOOL) loadingAdForPlacementID:(NSString*)placementID skipSettingLoadingStatus:(BOOL)skip;

-(void) attachWaterfall:(ATWaterfall*)waterfall completion:(void(^)(ATWaterfallWrapper *waterfallWrapper, ATWaterfall *waterfall, ATWaterfall *headerBiddingWaterfall, ATWaterfall *finalWaterfall, BOOL finished, NSDate *loadStartDate))completion;

-(void) attachDefaultWaterfall:(ATWaterfall*)defaultWaterfall completion:(void(^)(ATWaterfallWrapper *waterfallWrapper, ATWaterfall *waterfall, ATWaterfall *headerBiddingWaterfall, ATWaterfall *finalWaterfall, ATWaterfall *defaultWaterfall, BOOL finished, NSDate *loadStartDate))completion;
-(void) accessWaterfallForPlacementID:(NSString*)placementID requestID:(NSString*)requestID withBlock:(void(^)(ATWaterfallWrapper *waterfallWrapper, ATWaterfall *waterfall, ATWaterfall *headerBiddingWaterfall, ATWaterfall *defaultWaterfall, ATWaterfall *finalWaterfall, BOOL finished, NSDate *loadStartDate))block;

@end
