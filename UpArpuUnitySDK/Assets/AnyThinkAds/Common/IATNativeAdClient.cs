using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Api;

namespace AnyThinkAds.Common
{
    public interface IATNativeAdClient 
    {
		/***
		 * 请求广告  
		 * @param unitId  广告位id
		 * @parm mapJson 各平台的私有属性 一般可以不调用
		 */
        void loadNativeAd(string unitId, string mapJson);
		/***
		 * 判断是否有广告存在
		 * 可以在显示广告之前调用
		 * @param unitId  广告位id
		 */
        bool hasAdReady(string unitId);
		/***
		 * 
		 * 设置监听回调接口
		 * 
		 * @param listener  
		 */
        void setListener(ATNativeAdListener listener);
		/***
		 * 
		 * 展示广告,
		 * @param unitId 
		 * @param anyThinkNativeAdView  这里的属性是显示区域坐标等配置,需要自行设置
		 */
        void renderAdToScene(string unitId, ATNativeAdView anyThinkNativeAdView);

		/***
		 * 
		 * 清理广告
		 * @param unitId 
		 * @param anyThinkNativeAdView  这里的属性是显示区域坐标等配置,需要自行设置
		 */
        void cleanAdView(string unitId, ATNativeAdView anyThinkNativeAdView);
		/***
		 * 页面显示
		 */
        void onApplicationForces(string unitId, ATNativeAdView anyThinkNativeAdView);
		/***
		 * 页面隐藏
		 */ 
        void onApplicationPasue(string unitId, ATNativeAdView anyThinkNativeAdView);
		/***
		 * 清理缓存
		 */ 
        void cleanCache(string unitId);
		/**
		 * 设置本地参数
		 */
		void setLocalExtra(string unitid,string mapJson);

    }
}
