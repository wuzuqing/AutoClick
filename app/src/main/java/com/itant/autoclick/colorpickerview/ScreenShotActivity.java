//package com.itant.autoclick.activity;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.itant.autoclick.R;
//import com.itant.autoclick.util.LogUtils;
//import com.itant.autoclick.util.Util;
//
//public class ScreenShotActivity extends AppCompatActivity {
//
//    private android.widget.ImageView ivSort;
//    public static final int REQUEST_MEDIA_PROJECTION = 0x2893;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sort);
//        this.ivSort = (ImageView) findViewById(R.id.iv_sort);
//        Bitmap bitmap = Util.myShotBitmap(this);
//        ivSort.setImageBitmap(bitmap);
//        LogUtils.logd("bitmap:"+bitmap);
//        ivSort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
////        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
////        getWindow().setDimAmount(0f);
////
////        requestScreenShot();
//    }
//
////    public void requestScreenShot() {
////        if (Build.VERSION.SDK_INT >= 21) {
////            startActivityForResult(
////                    ((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent(),
////                    REQUEST_MEDIA_PROJECTION
////            );
////        }
////        else
////        {
////            toast("版本过低,无法截屏");
////        }
////    }
////
////    private void toast(String str) {
////        Toast.makeText(ScreenShotActivity.this,str,Toast.LENGTH_LONG).show();
////    }
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        switch (requestCode) {
////            case REQUEST_MEDIA_PROJECTION: {
////                if (resultCode == -1 && data != null) {
////                    Shotter shotter=new Shotter(ScreenShotActivity.this,data);
////                    shotter.startScreenShot(new Shotter.OnShotListener() {
////                        @Override
////                        public void onFinish() {
////                            toast("shot finish!");
////                            finish(); // don't forget finish activity
////                        }
////                    });
////                }
////            }
////        }
////    }
//}
