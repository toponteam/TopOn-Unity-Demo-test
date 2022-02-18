package com.ofm.unitybridge.interstitial;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.ofm.core.api.OfmAdError;
import com.ofm.core.api.OfmAdInfo;
import com.ofm.interstitial.api.OfmInterstitial;
import com.ofm.interstitial.api.OfmInterstitialListener;
import com.ofm.unitybridge.MsgTools;
import com.ofm.unitybridge.UnityPluginUtils;
import com.ofm.unitybridge.utils.Const;
import com.ofm.unitybridge.utils.TaskManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 */
public class InterstitialHelper {
    public static final String TAG = UnityPluginUtils.TAG;
    InterstitialListener mListener;
    Activity mActivity;
    OfmInterstitial mInterstitialAd;
    String mPlacementId;

    boolean isReady = false;

    public InterstitialHelper(InterstitialListener listener) {
        MsgTools.pirntMsg("InterstitialHelper: " + this);
        if (listener == null) {
            Log.e(TAG, "Listener == null");
        }
        mListener = listener;
        mActivity = UnityPluginUtils.getActivity("InterstitialHelper");
    }


    public void initInterstitial(final String placementId) {
        MsgTools.pirntMsg("initInterstitial 1: " + this);

        mInterstitialAd = new OfmInterstitial(mActivity, placementId);
        mPlacementId = placementId;


        MsgTools.pirntMsg("initInterstitial 2: " + this);

        mInterstitialAd.setAdListener(new OfmInterstitialListener() {

            @Override
            public void onInterstitialAdLoaded() {
                MsgTools.pirntMsg(" onInterstitialAdLoaded");
                isReady = true;
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdLoaded(mPlacementId);
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdLoadFail(final OfmAdError adError) {
                MsgTools.pirntMsg("onInterstitialAdLoadFail: " + adError.toString());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdLoadFail(mPlacementId, adError.getErrorCode(), adError.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdClicked(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onInterstitialAdClicked");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdClicked(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdShow(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onInterstitialAdShow");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdShow(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdClose(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onInterstitialAdClose");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdClose(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdVideoStart(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoStart");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdVideoStart(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdVideoEnd(final OfmAdInfo adInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoEnd");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdVideoEnd(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdVideoError(final OfmAdError adError) {
                MsgTools.pirntMsg("onInterstitialAdVideoError: " + adError.toString());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdVideoError(mPlacementId, adError.getErrorCode(), adError.toString());
                            }
                        }
                    }
                });
            }
        });

        MsgTools.pirntMsg("initInterstitial 3: " + this);
    }


    public void loadInterstitialAd(final String jsonMap, final String customRuleJsonMap) {
        MsgTools.pirntMsg("loadInterstitialAd, jsonMap: " + jsonMap + ", customeRuleJsonMap: " + customRuleJsonMap);

        if (!TextUtils.isEmpty(jsonMap)) {
            Map<String, Object> localExtra = new HashMap<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonMap);
                String useRewardedVideoAsInterstitial = (String) jsonObject.get(Const.Interstital.UseRewardedVideoAsInterstitial);

                if (useRewardedVideoAsInterstitial != null && TextUtils.equals(Const.Interstital.UseRewardedVideoAsInterstitialYes, useRewardedVideoAsInterstitial)) {
                    localExtra.put("is_use_rewarded_video_as_interstitial", true);
                }

                Const.fillMapFromJsonObject(localExtra, jsonObject);

                if (mInterstitialAd != null) {
                    mInterstitialAd.setConfigMap(localExtra);
                }
            } catch (Throwable e) {
            }

        }

        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd != null) {

                    Map<String, Object> customRuleMap = new HashMap<>();
                    if (!TextUtils.isEmpty(customRuleJsonMap)) {
                        try {
                            JSONObject jsonObject = new JSONObject(customRuleJsonMap);

                            Const.fillMapFromJsonObject(customRuleMap, jsonObject);
                        } catch (Throwable e) {
                        }
                    }

                    if (mInterstitialAd != null) {
                        mInterstitialAd.load(customRuleMap);
                    }
                } else {
                    Log.e(TAG, "loadInterstitialAd error, you must call initInterstitial first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (InterstitialHelper.this) {
                                    mListener.onInterstitialAdLoadFail(mPlacementId, "-1", "you must call initInterstitial first");
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    public void showInterstitialAd(final String jsonMap) {
        MsgTools.pirntMsg("showInterstitial: " + this + ", jsonMap: " + jsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
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
                    MsgTools.pirntMsg("showInterstitialAd: " + this + ", scenario: " + scenario);
                    mInterstitialAd.show(mActivity);
                } else {
                    Log.e(TAG, "showInterstitial error, you must call initInterstitial first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (InterstitialHelper.this) {
                                    mListener.onInterstitialAdLoadFail(mPlacementId, "-1", "you must call initInterstitial first");
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
            if (mInterstitialAd != null) {
                boolean isAdReady = mInterstitialAd.isAdReady();
                MsgTools.pirntMsg("isAdReady: " + isAdReady);
                return isAdReady;
            } else {
                Log.e(TAG, "isAdReady error, you must call initInterstitial first ");

            }
            MsgTools.pirntMsg("isAdReady, ent: " + this);
        } catch (Exception e) {
            MsgTools.pirntMsg("isAdReady, Exception: " + e.getMessage());
//            e.printStackTrace();
            return isReady;

        } catch (Throwable e) {
            MsgTools.pirntMsg("isAdReady, Throwable: " + e.getMessage());
//            e.printStackTrace();
            return isReady;
        }
        return isReady;
    }

    public void clean() {
        MsgTools.pirntMsg("clean: " + this);
        if (mInterstitialAd != null) {
            isReady = false;
        } else {
            Log.e(TAG, "clean error, you must call initInterstitial first ");

        }

    }

    public void onPause() {
//        MsgTools.pirntMsg("onPause-->");
//        if (mInterstitialAd != null) {
//            mInterstitialAd.onPause();
//        }
    }

    public void onResume() {
//        MsgTools.pirntMsg("onResume-->");
//        if (mInterstitialAd != null) {
//            mInterstitialAd.onResume();
//        }
    }

    public String checkAdStatus() {
//        if (mInterstitialAd != null) {
//            ATAdStatusInfo atAdStatusInfo = mInterstitialAd.checkAdStatus();
//            boolean loading = atAdStatusInfo.isLoading();
//            boolean ready = atAdStatusInfo.isReady();
//            OfmAdInfo atTopAdInfo = atAdStatusInfo.getATTopAdInfo();
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
