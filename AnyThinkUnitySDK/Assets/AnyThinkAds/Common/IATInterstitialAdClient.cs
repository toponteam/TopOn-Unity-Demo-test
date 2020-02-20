using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Api;

namespace AnyThinkAds.Common
{
    public interface IATInterstitialAdClient 
    {
		/***
		 * 请求广告  
		 * @param unitId  广告位id
		 * @parm mapJson 各平台的私有属性 一般可以不调用
		 */
        void loadInterstitialAd(string unitId, string mapJson);
		/***
		 * 
		 * 设置监听回调接口
		 * 
		 * @param listener  
		 */
        void setListener(ATInterstitialAdListener listener);
        /**
		 * 是否存在可以展示的广告
		 * @param unityid
		 */
        bool hasInterstitialAdReady(string unitId);
        /***
		 * 显示广告
		 */
        void showInterstitialAd(string unitId, string mapJson);
		/***
		 * 清理缓存
		 */ 
        void cleanCache(string unitId);
    }
}
