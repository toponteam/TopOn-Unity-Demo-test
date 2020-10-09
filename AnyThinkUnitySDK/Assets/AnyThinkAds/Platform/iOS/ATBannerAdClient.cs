using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;

namespace AnyThinkAds.iOS {
	public class ATBannerAdClient : IATBannerAdClient {
		private  ATBannerAdListener anyThinkListener;

		public void addsetting(string placementId,string json){
			//todo...
		}

		public void setListener(ATBannerAdListener listener) {
			Debug.Log("Unity: ATBannerAdClient::setListener()");
	        anyThinkListener = listener;
	    }

	    public void loadBannerAd(string placementId, string mapJson) {
			Debug.Log("Unity: ATBannerAdClient::loadBannerAd()");
			ATBannerAdWrapper.setClientForPlacementID(placementId, this);
			ATBannerAdWrapper.loadBannerAd(placementId, mapJson);
	    }

	    public void showBannerAd(string placementId, ATRect rect) {
			Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
			ATBannerAdWrapper.showBannerAd(placementId, rect);
	    }

        public void showBannerAd(string placementId, string position)
        {
            Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
            ATBannerAdWrapper.showBannerAd(placementId, position);
        }

        public void cleanBannerAd(string placementId) {
			Debug.Log("Unity: ATBannerAdClient::cleanBannerAd()");	
			ATBannerAdWrapper.cleanBannerAd(placementId);	
	    }

	    public void hideBannerAd(string placementId) {
	    	Debug.Log("Unity: ATBannerAdClient::hideBannerAd()");	
			ATBannerAdWrapper.hideBannerAd(placementId);
	    }

	    public void showBannerAd(string placementId) {
	    	Debug.Log("Unity: ATBannerAdClient::showBannerAd()");	
			ATBannerAdWrapper.showBannerAd(placementId);
	    }

        public void cleanCache(string placementId) {
			Debug.Log("Unity: ATBannerAdClient::cleanCache()");
			ATBannerAdWrapper.clearCache();
        }

        public void OnBannerAdLoad(string placementId) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdLoad()");
	        if (anyThinkListener != null) anyThinkListener.onAdLoad(placementId);
	    }
	    
	    public void OnBannerAdLoadFail(string placementId, string code, string message) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdLoadFail()");
	        if (anyThinkListener != null) anyThinkListener.onAdLoadFail(placementId, code, message);
	    }
	    
	    public void OnBannerAdImpress(string placementId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdImpress()");
            if (anyThinkListener != null) anyThinkListener.onAdImpress(placementId, new ATCallbackInfo(callbackJson));
	    }
	    
        public void OnBannerAdClick(string placementId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClick()");
            if (anyThinkListener != null) anyThinkListener.onAdClick(placementId, new ATCallbackInfo(callbackJson));
	    }
	    
        public void OnBannerAdAutoRefresh(string placementId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefresh()");
            if (anyThinkListener != null) anyThinkListener.onAdAutoRefresh(placementId, new ATCallbackInfo(callbackJson));
	    }
	    
	    public void OnBannerAdAutoRefreshFail(string placementId, string code, string message) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefreshFail()");
	        if (anyThinkListener != null) anyThinkListener.onAdAutoRefreshFail(placementId, code, message);
	    }

	    public void OnBannerAdClose(string placementId) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClose()");
	        if (anyThinkListener != null) anyThinkListener.onAdClose(placementId);
	    }

	    public void OnBannerAdCloseButtonTapped(string placementId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdCloseButton()");
	        if (anyThinkListener != null) anyThinkListener.onAdCloseButtonTapped(placementId, new ATCallbackInfo(callbackJson));
	    }
	}
}