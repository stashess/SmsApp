package com.waymaps.presenter;

import com.waymaps.contract.Callbacks;
import com.waymaps.contract.TaskContract;
import com.waymaps.data.AppRepository;
import com.waymaps.data.model.Task;
import com.waymaps.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class TaskPresenter implements TaskContract.TaskPresenter {

    private AppRepository appRepository;
    private AppExecutors appExecutors;
    private TaskContract.TaskView taskView;


    public TaskPresenter(AppRepository appRepository, AppExecutors appExecutors, TaskContract.TaskView taskView) {
        this.appRepository = appRepository;
        this.appExecutors = appExecutors;
        this.taskView = taskView;
    }

    @Override
    public void getAllTasks() {
        appRepository.getAllTask(new Callbacks.Tasks.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                taskView.showAllTasks(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                taskView.showAllTasks(new ArrayList<Task>());
            }
        });
    }
}
