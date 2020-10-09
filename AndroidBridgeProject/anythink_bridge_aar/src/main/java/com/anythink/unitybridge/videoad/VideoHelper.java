package com.anythink.unitybridge.videoad;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.network.adcolony.AdColonyATConst;
import com.anythink.network.adcolony.AdColonyRewardedVideoSetting;
import com.anythink.network.admob.AdmobATConst;
import com.anythink.network.admob.AdmobRewardedVideoSetting;
import com.anythink.network.applovin.ApplovinATConst;
import com.anythink.network.applovin.ApplovinRewardedVideoSetting;
import com.anythink.network.chartboost.ChartboostATConst;
import com.anythink.network.chartboost.ChartboostRewardedVideoSetting;
import com.anythink.network.flurry.FlurryATConst;
import com.anythink.network.flurry.FlurryRewardedVideoSetting;
import com.anythink.network.inmobi.InmobiATConst;
import com.anythink.network.inmobi.InmobiRewardedVideoSetting;
import com.anythink.network.ironsource.IronsourceATConst;
import com.anythink.network.ironsource.IronsourceRewardedVideoSetting;
import com.anythink.network.mintegral.MintegralATConst;
import com.anythink.network.mintegral.MintegralRewardedVideoSetting;
import com.anythink.network.mopub.MopubATConst;
import com.anythink.network.mopub.MopubRewardedVideoSetting;
import com.anythink.network.tapjoy.TapjoyATConst;
import com.anythink.network.tapjoy.TapjoyRewardedVideoSetting;
import com.anythink.network.toutiao.TTATConst;
import com.anythink.network.toutiao.TTRewardedVideoSetting;
import com.anythink.network.unityads.UnityAdsATConst;
import com.anythink.network.unityads.UnityAdsRewardedVideoSetting;
import com.anythink.network.vungle.VungleATConst;
import com.anythink.network.vungle.VungleRewardedVideoSetting;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoListener;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.utils.Const;
import com.anythink.unitybridge.utils.TaskManager;

import org.json.JSONObject;

import java.util.Iterator;


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
    ATRewardVideoAd mRewardVideoAd;
    String mUnitId;

    boolean isReady = false;
    boolean isReward = false;

    public VideoHelper(VideoListener pListener) {
        MsgTools.pirntMsg("VideoHelper >>> " + this);
        if (pListener == null) {
            MsgTools.pirntMsg("Listener == null ..");
        }
        mListener = pListener;
        mActivity = UnityPluginUtils.getActivity("VideoHelper");
    }


//    public void addSetting(){
//
//
//            AdmobRewardedVideoSetting _admobATMediationSetting = new AdmobRewardedVideoSetting();
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_ADMOB, _admobATMediationSetting);
//
//            MintegralRewardedVideoSetting _mintegralATMediationSetting = new MintegralRewardedVideoSetting();
//
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_MINTEGRAL, _mintegralATMediationSetting);
//
//
//            ApplovinRewardedVideoSetting _applovinATMediationSetting = new ApplovinRewardedVideoSetting();
//
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_APPLOVIN, _applovinATMediationSetting);
//
//
//            FlurryRewardedVideoSetting _flurryATMediationSetting = new FlurryRewardedVideoSetting();
//
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_FLURRY, _flurryATMediationSetting);
//
//
//            InmobiRewardedVideoSetting _inmobiATMediationSetting = new InmobiRewardedVideoSetting();
//
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_INMOBI, _inmobiATMediationSetting);
//
//
//            MopubRewardedVideoSetting _mopubATMediationSetting = new MopubRewardedVideoSetting();
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_MOPUB, _mopubATMediationSetting);
//
//
//            ChartboostRewardedVideoSetting _chartboostATMediationSetting = new ChartboostRewardedVideoSetting();
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_CHARTBOOST, _chartboostATMediationSetting);
//
//            TapjoyRewardedVideoSetting _tapjoyATMediationSetting = new TapjoyRewardedVideoSetting();
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_TAPJOY, _tapjoyATMediationSetting);
//
//            IronsourceRewardedVideoSetting _ironsourceATMediationSetting = new IronsourceRewardedVideoSetting();
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_IRONSOURCE, _ironsourceATMediationSetting);
//
//            UnityAdsRewardedVideoSetting _unityAdATMediationSetting = new UnityAdsRewardedVideoSetting();
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_UNITYADS, _unityAdATMediationSetting);
//
//            VungleRewardedVideoSetting vungleRewardVideoSetting = new VungleRewardedVideoSetting();
//            vungleRewardVideoSetting.setOrientation(2);
//            vungleRewardVideoSetting.setSoundEnable(true);
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_VUNGLE, vungleRewardVideoSetting);
//
//
//            AdColonyUparpuRewardedVideoSetting adColonyUparpuRewardVideoSetting = new AdColonyUparpuRewardedVideoSetting();
//            adColonyUparpuRewardVideoSetting.setEnableConfirmationDialog(false);
//            adColonyUparpuRewardVideoSetting.setEnableResultsDialog(false);
//            mRewardVideoAd.addSetting(ATNetworkType.NETWORK_ADCOLONY, adColonyUparpuRewardVideoSetting);
//
//
//
//    }

    public void initVideo(final String unitid) {
        MsgTools.pirntMsg("initVideo 1>>> " + this);

        mRewardVideoAd = new ATRewardVideoAd(mActivity, unitid);
        mUnitId = unitid;


        MsgTools.pirntMsg("initVideo 2>>> " + this);

        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                MsgTools.pirntMsg("onRewardedVideoAdLoaded ..");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        isReady = true;
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdLoaded(mUnitId);
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdFailed(final AdError pAdError) {
                MsgTools.pirntMsg("onRewardedVideoAdFailed >>" + pAdError.printStackTrace());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdFailed(mUnitId, pAdError.getCode(), pAdError.printStackTrace());
                            }
                        } else {
                            MsgTools.pirntMsg("onRewardedVideoAdFailed callnoback>>" + pAdError.printStackTrace());
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayStart(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayStart ..");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayStart(mUnitId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayEnd(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayEnd ..");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayEnd(mUnitId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayFailed(final AdError pAdError, ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayFailed .." + pAdError.printStackTrace());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayFailed(mUnitId, pAdError.getCode(), pAdError.printStackTrace());
                            }
                        }
                    }
                });
            }


            @Override
            public void onRewardedVideoAdClosed(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdClosed ..");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdClosed(mUnitId, isReward, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onRewardedVideoAdPlayClicked(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayClicked ..");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onRewardedVideoAdPlayClicked(mUnitId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onReward(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onReward ..");
                isReward = true;
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (VideoHelper.this) {
                                mListener.onReward(mUnitId, adInfo.toString());
                            }
                        }
                    }
                });
            }
        });
        MsgTools.pirntMsg("initVideo 3>>> " + this);
    }

    public void setUserData(String userid, String customData) {
        MsgTools.pirntMsg("setUserDate >>>" + this);
        if (mRewardVideoAd != null) {
            mRewardVideoAd.setUserData(userid, customData);
        } else {
            MsgTools.pirntMsg("setUserDate error  ..you must call initVideo first " + this);
            TaskManager.getInstance().run_proxy(new Runnable() {
                @Override
                public void run() {
                    if (mListener != null) {
                        synchronized (VideoHelper.this) {
                            mListener.onRewardedVideoAdFailed(mUnitId, "-1", "you must call initVideo first ..");
                        }
                    }
                }
            });
        }
    }

    public void addsetting(String appsetting) {
        MsgTools.pirntMsg("addsetting >>> " + this + "appsetting map >>>: " + appsetting);
        if (TextUtils.isEmpty(appsetting)) {
            return;
        }
        try {
            JSONObject _jsonObject = new JSONObject(appsetting);
            Iterator _iterator = _jsonObject.keys();
            String key, json;
            int networkType = 0;
            while (_iterator.hasNext()) {
                key = (String) _iterator.next();
                json = _jsonObject.getString(key);

                MsgTools.pirntMsg("addsetting >>> " + this + "NETWORK_(" + key + ") -->appsetting map >>>: " + json);
                networkType = Integer.parseInt(key);
                try {
                    switch (networkType) {
                        case AdmobATConst.NETWORK_FIRM_ID:

                            AdmobRewardedVideoSetting _admobATMediationSetting = new AdmobRewardedVideoSetting();

                            mRewardVideoAd.addSetting(AdmobATConst.NETWORK_FIRM_ID, _admobATMediationSetting);

                            break;
                        case MintegralATConst.NETWORK_FIRM_ID:
                            MintegralRewardedVideoSetting _mintegralATMediationSetting = new MintegralRewardedVideoSetting();

                            mRewardVideoAd.addSetting(MintegralATConst.NETWORK_FIRM_ID, _mintegralATMediationSetting);

                            break;
                        case ApplovinATConst.NETWORK_FIRM_ID:
                            ApplovinRewardedVideoSetting _applovinATMediationSetting = new ApplovinRewardedVideoSetting();
                            mRewardVideoAd.addSetting(ApplovinATConst.NETWORK_FIRM_ID, _applovinATMediationSetting);

                            break;
                        case FlurryATConst.NETWORK_FIRM_ID:
                            FlurryRewardedVideoSetting _flurryATMediationSetting = new FlurryRewardedVideoSetting();

                            mRewardVideoAd.addSetting(FlurryATConst.NETWORK_FIRM_ID, _flurryATMediationSetting);

                            break;
                        case InmobiATConst.NETWORK_FIRM_ID:

                            InmobiRewardedVideoSetting _inmobiATMediationSetting = new InmobiRewardedVideoSetting();

                            mRewardVideoAd.addSetting(InmobiATConst.NETWORK_FIRM_ID, _inmobiATMediationSetting);

                            break;
                        case MopubATConst.NETWORK_FIRM_ID:

                            MopubRewardedVideoSetting _mopubATMediationSetting = new MopubRewardedVideoSetting();
                            mRewardVideoAd.addSetting(MopubATConst.NETWORK_FIRM_ID, _mopubATMediationSetting);

                            break;
                        case ChartboostATConst.NETWORK_FIRM_ID:
                            ChartboostRewardedVideoSetting _chartboostATMediationSetting = new ChartboostRewardedVideoSetting();
                            mRewardVideoAd.addSetting(ChartboostATConst.NETWORK_FIRM_ID, _chartboostATMediationSetting);

                            break;
                        case TapjoyATConst.NETWORK_FIRM_ID:
                            TapjoyRewardedVideoSetting _tapjoyATMediationSetting = new TapjoyRewardedVideoSetting();
                            mRewardVideoAd.addSetting(TapjoyATConst.NETWORK_FIRM_ID, _tapjoyATMediationSetting);

                            break;
                        case IronsourceATConst.NETWORK_FIRM_ID:

                            IronsourceRewardedVideoSetting _ironsourceATMediationSetting = new IronsourceRewardedVideoSetting();
                            mRewardVideoAd.addSetting(IronsourceATConst.NETWORK_FIRM_ID, _ironsourceATMediationSetting);

                            break;
                        case UnityAdsATConst.NETWORK_FIRM_ID:
                            UnityAdsRewardedVideoSetting _unityAdATMediationSetting = new UnityAdsRewardedVideoSetting();
                            mRewardVideoAd.addSetting(UnityAdsATConst.NETWORK_FIRM_ID, _unityAdATMediationSetting);

                            break;
                        case VungleATConst.NETWORK_FIRM_ID:

                            VungleRewardedVideoSetting vungleRewardVideoSetting = new VungleRewardedVideoSetting();
                            JSONObject temp = new JSONObject(json);
                            if (json.contains("orientation")) {
                                vungleRewardVideoSetting.setOrientation(temp.getInt("orientation"));
                            }

                            if (json.contains("isSoundEnable")) {
                                vungleRewardVideoSetting.setSoundEnable(temp.getBoolean("isSoundEnable"));
                            }


                            if (json.contains("isBackButtonImmediatelyEnable")) {
                                vungleRewardVideoSetting.setBackButtonImmediatelyEnable(temp.getBoolean("isBackButtonImmediatelyEnable"));
                            }


                            mRewardVideoAd.addSetting(VungleATConst.NETWORK_FIRM_ID, vungleRewardVideoSetting);

                            break;
                        case AdColonyATConst.NETWORK_FIRM_ID:
                            AdColonyRewardedVideoSetting adColonyUparpuRewardVideoSetting = new AdColonyRewardedVideoSetting();


                            JSONObject temp1 = new JSONObject(json);
                            if (json.contains("enableConfirmationDialog")) {
                                adColonyUparpuRewardVideoSetting.setEnableConfirmationDialog(temp1.getBoolean("enableConfirmationDialog"));
                            }


                            if (json.contains("enableResultsDialog")) {
                                adColonyUparpuRewardVideoSetting.setEnableResultsDialog(temp1.getBoolean("enableResultsDialog"));
                            }


                            mRewardVideoAd.addSetting(AdColonyATConst.NETWORK_FIRM_ID, adColonyUparpuRewardVideoSetting);

                            break;

                        case TTATConst.NETWORK_FIRM_ID:

                            TTRewardedVideoSetting ttRewardedVideoSetting = new TTRewardedVideoSetting();

                            JSONObject temp2 = new JSONObject(json);
                            if (json.contains("requirePermission")) {
                                ttRewardedVideoSetting.setRequirePermission(temp2.getBoolean("requirePermission"));
                            }

                            if (json.contains("orientation")) {
                                ttRewardedVideoSetting.setVideoOrientation(temp2.getInt("orientation"));
                            }

                            if (json.contains("supportDeepLink")) {
                                ttRewardedVideoSetting.setSupportDeepLink(temp2.getBoolean("supportDeepLink"));
                            }

                            if (json.contains("rewardName")) {
                                ttRewardedVideoSetting.setRewardName(temp2.getString("rewardName"));
                            }

                            if (json.contains("rewardCount")) {
                                ttRewardedVideoSetting.setRewardAmount(temp2.getInt("rewardCount"));
                            }


                            ttRewardedVideoSetting.setRequirePermission(true);
                            mRewardVideoAd.addSetting(TTATConst.NETWORK_FIRM_ID, ttRewardedVideoSetting);

                            break;

                        default:

                    }
                } catch (Exception e) {

                }

            }
        } catch (Exception e) {
            if (Const.DEBUG) {
                e.printStackTrace();
            }
        }

    }


    public void fillVideo(final String jsonMap) {
        MsgTools.pirntMsg("fillVideo start:" + jsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String userId = "";
                String userExtraData = "";

                try {
                    if (!TextUtils.isEmpty(jsonMap)) {
                        JSONObject jsonObject = new JSONObject(jsonMap);
                        if (jsonObject.has("UserId")) {
                            userId = jsonObject.optString("UserId");
                        }

                        if (jsonObject.has("UserExtraData")) {
                            userExtraData = jsonObject.optString("UserId");
                        }
                    }
                } catch (Exception e) {

                }

                if (mRewardVideoAd != null) {
                    if (!TextUtils.isEmpty(userExtraData) || !TextUtils.isEmpty(userExtraData)) {
                        MsgTools.pirntMsg("fillVideo userId:" + userId + "-- userExtraData:" + userExtraData + "   " + this);
                        mRewardVideoAd.setUserData(userId, userExtraData);
                    }
                    mRewardVideoAd.load();
                } else {
                    MsgTools.pirntMsg("fillVideo error  ..you must call initVideo first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (VideoHelper.this) {
                                    mListener.onRewardedVideoAdFailed(mUnitId, "-1", "you must call initVideo first ..");
                                }
                            }
                        }
                    });
                }

            }
        });
    }


    public void showVideo(final String jsonMap) {
        MsgTools.pirntMsg("showVideo >>> " + this + ", jsonMap >>> " + jsonMap);
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
                    MsgTools.pirntMsg("showVideo >>> " + this + ", scenario >>> " + scenario);
                    mRewardVideoAd.show(mActivity, scenario);
                } else {
                    MsgTools.pirntMsg("showVideo error  ..you must call initVideo first " + this);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (VideoHelper.this) {
                                    mListener.onRewardedVideoAdFailed(mUnitId, "-1", "you must call initVideo first ..");
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("isAdReady >start>> " + this);

        try {
            if (mRewardVideoAd != null) {
                boolean isAdReady = mRewardVideoAd.isAdReady();
                MsgTools.pirntMsg("isAdReady >>> " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.pirntMsg("isAdReady error  ..you must call initVideo first ");

            }
            MsgTools.pirntMsg("isAdReady >ent>> " + this);
        } catch (Exception e) {
            MsgTools.pirntMsg("isAdReady >Exception>> " + e.getMessage());
            return isReady;

        } catch (Throwable e) {
            MsgTools.pirntMsg("isAdReady >Throwable>> " + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public void clean() {
        MsgTools.pirntMsg("clean >>> " + this);
        if (mRewardVideoAd != null) {

            isReady = false;
            mRewardVideoAd.clean();
        } else {
            MsgTools.pirntMsg("clean error  ..you must call initVideo first ");

        }

    }


    public void onPause() {
        MsgTools.pirntMsg("onPause-->");
        if (mRewardVideoAd != null) {
            mRewardVideoAd.onPause();
        }
    }

    public void onResume() {
        MsgTools.pirntMsg("onResume-->");
        if (mRewardVideoAd != null) {
            mRewardVideoAd.onResume();
        }
    }
}
