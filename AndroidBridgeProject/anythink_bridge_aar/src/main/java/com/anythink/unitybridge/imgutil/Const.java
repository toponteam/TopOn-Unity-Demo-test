package com.anythink.unitybridge.imgutil;

import android.os.Environment;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/8.
 * @Email: zhoushubin@salmonads.com
 */
public class Const {
    public static final boolean DEBUG = true;
    public static final String SPU_NAME = "imgspu";
    public static final String SCENARIO = "Scenario";

    public static class Interstital {
        public static final String UseRewardedVideoAsInterstitial = "UseRewardedVideoAsInterstitial";
        public static final String UseRewardedVideoAsInterstitialYes = "1";
        public static final String UseRewardedVideoAsInterstitialNo = "0";
    }

    public static class FOLDER {
        /**
         * sdcard
         */
        public static final String SDCARD_FOLDER = Environment.getExternalStorageDirectory().toString();

        public static final String ROOT_FOLDER = SDCARD_FOLDER + "/.anythink/";

        public static final String DOWNLOAD_FOLDER = ROOT_FOLDER + "download/";

        ;
    }

    public static void fillMapFromJsonObject(Map<String, Object> localExtra, JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        String key;
        while (keys.hasNext()) {
            key = keys.next();
            Object value = jsonObject.opt(key);
            localExtra.put(key, value);
        }
    }
}
