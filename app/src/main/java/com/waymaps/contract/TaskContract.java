package com.waymaps.contract;

import com.waymaps.data.model.Task;

import java.util.List;

public interface TaskContract {
    interface TaskView{
        void showAllTasks(List<Task> tasks);
    }

    interface TaskPresenter{
        void getAllTasks();
    }
}
