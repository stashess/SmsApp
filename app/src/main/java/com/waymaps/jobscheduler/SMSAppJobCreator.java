package com.waymaps.jobscheduler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.waymaps.jobscheduler.job.MailJob;
import com.waymaps.jobscheduler.job.NotificationJob;
import com.waymaps.jobscheduler.job.SMSJob;
import com.waymaps.jobscheduler.job.ServerJob;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public class SMSAppJobCreator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag){
            case MailJob.TAG: return new MailJob();
            case SMSJob.TAG: return new SMSJob();
            case ServerJob.TAG: return new ServerJob();
            case NotificationJob.TAG: return new NotificationJob();
            default: return null;
        }
    }
}
