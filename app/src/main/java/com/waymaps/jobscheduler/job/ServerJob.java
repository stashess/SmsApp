package com.waymaps.jobscheduler.job;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public class ServerJob extends Job{
    public static final String TAG = "SERVER_JOB";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        return null;
    }
}
