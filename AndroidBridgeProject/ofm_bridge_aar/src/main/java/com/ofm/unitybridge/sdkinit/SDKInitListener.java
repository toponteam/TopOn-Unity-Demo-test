package com.ofm.unitybridge.sdkinit;

/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/2.
 * @Email: zhoushubin@salmonads.com
 */
public interface SDKInitListener {
    public void initSDKSuccess(String appid);
    public void initSDKError(String appid, String msg);
}
