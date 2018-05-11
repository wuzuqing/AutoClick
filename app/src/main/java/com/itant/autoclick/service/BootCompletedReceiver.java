package com.itant.autoclick.service;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/1/13 23:36
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/1/13$
 * @updateDes ${TODO}
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.itant.autoclick.activity.MainActivity;
import com.itant.autoclick.util.HandlerUtil;

/**
 * 系统启动完成广播接收器
 * #<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
 * @author Lone_Wolf
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //example:启动程序
                    HandlerUtil.post(new Runnable() {
                @Override
                public void run() {
                    Intent start = new Intent(context, MainActivity.class);
                    start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//
                    start.putExtra("hao",true);
                    context.startActivity(start);
                }
            },4000);

        }
    }
}

