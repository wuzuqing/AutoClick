package com.itant.autoclick.v2.task;

import com.itant.autoclick.activity.BaseApplication;
import com.itant.autoclick.model.PointModel;
import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.AbsTaskElement;

public class MobaiTaskElement extends AbsTaskElement {
    public MobaiTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    PointModel paiHang = CmdData.get(PAI_HANG_BANG);
    PointModel bangDanSelf = CmdData.get(BANG_DAN_SELF);
    PointModel bangDanKuaFu = CmdData.get(BANG_DAN_CROSS);
    PointModel bandDanGuanKa = CmdData.get(BANG_DAN_GUAN_KA);
    PointModel bandDanQinMi = CmdData.get(BANG_DAN_QIN_MI);
    private boolean doBenfuBangDan;

    @Override
    protected boolean doTask() throws Exception {
        pageData = Util.getBitmapAndPageData();

        if (checkExp(netPoint, "当前网络异常")) return false;//检查网络环境

        if (checkPage("府内")) {
            AutoTool.execShellCmdChuFu();
            Thread.sleep(1800);
            return false;
        } else if (checkPage("府外")) {
            AutoTool.execShellCmd(CmdData.swipe(BaseApplication.getScreenWidth() - 50, 50));
            Thread.sleep(1200);
            AutoTool.execShellCmd(CmdData.swipe(300, 600));
            Thread.sleep(1000);
            AutoTool.execShellCmd(paiHang);
            Thread.sleep(800);
            return false;
        } else if (checkPage("排行榜")) {
            if (doBenfuBangDan) {
                AutoTool.execShellCmd(bangDanKuaFu);
            } else {
                AutoTool.execShellCmd(bangDanSelf);

            }
            Thread.sleep(1600);
//            AutoTool.execShellCmd(wang);
//            AutoTool.execShellCmd(pageData.get(0).getRect());
//            Thread.sleep(800);

        } else if (checkPage("本服榜单")) {
            doBenfuBangDan = true;
        } else if (checkPage("跨服榜单")) {

        } else {
            if (check(20)) {
                resetStep();
                return true;
            }
            Thread.sleep(200);
            return false;
        }
        return false;
    }
}
