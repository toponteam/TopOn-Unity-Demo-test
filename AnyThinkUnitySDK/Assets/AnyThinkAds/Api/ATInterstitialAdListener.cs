using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace AnyThinkAds.Api
{
    public interface ATInterstitialAdListener
    {
		/***
		 * 加载广告成功
		 * @param placementId 广告位id
		 */ 
        void onInterstitialAdLoad(string placementId);
		/***
		 * 加载广告失败
		 * @param placementId 广告位id
		 * @param code 错误码
		 * @param message 错误信息
		 */ 
        void onInterstitialAdLoadFail(string placementId, string code, string message);
		/***
		 * 广告展示
		 * @param placementId 广告位id
		 */ 
        void onInterstitialAdShow(string placementId, ATCallbackInfo callbackInfo);
		/***
		 * 广告展示失败
		 * @param placementId 广告位id
		 */ 
        void onInterstitialAdFailedToShow(string placementId);
		/***
		 * 广告关闭
		 * @param placementId 广告位id
		 */ 
        void onInterstitialAdClose(string placementId, ATCallbackInfo callbackInfo);
		/***
		 * 广告点击
		 * @param placementId 广告位id
		 */ 
        void onInterstitialAdClick(string placementId, ATCallbackInfo callbackInfo);
        /**
        *广告开始播放视频
        * @param placementId 广告位id
        */
        void onInterstitialAdStartPlayingVideo(string placementId, ATCallbackInfo callbackInfo);
        /**
        *广告视频播放结束
        * @param placementId 广告位id
        */
        void onInterstitialAdEndPlayingVideo(string placementId, ATCallbackInfo callbackInfo);
        /**
        *广告播放视频失败
        * @param placementId 广告位id
		* @param code 错误码
		* @param message 错误信息
        */
        void onInterstitialAdFailedToPlayVideo(string placementId, string code, string message);
    }
}
