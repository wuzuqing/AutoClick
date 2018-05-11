package com.itant.autoclick.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.itant.autoclick.Constant;
import com.itant.autoclick.activity.BaseApplication;
import com.itant.autoclick.model.PointModel;
import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.model.UserInfo;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.HandlerUtil;
import com.itant.autoclick.util.LaunchApp;
import com.itant.autoclick.util.LogUtils;
import com.itant.autoclick.util.NetWorkUtils;
import com.itant.autoclick.util.SPUtils;
import com.itant.autoclick.util.SuUtil;
import com.itant.autoclick.util.TaskUtil;
import com.itant.autoclick.util.ToastUitl;
import com.itant.autoclick.util.Util;

import java.util.List;

public class WPZMGService2 extends Service implements Constant {

    private List<TaskModel> tasks;

    private TaskThread mTaskThread;
    private static int currentUserInfo = 0;
    private UserInfo userInfo;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case TASK_ERROR:
                    mHandler.sendEmptyMessageDelayed(RESET_TASK, 3000);
                    break;
                case RESET_TASK:
                    new Thread(new TaskThread()).start();
                    break;
//                case SCREEN_OFF:
//                    WakeAndLock.get().ScreenLock();
//                    break;
//                case SCREEN_ON:
//                    WakeAndLock.get().wakeAndUnlock(true);
//                    break;
                case TASK_TOAST:
                    LogUtils.logdAndToast("正在执行" + msg.obj.toString());

                    break;
                case TASK_TOAST_COUNT_DOWN:
                    int countDown = msg.arg1;
                    if (countDown == 0) {
                        mHandler.sendMessageDelayed(Message.obtain(mHandler, RESET_TASK), 10);
                    } else {
                        String friendTime = getFriendTime(countDown);
                        LogUtils.logd(friendTime);
                        ToastUitl.showShort(friendTime);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(TASK_TOAST_COUNT_DOWN, --countDown, 0), 1000);
                    }
                    break;
                case TASK_STOP:
                    LogUtils.logd("已取消任务");
                    ToastUitl.showShort("已取消任务");
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.removeCallbacksAndMessages(null);
                    TaskUtil.isDestory = true;
                    stopSelf();
                    break;
            }
            return false;
        }
    });


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TaskUtil.isNewApi = Build.VERSION.SDK_INT >= 21;
        mTaskThread = new TaskThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.hasExtra("stop")) {
                mHandler.sendEmptyMessage(TASK_STOP);
            } else if (intent.hasExtra("oneTask")) {
                tasks = Util.getTaskModel();
                TaskUtil.isDestory = false;
                List<UserInfo> userInfos = Util.getUserInfo();
                currentUserInfo = SPUtils.getInt(CURRENT_USER_INFO);
                if (userInfo == null) {
                    userInfos.get(currentUserInfo % userInfos.size());
                }
                if (userInfo==null){
                    userInfo =  new UserInfo("ck71503775","520333");
                }
                HandlerUtil.async(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            execute(userInfo);
                            LogUtils.logd("task is over");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                TaskUtil.isDestory = false;
                HandlerUtil.async(mTaskThread);
            }
        } else {
            TaskUtil.isDestory = false;
            HandlerUtil.async(mTaskThread);
        }
        return START_STICKY;
    }

    private class TaskThread implements Runnable {
        @Override
        public void run() {
            try {

                tasks = Util.getTaskModel();
                List<UserInfo> userInfos = Util.getUserInfo();
                currentUserInfo = SPUtils.getInt(CURRENT_USER_INFO);
                if (SPUtils.getBoolean(KEY_REGISTER)) {
                    TaskUtil.register(null, null);
                    return;
                }
                TaskUtil.resetFail();
                while (!NetWorkUtils.isNetConnected(getApplicationContext())) {//检查网络
                    if (TaskUtil.isDestory) return;
                    if (TaskUtil.check(TaskUtil.failCount, 5)) {
                        mHandler.sendEmptyMessageDelayed(RESET_TASK, 60000);
                        return;
                    }
                    Thread.sleep(2000);
                }



                PointModel loginClose = CmdData.get(LOGIN_DIALOG_CLOSE);
                PointModel loginGame = CmdData.get(LOGIN_GAME);
                PointModel startGame = CmdData.get(START_GAME);
                PointModel dialogClose2 = CmdData.get(DIALOG_CLOSE2);//通告对话框关闭
                PointModel genXin = CmdData.get(GEN_XIN);//通告对话框关闭
                TaskUtil.netPoint = CmdData.get(NET_CLOSE);
                TaskUtil.dialogClose3 = CmdData.get(DIALOG_CLOSE3);        //道具对话框关闭按钮
                boolean isLoop = SPUtils.getBoolean(KEY_LOOP);
                boolean onlyFl = SPUtils.getBoolean(KEY_WORK_ONLY_FL);
                boolean hasGengXin = Util.checkHasGengXin();
//                boolean hasGengXin = SPUtils.getBoolean(KEY_GENG_XIN);
                boolean zhTask = SPUtils.getBoolean("zh");
                Util.refreshNextDayTime();
                int count = 0;
                while (true) { //循环执行小号操作
                    SuUtil.kill();       //退出游戏

                    if (TaskUtil.bitmap != null && !TaskUtil.bitmap.isRecycled()) {
                        TaskUtil.bitmap.recycle();
                        TaskUtil.bitmap = null;
                        System.gc();
                    }
                    userInfo = userInfos.get(currentUserInfo % userInfos.size());
                    if (onlyFl){
                        Util.lockFengLu(userInfo.getName(),"LOCK","1",5555);
                        currentUserInfo++;
                        userInfos.add(userInfo);
                        userInfo = userInfos.get(currentUserInfo % userInfos.size());
                    }
                    if (TaskUtil.isDestory) return;
                    Thread.sleep(BaseApplication.densityDpi ==480?3000:BaseApplication.getScreenWidth() == 1080 ? 1000 : 2500);
                    LaunchApp.launchapp(getApplicationContext(), LaunchApp.JPZMG_PACKAGE_NAME);    //启动游戏
                    Thread.sleep(TaskUtil.isNewApi ? 5000 : 3000);
                    TaskUtil.resetFail();
                    while (true) {   //检查准备输入账号的环境
                        if (TaskUtil.isDestory) return;
                        Util.getCapBitmapNew();
                        if (Util.checkColor(loginClose) || TaskUtil.check(TaskUtil.failCount, 8)) break;
                        Thread.sleep(600);
                    }
                    if (TaskUtil.needContinue) continue;

                    if (onlyFl) {
                        int userCount = 0;
                        while (true) {
                            if (!TextUtils.isEmpty(Util.getFengLuFileString(String.format("%s%s", userInfo.getName(), KEY_WORK_FL)))) {
                                if (userCount == userInfos.size()) {
                                    TaskUtil.isDestory = true;
                                    LogUtils.logd(" task is over");
                                    return;
                                }
                                currentUserInfo++;
                                userInfo = userInfos.get(currentUserInfo % userInfos.size());
                                LogUtils.logd("");
                                userCount++;
                                LogUtils.logd("userCount:" + userCount + " currentUserInfo:" + currentUserInfo + " userInfo:" + userInfo);
                            } else {
                                break;
                            }
                        }
                    }
                    int length = 11;
                    Thread.sleep(200);
                    AutoTool.execShellCmd(length + 1, 112);
                    Thread.sleep(500);
                    AutoTool.execShellCmd(CmdData.inputTextUserInfoName + userInfo.getName()); //输入账号
                    Thread.sleep(BaseApplication.getScreenWidth() == 1080 ? 500 : 2500);
                    AutoTool.execShellCmd(loginGame); //点击登录
                    TaskUtil.sleep(1000);
                    TaskUtil.failCount = 0;
                    while (hasGengXin) {               //检查进入游戏的环境
                        if (TaskUtil.isDestory) return;
                        LogUtils.logd("hasGengXin:" + hasGengXin);
                        TaskUtil.sleep(2200);
                        Util.getCapBitmapNew();
                        if (Util.checkColor(genXin)) {
                            AutoTool.execShellCmd(dialogClose2);  //维护公告
                            Thread.sleep(TaskUtil.isNewApi ? 1200 : 600);
                            break;
                        }else if (Util.checkColor(startGame)){
                            break;
                        }
                        if (TaskUtil.check(TaskUtil.failCount, 12)) break;

                    }
                    if (TaskUtil.needContinue) continue;
                    while (true) {               //检查进入游戏的环境
                        if (TaskUtil.isDestory) return;
                        Util.getCapBitmapNew();
                        if (Util.checkColor(startGame)) {
                            AutoTool.execShellCmd(startGame);  //进入游戏
                            Thread.sleep(TaskUtil.isNewApi ? 4500 : 3000);
                            TaskUtil.failCount = 0;
                            break;
                        } else {
                            boolean check = TaskUtil.check(TaskUtil.failCount, 12);
                            if (check) break;
                        }
                    }

                    while (true) {                           //检查 通告对话框的环境
                        if (TaskUtil.isDestory) return;

                        Util.getCapBitmapNew();
                        if (Util.checkColor(dialogClose2) || TaskUtil.check(TaskUtil.failCount, 8)) break;
                        TaskUtil.sleep(600);
                    }
                    if (TaskUtil.needContinue) continue;
                    AutoTool.execShellCmd(dialogClose2);  //关闭通告对话框
                    Thread.sleep(500);
                    if (userInfos.size() == 1) {
                        if (TaskUtil.isDestory) return;
                        if (execute(userInfo)) return;
                        restart();  return;
                    } else {
                        currentUserInfo++;
                        if (onlyFl){
                            Util.unLockFengLu(userInfo.getName(),"LOCK");
                        }
                        SPUtils.setInt(CURRENT_USER_INFO, currentUserInfo);
                        if (execute(userInfo)) return;
                    }
                    boolean isEnd = ++count == userInfos.size();
                    if (!isLoop && isEnd) {
                        TaskUtil.isDestory = true;
                        SuUtil.kill();       //退出游戏
                        LogUtils.logd(" task is over");
                        return;
                    } else if (isEnd && zhTask) {
                        restart();
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(TASK_ERROR);
            }
        }
    }

    private void restart() {
        SuUtil.kill();       //退出游戏
        if (TaskUtil.bitmap != null && !TaskUtil.bitmap.isRecycled()) {
            TaskUtil.bitmap.recycle();
            TaskUtil.bitmap = null;
            System.gc();
        }

//        AlarmService.set(this, SPUtils.getInt("space", 240));
//        stopSelf();
//        LogUtils.logd("sleep" + SPUtils.getInt("space", 240));
        mHandler.sendMessageDelayed(mHandler.obtainMessage(TASK_TOAST_COUNT_DOWN, SPUtils.getInt("space", 240), 0), 1000);
//        mHandler.sendEmptyMessageDelayed(SCREEN_OFF,3000);

    }

    private boolean execute(UserInfo userInfo) throws Exception {
        for (TaskModel task : tasks) { //循环执行任务
            if (TaskUtil.isDestory) return true;
            execute(task, userInfo);
            Thread.sleep(300);
        }
        return false;
    }


    public String getFriendTime(int countDownTime) {
        int second = countDownTime % 60; //秒
        int min = countDownTime / 60 % 60; //分
        int hour = countDownTime / 3600 % 24;
        return String.format("%d时%d分%d秒后执行", hour, min, second);
    }


    private void execute(final TaskModel task, final UserInfo userInfo) throws Exception {//异步
        switch (task.getType()) {
            case ONE:       //收菜
                boolean old = SPUtils.getBoolean(KEY_OLD_SHOU_CAI, true);
                if (old) {
                    TaskUtil.oneTaskOld1(task);
                } else {
                    TaskUtil.oneTask(task);
                }
                break;
            case TASK_ZHENG_WU:       //政绩
                TaskUtil.twoTask(task, userInfo);
                break;
            case TASK_FENG_LU:      //俸禄
                TaskUtil.fourTask1(task, userInfo);
                break;
            case TASK_MO_BAI:     //膜拜
                TaskUtil.threeTask(task, userInfo);
                break;
            case TASK_REN_WU_JIANG_LU:      //任务奖励
                TaskUtil.fiveTask(task, userInfo);
                break;
            case TASK_GUAN_KA:      //关卡奖励
                TaskUtil.sixTaskNewV2(task, userInfo);
                break;
            case TASK_SHU_YUAN:      //书院学习
                TaskUtil.shuYan(task, userInfo);
                break;
//            case TASK_YAN_HUI:      //赴宴
//                TaskUtil.yanHui(task, userInfo);
//                break;
            case TASK_CHEGN_JIU:      //成就
                TaskUtil.chengJiu(task, userInfo);
                break;
            case TASK_REGISTER:      //注册
                TaskUtil.register(task, userInfo);
                break;
            case TIME_LIMIT_REWARD:      //限时奖励
                TaskUtil.timeLimitRewardNew(task, userInfo);
                break;
            case EMAIL:      //邮箱降了奖励
                TaskUtil.email(task, userInfo);
                break;
            case LIAN_MENG_GAO_JIAN:      //联盟高建
                TaskUtil.lianMengGaoJian(task, userInfo);
                break;
            case GUAN_YAN:      //官宴
                TaskUtil.yanHuiNew(task, userInfo);
                break;
            case TASK_LIAN_MENG_FU_BEN:      //联盟副本
                TaskUtil.lianMengFuBen(task, userInfo);
                break;
            case TASK_KUA_FU_JIANG_LI:      //跨服奖励
                TaskUtil.kuaFuJlNew(task, userInfo);
                break;
            case TASK_ZHEGN_LIU:      //正九自动点剧情
                TaskUtil.zhengJiu(task, userInfo);
                break;
            case TASK_LAO_FANG:      //牢房
                TaskUtil.laoFang(task, userInfo);
                break;
            case SUI_JI_ZHAO_HUAN:      //召唤
                TaskUtil.suiJiZhaoHuan(task, userInfo);
                break;
            case TASK_YA_MEN:      //牢房
                TaskUtil.yaMen(task, userInfo);
                break;
        }
    }
}
