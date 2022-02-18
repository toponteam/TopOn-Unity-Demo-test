//
//  ATMyOfferSetting.h
//  AnyThinkSDK
//
//  Created by Martin Lau on 2019/9/23.
//  Copyright © 2019 Martin Lau. All rights reserved.
//

#import "ATOfferSetting.h"

// v5.7.47
typedef NS_ENUM(NSInteger, ATMyOfferInterType) {
    ATMyOfferInterTypeFull = 1,
    ATMyOfferInterTypeHalf
};

@interface ATMyOfferSetting : ATOfferSetting
-(instancetype) initWithDictionary:(NSDictionary *)dictionary placementID:(NSString*)placementID;

@property(nonatomic, readwrite) NSTimeInterval resourceCacheTime;

@property(nonatomic, readwrite) ATMyOfferInterType interType;

+(instancetype) mockSetting;
@end
