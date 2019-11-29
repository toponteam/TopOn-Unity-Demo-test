﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;

using AnyThinkAds.ThirdParty.MiniJSON;

public class NativeBannerScene : MonoBehaviour, ATNativeBannerAdListener {

	#if UNITY_ANDROID
	    static string mPlacementId_native_all = "b5aa1fa2cae775";

	#elif UNITY_IOS || UNITY_IPHONE
		static string mPlacementId_native_all = "b5b0f551340ea9";

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

	public void onAdLoaded(string unitId) {
        Debug.Log("Developer onAdLoaded------:" + unitId);
           
    } 

	public void onAdLoadFail(string unitId, string code, string message) {
        Debug.Log("Developer onAdLoadFail------:" + unitId + ", " + code + ", " + message);
           
    } 
	
    public void onAdImpressed(string unitId) {
        Debug.Log("Developer onAdImpressed------:" + unitId);
           
    } 
	
    public void onAdClicked(string unitId) {
        Debug.Log("Developer onAdClicked------:" + unitId);
           
    } 
	 
    public void onAdAutoRefresh(string unitId) {
        Debug.Log("Developer onAdAutoRefresh------:" + unitId);
           
    } 
	
	public void onAdAutoRefreshFailure(string unitId, string code, string message) {
        Debug.Log("Developer onAdAutoRefreshFailure------:" + unitId);
           
    } 
	
    public void onAdCloseButtonClicked(string unitId) {
        Debug.Log("Developer onAdCloseButtonClicked------:" + unitId);
           
    } 
}