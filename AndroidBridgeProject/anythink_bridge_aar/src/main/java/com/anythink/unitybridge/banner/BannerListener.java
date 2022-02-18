package com.anythink.unitybridge.banner;

public interface BannerListener {

    public void onBannerLoaded(String unitId);

    public void onBannerFailed(String unitId, String code, String msg);

    public void onBannerClicked(String unitId, String callbackJson);

    public void onBannerShow(String unitId, String callbackJson);

    public void onBannerClose(String unitId, String callbackJson);

    public void onBannerAutoRefreshed(String unitId, String callbackJson);

    public void onBannerAutoRefreshFail(String unitId, String code, String msg);

    //    -----------------AdSource listener
    public void onAdSourceBiddingAttempt(String unitId, String callbackJson);

    public void onAdSourceBiddingFilled(String unitId, String callbackJson);

    public void onAdSourceBiddingFail(String unitId, String callbackJson, String code, String error);

    public void onAdSourceAttemp(String unitId, String callbackJson);

    public void onAdSourceLoadFilled(String unitId, String callbackJson);

    public void onAdSourceLoadFail(String unitId, String callbackJson, String code, String error);

}
