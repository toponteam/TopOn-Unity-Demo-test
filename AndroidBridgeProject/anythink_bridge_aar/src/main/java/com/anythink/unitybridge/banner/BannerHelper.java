package com.anythink.unitybridge.banner;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.anythink.banner.api.ATBannerListener;
import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdSourceStatusListener;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.AdError;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.download.DownloadHelper;
import com.anythink.unitybridge.utils.Const;
import com.anythink.unitybridge.utils.TaskManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BannerHelper {

    private static final String kATBannerAdShowingPisitionTop = "top";
    private static final String kATBannerAdShowingPisitionBottom = "bottom";

    private final String TAG = getClass().getSimpleName();
    BannerListener mListener;
    Activity mActivity;
    String mPlacementId;

    ATBannerView mBannerView;

    public BannerHelper(BannerListener listener) {
        MsgTools.printMsg("BannerHelper: " + this);
        if (listener == null) {
            MsgTools.printMsg("Listener == null");
        }
        mListener = listener;
        mActivity = UnityPluginUtils.getActivity("BannerHelper");
        mPlacementId = "";
    }

    public void initBanner(String placementId) {
        MsgTools.printMsg("initBanner 1: " + placementId);
        mPlacementId = placementId;

        mBannerView = new ATBannerView(mActivity);
        mBannerView.setPlacementId(mPlacementId);
        MsgTools.printMsg("initBanner 2: " + mPlacementId);
        mBannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {
                MsgTools.printMsg("onBannerLoaded: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerLoaded(mPlacementId);
                            }
                        }
                    }
                });

            }

            @Override
            public void onBannerFailed(final AdError adError) {
                MsgTools.printMsg("onBannerFailed: " + mPlacementId + ", " + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerFailed(mPlacementId, adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }

            @Override
            public void onBannerClicked(final ATAdInfo adInfo) {
                MsgTools.printMsg("onBannerClicked: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerClicked(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onBannerShow(final ATAdInfo adInfo) {
                MsgTools.printMsg("onBannerShow: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerShow(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onBannerClose(final ATAdInfo adInfo) {
                MsgTools.printMsg("onBannerClose: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerClose(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onBannerAutoRefreshed(final ATAdInfo adInfo) {
                MsgTools.printMsg("onBannerAutoRefreshed: " + mPlacementId);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerAutoRefreshed(mPlacementId, adInfo.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onBannerAutoRefreshFail(final AdError adError) {
                MsgTools.printMsg("onBannerAutoRefreshFail: " + mPlacementId + ", " + adError.getFullErrorInfo());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerAutoRefreshFail(mPlacementId, adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }
        });

        mBannerView.setAdSourceStatusListener(new ATAdSourceStatusListener() {
            @Override
            public void onAdSourceBiddingAttempt(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAdSourceBiddingAttempt: " + mPlacementId );
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
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
                            synchronized (BannerHelper.this) {
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
                            synchronized (BannerHelper.this) {
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
                            synchronized (BannerHelper.this) {
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
                            synchronized (BannerHelper.this) {
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
                            synchronized (BannerHelper.this) {
                                mListener.onAdSourceLoadFail(mPlacementId, atAdInfo.toString(), adError.getCode(), adError.getFullErrorInfo());
                            }
                        }
                    }
                });
            }
        });
        
        MsgTools.printMsg("initBanner 3: " + mPlacementId);

        try {
            if (ATSDK.isCnSDK()) {
                mBannerView.setAdDownloadListener(DownloadHelper.getDownloadListener(mPlacementId));
            }
        } catch (Throwable e) {
        }

    }

    public void loadBannerAd(final String jsonMap) {
        MsgTools.printMsg("loadBanner: " + mPlacementId + ", jsonMap: " + jsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    if (!TextUtils.isEmpty(jsonMap)) {//针对 穿山甲第一次banner尺寸不对
                        try {
                            JSONObject jsonObject = new JSONObject(jsonMap);
                            if (jsonObject.has("banner_ad_size")) {
                                String banner_ad_size = jsonObject.getString("banner_ad_size");
                                MsgTools.printMsg("banner_ad_size----> " + banner_ad_size);

                                if (mBannerView != null && !TextUtils.isEmpty(banner_ad_size)) {
                                    String[] size = banner_ad_size.split("x");
                                    MsgTools.printMsg("loadBannerAd, banner_ad_size" + banner_ad_size);
                                    if (mBannerView.getLayoutParams() == null) {
                                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                                        mBannerView.setLayoutParams(lp);
                                    } else {
                                        mBannerView.getLayoutParams().width = Integer.parseInt(size[0]);
                                        mBannerView.getLayoutParams().height = Integer.parseInt(size[1]);
                                    }

                                }
                            }

                            if (jsonObject.has("inline_adaptive_width")) {
                                String adaptive_width = jsonObject.getString("inline_adaptive_width");
                                MsgTools.printMsg("inline_adaptive_width----> " + adaptive_width);
                                jsonObject.put("adaptive_width", adaptive_width);
                            }
                            if (jsonObject.has("inline_adaptive_orientation")) {
                                int adaptive_orientation = jsonObject.getInt("inline_adaptive_orientation");
                                MsgTools.printMsg("inline_adaptive_orientation----> " + adaptive_orientation);
                                jsonObject.put("adaptive_orientation", adaptive_orientation);
                            }

                            if (!jsonObject.has("adaptive_type")) {
                                jsonObject.put("adaptive_type", 0);
                            }

                            Map<String, Object> localExtra = new HashMap<>();
                            Const.fillMapFromJsonObject(localExtra, jsonObject);

                            mBannerView.setLocalExtra(localExtra);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mBannerView.loadAd();
                } else {
                    MsgTools.printMsg("loadBannerAd error, you must call initBanner first " + mPlacementId);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (BannerHelper.this) {
                                    mListener.onBannerFailed(mPlacementId, "-1", "you must call initBanner first");
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    public void loadBannerAd() {
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.loadAd();
                } else {
                    MsgTools.printMsg("loadBannerAd error, you must call initBanner first " + mPlacementId);
                    TaskManager.getInstance().run_proxy(new Runnable() {
                        @Override
                        public void run() {
                            if (mListener != null) {
                                synchronized (BannerHelper.this) {
                                    mListener.onBannerFailed(mPlacementId, "-1", "you must call initBanner first");
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    public void showBannerAd(final int x, final int y, final int width, final int height, final String jsonMap) {
        MsgTools.printMsg("showBanner: " + mPlacementId + ", jsonMap: " + jsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
                    layoutParams.leftMargin = x;
                    layoutParams.topMargin = y;
                    if (mBannerView.getParent() != null) {
                        ((ViewGroup) mBannerView.getParent()).removeView(mBannerView);
                    }

                    setConfig(jsonMap);

                    mActivity.addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.printMsg("show error, you must call initBanner first " + mPlacementId);
                }

            }
        });
    }


    public void showBannerAd(final String position, final String jsonMap) {
        MsgTools.printMsg("showBanner by position: " + mPlacementId + ", jsonMap: " + jsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    int width = 0;
                    int height = 0;
                    if (mBannerView.getLayoutParams() != null) {
                        width = mBannerView.getLayoutParams().width;
                        height = mBannerView.getLayoutParams().height;
                    }
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
                    if (kATBannerAdShowingPisitionTop.equals(position)) {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                    } else {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                    }
                    if (mBannerView.getParent() != null) {
                        ((ViewGroup) mBannerView.getParent()).removeView(mBannerView);
                    }

                    setConfig(jsonMap);

                    mActivity.addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.printMsg("show error, you must call initBanner first " + mPlacementId);
                }

            }
        });
    }

    private void setConfig(String jsonMap) {
        String scenario = "";
        if (!TextUtils.isEmpty(jsonMap)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonMap);
                if (jsonObject.has(Const.SCENARIO)) {
                    scenario = jsonObject.optString(Const.SCENARIO);
                }
            } catch (Exception e) {
                if (Const.DEBUG) {
                    e.printStackTrace();
                }
            }
        }

        MsgTools.printMsg("showBanner: " + mPlacementId + ", scenario: " + scenario);
        if (!TextUtils.isEmpty(scenario)) {
            mBannerView.setScenario(scenario);
        }
    }


    public void showBannerAd() {
        MsgTools.printMsg("showBanner without ATRect: " + mPlacementId);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.VISIBLE);
                } else {
                    MsgTools.printMsg("show error, you must call initBanner first " + mPlacementId);
                }

            }
        });
    }

    public void hideBannerAd() {
        MsgTools.printMsg("hideBannerAd: " + mPlacementId);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.GONE);
                } else {
                    MsgTools.printMsg("hideBannerAd error, you must call initBanner first " + mPlacementId);
                }

            }
        });
    }

    public void cleanBannerAd() {
        MsgTools.printMsg("clean: " + mPlacementId);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null && mBannerView.getParent() != null) {
                    MsgTools.printMsg("clean2: " + mPlacementId);
                    ViewParent viewParent = mBannerView.getParent();
                    ((ViewGroup) viewParent).removeView(mBannerView);
                } else {
                    MsgTools.printMsg("clean3: no banner clean " + mPlacementId);
                }
            }
        });
    }

    public String checkAdStatus() {
        MsgTools.printMsg("checkAdStatus: " + mPlacementId);
        if (mBannerView != null) {
            ATAdStatusInfo atAdStatusInfo = mBannerView.checkAdStatus();
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

        if (mBannerView != null) {
            JSONArray jsonArray = new JSONArray();

            List<ATAdInfo> vaildAds = mBannerView.checkValidAdCaches();
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
}
