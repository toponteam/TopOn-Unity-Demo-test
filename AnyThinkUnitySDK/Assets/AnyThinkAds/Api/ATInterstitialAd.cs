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

		public void loadInterstitialAd(string placementId, Dictionary<string,string> pairs)
        {
            client.loadInterstitialAd(placementId, Json.Serialize(pairs));
        }

		public void setListener(ATInterstitialAdListener listener)
        {
            client.setListener(listener);
        }

        public bool hasInterstitialAdReady(string placementId)
        {
            return client.hasInterstitialAdReady(placementId);
        }

        public string checkAdStatus(string placementId)
        {
            return client.checkAdStatus(placementId);
        }

        public void showInterstitialAd(string placementId)
        {
            client.showInterstitialAd(placementId, Json.Serialize(new Dictionary<string, string>()));
        }

        public void showInterstitialAd(string placementId, Dictionary<string, string> pairs)
        {
            client.showInterstitialAd(placementId, Json.Serialize(pairs));
        }

        public IATInterstitialAdClient GetATInterstitialAdClient()
        {
            return AnyThinkAds.ATAdsClientFactory.BuildInterstitialAdClient();
        }
	}
}
