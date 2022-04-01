package com.anythink.unitybridge.nativead;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anythink.nativead.api.ATNativeAdRenderer;
import com.anythink.nativead.api.ATNativeImageView;
import com.anythink.nativead.unitgroup.api.CustomNativeAd;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;


public class ATUnityRender implements ATNativeAdRenderer<CustomNativeAd> {

    Activity mActivity;
    ViewInfo mViewInfo;
    FrameLayout mFrameLayout;
    View mDislikeView;

    List<View> mClickViews = new ArrayList<>();

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

        final View mediaView = ad.getAdMediaView(mFrameLayout, view.getWidth());
        if (mediaView != null && ad.isNativeExpress()) { // 个性化模板View

            if (mDislikeView != null) {
                mDislikeView.setVisibility(View.GONE);
            }

            if (mViewInfo.imgMainView != null && mViewInfo.rootView != null) {
                mViewInfo.imgMainView.mX = 0;
                mViewInfo.imgMainView.mY = 0;
                mViewInfo.imgMainView.mWidth = mViewInfo.rootView.mWidth;
                mViewInfo.imgMainView.mHeight = mViewInfo.rootView.mHeight;

                MsgTools.printMsg("ExpressNative, NetworkType ----> " + mNetworkType);
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
            MsgTools.printMsg("title---->" + ad.getTitle());
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

            MsgTools.printMsg("cat---->" + ad.getCallToActionText());
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
            MsgTools.printMsg("desc---->" + ad.getDescriptionText());
            descView.setText(ad.getDescriptionText());


            descView.setMaxLines(3);
            descView.setMaxEms(15);
            descView.setEllipsize(TextUtils.TruncateAt.END);

            ViewInfo.add2ParentView(mFrameLayout, descView, mViewInfo.descView, -1);
        }

        ATNativeImageView iconView = null;
        if (mViewInfo.IconView != null) {

            FrameLayout iconArea = new FrameLayout(mActivity);

            View adIconView = ad.getAdIconView();
            if (adIconView == null) {
                iconView = new ATNativeImageView(mActivity);
                iconArea.addView(iconView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                iconView.setImage(ad.getIconImageUrl());
            } else {
                iconArea.addView(adIconView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }

            // 加载图片
            ViewInfo.add2ParentView(mFrameLayout, iconArea, mViewInfo.IconView, -1);
        }


        if (mNetworkType != 5 && mediaView != null) {
//            mediaView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            MsgTools.printMsg("mediaView ---> 视屏播放 " + ad.getVideoUrl());
            if (mViewInfo.imgMainView != null) {
                ViewInfo.add2ParentView(mFrameLayout, mediaView, mViewInfo.imgMainView, -1);
            }
        } else {
            //加载大图
            MsgTools.printMsg("mediaView ---> 大图播放");
            if (mViewInfo.imgMainView != null) {
                final ATNativeImageView mainImageView = new ATNativeImageView(mActivity);
                ViewInfo.add2ParentView(mFrameLayout, mainImageView, mViewInfo.imgMainView, -1);
                mainImageView.setImage(ad.getMainImageUrl());
            }
        }


        if (!TextUtils.isEmpty(ad.getAdFrom()) && mNetworkType == 23) {
            FrameLayout.LayoutParams adFromParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            adFromParam.leftMargin = CommonUtil.dip2px(mActivity, 3);
            adFromParam.bottomMargin = CommonUtil.dip2px(mActivity, 3);
            adFromParam.gravity = Gravity.BOTTOM;
            TextView adFromTextView = new TextView(mActivity);
            adFromTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
            adFromTextView.setPadding(CommonUtil.dip2px(mActivity, 5), CommonUtil.dip2px(mActivity, 2), CommonUtil.dip2px(mActivity, 5), CommonUtil.dip2px(mActivity, 2));
            adFromTextView.setBackgroundColor(0xff888888);
            adFromTextView.setTextColor(0xffffffff);
            adFromTextView.setText(ad.getAdFrom());

            mFrameLayout.addView(adFromTextView, adFromParam);
        }


        ATNativeImageView logoView = null;
        if (mViewInfo.adLogoView != null) {
            logoView = new ATNativeImageView(mActivity);
            ViewInfo.add2ParentView(mFrameLayout, logoView, mViewInfo.adLogoView, -1);
            logoView.setImage(ad.getAdChoiceIconUrl());
            MsgTools.printMsg("ad choice icon url == null:" + (ad.getAdChoiceIconUrl() == null));
            if (TextUtils.isEmpty(ad.getAdChoiceIconUrl())) {
                MsgTools.printMsg("start to add ad label textview");
                TextView adLableTextView = new TextView(mActivity);
                adLableTextView.setTextColor(Color.WHITE);
                adLableTextView.setText("AD");
                adLableTextView.setTextSize(11);
                adLableTextView.setPadding(CommonUtil.dip2px(mActivity, 3), 0, CommonUtil.dip2px(mActivity, 3), 0);
                adLableTextView.setBackgroundColor(Color.parseColor("#66000000"));
                if (mFrameLayout != null) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.leftMargin = CommonUtil.dip2px(mActivity, 3);
                    layoutParams.topMargin = CommonUtil.dip2px(mActivity, 3);
                    mFrameLayout.addView(adLableTextView, layoutParams);

                    MsgTools.printMsg("add ad label textview 2 activity");
                }
            }
        }

        //click
        List<View> customClickViews = new ArrayList<>();

        if (mViewInfo.rootView != null) {
            dealWithClick(view, mViewInfo.rootView.isCustomClick, mClickViews, customClickViews, "root");
        }
        if (mViewInfo.titleView != null) {
            dealWithClick(titleView, mViewInfo.titleView.isCustomClick, mClickViews, customClickViews, "title");
        }
        if (mViewInfo.descView != null) {
            dealWithClick(descView, mViewInfo.descView.isCustomClick, mClickViews, customClickViews, "desc");
        }
        if (mViewInfo.IconView != null && iconView != null) {
            dealWithClick(iconView, mViewInfo.IconView.isCustomClick, mClickViews, customClickViews, "icon");
        }
        if (mViewInfo.adLogoView != null) {
            dealWithClick(logoView, mViewInfo.adLogoView.isCustomClick, mClickViews, customClickViews, "adLogo");
        }
        if (mViewInfo.ctaView != null) {
            dealWithClick(ctaView, mViewInfo.ctaView.isCustomClick, mClickViews, customClickViews, "cta");
        }


        //dislike

        if (mDislikeView != null) {
            MsgTools.printMsg("bind dislike ----> " + mNetworkType);

            mDislikeView.setVisibility(View.VISIBLE);

            CustomNativeAd.ExtraInfo.Builder builder = new CustomNativeAd.ExtraInfo.Builder();
            builder.setCloseView(mDislikeView);

            if (customClickViews.size() > 0) {
                builder.setCustomViewList(customClickViews);
            }

            ad.setExtraInfo(builder.build());
        } else {
            if (customClickViews.size() > 0) {
                CustomNativeAd.ExtraInfo.Builder builder = new CustomNativeAd.ExtraInfo.Builder()
                        .setCustomViewList(customClickViews);

                ad.setExtraInfo(builder.build());
            }
        }

    }


    public void setDislikeView(View dislikeView) {
        this.mDislikeView = dislikeView;
    }

    private void dealWithClick(View view, boolean isCustomClick, List<View> clickViews, List<View> customClickViews, String name) {
        if (mNetworkType == 8 || mNetworkType == 22) {
            if (isCustomClick) {
                if (view != null) {
                    MsgTools.printMsg("add customClick ----> " + name);
                    customClickViews.add(view);
                }
                return;
            }
        }
        MsgTools.printMsg("add click ----> " + name);
        clickViews.add(view);
    }


    public List<View> getClickViews() {
        return mClickViews;
    }

}
