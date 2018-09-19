package com.waymaps.presenter;

import com.evernote.android.job.JobManager;
import com.waymaps.contract.MainContract;
import com.waymaps.data.AppRepository;
import com.waymaps.jobscheduler.job.MailJob;

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
    }

    private void stopServices(){
        JobManager.instance().cancelAll();
        MailJob.handler.removeCallbacksAndMessages(null);
    }
}
