using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;
using AnyThinkAds.ThirdParty.MiniJSON;

public class vidoeScenes : MonoBehaviour {

	#if UNITY_ANDROID
    static string mPlacementId_rewardvideo_all = "b5b728e7a08cd4";

	#elif UNITY_IOS || UNITY_IPHONE
	static string mPlacementId_rewardvideo_all = "b5b72b21184aa8";//"b5b44a0f115321";

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



	//单独适配平台属性
	private Dictionary<string,object>  addsetting(){
		Dictionary<string,object> appsettinglist = new Dictionary<string,object> ();

		//AdmobATRewardedVideoSetting
		Dictionary<string,object> admobATRewardedVideoSetting = new Dictionary<string,object> ();
		appsettinglist.Add(AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_ADMOB+"", Json.Serialize(admobATRewardedVideoSetting));

		//mintegralATMediationSetting
		Dictionary<string,object> mintegralATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_MINTEGRAL+"", Json.Serialize(mintegralATMediationSetting));

		//_applovinATMediationSetting
		Dictionary<string,object> _applovinATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_APPLOVIN+"", Json.Serialize(_applovinATMediationSetting));



		//_flurryATMediationSetting
		Dictionary<string,object> flurryATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_FLURRY+"", Json.Serialize(flurryATMediationSetting));


		//_inmobiATMediationSetting
		Dictionary<string,object> _inmobiATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_INMOBI+"", Json.Serialize(_inmobiATMediationSetting));


		//_mopubATMediationSetting
		Dictionary<string,object> _mopubATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_MOPUB+"", Json.Serialize(_mopubATMediationSetting));

		//_chartboostATMediationSetting
		Dictionary<string,object> _chartboostATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_CHARTBOOST+"", Json.Serialize(_chartboostATMediationSetting));

		//_tapjoyATMediationSetting
		Dictionary<string,object> _tapjoyATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_TAPJOY+"", Json.Serialize(_tapjoyATMediationSetting));

		//_ironsourceATMediationSetting
		Dictionary<string,object> _ironsourceATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_IRONSOURCE+"", Json.Serialize(_ironsourceATMediationSetting));

		//_unityAdATMediationSetting
		Dictionary<string,object> _unityAdATMediationSetting = new Dictionary<string,object> ();
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_UNITYADS+"", Json.Serialize(_unityAdATMediationSetting));

		//vungleRewardVideoSetting
		Dictionary<string,object> vungleRewardVideoSetting = new Dictionary<string,object> ();
		vungleRewardVideoSetting.Add("orientation",1);//1:2  1: 表示根据设备方向自动旋转  2:视频广告以最佳方向播放
		vungleRewardVideoSetting.Add("isSoundEnable",true);//true:false
		vungleRewardVideoSetting.Add("isBackButtonImmediatelyEnable",false);//true:false 如果为 true，用户可以立即使用返回按钮退出广告。如果为 false，在屏幕上的关闭按钮显示之前用户不可以使用返回按钮退出广告
		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_VUNGLE+"", Json.Serialize(vungleRewardVideoSetting));


		//adColonyRewardVideoSetting
		Dictionary<string,object> adColonyRewardVideoSetting = new Dictionary<string,object> ();

        adColonyRewardVideoSetting.Add("enableConfirmationDialog",false);//true:false
        adColonyRewardVideoSetting.Add("enableResultsDialog",false);//true:false
        appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_ADCOLONY+"", Json.Serialize(adColonyRewardVideoSetting));


		//ttATRewardedVideoSetting
		Dictionary<string,object> ttATRewardedVideoSetting = new Dictionary<string,object> ();
		ttATRewardedVideoSetting.Add("requirePermission",true);//是否申请权限
		ttATRewardedVideoSetting.Add("orientation",1);//可选参数 设置期望视频播放的方向
		ttATRewardedVideoSetting.Add("supportDeepLink",true);//可选参数 设置是否支持deeplink
		ttATRewardedVideoSetting.Add("rewardName","金币");//可选参数 励视频奖励的名称，针对激励视频参数
		ttATRewardedVideoSetting.Add("rewardCount",1);//可选参数 激励视频奖励个数

		appsettinglist.Add (AnyThinkAds.Api.ATConst.NETWORK_TYPE.NETWORK_TOUTIAO+"", Json.Serialize(ttATRewardedVideoSetting));



		return appsettinglist;
	}
    static ATCallbackListener callbackListener;
	public void loadVideo(){
        if(callbackListener == null){
            callbackListener = new ATCallbackListener();
            Debug.Log("Developer init video....unitid:" + mPlacementId_rewardvideo_all);
            ATRewardedVideo.Instance.setListener(callbackListener);
            ATRewardedVideo.Instance.addsetting(mPlacementId_rewardvideo_all, addsetting());
        }
       



		Dictionary<string,string> jsonmap = new Dictionary<string,string>();
		jsonmap.Add("age", "22");
		jsonmap.Add("sex", "lady");
		jsonmap.Add("rv", "1");


        ATRewardedVideo.Instance.loadVideoAd(mPlacementId_rewardvideo_all,jsonmap);
		
	}
	public void showVideo(){
		
		Debug.Log ("Developer show video....");
        ATRewardedVideo.Instance.showAd(mPlacementId_rewardvideo_all);
		
	}

	public void isReady(){

		// Debug.Log ("Developer isReady ?....");
        bool b = ATRewardedVideo.Instance.hasAdReady(mPlacementId_rewardvideo_all);
		Debug.Log("Developer isReady video...." + b);
      
	}

	public void cleanad(){
		Debug.Log ("Developer cleanad ....");
        ATRewardedVideo.Instance.cleanAd(mPlacementId_rewardvideo_all);
	}

//	bool isPaused;
//	void OnApplicationFocus(bool hasFocus)
//	{
//		 
//		isPaused = !hasFocus;
//		Debug.Log ("Developer OnApplicationFocus?"+isPaused);
//
//		ATRewardedVideo.Instance.onApplicationForces (currunitid);
//	}
//
//	void OnApplicationPause(bool pauseStatus)
//	{
//		isPaused = pauseStatus;
//		Debug.Log ("Developer OnApplicationPause?"+isPaused);
//		ATRewardedVideo.Instance.onApplicationPasue (currunitid);
//	}



    class ATCallbackListener : ATRewardedVideoListener {
        
        public void onRewardedVideoAdLoaded(string unitId){
            Debug.Log("Developer onRewardedVideoAdLoaded------");
        }

        public void onRewardedVideoAdLoadFail(string unitId, string code, string message){
            Debug.Log("Developer onRewardedVideoAdLoadFail------:code" + code + "--message:" + message);
        }

        public void onRewardedVideoAdPlayStart(string unitId){
            Debug.Log("Developer onRewardedVideoAdPlayStart------");
        }

        public void onRewardedVideoAdPlayEnd(string unitId){
            Debug.Log("Developer onRewardedVideoAdPlayEnd------");
        }

        public void onRewardedVideoAdPlayFail(string unitId, string code, string message){
            Debug.Log("Developer onRewardedVideoAdPlayFail------code:" + code + "---message:" + message);
        }

        public void onRewardedVideoAdPlayClosed(string unitId, bool isReward){
            Debug.Log("Developer onRewardedVideoAdPlayClosed------isReward:" + isReward);
        }

        public void onRewardedVideoAdPlayClicked(string unitId){
            Debug.Log("Developer onRewardVideoAdPlayClicked------");
        }

        public void onReward(string unitId){
            Debug.Log("Developer onReward------");
        }
    }
}
