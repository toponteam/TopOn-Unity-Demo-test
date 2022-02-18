package com.ofm.unitybridge.utils;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class Const {
    public static final boolean DEBUG = true;
    public static final String SCENARIO = "Scenario";

    public static class Interstital {
        public static final String UseRewardedVideoAsInterstitial = "UseRewardedVideoAsInterstitial";
        public static final String UseRewardedVideoAsInterstitialYes = "1";
        public static final String UseRewardedVideoAsInterstitialNo = "0";
    }

    public static class Native {
        public static final String native_ad_size = "native_ad_size";
    }

    public static void fillMapFromJsonObject(Map<String, Object> localExtra, JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            String key;
            while (keys.hasNext()) {
                key = keys.next();
                Object value = jsonObject.opt(key);
                localExtra.put(key, value);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
