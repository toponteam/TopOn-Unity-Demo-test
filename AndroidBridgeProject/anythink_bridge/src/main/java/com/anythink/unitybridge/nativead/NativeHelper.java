package com.anythink.unitybridge.nativead;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.nativead.api.ATNative;
import com.anythink.nativead.api.ATNativeAdView;
import com.anythink.nativead.api.ATNativeEventListener;
import com.anythink.nativead.api.ATNativeNetworkListener;
import com.anythink.nativead.api.NativeAd;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.imgutil.Const;
import com.anythink.unitybridge.imgutil.TaskManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/3.
 * @Email: zhoushubin@salmonads.com
 */

@SuppressWarnings("all")
public class NativeHelper {
    public static final String TAG = UnityPluginUtils.TAG;
    NativeListener mListener;
    Activity mActivity;

    String mUnitId;

    ATNativeAdView mATNativeAdView;
    ATNative mATNative;

    public NativeHelper(NativeListener pListener) {
        if (pListener == null) {
            Log.e(TAG, "Listener == null ..");
        }
        mListener = pListener;
        mActivity = UnityPluginUtils.getActivity("NativeHelper");
        mUnitId = "";
    }

    public void initNative(String unitid, String loalMapJson) {
        MsgTools.pirntMsg("initNative ..");
        mUnitId = unitid;
        mATNative = new ATNative(mActivity, unitid, new ATNativeNetworkListener() {
            @Override
            public void onNativeAdLoaded() {
                MsgTools.pirntMsg("onNativeAdLoaded ..");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onNativeAdLoaded(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onNativeAdLoadFail(final AdError pAdError) {
                MsgTools.pirntMsg("onNativeAdLoadFail .." + pAdError.printStackTrace());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onNativeAdLoadFail(mUnitId, pAdError.getCode(), pAdError.printStackTrace());
                        }
                    }
                });
            }
        });


        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject _jsonObject = new JSONObject(loalMapJson);
            Iterator _iterator = _jsonObject.keys();
            String key;
            while (_iterator.hasNext()) {
                key = (String) _iterator.next();
                map.put(key, _jsonObject.get(key));
            }
            mATNative.setLocalExtra(map);
            if (mATNativeAdView == null) {
                mATNativeAdView = new ATNativeAdView(mActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        MsgTools.pirntMsg("initNative ..2");
    }


    @Deprecated
    public void loadNative(String loalMapJson) {
        this.loadNative();
    }

    public void loadNative() {
        MsgTools.pirntMsg("loadNative ..");

        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mATNative == null) {
                    Log.e(TAG, "you must call initNative first ..");
                    if (mListener != null) {
                        mListener.onNativeAdLoadFail(mUnitId, "-1", "you must call initNative first ..");
                    }
                    return;
                }
                mATNative.makeAdRequest();
            }
        });
    }

    NativeAd mNativeAd;
    ViewInfo pViewInfo;

    private ViewInfo parseViewInfo(String showConfig) {
        pViewInfo = new ViewInfo();

        if (TextUtils.isEmpty(showConfig)) {
            Log.e(TAG, "showConfig is null ,user defult");
            pViewInfo = ViewInfo.createDefualtView(mActivity);
        }

        try {
            JSONObject _jsonObject = new JSONObject(showConfig);

            if (_jsonObject.has("parent")) {
                String tempjson = _jsonObject.getString("parent");
                MsgTools.pirntMsg("parent----> " + tempjson);
                pViewInfo.rootView = pViewInfo.parseINFO(tempjson, "parent", 0, 0);
            }

            if (_jsonObject.has("appIcon")) {
                String tempjson = _jsonObject.getString("appIcon");
                MsgTools.pirntMsg("appIcon----> " + tempjson);
                pViewInfo.IconView = pViewInfo.parseINFO(tempjson, "appIcon", 0, 0);
            }

            if (_jsonObject.has("mainImage")) {
                String tempjson = _jsonObject.getString("mainImage");
                MsgTools.pirntMsg("mainImage----> " + tempjson);
                pViewInfo.imgMainView = pViewInfo.parseINFO(tempjson, "mainImage", 0, 0);

            }

            if (_jsonObject.has("title")) {
                String tempjson = _jsonObject.getString("title");
                MsgTools.pirntMsg("title----> " + tempjson);
                pViewInfo.titleView = pViewInfo.parseINFO(tempjson, "title", 0, 0);
            }

            if (_jsonObject.has("desc")) {
                String tempjson = _jsonObject.getString("desc");
                MsgTools.pirntMsg("desc----> " + tempjson);
                pViewInfo.descView = pViewInfo.parseINFO(tempjson, "desc", 0, 0);
            }

            if (_jsonObject.has("adLogo")) {
                String tempjson = _jsonObject.getString("adLogo");
                MsgTools.pirntMsg("adLogo----> " + tempjson);
                pViewInfo.adLogoView = pViewInfo.parseINFO(tempjson, "adLogo", 0, 0);
            }

            if (_jsonObject.has("cta")) {
                String tempjson = _jsonObject.getString("cta");
                MsgTools.pirntMsg("cta----> " + tempjson);
                pViewInfo.ctaView = pViewInfo.parseINFO(tempjson, "cta", 0, 0);
            }


        } catch (JSONException pE) {
            pE.printStackTrace();
        }
        return pViewInfo;
    }


    public void show(final String showConfig) {
        MsgTools.pirntMsg("show" + showConfig);

        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NativeAd nativeAd = mATNative.getNativeAd();
                if (nativeAd != null) {
                    MsgTools.pirntMsg("nativeAd:" + nativeAd.toString());
                    pViewInfo = parseViewInfo(showConfig);
                    currViewInfo.add(pViewInfo);
                    mNativeAd = nativeAd;
                    nativeAd.setNativeEventListener(new ATNativeEventListener() {
                        @Override
                        public void onAdImpressed(ATNativeAdView view, ATAdInfo adInfo) {
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        mListener.onAdImpressed(mUnitId);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onAdClicked(ATNativeAdView view, ATAdInfo adInfo) {
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        mListener.onAdClicked(mUnitId);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onAdVideoStart(ATNativeAdView view) {
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        mListener.onAdVideoStart(mUnitId);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onAdVideoEnd(ATNativeAdView view) {
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        mListener.onAdVideoEnd(mUnitId);
                                    }
                                }
                            });

                        }

                        @Override
                        public void onAdVideoProgress(ATNativeAdView view, final int progress) {
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        mListener.onAdVideoProgress(mUnitId, progress);
                                    }
                                }
                            });

                        }
                    });

                    try {
                        nativeAd.renderAdView(mATNativeAdView, new ATUnityRender(mActivity, pViewInfo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    nativeAd.prepare(mATNativeAdView);

                    ViewInfo.addNativeAdView2Activity(mActivity, pViewInfo, mATNativeAdView);
                } else {
                    if (mListener != null) {
                        mListener.onNativeAdLoadFail(mUnitId, "-1", "onNativeAdLoadFail");
                    }
                }
            }
        });


    }


    public boolean isAdReady() {
        mNativeAd = mATNative.getNativeAd();
        return mNativeAd != null;
    }

    public void clean() {
        if (mATNative != null) {
//            mATNative.destory();
        }
    }

    static List<ViewInfo> currViewInfo = new ArrayList<>();

    public void cleanView() {

        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (ViewInfo tempinfo : currViewInfo) {
                        if (mATNativeAdView != null) {
                            ViewGroup _viewGroup = (ViewGroup) mATNativeAdView.getParent();
                            if (_viewGroup != null) {
                                _viewGroup.removeView(mATNativeAdView);

                            }
                        }
                    }

                } catch (Exception e) {
                    if (Const.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void onPause() {
        if (mNativeAd != null) {
            mNativeAd.onPause();
        }
    }

    public void onResume() {
        if (mNativeAd != null) {
            mNativeAd.onResume();
        }
    }
}
