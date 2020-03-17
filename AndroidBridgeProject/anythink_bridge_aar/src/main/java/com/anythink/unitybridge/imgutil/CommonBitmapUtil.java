
package com.anythink.unitybridge.imgutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import java.io.FileDescriptor;

/**
 * Bitmap创建类
 *
 * @author chenys
 */
public class CommonBitmapUtil {

//
//
//    public static Bitmap decodeFile(String pathName) {
//        Bitmap bitmap = null;
//        if (CommonFileUtil.isFileExist(pathName)) {
//            Options opts = new Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(pathName, opts);
//            opts.inJustDecodeBounds = false;
//            opts.inPurgeable = true;
//            opts.inInputShareable = true;
//            opts.inDither = true;
//
//            try {
//                bitmap = BitmapFactory.decodeFile(pathName, opts);
//            } catch (OutOfMemoryError e) {
//                if(Const.DEBUG){ e.printStackTrace();}
//                System.gc();
//                try {
//                    opts.inPreferredConfig = Config.RGB_565;
//                    bitmap = BitmapFactory.decodeFile(pathName, opts);
//                    opts.inPreferredConfig = Config.ARGB_8888;
//                } catch (OutOfMemoryError e1) {
//                    if(Const.DEBUG){e1.printStackTrace();}
//                }
//            } catch (Exception e2) {
//                if(Const.DEBUG) {e2.printStackTrace();}
//            }
//        }else{
//            return null;
//        }
//        return bitmap;
//    }




    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * Calculate an inSampleSize for use in a {@link Options} object
     * when decoding bitmaps using the decode* methods from {@link BitmapFactory}.
     * This implementation calculates the closest inSampleSize that is a power of 2 and will result
     * in the final decoded bitmap having a width and height equal to or larger than the requested
     * width and height.
     *
     * @param options An options object with out* params already populated (run through a decode*
     *        method with inJustDecodeBounds==true
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        // BEGIN_INCLUDE (calculate_sample_size)
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (reqWidth <= 0 && reqHeight <= 0) {
            return inSampleSize;
        }
        if(reqWidth > 0 && reqHeight == 0){
            //对于只需要宽度的图片进行按照宽度缩放
        }
        if (height > reqHeight || width > reqWidth) {
            if(reqWidth > 0 && reqHeight == 0){
                //对于只需要宽度的图片进行按照宽度缩放
                reqHeight = height * reqWidth /width;
            }
            if(reqHeight > 0 && reqWidth == 0){
                //对于只需要宽度的图片进行按照宽度缩放
                reqWidth = width * reqHeight /height;
            }
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            long totalPixels = width * height / (inSampleSize * inSampleSize);

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 4;

            while (totalPixels > totalReqPixelsCap && ((totalPixels / 4) > totalReqPixelsCap)) {
                inSampleSize *= 2;
                totalPixels /= 4;
            }
        }
        return inSampleSize;
        // END_INCLUDE (calculate_sample_size)
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
