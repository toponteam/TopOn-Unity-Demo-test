using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Reflection;
using System;

using AnyThinkAds.Common;
using AnyThinkAds.ThirdParty.MiniJSON;

namespace AnyThinkAds.Api
{
	public class ATInterstitialAd
	{
		private static readonly ATInterstitialAd instance = new ATInterstitialAd();
		private IATInterstitialAdClient client;

		private ATInterstitialAd()
		{
            client = GetATInterstitialAdClient();
		}

		public static ATInterstitialAd Instance 
		{
			get
			{
				return instance;
			}
		}

		public void loadInterstitialAd(string unitId, Dictionary<string,string> pairs)
        {
            
            client.loadInterstitialAd(unitId, Json.Serialize(pairs));

        }

		public void setListener(ATInterstitialAdListener listener)
        {
            client.setListener(listener);
        }

        public bool hasInterstitialAdReady(string unitId)
        {
            return client.hasInterstitialAdReady(unitId);

        }

        public void showInterstitialAd(string unitId)
        {
            client.showInterstitialAd(unitId);
        }

        public IATInterstitialAdClient GetATInterstitialAdClient()
        {
            Type anythinkSDKAPIClientFactory = Type.GetType(
                "AnyThinkAds.ATAdsClientFactory,Assembly-CSharp");
            MethodInfo method = anythinkSDKAPIClientFactory.GetMethod(
                "BuildInterstitialAdClient",
                BindingFlags.Static | BindingFlags.Public);
            return (IATInterstitialAdClient)method.Invoke(null, null);
        }
	}
}
