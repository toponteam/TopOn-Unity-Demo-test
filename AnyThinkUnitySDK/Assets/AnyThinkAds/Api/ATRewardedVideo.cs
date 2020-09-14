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
        public void loadVideoAd(string unitId, Dictionary<string,string> pairs)
        {
            
            client.loadVideoAd(unitId, Json.Serialize(pairs));

        }

		public void setListener(ATRewardedVideoListener listener)
        {
            client.setListener(listener);
        }

		public void addsetting(string unitId,Dictionary<string,object> pairs){
			client.addsetting (unitId,Json.Serialize(pairs));
		}
        public bool hasAdReady(string unitId)
        {
            return client.hasAdReady(unitId);

        }

        public void setUserData(string unitId, string userId, string customData)
        {
            client.setUserData(unitId, userId, customData);

        }

        public void showAd(string unitId)
        {
            client.showAd(unitId, Json.Serialize(new Dictionary<string, string>()));
        }

        public void showAd(string unitId, Dictionary<string, string> pairs)
        {
            client.showAd(unitId, Json.Serialize(pairs));
        }

        public void cleanAd(string unitId)
        {
            client.cleanAd(unitId);
        }

        public void onApplicationForces(string unitId)
        {
            client.onApplicationForces(unitId);
        }

        public void onApplicationPasue(string unitId)
        {
            client.onApplicationPasue(unitId);
        }

        public IATRewardedVideoAdClient GetATRewardedClient()
        {
            return AnyThinkAds.ATAdsClientFactory.BuildRewardedVideoAdClient();
        }

    }
}