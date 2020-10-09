using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Common;
using AnyThinkAds.Api;

namespace AnyThinkAds.iOS
{
    public class ATNativeBannerAdClient : IATNativeBannerAdClient
    {
    	private ATNativeBannerAdListener listener;
    	public void loadAd(string placementId, string mapJson) {
    		Debug.Log("ATNativeBannerAdClient::loadAd()");
    		ATNativeBannerAdWrapper.setClientForPlacementID(placementId, this);
    		Debug.Log("ATNativeBannerAdClient::loadAd(), after set client");
    		ATNativeBannerAdWrapper.loadAd(placementId, mapJson);
    		Debug.Log("ATNativeBannerAdClient::loadAd(), after invoke load ad");
    	}
    	
		public bool adReady(string placementId) {
			Debug.Log("ATNativeBannerAdClient::adReady()");
			return ATNativeBannerAdWrapper.adReady(placementId);
		}

        public void setListener(ATNativeBannerAdListener listener) {
			Debug.Log("ATNativeBannerAdClient::setListener()");
			this.listener = listener;
        }

        public void showAd(string placementId, ATRect rect, Dictionary<string, string> pairs) {
			Debug.Log("ATNativeBannerAdClient::showAd()");
			ATNativeBannerAdWrapper.showAd(placementId, rect, pairs);
        }

        public void removeAd(string placementId) {
			Debug.Log("ATNativeBannerAdClient::removeAd()");
			ATNativeBannerAdWrapper.removeAd(placementId);
        }

        //Listener method(s)
        public void onAdLoaded(string placementId) {
        	Debug.Log("ATNativeBannerAdClient::onAdLoaded()");
        	if(listener != null) listener.onAdLoaded(placementId);
        }
        
        public void onAdLoadFail(string placementId, string code, string message) {
        	Debug.Log("ATNativeBannerAdClient::onAdLoadFail()");
        	if(listener != null) listener.onAdLoadFail(placementId, code, message);
        }
        
        public void onAdImpressed(string placementId, string callbackJson) {
        	Debug.Log("ATNativeBannerAdClient::onAdImpressed()");
            if(listener != null) listener.onAdImpressed(placementId, new ATCallbackInfo(callbackJson));
        }
        
        public void onAdClicked(string placementId, string callbackJson) {
        	Debug.Log("ATNativeBannerAdClient::onAdClicked()");
            if(listener != null) listener.onAdClicked(placementId, new ATCallbackInfo(callbackJson));
        }
        
        public void onAdAutoRefresh(string placementId, string callbackJson) {
        	Debug.Log("ATNativeBannerAdClient::onAdAutoRefresh()");
            if(listener != null) listener.onAdAutoRefresh(placementId, new ATCallbackInfo(callbackJson));
        }
        
		public void onAdAutoRefreshFailure(string placementId, string code, string message) {
        	Debug.Log("ATNativeBannerAdClient::onAdAutoRefreshFailure()");
        	if(listener != null) listener.onAdAutoRefreshFailure(placementId, code, message);
        }

        public void onAdCloseButtonClicked(string placementId) {
        	Debug.Log("ATNativeBannerAdClient::onAdCloseButtonClicked()");
        	if(listener != null) listener.onAdCloseButtonClicked(placementId);
        }
    }
}
