using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;

namespace AnyThinkAds.iOS {
	public class ATInterstitialAdClient : IATInterstitialAdClient {
		private  ATInterstitialAdListener anyThinkListener;

		public void addsetting(string unitId,string json){
			//todo...
		}

		public void setListener(ATInterstitialAdListener listener) {
			Debug.Log("Unity: ATInterstitialAdClient::setListener()");
	        anyThinkListener = listener;
	    }

	    public void loadInterstitialAd(string unitId, string mapJson) {
			Debug.Log("Unity: ATInterstitialAdClient::loadInterstitialAd()");
            ATInterstitialAdWrapper.setClientForPlacementID(unitId, this);
			ATInterstitialAdWrapper.loadInterstitialAd(unitId, mapJson);
		}

		public bool hasInterstitialAdReady(string unitId) {
			Debug.Log("Unity: ATInterstitialAdClient::hasInterstitialAdReady()");
			return ATInterstitialAdWrapper.hasInterstitialAdReady(unitId);
		}

		public void showInterstitialAd(string unitId, string mapJson) {
			Debug.Log("Unity: ATInterstitialAdClient::showInterstitialAd()");
			ATInterstitialAdWrapper.showInterstitialAd(unitId, mapJson);
		}

		public void cleanCache(string unitId) {
			Debug.Log("Unity: ATInterstitialAdClient::cleanCache()");
			ATInterstitialAdWrapper.clearCache(unitId);
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

	    public void OnInterstitialAdVideoPlayStart(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdPlayStart()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdStartPlayingVideo(placementID);
	    }

	    public void OnInterstitialAdVideoPlayEnd(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdVideoPlayEnd()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdEndPlayingVideo(placementID);
	    }

	    public void OnInterstitialAdShow(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdShow()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdShow(placementID);
	    }

	    public void OnInterstitialAdFailedToShow(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdFailedToShow()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdFailedToShow(placementID);
	    }

	    public void OnInterstitialAdClick(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdClick()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdClick(placementID);
	    }

	    public void OnInterstitialAdClose(string placementID) {
	    	Debug.Log("Unity: ATInterstitialAdClient::OnInterstitialAdClose()");
	        if (anyThinkListener != null) anyThinkListener.onInterstitialAdClose(placementID);
	    }
	}
}
