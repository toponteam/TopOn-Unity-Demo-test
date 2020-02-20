using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Api;

namespace AnyThinkAds.Common
{
    public interface IATRewardedVideoAdClient
    {
		/**
		 * 请求视屏广告
		 * @param unitId 广告位id
		 * @parm mapJson 平台私有参数 一般不些
		 */
        void loadVideoAd(string unitId, string mapJson);
		/**
		 * @param listener 监听回调
		 */ 
        void setListener(ATRewardedVideoListener listener);
		/**
		 * 是否存在可以展示的广告
		 * @param unityid
		 * 
		 */ 
        bool hasAdReady(string unitId);
		/***
		 * 设置用户id
		 * @param unitid 
		 * @param userid 用户id
		 * @param customData  其他数据
		 */ 
        void setUserData(string unitId,string userId, string customData);
		/***
		 * 显示广告
		 */
        void showAd(string unitId, string mapJson);
		/***
		 * 
		 */
        void cleanAd(string unitId);
		/**
		 * 显示屏幕
		 */
        void onApplicationForces(string unitId);
		/***
		 * 暂停屏幕
		 */ 
        void onApplicationPasue(string unitId);
		/**
		 * 设置各种平台的私有信息
		 * 如:gdrp等信息
		 * 参数可以参考demo
		 */ 
		void addsetting (string unitId,string json);
    }
}
