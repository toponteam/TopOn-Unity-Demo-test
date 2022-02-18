using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using AOT;
using AnyThinkAds.ThirdParty.LitJson;
using AnyThinkAds.iOS;
using AnyThinkAds.Api;

public class ATNativeBannerAdWrapper : ATAdWrapper {
	static private Dictionary<string, ATNativeBannerAdClient> clients;
    static private string CMessageReceiverClass = "ATNativeBannerAdWrapper";

    static public new void InvokeCallback(JsonData jsonData) {
        Debug.Log("Unity: ATNativeBannerAdWrapper::InvokeCallback()");
        string extraJson = "";
        string callback = (string)jsonData["callback"];
        Dictionary<string, object> msgDict = JsonMapper.ToObject<Dictionary<string, object>>(jsonData["msg"].ToJson());
        JsonData msgJsonData = jsonData["msg"];
        IDictionary idic = (System.Collections.IDictionary)msgJsonData;

        if (idic.Contains("extra")) { 
            JsonData extraJsonDate = msgJsonData["extra"];
            if (extraJsonDate != null) {
                extraJson = msgJsonData["extra"].ToJson();
            }
        }
        
        if (callback.Equals("OnNativeBannerAdLoaded")) {
    		OnNativeBannerAdLoaded((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnNativeBannerAdLoadingFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnNativeBannerAdLoadingFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnNaitveBannerAdShow")) {
    		OnNaitveBannerAdShow((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnNativeBannerAdClick")) {
    		OnNativeBannerAdClick((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnNativeBannerAdAutorefresh")) {
    		OnNativeBannerAdAutorefresh((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnNativeBannerAdAutorefreshFailed")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnNativeBannerAdAutorefreshFailed((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnNativeBannerAdCloseButtonClicked")) {
    		OnNativeBannerAdCloseButtonClicked((string)msgDict["placement_id"]);
    	} else if (callback.Equals("PauseAudio")) {
            Debug.Log("c# : callback, PauseAudio");
            PauseAudio();
        } else if (callback.Equals("ResumeAudio")) {
            Debug.Log("c# : callback, ResumeAudio");
            ResumeAudio();
        }
    }

    static public void PauseAudio() {
        Debug.Log("ATNativeBannerAdWrapper::PauseAudio()");
    }

    static public void ResumeAudio() {
        Debug.Log("ATNativeBannerAdWrapper::ResumeAudio()");
    }

    static public void setClientForPlacementID(string placementID, ATNativeBannerAdClient client) {
    	Debug.Log("ATNativeBannerAdWrapper::setClientForPlacementID()");
        if (clients == null) clients = new Dictionary<string, ATNativeBannerAdClient>();
        if (clients.ContainsKey(placementID)) clients.Remove(placementID);
        clients.Add(placementID, client);
	}

    static public void loadAd(string placementID, string customData) {
		Debug.Log("ATNativeBannerAdWrapper::loadAd()");
		ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "loadNativeBannerAdWithPlacementID:customDataJSONString:callback:", new object[]{placementID, customData != null ? customData : ""}, true);
	}
	
	static public bool adReady(string placementID) {
		Debug.Log("ATNativeBannerAdWrapper::adReady()");
		return ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "isNativeBannerAdReadyForPlacementID:", new object[]{placementID});
	}

    static public void showAd(string placementID, ATRect rect, Dictionary<string, string> pairs) {
		Debug.Log("ATNativeBannerAdWrapper::showAd()");
		Dictionary<string, object> rectDict = new Dictionary<string, object>{ {"x", rect.x},  {"y", rect.y}, {"width", rect.width}, {"height", rect.height} };
    	ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "showNativeBannerAdWithPlacementID:rect:extra:", new object[]{placementID, JsonMapper.ToJson(rectDict), JsonMapper.ToJson(pairs != null ? pairs : new Dictionary<string, string>())}, false);
    }

    static public void removeAd(string placementID) {
		Debug.Log("ATNativeBannerAdWrapper::removeAd()");
		ATUnityCBridge.SendMessageToC(CMessageReceiverClass, "removeNativeBannerAdWithPlacementID:", new object[]{placementID}, false);
    }

    //Callbacks
    static private void OnNativeBannerAdLoaded(string placementID) {
		Debug.Log("Unity: ATNativeBannerAdWrapper::OnNativeBannerAdLoaded(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onAdLoaded(placementID);
	}

	static private void OnNativeBannerAdLoadingFailure(string placementID, Dictionary<string, object> errorDict) {
		Debug.Log("Unity: ATNativeBannerAdWrapper::OnNativeBannerAdLoadingFailure(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onAdLoadFail(placementID, (string)errorDict["code"], (string)errorDict["message"]);
	}

	static private void OnNaitveBannerAdShow(string placementID, string callbackJson) {
		Debug.Log("Unity: ATNativeBannerAdWrapper::OnNaitveBannerAdShow(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onAdImpressed(placementID, callbackJson);
	}

    static private void OnNativeBannerAdClick(string placementID, string callbackJson) {
		Debug.Log("Unity: ATNativeBannerAdWrapper::OnNativeBannerAdClick(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onAdClicked(placementID, callbackJson);
	}

    static private void OnNativeBannerAdAutorefresh(string placementID, string callbackJson) {
		Debug.Log("Unity: ATNativeBannerAdWrapper::OnNativeBannerAdAutorefresh(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onAdAutoRefresh(placementID, callbackJson);
	}

	static private void OnNativeBannerAdAutorefreshFailed(string placementID, Dictionary<string, object> errorDict) {
		Debug.Log("Unity: ATNativeBannerAdWrapper::OnNativeBannerAdAutorefreshFailed(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onAdAutoRefreshFailure(placementID, (string)errorDict["code"], (string)errorDict["message"]);
	}

	static private void OnNativeBannerAdCloseButtonClicked(string placementID) {
		Debug.Log("Unity: ATNativeBannerAdWrapper::OnNativeBannerAdCloseButtonClicked(" + placementID + ")");
        if (clients[placementID] != null) clients[placementID].onAdCloseButtonClicked(placementID);
	}
}
