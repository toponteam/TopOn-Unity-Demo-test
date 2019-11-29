using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Reflection;
using System;

using AnyThinkAds.Common;
using AnyThinkAds.ThirdParty.MiniJSON;

namespace AnyThinkAds.Api
{
    public class ATBannerAdLoadingExtra
    {
        public static readonly string kATBannerAdLoadingExtraBannerAdSize = "banner_ad_size";
        public static readonly string kATBannerAdLoadingExtraBannerAdSizeStruct = "banner_ad_size_struct";
        public static readonly string kATBannerAdSizeUsesPixelFlagKey = "uses_pixel";
    }
    public class ATBannerAd 
	{
		private static readonly ATBannerAd instance = new ATBannerAd();
		private IATBannerAdClient client;

		private ATBannerAd() 
		{
            client = GetATBannerAdClient();
		}

		public static ATBannerAd Instance 
		{
			get 
			{
				return instance;
			}
		}

		/**
		API
		*/
		public void loadBannerAd(string unitId, Dictionary<string,object> pairs)
		{
            if (pairs.ContainsKey(ATBannerAdLoadingExtra.kATBannerAdLoadingExtraBannerAdSize))
            {
                client.loadBannerAd(unitId, Json.Serialize(pairs));
            }
            else if (pairs.ContainsKey(ATBannerAdLoadingExtra.kATBannerAdLoadingExtraBannerAdSizeStruct))
            {
                ATSize size = (ATSize)(pairs[ATBannerAdLoadingExtra.kATBannerAdLoadingExtraBannerAdSizeStruct]);
                Dictionary<string, object> newPaires = new Dictionary<string, object> { { ATBannerAdLoadingExtra.kATBannerAdLoadingExtraBannerAdSize, size.width + "x" + size.height }, { ATBannerAdLoadingExtra.kATBannerAdSizeUsesPixelFlagKey, size.usesPixel } };
                client.loadBannerAd(unitId, Json.Serialize(newPaires));
            }
            else
            {
                client.loadBannerAd(unitId, Json.Serialize(pairs));
            }
			
		}

		public void setListener(ATBannerAdListener listener)
        {
            client.setListener(listener);
        }

        public void showBannerAd(string unitId, ATRect rect)
        {
            client.showBannerAd(unitId, rect);
        }

        public void showBannerAd(string unitId)
        {
            client.showBannerAd(unitId);
        }

        public void hideBannerAd(string unitId)
        {
            client.hideBannerAd(unitId);
        }

        public void cleanBannerAd(string unitId)
        {
            client.cleanBannerAd(unitId);
        }

        public IATBannerAdClient GetATBannerAdClient()
        {
            Type anythinkSDKAPIClientFactory = Type.GetType("AnyThinkAds.ATAdsClientFactory,Assembly-CSharp");
            MethodInfo method = anythinkSDKAPIClientFactory.GetMethod(
                "BuildBannerAdClient",
                BindingFlags.Static | BindingFlags.Public);
            return (IATBannerAdClient)method.Invoke(null, null);
        }
	}
}
