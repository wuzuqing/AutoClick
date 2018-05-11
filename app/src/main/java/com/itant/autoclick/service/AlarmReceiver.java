//package com.itant.autoclick.service;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import com.itant.autoclick.util.Util;
//
///**
// * @author 吴祖清
// * @version $Rev$
// * @createTime 2018/4/23 23:16
// * @des ${TODO}
// * @updateAuthor $Author$
// * @updateDate 2018/4/23$
// * @updateDes ${TODO}
// */
//
//public class AlarmReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals("com.g.android.RING")) {
//            Intent intent1 = new Intent(context, ScreenService.class);
//            context.startService(intent1);
//        }
//    }
//}
