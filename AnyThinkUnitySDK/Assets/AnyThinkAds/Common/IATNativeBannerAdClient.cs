using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Api;

namespace AnyThinkAds.Common
{
	public interface IATNativeBannerAdClient
	{
		/***
		 * 请求广告  
		 * @param unitId  广告位id
		 * @parm mapJson 各平台的私有属性 一般可以不调用
		 */
		void loadAd(string unitId, string mapJson);
		
		/***
		 * 判断是否有广告存在
		 * 可以在显示广告之前调用
		 * @param unitId  广告位id
		 */
		bool adReady(string unitId);
		/***
		 * 
		 * 设置监听回调接口
		 * 
		 * @param listener  
		 */
        void setListener(ATNativeBannerAdListener listener);
        /***
		 * 
		 * 展示广告,
		 * @param unitId 
		 * @param rect 
		 */
        void showAd(string unitId, ATRect rect, Dictionary<string, string> pairs);
        /***
		 * 
		 * 移除广告
		 * @param unitId 
		 */
        void removeAd(string unitId);
	}
}
