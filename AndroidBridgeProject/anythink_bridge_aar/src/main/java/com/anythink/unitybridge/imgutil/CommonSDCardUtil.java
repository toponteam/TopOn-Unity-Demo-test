
package com.anythink.unitybridge.imgutil;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.UUID;

/**
 * 描述: SDCard工具类
 *
 * @author chenys
 * @since 2013-7-11 下午4:25:27
 */


@SuppressWarnings("all")
public class CommonSDCardUtil {
	
	
	static boolean mHasPermission = false;
	static String mCachePath = "";
	static String mFilePath = "";
    static final long REMOVE_TIME_INTERVAL = 7 * 24 * 60 * 60 * 1000;
	
	
	public static void init(Context c){
		
		try {
			mCachePath = c.getFilesDir().getAbsolutePath() + File.separator;
//			FileUtil.createFolder(mCachePath, true);

			PackageManager pm = c.getPackageManager();
			int hasPerm = pm.checkPermission(
			    android.Manifest.permission.WRITE_EXTERNAL_STORAGE, 
			    c.getPackageName());
			if (hasPerm == PackageManager.PERMISSION_GRANTED) {
				mHasPermission = true;
			}else{
				mHasPermission = false;
			}

            mFilePath = getRootPath(c);
		} catch (Exception e) {
			mCachePath = c.getFilesDir().getAbsolutePath() + File.separator;
		}
		
	}
	
	
//	public static boolean hasPermission() {
//        return mHasPermission;
//    }
	
	
//	public static String getAppCachePath() {
//
//        return mCachePath;
//    }


    /**
     * 判断是否存在SDCard
     *
     * @return
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    /**
     * SDCard剩余大小
     *
     * @return 字节
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableExternalMemorySize() {
        if (hasSDCard()) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                return availableBlocks * blockSize;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }catch(Error e){
                if(Const.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * 获取存储根路径
     * @param context
     * @return
     */
    public static String getRootPath(Context context){
        File baseFile = null;

        /** 和权限无关 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                File fileDir = context.getExternalFilesDir(null);
                if (fileDir != null) {
                    /** 创建随机文件，看是否可创建，如果可以则认为可写入 */
                    baseFile = getRandomFileDir(fileDir);
                }
            }catch (Throwable t){
                Log.e("CommonSDKCardUtil", "hasSDCard is failed",t);
            }
        }

        //有权限,才能获取到空间
        if(mHasPermission){
            if(baseFile == null){
                String rootPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "Android" + File.separator + "data" + File.separator + context.getPackageName();
                baseFile = getRandomFileDir(new File(rootPath));
            }
            //如果sd卡空间不够，认为只能走保底路径
            if(!hasEnoughSpace()){
                baseFile = null;
            }
        }

        if(baseFile == null || !baseFile.exists()){
            baseFile = context.getFilesDir().getAbsoluteFile();
        }
        return baseFile.getAbsolutePath();
    }

    private static File getRandomFileDir(File fileDir) {
        File baseFile = null;
        File temp = new File(fileDir, UUID.randomUUID() + "");
        if (temp.exists()) {
            temp.delete();
        }
        if (temp.mkdirs()) {
            temp.delete();
            baseFile = fileDir.getAbsoluteFile();
        }
        return baseFile;
    }

    public static String getFileCachePath() {
        Log.i("CommonSDCardUtil", "save path:" + mFilePath);
        return mFilePath;
    }
    
    
    /** 
     * 计算剩余空间 
     * @param path 
     * @return 
     */  
//    public static long getDataAvailableSize(Context context)
//    {
//    	long size = 0;
//    	try{
//    		String path = context.getFilesDir().getAbsolutePath();
//    		StatFs fileStats = new StatFs(path);
//            fileStats.restat(path);
//            long blockSize = fileStats.getBlockSize();
//            long availableBlocks = fileStats.getAvailableBlocks();
//            size = availableBlocks * blockSize;
//    	}catch(Exception e){
//
//    	}
//
//        return size;
//    }


    /**
     * 是否有足够的空间（30M）
     *
     * @return
     */
    public static boolean hasEnoughSpace() {
        return getAvailableExternalMemorySize() > 30 * 1024 * 1024;
    }

//    /**
//     * 是否有足够的空间
//     *
//     * @param minSize 最小值
//     * @return
//     */
//    public static boolean hasEnoughSpace(long minSize) {
//        return getAvailableExternalMemorySize() > minSize;
//    }

    /**
     * SDCard总容量大小
     *
     * @return 字节
     */
    @SuppressWarnings("deprecation")
//    public static long getTotalExternalMemorySize() {
//        if (hasSDCard()) {
//            try {
//                File path = Environment.getExternalStorageDirectory();
//                StatFs stat = new StatFs(path.getPath());
//                long blockSize = stat.getBlockSize();
//                long totalBlocks = stat.getBlockCount();
//                return totalBlocks * blockSize;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return 0;
//            }
//        }
//        return 0;
//    }

    /**
     * 这个是手机内存的可用空间大小
     *
     * @return
     */
//    @SuppressWarnings("deprecation")
//    public static long getAvailableInternalMemorySize() {
//        File path = Environment.getDataDirectory();
//        StatFs stat = new StatFs(path.getPath());
//        long blockSize = stat.getBlockSize();
//        long availableBlocks = stat.getAvailableBlocks();
//        return availableBlocks * blockSize;
//    }

    /**
     * 这个是手机内存的总空间大小
     *
     * @return
     */

    public static void orderByDateAndDel(String fliePath) {
        try{
            File file = new File(fliePath);
            File[] fs = file.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {//过滤掉隐藏文件
                    if(System.currentTimeMillis() - pathname.lastModified() > REMOVE_TIME_INTERVAL){
                        return true;
                    }else{
                        return false;
                    }
                }
            });
//            Arrays.sort(fs, new Comparator<File>() {
//                public int compare(File f1, File f2) {
//                    long diff = f1.lastModified() - f2.lastModified();
//                    if (diff > 0)
//                        return 1;
//                    else if (diff == 0)
//                        return 0;
//                    else
//                        return -1;
//                }
//
//                public boolean equals(Object obj) {
//                    return true;
//                }
//
//            });
            for (int i = 0; i < fs.length; i++) {
                File file2 = fs[i];
                try{
                    if (file2.exists() && file2.isFile()) {
                        file2.delete();
                    }
                }catch(Exception e){
                }
            }
        }catch(Exception e){
        }
    }

}
