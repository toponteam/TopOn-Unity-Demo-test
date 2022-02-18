package com.ofm.unitybridge.sdkinit;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.core.api.ATSDK;
import com.anythink.core.api.ATSDKInitListener;
import com.anythink.core.api.NetTrafficeCallback;
import com.ofm.core.api.OfmSDK;
import com.ofm.core.api.OfmSDKInitListener;
import com.ofm.unitybridge.MsgTools;
import com.ofm.unitybridge.UnityPluginUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 * 测试方法二
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/2.
 * @Email: zhoushubin@salmonads.com
 */
public class SDKInitHelper {
    SDKInitListener mSDKInitListener;
    Activity mActivity;
    public static final String TAG = UnityPluginUtils.TAG;
    private static final String SP_KEY_FIRST_RUN = "SP_KEY_FIRST_RUN";

    public SDKInitHelper(SDKInitListener pSDKInitListener) {
        if (pSDKInitListener == null) {
            MsgTools.pirntMsg("pSDKInitListener == null ..");
        }
        mSDKInitListener = pSDKInitListener;
        mActivity = UnityPluginUtils.getActivity("SDKInitHelper");
    }

    public void initAppliction(final String appid, final String appkey, final String defaultConfig) {

        MsgTools.pirntMsg("initAppliction--> appid:" + appid);
        if (mActivity == null) {
            MsgTools.pirntMsg("initAppliction--> sActivity == null");
            if (mSDKInitListener != null) {
                mSDKInitListener.initSDKError(appid, "activity can not be null ");
            }
            return;
        }

        OfmSDK.initSDK(mActivity, appid, appkey, defaultConfig, new OfmSDKInitListener() {
            @Override
            public void onInitSuccess() {
                MsgTools.pirntMsg("init--> done :" + appid);
                if (mSDKInitListener != null) {
                    mSDKInitListener.initSDKSuccess(appid);
                }
            }

            @Override
            public void onInitFail(String s) {
                if (mSDKInitListener != null) {
                    mSDKInitListener.initSDKError(appid, "init faild [" + s + "] ");
                }
            }
        });

    }

    public void setDebugLogOpen(boolean isOpen) {
        MsgTools.pirntMsg("setDebugLogOpen--> :" + isOpen);
        OfmSDK.setNetworkLogDebug(isOpen);
    }

//    public void setChannel(final String channel) {
//        MsgTools.pirntMsg("setChannel--> :" + channel);
//        ATSDK.setChannel(channel);
//    }
//
//    public void setSubChannel(final String subChannel) {
//        MsgTools.pirntMsg("setSubChannel--> :" + subChannel);
//        ATSDK.setSubChannel(subChannel);
//    }

    public void initCustomMap(final String jsonMap) {
        MsgTools.pirntMsg("initCustomMap--> :" + jsonMap != null ? jsonMap : "");
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(jsonMap)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonMap);
                Iterator iterator = jsonObject.keys();
                String key;
                while (iterator.hasNext()) {
                    key = (String) iterator.next();
                    map.put(key, jsonObject.opt(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        OfmSDK.initCustomMap(map);
    }

//    public void initPlacementCustomMap(String placementId, String jsonMap) {
//        MsgTools.pirntMsg("initPlacementCustomMap-->" + placementId + ":" + jsonMap != null ? jsonMap : "");
//        Map<String, Object> map = new HashMap<>();
//        if (!TextUtils.isEmpty(jsonMap)) {
//            try {
//                JSONObject jsonObject = new JSONObject(jsonMap);
//                Iterator iterator = jsonObject.keys();
//                String key;
//                while (iterator.hasNext()) {
//                    key = (String) iterator.next();
//                    map.put(key, jsonObject.opt(key));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        ATSDK.initPlacementCustomMap(placementId, map);
//    }

//    public int getGDPRLevel() {
//        MsgTools.pirntMsg("getGDPRLevel");
//        return ATSDK.getGDPRDataLevel(mActivity);
//    }
//
//    public boolean isEUTraffic() {
//        MsgTools.pirntMsg("isEUTraffic");
//        return ATSDK.isEUTraffic(mActivity);
//    }
//
//    public void setGDPRLevel(int level) {
//        MsgTools.pirntMsg("setGDPRLevel--> level:" + level);
//
//
//        ATSDK.setGDPRUploadDataLevel(mActivity, level);
//    }
//
//    public void checkIsEuTraffic(final SDKEUCallbackListener callbackListener) {
//        MsgTools.pirntMsg("checkIsEuTraffic");
//        ATSDK.checkIsEuTraffic(mActivity, new NetTrafficeCallback() {
//            @Override
//            public void onResultCallback(boolean b) {
//                MsgTools.pirntMsg("check EU:" + b);
//                if (callbackListener != null) {
//                    callbackListener.onResultCallback(b);
//                }
//            }
//
//            @Override
//            public void onErrorCallback(String s) {
//                MsgTools.pirntMsg("check EU error:" + s);
//                if (callbackListener != null) {
//                    callbackListener.onErrorCallback(s);
//                }
//            }
//        });
//    }
//
//    public void showGDPRAuth() {
//        MsgTools.pirntMsg("showGDPRAuth ");
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ATSDK.showGdprAuth(mActivity);
//            }
//        });
//    }

    @Deprecated
    public void addNetworkGDPRInfo(int networkType, String mapJson) {
//        MsgTools.pirntMsg("addNetworkGDPRInfo networkType[" + networkType + "] mapjson [" + mapJson + "]");
//        Map<String, Object> map = new HashMap<>();
//        try {
//            JSONObject _jsonObject = new JSONObject(mapJson);
//            Iterator _iterator = _jsonObject.keys();
//            String key;
//            while (_iterator.hasNext()) {
//                key = (String) _iterator.next();
//                map.put(key, _jsonObject.get(key));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            ATSDK.addNetworkGDPRInfo(mActivity, networkType, changeKey(networkType, map));
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
    }

//    public void deniedUploadDeviceInfo(String arrayString) {
//        MsgTools.pirntMsg("deniedUploadDeviceInfo " + arrayString);
//        if (!TextUtils.isEmpty(arrayString)) {
//            String[] split = arrayString.split(",");
//            ATSDK.deniedUploadDeviceInfo(split);
//        }
//    }
//

}
