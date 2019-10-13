package com.itant.autoclick.v2.task;

import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.v2.AbsTaskElement;

public class ShuyuanTaskElement extends AbsTaskElement {
    public ShuyuanTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    @Override
    protected boolean doTask() {
        return false;
    }
}
