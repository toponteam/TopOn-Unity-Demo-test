using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using AOT;
using AnyThinkAds.ThirdParty.MiniJSON;
using AnyThinkAds.iOS;

public class ATInterstitialAdWrapper:ATAdWrapper {
	static private Dictionary<string, ATInterstitialAdClient> clients;
	static private string CMessaageReceiverClass = "ATInterstitialAdWrapper";

	static public void InvokeCallback(string callback, Dictionary<string, object> msgDict) {
        Debug.Log("Unity: ATInterstitialAdWrapper::InvokeCallback()");
        Dictionary<string, object> extra = new Dictionary<string, object>();
        if (msgDict.ContainsKey("extra")) { extra = msgDict["extra"] as Dictionary<string, object>; }
        if (callback.Equals("OnInterstitialAdLoaded")) {
    		OnInterstitialAdLoaded((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnInterstitialAdLoadFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = msgDict["error"] as Dictionary<string, object>;
    		if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnInterstitialAdLoadFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnInterstitialAdVideoPlayFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
    		Dictionary<string, object> errorMsg = msgDict["error"] as Dictionary<string, object>;
            if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnInterstitialAdVideoPlayFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnInterstitialAdVideoPlayStart")) {
    		OnInterstitialAdVideoPlayStart((string)msgDict["placement_id"], Json.Serialize(extra));
    	} else if (callback.Equals("OnInterstitialAdVideoPlayEnd")) {
    		OnInterstitialAdVideoPlayEnd((string)msgDict["placement_id"], Json.Serialize(extra));
    	} else if (callback.Equals("OnInterstitialAdShow")) {
    		OnInterstitialAdShow((string)msgDict["placement_id"], Json.Serialize(extra));
    	} else if (callback.Equals("OnInterstitialAdClick")) {
    		OnInterstitialAdClick((string)msgDict["placement_id"], Json.Serialize(extra));
    	} else if (callback.Equals("OnInterstitialAdClose")) {
    		OnInterstitialAdClose((string)msgDict["placement_id"], Json.Serialize(extra));
    	}
    }

	static public void setClientForPlacementID(string placementID, ATInterstitialAdClient client) {
        if (clients == null) clients = new Dictionary<string, ATInterstitialAdClient>();
        if (clients.ContainsKey(placementID)) clients.Remove(placementID);
        clients.Add(placementID, client);
	}

	static public void loadInterstitialAd(string placementID, string customData) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::loadInterstitialAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "loadInterstitialAdWithPlacementID:customDataJSONString:callback:", new object[]{placementID, customData != null ? customData : ""}, true);
    }

    static public bool hasInterstitialAdReady(string placementID) {
        Debug.Log("Unity: ATInterstitialAdWrapper::isInterstitialAdReady(" + placementID + ")");
    	return ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "interstitialAdReadyForPlacementID:", new object[]{placementID});
    }

    static public void showInterstitialAd(string placementID, string mapJson) {
	    Debug.Log("Unity: ATInterstitialAdWrapper::showInterstitialAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showInterstitialAdWithPlacementID:extraJsonString:", new object[]{placementID, mapJson});
    }

    static public void clearCache(string placementID) {
        Debug.Log("Unity: ATInterstitialAdWrapper::clearCache()");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "clearCache", null);
    }

    //Callbacks
    static private void OnInterstitialAdLoaded(string placementID) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdLoaded()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdLoaded(placementID);
    }

    static private void OnInterstitialAdLoadFailure(string placementID, Dictionary<string, object> errorDict) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdLoadFailure()");
        Debug.Log("placementID = " + placementID + "errorDict = " + Json.Serialize(errorDict));
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdLoadFailure(placementID, (string)errorDict["code"], (string)errorDict["message"]);
    }

     static private void OnInterstitialAdVideoPlayFailure(string placementID, Dictionary<string, object> errorDict) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdVideoPlayFailure()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdVideoPlayFailure(placementID, (string)errorDict["code"], (string)errorDict["message"]);
    }

    static private void OnInterstitialAdVideoPlayStart(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdPlayStart()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdVideoPlayStart(placementID, callbackJson);
    }

    static private void OnInterstitialAdVideoPlayEnd(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdVideoPlayEnd()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdVideoPlayEnd(placementID, callbackJson);
    }

    static private void OnInterstitialAdShow(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdShow()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdShow(placementID, callbackJson);
    }

    static private void OnInterstitialAdFailedToShow(string placementID) {
        Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdFailedToShow()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdFailedToShow(placementID);
    }

    static private void OnInterstitialAdClick(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdClick()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdClick(placementID, callbackJson);
    }

    static private void OnInterstitialAdClose(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdClose()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdClose(placementID, callbackJson);
    }
}



