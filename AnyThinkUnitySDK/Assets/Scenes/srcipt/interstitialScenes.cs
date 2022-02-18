using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;

using AnyThinkAds.ThirdParty.LitJson;


public class interstitialScenes : MonoBehaviour {




#if UNITY_ANDROID
    static string mPlacementId_interstitial_all = "b5baca53984692";
    static string showingScenario = "f5e71c49060ab3";

#elif UNITY_IOS || UNITY_IPHONE
    static string mPlacementId_interstitial_all = "b5bacad26a752a";
    static string showingScenario = "f5e549727efc49";

#endif


    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void gotoMainScenes(){
		Debug.Log ("Developer gotoMainScenes");
		AnyThinkAds.Demo.ATManager.printLogI ("Developer gotoMainScenes....");
		SceneManager.LoadScene ("demoMainScenes");
	}

    static InterstitalCallback callback;
    public void loadInterstitialAd() {
        if(callback == null) {
            callback = new InterstitalCallback();
            ATInterstitialAd.Instance.setListener(callback);
        }

        Dictionary<string,object> jsonmap = new Dictionary<string, object>();
        jsonmap.Add(AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL, AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL_NO);
        //jsonmap.Add(AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL, AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL_YES);

        ATInterstitialAd.Instance.loadInterstitialAd(mPlacementId_interstitial_all, jsonmap);
    }

    public void isReady()
    {
        bool b = ATInterstitialAd.Instance.hasInterstitialAdReady(mPlacementId_interstitial_all);
        Debug.Log("Developer isReady interstitial...." + b);

        string adStatus = ATInterstitialAd.Instance.checkAdStatus(mPlacementId_interstitial_all);
        Debug.Log("Developer checkAdStatus interstitial...." + adStatus);

        string adCaches = ATInterstitialAd.Instance.getValidAdCaches(mPlacementId_interstitial_all);
        Debug.Log("Developer getValidAdCaches interstitial...." + adCaches);

        ATInterstitialAd.Instance.entryScenarioWithPlacementID(mPlacementId_interstitial_all, showingScenario);

    }

    public void showInterstitialAd() {
        Dictionary<string, string> jsonmap = new Dictionary<string, string>();
        jsonmap.Add(AnyThinkAds.Api.ATConst.SCENARIO, showingScenario);

        ATInterstitialAd.Instance.showInterstitialAd(mPlacementId_interstitial_all, jsonmap);
    }


    // auto load
    public void addAutoLoadAdPlacementID()
    {
        if(callback == null) {
            callback = new InterstitalCallback();
            ATInterstitialAutoAd.Instance.setListener(callback);
        }

        string[] jsonList = {mPlacementId_interstitial_all};

        ATInterstitialAutoAd.Instance.addAutoLoadAdPlacementID(jsonList);
    }

    public void autoReadyForPlacementID()
    {
        ATInterstitialAutoAd.Instance.setAutoLocalExtra(mPlacementId_interstitial_all,new Dictionary<string, string> { { "placement_custom_key", "placement_custom" } });

        ATInterstitialAutoAd.Instance.entryAutoAdScenarioWithPlacementID(mPlacementId_interstitial_all, showingScenario);
        bool b = ATInterstitialAutoAd.Instance.autoLoadInterstitialAdReadyForPlacementID(mPlacementId_interstitial_all);
        Debug.Log("Developer isReady auto interstitial...." + b);

        string adCaches = ATInterstitialAutoAd.Instance.checkAutoAdStatus(mPlacementId_interstitial_all);
        Debug.Log("Developer checkAutoAdStatus interstitial...." + adCaches);
    }
     public void showAutoAd()
    {
        Dictionary<string, string> jsonmap = new Dictionary<string, string>();
        jsonmap.Add(AnyThinkAds.Api.ATConst.SCENARIO, showingScenario);

        ATInterstitialAutoAd.Instance.showAutoAd(mPlacementId_interstitial_all,jsonmap);
    }
    public void removeAutoLoadAdPlacementID()
    {
        string[] jsonList = { mPlacementId_interstitial_all};
        ATInterstitialAutoAd.Instance.removeAutoLoadAdPlacementID(jsonList);
    }







    class InterstitalCallback : ATInterstitialAdListener
    {
        public void onInterstitialAdClick(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdClick :" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onInterstitialAdClose(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdClose :" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onInterstitialAdEndPlayingVideo(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdEndPlayingVideo :" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onInterstitialAdFailedToPlayVideo(string placementId, string code, string message)
        {
            Debug.Log("Developer callback onInterstitialAdFailedToPlayVideo :" + placementId + "--code:" + code + "--msg:" + message);
        }

        public void onInterstitialAdLoad(string placementId)
        {
            Debug.Log("Developer callback onInterstitialAdLoad :" + placementId);
        }

        public void onInterstitialAdLoadFail(string placementId, string code, string message)
        {
            Debug.Log("Developer callback onInterstitialAdLoadFail :" + placementId + "--code:" + code + "--msg:" + message);
        }

        public void onInterstitialAdShow(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdShow :" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onInterstitialAdStartPlayingVideo(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdStartPlayingVideo :" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));

        }

        public void onInterstitialAdFailedToShow(string placementId)
        {
            Debug.Log("Developer callback onInterstitialAdFailedToShow :" + placementId);

        }

        public void startLoadingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer startLoadingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void finishLoadingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer finishLoadingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void failToLoadADSource(string placementId,ATCallbackInfo callbackInfo,string code, string message){
            Debug.Log("Developer failToLoadADSource------code:" + code + "---message:" + message+ "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void startBiddingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer startBiddingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void finishBiddingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer finishBiddingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void failBiddingADSource(string placementId,ATCallbackInfo callbackInfo,string code, string message){
            Debug.Log("Developer failBiddingADSource------code:" + code + "---message:" + message+ "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

    }

}
