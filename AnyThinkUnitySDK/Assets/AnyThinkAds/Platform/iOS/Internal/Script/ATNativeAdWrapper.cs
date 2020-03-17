using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using AOT;
using AnyThinkAds.ThirdParty.MiniJSON;
using AnyThinkAds.iOS;

public class ATNativeAdWrapper:ATAdWrapper {
    static private Dictionary<string, ATNativeAdClient> clients;
    static private string CMessageReceiverClass = "ATNativeAdWrapper";

    static public void InvokeCallback(string callback, Dictionary<string, object> msgDict) {
        Debug.Log("Unity: ATNativeAdWrapper::InvokeCallback()");
    	if (callback.Equals("OnNativeAdLoaded")) {
    		OnNativeAdLoaded((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnNativeAdLoadingFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = msgDict["error"] as Dictionary<string, object>;
    		if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnNativeAdLoadingFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnNaitveAdShow")) {
    		OnNaitveAdShow((string)msgDict["placement_id"], "");
    	} else if (callback.Equals("OnNativeAdClick")) {
    		OnNativeAdClick((string)msgDict["placement_id"], "");
    	} else if (callback.Equals("OnNativeAdVideoStart")) {
    		OnNativeAdVideoStart((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnNativeAdVideoEnd")) {
    		OnNativeAdVideoEnd((string)msgDict["placement_id"]);
        } else if (callback.Equals("OnNativeAdCloseButtonClick")) {
            OnNativeAdCloseButtonClick((string)msgDict["placement_id"], "");
        }
    }

    //Public method(s)
    static public void setClientForPlacementID(string placementID, ATNativeAdClient client) {
        if (clients == null) clients = new Dictionary<string, ATNativeAdClient>();
        if (clients.ContainsKey(placementID)) clients.Remove(placementID);
        clients.Add(placementID, client);
    }

    static public void loadNativeAd(string placementID, string customData) {
    	Debug.Log("Unity: ATNativeAdWrapper::loadNativeAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "loadNativeAdWithPlacementID:customDataJSONString:callback:", new object[]{placementID, customData != null ? customData : ""}, true);
    }

    static public bool isNativeAdReady(string placementID) {
        Debug.Log("Unity: ATNativeAdWrapper::isNativeAdReady(" + placementID + ")");
    	return ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "isNativeAdReadyForPlacementID:", new object[]{placementID});
    }

    static public void showNativeAd(string placementID, string metrics) {
	    Debug.Log("Unity: ATNativeAdWrapper::showNativeAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "showNativeAdWithPlacementID:metricsJSONString:", new object[]{placementID, metrics});
    }

    static public void removeNativeAdView(string placementID) {
        Debug.Log("Unity: ATNativeAdWrapper::removeNativeAdView(" + placementID + ")");
        ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "removeNativeAdViewWithPlacementID:", new object[]{placementID});
    }

    static public void clearCache() {
        Debug.Log("Unity: ATNativeAdWrapper::clearCache()");
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "clearCache", null);
    }

	//Callbacks
	static private void OnNativeAdLoaded(string placementID) {
		Debug.Log("Unity: ATNativeAdWrapper::OnNativeAdLoaded(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onNativeAdLoaded(placementID);
	}

	static private void OnNativeAdLoadingFailure(string placementID, Dictionary<string, object> errorDict) {
		Debug.Log("Unity: ATNativeAdWrapper::OnNativeAdLoadingFailure()");
        if (clients[placementID] != null) clients[placementID].onNativeAdLoadFail(placementID, (string)errorDict["code"], (string)errorDict["message"]);
	}

    static private void OnNaitveAdShow(string placementID, string callbackJson) {
        if (clients[placementID] != null) clients[placementID].onAdImpressed(placementID, callbackJson);
		Debug.Log("Unity: ATNativeAdWrapper::OnNaitveAdShow(" + placementID + ")");
	}

    static private void OnNativeAdClick(string placementID, string callbackJson) {
        if (clients[placementID] != null) clients[placementID].onAdClicked(placementID, callbackJson);
		Debug.Log("Unity: ATNativeAdWrapper::OnNativeAdClick(" + placementID + ")");
	}

	static private void OnNativeAdVideoStart(string placementID) {
        if (clients[placementID] != null) clients[placementID].onAdVideoStart(placementID);
		Debug.Log("Unity: ATNativeAdWrapper::OnNativeAdVideoStart(" + placementID + ")");
	}

	static private void OnNativeAdVideoEnd(string placementID) {
        if (clients[placementID] != null) clients[placementID].onAdVideoEnd(placementID);
		Debug.Log("Unity: ATNativeAdWrapper::OnNativeAdVideoEnd(" + placementID + ")");
	}

    static private void OnNativeAdCloseButtonClick(string placementID, string callbackJson)
    {
        if (clients[placementID] != null) clients[placementID].onAdCloseButtonClicked(placementID, callbackJson);
        Debug.Log("Unity: ATNativeAdWrapper::OnNativeAdCloseButtonClick(" + placementID + ")");
    }
}
