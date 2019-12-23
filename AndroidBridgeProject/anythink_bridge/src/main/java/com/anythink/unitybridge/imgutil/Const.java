package com.anythink.unitybridge.imgutil;

import android.os.Environment;

/**
 * Copyright (C) 2018 {XX} Science and Technology Co., Ltd.
 *
 * @version V{XX_XX}
 * @Author ：Created by zhoushubin on 2018/8/8.
 * @Email: zhoushubin@salmonads.com
 */
public class Const {
    public static final boolean DEBUG = true;
    public static final String  SPU_NAME = "imgspu";

    public static class FOLDER {
        /**
         * sdcard
         */
        public static final String SDCARD_FOLDER = Environment.getExternalStorageDirectory().toString();

        public static final String ROOT_FOLDER = SDCARD_FOLDER + "/.anythink/";

        public static final String DOWNLOAD_FOLDER = ROOT_FOLDER + "download/";

        ;
    }
}
