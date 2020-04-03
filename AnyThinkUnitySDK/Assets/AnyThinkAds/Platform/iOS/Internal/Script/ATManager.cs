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
}
