package com.anythink.unitybridge.sdkinit;

import android.app.Activity;
import android.location.Location;
import android.text.TextUtils;

import com.anythink.core.api.ATAreaCallback;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.ATSDKInitListener;
import com.anythink.core.api.AreaCode;
import com.anythink.core.api.NetTrafficeCallback;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SDKInitHelper {
    SDKInitListener mSDKInitListener;
    Activity mActivity;
    public static final String TAG = UnityPluginUtils.TAG;
    private static final String SP_KEY_FIRST_RUN = "SP_KEY_FIRST_RUN";

    public SDKInitHelper(SDKInitListener pSDKInitListener) {
        if (pSDKInitListener == null) {
            MsgTools.printMsg("pSDKInitListener == null ..");
        }
        mSDKInitListener = pSDKInitListener;
        mActivity = UnityPluginUtils.getActivity("SDKInitHelper");
    }

    public void initAppliction(final String appid, final String appkey) {

        MsgTools.printMsg("initAppliction--> appid:" + appid);
        if (mActivity == null) {
            MsgTools.printMsg("initAppliction--> sActivity == null");
            if (mSDKInitListener != null) {
                mSDKInitListener.initSDKError(appid, "activity can not be null ");
            }
            return;
        }

        //Deprecated
//        if (SPUtil.getBoolean(mActivity, mActivity.getPackageName(), SP_KEY_FIRST_RUN, true)) {
//            MsgTools.pirntMsg("initAppliction--> first run");
//            //首次启动
//            if (ATSDK.isEUTraffic(mActivity)) {
//                MsgTools.pirntMsg("initAppliction--> EUTraffic");
//                //欧盟地区
//                if (ATSDK.NONPERSONALIZED == ATSDK.getGDPRDataLevel(mActivity)) {
//                    MsgTools.pirntMsg("initAppliction--> showGdprAuth");
//                    ATSDK.showGdprAuth(mActivity);
//
//                    //保存 是否首次启动flag为false
//                    SPUtil.putBoolean(mActivity, mActivity.getPackageName(), SP_KEY_FIRST_RUN, false);
//                }
//            }
//        }

        ATSDK.init(mActivity, appid, appkey, new ATSDKInitListener() {
            @Override
            public void onSuccess() {
                MsgTools.printMsg("init--> done :" + appid);
                if (mSDKInitListener != null) {
                    mSDKInitListener.initSDKSuccess(appid);
                }
            }

            @Override
            public void onFail(String pS) {
                if (mSDKInitListener != null) {
                    mSDKInitListener.initSDKError(appid, "init faild [" + pS + "] ");
                }
            }
        });

    }

    public void setDebugLogOpen(boolean isOpen) {
        MsgTools.printMsg("setDebugLogOpen--> :" + isOpen);
        ATSDK.setNetworkLogDebug(isOpen);

        MsgTools.isDebug = isOpen;
    }

    public void setChannel(final String channel) {
        MsgTools.printMsg("setChannel--> :" + channel);
        ATSDK.setChannel(channel);
    }

    public void setSubChannel(final String subChannel) {
        MsgTools.printMsg("setSubChannel--> :" + subChannel);
        ATSDK.setSubChannel(subChannel);
    }

    public void initCustomMap(final String jsonMap) {
        MsgTools.printMsg("initCustomMap--> :" + jsonMap != null ? jsonMap : "");
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
        ATSDK.initCustomMap(map);
    }

    public void initPlacementCustomMap(String placementId, String jsonMap) {
        MsgTools.printMsg("initPlacementCustomMap-->" + placementId + ":" + jsonMap != null ? jsonMap : "");
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
        ATSDK.initPlacementCustomMap(placementId, map);
    }

    public int getGDPRLevel() {
        MsgTools.printMsg("getGDPRLevel");
        return ATSDK.getGDPRDataLevel(mActivity);
    }

    public boolean isEUTraffic() {
        MsgTools.printMsg("isEUTraffic");
        return ATSDK.isEUTraffic(mActivity);
    }

    public void setGDPRLevel(int level) {
        MsgTools.printMsg("setGDPRLevel--> level:" + level);


        ATSDK.setGDPRUploadDataLevel(mActivity, level);
    }

    public void checkIsEuTraffic(final SDKEUCallbackListener callbackListener) {
        MsgTools.printMsg("checkIsEuTraffic");
        ATSDK.checkIsEuTraffic(mActivity, new NetTrafficeCallback() {
            @Override
            public void onResultCallback(boolean b) {
                MsgTools.printMsg("check EU:" + b);
                if (callbackListener != null) {
                    callbackListener.onResultCallback(b);
                }
            }

            @Override
            public void onErrorCallback(String s) {
                MsgTools.printMsg("check EU error:" + s);
                if (callbackListener != null) {
                    callbackListener.onErrorCallback(s);
                }
            }
        });
    }

    public void showGDPRAuth() {
        MsgTools.printMsg("showGDPRAuth ");
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ATSDK.showGdprAuth(mActivity);
            }
        });
    }

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


    public void deniedUploadDeviceInfo(String arrayString) {
        MsgTools.printMsg("deniedUploadDeviceInfo: " + arrayString);
        if (!TextUtils.isEmpty(arrayString)) {
            try {
                JSONArray jsonArray = new JSONArray(arrayString);

                int length = jsonArray.length();
                if (length > 0) {
                    String[] infos = new String[length];

                    for (int i = 0; i < length; i++) {
                        infos[i] = jsonArray.getString(i);
                    }

                    ATSDK.deniedUploadDeviceInfo(infos);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void setExcludeBundleIdArray(String arrayString) {
        MsgTools.printMsg("setExcludeBundleIdArray: " + arrayString);
        if (!TextUtils.isEmpty(arrayString)) {
            try {
                JSONArray jsonArray = new JSONArray(arrayString);

                int length = jsonArray.length();
                if (length > 0) {
                    List<String> list = new ArrayList<>(length);

                    for (int i = 0; i < length; i++) {
                        list.add(jsonArray.getString(i));
                    }

                    ATSDK.setExcludePackageList(list);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void setExcludeAdSourceIdArrayForPlacementID(String placementID, String arrayString) {
        MsgTools.printMsg("setExcludeAdSourceIdArrayForPlacementID: " + placementID + ", " + arrayString);
        if (!TextUtils.isEmpty(arrayString)) {
            try {
                JSONArray jsonArray = new JSONArray(arrayString);

                int length = jsonArray.length();
                if (length > 0) {
                    List<String> list = new ArrayList<>(length);

                    for (int i = 0; i < length; i++) {
                        list.add(jsonArray.getString(i));
                    }

                    ATSDK.setFilterAdSourceIdList(placementID, list);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void setSDKArea(int area) {
        MsgTools.printMsg("setSDKArea: " + area);

        switch (area) {
            case Const.AREA_GLOBAL:
                ATSDK.setSDKArea(AreaCode.GLOBAL);
                break;
            case Const.AREA_CHINESE_MAINLAND:
                ATSDK.setSDKArea(AreaCode.CHINESE_MAINLAND);
                break;
        }
    }

    public void getArea(final AreaCallbackListener listener) {
        MsgTools.printMsg("getArea");

        ATSDK.getArea(new ATAreaCallback() {
            @Override
            public void onResultCallback(String s) {
                MsgTools.printMsg("getArea:" + s);
                if (listener != null) {
                    listener.onResultCallback(s);
                }
            }

            @Override
            public void onErrorCallback(String s) {
                MsgTools.printMsg("getArea:" + s);
                if (listener != null) {
                    listener.onErrorCallback(s);
                }
            }
        });
    }

    public void setWXStatus(boolean install) {
        MsgTools.printMsg("setWXStatus: " + install);

        ATSDK.setWXStatus(install);
    }

    public void setLocation(double longitude, double latitude) {
        MsgTools.printMsg("setLocation: " + longitude + ", " + latitude);

        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        ATSDK.setLocation(location);
    }


}
