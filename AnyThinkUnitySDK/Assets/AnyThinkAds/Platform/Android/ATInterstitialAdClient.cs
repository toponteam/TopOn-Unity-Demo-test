using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Common;
using AnyThinkAds.Api;
namespace AnyThinkAds.Android
{
    public class ATInterstitialAdClient : AndroidJavaProxy,IATInterstitialAdClient
    {

        private Dictionary<string, AndroidJavaObject> interstitialHelperMap = new Dictionary<string, AndroidJavaObject>();

		//private  AndroidJavaObject videoHelper;
        private  ATInterstitialAdListener anyThinkListener;

        public ATInterstitialAdClient() : base("com.anythink.unitybridge.interstitial.InterstitialListener")
        {
            
        }


        public void loadInterstitialAd(string unitId, string mapJson)
        {

            //如果不存在则直接创建对应广告位的helper
            if(!interstitialHelperMap.ContainsKey(unitId))
            {
                AndroidJavaObject videoHelper = new AndroidJavaObject(
                    "com.anythink.unitybridge.interstitial.InterstitialHelper", this);
                videoHelper.Call("initInterstitial", unitId);
                interstitialHelperMap.Add(unitId, videoHelper);
                Debug.Log("ATInterstitialAdClient : no exit helper ,create helper ");
            }

            try
            {
                Debug.Log("ATInterstitialAdClient : loadInterstitialAd ");
                interstitialHelperMap[unitId].Call("loadInterstitialAd", mapJson);
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATInterstitialAdClient :  error."+e.Message);
            }


        }

        public void setListener(ATInterstitialAdListener listener)
        {
            anyThinkListener = listener;
        }

        public bool hasInterstitialAdReady(string unitId)
        {
			bool isready = false;
			Debug.Log ("ATInterstitialAdClient : hasAdReady....");
			try{
                if (interstitialHelperMap.ContainsKey(unitId)) {
                    isready = interstitialHelperMap[unitId].Call<bool> ("isAdReady");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATInterstitialAdClient :  error."+e.Message);
			}
			return isready; 
        }


        public void showInterstitialAd(string unitId, string jsonmap)
        {
			Debug.Log("ATInterstitialAdClient : showAd " );

			try{
                if (interstitialHelperMap.ContainsKey(unitId)) {
                    this.interstitialHelperMap[unitId].Call ("showInterstitialAd", jsonmap);
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATInterstitialAdClient :  error."+e.Message);

			}
        }


        public void cleanCache(string unitId)
        {
			
			Debug.Log("ATInterstitialAdClient : clean" );

			try{
                if (interstitialHelperMap.ContainsKey(unitId)) {
                    this.interstitialHelperMap[unitId].Call ("clean");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATInterstitialAdClient :  error."+e.Message);
			}
        }

        public void onApplicationForces(string unitId)
        {
			Debug.Log ("onApplicationForces.... ");
			try{
				if (interstitialHelperMap.ContainsKey(unitId)) {
					this.interstitialHelperMap[unitId].Call ("onResume");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATInterstitialAdClient :  error."+e.Message);
			}
        }

        public void onApplicationPasue(string unitId)
        {
			Debug.Log ("onApplicationPasue.... ");
			try{
				if (interstitialHelperMap.ContainsKey(unitId)) {
					this.interstitialHelperMap[unitId].Call ("onPause");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATInterstitialAdClient :  error."+e.Message);
			}
        }

        //广告加载成功
        public void onInterstitialAdLoaded(string unitId)
        {
            Debug.Log("onInterstitialAdLoaded...unity3d.");
            if(anyThinkListener != null){
                anyThinkListener.onInterstitialAdLoad(unitId);
            }
        }

        //广告加载失败
        public void onInterstitialAdLoadFail(string unitId,string code, string error)
        {
            Debug.Log("onInterstitialAdFailed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onInterstitialAdLoadFail(unitId, code, error);
            }
        }

        //开始播放
        public void onInterstitialAdVideoStart(string unitId)
        {
            Debug.Log("onInterstitialAdPlayStart...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onInterstitialAdStartPlayingVideo(unitId);
            }
        }

        //结束播放
        public void onInterstitialAdVideoEnd(string unitId)
        {
            Debug.Log("onInterstitialAdPlayEnd...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onInterstitialAdEndPlayingVideo(unitId);
            }
        }

        //播放失败
        public void onInterstitialAdVideoError(string unitId,string code, string error)
        {
            Debug.Log("onInterstitialAdPlayFailed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onInterstitialAdFailedToPlayVideo(unitId, code, error);
            }
        }
        //广告关闭
        public void onInterstitialAdClose(string unitId)
        {
            Debug.Log("onInterstitialAdClosed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onInterstitialAdClose(unitId);
            }
        }
        //广告点击
        public void onInterstitialAdClicked(string unitId)
        {
            Debug.Log("onInterstitialAdClicked...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onInterstitialAdClick(unitId);
            }
        }

        public void onInterstitialAdShow(string unitId){
            Debug.Log("onInterstitialAdShow...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onInterstitialAdShow(unitId);
            }
        }
       
    }
}
