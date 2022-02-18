package com.ofm.unitybridge.banner;

public interface BannerListener {

    public void onBannerLoaded(String unitId);

    public void onBannerFailed(String unitId, String code, String msg);

    public void onBannerClicked(String unitId, String callbackJson);

    public void onBannerShow(String unitId, String callbackJson);

    public void onBannerClose(String unitId, String callbackJson);

    public void onBannerAutoRefreshed(String unitId, String callbackJson);

    public void onBannerAutoRefreshFail(String unitId, String code, String msg);

}
