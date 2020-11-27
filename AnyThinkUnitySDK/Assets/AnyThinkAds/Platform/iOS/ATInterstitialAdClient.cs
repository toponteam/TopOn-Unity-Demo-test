using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;

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
	}
}
