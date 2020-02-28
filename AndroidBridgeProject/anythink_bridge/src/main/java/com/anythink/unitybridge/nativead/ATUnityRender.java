package com.anythink.unitybridge.nativead;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.anythink.nativead.api.ATNativeAdRenderer;
import com.anythink.nativead.unitgroup.api.CustomNativeAd;
import com.anythink.network.admob.AdmobATConst;
import com.anythink.network.applovin.ApplovinATConst;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.imgutil.CommonBitmapUtil;
import com.anythink.unitybridge.imgutil.CommonImageLoader;
import com.anythink.unitybridge.imgutil.CommonImageLoaderListener;

import java.security.cert.CollectionCertStoreParameters;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 */
public class ATUnityRender implements ATNativeAdRenderer<CustomNativeAd> {

    Activity mActivity;
    ViewInfo mViewInfo;
    FrameLayout mFrameLayout;

    public ATUnityRender(Activity pActivity, ViewInfo pViewInfo) {
        mActivity = pActivity;
        mViewInfo = pViewInfo;
    }


    int mNetworkType;

    @Override
    public View createView(Context context, int networkType) {

        mNetworkType = networkType;
        mFrameLayout = new FrameLayout(context);
        return mFrameLayout;
    }

    @Override
    public void renderAdView(View view, CustomNativeAd ad) {

        TextView titleView = new TextView(mActivity);
        TextView descView = new TextView(mActivity);
        TextView ctaView = new TextView(mActivity);


        final ImageView logoView = new ImageView(mActivity);

        final View mediaView = ad.getAdMediaView(mFrameLayout, view.getWidth());
        if (mediaView != null && ad.isNativeExpress()) { // 个性化模板View
            if (mViewInfo.imgMainView != null && mViewInfo.rootView != null) {
                mViewInfo.imgMainView.mX = 0;
                mViewInfo.imgMainView.mY = 0;
                mViewInfo.imgMainView.mWidth = mViewInfo.rootView.mWidth;
                mViewInfo.imgMainView.mHeight = mViewInfo.rootView.mHeight;

                MsgTools.pirntMsg("ExpressNative, NetworkType ----> " + mNetworkType);
                ViewInfo.add2ParentView(mFrameLayout, mediaView, mViewInfo.imgMainView, -1);
                return;
            }
        }


        if (mViewInfo.titleView != null) {

            if (!TextUtils.isEmpty(mViewInfo.titleView.textcolor)) {
                titleView.setTextColor(Color.parseColor(mViewInfo.titleView.textcolor));
            }

            if (mViewInfo.titleView.textSize > 0) {
                titleView.setTextSize(mViewInfo.titleView.textSize);
            }
            MsgTools.pirntMsg("title---->" + ad.getTitle());
            titleView.setText(ad.getTitle());

            titleView.setSingleLine();
            titleView.setMaxEms(15);
            titleView.setEllipsize(TextUtils.TruncateAt.END);


            ViewInfo.add2ParentView(mFrameLayout, titleView, mViewInfo.titleView, -1);

        }


        if (mViewInfo.ctaView != null) {

            if (!TextUtils.isEmpty(mViewInfo.ctaView.textcolor)) {
                //                 MsgTools.pirntMsg("#"+Integer.toHexString(mViewInfo.ctaView.textcolor));
                //                ctaView.setTextColor(mViewInfo.ctaView.textcolor);
                ctaView.setTextColor(Color.parseColor(mViewInfo.ctaView.textcolor));
            }

            if (mViewInfo.ctaView.textSize > 0) {
                ctaView.setTextSize(mViewInfo.ctaView.textSize);
            }


            ctaView.setGravity(Gravity.CENTER);
            ctaView.setSingleLine();
            ctaView.setMaxEms(15);
            ctaView.setEllipsize(TextUtils.TruncateAt.END);

            MsgTools.pirntMsg("cat---->" + ad.getCallToActionText());
            ctaView.setText(ad.getCallToActionText());
            ViewInfo.add2ParentView(mFrameLayout, ctaView, mViewInfo.ctaView, -1);
        }


        if (mViewInfo.descView != null && descView != null) {

            if (!TextUtils.isEmpty(mViewInfo.descView.textcolor)) {
                descView.setTextColor(Color.parseColor(mViewInfo.descView.textcolor));

            }
            if (mViewInfo.descView.textSize > 0) {
                descView.setTextSize(mViewInfo.descView.textSize);
            }
            MsgTools.pirntMsg("desc---->" + ad.getDescriptionText());
            descView.setText(ad.getDescriptionText());


            descView.setMaxLines(3);
            descView.setMaxEms(15);
            descView.setEllipsize(TextUtils.TruncateAt.END);

            ViewInfo.add2ParentView(mFrameLayout, descView, mViewInfo.descView, -1);
        }

        if (mViewInfo.IconView != null) {

            FrameLayout iconArea = new FrameLayout(mActivity);


            if (ad.getAdIconView() == null) {
                final ImageView iconView = new ImageView(mActivity);
                iconArea.addView(iconView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                CommonImageLoader.getInstance(mActivity).load(ad.getIconImageUrl(), new CommonImageLoaderListener() {
                    @Override
                    public void onSuccessLoad(Bitmap bitmap, String key) {
                        if (bitmap != null) {
                            if (iconView != null) {
                                iconView.setImageBitmap(bitmap);
                            }
                        }
                    }

                    @Override
                    public void onFailedLoad(String description, String key) {
                        MsgTools.pirntMsg("load icon img failed");
                    }
                });
            } else {
                iconArea.addView(ad.getAdIconView(), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }

            // 加载图片
            ViewInfo.add2ParentView(mFrameLayout, iconArea, mViewInfo.IconView, -1);
        }


        if (mViewInfo.adLogoView != null) {
            // 加载图片

            CommonImageLoader.getInstance(mActivity).load(ad.getAdChoiceIconUrl(), new CommonImageLoaderListener() {
                @Override
                public void onSuccessLoad(Bitmap bitmap, String key) {
                    if (bitmap != null) {
                        if (logoView != null) {
                            logoView.setImageBitmap(bitmap);
                        }
                    }
                }

                @Override
                public void onFailedLoad(String description, String key) {
                    MsgTools.pirntMsg("load logo img failed");
                }
            });


            ViewInfo.add2ParentView(mFrameLayout, logoView, mViewInfo.adLogoView, -1);
        }

        if (mNetworkType != ApplovinATConst.NETWORK_FIRM_ID && mediaView != null) {
//            mediaView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            MsgTools.pirntMsg("mediaView ---> 视屏播放 " + ad.getVideoUrl());
            if (mViewInfo.imgMainView != null) {
                ViewInfo.add2ParentView(mFrameLayout, mediaView, mViewInfo.imgMainView, -1);
            }
        } else {
            //加载大图
            MsgTools.pirntMsg("mediaView ---> 大图播放");
            final ImageView mainImageView = new ImageView(mActivity);
            CommonImageLoader.getInstance(mActivity).load(ad.getMainImageUrl(), new CommonImageLoaderListener() {
                @Override
                public void onSuccessLoad(Bitmap bitmap, String key) {
                    if (bitmap != null) {
                        if (mainImageView != null) {
                            mainImageView.setImageBitmap(bitmap);
                        }
                    }
                }

                @Override
                public void onFailedLoad(String description, String key) {
                    MsgTools.pirntMsg("load main img failed");
                }
            });
            if (mViewInfo.imgMainView != null) {
                ViewInfo.add2ParentView(mFrameLayout, mainImageView, mViewInfo.imgMainView, -1);
            }
        }


        if (!TextUtils.isEmpty(ad.getAdFrom())) {
            FrameLayout.LayoutParams adFromParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            adFromParam.leftMargin = CommonBitmapUtil.dip2px(mActivity, 3);
            adFromParam.topMargin = CommonBitmapUtil.dip2px(mActivity, 3);
            TextView adFromTextView = new TextView(mActivity);
            adFromTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
            adFromTextView.setPadding(CommonBitmapUtil.dip2px(mActivity, 5), CommonBitmapUtil.dip2px(mActivity, 2), CommonBitmapUtil.dip2px(mActivity, 5), CommonBitmapUtil.dip2px(mActivity, 2));
            adFromTextView.setBackgroundColor(0xff888888);
            adFromTextView.setTextColor(0xffffffff);
            adFromTextView.setText(ad.getAdFrom());

            mFrameLayout.addView(adFromTextView, adFromParam);
        }

        if (mNetworkType == AdmobATConst.NETWORK_FIRM_ID) {
            MsgTools.pirntMsg("start to add admob ad textview ");
            TextView adLogoView = new TextView(mActivity);
            adLogoView.setTextColor(Color.WHITE);
            adLogoView.setText("AD");
            adLogoView.setTextSize(11);
            adLogoView.setPadding(CommonBitmapUtil.dip2px(mActivity, 3), 0, CommonBitmapUtil.dip2px(mActivity, 3), 0);
            adLogoView.setBackgroundColor(Color.parseColor("#66000000"));
            if (mFrameLayout != null) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = CommonBitmapUtil.dip2px(mActivity, 3);
                layoutParams.topMargin = CommonBitmapUtil.dip2px(mActivity, 3);
                mFrameLayout.addView(adLogoView, layoutParams);

                MsgTools.pirntMsg("add admob ad textview 2 activity");
            }
        }

    }
}
