package com.waymaps;

import android.app.Application;

import com.evernote.android.job.JobConfig;
import com.evernote.android.job.JobManager;
import com.waymaps.data.AppRepository;
import com.waymaps.data.local.pref.LocalPreferenceManager;
import com.waymaps.jobscheduler.SMSAppJobCreator;
import com.waymaps.util.InjectorUtils;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class MainApplication extends Application {
    public static MainApplication INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new SMSAppJobCreator());
        INSTANCE = this;
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    private void setDefaultSettings(){
        AppRepository appRepository = InjectorUtils.provideRepository(this);
        appRepository.setDefaultServiceStatus();
    }
}
