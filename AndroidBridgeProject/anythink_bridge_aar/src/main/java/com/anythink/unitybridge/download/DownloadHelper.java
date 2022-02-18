package com.anythink.unitybridge.download;


import com.anythink.china.api.ATAppDownloadListener;
import com.anythink.core.api.ATAdInfo;
import com.anythink.unitybridge.MsgTools;
import com.anythink.unitybridge.utils.TaskManager;

public class DownloadHelper {

    public static DownloadListener sDownloadListener;

    public DownloadHelper(DownloadListener listener) {
        MsgTools.printMsg("DownloadHelper init");
        if (sDownloadListener == null) {
            sDownloadListener = listener;
        }
    }

    public static ATAppDownloadListener getDownloadListener(final String mPlacementId) {
        return new ATAppDownloadListener() {
            @Override
            public void onDownloadStart(final ATAdInfo adInfo, final long totalBytes, final long currBytes, final String fileName, final String appName) {
                MsgTools.printMsg("onDownloadStart: " + mPlacementId + ", totalBytes: " + totalBytes + ", currBytes: " + currBytes + ", fileName: " + fileName + ", appName: " + appName);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (DownloadHelper.sDownloadListener != null) {
                            synchronized (DownloadHelper.sDownloadListener) {
                                DownloadHelper.sDownloadListener.onDownloadStart(mPlacementId, adInfo.toString(), totalBytes, currBytes, fileName, appName);
                            }
                        }
                    }
                });
            }

            @Override
            public void onDownloadUpdate(final ATAdInfo adInfo, final long totalBytes, final long currBytes, final String fileName, final String appName) {
                MsgTools.printMsg("onDownloadUpdate: " + mPlacementId + ", totalBytes: " + totalBytes + ", currBytes: " + currBytes + ", fileName: " + fileName + ", appName: " + appName);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (DownloadHelper.sDownloadListener != null) {
                            synchronized (DownloadHelper.sDownloadListener) {
                                DownloadHelper.sDownloadListener.onDownloadUpdate(mPlacementId, adInfo.toString(), totalBytes, currBytes, fileName, appName);
                            }
                        }
                    }
                });
            }

            @Override
            public void onDownloadPause(final ATAdInfo adInfo, final long totalBytes, final long currBytes, final String fileName, final String appName) {
                MsgTools.printMsg("onDownloadPause: " + mPlacementId + ", totalBytes: " + totalBytes + ", currBytes: " + currBytes + ", fileName: " + fileName + ", appName: " + appName);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (DownloadHelper.sDownloadListener != null) {
                            synchronized (DownloadHelper.sDownloadListener) {
                                DownloadHelper.sDownloadListener.onDownloadPause(mPlacementId, adInfo.toString(), totalBytes, currBytes, fileName, appName);
                            }
                        }
                    }
                });
            }

            @Override
            public void onDownloadFinish(final ATAdInfo adInfo, final long totalBytes, final String fileName, final String appName) {
                MsgTools.printMsg("onDownloadFinish: " + mPlacementId + ", totalBytes: " + totalBytes + ", fileName: " + fileName + ", appName: " + appName);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (DownloadHelper.sDownloadListener != null) {
                            synchronized (DownloadHelper.sDownloadListener) {
                                DownloadHelper.sDownloadListener.onDownloadFinish(mPlacementId, adInfo.toString(), totalBytes, fileName, appName);
                            }
                        }
                    }
                });
            }

            @Override
            public void onDownloadFail(final ATAdInfo adInfo, final long totalBytes, final long currBytes, final String fileName, final String appName) {
                MsgTools.printMsg("onDownloadFail: " + mPlacementId + ", totalBytes: " + totalBytes + ", currBytes: " + currBytes + ", fileName: " + fileName + ", appName: " + appName);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (DownloadHelper.sDownloadListener != null) {
                            synchronized (DownloadHelper.sDownloadListener) {
                                DownloadHelper.sDownloadListener.onDownloadFail(mPlacementId, adInfo.toString(), totalBytes, currBytes, fileName, appName);
                            }
                        }
                    }
                });
            }

            @Override
            public void onInstalled(final ATAdInfo adInfo, final String fileName, final String appName) {
                MsgTools.printMsg("onInstalled: " + mPlacementId + ", fileName: " + fileName + ", appName: " + appName);
                TaskManager.getInstance().run_proxy(new Runnable() {
                    @Override
                    public void run() {
                        if (DownloadHelper.sDownloadListener != null) {
                            synchronized (DownloadHelper.sDownloadListener) {
                                DownloadHelper.sDownloadListener.onInstalled(mPlacementId, adInfo.toString(), fileName, appName);
                            }
                        }
                    }
                });
            }
        };
    }


}
