using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using AOT;
using AnyThinkAds.ThirdParty.MiniJSON;
using AnyThinkAds.iOS;
public class ATRewardedVideoWrapper:ATAdWrapper {
    static private Dictionary<string, ATRewardedVideoAdClient> clients;
	static private string CMessageReceiverClass = "ATRewardedVideoWrapper";

    static public void InvokeCallback(string callback, Dictionary<string, object> msgDict) {
        Debug.Log("Unity: ATRewardedVideoWrapper::InvokeCallback(" + callback + " , " + msgDict + ")");
    	if (callback.Equals("OnRewardedVideoLoaded")) {
    		OnRewardedVideoLoaded((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnRewardedVideoLoadFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = msgDict["error"] as Dictionary<string, object>;
    		if (errorMsg["code"] != null) { errorDict.Add("code", errorMsg["code"]); }
    		if (errorMsg["reason"] != null) { errorDict.Add("message", errorMsg["reason"]); }
    		OnRewardedVideoLoadFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnRewardedVideoPlayFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
    		Dictionary<string, object> errorMsg = msgDict["error"] as Dictionary<string, object>;
            if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnRewardedVideoPlayFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnRewardedVideoPlayStart")) {
    		OnRewardedVideoPlayStart((string)msgDict["placement_id"], "");
    	} else if (callback.Equals("OnRewardedVideoPlayEnd")) {
    		OnRewardedVideoPlayEnd((string)msgDict["placement_id"], "");
    	} else if (callback.Equals("OnRewardedVideoClick")) {
    		OnRewardedVideoClick((string)msgDict["placement_id"], "");
    	} else if (callback.Equals("OnRewardedVideoClose")) {
    		OnRewardedVideoClose((string)msgDict["placement_id"], (bool)msgDict["rewarded"], "");
        } else if (callback.Equals("OnRewardedVideoReward")) {
            OnRewardedVideoReward((string)msgDict["placement_id"], "");
        }
    }

    //Public method(s)
    static public void setClientForPlacementID(string placementID, ATRewardedVideoAdClient client) {
        if (clients == null) clients = new Dictionary<string, ATRewardedVideoAdClient>();
        if (clients.ContainsKey(placementID)) clients.Remove(placementID);
        clients.Add(placementID, client);
    }

    static public void setExtra(Dictionary<string, object> extra) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::setExtra()");
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "setExtra:", new object[]{extra});
    }

    static public void loadRewardedVideo(string placementID, string customData) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::loadRewardedVideo(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "loadRewardedVideoWithPlacementID:customDataJSONString:callback:", new object[]{placementID, customData != null ? customData : ""}, true);
    }

    static public bool isRewardedVideoReady(string placementID) {
        Debug.Log("Unity: ATRewardedVideoWrapper::isRewardedVideoReady(" + placementID + ")");
    	return ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "rewardedVideoReadyForPlacementID:", new object[]{placementID});
    }

    static public void showRewardedVideo(string placementID, string mapJson) {
	    Debug.Log("Unity: ATRewardedVideoWrapper::showRewardedVideo(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "showRewardedVideoWithPlacementID:extraJsonString:", new object[]{placementID, mapJson});
    }

    static public void clearCache() {
        Debug.Log("Unity: ATRewardedVideoWrapper::clearCache()");
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "clearCache", null);
    }

    //Callbacks
    static public void OnRewardedVideoLoaded(string placementID) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoLoaded()");
        if (clients[placementID] != null) clients[placementID].onRewardedVideoAdLoaded(placementID);
    }

    static public void OnRewardedVideoLoadFailure(string placementID, Dictionary<string, object> errorDict) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoLoadFailure()");
        Debug.Log("placementID = " + placementID + "errorDict = " + Json.Serialize(errorDict));
        if (clients[placementID] != null) clients[placementID].onRewardedVideoAdFailed(placementID, (string)errorDict["code"], (string)errorDict["message"]);
    }

     static public void OnRewardedVideoPlayFailure(string placementID, Dictionary<string, object> errorDict) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoPlayFailure()");
        if (clients[placementID] != null) clients[placementID].onRewardedVideoAdPlayFailed(placementID, (string)errorDict["code"], (string)errorDict["message"]);

    }

    static public void OnRewardedVideoPlayStart(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoPlayStart()");
        if (clients[placementID] != null) clients[placementID].onRewardedVideoAdPlayStart(placementID, callbackJson);
    }

    static public void OnRewardedVideoPlayEnd(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoPlayEnd()");
        if (clients[placementID] != null) clients[placementID].onRewardedVideoAdPlayEnd(placementID, callbackJson);
    }

    static public void OnRewardedVideoClick(string placementID, string callbackJson) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoClick()");
        if (clients[placementID] != null) clients[placementID].onRewardedVideoAdPlayClicked(placementID, callbackJson);
    }

    static public void OnRewardedVideoClose(string placementID, bool rewarded, string callbackJson) {
    	Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoClose()");
        if (clients[placementID] != null) clients[placementID].onRewardedVideoAdClosed(placementID, rewarded, callbackJson);
    }
    static public void OnRewardedVideoReward(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATRewardedVideoWrapper::OnRewardedVideoReward()");
        if (clients[placementID] != null) clients[placementID].onRewardedVideoReward(placementID, callbackJson);
    }

}


