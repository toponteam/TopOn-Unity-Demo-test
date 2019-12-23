package com.anythink.unitybridge.nativead;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/3.
 * @Email: zhoushubin@salmonads.com
 */
public interface NativeListener {

    /**
     * 广告展示回调
     *
     */
    public void onAdImpressed(String unitId);

    /**
     * 广告点击回调
     *
     */
    public void onAdClicked(String unitId);

    /**
     * 广告视频开始回调
     *
     */
    public void onAdVideoStart(String unitId);
    /**
     * 广告视频结束回调
     *

     */
    public void onAdVideoEnd(String unitId);

    /**
     * 广告视频进度回调
     *
     */
    public void onAdVideoProgress(String unitId, int progress);


    /**
     * 广告加载成功
     */
    public void onNativeAdLoaded(String unitId);

    /**
     * 广告加载失败
     */
    public void onNativeAdLoadFail(String unitId, String code, String msg);
}
