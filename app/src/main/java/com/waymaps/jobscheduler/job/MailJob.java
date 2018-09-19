package com.waymaps.jobscheduler.job;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.waymaps.util.AppExecutors;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public class MailJob extends Job{

    public static final String TAG = "MAIL_JOB";

    public static Handler handler;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                MailJob.this.handler = handler;
                final int delay = 10000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(new Date().getTime());
                        handler.postDelayed(this,delay);
                    }
                },delay);
            }
        });


        return Result.RESCHEDULE;
    }

    public static void scheduleJob() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(MailJob.TAG);
        if (!jobRequests.isEmpty()) {
            return;
        }
        new JobRequest.Builder(MailJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15))
                .setUpdateCurrent(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }

}
