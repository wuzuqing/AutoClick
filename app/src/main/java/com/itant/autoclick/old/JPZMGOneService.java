//package com.itant.autoclick.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//
//import com.itant.autoclick.Constant;
//import com.itant.autoclick.activity.BaseApplication;
//import com.itant.autoclick.db.DbCore;
//import com.itant.autoclick.model.PointModel;
//import com.itant.autoclick.model.Result;
//import com.itant.autoclick.model.TaskModel;
//import com.itant.autoclick.model.UserInfo;
//import com.itant.autoclick.tool.AutoTool;
//import com.itant.autoclick.util.CmdData;
//import com.itant.autoclick.util.HandlerUtil;
//import com.itant.autoclick.util.LaunchApp;
//import com.itant.autoclick.util.LogUtils;
//import com.itant.autoclick.util.NetWorkUtils;
//import com.itant.autoclick.util.SPUtils;
//import com.itant.autoclick.util.SuUtil;
//import com.itant.autoclick.util.TaskUtil;
//import com.itant.autoclick.util.ToastUitl;
//import com.itant.autoclick.util.Util;
//import com.itant.autoclick.youtu.ImageParse;
//
//import java.util.List;
//
//public class JPZMGOneService extends Service implements Constant {
//
//    private List<TaskModel> tasks;
//
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
//                    TaskUtil.isDestory = true;
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
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mTaskThread = new TaskThread();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null && intent.hasExtra("stop")) {
//            mHandler.sendEmptyMessage(TASK_STOP);
//
//        } else {
//            isDestory = false;
//            TaskUtil.isDestory = false;
//            HandlerUtil.async(mTaskThread);
//        }
//        return START_STICKY;
//    }
//
//    private TaskThread mTaskThread;
//
//    private static int currentUserInfo = 0;
//    public static int failCount = 0;
//    private static boolean needContinue;
//    private PointModel netPoint;
//    private PointModel dialogClose3;
//
//    private class TaskThread implements Runnable {
//        @Override
//        public void run() {
//            try {
//                tasks = Util.getTaskModel();
//                List<UserInfo> userInfos = Util.getUserInfo();
//                currentUserInfo = SPUtils.getInt(CURRENT_USER_INFO);
//                if (SPUtils.getBoolean(KEY_REGISTER)) {
//                    register(null, null);
//                    return;
//                }
//                resetFail();
//                while (!NetWorkUtils.isNetConnected(getApplicationContext())) {//检查网络
//                    if (isDestory) return;
//                    if (check(failCount, 5)) {
//                        mHandler.sendEmptyMessageDelayed(RESET_TASK, 60000);
//                        return;
//                    }
//                    Thread.sleep(2000);
//                }
//                PointModel loginClose = CmdData.get(LOGIN_DIALOG_CLOSE);
//                PointModel loginGame = CmdData.get(LOGIN_GAME);
//                PointModel startGame = CmdData.get(START_GAME);
//                PointModel dialogClose2 = CmdData.get(DIALOG_CLOSE2);//通告对话框关闭
//                netPoint = CmdData.get(NET_CLOSE);
//                dialogClose3 = CmdData.get(DIALOG_CLOSE3);        //道具对话框关闭按钮
//                while (true) {//循环执行小号操作
//                    SuUtil.kill();       //退出游戏
//                    Thread.sleep(1000);
//                    LaunchApp.launchapp(getApplicationContext(), LaunchApp.JPZMG_PACKAGE_NAME);    //启动游戏
//                    Thread.sleep(2400);
//                    resetFail();
//                    while (true) {   //检查准备输入账号的环境
//                        if (isDestory) return;
//                        Bitmap capBitmap = Util.getCapBitmap();
//                        if (Util.checkColor(capBitmap, loginClose) || check(failCount, 8)) break;
//                        Thread.sleep(600);
//                    }
//                    if (needContinue) continue;
//                    UserInfo userInfo = userInfos.get(currentUserInfo % userInfos.size());
//                    int length = userInfo.getName().length();
//                    AutoTool.execShellCmd("input keyevent 20");
//                    Thread.sleep(100);
//                    AutoTool.execShellCmd("input keyevent 19");
//                    AutoTool.execShellCmd(length + 1, 67);
//                    Thread.sleep(300);
//                    AutoTool.execShellCmd(CmdData.inputTextUserInfoName + userInfo.getName()); //输入账号
//
//                    Thread.sleep(500);
//                    AutoTool.execShellCmd(loginGame); //点击登录
//
//                    failCount = 0;
//                    while (true) {               //检查进入游戏的环境
//                        if (isDestory) return;
//                        Bitmap capBitmap = Util.getCapBitmap();
//                        if (Util.checkColor(capBitmap, dialogClose2)) {
//                            AutoTool.execShellCmd(dialogClose2);  //维护公告
//                            Thread.sleep(500);
//                            break;
//                        }
//                        if (Util.checkColor(capBitmap, startGame) || check(failCount, 8)) break;
//                        Thread.sleep(500);
//                    }
//                    if (needContinue) continue;
//
//                    AutoTool.execShellCmd(startGame);  //进入游戏
//                    Thread.sleep(600);
//                    failCount = 0;
//
//                    while (true) {                           //检查 通告对话框的环境
//                        if (isDestory) return;
//                        Bitmap capBitmap = Util.getCapBitmap();
//                        if (Util.checkColor(capBitmap, dialogClose2) || check(failCount, 8)) break;
//                        Thread.sleep(500);
//                    }
//                    if (needContinue) continue;
//                    AutoTool.execShellCmd(dialogClose2);  //关闭通告对话框
//                    Thread.sleep(500);
//
//                    if (userInfos.size() == 1) {
//                        if (isDestory) return;
//                        if (execute(userInfo)) return;
////                        mHandler.sendMessage(mHandler.obtainMessage(TASK_TOAST_COUNT_DOWN,120,0));//一个小时一次
//                        mHandler.sendMessage(mHandler.obtainMessage(TASK_TOAST_COUNT_DOWN,7200,0));//一个小时一次
//                        SuUtil.kill();       //退出游戏
//                        break;
//                    } else {
//                        currentUserInfo++;
//                        SPUtils.setInt(CURRENT_USER_INFO, currentUserInfo);
//                        if (execute(userInfo)) return;
//                    }
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                mHandler.sendEmptyMessage(TASK_ERROR);
//            }
//        }
//    }
//
//    private boolean execute(UserInfo userInfo) throws Exception {
//        for (TaskModel task : tasks) { //循环执行任务
//            if (isDestory) return true;
//            execute(task, userInfo);
//            DbCore.getDaoSession().update(userInfo);
//            Thread.sleep(300);
//        }
//        return false;
//    }
//
//    private void resetFail() {
//        failCount = 0;
//        needContinue = false;
//    }
//
//    private boolean check(int failCount, int maxCount) {
//        boolean check = failCount > maxCount;
//        needContinue = check;
//        JPZMGOneService.failCount++;
//        return check;
//    }
//
//
//    public void send(String msg) {
//        mHandler.sendMessage(mHandler.obtainMessage(TASK_TOAST, msg));
//    }
//
//    public String getFriendTime(int countDownTime) {
//        int second = countDownTime % 60; //秒
//        int min = countDownTime / 60 % 60; //分
//        int hour = countDownTime / 3600 % 24; //分
//        return String.format("%d时%d分%d秒后执行",hour, min, second);
//    }
//
//
//    private void execute(final TaskModel task, final UserInfo userInfo) throws Exception {//异步
//        switch (task.getType()) {
//            case ONE:       //收菜
//                boolean old = SPUtils.getBoolean(KEY_OLD_SHOU_CAI,true);
//                if (old) {
//                    oneTaskOld(task);
//                } else {
//                    oneTask(task);
//                }
//                break;
//            case TASK_ZHENG_WU:       //政绩
//                TaskUtil.twoTask(task, userInfo);
//                break;
//            case TASK_FENG_LU:      //俸禄
//                fourTask(task, userInfo);
//                break;
//
//            case TASK_MO_BAI:     //膜拜
//                threeTask(task, userInfo);
//                break;
//            case TASK_REN_WU_JIANG_LU:      //任务奖励
//                fiveTask(task, userInfo);
//                break;
//            case TASK_GUAN_KA:      //关卡奖励
//                TaskUtil.sixTaskNewV2(task, userInfo);
//                break;
//            case TASK_SHU_YUAN:      //书院学习
//                TaskUtil.shuYan(task, userInfo);
//                break;
//            case TASK_YAN_HUI:      //赴宴
//                TaskUtil.yanHui(task, userInfo);
//                break;
//            case TASK_CHEGN_JIU:      //成就
//                TaskUtil.chengJiu(task, userInfo);
//                break;
//            case TASK_REGISTER:      //注册
//                register(task, userInfo);
//                break;
//            case TIME_LIMIT_REWARD:      //限时奖励
//                TaskUtil.timeLimitRewardNew(task, userInfo);
//                break;
//            case EMAIL:      //限时奖励
//                TaskUtil.email(task, userInfo);
//                break;
//            case XUN_FANG:      //限时奖励
//                TaskUtil.sunFang(task, userInfo);
//                break;
//            case SUI_JI_ZHAO_HUAN:      //限时奖励
//                TaskUtil.suiJiZhaoHuan(task, userInfo);
//                break;
//        }
//    }
//
//
//    private boolean hasTask;
//
//    private void fiveTask(TaskModel task, UserInfo userInfo) {
//        resetFail();
//        PointModel tabTask = CmdData.get(TAB_TASK);
//        PointModel dialogClose = CmdData.get(TASK_DIALOG_CLOSE);
//        PointModel dialogClose140 = CmdData.get(TASK_DIALOG_CLOSE_140);
//        PointModel welFare = CmdData.get(TASK_RIGHT_START_GET);  //福利
//        PointModel welFareGet = CmdData.get(TASK_RIGHT_END_GET);   //福利领取
//        List<PointModel> topModels = Util.getRenWuTopModel();
//        List<PointModel> rightModels = Util.getRenWuRightModel();
//
//        Bitmap bitmap;
//        try {
////            AutoTool.execShellCmdClose();
////            Thread.sleep(1200);
//            bitmap = Util.getCapBitmap();
//            if (Util.checkColor(bitmap, welFare)) {
//                int spaceWidth = BaseApplication.getScreenWidth() / 10;
//                AutoTool.execShellCmd(welFare);
//                Thread.sleep(800);
//                bitmap = Util.getCapBitmap();
//                if (Util.checkColor(bitmap, welFareGet)) {
//                    AutoTool.execShellCmd(welFareGet);
//                } else {
//                    for (int i = 1; i <= 6; i++) {
//                        AutoTool.execShellCmd(CmdData.clickInt(spaceWidth * i, welFareGet.getY()));
//                        Thread.sleep(400);
//                    }
//                }
//                Thread.sleep(200);
//                AutoTool.execShellCmdClose();
//                Thread.sleep(800);
//                bitmap = Util.getCapBitmap();
//            }
//
//            if (!Util.checkColor(bitmap, tabTask)) return;
//            AutoTool.execShellCmd(tabTask);
//            Thread.sleep(1000);
//            while (true) {
//                if (isDestory) return;
//                bitmap = Util.getCapBitmap();
//                if (checkExp(bitmap, netPoint, "当前网络异常")) continue;//检查网络环境
//                for (int i = 0; i < rightModels.size(); i++) {
//                    if (isDestory) return;
//                    if (!Util.checkColor(bitmap, rightModels.get(i))) {
//                        AutoTool.execShellCmd(rightModels.get(i));
//                        Thread.sleep(1200);
//                        if (i == rightModels.size() - 1) {
//                            hasTask = true;
//                            AutoTool.execShellCmdClose();
//                            Thread.sleep(1200);
//                            AutoTool.execShellCmd(tabTask);
//                            Thread.sleep(400);
//                            break;
//                        }
//                    } else {
//                        hasTask = false;
//                        break;
//                    }
//                }
//                if (hasTask) {
//                    hasTask = false;
//                    continue;
//                }
//
//                break;
//            }
//
//            Thread.sleep(200);
//            for (int i = 0; i < topModels.size(); i++) {
//                AutoTool.execShellCmd(topModels.get(i));
//                Thread.sleep(1200);
//                if (i<topModels.size()-1){
//                    AutoTool.execShellCmd(dialogClose);
//                    Thread.sleep(800);
//                }
//            }
//
//            AutoTool.execShellCmd(dialogClose140);
//            Thread.sleep(600);
//            AutoTool.execShellCmdClose();
//            Thread.sleep(600);
//            hasTask = false;
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    boolean isEnd = false;
//
////    private void twoTask(TaskModel task, UserInfo userInfo) {
////        if ((System.currentTimeMillis() - userInfo.getLastRefreshZhengWuTime()) < 0) {
////            return;
////        }
////        send(task.getName());
////        userInfo.setLastRefreshZhengWuTime(System.currentTimeMillis() + task.getSpaceTime() * 1000 + 1000);
////        resetFail();
////        PointModel shiye = CmdData.get(SHI_YE);
////        final PointModel getZhengJi = CmdData.get(ZHENG_JI_GET);
////        PointModel zhengJiBG = CmdData.get(ZHENG_JI_BG);
////        final PointModel daoJu = CmdData.get(DIALOG_CLOSE3);
////        try {
////            AutoTool.execShellCmd(shiye);
////            Thread.sleep(600);
////            Bitmap bitmap;
////            while (true) {
////                if (isDestory) return;
////                bitmap = Util.getCapBitmap();
////                if (checkExp(bitmap, netPoint, "当前网络异常")) continue;//检查网络环境
////                ImageParse.parseImg(false, new ImageParse.Call() {
////                    @Override
////                    public void call(List<Result.ItemsBean> result) {
////                        isEnd = TaskUtil.zhengWu(getZhengJi, daoJu, result);
////                    }
////                });
////                if (isEnd) {
////                    Thread.sleep(800);
////                    AutoTool.execShellCmd(CmdData.screenClose);
////                    Thread.sleep(1000);
////                    break;
////                }
////                if (check(failCount, 20)) break;
////            }
////
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////    }
//
//    private void threeTask(TaskModel task, UserInfo userInfo) {
//        if ((System.currentTimeMillis() - userInfo.getLastRefreshMoBaiTime()) < 0) {
//            return;
//        }
//        send(task.getName());
//        userInfo.setLastRefreshMoBaiTime(System.currentTimeMillis() + task.getSpaceTime() * 1000 + 1000);
//        resetFail();
//        PointModel paiHang = CmdData.get(PAI_HANG_BANG);
//        PointModel moBai = CmdData.get(BANG_DAN_GET);
//        PointModel bangDanSelf = CmdData.get(BANG_DAN_SELF);
//        PointModel bandDanGuanKa = CmdData.get(BANG_DAN_GUAN_KA);
//        PointModel bandDanQinMi = CmdData.get(BANG_DAN_QIN_MI);
//
//
//        try {
//            AutoTool.execShellCmdClose();
//            Thread.sleep(600);
//            AutoTool.execShellCmd(CmdData.swipe(BaseApplication.getScreenWidth() - 100, 100));
//            Thread.sleep(600);
//            AutoTool.execShellCmd(CmdData.swipe(BaseApplication.getScreenWidth() - 100, 100));
//            Thread.sleep(1600);
//            AutoTool.execShellCmd(paiHang);
//            Thread.sleep(600);
//            Bitmap bitmap;
//            while (true) {
//                if (isDestory) return;
//                bitmap = Util.getCapBitmap();
//                if (checkExp(bitmap, netPoint, "当前网络异常")) continue;//检查网络环境
//                if (Util.checkColor(bitmap, bangDanSelf)) {
//                    AutoTool.execShellCmd(bangDanSelf);
//                    Thread.sleep(240);
//                    break;
//                } else {
//                    Thread.sleep(240);
//                }
//                if (check(failCount, 3)) break;
//            }
//            int count = 0;
//            resetFail();
//            while (true) {
//                if (isDestory) return;
//                bitmap = Util.getCapBitmap();
//                if (checkExp(bitmap, netPoint, "当前网络异常")) continue;//检查网络环境
//                String color = Util.getColor(bitmap, moBai.getX(), moBai.getY());
//                if (color.equals(moBai.getNormalColor())) {
//                    count++;
//                    if (count == 1) {
//                        AutoTool.execShellCmd(bandDanGuanKa);
//                    } else if (count == 2) {
//                        AutoTool.execShellCmd(bandDanQinMi);
//                    } else if (count == 3) {
//                        AutoTool.execShellCmdClose();
//                        Thread.sleep(1000);
//                        AutoTool.execShellCmdClose();
//                        Thread.sleep(1200);
//                        AutoTool.execShellCmdClose();
//                        Thread.sleep(600);
//                        return;
//                    }
//                    Thread.sleep(1000);
//                } else {
//                    AutoTool.execShellCmd(moBai);
//                    Thread.sleep(800);
//                    AutoTool.execShellCmd(moBai);
//                    Thread.sleep(1200);
//                }
//                if (check(failCount, 12)) break;
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void fourTask(TaskModel task, UserInfo userInfo) {//俸禄
//        if ((System.currentTimeMillis() - userInfo.getLastRefreshFengLuTime()) < 0) {
//            return;
//        }
//        send(task.getName());
//        userInfo.setLastRefreshFengLuTime(System.currentTimeMillis() + task.getSpaceTime() * 1000 + 1000);
//        resetFail();
//        PointModel huangGong = CmdData.get(HUANG_GONG);
//        PointModel getFengLu = CmdData.get(HUANG_GONG_GET);
//        PointModel wang = CmdData.get(HUANG_GONG_WANG);
//        try {
//
//            AutoTool.execShellCmd(CmdData.screenClose);
//            Thread.sleep(800);
//
//            if (BaseApplication.getScreenWidth() == 720) {
//                AutoTool.execShellCmd(CmdData.swipeRight(BaseApplication.getScreenWidth()));
//                Thread.sleep(800);
//            }
//            AutoTool.execShellCmd(huangGong);
//            Thread.sleep(800);
//
//            AutoTool.execShellCmd(wang);
//            Thread.sleep(800);
//
//            Bitmap bitmap;
//            while (true) {
//                if (isDestory) return;
//                bitmap = Util.getCapBitmap();
//                if (checkExp(bitmap, netPoint, "当前网络异常")) continue;//检查网络环境
//                if (Util.checkColor(bitmap, getFengLu)) {
//                    AutoTool.execShellCmd(CmdData.screenClose);
//                    Thread.sleep(800);
//                    AutoTool.execShellCmd(CmdData.screenClose);
//                    Thread.sleep(800);
//                    AutoTool.execShellCmd(CmdData.screenClose); //回府
//                    Thread.sleep(800);
//                    break;
//                } else {
//                    AutoTool.execShellCmd(getFengLu);
//                    Thread.sleep(240);
//                }
//                if (check(failCount, 6)) break;
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean checkExp(Bitmap bitmap, PointModel model, String msg) throws InterruptedException {
//        if (bitmap == null) return true;
//        if (Util.getColor(bitmap, model.getX(), model.getY()).equals(model.getNormalColor())) { //检查网络环境
//            AutoTool.execShellCmd(model);
//            send(msg);
//            Thread.sleep(600);
//            return true;
//        }
//        return false;
//    }
//
//
//    private void oneTaskOld(TaskModel task) throws InterruptedException { //2
//        List<PointModel> pointModels = task.getData();
//        int count = 0;
//        send(task.getName());
//        PointModel huaAn = CmdData.get(HUA_AN);
//        AutoTool.execShellCmd(huaAn);                               //点击华安进入收菜界面
//        Thread.sleep(1200);
//        Bitmap bitmap;
//        resetFail();
//        while (true) {
//            if (isDestory) return;
//            bitmap = Util.getCapBitmap();
//            if (checkExp(bitmap, netPoint, "当前网络异常")) continue;//检查网络环境
//            if (checkExp(bitmap, dialogClose3, "关闭道具框")) continue;//检查网络环境
//            count = 0;
//            if (isDestory) return;
//            if (check(failCount, 20)) break;
//            for (int i = 0; i < pointModels.size(); i++) {
//                if (isDestory) return;
//                PointModel pointModel = pointModels.get(i);
//                if (!Util.checkColor(bitmap, pointModel)) {
//                    if (isDestory) return;
//                    AutoTool.execShellCmd(pointModel);
//                    Thread.sleep(280);
//                    count++;
//                }
//            }
//            if (!bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
//            Thread.sleep(600);
//            if (count > 10) {
//                AutoTool.execShellCmd(CmdData.screenClose);
//                Thread.sleep(600);
//                oneTask(task);
//            }
//            LogUtils.logd(" count" + count);
//            if (count == 0) {
//                AutoTool.execShellCmd(CmdData.screenClose);
//                Thread.sleep(1200);
//                return;
//            }
//        }
//    }
//
//    private void oneTask(TaskModel task) throws InterruptedException { //2
//        final List<PointModel> pointModels = task.getData();
//        int count = 0;
//        send(task.getName());
//
//        PointModel huaAn = CmdData.get(HUA_AN);
//        AutoTool.execShellCmd(huaAn);                               //点击华安进入收菜界面
//        Thread.sleep(1200);
//        isEnd = false;
//        Bitmap bitmap;
//        resetFail();
//        while (true) {
//            if (isDestory) return;
//            bitmap = Util.getCapBitmap();
//            if (checkExp(bitmap, netPoint, "当前网络异常")) continue;//检查网络环境
//            if (checkExp(bitmap, dialogClose3, "关闭道具框")) continue;//检查网络环境
//            if (isDestory) return;
//            if (check(failCount, 20)) break;
//
//            ImageParse.getSyncData( new ImageParse.Call() {
//                @Override
//                public void call(List<Result.ItemsBean> result) {
//                    isEnd = TaskUtil.getOneCount(result, pointModels);
//                }
//            });
//            if (isEnd) return;
//            LogUtils.logd(" count" + count);
//        }
//    }
//
//
//    public void register(TaskModel task, UserInfo userInfo) {
//        try {
//
//            List<UserInfo> userInfos = Util.getUserInfo();
//            currentUserInfo = 0;
////            currentUserInfo = SPUtils.getInt(CURRENT_USER_INFO);
//            PointModel loginClose = CmdData.get(LOGIN_DIALOG_CLOSE);
//            PointModel loginGame = CmdData.get(LOGIN_GAME);
//            PointModel realyNameOk = CmdData.get(REALY_NAME_OK);
//            PointModel registerOk = CmdData.get(REGISTER_OK);
//            PointModel createUser = CmdData.get(CREATE_USER);
//            PointModel startGame = CmdData.get(START_GAME);
//            PointModel register = CmdData.get(REGISTER_CLICK);
//            PointModel registerOther = CmdData.get(REGISTER_OTHER);
////            PointModel dialogClose2 = CmdData.get(DIALOG_CLOSE2);//通告对话框关闭
//            netPoint = CmdData.get(NET_CLOSE);
//            dialogClose3 = CmdData.get(DIALOG_CLOSE3);        //道具对话框关闭按钮
//            resetFail();
//            Bitmap bitmap = null;
//            while (!NetWorkUtils.isNetConnected(getApplicationContext())) {//检查网络
//                if (isDestory) return;
//                if (check(failCount, 30)) {
//                    mHandler.sendEmptyMessageDelayed(RESET_TASK, 60000);
//                    return;
//                }
//                Thread.sleep(2000);
//            }
//
//            while (true) {
//                if (currentUserInfo == userInfos.size()) {
//                    LogUtils.logd("注册结束");
//                    isDestory = true;
//                    return;
//                }
//                SuUtil.kill();       //退出游戏
//                Thread.sleep(1000);
//                LaunchApp.launchapp(getApplicationContext(), LaunchApp.JPZMG_PACKAGE_NAME);    //启动游戏
//                Thread.sleep(2400);
//
//                while (true) {   //检查准备输入账号的环境
//                    if (isDestory) return;
//                    Bitmap capBitmap = Util.getCapBitmap();
//                    if (Util.checkColor(capBitmap, loginClose) || check(failCount, 8)) break;
//                    Thread.sleep(600);
//                }
//                resetFail();
//                AutoTool.execShellCmd(register);
//                Thread.sleep(1000);
//                SPUtils.setInt(CURRENT_USER_INFO, currentUserInfo);
//                if (needContinue) continue;
//                userInfo = userInfos.get(currentUserInfo % userInfos.size());
//                currentUserInfo++;
//                int length = userInfo.getName().length();
//                AutoTool.execShellCmd("input keyevent 20");
//                Thread.sleep(100);
//                AutoTool.execShellCmd("input keyevent 19");
//                AutoTool.execShellCmd(length + 1, 67);
//                Thread.sleep(300);
//                AutoTool.execShellCmd(CmdData.inputTextUserInfoName + userInfo.getName()); //输入账号
//                Thread.sleep(1000);
//                AutoTool.execShellCmd("input keyevent 20");
//                Thread.sleep(200);
//                AutoTool.execShellCmd(6, 22);
//                Thread.sleep(200);
//                AutoTool.execShellCmd(6, 67);
//                Thread.sleep(500);
//                AutoTool.execShellCmd(CmdData.inputTextUserInfoName + "520333");
//                Thread.sleep(600);
//                AutoTool.execShellCmd(registerOk); //点击注册
//                Thread.sleep(800);
//                AutoTool.execShellCmd(realyNameOk); //确定
//                Thread.sleep(1200);
//                AutoTool.execShellCmd("input keyevent 20");
//                Thread.sleep(800);
//                AutoTool.execShellCmd("input keyevent 19");
//                Thread.sleep(800);
//                AutoTool.execShellCmd(CmdData.inputTextUserInfoName + "wuzuqing");
//                Thread.sleep(800);
//                AutoTool.execShellCmd("input keyevent 20");
//                Thread.sleep(200);
//                AutoTool.execShellCmd(CmdData.inputTextUserInfoName + "45092319900211537X");
//                Thread.sleep(800);
//                AutoTool.execShellCmd(registerOther);
//                Thread.sleep(800);
//
//                while (true) {
//                    if (isDestory) return;
//                    bitmap = Util.getCapBitmap();
//                    if (Util.checkColor(bitmap, startGame)) {
//                        AutoTool.execShellCmd(startGame); //进入游戏
//                        Thread.sleep(800);
//                        break;
//                    }
//                    check(failCount, 5);
//                    if (needContinue) break;
//                }
//                if (needContinue) continue;
//                AutoTool.execShellCmd(createUser); //创建角色
//                Thread.sleep(2400);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
