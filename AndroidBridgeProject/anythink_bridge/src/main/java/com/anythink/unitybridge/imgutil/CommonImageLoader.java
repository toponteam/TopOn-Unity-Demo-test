package com.anythink.unitybridge.imgutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.anythink.unitybridge.UnityPluginUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 */
@SuppressWarnings("all")
public class CommonImageLoader {
    private static final String TAG = "ImageLoader";
    private static final int MESSAGE_WHAT_SUCCESS = 1;
    private static final int MESSAGE_WHAT_FAILED = 2;

    private static final String MESSAGE_DATA_URL = "message_key";
    private static final String MESSAGE_DATA_BITMAP = "message_bitmap";
    private static final String MESSAGE_DATA_DESC = "message_message";

    private static CommonImageLoader mInstance;
    
    private CommonTaskLoader taskLoader;
    
    /**
	 * 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存
	 */
	private CommonLruCache<String, Bitmap> mMemoryCache;

    /**缓存Image在Dish的类**/
    private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 50; // 50MB
    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;

    // 图片下载任务队列
    private LinkedHashMap<String, List<CommonImageLoaderListener>> mListenerMap = new LinkedHashMap<String, List<CommonImageLoaderListener>>();

    // 图片下载完毕后刷新UI
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == MESSAGE_WHAT_SUCCESS) {

                // 加载图片成功
                String url = message.getData().getString(MESSAGE_DATA_URL);
                Bitmap bitmap = getBitmapFromDiskCache(url);
                if (bitmap != null) {
                    addBitmapToMemoryCache(url, bitmap);
                }

                LinkedList<CommonImageLoaderListener> list = (LinkedList<CommonImageLoaderListener>) mListenerMap.get(url);
                if (list != null) {
                    for (CommonImageLoaderListener listener : list) {
                        if (listener != null) {
                            if (bitmap != null) {
                                listener.onSuccessLoad(bitmap, url);
                            } else {
                                listener.onFailedLoad("Bitmap load fail", url);
                            }

                        }
                    }
            	}
                mListenerMap.remove(url);
            } else if (message.what == MESSAGE_WHAT_FAILED) {
                // 加载图片失败
                String url = message.getData().getString(MESSAGE_DATA_URL);
                String description = message.getData().getString(MESSAGE_DATA_DESC);

                LinkedList<CommonImageLoaderListener> list = (LinkedList<CommonImageLoaderListener>)mListenerMap.get(url);
            	if(list != null){
            		for(CommonImageLoaderListener listener : list){
            			if (listener != null) {
            				listener.onFailedLoad(description, url);
                        }
            		}
            	}
                mListenerMap.remove(url);
            }
        }
    };

	/**
	 * 添加Bitmap到内存缓存
	 *
	 * @param key
	 * @param bitmap
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			mMemoryCache.put(key, bitmap);
		}
	}

    /**
     * 添加Bitmap到文件缓存
     */
    public void addBitmapStreamToFileCache(String data, InputStream inputStream){
        // BEGIN_INCLUDE(add_bitmap_to_cache)
        if (data == null || inputStream == null) {
            return;
        }
        File diskCacheDir = new File(CommonSDCardUtil.getFileCachePath() + Const.FOLDER.DOWNLOAD_FOLDER);
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }

        synchronized (mDiskCacheLock) {
            // Add to disk cache
            if (mDiskLruCache != null) {
                final String key = hashKeyForDisk(data);
                OutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    if (snapshot == null) {
                        final DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                        if (editor != null) {
                            out = editor.newOutputStream(0);
                            // 读取数据
                            byte[] buffer = new byte[2048];
                            int ch = 0;
                            while ((ch = inputStream.read(buffer)) != -1) {
                                out.write(buffer, 0, ch);
                            }
                            editor.commit();
                            out.close();
                        }
                    } else {
                        snapshot.getInputStream(0).close();
                    }
                } catch (final IOException e) {
                    Log.e(TAG, "addBitmapToCache - " + e);
                } catch (Exception e) {
                    Log.e(TAG, "addBitmapToCache - " + e);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
        // END_INCLUDE(add_bitmap_to_cache)
    }

	/**
	 * 从内存缓存中获取一个Bitmap
	 *
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

    /**
     * 从文件缓存中获取bitmap
     */
    public Bitmap getBitmapFromDiskCache(String data){
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        final String key = hashKeyForDisk(data);
        Bitmap bitmap = null;

        synchronized (mDiskCacheLock) {
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (mDiskLruCache != null) {
                InputStream inputStream = null;
                try {
                    final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(0);
                        if (inputStream != null) {
                            FileDescriptor fd = ((FileInputStream) inputStream).getFD();

                            // Decode bitmap, but we don't want to sample so give
                            // MAX_VALUE as the target dimensions
                            bitmap = CommonBitmapUtil.decodeSampledBitmapFromDescriptor(fd, Integer.MAX_VALUE, Integer.MAX_VALUE);
                        }
                    }
                } catch (final Throwable e) {
                    Log.e(TAG, "getBitmapFromDiskCache - " + e);
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
            return bitmap;
        }
    }

    protected CommonImageLoader(Context context) {
    	taskLoader = new CommonTaskLoader(context);
    	// 获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int mCacheSize = maxMemory / 10;
		// 给LruCache分配1/5 4M
		mMemoryCache = new CommonLruCache<String, Bitmap>(mCacheSize) {

			// 必须重写此方法，来测量Bitmap的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                try{
                    synchronized (imageLoaderRefList){
                        if(imageLoaderRefList != null && imageLoaderRefList.size() > 0){
                            for(WeakReference<Object> weakReference: imageLoaderRefList){
                                Object obj = weakReference.get();
                                if(obj != null){
                                    return;
                                }
                            }
                        }
                    }
                    if (oldValue != null && !oldValue.equals(newValue) && !oldValue.isRecycled()) {
                        // The removed entry is a recycling drawable, so notify it
                        // that it has been removed from the memory cache
                        oldValue.recycle();
                        oldValue = null;
                    }
                }catch (Exception ex){
                    if(Const.DEBUG){
                        ex.printStackTrace();
                    }
                }

            }
        };
        CommonSDCardUtil.init(context);
        initDiskCache();
    }

    /**
     * Initializes the disk cache. Note that this includes disk access so this should not be
     * executed on the main/UI thread. By default an ImageCache does not initialize the disk cache
     * when it is created, instead you should call initDiskCache() to initialize it on a background
     * thread.
     */
    public void initDiskCache() {
        // Set up disk cache
        synchronized (mDiskCacheLock) {
            if (mDiskLruCache == null || mDiskLruCache.isClosed()) {
                File diskCacheDir = new File(CommonSDCardUtil.getFileCachePath() + Const.FOLDER.DOWNLOAD_FOLDER);
                if (diskCacheDir != null) {
                    if (!diskCacheDir.exists()) {
                        diskCacheDir.mkdirs();
                    }
                    try {
                        mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DEFAULT_DISK_CACHE_SIZE);
                    } catch (final IOException e) {
                        Log.e(TAG, "initDiskCache - " + e);
                    }
                }
            }
            mDiskCacheStarting = false;
            mDiskCacheLock.notifyAll();
        }
    }

    public static CommonImageLoader getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CommonImageLoader(context);
        }
        return mInstance;
    }



    /**
     * 加载图片
     *
     * @param imageUrl 图片URL
     * @param listener 监听
     */
    public void load(String imageUrl, CommonImageLoaderListener listener) {
        load(imageUrl, imageUrl, listener);
    }




    /**
     * 加载图片
     *
     * @param key            KEY
     * @param imageUrl       图片URL
     * @param listener       监听
     */
    public void load(String key, String imageUrl,
                     CommonImageLoaderListener listener) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(imageUrl)) {
            return;
        }

//        File saveFile = new File(savePath);
        if (getBitmapFromMemCache(imageUrl) != null) {
			listener.onSuccessLoad(getBitmapFromMemCache(imageUrl), key);
//			Log.e("", "-----hit key = " + imageUrl);
			return;
        } else {
            Bitmap bitmap = getBitmapFromDiskCache(imageUrl);
            if (bitmap != null) {
                Log.d(TAG, "url image [" + imageUrl + "] is downloaded");
                addBitmapToMemoryCache(imageUrl, bitmap);
                listener.onSuccessLoad(bitmap, key);
            } else {
                loadFormUrl(key, imageUrl, listener);
            }
        }
    }

    /**
     * 从URL中加载图片
     *
     * @param key               KEY
     * @param imageUrl          图片URL
     * @param listener          监听
     */
    private void loadFormUrl(String key, String imageUrl,
                             CommonImageLoaderListener listener) {

        if (!mListenerMap.containsKey(imageUrl)) {
        	LinkedList<CommonImageLoaderListener> list = new LinkedList<CommonImageLoaderListener>();
        	list.add(listener);
            mListenerMap.put(imageUrl, list);
            CommonImageTask worker = createImageWorker(key, imageUrl);
            taskLoader.run(worker);

        } else {
        	LinkedList<CommonImageLoaderListener> list = (LinkedList<CommonImageLoaderListener>)mListenerMap.get(imageUrl);
        	if(list != null && !list.contains(listener)){
        		list.add(listener);
        	}
            Log.d(TAG, "loading:" + imageUrl);
        }
    }

    /**
     * 创建ImageWorker
     *
     * @param key               KEY
     * @param imageUrl          图片URL
     * @return
     */
    protected CommonImageTask createImageWorker(String key, String imageUrl) {
        // ImageWorker 的监听器
        CommonImageTask.IImageWorkerListener listener = new CommonImageTask.IImageWorkerListener() {

            @Override
            public void onSuccess(String url) {
                Message message = handler.obtainMessage();
                message.what = MESSAGE_WHAT_SUCCESS;
                Bundle bundle = new Bundle();
                bundle.putString(MESSAGE_DATA_URL, url);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailed(String url, String description) {
                Message message = handler.obtainMessage();
                message.what = MESSAGE_WHAT_FAILED;
                Bundle bundle = new Bundle();
                bundle.putString(MESSAGE_DATA_URL, url);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        };

        CommonImageTask worker;
        worker = new CommonImageTask(key, imageUrl);  //创建ImageWorker

//        worker.setIsOnlyReadFromUrl(isOnlyReadFromUrl);
        worker.setImageWorkerListener(listener);

        return worker;
    }
    
    public void recycle(){
        try{
            synchronized (imageLoaderRefList){
                if(imageLoaderRefList != null && imageLoaderRefList.size() > 0){
                    for(WeakReference<Object> weakReference: imageLoaderRefList){
                        Object obj = weakReference.get();
                        if(obj != null){
                            return;
                        }
                    }
                }
            }

            if(mMemoryCache != null){
                mMemoryCache.evictAll();
                Log.i(TAG, "mMemoryCache.evictAll()");
            }
            if(mListenerMap != null){
                mListenerMap.clear();
            }
            //每天清理一次缓存
            long lastClearTime = SPUtil.getLong(UnityPluginUtils.getActivity("imgload").getApplication(), Const.SPU_NAME, "imagecache_clear_time", 0L);
            if((System.currentTimeMillis() - lastClearTime) > 24 * 60 * 60 * 1000){
                TaskManager.getInstance().run(new Worker() {
                    @Override
                    public void work() {
                        Log.i(TAG, "clearDiskCache");
//                        CommonSDCardUtil.clearCache();
                        clearDishCache(); //通过DishLruCache对象来清除
                    }
                });
                SPUtil.putLong(UnityPluginUtils.getActivity("imgload").getApplication(), Const.SPU_NAME, "imagecache_clear_time", System.currentTimeMillis());
            }
        }catch (Exception ex){
            if(Const.DEBUG){
                ex.printStackTrace();
            }
        }

    }

    public void clearDishCache() {

        synchronized (mDiskCacheLock) {
            mDiskCacheStarting = true;
            if (mDiskLruCache != null && !mDiskLruCache.isClosed()) {
                try {
                    mDiskLruCache.delete();
                } catch (Throwable e) {
                    Log.e(TAG, "clearCache - " + e);
                }
                mDiskLruCache = null;
                initDiskCache();
            }
        }
    }

    List<WeakReference<Object>> imageLoaderRefList = new ArrayList<WeakReference<Object>>();;

    /**
     * 增加引用
     * @param obj
     */
    public synchronized void setImageloaderRef(Object obj){
        if(obj == null){
            return;
        }
        WeakReference<Object> weakReference = new WeakReference<Object>(obj);
        if(imageLoaderRefList == null){
            imageLoaderRefList = new ArrayList<WeakReference<Object>>();
        }
        synchronized (imageLoaderRefList){
            imageLoaderRefList.add(weakReference);
        }

    }

    /**
     * 去除引用
     * @param obj
     */
    public synchronized void removeImageLoaderRef(Object obj){
        if(imageLoaderRefList != null && imageLoaderRefList.size() > 0){
            synchronized (imageLoaderRefList){
                for(WeakReference<Object> weakReference: imageLoaderRefList){
                    Object objTmp = weakReference.get();
                    if(objTmp != null && objTmp.equals(obj)){
                        imageLoaderRefList.remove(weakReference);
                        return;
                    }
                }

            }
        }
    }

    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a disk
     * filename.
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
