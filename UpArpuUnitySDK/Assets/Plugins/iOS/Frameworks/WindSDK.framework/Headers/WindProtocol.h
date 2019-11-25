//
//  WindProtocol.h
//  WindSDK
//
//  Created by happyelements on 2018/8/2.
//  Copyright © 2018 Codi. All rights reserved.
//

@class WindAdRequest;
@class WADOptions;

@protocol WindAdRequestProtocol<NSObject>

@optional

- (NSDictionary *)parametes;

- (WindAdRequest *)request;

- (NSMutableDictionary<NSString *,NSDictionary *> *)adOptions;

@end

