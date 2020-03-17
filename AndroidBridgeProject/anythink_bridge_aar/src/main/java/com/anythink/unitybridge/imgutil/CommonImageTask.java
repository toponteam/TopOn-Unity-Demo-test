package com.anythink.unitybridge.imgutil;


import android.util.Log;

import com.anythink.unitybridge.UnityPluginUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 */
public class CommonImageTask extends CommonTask {

    private static final String TAG = "ImageWorker";
    private String mKey;
    private String mImageUrl;
//    private String mSavePath;
//    private boolean mIsOnlyReadFromUrl = false; // 是否只从Url中加载文件
    private IImageWorkerListener mListener;


    public String getKey() {
        return mKey;
    }

//    public String getImageUrl() {
//        return mImageUrl;
//    }
//
//    public String getSavePath() {
//        return mSavePath;
//    }

    /**
     * 是否只从Url中加载文件
     *
     * @return
     */
//    public boolean isIsOnlyReadFromUrl() {
//        return mIsOnlyReadFromUrl;
//    }

    /**
     * 设置是否只从Url中加载文件
     *
     */
//    public void setIsOnlyReadFromUrl(boolean isOnlyReadFromUrl) {
//        this.mIsOnlyReadFromUrl = isOnlyReadFromUrl;
//    }

    public IImageWorkerListener getImageWorkerListener() {
        return mListener;
    }

    public void setImageWorkerListener(IImageWorkerListener listener) {
        this.mListener = listener;
    }

    public CommonImageTask(String key, String imageUrl) {
        mKey = key;
        mImageUrl = imageUrl;
//        mSavePath = savePath;
    }

    /**
     * 下载图片
     */
    private void download() {
        boolean httpResult = loadUrl();
        if (httpResult) {
            successLoad(mImageUrl);
        } else {
            String msg = "load image faild.";
            Log.d(TAG, msg);
            failedLoad(mImageUrl, msg);
        }
    }

    /**
     * 修正图片<br>
     * 当需要对下载后的图片做特殊处理时，重写此方法,在此方法中处理图片，并保存新的图片
     *
     * @param savePath
     */
    protected void adjustImage(String savePath) {

    }

    /**
     * 从 URL 中加载图片
     *
     * @return
     */
    protected boolean loadUrl() {
        boolean result = true;
        HttpURLConnection connection = null;
//        net.url.FileURLConnection _fileURLConnection = null;
        try {
//            File localFile = new File(mSavePath);
//            if (!localFile.exists()) {
//                localFile.mkdirs();
//            }
//
//            //先检查一下本地文件大小
//            if (localFile.exists()) {
//                localFile.delete();
//            }

//            File tempLocalFile = new File(mSavePath + ".temp");
//            //先检查一下本地文件大小
//            if (tempLocalFile.exists()) {
//                tempLocalFile.delete();
//                tempLocalFile.createNewFile();
//            }
            Log.i(TAG,"mImageUrl:"+mImageUrl);
            if(!mImageUrl.startsWith("http")){
                InputStream is = new FileInputStream(new File(mImageUrl.replaceAll("file://","")));
                CommonImageLoader.getInstance(UnityPluginUtils.getActivity("imgload").getApplication().getApplicationContext()).addBitmapStreamToFileCache(mImageUrl, is);
                is.close(); // 关闭输入流
                result = true;
                return result;
            }

            URL url = new URL(mImageUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30 * 1000);
            connection.setConnectTimeout(30 * 1000);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);

            connection.connect();

            int statusCode = connection.getResponseCode();

            //处理返回
            if (statusCode == 200) {
                long total = connection.getContentLength();
                InputStream is = connection.getInputStream();
//                if(!tempLocalFile.exists()){
//                    tempLocalFile.createNewFile();
//                }
//                FileOutputStream fileout = new FileOutputStream(tempLocalFile, true);
                // 读取数据
//                byte[] buffer = new byte[2048];
//                int ch = 0;
//                while ((ch = is.read(buffer)) != -1) {
//                    fileout.write(buffer, 0, ch);
//                }
                CommonImageLoader.getInstance(UnityPluginUtils.getActivity("imgload").getApplication().getApplicationContext()).addBitmapStreamToFileCache(mImageUrl, is);
                is.close(); // 关闭输入流
//                fileout.flush(); // 清空闪存,把数据全部写入到文件
//                fileout.close(); // 关闭输出流
//                tempLocalFile.renameTo(localFile);  // 把临时文件重命名为正式文件

                Log.d(TAG, "download file from [" + mImageUrl + "] ");
                result = true;
            } else {
                failedLoad(mImageUrl, "load image from http faild because http return code: " + statusCode + ".image url is " + mImageUrl);
                result = false;
            }
        } catch (Exception e) {
            failedLoad(mImageUrl, e.getMessage());
            result = false;
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            failedLoad(mImageUrl, e.getMessage());
            result = false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
//            httpClient = null;
        }
        return result;
    }

    /**
     * 加载成功
     */
    private void successLoad(String url) {
        if (mListener != null) {
            mListener.onSuccess(url);
        }
    }

    /**
     * 加载失败
     *
     * @param message 错误信息
     */
    private void failedLoad(String url, String message) {
        if (mListener != null) {
            mListener.onFailed(url, message);
        }
    }

    /**
     * ImageWorker 的坚挺
     */
    public static interface IImageWorkerListener {
        void onSuccess(String url);

        void onFailed(String url, String message);
    }

    @Override
    public void runTask() {
//        if (!mIsOnlyReadFromUrl) {
//            if (TextUtils.isEmpty(mSavePath)) {
//                failedLoad(mImageUrl, "save path is null.");
//                return;
//            }
//            // 检查本地图片是否缓存
//            File file = new File(mSavePath);
//            if (!file.exists() || file.length() <= 0) {
//                download();
//            } else {
//                loadFile();
//            }
//        } else {
            download();
//        }
    }

    @Override
    public void cancelTask() {


    }

    @Override
    public void pauseTask(boolean pause) {

    }
}
