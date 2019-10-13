package com.example.module_orc.ignore;

import com.example.module_orc.OrcConfig;
import com.example.module_orc.OrcModel;

import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：士元
 * 时间：2019/9/16 18:07
 * 邮箱：wuzuqing@linghit.com
 * 说明：府内
 */
public class FuneiIgnoreRect implements IIgnoreRect {
    public static final Rect huaAn = new Rect(122, 341, 98, 150);
    public static final Rect shiYe = new Rect(24, 333, 33, 18);
    public static final Rect hongYan = new Rect(237, 333, 60, 60);

    @Override
    public List<OrcModel> ignoreRect(List<Rect> rects) {
        List<OrcModel> result = new ArrayList<>();
        result.add(OrcConfig.append(huaAn));
        result.add(OrcConfig.append(shiYe));
        result.add(OrcConfig.append(hongYan));
        return result;
    }
}
