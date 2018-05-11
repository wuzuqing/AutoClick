//package com.itant.autoclick.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.PixelFormat;
//import android.graphics.Point;
//import android.hardware.display.DisplayManager;
//import android.hardware.display.VirtualDisplay;
//import android.media.ImageReader;
//import android.media.projection.MediaProjection;
//import android.media.projection.MediaProjectionManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Display;
//import android.widget.Toast;
//
//import com.itant.autoclick.util.GBData;
//
///**
// * @author 吴祖清
// * @version $Rev$
// * @createTime 2017/12/23 19:11
// * @des ${TODO}
// * @updateAuthor $Author$
// * @updateDate 2017/12/23$
// * @updateDes ${TODO}
// */
//
//public class MediaProjectionActivity extends NoAnimatorActivity {
//    private static final int REQUEST_MEDIA_PROJECTION = 1;
//    private MediaProjectionManager mMediaProjectionManager;
//    private MediaProjection mMediaProjection;
//    private VirtualDisplay mVirtualDisplay;
//    private static final String TAG = "MediaProjectionActivity";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
//
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
//
//            assert mMediaProjectionManager != null;
//            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (Build.VERSION.SDK_INT >= 21) {
//
//
//            if (requestCode == REQUEST_MEDIA_PROJECTION) {
//                if (resultCode != Activity.RESULT_OK) {
//                    Log.i(TAG, "User cancelled");
//                    Toast.makeText(this, "User cancelled!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Log.i(TAG, "Starting screen capture");
//
//                mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
//                setUpVirtualDisplay();
//            }
//            finish();
//        }
//    }
//
//    private void setUpVirtualDisplay() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            Point size = new Point();
//            DisplayMetrics metrics = new DisplayMetrics();
//            Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
//            defaultDisplay.getSize(size);
//            defaultDisplay.getMetrics(metrics);
//
//            final ImageReader imageReader = ImageReader.newInstance(size.x, size.y, PixelFormat.RGBA_8888, 1);
//            mVirtualDisplay = mMediaProjection.createVirtualDisplay("ScreenCapture", size.x, size.y, metrics.densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, imageReader.getSurface(), null, null);
//            GBData.reader = imageReader;
//        }
//    }
//}
