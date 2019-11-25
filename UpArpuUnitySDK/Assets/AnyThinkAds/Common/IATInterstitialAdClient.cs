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
		/***
		 * 
		 * 展示广告,
		 * @param unitId 
		 * @param rect the region used to show banner ad; currently only x&y fields in rect are used(as the origin, or top left corner of the banner).
		 */
		 bool hasInterstitialAdReady(string unitId);
		/***
		 * 设置用户id
		 * @param unitid 
		 * @param userid 用户id
		 * @param customData  其他数据
		 */ 
        void showInterstitialAd(string unitId);
		/***
		 * 清理缓存
		 */ 
        void cleanCache(string unitId);
    }
}
