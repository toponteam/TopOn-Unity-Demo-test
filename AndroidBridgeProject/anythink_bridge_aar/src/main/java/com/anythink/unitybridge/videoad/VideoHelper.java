package com.anythink.unitybridge.videoad;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdSourceStatusListener;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.ATNetworkConfirmInfo;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoExListener;
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


public class VideoHelper {
    public static final String TAG = UnityPluginUtils.TAG;
    VideoListener mListener;
    Activity mActivity;
    ATRewardVideoAd mRewardVideoAd;
    String mPlacementId;

    boolean isReady = false;
    boolean isReward = false;

    public VideoHelper(VideoListener pListener) {
        MsgTools.printMsg("VideoHelper: " + this);
        if (pListener == null) {
            MsgTools.printMsg("Listener == null: ");
        }
        mListener = pListener;
        mActivity = UnityPluginUtils.getActivity("VideoHelper");
    }

    public void initVideo(final String placementId) {
        MsgTools.printMsg("initVideo 1: " + placementId);

        mRewardVideoAd = new ATRewardVideoAd(mActivity, placementId);
        mPlacementId = placementId;


        MsgTools.printMsg("initVideo 2: " + placementId);

        mRewardVideoAd.setAdListener(new ATRewardVideoExListener() {
            @Override
            public void onDeeplinkCallback(ATAdInfo atAdInfo, boolean b) {

            }

            @Override
            public void onDownloadConfirm(Context context, ATAdInfo atAdInfo, ATNetworkConfirmInfo atNetworkConfirmInfo) {

            }

            @Override
            public void onRewardedVideoAdAgainPlayStart(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayStart: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdAgainPlayStart(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdAgainPlayEnd(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayEnd: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdAgainPlayEnd(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdAgainPlayFailed(final AdError adError, final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayFailed: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdAgainPlayFailed(mPlacementId, adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdAgainPlayClicked(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayClicked: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdAgainPlayClicked(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onAgainReward(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAgainReward: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onAgainReward(mPlacementId, atAdInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdLoaded() {
                MsgTools.printMsg("onRewardedVideoAdLoaded: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        isReady = true;
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdLoaded(mPlacementId);
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdFailed(final AdError pAdError) {
                MsgTools.printMsg("onRewardedVideoAdFailed: " + mPlacementId + ", " + pAdError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdFailed(mPlacementId, pAdError.getCode(), pAdError.getFullErrorInfo());
                            }
                        } else {
                            MsgTools.printMsg("onRewardedVideoAdFailed callnoback: " + pAdError.getFullErrorInfo());
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayStart(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayStart: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayStart(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayEnd(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayEnd: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayEnd(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayFailed(final AdError pAdError, ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayFailed: " + mPlacementId + ", " + pAdError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayFailed(mPlacementId, pAdError.getCode(), pAdError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }


            @Override
            public void onRewardedVideoAdClosed(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdClosed: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdClosed(mPlacementId, isReward, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayClicked(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayClicked: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayClicked(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onReward(final ATAdInfo adInfo) {
                MsgTools.printMsg("onReward: " + mPlacementId);
                isReward = true;
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onReward(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }
        });

        mRewardVideoAd.setAdSourceStatusListener(new ATAdSourceStatusListener() {
            @Override
            public void onAdSourceBiddingAttempt(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAdSourceBiddingAttempt: " + mPlacementId );
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
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
                            synchronized (VideoHelper.this) {
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
                            synchronized (VideoHelper.this) {
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
                            synchronized (VideoHelper.this) {
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
                            synchronized (VideoHelper.this) {
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
                            synchronized (VideoHelper.this) {
                                mListener.onAdSourceLoadFail(mPlacementId, atAdInfo.toString(), adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }
        });

        MsgTools.printMsg("initVideo 3: " + mPlacementId);

        try {
            if (ATSDK.isCnSDK()) {
                mRewardVideoAd.setAdDownloadListener(DownloadHelper.getDownloadListener(mPlacementId));
            }
        } catch (Throwable e) {
        }
    }

    public void fillVideo(final String jsonMap) {
        MsgTools.printMsg("fillVideo start: " + mPlacementId + ", jsonMap: " + jsonMap);
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

                if (mRewardVideoAd != null) {

                    if (!TextUtils.isEmpty(userId) || !TextUtils.isEmpty(userExtraData)) {

                        localExtra.put("user_id", userId);
                        localExtra.put("user_custom_data", userExtraData);

                        MsgTools.printMsg("fillVideo: " + mPlacementId + ", userId:" + userId + ", userExtraData:" + userExtraData);
                    }

                    mRewardVideoAd.setLocalExtra(localExtra);
                    mRewardVideoAd.load();
                } else {
                    MsgTools.printMsg("fillVideo error, you must call initVideo first " + mPlacementId);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (VideoHelper.this) {
                                    mListener.onRewardedVideoAdFailed(mPlacementId, "-1", "you must call initVideo first");
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    public void showVideo(final String jsonMap) {
        MsgTools.printMsg("showVideo: " + mPlacementId + ", jsonMap: " + jsonMap);
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
                    MsgTools.printMsg("showVideo: " + mPlacementId + ", scenario: " + scenario);
                    if (!TextUtils.isEmpty(scenario)) {
                        mRewardVideoAd.show(mActivity, scenario);
                    } else {
                        mRewardVideoAd.show(mActivity);
                    }
                } else {
                    MsgTools.printMsg("showVideo error, you must call initVideo first " + mPlacementId);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (VideoHelper.this) {
                                    mListener.onRewardedVideoAdFailed(mPlacementId, "-1", "you must call initVideo first");
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
            if (mRewardVideoAd != null) {
                boolean isAdReady = mRewardVideoAd.isAdReady();
                MsgTools.printMsg("isAdReady: " + mPlacementId + isAdReady);
                return isAdReady;
            } else {
                MsgTools.printMsg("isAdReady error, you must call initVideo first ");

            }
            MsgTools.printMsg("isAdReady end: " + mPlacementId);
        } catch (Exception e) {
            MsgTools.printMsg("isAdReady Exception: " + e.getMessage());
            return isReady;

        } catch (Throwable e) {
            MsgTools.printMsg("isAdReady Throwable:" + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public String checkAdStatus() {
        MsgTools.printMsg("checkAdStatus: " + mPlacementId);
        if (mRewardVideoAd != null) {
            ATAdStatusInfo atAdStatusInfo = mRewardVideoAd.checkAdStatus();
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

        if (mRewardVideoAd != null) {
            JSONArray jsonArray = new JSONArray();

            List<ATAdInfo> vaildAds = mRewardVideoAd.checkValidAdCaches();
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
                    ATRewardVideoAd.entryAdScenario(mPlacementId, scenarioId);
                } else {
                    MsgTools.printMsg("entryAdScenario error, you must call initVideo first " + mPlacementId);
                }
            }
        });
    }
}
