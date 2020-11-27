using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;

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
	}
}
