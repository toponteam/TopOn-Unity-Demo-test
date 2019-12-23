package com.anythink.unitybridge.sdkinit;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.core.api.ATSDK;
import com.anythink.core.api.ATSDKInitListener;
import com.anythink.network.adcolony.AdColonyATConst;
import com.anythink.network.admob.AdmobATConst;
import com.anythink.network.applovin.ApplovinATConst;
import com.anythink.network.chartboost.ChartboostATConst;
import com.anythink.network.facebook.FacebookATConst;
import com.anythink.network.flurry.FlurryATConst;
import com.anythink.network.inmobi.InmobiATConst;
import com.anythink.network.ironsource.IronsourceATConst;
import com.anythink.network.mintegral.MintegralATConst;
import com.anythink.network.mopub.MopubATConst;
import com.anythink.network.tapjoy.TapjoyATConst;
import com.anythink.network.unityads.UnityAdsATConst;
import com.anythink.network.vungle.VungleATConst;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.imgutil.SPUtil;

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

    public void initAppliction(final String appid, final String appkey) {

        MsgTools.pirntMsg("initAppliction--> appid:" + appid);
        if (mActivity == null) {
            MsgTools.pirntMsg("initAppliction--> sActivity == null");
            if (mSDKInitListener != null) {
                mSDKInitListener.initSDKError(appid, "activity can not be null ");
            }
            return;
        }

        if (SPUtil.getBoolean(mActivity, mActivity.getPackageName(), SP_KEY_FIRST_RUN, true)) {
            MsgTools.pirntMsg("initAppliction--> first run");
            //首次启动
            if(ATSDK.isEUTraffic(mActivity)) {
                MsgTools.pirntMsg("initAppliction--> EUTraffic");
                //欧盟地区
                if(ATSDK.NONPERSONALIZED == ATSDK.getGDPRDataLevel(mActivity)) {
                    MsgTools.pirntMsg("initAppliction--> showGdprAuth");
                    ATSDK.showGdprAuth(mActivity);

                    //保存 是否首次启动flag为false
                    SPUtil.putBoolean(mActivity, mActivity.getPackageName(), SP_KEY_FIRST_RUN, false);
                }
            }
        }

        ATSDK.init(mActivity, appid, appkey, new ATSDKInitListener() {
            @Override
            public void onSuccess() {
                MsgTools.pirntMsg("init--> done :" + appid);
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
        ATSDK.setNetworkLogDebug(isOpen);
    }

    public void setChannel(final String channel) {
        ATSDK.setChannel(channel);
    }

    public void initCustomMap(final String jsonMap) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(jsonMap)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonMap);
                Iterator iterator = jsonObject.keys();
                String key;
                while (iterator.hasNext()) {
                    key = (String) iterator.next();
                    map.put(key, (String) jsonObject.get(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ATSDK.initCustomMap(map);
    }


    public void setGDPRLevel(int level) {
        MsgTools.pirntMsg("setGDPRLevel--> level:" + level);


        ATSDK.setGDPRUploadDataLevel(mActivity, level);
    }

    public void showGDPRAuth() {
        MsgTools.pirntMsg("showGDPRAuth ");
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ATSDK.showGdprAuth(mActivity);
            }
        });
    }

    public void addNetworkGDPRInfo(int networkType, String mapJson) {
        MsgTools.pirntMsg("addNetworkGDPRInfo networkType[" + networkType + "] mapjson [" + mapJson + "]");
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject _jsonObject = new JSONObject(mapJson);
            Iterator _iterator = _jsonObject.keys();
            String key;
            while (_iterator.hasNext()) {
                key = (String) _iterator.next();
                map.put(key, _jsonObject.get(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ATSDK.addNetworkGDPRInfo(mActivity, networkType, changeKey(networkType, map));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private Map<String, Object> changeKey(int networkType, Map<String, Object> map) {
        Map<String, Object> localMap = new HashMap<>();
        switch (networkType) {
            case FacebookATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_FACEBOOK .....");
                break;
            case AdmobATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_ADMOB .....");

                if (map.containsKey("ConsentStaus")) {
                    // true 同意| false 不同意
                    localMap.put(AdmobATConst.LOCATION_MAP_KEY_GDPR, Boolean.parseBoolean((String) map.get("ConsentStaus")));
                }


                break;
            case InmobiATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_INMOBI .....");


                if (map.containsKey("GDPRScope")) {
                    //是否GDPR地区
                    localMap.put(InmobiATConst.LOCATION_MAP_KEY_GDPR_SCOPE, map.get("GDPRScope"));//1|0
                }


                if (map.containsKey("GDPRAvailable")) {
                    //是否同意GDPR
                    localMap.put(InmobiATConst.LOCATION_MAP_KEY_GDPR, Boolean.parseBoolean((String) map.get("GDPRAvailable")));//true | false
                }


                break;
            case FlurryATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_FLURRY .....");


                if (map.containsKey("ConsentString")) {
                    //是否GDPR地区
                    localMap.put(FlurryATConst.LOCATION_MAP_KEY_GDPR_IABSTR, map.containsKey("ConsentString"));
                }
                if (map.containsKey("GDPRScope")) {
                    localMap.put(FlurryATConst.LOCATION_MAP_KEY_GDPR_isGdprScope, Boolean.parseBoolean((String) map.get("GDPRScope")));
                }


                break;
            case ApplovinATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_APPLOVIN .....");


                if (map.containsKey("HasUserConstent")) {
                    localMap.put(ApplovinATConst.LOCATION_MAP_KEY_GDPR, Boolean.parseBoolean((String) map.get("HasUserConstent")));
                }
                break;
            case MintegralATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_MINTEGRAL .....");
                if (map.containsKey("GDPRAvailable")) {
                    //是否同意GDPR协议
                    localMap.put(MintegralATConst.LOCATION_MAP_KEY_GDPR, map.get("GDPRAvailable"));
                }
                break;
            case MopubATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_MOPUB .....");


                if (map.containsKey("Consent")) {
                    localMap.put(MopubATConst.LOCATION_MAP_KEY_GDPR, Boolean.parseBoolean((String) map.get("Consent")));
                }
                //是否同意GDPR


                break;
            case ChartboostATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_CHARTBOOST .....");

                if (map.containsKey("restrictData")) {
                    localMap.put(ChartboostATConst.LOCATION_MAP_KEY_RESTRICTDATACONTROL, Boolean.parseBoolean((String) map.get("restrictData")));
                }
                break;
            case TapjoyATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_TAPJOY .....");


                if (map.containsKey("userConsent")) {
                    //是否同意GDPR
                    localMap.put("constent_user_data", map.get("userConsent"));//1|0
                }


                if (map.containsKey("subjectGDPR")) {

                    localMap.put("subject_to_gdpr", Boolean.parseBoolean((String) map.get("subjectGDPR")));//true | false
                }


                break;
            case IronsourceATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_IRONSOURCE .....");
                if (map.containsKey("Consent")) {
                    localMap.put(IronsourceATConst.LOCATION_MAP_KEY_CONSENT, Boolean.parseBoolean((String) map.get("Consent")));

                }

                break;
            case UnityAdsATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_UNITYADS .....");

                if (map.containsKey("GDPRConsent")) {
                    localMap.put(UnityAdsATConst.LOCATION_MAP_KEY_GDPR_CONSENT, Boolean.parseBoolean((String) map.get("GDPRConsent")));

                }

                break;
            case VungleATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_VUNGLE .....");

//                if(map.containsKey("GDPRConsent")) {
//                    localMap.put(UnityAdConst.LOCATION_MAP_KEY_CONSENT, Boolean.parseBoolean(map.get("GDPRConsent")));
//                }

                break;
            case AdColonyATConst.NETWORK_FIRM_ID:
                MsgTools.pirntMsg("NETWORK_ADCOLONY .....");
                if (map.containsKey("GDPRConstentString")) {
                    localMap.put(AdColonyATConst.LOCATION_MAP_KEY_GDPRCONTENT, map.get("GDPRConstentString"));
                }
                if (map.containsKey("GDPRRuired")) {
                    localMap.put(AdColonyATConst.LOCATION_MAP_KEY_GDPRREQUEST, Boolean.parseBoolean((String) map.get("GDPRRequired")));
                }


                break;
            default:
                MsgTools.pirntMsg("NETWORK_UNKOWN .....");
        }

        return localMap;
    }
}
