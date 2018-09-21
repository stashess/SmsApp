package com.waymaps.presenter;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.evernote.android.job.JobManager;
import com.waymaps.contract.MainContract;
import com.waymaps.data.AppRepository;
import com.waymaps.jobscheduler.job.MailJob;
import com.waymaps.jobscheduler.job.NotificationJob;
import com.waymaps.jobscheduler.job.ServerJob;
import com.waymaps.jobscheduler.util.JobHandler;
import com.waymaps.notification.NotificationUtil;
import com.waymaps.service.SMSService;

/**
 * Created by StanislavCheslavskyi on 19.09.2018.
 */

public class MainPresenter implements MainContract.MainPresenter {

    private AppRepository appRepository;
    private MainContract.MainView mainView;


    public MainPresenter(MainContract.MainView mainView, AppRepository appRepository) {
        this.mainView = mainView;
        this.appRepository = appRepository;
    }


    @Override
    public void onSwitchChange(boolean state) {
        appRepository.setServiceStatus(state?"1":"0");
        if (state) {
            startServices();
        } else {
            stopServices();
        }
    }

    @Override
    public void getServiceStatus() {
        String serviceStatus = appRepository.getServiceStatus();
        boolean state = "1".equals(serviceStatus);
        mainView.showServiceStatus(state);
    }

    private void startServices(){
        stopServices();
        MailJob.scheduleJob();
        ServerJob.scheduleJob();
        NotificationJob.scheduleJob();
        Intent intent = new Intent(mainView.getContext(),SMSService.class);
        mainView.startService(intent);
    }

    private void stopServices(){
        JobManager.instance().cancelAll();
        JobHandler.getInstance().deleteAllTask();

        NotificationManager notificationManager = (NotificationManager) mainView.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationUtil.N_SERVICE_START,0);

        Intent intent = new Intent(mainView.getContext(),SMSService.class);
        mainView.stopService(intent);
    }
}
