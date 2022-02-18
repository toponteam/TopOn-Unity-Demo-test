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

	    public string checkAdStatus(string placementId) {
            Debug.Log("Unity: ATBannerAdClient::checkAdStatus()");
            return ATBannerAdWrapper.checkAdStatus(placementId);
        }

		public string getValidAdCaches(string placementId)
		{
			Debug.Log("Unity: ATBannerAdClient::getValidAdCaches()");
			return ATBannerAdWrapper.getValidAdCaches(placementId);
		}

		public void showBannerAd(string placementId, ATRect rect) {
			Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
			ATBannerAdWrapper.showBannerAd(placementId, rect);
	    }

	    public void showBannerAd(string placementId, ATRect rect, string mapJson) {
			Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
			ATBannerAdWrapper.showBannerAd(placementId, rect, mapJson);
	    }

        public void showBannerAd(string placementId, string position)
        {
            Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
            ATBannerAdWrapper.showBannerAd(placementId, position);
        }

        public void showBannerAd(string placementId, string position, string mapJson)
        {
            Debug.Log("Unity: ATBannerAdClient::showBannerAd()");
            ATBannerAdWrapper.showBannerAd(placementId, position, mapJson);
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
		//auto callbacks
	    public void startLoadingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATBannerAdWrapper::startLoadingADSource()");
            if (anyThinkListener != null) anyThinkListener.startLoadingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }
	    public void finishLoadingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATBannerAdWrapper::finishLoadingADSource()");
            if (anyThinkListener != null) anyThinkListener.finishLoadingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }	
	    public void failToLoadADSource(string placementId,string callbackJson, string code, string error) 
		{
	        Debug.Log("Unity: ATBannerAdWrapper::failToLoadADSource()");
	        if (anyThinkListener != null) anyThinkListener.failToLoadADSource(placementId, new ATCallbackInfo(callbackJson),code, error);
	    }
		public void startBiddingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATBannerAdWrapper::startBiddingADSource()");
            if (anyThinkListener != null) anyThinkListener.startBiddingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }
	    public void finishBiddingADSource(string placementId, string callbackJson) 
		{
	        Debug.Log("Unity: ATBannerAdWrapper::finishBiddingADSource()");
            if (anyThinkListener != null) anyThinkListener.finishBiddingADSource(placementId, new ATCallbackInfo(callbackJson));
	    }	
	    public void failBiddingADSource(string placementId, string callbackJson,string code, string error) 
		{
	        Debug.Log("Unity: ATBannerAdWrapper::failBiddingADSource()");
	        if (anyThinkListener != null) anyThinkListener.failBiddingADSource(placementId,new ATCallbackInfo(callbackJson), code, error);
	    }


	}
}