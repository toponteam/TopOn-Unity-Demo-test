package com.ofm.unitybridge;

import android.app.Activity;

import com.unity3d.player.UnityPlayer;

/**
 * Copyright (C) 2018 uparpu Science and Technology Co., Ltd.
 *
 * @version V1.2
 */
public class UnityPluginUtils {
    public static final String TAG = "OFM_unity3d_bridge";

    public static Activity getActivity(String msg) {
        return UnityPlayer.currentActivity;
    }

    public static void runOnUiThread(Runnable runnable) {
        try {
            UnityPlayer.currentActivity.runOnUiThread(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
