package com.ofm.unitybridge.nativead;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.anythink.core.api.ATAdInfo;
import com.anythink.nativead.api.ATNative;
import com.anythink.nativead.api.ATNativeAdView;
import com.anythink.nativead.api.ATNativeDislikeListener;
import com.anythink.nativead.api.NativeAd;
import com.ofm.core.api.OfmAdError;
import com.ofm.core.api.OfmAdInfo;
import com.ofm.nativead.api.OfmNative;
import com.ofm.nativead.api.OfmNativeAd;
import com.ofm.nativead.api.OfmNativeEventListener;
import com.ofm.nativead.api.OfmNativeLoadListener;
import com.ofm.unitybridge.MsgTools;
import com.ofm.unitybridge.UnityPluginUtils;
import com.ofm.unitybridge.utils.Const;
import com.ofm.unitybridge.utils.TaskManager;

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

    String mPlacementId;

    OfmNative mOfmNative;
    OfmNativeAd mNativeAd;
    private View mAdView;

    public NativeHelper(NativeListener pListener) {
        MsgTools.pirntMsg("NativeHelper: " + this);
        if (pListener == null) {
            Log.e(TAG, "Listener == null");
        }
        mListener = pListener;
        mActivity = UnityPluginUtils.getActivity("NativeHelper");
        mPlacementId = "";
    }

    public void initNative(String placementId) {
        MsgTools.pirntMsg("initNative 1: " + this);
        mPlacementId = placementId;
        mOfmNative = new OfmNative(mActivity, placementId);

        mOfmNative.setAdListener(new OfmNativeLoadListener() {
            @Override
            public void onNativeLoaded(OfmNativeAd ofmNativeAd) {
                mNativeAd = ofmNativeAd;
                MsgTools.pirntMsg("onNativeLoaded");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (NativeHelper.this) {
                                mListener.onNativeAdLoaded(mPlacementId);
                            }
                        }
                    }
                });
            }

            @Override
            public void onNativeFailed(final OfmAdError adError) {
                MsgTools.pirntMsg("onNativeFailed: " + adError.toString());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (NativeHelper.this) {
                                mListener.onNativeAdLoadFail(mPlacementId, adError.getErrorCode(), adError.toString());
                            }
                        }
                    }
                });
            }
        });

        MsgTools.pirntMsg("initNative 2");
    }

    public void loadNative(final String jsonMap, final String customRuleMapJson) {
        MsgTools.pirntMsg("loadNative, jsonMap: " + jsonMap + ", customRuleMapJson: " + customRuleMapJson);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOfmNative == null) {
                    Log.e(TAG, "you must call initNative first");
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (NativeHelper.this) {
                                    mListener.onNativeAdLoadFail(mPlacementId, "-1", "you must call initNative first");
                                }
                            }
                        }
                    });
                    return;
                }

                Map<String, Object> localExtra = new HashMap<>();
                try {
                    JSONObject _jsonObject = new JSONObject(jsonMap);
                    Iterator _iterator = _jsonObject.keys();
                    String key;
                    while (_iterator.hasNext()) {
                        key = (String) _iterator.next();
                        localExtra.put(key, _jsonObject.get(key));

                        if (TextUtils.equals(Const.Native.native_ad_size, key)) {
                            String native_ad_size = _jsonObject.optString(key);

                            if (!TextUtils.isEmpty(native_ad_size)) {
                                String[] sizes = native_ad_size.split("x");
                                localExtra.put("key_width", sizes[0]);
                                localExtra.put("key_height", sizes[1]);
                            }
                        }
                    }
                    mOfmNative.setConfigMap(localExtra);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Map<String, Object> customRuleMap = new HashMap<>();
                try {
                    JSONObject jsonObject = new JSONObject(customRuleMapJson);

                    Const.fillMapFromJsonObject(customRuleMap, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mOfmNative.load(customRuleMap);
            }
        });
    }

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
                if (mNativeAd != null) {
                    MsgTools.pirntMsg("show nativeAd:" + mNativeAd.toString());
                    pViewInfo = parseViewInfo(showConfig);
                    currViewInfo.add(pViewInfo);
                    mNativeAd.setEventListener(new OfmNativeEventListener() {
                        @Override
                        public void onAdImpressed(final OfmAdInfo adInfo) {
                            MsgTools.pirntMsg("native onAdImpressed");
                            mNativeAd = null;
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        synchronized (NativeHelper.this) {
                                            mListener.onAdImpressed(mPlacementId, adInfo.toString());
                                        }
                                    }
                                }
                            });
                        }

                        @Override
                        public void onAdClicked(final OfmAdInfo adInfo) {
                            MsgTools.pirntMsg("native onAdClicked");
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        synchronized (NativeHelper.this) {
                                            mListener.onAdClicked(mPlacementId, adInfo.toString());
                                        }
                                    }
                                }
                            });
                        }

                        @Override
                        public void onAdVideoStart() {
                            MsgTools.pirntMsg("native onAdVideoStart");
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        synchronized (NativeHelper.this) {
                                            mListener.onAdVideoStart(mPlacementId);
                                        }
                                    }
                                }
                            });
                        }

                        @Override
                        public void onAdVideoEnd() {
                            MsgTools.pirntMsg("native onAdVideoEnd");
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        synchronized (NativeHelper.this) {
                                            mListener.onAdVideoEnd(mPlacementId);
                                        }
                                    }
                                }
                            });

                        }

                        @Override
                        public void onAdVideoProgress(final int progress) {
                            MsgTools.pirntMsg("native onAdVideoProgress");
                            TaskManager.getInstance().run_proxy(new Runnable() {
                                @Override
                                public void run() {
                                    if (mListener != null) {
                                        synchronized (NativeHelper.this) {
                                            mListener.onAdVideoProgress(mPlacementId, progress);
                                        }
                                    }
                                }
                            });

                        }
                    });

//                    mNativeAd.setDislikeCallbackListener(new ATNativeDislikeListener() {
//                        @Override
//                        public void onAdCloseButtonClick(ATNativeAdView atNativeAdView, final ATAdInfo atAdInfo) {
//                            TaskManager.getInstance().run_proxy(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (mListener != null) {
//                                        synchronized (NativeHelper.this) {
//                                            mListener.onAdCloseButtonClicked(mPlacementId, atAdInfo.toString());
//                                        }
//                                    }
//                                }
//                            });
//                        }
//                    });


                    mAdView = null;
                    try {
                        mAdView = mNativeAd.renderWithView(mActivity, new ATUnityRender(mActivity, pViewInfo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    if (pViewInfo.adLogoView != null) {
//                        FrameLayout.LayoutParams adLogoLayoutParams = new FrameLayout.LayoutParams(pViewInfo.adLogoView.mWidth, pViewInfo.adLogoView.mHeight);
//                        adLogoLayoutParams.leftMargin = pViewInfo.adLogoView.mX;
//                        adLogoLayoutParams.topMargin = pViewInfo.adLogoView.mY;
//                        nativeAd.prepare(mATNativeAdView, adLogoLayoutParams);
//                        MsgTools.pirntMsg("prepare native ad with logo:" + mPlacementId);
//                    } else {
//                        nativeAd.prepare(mATNativeAdView);
//                        MsgTools.pirntMsg("prepare native ad:" + mPlacementId);
//                    }

                    ViewInfo.addNativeAdView2Activity(mActivity, pViewInfo, mAdView);
                } else {
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (NativeHelper.this) {
                                    mListener.onNativeAdLoadFail(mPlacementId, "-1", "onNativeAdLoadFail");
                                }
                            }
                        }
                    });
                }
            }
        });


    }


    public boolean isAdReady() {
        return mNativeAd != null;
    }

    public void clean() {
//        if (mATNative != null) {
//            mATNative.destory();
//        }
    }

    static List<ViewInfo> currViewInfo = new ArrayList<>();

    public void cleanView() {

        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (ViewInfo tempinfo : currViewInfo) {
                        if (mAdView != null) {
                            ViewGroup _viewGroup = (ViewGroup) mAdView.getParent();
                            if (_viewGroup != null) {
                                _viewGroup.removeView(mAdView);

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
//        if (mNativeAd != null) {
//            mNativeAd.onPause();
//        }
    }

    public void onResume() {
//        if (mNativeAd != null) {
//            mNativeAd.onResume();
//        }
    }
}
