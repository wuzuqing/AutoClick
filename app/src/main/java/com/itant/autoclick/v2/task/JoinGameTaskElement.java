package com.itant.autoclick.v2.task;

import android.util.Log;

import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.TaskUtil;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.AbsTaskElement;
import com.itant.autoclick.v2.TaskState;

public class JoinGameTaskElement extends AbsTaskElement {
    public JoinGameTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    @Override
    protected boolean doTask() throws Exception {
        // 检查是否有更新
//                    while (hasGengXin) {               //检查进入游戏的环境
//                        if (TaskUtil.isDestory) return;
//                        LogUtils.logd("hasGengXin:" + hasGengXin);
//                        TaskUtil.sleep(2200);
//                        Util.getCapBitmapNew();
//                        if (Util.checkColor(genXin)) {
//                            AutoTool.execShellCmd(dialogClose2);  //维护公告
//                            Thread.sleep(TaskUtil.isNewApi ? 1200 : 600);
//                            break;
//                        } else if (Util.checkColor(startGame)) {
//                            break;
//                        }
//                        if (TaskUtil.check(TaskUtil.failCount, 12)) break;
//
//                    }

        Log.d(TAG, "doTask: start");
        while (TaskState.isWorking) {
            pageData = Util.getBitmapAndPageData();
            if (checkPage("进入游戏")) {
                AutoTool.execShellCmd(pageData.get(0).getRect());  //进入游戏
                Thread.sleep(1200);
                break;
            } else if (check(8)) {
                resetStep();
                return true;
            }
            TaskUtil.sleep(400);
        }

        while (TaskState.isWorking) {                           //检查 通告对话框的环境
            pageData = Util.getBitmapAndPageData();
            if (checkPage("游戏公告")) {
                AutoTool.execShellCmd(pageData.get(1).getRect());  //关闭通告对话框
                break;
            } else if (check(8)) {
                resetStep();
                return true;
            }
            TaskUtil.sleep(200);
        }
        return true;
    }
}
