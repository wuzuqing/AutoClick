package com.itant.autoclick.v2;

import android.os.Handler;

import com.itant.autoclick.model.TaskModel;

public interface TaskElement extends Runnable {
    void setTaskModel(TaskModel taskModel);

    TaskModel getTaskModel();

    void bindHandler(Handler handler);

    void printWorkName();
}
