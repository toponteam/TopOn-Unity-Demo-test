//
//  KSAdSDKDefine.h
//  Pods
//
//  Created by xuzhijun on 2019/8/28.
//

#ifndef KSAdSDKDefine_h
#define KSAdSDKDefine_h

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, KSRewardedVideoAdRewardedType) {
    // 正常的激励视频，必须是视频播放完成才显示关闭按钮
    KSRewardedVideoAdRewardedTypeNormal                 =           1,
    // 播放30秒，显示关闭按钮，并且有奖励，如果视频小于等于30秒，就是normal
    KSRewardedVideoAdRewardedTypePlay30Second           =           2,
//    // 播放5秒显示跳过，播放完成后，显示关闭按钮，点击跳过，弹出是否有奖励的选项
//    KSRewardedVideoAdRewardedTypeCanSkip                =           3,
//    // 播放5秒显示跳过，播放30秒，显示关闭按钮，并且有奖励，如果视频小于等于30秒，就是normal
//    KSRewardedVideoAdRewardedTypePlay30SecondAndCanSkip =           4,
};


typedef NS_ENUM(NSInteger, KSAdSDKLogLevel) {
    KSAdSDKLogLevelAll      =       0,
    KSAdSDKLogLevelVerbose,
    KSAdSDKLogLevelDebug,
    KSAdSDKLogLevelInfo,
    KSAdSDKLogLevelWarn,
    KSAdSDKLogLevelError,
    KSAdSDKLogLevelOff,
};



#endif /* KSAdSDKDefine_h */
