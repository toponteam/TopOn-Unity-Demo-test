using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Reflection;
using System;

using AnyThinkAds.Common;
using AnyThinkAds.ThirdParty.MiniJSON;


namespace AnyThinkAds.Api
{
    public class ATNativeAd
    {

        private static readonly ATNativeAd instance = new ATNativeAd();
        private IATNativeAdClient client;

        public ATNativeAd(){
            client = GetATNativeAdClient();
        }

        public static ATNativeAd Instance
        {
            get
            {
                return instance;
            }
        }

		public void setLocalExtra(string unitId, Dictionary<String,String> pairs){
			client.setLocalExtra(unitId,Json.Serialize(pairs));
		}
        public void loadNativeAd(string unitId, Dictionary<String,String> pairs){
            client.loadNativeAd(unitId,Json.Serialize(pairs));
        }

        public bool hasAdReady(string unitId){
            return client.hasAdReady(unitId);
        }

        public void setListener(ATNativeAdListener listener){
            client.setListener(listener);
        }

        public void renderAdToScene(string unitId, ATNativeAdView anyThinkNativeAdView){
            client.renderAdToScene(unitId, anyThinkNativeAdView);
        }

        public void cleanAdView(string unitId, ATNativeAdView anyThinkNativeAdView){
            client.cleanAdView(unitId, anyThinkNativeAdView);
        }

        public void onApplicationForces(string unitId, ATNativeAdView anyThinkNativeAdView){
            client.onApplicationForces(unitId, anyThinkNativeAdView);
        }

        public void onApplicationPasue(string unitId, ATNativeAdView anyThinkNativeAdView){
            client.onApplicationPasue(unitId, anyThinkNativeAdView);
        }

        public void cleanCache(string unitId){
            client.cleanCache(unitId);
        }



        public IATNativeAdClient GetATNativeAdClient()
        {
            Type anythinkSDKAPIClientFactory = Type.GetType(
                "AnyThinkAds.ATAdsClientFactory,Assembly-CSharp");
            MethodInfo method = anythinkSDKAPIClientFactory.GetMethod(
                "BuildNativeAdClient",
                BindingFlags.Static | BindingFlags.Public);
            return (IATNativeAdClient)method.Invoke(null, null);
        }

    }
}