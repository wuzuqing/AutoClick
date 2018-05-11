//package com.itant.autoclick.util;
//
//import android.graphics.Bitmap;
//import android.media.Image;
//import android.media.ImageReader;
//import android.os.Build;
//import android.util.Log;
//
//import java.nio.ByteBuffer;
//
///**
// * @author 吴祖清
// * @version 1.0
// * @createDate 2018/1/1 20:30
// * @des ${TODO}
// * @updateAuthor #author
// * @updateDate 2018/1/1
// * @updateDes ${TODO}
// */
//
//public class GBData {
//    private static final String TAG = "GBData";
//   public static ImageReader reader;
//    private static Bitmap bitmap;
//
//    public static String getColorStr(int x,int y){
//        return Util.getColorHtml(getColor(x, y));
//    }
//
//    public static int getColor(int x, int y) {
//        if (reader == null) {
//            Log.w(TAG, "getClor: reader is null");
//            return -1;
//        }
//        if(Build.VERSION.SDK_INT<21)return 0;
//        Image image = reader.acquireLatestImage();
//
//        if (image == null) {
//            if (bitmap == null) {
//                Log.w(TAG, "getColor: image is null");
//                return -1;
//            }
//            return bitmap.getPixel(x, y);
//        }
//        int width = image.getWidth();
//        int height = image.getHeight();
//        final Image.Plane[] planes = image.getPlanes();
//        final ByteBuffer buffer = planes[0].getBuffer();
//        int pixelStride = planes[0].getPixelStride();
//        int rowStride = planes[0].getRowStride();
//        int rowPadding = rowStride - pixelStride * width;
//        if (bitmap == null) {
//            bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
//        }
//        bitmap.copyPixelsFromBuffer(buffer);
//        image.close();
//
//        return bitmap.getPixel(x, y);
//    }
//}
