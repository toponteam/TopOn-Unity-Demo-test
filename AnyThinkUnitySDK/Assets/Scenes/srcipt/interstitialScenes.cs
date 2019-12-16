using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;

using AnyThinkAds.ThirdParty.MiniJSON;


public class interstitialScenes : MonoBehaviour {




#if UNITY_ANDROID
    static string mPlacementId_interstitial_all = "b5baca599c7c61";

#elif UNITY_IOS || UNITY_IPHONE
    static string mPlacementId_interstitial_all = "b5d13340a1dd21";

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
        jsonmap.Add("age", "22");
        jsonmap.Add("sex", "lady");
        jsonmap.Add("interstitial", "3");

        ATInterstitialAd.Instance.loadInterstitialAd(mPlacementId_interstitial_all, jsonmap);
    }

    public void showInterstitialAd() {
        ATInterstitialAd.Instance.showInterstitialAd(mPlacementId_interstitial_all);
    }

    class InterstitalCallback : ATInterstitialAdListener
    {
        public void onInterstitialAdClick(string unitId)
        {
            Debug.Log("Developer callback onInterstitialAdClick :" + unitId);
        }

        public void onInterstitialAdClose(string unitId)
        {
            Debug.Log("Developer callback onInterstitialAdClose :" + unitId);
        }

        public void onInterstitialAdEndPlayingVideo(string unitId)
        {
            Debug.Log("Developer callback onInterstitialAdEndPlayingVideo :" + unitId);
        }

        public void onInterstitialAdFailedToPlayVideo(string unitId, string code, string message)
        {
            Debug.Log("Developer callback onInterstitialAdFailedToPlayVideo :" + unitId + "--code:" + code + "--msg:" + message);
        }

        public void onInterstitialAdLoad(string unitId)
        {
            Debug.Log("Developer callback onInterstitialAdLoad :" + unitId);
        }

        public void onInterstitialAdLoadFail(string unitId, string code, string message)
        {
            Debug.Log("Developer callback onInterstitialAdLoadFail :" + unitId + "--code:" + code + "--msg:" + message);
        }

        public void onInterstitialAdShow(string unitId)
        {
            Debug.Log("Developer callback onInterstitialAdShow :" + unitId);
        }

        public void onInterstitialAdStartPlayingVideo(string unitId)
        {
            Debug.Log("Developer callback onInterstitialAdStartPlayingVideo :" + unitId);
            
        }

        public void onInterstitialAdFailedToShow(string unitId)
        {
            Debug.Log("Developer callback onInterstitialAdFailedToShow :" + unitId);
            
        }
    }

}
