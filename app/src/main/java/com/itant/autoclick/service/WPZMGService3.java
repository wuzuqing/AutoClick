package com.itant.autoclick.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.itant.autoclick.Constant;
import com.itant.autoclick.util.Util;
import com.itant.autoclick.v2.TaskElement;
import com.itant.autoclick.v2.TaskState;

public class WPZMGService3 extends Service implements Constant {

    private TaskState mTaskState;
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                TaskElement taskStateTask = mTaskState.getTask();
                taskStateTask.printWorkName();
                taskStateTask.bindHandler(this);
                AsyncTask.THREAD_POOL_EXECUTOR.execute(taskStateTask);
            } else if (msg.what == 1) {

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mTaskState = TaskState.get();
        mTaskState.init(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Util.refreshNextDayTime();
        TaskState.isWorking = true;
        mMainHandler.sendEmptyMessage(0);
        return super.onStartCommand(intent, flags, startId);
    }
}
