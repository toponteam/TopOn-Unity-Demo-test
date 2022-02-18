package com.anythink.unitybridge.nativead;


public interface NativeListener {

    public void onAdImpressed(String unitId, String callbackJson);

    public void onAdClicked(String unitId, String callbackJson);

    public void onAdVideoStart(String unitId);

    public void onAdVideoEnd(String unitId);

    public void onAdVideoProgress(String unitId, int progress);


    public void onNativeAdLoaded(String unitId);

    public void onNativeAdLoadFail(String unitId, String code, String msg);

    public void onAdCloseButtonClicked(String unitId, String callbackJson);

    //    -----------------AdSource listener
    public void onAdSourceBiddingAttempt(String unitId, String callbackJson);

    public void onAdSourceBiddingFilled(String unitId, String callbackJson);

    public void onAdSourceBiddingFail(String unitId, String callbackJson, String code, String error);

    public void onAdSourceAttemp(String unitId, String callbackJson);

    public void onAdSourceLoadFilled(String unitId, String callbackJson);

    public void onAdSourceLoadFail(String unitId, String callbackJson, String code, String error);

}
