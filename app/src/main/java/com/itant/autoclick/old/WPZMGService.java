//package com.itant.autoclick.service;
//
//import android.app.ActivityManager;
//import android.app.Service;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//
//import com.itant.autoclick.Constant;
//import com.itant.autoclick.model.PointModel;
//import com.itant.autoclick.model.TaskModel;
//import com.itant.autoclick.tool.AutoTool;
//import com.itant.autoclick.util.CmdData;
//import com.itant.autoclick.util.LogUtils;
//import com.itant.autoclick.util.NetWorkUtils;
//import com.itant.autoclick.util.TaskUtil;
//import com.itant.autoclick.util.ToastUitl;
//import com.itant.autoclick.util.Util;
//
//import java.util.List;
//
//public class WPZMGService extends Service implements Constant {
//
//    private List<TaskModel> tasks;
//    private long currentTime;
//    private static final int RESET_TASK = 200;
//    private static final int TASK_ERROR = 100;
//    private static final int TASK_TOAST = 500;
//    private static final int TASK_STOP = 2500;
//    private static final int TASK_TOAST_COUNT_DOWN = 1500;
//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case TASK_ERROR:
//                    mHandler.sendEmptyMessageDelayed(RESET_TASK, 3000);
//                    break;
//                case RESET_TASK:
//                    new Thread(new TaskThread()).start();
//                    break;
//                case TASK_TOAST:
//                    LogUtils.logd("正在执行" + msg.obj.toString());
//                    ToastUitl.showShort("正在执行" + msg.obj.toString());
//                    break;
//                case TASK_TOAST_COUNT_DOWN:
//                    int countDown = msg.arg1;
//                    if (countDown == 0) {
//                        mHandler.sendMessageDelayed(Message.obtain(mHandler, RESET_TASK), 10);
//                    } else {
//                        String friendTime = getFriendTime(countDown);
//                        LogUtils.logd(friendTime);
//                        ToastUitl.showShort(friendTime);
//                        mHandler.sendMessageDelayed(mHandler.obtainMessage(TASK_TOAST_COUNT_DOWN, --countDown, 0), 1000);
//                    }
//                    break;
//                case TASK_STOP:
//                    LogUtils.logd("已取消任务");
//                    ToastUitl.showShort("已取消任务");
//                    mHandler.removeCallbacksAndMessages(null);
//                    mHandler.removeCallbacksAndMessages(null);
//                    isDestory = true;
//                    stopSelf();
//                    break;
//            }
//            return false;
//        }
//    });
//
//    private boolean isDestory;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null && intent.hasExtra("stop")) {
//            mHandler.sendEmptyMessage(TASK_STOP);
//        } else {
//            isDestory = false;
//            new Thread(new TaskThread()).start();
//        }
//        return START_STICKY;
//    }
//
//
//    public static void onStop(Context context) {
//        Intent intent = new Intent(context, WPZMGService.class);
//        intent.putExtra("stop", true);
//        context.startService(intent);
//    }
//
//    /**
//     * 判断某个界面是否在前台
//     *
//     * @param context Context
//     * @return 是否在前台显示
//     */
//    public boolean isForeground(Context context) {
//        if (context == null) return false;
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
//        if (list != null && list.size() > 0) {
//            ComponentName cpn = list.get(0).topActivity;
//            if (PAKAGE_NAME.equals(cpn.getPackageName())) return true;
//        }
//        return false;
//    }
//
//
//    /**
//     * 闭其他的应用程序
//     *
//     * @param context Context
//     * @return 是否在前台显示
//     */
//    public void killBackgroundProcesses(Context context) {
//        if (context == null) return;
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        am.killBackgroundProcesses(PAKAGE_NAME);
//    }
//
//    private String PAKAGE_NAME = "com.anzhuojwgly.ckhd";
//
//    private class TaskThread implements Runnable {
//        @Override
//        public void run() {
//            try {
//                int count = 0;
//                while (!isForeground(getApplicationContext())) {
//                    if (isDestory) return;
//
//                    Thread.sleep(2000);
//                    count++;
//                    if (count == 15) {
//                        mHandler.sendMessageDelayed(mHandler.obtainMessage(TASK_TOAST_COUNT_DOWN, 60, 0), 10);
//                        return;
//                    }
//                    send("请进入游戏界面");
//                }
//
//                while (!NetWorkUtils.isNetConnected(getApplicationContext())) {
//                    if (isDestory) return;
//                    Thread.sleep(2000);
//                }
//                tasks = Util.getTaskModel();
//                netPoint = CmdData.get(NET_CLOSE);
//                dialogClose3 = CmdData.get(DIALOG_CLOSE3);        //道具对话框关闭按钮
//                int nextTime = 0;
//                long startTime = 0;
//                for (TaskModel task : tasks) {
//                    startTime = currentTime = System.currentTimeMillis();
//                    if (currentTime >= task.getLastRefreshTime()) {
//                        task.setLastRefreshTime(currentTime + task.getSpaceTime() * 1000);
//                        nextTime = task.getSpaceTime();
//                        execute(task);
//                    } else {
//                        nextTime = (int) ((task.getLastRefreshTime() - currentTime) / 1000);
//                    }
//                    if (isDestory) return;
//                    Thread.sleep(300);
//                }
//                startTime = System.currentTimeMillis() - startTime;
//                mHandler.sendMessageDelayed(mHandler.obtainMessage(TASK_TOAST_COUNT_DOWN, (int) (nextTime - startTime / 1000 + 2), 0), 10);
//            } catch (Exception e) {
//                e.printStackTrace();
//                mHandler.sendEmptyMessage(TASK_ERROR);
//            }
//        }
//    }
//
//    public void send(String msg) {
//        mHandler.sendMessage(mHandler.obtainMessage(TASK_TOAST, msg));
//    }
//
//    public String getFriendTime(int countDownTime) {
//        int second = countDownTime % 60; //秒
//        int min = countDownTime / 60 % 60; //分
//        return String.format("%d分%d秒后执行", min, second);
//    }
//
//    private void execute(TaskModel task) throws Exception {//异步
//        send(task.getName());
//        switch (task.getType()) {
//            case ONE:
//                oneTask(task);
//                break;
//        }
//    }
//
//
//    private void resetFail() {
//        failCount = 0;
//        needContinue = false;
//    }
//    private static int failCount = 0;
//    private static boolean needContinue;
//    private PointModel netPoint;
//    private PointModel dialogClose3;
//
//
//    private void oneTask(TaskModel task)  throws InterruptedException {
//        List<PointModel> pointModels = task.getData();
//        int count = 0;
//        send(task.getName());
//        resetFail();
//        while (true) {
//            if (isDestory) return;
//             Util.getCapBitmap();
//            if (TaskUtil.checkExp( netPoint, "当前网络异常")) continue;//检查网络环境
//            if (TaskUtil.checkExp( dialogClose3, "关闭道具框")) continue;//检查网络环境
//            count = 0;
//            if (isDestory) return;
//            if (check(failCount, 20)) break;
//            for (int i = 0; i < pointModels.size(); i++) {
//                PointModel pointModel = pointModels.get(i);
//                if (!Util.checkColor( pointModel)) {
//                    if (isDestory) return;
//                    AutoTool.execShellCmd(pointModel);
//                    Thread.sleep(280);
//                    count++;
//                }
//            }
//
//            Thread.sleep(600);
//            if (count > 10) {
//                AutoTool.execShellCmd(CmdData.screenClose);
//                Thread.sleep(600);
//                oneTask(task);
//            }
//            LogUtils.logd(" count" + count);
//            if (count == 0) {
//                return;
//            }
//        }
//    }
//    private boolean check(int failCount, int maxCount) {
//        boolean check = failCount > maxCount;
//        needContinue = check;
//        WPZMGService.failCount++;
//        return check;
//    }
//
//
//}
