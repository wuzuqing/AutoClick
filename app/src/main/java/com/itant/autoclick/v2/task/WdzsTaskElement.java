package com.itant.autoclick.v2.task;

import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.v2.AbsTaskElement;

public class WdzsTaskElement extends AbsTaskElement {
    public WdzsTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    @Override
    protected boolean doTask() {
        return false;
    }
}
