using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;
using AnyThinkAds.ThirdParty.LitJson;


namespace AnyThinkAds.iOS {
	
	public class ATInterstitialAdClient : IATInterstitialAdClient {
		private  ATInterstitialAdListener anyThinkListener;

		public void addsetting(string placementId,string json){
			//todo...
		}

		public void setListener(ATInterstitialAdListener listener) {
			Debug.Log("Unity: ATInterstitialAdClient::setListener()");
	        anyThinkListener = listener;
	    }

	    public void loadInterstitialAd(string placementId, string mapJson) {
			Debug.Log("Unity: ATInterstitialAdClient::loadInterstitialAd()");
            ATInterstitialAdWrapper.setClientForPlacementID(placementId, this);
			ATInterstitialAdWrapper.loadInterstitialAd(placementId, mapJson);
		}

		public bool hasInterstitialAdReady(string placementId) {
			Debug.Log("Unity: ATInterstitialAdClient::hasInterstitialAdReady()");
			return ATInterstitialAdWrapper.hasInterstitialAdReady(placementId);
		}

		public void showInterstitialAd(string placementId, string mapJson) {
			Debug.Log("Unity: ATInterstitialAdClient::showInterstitialAd()");
			ATInterstitialAdWrapper.showInterstitialAd(placementId, mapJson);
		}

		public void cleanCache(string placementId) {
			Debug.Log("Unity: ATInterstitialAdClient::cleanCache()");
			ATInterstitialAdWrapper.clearCache(placementId);
		}

		public string checkAdStatus(string placementId) {
			Debug.Log("Unity: ATInterstitialAdClient::checkAdStatus()");
			return ATInterstitialAdWrapper.checkAdStatus(placementId);
		}

		public string getValidAdCaches(string placementId)
		{
			Debug.Log("Unity: ATInterstitialAdClient::getValidAdCaches()");
			return ATInterstitialAdWrapper.getValidAdCaches(placementId);
		}

		public void entryScenarioWithPlacementID(string placementId, string scenarioID){
            Debug.Log("Unity: ATInterstitialAdClient::entryScenarioWithPlacementID()");
			ATInterstitialAdWrapper.entryScenarioWithPlacementID(placementId,scenarioID);
		}


		//Callbacks
		public void OnInterstitialAdLoaded(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdLoaded()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdLoad(placementID);
	    }

	    public void OnInterstitialAdLoadFailure(string placementID, string code, string error) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdLoadFailure()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdLoadFail(placementID, code, error);
	    }

	     public void OnInterstitialAdVideoPlayFailure(string placementID, string code, string error) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdVideoPlayFailure()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdFailedToPlayVideo(placementID, code, error);
	    }

	    public void OnInterstitialAdVideoPlayStart(string placementID, string callbackJson) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdPlayStart()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdStartPlayingVideo(placementID, new ATCallbackInfo(callbackJson));
	    }

	    public void OnInterstitialAdVideoPlayEnd(string placementID, string callbackJson) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdVideoPlayEnd()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdEndPlayingVideo(placementID, new ATCallbackInfo(callbackJson));
	    }

        public void OnInterstitialAdShow(string placementID, string callbackJson) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdShow()");
            if (anyThinkListener != null) anyThinkListener.onInterstitialAdShow(placementID, new ATCallbackInfo(callbackJson));
	    }

        public void OnInterstitialAdFailedToShow(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdFailedToShow()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdFailedToShow(placementID);
	    }

        public void OnInterstitialAdClick(string placementID, string callbackJson) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdClick()");
            if (anyThinkListener != null) anyThinkListener.onInterstitialAdClick(placementID, new ATCallbackInfo(callbackJson));
	    }

        public void OnInterstitialAdClose(string placementID, string callbackJson) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdClose()");
            if (anyThinkListener != null) anyThinkListener.onInterstitialAdClose(placementID, new ATCallbackInfo(callbackJson));
	    }
		
		//auto callbacks
	    public void startLoadingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATInterstitialAdClient::startLoadingADSource()");
            if (anyThinkListener != null) anyThinkListener.startLoadingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }
	    public void finishLoadingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATInterstitialAdClient::finishLoadingADSource()");
            if (anyThinkListener != null) anyThinkListener.finishLoadingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }	
	    public void failToLoadADSource(string placementId, string callbackJson,string code, string error) 
		{
	        Debug.Log("Unity: ATInterstitialAdClient::failToLoadADSource()");
	        if (anyThinkListener != null) anyThinkListener.failToLoadADSource(placementId, new ATCallbackInfo(callbackJson),code, error);
	    }
		public void startBiddingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATInterstitialAdClient::startBiddingADSource()");
            if (anyThinkListener != null) anyThinkListener.startBiddingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }
	    public void finishBiddingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATInterstitialAdClient::finishBiddingADSource()");
            if (anyThinkListener != null) anyThinkListener.finishBiddingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }	
	    public void failBiddingADSource(string placementId,string callbackJson, string code, string error) 
		{
	        Debug.Log("Unity: ATInterstitialAdClient::failBiddingADSource()");
	        if (anyThinkListener != null) anyThinkListener.failBiddingADSource(placementId, new ATCallbackInfo(callbackJson),code, error);
	    }

	    // Auto
		public void addAutoLoadAdPlacementID(string[] placementIDList) 
		{
			Debug.Log("Unity: ATInterstitialAdClient:addAutoLoadAdPlacementID()");

		

	     	if (placementIDList != null && placementIDList.Length > 0)
            {
				foreach (string placementID in placementIDList)
        		{
					ATInterstitialAdWrapper.setClientForPlacementID(placementID, this);
				}

                string placementIDListString = JsonMapper.ToJson(placementIDList);
				ATInterstitialAdWrapper.addAutoLoadAdPlacementID(placementIDListString);
                Debug.Log("addAutoLoadAdPlacementID, placementIDList === " + placementIDListString);
            }
            else
            {
                Debug.Log("addAutoLoadAdPlacementID, placementIDList = null");
            } 		

		}

		public void removeAutoLoadAdPlacementID(string placementId) 
		{
			Debug.Log("Unity: ATInterstitialAdClient:removeAutoLoadAdPlacementID()");
			ATInterstitialAdWrapper.removeAutoLoadAdPlacementID(placementId);
		}

		public bool autoLoadInterstitialAdReadyForPlacementID(string placementId) 
		{
			Debug.Log("Unity: ATInterstitialAdClient:autoLoadInterstitialAdReadyForPlacementID()");
			return ATInterstitialAdWrapper.autoLoadInterstitialAdReadyForPlacementID(placementId);
		}
		public string getAutoValidAdCaches(string placementId)
		{
			Debug.Log("Unity: ATInterstitialAdClient:getAutoValidAdCaches()");
			return ATInterstitialAdWrapper.getAutoValidAdCaches(placementId);
		}

		public string checkAutoAdStatus(string placementId) {
			Debug.Log("Unity: ATInterstitialAdClient::checkAutoAdStatus()");
			return ATInterstitialAdWrapper.checkAutoAdStatus(placementId);
		}	


		public void setAutoLocalExtra(string placementId, string mapJson) 
		{
			Debug.Log("Unity: ATInterstitialAdClient:setAutoLocalExtra()");
			ATInterstitialAdWrapper.setAutoLocalExtra(placementId, mapJson);
		}
		public void entryAutoAdScenarioWithPlacementID(string placementId, string scenarioID) 
		{
			Debug.Log("Unity: ATInterstitialAdClient:entryAutoAdScenarioWithPlacementID()");
			ATInterstitialAdWrapper.entryAutoAdScenarioWithPlacementID(placementId, scenarioID);
		}
		public void showAutoAd(string placementId, string mapJson) 
		{
	    	Debug.Log("Unity: ATInterstitialAdClient::showAutoAd()");
	    	ATInterstitialAdWrapper.showAutoInterstitialAd(placementId, mapJson);
	    }


	}
}
