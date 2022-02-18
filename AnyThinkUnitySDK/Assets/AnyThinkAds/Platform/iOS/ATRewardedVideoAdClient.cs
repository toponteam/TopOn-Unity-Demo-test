using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;
using AnyThinkAds.ThirdParty.LitJson;

namespace AnyThinkAds.iOS {
	public class ATRewardedVideoAdClient : IATRewardedVideoAdClient {
		private  ATRewardedVideoListener anyThinkListener;

		public void addsetting (string placementId,string json){
			//todo...
		}
		public void setListener(ATRewardedVideoListener listener) {
			Debug.Log("Unity: ATRewardedVideoAdClient::setListener()");
	        anyThinkListener = listener;
	    }

		public void loadVideoAd(string placementId, string mapJson) {
			Debug.Log("Unity: ATRewardedVideoAdClient::loadVideoAd()");
			ATRewardedVideoWrapper.setClientForPlacementID(placementId, this);
			ATRewardedVideoWrapper.loadRewardedVideo(placementId, mapJson);
		}

		public bool hasAdReady(string placementId) {
			Debug.Log("Unity: ATRewardedVideoAdClient::hasAdReady()");
			return ATRewardedVideoWrapper.isRewardedVideoReady(placementId);
		}

		//To be implemented
		public void setUserData(string placementId, string userId, string customData) {
			Debug.Log("Unity: ATRewardedVideoAdClient::setUserData()");
	    }

	    public void showAd(string placementId, string mapJson) {
	    	Debug.Log("Unity: ATRewardedVideoAdClient::showAd()");
	    	ATRewardedVideoWrapper.showRewardedVideo(placementId, mapJson);
	    }

	    public void cleanAd(string placementId) {
	    	Debug.Log("Unity: ATRewardedVideoAdClient::cleanAd()");
	    	ATRewardedVideoWrapper.clearCache();
	    }

	    public void onApplicationForces(string placementId) {
			Debug.Log("Unity: ATRewardedVideoAdClient::onApplicationForces()");
	    }

	    public void onApplicationPasue(string placementId) {
			Debug.Log("Unity: ATRewardedVideoAdClient::onApplicationPasue()");
	    }

	    public string checkAdStatus(string placementId) {
	    	Debug.Log("Unity: ATRewardedVideoAdClient::checkAdStatus()");
	    	return ATRewardedVideoWrapper.checkAdStatus(placementId);
	    }

		public void entryScenarioWithPlacementID(string placementId, string scenarioID){
            Debug.Log("Unity: ATRewardedVideoAdClient::entryScenarioWithPlacementID()");
			ATRewardedVideoWrapper.entryScenarioWithPlacementID(placementId,scenarioID);
		}

		public string getValidAdCaches(string placementId)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient::getValidAdCaches()");
			return ATRewardedVideoWrapper.getValidAdCaches(placementId);
		}

        // Auto
		public void addAutoLoadAdPlacementID(string[] placementIDList)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient:addAutoLoadAdPlacementID()");

	     	if (placementIDList != null && placementIDList.Length > 0)
            {
				foreach (string placementID in placementIDList)
        		{
					ATRewardedVideoWrapper.setClientForPlacementID(placementID, this);
				}

                string placementIDListString = JsonMapper.ToJson(placementIDList);
				ATRewardedVideoWrapper.addAutoLoadAdPlacementID(placementIDListString);
                Debug.Log("addAutoLoadAdPlacementID, placementIDList === " + placementIDListString);
            }
            else
            {
                Debug.Log("addAutoLoadAdPlacementID, placementIDList = null");
            } 			
		}

		public void removeAutoLoadAdPlacementID(string placementId) 
		{
			Debug.Log("Unity: ATRewardedVideoAdClient:removeAutoLoadAdPlacementID()");
			ATRewardedVideoWrapper.removeAutoLoadAdPlacementID(placementId);
		}

		public bool autoLoadRewardedVideoReadyForPlacementID(string placementId) 
		{
			Debug.Log("Unity: ATRewardedVideoAdClient:autoLoadRewardedVideoReadyForPlacementID()");
			return ATRewardedVideoWrapper.autoLoadRewardedVideoReadyForPlacementID(placementId);
		}
		public string getAutoValidAdCaches(string placementId)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient:getAutoValidAdCaches()");
			return ATRewardedVideoWrapper.getAutoValidAdCaches(placementId);
		}
		public string checkAutoAdStatus(string placementId) {
	    	Debug.Log("Unity: ATRewardedVideoAdClient::checkAutoAdStatus()");
	    	return ATRewardedVideoWrapper.checkAutoAdStatus(placementId);
	    }

		public void setAutoLocalExtra(string placementId, string mapJson) 
		{
			Debug.Log("Unity: ATRewardedVideoAdClient:setAutoLocalExtra()");
			ATRewardedVideoWrapper.setAutoLocalExtra(placementId, mapJson);
		}
		public void entryAutoAdScenarioWithPlacementID(string placementId, string scenarioID) 
		{
			Debug.Log("Unity: ATRewardedVideoAdClient:entryAutoAdScenarioWithPlacementID()");
			ATRewardedVideoWrapper.entryAutoAdScenarioWithPlacementID(placementId, scenarioID);
		}
		public void showAutoAd(string placementId, string mapJson) 
		{
	    	Debug.Log("Unity: ATRewardedVideoAdClient::showAutoAd()");
	    	ATRewardedVideoWrapper.showAutoRewardedVideo(placementId, mapJson);
	    }

		//auto callbacks
	    public void startLoadingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATRewardedVideoAdClient::startLoadingADSource()");
            if (anyThinkListener != null) anyThinkListener.startLoadingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }
	    public void finishLoadingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATRewardedVideoAdClient::finishLoadingADSource()");
            if (anyThinkListener != null) anyThinkListener.finishLoadingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }	
	    public void failToLoadADSource(string placementId, string callbackJson,string code, string error) 
		{
	        Debug.Log("Unity: ATRewardedVideoAdClient::failToLoadADSource()");
	        if (anyThinkListener != null) anyThinkListener.failToLoadADSource(placementId,new ATCallbackInfo(callbackJson), code, error);
	    }
		public void startBiddingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATRewardedVideoAdClient::startBiddingADSource()");
            if (anyThinkListener != null) anyThinkListener.startBiddingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }
	    public void finishBiddingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATRewardedVideoAdClient::finishBiddingADSource()");
            if (anyThinkListener != null) anyThinkListener.finishBiddingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }	
	    public void failBiddingADSource(string placementId, string callbackJson,string code, string error) 
		{
	        Debug.Log("Unity: ATRewardedVideoAdClient::failBiddingADSource()");
	        if (anyThinkListener != null) anyThinkListener.failBiddingADSource(placementId,new ATCallbackInfo(callbackJson), code, error);
	    }







		

		//Callbacks
		public void onRewardedVideoAdLoaded(string placementId) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdLoaded()");
	        if(anyThinkListener != null) anyThinkListener.onRewardedVideoAdLoaded(placementId);
	    }

	    public void onRewardedVideoAdFailed(string placementId, string code, string error) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdFailed()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdLoadFail(placementId, code, error);
	    }

        public void onRewardedVideoAdPlayStart(string placementId, string callbackJson) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayStart()");
            if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayStart(placementId, new ATCallbackInfo(callbackJson));
	    }

        public void onRewardedVideoAdPlayEnd(string placementId, string callbackJson) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayEnd()");
            if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayEnd(placementId, new ATCallbackInfo(callbackJson));
	    }

	    public void onRewardedVideoAdPlayFailed(string placementId, string code, string error) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayFailed()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayFail(placementId, code, error);
	    }

        public void onRewardedVideoAdClosed(string placementId, bool isRewarded, string callbackJson) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdClosed()");
            if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayClosed(placementId, isRewarded, new ATCallbackInfo(callbackJson));
	    }

        public void onRewardedVideoAdPlayClicked(string placementId, string callbackJson) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayClicked()");
            if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayClicked(placementId, new ATCallbackInfo(callbackJson));
	    }

        public void onRewardedVideoReward(string placementId, string callbackJson) {
            Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoReward()");
            if (anyThinkListener != null) anyThinkListener.onReward(placementId, new ATCallbackInfo(callbackJson));
        }

		//--------again callback-------
		public void onRewardedVideoAdAgainPlayStart(string placementId, string callbackJson)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdAgainPlayStart()");
			if (anyThinkListener != null && anyThinkListener is ATRewardedVideoExListener)
			{
				((ATRewardedVideoExListener)anyThinkListener).onRewardedVideoAdAgainPlayStart(placementId, new ATCallbackInfo(callbackJson));
			}
		}

		public void onRewardedVideoAdAgainPlayEnd(string placementId, string callbackJson)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdAgainPlayEnd()");
			if (anyThinkListener != null && anyThinkListener is ATRewardedVideoExListener)
			{
				((ATRewardedVideoExListener)anyThinkListener).onRewardedVideoAdAgainPlayEnd(placementId, new ATCallbackInfo(callbackJson));
			}
		}


		public void onRewardedVideoAdAgainPlayFailed(string placementId, string code, string error)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdAgainPlayFailed()");
			if (anyThinkListener != null && anyThinkListener is ATRewardedVideoExListener)
			{
				((ATRewardedVideoExListener)anyThinkListener).onRewardedVideoAdAgainPlayFail(placementId, code, error);
			}
		}


		public void onRewardedVideoAdAgainPlayClicked(string placementId, string callbackJson)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdAgainPlayClicked()");
			if (anyThinkListener != null && anyThinkListener is ATRewardedVideoExListener)
			{
				((ATRewardedVideoExListener)anyThinkListener).onRewardedVideoAdAgainPlayClicked(placementId, new ATCallbackInfo(callbackJson));
			}
		}


		public void onAgainReward(string placementId, string callbackJson)
		{
			Debug.Log("Unity: ATRewardedVideoAdClient::onAgainReward()");
			if (anyThinkListener != null && anyThinkListener is ATRewardedVideoExListener)
			{
				((ATRewardedVideoExListener)anyThinkListener).onAgainReward(placementId, new ATCallbackInfo(callbackJson));
			}
		}

	}
}
