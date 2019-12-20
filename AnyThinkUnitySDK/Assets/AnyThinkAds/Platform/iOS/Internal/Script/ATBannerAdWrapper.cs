using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using AOT;
using AnyThinkAds.ThirdParty.MiniJSON;
using AnyThinkAds.iOS;
using AnyThinkAds.Api;

public class ATBannerAdWrapper:ATAdWrapper {
	static private Dictionary<string, ATBannerAdClient> clients;
	static private string CMessaageReceiverClass = "ATBannerAdWrapper";

	static public void InvokeCallback(string callback, Dictionary<string, object> msgDict) {
        Debug.Log("Unity: ATBannerAdWrapper::InvokeCallback()");
    	if (callback.Equals("OnBannerAdLoad")) {
    		OnBannerAdLoad((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnBannerAdLoadFail")) {
            Dictionary<string, object> errorMsg = msgDict["error"] as Dictionary<string, object>;
    		OnBannerAdLoadFail((string)msgDict["placement_id"], (string)errorMsg["code"], (string)errorMsg["reason"]);
    	} else if (callback.Equals("OnBannerAdImpress")) {
    		OnBannerAdImpress((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnBannerAdClick")) {
    		OnBannerAdClick((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnBannerAdAutoRefresh")) {
    		OnBannerAdAutoRefresh((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnBannerAdAutoRefreshFail")) {
            Dictionary<string, object> errorMsg = msgDict["error"] as Dictionary<string, object>;
    		OnBannerAdAutoRefreshFail((string)msgDict["placement_id"], (string)errorMsg["code"], (string)errorMsg["reason"]);
    	} else if (callback.Equals("OnBannerAdClose")) {
    		OnBannerAdClose((string)msgDict["placement_id"]);
    	}
    }

    static public void loadBannerAd(string placementID, string customData) {
    	Debug.Log("Unity: ATBannerAdWrapper::loadBannerAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "loadBannerAdWithPlacementID:customDataJSONString:callback:", new object[]{placementID, customData != null ? customData : ""}, true);
    }

    static public void hideBannerAd(string placementID) {
        Debug.Log("Unity: ATBannerAdWrapper::showBannerAd(" + placementID + ")");
        ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "hideBannerAdWithPlacementID:", new object[]{placementID}, false);
    }

    static public void showBannerAd(string placementID) {
        Debug.Log("Unity: ATBannerAdWrapper::showBannerAd(" + placementID + ")");
        ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showBannerAdWithPlacementID:", new object[]{placementID}, false);
    }

    static public void showBannerAd(string placementID, string position)
    {
        Debug.Log("Unity: ATBannerAdWrapper::showBannerAd(" + placementID + "," + position + ")");
        Dictionary<string, object> rectDict = new Dictionary<string, object> { { "position", position } };
        ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showBannerAdWithPlacementID:rect:", new object[] { placementID, Json.Serialize(rectDict) }, false);
    }

    static public void showBannerAd(string placementID, ATRect rect) {
    	Debug.Log("Unity: ATBannerAdWrapper::showBannerAd(" + placementID + ")");
        Dictionary<string, object> rectDict = new Dictionary<string, object>{ {"x", rect.x},  {"y", rect.y}, {"width", rect.width}, {"height", rect.height}, {"uses_pixel", rect.usesPixel}};
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showBannerAdWithPlacementID:rect:", new object[]{placementID, Json.Serialize(rectDict)}, false);
    }

    static public void cleanBannerAd(string placementID) {
    	Debug.Log("Unity: ATBannerAdWrapper::cleanBannerAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "removeBannerAdWithPlacementID:", new object[]{placementID}, false);
    }

    static public void clearCache() {
        Debug.Log("Unity: ATBannerAdWrapper::clearCache()");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "clearCache", null);
    }

    static public void setClientForPlacementID(string placementID, ATBannerAdClient client) {
        if (clients == null) clients = new Dictionary<string, ATBannerAdClient>();
        if (clients.ContainsKey(placementID)) clients.Remove(placementID);
        clients.Add(placementID, client);
	}

	static private void OnBannerAdLoad(string placementID) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdLoad()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdLoad(placementID);
    }
    
    static private void OnBannerAdLoadFail(string placementID, string code, string message) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdLoadFail()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdLoadFail(placementID, code, message);
    }
    
    static private void OnBannerAdImpress(string placementID) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdImpress()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdImpress(placementID);
    }
    
    static private void OnBannerAdClick(string placementID) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClick()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdClick(placementID);
    }
    
    static private void OnBannerAdAutoRefresh(string placementID) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefresh()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdAutoRefresh(placementID);
    }
    
    static private void OnBannerAdAutoRefreshFail(string placementID, string code, string message) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefreshFail()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdAutoRefreshFail(placementID, code, message);
    }

    static private void OnBannerAdClose(string placementID) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClose()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdClose(placementID);
    }
}
