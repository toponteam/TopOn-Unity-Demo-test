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
import com.anythink.core.api.AdError;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.UnityPluginUtils;
import com.anythink.unitybridge.imgutil.TaskManager;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerHelper {

    private static final String kATBannerAdShowingPisitionTop = "top";
    private static final String kATBannerAdShowingPisitionBottom = "bottom";

    private final String TAG = getClass().getSimpleName();
    BannerListener mListener;
    Activity mActivity;
    String mUnitId;

    ATBannerView mBannerView;

    public BannerHelper(BannerListener listener) {
        MsgTools.pirntMsg("BannerHelper >>> " + this);
        if (listener == null) {
            MsgTools.pirntMsg("Listener == null ..");
        }
        mListener = listener;
        mActivity = UnityPluginUtils.getActivity("BannerHelper");
        mUnitId = "";
    }

    /**
     * 初始化Banner对象
     *
     * @param unitId
     */
    public void initBanner(String unitId) {
        MsgTools.pirntMsg("initBanner 1>>> " + this);
        mUnitId = unitId;

        mBannerView = new ATBannerView(mActivity);
        mBannerView.setUnitId(mUnitId);
        MsgTools.pirntMsg("initBanner 2>>> " + this);
        mBannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {
                MsgTools.pirntMsg("initBanner onBannerLoaded>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onBannerLoaded(mUnitId);
                        }
                    }
                });

            }

            @Override
            public void onBannerFailed(final AdError adError) {
                MsgTools.pirntMsg("initBanner onBannerFailed>>> " + adError.printStackTrace());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onBannerFailed(mUnitId, adError.getCode(), adError.printStackTrace());
                        }
                    }
                });
            }

            @Override
            public void onBannerClicked(ATAdInfo adInfo) {
                MsgTools.pirntMsg("initBanner onBannerClicked>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onBannerClicked(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onBannerShow(ATAdInfo adInfo) {
                MsgTools.pirntMsg("initBanner onBannerShow>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onBannerShow(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onBannerClose() {
                MsgTools.pirntMsg("initBanner onBannerClose>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onBannerClose(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onBannerAutoRefreshed(ATAdInfo adInfo) {
                MsgTools.pirntMsg("initBanner onBannerAutoRefreshed>>> ");
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onBannerAutoRefreshed(mUnitId);
                        }
                    }
                });
            }

            @Override
            public void onBannerAutoRefreshFail(final AdError adError) {
                MsgTools.pirntMsg("initBanner onBannerAutoRefreshFail>>> " + adError.printStackTrace());
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onBannerAutoRefreshFail(mUnitId, adError.getCode(), adError.printStackTrace());
                        }
                    }
                });
            }
        });
        MsgTools.pirntMsg("initBanner 3>>> " + this);
    }

    /**
     * 加载banner广告
     *
     * @param jsonMap
     */
    public void loadBannerAd(final String jsonMap) {
        MsgTools.pirntMsg("jsonMap - " + jsonMap);
        UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    if(!TextUtils.isEmpty(jsonMap)) {//针对 穿山甲第一次banner尺寸不对
                        try {
                            JSONObject jsonObject = new JSONObject(jsonMap);
                            if (jsonObject.has("banner_ad_size")) {
                                String banner_ad_size = jsonObject.getString("banner_ad_size");
                                MsgTools.pirntMsg("banner_ad_size----> " + banner_ad_size);

                                if (mBannerView != null && !TextUtils.isEmpty(banner_ad_size)) {
                                    String[] size = banner_ad_size.split("x");
                                    MsgTools.pirntMsg("loadBannerAd, banner_ad_size" + banner_ad_size);
                                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mBannerView.getLayoutParams();
                                    if (lp == null) {
                                        lp = new FrameLayout.LayoutParams(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                                    } else {
                                        lp.width = Integer.parseInt(size[0]);
                                        lp.height = Integer.parseInt(size[1]);
                                    }
                                    mBannerView.setLayoutParams(lp);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mBannerView.loadAd();
                } else {
                    MsgTools.pirntMsg("loadBannerAd error  ..you must call initBanner first " + this);
                    if (mListener != null) {
                        mListener.onBannerFailed(mUnitId, "-1", "you must call initBanner first ..");
                    }
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
                    MsgTools.pirntMsg("loadBannerAd error  ..you must call initBanner first " + this);
                    if (mListener != null) {
                        mListener.onBannerFailed(mUnitId, "-1", "you must call initBanner first ..");
                    }
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
        MsgTools.pirntMsg("showBanner >>> " + this);
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
                    MsgTools.pirntMsg("show error  ..you must call initBanner first " + this);
                }

            }
        });
    }

    /**
     * 展示BannerView（传入位置）
     */
    public void showBannerAd(final String position) {
        MsgTools.pirntMsg("showBanner by position>>> " + this);
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
                    mActivity.addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.pirntMsg("show error  ..you must call initBanner first " + this);
                }

            }
        });
    }

    public void showBannerAd() {
        MsgTools.pirntMsg("showBanner without ATRect >>> " + this);
         UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.VISIBLE);
                } else {
                    MsgTools.pirntMsg("show error  ..you must call initBanner first " + this);
                }

            }
        });
    }

    public void hideBannerAd() {
        MsgTools.pirntMsg("hideBannerAd >>> " + this);
         UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.GONE);
                } else {
                    MsgTools.pirntMsg("hideBannerAd error  ..you must call initBanner first " + this);
                }

            }
        });
    }

    /**
     * 移除BannerView
     */
    public void cleanBannerAd() {
        MsgTools.pirntMsg("clean >>> " + this);
         UnityPluginUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null && mBannerView.getParent() != null) {
                    MsgTools.pirntMsg("clean2 >>> " + this);
                    ViewParent viewParent = mBannerView.getParent();
                    ((ViewGroup) viewParent).removeView(mBannerView);
                } else {
                    MsgTools.pirntMsg("clean3 >>> no banner clean " + this);
                }
            }
        });
    }
}
