using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Reflection;
using System;

using AnyThinkAds.Common;
using AnyThinkAds.ThirdParty.LitJson;

namespace AnyThinkAds.Api
{
   
    public class ATInterstitialAutoAd
	{
		private static readonly ATInterstitialAutoAd instance = new ATInterstitialAutoAd();
		private IATInterstitialAdClient client;

		private ATInterstitialAutoAd()
		{
            client = GetATInterstitialAdClient();
		}

		public static ATInterstitialAutoAd Instance 
		{
			get
			{
				return instance;
			}
		}

	     public IATInterstitialAdClient GetATInterstitialAdClient()
        {
            return AnyThinkAds.ATAdsClientFactory.BuildInterstitialAdClient();
        }

		public void setListener(ATInterstitialAdListener listener)
        {
            client.setListener(listener);
        }

        // auto

        public void addAutoLoadAdPlacementID(string[] placementIDList)
        {
            client.addAutoLoadAdPlacementID(placementIDList);   
        }

        public void removeAutoLoadAdPlacementID(string[] placementIDList)
        {
            if (placementIDList != null && placementIDList.Length > 0)
            {
                string placementIDListString = JsonMapper.ToJson(placementIDList);
                client.removeAutoLoadAdPlacementID(placementIDListString);
                Debug.Log("removeAutoLoadAdPlacementID, placementIDList === " + placementIDListString);
            }
            else
            {
                Debug.Log("removeAutoLoadAdPlacementID, placementIDList = null");
            } 
        }

        public string checkAutoAdStatus(string placementId)
        {
            return client.checkAutoAdStatus(placementId);
        }

        public bool autoLoadInterstitialAdReadyForPlacementID(string placementId)
        {
            return client.autoLoadInterstitialAdReadyForPlacementID(placementId);
        }
        public string getAutoValidAdCaches(string placementId)
        {
            return client.getAutoValidAdCaches(placementId);
        }

        public void setAutoLocalExtra(string placementId, Dictionary<string,string> pairs)
        {
            client.setAutoLocalExtra(placementId, JsonMapper.ToJson(pairs));
        }
        public void entryAutoAdScenarioWithPlacementID(string placementId, string scenarioID)
        {
            client.entryAutoAdScenarioWithPlacementID(placementId, scenarioID);
        }

        public void showAutoAd(string placementId)
        {
            client.showAutoAd(placementId, JsonMapper.ToJson(new Dictionary<string, string>()));
        }

        public void showAutoAd(string placementId, Dictionary<string, string> pairs)
        {
            client.showAutoAd(placementId, JsonMapper.ToJson(pairs));
        }    






	}
}
