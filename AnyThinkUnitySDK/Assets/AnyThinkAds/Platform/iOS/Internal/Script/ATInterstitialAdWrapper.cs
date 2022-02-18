using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using AOT;
using AnyThinkAds.ThirdParty.LitJson;
using AnyThinkAds.iOS;

public class ATInterstitialAdWrapper:ATAdWrapper {
	static private Dictionary<string, ATInterstitialAdClient> clients;
	static private string CMessaageReceiverClass = "ATInterstitialAdWrapper";

	static public new void InvokeCallback(JsonData jsonData) {
        Debug.Log("Unity: ATInterstitialAdWrapper::InvokeCallback()");
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
        
        if (callback.Equals("OnInterstitialAdLoaded")) {
    		OnInterstitialAdLoaded((string)msgDict["placement_id"]);
    	} else if (callback.Equals("OnInterstitialAdLoadFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnInterstitialAdLoadFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnInterstitialAdVideoPlayFailure")) {
    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
    		Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
            if (errorMsg.ContainsKey("code")) { errorDict.Add("code", errorMsg["code"]); }
            if (errorMsg.ContainsKey("reason")) { errorDict.Add("message", errorMsg["reason"]); }
    		OnInterstitialAdVideoPlayFailure((string)msgDict["placement_id"], errorDict);
    	} else if (callback.Equals("OnInterstitialAdVideoPlayStart")) {
    		OnInterstitialAdVideoPlayStart((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnInterstitialAdVideoPlayEnd")) {
    		OnInterstitialAdVideoPlayEnd((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnInterstitialAdShow")) {
    		OnInterstitialAdShow((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnInterstitialAdClick")) {
    		OnInterstitialAdClick((string)msgDict["placement_id"], extraJson);
    	} else if (callback.Equals("OnInterstitialAdClose")) {
            OnInterstitialAdClose((string)msgDict["placement_id"], extraJson);
        } else if (callback.Equals("OnInterstitialAdFailedToShow")) {
            OnInterstitialAdFailedToShow((string)msgDict["placement_id"]);
        }else if (callback.Equals("startLoadingADSource")) {
            StartLoadingADSource((string)msgDict["placement_id"], extraJson);
        }else if (callback.Equals("finishLoadingADSource")) {
            FinishLoadingADSource((string)msgDict["placement_id"], extraJson);
        }else if (callback.Equals("failToLoadADSource")) {

    		Dictionary<string, object> errorDict = new Dictionary<string, object>();
            Dictionary<string, object> errorMsg = JsonMapper.ToObject<Dictionary<string, object>>(msgJsonData["error"].ToJson());
    		if (errorMsg["code"] != null) { errorDict.Add("code", errorMsg["code"]); }
    		if (errorMsg["reason"] != null) { errorDict.Add("message", errorMsg["reason"]); }
    		FailToLoadADSource((string)msgDict["placement_id"],extraJson, errorDict);  

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

    static public string checkAdStatus(string placementID) {
        Debug.Log("Unity: ATInterstitialAdWrapper::checkAdStatus(" + placementID + ")");
        return ATUnityCBridge.GetStringMessageFromC(CMessaageReceiverClass, "checkAdStatus:", new object[]{placementID});
    }

    static public string getValidAdCaches(string placementID)
    {
        Debug.Log("Unity: ATInterstitialAdWrapper::checkAdStatus(" + placementID + ")");
        return ATUnityCBridge.GetStringMessageFromC(CMessaageReceiverClass, "getValidAdCaches:", new object[] { placementID });
    }
  
    static public void entryScenarioWithPlacementID(string placementID, string scenarioID) 
    {
    	Debug.Log("Unity: ATInterstitialAdWrapper::entryScenarioWithPlacementID(" + placementID + scenarioID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "entryScenarioWithPlacementID:scenarioID:", new object[]{placementID, scenarioID});
    }

    //Callbacks
    static private void OnInterstitialAdLoaded(string placementID) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdLoaded()");
        if (clients[placementID] != null) clients[placementID].OnInterstitialAdLoaded(placementID);
    }

    static private void OnInterstitialAdLoadFailure(string placementID, Dictionary<string, object> errorDict) {
    	Debug.Log("Unity: ATInterstitialAdWrapper::OnInterstitialAdLoadFailure()");
        Debug.Log("placementID = " + placementID + "errorDict = " + JsonMapper.ToJson(errorDict));
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
    // ad source callback
    static public void StartLoadingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATInterstitialAdWrapper::StartLoadingADSource()");
        if (clients[placementID] != null) clients[placementID].startLoadingADSource(placementID, callbackJson);
    }    
    static public void FinishLoadingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATInterstitialAdWrapper::FinishLoadingADSource()");
        if (clients[placementID] != null) clients[placementID].finishLoadingADSource(placementID, callbackJson);
    }

    static public void FailToLoadADSource(string placementID,string callbackJson, Dictionary<string, object> errorDict) 
    {
    	Debug.Log("Unity: ATInterstitialAdWrapper::FailToLoadADSource()");

        Debug.Log("placementID = " + placementID + "errorDict = " + JsonMapper.ToJson(errorDict));
        if (clients[placementID] != null) clients[placementID].failToLoadADSource(placementID,callbackJson,(string)errorDict["code"], (string)errorDict["message"]);
    }

    static public void StartBiddingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATInterstitialAdWrapper::StartBiddingADSource()");
        if (clients[placementID] != null) clients[placementID].startBiddingADSource(placementID, callbackJson);
    }    
    static public void FinishBiddingADSource(string placementID, string callbackJson)
    {
        Debug.Log("Unity: ATInterstitialAdWrapper::FinishBiddingADSource()");
        if (clients[placementID] != null) clients[placementID].finishBiddingADSource(placementID, callbackJson);
    }

    static public void FailBiddingADSource(string placementID, string callbackJson,Dictionary<string, object> errorDict) 
    {
    	Debug.Log("Unity: ATInterstitialAdWrapper::FailBiddingADSource()");

        Debug.Log("placementID = " + placementID + "errorDict = " + JsonMapper.ToJson(errorDict));
        if (clients[placementID] != null) clients[placementID].failBiddingADSource(placementID,callbackJson,(string)errorDict["code"], (string)errorDict["message"]);
    }

 // Auto
     static public void addAutoLoadAdPlacementID(string placementID) 
     {
    	Debug.Log("Unity: ATInterstitialAdWrapper::addAutoLoadAdPlacementID(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "addAutoLoadAdPlacementID:callback:", new object[]{placementID}, true);
    }

    static public void removeAutoLoadAdPlacementID(string placementID) 
    {
    	Debug.Log("Unity: ATInterstitialAdWrapper::removeAutoLoadAdPlacementID(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "removeAutoLoadAdPlacementID:", new object[]{placementID});
    }
    static public bool autoLoadInterstitialAdReadyForPlacementID(string placementID) 
    {
        Debug.Log("Unity: ATInterstitialAdWrapper::autoLoadInterstitialAdReadyForPlacementID(" + placementID + ")");
        
    	return ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "autoLoadInterstitialAdReadyForPlacementID:", new object[]{placementID});
    }    
    static public string getAutoValidAdCaches(string placementID)
    {
        Debug.Log("Unity: ATInterstitialAdWrapper::getAutoValidAdCaches");
        return ATUnityCBridge.GetStringMessageFromC(CMessaageReceiverClass, "getAutoValidAdCaches:", new object[]{placementID});
    }

    static public string checkAutoAdStatus(string placementID) {
        Debug.Log("Unity: ATInterstitialAdWrapper::checkAutoAdStatus(" + placementID + ")");
        return ATUnityCBridge.GetStringMessageFromC(CMessaageReceiverClass, "checkAutoAdStatus:", new object[]{placementID});
    }

    static public void setAutoLocalExtra(string placementID, string customData) 
    {

    	Debug.Log("Unity: ATInterstitialAdWrapper::setAutoLocalExtra(" + placementID + customData + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "setAutoLocalExtra:customDataJSONString:", new object[] {placementID, customData != null ? customData : ""});
    }

    static public void entryAutoAdScenarioWithPlacementID(string placementID, string scenarioID) 
    {
    	Debug.Log("Unity: ATInterstitialAdWrapper::entryAutoAdScenarioWithPlacementID(" + placementID + scenarioID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "entryAutoAdScenarioWithPlacementID:scenarioID:", new object[]{placementID, scenarioID});
    }

    static public void showAutoInterstitialAd(string placementID, string mapJson) {
	    Debug.Log("Unity: ATInterstitialAdWrapper::showAutoInterstitialAd(" + placementID + ")");
    	ATUnityCBridge.SendMessageToC(CMessaageReceiverClass, "showAutoInterstitialAd:extraJsonString:", new object[]{placementID, mapJson});
    }



}



