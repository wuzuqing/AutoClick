package com.itant.autoclick.v2.task;

import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.model.UserInfo;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.LaunchApp;
import com.itant.autoclick.util.TaskUtil;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.AbsTaskElement;
import com.itant.autoclick.v2.TaskState;

import io.virtualapp.AutoClick;

public class StartAndLoginTaskElement extends AbsTaskElement {

    public StartAndLoginTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    @Override
    protected boolean doTask() throws Exception {
        // 检查网络 等待2秒
        while (!isNetConnected()) {
            if (check(TaskUtil.failCount, 5)) {
                TaskState.isWorking=false;
                return true;
            }
            Thread.sleep(2000);
        }
        // 退出游戏
        AutoClick.keyEvent(4);
        Thread.sleep(800);

        //
        AutoClick.click(899, 1117);
        Thread.sleep(1200);

        // 启动游戏
        LaunchApp.launchApp();
        Thread.sleep(4000);
        //获取账号
        TaskState.resetFail();

        while (TaskState.isWorking) {
            pageData = Util.getBitmapAndPageData();
            if (checkPage("登录")  ) {
                break;
            }else if (check(8)){

                return true;
            }
            Thread.sleep(600);
        }
        if (TaskState.needContinue) return false;
        //输入账号
        UserInfo userInfo = TaskState.get().getUserInfo();
        int length = 11;
        Thread.sleep(200);
        // 删除账号
        AutoTool.execShellCmd(length + 1, 112);
        Thread.sleep(500);
        //输入账号
        AutoTool.execShellCmd(CmdData.inputTextUserInfoName + userInfo.getName()); //输入账号

        Thread.sleep(1000);
        AutoTool.execShellCmd(pageData.get(0).getRect()); //点击登录
        TaskUtil.sleep(1000);

        return true;
    }


}
