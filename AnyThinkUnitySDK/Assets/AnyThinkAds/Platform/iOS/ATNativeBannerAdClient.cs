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
    	public void loadAd(string unitId, string mapJson) {
    		Debug.Log("ATNativeBannerAdClient::loadAd()");
    		ATNativeBannerAdWrapper.setClientForPlacementID(unitId, this);
    		Debug.Log("ATNativeBannerAdClient::loadAd(), after set client");
    		ATNativeBannerAdWrapper.loadAd(unitId, mapJson);
    		Debug.Log("ATNativeBannerAdClient::loadAd(), after invoke load ad");
    	}
    	
		public bool adReady(string unitId) {
			Debug.Log("ATNativeBannerAdClient::adReady()");
			return ATNativeBannerAdWrapper.adReady(unitId);
		}

        public void setListener(ATNativeBannerAdListener listener) {
			Debug.Log("ATNativeBannerAdClient::setListener()");
			this.listener = listener;
        }

        public void showAd(string unitId, ATRect rect, Dictionary<string, string> pairs) {
			Debug.Log("ATNativeBannerAdClient::showAd()");
			ATNativeBannerAdWrapper.showAd(unitId, rect, pairs);
        }

        public void removeAd(string unitId) {
			Debug.Log("ATNativeBannerAdClient::removeAd()");
			ATNativeBannerAdWrapper.removeAd(unitId);
        }

        //Listener method(s)
        public void onAdLoaded(string unitId) {
        	Debug.Log("ATNativeBannerAdClient::onAdLoaded()");
        	if(listener != null) listener.onAdLoaded(unitId);
        }
        
        public void onAdLoadFail(string unitId, string code, string message) {
        	Debug.Log("ATNativeBannerAdClient::onAdLoadFail()");
        	if(listener != null) listener.onAdLoadFail(unitId, code, message);
        }
        
        public void onAdImpressed(string unitId, string callbackJson) {
        	Debug.Log("ATNativeBannerAdClient::onAdImpressed()");
            if(listener != null) listener.onAdImpressed(unitId, new ATCallbackInfo(callbackJson));
        }
        
        public void onAdClicked(string unitId, string callbackJson) {
        	Debug.Log("ATNativeBannerAdClient::onAdClicked()");
            if(listener != null) listener.onAdClicked(unitId, new ATCallbackInfo(callbackJson));
        }
        
        public void onAdAutoRefresh(string unitId, string callbackJson) {
        	Debug.Log("ATNativeBannerAdClient::onAdAutoRefresh()");
            if(listener != null) listener.onAdAutoRefresh(unitId, new ATCallbackInfo(callbackJson));
        }
        
		public void onAdAutoRefreshFailure(string unitId, string code, string message) {
        	Debug.Log("ATNativeBannerAdClient::onAdAutoRefreshFailure()");
        	if(listener != null) listener.onAdAutoRefreshFailure(unitId, code, message);
        }

        public void onAdCloseButtonClicked(string unitId) {
        	Debug.Log("ATNativeBannerAdClient::onAdCloseButtonClicked()");
        	if(listener != null) listener.onAdCloseButtonClicked(unitId);
        }
    }
}
