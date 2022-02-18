package com.ofm.unitybridge.banner;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.ofm.banner.api.OfmBanner;
import com.ofm.banner.api.OfmBannerAd;
import com.ofm.banner.api.OfmBannerEventListener;
import com.ofm.banner.api.OfmBannerLoadListener;
import com.ofm.core.api.OfmAdError;
import com.ofm.core.api.OfmAdInfo;
import com.ofm.unitybridge.MsgTools;
import com.ofm.unitybridge.UnityPluginUtils;
import com.ofm.unitybridge.utils.Const;
import com.ofm.unitybridge.utils.TaskManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BannerHelper {

    private static final String kATBannerAdShowingPisitionTop = "top";
    private static final String kATBannerAdShowingPisitionBottom = "bottom";

    private final String TAG = getClass().getSimpleName();
    BannerListener mListener;
    Activity mActivity;
    String mPlacementId;

    OfmBanner mBanner;
    View mBannerView;

    int adWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
    int adHeight = FrameLayout.LayoutParams.WRAP_CONTENT;

    public BannerHelper(BannerListener listener) {
        MsgTools.pirntMsg("BannerHelper: " + this);
        if (listener == null) {
            MsgTools.pirntMsg("Listener == null");
        }
        mListener = listener;
        mActivity = UnityPluginUtils.getActivity("BannerHelper");
        mPlacementId = "";
    }

    /**
     * 初始化Banner对象
     *
     * @param unitId
     */
    public void initBanner(String unitId) {
        MsgTools.pirntMsg("initBanner 1: " + this);
        mPlacementId = unitId;

        mBanner = new OfmBanner(mActivity, mPlacementId);
        MsgTools.pirntMsg("initBanner 2: " + this);

        mBanner.setAdListener(new OfmBannerLoadListener() {
            @Override
            public void onBannerLoaded(OfmBannerAd ofmBannerAd) {
                mBannerView = ofmBannerAd.getBannerView();

                ofmBannerAd.setEventListener(new OfmBannerEventListener() {

                    @Override
                    public void onBannerClicked(final OfmAdInfo adInfo) {
                        MsgTools.pirntMsg("onBannerClicked");
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
                    public void onBannerShow(final OfmAdInfo adInfo) {
                        MsgTools.pirntMsg("onBannerShow:");
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
                    public void onBannerClose(final OfmAdInfo adInfo) {
                        MsgTools.pirntMsg("onBannerClose:");
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

//                    @Override
//                    public void onBannerAutoRefreshed(final OfmAdInfo adInfo) {
//                        MsgTools.pirntMsg("initBanner onBannerAutoRefreshed: ");
//                        TaskManager.getInstance().run_proxy(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (mListener != null) {
//                                    synchronized (BannerHelper.this) {
//                                        mListener.onBannerAutoRefreshed(mPlacementId, adInfo.toString());
//                                    }
//                                }
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onBannerAutoRefreshFail(final OfmAdError adError) {
//                        MsgTools.pirntMsg("initBanner onBannerAutoRefreshFail: " + adError.toString());
//                        TaskManager.getInstance().run_proxy(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (mListener != null) {
//                                    synchronized (BannerHelper.this) {
//                                        mListener.onBannerAutoRefreshFail(mPlacementId, adError.getErrorCode(), adError.toString());
//                                    }
//                                }
//                            }
//                        });
//                    }
                });

                MsgTools.pirntMsg("onBannerLoaded");
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
            public void onBannerFailed(final OfmAdError adError) {
                MsgTools.pirntMsg("onBannerFailed: " + adError.toString());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            synchronized (BannerHelper.this) {
                                mListener.onBannerFailed(mPlacementId, adError.getErrorCode(), adError.toString());
                            }
                        }
                    }
                });
            }
        });

        MsgTools.pirntMsg("initBanner 3: " + this);
    }

    /**
     * 加载banner广告
     *
     * @param jsonMap
     */
    public void loadBannerAd(final String jsonMap, final String customRuleJsonMap) {
        MsgTools.pirntMsg("loadBannerAd, jsonMap: " + jsonMap + ", customRuleJsonMap: " + customRuleJsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBanner != null) {
                    if (!TextUtils.isEmpty(jsonMap)) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonMap);

                            Map<String, Object> localExtra = new HashMap<>();

                            if (jsonObject.has("banner_ad_size")) {
                                String banner_ad_size = jsonObject.getString("banner_ad_size");
                                MsgTools.pirntMsg("banner_ad_size----> " + banner_ad_size);

                                if (mBanner != null && !TextUtils.isEmpty(banner_ad_size)) {
                                    String[] size = banner_ad_size.split("x");
                                    MsgTools.pirntMsg("loadBannerAd, banner_ad_size" + banner_ad_size);

                                    localExtra.put("key_width", Integer.parseInt(size[0]));
                                    localExtra.put("key_height", Integer.parseInt(size[1]));

                                    adWidth = Integer.parseInt(size[0]);
                                    adHeight = Integer.parseInt(size[1]);

                                }
                            }

                            if (jsonObject.has("inline_adaptive_width")) {
                                String adaptive_width = jsonObject.getString("inline_adaptive_width");
                                MsgTools.pirntMsg("inline_adaptive_width----> " + adaptive_width);
                                jsonObject.put("adaptive_width", adaptive_width);
                            }
                            if (jsonObject.has("inline_adaptive_orientation")) {
                                int adaptive_orientation = jsonObject.getInt("inline_adaptive_orientation");
                                MsgTools.pirntMsg("inline_adaptive_orientation----> " + adaptive_orientation);
                                jsonObject.put("adaptive_orientation", adaptive_orientation);
                            }

                            if (!jsonObject.has("adaptive_type")) {
                                jsonObject.put("adaptive_type", 0);
                            }


                            Const.fillMapFromJsonObject(localExtra, jsonObject);

                            mBanner.setConfigMap(localExtra);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Map<String, Object> customRuleMap = new HashMap<>();
                    if (!TextUtils.isEmpty(customRuleJsonMap)) {
                        try {
                            JSONObject jsonObject = new JSONObject(customRuleJsonMap);

                            Const.fillMapFromJsonObject(customRuleMap, jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mBanner.load(customRuleMap);
                } else {
                    MsgTools.pirntMsg("loadBannerAd error, you must call initBanner first " + this);
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
                if (mBanner != null) {
                    mBanner.load(new HashMap<String, Object>());
                } else {
                    MsgTools.pirntMsg("loadBannerAd error, you must call initBanner first " + this);
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

    /**
     * 展示BannerView
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void showBannerAd(final int x, final int y, final int width, final int height) {
        MsgTools.pirntMsg("showBanner: " + this);
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
                    mActivity.addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.pirntMsg("show error, you must call initBanner first " + this);
                }

            }
        });
    }

    /**
     * 展示BannerView（传入位置）
     */
    public void showBannerAd(final String position) {
        MsgTools.pirntMsg("showBanner by position: " + this);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {

//
//                    if (mBannerView.getLayoutParams() != null) {
//                        width = mBannerView.getLayoutParams().width;
//                        height = mBannerView.getLayoutParams().height;
//                    }
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(adWidth, adHeight);
                    if (kATBannerAdShowingPisitionTop.equals(position)) {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                    } else {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                    }
                    if (mBannerView.getParent() != null) {
                        ((ViewGroup) mBannerView.getParent()).removeView(mBannerView);
                    }
                    mActivity.addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.pirntMsg("show error, you must call initBanner first " + this);
                }

            }
        });
    }

    public void showBannerAd() {
        MsgTools.pirntMsg("showBanner without ATRect: " + this);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.VISIBLE);
                } else {
                    MsgTools.pirntMsg("show error, you must call initBanner first " + this);
                }

            }
        });
    }

    public void hideBannerAd() {
        MsgTools.pirntMsg("hideBannerAd: " + this);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.GONE);
                } else {
                    MsgTools.pirntMsg("hideBannerAd error, you must call initBanner first " + this);
                }

            }
        });
    }

    /**
     * 移除BannerView
     */
    public void cleanBannerAd() {
        MsgTools.pirntMsg("clean: " + this);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null && mBannerView.getParent() != null) {
                    MsgTools.pirntMsg("clean2: " + this);
                    ViewParent viewParent = mBannerView.getParent();
                    ((ViewGroup) viewParent).removeView(mBannerView);
                } else {
                    MsgTools.pirntMsg("clean3: no banner clean " + this);
                }
            }
        });
    }
}
