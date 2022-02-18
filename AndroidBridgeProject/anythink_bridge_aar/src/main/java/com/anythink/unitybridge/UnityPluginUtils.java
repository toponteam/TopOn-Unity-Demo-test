package com.anythink.unitybridge;

import android.app.Activity;

import com.unity3d.player.UnityPlayer;


public class UnityPluginUtils {
    public static final String TAG = "AT_android_unity3d";

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
