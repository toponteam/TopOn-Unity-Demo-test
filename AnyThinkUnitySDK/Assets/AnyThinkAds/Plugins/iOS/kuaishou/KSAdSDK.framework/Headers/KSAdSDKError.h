//
//  KSAdSDKError.h
//  KSAdSDK
//
//  Created by 徐志军 on 2019/8/29.
//  Copyright © 2019 KuaiShou. All rights reserved.
//


#import <Foundation/Foundation.h>


typedef NS_ENUM(NSInteger, KSAdErrorCode) {
    
    KSAdErrorCodeNoError        =           20000,      // 成功
    
    KSAdErrorCodeNetworkError   =           40001,      // 网络错误
    KSAdErrorCodeDataParse      =           40002,      // data数据解析错误
    KSAdErrorCodeDataEmpty      =           40003,      // data empty
    KSAdErrorCodeCacheError     =           40004,      // 缓存出错
    
    
    KSAdErrorCodeNotVideoAd     =           50001,      // not a video ad
    KSAdErrorCodeNoMoreData     =           100006,     // 拉取内容视频时，没有更多了
    KSAdErrorCodeShareUrl       =           100007,     // 拉取分享接口，获取shareURL失败
};



