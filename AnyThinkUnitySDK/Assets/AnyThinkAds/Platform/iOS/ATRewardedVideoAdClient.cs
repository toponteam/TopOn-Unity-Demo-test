using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;

namespace AnyThinkAds.iOS {
	public class ATRewardedVideoAdClient : IATRewardedVideoAdClient {
		private  ATRewardedVideoListener anyThinkListener;

		public void addsetting (string unitId,string json){
			//todo...
		}
		public void setListener(ATRewardedVideoListener listener) {
			Debug.Log("Unity: ATRewardedVideoAdClient::setListener()");
	        anyThinkListener = listener;
	    }

		public void loadVideoAd(string unitId, string mapJson) {
			Debug.Log("Unity: ATRewardedVideoAdClient::loadVideoAd()");
			ATRewardedVideoWrapper.setClientForPlacementID(unitId, this);
			ATRewardedVideoWrapper.loadRewardedVideo(unitId, mapJson);
		}

		public bool hasAdReady(string unitId) {
			Debug.Log("Unity: ATRewardedVideoAdClient::hasAdReady()");
			return ATRewardedVideoWrapper.isRewardedVideoReady(unitId);
		}

		//To be implemented
		public void setUserData(string unitId, string userId, string customData) {
			Debug.Log("Unity: ATRewardedVideoAdClient::setUserData()");
	    }

	    public void showAd(string unitId, string mapJson) {
	    	Debug.Log("Unity: ATRewardedVideoAdClient::showAd()");
	    	ATRewardedVideoWrapper.showRewardedVideo(unitId, mapJson);
	    }

	    public void cleanAd(string unitId) {
	    	Debug.Log("Unity: ATRewardedVideoAdClient::cleanAd()");
	    	ATRewardedVideoWrapper.clearCache();
	    }

	    public void onApplicationForces(string unitId) {
			Debug.Log("Unity: ATRewardedVideoAdClient::onApplicationForces()");
	    }

	    public void onApplicationPasue(string unitId) {
			Debug.Log("Unity: ATRewardedVideoAdClient::onApplicationPasue()");
	    }

		//Callbacks
	    public void onRewardedVideoAdLoaded(string unitId) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdLoaded()");
	        if(anyThinkListener != null) anyThinkListener.onRewardedVideoAdLoaded(unitId);
	    }

	    public void onRewardedVideoAdFailed(string unitId, string code, string error) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdFailed()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdLoadFail(unitId, code, error);
	    }

	    public void onRewardedVideoAdPlayStart(string unitId) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayStart()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayStart(unitId);
	    }

	    public void onRewardedVideoAdPlayEnd(string unitId) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayEnd()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayEnd(unitId);
	    }

	    public void onRewardedVideoAdPlayFailed(string unitId, string code, string error) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayFailed()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayFail(unitId, code, error);
	    }

	    public void onRewardedVideoAdClosed(string unitId, bool isRewarded) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdClosed()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayClosed(unitId, isRewarded);
	    }

	    public void onRewardedVideoAdPlayClicked(string unitId) {
	        Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoAdPlayClicked()");
	        if (anyThinkListener != null) anyThinkListener.onRewardedVideoAdPlayClicked(unitId);
	    }

        public void onRewardedVideoReward(string unitId) {
            Debug.Log("Unity: ATRewardedVideoAdClient::onRewardedVideoReward()");
            if (anyThinkListener != null) anyThinkListener.onReward(unitId);
        }
	}
}
