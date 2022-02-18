using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;
using AnyThinkAds.ThirdParty.LitJson;

public class vidoeScenes : MonoBehaviour {

#if UNITY_ANDROID
    static string mPlacementId_rewardvideo_all = "b5b449fb3d89d7";
    static string showingScenario = "f5e71c46d1a28f";
#elif UNITY_IOS || UNITY_IPHONE
    static string mPlacementId_rewardvideo_all = "b5b44a0f115321";//"b5b44a0f115321";
    static string showingScenario = "f5e54970dc84e6";

#endif

    ATRewardedVideo rewardedVideo;
	
 
	// Use this for initialization
	void Start () {
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void gotoMainScenes(){
		Debug.Log ("Developer gotoMainScenes");
		AnyThinkAds.Demo.ATManager.printLogI ("gotoMainScenes....");
		SceneManager.LoadScene ("demoMainScenes");
	}
	public void gotoNativeScenes(){
		Debug.Log (" Developer gotoNativeScenes");
		AnyThinkAds.Demo.ATManager.printLogI ("gotoNativeScenes....");
		SceneManager.LoadScene ("nativeMainScenes");
	}





    static ATCallbackListener callbackListener;
	public void loadVideo(){
        if(callbackListener == null){
            callbackListener = new ATCallbackListener();
            Debug.Log("Developer init video....placementId:" + mPlacementId_rewardvideo_all);
            ATRewardedVideo.Instance.setListener(callbackListener);
        }

        ATSDKAPI.setCustomDataForPlacementID(new Dictionary<string, string> { { "placement_custom_key", "placement_custom" } }, mPlacementId_rewardvideo_all);

		Dictionary<string,string> jsonmap = new Dictionary<string,string>();
		jsonmap.Add(ATConst.USERID_KEY, "test_user_id");
		jsonmap.Add(ATConst.USER_EXTRA_DATA, "test_user_extra_data");


        ATRewardedVideo.Instance.loadVideoAd(mPlacementId_rewardvideo_all,jsonmap);

        // ATRewardedVideo.Instance.addAutoLoadAdPlacementID(mPlacementId_rewardvideo_all);
		
	}
	public void showVideo(){
		
		Debug.Log ("Developer show video....");

        Dictionary<string, string> jsonmap = new Dictionary<string, string>();
        jsonmap.Add(AnyThinkAds.Api.ATConst.SCENARIO, showingScenario);
        ATRewardedVideo.Instance.showAd(mPlacementId_rewardvideo_all, jsonmap);

        // ATRewardedVideo.Instance.showAutoAd(mPlacementId_rewardvideo_all);
		
	}

	public void isReady(){


        // bool b = ATRewardedVideo.Instance.autoLoadRewardedVideoReadyForPlacementID(mPlacementId_rewardvideo_all);
		// Debug.Log("Developer Auto isReady video...." + b);

        // string adCaches = ATRewardedVideo.Instance.getAutoValidAdCaches(mPlacementId_rewardvideo_all);
        // Debug.Log("Developer getAutoValidAdCaches video...." + adCaches);

        ATRewardedVideo.Instance.entryScenarioWithPlacementID(mPlacementId_rewardvideo_all,"123");
        
		Debug.Log ("Developer isReady ?....");
        bool b = ATRewardedVideo.Instance.hasAdReady(mPlacementId_rewardvideo_all);
		Debug.Log("Developer isReady video...." + b);

        string adStatus = ATRewardedVideo.Instance.checkAdStatus(mPlacementId_rewardvideo_all);
        Debug.Log("Developer checkAdStatus video...." + adStatus);

        string adCaches = ATRewardedVideo.Instance.getValidAdCaches(mPlacementId_rewardvideo_all);
        Debug.Log("Developer getValidAdCaches video...." + adCaches);
    }

    // auto load
    public void addAutoLoadAdPlacementID()
    {
          if(callbackListener == null){
            callbackListener = new ATCallbackListener();
            Debug.Log("Developer init video....placementId:" + mPlacementId_rewardvideo_all);
            ATRewardedAutoVideo.Instance.setListener(callbackListener);
        }

        string[] jsonList = {mPlacementId_rewardvideo_all};
        ATRewardedAutoVideo.Instance.addAutoLoadAdPlacementID(jsonList);
    }

    public void removeAutoLoadAdPlacementID()
    {
        string[] jsonList = {mPlacementId_rewardvideo_all};

        ATRewardedAutoVideo.Instance.removeAutoLoadAdPlacementID(jsonList);
    }

    public void autoReadyForPlacementID()
    {

        ATRewardedAutoVideo.Instance.setAutoLocalExtra(mPlacementId_rewardvideo_all,new Dictionary<string, string> { { "placement_custom_key", "placement_custom" } });

        ATRewardedAutoVideo.Instance.entryAutoAdScenarioWithPlacementID(mPlacementId_rewardvideo_all, showingScenario);

        bool b = ATRewardedAutoVideo.Instance.autoLoadRewardedVideoReadyForPlacementID(mPlacementId_rewardvideo_all);
        Debug.Log("Developer isReady auto ...." + b);

        string adCaches = ATRewardedAutoVideo.Instance.checkAutoAdStatus(mPlacementId_rewardvideo_all);
        Debug.Log("Developer checkAutoAdStatus ...." + adCaches);
    }
     public void showAutoAd()
    {
        Dictionary<string, string> jsonmap = new Dictionary<string, string>();
        jsonmap.Add(AnyThinkAds.Api.ATConst.SCENARIO, showingScenario);

        // ATRewardedAutoVideo.Instance.showAutoAd(mPlacementId_rewardvideo_all);

        ATRewardedAutoVideo.Instance.showAutoAd(mPlacementId_rewardvideo_all,jsonmap);
    }


    class ATCallbackListener : ATRewardedVideoExListener {
        
        public void onRewardedVideoAdLoaded(string placementId)
        {
            Debug.Log("Developer onRewardedVideoAdLoaded------");
        }

        public void onRewardedVideoAdLoadFail(string placementId, string code, string message){
            Debug.Log("Developer onRewardedVideoAdLoadFail------:code" + code + "--message:" + message);
        }

        public void onRewardedVideoAdPlayStart(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onRewardedVideoAdPlayStart------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onRewardedVideoAdPlayEnd(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onRewardedVideoAdPlayEnd------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onRewardedVideoAdPlayFail(string placementId, string code, string message){
            Debug.Log("Developer onRewardedVideoAdPlayFail------code:" + code + "---message:" + message);
        }

        public void onRewardedVideoAdPlayClosed(string placementId, bool isReward, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onRewardedVideoAdPlayClosed------isReward:" + isReward + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onRewardedVideoAdPlayClicked(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onRewardVideoAdPlayClicked------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void onReward(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onReward------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

        public void startLoadingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer startLoadingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void finishLoadingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer finishLoadingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void failToLoadADSource(string placementId,ATCallbackInfo callbackInfo,string code, string message){
            Debug.Log("Developer failToLoadADSource------code:" + code + "---message:" + message + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void startBiddingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer startBiddingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void finishBiddingADSource(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer finishBiddingADSource------" + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void failBiddingADSource(string placementId,ATCallbackInfo callbackInfo,string code, string message){
            Debug.Log("Developer failBiddingADSource------code:" + code + "---message:" + message + "->" + JsonMapper.ToJson(callbackInfo.toAdsourceDictionary()));

        }

		public void onRewardedVideoAdAgainPlayStart(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onRewardedVideoAdAgainPlayStart------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }

		public void onRewardedVideoAdAgainPlayEnd(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onRewardedVideoAdAgainPlayEnd------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));
        }
		
		public void onRewardedVideoAdAgainPlayFail(string placementId, string code, string message){
            Debug.Log("Developer onRewardedVideoAdAgainPlayFail------code:" + code + "---message:" + message);
        }

		public void onRewardedVideoAdAgainPlayClicked(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onRewardedVideoAdAgainPlayClicked------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));

        }

		public void onAgainReward(string placementId, ATCallbackInfo callbackInfo){
            Debug.Log("Developer onAgainReward------" + "->" + JsonMapper.ToJson(callbackInfo.toDictionary()));

        }








    }
}
