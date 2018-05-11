package com.itant.autoclick.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itant.autoclick.Constant;
import com.itant.autoclick.R;
import com.itant.autoclick.service.MainService;
import com.itant.autoclick.util.JhmUtil;
import com.itant.autoclick.util.LogUtils;
import com.itant.autoclick.util.SPUtils;
import com.itant.autoclick.util.ToastUitl;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity implements Constant.Login {


    private android.widget.EditText etJhm;
    private android.widget.TextView tvTyOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long currentTime = System.currentTimeMillis();
        long tyOver = SPUtils.getLong(TY_OVER);
         if((tyOver -currentTime)>0 && tyOver>0 ){
            MainService.start(LoginActivity.this);
        }else{
             setContentView(R.layout.activity_login);
             Button btnTy = (Button) findViewById(R.id.btn_ty);
             tvTyOver = (TextView) findViewById(R.id.tv_ty_over);
             LogUtils.logd("tyOverTime:"+tyOver + "  currentTime:"+currentTime  + "  xc:"+(currentTime - tyOver));
             if ( tyOver > 0) {
                 tvTyOver.setVisibility(View.VISIBLE);
                 btnTy.setVisibility(View.GONE);
             }else {
                 tvTyOver.setVisibility(View.GONE);
                 btnTy.setVisibility(View.VISIBLE);
             }

             btnTy.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Calendar calendar = Calendar.getInstance();
                     calendar.add(Calendar.MINUTE,30);
                     SPUtils.setLong(TY_OVER, calendar.getTimeInMillis());
                     SPUtils.setBoolean(IS_TY,true);
                     MainService.start(LoginActivity.this);
                 }
             });
             Button btnJhm = (Button) findViewById(R.id.btn_jhm);
             btnJhm.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     String jmh = etJhm.getText().toString();
                     if (TextUtils.isEmpty(jmh)) {
                         ToastUitl.showShort("请输入激活码");
                         return;
                     }
                     if (JhmUtil.check(jmh)) {
                         Calendar calendar = Calendar.getInstance();
                         JhmUtil.JhmModel model = JhmUtil.get(jmh);
                         calendar.add(Calendar.DATE,model.getLiveDay());
                         SPUtils.setLong(TY_OVER, calendar.getTimeInMillis());
                         SPUtils.setBoolean(IS_TY,false);
                         MainService.start(LoginActivity.this);
                     } else {
                         ToastUitl.showShort("无该激活码或该激活码已失效,请重新输入...");
                         etJhm.setText("");
                     }
                 }
             });
             this.etJhm = (EditText) findViewById(R.id.et_jhm);
         }

    }

    public long getLiveTime(int day) {
        return 3600* 24 * 1000 * day;
    }
    public long getLiveTime(int day,float time) {
        return (long) (3600* time * 1000 * day);
    }
}
