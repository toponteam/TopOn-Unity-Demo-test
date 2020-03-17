using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace AnyThinkAds.Api
{
    public interface ATInterstitialAdListener
    {
		/***
		 * 加载广告成功
		 * @param unitId 广告位id
		 */ 
        void onInterstitialAdLoad(string unitId);
		/***
		 * 加载广告失败
		 * @param unitId 广告位id
		 * @param code 错误码
		 * @param message 错误信息
		 */ 
        void onInterstitialAdLoadFail(string unitId, string code, string message);
		/***
		 * 广告展示
		 * @param unitId 广告位id
		 */ 
        void onInterstitialAdShow(string unitId, ATCallbackInfo callbackInfo);
		/***
		 * 广告展示失败
		 * @param unitId 广告位id
		 */ 
        void onInterstitialAdFailedToShow(string unitId);
		/***
		 * 广告关闭
		 * @param unitId 广告位id
		 */ 
        void onInterstitialAdClose(string unitId, ATCallbackInfo callbackInfo);
		/***
		 * 广告点击
		 * @param unitId 广告位id
		 */ 
        void onInterstitialAdClick(string unitId, ATCallbackInfo callbackInfo);
        /**
        *广告开始播放视频
        * @param unitId 广告位id
        */
        void onInterstitialAdStartPlayingVideo(string unitId);
        /**
        *广告视频播放结束
        * @param unitId 广告位id
        */
        void onInterstitialAdEndPlayingVideo(string unitId);
        /**
        *广告播放视频失败
        * @param unitId 广告位id
		* @param code 错误码
		* @param message 错误信息
        */
        void onInterstitialAdFailedToPlayVideo(string unitId, string code, string message);
    }
}
