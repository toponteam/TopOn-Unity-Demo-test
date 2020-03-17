using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Common;
using AnyThinkAds.Api;
namespace AnyThinkAds.Android
{
    public class ATBannerAdClient : AndroidJavaProxy, IATBannerAdClient
    {

        private Dictionary<string, AndroidJavaObject> bannerHelperMap = new Dictionary<string, AndroidJavaObject>();

        private  ATBannerAdListener anyThinkListener;

        public ATBannerAdClient() : base("com.anythink.unitybridge.banner.BannerListener")
        {
            
        }


        public void loadBannerAd(string unitId, string mapJson)
        {

            //如果不存在则直接创建对应广告位的helper
            if(!bannerHelperMap.ContainsKey(unitId))
            {
                AndroidJavaObject bannerHelper = new AndroidJavaObject(
                    "com.anythink.unitybridge.banner.BannerHelper", this);
                bannerHelper.Call("initBanner", unitId);
                bannerHelperMap.Add(unitId, bannerHelper);
                Debug.Log("ATBannerAdClient : no exit helper ,create helper ");
            }

            try
            {
                Debug.Log("ATBannerAdClient : loadBannerAd ");
                bannerHelperMap[unitId].Call("loadBannerAd", mapJson);
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine("Exception caught: {0}", e);
                Debug.Log ("ATBannerAdClient :  error."+e.Message);
            }


        }

        public void setListener(ATBannerAdListener listener)
        {
            anyThinkListener = listener;
        }


        public void showBannerAd(string unitId, string position)
        {
            Debug.Log("ATBannerAdClient : showBannerAd by position" );
            //todo
            try
            {
                if (bannerHelperMap.ContainsKey(unitId))
                {
                    this.bannerHelperMap[unitId].Call("showBannerAd", position);
                }
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine("Exception caught: {0}", e);
                Debug.Log("ATBannerAdClient :  error." + e.Message);
            }

        }
       

		
        public void showBannerAd(string unitId, ATRect rect)
        {
            Debug.Log("ATBannerAdClient : showBannerAd " );

			try{
                if (bannerHelperMap.ContainsKey(unitId)) {
                    this.bannerHelperMap[unitId].Call ("showBannerAd", rect.x, rect.y, rect.width, rect.height);
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
                Debug.Log ("ATBannerAdClient :  error."+e.Message);

			}
        }

        public void cleanBannerAd(string unitId)
        {
			
            Debug.Log("ATBannerAdClient : cleanBannerAd" );

			try{
                if (bannerHelperMap.ContainsKey(unitId)) {
                    this.bannerHelperMap[unitId].Call ("cleanBannerAd");
				}
			}catch(System.Exception e){
				System.Console.WriteLine("Exception caught: {0}", e);
                Debug.Log ("ATBannerAdClient :  error."+e.Message);
			}
        }

        public void hideBannerAd(string unitId) 
        {
            Debug.Log("ATBannerAdClient : hideBannerAd");

            try
            {
                if (bannerHelperMap.ContainsKey(unitId))
                {
                    this.bannerHelperMap[unitId].Call("hideBannerAd");
                }
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine("Exception caught: {0}", e);
                Debug.Log("ATBannerAdClient :  error." + e.Message);
            }
        }

        //针对已有的进行展示，没有就调用该方法无效
        public void showBannerAd(string unitId)
        {
            Debug.Log("ATBannerAdClient : showBannerAd ");

            try
            {
                if (bannerHelperMap.ContainsKey(unitId))
                {
                    this.bannerHelperMap[unitId].Call("showBannerAd");
                }
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine("Exception caught: {0}", e);
                Debug.Log("ATBannerAdClient :  error." + e.Message);

            }
        }

        public void cleanCache(string unitId)
        {
            
        }

       
        //广告加载成功
        public void onBannerLoaded(string unitId)
        {
            Debug.Log("onBannerLoaded...unity3d.");
            if(anyThinkListener != null){
                anyThinkListener.onAdLoad(unitId);
            } 
        }

        //广告加载失败
        public void onBannerFailed(string unitId,string code, string error)
        {
            Debug.Log("onBannerFailed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onAdLoadFail(unitId, code, error);
            }
        }

        //广告点击
        public void onBannerClicked(string unitId, string callbackJson)
        {
            Debug.Log("onBannerClicked...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onAdClick(unitId, new ATCallbackInfo(callbackJson));
            }
        }

        //广告展示
        public void onBannerShow(string unitId, string callbackJson)
        {
            Debug.Log("onBannerShow...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onAdImpress(unitId, new ATCallbackInfo(callbackJson));
            }
        }

        //广告关闭
        public void onBannerClose(string unitId)
        {
            Debug.Log("onBannerClose...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onAdClose(unitId);
            }
        }
        //广告关闭
        public void onBannerAutoRefreshed(string unitId, string callbackJson)
        {
            Debug.Log("onBannerAutoRefreshed...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onAdAutoRefresh(unitId, new ATCallbackInfo(callbackJson));
            }
        }
        //广告自动刷新失败
        public void onBannerAutoRefreshFail(string unitId, string code, string msg)
        {
            Debug.Log("onBannerAutoRefreshFail...unity3d.");
            if (anyThinkListener != null)
            {
                anyThinkListener.onAdAutoRefreshFail(unitId,code,msg);
            }
        }
       
    }
}
