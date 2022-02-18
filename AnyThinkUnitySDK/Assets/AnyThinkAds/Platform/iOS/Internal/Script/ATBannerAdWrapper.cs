using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using AOT;
using AnyThinkAds.ThirdParty.LitJson;
using AnyThinkAds.iOS;
using AnyThinkAds.Api;

public class ATBannerAdWrapper:ATAdWrapper {
	static private Dictionary<string, ATBannerAdClient> clients;
	static private string CMessaageReceiverClass = "ATBannerAdWrapper";

	static public new void InvokeCallback(JsonData jsonData) {
        Debug.Log("Unity: ATBannerAdWrapper::InvokeCallback()");
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

        if (callback.Equals("OnBannerAdLoad")) {
    		OnBannerAdLoad((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnBannerAdLoadFail")) {
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		OnBannerAdLoadFail((string)msgDict["placement_id"], (string)errorMsg["code"], (string)errorMsg["reason"]);
    	} else if (callback.Equals("OnBannerAdImpress")) {
    		OnBannerAdImpress((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnBannerAdClick")) {
    		OnBannerAdClick((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnBannerAdAutoRefresh")) {
    		OnBannerAdAutoRefresh((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnBannerAdAutoRefreshFail")) {
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		OnBannerAdAutoRefreshFail((string)msgDict["placement_id"], (string)errorMsg["code"], (string)errorMsg["reason"]);
    	} else if (callback.Equals("OnBannerAdClose")) {
    		OnBannerAdClose((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnBannerAdCloseButtonTapped")) {
            OnBannerAdCloseButtonTapped((string)msgDict["placement_id"], extraJson);
        }else if (callback.Equals("startLoadingADSource")) {
            StartLoadingADSource((string)msgDict["placement_id"], extraJson);
        }else if (callback.Equals("finishLoadingADSource")) {
            FinishLoadingADSource((string)msgDict["placement_id"], extraJson);
        }else if (callback.Equals("failToLoadADSource")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		if (errorMsg["code"] != null) { errorDict.Add("code", errorMsg["code"]); }
    		if (errorMsg["reason"] != null) { errorDict.Add("message", errorMsg["reason"]); }
    		FailToLoadADSource((string)msgDict["placement_id"], extraJson,errorDict);            
        }else if (callback.Equals("startBiddingADSource")) {
            StartBiddingADSource((string)msgDict["placement_id"], extraJson);
           
        }else if (callback.Equals("finishBiddingADSource")) {
            FinishBiddingADSource((string)msgDict["placement_id"], extraJson);
  
        }else if (callback.Equals("failBiddingADSource")) {
        	Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		if (errorMsg["code"] != null) { errorDict.Add("code", errorMsg["code"]); }
    		if (errorMsg["reason"] != null) { errorDict.Add("message", errorMsg["reason"]); }
    		FailBiddingADSource((string)msgDict["placement_id"],extraJson, errorDict);
        }

    }

    static public void loadBannerAd(string placementID, string customData) {
    	Debug.Log("Unity: ATBannerAdWrapper::loadBannerAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "loadBannerAdWithPlacementID:customDataJSONString:callback:", new object[]{placementID, customData != null ? customData : ""}, true);
    }

    static public string checkAdStatus(string placementID) {
        Debug.Log("Unity: ATBannerAdWrapper::checkAdStatus(" + placementID + ")");
        return ATUnityCBridge.GetStringMessageFromC(CMessaageReceiverClass, "checkAdStatus:", new object[]{placementID});
    }

    static public string getValidAdCaches(string placementID)
    {
        Debug.Log("Unity: ATBannerAdWrapper::getValidAdCaches(" + placementID + ")");
        return ATUnityCBridge.GetStringMessageFromC(CMessaageReceiverClass, "getValidAdCaches:", new object[] { placementID });
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
        ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showBannerAdWithPlacementID:rect:extraJsonString:", new object[] { placementID, JsonMapper.ToJson(rectDict), null}, false);
    }

    static public void showBannerAd(string placementID, string position, string mapJson)
    {
        Debug.Log("Unity: ATBannerAdWrapper::showBannerAd(" + placementID + "," + position + ")");
        Dictionary<string, object> rectDict = new Dictionary<string, object> { { "position", position } };
        ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showBannerAdWithPlacementID:rect:extraJsonString:", new object[] { placementID, JsonMapper.ToJson(rectDict), mapJson}, false);
    }

    static public void showBannerAd(string placementID, ATRect rect) {
        Debug.Log("Unity: ATBannerAdWrapper::showBannerAd(" + placementID + ")");
        Dictionary<string, object> rectDict = new Dictionary<string, object>{ {"x", rect.x},  {"y", rect.y}, {"width", rect.width}, {"height", rect.height}, {"uses_pixel", rect.usesPixel}};
        ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showBannerAdWithPlacementID:rect:extraJsonString:", new object[]{placementID, JsonMapper.ToJson(rectDict), null}, false);
    }

    static public void showBannerAd(string placementID, ATRect rect, string mapJson) {
    	Debug.Log("Unity: ATBannerAdWrapper::showBannerAd(" + placementID + ")");
        Dictionary<string, object> rectDict = new Dictionary<string, object>{ {"x", rect.x},  {"y", rect.y}, {"width", rect.width}, {"height", rect.height}, {"uses_pixel", rect.usesPixel}};
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showBannerAdWithPlacementID:rect:extraJsonString:", new object[]{placementID, JsonMapper.ToJson(rectDict), mapJson}, false);
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
    
    static private void OnBannerAdImpress(string placementID, string callbackJson) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdImpress()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdImpress(placementID, callbackJson);
    }
    
    static private void OnBannerAdClick(string placementID, string callbackJson) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClick()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdClick(placementID, callbackJson);
    }
    
    static private void OnBannerAdAutoRefresh(string placementID, string callbackJson) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefresh()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdAutoRefresh(placementID, callbackJson);
    }
    
    static private void OnBannerAdAutoRefreshFail(string placementID, string code, string message) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdAutoRefreshFail()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdAutoRefreshFail(placementID, code, message);
    }

    static private void OnBannerAdCloseButtonTapped(string placementID, string callbackJson) {
        Debug.Log("Unity: ATBannerAdWrapper::onAdCloseButtonTapped()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdCloseButtonTapped(placementID, callbackJson);
    }

    static private void OnBannerAdClose(string placementID) {
		Debug.Log("Unity: ATBannerAdWrapper::OnBannerAdClose()");
        if (clients[placementID] != null) clients[placementID].OnBannerAdClose(placementID);
    }

    // ad source callback
    static public void StartLoadingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATBannerAdWrapper::StartLoadingADSource()");
        if (clients[placementID] != null) clients[placementID].startLoadingADSource(placementID, callbackJson);
    }    
    static public void FinishLoadingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATBannerAdWrapper::FinishLoadingADSource()");
        if (clients[placementID] != null) clients[placementID].finishLoadingADSource(placementID, callbackJson);
    }

    static public void FailToLoadADSource(string placementID,string callbackJson, Dictionary<string, object> errorDict) 
    {
    	Debug.Log("Unity: ATBannerAdWrapper::FailToLoadADSource()");

        Debug.Log("placementID = " + placementID + "errorDict = " + JsonMapper.ToJson(errorDict));
        if (clients[placementID] != null) clients[placementID].failToLoadADSource(placementID,callbackJson, (string)errorDict["code"], (string)errorDict["message"]);
    }

    static public void StartBiddingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATBannerAdWrapper::StartBiddingADSource()");
        if (clients[placementID] != null) clients[placementID].startBiddingADSource(placementID, callbackJson);
    }    
    static public void FinishBiddingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATBannerAdWrapper::FinishBiddingADSource()");
        if (clients[placementID] != null) clients[placementID].finishBiddingADSource(placementID, callbackJson);
    }

    static public void FailBiddingADSource(string placementID,string callbackJson, Dictionary<string, object> errorDict) 
    {
    	Debug.Log("Unity: ATBannerAdWrapper::FailBiddingADSource()");

        Debug.Log("placementID = " + placementID + "errorDict = " + JsonMapper.ToJson(errorDict));
        if (clients[placementID] != null) clients[placementID].failBiddingADSource(placementID, callbackJson,(string)errorDict["code"], (string)errorDict["message"]);
    }
}
