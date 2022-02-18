package com.anythink.unitybridge;

import android.util.Log;


public class MsgTools {
    private static final String TAG = UnityPluginUtils.TAG;
    public static boolean isDebug = true;

    public static void printMsg(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void printMsg(String preLog, String msg) {
        if (isDebug) {
            Log.e(TAG, preLog + msg);
        }
    }

}
