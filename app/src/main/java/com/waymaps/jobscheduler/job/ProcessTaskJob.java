package com.waymaps.jobscheduler.job;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;

public class ProcessTaskJob extends Job {

    public static final String TAG = "PROCESS_TASK_JOB";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        return null;
    }
}
