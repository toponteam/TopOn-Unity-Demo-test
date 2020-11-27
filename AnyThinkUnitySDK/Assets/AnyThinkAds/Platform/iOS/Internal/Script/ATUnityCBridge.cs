using System.Runtime.InteropServices;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AOT;
using AnyThinkAds.ThirdParty.MiniJSON;
using System;

public class ATUnityCBridge {
	public delegate void CCallBack(string wrapperClass, string msg);
	
    #if UNITY_IOS || UNITY_IPHONE
	[DllImport("__Internal")]
    extern static bool message_from_unity(string msg, CCallBack callback);

    [DllImport("__Internal")]
    extern static int get_message_for_unity(string msg, CCallBack callback);

    [DllImport("__Internal")]
    extern static string get_string_message_for_unity(string msg, CCallBack callback);
    #endif

    [MonoPInvokeCallback(typeof(CCallBack))]
    static public void MessageFromC(string wrapperClass, string msg) {
        Debug.Log("Unity: ATUnityCBridge::MessageFromC(" + wrapperClass + "," + msg + ")");
        Dictionary<string, object> msgDict = Json.Deserialize(msg) as Dictionary<string, object>;
        if (wrapperClass.Equals("ATRewardedVideoWrapper")) {
            Debug.Log("Unity: ATUnityCBridge::MessageFromC(), hit rv");
            ATRewardedVideoWrapper.InvokeCallback((string)msgDict["callback"], (Dictionary<string, object>)msgDict["msg"]);
        } else if (wrapperClass.Equals("ATNativeAdWrapper")) {
            ATNativeAdWrapper.InvokeCallback((string)msgDict["callback"], (Dictionary<string, object>)msgDict["msg"]);
        } else if (wrapperClass.Equals("ATInterstitialAdWrapper")) {
            ATInterstitialAdWrapper.InvokeCallback((string)msgDict["callback"], (Dictionary<string, object>)msgDict["msg"]);
        } else if (wrapperClass.Equals("ATBannerAdWrapper")) {
            ATBannerAdWrapper.InvokeCallback((string)msgDict["callback"], (Dictionary<string, object>)msgDict["msg"]);
        } else if (wrapperClass.Equals("ATNativeBannerAdWrapper")) {
            ATNativeBannerAdWrapper.InvokeCallback((string)msgDict["callback"], (Dictionary<string, object>)msgDict["msg"]);
        }
    }

    static public bool SendMessageToC(string className, string selector, object[] arguments) {
        return SendMessageToC(className, selector, arguments, false);
    }

    static public int GetMessageFromC(string className, string selector, object[] arguments) {
        Debug.Log("Unity: ATUnityCBridge::GetMessageFromC()");
        Dictionary<string, object> msgDict = new Dictionary<string, object>();
        msgDict.Add("class", className);
        msgDict.Add("selector", selector);
        msgDict.Add("arguments", arguments);
        #if UNITY_IOS || UNITY_IPHONE
        return get_message_for_unity(Json.Serialize(msgDict), null);
        #else
        return 0;
        #endif
    }

    static public string GetStringMessageFromC(string className, string selector, object[] arguments) {
        Debug.Log("Unity: ATUnityCBridge::GetStringMessageFromC()");
        Dictionary<string, object> msgDict = new Dictionary<string, object>();
        msgDict.Add("class", className);
        msgDict.Add("selector", selector);
        msgDict.Add("arguments", arguments);
        #if UNITY_IOS || UNITY_IPHONE
        return get_string_message_for_unity(Json.Serialize(msgDict), null);
        #else 
        return "";
        #endif
    }

    static public bool SendMessageToC(string className, string selector, object[] arguments, bool carryCallback) {
        Debug.Log("Unity: ATUnityCBridge::SendMessageToC()");
        Dictionary<string, object> msgDict = new Dictionary<string, object>();
    	msgDict.Add("class", className);
    	msgDict.Add("selector", selector);
    	msgDict.Add("arguments", arguments);
        CCallBack callback = null;
        if (carryCallback) callback = MessageFromC;
        #if UNITY_IOS || UNITY_IPHONE
        return message_from_unity(Json.Serialize(msgDict), callback);
        #else
        return false;
        #endif
    }

#if UNITY_IOS || UNITY_IPHONE
    [DllImport("__Internal")]
    extern static bool message_from_unity(string msg, Func<string, int> callback);
#endif
    static public void SendMessageToCWithCallBack(string className, string selector, object[] arguments, Func<string, int> callback)
    {
        Debug.Log("Unity: ATUnityCBridge::SendMessageToCWithCallBack()");
        Dictionary<string, object> msgDict = new Dictionary<string, object>();
        msgDict.Add("class", className);
        msgDict.Add("selector", selector);
        msgDict.Add("arguments", arguments);
#if UNITY_IOS || UNITY_IPHONE
        message_from_unity(Json.Serialize(msgDict), callback);
#endif
    }
}
