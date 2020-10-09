using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Api;

namespace AnyThinkAds.Common
{
    public interface IATBannerAdClient 
    {
		/***
		 * 请求广告  
		 * @param placementId  广告位id
		 * @parm mapJson 各平台的私有属性 一般可以不调用
		 */
        void loadBannerAd(string placementId, string mapJson);
		/***
		 * 
		 * 设置监听回调接口
		 * 
		 * @param listener  
		 */
        void setListener(ATBannerAdListener listener);
        /***
         * 
         * 展示广告,
         * @param placementId 
         * @param pass bottom or top for position
         */
        void showBannerAd(string placementId, string position);
        /***
		 * 
		 * 展示广告,
		 * @param placementId 
		 * @param rect the region used to show banner ad; currently only x&y fields in rect are used(as the origin, or top left corner of the banner).
		 */
        void showBannerAd(string placementId, ATRect rect);
		/***
		 * 
		 * 清理广告
		 * @param placementId 
		 * @param anyThinkNativeAdView  这里的属性是显示区域坐标等配置,需要自行设置
		 */
        void cleanBannerAd(string placementId);
        /***
		 * 
		 * 隐藏广告
		 * @param placementId 
		 * @param rect the region used to show banner ad.
		 */
        void hideBannerAd(string placementId);
        /***
		 * 
		 * （重新）展示之前隐藏的广告
		 * @param placementId 
		 */
        void showBannerAd(string placementId);
		/***
		 * 清理缓存
		 */ 
        void cleanCache(string placementId);
    }
}
