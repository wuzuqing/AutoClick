package com.itant.autoclick.v2.task;

import com.itant.autoclick.model.TaskModel;
import com.itant.autoclick.v2.AbsTaskElement;

public class HyzjTaskElement extends AbsTaskElement {
    public HyzjTaskElement(TaskModel taskModel) {
        super(taskModel);
    }

    @Override
    protected boolean doTask() {
        return false;
    }
}
