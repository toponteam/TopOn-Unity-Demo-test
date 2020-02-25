using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;
using AnyThinkAds.iOS;
using AnyThinkAds.ThirdParty.MiniJSON;

namespace AnyThinkAds.iOS {
	public class ATNativeAdClient : IATNativeAdClient {
		private ATNativeAdListener mlistener;
		public void loadNativeAd(string unitId, string mapJson) {
            Debug.Log("Unity:ATNativeAdClient::loadNativeAd()");
            ATNativeAdWrapper.setClientForPlacementID(unitId, this);
            ATNativeAdWrapper.loadNativeAd(unitId, mapJson);
        }

		public void setLocalExtra (string unitId,string localExtra){
			
		}

        public bool hasAdReady(string unitId) {
            Debug.Log("Unity:ATNativeAdClient::hasAdReady()");
			return ATNativeAdWrapper.isNativeAdReady(unitId);
        }

        public void setListener(ATNativeAdListener listener) {
            Debug.Log("Unity:ATNativeAdClient::setListener()");
            mlistener = listener;
        }

		public void renderAdToScene(string unitId, ATNativeAdView anyThinkNativeAdView) {	
            Debug.Log("Unity:ATNativeAdClient::renderAdToScene()");
            ATNativeAdWrapper.showNativeAd(unitId, anyThinkNativeAdView.toJSON());
        }

        public void cleanAdView(string unitId, ATNativeAdView anyThinkNativeAdView) {
			Debug.Log("Unity:ATNativeAdClient::cleanAdView()");
            ATNativeAdWrapper.removeNativeAdView(unitId);
        }

        public void onApplicationForces(string unitId, ATNativeAdView anyThinkNativeAdView) {
			Debug.Log("Unity:ATNativeAdClient::onApplicationForces()");
        }

        public void onApplicationPasue(string unitId, ATNativeAdView anyThinkNativeAdView) {
			Debug.Log("Unity:ATNativeAdClient::onApplicationPasue()");
        }

        public void cleanCache(string unitId) {
			Debug.Log("Unity:ATNativeAdClient::cleanCache()");
            ATNativeAdWrapper.clearCache();
        }

        //Callbacks
        public void onAdImpressed(string unitId) {
            Debug.Log("Unity:ATNativeAdClient::onAdImpressed...unity3d.");
            if(mlistener != null) mlistener.onAdImpressed(unitId);
        }

        public void onAdClicked(string unitId) {
            Debug.Log("Unity:ATNativeAdClient::onAdClicked...unity3d.");
            if (mlistener != null) mlistener.onAdClicked(unitId);
        }

        public void onAdCloseButtonClicked(string unitId)
        {
            Debug.Log("Unity:ATNativeAdClient::onAdCloseButtonClicked...unity3d.");
            if (mlistener != null) mlistener.onAdCloseButtonClicked(unitId);
        }

        public void onAdVideoStart(string unitId) {
            Debug.Log("Unity:ATNativeAdClient::onAdVideoStart...unity3d.");
            if (mlistener != null) mlistener.onAdVideoStart(unitId);
        }

        public void onAdVideoEnd(string unitId) {
            Debug.Log("Unity:ATNativeAdClient::onAdVideoEnd...unity3d.");
            if (mlistener != null) mlistener.onAdVideoEnd(unitId);
        }

        public void onAdVideoProgress(string unitId,int progress) {
            Debug.Log("Unity:ATNativeAdClient::onAdVideoProgress...progress[" + progress + "]");
            if (mlistener != null) mlistener.onAdVideoProgress(unitId, progress);
        }

        public void onNativeAdLoaded(string unitId) {
            Debug.Log("Unity:ATNativeAdClient::onNativeAdLoaded...unity3d.");
            if (mlistener != null) mlistener.onAdLoaded(unitId);
        }

        public void onNativeAdLoadFail(string unitId,string code, string msg) {
            Debug.Log("Unity:ATNativeAdClient::onNativeAdLoadFail...unity3d. code:" + code + " msg:" + msg);
            if (mlistener != null) mlistener.onAdLoadFail(unitId, code, msg);
        }
	}
}
