package com.anythink.unitybridge.nativead;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.anythink.nativead.api.ATNativeAdView;
import com.anythink.unitybridge.MsgTools;

import org.json.JSONException;
import org.json.JSONObject;


public class ViewInfo {
    protected class INFO {
        protected int mX = 0;
        protected int mY = 0;
        protected int mWidth = 0;
        protected int mHeight = 0;
        protected String bgcolor = "";
        protected int textSize = 0;
        protected String textcolor = "";
        protected boolean isCustomClick = false;

        protected String name;

        public INFO() {

        }
    }


    public static void add2ParentView(final FrameLayout view, final View childView, final INFO pViewInfo, final int gravity) {

        if (view == null || pViewInfo == null) {
            return;
        }

        if (childView == null || pViewInfo.mWidth < 0 || (pViewInfo.mHeight < 0 && pViewInfo.mHeight != ViewGroup.LayoutParams.WRAP_CONTENT)) {
            Log.e("add2activity--[" + pViewInfo.name + "]", " config error ,show error !");
            return;
        }


        Log.i("add2activity", "[" + pViewInfo.name + "]   add 2 activity");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(pViewInfo.mWidth, pViewInfo.mHeight);
        layoutParams.leftMargin = pViewInfo.mX;
        layoutParams.topMargin = pViewInfo.mY;
        if (gravity > 0) {
            layoutParams.gravity = gravity;
        } else {
            layoutParams.gravity = 51;
        }


        childView.setLayoutParams(layoutParams);

//                pViewInfo.mView.setBackgroundColor(pViewInfo.bgcolor);
        try {
            if (!TextUtils.isEmpty(pViewInfo.bgcolor)) {
                childView.setBackgroundColor(Color.parseColor(pViewInfo.bgcolor));
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewParent oldParent = childView.getParent();
        if (oldParent instanceof ViewGroup) {
            ((ViewGroup) oldParent).removeView(childView);
        }

        view.addView(childView, layoutParams);

    }


//    public static void add2RootView(final Activity pActivity,final  ViewInfo pViewInfo){
//        RelativeLayout relative = new RelativeLayout(pActivity);
//        relative.setBackgroundColor(Color.parseColor(pViewInfo.rootView.bgcolor));
//
//        pActivity.setContentView(relative);
//
//    }


    public static void addNativeAdView2Activity(final Activity pActivity, final ViewInfo pViewInfo, final ATNativeAdView mATNativeAdView, final int parentGravity) {

        if (pActivity == null || mATNativeAdView == null) {
            MsgTools.printMsg("pActivity or native ad view is null");
            return;
        }


        pActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ViewGroup _viewGroup = (ViewGroup) mATNativeAdView.getParent();
                    if (_viewGroup != null) {
                        _viewGroup.removeView(mATNativeAdView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                if(pViewInfo.rootView != null){
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(pViewInfo.rootView.mWidth, pViewInfo.rootView.mHeight);

                    if (parentGravity != -1) {
                        layoutParams.gravity = parentGravity;
                    } else {
                        layoutParams.leftMargin = pViewInfo.rootView.mX;
                        layoutParams.topMargin = pViewInfo.rootView.mY;
                    }

                    if(!TextUtils.isEmpty(pViewInfo.rootView.bgcolor)){
                        mATNativeAdView.setBackgroundColor(Color.parseColor(pViewInfo.rootView.bgcolor));
                    }

                    MsgTools.printMsg("Add native view to content start....");
                    pActivity.addContentView(mATNativeAdView, layoutParams);
                    MsgTools.printMsg("Add native view to content end....");
                } else {
                    MsgTools.printMsg("pViewInfo.rootView is null");
                }


            }
        });
    }


    public INFO rootView, imgMainView, IconView, titleView, descView, adLogoView, ctaView, dislikeView;


    public static ViewInfo createDefualtView(Activity pActivity) {


        DisplayMetrics dm = pActivity.getResources().getDisplayMetrics();
        int heigth = dm.heightPixels;
        int width = dm.widthPixels;

        ViewInfo _viewInfo = new ViewInfo();
        _viewInfo.rootView.textSize = 12;
        _viewInfo.rootView.textcolor = "0X000000";
        _viewInfo.rootView.bgcolor = "0XFFFFFF";
        _viewInfo.rootView.mWidth = width;
        _viewInfo.rootView.mHeight = heigth / 5;
        _viewInfo.rootView.mX = 0;
        _viewInfo.rootView.mY = 0;
        _viewInfo.rootView.name = "rootView_def";

        //imgMainView
        _viewInfo.imgMainView.textSize = 12;
        _viewInfo.imgMainView.textcolor = "0X000000";
        _viewInfo.imgMainView.bgcolor = "0XFFFFFF";
        _viewInfo.imgMainView.mWidth = 25;
        _viewInfo.imgMainView.mHeight = 25;
        _viewInfo.imgMainView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.imgMainView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.imgMainView.name = "imgMainView_def";


        _viewInfo.adLogoView.textSize = 12;
        _viewInfo.adLogoView.textcolor = "0X000000";
        _viewInfo.adLogoView.bgcolor = "0XFFFFFF";
        _viewInfo.adLogoView.mWidth = _viewInfo.rootView.mWidth * 3 / 5;
        _viewInfo.adLogoView.mHeight = _viewInfo.rootView.mHeight / 2;
        _viewInfo.adLogoView.mX = _viewInfo.rootView.mX + 100;
        _viewInfo.adLogoView.mY = _viewInfo.rootView.mX + 10;
        _viewInfo.adLogoView.name = "adlogo_def";


        _viewInfo.IconView.textSize = 12;
        _viewInfo.IconView.textcolor = "0X000000";
        _viewInfo.IconView.bgcolor = "0XFFFFFF";
        _viewInfo.IconView.mWidth = 25;
        _viewInfo.IconView.mHeight = 25;
        _viewInfo.IconView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.IconView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.IconView.name = "appicon_def";


        _viewInfo.titleView.textSize = 12;
        _viewInfo.titleView.textcolor = "0X000000";
        _viewInfo.titleView.bgcolor = "0XFFFFFF";
        _viewInfo.titleView.mWidth = 25;
        _viewInfo.titleView.mHeight = 25;
        _viewInfo.titleView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.titleView.mY = _viewInfo.rootView.mX + 0;


        _viewInfo.descView.textSize = 12;
        _viewInfo.descView.textcolor = "0X000000";
        _viewInfo.descView.bgcolor = "0XFFFFFF";
        _viewInfo.descView.mWidth = 25;
        _viewInfo.descView.mHeight = 25;
        _viewInfo.descView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.descView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.descView.name = "desc_def";

        _viewInfo.ctaView.textSize = 12;
        _viewInfo.ctaView.textcolor = "0X000000";
        _viewInfo.ctaView.bgcolor = "0XFFFFFF";
        _viewInfo.ctaView.mWidth = 25;
        _viewInfo.ctaView.mHeight = 25;
        _viewInfo.ctaView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.ctaView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.ctaView.name = "cta_def";

        _viewInfo.dislikeView.textSize = 12;
        _viewInfo.dislikeView.textcolor = "0X000000";
        _viewInfo.dislikeView.bgcolor = "0X000000";
        _viewInfo.dislikeView.mWidth = 25;
        _viewInfo.dislikeView.mHeight = 25;
        _viewInfo.dislikeView.mX = _viewInfo.rootView.mX + 0;
        _viewInfo.dislikeView.mY = _viewInfo.rootView.mX + 0;
        _viewInfo.dislikeView.name = "dislike_def";

        return _viewInfo;
    }

    public INFO parseINFO(String json, String name, int px, int py) throws JSONException {
        INFO _info = new INFO();
        JSONObject _jsonObject = new JSONObject(json);
        if (_jsonObject.has("x")) {
            _info.mX = _jsonObject.getInt("x") + px;
        }

        if (_jsonObject.has("y")) {
            _info.mY = _jsonObject.getInt("y") + py;
        }
        if (_jsonObject.has("width")) {
            _info.mWidth = _jsonObject.getInt("width");
        }
        if (_jsonObject.has("height")) {
            _info.mHeight = _jsonObject.getInt("height");
        }
        if (_jsonObject.has("backgroundColor")) {
            _info.bgcolor = _jsonObject.getString("backgroundColor");
        }
        if (_jsonObject.has("textColor")) {
            _info.textcolor = _jsonObject.getString("textColor");
        }
        if (_jsonObject.has("textSize")) {
            _info.textSize = _jsonObject.getInt("textSize");
        }
        if (_jsonObject.has("isCustomClick")) {
            _info.isCustomClick = _jsonObject.getBoolean("isCustomClick");
        }

        _info.name = name;
        return _info;
    }


}
