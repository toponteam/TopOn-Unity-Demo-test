using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;

namespace AnyThinkAds.iOS {
	public class ATBannerAdClient : IATBannerAdClient {
		private  ATBannerAdListener anyThinkListener;

		public void addsetting(string unitId,string json){
			//todo...
		}

		public void setListener(ATBannerAdListener listener) {
			Debug.Log("Unity: ATBannerAdClient::setListener()");
	        anyThinkListener = listener;
	    }

	    public void loadBannerAd(string unitId, string mapJson) {
			Debug.Log("Unity: ATBannerAdClient::loadBannerAd()");
			ATBannerAdWrapper.setClientForPlacementID(unitId, this);
			ATBannerAdWrapper.loadBannerAd(unitId, mapJson);
	    }

	    public void showBannerAd(string unitId, ATRect rect) {
			Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
			ATBannerAdWrapper.showBannerAd(unitId, rect);
	    }

        public void showBannerAd(string unitId, string position)
        {
            Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
            ATBannerAdWrapper.showBannerAd(unitId, position);
        }

        public void cleanBannerAd(string unitId) {
			Debug.Log("Unity: ATBannerAdClient::cleanBannerAd()");	
			ATBannerAdWrapper.cleanBannerAd(unitId);	
	    }

	    public void hideBannerAd(string unitId) {
	    	Debug.Log("Unity: ATBannerAdClient::hideBannerAd()");	
			ATBannerAdWrapper.hideBannerAd(unitId);
	    }

	    public void showBannerAd(string unitId) {
	    	Debug.Log("Unity: ATBannerAdClient::showBannerAd()");	
			ATBannerAdWrapper.showBannerAd(unitId);
	    }

        public void cleanCache(string unitId) {
			Debug.Log("Unity: ATBannerAdClient::cleanCache()");
			ATBannerAdWrapper.clearCache();
        }

        public void OnBannerAdLoad(string unitId) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdLoad()");
	        if (anyThinkListener != null) anyThinkListener.onAdLoad(unitId);
	    }
	    
	    public void OnBannerAdLoadFail(string unitId, string code, string message) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdLoadFail()");
	        if (anyThinkListener != null) anyThinkListener.onAdLoadFail(unitId, code, message);
	    }
	    
	    public void OnBannerAdImpress(string unitId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdImpress()");
            if (anyThinkListener != null) anyThinkListener.onAdImpress(unitId, new ATCallbackInfo(callbackJson));
	    }
	    
        public void OnBannerAdClick(string unitId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClick()");
            if (anyThinkListener != null) anyThinkListener.onAdClick(unitId, new ATCallbackInfo(callbackJson));
	    }
	    
        public void OnBannerAdAutoRefresh(string unitId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefresh()");
            if (anyThinkListener != null) anyThinkListener.onAdAutoRefresh(unitId, new ATCallbackInfo(callbackJson));
	    }
	    
	    public void OnBannerAdAutoRefreshFail(string unitId, string code, string message) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefreshFail()");
	        if (anyThinkListener != null) anyThinkListener.onAdAutoRefreshFail(unitId, code, message);
	    }

	    public void OnBannerAdClose(string unitId) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClose()");
	        if (anyThinkListener != null) anyThinkListener.onAdClose(unitId);
	    }

	    public void OnBannerAdCloseButtonTapped(string unitId, string callbackJson) {
			Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdCloseButton()");
	        if (anyThinkListener != null) anyThinkListener.onAdCloseButtonTapped(unitId, new ATCallbackInfo(callbackJson));
	    }
	}
}