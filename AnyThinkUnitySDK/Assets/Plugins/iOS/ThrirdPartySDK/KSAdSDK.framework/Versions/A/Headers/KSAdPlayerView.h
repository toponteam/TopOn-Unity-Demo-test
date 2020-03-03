//
//  KSAdPlayerView.h
//  KSAdPlayer
//
//  Created by 徐志军 on 2019/10/10.
//  Copyright © 2019 KuaiShou. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>

@class KSAdPlayerView;

@protocol KSAdPlayerViewDelegate <NSObject>

@optional
// 所有的代理方法均已回到主线程 可直接刷新UI
// 可播放／播放中
- (void)playerViewIsReadyToPlayVideo:(KSAdPlayerView *)playerView;
// 播放完毕
- (void)playerViewDidReachEnd:(KSAdPlayerView *)playerView;
// 当前播放时间
- (void)playerView:(KSAdPlayerView *)playerView currentTime:(CGFloat)currentTime;
// duration 当前缓冲的长度
- (void)playerView:(KSAdPlayerView *)playerView loadedTimeRangeDidChange:(CGFloat)duration;
// 进行跳转后没数据 即播放卡顿
- (void)playerViewPlaybackBufferEmpty:(KSAdPlayerView *)playerView;
// 进行跳转后有数据 能够继续播放
- (void)playerViewPlaybackLikelyToKeepUp:(KSAdPlayerView *)playerView;
// 加载失败
- (void)playerView:(KSAdPlayerView *)playerView didFailWithError:(NSError *)error;

@end

@interface KSAdPlayerView : UIView

// 默认：AVLayerVideoGravityResizeAspect
@property(nonatomic, copy) AVLayerVideoGravity videoGravity;
//播放时为YES 暂停时为NO
@property (nonatomic, assign, readonly) BOOL isPlaying;
// 是否完成
@property (nonatomic, assign, readonly) BOOL isFinish;
@property (nonatomic, weak) id<KSAdPlayerViewDelegate> delegate;
@property (nonatomic, strong, readonly) AVPlayerItem *playerItem;
@property (nonatomic, readonly) AVURLAsset *urlAsset;
@property (nonatomic, copy) void(^pauseBySystemInterruptBlock)(void);
@property (nonatomic, copy) void(^resumeBySystemInterruptBlock)(void);
@property (nonatomic, copy) void(^clickBlackAreaBlock)(void);

#pragma mark - Public
// 设置播放URL
- (void)setURL:(NSURL *)URL;
// 指定到某个时间播放
- (void)resumeWithTime:(CGFloat)time;
// 播放
- (void)play;
// 暂停
- (void)pause;
// 停止播放
- (void)stop;
- (void)changeCurrentURLAsset:(AVURLAsset *)urlAsset;


// 设置视频是否静音
- (void)setVideoSoundEnable:(BOOL)enable;

- (void)updateVolume:(CGFloat)volume;

- (void)playFromBegin;

#pragma mark - Readonly
@property (nonatomic, readonly) CGFloat duration;
@property (nonatomic, readonly) CGFloat currentTime;





@end
