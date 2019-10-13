package com.itant.autoclick.v2.task;

import android.text.TextUtils;

import com.example.module_orc.OrcModel;
import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.LogUtils;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.AbsTaskElement;

import static com.itant.autoclick.util.TaskUtil.netPoint;

public class JyzcTaskElement extends AbsTaskElement {
    public JyzcTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    private boolean needClickZhengshou = true;
    @Override
    protected boolean doTask() throws Exception {
        pageData = Util.getBitmapAndPageData();

        if (checkExp(netPoint, "当前网络异常")) return false;//检查网络环境

        if (checkPage("府内")) {
            AutoTool.execShellCmd(pageData.get(0).getRect());
            Thread.sleep(1000);
            return false;
        } else if (checkPage("道具使用")) {
            AutoTool.execShellCmd(pageData.get(0).getRect());
            Thread.sleep(800);
            return false;
        } else if (!checkPage("经营资产")) {
            if (check(12)) {
                resetStep();
                return true;
            }
            Thread.sleep(200);
            return false;
        }
        if (needClickZhengshou){
            AutoTool.execShellCmd(CmdData.get(ZHENG_SHOU));
            Thread.sleep(800);
            pageData = Util.getBitmapAndPageData();
            needClickZhengshou = false;
        }
        int count = 0;

        for (OrcModel orcModel : pageData) {
            if (TextUtils.equals("经营", orcModel.getResult())) {
                AutoTool.execShellCmd(orcModel.getRect());
                Thread.sleep(80);
                count++;
            }
        }
        Thread.sleep(700);
        boolean isEnd = count == 0;
        LogUtils.logd(" count" + count);
        if (isEnd) {
            clickClose();
            Thread.sleep(800);
            return true;
        }
        return false;
    }
}
