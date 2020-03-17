using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace AnyThinkAds.Api
{
    public interface ATRewardedVideoListener
    {
		/***
		 * 加载广告成功
		 */ 
        void onRewardedVideoAdLoaded(string unitId);
		/***
		 * 加载广告失败
		 */ 
        void onRewardedVideoAdLoadFail(string unitId,string code, string message);
		/***
		 * 视频播放开始
		 */ 
        void onRewardedVideoAdPlayStart(string unitId, ATCallbackInfo callbackInfo);
		/***
		 * 视频播放结束
		 */ 
        void onRewardedVideoAdPlayEnd(string unitId, ATCallbackInfo callbackInfo);
		/***
		 * 视频播放失败
		 * @param code 错误码
		 * @param message 错误信息
		 */ 
        void onRewardedVideoAdPlayFail(string unitId,string code, string message);
		/**
		 * 视频页面关闭
		 * @param isReward 视频是否播放完成
		 */ 
        void onRewardedVideoAdPlayClosed(string unitId,bool isReward, ATCallbackInfo callbackInfo);
		/***
		 * 视频点击
		 */ 
        void onRewardedVideoAdPlayClicked(string unitId, ATCallbackInfo callbackInfo);
        /**
         * 激励下发
         */
        void onReward(string unitId, ATCallbackInfo callbackInfo);

    }
}
