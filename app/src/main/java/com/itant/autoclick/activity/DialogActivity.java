package com.itant.autoclick.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;

import com.itant.autoclick.Constant;
import com.itant.autoclick.R;
import com.itant.autoclick.util.SPUtils;
import com.itant.autoclick.util.Util;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2017/12/23 19:11
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2017/12/23$
 * @updateDes ${TODO}
 */

public class DialogActivity extends NoAnimatorActivity implements Constant {
    private EditText etSpace;
    private EditText etXsPosition;
    private EditText etCount;
    private EditText etYanHuiNumber;
    private EditText etUserInfo;
    private CheckBox cbGx,cbKfJl,cbLoop,cbZw,cbOnlyFl, cbZh,cbFl, cbMb, cbTask, cbChouCai, cbGuanKa, cbShuYuan,
            cbYanHui, cbZc, cbXs, cbCJ, cbOldShouCai,cbYx,cbLm,cbLmFb,cbGy,cbLf,cbYm,cbKfMb;
    boolean isTy;
    private CheckBox cbHyzh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting);
        etSpace =  findViewById(R.id.et_space);
        cbLoop =  findViewById(R.id.cb_loop);
        etYanHuiNumber =  findViewById(R.id.et_yan_hui_number);
        etCount =  findViewById(R.id.et_count);
        etXsPosition =  findViewById(R.id.et_xs_position);
        etUserInfo =  findViewById(R.id.et_height_user_info);
        cbChouCai =  findViewById(R.id.cb_shoucai);
        cbYm =  findViewById(R.id.cb_ym);
        cbZw =  findViewById(R.id.cb_zw);
        cbKfMb =  findViewById(R.id.cb_kf_bo);
        cbFl =  findViewById(R.id.cb_fl);
        cbOnlyFl =  findViewById(R.id.cb_fl_only);
        cbMb =  findViewById(R.id.cb_mb);
        cbGx =  findViewById(R.id.cb_gx);
        cbKfJl =  findViewById(R.id.cb_kf_jl);
        cbTask =  findViewById(R.id.cb_task);
        cbGuanKa =  findViewById(R.id.cb_guanka);
        cbShuYuan =  findViewById(R.id.cb_sy);
        cbZh =  findViewById(R.id.cbZh);
        cbHyzh =  findViewById(R.id.cb_hyzh);
        cbYanHui =  findViewById(R.id.cb_yh);
        cbZc =  findViewById(R.id.cb_zc);
        cbXs =  findViewById(R.id.cbXs);
        cbCJ =  findViewById(R.id.cbCj);
        cbOldShouCai =  findViewById(R.id.cbOsc);
        cbYx =  findViewById(R.id.cb_yx);
        cbLm =  findViewById(R.id.cb_lm);
        cbLmFb =  findViewById(R.id.cb_lmfb);
        cbLf =  findViewById(R.id.cb_lf);
        cbGy =  findViewById(R.id.cb_gy);
        initData();
    }

    private void initData() {
        isTy = SPUtils.getBoolean(Login.IS_TY);
        etYanHuiNumber.setText(SPUtils.getString(KEY_YAN_HUI_NUMBER));
        etSpace.setText(String.valueOf(SPUtils.getInt("space", 60)));
        etCount.setText(String.valueOf(SPUtils.getInt("count", 1)));
        etXsPosition.setText(String.valueOf(SPUtils.getInt("etXsPosition", 1)));
        cbZw.setChecked(SPUtils.getBoolean(KEY_WORK_ZW));
        cbFl.setChecked(SPUtils.getBoolean(KEY_WORK_FL));
        cbOnlyFl.setChecked(SPUtils.getBoolean(KEY_WORK_ONLY_FL));
        cbMb.setChecked(SPUtils.getBoolean(KEY_WORK_MB));
        cbKfMb.setChecked(SPUtils.getBoolean(KEY_MO_BAI_KUA_FU));
        cbTask.setChecked(SPUtils.getBoolean(KEY_WORK_TASK));
        cbChouCai.setChecked(SPUtils.getBoolean(KEY_CHOU_CAI));
        cbZh.setChecked(SPUtils.getBoolean("zh"));
        cbGuanKa.setChecked(SPUtils.getBoolean(KEY_GUAN_KA));
        cbHyzh.setChecked(SPUtils.getBoolean(KEY_ZHAO_HUAN));
        cbShuYuan.setChecked(SPUtils.getBoolean(KEY_SHU_YUAN));
        cbYanHui.setChecked(SPUtils.getBoolean(KEY_YAN_HUI));
        cbGx.setChecked(SPUtils.getBoolean(KEY_GENG_XIN));
        cbKfJl.setChecked(SPUtils.getBoolean(KEY_KUA_FU_JL));
        cbLf.setChecked(SPUtils.getBoolean(KEY_LAO_FANG));
        cbZc.setChecked(SPUtils.getBoolean(KEY_REGISTER));
        cbXs.setChecked(SPUtils.getBoolean(KEY_XIAN_SHI));
        cbCJ.setChecked(SPUtils.getBoolean(KEY_CHENG_JIU));
        cbOldShouCai.setChecked(SPUtils.getBoolean(KEY_OLD_SHOU_CAI,true));
        cbYx.setChecked(SPUtils.getBoolean(KEY_EMAIL));
        cbLm.setChecked(SPUtils.getBoolean(KEY_LIAN_MENG));
        cbLmFb.setChecked(SPUtils.getBoolean(KEY_LIAN_MENG_FU_BEN));
        cbGy.setChecked(SPUtils.getBoolean(KEY_GUAN_YAN));
        cbYm.setChecked(SPUtils.getBoolean(KEY_YA_MEN));
        cbLoop.setChecked(SPUtils.getBoolean(KEY_LOOP,true));

    }

    private void reset() {
        if (!TextUtils.isEmpty(etUserInfo.getText())) {
            String s = etUserInfo.getText().toString();
            Util.saveUserInfo(s);
        }
        if (isTy){
            SPUtils.setBoolean(KEY_CHOU_CAI, cbChouCai.isChecked());
        }else{
            SPUtils.setInt(KEY_SPACE_TIME_ONE, Integer.parseInt(etCount.getText().toString()));
            SPUtils.setInt("space", Integer.parseInt(etSpace.getText().toString()));
            if (!TextUtils.isEmpty(etXsPosition.getText().toString())){
                SPUtils.setInt("etXsPosition", Integer.parseInt(etXsPosition.getText().toString()));
            }
            if (!TextUtils.isEmpty(etCount.getText().toString())){
                SPUtils.setInt("count", Integer.parseInt(etCount.getText().toString()));
            }
            SPUtils.setBoolean(KEY_WORK_ZW, cbZw.isChecked());
            SPUtils.setBoolean(KEY_KUA_FU_JL, cbKfJl.isChecked());
            SPUtils.setBoolean(KEY_MO_BAI_KUA_FU, cbKfMb.isChecked());
            SPUtils.setString(KEY_YAN_HUI_NUMBER, etYanHuiNumber.getText().toString());

            SPUtils.setBoolean(KEY_WORK_MB,false);
            SPUtils.setBoolean(KEY_WORK_TASK, cbTask.isChecked());
            SPUtils.setBoolean(KEY_CHOU_CAI, cbChouCai.isChecked());
            SPUtils.setBoolean(KEY_GUAN_KA, cbGuanKa.isChecked());
            SPUtils.setBoolean(KEY_SHU_YUAN, cbShuYuan.isChecked());
            SPUtils.setBoolean("zh", cbZh.isChecked());
            SPUtils.setBoolean(KEY_YAN_HUI, cbYanHui.isChecked());
            SPUtils.setBoolean(KEY_REGISTER, cbZc.isChecked());
            SPUtils.setBoolean(KEY_XIAN_SHI, cbXs.isChecked());
            SPUtils.setBoolean(KEY_CHENG_JIU, cbCJ.isChecked());
            SPUtils.setBoolean(KEY_OLD_SHOU_CAI, cbOldShouCai.isChecked());
            SPUtils.setBoolean(KEY_EMAIL, cbYx.isChecked());
            SPUtils.setBoolean(KEY_ZHAO_HUAN, cbHyzh.isChecked());
            SPUtils.setBoolean(KEY_WORK_ONLY_FL, cbOnlyFl.isChecked());
            SPUtils.setBoolean(KEY_WORK_FL, cbOnlyFl.isChecked());
            SPUtils.setBoolean(KEY_GENG_XIN, cbGx.isChecked());
            SPUtils.setBoolean(KEY_LIAN_MENG, cbLm.isChecked());
            SPUtils.setBoolean(KEY_GUAN_YAN, cbGy.isChecked());
            SPUtils.setBoolean(KEY_LIAN_MENG_FU_BEN, cbLmFb.isChecked());
            SPUtils.setBoolean(KEY_LOOP, cbLoop.isChecked());
            SPUtils.setBoolean(KEY_YA_MEN, cbYm.isChecked());
            SPUtils.setBoolean(KEY_LAO_FANG, cbLf.isChecked());
        }
        Util.resetTaskModel();
        SPUtils.setInt(CURRENT_USER_INFO,0);
    }


    private float getFloat(EditText editText, float defValue) {
        if (TextUtils.isEmpty(editText.getText())) return defValue;
        try {
            return Float.parseFloat(editText.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideSoftKeyboard(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void exit(View view) {
        Util.exit(this);
    }

    public void saveParams(View view) {
        hideSoftKeyboard(etSpace);
        reset();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, 0);
    }

    public void help(View view) {
        startActivity(new Intent(this, HelpActivity.class));
    }
}
