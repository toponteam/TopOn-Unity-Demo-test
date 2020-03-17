package com.anythink.unitybridge.imgutil;

import android.graphics.Bitmap;

/**
 * 图片加载器的监听
 */
public interface CommonImageLoaderListener {
    /**
     * 成功加载图片
     * @param bitmap 图片Bitmap
     * @param key KEY
     */
    void onSuccessLoad(Bitmap bitmap, String key);

    /**
     * 加载图片失败
     * @param description 说明
     * @param key KEY
     */
    void onFailedLoad(String description, String key);
}
