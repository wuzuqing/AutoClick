package com.itant.autoclick.v2.task;

import com.example.module_orc.OrcModel;
import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.AbsTaskElement;

public class ClzwTaskElement extends AbsTaskElement {
    public ClzwTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    @Override
    protected boolean doTask() throws Exception {
//        if (checkTime( KEY_WORK_ZW, ACache.TIME_HOUR * 2)) {
//            return  true;
//        }
        pageData = Util.getBitmapAndPageData();

        if (checkExp(netPoint, "当前网络异常")) return false;//检查网络环境

        if (checkPage("府内")) {
            AutoTool.execShellCmd(pageData.get(1).getRect());
            Thread.sleep(1000);
            return false;
        } else if (checkPage("道具使用")) {
            AutoTool.execShellCmd(pageData.get(0).getRect());
            Thread.sleep(800);
            return false;
        } else if (!checkPage("处理公务")) {
            if (check(12)) {
                resetStep();
                return true;
            }
            Thread.sleep(200);
            return false;
        }
        for (OrcModel model : pageData) {
            if (model.getResult().startsWith("获得")){
                AutoTool.execShellCmd(model.getRect());
                Thread.sleep(700);
                return false;
            }
        }

        clickClose();
        Thread.sleep(800);

        return true;
    }
}
