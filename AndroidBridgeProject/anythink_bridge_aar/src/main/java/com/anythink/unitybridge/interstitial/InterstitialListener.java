package com.anythink.unitybridge.interstitial;


public interface InterstitialListener {

    public void onInterstitialAdLoaded(String unitId);

    public void onInterstitialAdLoadFail(String unitId, String code, String msg);

    public void onInterstitialAdClicked(String unitId, String callbackJson);

    public void onInterstitialAdShow(String unitId, String callbackJson);

    public void onInterstitialAdClose(String unitId, String callbackJson);

    public void onInterstitialAdVideoStart(String unitId, String callbackJson);

    public void onInterstitialAdVideoEnd(String unitId, String callbackJson);

    public void onInterstitialAdVideoError(String unitId, String code, String msg);

    //    -----------------AdSource listener
    public void onAdSourceBiddingAttempt(String unitId, String callbackJson);

    public void onAdSourceBiddingFilled(String unitId, String callbackJson);

    public void onAdSourceBiddingFail(String unitId, String callbackJson, String code, String error);

    public void onAdSourceAttemp(String unitId, String callbackJson);

    public void onAdSourceLoadFilled(String unitId, String callbackJson);

    public void onAdSourceLoadFail(String unitId, String callbackJson, String code, String error);

}
