package com.waymaps.jobscheduler.job;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.waymaps.data.AppRepository;
import com.waymaps.util.AppExecutors;
import com.waymaps.util.InjectorUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public class ServerJob extends Job{
    public static final String TAG = "SERVER_JOB";

    private AppRepository appRepository;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        appRepository = InjectorUtils.provideRepository(getContext());

        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                final int delay = 10000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ("1".equals(appRepository.getServiceStatus())){
                            System.out.println(handler.hashCode());;
                            handler.postDelayed(this,delay);
                        } else {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }
                },delay);
            }
        });

        return Result.SUCCESS;
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
