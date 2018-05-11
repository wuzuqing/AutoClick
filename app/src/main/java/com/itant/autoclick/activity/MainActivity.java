package com.itant.autoclick.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.itant.autoclick.service.MainService;
import com.itant.autoclick.util.ScreenCapture;

public class MainActivity extends NoAnimatorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 23) {
            start();
        } else {
            if (Settings.canDrawOverlays(this)) {
                start();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void start() {
        if (BaseApplication.DEBUG()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                //请求权限
                ScreenCapture.requestCapturePermission(this);
            } else {
                startMainService();
            }
        }
    }
    private void startMainService() {

//        if (WakeAndLock.get().ScreenLock(this)){
//            return;
//        }
        if (getIntent() != null && getIntent().hasExtra("hao")) {
            Intent intent1 = new Intent(MainActivity.this, MainService.class);
            intent1.putExtra("action", "ACTION_BOOT_COMPLETED");
            startService(intent1);
            finish();
        } else {
            MainService.start(MainActivity.this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ScreenCapture.onActivityResult(this, requestCode, resultCode, data)) {
            startMainService();
        }else{
            startMainService();
        }
    }

}
