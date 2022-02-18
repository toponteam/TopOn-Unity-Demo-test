package com.anythink.unitybridge.videoad;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoEventListener;
import com.anythink.rewardvideo.api.ATRewardVideoAutoLoadListener;
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

public class VideoAutoAdHelper {
    public final String LOG_PRE = "video_autoad ";

    VideoListener mListener;
    Activity mActivity;
    List<String> mAddedPlacementIds;

    public VideoAutoAdHelper(VideoListener pListener) {
        MsgTools.printMsg(LOG_PRE, "VideoAutoAdHelper: " + this);
        if (pListener == null) {
            MsgTools.printMsg(LOG_PRE, "Listener == null: ");
        }
        mListener = pListener;
        mActivity = UnityPluginUtils.getActivity("VideoAutoAdHelper");
        mAddedPlacementIds = new ArrayList<>();

        ATRewardVideoAutoAd.init(mActivity, null, new ATRewardVideoAutoLoadListener() {
            @Override
            public void onRewardVideoAutoLoaded(final String placementId) {
                MsgTools.printMsg(LOG_PRE, "onRewardVideoAutoLoaded: " + placementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoAutoAdHelper.this) {
                                mListener.onRewardedVideoAdLoaded(placementId);
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardVideoAutoLoadFail(final String placementId, final AdError adError) {
                MsgTools.printMsg(LOG_PRE, "onRewardVideoAutoLoadFail: " + placementId + ", " + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoAutoAdHelper.this) {
                                mListener.onRewardedVideoAdFailed(placementId, adError.getCode(), adError.getFullErrorInfo());
                            }
                        } else {
                            MsgTools.printMsg(LOG_PRE, "onRewardVideoAutoLoadFail callnoback: " + adError.getFullErrorInfo());
                        }
                    }
                });
            }
        });

        MsgTools.printMsg(LOG_PRE, "VideoAutoAdHelper: init success");

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
                    ATRewardVideoAutoAd.addPlacementId(placementIdArr);
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
                    ATRewardVideoAutoAd.removePlacementId(placementIdArr);
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

        if (!ATRewardVideoAutoAd.isAdReady(placementId)) {
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
                final boolean[] isReaward = {false};
                ATRewardVideoAutoAd.show(mActivity, placementId, finalScenario, new ATRewardVideoAutoEventListener() {
                    @Override
                    public void onRewardedVideoAdPlayStart(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdPlayStart: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdPlayStart(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdPlayEnd(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdPlayEnd: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdPlayEnd(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdPlayFailed(final AdError adError, final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdPlayFailed: " + placementId + ", " + adError.getFullErrorInfo());
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdPlayFailed(placementId, adError.getCode(), adError.getFullErrorInfo());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdClosed(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdClosed: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdClosed(placementId, isReaward[0], atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdPlayClicked(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdPlayClicked: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdPlayClicked(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onReward(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onReward: " + placementId);
                        isReaward[0] = true;
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onReward(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdAgainPlayStart(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdAgainPlayStart: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdAgainPlayStart(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdAgainPlayEnd(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdAgainPlayEnd: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdAgainPlayEnd(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdAgainPlayFailed(final AdError adError, final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdAgainPlayFailed: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdAgainPlayFailed(placementId, adError.getCode(), adError.getFullErrorInfo());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onRewardedVideoAdAgainPlayClicked(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onRewardedVideoAdAgainPlayClicked: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onRewardedVideoAdAgainPlayClicked(placementId, atAdInfo.toString());
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onAgainReward(final ATAdInfo atAdInfo) {
                        MsgTools.printMsg("onAgainReward: " + placementId);
                        TaskManager.getInstance().run_proxy(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    synchronized (VideoAutoAdHelper.this) {
                                        mListener.onAgainReward(placementId, atAdInfo.toString());
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
        MsgTools.printMsg(LOG_PRE, "setExtraData start: " + placementId + "," + jsonMap);
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
                    e.printStackTrace();
                    MsgTools.printMsg(LOG_PRE, "setExtraData failed: " + placementId + "," + jsonMap);

                }
                if (!TextUtils.isEmpty(userId) || !TextUtils.isEmpty(userExtraData)) {

                    localExtra.put("user_id", userId);
                    localExtra.put("user_custom_data", userExtraData);
                    ATRewardVideoAutoAd.setLocalExtra(placementId, localExtra);
                    MsgTools.printMsg("fillVideo: " + placementId + ", userId:" + userId + ", userExtraData:" + userExtraData);
                }
            }
        });
    }


    public boolean isAdReady(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "isAdReady please addPlacementIds first: " + placementId);
        }
        boolean isReady = ATRewardVideoAutoAd.isAdReady(placementId);
        MsgTools.printMsg(LOG_PRE, "isAdReady: " + placementId + ", " + isReady);
        return isReady;
    }

    public String checkAdStatus(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "checkAdStatus please addPlacementIds first: " + placementId);
        }
        ATAdStatusInfo atAdStatusInfo = ATRewardVideoAutoAd.checkAdStatus(placementId);
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

        List<ATAdInfo> vaildAds = ATRewardVideoAutoAd.checkValidAdCaches(placementId);
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
        ATRewardVideoAutoAd.entryAdScenario(placementId, scenario);
    }
}
