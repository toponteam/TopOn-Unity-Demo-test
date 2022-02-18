using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.Common;
using AnyThinkAds.Api;
using AOT;
using System;
using AnyThinkAds.ThirdParty.LitJson;

namespace AnyThinkAds.iOS {
	public class ATSDKAPIClient : IATSDKAPIClient {
        static private ATGetUserLocationListener locationListener;
        static private ATGetAreaListener areaListener;

        public ATSDKAPIClient () {
            Debug.Log("Unity:ATSDKAPIClient::ATSDKAPIClient()");
		}
		public void initSDK(string appId, string appKey) {
			Debug.Log("Unity:ATSDKAPIClient::initSDK(string, string)");
			initSDK(appId, appKey, null);
	    }

	    public void initSDK(string appId, string appKey, ATSDKInitListener listener) {
	    	Debug.Log("Unity:ATSDKAPIClient::initSDK(string, string, ATSDKInitListener)");
	    	bool started = ATManager.StartSDK(appId, appKey);
            if (listener != null)
            {
                if (started)
                {
                    listener.initSuccess();
                }
                else
                {
                    listener.initFail("Failed to init.");
                }
            }
	    }

        [MonoPInvokeCallback(typeof(Func<string, int>))]
       static public int DidGetUserLocation(string location)
        {
            if (locationListener != null) { locationListener.didGetUserLocation(Int32.Parse(location)); }
            return 0;
        }

        [MonoPInvokeCallback(typeof(Func<string, int>))]
        static public int GetAreaInfo(string msg)
        {
            Debug.Log("Unity:ATSDKAPIClient::GetAreaInfo(" + msg + ")");
            if (areaListener != null) 
            { 
                JsonData msgJsonData = JsonMapper.ToObject(msg);
                IDictionary idic = (System.Collections.IDictionary)msgJsonData;

                if (idic.Contains("areaCode")) {
                    string areaCode = (string)msgJsonData["areaCode"];
                    Debug.Log("Unity:ATSDKAPIClient::GetAreaInfo::areaCode(" + areaCode + ")");
                    areaListener.onArea(areaCode);
                }
                
                if (idic.Contains("errorMsg")) { 
                    string errorMsg = (string)msgJsonData["errorMsg"];
                    Debug.Log("Unity:ATSDKAPIClient::GetAreaInfo::errorMsg(" + errorMsg + ")");
                    areaListener.onError(errorMsg);
                }
            }
            return 0;
        }

        public void getUserLocation(ATGetUserLocationListener listener)
        {
            Debug.Log("Unity:ATSDKAPIClient::getUserLocation()");
            ATSDKAPIClient.locationListener = listener;
            ATManager.getUserLocation(DidGetUserLocation);
        }

        public void setGDPRLevel(int level) {
	    	Debug.Log("Unity:ATSDKAPIClient::setGDPRLevel()");
	    	ATManager.SetDataConsent(level);
	    }

	    public void showGDPRAuth() {
	    	Debug.Log("Unity:ATSDKAPIClient::showGDPRAuth()");
	    	ATManager.ShowGDPRAuthDialog();
	    }

	    public void setPurchaseFlag() {
			ATManager.setPurchaseFlag();
		}

		public void clearPurchaseFlag() {
			ATManager.clearPurchaseFlag();
		}

		public bool purchaseFlag() {
			return ATManager.purchaseFlag();
		}

	    public void addNetworkGDPRInfo(int networkType, string mapJson) {
	    	Debug.Log("Unity:ATSDKAPIClient::addNetworkGDPRInfo()");
	    	ATManager.SetNetworkGDPRInfo(networkType, mapJson);
	    }

        public void setChannel(string channel)
        {
            ATManager.setChannel(channel);
        }

        public void setSubChannel(string subchannel)
        {
            ATManager.setSubChannel(subchannel);
        }

        public void initCustomMap(string jsonMap)
        {
            ATManager.setCustomMap(jsonMap);
        }

        public void setCustomDataForPlacementID(string customData, string placementID)
        {
            ATManager.setCustomDataForPlacementID(customData, placementID);
        }

        public void setLogDebug(bool isDebug)
        {
            ATManager.setLogDebug(isDebug);
        }

        public int getGDPRLevel()
        {
            return ATManager.GetDataConsent();
        }

        public bool isEUTraffic()
        {
            return ATManager.isEUTraffic();
        }

        public void deniedUploadDeviceInfo(string deniedInfo)
        {
            ATManager.deniedUploadDeviceInfo(deniedInfo);
        }

        public void setExcludeBundleIdArray(string bundleIds)
        {
            Debug.Log("Unity:ATSDKAPIClient::setExcludeBundleIdArray()");
            ATManager.setExcludeBundleIdArray(bundleIds);
        }

        public void setExcludeAdSourceIdArrayForPlacementID(string placementID, string adSourceIds) 
        {
            Debug.Log("Unity:ATSDKAPIClient::setExcludeAdSourceIdArrayForPlacementID()");
            ATManager.setExcludeAdSourceIdArrayForPlacementID(placementID, adSourceIds);
        }
        
        public void setSDKArea(int area)
        {
            Debug.Log("Unity:ATSDKAPIClient::setSDKArea()");
            ATManager.setSDKArea(area);
        }
        
        public void getArea(ATGetAreaListener listener)
        {
            Debug.Log("Unity:ATSDKAPIClient::getArea()");
            ATSDKAPIClient.areaListener = listener;
            ATManager.getArea(GetAreaInfo);
        }
        
        public void setWXStatus(bool install)
        {
            Debug.Log("Unity:ATSDKAPIClient::setWXStatus()");
            ATManager.setWXStatus(install);
        }
        
        public void setLocation(double longitude, double latitude)
        {
            Debug.Log("Unity:ATSDKAPIClient::setLocation()");
            ATManager.setLocation(longitude, latitude);
        }
	}
}
