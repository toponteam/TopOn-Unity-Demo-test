package com.anythink.unitybridge.interstitial;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdSourceStatusListener;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.download.DownloadHelper;
import com.anythink.unitybridge.utils.Const;
import com.anythink.unitybridge.utils.TaskManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InterstitialHelper {
    public static final String TAG = UnityPluginUtils.TAG;
    InterstitialListener mListener;
    Activity mActivity;
    ATInterstitial mInterstitialAd;
    String mPlacementId;

    boolean isReady = false;

    public InterstitialHelper(InterstitialListener listener) {
        MsgTools.printMsg("InterstitialHelper: " + this);
        if (listener == null) {
            Log.e(TAG, "Listener == null ..");
        }
        mListener = listener;
        mActivity = UnityPluginUtils.getActivity("InterstitialHelper");
    }


    public void initInterstitial(final String placementId) {
        MsgTools.printMsg("initInterstitial 1: " + placementId);

        mInterstitialAd = new ATInterstitial(mActivity, placementId);
        mPlacementId = placementId;


        MsgTools.printMsg("initInterstitial 2: " + placementId);

        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                MsgTools.printMsg("onInterstitialAdLoaded: " + mPlacementId);
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
            public void onInterstitialAdLoadFail(final AdError adError) {
                MsgTools.printMsg("onInterstitialAdLoadFail: " + mPlacementId + ", " + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdLoadFail(mPlacementId, adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdClicked(final ATAdInfo adInfo) {
                MsgTools.printMsg("onInterstitialAdClicked: " + mPlacementId);
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
            public void onInterstitialAdShow(final ATAdInfo adInfo) {
                MsgTools.printMsg("onInterstitialAdShow: " + mPlacementId);
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
            public void onInterstitialAdClose(final ATAdInfo adInfo) {
                MsgTools.printMsg("onInterstitialAdClose: " + mPlacementId);
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
            public void onInterstitialAdVideoStart(final ATAdInfo adInfo) {
                MsgTools.printMsg("onInterstitialAdVideoStart: " + mPlacementId);
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
            public void onInterstitialAdVideoEnd(final ATAdInfo adInfo) {
                MsgTools.printMsg("onInterstitialAdVideoEnd: " + mPlacementId);
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
            public void onInterstitialAdVideoError(final AdError adError) {
                MsgTools.printMsg("onInterstitialAdVideoError: " + mPlacementId + ", " + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onInterstitialAdVideoError(mPlacementId, adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }
        });
        
        mInterstitialAd.setAdSourceStatusListener(new ATAdSourceStatusListener() {
            @Override
            public void onAdSourceBiddingAttempt(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAdSourceBiddingAttempt: " + mPlacementId );
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onAdSourceBiddingAttempt(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onAdSourceBiddingFilled(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAdSourceBiddingFilled: " + mPlacementId );
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onAdSourceBiddingFilled(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onAdSourceBiddingFail(final ATAdInfo atAdInfo, final AdError adError) {
                MsgTools.printMsg("onAdSourceBiddingFail: " + mPlacementId + "," + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onAdSourceBiddingFail(mPlacementId, atAdInfo.toString(), adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }

            @Override
            public void onAdSourceAttemp(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAdSourceAttemp: " + mPlacementId );
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onAdSourceAttemp(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onAdSourceLoadFilled(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAdSourceLoadFilled: " + mPlacementId );
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onAdSourceLoadFilled(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onAdSourceLoadFail(final ATAdInfo atAdInfo, final AdError adError) {
                MsgTools.printMsg("onAdSourceLoadFail: " + mPlacementId + "," + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialHelper.this) {
                                mListener.onAdSourceLoadFail(mPlacementId, atAdInfo.toString(), adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }
        });
        
        MsgTools.printMsg("initInterstitial 3: " + placementId);

        try {
            if (ATSDK.isCnSDK()) {
                mInterstitialAd.setAdDownloadListener(DownloadHelper.getDownloadListener(mPlacementId));
            }
        } catch (Throwable e) {
        }
    }


    public void loadInterstitialAd(final String jsonMap) {
        MsgTools.printMsg("loadInterstitialAd: " + mPlacementId + ", jsonMap: " + jsonMap);

        if (!TextUtils.isEmpty(jsonMap)) {
            Map<String, Object> localExtra = new HashMap<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonMap);
                try {
                    String useRewardedVideoAsInterstitial = (String) jsonObject.get(Const.Interstital.UseRewardedVideoAsInterstitial);

                    if (useRewardedVideoAsInterstitial != null && TextUtils.equals(Const.Interstital.UseRewardedVideoAsInterstitialYes, useRewardedVideoAsInterstitial)) {
                        localExtra.put("is_use_rewarded_video_as_interstitial", true);
                    }
                } catch (Throwable e) {
                }

                try {
                    String inter_ad_size = (String) jsonObject.get(Const.Interstital.interstitial_ad_size);

                    if (!TextUtils.isEmpty(inter_ad_size)) {
                        String[] sizes = inter_ad_size.split("x");
                        MsgTools.printMsg("loadInterstitialAd, inter_ad_size" + inter_ad_size);

                        localExtra.put("key_width", sizes[0]);
                        localExtra.put("key_height", sizes[1]);

                    }
                } catch (Throwable e) {
                }

                Const.fillMapFromJsonObject(localExtra, jsonObject);

                if (mInterstitialAd != null) {
                    mInterstitialAd.setLocalExtra(localExtra);
                }
            } catch (Throwable e) {
            }

        }

        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd != null) {

                    mInterstitialAd.load();
                } else {
                    Log.e(TAG, "loadInterstitialAd error, you must call initInterstitial first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (InterstitialHelper.this) {
                                    mListener.onInterstitialAdLoadFail(mPlacementId, "-1", "you must call initInterstitial first ..");
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    public void showInterstitialAd(final String jsonMap) {
        MsgTools.printMsg("showInterstitial: " + this + ", jsonMap: " + jsonMap);
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
                    MsgTools.printMsg("showInterstitialAd: " + this + ", scenario: " + scenario);
                    if (!TextUtils.isEmpty(scenario)) {
                        mInterstitialAd.show(mActivity, scenario);
                    } else {
                        mInterstitialAd.show(mActivity);
                    }
                } else {
                    Log.e(TAG, "showInterstitial error, you must call initInterstitial first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (InterstitialHelper.this) {
                                    mListener.onInterstitialAdLoadFail(mPlacementId, "-1", "you must call initInterstitial first ..");
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.printMsg("isAdReady start: " + mPlacementId);

        try {
            if (mInterstitialAd != null) {
                boolean isAdReady = mInterstitialAd.isAdReady();
                MsgTools.printMsg("isAdReady: " + isAdReady);
                return isAdReady;
            } else {
                Log.e(TAG, "isAdReady error, you must call initInterstitial first ");

            }
            MsgTools.printMsg("isAdReady end: " + mPlacementId);
        } catch (Exception e) {
            MsgTools.printMsg("isAdReady Exception: " + e.getMessage());
//            e.printStackTrace();
            return isReady;

        } catch (Throwable e) {
            MsgTools.printMsg("isAdReady Throwable: " + e.getMessage());
//            e.printStackTrace();
            return isReady;
        }
        return isReady;
    }


    public String checkAdStatus() {
        MsgTools.printMsg("checkAdStatus: " + mPlacementId);
        if (mInterstitialAd != null) {
            ATAdStatusInfo atAdStatusInfo = mInterstitialAd.checkAdStatus();
            boolean loading = atAdStatusInfo.isLoading();
            boolean ready = atAdStatusInfo.isReady();
            ATAdInfo atTopAdInfo = atAdStatusInfo.getATTopAdInfo();

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("isLoading", loading);
                jsonObject.put("isReady", ready);
                jsonObject.put("adInfo", atTopAdInfo);

                return jsonObject.toString();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getValidAdCaches() {
        MsgTools.printMsg("getValidAdCaches:" + mPlacementId);

        if (mInterstitialAd != null) {
            JSONArray jsonArray = new JSONArray();

            List<ATAdInfo> vaildAds = mInterstitialAd.checkValidAdCaches();
            if (vaildAds == null) {
                return "";
            }

            int size = vaildAds.size();

            for (int i = 0; i < size; i++) {
                try {
                    jsonArray.put(new JSONObject(vaildAds.get(i).toString()));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return jsonArray.toString();
        }
        return "";
    }

    public void entryAdScenario(final String scenarioId) {
        MsgTools.printMsg("entryAdScenario start: " + mPlacementId + ", scenarioId: " + scenarioId);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(mPlacementId)) {
                    ATInterstitial.entryAdScenario(mPlacementId, scenarioId);
                } else {
                    MsgTools.printMsg("entryAdScenario error, you must call initInterstitial first " + mPlacementId);
                }
            }
        });
    }
}
