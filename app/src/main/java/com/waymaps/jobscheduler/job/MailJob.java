package com.waymaps.jobscheduler.job;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.waymaps.data.AppRepository;
import com.waymaps.jobscheduler.util.JobHandler;
import com.waymaps.util.AppExecutors;
import com.waymaps.util.InjectorUtils;
import com.waymaps.util.ToastUtil;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public class MailJob extends Job{

    public static final String TAG = "MAIL_JOB";

    private AppRepository appRepository;

    public MailJob() {
    }

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        appRepository = InjectorUtils.provideRepository(getContext());
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                final int delay = 3000;
                final Handler handler = new Handler();
                JobHandler.getInstance().addTaskWithTag(TAG,handler);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ("1".equals(appRepository.getServiceStatus())){
                            System.out.println(TAG + " " + handler.hashCode());;
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
                //.setPeriodic(TimeUnit.MINUTES.toMillis(15))
                .startNow()
                //.setUpdateCurrent(true)
                //.setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                //.setRequirementsEnforced(true)
                .build()
                .schedule();
    }

}
