package com.waymaps.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waymaps.app.R;
import com.waymaps.contract.TaskContract;
import com.waymaps.data.model.Task;
import com.waymaps.presenter.PhonePresenter;
import com.waymaps.presenter.TaskPresenter;
import com.waymaps.util.AppExecutors;
import com.waymaps.util.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TaskFragment extends AbstractFragment implements TaskContract.TaskView {


    private List<Task> tasks = new ArrayList<>();

    private TaskContract.TaskPresenter taskPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this, view);
        taskPresenter = new TaskPresenter(InjectorUtils.provideRepository(getContext()), AppExecutors.getInstance(),this);
        return view;
    }

    @Override
    public void showAllTasks(List<Task> tasks) {

    }

    @Override
    protected String getFragmentName() {
        return getResources().getString(R.string.fragment_task_name);
    }

    @Override
    public void onResume() {
        super.onResume();
        taskPresenter.getAllTasks();
    }


}
