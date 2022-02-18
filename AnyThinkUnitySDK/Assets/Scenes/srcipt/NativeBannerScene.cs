using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;

using AnyThinkAds.ThirdParty.LitJson;

public class NativeBannerScene : MonoBehaviour, ATNativeBannerAdListener {

	#if UNITY_ANDROID
	    static string mPlacementId_native_all = "b5aa1fa2cae775";

	#elif UNITY_IOS || UNITY_IPHONE
		static string mPlacementId_native_all = "b5b0f555698607";

	#endif

	// Use this for initialization
	void Start () {
		ATNativeBannerAd.Instance.setListener(this);
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void gotoMainScene(){
		Debug.Log ("NativeBannerScene::gotoMainScene");
		SceneManager.LoadScene("demoMainScenes");
	}

	public void loadAd() {
		Debug.Log("NativeBannerScene::loadAd");
		ATNativeBannerAd.Instance.loadAd(mPlacementId_native_all, null);
	}

	public void showAd() {
		Debug.Log("NativeBannerScene::showAd");
		Debug.Log("Screen Width : " + Screen.width + ", Screen dpi: " + Screen.dpi);
		ATRect arpuRect = new ATRect(0,100, 414,200);
        ATNativeBannerAd.Instance.showAd(mPlacementId_native_all, arpuRect, new Dictionary<string, string>{{ATNativeBannerAdShowingExtra.kATNativeBannerAdShowingExtraBackgroundColor, "#FFFFFF"}, {ATNativeBannerAdShowingExtra.kATNativeBannerAdShowingExtraTitleColor, "#FF0000"}});
	}

	public void removeAd() {
		Debug.Log("NativeBannerScene::removeAd");
		ATNativeBannerAd.Instance.removeAd(mPlacementId_native_all);
	}

	public void adReady() {
		Debug.Log("Developer NativeBannerScene::adReady:" + (ATNativeBannerAd.Instance.adReady(mPlacementId_native_all) ? "yes" : "no"));
	}

	public void onAdLoaded(string placementId) {
        Debug.Log("Developer onAdLoaded------:" + placementId);
           
    } 

	public void onAdLoadFail(string placementId, string code, string message) {
        Debug.Log("Developer onAdLoadFail------:" + placementId + ", " + code + ", " + message);
           
    } 
	
    public void onAdImpressed(string placementId, ATCallbackInfo callbackInfo) {
        Debug.Log("Developer onAdImpressed------:" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
           
    } 
	
    public void onAdClicked(string placementId, ATCallbackInfo callbackInfo) {
        Debug.Log("Developer onAdClicked------:" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
           
    } 
	 
    public void onAdAutoRefresh(string placementId, ATCallbackInfo callbackInfo) {
        Debug.Log("Developer onAdAutoRefresh------:" + placementId + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
           
    } 
	
	public void onAdAutoRefreshFailure(string placementId, string code, string message) {
        Debug.Log("Developer onAdAutoRefreshFailure------:" + placementId);
           
    } 
	
    public void onAdCloseButtonClicked(string placementId) {
        Debug.Log("Developer onAdCloseButtonClicked------:" + placementId);
           
    } 
}
