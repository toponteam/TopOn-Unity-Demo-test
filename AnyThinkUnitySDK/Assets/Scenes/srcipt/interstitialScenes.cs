using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;

using AnyThinkAds.ThirdParty.MiniJSON;


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

        Dictionary<string,string> jsonmap = new Dictionary<string,string>();
        jsonmap.Add(AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL, AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL_NO);
        //jsonmap.Add(AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL, AnyThinkAds.Api.ATConst.USE_REWARDED_VIDEO_AS_INTERSTITIAL_YES);

        ATInterstitialAd.Instance.loadInterstitialAd(mPlacementId_interstitial_all, jsonmap);
    }

    public void showInterstitialAd() {
        Dictionary<string, string> jsonmap = new Dictionary<string, string>();
        jsonmap.Add(AnyThinkAds.Api.ATConst.SCENARIO, showingScenario);

        ATInterstitialAd.Instance.showInterstitialAd(mPlacementId_interstitial_all, jsonmap);
    }

    class InterstitalCallback : ATInterstitialAdListener
    {
        public void onInterstitialAdClick(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdClick :" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));
        }

        public void onInterstitialAdClose(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdClose :" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));
        }

        public void onInterstitialAdEndPlayingVideo(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdEndPlayingVideo :" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));
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
            Debug.Log("Developer callback onInterstitialAdShow :" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));
        }

        public void onInterstitialAdStartPlayingVideo(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer callback onInterstitialAdStartPlayingVideo :" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));

        }

        public void onInterstitialAdFailedToShow(string placementId)
        {
            Debug.Log("Developer callback onInterstitialAdFailedToShow :" + placementId);

        }
    }

}
