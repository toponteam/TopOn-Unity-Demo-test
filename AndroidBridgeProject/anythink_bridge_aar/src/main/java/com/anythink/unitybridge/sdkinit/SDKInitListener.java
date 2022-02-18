package com.anythink.unitybridge.sdkinit;


public interface SDKInitListener {
    public void initSDKSuccess(String appid);
    public void initSDKError(String appid, String msg);
}
