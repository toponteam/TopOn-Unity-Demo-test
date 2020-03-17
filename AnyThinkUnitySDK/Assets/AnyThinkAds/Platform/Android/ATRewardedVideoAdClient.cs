using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Common;
using AnyThinkAds.Api;
namespace AnyThinkAds.Android
{
    public class ATRewardedVideoAdClient : AndroidJavaProxy,IATRewardedVideoAdClient
    {

        private Dictionary<string, AndroidJavaObject> videoHelperMap = new Dictionary<string, AndroidJavaObject>();

		//private  AndroidJavaObject videoHelper;
        private  ATRewardedVideoListener anyThinkListener;

        public ATRewardedVideoAdClient() : base("com.anythink.unitybridge.videoad.VideoListener")
        {
            
        }


        public void loadVideoAd(string unitId, string mapJson)
        {

            //如果不存在则直接创建对应广告位的helper
            if(!videoHelperMap.ContainsKey(unitId))
            {
                AndroidJavaObject videoHelper = new AndroidJavaObject(
                    "com.anythink.unitybridge.videoad.VideoHelper", this);
                videoHelper.Call("initVideo", unitId);
                videoHelperMap.Add(unitId, videoHelper);
                Debug.Log("ATRewardedVideoAdClient : no exit helper ,create helper ");
            }

            try
            {
                Debug.Log("ATRewardedVideoAdClient : loadVideoAd ");
                videoHelperMap[unitId].Call("fillVideo", mapJson);
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);
            }


        }

        public void setListener(ATRewardedVideoListener listener)
        {
            anyThinkListener = listener;
        }

        public bool hasAdReady(string unitId)
        {
			bool isready = false;
			Debug.Log ("ATRewardedVideoAdClient : hasAdReady....");
			try{
                if (videoHelperMap.ContainsKey(unitId)) {
                    isready = videoHelperMap[unitId].Call<bool> ("isAdReady");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);
			}
			return isready; 
        }

		public void setUserData(string unitId, string userId, string customData)
        {
			Debug.Log("ATRewardedVideoAdClient : setUserData  " );

			try{
                if (videoHelperMap.ContainsKey(unitId)) {
                    this.videoHelperMap[unitId].Call ("setUserData",userId,customData);
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);
			}
        }

        public void showAd(string unitId, string scenario)
        {
			Debug.Log("ATRewardedVideoAdClient : showAd " );

			try{
                if (videoHelperMap.ContainsKey(unitId)) {
                    this.videoHelperMap[unitId].Call ("showVideo", scenario);
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);

			}
        }

		public void addsetting (string unitId,string json){
			Debug.Log("ATRewardedVideoAdClient : addsetting" );

			try{
				if (videoHelperMap.ContainsKey(unitId)) {
					this.videoHelperMap[unitId].Call ("addsetting",json);
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);
			}
		}

        public void cleanAd(string unitId)
        {
			
			Debug.Log("ATRewardedVideoAdClient : clean" );

			try{
                if (videoHelperMap.ContainsKey(unitId)) {
                    this.videoHelperMap[unitId].Call ("clean");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);
			}
        }

        public void onApplicationForces(string unitId)
        {
			Debug.Log ("onApplicationForces.... ");
			try{
				if (videoHelperMap.ContainsKey(unitId)) {
					this.videoHelperMap[unitId].Call ("onResume");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);
			}
        }

        public void onApplicationPasue(string unitId)
        {
			Debug.Log ("onApplicationPasue.... ");
			try{
				if (videoHelperMap.ContainsKey(unitId)) {
					this.videoHelperMap[unitId].Call ("onPause");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATRewardedVideoAdClient :  error."+e.Message);
			}
        }

        //广告加载成功
        public void onRewardedVideoAdLoaded(string unitId)
        {
            Debug.Log("onRewardedVideoAdLoaded...unity3d.");
            if(anyThinkListener != null){
                anyThinkListener.onRewardedVideoAdLoaded(unitId);
            }
        }

        //广告加载失败
        public void onRewardedVideoAdFailed(string unitId,string code, string error)
        {
            Debug.Log("onRewardedVideoAdFailed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onRewardedVideoAdLoadFail(unitId, code, error);
            }
        }

        //开始播放
        public void onRewardedVideoAdPlayStart(string unitId, string callbackJson)
        {
            Debug.Log("onRewardedVideoAdPlayStart...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onRewardedVideoAdPlayStart(unitId, new ATCallbackInfo(callbackJson));
            }
        }

        //结束播放
        public void onRewardedVideoAdPlayEnd(string unitId, string callbackJson)
        {
            Debug.Log("onRewardedVideoAdPlayEnd...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onRewardedVideoAdPlayEnd(unitId, new ATCallbackInfo(callbackJson));
            }
        }

        //播放失败
        public void onRewardedVideoAdPlayFailed(string unitId,string code, string error)
        {
            Debug.Log("onRewardedVideoAdPlayFailed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onRewardedVideoAdPlayFail(unitId, code, error);
            }
        }
        //广告关闭
        public void onRewardedVideoAdClosed(string unitId,bool isRewarded, string callbackJson)
        {
            Debug.Log("onRewardedVideoAdClosed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onRewardedVideoAdPlayClosed(unitId,isRewarded, new ATCallbackInfo(callbackJson));
            }
        }
        //广告点击
        public void onRewardedVideoAdPlayClicked(string unitId, string callbackJson)
        {
            Debug.Log("onRewardedVideoAdPlayClicked...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onRewardedVideoAdPlayClicked(unitId, new ATCallbackInfo(callbackJson));
            }
        }

        //广告激励下发
        public void onReward(string unitId, string callbackJson)
        {
            Debug.Log("onReward...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onReward(unitId, new ATCallbackInfo(callbackJson));
            }
        }
       
    }
}
