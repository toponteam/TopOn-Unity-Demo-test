package com.anythink.unitybridge.videoad;


public interface VideoListener {
    public void onRewardedVideoAdLoaded(String unitId);

    public void onRewardedVideoAdFailed(String unitId, String code, String error);

    public void onRewardedVideoAdPlayStart(String unitId, String callbackJson);

    public void onRewardedVideoAdPlayEnd(String unitId, String callbackJson);

    public void onRewardedVideoAdPlayFailed(String unitId, String code, String error);

    public void onRewardedVideoAdClosed(String unitId, boolean isRewarded, String callbackJson);

    public void onRewardedVideoAdPlayClicked(String unitId, String callbackJson);

    public void onReward(String unitId, String callbackJson);

//    -----------------Again

    public void onRewardedVideoAdAgainPlayStart(String unitId, String callbackJson);

    public void onRewardedVideoAdAgainPlayEnd(String unitId, String callbackJson);

    public void onRewardedVideoAdAgainPlayFailed(String unitId, String code, String error);

    public void onRewardedVideoAdAgainPlayClicked(String unitId, String callbackJson);

    public void onAgainReward(String unitId, String callbackJson);

    //    -----------------AdSource listener
    public void onAdSourceBiddingAttempt(String unitId, String callbackJson);

    public void onAdSourceBiddingFilled(String unitId, String callbackJson);

    public void onAdSourceBiddingFail(String unitId, String callbackJson, String code, String error);

    public void onAdSourceAttemp(String unitId, String callbackJson);

    public void onAdSourceLoadFilled(String unitId, String callbackJson);

    public void onAdSourceLoadFail(String unitId, String callbackJson, String code, String error);
}
