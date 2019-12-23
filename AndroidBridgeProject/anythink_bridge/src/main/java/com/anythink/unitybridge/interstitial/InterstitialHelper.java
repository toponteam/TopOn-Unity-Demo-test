package com.anythink.unitybridge.interstitial;

import android.app.Activity;
import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.imgutil.TaskManager;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 */
public class InterstitialHelper {
    public static final String TAG = UnityPluginUtils.TAG;
    InterstitialListener mListener;
    Activity mActivity;
    ATInterstitial mInterstitialAd;
    String mUnitId;

    boolean isReady = false;

    public InterstitialHelper(InterstitialListener listener) {
        MsgTools.pirntMsg("InterstitialHelper >>> " + this);
        if (listener == null) {
            Log.e(TAG, "Listener == null ..");
        }
        mListener = listener;
        mActivity = UnityPluginUtils.getActivity("InterstitialHelper");
    }


    public void initInterstitial(final String unitid) {
        MsgTools.pirntMsg("initInterstitial 1>>> " + this);

        mInterstitialAd = new ATInterstitial(mActivity, unitid);
        mUnitId = unitid;


        MsgTools.pirntMsg("initInterstitial 2>>> " + this);

        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdLoaded>>> ");
                isReady = true;
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdLoaded(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdLoadFail(final AdError adError) {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdLoadFail>>> " + adError.printStackTrace());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdLoadFail(mUnitId, adError.getCode(), adError.printStackTrace());
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdClicked(ATAdInfo adInfo) {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdClicked>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdClicked(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdShow(ATAdInfo adInfo) {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdShow>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdShow(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdClose(ATAdInfo adInfo) {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdClose>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdClose(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdVideoStart() {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdVideoStart>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdVideoStart(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdVideoEnd() {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdVideoEnd>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdVideoEnd(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onInterstitialAdVideoError(final AdError adError) {
                MsgTools.pirntMsg("initInterstitial onInterstitialAdVideoError>>> :" + adError.printStackTrace());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onInterstitialAdVideoError(mUnitId, adError.getCode(), adError.printStackTrace());
                        }
                    }
                });
            }
        });
        MsgTools.pirntMsg("initInterstitial 3>>> " + this);
    }


    @Deprecated
    public void loadInterstitialAd(final String jsonMap) {
        this.loadInterstitialAd();
    }

    public void loadInterstitialAd() {

         UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd != null) {

                    mInterstitialAd.load();
                } else {
                    Log.e(TAG, "loadInterstitialAd error  ..you must call initInterstitial first " + this);
                    if (mListener != null) {
                        mListener.onInterstitialAdLoadFail(mUnitId, "-1", "you must call initInterstitial first ..");
                    }
                }

            }
        });
    }

    public void showInterstitialAd() {
        MsgTools.pirntMsg("showInterstitial >>> " + this);
         UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    isReady = false;
                    mInterstitialAd.show();
                } else {
                    Log.e(TAG, "showInterstitial error  ..you must call initInterstitial first " + this);
                    if (mListener != null) {
                        mListener.onInterstitialAdLoadFail(mUnitId, "-1", "you must call initInterstitial first ..");
                    }
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("isAdReady >start>> " + this);

        try {
            if (mInterstitialAd != null) {
                MsgTools.pirntMsg("isAdReady >>> " + mInterstitialAd.isAdReady());
                return mInterstitialAd.isAdReady();
            } else {
                Log.e(TAG, "isAdReady error  ..you must call initInterstitial first ");

            }
            MsgTools.pirntMsg("isAdReady >ent>> " + this);
        } catch (Exception e) {
            MsgTools.pirntMsg("isAdReady >Exception>> " + e.getMessage());
//            e.printStackTrace();
            return isReady;

        } catch (Throwable e) {
            MsgTools.pirntMsg("isAdReady >Throwable>> " + e.getMessage());
//            e.printStackTrace();
            return isReady;
        }
        return isReady;
    }

    public void clean() {
        MsgTools.pirntMsg("clean >>> " + this);
        if (mInterstitialAd != null) {
            isReady = false;
            mInterstitialAd.clean();
        } else {
            Log.e(TAG, "clean error  ..you must call initInterstitial first ");

        }

    }

    public void onPause() {
        MsgTools.pirntMsg("onPause-->");
        if (mInterstitialAd != null) {
            mInterstitialAd.onPause();
        }
    }

    public void onResume() {
        MsgTools.pirntMsg("onResume-->");
        if (mInterstitialAd != null) {
            mInterstitialAd.onResume();
        }
    }
}
