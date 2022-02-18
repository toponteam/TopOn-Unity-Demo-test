package com.ofm.unitybridge.videoad;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/3.
 * @Email: zhoushubin@salmonads.com
 */
public interface VideoListener {
    public void onRewardedVideoAdLoaded(String unitId); //广告加载成功

    public void onRewardedVideoAdFailed(String unitId, String code, String error);//广告加载失败

    public void onRewardedVideoAdPlayStart(String unitId, String callbackJson);//开始播放

    public void onRewardedVideoAdPlayEnd(String unitId, String callbackJson);//结束播放

    public void onRewardedVideoAdPlayFailed(String unitId, String code, String error);//播放失败

    public void onRewardedVideoAdClosed(String unitId, boolean isRewarded, String callbackJson);//广告关闭

    public void onRewardedVideoAdPlayClicked(String unitId, String callbackJson);//广告点击

    public void onReward(String unitId, String callbackJson);//广告激励
}
