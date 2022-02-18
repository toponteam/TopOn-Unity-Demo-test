using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class ATManager {
	private static bool SDKStarted;
	public static bool StartSDK(string appID, string appKey) {
		Debug.Log("Unity: ATManager::StartSDK(" + appID + "," + appKey + ")");
		if (!SDKStarted) {
			Debug.Log("Has not been started before, will starting SDK");
			SDKStarted = true;
			return ATUnityCBridge.SendMessageToC("ATUnityManager", "startSDKWithAppID:appKey:", new object[]{appID, appKey});
		} else {
			Debug.Log("SDK has been started already, ignore this call");
            return false;
		}
	}

	public static void setPurchaseFlag() {
		ATUnityCBridge.SendMessageToC("ATUnityManager", "setPurchaseFlag", null);
	}

	public static void clearPurchaseFlag() {
		ATUnityCBridge.SendMessageToC("ATUnityManager", "clearPurchaseFlag", null);
	}

	public static bool purchaseFlag() {
		return ATUnityCBridge.SendMessageToC("ATUnityManager", "clearPurchaseFlag", null);
	}

	public static bool isEUTraffic() {
		return ATUnityCBridge.SendMessageToC("ATUnityManager", "inDataProtectionArea", null);
	}

    public static void getUserLocation(Func<string, int> callback)
    {
        Debug.Log("Unity:ATManager::getUserLocation()");
        ATUnityCBridge.SendMessageToCWithCallBack("ATUnityManager", "getUserLocation:", new object[] { }, callback);
    }

	public static void ShowGDPRAuthDialog() {
		ATUnityCBridge.SendMessageToC("ATUnityManager", "presentDataConsentDialog", null);
	}

	public static int GetDataConsent() {
		return ATUnityCBridge.GetMessageFromC("ATUnityManager", "getDataConsent", null);
	}

	public static void SetDataConsent(int consent) {
		ATUnityCBridge.SendMessageToC("ATUnityManager", "setDataConsent:", new object[]{consent});
	}

	public static void SetNetworkGDPRInfo(int network, string mapJson) {
		ATUnityCBridge.SendMessageToC("ATUnityManager", "setDataConsent:network:", new object[]{mapJson, network});
	}

    public static void setChannel(string channel)
    {
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setChannel:", new object[] {channel});
    }

    public static void setSubChannel(string subchannel)
    {
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setSubChannel:", new object[] {subchannel});
    }

    public static void setCustomMap(string jsonMap)
    {
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setCustomData:", new object[] { jsonMap });
    }

    public static void setCustomDataForPlacementID(string customData, string placementID)
    {
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setCustomData:forPlacementID:", new object[] {customData, placementID});
    }

    public static void setLogDebug(bool isDebug)
    {
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setDebugLog:", new object[] { isDebug ? "true" : "false" });
    }

    public static void deniedUploadDeviceInfo(string deniedInfo)
    {
        ATUnityCBridge.SendMessageToC("ATUnityManager", "deniedUploadDeviceInfo:", new object[] {deniedInfo});
    }

    public static void setExcludeBundleIdArray(string bundleIds)
    {
        Debug.Log("Unity:ATManager::setExcludeBundleIdArray()");
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setExcludeBundleIdArray:", new object[] {bundleIds});
    }

    public static void setExcludeAdSourceIdArrayForPlacementID(string placementID, string adSourceIds) 
    {
        Debug.Log("Unity:ATManager::setExcludeAdSourceIdArrayForPlacementID()");
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setExludePlacementid:unitIDArray:", new object[] {placementID, adSourceIds});
    }
    
    public static void setSDKArea(int area)
    {
        Debug.Log("Unity:ATManager::setSDKArea()");
        ATUnityCBridge.SendMessageToC("ATUnityManager", "setSDKArea:", new object[] {area});
    }
    
    public static void getArea(Func<string, int> callback)
    {
        Debug.Log("Unity:ATManager::getArea()");
        ATUnityCBridge.SendMessageToCWithCallBack("ATUnityManager", "getArea:", new object[] { }, callback);
    }
    
    public static void setWXStatus(bool install)
    {
        Debug.Log("Unity:ATManager::setWXStatus()");
    	ATUnityCBridge.SendMessageToC("ATUnityManager", "setWXStatus:", new object[] {install});
    }
    
    public static void setLocation(double longitude, double latitude)
    {
        Debug.Log("Unity:ATManager::setLocation()");
    	ATUnityCBridge.SendMessageToC("ATUnityManager", "setLocationLongitude:dimension:", new object[] {longitude, latitude});
    }
}
