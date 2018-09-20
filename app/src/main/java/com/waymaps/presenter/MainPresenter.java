package com.waymaps.presenter;

import android.content.Intent;

import com.evernote.android.job.JobManager;
import com.waymaps.contract.MainContract;
import com.waymaps.data.AppRepository;
import com.waymaps.jobscheduler.job.MailJob;
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
        MailJob.scheduleJob();
        Intent intent = new Intent(mainView.getContext(),SMSService.class);
        mainView.startService(intent);
    }

    private void stopServices(){
        JobManager.instance().cancelAll();
        Intent intent = new Intent(mainView.getContext(),SMSService.class);
        mainView.stopService(intent);
    }
}
