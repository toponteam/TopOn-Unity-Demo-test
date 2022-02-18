package com.ofm.unitybridge.nativead;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anythink.nativead.api.ATNativeImageView;
import com.ofm.nativead.api.OfmFeedInfo;
import com.ofm.nativead.api.OfmNativeAdRender;
import com.ofm.unitybridge.MsgTools;
import com.ofm.unitybridge.R;


/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 */
public class ATUnityRender extends OfmNativeAdRender {

    Activity mActivity;
    ViewInfo mViewInfo;
    FrameLayout mFrameLayout;

    public ATUnityRender(Activity pActivity, ViewInfo pViewInfo) {
        mActivity = pActivity;
        mViewInfo = pViewInfo;
    }


//    int mNetworkType;

    @Override
    public View createView(Context context) {
        mFrameLayout = new FrameLayout(context);
        return mFrameLayout;
    }

    @Override
    public void renderAdView(View view, OfmFeedInfo ad) {

        TextView titleView = new TextView(mActivity);
        TextView descView = new TextView(mActivity);
        TextView ctaView = new TextView(mActivity);

        titleView.setId(R.id.title_view_id);
        descView.setId(R.id.desc_view_id);
        ctaView.setId(R.id.cta_view_id);

        setTitleViewId(R.id.title_view_id);
        setDescriptionViewId(R.id.desc_view_id);
        setCalltoActionViewId(R.id.cta_view_id);

        final View mediaView = ad.getAdMediaView(mFrameLayout, view.getWidth());
        if (mediaView != null && ad.isNativeExpress()) { // 个性化模板View
            if (mViewInfo.imgMainView != null && mViewInfo.rootView != null) {
                mViewInfo.imgMainView.mX = 0;
                mViewInfo.imgMainView.mY = 0;
                mViewInfo.imgMainView.mWidth = mViewInfo.rootView.mWidth;
                mViewInfo.imgMainView.mHeight = mViewInfo.rootView.mHeight;

//                MsgTools.pirntMsg("ExpressNative, NetworkType ----> " + mNetworkType);
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

            iconArea.setId(R.id.icon_view_id);

            setIconViewId(R.id.icon_view_id);

            if (ad.getAdIconView() == null) {
                final ATNativeImageView iconView = new ATNativeImageView(mActivity);
                iconArea.addView(iconView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                iconView.setImage(ad.getIconImageUrl());
            } else {
                iconArea.addView(ad.getAdIconView(), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }

            // 加载图片
            ViewInfo.add2ParentView(mFrameLayout, iconArea, mViewInfo.IconView, -1);
        }

        if (mViewInfo.adLogoView != null) {
            final ATNativeImageView logoView = new ATNativeImageView(mActivity);
            logoView.setId(R.id.logo_view_id);

            setAdLogoViewId(R.id.logo_view_id);

            ViewInfo.add2ParentView(mFrameLayout, logoView, mViewInfo.adLogoView, -1);
            logoView.setImage(ad.getAdChoiceIconUrl());
        }

//        if (mNetworkType != 5 && mediaView != null) {
        if (mediaView != null) {
//            mediaView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            MsgTools.pirntMsg("mediaView ---> 视屏播放 " + ad.getVideoUrl());
            if (mViewInfo.imgMainView != null) {
                ViewInfo.add2ParentView(mFrameLayout, mediaView, mViewInfo.imgMainView, -1);
            }
        } else {
            //加载大图
            MsgTools.pirntMsg("mediaView ---> 大图播放");
            if (mViewInfo.imgMainView != null) {
                final ATNativeImageView mainImageView = new ATNativeImageView(mActivity);
                ViewInfo.add2ParentView(mFrameLayout, mainImageView, mViewInfo.imgMainView, -1);
                mainImageView.setImage(ad.getMainImageUrl());
            }
        }

//
//        if (!TextUtils.isEmpty(ad.getAdFrom()) && mNetworkType == 23) {
//            FrameLayout.LayoutParams adFromParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//            adFromParam.leftMargin = CommonUtil.dip2px(mActivity, 3);
//            adFromParam.bottomMargin = CommonUtil.dip2px(mActivity, 3);
//            adFromParam.gravity = Gravity.BOTTOM;
//            TextView adFromTextView = new TextView(mActivity);
//            adFromTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
//            adFromTextView.setPadding(CommonUtil.dip2px(mActivity, 5), CommonUtil.dip2px(mActivity, 2), CommonUtil.dip2px(mActivity, 5), CommonUtil.dip2px(mActivity, 2));
//            adFromTextView.setBackgroundColor(0xff888888);
//            adFromTextView.setTextColor(0xffffffff);
//            adFromTextView.setText(ad.getAdFrom());
//
//            adFromTextView.setId(R.id.source_view_id);
//            setSourceViewId(R.id.source_view_id);
//
//            mFrameLayout.addView(adFromTextView, adFromParam);
//        }
//
//        if (mNetworkType == 2) {
//            MsgTools.pirntMsg("start to add admob ad textview ");
//            TextView adLogoView = new TextView(mActivity);
//            adLogoView.setTextColor(Color.WHITE);
//            adLogoView.setText("AD");
//            adLogoView.setTextSize(11);
//            adLogoView.setPadding(CommonUtil.dip2px(mActivity, 3), 0, CommonUtil.dip2px(mActivity, 3), 0);
//            adLogoView.setBackgroundColor(Color.parseColor("#66000000"));
//            if (mFrameLayout != null) {
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.leftMargin = CommonUtil.dip2px(mActivity, 3);
//                layoutParams.topMargin = CommonUtil.dip2px(mActivity, 3);
//                mFrameLayout.addView(adLogoView, layoutParams);
//
//                MsgTools.pirntMsg("add admob ad textview 2 activity");
//            }
//        }

    }

}
