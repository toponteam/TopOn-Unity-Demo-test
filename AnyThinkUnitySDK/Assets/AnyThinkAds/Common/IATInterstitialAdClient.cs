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
		 * @param placementId  广告位id
		 * @parm mapJson 各平台的私有属性 一般可以不调用
		 */
        void loadInterstitialAd(string placementId, string mapJson);
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
        bool hasInterstitialAdReady(string placementId);
        /**
        * 获取广告状态信息（是否正在加载、是否存在可以展示广告、广告缓存详细信息）
        * @param unityid
        * 
        */
        string checkAdStatus(string placementId);
        /***
		 * 显示广告
		 */
        void showInterstitialAd(string placementId, string mapJson);


		/***
		 * 获取所有可用缓存广告
		 */
		string getValidAdCaches(string placementId);

        void entryScenarioWithPlacementID(string placementId, string scenarioID);
		

        string checkAutoAdStatus(string placementId);

		void addAutoLoadAdPlacementID(string[] placementIDList);

        void removeAutoLoadAdPlacementID(string placementId);

		bool autoLoadInterstitialAdReadyForPlacementID(string placementId);

		string getAutoValidAdCaches(string placementId);

        void setAutoLocalExtra(string placementId, string mapJson);

        void entryAutoAdScenarioWithPlacementID(string placementId, string scenarioID);

		void showAutoAd(string placementId, string mapJson);
	}
}
