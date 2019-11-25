using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;

namespace AnyThinkAds.Android
{
    public class ATNativeAdClient : AndroidJavaProxy, IATNativeAdClient
    {
		
        private Dictionary<string, AndroidJavaObject> nativeAdHelperMap = new Dictionary<string, AndroidJavaObject>();
        private ATNativeAdListener mlistener;

        public ATNativeAdClient(): base("com.anythink.unitybridge.nativead.NativeListener")
        {

        }


		public void setLocalExtra (string unitId,string localExtra){
			if (!nativeAdHelperMap.ContainsKey (unitId)) {
				AndroidJavaObject nativeHelper = new AndroidJavaObject (
                    "com.anythink.unitybridge.nativead.NativeHelper", this);
				nativeHelper.Call ("initNative", unitId, localExtra);
				nativeAdHelperMap.Add (unitId, nativeHelper);

			}
		}

        public void loadNativeAd(string unitId, string mapJson)
        {
			Debug.Log ("loadNativeAd....jsonmap:"+mapJson);
            if(!nativeAdHelperMap.ContainsKey(unitId)){
                AndroidJavaObject nativeHelper = new AndroidJavaObject(
                    "com.anythink.unitybridge.nativead.NativeHelper", this);
                nativeHelper.Call("initNative", unitId, "{}");
                nativeAdHelperMap.Add(unitId, nativeHelper);
            }
			try{
                if (nativeAdHelperMap.ContainsKey(unitId)) {
                    nativeAdHelperMap[unitId].Call ("loadNative",mapJson);
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATNativeAdClient :  error."+e.Message);
			}
        }


        public bool hasAdReady(string unitId)
        {
			bool isready = false;
			Debug.Log ("hasAdReady....");
			try{
                if (nativeAdHelperMap.ContainsKey(unitId)) {
                    isready = nativeAdHelperMap[unitId].Call<bool> ("isAdReady");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATNativeAdClient :  error."+e.Message);
			}
			return isready;   
        }

        public void setListener(ATNativeAdListener listener)
        {
            mlistener = listener;
        }

		public void renderAdToScene(string unitId, ATNativeAdView anyThinkNativeAdView)
        {	
			string showconfig = anyThinkNativeAdView.toJSON ();
            //暂未实现 show
			Debug.Log ("renderAdToScene....showconfig >>>:"+showconfig);
			try{
                if (nativeAdHelperMap.ContainsKey(unitId)) {
                    nativeAdHelperMap[unitId].Call ("show",showconfig);
				}
			}catch(System.Exception e){
				Debug.Log ("ATNativeAdClient :  error."+e.Message);
				System.Console.WriteLine("Exception caught: {0}", e);
			}
        }

        public void cleanAdView(string unitId, ATNativeAdView anyThinkNativeAdView)
        {
           //
			Debug.Log ("cleanAdView.... ");
			try{

				if (nativeAdHelperMap.ContainsKey(unitId)) {
					nativeAdHelperMap[unitId].Call ("cleanView");
				}

			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATNativeAdClient :  error."+e.Message);
			}
        }

        public void onApplicationForces(string unitId, ATNativeAdView anyThinkNativeAdView)
        {


			Debug.Log ("onApplicationForces.... ");
			try{

				if (nativeAdHelperMap.ContainsKey(unitId)) {
					nativeAdHelperMap[unitId].Call ("onResume");
				}

			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATNativeAdClient :  error."+e.Message);
			}
        }


        public void onApplicationPasue(string unitId, ATNativeAdView anyThinkNativeAdView)
        {

			Debug.Log ("onApplicationPasue.... ");
			try{
				

				if (nativeAdHelperMap.ContainsKey(unitId)) {
					nativeAdHelperMap[unitId].Call ("onPause");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATNativeAdClient :  error."+e.Message);
			}
        }

        public void cleanCache(string unitId)
        {
			Debug.Log ("cleanCache....");
			try{
                if (nativeAdHelperMap.ContainsKey(unitId)) {
                    nativeAdHelperMap[unitId].Call ("clean");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
				Debug.Log ("ATNativeAdClient :  error."+e.Message);
			}
        }

        /**
     * 广告展示回调
     *
     * @param view
     */
        public void onAdImpressed(string unitId)
        {
            Debug.Log("onAdImpressed...unity3d.");
            if(mlistener != null){
                mlistener.onAdImpressed(unitId);
            }
        }

        /**
     * 广告点击回调
     *
     * @param view
     */
        public void onAdClicked(string unitId)
        {
            Debug.Log("onAdClicked...unity3d.");
            if (mlistener != null)
            {
                mlistener.onAdClicked(unitId);
            }
        }

        /**
     * 广告视频开始回调
     *
     * @param view
     */
        public void onAdVideoStart(string unitId)
        {
            Debug.Log("onAdVideoStart...unity3d.");
            if (mlistener != null)
            {
                mlistener.onAdVideoStart(unitId);
            }
        }

        /**
     * 广告视频结束回调
     *
     * @param view
     */
        public void onAdVideoEnd(string unitId)
        {
            Debug.Log("onAdVideoEnd...unity3d.");
            if (mlistener != null)
            {
                mlistener.onAdVideoEnd(unitId);
            }
        }

        /**
     * 广告视频进度回调
     *
     * @param view
     */
        public void onAdVideoProgress(string unitId,int progress)
        {
            Debug.Log("onAdVideoProgress...progress[" + progress + "]");
            if (mlistener != null)
            {
                mlistener.onAdVideoProgress(unitId, progress);
            }
        }


        /**
     * 广告加载成功
     */
        public void onNativeAdLoaded(string unitId)
        {
            Debug.Log("onNativeAdLoaded...unity3d.");
            if (mlistener != null)
            {
                mlistener.onAdLoaded(unitId);
            }

        }

        /**
     * 广告加载失败
     */
        public void onNativeAdLoadFail(string unitId,string code, string msg)
        {
            Debug.Log("onNativeAdLoadFail...unity3d. code:" + code + " msg:" + msg);
            if (mlistener != null)
            {
                mlistener.onAdLoadFail(unitId, code, msg);
            }
        }


    }
}
