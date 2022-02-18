package com.ofm.unitybridge;

import android.util.Log;

/**
 * Copyright (C) 2018 uparpu Science and Technology Co., Ltd.
 *
 * @version V1.2
 * @Author ：Created by zhoushubin on 2018/8/1.
 * @Email: zhoushubin@salmonads.com
 */
public class MsgTools {
    private static final String TAG = UnityPluginUtils.TAG;
    static boolean isDebug = true;

    public static void pirntMsg(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

}
