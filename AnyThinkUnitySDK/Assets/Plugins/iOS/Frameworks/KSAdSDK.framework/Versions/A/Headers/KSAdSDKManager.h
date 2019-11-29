//
//  KSAdSDKManager.h
//  AFNetworking
//
//  Created by xuzhijun on 2019/8/28.
//

#import <Foundation/Foundation.h>
#import "KSAdSDKDefine.h"



NS_ASSUME_NONNULL_BEGIN

@class KSAdUserInfo;


@interface KSAdSDKManager : NSObject
@property (nonatomic, readonly, class) NSString *SDKVersion;

/**
 Register the App key that’s already been applied before requesting an ad from TikTok Audience Network.
 @param appId : the unique identifier of the App
 */
// required
+ (void)setAppId:(NSString *)appId;
// optional
+ (void)setAppName:(NSString *)appName;
// optional
+ (void)setUserInfoBlock:(void(^)(KSAdUserInfo *))userInfoBlock;
/**
 Configure development mode.
 @param level : default BUAdSDKLogLevelNone
 */
// optional
+ (void)setLoglevel:(KSAdSDKLogLevel)level;

+ (NSString *)appId;
@end

NS_ASSUME_NONNULL_END
