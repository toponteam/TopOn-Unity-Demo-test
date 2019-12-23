package com.anythink.unitybridge.banner;

public interface BannerListener {

    public void onBannerLoaded(String unitId);

    public void onBannerFailed(String unitId, String code, String msg);

    public void onBannerClicked(String unitId);

    public void onBannerShow(String unitId);

    public void onBannerClose(String unitId);

    public void onBannerAutoRefreshed(String unitId);

    public void onBannerAutoRefreshFail(String unitId, String code, String msg);

}
