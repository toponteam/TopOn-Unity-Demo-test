//
//  NADNativeVideoView.h
//  NendAdFramework
//
//  Copyright © 2018年 F@N Communications, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class NADNativeVideo;
@class NADNativeVideoView;

NS_ASSUME_NONNULL_BEGIN
@protocol NADNativeVideoViewDelegate <NSObject>

@optional
- (void)nadNativeVideoViewDidStartPlay:(NADNativeVideoView *)videoView;
- (void)nadNativeVideoViewDidStopPlay:(NADNativeVideoView *)videoView;
- (void)nadNativeVideoViewDidCompletePlay:(NADNativeVideoView *)videoView;
- (void)nadNativeVideoViewDidFailToPlay:(NADNativeVideoView *)videoView;
- (void)nadNativeVideoViewDidOpenFullScreen:(NADNativeVideoView *)videoView;
- (void)nadNativeVideoViewDidCloseFullScreen:(NADNativeVideoView *)videoView;
- (void)nadNativeVideoViewDidStartFullScreenPlaying:(NADNativeVideoView *)videoView;
- (void)nadNativeVideoViewDidStopFullScreenPlaying:(NADNativeVideoView *)videoView;

@end

@interface NADNativeVideoView : UIView

@property (readwrite, nonatomic, weak) id<NADNativeVideoViewDelegate> delegate;
@property (readwrite, nonatomic, strong) NADNativeVideo *videoAd;
@property (readwrite, nonatomic, weak, nullable) UIViewController *rootViewController;

@end
NS_ASSUME_NONNULL_END
