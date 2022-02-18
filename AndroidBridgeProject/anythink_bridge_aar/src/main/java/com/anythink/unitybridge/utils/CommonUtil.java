package com.anythink.unitybridge.utils;

import android.content.Context;

public class CommonUtil {

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getResId(Context context, String resName, String resType) {
        if (context != null) {
            resName = "anythink_" + resName;
            return context.getResources().getIdentifier(resName, resType,
                    context.getPackageName());
        }
        return -1;
    }
}
