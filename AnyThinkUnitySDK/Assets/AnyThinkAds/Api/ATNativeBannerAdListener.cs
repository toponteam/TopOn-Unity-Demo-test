using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace AnyThinkAds.Api
{
    public interface ATNativeBannerAdListener
    {
		/***
		 * 广告请求成功
		 */
        void onAdLoaded(string unitId);
		/***
		 * 广告请求失败
		 */ 
        void onAdLoadFail(string unitId, string code, string message);
		/***
		 * 广告展示
		 */ 
        void onAdImpressed(string unitId, ATCallbackInfo callbackInfo);
		/**
		 * 广告点击
		 */ 
        void onAdClicked(string unitId, ATCallbackInfo callbackInfo);
        /**
		 * 广告自动刷新
		 */ 
        void onAdAutoRefresh(string unitId, ATCallbackInfo callbackInfo);
        /**
		 * 广告自动刷新失败
		 */ 
		void onAdAutoRefreshFailure(string unitId, string code, string message);
        /**
		 * 关闭按钮被点击（内部逻辑不会移除ad view）
		 */ 
        void onAdCloseButtonClicked(string unitId);
    }
}
