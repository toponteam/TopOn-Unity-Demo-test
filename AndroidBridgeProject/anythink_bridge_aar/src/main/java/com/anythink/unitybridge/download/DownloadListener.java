package com.anythink.unitybridge.download;


public interface DownloadListener {
    void onDownloadStart(String unitId, String callbackJson, long totalBytes, long currBytes, String fileName, String appName);

    void onDownloadUpdate(String unitId, String callbackJson, long totalBytes, long currBytes, String fileName, String appName);

    void onDownloadPause(String unitId, String callbackJson, long totalBytes, long currBytes, String fileName, String appName);

    void onDownloadFinish(String unitId, String callbackJson, long totalBytes, String fileName, String appName);

    void onDownloadFail(String unitId, String callbackJson, long totalBytes, long currBytes, String fileName, String appName);

    void onInstalled(String unitId, String callbackJson, String fileName, String appName);
}
