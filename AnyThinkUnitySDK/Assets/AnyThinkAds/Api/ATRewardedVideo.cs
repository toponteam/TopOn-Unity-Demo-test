using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Reflection;
using System;

using AnyThinkAds.Common;
using AnyThinkAds.ThirdParty.MiniJSON;


namespace AnyThinkAds.Api
{
    public class ATRewardedVideo
    {
        private static readonly ATRewardedVideo instance = new ATRewardedVideo();
        private IATRewardedVideoAdClient client;

        private ATRewardedVideo()
        {
            client = GetATRewardedClient();
        }

        public static ATRewardedVideo Instance
        {
            get
            {
                return instance;
            }
        }


		/***
		 * 
		 */
        public void loadVideoAd(string placementId, Dictionary<string,string> pairs)
        {
            
            client.loadVideoAd(placementId, Json.Serialize(pairs));

        }

		public void setListener(ATRewardedVideoListener listener)
        {
            client.setListener(listener);
        }

		public void addsetting(string placementId,Dictionary<string,object> pairs){
			client.addsetting (placementId,Json.Serialize(pairs));
		}
        public bool hasAdReady(string placementId)
        {
            return client.hasAdReady(placementId);

        }

        public string checkAdStatus(string placementId)
        {
            return client.checkAdStatus(placementId);

        }

        public void setUserData(string placementId, string userId, string customData)
        {
            client.setUserData(placementId, userId, customData);

        }

        public void showAd(string placementId)
        {
            client.showAd(placementId, Json.Serialize(new Dictionary<string, string>()));
        }

        public void showAd(string placementId, Dictionary<string, string> pairs)
        {
            client.showAd(placementId, Json.Serialize(pairs));
        }

        public void cleanAd(string placementId)
        {
            client.cleanAd(placementId);
        }

        public void onApplicationForces(string placementId)
        {
            client.onApplicationForces(placementId);
        }

        public void onApplicationPasue(string placementId)
        {
            client.onApplicationPasue(placementId);
        }

        public IATRewardedVideoAdClient GetATRewardedClient()
        {
            return AnyThinkAds.ATAdsClientFactory.BuildRewardedVideoAdClient();
        }

    }
}