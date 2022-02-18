package com.ofm.unitybridge.videoad;

import android.app.Activity;
import android.text.TextUtils;

import com.ofm.core.api.OfmAdError;
import com.ofm.core.api.OfmAdInfo;
import com.ofm.rewardvideo.api.OfmRewardVideo;
import com.ofm.rewardvideo.api.OfmRewardVideoListener;
import com.ofm.unitybridge.MsgTools;
import com.ofm.unitybridge.UnityPluginUtils;
import com.ofm.unitybridge.utils.Const;
import com.ofm.unitybridge.utils.TaskManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/3.
 * @Email: zhoushubin@salmonads.com
 */
public class VideoHelper {
    public static final String TAG = UnityPluginUtils.TAG;
    VideoListener mListener;
    Activity mActivity;
    OfmRewardVideo mRewardVideoAd;
    String mPlacmentId;

    boolean isReady = false;
    boolean isReward = false;

    public VideoHelper(VideoListener pListener) {
        MsgTools.pirntMsg("VideoHelper: " + this);
        if (pListener == null) {
            MsgTools.pirntMsg("Listener == null");
        }
        mListener = pListener;
        mActivity = UnityPluginUtils.getActivity("VideoHelper");
    }

    public void initVideo(final String placementId) {
        MsgTools.pirntMsg("initVideo 1>: " + this);

        mRewardVideoAd = new OfmRewardVideo(mActivity, placementId);
        mPlacmentId = placementId;


        MsgTools.pirntMsg("initVideo 2>: " + this);

        mRewardVideoAd.setAdListener(new OfmRewardVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                MsgTools.pirntMsg("onRewardedVideoAdLoaded");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        isReady = true;
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdLoaded(mPlacmentId);
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdFailed(final OfmAdError adError) {
                MsgTools.pirntMsg("onRewardedVideoAdFailed: " + adError.toString());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdFailed(mPlacmentId, adError.getErrorCode(), adError.toString());
                            }
                        } else {
                            MsgTools.pirntMsg("onRewardedVideoAdFailed callnoback>>" + adError.toString());
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayStart(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayStart");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayStart(mPlacmentId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayEnd(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayEnd");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayEnd(mPlacmentId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayFailed(final OfmAdError adError, OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayFailed: " + adError.toString());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayFailed(mPlacmentId, adError.getErrorCode(), adError.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdClosed(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdClosed");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdClosed(mPlacmentId, isReward, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayClicked(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayClicked");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayClicked(mPlacmentId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onReward(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onReward");
                isReward = true;
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onReward(mPlacmentId, adInfo.toString());
                            }
                        }
                    }
                });
            }
        });

        MsgTools.pirntMsg("initVideo 3>: " + this);
    }

    public void setUserData(String userid, String customData) {
//        MsgTools.pirntMsg("setUserDate, >>" + this);
//        if (mRewardVideoAd != null) {
//            mRewardVideoAd.setUserData(userid, customData);
//        } else {
//            MsgTools.pirntMsg("setUserDate error you must call initVideo first " + this);
//            TaskManager.getInstance().run_proxy(new Runnable() {
//                @Override
//                public void run() {
//                    if (mListener != null) {
//                        synchronized (VideoHelper.this) {
//                            mListener.onRewardedVideoAdFailed(mUnitId, "-1", "you must call initVideo first");
//                        }
//                    }
//                }
//            });
//        }
    }

    public void addsetting(String appsetting) {
//        MsgTools.pirntMsg("addsetting: " + this + "appsetting map, >>: " + appsetting);
//        if (TextUtils.isEmpty(appsetting)) {
//            return;
//        }
//        try {
//            JSONObject _jsonObject = new JSONObject(appsetting);
//            Iterator _iterator = _jsonObject.keys();
//            String key, json;
//            int networkType = 0;
//            while (_iterator.hasNext()) {
//                key = (String) _iterator.next();
//                json = _jsonObject.getString(key);
//
//                MsgTools.pirntMsg("addsetting: " + this + "NETWORK_(" + key + ") -->appsetting map, >>: " + json);
//                networkType = Integer.parseInt(key);
//                try {
//                    switch (networkType) {
//                        case AdmobATConst.NETWORK_FIRM_ID:
//
//                            AdmobRewardedVideoSetting _admobATMediationSetting = new AdmobRewardedVideoSetting();
//
//                            mRewardVideoAd.addSetting(AdmobATConst.NETWORK_FIRM_ID, _admobATMediationSetting);
//
//                            break;
//                        case MintegralATConst.NETWORK_FIRM_ID:
//                            MintegralRewardedVideoSetting _mintegralATMediationSetting = new MintegralRewardedVideoSetting();
//
//                            mRewardVideoAd.addSetting(MintegralATConst.NETWORK_FIRM_ID, _mintegralATMediationSetting);
//
//                            break;
//                        case ApplovinATConst.NETWORK_FIRM_ID:
//                            ApplovinRewardedVideoSetting _applovinATMediationSetting = new ApplovinRewardedVideoSetting();
//                            mRewardVideoAd.addSetting(ApplovinATConst.NETWORK_FIRM_ID, _applovinATMediationSetting);
//
//                            break;
//                        case FlurryATConst.NETWORK_FIRM_ID:
//                            FlurryRewardedVideoSetting _flurryATMediationSetting = new FlurryRewardedVideoSetting();
//
//                            mRewardVideoAd.addSetting(FlurryATConst.NETWORK_FIRM_ID, _flurryATMediationSetting);
//
//                            break;
//                        case InmobiATConst.NETWORK_FIRM_ID:
//
//                            InmobiRewardedVideoSetting _inmobiATMediationSetting = new InmobiRewardedVideoSetting();
//
//                            mRewardVideoAd.addSetting(InmobiATConst.NETWORK_FIRM_ID, _inmobiATMediationSetting);
//
//                            break;
//                        case MopubATConst.NETWORK_FIRM_ID:
//
//                            MopubRewardedVideoSetting _mopubATMediationSetting = new MopubRewardedVideoSetting();
//                            mRewardVideoAd.addSetting(MopubATConst.NETWORK_FIRM_ID, _mopubATMediationSetting);
//
//                            break;
//                        case ChartboostATConst.NETWORK_FIRM_ID:
//                            ChartboostRewardedVideoSetting _chartboostATMediationSetting = new ChartboostRewardedVideoSetting();
//                            mRewardVideoAd.addSetting(ChartboostATConst.NETWORK_FIRM_ID, _chartboostATMediationSetting);
//
//                            break;
//                        case TapjoyATConst.NETWORK_FIRM_ID:
//                            TapjoyRewardedVideoSetting _tapjoyATMediationSetting = new TapjoyRewardedVideoSetting();
//                            mRewardVideoAd.addSetting(TapjoyATConst.NETWORK_FIRM_ID, _tapjoyATMediationSetting);
//
//                            break;
//                        case IronsourceATConst.NETWORK_FIRM_ID:
//
//                            IronsourceRewardedVideoSetting _ironsourceATMediationSetting = new IronsourceRewardedVideoSetting();
//                            mRewardVideoAd.addSetting(IronsourceATConst.NETWORK_FIRM_ID, _ironsourceATMediationSetting);
//
//                            break;
//                        case UnityAdsATConst.NETWORK_FIRM_ID:
//                            UnityAdsRewardedVideoSetting _unityAdATMediationSetting = new UnityAdsRewardedVideoSetting();
//                            mRewardVideoAd.addSetting(UnityAdsATConst.NETWORK_FIRM_ID, _unityAdATMediationSetting);
//
//                            break;
//                        case VungleATConst.NETWORK_FIRM_ID:
//
//                            VungleRewardedVideoSetting vungleRewardVideoSetting = new VungleRewardedVideoSetting();
//                            JSONObject temp = new JSONObject(json);
//                            if (json.contains("orientation")) {
//                                vungleRewardVideoSetting.setOrientation(temp.getInt("orientation"));
//                            }
//
//                            if (json.contains("isSoundEnable")) {
//                                vungleRewardVideoSetting.setSoundEnable(temp.getBoolean("isSoundEnable"));
//                            }
//
//
//                            if (json.contains("isBackButtonImmediatelyEnable")) {
//                                vungleRewardVideoSetting.setBackButtonImmediatelyEnable(temp.getBoolean("isBackButtonImmediatelyEnable"));
//                            }
//
//
//                            mRewardVideoAd.addSetting(VungleATConst.NETWORK_FIRM_ID, vungleRewardVideoSetting);
//
//                            break;
//                        case AdColonyATConst.NETWORK_FIRM_ID:
//                            AdColonyRewardedVideoSetting adColonyUparpuRewardVideoSetting = new AdColonyRewardedVideoSetting();
//
//
//                            JSONObject temp1 = new JSONObject(json);
//                            if (json.contains("enableConfirmationDialog")) {
//                                adColonyUparpuRewardVideoSetting.setEnableConfirmationDialog(temp1.getBoolean("enableConfirmationDialog"));
//                            }
//
//
//                            if (json.contains("enableResultsDialog")) {
//                                adColonyUparpuRewardVideoSetting.setEnableResultsDialog(temp1.getBoolean("enableResultsDialog"));
//                            }
//
//
//                            mRewardVideoAd.addSetting(AdColonyATConst.NETWORK_FIRM_ID, adColonyUparpuRewardVideoSetting);
//
//                            break;
//
//                        case TTATConst.NETWORK_FIRM_ID:
//
//                            TTRewardedVideoSetting ttRewardedVideoSetting = new TTRewardedVideoSetting();
//
//                            JSONObject temp2 = new JSONObject(json);
//                            if (json.contains("requirePermission")) {
//                                ttRewardedVideoSetting.setRequirePermission(temp2.getBoolean("requirePermission"));
//                            }
//
//                            if (json.contains("orientation")) {
//                                ttRewardedVideoSetting.setVideoOrientation(temp2.getInt("orientation"));
//                            }
//
//                            if (json.contains("supportDeepLink")) {
//                                ttRewardedVideoSetting.setSupportDeepLink(temp2.getBoolean("supportDeepLink"));
//                            }
//
//                            if (json.contains("rewardName")) {
//                                ttRewardedVideoSetting.setRewardName(temp2.getString("rewardName"));
//                            }
//
//                            if (json.contains("rewardCount")) {
//                                ttRewardedVideoSetting.setRewardAmount(temp2.getInt("rewardCount"));
//                            }
//
//
//                            ttRewardedVideoSetting.setRequirePermission(true);
//                            mRewardVideoAd.addSetting(TTATConst.NETWORK_FIRM_ID, ttRewardedVideoSetting);
//
//                            break;
//
//                        default:
//
//                    }
//                } catch (Exception e) {
//
//                }
//
//            }
//        } catch (Exception e) {
//            if (Const.DEBUG) {
//                e.printStackTrace();
//            }
//        }

    }


    public void fillVideo(final String jsonMap, final String customRuleJsonMap) {
        MsgTools.pirntMsg("fillVideo jsonMap:" + jsonMap + ", customRuleJsonMap: " + customRuleJsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String userId = "";
                String userExtraData = "";

                Map<String, Object> localExtra = new HashMap<>();
                try {
                    if (!TextUtils.isEmpty(jsonMap)) {
                        JSONObject jsonObject = new JSONObject(jsonMap);

                        Const.fillMapFromJsonObject(localExtra, jsonObject);

                        if (jsonObject.has("UserId")) {
                            userId = jsonObject.optString("UserId");
                        }

                        if (jsonObject.has("UserExtraData")) {
                            userExtraData = jsonObject.optString("UserExtraData");
                        }
                    }
                } catch (Exception e) {

                }

                Map<String, Object> customRuleMap = new HashMap<>();
                try {
                    if (!TextUtils.isEmpty(customRuleJsonMap)) {
                        JSONObject jsonObject = new JSONObject(customRuleJsonMap);

                        Const.fillMapFromJsonObject(customRuleMap, jsonObject);
                    }
                } catch (Exception e) {

                }


                if (mRewardVideoAd != null) {

                    if (!TextUtils.isEmpty(userId) || !TextUtils.isEmpty(userExtraData)) {

                        localExtra.put("user_id", userId);
                        localExtra.put("user_custom_data", userExtraData);

                        MsgTools.pirntMsg("fillVideo userId:" + userId + "-- userExtraData:" + userExtraData + "   " + this);
                    }

                    mRewardVideoAd.setConfigMap(localExtra);
                    mRewardVideoAd.load(customRuleMap);
                } else {
                    MsgTools.pirntMsg("fillVideo error, you must call initVideo first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (VideoHelper.this) {
                                    mListener.onRewardedVideoAdFailed(mPlacmentId, "-1", "you must call initVideo first");
                                }
                            }
                        }
                    });
                }

            }
        });
    }


    public void showVideo(final String jsonMap) {
        MsgTools.pirntMsg("showVideo: " + this + ", jsonMap: " + jsonMap);
        isReward = false;
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRewardVideoAd != null) {
                    isReady = false;

                    String scenario = "";
                    if (!TextUtils.isEmpty(jsonMap)) {
                        try {
                            JSONObject _jsonObject = new JSONObject(jsonMap);
                            if (_jsonObject.has(Const.SCENARIO)) {
                                scenario = _jsonObject.optString(Const.SCENARIO);
                            }
                        } catch (Exception e) {
                            if (Const.DEBUG) {
                                e.printStackTrace();
                            }
                        }
                    }
                    MsgTools.pirntMsg("showVideo: " + this + ", scenario: " + scenario);
                    mRewardVideoAd.show(mActivity);
                } else {
                    MsgTools.pirntMsg("showVideo error, you must call initVideo first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (VideoHelper.this) {
                                    mListener.onRewardedVideoAdFailed(mPlacmentId, "-1", "you must call initVideo first");
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("isAdReady, start: " + this);

        try {
            if (mRewardVideoAd != null) {
                boolean isAdReady = mRewardVideoAd.isAdReady();
                MsgTools.pirntMsg("isAdReady: " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.pirntMsg("isAdReady error, you must call initVideo first ");

            }
            MsgTools.pirntMsg("isAdReady, end: " + this);
        } catch (Exception e) {
            MsgTools.pirntMsg("isAdReady, Exception: " + e.getMessage());
            return isReady;

        } catch (Throwable e) {
            MsgTools.pirntMsg("isAdReady, Throwable: " + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public void clean() {
        MsgTools.pirntMsg("clean: " + this);
        if (mRewardVideoAd != null) {

            isReady = false;
        } else {
            MsgTools.pirntMsg("clean error, you must call initVideo first ");

        }

    }


    public void onPause() {
//        MsgTools.pirntMsg("onPause-->");
//        if (mRewardVideoAd != null) {
//            mRewardVideoAd.onPause();
//        }
    }

    public void onResume() {
//        MsgTools.pirntMsg("onResume-->");
//        if (mRewardVideoAd != null) {
//            mRewardVideoAd.onResume();
//        }
    }

    public String checkAdStatus() {
//        if (mRewardVideoAd != null) {
//            ATAdStatusInfo atAdStatusInfo = mRewardVideoAd.checkAdStatus();
//            boolean loading = atAdStatusInfo.isLoading();
//            boolean ready = atAdStatusInfo.isReady();
//            ATAdInfo atTopAdInfo = atAdStatusInfo.getATTopAdInfo();
//
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("isLoading", loading);
//                jsonObject.put("isReady", ready);
//                jsonObject.put("adInfo", atTopAdInfo);
//
//                return jsonObject.toString();
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//        }
        return "";
    }

}
