package com.anythink.unitybridge.interstitial;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialAutoAd;
import com.anythink.interstitial.api.ATInterstitialAutoEventListener;
import com.anythink.interstitial.api.ATInterstitialAutoLoadListener;
import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoEventListener;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.utils.Const;
import com.anythink.unitybridge.utils.TaskManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterstitialAutoAdHelper {
    public final String LOG_PRE = "interstitial_autoad ";

    InterstitialListener mListener;
    Activity mActivity;
    List<String> mAddedPlacementIds;

    public InterstitialAutoAdHelper(InterstitialListener pListener) {
        MsgTools.printMsg(LOG_PRE, "InterstitialAutoAdHelper: " + this);
        if (pListener == null) {
            MsgTools.printMsg(LOG_PRE, "Listener == null: ");
        }
        mListener = pListener;
        mActivity = UnityPluginUtils.getActivity("InterstitialAutoAdHelper");
        mAddedPlacementIds = new ArrayList<>();

        ATInterstitialAutoAd.init(mActivity, null, new ATInterstitialAutoLoadListener() {
            @Override
            public void onInterstitialAutoLoaded(final String placementId) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAutoLoaded: " + placementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialAutoAdHelper.this) {
                                mListener.onInterstitialAdLoaded(placementId);
                            }
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAutoLoadFail(final String placementId, final AdError adError) {
                MsgTools.printMsg(LOG_PRE, "onRewardVideoAutoLoadFail: " + placementId + ", " + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (InterstitialAutoAdHelper.this) {
                                mListener.onInterstitialAdLoadFail(placementId, adError.getCode(), adError.getFullErrorInfo());
                            }
                        } else {
                            MsgTools.printMsg(LOG_PRE, "onRewardVideoAutoLoadFail callnoback: " + adError.getFullErrorInfo());
                        }
                    }
                });
            }

        });

        MsgTools.printMsg(LOG_PRE, "InterstitialAutoAdHelper: init success");

    }

    public void addPlacementIds(String placementIds) {
        if (TextUtils.isEmpty(placementIds)) {
            MsgTools.printMsg(LOG_PRE, "addPlacementIds warn: not set placementIds");
            return;
        }
        try {
            MsgTools.printMsg(LOG_PRE, "addPlacementIds: " + placementIds);
            JSONArray jsonArray = new JSONArray(placementIds);
            final String[] placementIdArr = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                placementIdArr[i] = jsonArray.getString(i);
            }
            mAddedPlacementIds.addAll(Arrays.asList(placementIdArr));
            UnityPluginUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ATInterstitialAutoAd.addPlacementId(placementIdArr);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            MsgTools.printMsg(LOG_PRE, "addPlacementIds error,please check your placementIds:\"" + placementIds + "\"");
        }
    }

    public void removePlacementIds(String placementIds) {
        if (TextUtils.isEmpty(placementIds)) {
            MsgTools.printMsg(LOG_PRE, "removePlacementIds warn: not set placementId");
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(placementIds);
            final String[] placementIdArr = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                placementIdArr[i] = jsonArray.getString(i);
            }
            mAddedPlacementIds.removeAll(Arrays.asList(placementIdArr));
            MsgTools.printMsg(LOG_PRE, "removePlacementIds: " + placementIds);
            UnityPluginUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ATInterstitialAutoAd.removePlacementId(placementIdArr);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            MsgTools.printMsg(LOG_PRE, "removePlacementIds error,please check your placementIds:\"" + placementIds + "\"");
        }
    }

    public void show(final String placementId, String jsonMap) {
//        check setAdListen
        if (mListener == null) {
            MsgTools.printMsg(LOG_PRE, "show warn:please init before show");
        }

        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "show warn:please addPlacementIds first: " + placementId);
        }

        if (!ATInterstitialAutoAd.isAdReady(placementId)) {
            MsgTools.printMsg(LOG_PRE, "show fail:this placementId(" + placementId + ") is not ready to show");
            return;
        }

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

        MsgTools.printMsg(LOG_PRE, "show placementId:" + placementId + ", scenario:" + scenario);
        final String finalScenario = scenario;
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ATInterstitialAutoAd.show(mActivity, placementId, finalScenario, new ATInterstitialAutoEventListener() {
                    @Override
                    public void onInterstitialAdClicked(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onInterstitialAdClicked: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (InterstitialAutoAdHelper.this) {
                                        mListener.onInterstitialAdClicked(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onInterstitialAdShow(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onInterstitialAdShow: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (InterstitialAutoAdHelper.this) {
                                        mListener.onInterstitialAdShow(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onInterstitialAdClose(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onInterstitialAdClose: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (InterstitialAutoAdHelper.this) {
                                        mListener.onInterstitialAdClose(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onInterstitialAdVideoStart(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onInterstitialAdVideoStart: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (InterstitialAutoAdHelper.this) {
                                        mListener.onInterstitialAdVideoStart(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onInterstitialAdVideoEnd(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onInterstitialAdVideoEnd: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (InterstitialAutoAdHelper.this) {
                                        mListener.onInterstitialAdVideoEnd(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onInterstitialAdVideoError(final AdError adError) {
                        MsgTools.printMsg("onInterstitialAdVideoError: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (InterstitialAutoAdHelper.this) {
                                        mListener.onInterstitialAdVideoError(placementId, adError.getCode(), adError.getFullErrorInfo());
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    public void setAdExtraData(final String placementId, final String jsonMap) {
        MsgTools.printMsg(LOG_PRE, "setAdExtraData start: " + placementId + ", jsonMap: " + jsonMap);

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

                ATInterstitialAutoAd.setLocalExtra(placementId, localExtra);
            } catch (Throwable e) {
            }

        }
    }


    public boolean isAdReady(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "isAdReady please addPlacementIds first: " + placementId);
        }
        boolean isReady = ATInterstitialAutoAd.isAdReady(placementId);
        MsgTools.printMsg(LOG_PRE, "isAdReady: " + placementId + ", " + isReady);
        return isReady;
    }

    public String checkAdStatus(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "checkAdStatus please addPlacementIds first: " + placementId);
        }
        ATAdStatusInfo atAdStatusInfo = ATInterstitialAutoAd.checkAdStatus(placementId);
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
        return "";
    }

    public String getValidAdCaches(String placementId) {
        MsgTools.printMsg(LOG_PRE, "getValidAdCaches:" + placementId);

        JSONArray jsonArray = new JSONArray();

        List<ATAdInfo> vaildAds = ATInterstitialAutoAd.checkValidAdCaches(placementId);
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

    public void entryAdScenario(String placementId, String scenario) {
        MsgTools.printMsg(LOG_PRE, "entryAdScenario... " + placementId + "," + scenario);
        ATInterstitialAutoAd.entryAdScenario(placementId, scenario);
    }
}
