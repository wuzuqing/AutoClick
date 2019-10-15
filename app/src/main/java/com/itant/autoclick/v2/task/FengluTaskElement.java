package com.itant.autoclick.v2.task;

import com.itant.autoclick.model.PointModel;
import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.TaskUtil;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.AbsTaskElement;

public class FengluTaskElement extends AbsTaskElement {
    public FengluTaskElement(TaskModel taskModel) {
        super(taskModel);
    }
    PointModel getFengLu = CmdData.get(HUANG_GONG_GET);
    PointModel wang = CmdData.get(HUANG_GONG_WANG);
    PointModel huangGongClose = CmdData.get(HUANG_GONG_CLOSE);
    @Override
    protected boolean doTask() throws Exception{
        pageData = Util.getBitmapAndPageData();

        if (checkExp(netPoint, "当前网络异常")) return false;//检查网络环境

        if (checkPage("府内")) {
            AutoTool.execShellCmdChuFu();
            Thread.sleep(1800);
            return false;
        }else if (checkPage("府外")) {
            AutoTool.execShellCmd(pageData.get(0).getRect());
            Thread.sleep(800);
            AutoTool.execShellCmd(wang);
            Thread.sleep(800);
            return false;
        } else if (checkPage("皇宫")){
//            AutoTool.execShellCmd(wang);
            AutoTool.execShellCmd(pageData.get(0).getRect());
            Thread.sleep(800);
        }else if (!checkPage("皇宫俸禄")) {
            if (check(12)) {
                resetStep();
                return true;
            }
            Thread.sleep(200);
            return false;
        }
        if (Util.checkColor(TaskUtil.bitmap, getFengLu)) {
            AutoTool.execShellCmd(huangGongClose);
            Thread.sleep(800);
            AutoTool.execShellCmd(huangGongClose);
            Thread.sleep(1200);
            AutoTool.execShellCmdChuFu();
            Thread.sleep(800);
          return true;
        } else {
            AutoTool.execShellCmd(getFengLu);
            Thread.sleep(800);
        }
        return false;
    }
}
