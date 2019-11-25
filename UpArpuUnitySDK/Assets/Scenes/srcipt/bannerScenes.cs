using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;

using AnyThinkAds.ThirdParty.MiniJSON;


public class bannerScenes : MonoBehaviour {




#if UNITY_ANDROID
    static string mPlacementId_native_all = "b5c4ad2fa3ab6d";

#elif UNITY_IOS || UNITY_IPHONE
	static string mPlacementId_native_all = "b5baf502bb23e3";
    //static string mPlacementId_native_all = "b5bacaccb61c29";
#endif

    private int screenWidth;
    // Use this for initialization
    void Start () {
        this.screenWidth = Screen.width;
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void gotoMainScenes() {
		Debug.Log ("Developer gotoMainScenes");
		AnyThinkAds.Demo.ATManager.printLogI ("Developer gotoMainScenes....");
		SceneManager.LoadScene ("demoMainScenes");
	}

    static BannerCallback bannerCallback ;

    public void loadBannerAd() {
        if(bannerCallback == null){
            bannerCallback = new BannerCallback();
            ATBannerAd.Instance.setListener(bannerCallback);
        }

        Dictionary<string, object> jsonmap = new Dictionary<string,object>();
        jsonmap.Add("age", "22");
        jsonmap.Add("sex", "lady");
        jsonmap.Add("banner", "2");
        ATSize bannerSize = new ATSize(this.screenWidth, 100, true);
        jsonmap.Add(ATBannerAdLoadingExtra.kATBannerAdLoadingExtraBannerAdSizeStruct, bannerSize);
        ATBannerAd.Instance.loadBannerAd(mPlacementId_native_all, jsonmap);
    }

    public void showBannerAd() {
        ATRect arpuRect = new ATRect(0,70, screenWidth, 100, true);
        ATBannerAd.Instance.showBannerAd(mPlacementId_native_all, arpuRect);
    }

    public void removeBannerAd() {
        ATBannerAd.Instance.cleanBannerAd(mPlacementId_native_all);
    }

    public void hideBannerAd() {
        ATBannerAd.Instance.hideBannerAd(mPlacementId_native_all);
    }

    /*
     * Use this method when you want to reshow a banner that is previously hidden(by calling hideBannerAd)   
    */
    public void reshowBannerAd() {
        ATBannerAd.Instance.showBannerAd(mPlacementId_native_all);
    }

    class BannerCallback : ATBannerAdListener
    {
        public void onAdAutoRefresh(string unitId)
        {
            Debug.Log("Developer callback onAdAutoRefresh :" +  unitId);
        }

        public void onAdAutoRefreshFail(string unitId, string code, string message)
        {
            Debug.Log("Developer callback onAdAutoRefreshFail : "+ unitId + "--code:" + code + "--msg:" + message);
        }

        public void onAdClick(string unitId)
        {
            Debug.Log("Developer callback onAdClick :" + unitId);
        }

        public void onAdClose(string unitId)
        {
            Debug.Log("Developer callback onAdClose :" + unitId);
        }

        public void onAdImpress(string unitId)
        {
            Debug.Log("Developer callback onAdImpress :" + unitId);
        }

        public void onAdLoad(string unitId)
        {
            Debug.Log("Developer callback onAdLoad :" + unitId);
        }

        public void onAdLoadFail(string unitId, string code, string message)
        {
            Debug.Log("Developer callback onAdLoadFail : : " + unitId + "--code:" + code + "--msg:" + message);
        }
    }


}
