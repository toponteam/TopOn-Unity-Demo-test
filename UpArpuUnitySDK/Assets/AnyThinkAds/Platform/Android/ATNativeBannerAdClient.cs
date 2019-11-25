using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using AnyThinkAds.Common;
using AnyThinkAds.Api;

namespace AnyThinkAds.Android
{
    public class ATNativeBannerAdClient :IATNativeBannerAdClient
    {
        public ATNativeBannerAdClient() {

        }

    	public void loadAd(string unitId, string mapJson) {

    	}
    	
		public bool adReady(string unitId) {
			return false;
		}

        public void setListener(ATNativeBannerAdListener listener) {

        }

        public void showAd(string unitId, ATRect rect, Dictionary<string, string> pairs) {

        }

        public void removeAd(string unitId) {

        }
    }
}
