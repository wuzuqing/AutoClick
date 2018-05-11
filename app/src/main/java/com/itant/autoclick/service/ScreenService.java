package com.itant.autoclick.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;

import com.itant.autoclick.activity.MainActivity;
import com.itant.autoclick.util.HandlerUtil;
import com.itant.autoclick.util.LaunchApp;
import com.itant.autoclick.util.SuUtil;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/4/23 23:37
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/4/23$
 * @updateDes ${TODO}
 */

public class ScreenService extends Service {

    //声明键盘管理器
    KeyguardManager mKeyguardManager = null;
    //声明键盘锁
    private KeyguardManager.KeyguardLock mKeyguardLock = null;
    //声明电源管理器
    private PowerManager pm;
    private PowerManager.WakeLock wakeLock;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
//获取电源的服务
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//获取系统服务
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //点亮亮屏
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wakeLock.acquire();
        //初始化键盘锁，可以锁定或解开键盘锁
        mKeyguardLock = mKeyguardManager.newKeyguardLock("");
        //禁用显示键盘锁定
        mKeyguardLock.disableKeyguard();

        HandlerUtil.post(new Runnable() {
            @Override
            public void run() {
                LaunchApp.launchapp(getApplicationContext(), LaunchApp.JPZMG_PACKAGE_NAME);    //启动游戏
                HandlerUtil.post(new Runnable() {
                    @Override
                    public void run() {
                        SuUtil.kill();       //退出游戏
                        Intent start = new Intent(ScreenService.this, MainActivity.class);
                        start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//
                        start.putExtra("hao", true);
                        startActivity(start);
                        stopSelf();
                    }
                }, 4000);
            }
        }, 2000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        wakeLock.release();
        super.onDestroy();
    }
}