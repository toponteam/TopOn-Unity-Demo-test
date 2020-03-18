using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace AnyThinkAds.Api
{
    public interface ATBannerAdListener
    {
		/***
		 * 广告请求成功
		 */
        void onAdLoad(string unitId);
		/***
		 * 广告请求失败
		 */ 
        void onAdLoadFail(string unitId, string code, string message);
		/***
		 * 广告展示
		 */ 
        void onAdImpress(string unitId, ATCallbackInfo callbackInfo);
		/**
		 * 广告点击
		 */ 
        void onAdClick(string unitId, ATCallbackInfo callbackInfo);
		/**
		 * 广告自动刷新
		 */
        void onAdAutoRefresh(string unitId, ATCallbackInfo callbackInfo);
        /**
        *广告自动刷新失败
        */
        void onAdAutoRefreshFail(string unitId, string code, string message);
        /**
        *广告关闭；某些厂商不支持
        */
        void onAdClose(string unitId);
        /**
        *广告关闭；某些厂商不支持
        */
        void onAdCloseButtonTapped(string unitId, ATCallbackInfo callbackInfo);
    }
}
