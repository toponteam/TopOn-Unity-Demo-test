using System.Collections;
using System.Collections.Generic;
using System.Reflection;
using System;
using UnityEngine;



using AnyThinkAds.Common;
using AnyThinkAds.ThirdParty.MiniJSON;


namespace AnyThinkAds.Api
{
    public class ATSDKAPI
    {
        public static readonly int PERSONALIZED = 0;
        public static readonly int NONPERSONALIZED = 1;
        public static readonly int UNKNOWN = 2;

        private static readonly IATSDKAPIClient client = GetATSDKAPIClient();

        public static void initSDK(string appId, string appKey)
        {
            client.initSDK(appId, appKey);
        }

        public static void initSDK(string appId, string appKey, ATSDKInitListener listener)
        {
            client.initSDK(appId, appKey, listener);
        }

        public static void setGDPRLevel(int level)
        {
            client.setGDPRLevel(level);
        }

        public static int getGDPRLevel() {
            return client.getGDPRLevel();
        }

        public static bool isEUTraffic() {
            return client.isEUTraffic();
        }

        public static void setChannel(string channel)
        {
            client.setChannel(channel);
        }

        public static void initCustomMap(Dictionary<string, string> customMap)
        {
            client.initCustomMap(Json.Serialize(customMap));
        }

        public static void setCustomDataForPlacementID(Dictionary<string, string> customData, string placementID)
        {
            client.setCustomDataForPlacementID(Json.Serialize(customData), placementID);
        }

        public static void showGDPRAuth()
        {
            client.showGDPRAuth();
        }

        public void setPurchaseFlag() 
        {
            client.setPurchaseFlag();
        }

        public bool purchaseFlag() 
        {
            return client.purchaseFlag();
        }

        public void clearPurchaseFlag() 
        {
            client.clearPurchaseFlag();
        }

        public static void setLogDebug(bool isDebug)
        {
            client.setLogDebug(isDebug);
        }

		public static void addNetworkGDPRInfo(int networkType, Dictionary<string,object> dictionary)
        {
            client.addNetworkGDPRInfo(networkType, Json.Serialize(dictionary));
        }

        private static IATSDKAPIClient GetATSDKAPIClient(){
            Debug.Log("GetATSDKAPIClient");
            Type anythinkSDKAPIClientFactory = Type.GetType(
                "AnyThinkAds.ATAdsClientFactory,Assembly-CSharp");
            MethodInfo method = anythinkSDKAPIClientFactory.GetMethod(
                "BuildSDKAPIClient",
                BindingFlags.Static | BindingFlags.Public);
            return (IATSDKAPIClient)method.Invoke(null, null);
        }

    }
}

