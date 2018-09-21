package com.waymaps.jobscheduler.job;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.waymaps.activity.MainActivity;
import com.waymaps.app.R;
import com.waymaps.data.AppRepository;
import com.waymaps.jobscheduler.util.JobHandler;
import com.waymaps.notification.NotificationUtil;
import com.waymaps.util.AppExecutors;
import com.waymaps.util.InjectorUtils;

import java.util.Random;
import java.util.Set;

/**
 * Created by StanislavCheslavskyi on 21.09.2018.
 */

public class NotificationJob extends Job {

    public static final String TAG = "NOTIFICATION_JOB";

    private AppRepository appRepository;

    public NotificationJob() {
    }

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), MainActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(getContext(), NotificationUtil.N_SERVICE_START)
                .setContentTitle(getContext().getResources().getString(R.string.app_name))
                .setContentText(getContext().getResources().getString(R.string.service_started))
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.ic_service_started)
                .setShowWhen(true)
                .setColor(Color.BLACK)
                .setLocalOnly(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(true)
                .build();

        NotificationManagerCompat.from(getContext())
                .notify(NotificationUtil.N_SERVICE_START,0, notification);

        return Result.SUCCESS;
    }


    public static void scheduleJob() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(NotificationJob.TAG);
        if (!jobRequests.isEmpty()) {
            return;
        }

        new JobRequest.Builder(NotificationJob.TAG)
                .startNow()
                .build()
                .schedule();
    }

}
