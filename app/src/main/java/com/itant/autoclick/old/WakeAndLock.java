//package com.itant.autoclick.util;
//
//import android.app.Activity;
//import android.app.KeyguardManager;
//import android.app.admin.DevicePolicyManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.os.PowerManager;
//import android.util.Log;
//
//import com.itant.autoclick.activity.BaseApplication;
//import com.itant.autoclick.activity.Darclass;
//
///**
// * @author 吴祖清
// * @version $Rev$
// * @createTime 2018/4/10 20:56
// * @des ${TODO}
// * @updateAuthor $Author$
// * @updateDate 2018/4/10$
// * @updateDes ${TODO}
// */
//
//public class WakeAndLock {
//
//    protected static final int REQUEST_ENABLE = 0;
//    private static WakeAndLock sWakeAndLock = new WakeAndLock();
//
//    public static WakeAndLock get(){
//        return sWakeAndLock;
//    }
//    /**
//     * 组件
//     */
//    private   ComponentName devAdmReceiver;
//    private KeyguardManager km;
//    private KeyguardManager.KeyguardLock kl;
//    private PowerManager pm;
//    private PowerManager.WakeLock wl;
//    /**
//     * 设备管理
//     */
//    public DevicePolicyManager mDPM;
//
//    private static final String TAG = "WakeAndLock";
//
//    public boolean checkHasPre(Activity context) {
//        devAdmReceiver = new ComponentName(context, Darclass.class);
//        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
//        return mDPM.isAdminActive(devAdmReceiver);
//    }
//
//    //锁频
//    public boolean ScreenLock(Activity activity) {
//// TODO Auto-generated method stub
//        Context context = BaseApplication.getAppContext();
//        devAdmReceiver = new ComponentName(context, Darclass.class);
//        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
//        if (!mDPM.isAdminActive(devAdmReceiver)) {
//            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, devAdmReceiver);
//            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "设备管理涉及的管理权限,下面是清单,一次性激活!");
//            Log.e(TAG, "添加设备权限");
//            activity.startActivityForResult(intent, REQUEST_ENABLE);
//            return true;
//        } else {
////            mDPM.lockNow();
////            long locknow = System.currentTimeMillis();
////            Log.e(TAG, "locknow---syscurrenttime>>>" + locknow);
////            wakeAndUnlock(true);
//            return false;
//        }
//    }
//    private boolean isLock;
//    //锁频
//    public void ScreenLock() {
//// TODO Auto-generated method stub
//        Context context = BaseApplication.getAppContext();
//        devAdmReceiver = new ComponentName(context, Darclass.class);
//        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
//        if (mDPM.isAdminActive(devAdmReceiver)) {
//            mDPM.lockNow();
//            long locknow = System.currentTimeMillis();
//            Log.e(TAG, "locknow---syscurrenttime>>>" + locknow);
//            wakeAndUnlock(false);
//            isLock = true;
//        }
//    }
//
//    public boolean isLock() {
//        return isLock;
//    }
//
//    /**
//     * 唤醒 解锁
//     */
//    public void wakeAndUnlock(boolean b) {
//        Context context = BaseApplication.getAppContext();
//        if (b) {
//            // 获取电源管理器对象
//            pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            // 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
//            wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
//            // 点亮屏幕
//            wl.acquire();
//            // 得到键盘锁管理器对象
//            km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//            kl = km.newKeyguardLock("unLock");
//            // 解锁
//            kl.disableKeyguard();
//            isLock = false;
//        } else {
//            // 锁屏
//            kl.reenableKeyguard();
//            // 释放wakeLock，关灯
//            wl.release();
//        }
//    }
//}
