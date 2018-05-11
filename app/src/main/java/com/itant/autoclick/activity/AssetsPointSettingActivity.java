package com.itant.autoclick.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itant.autoclick.Constant;
import com.itant.autoclick.R;
import com.itant.autoclick.dialog.SelectPropDialog;
import com.itant.autoclick.model.PointModel;
import com.itant.autoclick.util.CmdData;
import com.itant.autoclick.util.HandlerUtil;
import com.itant.autoclick.util.JsonUtils;
import com.itant.autoclick.util.LogUtils;
import com.itant.autoclick.util.SPUtils;
import com.itant.autoclick.util.TaskUtil;
import com.itant.autoclick.util.Util;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2017/12/26 13:10
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2017/12/26$
 * @updateDes ${TODO}
 */

public class AssetsPointSettingActivity extends NoAnimatorActivity implements Constant {

    private android.widget.TextView tvContent;
    private PointModel mPointModel;
    private SelectPropDialog mSelectPropDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_setting);
        Button save = (Button) findViewById(R.id.save);
        this.tvContent = (TextView) findViewById(R.id.tv_content);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonList = JsonUtils.toJson(CmdData.coordinateList);
                SPUtils.setString(COORDINATE_KEY,jsonList);

                LogUtils.logd("coordinateList:"+jsonList);
                finish();
            }
        });

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectPropDialog == null) {
                    mSelectPropDialog = new SelectPropDialog(AssetsPointSettingActivity.this);
                    mSelectPropDialog.setCall(new SelectPropDialog.Call<PointModel>() {
                        @Override
                        public void call(PointModel objects) {
                            mPointModel = objects;
                            tvContent.setText(mPointModel.getName() + " \nx:" + mPointModel.getX() + " y:" + mPointModel.getY());
                            if (!TextUtils.isEmpty(mPointModel.getNormalColor())) {
                                tvContent.setTextColor(Color.parseColor(mPointModel.getNormalColor()));
                            }
                        }
                    });
                }
                mSelectPropDialog.setData(CmdData.coordinateList);
                mSelectPropDialog.show();
            }
        });
    }



    private String color;

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPointModel == null) {
                    return false;
                }
                HandlerUtil.async(new Runnable() {
                    @Override
                    public void run() {
                        final int x = (int) event.getRawX();
                        final int y = (int) event.getRawY();
                        Util.getCapBitmapNew();
                        color = Util.getColor(TaskUtil.bitmap, x, y);
                        LogUtils.logd("color:" + color + " x:" + x + " Y:" + y);
                        if (TextUtils.isEmpty(color)) return;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPointModel.setX(x);
                                mPointModel.setY(y);
                                tvContent.setTextColor(Color.parseColor(color));
                                mPointModel.setNormalColor(color);
                                tvContent.setText(mPointModel.getName() + " \nx:" + mPointModel.getX() + " y:" + mPointModel.getY());
                            }
                        });
                    }
                });
                break;
        }
        return super.onTouchEvent(event);
    }

}
