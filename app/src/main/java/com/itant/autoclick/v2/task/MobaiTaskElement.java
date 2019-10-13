package com.itant.autoclick.v2.task;

import com.example.module_orc.ignore.BenfubangdanIgnoreRect;
import com.itant.autoclick.activity.BaseApplication;
import com.itant.autoclick.model.PointModel;
import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.tool.AutoTool;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.AbsTaskElement;

import org.opencv.core.Rect;

public class MobaiTaskElement extends AbsTaskElement {
    public MobaiTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    PointModel paiHang = CmdData.get(PAI_HANG_BANG);
    PointModel bangDanSelf = CmdData.get(BANG_DAN_SELF);
    PointModel bangDanKuaFu = CmdData.get(BANG_DAN_CROSS);
    private boolean doBenfuBangDan;
    PointModel huangGongClose = CmdData.get(HUANG_GONG_CLOSE);
    private int status;

    @Override
    protected boolean doTask() throws Exception {
        pageData = Util.getBitmapAndPageData();

        if (checkExp(netPoint, "当前网络异常")) return false;//检查网络环境

        if (checkPage("府内")) {
            AutoTool.execShellCmdChuFu();
            Thread.sleep(1800);
            return false;
        } else if (checkPage("府外")) {
            doBenfuBangDan = false;
            AutoTool.execShellCmd(CmdData.swipe(BaseApplication.getScreenWidth() - 50, 50));
            Thread.sleep(600);
            AutoTool.execShellCmd(CmdData.swipe(300, 600));
            Thread.sleep(600);
            AutoTool.execShellCmd(paiHang);
            Thread.sleep(800);
            return false;
        } else if (checkPage("排行榜")) {
            if (doBenfuBangDan) {
                status = 0;
                AutoTool.execShellCmd(bangDanKuaFu);
            } else {
                status = 0;
                AutoTool.execShellCmd(bangDanSelf);
            }
            Thread.sleep(1000);

        } else if (checkPage("本服榜单") || checkPage("跨服榜单")) {
            Rect moBai = BenfubangdanIgnoreRect.moBai;
            Rect target;
            while (true) {
                target = pageData.get(0).getRect();   // 膜拜
                if (status == 0) {
                    if (target.equals(moBai)) {
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1500);
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1200);
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1200);
                    } else {
                        target = pageData.get(1).getRect();
                        AutoTool.execShellCmdXy(target.x, target.y);
                        status = 1;
                        Thread.sleep(1200);
                    }
                } else if (status == 1) {
                    if (target.equals(moBai)) {
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1500);
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1200);
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1200);
                    } else {
                        target = pageData.get(2).getRect();
                        AutoTool.execShellCmdXy(target.x, target.y);
                        status = 2;
                        Thread.sleep(1200);
                    }
                } else if (status == 2) {
                    if (target.equals(moBai)) {
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1500);
                        AutoTool.execShellCmd(moBai);
                        Thread.sleep(1200);
                    } else {
                        if (!doBenfuBangDan) {
                            AutoTool.execShellCmd(huangGongClose);
                            Thread.sleep(1200);
                            doBenfuBangDan = true;
                            return false;
//                            AutoTool.execShellCmd(huangGongClose);
//                            Thread.sleep(1200);
//                            AutoTool.execShellCmd(huangGongClose);
//                            Thread.sleep(800);
//                            return true;
                        } else {
                            AutoTool.execShellCmd(huangGongClose);
                            Thread.sleep(1200);
                            AutoTool.execShellCmd(huangGongClose);
                            Thread.sleep(800);
                            return true;
                        }
                    }
                }
                pageData = Util.getBitmapAndPageData();
            }
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
