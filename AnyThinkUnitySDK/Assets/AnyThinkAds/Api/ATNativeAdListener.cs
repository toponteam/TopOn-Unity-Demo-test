using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace AnyThinkAds.Api
{
    public interface ATNativeAdListener
    {
		/***
		 * 广告请求成功
		 */
        void onAdLoaded(string placementId);
		/***
		 * 广告请求失败
		 */ 
        void onAdLoadFail(string placementId, string code, string message);
		/***
		 * 广告展示
		 */ 
        void onAdImpressed(string placementId, ATCallbackInfo callbackInfo);
		/**
		 * 广告点击
		 */ 
        void onAdClicked(string placementId, ATCallbackInfo callbackInfo);
		/***
		 * 视屏播放开始 如果有
		 */ 
        void onAdVideoStart(string placementId);
		/***
		 * 视屏播放结束 如果有
		 */ 
        void onAdVideoEnd(string placementId);
		/***
		 * 视屏播放进度 如果有
		 */ 
        void onAdVideoProgress(string placementId,int progress);
        /***
		 * 广告关闭按钮点击 如果有
		 */
        void onAdCloseButtonClicked(string placementId, ATCallbackInfo callbackInfo);

    }
}
